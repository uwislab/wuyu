<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>  <div style="padding:20px;">
    <el-tabs type="border-card" :tab-position="tabPosition">
      <el-tab-pane label="优秀班级">
        <div>
          <el-row :gutter="24">
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <!-- <div slot="header" class="clearfix">
    <span></span>
  </div> -->
                <div class="echart" id="mychart1" :style="myChartStyle1"></div>
              </el-card>
            </el-col>
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <!-- <div slot="header" class="clearfix">
    <span></span>
  </div> -->
                <div class="echart" id="mychart2" :style="myChartStyle1"></div>
              </el-card>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <!-- <div slot="header" class="clearfix">
    <span></span>
  </div> -->
                <div class="echart" id="mychart3" :style="myChartStyle1"></div>
              </el-card>
            </el-col>
            <el-col :span="12" :xs="11">
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
      <el-tab-pane label="优秀个人">
        <div>
          <el-row :gutter="24">
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <div slot="header" class="clearfix">
                  <span>优秀个人总分图</span>
                </div>
                <div class="echart" id="mychart7" :style="myChartStyle2"></div>
              </el-card>
            </el-col>
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <div slot="header" class="clearfix">
                  <span>优秀个人德育图</span>
                </div>
                <div class="echart" id="mychart8" :style="myChartStyle2"></div>
              </el-card>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <div slot="header" class="clearfix">
                  <span>优秀个人智育图</span>
                </div>
                <div class="echart" id="mychart9" :style="myChartStyle2"></div>
              </el-card>
            </el-col>
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <div slot="header" class="clearfix">
                  <span>优秀个人体育图</span>
                </div>
                <div class="echart" id="mychart10" :style="myChartStyle2"></div>
              </el-card>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <div slot="header" class="clearfix">
                  <span>优秀个人美育图</span>
                </div>
                <div class="echart" id="mychart11" :style="myChartStyle2"></div>
              </el-card>
            </el-col>
            <el-col :span="12" :xs="11">
              <el-card class="box-card" style="height: 100%;width:100%">
                <div slot="header" class="clearfix">
                  <span>优秀个人劳育图</span>
                </div>
                <div class="echart" id="mychart12" :style="myChartStyle2"></div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div></el-container>
  </el-container>

</template>

