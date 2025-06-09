<template>
    <div>
      <el-select v-model="selectedGrade" placeholder="请选择年级" @change="handleGradeChange">
        <el-option v-for="grade in grades" :key="grade" :label="grade" :value="grade"></el-option>
      </el-select>
      <el-select v-if="selectedGrade !== '全部年级'" v-model="selectedClass" placeholder="请选择班级" @change="handleClassChange">
        <el-option v-for="classitem in classes" :key="classitem" :label="classitem" :value="classitem"></el-option>
      </el-select>
      <el-row :gutter="10" style="height: calc(100vh - 200px); position: relative;">
        <el-col :span="18" style="height: 100%; position: relative;">
          <el-card style="height: 100%; position: absolute; top: 0; left: 0;">
            <div id="line" style="height: 100%; width: 100%;"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  import { getGradeScore } from "@/api/fuScale";
  
  export default {
    name: "gradeclass",
    data() {
      return {
        selectedGrade: '全部年级',
        selectedClass: '全部班级',
        grades: ['全部年级', '一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
        classes: ['全部班级', '一班', '二班', '三班'],
        scores: [], // 存储平均成绩数据
        chart: null
      }
    },
    mounted() {
      this.fetchGradeData();
      this.resizeChart();
      window.addEventListener('resize', this.resizeChart);
    },
    methods: {
      fetchGradeData() {
        this.destroyChart();
        const grade = this.selectedGrade === '全部年级' ? '' : this.selectedGrade;
        const classId = this.selectedClass === '全部班级' ? '' : this.selectedClass;
        getGradeScore(grade, classId).then(res => {
          if (res && res.code === 200 && res.data) {
            this.scores = res.data.map(item => item.avgScore); // 假设返回的数据格式正确
            this.drawLine();
          } else {
            console.error('Failed to fetch data:', res);
          }
        }).catch(error => {
          console.error('Error fetching data:', error);
        });
      },
      drawLine() {
        let lineDom = document.getElementById('line');
        let lineChart = echarts.init(lineDom);
        const option = {
          title: {
            text: `${this.selectedGrade} ${this.selectedClass === '全部班级' ? '' : this.selectedClass}的平均成绩`
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
            min: 70,
            max: 100
          },
          series: [{
            data: this.scores,
            type: 'line'
          }]
        };
        lineChart.setOption(option);
        this.chart = lineChart;
      },
      handleGradeChange() {
        this.selectedClass = '全部班级'; // 重置班级选择
        this.fetchGradeData();
      },
      handleClassChange() {
        this.fetchGradeData();
      },
      destroyChart() {
        if (this.chart) {
          this.chart.dispose();
        }
      },
      resizeChart() {
        if (this.chart) {
          this.chart.resize();
        }
      }
    },
    beforeDestroy() {
      this.destroyChart();
      window.removeEventListener('resize', this.resizeChart);
    }
  }
  </script>
  
  <style scoped>
  </style>