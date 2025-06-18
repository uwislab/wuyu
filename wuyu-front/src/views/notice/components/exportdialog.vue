<template>
  <div>
    <!-- 导出通知册对话框 -->
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
        <el-button type="success" @click="innerVisible=true">导出pdf</el-button>
        <el-button type="primary" @click="cleanpreview">确 定</el-button>
      </span>
      <el-dialog
        width="30%"
        title="内层 Dialog"
        :visible.sync="innerVisible"
        append-to-body>
        <el-radio-group v-model="option">
          <el-radio :label="1">不可选下载位置</el-radio>
          <el-radio :label="0">可选下载位置</el-radio>
        </el-radio-group>
        <div slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="confirm">确 认</el-button>
        </div>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
import { showLoading, closeLoading } from '@/utils/loading'
import { previewNoticeBooklet,noticeBookletModify } from '@/api/notice'
import html2pdf from 'html2pdf.js'
export default {
  name: 'PreviewDialogs',
  props: {
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
      previewStudentName: '',
      innerVisible: false, // 内层对话框的显示状态
      option:1
    }
    },
    methods: {
        // 单个导出
    async handleExport(row) {
      try {
        showLoading('正在导出，请稍候...')
        const params = {
          studentId: row.studentId
        }

        // 构建查询字符串
        const queryString = Object.keys(params)
          .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
          .join('&')
        const downloadUrl = `${this.apiBaseUrl}/noticeBooklet/word/generate?${queryString}`

        // 发起下载请求
        const response = await fetch(downloadUrl, {
          method: 'GET',
          headers: {
            'Accept': 'application/octet-stream'
          }
        })

        if (!response.ok) {
          throw new Error(`下载失败，服务器返回状态码: ${response.status}`)
        }

        const arrayBuffer = await response.arrayBuffer()
        if (!arrayBuffer || arrayBuffer.byteLength === 0) {
          throw new Error('下载的文件为空')
        }
        const blob = new Blob([arrayBuffer], { type: 'application/octet-stream' })
        const url = window.URL.createObjectURL(blob)
        const a = document.createElement('a')
        a.style.display = 'none'
        a.href = url
        a.download = `${row.studentName}的通知册.docx`
        document.body.appendChild(a)
        a.click()

        setTimeout(() => {
          window.URL.revokeObjectURL(url)
          document.body.removeChild(a)
          closeLoading()
          this.$message.success('导出成功')
        }, 1000)

      } catch (error) {
        console.error('导出失败:', error)
        closeLoading()
        this.$message.error('导出失败：' + (error.message || '未知错误'))
      }
    },
      handleClose() {
        this.$emit('update:dialogVisible', false)
        this.$emit('update:dialogVisiblepreview', false)
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
        // 取消下载
        cancel() {
          this.innerVisible = false;
          this.dialogVisiblepreview= false;
        },
        // 确认下载
        confirm() {
          this.innerVisible = false;
          this.dialogVisiblepreview= false;
          if (this.option === 1) {
            this.doprint1();
          } else {
            this.doprint2();
          }
        },
        // 导出pdf版本的通知册
        doprint1() {
          const iframe = this.$el.querySelector('iframe');
          if (iframe && iframe.contentWindow) {
            const doc = iframe.contentDocument || iframe.contentWindow.document;
            const oldTitle = doc.title;
            doc.title = `${this.previewStudentName}同学的通知册`;
            iframe.contentWindow.focus();
            iframe.contentWindow.print();
            setTimeout(() => {
              doc.title = oldTitle;
            }, 1000);
          }
        },
        doprint2() {
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

</style>