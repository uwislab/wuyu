<template>
  <div style="text-align: center">
    <div style="line-height: 100px; font-size: 30px">会议统计报告</div>
    <div id="all" style="height: 600px; width: 100%"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import meetingProto from '@/api/cultivationModule/meeting';
export default {
  name: "dataStatistics",
  data() {
    return {
      URL_ACTION: '',
      dataForm: {
        quarter: '',
        year: '',
      }
    };
  },
  methods: {
    getYear(year, data) {

    },
    getAll(data) {
      let myChart = echarts.init(document.getElementById('all'))
      const lg = ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级']
      let targetMap = new Map()
      let yearMap = new Set()
      data.forEach(item => {
        let yr = item.launchDate.split('-')[0]
        yearMap.add(yr)
      })
      let years = []
      yearMap.forEach(function(v, k) {
        years.push(k)
      })
      years.sort()

      let ds = Array(6).fill(0).map(() => Array(years.length).fill(0));
      for (let i = 0; i < ds.length; i++) {
        for (let j = 0; j < years.length; j++) {
          data.forEach(item => {
            if (item.target === lg[i] && item.launchDate.split('-')[0] === years[j]) {
              ds[i][j] += 1
            }
          })
        }
      }
      console.log(ds)
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        legend: {
          data: lg
        },
        xAxis: [
          {
            type: 'category',
            name: '年份',
            data: years,
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '次数',
            min: 0,
            max: 20,
            interval: 5,
            axisLabel: {
              formatter: '{value}'
            }
          },
        ],
        series: [
          {
            name: '一年级',
            type: 'bar',
            data: ds[0]
          },
          {
            name: '二年级',
            type: 'bar',
            data: ds[1]
          },
          {
            name: '三年级',
            type: 'bar',
            data: ds[2]
          },
          {
            name: '四年级',
            type: 'bar',
            data: ds[3]
          },
          {
            name: '五年级',
            type: 'bar',
            data: ds[4]
          },
          {
            name: '六年级',
            type: 'bar',
            data: ds[5]
          },
        ]
      };

      option && myChart.setOption(option);
    },
    getData() {
      meetingProto.list().then((res) => {
        this.getAll(res)
      })
    }
  },
  mounted() {
    this.getData()
  }
}
</script>
