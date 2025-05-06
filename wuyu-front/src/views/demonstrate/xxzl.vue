<!--<template>-->
<!--  <div style="padding:20px;">-->
<!--    <el-row :gutter="20" style="margin-bottom: 20px;">-->
<!--      <el-col :span="24">-->
<!--        <el-card class="box-card">-->
<!--          <div slot="header" class="clearfix">-->
<!--            <span>学校总览</span>-->
<!--          </div>-->
<!--          <div>-->
<!--            <div>-->
<!--              <el-select v-model="select" multiple placeholder="请选择" style="width: 100%" @change="myChange">-->
<!--                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </div>-->
<!--            <div id="id0" style="width: 100%;height:300px;margin-top: 20px;"></div>-->
<!--          </div>-->
<!--        </el-card>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--  </div>-->
<!--</template>-->
<!--<script>-->
<!--import api from '@/api/demonstrate/api'-->
<!--import echarts from 'echarts'-->
<!--export default {-->
<!--  data() {-->
<!--    return {-->
<!--      options: [{-->
<!--        value: '一年级',-->
<!--        label: '一年级'-->
<!--      }, {-->
<!--        value: '二年级',-->
<!--        label: '二年级'-->
<!--      }, {-->
<!--        value: '三年级',-->
<!--        label: '三年级'-->
<!--      }, {-->
<!--        value: '四年级',-->
<!--        label: '四年级'-->
<!--      }, {-->
<!--        value: '五年级',-->
<!--        label: '五年级'-->
<!--      }, {-->
<!--        value: '六年级',-->
<!--        label: '六年级'-->
<!--      }],-->
<!--      select: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],-->
<!--      type: "德智体美劳",-->
<!--      typeList: ['德', '智', '体', '美', '劳'],-->
<!--      idList: ['id0', 'id1', 'id2', 'id3', 'id4'],-->
<!--      colorList: ['#1b6cd2', '#B5C334', '#FCCE10', '#E87C25', '#27727B'],-->
<!--      dataList: [[], [], [], [], []]-->
<!--    }-->
<!--  },-->
<!--  mounted() {-->
<!--    api.getXxzl().then(e => {-->
<!--      if (e.xAxisData) {-->
<!--        var xAxisData = e.xAxisData;-->
<!--        this.dataList[0] = {-->
<!--          name: '德育',-->
<!--          data: e.deyuData,-->
<!--          type: 'bar',-->
<!--          label: {-->
<!--            show: true,-->
<!--            position: 'outside'-->
<!--          }-->
<!--        }-->
<!--        this.dataList[1] = {-->
<!--          name: '智育',-->
<!--          data: e.zhiyuData,-->
<!--          type: 'bar',-->
<!--          label: {-->
<!--            show: true,-->
<!--            position: 'outside'-->
<!--          }-->
<!--        }-->
<!--        this.dataList[2] = {-->
<!--          name: '体育',-->
<!--          data: e.tiyuData,-->
<!--          type: 'bar',-->
<!--          label: {-->
<!--            show: true,-->
<!--            position: 'outside'-->
<!--          }-->
<!--        }-->
<!--        this.dataList[3] = {-->
<!--          name: '美育',-->
<!--          data: e.meiyuData,-->
<!--          type: 'bar',-->
<!--          label: {-->
<!--            show: true,-->
<!--            position: 'outside'-->
<!--          }-->
<!--        }-->
<!--        this.dataList[4] = {-->
<!--          name: '劳育',-->
<!--          data: e.laoyuData,-->
<!--          type: 'bar',-->
<!--          label: {-->
<!--            show: true,-->
<!--            position: 'outside'-->
<!--          }-->
<!--        }-->
<!--        this.initChart("id0", "", this.colorList[0], xAxisData, this.dataList);-->
<!--      }-->
<!--    })-->
<!--  },-->
<!--  methods: {-->
<!--    myChange(value) {-->
<!--      api.getXxzl({ grade: value.join(",") }).then(e => {-->
<!--        if (e.xAxisData) {-->
<!--          var xAxisData = e.xAxisData;-->
<!--          this.dataList[0] = {-->
<!--            name: '德育',-->
<!--            data: e.deyuData,-->
<!--            type: 'bar',-->
<!--            label: {-->
<!--              show: true,-->
<!--              position: 'outside'-->
<!--            },-->
<!--          }-->
<!--          this.dataList[1] = {-->
<!--            name: '智育',-->
<!--            data: e.zhiyuData,-->
<!--            type: 'bar',-->
<!--            label: {-->
<!--              show: true,-->
<!--              position: 'outside'-->
<!--            }-->
<!--          }-->
<!--          this.dataList[2] = {-->
<!--            name: '体育',-->
<!--            data: e.tiyuData,-->
<!--            type: 'bar',-->
<!--            label: {-->
<!--              show: true,-->
<!--              position: 'outside'-->
<!--            }-->
<!--          }-->
<!--          this.dataList[3] = {-->
<!--            name: '美育',-->
<!--            data: e.meiyuData,-->
<!--            type: 'bar',-->
<!--            label: {-->
<!--              show: true,-->
<!--              position: 'outside'-->
<!--            }-->
<!--          }-->
<!--          this.dataList[4] = {-->
<!--            name: '劳育',-->
<!--            data: e.laoyuData,-->
<!--            type: 'bar',-->
<!--            label: {-->
<!--              show: true,-->
<!--              position: 'outside'-->
<!--            }-->
<!--          }-->
<!--          this.initChart("id0", "", this.colorList[0], xAxisData, this.dataList);-->
<!--        }-->
<!--      })-->
<!--    },-->
<!--    initChart(divId, index, myColor, xData, data) {-->
<!--      const that = this;-->
<!--      const myChart = echarts.init(document.getElementById(divId))-->
<!--      const option = {-->
<!--        legend: {-->
<!--          data: ['德育', '智育', '体育', '美育', '劳育']-->
<!--        },-->
<!--        xAxis: {-->
<!--          type: 'category',-->
<!--          data: xData-->
<!--        },-->
<!--        yAxis: {-->
<!--          type: 'value',-->
<!--          name: '分数',-->
<!--          min: 0,-->
<!--          max: 100-->
<!--        },-->
<!--        tooltip: {-->
<!--          trigger: 'axis', //坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用-->
<!--          axisPointer: {// 坐标轴指示器，坐标轴触发有效-->
<!--            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'-->
<!--          }-->
<!--        },-->
<!--        series: data-->
<!--      };-->
<!--      myChart.setOption(option)-->
<!--      myChart.on('click', function (params) {-->
<!--        console.log(params);-->
<!--        that.$router.push({-->
<!--          path: 'xxzs',-->
<!--          query: {-->
<!--            param: params.name,-->
<!--            range: 'grade'-->
<!--          }-->
<!--        })-->
<!--      })-->
<!--      this.$nextTick(() => {-->
<!--        myChart.resize();-->
<!--      });-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->


<template>
  <div class="xxzl">
    <div class="box-card">
      <h2>选择年级</h2>
      <el-checkbox-group v-model="selectedGrades" @change="updateChart">
        <el-checkbox v-for="grade in grades" :key="grade" :label="grade">{{ grade }}</el-checkbox>
      </el-checkbox-group>
    </div>
    <div class="chart-container">
      <div class="chart" ref="chart" style="height: 400px;"></div>
    </div>
    <div class="bottom">
      <div class="left">
        <h2>每年级平均成绩</h2>
        <ul>
          <li v-for="(average, index) in averageScores" :key="index">
            {{ grades[index] }}：{{ average }}
          </li>
        </ul>
      </div>
      <div class="right">
        <h2>成绩最高的班级</h2>
        <ul>
          <li v-for="(topClass, index) in topClasses" :key="index">
            {{ grades[index] }}：{{ topClass }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      selectedGrades: [],
      grades: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      chartData: {
        xAxisData: ['德', '智', '体', '美', '劳'],
        seriesData: []
      },
      colors: ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF', '#00FFFF'], // 柱状图颜色
      scores: {
        '一年级': [80, 90, 85, 75, 95],
        '二年级': [70, 85, 80, 90, 80],
        '三年级': [75, 80, 90, 85, 70],
        '四年级': [85, 75, 80, 95, 90],
        '五年级': [90, 85, 75, 80, 85],
        '六年级': [95, 80, 85, 70, 75]
      },
      chart: null
    };
  },
  computed: {
    averageScores() {
      const averageScores = [];
      for (const grade of this.grades) {
        const gradeScores = this.scores[grade];
        const average = gradeScores.reduce((sum, score) => sum + score, 0) / gradeScores.length;
        averageScores.push(average.toFixed(2));
      }
      return averageScores;
    },
    topClasses() {
      const topClasses = [];
      for (const grade of this.grades) {
        const gradeScores = this.scores[grade];
        const maxScore = Math.max(...gradeScores);
        const maxClassIndex = gradeScores.indexOf(maxScore) + 1;
        topClasses.push(`${grade}${maxClassIndex}班`);
      }
      return topClasses;
    }
  },
  mounted() {
    this.updateChart();

    // 监听窗口大小变化，重绘图表
    window.addEventListener('resize', () => this.chart.resize());
  },
  methods: {
    updateChart() {
      const { selectedGrades } = this;

      const seriesData = this.grades.map((grade, index) => {
        return {
          name: grade,
          type: 'bar',
          itemStyle: {
            color: selectedGrades.includes(grade) ? this.colors[index % this.colors.length] : 'transparent' // 控制柱状图颜色和显示
          },
          data: selectedGrades.includes(grade) ? this.getGradeData(grade) : []
        };
      });

      // 更新图表数据
      this.chartData.seriesData = seriesData;

      // 初始化图表
      this.initChart();
    },
    getGradeData(grade) {
      // 返回对应年级的德智体美劳数据
      return this.scores[grade];
    },
    initChart() {
      const chartDiv = this.$refs.chart;
      this.chart = echarts.init(chartDiv);
      const { xAxisData, seriesData } = this.chartData;

      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: this.grades,
          selected: this.grades.reduce((acc, grade) => {
            acc[grade] = this.selectedGrades.includes(grade);
            return acc;
          }, {})
        },
        xAxis: {
          type: 'category',
          data: xAxisData
        },
        yAxis: {
          type: 'value'
        },
        series: seriesData
      };

      this.chart.setOption(option);
    }
  },
  // 在生命周期末销毁图表，防止内存泄漏
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
};
</script>

<style>
.xxzl {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  width: 100%;
  height: 100%;
  min-height: calc(100vh - 84px);

  .box-card {
    width: 80%;
    background-color: aliceblue;
    border-radius: 8px;
    box-shadow: 3px 4px 10px rgba(0, 0, 0, 0.3);
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .chart-container {
    width: 80%;
    height: 400px;
    box-shadow: 3px 4px 10px rgba(0, 0, 0, 0.3);
  }

  .bottom {
    width: 80%;
    height: 200px;
    background-color: aliceblue;
    box-shadow: 3px 4px 10px rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
}
</style>
