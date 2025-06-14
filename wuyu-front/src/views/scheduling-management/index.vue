<template>
  <div class="course-scheduling-container">
    <!-- 顶部工具栏 -->
    <div class="toolbar-section">
      <div class="filter-group">
        <!-- 学年选择 -->
        <el-select
          v-model="filter.academicYear"
          placeholder="选择学年"
          clearable
          class="filter-item"
          @change="handleFilterChange"
        >
          <el-option
            v-for="year in academicYearOptions"
            :key="year.value"
            :label="year.label"
            :value="year.value"
          />
        </el-select>

        <!-- 学期选择 -->
        <el-select
          v-model="filter.semester"
          placeholder="选择学期"
          clearable
          class="filter-item"
          @change="handleFilterChange"
        >
          <el-option
            v-for="sem in semesterOptions"
            :key="sem.value"
            :label="sem.label"
            :value="sem.value"
          />
        </el-select>

        <!-- 年级选择 -->
        <el-select
          v-model="filter.grade"
          placeholder="选择年级"
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

        <!-- 班级选择 -->
        <el-select
          v-model="filter.classNum"
          placeholder="选择班级"
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

        <!-- 课程名称搜索 -->
        <el-input
          v-model="filter.course"
          placeholder="搜索课程名称"
          clearable
          class="filter-item"
          @change="handleFilterChange"
          prefix-icon="el-icon-search"
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
          <el-dropdown-item>
            <el-button @click="downloadTemplate"><i class="el-icon-download"></i>下载模板</el-button>
          </el-dropdown-item>
          <el-dropdown-item @click="handleExport">
            <el-button @click="handleExport"><i class="el-icon-upload2"></i>导出数据</el-button>
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
                :title="`是否确认复制上学期的排课`"
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
                <el-dropdown trigger="click" @command="handleTreeCommand(node.data,$event)">
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
              <div>
                <el-button type="primary" icon="el-icon-upload" size="small" @click="handleUpload">导入课表</el-button>
              </div>
              <Import :import-dialog-visible="importDialogVisible"
              @update:importDialogVisible="importDialogVisible = $event"
              :fetchData="fetchData"
              :fetchAllCourses="fetchAllCourses"></Import>

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
          <el-table-column prop="academicYear" label="学年" width="120" align="center" />
          <el-table-column prop="semesterlabel" label="学期" width="120" align="center" />
          <el-table-column prop="className" label="班级" width="120" align="center" />
          <el-table-column prop="course" label="课程名称" min-width="120"  align="center"/>
          <el-table-column label="任课教师" width="150" align="left">
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


    <!-- 学期初时间设置弹窗 -->
    <semester-start-dialog
      :visible="semesterStartDialogVisible"
      @update:visible="semesterStartDialogVisible = $event"
      @confirm="handleSemesterStartConfirm"
      @cancel="handleSemesterStartCancel"
    />

    <!-- 导入结果提示 -->
    <el-alert
      v-if="importResult"
      :title="importResult.message"
      :type="importResult.type"
      :closable="false"
      show-icon
      class="import-result" />

    <!-- 课程新增弹窗 -->
    <lesson-info-dialog
      :visible="dialogVisible"
      :form-data="formData"
      :academicYears="academicYearOptions"
      @update:visible="dialogVisible = $event"
      @refresh-data="refreshData"
    />
  </div>

</template>

<script setup>
import { ref, reactive, computed, watch,onMounted } from 'vue';
import { Message,MessageBox} from 'element-ui';
import {getLessonPageAPI,
        deleteLessonAPI,
        updateLessonAPI,
        copyLastSemesterSchedule,
        downloadModel,
        exportExcel,
        getAcademicAPI,
        autoCopyLastSemesterSchedule
      }
        from '@/api/schedulModule/index'
import lessonInfoDialog from './components/lessonInfoDialog.vue'
import TeacherSel from './components/TeacherSel.vue'
import SemesterStartDialog from './components/SemesterStartDialog.vue'
import Import from './components/Import.vue'

