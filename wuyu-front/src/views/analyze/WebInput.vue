<template>
  <div style="margin: 30px">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row :gutter="50" style="margin-bottom: 120px; flex-wrap: wrap; display:flex;" >
        <el-col :span="12">
          <el-card style="color: #606266; min-width: 100%; height: 100%; margin-right: 20px;">
              <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px">
                <div style="margin-bottom: 40px">
                  <h1 style="color: #67C23A"><span style="color: #E6A23C">{{this.ruleForm.Algorithm}}</span>分析结果</h1>
                </div>
                <div style="margin-bottom: 30px">
                  <h3>该学生学习表现</h3>
                </div>
                <div style="margin-bottom: 50px">
                  <h2>鉴定为<span style="color: #F56C6C">{{this.result_serve}}</span></h2>
                </div>
                <div style="margin-bottom: 50px">
                  <h4>这是基于{{this.ruleForm.Algorithm}}的学生五育成绩预测学生表现</h4>
                  <h4>此结果仅供参考</h4>
                </div>
              </div>
            </el-card>
        </el-col>
        <el-col :span="12">
          <el-card style="color: #67C23A; min-width: 100%; height: 100%; margin-right: 20px;">
              <el-row style="text-align: center">
                <el-form-item label="预测算法" prop="Algorithm">
                  <el-radio-group v-model="ruleForm.Algorithm" size="medium">
                    <el-radio-button label="K-Means算法"></el-radio-button>
                    <el-radio-button label="朴素贝叶斯算法"></el-radio-button>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="年级" prop="regrade">
                  <el-select v-model="ruleForm.regrade" placeholder="请选择年级">
                    <el-option label="一年级" value="一年级"></el-option>
                    <el-option label="二年级" value="二年级"></el-option>
                    <el-option label="三年级" value="三年级"></el-option>
                    <el-option label="四年级" value="四年级"></el-option>
                    <el-option label="五年级" value="五年级"></el-option>
                    <el-option label="六年级" value="六年级"></el-option>
                    <el-option label="总体" value="Sum"></el-option>
                  </el-select>
                </el-form-item>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="德育分" prop="De">
                    <el-input v-model.number="ruleForm.De" autocomplete="off"></el-input>

                  </el-form-item>
                </el-col>

                <el-col :span="12">
                  <el-form-item label="智育分" prop="Zhi">
                    <el-input v-model.number="ruleForm.Zhi" autocomplete="off"></el-input>

                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="体育分" prop="Ti">
                    <el-input v-model.number="ruleForm.Ti" autocomplete="off"></el-input>

                  </el-form-item>
                </el-col>

                <el-col :span="12">
                  <el-form-item label="美育分" prop="Mei">
                    <el-input v-model.number="ruleForm.Mei" autocomplete="off"></el-input>

                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12">
                  <el-form-item label="劳育分" prop="Lao">
                    <el-input v-model.number="ruleForm.Lao" autocomplete="off"></el-input>

                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                  <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">提交</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-card>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import * as echarts from 'echarts';
// import axios from "@/plugins/axios";
import axios from "@/utils/axios";

export default {
  name: "WebInput",
  data() {
    return {
      loading: false,
      result_serve: '不知道',
      ruleForm: {
        Algorithm: '',
        regrade: '',
        De: '',
        Zhi: '',
        Ti: '',
        Mei: '',
        Lao: ''
      },
      rules: {
        Algorithm:[
          { required: true, message: '请选择预测算法', trigger: 'change' }
        ],
        regrade: [
          { required: true, message: '请选择学生年级', trigger: 'change' }
        ],
        De: [
          { required: true, message: '请输入德育分', trigger: 'blur' },
          { type: 'number', min: 0, max: 100, message: '必须为数字'}
        ],
        Zhi: [
          { required: true, message: '请输入智育分', trigger: 'blur' },
          {type: 'number', min: 0, max: 100, message: '必须为数字'}
        ],
        Ti: [
          { required: true, message: '请输入劳育分', trigger: 'blur' },
          {type: 'number', min: 0, max: 100, message: '必须为数字'}
        ],
        Mei: [
          { required: true, message: '请输入美育分', trigger: 'blur' },
          {type: 'number', min: 0, max: 100, message: '必须为数字'}
        ],
        Lao: [
          { required: true, message: '请输入劳育分', trigger: 'blur' },
          {type: 'number', min: 0, max: 100, message: '必须为数字'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.loading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          switch (this.ruleForm.Algorithm){
            case "K-Means算法":
              //axios.post()为发送数据到后端 改axios.get 后端用@RequestMapping("/save")@ResponseBody接收
              axios.get("http://36.111.68.174:33380/WebInputPrediction/KMeans", this.ruleForm).then(res=>{
                console.log(res)
                this.result_serve = res.data
                this.loading = false
              });
              break;
            case "朴素贝叶斯算法":
              //axios.post()为发送数据到后端 改axios.get 后端用@RequestMapping("/save")@ResponseBody接收
              axios.get("http://36.111.68.174:33380/WebInputPrediction/NaiveBayes", this.ruleForm).then(res=>{
                console.log(res)
                this.result_serve = res.data
                this.loading = false
              });
              break;
            default: break;
          }
        } else {
          console.log('提交失败!!!');
          this.loading = false
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.result_serve_K = "不知道";
      this.loading = false
    }
  }
}

</script>

<style scoped>

</style>
