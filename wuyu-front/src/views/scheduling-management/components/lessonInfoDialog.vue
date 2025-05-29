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
        <!-- 年级 -->
        <el-col :span="12">
          <el-form-item label="年级" prop="grade">
            <el-select
              v-model="form.grade"
              placeholder="请选择年级"
              clearable
              class="w-full"
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

        <!-- 教师选择 -->
        <el-col :span="24">
          <el-form-item label="任课教师" prop="teacherId">
            <el-select
              v-model="form.teacherName"
              filterable
              clearable
              placeholder="请选择任课教师"
              class="w-full"
              @change="handleTeacherChange"
            >
              <el-option
                v-for="teacher in filteredTeachers"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"
              >
                <span style="float: left">{{ teacher.name }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">
                  {{ teacher.department }} - {{ teacher.title }}
                </span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed, watch } from 'vue'

const props = defineProps({
  visible: Boolean,
  formData: {
    type: Object,
    default: () => ({
      grade: null,
      classNum: null,
      className: '',
      course: '',
      teacherId: null,
      teacherName: ''
    })
  },
  teachers: {  // 接收教师列表数据
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['update:visible', 'submit'])

// 年级选项
const gradeOptions = Array.from({ length: 6 }, (_, i) => ({
  value: i + 1,
  label: `${i + 1}年级`
}))

// 班级序号选项
const classNumOptions = Array.from({ length: 10 }, (_, i) => i + 1)

const formRef = ref(null)
const dialogVisible = ref(false)
const searchQuery = ref('')

const form = reactive({ ...props.formData })

const rules = reactive({
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
  teacherId: [
    { required: true, message: '请选择任课教师', trigger: 'change' }
  ]
})

// 过滤教师列表
const filteredTeachers = computed(() => {
  return props.teachers.filter(teacher => {
    const query = searchQuery.value.toLowerCase()
    return teacher.name.toLowerCase().includes(query) ||
           teacher.pinyin.toLowerCase().includes(query) ||
           teacher.department.toLowerCase().includes(query)
  })
})

watch(() => props.visible, val => {
  dialogVisible.value = val
  if (val) {
    Object.assign(form, props.formData)
  }
})

const handleClose = () => {
  emit('update:visible', false)
  formRef.value?.resetFields()
}

// 教师选择变更处理
const handleTeacherChange = (teacherId) => {
  const selectedTeacher = props.teachers.find(t => t.id === teacherId)
  if (selectedTeacher) {
    form.teacherName = selectedTeacher.name
  }
}

const handleSubmit = () => {
  formRef.value.validate(valid => {
    if (valid) {
      emit('submit', { ...form })
      dialogVisible.value = false
    }
  })
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

.el-select-dropdown__item {
  display: flex;
  justify-content: space-between;
  padding: 0 20px;

  span:first-child {
    flex: 1;
    margin-right: 20px;
  }

  span:last-child {
    color: #909399;
    font-size: 12px;
  }
}
</style>
