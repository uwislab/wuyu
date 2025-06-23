<template>
  <div :style="{height:'400px',width:'48%'}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    setOptions({ retA, retB, retC, retD, xArr } = {}) {
      this.chart.setOption({
        title: {
          text: xArr[0].substring(0, 2)+'级'
        },
        xAxis: {
          data: xArr.map(i=>{return i.substring(2, 4)}),
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
        legend: {
          data: ['优秀率', '及格率', '平均优秀率', '平均及格率']
        },
        series: [{
          name: '优秀率', itemStyle: {
            normal: {
              color: 'red',
              lineStyle: {
                color: 'red',
                width: 2
              }
            }
          },
          smooth: true,
          type: 'line',
          data: retB,
          animationDuration: 2800,
          animationEasing: 'cubicInOut'
        },{
          name: '平均优秀率', itemStyle: {
            normal: {
              color: '#ffb700',
              lineStyle: {
                type: 'dashed',
                color: '#ffb700',
                width: 2
              }
            }
          },
          smooth: true,
          type: 'line',
          data: retD,
          animationDuration: 2800,
          animationEasing: 'cubicInOut'
        },{
          name: '平均及格率', itemStyle: {
            normal: {
              color: '#005cff',
              lineStyle: {
                type: 'dashed',
                color: '#005cff',
                width: 2
              }
            }
          },
          smooth: true,
          type: 'line',
          data: retC,
          animationDuration: 2800,
          animationEasing: 'cubicInOut'
        },
          {
            name: '及格率',
            smooth: true,
            type: 'bar',
            barWidth:40,
            itemStyle: {
              normal: {
                color: '#528fff',
                lineStyle: {
                  color: '#528fff',
                  width: 2
                },
                areaStyle: {
                  color: '#f3f8ff'
                }
              }
            },
            data: retA,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }]
      })
    }
  }
}
</script>
