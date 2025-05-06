<template>
  <div class="maindiv clearfix">
    <div class="contentbox clearfix">
      <!--  体育评价量表的echart饼图  -->
      <div id="left">
        <div id="left1"></div>
        <div id="left2"></div>
        <div id="left3"></div>
      </div>
      <div id="main"></div>
      <div id="right">
        <div id="right1"></div>
        <div id="right2"></div>
        <div id="right3"></div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import * as echarts from 'echarts';
import FileSaver from 'file-saver'
import * as XLSX from 'xlsx'
import re_htmlToPdf from '@/utils/re_htmlToPdf'
export default{
  name:'FiveupChart',
  data(){
    return{
      score:[[]],
      grade:[[]],
      scorecopy:[],
      Analyse:'',
      input:'',
      scoreList:{
        gradeOne:{
          classOne:[],
          classTwo:[],
          classThree:[],
        },
        gradeTwo:{
          classOne:[],
          classTwo:[],
          classThree:[],
        },
        gradeThree:{
          classOne:[],
          classTwo:[],
          classThree:[],
        },
        gradeFour:{
          classOne:[],
          classTwo:[],
          classThree:[],
        },
        gradeFive:{
          classOne:[],
          classTwo:[],
          classThree:[],
        },
        gradeSix:{
          classOne:[],
          classTwo:[],
          classThree:[],
        },
      }
    }
  },
  methods: {
    // 中央EChart图表加载函数
    chartInit() {
      let chartDom = document.getElementById('main');
      let option = {
        legend: {},
        tooltip: {
          trigger: 'axis',
          showContent: false
        },
        dataset: {
          source: this.grade
        },
        xAxis: {type: 'category'},
        yAxis: {gridIndex: 0},
        grid: {top: '55%'},
        series: [
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: {focus: 'series'}
          },
          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '25%'],
            emphasis: {
              focus: 'self'
            },
            label: {
              formatter: '{b}: {@2012} ({d}%)'
            },
            encode: {
              itemName: 'product',
              value: '2012',
              tooltip: '2012'
            }
          }
        ]
      };
      let myChart = echarts.init(chartDom);
      myChart.on('updateAxisPointer', function (event) {
        const xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          const dimension = xAxisInfo.value + 1;
          myChart.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      myChart.on('click', function (param) {
        console.log(param);
      })
      myChart.setOption(option);
    },
    // 一年级EChart图表加载函数
    GOneChartInit() {
      let that = this;
      let chartDom = document.getElementById("left1");
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: '一年级五育成绩图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '一年级',
            type: 'pie',
            radius: '50%',
            data: that.score[1],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.on('click', function (param) {
        let gradeName = param.seriesName
        let itemName = param.name
        that.$router.push({name: 'GradeFuReport', params: {gradeName, itemName}})
      })
      option && myChart.setOption(option);
    },
    // 二年级EChart图表加载函数
    GTwoChartInit() {
      let that = this;
      let chartDom = document.getElementById("left2");
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: '二年级五育成绩图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '二年级',
            type: 'pie',
            radius: '50%',
            data: that.score[2],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.on('click', function (param) {
        let gradeName = param.seriesName
        let itemName = param.name
        that.$router.push({name: 'GradeFuReport', params: {gradeName, itemName}})
      })
      option && myChart.setOption(option);
    },
    // 三年级EChart图表加载函数
    GThreeChartInit() {
      let that = this;
      let chartDom = document.getElementById("left3");
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: '三年级五育成绩图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '三年级',
            type: 'pie',
            radius: '50%',
            data: that.score[3],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.on('click', function (param) {
        let gradeName = param.seriesName
        let itemName = param.name
        that.$router.push({name: 'GradeFuReport', params: {gradeName, itemName}})
      })
      option && myChart.setOption(option);
    },
    // 四年级EChart图表加载函数
    GFourChartInit() {
      let that = this;
      let chartDom = document.getElementById("right1");
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: '四年级五育成绩图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '四年级',
            type: 'pie',
            radius: '50%',
            data: that.score[4],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.on('click', function (param) {
        let gradeName = param.seriesName
        let itemName = param.name
        that.$router.push({name: 'GradeFuReport', params: {gradeName, itemName}})
      })
      option && myChart.setOption(option);
    },
    // 五年级EChart图表加载函数
    GFiveChartInit() {
      let that = this;
      let chartDom = document.getElementById("right2");
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: '五年级五育成绩图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '五年级',
            type: 'pie',
            radius: '50%',
            data: that.score[5],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.on('click', function (param) {
        let gradeName = param.seriesName
        let itemName = param.name
        that.$router.push({name: 'GradeFuReport', params: {gradeName, itemName}})
      })
      option && myChart.setOption(option);
    },
    // 六年级EChart图表加载函数
    GSixChartInit() {
      let that = this;
      let chartDom = document.getElementById("right3");
      let myChart = echarts.init(chartDom);
      let option = {
        title: {
          text: '六年级五育成绩图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '六年级',
            type: 'pie',
            radius: '50%',
            data: that.score[6],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.on('click', function (param) {
        let gradeName = param.seriesName
        let itemName = param.name
        that.$router.push({name: 'GradeFuReport', params: {gradeName, itemName}})
      })
      option && myChart.setOption(option);
    },



  },
  mounted(){
    for (let i=1;i<8;i++) {

      axios.get("http://36.111.68.174:33380/diagnose/getGradeScoreByGrade", {
        params: {
          grade: i
        }
      }).then(
        response => {
          this.score[i] = response.data;
          switch (i) {
            case 1: {
              this.GOneChartInit();
            }
            case 2: {
              this.GTwoChartInit();
            }
            case 3: {
              this.GThreeChartInit();
            }
            case 4: {
              this.GFourChartInit();
            }
            case 5: {
              this.GFiveChartInit();
            }
            case 6: {
              this.GSixChartInit();
            }
            case 7:{
              this.grade = [
                ['product', '德育', '智育', '体育', '美育', '劳育']
              ];
              console.log(this.grade)
              for (let i = 1; i < 7; i++) {
                let gradeName = `一年级`;
                if (i === 2) gradeName = '二年级';
                if (i === 3) gradeName = '三年级';
                if (i === 4) gradeName = '四年级';
                if (i === 5) gradeName = '五年级';
                if (i === 6) gradeName = '六年级';

                let row = [
                  gradeName,
                  this.score[i][0].value ,
                  this.score[i][1].value ,
                  this.score[i][2].value ,
                  this.score[i][3].value ,
                  this.score[i][4].value
                ];

                this.grade.push(row);
              }
              this.chartInit()
            }
          }
        },
        error => {
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '加载失败',
          });
        }
      );


    }
  }


}
</script>
<style  scoped>
#left {
  width: 25%;
  height: 100%;
}
#left1 {
  width: 100%;
  height: 33%;
}
#left2 {
  width: 100%;
  height: 33%;
}
#left3 {
  width: 100%;
  height: 33%;
}
#main {
  width: 50%;
  height: 720px;
}
#right {
  width: 25%;
  height: 100%;
}
#right1 {
  width: 100%;
  height: 33%;
}
#right2 {
  width: 100%;
  height: 33%;
}
#right3 {
  width: 100%;
  height: 33%;
}

.clearfix:before,
.clearfix:after{
  display:table;
  clear:both;
  content:'';
}
.maindiv{
  margin: 2%;
  padding: 2%;
  width: 96%;
  height: 800px;
  border:3px solid #000000;
  border-radius: 20px;
}
.contentbox{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
}
#SportsAnalyse p{
  font-family: "微软雅黑","Microsoft YaHei","Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB",Arial,sans-serif;
  font-weight:bold;
  font-size: large;
  line-height: 1.5;
  margin-top:2px;
  margin-left:0px;
  text-indent: 40px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}
</style>
