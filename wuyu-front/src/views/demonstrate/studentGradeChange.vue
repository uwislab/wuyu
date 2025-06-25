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
import { getStudentScore, getSemesterScore, getSearchStudent, getStudentSemesters, getClassAndGradeScores } from '@/api/studentScore';
import * as echarts from 'echarts';

export default {
  data() {
    return {
      studentKeyword: '',
      selectedStudent: null,
      semesterOptions: [],
      selectedSemester: '',
      debounceTimer: null,
      semesterData: [] // 用于保存所有学期成绩数据
    };
  },
  mounted() {
    // 初始化图表
    this.initRadarChart();
    this.initLineChart();
  },
  watch: {
    selectedSemester(newVal) {
      if (newVal && this.selectedStudent && this.semesterData.length > 0) {
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
    debounce(fn, delay = 300) {
      clearTimeout(this._debounceTimer);
      this._debounceTimer = setTimeout(() => {
        fn();
      }, delay);
    },
    async querySearchAsync(queryString, cb) {
      if (!queryString.trim()) {
        cb([]);
        return;
      }

      this.debounce(async () => {
        try {
          const res = await getSearchStudent(queryString);
          cb(res);
        } catch (error) {
          console.error('搜索学生失败', error);
          cb([]);
        }
      }, 300); // 延迟时间可配置
    },
    async handleStudentSelect(student) {
      this.selectedStudent = student;
      this.studentKeyword = `${student.studentName} (${student.studentId})`;

      try {
        const res = await getStudentSemesters(student.studentId);
        this.semesterOptions = res.map(item => item.semester);

        if (this.semesterOptions.length > 0) {
          this.selectedSemester = this.semesterOptions[0];
          // 👇 学生一选中就更新雷达图
          await this.updateLineChart(); // 先获取 semesterData
          // console.log('semesterData:', this.semesterData);
          this.updateRadarChart();     // 然后更新雷达图
        } else {
          this.selectedSemester = '';
          this.showEmptyChart(this.$refs.radarChart, "暂无学期数据");
        }
      } catch (error) {
        console.error('加载学期失败', error);
        this.semesterOptions = [];
        this.selectedSemester = '';
        this.showEmptyChart(this.$refs.radarChart, "加载学期失败");
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
      if (this.chartDestroyed) {
        console.warn('组件已销毁，跳过雷达图更新');
        return;
      }

      const semester = this.selectedSemester;

      const selectedData = this.semesterData.find(item => item.semester === semester);
      if (!selectedData) {
        this.showEmptyChart(this.$refs.radarChart, "暂无数据");
        return;
      }

      const studentId = this.selectedStudent.studentId;
      try {
        const classAndGradeData = await getClassAndGradeScores(studentId, semester);
        // console.log('班级和年级平均分数据:', classAndGradeData);

        const radarChart = echarts.getInstanceByDom(this.$refs.radarChart);
        if (!radarChart) {
          console.error('雷达图实例不存在');
          return;
        }

        const option = {
          legend: {
            show: true,
            data: ['学生', '班级平均', '年级平均'],
            bottom: 10
          },
          title: {
            text: `五育成绩 - ${semester}`,
            x: 'center'
          },
          tooltip: {
          },
          radar: {
            indicator: [
              { name: '德', max: 100 },
              { name: '智', max: 100 },
              { name: '体', max: 100 },
              { name: '美', max: 100 },
              { name: '劳', max: 100 }
            ],
            splitArea: {
              show: true,
              areaStyle: {
                color: ['rgba(255,255,255,0.3)', 'rgba(0,0,0,0.1)']
              }
            },
            axisLine: {
              lineStyle: {
                color: '#ccc'
                // color: '#707070'
              }
            }
          },
          series: [{
            name: '五育成绩对比',
            type: 'radar',
            data: [
              {
                value: [
                  selectedData.deyu || 0,
                  selectedData.zhiyu || 0,
                  selectedData.tiyu || 0,
                  selectedData.meiyu || 0,
                  selectedData.laoyu || 0
                ],
                name: '学生',
                areaStyle: { color: 'rgba(255, 0, 0, 0.3)' }
              },
              {
                value: [
                  classAndGradeData.classDeYu || 0,
                  classAndGradeData.classZhiYu || 0,
                  classAndGradeData.classTiYu || 0,
                  classAndGradeData.classMeiYu || 0,
                  classAndGradeData.classLaoYu || 0
                ],
                name: '班级平均',
                areaStyle: { color: 'rgba(30, 144, 255, 0.2)' }
              },
              {
                value: [
                  classAndGradeData.gradeDeYu || 0,
                  classAndGradeData.gradeZhiYu || 0,
                  classAndGradeData.gradeTiYu || 0,
                  classAndGradeData.gradeMeiYu || 0,
                  classAndGradeData.gradeLaoYu || 0
                ],
                name: '年级平均',
                areaStyle: { color: 'rgba(50, 205, 50, 0.2)' }
              }
            ]
          }]
        };
        radarChart.setOption(option, true);
      } catch (error) {
        console.error('加载班级/年级数据失败', error);
      }
    },
    async updateLineChart() {
      const studentId = this.selectedStudent.studentId;
      const studentName = this.selectedStudent.studentName;

      try {
        const res = await getSemesterScore(studentId, studentName);

        // 保存到 data 中供雷达图使用
        this.semesterData = res;

        const semesterNames = res.map(item => item.semester);

        const dataset = {
          source: [
            ['学期', '德育', '智育', '体育', '美育', '劳育', '总成绩'],
            ...res.map(item => [
              item.semester,
              item.deyu,
              item.zhiyu,
              item.tiyu,
              item.meiyu,
              item.laoyu,
              item.totalScore
            ])
          ]
        };

        const lineChart = echarts.getInstanceByDom(this.$refs.lineChart);
        const option = {
          title: {
            text: '每学期五育成绩变化',
            left: 'center'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['德育', '智育', '体育', '美育', '劳育', '总成绩'],
            top: '5%', // 调整图例顶部距离
            // right: '10%' // 将图例移到右侧
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: semesterNames
          },
          yAxis: {
            type: 'value'
          },
          dataset,
          color: ['#facc14', '#3f51b5', '#ff5252', '#4caf50', '#9c27b0', '#e64a19'],
          series: [
            { name: '德育', type: 'line', smooth: true, symbol: 'circle', symbolSize: 6 },
            { name: '智育', type: 'line', smooth: true, symbol: 'circle', symbolSize: 6 },
            { name: '体育', type: 'line', smooth: true, symbol: 'circle', symbolSize: 6 },
            { name: '美育', type: 'line', smooth: true, symbol: 'circle', symbolSize: 6 },
            { name: '劳育', type: 'line', smooth: true, symbol: 'circle', symbolSize: 6 },
            { name: '总成绩', type: 'line', smooth: true, symbol: 'circle', symbolSize: 8, lineStyle: { width: 2.5 } }
          ],
          graphic: null
        };
        lineChart.setOption(option, true); // 第二个参数 true 表示合并配置，避免清空原有内容
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