<template>
  <el-dialog
    title="确定学年学期"
    :visible="visible"
    @close="handleClose"
    width="800px"
    :close-on-click-modal="false"
  >
    <el-form :model="form" label-width="100px">
      <el-form-item label="学期初时间">
        <el-date-picker
          v-model="form.startDate"
          type="date"
          placeholder="选择学期初时间"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          :editable="false"
        />
      </el-form-item>
      <el-form-item label="学年">
        <el-input v-model="form.academicYear" placeholder="请输入学年，如：2023-2024" />
      </el-form-item>
      <el-form-item label="学期">
        <el-select v-model="form.semester" placeholder="请选择学期">
          <el-option label="第一学期" value="1" />
          <el-option label="第二学期" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否覆盖">
        <el-switch
          v-model="form.isOverwrite"
          active-text="覆盖"
          inactive-text="不覆盖"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleFetchData" :loading="loading">获取上学期数据</el-button>
      </el-form-item>
    </el-form>


    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleConfirm">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue';
import { Message } from 'element-ui';
import { copyLastSemesterSchedule } from '@/api/schedulModule/index';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['update:visible', 'confirm', 'cancel']);

const form = reactive({
  startDate: '',
  academicYear: '',
  semester: '',
  isOverwrite: false
});

const lastSemesterData = ref([]);
const loading = ref(false);

// 设置默认学年和学期
const setDefaultAcademicYearAndSemester = () => {
  const now = new Date();
  const currentYear = now.getFullYear();
  const currentMonth = now.getMonth() + 1; // getMonth() 返回 0-11

  // 设置学年
  if (currentMonth >= 2 && currentMonth <= 8) {
    // 2-8月，使用当前年
    form.academicYear = `${currentYear - 1}-${currentYear}`;
    form.semester = '2'; // 第二学期
  } else {
    // 9-1月，使用当前年
    form.academicYear = `${currentYear}-${currentYear + 1}`;
    form.semester = '1'; // 第一学期
  }
};

// 监听弹窗显示状态
watch(() => props.visible, (newVal) => {
  if (newVal) {
    setDefaultAcademicYearAndSemester();
  }
});

// 获取上学期数据
const fetchLastSemesterData = async () => {
  if (!form.academicYear || !form.semester) {
    Message.warning('请先填写学年和学期信息');
    return;
  }

  try {
    loading.value = true;
    const params = {
      academicYear: form.academicYear,
      semester: form.semester,
      isOverwrite: form.isOverwrite
    };
    console.log('请求参数：', params);
    const res = await copyLastSemesterSchedule({
      academicYear: form.academicYear,
      semester: form.semester,
      isOverwrite: form.isOverwrite
    });
    
    if (res.code === 200) {
      // 检查返回数据的结构
      if (res.data && Array.isArray(res.data)) {
        lastSemesterData.value = res.data;
      } else if (res.data && Array.isArray(res.data.records)) {
        lastSemesterData.value = res.data.records;
      } else {
        lastSemesterData.value = [];
      }
      Message.success('获取数据成功');
      console.log("获取上学期数据: res.data",res.data)
    } else {
      Message.error(res.message || '获取上学期数据失败');
      lastSemesterData.value = [];
    }
  } catch (error) {
    console.error('获取上学期数据失败:', error);
    Message.error('获取上学期数据失败');
    lastSemesterData.value = [];
  } finally {
    loading.value = false;
  }
};

// 处理获取数据按钮点击
const handleFetchData = () => {
  fetchLastSemesterData();
};

// 处理关闭
const handleClose = () => {
  emit('cancel');
  emit('update:visible', false);
  resetForm();
};

// 重置表单
const resetForm = () => {
  form.startDate = '';
  form.academicYear = '';
  form.semester = '';
  form.isOverwrite = false;
  lastSemesterData.value = [];
};

// 处理取消
const handleCancel = () => {
  emit('cancel',{
    isOverwrite: false
  });
  emit('update:visible', false);
  resetForm();
};

// 处理确认
const handleConfirm = () => {
  if (!form.startDate) {
    Message.warning('请选择学期初时间');
    return;
  }
  if (!form.academicYear || !form.semester) {
    Message.warning('请填写学年和学期信息');
    return;
  }
  emit('confirm', {
    startDate: form.startDate,
    academicYear: form.academicYear,
    semester: form.semester,
    isOverwrite: true
  });
  emit('update:visible', false);
  resetForm();
};
</script>

<style lang="scss" scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.last-semester-section {
  margin-top: 20px;
  
  h3 {
    margin-bottom: 15px;
    font-size: 16px;
    color: #303133;
  }
}
</style> 