<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>
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
</el-container>
  </el-container>

</template>
<script>
import Aside from "@/views/teacher_workspace/Aside"
import api from '@/api/demonstrate/api'
import echarts from 'echarts'
export default {
  data() {
    return {
      typeList: ['1'],
      colorList: ['#1b6cd2', '#B5C334', '#FCCE10', 'rgba(144,26,194,0.74)', '#27727B', '#901AC2BC']
    }
  },
  components: {
      Aside
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
  },
  methods: {
    initChart(divId, index, myColor, myData) {
      const that = this;
      const myChart = echarts.init(document.getElementById(divId))
      var list = [];
      for (var i = 0; i < myData.gradeList.length; i++) {
        var data = {
          value: myData[myData.gradeList[i]],
          name: myData.gradeList[i]
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
            { name: '德', max: 100, min: 50 },
            { name: '智', max: 100, min: 50 },
            { name: '体', max: 100, min: 50 },
            { name: '美', max: 100, min: 50 },
            { name: '劳', max: 100, min: 50 }
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
      myChart.setOption(option)
    }
  }
}
</script>
