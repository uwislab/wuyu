<template>
  <div class="newwyzk-container">
    <!-- 顶部标题 -->
    <header class="header">
      <!-- <h1>新五育中控-@group1 2025</h1> -->
      <div class="year-selector">
        <el-select v-model="selectedYear" placeholder="选择年份" @change="handleYearChange">
          <el-option
            v-for="year in years"
            :key="year"
            :label="year + '年'"
            :value="year"
          />
        </el-select>
      </div>
    </header>

    <!-- 基本信息展示区域 -->
    <div class="info-section">
      <!-- 左侧学校基本信息 -->
      <div class="school-info">
        <div class="info-block">
          <h3>基础信息</h3>
          <ul>
            <li>
              <span class="label">学校名称：</span>
              <span class="value">成都市阳光实验小学（主校区）</span>
            </li>
            <li>
              <span class="label">建校时间：</span>
              <span class="value">2003年6月</span>
            </li>
            <li>
              <span class="label">学校性质：</span>
              <span class="value">教育部门公办</span>
            </li>
            <li>
              <span class="label">学校类别：</span>
              <span class="value">完全小学</span>
            </li>
            <li>
              <span class="label">地域类型：</span>
              <span class="value">城市城区学校</span>
            </li>
            <li>
              <span class="label">占地面积：</span>
              <span class="value">约20亩，建筑面积8000平方米</span>
            </li>
          </ul>
        </div>
        <div class="info-block">
          <h3>教育特色</h3>
          <div class="feature-content">
            <p class="label">办学理念：</p>
            <p class="value">"以人为本，快乐成长"，注重综合素质教育，打造"书香校园"与"健康校园"</p>
          </div>
        </div>
      </div>

      <!-- 右侧数据卡片 -->
      <div class="info-cards">
        <div class="info-card" :class="{ 'is-loading': loading }">
          <div class="info-icon">
            <i class="el-icon-user-solid"></i>
          </div>
          <div class="info-content">
            <div class="info-value">
              <count-to :start-val="0" :end-val="panel.studentNum" :duration="2600" />
            </div>
            <div class="info-label">学生数量</div>
          </div>
          <div class="loading-overlay" v-if="loading">
            <i class="el-icon-loading"></i>
          </div>
        </div>
        <div class="info-card" :class="{ 'is-loading': loading }">
          <div class="info-icon">
            <i class="el-icon-s-custom"></i>
          </div>
          <div class="info-content">
            <div class="info-value">
              <count-to :start-val="0" :end-val="panel.teacherNum" :duration="2600" />
            </div>
            <div class="info-label">教师数量</div>
          </div>
          <div class="loading-overlay" v-if="loading">
            <i class="el-icon-loading"></i>
          </div>
        </div>
        <div class="info-card" :class="{ 'is-loading': loading }">
          <div class="info-icon">
            <i class="el-icon-school"></i>
          </div>
          <div class="info-content">
            <div class="info-value">
              <count-to :start-val="0" :end-val="panel.gradeNum" :duration="2600" />
            </div>
            <div class="info-label">年级数量</div>
          </div>
          <div class="loading-overlay" v-if="loading">
            <i class="el-icon-loading"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <main class="main-content">
      <!-- 轮播图区域 -->
      <el-carousel 
        :interval="5000" 
        height="600px"
        :autoplay="true"
        indicator-position="outside"
        class="chart-carousel"
      >
        <el-carousel-item v-for="(item, index) in typeList" :key="index">
          <div class="chart-container">
            <div :id="'chart' + index" class="chart"></div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </main>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import CountTo from 'vue-count-to'
import { getPanelData } from '@/api/managementModule/dataBase'

