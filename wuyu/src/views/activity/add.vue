<template>
  <div  style="width: 50%;margin:150px auto">
<el-form ref="form" :model="form" name="form" label-width="80px"    >

  <el-form-item label="活动名称:">
    <el-input v-model="form.activityName"></el-input>
  </el-form-item>

  <el-form-item label="活动类别:">
    <el-select v-model="form.activityTarget" placeholder="请选择活动类别">
      <el-option label="德育" value="德育"></el-option>
      <el-option label="智育" value="智育"></el-option>
      <el-option label="体育" value="体育"></el-option>
      <el-option label="劳育" value="劳育"></el-option>
      <el-option label="美育" value="美育"></el-option>
    </el-select>
  </el-form-item>

  <el-form-item label="活动学年:">
    <el-select v-model="form.activityTerm1" placeholder="请选择活动学期">
      <el-option label="一年级" value="1"></el-option>
      <el-option label="二年级" value="3"></el-option>
      <el-option label="三年级" value="5"></el-option>
      <el-option label="四年级" value="7"></el-option>
      <el-option label="五年级" value="9"></el-option>
      <el-option label="六年级" value="11"></el-option>
    </el-select>
  </el-form-item>

  <el-form-item label="活动学期:">
    <el-select v-model="form.activityTerm2" placeholder="请选择活动学期">
      <el-option label="上学期" value="0"></el-option>
      <el-option label="下学期" value="1"></el-option>
    </el-select>
  </el-form-item>


  <el-form-item label="活动内容:">
    <el-input type="textarea" v-model="form.activityContent" :rows="10"></el-input>
  </el-form-item>

  <el-form-item>
    <el-button type="primary" @click="onSubmit">创建活动</el-button>
    <el-button>取消创建</el-button>
  </el-form-item>

</el-form>
  </div>
</template>
<script>
  import teachingActivity from '@/api/activity'

  export default {
    data() {
      return {
        form: {
          activityName: '',
          activityTarget: '',
          activityTerm: '',
          activityTerm2:'',
          activityTerm1:'',
          activityContent: '',
        }
      }
    },
    methods: {
      onSubmit() {
        this.form.activityTerm=parseInt(this.form.activityTerm1)+parseInt(this.form.activityTerm2);
         // alert(this.form.activityTerm)
        console.log(this.form)
          this.$refs.form.validate(valid => {
            if (valid) {
              this.loading = true
              this.$confirm('是否提交数据', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                teachingActivity.saveActivity(this.form).then(response => {
                  if (response.code = 200) {
                    this.$notify({
                      title: '成功',
                      message: '活动创建成功',
                      type: 'success',
                      duration: 2000
                    })
                    this.$router.back();
                  }
                });
              }).catch(() => {
                this.loading = false
              })
            } else {
              return false
            }
          })
      }
    }
  }
</script>
