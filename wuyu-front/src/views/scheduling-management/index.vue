<template>
  <div class="course-scheduling-container">
    <!-- 顶部工具栏 -->
    <div class="toolbar-section">
      <div class="filter-group">
        <el-select
          v-model="filter.grade"
          placeholder="年级"
          clearable
          class="filter-item"
          @change="handleGradeChange"
        >
          <el-option
            v-for="grade in gradeOptions"
            :key="grade.value"
            :label="grade.label"
            :value="grade.value"
          />
        </el-select>

        <el-select
          v-model="filter.classNum"
          placeholder="班级"
          clearable
          class="filter-item"
          @change="handleFilterChange"
        >
          <el-option
            v-for="cls in classOptions"
            :key="cls"
            :label="`${cls}班`"
            :value="cls"
          />
        </el-select>

        <el-input
          v-model="filter.course"
          placeholder="课程名称"
          clearable
          class="filter-item"
          @change="handleFilterChange"
        />
      </div>

      <div class="operation-group">
        <el-button type="primary" icon="el-icon-plus" @click="dialogVisible=true">新增</el-button>
        <el-button type="danger" icon="el-icon-delete" :disabled="deleDisabled" @click="handleDeleteLesson(ids)">删除</el-button>
        <el-dropdown trigger="click" class="more-actions">
          <el-button type="info">
            更多操作<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="downloadTemplate">
                <i class="el-icon-download"></i>下载模板
              </el-dropdown-item>
              <el-dropdown-item @click="handleExport">
                <i class="el-icon-upload2"></i>导出数据
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div class="main-content">
      <!-- 左侧课程树 -->
      <el-card class="course-tree" shadow="never">
        <template #header>
          <div class="card-header">
            <span>课程排课列表</span>
            <div class="card-actions">
              <el-switch
                v-model="autoCopyEnabled"
                active-text="自动复制"
                active-color="#409EFF"
                @change="handleAutoCopySwitch"
              />
              <el-popconfirm
                title="确认复制上学期排课？"
                confirm-button-text="确认"
                cancel-button-text="取消"
                @confirm="handleAutoCopy"
              >
                <template #reference>
                  <el-button type="text" icon="el-icon-copy-document" circle>上学期排课</el-button>
                </template>
              </el-popconfirm>
            </div>
          </div>
        </template>

        <el-tree
          :data="filteredCourseTree"
          :props="treeProps"
          node-key="id"
          class="custom-tree"
          accordion
        >
          <template #default="{ node, data }">
            <div class="tree-node">
              <div class="node-info">
                <span class="node-label">{{ node.label }}</span>
                <!-- <span v-if="data.type === 'course'" class="course-meta">
                  <el-tag size="mini">{{ data.period }}课时</el-tag>
                  <el-tag size="mini" type="success">{{ data.credit }}学分</el-tag>
                </span> -->
              </div>
              <div v-if="data.type === 'course'" class="node-actions">
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-user"
                  @click.stop="openTeacherDialog(data)"
                >
                  {{ data.teacher || '分配教师' }}
                </el-button>
                <el-dropdown trigger="click" @command="handleTreeCommand">
                  <el-button type="text" icon="el-icon-more" circle size="small"></el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item command="edit">编辑课程</el-dropdown-item>
                      <el-dropdown-item command="delete">删除课程</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </div>
          </template>
        </el-tree>
      </el-card>

      <!-- 右侧表格 -->
      <el-card class="summary-table" shadow="never">
        <template #header>
          <div class="card-header">
            <span>课程安排总览</span>
            <el-upload
              action="#"
              :on-change="handleUpload"
              :show-file-list="false"
              accept=".xlsx,.xls"
            >
              <el-button type="primary" icon="el-icon-upload" size="small">导入课表</el-button>
            </el-upload>
          </div>
        </template>

        <el-table
          :data="tableData"
          v-loading="tableLoading"
          stripe
          highlight-current-row
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="30"></el-table-column>
          <el-table-column prop="grade" label="年级" width="120" align="center" />
          <el-table-column prop="classNum" label="班级" width="150" align="center" />
          <el-table-column prop="course" label="课程名称" min-width="250" />
          <el-table-column label="任课教师" width="200">
            <template #default="{ row }">
              <div class="teacher-cell">
                <div class="teacher-info">
                  <span class="teacher-name">{{ row.teacherName || '未分配' }}</span>
                  <el-button
                    type="text"
                    size="mini"
                    icon="el-icon-edit"
                    @click="openTeacherDialog(row)"
                  ></el-button>
                </div>
              </div>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="period" label="课时" width="100" align="center" /> -->
          <el-table-column prop="id" label="操作" width="300" align="center">
            <template #default="{ row }">
              <el-button
                type="primary"
                size="small"
                @click="handleUpdateLesson(row)"
                round
              >
                编辑课程
              </el-button>
              <el-button
                type="danger"
                size="small"
                @click="handleDeleteLesson(row.id)"
                round
              >
                删除课程
              </el-button>
            </template>
          </el-table-column>
        </el-table>

         <div class="pagination-wrapper">
          <el-pagination
            v-model:current-page="pagination.page"
            v-model:page-size="pagination.size"
            :total="pagination.total"
            :page-sizes="[10, 20, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper"
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-card>
    </div>

    <!-- 教师检索弹窗 -->
    <teacher-sel
      :visible.sync="teacherSelVisible"
      @select="handleTeacherSelect"
    />
    <!-- 导入结果提示 -->
    <el-alert
      v-if="importResult"
      :title="importResult.message"
      :type="importResult.type"
      :closable="false"
      show-icon
      class="import-result" />

    <lesson-info-dialog
    :visible="dialogVisible"
    :form-data="formData"
    :teachers="teacherList"
    @submit="handleSubmit"
    @update:visible="dialogVisible = $event"
  />
  </div>