const handleTreeCommand = ( data , e ) => {
  if(e==='edit'){
    const row = {
      ...data,
      course:data.label,
      className:`${data.grade}年级${data.classNum}班`,
      teacherName:data.teacher
    }
    handleUpdateLesson(row)
  } else if(e === 'delete') {
    handleDeleteLesson(data.id)
  }

}
const dialogVisible = ref(false)
const formData = ref({})
const semesterStartDialogVisible = ref(false)

const refreshData = async () => {
  formData.value = {}
  await fetchData()
  await fetchAllCourses()
}

// 年级/班级选项
const gradeOptions = Array.from({ length: 6 }, (_, i) => ({
  value: i + 1,
  label: `${i + 1}年级`
}))
const classOptions = Array.from({ length: 10 }, (_, i) => i + 1)

// 新增学年选项和当前学期标识
const academicYearOptions = ref([])
const semesterOptions = [
  { label: '上学期', value: 1 },
  { label: '下学期', value: 2 }
]

// 获取学年选项数据
const fetchAcademicYearsA = () => {
    getAcademicAPI().then((res)=>{
      if (res.code === 200) {
        academicYearOptions.value = res.data.map(year => ({
          label: year,
          value: year
        }))
      }
    }).catch (error => {
      Message.error('学年数据加载失败')
    })
}
const filter = reactive({
  grade: null,
  classNum: null,
  course: '',
  academicYear: null,    // 学年筛选
  semester: null,        // 学期筛选
  onlyCurrent: false     // 只查看当前学期
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
      })
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
      classNum: item.classNum,
      academicYear:item.academicYear,
      semester:item.semester
    })
  })

  return Array.from(treeMap.values())
}

const currentCourse = ref(null);

// 课程树过滤
const filteredCourseTree = computed(() => {
  if (!filter.grade && !filter.classNum && !filter.course) {
    return courseTree.value
  }

  return courseTree.value
    .filter(gradeNode => {
      return !filter.grade || gradeNode.gradeValue === filter.grade
    })
    .map(gradeNode => ({
      ...gradeNode,
      children: gradeNode.children
        .filter(classNode => {
          return !filter.classNum || classNode.classNum === filter.classNum
        })
        .map(classNode => ({
          ...classNode,
          children: classNode.children.filter(course => {
            return !filter.course || course.label.includes(filter.course)
          })
        }))
        .filter(classNode => classNode.children.length > 0)
    }))
    .filter(gradeNode => gradeNode.children.length > 0)
})

const treeProps = ref({
  children: 'children',
  label: 'label'
})

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
}
// 课程信息的增删改
const handleAddCourse = () => {
  dialogVisible.value = true
}

const handleDeleteLesson = (aaids) => {
  const normalizedAaids = Array.isArray(aaids) ? aaids : [aaids];

  MessageBox.confirm(
    `确定要删除这${normalizedAaids.length}条数据吗?`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
  .then(async () => {
    const intAaids = normalizedAaids.map(id => {
      const num = Number(id);
      if (isNaN(num)) throw new Error(`无效的ID值：${id}（必须为数字）`)
      return num;
    });

    const res = await deleteLessonAPI(intAaids);  // 始终传递数组

    if (res.code === 200) {
      Message({
        type: 'success',
        message: '删除成功!'
      });
      fetchData();
      fetchAllCourses();
    }
  })
  .catch((err) => {
    const errorMsg = err?.message || '已取消删除';
    Message({
      type: errorMsg.includes('无效') ? 'error' : 'info',
      message: errorMsg
    })
  })
}

const handleUpdateLesson = (row) => {
  formData.value = row
  dialogVisible.value = true
}

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
      course: filter.course || null,
       academicYear: filter.academicYear || null,     // 学年
      semester: filter.semester !== null ? filter.semester : null,  // 学期
      isCurrent: filter.onlyCurrent || null         // 是否当前学期
    }

    const res = await getLessonPageAPI(params)
    if (res.code === 200) {
      tableData.value = res.data.records.map(item => ({
        ...item,
        semesterlabel: item.semester === 1 ? '上学期' : item.semester === 2 ? '下学期' : '未知'
      }))
      pagination.total = Number(res.data.total)
    }
  } catch (error) {
    Message.error('数据加载失败')
  } finally {
    tableLoading.value = false
    fetchAcademicYearsA()
  }
};

