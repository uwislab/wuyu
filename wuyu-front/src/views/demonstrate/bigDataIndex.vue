<template>
  <div class="brand-container" ref="myContent">
    <div class="wrap">
      <header>
        <div class="weather">
          <img :src="imgSrc">
          <span class="tem">{{ weatcherData.tem }}°C</span>
          <span class="wea">{{ weatcherData.wea }}</span>
          <button @click="click">全屏</button>
        </div>
        <h2>五育中控平台</h2>
        <div class="showTime">
          <span class="time">{{ nowTime }}</span>
          <span class="date">
            <span>{{ week }} 第五周</span>
            <span>{{ date }}</span>
          </span>
        </div>
      </header>

      <section class="mainbox">
        <div class="item left">
          <div class="panel">
            <h2>德育各年级平均分数</h2>
            <div id="id0" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>智育各年级平均分数</h2>
            <div id="id1" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>体育各年级平均分数</h2>
            <div id="id2" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
        </div>

        <div class="item center">
          <div class="resume">
            <div class="resume-hd">
              <ul>
                <li>
                  <countTo :startVal='startVal' :endVal='all' :duration='6000' separator=""></countTo>
                </li>
                <li>
                  <countTo :startVal='startVal' :endVal='ava' :duration='6000' separator=""></countTo>
                </li>
              </ul>
            </div>
            <div class="resume-bd">
              <ul>
                <li>老师人数（单位：人）</li>
                <li>学生人数（单位：人）</li>
              </ul>
            </div>
          </div>
          <div class="map">
            <!--
            <div class="map1"></div>
            <div class="map2"></div>
            <div class="map3"></div>
-->

            <h3 style="color: white;text-align: center">班级风采</h3>
            <el-carousel :autoplay="true" indicator-position="none" :interval="4000" height="230px">
              <el-carousel-item v-for="(item, index) in pictureList1" :key="index">
                <div class="panel">
                  <img :src="item" style="width: 100%; height: 100%; object-fit: cover;">
                </div>
              </el-carousel-item>
            </el-carousel>
            <h3 style="color: white;text-align: center">五育标兵</h3>
            <el-carousel :autoplay="true" indicator-position="none" :interval="5000" arrow="always" height="250px">
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xygrChart1" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xygrChart2" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xygrChart3" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xygrChart4" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xygrChart5" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xygrChart6" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>

        <div class="item right">
          <div class="panel">
            <h2>美育各年级平均分数</h2>
            <div id="id3" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>劳育各年级平均分数</h2>
            <div id="id4" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>燃尽图展示</h2>
            <div id="id5" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
        </div>
      </section>

    </div>

  </div>
</template>

