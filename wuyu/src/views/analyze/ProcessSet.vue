<template>
<div>
  <div style="margin-bottom: 40px; margin-top: 40px; text-align: center; color: #606266">
    <h1 style="font-weight: bold">数据预处理与更新设置</h1>
    <p>用户自定义算法的预处理方法</p>
  </div>
  <div style="margin: 0 200px 0 200px">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-card style="margin-bottom: 30px">

          <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px; margin-bottom: 20px">
            <el-popover
              placement="top-start"
              title="K-Means算法"
              width="200"
              trigger="hover"
              content="主要应用于数据分析1、K-Means画图、数据预测">
            <h4 style="font-weight: bold; color: #409EFF" slot="reference">K-Means算法</h4>
            </el-popover>
          </div>
          <el-row>
            <el-col :span="12">
              <el-popover
                placement="top-start"
                title="填补缺失值选择"
                width="200"
                trigger="hover"
                content="K-Means重在聚类，平均值法有利于使数据不分散">
                <el-form-item label="填补缺失值" prop="Kfill" slot="reference">
                  <el-radio-group v-model="ruleForm.Kfill" size="medium">
                    <el-radio-button label="平均值法"></el-radio-button>
                    <el-radio-button label="赋0法"></el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-popover>
            </el-col>
            <el-col :span="12">
              <el-popover
                placement="top-start"
                title="规范化"
                width="200"
                trigger="hover"
                content="对原始数据的线性变换，将数据值映射到[0, 100]区间内">
              <el-form-item label="数据规范化" prop="Knormal" slot="reference">
                <el-radio-group v-model="ruleForm.Knormal" size="medium">
                  <el-radio-button label="是"></el-radio-button>
                  <el-radio-button label="否"></el-radio-button>
                </el-radio-group>
              </el-form-item>
              </el-popover>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-popover
                placement="top-start"
                title="距离公式"
                width="200"
                trigger="hover"
                content="K-Means聚类算法中点与点间距离的计算公式">
              <el-form-item label="距离公式" prop="Distance" slot="reference">
                <el-radio-group v-model="ruleForm.Distance" size="medium" @change="Minkowski_diabled">
                  <el-radio-button label="夹角余弦相似度"></el-radio-button>
                  <el-radio-button label="闵可夫斯基距离"></el-radio-button>
                </el-radio-group>
              </el-form-item>
              </el-popover>
            </el-col>
            <el-col :span="12">
              <el-form-item label="P值" prop="P">
                <el-input v-model.number="ruleForm.P" autocomplete="off" :disabled="Minkowski" placeholder="请输入正整数的P值" style="width: 180px"></el-input>
                <p style="color: #606266">只有选择闵可夫斯基距离时可填写</p>
              </el-form-item>
            </el-col>
          </el-row>
        </el-card>
        <el-card style="margin-bottom: 30px">
          <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px; margin-bottom: 20px">
            <el-popover
              placement="top-start"
              title="Aporiri算法"
              width="200"
              trigger="hover"
              content="主要应用数据分析2">
            <h4 style="font-weight: bold; color: #67C23A" slot="reference">Aporiri算法</h4>
            </el-popover>
          </div>
          <el-col :span="12">
            <el-popover
              placement="top-start"
              title="填补缺失值"
              width="200"
              trigger="hover"
              content="建议赋0法，0分和空值都是0">
            <el-form-item label="填补缺失值" prop="Afill"  slot="reference">
              <el-radio-group v-model="ruleForm.Afill" size="medium" fill="#67C23A">
                <el-radio-button label="平均值法"></el-radio-button>
                <el-radio-button label="赋0法"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            </el-popover>
          </el-col>
          <el-col :span="12">
            <el-popover
              placement="top-start"
              title="规范化"
              width="200"
              trigger="hover"
              content="对原始数据的线性变换，将数据值映射到[0, 100]区间内">
            <el-form-item label="数据规范化" prop="Anormal" slot="reference">
              <el-radio-group v-model="ruleForm.Anormal" size="medium" fill="#67C23A">
                <el-radio-button label="是"></el-radio-button>
                <el-radio-button label="否"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            </el-popover>
          </el-col>
        </el-card>
        <el-card style="margin-bottom: 30px">
          <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px; margin-bottom: 20px">
            <el-popover
              placement="top-start"
              title="朴素贝叶斯算法"
              width="200"
              trigger="hover"
              content="主要应用数据预测">
            <h4 style="font-weight: bold; color: #E6A23C" slot="reference">朴素贝叶斯算法</h4>
            </el-popover>
          </div>
          <el-col :span="12">
            <el-popover
              placement="top-start"
              title="填补缺失值"
              width="200"
              trigger="hover"
              content="建议赋0法，0分和空值都是0">
            <el-form-item label="填补缺失值" prop="Bfill" slot="reference">
              <el-radio-group v-model="ruleForm.Bfill" size="medium" fill="#E6A23C">
                <el-radio-button label="平均值法"></el-radio-button>
                <el-radio-button label="赋0法"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            </el-popover>
          </el-col>
          <el-col :span="12">
            <el-popover
              placement="top-start"
              title="规范化"
              width="200"
              trigger="hover"
              content="对原始数据的线性变换，将数据值映射到[0, 100]区间内">
            <el-form-item label="数据规范化" prop="Bnormal" slot="reference">
              <el-radio-group v-model="ruleForm.Bnormal" size="medium" fill="#E6A23C">
                <el-radio-button label="是"></el-radio-button>
                <el-radio-button label="否"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            </el-popover>
          </el-col>
        </el-card>
        <el-card style="margin-bottom: 30px">
          <el-col style="margin-left: 180px; margin-top: 10px">
            <el-form-item >
              <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
              <el-button type="danger" @click="resetForm('ruleForm')">重置</el-button>
              <el-button @click="handle">返回</el-button>
            </el-form-item>
          </el-col>
          <el-col style="margin-left: 230px">
            <el-form-item>
              <el-button type="success" @click="bestset" round>默认最佳设置</el-button>
            </el-form-item>
          </el-col>


        </el-card>
      </el-row>
    </el-form>
  </div>
