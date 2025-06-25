<!--
 * @Author: hezeliangfj
 * @Date: 2025-06-18 20:42:59
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-18 21:58:22
 * @version: 0.0.1
 * @FilePath: \medical-demo\src\view\schedule\doctor\preview.vue
 * @Descripttion: 预览通知册弹框组件
 *
 * 使用方法：
 * 1. 在父组件中引入：import PreviewDialog from './components/previewdialog.vue'
 * 2. 注册组件：components: { PreviewDialog }
 * 3. 在模板中使用：
 *    <PreviewDialog
 *      :dialog-visible.sync="previewDialogVisible"
 *      :content.sync="previewContent"
 *      :student-info="currentStudent"
 *      ref="previewDialog"
 *      @close="handlePreviewClose"
 *    />
 * 4. 调用预览方法：
 *    await this.$refs.previewDialog.previewStudentNotice(studentInfo)
 *
 * Props:
 * - dialogVisible: 控制弹框显示/隐藏
 * - content: 预览内容（HTML字符串）
 * - studentInfo: 学生信息对象 { studentId, studentName }
 *
 * Events:
 * - close: 弹框关闭时触发
 * - update:dialogVisible: 更新弹框显示状态
 * - update:content: 更新预览内容
-->
<template>
  <div>
    <!-- 预览通知册对话框 -->
    <el-dialog
      :title="`预览${previewStudentName || ''}同学的通知册`"
      :visible.sync="dialogVisible"
      width="60%"
      :close-on-click-modal="false"
      :before-close="handleClose"
      class="preview-dialog">
      <iframe :srcdoc="content" style="width:100%;height:600px;border:none;"></iframe>
      <span slot="footer" class="dialog-footer">
        <el-button type="success" @click="showExportOptions">导出PDF</el-button>
        <el-button type="primary" @click="handleClose">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 导出选项对话框 -->
    <el-dialog
      width="30%"
      title="选择下载方式"
      :visible.sync="exportDialogVisible"
      append-to-body>
      <el-radio-group v-model="exportOption">
        <el-radio :label="1">不可选下载位置</el-radio>
        <el-radio :label="0">可选下载位置</el-radio>
      </el-radio-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelExport">取 消</el-button>
        <el-button type="primary" @click="confirmExport">确 认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { showLoading, closeLoading } from '@/utils/loading'
import { previewNoticeBooklet } from '@/api/notice'
import html2pdf from 'html2pdf.js'

export default {
  name: 'PreviewDialog',
  props: {
    // 预览对话框显示状态
    dialogVisible: {
      type: Boolean,
      default: false
    },
    // 预览内容
    content: {
      type: String,
      default: ''
    },
    // 学生信息
    studentInfo: {
      type: Object,
      default: () => ({
        studentName: '',
        studentId: ''
      })
    }
  },
  data() {
    return {
      previewStudentName: '',
      exportDialogVisible: false,
      exportOption: 1
    }
  },
  watch: {
    dialogVisible(newVal) {
      if (newVal && this.studentInfo && this.studentInfo.studentName) {
        this.previewStudentName = this.studentInfo.studentName
      }
    }
  },
  methods: {
    // 关闭对话框
    handleClose() {
      this.$emit('update:dialogVisible', false)
      this.$emit('close')
    },

    // 显示导出选项
    showExportOptions() {
      if (!this.previewStudentName) {
        this.$message.warning('学生信息不完整，无法导出')
        return
      }
      this.exportDialogVisible = true
    },

    // 取消导出
    cancelExport() {
      this.exportDialogVisible = false
    },

    // 确认导出
    confirmExport() {
      this.exportDialogVisible = false
      if (this.exportOption === 1) {
        this.exportToPDF()
      } else {
        this.exportToPrint()
      }
    },

    // 打印方式导出
    exportToPrint() {
      const iframe = this.$el.querySelector('iframe')
      if (iframe && iframe.contentWindow) {
        const doc = iframe.contentDocument || iframe.contentWindow.document
        const oldTitle = doc.title
        doc.title = `${this.previewStudentName || ''}同学的通知册`
        iframe.contentWindow.focus()
        iframe.contentWindow.print()
        setTimeout(() => {
          doc.title = oldTitle
        }, 1000)
      }
    },

    // PDF方式导出
    exportToPDF() {
      const iframe = this.$el.querySelector('iframe')
      if (iframe && iframe.contentWindow) {
        showLoading('正在导出PDF，请稍候...')
        const doc = iframe.contentDocument || iframe.contentWindow.document
        let styleHtml = ''
        doc.querySelectorAll('style').forEach(style => {
          styleHtml += style.outerHTML
        })
        const bodyHtml = doc.body ? doc.body.innerHTML : ''
        const container = document.createElement('div')
        container.style.padding = '0 20px'
        container.innerHTML = styleHtml + bodyHtml
        document.body.appendChild(container)

        html2pdf()
          .set({
            filename: `${this.previewStudentName || ''}同学的通知册.pdf`,
            html2canvas: { scale: 2 },
            jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
          })
          .from(container)
          .save()
          .then(() => {
            document.body.removeChild(container)
            closeLoading()
            this.$message.success('PDF导出成功')
          })
          .catch((error) => {
            console.error('PDF导出失败:', error)
            closeLoading()
            this.$message.error('PDF导出失败')
            document.body.removeChild(container)
          })
      }
    },

    // 预览学生通知册（供外部调用）
    async previewStudentNotice(studentInfo) {
      try {
        if (!studentInfo || !studentInfo.studentId) {
          this.$message.error('学生信息不完整')
          return
        }

        showLoading('预览生成中，请稍候...')
        const params = {
          studentId: studentInfo.studentId
        }
        const res = await previewNoticeBooklet({ params })
        if (res.code === 200) {
          // 在content中插入首行缩进样式
          let html = res.data
          // 在<head>后插入样式
          html = html.replace(/<head>/i, '<head><style>.content-section{text-indent:2em;}</style>')
          this.$emit('update:content', html)
          this.previewStudentName = studentInfo.studentName || ''
          this.$emit('update:dialogVisible', true)
        } else {
          this.$message.error('预览数据获取失败')
        }
      } catch (error) {
        console.error('预览失败:', error)
        this.$message.error('预览失败：' + (error.message || '未知错误'))
      } finally {
        closeLoading()
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
    padding: 10px;
  }

  ::v-deep .el-dialog__title {
    display: inline-block;
    width: 100%;
    text-align: center;
  }
}

.dialog-footer {
  text-align: center;
}
</style>
