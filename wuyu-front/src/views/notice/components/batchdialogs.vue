<!--
 * @Author: hezeliangfj
 * @Date: 2025-06-18 11:23:01
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-18 17:21:29
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\views\notice\components\dialogs.vue
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div>
    <!-- 批量导出对话框 -->
    <el-dialog
      :close-on-click-modal="false"
      title="批量导出"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>
        是否导出{{ gradeId ? gradeId + '年级' : '' }}{{ classId ? classId + '班' : '' }}的所有学生的通知册信息
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleExportBatch">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 预览通知册对话框 -->
    <el-dialog
      :title="`预览${previewStudentName || ''}同学的通知册`"
      :visible.sync="dialogVisiblepreview"
      width="50%" :close-on-click-modal="false"
      :before-close="handleClose"
      class="preview-dialog">
      <!-- <span>{{content}}</span> -->
      <!-- <div class="preview-content" v-html="content"></div> -->
      <iframe :srcdoc="content" style="width:100%;height:600px;border:none;"></iframe>
      <span slot="footer" class="dialog-footer">
        <el-button type="success" @click="doprint">导出pdf</el-button>
        <el-button type="primary" @click="cleanpreview">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑学生信息对话框 -->
    <el-dialog
      title="编辑学生信息"
      :visible.sync="editDialogVisible"
      width="50%" :close-on-click-modal="false"
      :before-close="handleEditClose"
      class="edit-dialog">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="editForm.studentName" disabled style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="editForm.studentId" disabled style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input :value="editForm.studentGrade ? editForm.studentGrade + '年级' : ''" disabled style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input :value="editForm.studentClassNumber ? editForm.studentClassNumber + '班' : ''" disabled style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="德育">
          <div class="wuyu-row">
            <span class="wuyu-sub-label">实际</span>
            <el-input v-model="editForm.sdeyu" size="small" class="wuyu-input"></el-input>
            <span class="wuyu-sub-label">期望</span>
            <el-input v-model="editForm.pdeyu" size="small" class="wuyu-input"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="智育">
          <div class="wuyu-row">
            <span class="wuyu-sub-label">实际</span>
            <el-input v-model="editForm.szhiyu" size="small" class="wuyu-input"></el-input>
            <span class="wuyu-sub-label">期望</span>
            <el-input v-model="editForm.pzhiyu" size="small" class="wuyu-input"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="体育">
          <div class="wuyu-row">
            <span class="wuyu-sub-label">实际</span>
            <el-input v-model="editForm.stiyu" size="small" class="wuyu-input"></el-input>
            <span class="wuyu-sub-label">期望</span>
            <el-input v-model="editForm.ptiyu" size="small" class="wuyu-input"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="美育">
          <div class="wuyu-row">
            <span class="wuyu-sub-label">实际</span>
            <el-input v-model="editForm.smeiyu" size="small" class="wuyu-input"></el-input>
            <span class="wuyu-sub-label">期望</span>
            <el-input v-model="editForm.pmeiyu" size="small" class="wuyu-input"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="劳育">
          <div class="wuyu-row">
            <span class="wuyu-sub-label">实际</span>
            <el-input v-model="editForm.slaoyu" size="small" class="wuyu-input"></el-input>
            <span class="wuyu-sub-label">期望</span>
            <el-input v-model="editForm.plaoyu" size="small" class="wuyu-input"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="假期计划">
          <el-input type="textarea" v-model="editForm.pplan"></el-input>
        </el-form-item>
        <el-form-item label="班主任建议">
          <el-input type="textarea" v-model="editForm.comment"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleEditClose">取 消</el-button>
        <el-button type="primary" @click="handleEditSave">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { showLoading, closeLoading } from '@/utils/loading'
