<template>
  <el-dialog
    title="选择任课教师"
    :visible.sync="dialogVisible"
    width="60%"
    :modal="false"
    :before-close="handleClose">
    <el-input
      v-model="searchQuery"
      placeholder="输入教师姓名/拼音搜索"
      prefix-icon="el-icon-search"
      class="mb-4" />
    <el-table
      :data="filteredTeachers"
      stripe
      size="small"
      @row-click="handleTeacherSelect">
      <el-table-column prop="teacherName" label="教师姓名" width="120" />
      <el-table-column prop="position" label="职位" width="120" />
      <el-table-column prop="title" label="职称" width="100" />
      <el-table-column prop="phoneNum" label="联系电话" width="160" />
      <el-table-column label="操作" width="100">
        <template slot-scope="{ row }">
          <el-button type="primary" size="small" @click="handleTeacherSelect(row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page.sync="pagination.current"
      :page-size="pagination.size"
      :total="totalTeachers"
      layout="prev, pager, next"
      class="mt-4"
      @current-change="handlePageChange" />
  </el-dialog>
</template>

<script>
import { getTeacherListByPage } from '@/api/schedulModule/index'
import pinyin from 'pinyin'

export default {
  name: 'TeacherSel',
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dialogVisible: false,
      searchQuery: '',
      teachers: [],
      teacherPinyin: [], // 存储教师拼音数据
      pagination: {
        current: 1,
        size: 10
      },
      totalTeachers: 0
    }
  },
  computed: {
    filteredTeachers() {
      if (!this.searchQuery) {
        return this.teachers
      }
      const query = this.searchQuery.toLowerCase()
      return this.teachers.filter(teacher => {
        const teacherPinyin = this.teacherPinyin.find(p => p.id === teacher.id)
        return teacher.teacherName.toLowerCase().includes(query) ||
               (teacherPinyin && teacherPinyin.pinyin.toLowerCase().includes(query))
      })
    }
  },
  watch: {
    visible(val) {
      this.dialogVisible = val
      if (val) {
        this.fetchTeachers()
      }
    },
    dialogVisible(val) {
      if (!val) {
        this.$emit('update:visible', false)
      }
    }
  },
  methods: {
    // 获取教师列表（使用教师管理中的接口getTeacherListByPage）
    async fetchTeachers() {
      try {
        const res = await getTeacherListByPage({
          pageNum: this.pagination.current,
          pageSize: this.pagination.size
        })
        if (res.code === 200) {
          this.teachers = res.data.list
          this.totalTeachers = res.data.total
          // 生成拼音数据
          this.teacherPinyin = this.teachers.map(teacher => ({
            id: teacher.id,
            name: teacher.teacherName,
            pinyin: pinyin(teacher.teacherName, {
              style: pinyin.STYLE_NORMAL,
              heteronym: false
            }).join('')
          }))
        }
      } catch (error) {
        console.error('获取教师列表失败:', error)
        this.$message.error('获取教师列表失败')
      }
    },
    handleTeacherSelect(teacher) {
      this.$emit('select', teacher)
      this.handleClose()
    },
    handlePageChange(page) {
      this.pagination.current = page
      this.fetchTeachers()
    },
    handleClose() {
      this.searchQuery = ''
      this.pagination.current = 1
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.el-dialog {
  z-index: 3000 !important;
}

.el-dialog__wrapper {
  z-index: 2000 !important;
}

.mb-4 {
  margin-bottom: 16px;
}

.mt-4 {
  margin-top: 16px;
}
</style>
