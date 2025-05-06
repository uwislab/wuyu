<template>
  <div>
    <el-select v-model="selectedGrade" placeholder="请选择年级" @change="handleGradeChange">
      <el-option v-for="grade in grades" :key="grade" :value="grade"></el-option>
    </el-select>
    <el-row :gutter="10">
      <el-col :span="18">
        <el-card>
          <div style="width: 100%; height: 400px" id="line"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { getGradeScore } from "@/api/fuScale";

export default {
  name: "grade",
  data() {
    return {
      selectedGrade: '一年级',
      grades: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      asd: [], // 将asd移到组件数据中
      chart: null
    }
  },
  mounted() {
    this.fetchGradeData(this.selectedGrade);
  },
  methods: {
    fetchGradeData(grade) {
      this.destroyChart();
      getGradeScore(grade).then(res => {
        this.asd = res;
        this.drawLine();
      });
    },
    drawLine() {
      let lineDom = document.getElementById('line');
      let lineChart = echarts.init(lineDom);
      const option = {
        title: {
          text: this.selectedGrade
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: ['德', '智', '体', '美', '劳']
        },
        yAxis: {
          type: 'value',
          min: 73,
          max: 88
        },
        series: this.asd
      };
      lineChart.setOption(option);
      this.chart = lineChart;
    },
    handleGradeChange() {
      this.fetchGradeData(this.selectedGrade);
    },
    destroyChart() {
      if (this.chart) {
        this.chart.dispose();
      }
    }
  },
  beforeDestroy() {
    this.destroyChart();
  }
}

</script>

<style scoped></style>
