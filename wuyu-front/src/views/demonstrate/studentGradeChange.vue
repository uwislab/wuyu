<template>
  <div class="container">
    <!-- 学生搜索 和 学期选择 控件 -->
    <div class="controls">
      <el-autocomplete v-model="studentKeyword" :fetch-suggestions="querySearchAsync" placeholder="请输入学生姓名或学号"
        @select="handleStudentSelect" style="width: 200px;">
        <template slot-scope="{ item }">
          {{ item.studentName }} ({{ item.studentId }})
        </template>
      </el-autocomplete>

      <el-select v-model="selectedSemester" placeholder="请选择学期" style="margin-left: 10px;" :disabled="!selectedStudent">
        <el-option v-for="sem in semesterOptions" :key="sem" :label="sem" :value="sem" />
      </el-select>
    </div>

    <!-- 雷达图和曲线图 容器 -->
    <div class="charts-container">
      <div ref="radarChart" class="chart radar-chart"></div>
      <div ref="lineChart" class="chart line-chart"></div>
    </div>
  </div>
</template>

<script>
import { getStudentScore, getSemesterScore, getSearchStudent, getStudentSemesters } from '@/api/studentScore';
import * as echarts from 'echarts';

export default {
  data() {
    return {
      studentKeyword: '',
      selectedStudent: null,
      semesterOptions: [],
      selectedSemester: ''
    };
  },
  mounted() {
    // 初始化图表
    this.initRadarChart();
    this.initLineChart();
  },
  watch: {
    selectedSemester(newVal) {
      if (newVal && this.selectedStudent) {
        this.updateRadarChart();
      }
    },
    selectedStudent(newVal) {
      if (newVal) {
        this.updateLineChart();
        // 不在这里调用 updateRadarChart()
      }
    }
  },
  methods: {
    async querySearchAsync(queryString, cb) {
      if (!queryString.trim()) {
        cb([]);
        return;
      }
      try {
        const res = await getSearchStudent(queryString);
        cb(res);
      } catch (error) {
        console.error('搜索学生失败', error);
        cb([]);
      }
    },

    async handleStudentSelect(student) {
      this.selectedStudent = student;
      this.studentKeyword = `${student.studentName} (${student.studentId})`;

      try {
        const res = await getStudentSemesters(student.studentId);
        this.semesterOptions = res.map(item => item.semester);
        this.selectedSemester = this.semesterOptions.length > 0 ? this.semesterOptions[0] : '';
      } catch (error) {
        console.error('加载学期失败', error);
        this.semesterOptions = [];
        this.selectedSemester = '';
      }
    },

    initRadarChart() {
      const radarChart = echarts.init(this.$refs.radarChart);
      const option = {
        title: {
          text: '五育成绩',
          left: 'center',
        },
        tooltip: {},
        radar: {
          indicator: [
            { name: '德', max: 100 },
            { name: '智', max: 100 },
            { name: '体', max: 100 },
            { name: '美', max: 100 },
            { name: '劳', max: 100 }
          ]
        },
        series: [{
          name: '五育成绩',
          type: 'radar',
          data: []
        }]
      };
      radarChart.setOption(option);
    },

    initLineChart() {
      const lineChart = echarts.init(this.$refs.lineChart);
      const option = {
        title: {
          text: '每学期五育总成绩变化',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: '总分',
          type: 'line',
          data: [],
          markPoint: {
            data: [
              { type: 'max', name: '最大值' },
              { type: 'min', name: '最小值' }
            ]
          },
          markLine: {
            data: [
              { type: 'average', name: '平均值' }
            ]
          }
        }]
      };
      lineChart.setOption(option);
    },

    async updateRadarChart() {
      const studentId = this.selectedStudent.studentId;
      const studentName = this.selectedStudent.studentName;
      const semester = this.selectedSemester;

      try {
        const res = await getStudentScore(studentId, studentName, semester);

        const radarChart = echarts.getInstanceByDom(this.$refs.radarChart);
        const option = {
          title: {
            text: `五育成绩 - ${semester}`
          },
          series: [{
            data: [
              {
                value: [
                  res.moralityScore,
                  res.intelligenceScore,
                  res.physicalScore,
                  res.aestheticScore,
                  res.labourScore
                ],
                name: '成绩',
                areaStyle: {
                  color: 'rgba(255, 0, 0, 0.3)' // 可以调整颜色和透明度
                }
              }
            ]
          }]
        };
        radarChart.setOption(option);
      } catch (error) {
        console.error('加载雷达图数据失败', error);
        this.showEmptyChart(this.$refs.radarChart, "暂无数据");
      }
    },

    async updateLineChart() {
      const studentId = this.selectedStudent.studentId;
      const studentName = this.selectedStudent.studentName;

      try {
        const res = await getSemesterScore(studentId, studentName);

        const lineChart = echarts.getInstanceByDom(this.$refs.lineChart);
        const option = {
          xAxis: {
            data: res.map(item => item.semester)
          },
          series: [{
            data: res.map(item => item.totalScore),
            smooth: true,
          }]
        };
        lineChart.setOption(option);
      } catch (error) {
        console.error('加载折线图数据失败', error);
        this.showEmptyChart(this.$refs.lineChart, "暂无数据");
      }
    },

    showEmptyChart(elementRef, message) {
      const chartInstance = echarts.getInstanceByDom(elementRef);
      if (chartInstance) {
        chartInstance.setOption({
          series: [{
            data: []
          }],
          graphic: {
            type: 'text',
            left: 'center',
            top: 'middle',
            style: {
              text: message,
              fill: '#909399',
              fontSize: 14
            }
          }
        });
      }
    }
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
  width: 90%;
}

.controls {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.charts-container {
  display: flex;
  justify-content: space-between;
}

.chart {
  width: 48%;
  height: 550px;
  background-color: #fff;
  border: 1px solid #dcdcdc;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 20px;
}
</style>