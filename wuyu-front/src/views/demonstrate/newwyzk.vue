<template>
  <div class="newwyzk-container">
    <!-- 顶部信息栏 -->
    <div class="top-info-bar">
      <div class="date-weather">
        <div class="date-info">
          <span class="date">{{ currentDate }}</span>
          <span class="week">{{ currentWeek }}</span>
        </div>
        <div class="weather-info">
          <i :class="weatherIcon"></i>
          <div class="weather-details">
            <span class="temperature">{{ temperature }}°C</span>
            <span class="description">{{ weatherDescription }}</span>
          </div>
        </div>
      </div>
      <div class="countdown-card">
        <i class="el-icon-time"></i>
        <span>距离高考还剩</span>
        <span class="days">{{ countdownDays }}</span>
        <span>天</span>
      </div>
    </div>
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
import '@/assets/js/flexible'
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
      isFullscreen: false,
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
      // 新增天气 日期 倒计时数据属性
      currentDate: '',
      currentWeek: '',
      temperature: '--',
      weatherIcon: 'el-icon-sunny',
      weatherDescription: '',
      countdownDays: 0,
      weatherTimer: null,
      // 新增城市位置ID
      locationId: '101010100', // 默认北京
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

    // 初始化日期时间和倒计时
    this.updateDateTime()
    this.calculateCountdown()

    // 获取位置信息
    this.getLocation().then(() => {
      // 获取天气信息
      this.getWeatherInfo();
    });

    // 获取天气信息
    this.getWeatherInfo()

    // 设置定时更新
    this.weatherTimer = setInterval(() => {
      this.updateDateTime()
      this.calculateCountdown()
      this.getWeatherInfo()
    }, 60000) // 每分钟更新一次

    this.weatherTimer = setInterval(() => {
      this.getWeatherInfo();
    }, 1800000); // 每30分钟更新一次天气

    this.$nextTick(() => {
      this.initCharts();
    });

    // 添加全屏变化事件监听
    document.addEventListener('fullscreenchange', this.handleFullscreenChange);
    document.addEventListener('webkitfullscreenchange', this.handleFullscreenChange);
    document.addEventListener('mozfullscreenchange', this.handleFullscreenChange);
    document.addEventListener('MSFullscreenChange', this.handleFullscreenChange);

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
    // 更新日期和星期
    updateDateTime() {
      const now = new Date()
      const year = now.getFullYear()
      const month = String(now.getMonth() + 1).padStart(2, '0')
      const day = String(now.getDate()).padStart(2, '0')
      this.currentDate = `${year}年${month}月${day}日`

      const weeks = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
      this.currentWeek = weeks[now.getDay()]
    },
    // 获取位置信息
    getLocation() {
      return new Promise((resolve, reject) => {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            position => {
              const { latitude, longitude } = position.coords;
              const key = 'd1f1e40002524874b27188d57349f3b0'; // 和风天气API key

              fetch(`https://geoapi.qweather.com/v2/city/lookup?location=${longitude},${latitude}&key=${key}`)
                .then(response => response.json())
                .then(data => {
                  if (data.code === '200' && data.location && data.location.length > 0) {
                    this.locationId = data.location[0].id;
                    resolve();
                  } else {
                    console.warn('获取位置ID失败，使用默认位置');
                    resolve(); // 使用默认位置
                  }
                })
                .catch(error => {
                  console.error('获取位置ID失败:', error);
                  resolve(); // 使用默认位置
                });
            },
            error => {
              console.error('获取地理位置失败:', error);
              resolve(); // 使用默认位置
            }
          );
        } else {
          console.warn('浏览器不支持地理位置获取，使用默认位置');
          resolve(); // 使用默认位置
        }
      });
    },
    // 获取天气信息
    async getWeatherInfo() {
      try {
        const key = 'd1f1e40002524874b27188d57349f3b0'; // 和风天气API key
        const response = await fetch(`https://devapi.qweather.com/v7/weather/now?location=${this.locationId}&key=${key}`);
        const data = await response.json();

        if (data.code === '200') {
          const weather = data.now;
          this.temperature = weather.temp;
          this.weatherDescription = weather.text;
          this.setWeatherIcon(weather.text);
        } else {
          throw new Error(`天气API返回错误: ${data.code}`);
        }
      } catch (error) {
        console.error('获取天气信息失败:', error);
        // 设置默认值
        this.temperature = '--';
        this.weatherDescription = '获取失败';
        this.weatherIcon = 'el-icon-sunny';
      }
    },
    // 设置天气图标
    setWeatherIcon(description) {
      // 根据天气描述设置对应的element-ui图标
      const iconMap = {
        '晴': 'el-icon-sunny',
        '多云': 'el-icon-cloudy',
        '阴': 'el-icon-cloudy',
        '小雨': 'el-icon-light-rain',
        '中雨': 'el-icon-light-rain',
        '大雨': 'el-icon-heavy-rain',
        '暴雨': 'el-icon-heavy-rain',
        '雷阵雨': 'el-icon-heavy-rain',
        '小雪': 'el-icon-snow',
        '中雪': 'el-icon-snow',
        '大雪': 'el-icon-snow',
        '暴雪': 'el-icon-snow',
        '雾': 'el-icon-cloudy',
        '沙尘暴': 'el-icon-warning',
        '浮尘': 'el-icon-warning',
        '扬沙': 'el-icon-warning',
        '强沙尘暴': 'el-icon-warning',
        '阵雪': 'el-icon-snow',
        '毛毛雨': 'el-icon-light-rain',
        '雨': 'el-icon-light-rain',
        '雪': 'el-icon-snow'
      }
      // 如果描述在映射表中，则使用对应的图标，否则使用默认的晴天图标
      this.weatherIcon = iconMap[description] || 'el-icon-sunny'
    },
    // 计算高考倒计时
    calculateCountdown() {
      const now = new Date()
      const currentYear = now.getFullYear()
      // 设置今年高考时间（通常是6月7日）
      const examDate = new Date(currentYear, 5, 7) // 月份从0开始，所以5表示6月

      // 如果今年的高考已经过去，就计算到明年的高考
      if (now > examDate) {
        examDate.setFullYear(currentYear + 1)
      }

      const timeDiff = examDate.getTime() - now.getTime()
      this.countdownDays = Math.ceil(timeDiff / (1000 * 3600 * 24))
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
    },
    // 切换全屏显示
    async toggleFullScreen() {
      try {
        const container = this.$refs.container;
        if (!this.isFullscreen) {
          if (container.requestFullscreen) {
            await container.requestFullscreen();
          } else if (container.webkitRequestFullscreen) {
            await container.webkitRequestFullscreen();
          } else if (container.mozRequestFullScreen) {
            await container.mozRequestFullScreen();
          } else if (container.msRequestFullscreen) {
            await container.msRequestFullscreen();
          }
        } else {
          if (document.exitFullscreen) {
            await document.exitFullscreen();
          } else if (document.webkitExitFullscreen) {
            await document.webkitExitFullscreen();
          } else if (document.mozCancelFullScreen) {
            await document.mozCancelFullScreen();
          } else if (document.msExitFullscreen) {
            await document.msExitFullscreen();
          }
        }
      } catch (err) {
        console.error('全屏切换失败:', err);
        this.$message.error('全屏切换失败，请检查浏览器设置');
      }
    },
    // 监听全屏变化
    handleFullscreenChange() {
      this.isFullscreen = Boolean(
        document.fullscreenElement ||
        document.webkitFullscreenElement ||
        document.mozFullScreenElement ||
        document.msFullscreenElement
      );
      // 全屏状态改变时重新初始化图表
      this.$nextTick(() => {
        this.initCharts();
      });
    }
  },
  beforeDestroy() {
    // 清除图表实例
    this.chartList.forEach(chart => {
      chart.dispose()
    })
    // 清除定时器
    if (this.weatherTimer) {
      clearInterval(this.weatherTimer)
    }
    if (this.dateTimer) {
      clearInterval(this.dateTimer);
    }

    // 移除全屏变化事件监听
    document.removeEventListener('fullscreenchange', this.handleFullscreenChange);
    document.removeEventListener('webkitfullscreenchange', this.handleFullscreenChange);
    document.removeEventListener('mozfullscreenchange', this.handleFullscreenChange);
    document.removeEventListener('MSFullscreenChange', this.handleFullscreenChange);

  }
}
</script>