export default {
  name: 'NewWyzk',
  components: {
    CountTo
  },
  data() {
    return {
      typeList: ['德', '智', '体', '美', '劳'],
      years: [2021, 2022, 2023, 2024, 2025],
      selectedYear: 2025,
      chartList: [],
      panel: {
        studentNum: 0,
        teacherNum: 0,
        gradeNum: 0
      },
      loading: false,
      // 每个育对应的颜色
      typeColors: {
        德: '#FF4B55', // 红色
        智: '#4B7BE5', // 蓝色
        体: '#00BFA5', // 绿色
        美: '#FFAB2B', // 橙色
        劳: '#7265E6'  // 紫色
      },
      // 模拟数据 - 按年份存储各年级的成绩
      scoreData: {
        2021: {
          德育: [85, 82, 88, 86, 89, 87],
          智育: [88, 86, 89, 87, 90, 88],
          体育: [87, 85, 86, 88, 89, 90],
          美育: [86, 88, 85, 87, 89, 86],
          劳育: [85, 87, 89, 86, 88, 87]
        },
        2022: {
          德育: [86, 83, 89, 87, 90, 88],
          智育: [89, 87, 90, 88, 91, 89],
          体育: [88, 86, 87, 89, 90, 91],
          美育: [87, 89, 86, 88, 90, 87],
          劳育: [86, 88, 90, 87, 89, 88]
        },
        2023: {
          德育: [87, 84, 90, 88, 91, 89],
          智育: [90, 88, 91, 89, 92, 90],
          体育: [89, 87, 88, 90, 91, 92],
          美育: [88, 90, 87, 89, 91, 88],
          劳育: [87, 89, 91, 88, 90, 89]
        },
        2024: {
          德育: [88, 85, 91, 89, 92, 90],
          智育: [91, 89, 92, 90, 93, 91],
          体育: [90, 88, 89, 91, 92, 93],
          美育: [89, 91, 88, 90, 92, 89],
          劳育: [88, 90, 92, 89, 91, 90]
        },
        2025: {
          德育: [89, 86, 92, 90, 93, 91],
          智育: [92, 90, 93, 91, 94, 92],
          体育: [91, 89, 90, 92, 93, 94],
          美育: [90, 92, 89, 91, 93, 90],
          劳育: [89, 91, 93, 90, 92, 91]
        }
      },
    }
  },
  computed: {
    schoolId() {
      try {
        const userInfo = window.localStorage.getItem("UserInfo");
        if (!userInfo) {
          console.error('UserInfo not found in localStorage');
          return null;
        }
        const parsed = JSON.parse(userInfo);
        return parsed.schoolId;
      } catch (error) {
        console.error('Error getting schoolId:', error);
        return null;
      }
    }
  },
  mounted() {
    if (this.schoolId) {
      this.getBasicInfo();
    } else {
      this.$message.error('无法获取学校信息，请重新登录');
    }
    this.$nextTick(() => {
      this.initCharts();
    });
  },
  methods: {
    // 获取基本信息
    getBasicInfo() {
      this.loading = true;
      getPanelData({ schoolId: this.schoolId })
        .then((res) => {
          if (res.data && res.data.panel) {
            this.panel = res.data.panel;
          } else {
            throw new Error('Invalid response format');
          }
        })
        .catch(error => {
          console.error('获取基本信息失败:', error);
          this.$message.error('获取学校基本信息失败，请稍后重试');
        })
        .finally(() => {
          this.loading = false;
        });
    },
    initCharts() {
      // 清除旧图表
      this.chartList.forEach(chart => {
        chart.dispose()
      })
      this.chartList = []

      // 初始化新图表
      this.typeList.forEach((type, index) => {
        const chartDom = document.getElementById('chart' + index)
        if (chartDom) {
          const chart = echarts.init(chartDom)
          this.chartList.push(chart)
          this.updateChart(chart, type, index)
        }
      })

      // 监听窗口大小变化
      window.addEventListener('resize', () => {
        this.chartList.forEach(chart => {
          chart.resize()
        })
      })
    },
    updateChart(chart, type, index) {
      const option = {
        backgroundColor: 'transparent',
        title: {
          text: type + '育成绩变化',
          textStyle: {
            color: '#fff',
            fontSize: 20,
            fontWeight: 'normal'
          },
          top: 20,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(0,0,0,0.7)',
          borderColor: 'rgba(0,0,0,0.7)',
          textStyle: {
            color: '#fff'
          },
          formatter: function(params) {
            const yearStr = params[0].seriesName;
            const gradeStr = params[0].name;
            const score = params[0].value;
            return `${yearStr}<br/>${gradeStr}: ${score}分`;
          }
        },
        legend: {
          show: false // 隐藏图例，因为只显示一年的数据
        },
        grid: {
          top: 80,
          left: '5%',
          right: '5%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
          axisLabel: {
            color: '#fff',
            fontSize: 14
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.3)'
            }
          },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          type: 'value',
          min: 60,
          max: 100,
          interval: 5,
          axisLabel: {
            color: '#fff',
            fontSize: 14,
            formatter: '{value}分'
          },
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.1)',
              type: 'dashed'
            }
          }
        },
        series: this.getSeriesData(type),
        color: [this.typeColors[type]]
      }

      chart.setOption(option)
    },
    getSeriesData(type) {
      if (this.selectedYear) {
        // 显示选定年份的数据
        return [{
          name: this.selectedYear + '年',
          type: 'line',
          data: this.scoreData[this.selectedYear][type + '育'],
          smooth: true,
          symbol: 'circle',
          symbolSize: 8,
          lineStyle: {
            width: 2,
            color: this.typeColors[type]
          },
          itemStyle: {
            color: this.typeColors[type]
          },
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [{
                offset: 0,
                color: this.typeColors[type] // 渐变起始色
              }, {
                offset: 1,
                color: 'rgba(0,0,0,0)' // 渐变结束色
              }]
            }
          }
        }]
      } else {
        // 显示所有年份的数据
        return this.years.map(year => ({
          name: year + '年',
          type: 'line',
          data: this.scoreData[year][type + '育'],
          smooth: true,
          symbol: 'circle',
          symbolSize: 8,
          lineStyle: {
            width: 2,
            color: this.typeColors[type]
          },
          itemStyle: {
            color: this.typeColors[type]
          }
        }))
      }
    },
    handleYearChange() {
      this.$nextTick(() => {
        this.initCharts()
      })
    }
  },
  beforeDestroy() {
    // 清除图表实例
    this.chartList.forEach(chart => {
      chart.dispose()
    })
    window.removeEventListener('resize', this.handleResize)
  }
}
</script>

