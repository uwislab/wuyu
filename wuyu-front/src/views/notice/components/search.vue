<!--
 * @Author: hezeliangfj
 * @Date: 2025-06-18 16:05:40
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-18 22:00:38
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\views\notice\components\search.vue
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div class="filter_container">
    <div class="left_filters">
      <el-select v-model="gradeId" placeholder="请选择年级" class="search-item" @change="onGradeChange">
        <el-option
          v-for="item in grades"
          :key="item"
          :label="`${item}年级`"
          :value="item">
        </el-option>
      </el-select>
      <el-select v-model="classId" placeholder="请选择班级" class="search-item" @change="onClassChange">
        <el-option
          v-for="item in classes"
          :key="item"
          :label="`${item}班`"
          :value="item">
        </el-option>
      </el-select>
      <el-input
        v-model="findKey"
        placeholder="请输入学生学号或姓名"
        class="search-item"
        style="width: 200px;"
        clearable
        @clear="onClearSearch">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
      <!-- <el-input v-model="inputName" placeholder="请输入学生姓名" class="search-item"></el-input> -->
    </div>
    <div class="right_actions">
      <el-button type="primary" @click="onSearch">查询</el-button>
    </div>
  </div>
</template>

<script>
import { getStudent, noticeBooklet, noticeBookletStudent } from '@/api/notice.js'
import { closeLoading,showLoading } from '@/utils/loading'

export default {
  name: 'SearchBar',
  data() {
    return {
      gradeId: null,
      classId: null,
      grades: [],
      classNames: [],
      classes: [],
      list: [],
      inputNum: '',
      inputName: '',
      searchTimer: null,
      isSearching: false,
      findKey: ''
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
    filteredList() {
      let result = [...this.list]
      if (this.gradeId) {
        result = result.filter(item => item.studentGrade === this.gradeId)
      }
      if (this.classId) {
        result = result.filter(item => item.studentClassNumber === this.classId)
      }
      return result
    }
  },
  // watch: {
  //   // 监听输入框变化，实现实时搜索
  //   inputNum: {
  //     handler(newVal) {
  //       // 清除之前的定时器
  //       if (this.searchTimer) {
  //         clearTimeout(this.searchTimer)
  //       }

  //       // 如果输入为空，恢复原始数据
  //       if (!newVal || newVal.trim() === '') {
  //         this.fetchNoticeBooklet()
  //         return
  //       }

  //       // 设置防抖，500ms后执行搜索
  //       this.searchTimer = setTimeout(() => {
  //         this.searchStudent(newVal.trim())
  //       }, 500)
  //     },
  //     immediate: false
  //   }
  // },
  methods: {
    async fetchCourse() {
      const params = {}
      const res = await getStudent(params)
      this.grades = res.data.grades
      this.classNames = res.data.classNames
      this.classes = this.filteredClasses
    },
    async fetchNoticeBooklet() {
      const params = { isRemark: false }
      const res = await noticeBooklet({ params })
      this.list = res.data
      this.emitList()
    },
    async searchStudent(payload) {
      try {
        // console.log('搜索参数:', payload)
        showLoading('正在搜索中，请稍候...')
        const res = await noticeBooklet({ params:payload })
        if (res.code === 200) {
          this.list = res.data
          this.emitList()
          // console.log('搜索结果:', res.data)
          closeLoading()
        } else {
          this.$message.error('搜索失败，请重试')
        }
      } catch (error) {
        console.error('搜索失败:', error)
        this.$message.error('搜索失败：' + (error.message || '未知错误'))
      } finally {
        closeLoading()
      }
    },
    onGradeChange() {
      this.classId = null
      this.classes = this.filteredClasses
      this.emitList()
    },
    onClassChange() {
      this.emitList()
    },
    onSearch() {
      // 构建搜索参数
      const payload = {}

      // 添加年级和班级参数
      if (this.gradeId) {
        payload.gradeId = this.gradeId
      }
      if (this.classId) {
        payload.classId = this.classId
      }
      if (this.findKey && this.findKey.trim() !== '') {
        payload.findKey = this.findKey.trim()
      }

      // 如果有搜索条件，调用搜索方法；否则恢复原始数据
      if (Object.keys(payload).length > 0) {
        this.searchStudent(payload)
      } else {
        this.fetchNoticeBooklet()
      }
    },
    emitList() {
      this.$emit('update:list', this.filteredList, this.gradeId, this.classId)
    },
    onClearSearch() {
      this.findKey = ''
      this.fetchNoticeBooklet()
    }
  },
  created() {
    this.fetchCourse()
    this.fetchNoticeBooklet()
  }
}
</script>

<style scoped lang="scss">
.filter_container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  overflow: hidden;
  white-space: nowrap;
}
.left_filters {
  display: flex;
  flex-wrap: nowrap;
  margin-left: 20px;
  overflow: hidden;
  white-space: nowrap;
}
.search-item {
  width: 150px;
  margin-right: 12px;
}
.right_actions {
  margin-left: 20px;
}
</style>
