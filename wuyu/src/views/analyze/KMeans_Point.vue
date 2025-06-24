<template>
  <div style="margin: 30px">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" :label-position="labelPosition" class="demo-ruleForm">
      <el-row :gutter="50" style="margin-bottom: 120px; flex-wrap: wrap; display:flex;" >
        <el-col :span="12">
          <el-card style="color: #606266; min-width: 100%; height: 100%; margin-right: 20px;">
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px">
              <div style="margin-bottom: 40px;">
                <h1 style="color: #67C23A">K-Means算法实时画图</h1>
                <p>利用K-Means聚类算法，通过指定散点图X轴、Y轴坐标元素以及学生年级后，点击提交按钮，展示相应散点图数据。</p>
                <p></p>
              </div>
              <el-row style="margin-bottom: 18px">
                <el-col style="color: #409EFF" :span="12"><i class="el-icon-user-solid"></i>优秀: {{stu_num["1"]}}</el-col>
                <el-col style="color: #67C23A" :span="12"><i class="el-icon-user-solid"></i>良好: {{stu_num["2"]}}</el-col>
              </el-row>
              <el-row>
                <el-col style="color: #E6A23C" :span="12"><i class="el-icon-user-solid"></i>一般: {{stu_num["3"]}}</el-col>
                <el-col style="color: #F56C6C" :span="12"><i class="el-icon-user-solid"></i>不及格: {{stu_num["4"]}}</el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card style="color: #67C23A; min-width: 100%; height: 100%; margin-right: 20px;">

            <el-form-item label="距离公式" style="text-align: center" prop="Distance">
              <el-radio-group v-model="ruleForm.Distance" size="medium" @change="Minkowski_diabled">
                <el-radio-button label="夹角余弦相似度"></el-radio-button>
                <el-radio-button label="闵可夫斯基距离"></el-radio-button>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="P值" style="text-align: center" prop="P">
              <el-input v-model.number="ruleForm.P" autocomplete="off" :disabled="Minkowski" placeholder="请输入正整数的P值" style="width: 180px"></el-input>
              <p style="color: #606266">只有选择闵可夫斯基距离时可填写</p>
            </el-form-item>

            <el-form-item label="X轴" style="text-align: center" prop="X_axles">
              <el-radio-group v-model="ruleForm.X_axles" size="medium" @change="Disable_2radio_X">
                <el-radio-button label="德育" :disabled="show1_1"></el-radio-button>
                <el-radio-button label="智育" :disabled="show1_2"></el-radio-button>
                <el-radio-button label="体育" :disabled="show1_3"></el-radio-button>
                <el-radio-button label="美育" :disabled="show1_4"></el-radio-button>
                <el-radio-button label="劳育" :disabled="show1_5"></el-radio-button>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="Y轴" style="text-align: center" prop="Y_axles">
              <el-radio-group v-model="ruleForm.Y_axles" size="medium" fill="#67C23A" @change="Disable_2radio_Y">
                <el-radio-button label="德育" :disabled="show2_1"></el-radio-button>
                <el-radio-button label="智育" :disabled="show2_2"></el-radio-button>
                <el-radio-button label="体育" :disabled="show2_3"></el-radio-button>
                <el-radio-button label="美育" :disabled="show2_4"></el-radio-button>
                <el-radio-button label="劳育" :disabled="show2_5"></el-radio-button>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="年级" style="text-align: center" prop="regrade">
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

            <el-form-item>
              <el-col style="text-align: center">
                <a href="#sub_skip"><el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">提交</el-button></a>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
              </el-col>
            </el-form-item>
          </el-card>
        </el-col>
      </el-row>


      <el-row>
        <el-col v-loading="loading"
                :data="ruleForm"
                style="width: 100%">
          <a id="sub_skip"></a>
          <div id="main" style="width: 1000px; height: 600px"></div>
        </el-col>
      </el-row>



    </el-form>
  </div>

</template>

<script>
import axios from "@/utils/axios";
import * as echarts from 'echarts';

