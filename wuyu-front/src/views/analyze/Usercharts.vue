<template>
  <div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row :gutter="10" style="margin-bottom: 20px">
<!--      一行24-->
        <el-col :span="6">
          <el-card style="color: #409EFF">
            <div><i class="el-icon-user-solid"></i> 学生总数</div>
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 25px">
              {{ruleForm.stu_num}}
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="color: #67C23A">
            <div><i class="el-icon-user-solid"></i> 全校不及格人数</div>
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 25px">
              {{ruleForm.fai_num}}
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="color: #E6A23C">
            <div><i class="el-icon-user-solid"></i> 最好学科</div>
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 25px">
              {{ruleForm.Best_sub}}
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="color: #F56C6C">
            <div><i class="el-icon-user-solid"></i> 最差学科</div>
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 25px">
              {{ruleForm.Worst_sub}}
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10" style="margin-bottom: 20px; flex-wrap: wrap; display:flex;">
        <el-col :span="12">
          <el-card style="color: #67C23A; min-width: 100%; height: 100%; margin-right: 20px;">
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px">
              <div style="margin-bottom: 40px; ">
                <h1>K-Means算法</h1>
                <div style="color: #606266">
                  <p>利用K-Means算法，通过学生德智体美劳五育成绩来划分其表现。</p>
                  <p>学生表现将被划分为优秀、良好、一般与不及格四类，该饼图将展示各个表现人数。</p>
                </div>
              </div>
            </div>
            <div id="pai" style="width: 500px; height: 400px"></div>
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
              <el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">提交</el-button>
            </el-form-item>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card style="color: #67C23A; min-width: 100%; height: 100%; margin-right: 20px;">
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px">
              <div style="margin-bottom: 120px;">
                <h1>学生五育平均分</h1>
                <div style="color: #606266">
                  <p>被划分为优秀、良好、一般与不及格四类表现学生的平均成绩。</p>
                </div>
              </div>
            </div>
            <div id="funnel" style="width: 500px; height: 400px;"></div>
          </el-card>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import axios from "@/utils/axios";


export default {
  name: "Usercharts",
  data(){
    return{
      loading: false,
      ruleForm: {
        regrade: '',
        stu_num: '100',
        fai_num: '100',
        Best_sub: '不知道',
        Worst_sub: '不知道'
      },
      rules: {
        regrade: [
          { required: true, message: '请选择学生年级', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.loading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.ruleForm)

          var chartDom = document.getElementById('funnel');
          var myChart = echarts.init(chartDom);
          var option;

          var chartDom1 = document.getElementById('pai');
          var myChart1 = echarts.init(chartDom1);
          var option1;

          option = {
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c}分'
            },
            toolbox: {
              feature: {
                restore: {},
                saveAsImage: {}
              }
            },
            legend: {
              data: ['优秀', '良好', '一般', '不及格']
            },
            series: [
              {
                name: 'Funnel',
                type: 'funnel',
                left: '10%',
                top: 60,
                bottom: 60,
                width: '80%',
                min: 0,
                max: 100,
                minSize: '0%',
                maxSize: '100%',
                sort: 'descending',
                gap: 2,
                label: {
                  show: true,
                  position: 'inside'
                },
                labelLine: {
                  length: 10,
                  lineStyle: {
                    width: 1,
                    type: 'solid'
                  }
                },
                itemStyle: {
                  borderColor: '#fff',
                  borderWidth: 1
                },
                emphasis: {
                  label: {
                    fontSize: 20
                  }
                },
                data: []
              }
            ]
          };
          option1 = {
            tooltip: {
              trigger: 'item'
            },
            legend: {
              top: '5%',
              left: 'center'
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
                name: '学生个数',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                  borderRadius: 10,
                  borderColor: '#fff',
                  borderWidth: 2
                },
                label: {
                  show: false,
                  position: 'center'
                },
                emphasis: {
                  label: {
                    show: true,
                    fontSize: '40',
                    fontWeight: 'bold'
                  }
                },
                labelLine: {
                  show: false
                },
                data: []
              }
            ]
          };
          axios.get("http://36.111.68.174:33380/echarts/Performscore", this.ruleForm).then(res1 => {
            console.log(res1);
            option.series[0].data = res1.data;
            // option1.series[0].data = [
            //   {name: "优秀"; value: res.data[0]}
            // ]option1 && myChart1.setOption(option1);
            option && myChart.setOption(option);
            this.loading = false
          })
          axios.get("http://36.111.68.174:33380/echarts/scores", this.ruleForm).then(res1 => {
            console.log(res1);
            option1.series[0].data = res1.data;
            // option1.series[0].data = [
            //   {name: "优秀"; value: res.data[0]}
            // ]option1 && myChart1.setOption(option1);
            option1 && myChart1.setOption(option1);
            this.loading = false
          })
        } else {
          console.log('提交失败!!');
          this.loading = false
          return false;
        }
      });
    },
  },
  // // mounted页面元素渲染完成之后再触发
  mounted(){
    var chartDom = document.getElementById('funnel');
    var myChart = echarts.init(chartDom);
    var option;

    var chartDom1 = document.getElementById('pai');
    var myChart1 = echarts.init(chartDom1);
    var option1;
    option = {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c}分'
      },
      toolbox: {
        feature: {
          restore: {},
          saveAsImage: {}
        }
      },
      legend: {
        data: ['优秀', '良好', '一般', '不及格']
      },
      series: [
        {
          name: 'Funnel',
          type: 'funnel',
          left: '10%',
          top: 60,
          bottom: 60,
          width: '80%',
          min: 0,
          max: 100,
          minSize: '0%',
          maxSize: '100%',
          sort: 'descending',
          gap: 2,
          label: {
            show: true,
            position: 'inside'
          },
          labelLine: {
            length: 10,
            lineStyle: {
              width: 1,
              type: 'solid'
            }
          },
          itemStyle: {
            borderColor: '#fff',
            borderWidth: 1
          },
          emphasis: {
            label: {
              fontSize: 20
            }
          },
          data: [
            { value: 60, name: '良好' },
            { value: 40, name: '一般' },
            { value: 20, name: '不及格' },
            { value: 80, name: '优秀' }
          ]
        }
      ]
    };
    option1 = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '5%',
        left: 'center'
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
          name: '学生个数',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '40',
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: [
            {name: "优秀", value: 1},
            {name: "良好", value: 7},
            {name: "一般", value: 11},
            {name: "不及格", value: 7}]
        }
      ]
    };
    axios.get("http://36.111.68.174:33380/echarts/Top4").then(res1 => {
      console.log(res1);
      this.ruleForm.stu_num = res1.data.stu_num;
      this.ruleForm.fai_num = res1.data.fai_num;
      this.ruleForm.Best_sub = res1.data.Best_sub;
      this.ruleForm.Worst_sub = res1.data.Worst_sub;
    })
    option && myChart.setOption(option);
    option1 && myChart1.setOption(option1);
  }
}
</script>

<style scoped>

</style>
