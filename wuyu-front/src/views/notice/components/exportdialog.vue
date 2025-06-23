<!--
 * @Author: hezeliangfj
 * @Date: 2025-06-18 22:05:00
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-23 16:35:31
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\views\notice\components\exportdialog.vue
 * @Descripttion: 导出弹框组件
-->
<template>
  <div>
    <!-- 导出确认对话框 -->
    <el-dialog
      :title="`导出${studentInfo?.studentName || ''}同学的通知册`"
      :visible="internalVisible"
      width="30%"
      :close-on-click-modal="false"
      :before-close="handleClose"
      class="export-dialog">
      <div class="export-content">
        <p>确认要导出 <strong>{{ studentInfo?.studentName || '' }}</strong> 同学的通知册吗？</p>
        <p class="export-tip">导出格式：Word文档 (.docx)</p>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel" :disabled="isExporting">取 消</el-button>
        <el-button type="primary" @click="handleConfirm" :loading="isExporting">
          {{ isExporting ? '导出中...' : '确 认' }}
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { showLoading, closeLoading } from '@/utils/loading'

export default {
  name: 'ExportDialog',
  props: {
    // 导出对话框显示状态
    dialogVisible: {
      type: Boolean,
      default: false
    },
    // 学生信息
    studentInfo: {
      type: Object,
      default: () => ({
        studentName: '',
        studentId: ''
      })
    },
    // API基础URL
    apiBaseUrl: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      isExporting: false,
      internalVisible: this.dialogVisible
    }
  },
  watch: {
    dialogVisible(val) {
      this.internalVisible = val;
    },
    internalVisible(val) {
      this.$emit('update:dialogVisible', val);
    }
  },
  methods: {
    // 关闭弹框（右上角关闭、遮罩关闭、before-close）
    handleClose(done) {
      if (this.isExporting) return;
      this.resetDialog();
      if (done) done();
    },
    // 取消按钮
    handleCancel() {
      if (this.isExporting) return;
      this.resetDialog();
    },
    // 公共重置方法
    resetDialog() {
      this.isExporting = false;
      this.$emit('update:dialogVisible', false);
      this.$emit('close');
    },
    // 确认导出
    async handleConfirm() {
      if (this.isExporting || !this.studentInfo?.studentId) {
        this.$message.warning('无法导出：学生信息不完整');
        return;
      }
      try {
        this.isExporting = true;
        showLoading('正在导出，请稍候...');
        const params = {
          studentId: this.studentInfo.studentId
        };
        const queryString = Object.keys(params)
          .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
          .join('&');
        const downloadUrl = `${this.apiBaseUrl}/noticeBooklet/word/generate?${queryString}`;
        const response = await fetch(downloadUrl, {
          method: 'GET',
          headers: {
            'Accept': 'application/octet-stream'
          }
        });
        if (!response.ok) {
          throw new Error(`下载失败，服务器返回状态码: ${response.status}`);
        }
        const arrayBuffer = await response.arrayBuffer();
        if (!arrayBuffer || arrayBuffer.byteLength === 0) {
          throw new Error('下载的文件为空');
        }
        const blob = new Blob([arrayBuffer], { type: 'application/octet-stream' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = `${this.studentInfo?.studentName || '未知学生'}的通知册.docx`;
        document.body.appendChild(a);
        a.click();
        setTimeout(() => {
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);
          closeLoading();
          this.$message.success('导出成功');
          this.resetDialog();
        }, 1000);
      } catch (error) {
        console.error('导出失败:', error);
        closeLoading();
        this.$message.error('导出失败：' + (error.message || '未知错误'));
      } finally {
        this.isExporting = false;
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.export-dialog {
  ::v-deep .el-dialog__title {
    display: inline-block;
    width: 100%;
    text-align: center;
  }
}

.export-content {
  text-align: center;

  p {
    margin: 10px 0;
    font-size: 14px;
    color: #333;

    strong {
      color: #409EFF;
      font-weight: bold;
    }
  }

  .export-tip {
    color: #909399;
    font-size: 12px;
    margin-top: 15px;
  }
}

.dialog-footer {
  text-align: center;
}
</style>