export default {
  name: "KMeans_Point",
  data () {
    return {
      loading: false,
      Minkowski: true,
      labelPosition: 'top',
      show1_1: false,
      show1_2: false,
      show1_3: false,
      show1_4: false,
      show1_5: false,
      show2_1: false,
      show2_2: false,
      show2_3: false,
      show2_4: false,
      show2_5: false,
      ruleForm: {
        Distance: '夹角余弦相似度',
        P: 1,
        X_axles: '',
        Y_axles: '',
        regrade: ''
      },
      stu_num: {},
      rules: {
        Distance: [
          { required: true, message: '请选择K-Means距离公式', trigger: 'change' }
        ],
        P: [
          { required: true, message: '请输入闵可夫斯基距离的P值', trigger: 'blur' },
          {pattern: /^(?:[1-9]\d*)$/, message: '必须为正整数'}
        ],
        X_axles: [
          { required: true, message: '请选择X轴', trigger: 'change' }
        ],
        Y_axles: [
          { required: true, message: '请选择Y轴', trigger: 'change' }
        ],
        regrade: [
          { required: true, message: '请选择学生年级', trigger: 'change' }
        ]
      }

    };
  },
  methods: {
    Disable_2radio_X(value){
      // 如果第一个选择框有值，那么第二个选择框可选，否则禁用
      switch (value){
        case '德育':
          this.show2_1 = true;
          this.show2_2 = false;
          this.show2_3 = false;
          this.show2_4 = false;
          this.show2_5 = false;
          break;
        case '智育':
          this.show2_1 = false;
          this.show2_2 = true;
          this.show2_3 = false;
          this.show2_4 = false;
          this.show2_5 = false;
          break;
        case '体育':
          this.show2_1 = false;
          this.show2_2 = false;
          this.show2_3 = true;
          this.show2_4 = false;
          this.show2_5 = false;
          break;
        case '美育':
          this.show2_1 = false;
          this.show2_2 = false;
          this.show2_3 = false;
          this.show2_4 = true;
          this.show2_5 = false;
          break;
        case '劳育':
          this.show2_1 = false;
          this.show2_2 = false;
          this.show2_3 = false;
          this.show2_4 = false;
          this.show2_5 = true;
      }
    },
    Disable_2radio_Y(value){
      // 如果第一个选择框有值，那么第二个选择框可选，否则禁用
      switch (value){
        case '德育':
          this.show1_1 = true;
          this.show1_2 = false;
          this.show1_3 = false;
          this.show1_4 = false;
          this.show1_5 = false;
          break;
        case '智育':
          this.show1_1 = false;
          this.show1_2 = true;
          this.show1_3 = false;
          this.show1_4 = false;
          this.show1_5 = false;
          break;
        case '体育':
          this.show1_1 = false;
          this.show1_2 = false;
          this.show1_3 = true;
          this.show1_4 = false;
          this.show1_5 = false;
          break;
        case '美育':
          this.show1_1 = false;
          this.show1_2 = false;
          this.show1_3 = false;
          this.show1_4 = true;
          this.show1_5 = false;
          break;
        case '劳育':
          this.show1_1 = false;
          this.show1_2 = false;
          this.show1_3 = false;
          this.show1_4 = false;
          this.show1_5 = true;
      }
    },
    submitForm(formName) {
      // console.log("X:", this.ruleForm.X_axles);
      // console.log("Y:", this.ruleForm.Y_axles);
      // console.log('X, Y', this.ruleForm)
      this.loading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {

          var chartDom = document.getElementById('main');
          var myChart = echarts.init(chartDom);
          var option;
          var x_name = this.ruleForm.X_axles;
          var y_name = this.ruleForm.Y_axles;

          option = {
            xAxis: {
              name: this.ruleForm.X_axles,
            },
            yAxis: {
              name: this.ruleForm.Y_axles,
            },
            legend: {
              right: '10%',
              top: '3%',
              // data: ['优秀', '良好', '一般', '不及格']
              data: ['优秀', '良好', '一般', '不及格']
            },
            tooltip: {
              trigger: 'item',
              formatter:function(params){
                console.log(params)
                return params.seriesName + "<br>" + x_name + params.data[0] + "<br>" + y_name + params.data[1];
              }
            },
            // 工具箱
            toolbox: {
              // 显示工具箱
              show: true,
              feature: {
                mark: {
                  show: true
                },
                // 还原
                restore: {
                  show: true
                },
                // 保存为图片
                saveAsImage: {
                  show: true
                }
              }
            },
            series: [
              {
                name: '优秀',
                type: 'scatter',
                encode: { tooltip: [0, 1] },
                symbolSize: 10,
                datasetIndex: 1,
                data: []
              },
              {
                name: '良好',
                type: 'scatter',
                encode: { tooltip: [0, 1] },
                symbolSize: 10,
                datasetIndex: 1,
                data: []
              },
              {
                name: '一般',
                type: 'scatter',
                encode: { tooltip: [0, 1] },
                symbolSize: 10,
                datasetIndex: 1,
                data: []
              },
              {
                name: '不及格',
                type: 'scatter',
                encode: { tooltip: [0, 1] },
                symbolSize: 10,
                datasetIndex: 1,
                data: []
              }
            ]
          };
          // 服务器后端功能有些问题，本地测试
          //axios.post()为发送数据到后端 改axios.get 后端用@RequestMapping("/save")@ResponseBody接收
          axios.get("http://36.111.68.174:33380/kmeanspoint/KMeans_2", this.ruleForm).then(res=>{
          // axios.get("http://49.51.69.99:9200/kmeanspoint/KMeans_2", this.ruleForm).then(res=>{
            console.log(res);
            option.series[0].data = res.data[0]["优秀"];
            option.series[1].data = res.data[0]["良好"];
            option.series[2].data = res.data[0]["一般"];
            option.series[3].data = res.data[0]["不及格"];
            this.stu_num = res.data[1];
            console.log(this.stu_num)
            option && myChart.setOption(option);
            this.loading = false
          })
        } else {
          console.log('提交失败!!');
          this.loading = false;
          this.show1_1 = false;
          this.show1_2 = false;
          this.show1_3 = false;
          this.show1_4 = false;
          this.show1_5 = false;
          this.show2_1 = false;
          this.show2_2 = false;
          this.show2_3 = false;
          this.show2_4 = false;
          this.show2_5 = false;
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.show1_1 = false;
      this.show1_2 = false;
      this.show1_3 = false;
      this.show1_4 = false;
      this.show1_5 = false;
      this.show2_1 = false;
      this.show2_2 = false;
      this.show2_3 = false;
      this.show2_4 = false;
      this.show2_5 = false;
      this.loading = false
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
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;
    var x_name = 'qq';
    var y_name = 'ww';
    // console.log(x_name, this.ruleForm.X_axles)
    option = {
      xAxis: {
        name: 'qq',
      },
      yAxis: {
        name: 'ww',
      },
      legend: {
        right: '10%',
        top: '3%',
        data: ['A', 'B']
      },
      tooltip: {
        trigger: 'item',
        formatter:function(params){
          // console.log(params)
          return params.seriesName + "<br>" + x_name + params.data[0] + "<br>" + y_name + params.data[1];
        }
      },
      // 工具箱
      toolbox: {
        // 显示工具箱
        show: true,
          feature: {
          dataView: {},
          mark: {
            show: true
          },
          // 还原
          restore: {
            show: true
          },
          // 保存为图片
          saveAsImage: {
            show: true
          }
        }
      },
      series: [
        {
          name: 'A',
          type: 'scatter',
          encode: { tooltip: [0, 1] },
          symbolSize: 10,
          datasetIndex: 1,
          data: [
            [10.0, 8.04],
            [8.07, 6.95],
            [13.0, 7.58],
            [9.05, 8.81],
            [11.0, 8.33],
            [14.0, 7.66],
            [13.4, 6.81],
            [10.0, 6.33],
            [14.0, 8.96],
            [12.5, 6.82],
            [9.15, 7.2],
            [11.5, 7.2],
            [3.03, 4.23],
            [12.2, 7.83],
            [2.02, 4.47],
            [1.05, 3.33],
            [4.05, 4.96],
            [6.03, 7.24],
            [12.0, 6.26],
            [12.0, 8.84],
            [7.08, 5.82],
            [5.02, 5.68]
          ]
        },
        {
          name: 'B',
          type: 'scatter',
          encode: { tooltip: [0, 1] },
          symbolSize: 10,
          datasetIndex: 1,
          data: [
            [1, 8.04],
            [2.07, 6.95],
            [1.0, 7.58],
            [4.05, 8.81],
            [11.0, 9.33]
          ]
        }
      ]
    };
    option && myChart.setOption(option);
  }
}
</script>

<style scoped>

</style>