</template>

<script setup>
import { ref, reactive, computed, watch,onMounted } from 'vue';
import { Message,Loading,MessageBox} from 'element-ui';
import {getLessonPageAPI,
        deleteLessonAPI,
        getTeacherListAPI
        }
        from '@/api/schedulModule/index'
import lessonInfoDialog from './components/lessonInfoDialog.vue'
import TeacherSel from '@/components/TeacherSel'
import pinyin from 'pinyin';
components: {
  TeacherSel
}

const dialogVisible = ref(false)
const formData = ref({})
const teacherList = ref([
  {
    id: 1,
    name: '张老师',
    pinyin: 'zhang',
    department: '数学系',
    title: '教授'
  },

])

const handleSubmit = (form) => {
  console.log('提交数据：', form)
}

import { ref, reactive, computed, watch, onMounted } from 'vue';
import { Message, Loading } from 'element-ui';
import { getLessonPageAPI, getTeacherListAPI } from '@/api/schedulModule/index'
import TeacherSel from '@/components/TeacherSel'
import pinyin from 'pinyin';
components: {
  TeacherSel
}
// 年级/班级选项
const gradeOptions = Array.from({ length: 6 }, (_, i) => ({
  value: i + 1,
  label: `${i + 1}年级`
}))
const classOptions = Array.from({ length: 10 }, (_, i) => i + 1)

const filter = reactive({
  grade: null,
  classNum: null,
  course: ''
})
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})
const tableData = ref([])
const tableLoading = ref(false)


// 课程树数据
const courseTree = ref([]);
const transformToTree = (records) => {
  const treeMap = new Map()

  records.forEach(item => {
    const gradeKey = `grade-${item.grade}`;
    const classKey = `class-${item.grade}-${item.classNum}`

    // 年级节点
    if (!treeMap.has(gradeKey)) {
      treeMap.set(gradeKey, {
        id: gradeKey,
        type: 'grade',
        label: `${item.grade}年级`,
        gradeValue: item.grade,
        children: []
      });
    }

    // 班级节点
    const gradeNode = treeMap.get(gradeKey);
    const classNode = gradeNode.children.find(c => c.classNum === item.classNum)
    if (!classNode) {
      gradeNode.children.push({
        id: classKey,
        type: 'class',
        label: item.classNum+'班',
        classNum: item.classNum,
        children: []
      })
    }

    // 课程节点
    const targetClass = gradeNode.children.find(c => c.classNum === item.classNum);
    targetClass.children.push({
      id: item.id,
      type: 'course',
      label: item.course,
      teacher: item.teacherName,
      teacherId: item.teacherId,
      grade: item.grade,
      classNum: item.classNum
    })
  })

  return Array.from(treeMap.values())
}

