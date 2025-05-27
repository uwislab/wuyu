<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.course"
        placeholder="课程名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="年级" prop="grade" align="center" />
      <el-table-column label="班级" prop="classNum" align="center" />
      <el-table-column label="班级名称" prop="className" align="center" />
      <el-table-column label="课程名称" prop="course" align="center" />
      <el-table-column label="授课教师" prop="teacherName" align="center">
        <template slot-scope="{row}">
          {{ row.teacherName || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleSetTeacher(row)">
            {{ row.teacherName ? '更换教师' : '设置教师' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <!-- 教师检索对话框 -->
    <teacher-sel
      :visible.sync="teacherDialogVisible"
      @select-teacher="handleTeacherSelected"
    />
  </div>
</template>

<script>
import TeacherSel from '@/components/TeacherSel'
import Pagination from '@/components/Pagination'

export default {
  name: 'CourseManagement',
  components: { TeacherSel, Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        course: undefined
      },
      teacherDialogVisible: false,
      currentCourse: null
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      // 模拟后端接口返回数据
      setTimeout(() => {
        this.list = [
          {
            id: 1,
            grade: 1,
            classNum: 1,
            className: '一年级1班',
            course: '语文',
            teacherName: ''
          },
          {
            id: 2,
            grade: 1,
            classNum: 1,
            className: '一年级1班',
            course: '数学',
            teacherName: ''
          },
          {
            id: 3,
            grade: 1,
            classNum: 1,
            className: '一年级1班',
            course: '英语',
            teacherName: ''
          }
        ]
        this.total = this.list.length
        this.listLoading = false
      }, 500)
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSetTeacher(row) {
      this.currentCourse = row
      this.teacherDialogVisible = true
    },
    handleTeacherSelected(teacher) {
      if (this.currentCourse) {
        // 更新课程列表中的教师信息
        const index = this.list.findIndex(item => item.id === this.currentCourse.id)
        if (index !== -1) {
          this.list[index].teacherName = teacher.teacherName
          this.list[index].teacherId = teacher.id
          
          // 这里应该调用后端接口保存数据
          // 暂时使用前端数据模拟
          this.$message.success('设置教师成功')
        }
      }
      this.teacherDialogVisible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.filter-container {
  padding-bottom: 10px;
  .filter-item {
    display: inline-block;
    vertical-align: middle;
    margin-bottom: 10px;
  }
}
</style> 