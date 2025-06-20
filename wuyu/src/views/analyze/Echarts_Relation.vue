<template>
  <div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row :gutter="10" style="margin-bottom: 20px; flex-wrap: wrap; display:flex;">
        <el-col :span="12">
          <el-card style="color: #67C23A; min-width: 100%; height: 100%; margin-right: 20px;">
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px">
              <div style="margin-bottom: 40px; ">
                <h1>学生各育不及格率</h1>
                <div style="color: #606266">
                  <p>利用K-Means算法，通过学生德智体美劳五育成绩来划分来计算其不及格率。</p>
                  <p>与平时的死板划分不同，这是基于K-Means算法的动态划分，结果更能反映强势科目与弱势科目。</p>
                </div>
              </div>
            </div>
            <div id="radar" style="width: 500px; height: 400px; margin-left: 50px"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card style="min-width: 100%; height: 100%; margin-right: 20px;">
            <div style="padding: 10px 0; text-align: center; font-weight: bold; font-size: 18px">
              <div style="margin-bottom: 40px; color: #67C23A">
                <h1>学生表现与五育关联</h1>
                <div style="color: #606266">
                  <p><span style="color: #E6A23C">{{this.ruleForm.regrade}}</span>表现<span style="color: #E6A23C">{{this.ruleForm.rePer}}</span>的学生与五育的关联</p>
                  <p>这是经过Apriori算法计算得到的结论</p>
                </div>
              </div>
            </div>
            <el-form-item label="学生年级" style="text-align: center" prop="regrade">
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
            <el-form-item label="学生表现" style="text-align: center" prop="rePer">
              <el-select v-model="ruleForm.rePer" placeholder="请选择学生表现">
                <el-option label="优秀" value="优秀"></el-option>
                <el-option label="良好" value="良好"></el-option>
                <el-option label="一般" value="一般"></el-option>
                <el-option label="不及格" value="不及格"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">提交</el-button>
            </el-form-item>

            <div id="relation" style="width: 500px; height: 400px; align-content: center"></div>

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
  name: "Echarts_Relation",
  data(){
    return{
      loading: false,
      ruleForm:{
        regrade: '',
        rePer: ''
      },
      rules: {
        regrade: [
          { required: true, message: '请选择学生年级', trigger: 'change' }
        ],
        rePer: [
          { required: true, message: '请选择学生表现', trigger: 'change' }
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

          var myChart = echarts.init(document.getElementById('relation'));
          var option;

          var categories = [];

          option = {
            // 图的标题
            title: {
              text: ''
            },
            // 提示框的配置
            tooltip: {
              formatter: function (x) {
                return x.data.des;
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
            series: [{
              type: 'graph', // 类型:关系图
              layout: 'force', //图的布局，类型为力导图
              symbolSize: 40, // 调整节点的大小
              label: {
                fontWeight: 'bold',
                fontSize: 18,
                show: true
              },
              roam: true, // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移,可以设置成 'scale' 或者 'move'。设置成 true 为都开启
              edgeSymbol: ['circle', 'arrow'],
              edgeSymbolSize: [2, 10],
              edgeLabel: {
                fontSize: 20,
                formatter: function (x) {
                  return x.data.name;
                }
              },
              force: {
                repulsion: 2500,
                edgeLength: [10, 50]
              },
              draggable: true,
              lineStyle: {
                width: 5,
                color: '#409EFF'
              },

              data: [],
              links: [],
              categories: categories,
            }]
          };

          axios.get("http://36.111.68.174:33380/echarts/relationchange", this.ruleForm).then(res => {
            console.log(res);

            option.series[0].data[0] = {
              name: this.ruleForm.rePer,
              des: '学生表现' + this.ruleForm.rePer,
              symbolSize: 70,
              category: 0,
            };
            categories[0] = {
              name: this.ruleForm.rePer
            }
            for (var i = 1; i < res.data.length + 1; i++) {
              option.series[0].data[i] = {
                name: res.data[i - 1],
                des: res.data[i - 1] + "育",
                symbolSize: 50,
                category: i
              };
              option.series[0].links[i - 1] = {
                source: this.ruleForm.rePer,
                target: res.data[i - 1]
              };
              categories[i] = {
                name: res.data[i - 1]
              }
            }

            option && myChart.setOption(option);
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
  mounted() {
    var myChart = echarts.init(document.getElementById('relation'));
    var option;

    var chartDom1 = document.getElementById('radar');
    var myChart1 = echarts.init(chartDom1);
    var option1;

    var categories = [];

    option = {
      // 图的标题
      title: {
        text: ''
      },
      // 提示框的配置
      tooltip: {
        formatter: function (x) {
          return x.data.des;
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
      series: [{
        type: 'graph', // 类型:关系图
        layout: 'force', //图的布局，类型为力导图
        symbolSize: 40, // 调整节点的大小
        label: {
          fontWeight: 'bold',
          fontSize: 18,
          show: true
        },
        roam: true, // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移,可以设置成 'scale' 或者 'move'。设置成 true 为都开启
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [2, 10],
        edgeLabel: {
          fontSize: 20,
          formatter: function (x) {
            return x.data.name;
          }
        },
        force: {
          repulsion: 2500,
          edgeLength: [10, 50]
        },
        draggable: true,
        lineStyle: {
          width: 5,
          color: '#409EFF'
        },

        data: [
          {
            name: "A",
            des: "A",
            symbolSize: 50,
            category: 1
          },
          {
            name: "B",
            des: "B",
            symbolSize: 50,
            category: 2
          }

        ],
        links: [{
          source: "A",
          target: "B"
        }],
        categories: categories,
      }]
    };
    option1 = {
      legend: {
        orient: 'vertical',
        x:'top',      //可设定图例在左、右、居中
        y:'left',     //可设定图例在上、下、居中
        padding:[0,0,0,-5],
        data: ["一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "总体"]
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
      tooltip: {
        trigger: 'axis'
      },
      radar: {
        // shape: 'circle',
        indicator: [
          { name: '德育', max: 100 },
          { name: '智育', max: 100 },
          { name: '体育', max: 100 },
          { name: '美育', max: 100 },
          { name: '劳育', max: 100 }
        ]
      },
      series: [
        {
          name: 'Budget vs spending',
          type: 'radar',
          tooltip: {
            trigger: 'item'
          },
          areaStyle: {},
          data: [
            {
              value: [0.5411255411255411, 24.891774891774894, 8.152958152958153, 65.83694083694084, 36.25541125541126, 9.812409812409813],
              name: 'A'
            },
            {
              value: [0.5, 2, 89, 6, 36, 9],
              name: 'B'
            }
          ]
        }
      ]
    };
    axios.get("http://36.111.68.174:33380/echarts/Fai_rate").then(res1 => {
      console.log(res1);
      option1.series[0].data = res1.data;
      // option1.series[0].data = [
      //   {name: "优秀"; value: res.data[0]}
      // ]option1 && myChart1.setOption(option1);
      option1 && myChart1.setOption(option1);
    })
    option && myChart.setOption(option);
  }

}
</script>

<style scoped>

</style>
