<template>
  <div class="main-container">
    <!-- 成绩分布图表区域 -->
    <div class="chart-section">
      <h3>课程成绩分布统计</h3>

      <!-- 课程选择下拉框 -->
      <el-select v-model="selectedCourse" placeholder="请选择课程" @change="fetchScoreData">
        <el-option v-for="course in courses" :key="course" :label="course" :value="course"></el-option>
      </el-select>

      <!-- 考试序号选择下拉框 -->
      <el-select v-model="selectedTestNumber" placeholder="请选择考试序号" @change="fetchScoreData">
        <el-option v-for="testNumber in testNumbers" :key="testNumber" :label="testNumber"
          :value="testNumber"></el-option>
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
            <el-option v-for="course in courses" :key="course" :label="course" :value="course">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-select v-model="trendForm.studentName" placeholder="请选择学生" clearable>
            <el-option v-for="student in trendStudentOptions" :key="student" :label="student"
              :value="student"></el-option>
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

    <!-- 学生多科综合成绩雷达图 -->
    <div class="radar-section">
      <h3>学生多科综合成绩雷达图</h3>

      <!-- 学生选择下拉框 -->
      <el-select v-model="selectedStudentNum" placeholder="请选择学生" @change="fetchRadarData">
        <el-option v-for="student in studentOptions" :key="student.value" :label="student.label"
          :value="student.value"></el-option>
      </el-select>

      <!-- 新增考试序号选择下拉框 -->
      <el-select v-model="selectedRadarTestNumber" placeholder="请选择考试序号" @change="fetchRadarData">
        <el-option v-for="testNumber in testNumbers" :key="testNumber" :label="testNumber"
          :value="testNumber"></el-option>
      </el-select>

      <!-- 绘制图表的容器 -->
      <div id="radarChart" style="width: 100%; height: 400px;"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {
  getCourseScoreDistribution,
  getPersonalScoreTrend,
  getStudentList,                   // 新增
  getStudentMultiSubjectScores     // 新增
} from '@/api/courseScore';
export default {
  data() {
    return {

      selectedRadarTestNumber: null, // 新增当前选中的雷达图考试序号
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
      scoreTrendData: [],
      radarChart: null, // 雷达图实例
      selectedStudentNum: '', // 当前选中的学生学号
      studentOptions: [], // 学生下拉选项
      radarChartData: [], // 雷达图数据
      trendStudentOptions: [] // 学生下拉选项
    };
  },
  beforeDestroy() {
    if (this.chart) this.chart.dispose();
    if (this.trendChart) this.trendChart.dispose();
    if (this.radarChart) this.radarChart.dispose();

    // 移除 resize 监听器
    window.removeEventListener('resize', () => {
      if (this.chart) this.chart.resize();
      if (this.trendChart) this.trendChart.resize();
      if (this.radarChart) this.radarChart.resize();
    });
  },
  mounted() {
    this.fetchCoursesAndTestNumbers();
    this.fetchStudents(); // 新增
    this.initCharts();
    this.fetchTrendStudents();

    // 添加 resize 监听器
    window.addEventListener('resize', () => {
      if (this.chart) this.chart.resize();
      if (this.trendChart) this.trendChart.resize();
      if (this.radarChart) this.radarChart.resize(); // 新增雷达图监听
    });
  },
  methods: {
    // 初始化所有图表
    initCharts() {
      this.chart = echarts.init(document.getElementById('scoreChart'));
      this.trendChart = echarts.init(document.getElementById('scoreTrendChart'));
      this.radarChart = echarts.init(document.getElementById('radarChart')); // 新增
    },

    async fetchTrendStudents() {
      try {
        const response = await getStudentList(); // 使用已有接口
        this.trendStudentOptions = response.data.map(student =>
          `${student.studentName}`
        );
      } catch (error) {
        console.error("获取学生列表失败:", error);
      }
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



    // 获取学生列表
    async fetchStudents() {
      try {
        const response = await getStudentList(); // 使用封装好的接口
        if (response.code === 200) {
          this.studentOptions = response.data.map(student => ({
            value: student.studentNum,
            label: `${student.studentName}(${student.studentNum})`
          }));

          if (this.studentOptions.length > 0 && !this.selectedStudentNum) {
            this.selectedStudentNum = this.studentOptions[0].value;
            this.fetchRadarData();
          }
        }
      } catch (error) {
        console.error("获取学生列表失败:", error);
        this.$message.error("获取学生列表失败");
      }
    },


    // 获取学生综合成绩雷达图数据
    async fetchRadarData(studentNum = this.selectedStudentNum) {
      // 增加考试序号的非空判断
      if (!studentNum || this.selectedRadarTestNumber === null) {
        return;
      }

      try {
        const res = await getStudentMultiSubjectScores({
          studentNum: this.selectedStudentNum,
          testNumber: this.selectedRadarTestNumber // 新增考试序号参数
        });
        if (res.code === 200) {
          this.radarChartData = res.data.map(item => ({
            courseName: item.courseName,
            score: item.score
          }));
          this.updateRadarChart();
        }
      } catch (error) {
        console.error("获取学生多科成绩失败:", error);
        this.$message.error("获取学生多科成绩失败");
      }
    },

    updateRadarChart() {
      if (!this.radarChart || !this.radarChartData.length) return;

      const indicatorData = this.radarChartData.map(item => ({
        name: item.courseName,
        max: 100
      }));

      const seriesData = [{
        name: '学生成绩',
        value: this.radarChartData.map(item => item.score),
        areaStyle: { color: 'rgba(54, 162, 235, 0.5)' },
        lineStyle: { color: '#3eaaff' }
      }];

      const option = {
        // 在标题中显示考试序号
        title: {
          text: `${this.selectedStudentNum} - 第${this.selectedRadarTestNumber}次考试多科综合成绩雷达图`,
          left: 'center'
        },
        tooltip: {},
        radar: {
          shape: 'circle',
          indicator: indicatorData
        },
        series: [{
          type: 'radar',
          data: seriesData
        }]
      };

      this.radarChart.setOption(option, true);
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
            color: function (params) {
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

.chart-section,
.trend-section {
  margin-bottom: 40px;
  border: 1px solid #ebeef5;
  padding: 20px;
  border-radius: 8px;
}

.chart-section h3,
.trend-section h3 {
  margin-bottom: 20px;
}

.el-select {
  margin-right: 10px;
  margin-bottom: 20px;
}
</style>