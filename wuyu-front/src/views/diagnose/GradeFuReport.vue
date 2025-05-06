<template>
  <div class="maindiv clearfix">
    <div class="contentbox clearfix">
      <!--  体育评价量表的echart饼图  -->
      <div id="top">
        <div id="t_left"></div>
        <div id="t_middle"></div>
        <div id="t_right"></div>
      </div>
      <div id="bottom">
        <div id="b_left"></div>
        <div id="b_right"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "GradeFuReport",
  data() {
    return {

    }
  },
  methods: {
    classOneChartInit(gradeName, itemName) {
      let that = this;
      let chartDom = document.getElementById('t_left');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        series: [
          {
            name: '一班',
            type: 'gauge',
            radius: "75%",
            axisLine: {
              lineStyle: {
                width: 20,
                color: [
                  [0.3, '#67e0e3'],
                  [0.7, '#37a2da'],
                  [1, '#fd666d']
                ]
              }
            },
            pointer: {
              length: 120,
              width: 5,
              itemStyle: {
                color: 'inherit'
              }
            },
            axisTick: {
              distance: -30,
              length: 8,
              lineStyle: {
                color: '#fff',
                width: 2
              }
            },
            splitLine: {
              distance: -30,
              length: 30,
              lineStyle: {
                color: '#fff',
                width: 4
              }
            },
            axisLabel: {
              distance: -65,
              fontSize: 20
            },
            detail: {
              valueAnimation: true,
              formatter: '一班' + itemName + '成绩\n{value} 分',
              fontSize: 25
            },
            data: [
              {
                value: 80
              }
            ]
          }
        ]
      };
      myChart.on('click', function (param) {
        let clickedPie = param.seriesName;
        that.$router.push({name: 'ClassFuReport', params: {gradeName, itemName, clickedPie}})
      })
      option && myChart.setOption(option);
    },
    classTwoChartInit(gradeName, itemName) {
      let chartDom = document.getElementById('t_middle');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        series: [
          {
            name: '二班',
            type: 'gauge',
            radius: "75%",
            axisLine: {
              lineStyle: {
                width: 20,
                color: [
                  [0.3, '#67e0e3'],
                  [0.7, '#37a2da'],
                  [1, '#fd666d']
                ]
              }
            },
            pointer: {
              length: 120,
              width: 5,
              itemStyle: {
                color: 'inherit'
              }
            },
            axisTick: {
              distance: -30,
              length: 8,
              lineStyle: {
                color: '#fff',
                width: 2
              }
            },
            splitLine: {
              distance: -30,
              length: 30,
              lineStyle: {
                color: '#fff',
                width: 4
              }
            },
            axisLabel: {
              distance: -65,
              fontSize: 20
            },
            detail: {
              valueAnimation: true,
              formatter: '二班' + itemName + '成绩\n{value} 分',
              fontSize: 25
            },
            data: [
              {
                value: 80
              }
            ]
          }
        ]
      };

      option && myChart.setOption(option);
    },
    classThreeChartInit(gradeName, itemName) {
      let chartDom = document.getElementById('t_right');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        series: [
          {
            name: '三班',
            type: 'gauge',
            radius: "75%",
            axisLine: {
              lineStyle: {
                width: 20,
                color: [
                  [0.3, '#67e0e3'],
                  [0.7, '#37a2da'],
                  [1, '#fd666d']
                ]
              }
            },
            pointer: {
              length: 120,
              width: 5,
              itemStyle: {
                color: 'inherit'
              }
            },
            axisTick: {
              distance: -30,
              length: 8,
              lineStyle: {
                color: '#fff',
                width: 2
              }
            },
            splitLine: {
              distance: -30,
              length: 30,
              lineStyle: {
                color: '#fff',
                width: 4
              }
            },
            axisLabel: {
              distance: -65,
              fontSize: 20
            },
            detail: {
              valueAnimation: true,
              formatter: '三班' + itemName + '成绩\n{value} 分',
              fontSize: 25
            },
            data: [
              {
                value: 80
              }
            ]
          }
        ]
      };

      option && myChart.setOption(option);
    },
    lineChartInit(gradeName, itemName) {
      let chartDom = document.getElementById('b_left');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: gradeName + itemName + '成绩总览折线图'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['一班', '二班', '三班']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['1-3月', '4-6月', '7-9月', '10-12月']
        },
        yAxis: {
          type: 'value',
          min: 60,
          max: 100,
        },
        series: [
          {
            name: '一班',
            type: 'line',
            data: [79, 86, 83, 81]
          },
          {
            name: '二班',
            type: 'line',
            data: [86, 90, 85, 76]
          },
          {
            name: '三班',
            type: 'line',
            data: [83, 84, 85, 85]
          }
        ]
      };

      option && myChart.setOption(option);
    },
    barChartInit() {
      let chartDom = document.getElementById('b_right');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: ['一班', '二班', '三班']
        },
        series: [
          {
            data: [76, 83, 85],
            type: 'bar',
            label: {
              show: true,
              position: 'right',
              fontSize: 20,
            },
            itemStyle: {
              color: '#00AAAA',
            }
          }
        ]
      };
      myChart.on('click', function (param) {
        console.log(param);
        // let gradeName = param.seriesName;
        // let clickedPie = param.name;
        // console.log(gradeName + clickedPie);
        // that.$router.push('GradeFuReport');
      })
      option && myChart.setOption(option);
    }
  },
  mounted() {
    let gradeName = this.$route.params.gradeName;
    let itemName = this.$route.params.itemName;
    this.classOneChartInit(gradeName, itemName);
    this.classTwoChartInit(gradeName, itemName);
    this.classThreeChartInit(gradeName, itemName);
    this.lineChartInit(gradeName, itemName);
    this.barChartInit();
  }
}

</script>

<style scoped>
#top {
  width: 100%;
  height: 350px;
  margin-top: 20px;
}
#t_left {
  width: 31%;
  height: 100%;
  float: left;
  margin-left: 2%;
}
#t_middle {
  width: 31%;
  height: 100%;
  float: left;
  margin-left: 2%;
}
#t_right {
  width: 31%;
  height: 100%;
  float: left;
  margin-left: 2%;
}
#bottom {
  width: 100%;
  height: 450px;
  border: solid 1px #000000;
}
#b_left {
  width: 45%;
  height: 100%;
  float: left;
  margin-left: 3%;
}
#b_right {
  width: 45%;
  height: 100%;
  float: left;
  margin-left: 3%;
}
</style>
