<template>
  <div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column prop="itemContent"  label="一级指标内容"  width="300" style="text-align: center"> </el-table-column>
        <el-table-column prop="itemLevel" label="指标水平" width="300" style="text-align: center"></el-table-column>
        <el-table-column prop="itemScore" label="一级指标分数" width="300" style="text-align: center"> </el-table-column>
        <el-table-column prop="preItem" label="有无前置指标" width="300" style="text-align: center"></el-table-column>
      </el-table>
    </div>
    <div id="myChart" :style="{ width: '600px', height: '400px' }"></div>
  </div>
</template>

<script>
import {getRadarList} from "@/api/fuScale";
let dataMax = []
let radarNum = []
export default {
  name: 'Radar',
  data() {
    return {
      tableData: [],
    }
  },
  mounted() {
    getRadarList().then(res => {
      this.tableData = res;
      for (let i = 0; i < this.tableData.length; i++) {
        let asd = {}
        asd["name"] = this.tableData[i].itemContent
        asd["max"] = 100
        dataMax[i] = asd
        radarNum[i] = this.tableData[i].itemScore
      }
      this.drawLine()
      console.log(dataMax.length)
    })
  },
  methods: {
    drawLine() {
      // 基于准备好的dom，初始化echarts实例

      let myChart = this.$echarts.init(document.getElementById('myChart'))

      console.log(dataMax)

      console.log(dataMax.length)


      let option = {
        //配置维度的最大值
        radar: {
          name: {
            show: true,
            color: 'red',
          },
          //   雷达图的指示器，用来指定雷达图中的多个变量（维度）
          indicator: dataMax,
          shape: 'circle', //对雷达图形设置成一个圆形,可选 circle:圆形,polygon:多角形(默认)
        },
        series: [
          {
            type: 'radar',
            label: {
              show: true, //显示数值
            },
            areaStyle: {}, //每个雷达图形成一个阴影的面积
            data: [
              {
                name: '各个第一指标的分数',
                value: radarNum
              },
              {
                name: '年级平均',
                value: [33, 29, 24, 45, 37]
              }
            ],
          },
        ],
      }
      // 绘制图表
      myChart.setOption(option)
    },
  },
}
</script>

<style>
</style>