// 获取全部课程数据
const fetchAllCourses = async () => {
  try {
    const params = {
      page: 1,
      size: 10000,
      minGrade: filter.grade || null,
      maxGrade: filter.grade || null,
      classNum: filter.classNum ? Number(filter.classNum) : null,
      course: filter.course || null,
      academicYear: filter.academicYear || null,
      semester: filter.semester !== null ? filter.semester : null,
      isCurrent: filter.onlyCurrent || null
    }

    const res = await getLessonPageAPI(params)
    if (res.code === 200) {
      courseTree.value = transformToTree(res.data.records)
    }
  } catch (error) {
    Message.error('课程树数据加载失败')
  }
}

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


// 导入课表信息
  // 控制导入组件是否显示
const importDialogVisible = ref(false)
const handleUpload = () => {
  importDialogVisible.value = true
};

// 下载模板
const downloadTemplate = async () => {
  try{
    const res = await downloadModel()
    const url = window.URL.createObjectURL(new Blob([res.data],{ type:"application/vnd.ms-excel;charset=utf-8"}))
    const link = document.createElement('a')
    document.body.appendChild(link);
    link.href = url
    link.setAttribute('download','排课模板.xls')
    link.click()
    // 清除
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
    Message.success({
      message: '导出成功！',
      duration: 3000,
      showClose: true
    })

}catch (error) {
  Message.error({
      message: '模板下载失败，请重试'+ (error.message || '未知错误'),
      duration: 3000,
      showClose: true
    });
}
}

//导出数据
const handleExport = async () =>{
  try{
    const res = await exportExcel({
      page: pagination.page,
      size: pagination.size,
      minGrade: filter.minGrade,
      maxGrade: filter.maxGrade,
      classNum: filter.classNum,
      course: filter.course,
      academicYear: filter.academicYear,
      semester: filter.semester,
    })
    if(res.data.size === 52) {
      Message.error({
      message: '导出失败，请重试,'+ (res.message || '未知错误'),
      duration: 3000,
      showClose: true
    });
    }else{
      const url = window.URL.createObjectURL(new Blob([res.data],{type:"application/vnd.ms-excel;charset=utf-8"}))
      const link = document.createElement('a')
      document.body.appendChild(link);
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download','排课信息.xls')
      link.click()
      // 清除
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
      Message.success({
        message: '导出成功！',
        duration: 3000,
        showClose: true
      });
    }
  } catch (error){
    Message.error({
      message: '导出失败，请重试'+ (error.message || '未知错误'),
      duration: 3000,
      showClose: true
    });
  }
}

// 教师检索相关
const teacherSelVisible = ref(false);

// 打开教师选择弹窗
const openTeacherDialog = (course) => {
  currentCourse.value = course;
  teacherSelVisible.value = true;
};

