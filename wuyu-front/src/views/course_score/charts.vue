<template>
  <div class="main-container">
    <!-- 成绩分布图表区域 -->
    <div class="chart-section">
      <h3>课程成绩分布统计</h3>
      
      <!-- 课程选择下拉框 -->
      <el-select v-model="selectedCourse" placeholder="请选择课程" @change="fetchScoreData">
        <el-option 
          v-for="course in courses" 
          :key="course" 
          :label="course" 
          :value="course"
        ></el-option>
      </el-select>

      <!-- 考试序号选择下拉框 -->
      <el-select v-model="selectedTestNumber" placeholder="请选择考试序号" @change="fetchScoreData">
        <el-option 
          v-for="testNumber in testNumbers" 
          :key="testNumber" 
          :label="testNumber" 
          :value="testNumber"
        ></el-option>
      </el-select>

      <!-- 绘制图表的容器 -->
      <div id="scoreChart" style="width: 100%; height: 400px;"></div>
    </div>

    <!-- 成绩趋势图表区域 -->
    <div class="trend-section">
      <h3>个人成绩趋势分析</h3>
      
      <!-- 输入框 -->
      <el-form :inline="true" :model="trendForm" class="demo-form-inline">
        <el-form-item label="课程名称">
          <el-select v-model="trendForm.courseName" placeholder="请选择课程">
            <el-option
              v-for="course in courses"
              :key="course"
              :label="course"
              :value="course">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="trendForm.studentName" placeholder="请输入学生姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchScoreTrend">查询</el-button>
        </el-form-item>
      </el-form>

      <!-- 绘制图表的容器 -->
      <div id="scoreTrendChart" style="width: 100%; height: 400px;"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { getCourseScoreDistribution, getPersonalScoreTrend } from '@/api/courseScore';

export default {
  data() {
    return {
      // 成绩分布相关数据
      selectedCourse: '',
      selectedTestNumber: null,
      courses: [],
      testNumbers: [],
      chart: null,
      scoreDistributionData: [],
      
      // 成绩趋势相关数据
      trendChart: null,
      trendForm: {
        courseName: '',
        studentName: ''
      },
      scoreTrendData: []
    };
  },
  mounted() {
    this.fetchCoursesAndTestNumbers();
    this.initCharts();
  },
  methods: {
    // 初始化所有图表
    initCharts() {
      this.chart = echarts.init(document.getElementById('scoreChart'));
      this.trendChart = echarts.init(document.getElementById('scoreTrendChart'));
    },
    
    // 获取课程和考试序号列表
    async fetchCoursesAndTestNumbers() {
      try {
        const response = await getCourseScoreDistribution({ action: 'getOptions' });
        this.courses = response.data.courses;
        this.testNumbers = response.data.testNumbers;

        if (this.courses.length > 0) {
          this.selectedCourse = this.courses[0];
          this.trendForm.courseName = this.courses[0];
        }
        
        if (this.testNumbers.length > 0) {
          this.selectedTestNumber = this.testNumbers[0];
        }

        this.fetchScoreData();
      } catch (error) {
        console.error('获取课程和考试序号失败:', error);
      }
    },
    
    // 获取成绩分段数据
    async fetchScoreData() {
      if (!this.selectedCourse || this.selectedTestNumber === null) {
        return;
      }

      try {
        const response = await getCourseScoreDistribution({
          courseName: this.selectedCourse,
          testNumber: this.selectedTestNumber
        });
        this.scoreDistributionData = response.data;
        this.updateChart();
      } catch (error) {
        console.error('获取成绩分段数据失败:', error);
      }
    },
    
    // 更新成绩分布图表
    updateChart() {
      if (!this.chart || !this.scoreDistributionData.length) {
        return;
      }

      const option = {
        title: {
          text: `${this.selectedCourse} - 第${this.selectedTestNumber}次考试成绩分布`,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: this.scoreDistributionData.map(item => item.scoreRange)
        },
        yAxis: {
          type: 'value',
          name: '学生人数',
          nameLocation: 'end'
        },
        series: [{
          name: '学生人数',
          type: 'bar',
          data: this.scoreDistributionData.map(item => item.studentCount),
          itemStyle: {
            color: function(params) {
              const colors = ['#FF4500', '#FF8C00', '#FFD700', '#90EE90', '#32CD32'];
              return colors[params.dataIndex];
            }
          },
          label: {
            show: true,
            position: 'top',
            formatter: '{c}人'
          }
        }]
      };

      this.chart.setOption(option);
    },
    
    // 获取成绩趋势数据
    async fetchScoreTrend() {
      if (!this.trendForm.courseName || !this.trendForm.studentName) {
        return;
      }

      try {
        const response = await getPersonalScoreTrend({
          courseName: this.trendForm.courseName,
          studentName: this.trendForm.studentName
        });
        this.scoreTrendData = response.data;
        this.updateTrendChart();
      } catch (error) {
        console.error('获取成绩趋势数据失败:', error);
      }
    },
    
    // 更新成绩趋势图表
    updateTrendChart() {
      if (!this.trendChart || !this.scoreTrendData.length) {
        return;
      }

      const option = {
        title: {
          text: `${this.trendForm.courseName} - ${this.trendForm.studentName}成绩趋势`,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.scoreTrendData.map(item => `第${item.testNumber}次考试`)
        },
        yAxis: {
          type: 'value',
          name: '分数',
          min: 0,
          max: 100
        },
        series: [{
          name: '分数',
          type: 'line',
          data: this.scoreTrendData.map(item => item.score),
          smooth: true,
          itemStyle: {
            color: '#409EFF'
          },
          label: {
            show: true,
            position: 'top',
            formatter: '{c}'
          }
        }]
      };

      this.trendChart.setOption(option);
    }
  }
};
</script>

<style scoped>
.main-container {
  padding: 20px;
}

.chart-section, .trend-section {
  margin-bottom: 40px;
  border: 1px solid #ebeef5;
  padding: 20px;
  border-radius: 8px;
}

.chart-section h3, .trend-section h3 {
  margin-bottom: 20px;
}

.el-select {
  margin-right: 10px;
  margin-bottom: 20px;
}
</style>