</div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "ProcessSet",
  data() {
    return {
      ruleForm: {
        Kfill: '',
        Knormal: '',
        Distance: '',
        P: '',
        Afill: '',
        Anormal: '',
        Bfill: '',
        Bnormal: '',
      },
      rules: {
        Kfill: [
          { required: true, message: '请选择填补缺失值方法', trigger: 'change' }
        ],
        Knormal: [
          { required: true, message: '请选择是否进行数据规范化', trigger: 'change' }
        ],
        Distance: [
          { required: true, message: '请选择K-Means距离公式', trigger: 'change' }
        ],
        P: [
          { required: true, message: '请输入闵可夫斯基距离的P值', trigger: 'blur' },
          {pattern: /^(?:[1-9]\d*)$/, message: '必须为正整数'}
        ],
        Afill: [
          { required: true, message: '请选择填补缺失值方法', trigger: 'change' }
        ],
        Anormal: [
          { required: true, message: '请选择是否进行数据规范化', trigger: 'change' }
        ],
        Bfill: [
          { required: true, message: '请选择填补缺失值方法', trigger: 'change' }
        ],
        Bnormal: [
          { required: true, message: '请选择是否进行数据规范化', trigger: 'change' }
        ]
      },
      Minkowski: true
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.get("http://36.111.68.174:33380/dataprocessing/UpSet", this.ruleForm).then(res => {
            console.log(res);
            alert("设置成功！");
            this.$router.push('/analyze/DataProcessing');
          });

        } else {
          console.log('提交失败!!!');
          alert("设置失败!!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    bestset(formName) {
      this.ruleForm.Kfill = '平均值法';
      this.ruleForm.Knormal = '是';
      this.ruleForm.Distance = '夹角余弦相似度';
      this.ruleForm.P = 1;
      this.ruleForm.Afill = '赋0法';
      this.ruleForm.Anormal = '是';
      this.ruleForm.Bfill = '赋0法';
      this.ruleForm.Bnormal = '是';
      this.Minkowski = true
    },
    handle () {
      //  路径/home对应我在router目录下index.js中定义的path属性值
      this.$router.push('/analyze/DataProcessing');
    },
    Minkowski_diabled() {
      if (this.ruleForm.Distance === '闵可夫斯基距离') {
        this.Minkowski = false;
      }else {
        this.Minkowski = true;
        this.ruleForm.P = 1;
      }
    }
  },
  mounted() {
    axios.get("http://36.111.68.174:33380/dataprocessing/setview", this.ruleForm).then(res => {
      this.ruleForm.Kfill = res.data.Kfill;
      this.ruleForm.Knormal = res.data.Knormal;
      this.ruleForm.Distance = res.data.Distance;
      this.ruleForm.P = res.data.P;
      if (this.ruleForm.P === 1) {
        this.ruleForm.P = 1;
        this.Minkowski = true;
      }else {
        this.Minkowski = false;
      }
      this.ruleForm.Afill = res.data.Afill;
      this.ruleForm.Anormal = res.data.Anormal;
      this.ruleForm.Bfill = res.data.Bfill;
      this.ruleForm.Bnormal = res.data.Bnormal;
    });
  },
  created() {

  }
}
</script>

<style scoped>

</style>
