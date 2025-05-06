<template>
  <div className="Home3">
    <div style="margin-left: 200px">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-form ref="form" :model="form" label-width="60px">
            <el-form-item >
              输入查询ip：
              <el-input v-model="form.ip">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button  @click="myEcharts2">查询
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div>
        <div id="main" ></div>
        <div id="main2" ></div>
      </div>
    </div>

  </div>
</template>

<script>
/*
import request from "../utils/request";
var data1=[]
var data2=[]
export default {
  name: 'Home3',
  data() {
    const self = this;
    return {
      form: {},
      search: '',
      total: 0,
      dialogVisible: false,
      currentPage: 1,
      dialogVisible2: false,
      pageSize: 10,
      ip: "",
      data1:[

      ],
      data2:[

      ],
      js:{

      }
    }

  },
  methods: {
    myEcharts2(){
      console.log(this.form)

        request.post("/user4/ip",this.form).then(res =>{

          this.data3=res.data.bai
          this.data4=res.data.dat
          var myChart = this.$echarts.init(document.getElementById('main'));

          // 指定图表的配置项和数据
          var option = {
            tooltip: {              //设置tip提示
              trigger: 'axis'
            },

            legend: {               //设置区分（哪条线属于什么）
              data:['内存']
            },
            color: ['#8AE09F'],       //设置区分（每条线是什么颜色，和 legend 一一对应）
            xAxis: {                //设置x轴
              type: 'category',
              boundaryGap: false,     //坐标轴两边不留白
              data: this.data3,
              name: '日期',           //X轴 name
              nameTextStyle: {        //坐标轴名称的文字样式
                color: '#2b2b2b',
                fontSize: 16,
                padding: [0, 0, 0, 20]
              },
              axisLine: {             //坐标轴轴线相关设置。
                lineStyle: {
                  color: '#2b2b2b',
                }
              }
            },
            yAxis: {
              name: '内存使用率%',
              nameTextStyle: {
                color: '#2b2b2b',
                fontSize: 16,
                padding: [0, 0, 10, 0]
              },
              axisLine: {
                lineStyle: {
                  color: '#2b2b2b',
                }
              },
              type: 'value'
            },
            series: [
              {
                name: '内存',
                data: this.data4,
                type: 'line',
                lineStyle: {
                  normal: {
                    color: '#FA6F53',
                  }
                },
              }
            ]
          };
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        })

        //this.myEcharts()



    },
    myEcharts() {
      console.log(this.form)
      request.post("/user4/ip2",this.form).then(res => {


        this.data5 = res.data.bai
        this.data6 = res.data.dat
        var myChart = this.$echarts.init(document.getElementById('main2'));

        var option = {
          tooltip: {              //设置tip提示
            trigger: 'axis'
          },

          legend: {               //设置区分（哪条线属于什么）
            data:['磁盘']
          },
          color: ['#8AE09F'],       //设置区分（每条线是什么颜色，和 legend 一一对应）
          xAxis: {                //设置x轴
            type: 'category',
            boundaryGap: false,     //坐标轴两边不留白
            data: this.data5,
            name: '日期',           //X轴 name
            nameTextStyle: {        //坐标轴名称的文字样式
              color: '#2b2b2b',
              fontSize: 16,
              padding: [0, 0, 0, 20]
            },
            axisLine: {             //坐标轴轴线相关设置。
              lineStyle: {
                color: '#2b2b2b',
              }
            }
          },
          yAxis: {
            name: '磁盘使用率%',
            nameTextStyle: {
              color: '#2b2b2b',
              fontSize: 16,
              padding: [0, 0, 10, 0]
            },
            axisLine: {
              lineStyle: {
                color: '#2b2b2b',
              }
            },
            type: 'value'
          },
          series: [
            {
              name: '磁盘',
              data: this.data6,
              type: 'line',
              lineStyle: {
                normal: {
                  color: '#FA6F53',
                }
              },
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      })

    }
  }
}
*/
</script>

<style>
/*
#main{
  width:1000px;
  height:500px;
  margin-left: 60%;
  margin-top: 50%;
  transform: translate(-110%,-190%);
}
#main2{
  width:1000px;
  height:500px;
  margin-left: 60%;
  margin-top: 50%;
  transform: translate(-20%,-481%);
}*/
</style>