<script>
import '@/assets/js/flexible'
import '@/assets/js/china'
import countTo from 'vue-count-to'
import api from '@/api/demonstrate/api'
import echarts from 'echarts'
import screenfull from 'screenfull'
import { getPanelData } from '@/api/managementModule/dataBase'
export default {
  name: 'Brand',
  components: {
    countTo
  },
  data() {
    return {
      pictureList1: [
        "https://i04piccdn.sogoucdn.com/54cf56a0bf810e40",
        "https://i04piccdn.sogoucdn.com/bdc86846b7cd4586",
        "http://5b0988e595225.cdn.sohucs.com/images/20170917/0ed434e0321b4c589607be372fc85572.jpeg"
      ],
      pictureList2: [
        "https://img2.baidu.com/it/u=2513024551,2896067572&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1113%2F060120105F7%2F200601105F7-3-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656687720&t=867c801f0911ed362a3d06f2c1fff054",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F102920105033%2F201029105033-1-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656687720&t=b8754d6573884c844088a92959416fcf"
      ],


      isFullscreen: false,
      nowTime: '',
      week: '',
      date: '',
      timer: null,
      imgSrc: '',
      weatcherData: {
        tem: '--',
        wea: '未知'
      },
      startVal: 0,
      chartList: [],
      typeList: ['德', '智', '体', '美', '劳'],
      typeEnList: ['deyu', 'zhiyu', 'tiyu', 'meiyu', 'laoyu'],
      idList: ['id0', 'id1', 'id2', 'id3', 'id4'],
      colorList: ['#1b6cd2', '#B5C334', '#FCCE10', '#E87C25', '#27727B'],
      all: 0,
      ava: 0,
      myChartStyle: { float: "left", width: "100%", height: "230px" }, //图表样式
    }
  },
  computed: {
    schoolId() {
      return JSON.parse(window.localStorage.getItem("UserInfo")).schoolId;
    }
  },
  created() {
    const that = this;
    // 获取教师数和学生数
    getPanelData({ schoolId: this.schoolId }).then((res) => {
      that.all = res.data.panel.teacherNum;
      that.ava = res.data.panel.studentNum;
    });
    
    // 初始化五育各年级分数图表
    api.getWydc().then(res => {
      // 确保DOM已经渲染完成
      that.$nextTick(() => {
        // 初始化德育图表
        that.initWynjChart('id0', 'deyu', '', res)
        // 初始化智育图表
        that.initWynjChart('id1', 'zhiyu', '', res)
        // 初始化体育图表
        that.initWynjChart('id2', 'tiyu', '', res)
        // 初始化美育图表
        that.initWynjChart('id3', 'meiyu', '', res)
        // 初始化劳育图表
        that.initWynjChart('id4', 'laoyu', '', res)
      })
    })

    // 初始化燃尽图
    api.getWanchengdu().then(e => {
      that.$nextTick(() => {
        that.initWancheng("id5", e.gradeList, e.successList, e.failList);
      })
    })
  },
  mounted() {
    this.getWeather();
    this.timer = setInterval(() => {
      this.getWeather();
    }, 1000 * 60 * 60)

    this.nowTimes();
    this.initXYBJEcharts();
    this.initXYGREcharts();
  },
  methods: {
    timeFormate(timeStamp) { //显示当前时间
      let newDate = new Date(timeStamp);
      let year = newDate.getFullYear();
      let month = newDate.getMonth() + 1 < 10 ? '0' + (newDate.getMonth() + 1) : newDate.getMonth() + 1;
      let date = newDate.getDate() < 10 ? '0' + newDate.getDate() : newDate.getDate();
      let hh = newDate.getHours() < 10 ? '0' + newDate.getHours() : newDate.getHours();
      let mm = newDate.getMinutes() < 10 ? '0' + newDate.getMinutes() : newDate.getMinutes();
      let ss = newDate.getSeconds() < 10 ? '0' + newDate.getSeconds() : newDate.getSeconds();
      let week = newDate.getDay();
      let weeks = ['日', '一', '二', '三', '四', '五', '六'];
      let getWeek = '星期' + weeks[week];
      this.week = getWeek;
      this.date = year + '.' + month + '.' + date;
      this.nowTime = hh + ':' + mm + ':' + ss;
    },
    nowTimes() {
      this.timeFormate(new Date());
      this.getLunarDate();
      setInterval(this.nowTimes, 1000);
      this.clear();
    },
    clear() {
      clearInterval(this.nowTimes)
      this.nowTimes = null;
    },
    getWeather() { // 第三方天气api接口
      // 先获取城市位置
      this.getLocation().then(location => {
        const key = 'd1f1e40002524874b27188d57349f3b0'; // 和风天气API key
        
        fetch(`https://devapi.qweather.com/v7/weather/now?location=${location}&key=${key}`)
          .then(response => response.json())
          .then(data => {
            if(data.code === '200') {
              const weather = data.now;
              this.weatcherData = {
                tem: weather.temp,
                wea: weather.text
              };
              
              // 根据天气状况设置对应的图标
              const weatherIcons = {
                '晴': 'qing.png',
                '多云': 'yun.png',
                '阴': 'yin.png',
                '小雨': 'yu.png',
                '中雨': 'yu.png',
                '大雨': 'yu.png',
                '雷阵雨': 'yu.png',
                '小雪': 'xue.png',
                '中雪': 'xue.png',
                '大雪': 'xue.png',
                '雾': 'wu.png',
                '沙尘暴': 'shachen.png',
                '浮尘': 'shachen.png',
                '扬沙': 'shachen.png',
                '强沙尘暴': 'shachen.png',
                '阵雪': 'xue.png',
                '毛毛雨': 'yu.png',
                '暴雨': 'yu.png',
                '大暴雨': 'yu.png',
                '特大暴雨': 'yu.png',
                '强阵雨': 'yu.png',
                '强雷阵雨': 'yu.png',
                '雨': 'yu.png',
                '雪': 'xue.png'
              };
              
              this.imgSrc = require(`../../assets/img/brand/${weatherIcons[weather.text] || 'qing.png'}`);
            }
          })
          .catch(error => {
            console.error('获取天气数据失败:', error);
            // 设置默认值
            this.weatcherData = {
              tem: '--',
              wea: '未知'
            };
            this.imgSrc = require('../../assets/img/brand/qing.png');
          });
      });
    },

    // 获取城市位置
    getLocation() {
      return new Promise((resolve, reject) => {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            position => {
              const { latitude, longitude } = position.coords;
              // 使用和风天气的城市查询API获取城市ID
              const key = 'd1f1e40002524874b27188d57349f3b0'; // 和风天气API key
              fetch(`https://geoapi.qweather.com/v2/city/lookup?location=${longitude},${latitude}&key=${key}`)
                .then(response => response.json())
                .then(data => {
                  if(data.code === '200' && data.location && data.location.length > 0) {
                    resolve(data.location[0].id);
                  } else {
                    resolve('101010100'); // 默认北京
                  }
                })
                .catch(() => {
                  resolve('101010100'); // 默认北京
                });
            },
            error => {
              console.error('获取位置失败:', error);
              resolve('101010100'); // 默认北京
            }
          );
        } else {
          resolve('101010100'); // 默认北京
        }
      });
    },

    initWynjChart(divId, type, title, res) {
      // 确保DOM元素存在
      const dom = document.getElementById(divId)
      if (!dom) {
        console.error(`找不到id为${divId}的DOM元素`)
        return
      }

      // 类型映射到数组索引
      const typeMap = {
        'deyu': 0,  // 德育对应每组数据的第一个数
        'zhiyu': 1, // 智育对应每组数据的第二个数
        'tiyu': 2,  // 体育对应每组数据的第三个数
        'meiyu': 3, // 美育对应每组数据的第四个数
        'laoyu': 4  // 劳育对应每组数据的第五个数
      }

      // 获取对应类型的分数数据
      const scores = Object.keys(res).map(grade => res[grade][typeMap[type]])

      const myChart = echarts.init(dom)
      const option = {
        title: {
          text: title,
          textStyle: {
            color: '#fff',
            fontWeight: 500,
          },
          top: '5%'
        },
        tooltip: {
          trigger: 'axis',
          formatter: '{b}年级: {c}分'
        },
        legend: {
          data: ['分数'],
          textStyle: {
            color: '#fff'
          }
        },
        xAxis: {
          type: 'category',
          data: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            },
            interval: 0
          }
        },
        yAxis: {
          type: 'value',
          name: '分数',
          nameTextStyle: {
            color: '#fff'
          },
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            }
          },
          min: 0,
          max: 100
        },
        series: [
          {
            name: '分数',
            type: 'line',
            data: scores,  // 使用处理后的分数数据
            smooth: true,
            symbol: 'circle',
            symbolSize: 8,
            itemStyle: {
              color: '#1b6cd2'
            },
            label: {
              show: true,
              position: 'top',
              color: '#fff',
              formatter: '{c}分'
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
                  color: 'rgba(27, 108, 210, 0.5)'
                }, {
                  offset: 1,
                  color: 'rgba(27, 108, 210, 0.1)'
                }]
              }
            }
          }
        ],
        grid: {
          left: '5%',
          right: '5%',
          bottom: '10%',
          top: '20%',
          containLabel: true
        }
      }
      
      myChart.setOption(option)
      window.addEventListener("resize", function () {
        myChart.resize();
      });
      this.chartList.push(myChart)
    },
    initWancheng(divId, xAxisData, succesList, failList) {
      const myChart = echarts.init(document.getElementById(divId))
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['总数', '已完成'],
          textStyle: {
            color: '#fffff'//字体颜色
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            }
          },
          min: 5
        },
        series: [
          {
            name: '总数',
            data: [15, 20, 15, 18, 17, 19],
            type: 'line'
          },
          {
            name: '已完成',
            data: [10, 15, 12, 18, 14, 19],
            type: 'line'
          }
        ]
      };
      myChart.setOption(option)
      this.chartList.push(myChart)
    },
    click() {
      if (!screenfull.enabled) {
        this.$message({
          message: 'you browser can not work',
          type: 'warning'
        })
        return false
      }
      screenfull.toggle(this.$refs.myContent)
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen
    },
    init() {
      if (screenfull.enabled) {
        screenfull.on('change', this.change)
      }
    },
    destroy() {
      if (screenfull.enabled) {
        screenfull.off('change', this.change)
      }
    },
    destroyChart() {
      this.chartList.forEach(chart => {
        chart.dispose()
      })
      this.chartList = []
    },
    getWeekOfYear() {
      const now = new Date();
      const start = new Date(now.getFullYear(), 0, 1);
      const diff = now - start;
      const oneWeek = 7 * 24 * 60 * 60 * 1000;
      return Math.ceil(diff / oneWeek);
    },
    getLunarDate() {
      const lunar = new Date();
      const lunarInfo = [
        '正', '二', '三', '四', '五', '六', '七', '八', '九', '十', '冬', '腊'
      ];
      const lunarDay = [
        '初一', '初二', '初三', '初四', '初五', '初六', '初七', '初八', '初九', '初十',
        '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十',
        '廿一', '廿二', '廿三', '廿四', '廿五', '廿六', '廿七', '廿八', '廿九', '三十'
      ];
      // 这里使用一个简单的模拟，实际项目中建议使用专门的农历转换库
      this.lunarDate = `农历${lunarInfo[lunar.getMonth()]}月${lunarDay[lunar.getDate() - 1]}`;
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.destroyChart();
  },
}
</script>

