<template>
  <div class="grade-score-chart">
    <!-- 年份选择器 -->
    <el-select v-model="selectedYear" placeholder="请选择年份" @change="handleYearChange">
      <el-option v-for="year in availableYears" :key="year" :value="year" :label="`${year}年`"></el-option>
    </el-select>
    <!-- 班级选择器-->
    <el-select v-model="selectedClassName" placeholder="请选择班级" @change="handleClassChange" v-if="showClassSelector">
      <el-option v-for="className in classNames" :key="className" :value="className" :label="className"></el-option>
      <el-option key="all" value="all" label="全年级"></el-option>
    </el-select>
    <!-- 查询按钮 -->
    <el-button @click="fetchData" type="primary">查询</el-button>
    <!-- 图表和错误信息展示区域 -->
    <el-row :gutter="10" class="mt-20">
      <el-col :span="18">
        <el-card shadow="hover">
          <div class="chart-container" id="lineChart"></div>
        </el-card>
      </el-col>
      <el-col :span="6" v-if="errorMessage">
        <el-alert title="数据加载失败" type="error" :description="errorMessage" show-icon class="alert-container"></el-alert>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { gradeScore, gradeclassScore } from "@/api/fuScale";

export default {
  name: "GradeScoreChart",
  data() {
    return {
      selectedYear: null,
      selectedClassName: null,
      availableYears: [2020, 2021, 2022],
      classNames: ['一班', '二班', '三班'],
      showClassSelector: false,
      scoreData: [],
      chart: null,
      errorMessage: '',
      xAxisLabels: [],
      scoreDimensions: [
        { key:'moralityScore', name: '德育', color: '#007BFF' },
        { key: 'intelligenceScore', name: '智育', color: '#28A745' },
        { key: 'physicalScore', name: '体育', color: '#FFC107' },
        { key: 'aestheticScore', name: '美育', color: '#DC3545' },
        { key: 'labourScore', name: '劳育', color: '#17A2B8' }
      ],
      gradeNameMap: {
        1: '一年级',
        2: '二年级',
        3: '三年级',
        4: '四年级',
        5: '五年级',
        6: '六年级'
      }
    };
  },
  mounted() {
    this.initStyles();
  },
  methods: {
    initStyles() {
      const style = document.createElement('style');
      style.textContent = `
       .grade-score-chart { padding: 20px; }
       .mt-20 { margin-top: 20px; }
       .chart-container { width: 100%; height: 600px; }
       .alert-container { margin-top: 30px; }
       .el-select-dropdown .el-select-dropdown__item[data-value="all"] { font-weight: bold; color: '#1890ff'; }
      `;
      document.head.appendChild(style);
    },
    handleYearChange() {
      this.selectedClassName = null;
      this.showClassSelector = true;
    },
    handleClassChange() { },
    fetchData() {
      this.errorMessage = '';
      this.destroyChart();
      if (!this.selectedYear) {
        this.errorMessage = '请选择年份';
        return;
      }
      if (!this.selectedClassName) {
        this.errorMessage = '请选择班级或全年级';
        return;
      }
      if (this.selectedClassName === 'all') {
        this.fetchWholeGradeData();
      } else {
        this.fetchClassData();
      }
    },
    fetchWholeGradeData() {
      gradeScore(this.selectedYear)
       .then(res => {
          if (res.code === 200 && res.data && Array.isArray(res.data)) {
            this.processData(res.data, true);
            this.drawChart();
          } else {
            this.errorMessage = '获取全年级数据失败';
          }
        })
       .catch(err => {
          this.errorMessage = `网络错误: ${err.message}`;
        });
    },
    fetchClassData() {
      gradeclassScore(this.selectedYear, this.selectedClassName)
       .then(res => {
          if (res.code === 200 && res.data && Array.isArray(res.data)) {
            this.processData(res.data, false);
            this.drawChart();
          } else {
            this.errorMessage = '获取班级数据失败';
          }
        })
       .catch(err => {
          this.errorMessage = `网络错误: ${err.message}`;
        });
    },
    processData(dataList, isWholeGrade) {
      if (!dataList || dataList.length === 0) {
        this.errorMessage = '数据为空，请检查接口返回';
        return;
      }

      const semesterMap = { '12': '上学期', '07': '下学期' };
      this.xAxisLabels = [];

      // 筛选学年数据（2020年包含202012和202107）
      const filteredData = dataList.filter(item => {
        const dataStr = item.data.toString();
        const year = parseInt(dataStr.substring(0, 4));
        const month = dataStr.substring(4);
        return (month === '12' && year === this.selectedYear) ||
               (month === '07' && year === this.selectedYear + 1);
      });

      // 班级筛选
      if (!isWholeGrade && this.selectedClassName!== 'all') {
        const filteredByClass = filteredData.filter(item => item.className === this.selectedClassName);
        if (filteredByClass.length === 0) {
          this.errorMessage = `未找到${this.selectedYear}年${this.selectedClassName}的成绩`;
          return;
        }
      }

      // 按年级和学期排序
      const sortedData = filteredData.sort((a, b) => {
        if (a.gradeId!== b.gradeId) return a.gradeId - b.gradeId;
        return a.data - b.data;
      });

      // 生成图表数据（包含德育）
      this.scoreData = this.scoreDimensions.map(dim => ({
        name: dim.name,
        type: 'line',
        data: sortedData.map(item => item[dim.key] || null),
        symbol: 'circle',
        symbolSize: 8,
        lineStyle: { width: 2.5, type:'solid', color: dim.color },
        itemStyle: { color: dim.color },
        connectNulls: true // 连接空值确保曲线连续
      }));

      // 生成横坐标标签
      this.xAxisLabels = sortedData.map(item => {
        const dataStr = item.data.toString();
        const year = dataStr.substring(0, 4);
        const semesterCode = dataStr.substring(4);
        const semester = semesterMap[semesterCode] || '未知学期';
        const gradeName = this.gradeNameMap[item.gradeId] || `年级${item.gradeId}`;

        if (isWholeGrade) {
          return `${gradeName}${semester}`;
        } else {
          return `${gradeName}${this.selectedClassName}${semester}`;
        }
      });

      // 检查数据
      if (sortedData.length === 0) {
        this.errorMessage = `未找到${this.selectedYear}年${this.selectedClassName}的成绩`;
      }
    },
    drawChart() {
      if (this.errorMessage ||!this.scoreData.length) return;

      // 计算Y轴范围
      const allScores = this.scoreData.flatMap(series =>
        series.data.filter(score => score!== null)
      );
      const minScore = allScores.length? Math.min(...allScores) - 5 : 70;
      let maxScore = allScores.length? Math.max(...allScores) + 5 : 95;
      if (maxScore > 100) {
        maxScore = 100;
      }

      const option = {
        title: {
          text: this.selectedClassName === 'all'
           ? `${this.selectedYear}年全年级五育成绩趋势`
            : `${this.selectedYear}年${this.selectedClassName}五育成绩趋势`,
          left: 'center',
          textStyle: { fontWeight: 'bold', fontSize: 18 },
          top: 10
        },
        tooltip: {
          trigger: 'axis',
          formatter: (params) => params.map(p => `${p.seriesName}: ${p.value}分`).join('<br>')
        },
        legend: {
          top: 40,
          type:'scroll',
          orient: 'horizontal',
          left: 'center',
          textStyle: { fontSize: 14, color: '#333' },
          data: this.scoreData.map(s => s.name)
        },
        xAxis: {
          type: 'category',
          data: this.xAxisLabels,
          axisTick: { alignWithLabel: true },
          axisLine: { lineStyle: { color: '#666' } },
          axisLabel: { interval: 0, rotate: 45, fontSize: 12 }
        },
        yAxis: {
          type: 'value',
          min: minScore,
          max: maxScore,
          axisLabel: { formatter: '{value}分', fontSize: 12 },
          splitLine: { lineStyle: { color: '#E0E0E0' } }
        },
        series: this.scoreData,
        grid: {
          left: '3%',
          right: '4%',
          bottom: '15%',
          top: 70,
          containLabel: true
        }
      };

      const chartDom = document.getElementById('lineChart');
      this.chart = echarts.init(chartDom);
      this.chart.setOption(option);
      window.addEventListener('resize', () => this.chart.resize());
    },
    destroyChart() {
      if (this.chart) {
        this.chart.dispose();
        this.chart = null;
      }
    }
  },
  beforeDestroy() {
    this.destroyChart();
  }
};
</script>

<style scoped>
/* 组件私有样式 */
.grade-score-chart {
  padding: 30px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
}

/* 选择器样式调整 */
.el-select {
  margin-right: 15px;
  width: 150px;
  border-radius: 5px;
}

/* 查询按钮样式调整 */
.el-button {
  border-radius: 5px;
  padding: 8px 16px;
  font-size: 14px;
  background-color: #1890ff;
  border-color: #1890ff;
  transition: all 0.3s ease;
}

.el-button:hover {
  background-color: #096dd9;
  border-color: #096dd9;
}

/* 卡片样式调整 */
.el-card {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  border-radius: 8px;
}

/* 图表容器样式调整 */
.chart-container {
  border-radius: 8px;
  overflow: hidden;
  height: 750px;
}

/* 错误提示样式调整 */
.alert-container {
  margin-top: 30px;
  padding: 15px;
  border-radius: 8px;
}
</style>