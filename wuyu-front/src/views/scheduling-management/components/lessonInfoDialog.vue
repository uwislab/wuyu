<template>
  <el-dialog
    :title="form.id ? '修改课程信息' : '新增课程信息'"
    :visible.sync="dialogVisible"
    width="600px"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="100px"
      label-position="right"
    >
      <el-row :gutter="20">
        <!-- 学年 -->
        <el-col :span="12">
          <el-form-item label="学年" prop="academicYear">
            <el-select
              v-model="form.academicYear"
              placeholder="请选择学年"
              clearable
              class="w-full"
              @change="updateClassName"
            >
              <el-option
                v-for="year in academicYears"
                :key="year.value"
                :label="year.label"
                :value="year.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!-- 学期 -->
        <el-col :span="12">
          <el-form-item label="学期" prop="semester">
            <el-select
              v-model="form.semester"
              placeholder="请选择学期"
              clearable
              class="w-full"
            >
              <el-option
                v-for="seme in semesterOptions"
                :key="seme.value"
                :label="seme.label"
                :value="seme.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!-- 年级 -->
        <el-col :span="12">
          <el-form-item label="年级" prop="grade">
            <el-select
              v-model="form.grade"
              placeholder="请选择年级"
              clearable
              class="w-full"
              @change="updateClassName"
            >
              <el-option
                v-for="grade in gradeOptions"
                :key="grade.value"
                :label="grade.label"
                :value="grade.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <!-- 班级序号 -->
        <el-col :span="12">
          <el-form-item label="班级序号" prop="classNum">
            <el-select
              v-model="form.classNum"
              placeholder="请选择班级序号"
              clearable
              class="w-full"
              @change="updateClassName"
            >
              <el-option
                v-for="num in classNumOptions"
                :key="num"
                :label="num"
                :value="num"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <!-- 班级名称 -->
        <el-col :span="24">
          <el-form-item label="班级名称" prop="className">
            <el-input
              v-model="form.className"
              placeholder="请输入班级名称"
              clearable
            />
          </el-form-item>
        </el-col>

        <!-- 课程名称 -->
        <el-col :span="24">
          <el-form-item label="课程名称" prop="course">
            <el-input
              v-model="form.course"
              placeholder="请输入课程名称"
              clearable
            />
          </el-form-item>
        </el-col>

        <!-- 教师输入框 -->
        <el-col :span="24">
          <el-form-item label="任课教师" prop="teacherName">
            <el-input
              v-model="form.teacherName"
              placeholder="请点击选择任课教师"
              clearable
              readonly
              @click.native="openTeacherDialog"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false" :loading="loading">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="loading">确 定</el-button>
      </span>
    </template>

    <!-- 教师选择弹窗 -->
    <teacher-sel
      :visible.sync="teacherSelVisible"
      @select="handleTeacherSelect"
    />
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { Message } from 'element-ui'
import { addLessonAPI, updateLessonAPI } from '@/api/schedulModule/index'
import TeacherSel from './TeacherSel.vue'

const props = defineProps({
  visible: Boolean,
  formData: {
    type: Object,
    default: () => ({})
  },
  academicYears:{
    type:Array,
    default: () => []
  }
})

const semesterOptions = [
  { label: '上学期', value: 1 },
  { label: '下学期', value: 2 }
]


const emit = defineEmits(['update:visible', 'submit', 'success','refresh-data'])

// 年级选项
const gradeOptions = Array.from({ length: 6 }, (_, i) => ({
  value: i + 1,
  label: `${i + 1}年级`
}))

// 班级序号选项
const classNumOptions = Array.from({ length: 10 }, (_, i) => i + 1)

const formRef = ref(null)
const dialogVisible = ref(false)
const loading = ref(false) // 添加加载状态
const teacherSelVisible = ref(false)

const form = ref({ ...props.formData })

const rules = reactive({
  academicYear: [
    { required: true, message: '请选择学年', trigger: 'change' }
  ],
  semester: [
    { required: true, message: '请选择学期', trigger: 'change' }
  ],
  grade: [
    { required: true, message: '请选择年级', trigger: 'change' }
  ],
  classNum: [
    { required: true, message: '请选择班级序号', trigger: 'change' }
  ],
  className: [
    { required: true, message: '请输入班级名称', trigger: 'blur' },
    { max: 20, message: '长度不能超过20个字符', trigger: 'blur' }
  ],
  course: [
    { required: true, message: '请输入课程名称', trigger: 'blur' },
    { max: 50, message: '长度不能超过50个字符', trigger: 'blur' }
  ],
  teacherName: [
    { required: false, message: '请输入任课教师姓名', trigger: 'blur' },
    { min: 2, message: '教师姓名至少需要2个字符', trigger: 'blur' }
  ]
})

// 监听props.visible变化
watch(() => props.visible, val => {
  dialogVisible.value = val
  if (val) {
    // 打开对话框时重置表单
    formRef.value?.resetFields()
    // Object.assign(form, props.formData)
  }
})

 watch(() => props.formData, (newVal) => {
      form.value = { ...newVal };
    }, { deep: true });

// 关闭对话框处理
const handleClose = () => {
  emit('update:visible', false)
  formRef.value?.resetFields()
}

const updateClassName = () => {
  if (form.value.grade && form.value.classNum && !props.formData.id) {
    form.value.className = `${form.value.grade}年级${form.value.classNum}班`
  }
}

// 处理表单提交
const handleSubmit = async () => {
  formRef.value.validate(async valid => {
    if (!valid) return

    loading.value = true

    try {
      const lessonData = {
        ...form.value,
      }

      let result
      if (form.value.id) {
        // 更新课程信息
        result = await updateLessonAPI(lessonData)

        if(result.code === 200) {
          Message.success('课程信息更新成功')
        } else {
          Message.error('出错了，稍后重试~')
        }
      } else {
        // 添加新课程
        result = await addLessonAPI(lessonData)

        if(result.code === 200) {
          Message.success('新课程添加成功')
        } else {
          Message.error('出错了，稍后重试~')
        }
      }

      emit('success', result) // 通知父组件操作成功
      emit('update:visible', false)
      emit('refresh-data')
      formRef.value.resetFields() // 重置表单
    } catch (error) {
      Message.error(result.message)
    } finally {
      loading.value = false
    }
  })
}

// 打开教师选择弹窗
const openTeacherDialog = () => {
  teacherSelVisible.value = true
}

// 处理教师选择
const handleTeacherSelect = (teacher) => {
  form.value.teacherName = teacher.teacherName
  form.value.teacherId = teacher.id
  teacherSelVisible.value = false
}
</script>

<style lang="scss" scoped>
.el-select {
  width: 100%;
}

.el-form-item {
  margin-bottom: 18px;

  :deep(.el-form-item__label) {
    font-weight: 500;
    padding-right: 20px;
  }
}

.dialog-footer {
  .el-button + .el-button {
    margin-left: 12px;
  }
}

:deep(.el-button.is-loading) {
  cursor: not-allowed;
}
</style>