const autoCopyEnabled = ref(true);
const teacherDialogVisible = ref(false)
const currentCourse = ref(null);
const importResult = ref(null);

const filteredCourseTree = computed(() => {
  if (!filter.grade && !filter.class && !filter.courseName) return courseTree.value

  return courseTree.value.map(gradeNode => {
    if (filter.grade && gradeNode.label !== filter.grade) return null
    return {
      ...gradeNode,
      children: gradeNode.children.map(classNode => {
        if (filter.class && classNode.label !== filter.class) return null
        return {
          ...classNode,
          children: classNode.children.filter(course =>
            course.label.includes(filter.courseName)
          )
        }
      }).filter(Boolean)
    };
  }).filter(Boolean)
});

const treeProps = ref({
  children: 'children',
  label: 'label'
})


const selectTeacher = (teacher) => {
  if (!currentCourse.value) return

  const updateNode = (nodes) => {
    nodes.forEach(node => {
      if (node.id === currentCourse.value.id && node.type === 'course') {
        node.teacher = teacher.teacherName
        node.teacherId = teacher.id
      }
      if (node.children) updateNode(node.children)
    })
  }
  updateNode(courseTree.value)

  teacherSelVisible.value = false
  Message.success(`已为课程${currentCourse.value.label}设置教师：${teacher.teacherName}`)
}

// 勾选的条数变化
const deleDisabled = ref(true)
const ids = ref([])
const handleSelectionChange = (e) => {
  ids.value = e.map(item => item.id);
  if(ids.value.length) {
    deleDisabled.value = false
  } else {
    deleDisabled.value = true
  }
  console.log('当前选中的ID列表：', ids.value);
  console.log('当前变化的事件对象：', e);
}
// 课程信息的增删改
const handleAddCourse = () => {

}

