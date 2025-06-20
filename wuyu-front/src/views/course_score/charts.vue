<template>
  <div class="chart-container">
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
</template>

<script>
import * as echarts from 'echarts';
import { getCourseScoreDistribution } from '@/api/courseScore';

export default {
  data() {
    return {
      selectedCourse: '',
      selectedTestNumber: null,
      courses: [],
      testNumbers: [],
      chart: null,
      scoreDistributionData: []
    };
  },
  mounted() {
    this.fetchCoursesAndTestNumbers();
    this.initChart();
  },
  methods: {
    // 初始化图表
    initChart() {
      this.chart = echarts.init(document.getElementById('scoreChart'));
    },
    // 获取课程和考试序号列表
    async fetchCoursesAndTestNumbers() {
      try {
        const response = await getCourseScoreDistribution({ action: 'getOptions' });
        this.courses = response.data.courses;
        this.testNumbers = response.data.testNumbers;

        if (this.courses.length > 0) {
          this.selectedCourse = this.courses[0];
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
    // 更新图表
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
        series: [
          {
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
          }
        ]
      };

      this.chart.setOption(option);
    }
  }
};
</script>

<style scoped>
/* 可以添加自定义样式 */
.chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.chart-container select {
  margin: 10px;
}
</style>