<style lang="scss" scoped>
.newwyzk-container {
  width: 100%;
  height: 100vh;
  background: linear-gradient(135deg, #1a2b3c 0%, #2d1b3c 100%);
  padding: 0.125rem;
  box-sizing: border-box;
  min-width: 1024px;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background:
      radial-gradient(circle at 20% 20%, rgba(255, 255, 255, 0.05) 0%, transparent 50%),
      radial-gradient(circle at 80% 80%, rgba(255, 255, 255, 0.05) 0%, transparent 50%);
    pointer-events: none;
  }

  &:fullscreen,
  &:-webkit-full-screen,
  &:-moz-full-screen {
    width: 100vw;
    height: 100vh;
    padding: 0.125rem;
    background: linear-gradient(135deg, #1a2b3c 0%, #2d1b3c 100%);
    overflow: auto;
  }

  .top-info-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 0.6rem;
    padding: 0 0.2rem;
    background: rgba(255, 255, 255, 0.08);
    backdrop-filter: blur(10px);
    border-radius: 0.1rem;
    margin-bottom: 0.2rem;
    border: 1px solid rgba(255, 255, 255, 0.1);
    box-shadow: 0 0.04rem 0.12rem rgba(0, 0, 0, 0.15);
    transition: all 0.3s ease;

    &:hover {
      background: rgba(255, 255, 255, 0.1);
      border-color: rgba(255, 255, 255, 0.15);
    }

    .date-weather {
      display: flex;
      align-items: center;
      gap: 0.3rem;

      .date-info {
        .date {
          color: #fff;
          font-size: 0.2rem;
          margin-right: 0.15rem;
          text-shadow: 0 0 0.1rem rgba(255, 255, 255, 0.3);
        }

        .week {
          color: rgba(255, 255, 255, 0.8);
          font-size: 0.16rem;
        }
      }

      .weather-info {
        display: flex;
        align-items: center;
        gap: 0.1rem;
        padding: 0.1rem 0.15rem;
        background: rgba(255, 255, 255, 0.05);
        border-radius: 0.08rem;
        transition: all 0.3s ease;

        &:hover {
          background: rgba(255, 255, 255, 0.08);
        }

        i {
          color: #fff;
          font-size: 0.24rem;
          transition: transform 0.3s ease;
        }

        .weather-details {
          display: flex;
          flex-direction: column;

          .temperature {
            color: #fff;
            font-size: 0.2rem;
            line-height: 1.2;
            font-weight: 500;
          }

          .description {
            color: rgba(255, 255, 255, 0.8);
            font-size: 0.14rem;
          }
        }
      }
    }

    .countdown-card {
      display: flex;
      align-items: center;
      gap: 0.1rem;
      padding: 0.1rem 0.2rem;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 0.1rem;
      transition: all 0.3s ease;
      border: 1px solid rgba(255, 255, 255, 0.1);

      &:hover {
        background: rgba(255, 255, 255, 0.15);
        transform: translateY(-0.02rem);
      }

      i {
        color: #fff;
        font-size: 0.2rem;
      }

      span {
        color: #fff;
        font-size: 0.16rem;

        &.days {
          font-size: 0.24rem;
          font-weight: bold;
          color: #ff6b6b;
          margin: 0 0.05rem;
          text-shadow: 0 0 0.1rem rgba(255, 107, 107, 0.3);
        }
      }
    }
  }

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 0.6rem;
    padding: 0 0.4rem;
    margin-bottom: 0.2rem;
    position: relative;

    &::after {
      content: '';
      position: absolute;
      bottom: -0.1rem;
      left: 50%;
      transform: translateX(-50%);
      width: 80%;
      height: 1px;
      background: linear-gradient(
          to right,
          transparent,
          rgba(255, 255, 255, 0.2),
          transparent
      );
    }

    .year-selector {
      :deep(.el-select) {
        width: 1.5rem;

        .el-input__inner {
          background: rgba(255, 255, 255, 0.1);
          border: 1px solid rgba(255, 255, 255, 0.2);
          color: #fff;
          height: 0.4rem;
          line-height: 0.4rem;
          font-size: 0.16rem;
          transition: all 0.3s ease;

          &:hover, &:focus {
            background: rgba(255, 255, 255, 0.15);
            border-color: rgba(255, 255, 255, 0.3);
          }

          &::placeholder {
            color: rgba(255, 255, 255, 0.5);
          }
        }
      }
    }

    .fullscreen-btn {
      cursor: pointer;
      width: 0.4rem;
      height: 0.4rem;
      border-radius: 50%;
      background: rgba(255, 255, 255, 0.1);
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all 0.3s ease;
      border: 1px solid rgba(255, 255, 255, 0.1);

      &:hover {
        background: rgba(255, 255, 255, 0.2);
        transform: scale(1.05);
        border-color: rgba(255, 255, 255, 0.2);
      }

      i {
        color: #fff;
        font-size: 0.2rem;
        transition: transform 0.3s ease;
      }

      &:active {
        transform: scale(0.95);
      }
    }
  }

  .info-section {
    display: flex;
    justify-content: space-between;
    gap: 0.2rem;
    margin-bottom: 0.3rem;
    padding: 0 0.4rem;

    .school-info {
      flex: 1;
      margin-right: 0.4rem;
      background: rgba(255, 255, 255, 0.08);
      backdrop-filter: blur(10px);
      padding: 0.25rem;
      border-radius: 0.1rem;
      border: 1px solid rgba(255, 255, 255, 0.1);
      transition: all 0.3s ease;

      &:hover {
        background: rgba(255, 255, 255, 0.1);
        transform: translateY(-0.02rem);
      }

      .info-block {
        margin-bottom: 0.2rem;
        position: relative;

        &:last-child {
          margin-bottom: 0;
        }

        h3 {
          color: #fff;
          font-size: 0.18rem;
          margin: 0 0 0.15rem;
          padding-bottom: 0.1rem;
          border-bottom: 1px solid rgba(255, 255, 255, 0.2);
          position: relative;

          &::after {
            content: '';
            position: absolute;
            bottom: -1px;
            left: 0;
            width: 2rem;
            height: 1px;
            background: linear-gradient(to right, rgba(126, 240, 255, 0.8), transparent);
          }
        }

        ul {
          list-style: none;
          padding: 0;
          margin: 0;

          li {
            margin-bottom: 0.12rem;
            display: flex;
            align-items: flex-start;
            transition: all 0.3s ease;

            &:hover {
              background: rgba(255, 255, 255, 0.05);
              border-radius: 0.04rem;
              padding: 0.05rem;
              margin: -0.05rem;
            }

            &:last-child {
              margin-bottom: 0;
            }
          }
        }

        .feature-content {
          .label {
            margin-bottom: 0.08rem;
          }

          .value {
            line-height: 1.6;
          }
        }

        .label {
          color: rgba(255, 255, 255, 0.7);
          font-size: 0.14rem;
          min-width: 0.9rem;
          margin-right: 0.1rem;
        }

        .value {
          color: #fff;
          font-size: 0.14rem;
          flex: 1;
        }
      }
    }

    .info-cards {
      display: flex;
      flex-direction: column;
      gap: 0.2rem;
      width: 3rem;

      .info-card {
        margin: 0;
        width: 100%;
        background: rgba(255, 255, 255, 0.08);
        backdrop-filter: blur(10px);
        padding: 0.2rem;
        border-radius: 0.1rem;
        display: flex;
        align-items: center;
        transition: all 0.3s ease;
        position: relative;
        overflow: hidden;
        border: 1px solid rgba(255, 255, 255, 0.1);

        &::before {
          content: '';
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          background: linear-gradient(
              45deg,
              transparent,
              rgba(255, 255, 255, 0.05),
              transparent
          );
          transform: translateX(-100%);
          transition: transform 0.6s ease;
        }

        &:hover {
          transform: translateY(-0.05rem);
          background: rgba(255, 255, 255, 0.1);
          border-color: rgba(255, 255, 255, 0.2);

          &::before {
            transform: translateX(100%);
          }

          .info-icon {
            background: rgba(255, 255, 255, 0.25);

            i {
              transform: scale(1.1);
            }
          }
        }

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
            font-size: 0.24rem;
            color: #fff;
            animation: spin 1s linear infinite;
          }
        }

        .info-icon {
          width: 0.6rem;
          height: 0.6rem;
          margin-right: 0.2rem;
          border-radius: 50%;
          background: rgba(255, 255, 255, 0.2);
          display: flex;
          align-items: center;
          justify-content: center;
          transition: all 0.3s ease;

          i {
            color: #fff;
            font-size: 0.3rem;
            transition: all 0.3s ease;
          }
        }

        .info-content {
          flex: 1;
          transition: opacity 0.3s ease;

          .info-value {
            font-size: 0.28rem;
            font-weight: bold;
            color: #fff;
            margin-bottom: 0.05rem;
            text-shadow: 0 0 0.1rem rgba(255, 255, 255, 0.3);
          }

          .info-label {
            font-size: 0.14rem;
            color: rgba(255, 255, 255, 0.7);
          }
        }
      }
    }
  }

  .main-content {
    padding: 0 0.4rem;

    .chart-carousel {
      :deep(.el-carousel__indicators) {
        bottom: -0.3rem;

        .el-carousel__button {
          background-color: rgba(255, 255, 255, 0.5);
          transition: all 0.3s ease;

          &:hover {
            background-color: rgba(255, 255, 255, 0.8);
          }
        }
      }

      :deep(.el-carousel__arrow) {
        background-color: rgba(0, 0, 0, 0.5);
        border: 1px solid rgba(255, 255, 255, 0.2);
        transition: all 0.3s ease;

        &:hover {
          background-color: rgba(0, 0, 0, 0.7);
          transform: scale(1.1);
        }

        &:active {
          transform: scale(0.95);
        }
      }
    }

    .chart-container {
      height: 100%;
      padding: 0.2rem;
      box-sizing: border-box;
      background: rgba(255, 255, 255, 0.08);
      border-radius: 0.1rem;
      border: 1px solid rgba(255, 255, 255, 0.1);
      transition: all 0.3s ease;

      &:hover {
        background: rgba(255, 255, 255, 0.1);
        border-color: rgba(255, 255, 255, 0.2);
      }

      h2 {
        color: #fff;
        text-align: center;
        margin: 0 0 0.2rem;
        font-size: 0.24rem;
        text-shadow: 0 0 0.1rem rgba(255, 255, 255, 0.3);
      }

      .chart {
        height: calc(100% - 0.6rem);
        width: 100%;
      }
    }
  }
}

:deep(.el-select-dropdown) {
  background: rgba(45, 45, 45, 0.9);
  border: 1px solid rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);

  .el-select-dropdown__item {
    color: #fff;
    transition: all 0.3s ease;

    &.hover, &:hover {
      background: rgba(255, 255, 255, 0.1);
    }

    &.selected {
      background: rgba(255, 255, 255, 0.2);
      color: #409EFF;
    }
  }
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

@media screen and (max-width: 1024px) {
  html {
    font-size: 42px !important;
  }
}

@media screen and (min-width: 1920px) {
  html {
    font-size: 80px !important;
  }
}
</style>
