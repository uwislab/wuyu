<!--
 * @Author: hezeliangfj
 * @Date: 2025-06-14 12:54:59
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-17 14:34:56
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\views\notice\index.vue
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div>
    <div class="filter-container">
      <div class="left-filters">
        <el-select v-model="gradeId" placeholder="请选择年级" style="width:150px">
          <el-option
            v-for="item in grades"
            :key="item"
            :label="`${item}年级`"
            :value="item">
          </el-option>
        </el-select>
        <el-select v-model="classId" placeholder="请选择班级" style="margin-left: 20px;width: 150px;">
          <el-option
            v-for="item in classes"
            :key="item"
            :label="`${item}班`"
            :value="item">
          </el-option>
        </el-select>
      </div>
      <div class="right-actions">
        <el-button type="primary" @click="dialogVisible=true">批量导出<i class="el-icon-tickets"></i></el-button>
      </div>
    </div>
    <el-dialog :close-on-click-modal="false"
      title="批量导出"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <!-- <span v-if="selectedStudents.length > 0">
        是否导出选中的 {{ selectedStudents.length }} 名学生（{{ selectedStudents.map(s => s.studentName).join('、') }}）的通知册信息
      </span> -->
      <span>
        是否导出{{ gradeId ? gradeId + '年级' : '' }}{{ classId ? classId + '班' : '' }}的所有学生的通知册信息
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleExportBatch">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="预览通知册"
      :visible.sync="dialogVisiblepreview"
      width="60%"
      :before-close="handleClose"
      >
      <span v-html="content"></span>
      <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cleanpreview">确 定</el-button>
      </span>
    </el-dialog>
    <div class="table-container">
      <el-table :data="paginatedList" style="width: 100%" id="dataTable" border stripe>
        <!-- <el-table-column fixed type="selection" tooltip-effect="dark" width='40'>
        </el-table-column> -->
        <el-table-column prop="studentGrade" label="年级" width='80'>
        </el-table-column>
        <el-table-column prop="studentClassNumber" label="班级" width='80'>
        </el-table-column>
        <el-table-column prop="studentId" label="学生学号" >
        </el-table-column>
        <el-table-column prop="studentName" label="学生姓名">
        </el-table-column>
        <el-table-column label="德育" align="center">
          <el-table-column prop="sdeyu" label="实际" width="80">
            <template slot-scope="scope">
              {{ scope.row.sdeyu || '暂无' }}
            </template>
          </el-table-column>
          <el-table-column prop="pdeyu" label="期望" width="80">
            <template slot-scope="scope">
              {{ scope.row.pdeyu || '暂无' }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="劳育" align="center">
          <el-table-column prop="slaoyu" label="实际" width="80">
            <template slot-scope="scope">
              {{ scope.row.slaoyu || '暂无' }}
            </template>
          </el-table-column>
          <el-table-column prop="plaoyu" label="期望" width="80">
            <template slot-scope="scope">
              {{ scope.row.plaoyu || '暂无' }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="体育" align="center">
          <el-table-column prop="stiyu" label="实际" width="80">
            <template slot-scope="scope">
              {{ scope.row.stiyu || '暂无' }}
            </template>
          </el-table-column>
          <el-table-column prop="ptiyu" label="期望" width="80">
            <template slot-scope="scope">
              {{ scope.row.ptiyu || '暂无' }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="智育" align="center">
          <el-table-column prop="szhiyu" label="实际" width="80">
            <template slot-scope="scope">
              {{ scope.row.szhiyu || '暂无' }}
            </template>
          </el-table-column>
          <el-table-column prop="pzhiyu" label="期望" width="80">
            <template slot-scope="scope">
              {{ scope.row.pzhiyu || '暂无' }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="美育" align="center">
          <el-table-column prop="smeiyu" label="实际" width="80">
            <template slot-scope="scope">
              {{ scope.row.smeiyu || '暂无' }}
            </template>
          </el-table-column>
          <el-table-column prop="pmeiyu" label="期望" width="80">
            <template slot-scope="scope">
              {{ scope.row.pmeiyu || '暂无' }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200px">
          <template slot-scope="scope">
            <el-button type="success" @click="handlepreview(scope.row)">预览<i class="el-icon-tickets"></i></el-button>
            <el-button type="primary" @click="handleExport(scope.row)">导出<i class="el-icon-tickets"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页器 -->
      <div >
        <el-pagination :current-page="query.page" :page-sizes="[10, 30, 100]" :page-size="query.pageSize"
          :total="totalCount" @size-change="handleSizeChange" @current-change="handleCurrentChange"
          layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { getStudent, exportZip,noticeBooklet,exportBooklet,previewNoticeBooklet,exportNoticeBooklet } from '@/api/notice.js'
import { showLoading, closeLoading } from '@/utils/loading'
// import {}
import axios from 'axios'
// import { create } from 'sortablejs';
export default {
  // name: 'NoticeIndex',
  // computed: {
  //   ...mapGetters([
  //     'permission_routes'
  //   ]),
  //   menuRoutes() {
  //     return this.permission_routes
  //   }
  // },
  data() {
    return {
      dialogVisible: false,
      dialogVisiblepreview: false,
      list: [],
      grades: [],
      classNames: [],
      classes: [],
      gradeId: null,
      classId: null,
      apiBaseUrl: process.env.VUE_APP_DEVELOP06_API,
      // 查询
      query: {
        "pageSize": 10,
        "page": 1,
        "courseName": "",
        "courseType": "",
        "teacherName": "",
        "studentNum": "",
        "studentName": "",
        "login_time": "",
        "create_time": "",
        "orderby": `create_time desc`
      },
      // 数据
      total: 0,//数据总数
      list: [],
      list_user_course_teacher: [],
      deleteIds: [],
      selectedStudents: [], // 存储选中的学生信息
      uploadHeaders: {
        'Content-Type': 'multipart/form-data'
      },
      uploadFile: null,
      content: '',
      downloadStatus: false, // 添加下载状态标志
    }
  },
  computed: {
    filteredClasses() {
      if (!this.gradeId) return []
      const uniqueClasses = new Set()
      this.classNames.forEach(item => {
        if (item[1] === this.gradeId) {
          uniqueClasses.add(item[0])
        }
      })
      return Array.from(uniqueClasses).sort((a, b) => a - b)
    },
    // 过滤后的数据
    filteredList() {
      let result = [...this.list]

      // 根据年级和班级筛选
      if (this.gradeId) {
        result = result.filter(item => item.studentGrade === this.gradeId)
      }
      if (this.classId) {
        result = result.filter(item => item.studentClassNumber === this.classId)
      }

      return result
    },
    // 分页后的数据
    paginatedList() {
      const start = (this.query.page - 1) * this.query.pageSize
      const end = start + this.query.pageSize
      return this.filteredList.slice(start, end)
    },
    // 总数据量
    totalCount() {
      return this.filteredList.length
    }
  },
  watch: {
    gradeId: {
      handler(newVal) {
        this.classId = null
        this.classes = this.filteredClasses
        this.query.page = 1 // 重置页码
      },
      immediate: true
    },
    classId: {
      handler() {
        this.query.page = 1 // 重置页码
      }
    },
    downloadStatus(newVal) {
      if (newVal) {
        // 下载完成后的处理
        setTimeout(() => {
          closeLoading()
          this.$message.success('批量导出成功')
          this.dialogVisible = false
          this.downloadStatus = false // 重置状态
        }, 1000)
      }
    }
  },
  methods: {
    handleClose () {
      this.dialogVisible = false
      this.dialogVisiblepreview = false
    },
    async fetchCourse() {
      const params = {}
      const res = await getStudent(params)
      this.grades = res.data.grades
      this.classNames = res.data.classNames
    },
    async fetchNoticeBooklet() {
      const params = {
        isRemark: false
      }
      const res = await noticeBooklet({params})
      this.list = res.data
    },
    handleSizeChange(pageSize) {
      this.query.pageSize = pageSize
      this.query.page = 1 // 切换每页条数时重置为第一页
    },
    handleCurrentChange(page) {
      this.query.page = page
    },
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
    // 批量导出
    async handleExportBatch() {
      try {
        showLoading('批量导出中，请稍候...')
        const params = {
          gradeId: this.gradeId
        }
        if (this.classId && !isNaN(this.classId)) {
          params.classId = Number(this.classId)
        }

        // 构建查询字符串
        const queryString = Object.keys(params)
          .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
          .join('&')
        const downloadUrl = `${this.apiBaseUrl}/export/zip?${queryString}`
        console.log('开始下载，URL:', downloadUrl)

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

        if (arrayBuffer.byteLength < 100) {
          throw new Error('下载的文件太小，可能已损坏')
        }

        const blob = new Blob([arrayBuffer], { type: 'application/zip' })
        const url = window.URL.createObjectURL(blob)
        const a = document.createElement('a')
        a.style.display = 'none'
        a.href = url
        a.download = `${this.gradeId}年级${this.classId ? this.classId + '班' : ''}的通知册.zip`
        document.body.appendChild(a)
        a.click()

        setTimeout(() => {
          window.URL.revokeObjectURL(url)
          document.body.removeChild(a)
          closeLoading()
          this.$message.success('批量导出成功')
          this.dialogVisible = false
        }, 1000)

      } catch (error) {
        console.error('批量导出失败:', error)
        closeLoading()
        this.$message.error('批量导出失败：' + (error.message || '未知错误'))
      }
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
          this.dialogVisiblepreview = true
          this.content = res.data
        } else {
          this.$message.error('预览数据获取失败')
        }
      } catch (error) {
        console.error('预览失败:', error)
      } finally {
        closeLoading()
      }
    },
    cleanpreview() {
      this.dialogVisiblepreview = false;
      this.content = ""; // 清除预览内容
    },
    handleFileChange(file) {
      this.uploadFile = file.raw
    },
    cancelExcel() {
      this.uploadFile = null
      this.dialogVisible = false
    },
    handleSelectionChange(selection) {
      this.selectedStudents = selection.map(item => ({
        studentId: item.studentId,
        studentName: item.studentName
      }))
      // console.log('选中的学生：', this.selectedStudents)
    }
  },
  created() {
    this.fetchCourse()
    this.fetchNoticeBooklet()
  }
}
</script>

<style lang="scss" scoped>
.filter-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;

  .left-filters {
    display: flex;
    margin-left: 20px;
  }

  .right-actions {
    margin-right: 20px;
  }
}

.table-container {
  margin: 0px 20px 20px 20px;
  // padding: 20px;
  background-color: #fff;
  border-radius: 4px;
}
</style>