import { previewNoticeBooklet,noticeBookletModify } from '@/api/notice'
import html2pdf from 'html2pdf.js'
export default {
  name: 'NoticeDialogs',
  props: {
    // 批量导出对话框显示状态
    dialogVisible: {
      type: Boolean,
      default: false
    },
    // 预览对话框显示状态
    dialogVisiblepreview: {
      type: Boolean,
      default: false
    },
    // 年级ID
    gradeId: {
      type: [String, Number],
      default: null
    },
    // 班级ID
    classId: {
      type: [String, Number],
      default: null
    },
    // 预览内容
    content: {
      type: String,
      default: ''
    },
    // API基础URL
    apiBaseUrl: {
      type: String,
      required: true
    },
    // 总数据量
    totalCount: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      downloadStatus: false, // 添加下载状态标志
      editDialogVisible: false,
      editForm: {
        studentName: '',
        studentId: '',
        classId: NaN,
        gradeId: NaN
        // 其它字段
      },
      previewStudentName: ''
    }
  },
  watch: {
    downloadStatus(newVal) {
      if (newVal) {
        // 下载完成后的处理
        setTimeout(() => {
          closeLoading()
          this.$message.success('批量导出成功！')
          this.$emit('update:dialogVisible', false)
          this.downloadStatus = false // 重置状态
        }, 100)
      }
    }
  },
  methods: {
    handleClose() {
      this.$emit('update:dialogVisible', false)
      this.$emit('update:dialogVisiblepreview', false)
    },
    // 批量导出
    async handleExportBatch() {
      try {
        showLoading('批量导出中，请稍候...');
        const params = {
          gradeId: this.gradeId
        };
        if (this.classId && !isNaN(this.classId)) {
          params.classId = Number(this.classId);
        }
        // 构建查询字符串
        const queryString = Object.keys(params)
          .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
          .join('&');
        const downloadUrl = `${this.apiBaseUrl}/export/zip?${queryString}`;
        // 创建隐藏的 iframe 触发下载
        const iframe = document.createElement('iframe');
        iframe.style.display = 'none';
        iframe.src = downloadUrl;
        document.body.appendChild(iframe);

        // 使用 Promise 动态等待下载完成（最多等待 3.5 秒）
        const downloadTimeout = new Promise((resolve) => {
          setTimeout(() => {
            document.body.removeChild(iframe);
            resolve();
          }, this.totalCount*3000); // 设置最大等待时间（可根据实际文件大小调整）
        });
        // 等待下载完成或超时
        await downloadTimeout;
        closeLoading();
        this.$message.success('批量导出成功');
        this.$emit('update:dialogVisible', false);
      } catch (error) {
        console.error('批量导出失败:', error);
        closeLoading();
        this.$message.error('批量导出失败：' + (error.message || '未知错误'));
        document.body.removeChild(iframe); // 确保移除 iframe
      }
    },
    // 清除预览
    cleanpreview() {
      this.$emit('update:dialogVisiblepreview', false);
      this.$emit('update:content', ''); // 清除预览内容
    },
    // 预览下载
    async handlepreview(row) {
      try {
        showLoading('预览生成中，请稍候...')
        const params = {
          studentId: row.studentId,
        }
        const res = await previewNoticeBooklet({ params })
        if (res.code === 200) {
          // 在content中插入首行缩进样式
          let html = res.data;
          // 在<head>后插入样式
          html = html.replace(/<head>/i, '<head><style>.content-section{text-indent:2em;}</style>');
          this.$emit('update:dialogVisiblepreview', true)
          this.$emit('update:content', html)
          this.previewStudentName = row.studentName;
        } else {
          this.$message.error('预览数据获取失败')
        }
      } catch (error) {
        console.error('预览失败:', error)
      } finally {
        closeLoading()
      }
    },
    openEditDialog(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
      // console.log(this.editForm,row);
    },
    handleEditClose() {
      this.editDialogVisible = false;
    },
    handleEditSave() {
      // 这里可以emit事件给父组件，或直接保存
      this.$emit('edit-save', this.editForm);
      this.editDialogVisible = false;
    },
    // 导出pdf版本的通知册
    // doprint() {
    //   const iframe = this.$el.querySelector('iframe');
    //   if (iframe && iframe.contentWindow) {
    //     const doc = iframe.contentDocument || iframe.contentWindow.document;
    //     const oldTitle = doc.title;
    //     doc.title = `${this.previewStudentName}同学的通知册`;
    //     iframe.contentWindow.focus();
    //     iframe.contentWindow.print();
    //     setTimeout(() => {
    //       doc.title = oldTitle;
    //     }, 1000);
    //   }
    // }
    doprint() {
      const iframe = this.$el.querySelector('iframe');
      if (iframe && iframe.contentWindow) {
        showLoading('正在导出PDF，请稍候...');
        const doc = iframe.contentDocument || iframe.contentWindow.document;
        let styleHtml = '';
        doc.querySelectorAll('style').forEach(style => {
          styleHtml += style.outerHTML;
        });
        const bodyHtml = doc.body ? doc.body.innerHTML : '';
        const container = document.createElement('div');
        container.style.padding = '0 20px';
        container.innerHTML = styleHtml + bodyHtml;
        document.body.appendChild(container);
        html2pdf()
          .set({
            filename: `${this.previewStudentName}同学的通知册.pdf`,
            html2canvas: { scale: 2 },
            jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
          })
          .from(container)
          .save()
          .then(() => {
            document.body.removeChild(container);
            closeLoading();
          })
          .catch(() => {
            closeLoading();
          });
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.preview-dialog {
  ::v-deep .el-dialog {
    max-height: 85vh;
    display: flex;
    flex-direction: column;
  }
  ::v-deep .el-dialog__body {
    flex: 1;
    overflow: hidden;
    padding: 10px 10px;
  }
  // .preview-content {
  //   max-height: 60vh;
  //   overflow: hidden;
  //   // padding: 10px;
  //   border: 1px solid #e4e7ed;
  //   border-radius: 4px;
  //   background-color: #fafafa;
  //   transform: scale(1);
  //   transform-origin: top left;
  //   width: 100%; // 补偿缩放后的宽度 (100% / 0.6)
  //   height: 120%; // 补偿缩放后的高度 (100% / 0.6)

  //   // 确保HTML内容适应容器
  //   ::v-deep html, ::v-deep body {
  //     margin: 0;
  //     padding: 0;
  //     font-size: 12px;
  //     line-height: 1.4;
  //     width: 100%;
  //     box-sizing: border-box;
  //     overflow: hidden;
  //   }

  //   ::v-deep h1 {
  //     font-size: 16px;
  //     margin: 8px 0;
  //   }

  //   ::v-deep h2 {
  //     font-size: 14px;
  //     margin: 6px 0;
  //   }

  //   ::v-deep h3 {
  //     font-size: 12px;
  //     margin: 4px 0;
  //   }

  //   ::v-deep table {
  //     font-size: 10px;
  //     margin-bottom: 8px;
  //     width: 100%;
  //     table-layout: fixed;
  //     border-collapse: collapse;
  //   }

  //   ::v-deep th, ::v-deep td {
  //     padding: 3px 4px;
  //     word-wrap: break-word;
  //     overflow: hidden;
  //     text-overflow: ellipsis;
  //   }

  //   ::v-deep .section-header {
  //     margin-top: 8px;
  //   }

  //   ::v-deep .content-section {
  //     margin: 4px 0;
  //   }

  //   ::v-deep .signature-date {
  //     margin-top: 8px;
  //   }
  // }
}
.wuyu-row {
  display: flex;
  align-items: center;
  margin-bottom: 0;
}
.wuyu-label {
  width: 50px;
  color: #333;
  font-weight: bold;
  margin-right: 10px;
}
.wuyu-sub-label {
  margin: 0 6px;
  color: #888;
}
.wuyu-input {
  width: 80px;
  margin-right: 16px;
}
::v-deep .el-dialog__title {
  display: inline-block;
  width: 100%;
  text-align: center;
}
</style>
