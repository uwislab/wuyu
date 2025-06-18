<!--
 * @Author: hezeliangfj
 * @Date: 2025-06-18 15:35:11
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-18 22:04:36
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\views\notice\components\editmodal.vue
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <el-dialog
    :title="`修改${editForm.studentName || ''}同学的信息`"
    :visible.sync="editDialogVisible"
    width="50%" :close-on-click-modal="false"
    :before-close="handleEditClose"
    class="edit-dialog">
    <el-form :model="editForm" label-width="100px">
      <el-form-item label="姓名">
        <el-input v-model="editForm.studentName" disabled style="width:200px"></el-input>
      </el-form-item>
      <el-form-item label="学号">
        <el-input v-model="editForm.studentId" disabled style="width:200px"></el-input>
      </el-form-item>
      <el-form-item label="年级">
        <el-input :value="editForm.studentGrade ? editForm.studentGrade + '年级' : ''" disabled style="width:200px"></el-input>
      </el-form-item>
      <el-form-item label="班级">
        <el-input :value="editForm.studentClassNumber ? editForm.studentClassNumber + '班' : ''" disabled style="width:200px"></el-input>
      </el-form-item>
      <el-form-item label="德育">
        <div class="wuyu-row">
          <span class="wuyu-sub-label">实际</span>
          <el-input v-model="editForm.sdeyu" size="small" class="wuyu-input"></el-input>
          <span class="wuyu-sub-label">期望</span>
          <el-input v-model="editForm.pdeyu" size="small" class="wuyu-input"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="智育">
        <div class="wuyu-row">
          <span class="wuyu-sub-label">实际</span>
          <el-input v-model="editForm.szhiyu" size="small" class="wuyu-input"></el-input>
          <span class="wuyu-sub-label">期望</span>
          <el-input v-model="editForm.pzhiyu" size="small" class="wuyu-input"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="体育">
        <div class="wuyu-row">
          <span class="wuyu-sub-label">实际</span>
          <el-input v-model="editForm.stiyu" size="small" class="wuyu-input"></el-input>
          <span class="wuyu-sub-label">期望</span>
          <el-input v-model="editForm.ptiyu" size="small" class="wuyu-input"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="美育">
        <div class="wuyu-row">
          <span class="wuyu-sub-label">实际</span>
          <el-input v-model="editForm.smeiyu" size="small" class="wuyu-input"></el-input>
          <span class="wuyu-sub-label">期望</span>
          <el-input v-model="editForm.pmeiyu" size="small" class="wuyu-input"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="劳育">
        <div class="wuyu-row">
          <span class="wuyu-sub-label">实际</span>
          <el-input v-model="editForm.slaoyu" size="small" class="wuyu-input"></el-input>
          <span class="wuyu-sub-label">期望</span>
          <el-input v-model="editForm.plaoyu" size="small" class="wuyu-input"></el-input>
        </div>
      </el-form-item>
      <el-form-item label="假期计划">
        <el-input type="textarea" v-model="editForm.pplan"></el-input>
      </el-form-item>
      <el-form-item label="班主任建议">
        <el-input type="textarea" v-model="editForm.comment"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleEditClose">取 消</el-button>
      <el-button type="primary" @click="handleEditSave">保 存</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { showLoading, closeLoading } from '@/utils/loading'
import { noticeBookletModify } from '@/api/notice'
export default {
  name: 'EditModal',
  props: {
    // 控制弹框显示
    editDialogVisible: {
      type: Boolean,
      required: true
    },
    // 编辑表单数据
    editForm: {
      type: Object,
      required: true
    }
  },
  methods: {
    // 点击取消
    handleEditClose() {
      this.$emit('close')
      // console.log('close')
    },
    // 点击确认
    async handleEditSave() {
      this.$emit('save', this.editForm)
      console.log(this.editForm)
      try{
        showLoading(`修改${this.editForm.studentName}同学的通知册中，请稍候...`);
        const payload = { ...this.editForm } // 浅拷贝
        // 或 const payload = JSON.parse(JSON.stringify(this.editForm)) // 深拷贝
        // 这里可以直接用payload作为json传给后端
        // console.log(payload);
        const res = await noticeBookletModify(payload)
        if(res.code ===200 ) {
          closeLoading();
          this.$message.success('修改成功');
          // 修改成功后触发刷新事件
          this.$emit('refresh-data');
          // 关闭弹框
          this.$emit('close');
        } else {
          this.$message.error('修改失败，请重新修改');
        }
      } catch(error) {
        console.error('修改失败:', error)
        this.$message.error('修改失败，请重新修改');
      } finally {
        closeLoading()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.wuyu-row {
  display: flex;
  align-items: center;
  margin-bottom: 0;
}
.wuyu-sub-label {
  margin: 0 6px;
  color: #888;
}
.wuyu-input {
  width: 80px;
  margin-right: 16px;
}
::v-deep .el-dialog__title {
  display: inline-block;
  width: 100%;
  text-align: center;
}

</style>
