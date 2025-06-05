<template>
  <el-dialog
    title="设置学期初时间"
    :visible="visible"
    @close="handleClose"
    width="400px"
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
import { ref, reactive } from 'vue';
import { Message } from 'element-ui';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['update:visible', 'confirm']);

const form = reactive({
  startDate: ''
});

// 处理关闭
const handleClose = () => {
  emit('update:visible', false);
  form.startDate = '';
};

// 处理取消
const handleCancel = () => {
  emit('update:visible', false);
  form.startDate = '';
};

// 处理确认
const handleConfirm = () => {
  if (!form.startDate) {
    Message.warning('请选择学期初时间');
    return;
  }
  emit('confirm', form.startDate);
  emit('update:visible', false);
  form.startDate = '';
};
</script>

<style lang="scss" scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
</style> 