<style lang="scss" scoped>
.brand-container {
  position: absolute;
  width: 100%;
  height: 100%;
  background: #000;
  overflow-x: hidden;

  .wrap {
    background: url(../../assets/img/brand/bg.jpg) no-repeat #000;
    background-size: cover;
    line-height: 1.15;

    header {
      position: relative;
      height: 1rem;
      background: url(../../assets/img/brand/head_bg.png) no-repeat top center;
      background-size: 100% 100%;

      h2 {
        color: #7ef0ff;
        font-size: 0.475rem;
        text-align: center;
        line-height: 0.75rem;
        letter-spacing: 1px;
      }

      .weather {
        position: absolute;
        left: 1.375rem;
        top: 0.35rem;
        font-size: 0.25rem;
        color: rgba(126, 240, 255, .7);

        img {
          width: .45rem;
        }

        span {
          display: inline-block;
        }

        .tem {
          margin: 0 .1rem 0 .2rem;
        }
      }

      .showTime {
        position: absolute;
        right: 1.375rem;
        top: 0.5rem;
        color: rgba(126, 240, 255, .7);
        display: flex;

        .time {
          font-size: .28rem;
          margin-right: .18rem;
        }

        .date {
          span {
            display: block;

            &:nth-child(1) {
              font-size: .12rem;
              text-align: right;
            }

            &:nth-child(2) {
              font-size: .14rem;
            }
          }
        }
      }
    }

    .mainbox {
      min-width: 1024px;
      max-width: 1920px;
      padding: 0.125rem 0.125rem 0;
      display: flex;

      .item {
        flex: 3;

        &.center {
          flex: 5;
          margin: 0 0.125rem 0.1rem;
          overflow: hidden;

          .resume {
            background: rgba(101, 132, 226, 0.1);
            padding: 0.1875rem;

            .resume-hd {
              position: relative;
              border: 1px solid rgba(25, 186, 139, 0.17);

              ul {
                display: flex;

                %li-line {
                  content: "";
                  position: absolute;
                  height: 50%;
                  width: 1px;
                  background: rgba(255, 255, 255, 0.2);
                  top: 25%;
                }

                li {
                  position: relative;
                  flex: 1;
                  text-align: center;
                  height: 1.2rem;
                  line-height: 1.2rem;
                  font-size: 0.65rem;
                  color: #ffeb7b;
                  padding: 0.05rem 0;
                  font-family: 'DIGITALDREAMFAT';
                  font-weight: bold;

                  &:nth-child(2) {
                    &:after {
                      @extend %li-line;
                      right: 0;
                    }

                    &:before {
                      @extend %li-line;
                      left: 0;
                    }
                  }
                }
              }

              &:before {
                content: "";
                position: absolute;
                width: 30px;
                height: 10px;
                border-top: 2px solid #02a6b5;
                border-left: 2px solid #02a6b5;
                top: 0;
                left: 0;
              }

              &:after {
                content: "";
                position: absolute;
                width: 30px;
                height: 10px;
                border-bottom: 2px solid #02a6b5;
                border-right: 2px solid #02a6b5;
                right: 0;
                bottom: 0;
              }
            }

            .resume-bd {
              ul {
                display: flex;

                li {
                  flex: 1;
                  height: 0.5rem;
                  line-height: 0.5rem;
                  text-align: center;
                  font-size: 0.225rem;
                  color: rgba(255, 255, 255, 0.7);
                  padding-top: 0.125rem;
                }
              }
            }
          }
        }

        %map-style {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
          width: 6.475rem;
          height: 6.475rem;
          background: url(../../assets/img/brand/map.png) no-repeat;
          background-size: 100% 100%;
          opacity: 0.3;
        }

        .map {
          position: relative;
          height: 10.125rem;

          .chart {
            position: absolute;
            top: 0;
            left: 0;
            z-index: 5;
            height: 10.125rem;
            width: 100%;
          }

          .map1 {
            @extend %map-style;
          }

          .map2 {
            @extend %map-style;
            width: 8.0375rem;
            height: 8.0375rem;
            background-image: url(../../assets/img/brand/lbx.png);
            opacity: 0.6;
            -webkit-animation: rotate 15s linear infinite;
            animation: rotate 15s linear infinite;
            z-index: 2;
          }

          .map3 {
            @extend %map-style;
            width: 7.075rem;
            height: 7.075rem;
            background-image: url(../../assets/img/brand/jt.png);
            -webkit-animation: rotate1 10s linear infinite;
            animation: rotate1 10s linear infinite;
          }

          .panel-footer {
            width: 100%;
            height: 50px;
            text-align: center;
            line-height: 20px;
            // color: #fff;
            font-size: 20px;
            font-weight: 300;
          }
        }

        .panel {
          position: relative;
          height: 3.875rem;
          border: 1px solid rgba(25, 186, 139, 0.17);
          background: rgba(255, 255, 255, 0.04) url(../../assets/img/brand/line.png);
          padding: 0 0.1875rem 0;
          margin-bottom: 0.1875rem;

          &:before {
            position: absolute;
            top: 0;
            left: 0;
            content: "";
            width: 10px;
            height: 10px;
            border-top: 2px solid #02a6b5;
            border-left: 2px solid #02a6b5;
          }

          &:after {
            position: absolute;
            top: 0;
            right: 0;
            content: "";
            width: 10px;
            height: 10px;
            border-top: 2px solid #02a6b5;
            border-right: 2px solid #02a6b5;
          }

          .panel-footer {
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;

            &:before {
              position: absolute;
              bottom: 0;
              left: 0;
              content: "";
              width: 10px;
              height: 10px;
              border-bottom: 2px solid #02a6b5;
              border-left: 2px solid #02a6b5;
            }

            &:after {
              position: absolute;
              bottom: 0;
              right: 0;
              content: "";
              width: 10px;
              height: 10px;
              border-bottom: 2px solid #02a6b5;
              border-right: 2px solid #02a6b5;
            }
          }

          h2 {
            height: 0.6rem;
            line-height: 0.6rem;
            text-align: center;
            color: #fff;
            font-size: 0.225rem;
            font-weight: 400;

            a {
              margin: 0 0.1875rem;
              color: #fff;
              text-decoration: none;
            }
          }

          .chart {
            height: 3rem;
          }
        }
      }

    }

  }

}

@-webkit-keyframes rotate {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }

  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

@keyframes rotate {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }

  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

@-webkit-keyframes rotate1 {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }

  to {
    transform: translate(-50%, -50%) rotate(-360deg);
  }
}

@keyframes rotate1 {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }

  to {
    transform: translate(-50%, -50%) rotate(-360deg);
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

.weather {
  display: flex;
  align-items: center;
  color: #fff;
  font-size: 14px;
  
  img {
    width: 30px;
    height: 30px;
    margin-right: 10px;
  }
  
  .tem {
    font-size: 20px;
    margin: 0 10px;
  }
  
  .wea {
    margin-right: 15px;
  }
  
  .humidity, .wind {
    margin: 0 10px;
    padding: 2px 8px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 4px;
  }
}

.showTime {
  color: #fff;
  text-align: right;
  
  .time {
    font-size: 24px;
    margin-right: 15px;
  }
  
  .date {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    
    span {
      margin: 2px 0;
    }
    
    .lunar {
      font-size: 12px;
      color: rgba(255, 255, 255, 0.8);
    }
  }
}
</style>