<script>
import Aside from "@/views/teacher_workspace/Aside"
import api from '@/api/demonstrate/api'
import * as echarts from "echarts";
export default {
  name: "index",
  data() {
    return {
      tabPosition: 'top',
      xyClassList: [],
      xData7: [],
      yData7: [],
      xData8: [],
      yData8: [],
      xData9: [],
      yData9: [],
      xData10: [],
      yData10: [],
      xData11: [],
      yData11: [],
      xData12: [],
      yData12: [],
      xyPeoList: [],
      myChartStyle1: { float: "left", width: "550px", height: "350px" },//图表样式
      myChartStyle2: { float: "left", width: "590px", height: "250px" },//图表样式
    };
  },
  components: {
      Aside
    },
  mounted() {
    this.getData();
    // this.classEchart();
    // this.studentEchart();
  },
  methods: {
    getData() {
      let that = this
      api.getXYStudent()
        .then(function (res) {
          that.xyPeoList = res
          that.peopleEchart()
        })
        .catch(function (error) {
          console.log(error);
        })
      api.getXYClass()
        .then(function (res) {
          that.xyClassList = res.xyClass
          that.classEchart()
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    handleResize() {
      this.fullWidth = document.documentElement.clientWidth
      this.fullHeight = document.documentElement.clientHeight
    },
    //优秀学生
    peopleEchart() {
      let that = this
      const kemu = ['zong', 'deyu', 'zhiyu', 'tiyu', 'meiyu', 'laoyu']
      kemu.forEach((element, index) => {
        const option = {
          xAxis: {
            data: that.xyPeoList[element].name,
            axisLabel: {
              show: true,
              interval: 0,//使x轴文字显示全
              formatter: function (params) {
                var newParamsName = "";
                var paramsNameNumber = params.length;
                var provideNumber = 3; //一行显示几个字
                var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                if (paramsNameNumber > provideNumber) {
                  for (var p = 0; p < rowNumber; p++) {
                    var tempStr = "";
                    var start = p * provideNumber;
                    var end = start + provideNumber;
                    if (p == rowNumber - 1) {
                      tempStr = params.substring(start, paramsNameNumber);
                    } else {
                      tempStr = params.substring(start, end) + "\n";
                    }
                    newParamsName += tempStr;
                  }
                } else {
                  newParamsName = params;
                }
                return newParamsName;
              }
            }
          },
          yAxis: {},
          series: [
            {
              type: "line",
              data: that.xyPeoList[element].score,
              name: "分数", // legend属性
              label: {
                // 柱状图上方文本标签，默认展示数值信息
                show: true,
                position: "top"
              }
            },
          ],
          grid: {
            top: 25,
          },
        }
        const myChart = echarts.init(document.getElementById(`mychart${index + 7}`))
        myChart.setOption(option)
      })
    },
    // 优秀班级
    classEchart() {
      let that = this
      const mulColumnZZTData1 = {
        title: {
          text: that.xyClassList[0].class,
        },
        legend: {
          orient: 'vertical',
          right: '10%'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} 分' // <br/>换行
          //a:（系列名称） b:(数据名称) c:(数值) d:(饼图百分比)
        },
        series: [
          {
            type: 'pie',
            radius: ['0%', '60%'],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: that.xyClassList[0].deyu, name: '德育' },
              { value: that.xyClassList[0].zhiyu, name: '智育' },
              { value: that.xyClassList[0].tiyu, name: '体育' },
              { value: that.xyClassList[0].meiyu, name: '美育' },
              { value: that.xyClassList[0].laoyu, name: '劳育' },
            ]
          }
        ]
      };
      const myChart1 = echarts.init(document.querySelector("#mychart1"));
      myChart1.setOption(mulColumnZZTData1);
      const mulColumnZZTData2 = {
        title: {
          text: that.xyClassList[1].class,
        },
        legend: {
          orient: 'vertical',
          right: '10%'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} 分' // <br/>换行
          //a:（系列名称） b:(数据名称) c:(数值) d:(饼图百分比)
        },
        series: [
          {
            type: 'pie',
            radius: ['0%', '60%'],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: that.xyClassList[1].deyu, name: '德育' },
              { value: that.xyClassList[1].zhiyu, name: '智育' },
              { value: that.xyClassList[1].tiyu, name: '体育' },
              { value: that.xyClassList[1].meiyu, name: '美育' },
              { value: that.xyClassList[1].laoyu, name: '劳育' },
            ]
          }
        ]
      };
      const myChart2 = echarts.init(document.querySelector("#mychart2"));
      myChart2.setOption(mulColumnZZTData2);
      const mulColumnZZTData3 = {
        title: {
          text: that.xyClassList[2].class,
        },
        legend: {
          orient: 'vertical',
          right: '10%'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} 分' // <br/>换行
          //a:（系列名称） b:(数据名称) c:(数值) d:(饼图百分比)
        },
        series: [
          {
            type: 'pie',
            radius: ['0%', '60%'],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: that.xyClassList[2].deyu, name: '德育' },
              { value: that.xyClassList[2].zhiyu, name: '智育' },
              { value: that.xyClassList[2].tiyu, name: '体育' },
              { value: that.xyClassList[2].meiyu, name: '美育' },
              { value: that.xyClassList[2].laoyu, name: '劳育' },
            ]
          }
        ]
      };
      const myChart3 = echarts.init(document.querySelector("#mychart3"));
      myChart3.setOption(mulColumnZZTData3);
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart1.resize();
        myChart2.resize();
        myChart3.resize();
      });
    },
  },
  //两个生命周期中监听窗口大小变化
  //参数1：监听的事件
  //参数2：一个回调函数，用于赋值
  created() {
    window.addEventListener('resize', this.handleResize)
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.handleResize)
  }
}
</script>

<style scoped>
.el-tabs__content {
  height: calc(100vh - 110px);
  overflow-y: auto;
}
.el-tabs__header {
  padding: 0 0 0 55px !important;
}
.el-card__body {
  padding: 0 !important;
}
.el-row {
  margin-bottom: 10px;
}
</style>