// 处理教师选择
const handleTeacherSelect = async (teacher) => {
  if (!currentCourse.value) return;

  // 检查教师是否已在同一班级有其他课程
  const hasConflict = tableData.value.some(item =>
    item.teacherId === teacher.id &&
    item.grade === currentCourse.value.grade &&
    item.classNum === currentCourse.value.classNum &&
    item.id !== currentCourse.value.id
  );

  if (hasConflict) {
    Message.error(`${teacher.teacherName}老师已经在${currentCourse.value.grade}年级${currentCourse.value.classNum}班有其他课程，不能重复分配`);
    return;
  }

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

  // 检查必要参数
  if (!currentCourse.value?.id) {
    Message.error('课程ID不存在，无法更新教师信息');
    return;
  }

  const { grade, classNum, course, id ,label ,academicYear , semester } = currentCourse.value;
  try {
    const className = currentCourse.value.grade + '年级' + currentCourse.value.classNum + '班'
    const res = await updateLessonAPI({
      academicYear,
      semester,
      grade,
      classNum,
      className,
      course:course?course:label,
      teacherName: teacher.teacherName,
      teacherId: teacher.id,
      id
    });

    if (res.code === 200) {
      Message.success(`已为${grade}年级${classNum}班的${course?course:label}课程设置教师：${teacher.teacherName}`);
    } else {
      Message.error('设置教师失败');
    }
  } catch (error) {
    Message.error('设置教师失败，请稍后重试');
  } finally {
    teacherSelVisible.value = false;
  }
};

// 复制上学期排课
const handleAutoCopy = async () => {
  try {
    // 从 localStorage 获取当前学期和学年
    let currentSemester = localStorage.getItem('currentSemester')
    let currentYear = localStorage.getItem('currentYear')

    // 如果没有学期和学年信息，设置默认值
    if (!currentSemester || !currentYear) {
      const currentDate = new Date()
      const currentMonth = currentDate.getMonth() + 1 // 获取当前月份（0-11，需要+1）
      const year = currentDate.getFullYear()

      if (currentMonth >= 2 && currentMonth <= 8) {
        // 2-8月，使用当前年
        currentYear = `${year - 1}-${year}`
        currentSemester = '2' // 第二学期
      } else {
        // 9-1月，使用当前年
        currentYear = `${year}-${year + 1}`
        currentSemester = '1' // 第一学期
      }
    }

    const res = await copyLastSemesterSchedule({
      academicYear: currentYear,
      semester: currentSemester,
      isOverwrite: true
    })
    if (res.code === 200) {
      Message.success(`复制上学期${currentYear}学年${currentSemester}学期的排课成功`)
      // 重新获取数据
      await fetchData()
      await fetchAllCourses()
    }
  } catch (error) {
    Message.error('复制上学期排课失败')
  }
}


// 监听弹窗关闭，重置当前课程
watch(teacherSelVisible, (val) => {
  if (!val) {
    currentCourse.value = null;
  }
})

// 学期初时间设置相关
const autoCopyEnabled = ref(false)

// 处理自动复制开关变化
const handleAutoCopySwitch = (val) => {
  if (val) {
    // 手动打开开关时，打开弹窗
    semesterStartDialogVisible.value = true
  }
}

// 开关自动复制
const handleAutoCopyClass = (val) => {
  if (val) {
    autoCopyLastSemesterSchedule({enabled: true});
  }
}
// 处理学期初时间确认
const handleSemesterStartConfirm = (formData) => {
  semesterStartDialogVisible.value = false
  if(formData.isOverwrite){
    fetchData()
    fetchAllCourses()
    // 确认后保持开关打开
    autoCopyEnabled.value = true
    // 保存学期初时间到localStorage
    localStorage.setItem('semesterStartTime', JSON.stringify({
      startDate: formData.startDate,
      academicYear: formData.academicYear,
      semester: formData.semester,
      timestamp: new Date().getTime()
    }))
  }
  handleAutoCopyClass(autoCopyEnabled.value)
  Message.success('学期初时间设置成功')
}

// 处理学期初时间取消
const handleSemesterStartCancel = (formData) => {
  semesterStartDialogVisible.value = false
  if(!formData.isOverwrite){
    // 取消后关闭开关
    autoCopyEnabled.value = false
  }
}

onMounted(() => {
  fetchData()
  fetchAllCourses()
  handleAutoCopyClass()
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
  grid-template-columns: 380px 1fr;
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

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
</style>