<style lang="scss" scoped>
.newwyzk-container {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(135deg, #1a2b3c 0%, #2d1b3c 100%);
  padding: 20px;
  box-sizing: border-box;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px 40px;
    margin-bottom: 20px;

    h1 {
      color: #fff;
      font-size: 32px;
      margin: 0;
      text-shadow: 0 0 10px rgba(255,255,255,0.3);
    }

    .year-selector {
      :deep(.el-select) {
        width: 150px;

        .el-input__inner {
          background: rgba(255,255,255,0.1);
          border: 1px solid rgba(255,255,255,0.2);
          color: #fff;

          &::placeholder {
            color: rgba(255,255,255,0.5);
          }
        }
      }
    }
  }

  .info-section {
    display: flex;
    justify-content: space-between;
    margin-bottom: 30px;
    padding: 0 40px;

    .school-info {
      flex: 1;
      margin-right: 40px;
      background: rgba(255, 255, 255, 0.1);
      backdrop-filter: blur(10px);
      border-radius: 15px;
      padding: 25px;

      .info-block {
        margin-bottom: 20px;

        &:last-child {
          margin-bottom: 0;
        }

        h3 {
          color: #fff;
          font-size: 18px;
          margin: 0 0 15px;
          padding-bottom: 10px;
          border-bottom: 1px solid rgba(255, 255, 255, 0.2);
        }

        ul {
          list-style: none;
          padding: 0;
          margin: 0;

          li {
            margin-bottom: 12px;
            display: flex;
            align-items: flex-start;

            &:last-child {
              margin-bottom: 0;
            }
          }
        }

        .feature-content {
          .label {
            margin-bottom: 8px;
          }
          
          .value {
            line-height: 1.6;
          }
        }

        .label {
          color: rgba(255, 255, 255, 0.7);
          font-size: 14px;
          min-width: 90px;
          margin-right: 10px;
        }

        .value {
          color: #fff;
          font-size: 14px;
          flex: 1;
        }
      }
    }

    .info-cards {
      display: flex;
      flex-direction: column;
      gap: 20px;
      width: 300px;

      .info-card {
        margin: 0;
        width: 100%;
        background: rgba(255, 255, 255, 0.1);
        backdrop-filter: blur(10px);
        border-radius: 15px;
        padding: 20px;
        display: flex;
        align-items: center;
        transition: transform 0.3s ease;
        position: relative;
        overflow: hidden;

        &.is-loading {
          pointer-events: none;
          
          .info-content {
            opacity: 0.5;
          }
        }

        .loading-overlay {
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          background: rgba(0, 0, 0, 0.1);
          display: flex;
          align-items: center;
          justify-content: center;
          backdrop-filter: blur(2px);

          i {
            font-size: 24px;
            color: #fff;
          }
        }

        &:hover {
          transform: translateY(-5px);
        }

        .info-icon {
          width: 60px;
          height: 60px;
          border-radius: 50%;
          background: rgba(255, 255, 255, 0.2);
          display: flex;
          align-items: center;
          justify-content: center;
          margin-right: 20px;

          i {
            font-size: 30px;
            color: #fff;
          }
        }

        .info-content {
          flex: 1;
          transition: opacity 0.3s ease;

          .info-value {
            font-size: 28px;
            font-weight: bold;
            color: #fff;
            margin-bottom: 5px;
          }

          .info-label {
            font-size: 14px;
            color: rgba(255, 255, 255, 0.7);
          }
        }
      }
    }
  }

  .main-content {
    .chart-carousel {
      :deep(.el-carousel__indicators) {
        bottom: -30px;

        .el-carousel__button {
          background-color: rgba(255,255,255,0.5);
        }
      }

      :deep(.el-carousel__arrow) {
        background-color: rgba(0,0,0,0.5);
        border: 1px solid rgba(255,255,255,0.2);

        &:hover {
          background-color: rgba(0,0,0,0.7);
        }
      }
    }

    .chart-container {
      height: 100%;
      padding: 20px;
      box-sizing: border-box;

      h2 {
        color: #fff;
        text-align: center;
        margin: 0 0 20px;
        font-size: 24px;
      }

      .chart {
        height: calc(100% - 60px);
        width: 100%;
      }
    }
  }
}

:deep(.el-select-dropdown) {
  background: rgba(45, 45, 45, 0.9);
  border: 1px solid rgba(255,255,255,0.1);

  .el-select-dropdown__item {
    color: #fff;

    &.hover, &:hover {
      background: rgba(255,255,255,0.1);
    }

    &.selected {
      background: rgba(255,255,255,0.2);
      color: #409EFF;
    }
  }
}
</style> 