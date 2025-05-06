<template>
  <div style="padding:20px;">
    <el-row :gutter="20">
      <el-col style="margin-bottom: 20px;" :span="24" v-for="(type, index) in typeList" :key="index">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>五育达成</span>
          </div>
          <div :id="'id' + index" style="width: 100%;height:700px;">
            {{ 'id' + index }}
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import api from '@/api/demonstrate/api'
import echarts from 'echarts'
export default {
  data() {
    return {
      typeList: ['1'],
      colorList: ['#1b6cd2', '#B5C334', '#FCCE10', 'rgba(144,26,194,0.74)', '#27727B', '#901AC2BC'],
      chart: null
    }
  },
  mounted() {
    const that = this;
    api.getWydc().then(e => {
      setTimeout(function () {
        for (var i = 0; i < that.typeList.length; i++) {
          that.initChart('id' + i, that.typeList[i], that.colorList[i], e);
        }
      }, 500);
    })
    // 监听窗口大小变化，重绘图表
    window.addEventListener('resize', () => this.chart.resize());
  },
  methods: {
    getGrade(i) {
      let res = ''
      switch(i) {
        case 1:
          res = "一年级"
          break
        case 2:
          res = "二年级"
          break
        case 3:
          res = "三年级"
          break
        case 4:
          res = "四年级"
          break
        case 5:
          res = "五年级"
          break
        case 6:
          res = "六年级"
          break
        default:
          res = "Unknown"
      }
      return res
    },
    initChart(divId, index, myColor, myData) {
      this.chart = echarts.init(document.getElementById(divId))
      let list = [];
      for (let i = 1; i <= 6; i++) {
        let data = {
          value: myData[i],
          name: this.getGrade(i)
        };
        list.push(data);
      }
      const option = {
        // title: {
        //   text: '五育达成'
        // },
        legend: {
          data: myData.gradeList
        },
        tooltip: {
          show: true,
        },
        radar: {
          // shape: 'circle',
          indicator: [
            { name: '德', max: 100 },
            { name: '智', max: 100 },
            { name: '体', max: 100 },
            { name: '美', max: 100 },
            { name: '劳', max: 100 }
          ]
        },
        series: [
          {
            type: 'radar',
            emphasis: {
              label: {
                show: 'true',
                position: 'top'
              },
              lineStyle: {
                width: 4
              },
              areaStyle: {
              }
            },
            data: list,
            itemStyle: {
              normal: {
                label: {
                  show: true // 在折线拐点上显示数据
                }
              }
            }
          }
        ]
      };
      this.chart.setOption(option)
    }
  },
  // 在生命周期末销毁图表，防止内存泄漏
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
}
</script>