const handleDeleteLesson = (aaids) => {
  console.log('传入的id如下：',aaids);
    MessageBox.confirm(
    `确定要删除这${aaids.length}条数据吗?`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
  .then(() => {
    console.log('调用删除API，ID:', aaids)
    const res = deleteLessonAPI(aaids)
    if(res.code === 200 ){
      Message({
        type: 'success',
        message: '删除成功!'
      })
      fetchData()
      fetchAllCourses()
    }
  })
  .catch(() => {
    Message({
      type: 'info',
      message: '已取消删除'
    })
  })
}

const handleUpdateLesson = (row) => {
  console.log('传进来的row值为：',row);

  formData.value = row
  dialogVisible.value = true
}

// 自动fuzhi
// 自动复制排课
const handleAutoCopy = () => {
  Loading.service({ text: '复制中...' })
  setTimeout(() => {
    Message.success('上学期排课已复制到本学期')
    Loading.service().close()
  }, 1000);
}

const handleUpload = (file) => {
  // 模拟文件上传
  Loading.service({ text: '导入中...' })
  setTimeout(() => {
    Loading.service().close()
    importResult.value = {
      type: 'success',
      Message: '成功导入15条记录，失败2条'
    };
    console.log('导入错误：第3行班级不存在；第7行教师未注册');
  }, 1500)
};

const handleExport = () => {
  Loading.service({ text: '导出中...' })
  setTimeout(() => {
    Loading.service().close();
    Message.success('数据已成功导出为Excel')
  }, 1000);
};

watch(teacherSelVisible, (val) => {
  if (!val) currentCourse.value = null
})

// 获取表格数据（分页）
const fetchData = async () => {
  try {
    tableLoading.value = true
    const params = {
      page: pagination.page,
      size: pagination.size,
      minGrade: filter.grade || null,
      maxGrade: filter.grade || null,
      classNum: filter.classNum ? Number(filter.classNum) : null,
      course: filter.course || null
    }

    const res = await getLessonPageAPI(params)
    if (res.code === 200) {
      tableData.value = res.data.records
      pagination.total = Number(res.data.total)
    }
  } catch (error) {
    Message.error('数据加载失败')
  } finally {
    tableLoading.value = false
  }
};

// 获取全部课程数据
const fetchAllCourses = async () => {
  try {
    const res = await getLessonPageAPI({
      page: 1,
      size: 10000,
      minGrade: filter.grade || null,
      maxGrade: filter.grade || null,
      classNum: filter.classNum ? Number(filter.classNum) : null,
      course: filter.course || null
    })

    if (res.code === 200) {
      courseTree.value = transformToTree(res.data.records)
    }
  } catch (error) {
    Message.error('课程树数据加载失败')
  }
};

const handleGradeChange = () => {
  filter.classNum = null
  handleFilterChange()
}

// 过滤条件变化处理
const handleFilterChange = () => {
  pagination.page = 1
  fetchData()
  fetchAllCourses()
}
const handleSizeChange = (newSize) => {
  pagination.size = newSize
  fetchData()
}
const handleCurrentChange = (newPage) => {
  pagination.page = newPage
  fetchData()
}

// 教师检索相关
const teacherSelVisible = ref(false);

// 打开教师选择弹窗
const openTeacherDialog = (course) => {
  currentCourse.value = course;
  teacherSelVisible.value = true;
};

// 处理教师选择
const handleTeacherSelect = (teacher) => {
  if (!currentCourse.value) return;

  // 更新课程树中的教师信息
  const updateNode = (nodes) => {
    nodes.forEach(node => {
      if (node.id === currentCourse.value.id && node.type === 'course') {
        node.teacher = teacher.teacherName;
        node.teacherId = teacher.id;
      }
      if (node.children) updateNode(node.children);
    });
  };
  updateNode(courseTree.value);

  // 更新表格数据中的教师信息
  const updateTableData = () => {
    const index = tableData.value.findIndex(
      item => item.id === currentCourse.value.id
    );
    if (index !== -1) {
      tableData.value[index].teacherName = teacher.teacherName;
      tableData.value[index].teacherId = teacher.id;
    }
  };
  updateTableData();

  teacherSelVisible.value = false;
  Message.success(`已为课程${currentCourse.value.label}设置教师：${teacher.teacherName}`);
};

// 监听弹窗关闭，重置当前课程
watch(teacherSelVisible, (val) => {
  if (!val) {
    currentCourse.value = null;
  }
});

onMounted(() => {
  fetchData()
  fetchAllCourses()
  fetchTeachers()
})
</script>

<style lang="scss" scoped>
.course-scheduling-container {
  padding: 24px;
  background: #f5f7fa;
  min-height: 100vh;
}

.toolbar-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 16px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);

  .filter-group {
    display: flex;
    gap: 12px;

    .filter-item {
      width: 160px;
    }
  }

  .operation-group {
    display: flex;
    gap: 12px;
  }
}

.main-content {
  display: grid;
  grid-template-columns: 320px 1fr;
  gap: 20px;
}

.course-tree {
  :deep(.el-card__header) {
    padding: 12px 20px;
    background: #f8fafc;
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 14px;
    color: #303133;
  }

  .custom-tree {
    :deep(.el-tree-node__content) {
      height: 40px;
    }
  }
}

.tree-node {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding-right: 8px;

  .node-info {
    display: flex;
    flex-direction: column;

    .node-label {
      font-size: 14px;
      color: #303133;
    }

    .course-meta {
      margin-top: 4px;

      .el-tag {
        margin-right: 6px;
        height: 22px;
        line-height: 20px;
      }
    }
  }

  .node-actions {
    display: flex;
    align-items: center;
    gap: 8px;
  }
}

.summary-table {
  :deep(.el-card__header) {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 20px;
    background: #f8fafc;
  }

  .teacher-cell {
    display: flex;
    align-items: center;
    gap: 12px;

    .teacher-info {
      display: flex;
      align-items: center;
      gap: 8px;

      .teacher-name {
        max-width: 120px;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.mb-4 {
  margin-bottom: 16px;
}

.mt-4 {
  margin-top: 16px;
}
.card-actions{
  margin-left: 20px;
  /* src,utils-reques,vue.config.js */
}
</style>
