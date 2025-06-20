<template>
  <div id="app">
    <div class="chart-header">
      <p>男老师人数: {{ maleTeacherCount }}</p>
      <p>女老师人数: {{ femaleTeacherCount }}</p>
      <p>男学生人数: {{ maleStudentCount }}</p>
      <p>女学生人数: {{ femaleStudentCount }}</p>
    </div>
    <div id="chart-container">
      <div id="pie-chart"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      chartData: {
        "初级教师": 11,
        "中级教师": 8,
        "高级教师": 14,
        "特级教师": 12
      },
      maleTeacherCount: 40,
      femaleTeacherCount: 8,
      maleStudentCount: 276,
      femaleStudentCount: 244
    };
  },
  mounted() {
    this.drawPieChart();
  },
  methods: {
    drawPieChart() {
      const chartDom = document.getElementById('pie-chart');
      const myChart = echarts.init(chartDom);
      // 获取数据的名称列表（对应饼状图的各部分名称）
      const categoryList = Object.keys(this.chartData);
      // 获取数据的数值列表（对应饼状图各部分占比的数据依据）
      const valueList = Object.values(this.chartData);
      const data = categoryList.map((category, index) => ({
        name: category,
        value: valueList[index]
      }));

      const option = {
        title: {
          text: '示例数据分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '数据分类',
            type: 'pie',
            radius: '50%',
            data: data,
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

      myChart.setOption(option);
    }
  }
};
</script>

<style scoped>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 20px;
  background-color: #f4f4f4;
}

h1 {
  text-align: center;
  color: #333;
}

.chart-header {
  text-align: left;
  margin-bottom: 10px;
  padding-left: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  width: 600px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.chart-header p {
  margin: 5px 0;
  font-size: 18px;
  text-align: center;
}

#chart-container {
  background-color: white;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  width: 600px;
  height: 400px;
  margin: 0 auto;
}

#pie-chart {
  width: 100%;
  height: 100%;
}
</style>
