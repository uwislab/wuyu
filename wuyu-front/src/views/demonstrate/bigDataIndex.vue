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
            <h2>德育各年级变化</h2>
            <div id="id0" style="width: 350px;height: 220px; margin-top: -10px;"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>智育各年级变化</h2>
            <div id="id1" style="width: 350px;height: 220px; margin-top: -10px;"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>体育各年级变化</h2>
            <div id="id2" style="width: 350px;height: 220px; margin-top: -10px;"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
        </div>

        <div class="item center">
          <div class="resume">
            <div class="resume-hd">
              <ul>
                <li>
                  <countTo :startVal='startVal' :endVal='panel.teacherNum' :duration='6000' separator=""></countTo>
                </li>
                <li>
                  <countTo :startVal='startVal' :endVal='panel.studentNum' :duration='6000' separator=""></countTo>
                </li>
                <li>
                  <countTo :startVal='startVal' :endVal='panel.gradeNum' :duration='6000' separator=""></countTo>
                </li>
              </ul>
            </div>
            <div class="resume-bd">
              <ul>
                <li>老师人数（单位：人）</li>
                <li>学生人数（单位：人）</li>
                <li>年级数量（单位：个）</li>
              </ul>
            </div>
          </div>
          <div class="map">
            <h3 style="color: white;text-align: center">五育达成情况</h3>
            <div class="panel panel-main">
              <div class="panel-header">
                <span class="panel-title">五育达成度分析</span>
                <div class="panel-header-border"></div>
              </div>
              <div class="echart" id="wydcChart" style="width: 100%; height: 300px;"></div>
            </div>
            <h3 style="color: white;text-align: center">五育标兵</h3>
            <el-carousel :autoplay="true" indicator-position="none" :interval="5000" arrow="always" height="250px" class="carousel-container">
              <el-carousel-item v-for="(item, index) in 5" :key="index">
                <div class="panel panel-carousel" style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%; background: transparent;">
                  <div class="panel-header" style="margin-bottom: 10px;">
                    <span class="panel-title">{{ typeList[index] }}育标兵</span>
                    <div class="panel-header-border"></div>
                  </div>
                  <img :src="pictureList1[index % pictureList1.length]" alt="标兵图片" style="width: 500px; height: 200px; object-fit: cover; border-radius: 18px; box-shadow: 0 8px 28px rgba(0,0,0,0.22);">
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>

        <div class="item right">
          <div class="panel">
            <h2>美育各年级变化</h2>
            <div id="id3" style="width: 350px;height: 220px; margin-top: -10px;"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>劳育各年级变化</h2>
            <div id="id4" style="width: 350px;height: 220px; margin-top: -10px;"></div>
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
import { getPanelData } from "@/api/managementModule/dataBase"
import echarts from 'echarts'
import screenfull from 'screenfull'
import { getGradeScore } from '@/api/fuScale'
export default {
  name: 'Brand',
  components: {
    countTo
  },
  data() {
    return {
      pictureList1: [
        "https://i04piccdn.sogoucdn.com/2b58f7955a60852f",
        "https://i04piccdn.sogoucdn.com/4c32f3068e5a5124",
        "https://i02piccdn.sogoucdn.com/1872684e9d957a10"
      ],
      pictureList2: [
        "https://i01piccdn.sogoucdn.com/f02186169a45663d",
        "https://i03piccdn.sogoucdn.com/9159a1772fee80f7",
        "https://i04piccdn.sogoucdn.com/2b58f7955a60852f"
      ],


      isFullscreen: false,
      nowTime: '',
      week: '',
      date: '',
      timer: null,
      imgSrc: require('../../assets/img/brand/qing.png'),
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
      panel: {
        studentNum: 0,
        teacherNum: 0,
        gradeNum: 0
      },
      all: 0,
      ava: 0,
      myChartStyle: { float: "left", width: "100%", height: "230px" }, //图表样式
      grades: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      yearList: ['2020-2021学年', '2021-2022学年', '2022-2023学年', '2023-2024学年'],
      wuyuSeries: [
        { name: '德育', data: [] },
        { name: '智育', data: [] },
        { name: '体育', data: [] },
        { name: '美育', data: [] },
        { name: '劳育', data: [] }
      ],
      gradeScores: {}, // Store grade scores by year
    }
  },
  computed: {
    schoolId() {
      return JSON.parse(window.localStorage.getItem("UserInfo")).schoolId;
    }
  },
  created() {
    const that = this;
    // 获取学校基本信息
    getPanelData({ schoolId: this.schoolId }).then((res) => {
      this.panel = res.data.panel;
    });

    // 获取五育年级变化数据
    api.getWynjbh().then(e => {
      setTimeout(function () {
        for (var i = 0; i < that.typeList.length; i++) {
          e[that.typeEnList[i]].forEach(eList => {
            if (eList.data.length < 3) {
              for (let i = 0; i <= 3 - eList.data.length; i++) {
                eList.data.unshift('')
              }
            }
          })
          that.initChart('id' + i, that.typeList[i], that.colorList[i]
            , e["gradeList"]
            , e[e["gradeList"][i] + 'riqi']
            , e[that.typeEnList[i]]);
        }
      }, 500);
    })

    // 获取五育达成数据
    api.getWydc().then(e => {
      console.log('五育达成数据:', e); // 添加日志查看数据结构
      const mockData = {
        '一年级': { deyu: 72, zhiyu: 68, tiyu: 75, meiyu: 68, laoyu: 75 },
        '二年级': { deyu: 40, zhiyu: 30, tiyu: 50, meiyu: 30, laoyu: 40 },
        '三年级': { deyu: 20, zhiyu: 25, tiyu: 30, meiyu: 20, laoyu: 35 },
        '四年级': { deyu: 30, zhiyu: 20, tiyu: 40, meiyu: 35, laoyu: 30 },
        '五年级': { deyu: 25, zhiyu: 30, tiyu: 35, meiyu: 25, laoyu: 45 },
        '六年级': { deyu: 35, zhiyu: 25, tiyu: 45, meiyu: 30, laoyu: 35 },
        gradeList: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级']
      };

      // 合并实际数据和模拟数据
      const mergedData = {
        ...mockData,
        ...e,
        gradeList: e.gradeList || mockData.gradeList
      };

      setTimeout(() => {
        this.initWydcChart(mergedData);
      }, 500);
    })

    // 获取完成度数据
    api.getWanchengdu().then(e => {
      that.all = Number.parseInt(e.count);
      that.ava = Number.parseInt(e.ava);
      setTimeout(function () {
        that.initWancheng("id5", e.gradeList, e.successList, e.failList);
      }, 500);
    })

    // 获取五育年级平均成绩数据
    this.fetchAllGradeWuyuScore();

    // 获取五育年级变化数据
    this.fetchGradeScoresByYear();
  },
  mounted() {
    this.getWeather();
    this.timer = setInterval(() => {
      this.getWeather();
    }, 1000 * 60 * 60)

    this.nowTimes();
    this.initXYGREcharts();
  },
  methods: {
    getGrade(i) {
      let res = ''
      switch(i) {
        case 1:
          res = "一年级"
          break
        case 2:
          res = "二年级"
          break
        case 3:
          res = "三年级"
          break
        case 4:
          res = "四年级"
          break
        case 5:
          res = "五年级"
          break
        case 6:
          res = "六年级"
          break
        default:
          res = "Unknown"
      }
      return res
    },
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
    initChart(divId, index, myColor, legendData, riqi, series) {
      const myChart = echarts.init(document.getElementById(divId))
      const option = {
        title: {
          text: index + '育各年级变化',
          textStyle: {
            color: '#fff',
            fontSize: 16,
            fontWeight: 'normal'
          }
        },
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(0,0,0,0.7)',
          borderColor: 'rgba(0,0,0,0.7)',
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          show: false,
        },
        grid: {
          left: '8%',
          right: '8%',
          bottom: '14%',
          top: 60,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: riqi,
          axisLabel: {
            show: true,
            color: '#333',
            fontSize: 13,
            fontWeight: 400,
            interval: 0,
            margin: 12
          },
          axisLine: {
            lineStyle: {
              color: '#d9d9d9',
              width: 1
            }
          },
          axisTick: { show: false },
          splitLine: { show: false }
        },
        yAxis: {
          type: 'value',
          name: '分数',
          nameTextStyle: {
            color: '#666',
            fontSize: 13,
            fontWeight: 400
          },
          axisLabel: {
            show: true,
            color: '#333',
            fontSize: 13,
            margin: 16
          },
          axisLine: {
            lineStyle: {
              color: '#d9d9d9',
              width: 1
            }
          },
          axisTick: { show: false },
          splitLine: {
            lineStyle: {
              color: '#ededed',
              type: 'dashed',
              width: 1
            }
          },
          min: 60,
          max: 100
        },
        series: series.map((item, idx) => ({
          ...item,
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 7,
          itemStyle: {
            color: myColor,
            borderColor: myColor,
            borderWidth: 0,
            shadowColor: 'transparent',
            shadowBlur: 0
          },
          emphasis: {
            focus: 'series',
            itemStyle: {
              borderColor: myColor,
              borderWidth: 0,
              shadowColor: 'transparent',
              shadowBlur: 0
            },
            lineStyle: {
              width: 3
            }
          },
          lineStyle: {
            width: 2,
            color: myColor,
            shadowColor: 'transparent',
            shadowBlur: 0
          },
          areaStyle: undefined,
          label: {
            show: true,
            position: 'top',
            color: '#333',
            fontWeight: 'normal',
            fontSize: 13,
            backgroundColor: 'transparent',
            borderRadius: 0,
            padding: 0,
            distance: 8,
            formatter: function(params) { return params.value; }
          }
        })),
        tooltip: {
          trigger: 'axis',
          backgroundColor: '#fff',
          borderColor: '#d9d9d9',
          borderWidth: 1,
          textStyle: {
            color: '#333',
            fontSize: 13
          },
          extraCssText: 'border-radius: 4px; box-shadow: 0 2px 8px #eee;'
        },
      };
      myChart.setOption(option)
      this.chartList.push(myChart)

      // 设置自适应
      window.addEventListener('resize', () => {
        myChart.resize();
      });
    },
    initXYGREcharts() {
      const kemu = ['deyu', 'zhiyu', 'tiyu', 'meiyu', 'laoyu']
      const kumuName = ['德育', '智育', '体育', '美育', '劳育']
      const colors = ['#FF4B55', '#4B7BE5', '#23B7E5', '#7265E6', '#FFAB2B'];

      api.getXYStudent()
        .then(res => {
          kemu.forEach((element, index) => {
            const option = {
              title: {
                text: kumuName[index] + '标兵',
                textStyle: {
                  color: '#fff',
                  fontSize: 16,
                  fontWeight: 'normal'
                },
                top: 10,
                left: 'center'
              },
              tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(0,0,0,0.7)',
                borderColor: 'rgba(0,0,0,0.7)',
                textStyle: {
                  color: '#fff'
                }
              },
              grid: {
                top: '20%',
                left: '10%',
                right: '10%',
                bottom: '15%',
                containLabel: true
              },
              xAxis: {
                data: res[element].name,
                axisLabel: {
                  show: true,
                  textStyle: {
                    color: '#fff'
                  },
                  interval: 0,
                  formatter: function (params) {
                    var newParamsName = "";
                    var paramsNameNumber = params.length;
                    var provideNumber = 2;
                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                    if (paramsNameNumber > provideNumber) {
                      for (var p = 0; p < rowNumber; p++) {
                        var tempStr = "";
                        var start = p * provideNumber;
                        var end = start + provideNumber;
                        if (p == rowNumber - 1) {
                          tempStr = params.substring(start, paramsNameNumber);
                        } else {
                          tempStr = params.substring(start, end) + "\n";
                        }
                        newParamsName += tempStr;
                      }
                    } else {
                      newParamsName = params;
                    }
                    return newParamsName;
                  }
                },
                axisLine: {
                  lineStyle: {
                    color: 'rgba(255, 255, 255, 0.2)'
                  }
                },
                splitLine: {
                  show: false
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
                axisLine: {
                  lineStyle: {
                    color: 'rgba(255, 255, 255, 0.2)'
                  }
                },
                splitLine: {
                  lineStyle: {
                    color: 'rgba(255, 255, 255, 0.1)'
                  }
                },
                min: 60,
                max: 100
              },
              series: [
                {
                  type: "line",
                  data: res[element].score,
                  name: "分数",
                  smooth: true,
                  symbol: 'circle',
                  symbolSize: 8,
                  itemStyle: {
                    color: colors[index]
                  },
                  lineStyle: {
                    width: 2,
                    color: colors[index]
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
                        color: colors[index]
                      }, {
                        offset: 1,
                        color: 'rgba(0,0,0,0)'
                      }]
                    }
                  },
                  label: {
                    show: true,
                    position: "top",
                    textStyle: {
                      color: '#fff'
                    }
                  }
                }
              ]
            }
            const myChart = echarts.init(document.getElementById(`xygrChart${index + 1}`))
            myChart.setOption(option)
            window.addEventListener("resize", () => {
              myChart.resize();
            });
            this.chartList.push(myChart)
          })
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    initWydcChart(data) {
      const myChart = echarts.init(document.getElementById('wydcChart'))
      console.log('初始化图表数据:', data); // 添加日志

      // 处理数据，确保数据格式正确
      const gradeList = ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'];
      const colors = ['#FF4B55', '#4B7BE5', '#23B7E5', '#7265E6', '#FFAB2B', '#00BFA5'];

      // 添加数据验证和默认值
      const defaultData = {
        deyu: 0,
        zhiyu: 0,
        tiyu: 0,
        meiyu: 0,
        laoyu: 0
      };

      const seriesData = gradeList.map((grade, index) => {
        const gradeData = data[grade] || defaultData;

        // 确保所有数值都是有效的数字
        const values = [
          'deyu',
          'zhiyu',
          'tiyu',
          'meiyu',
          'laoyu'
        ].map(key => {
          const val = Number(gradeData[key]);
          return isNaN(val) ? 0 : Math.min(Math.max(val, 0), 100); // 限制在0-100之间
        });

        return {
          name: grade,
          value: values,
          itemStyle: {
            color: colors[index]
          },
          lineStyle: {
            width: 2,
            color: colors[index]
          },
          areaStyle: {
            color: colors[index],
            opacity: 0.1
          }
        };
      });

      const option = {
        backgroundColor: 'transparent',
        title: {
          text: '五育达成度分析',
          left: 'center',
          top: 0,
          textStyle: {
            color: '#fff',
            fontSize: 16,
            fontWeight: 'normal'
          }
        },
        tooltip: {
          trigger: 'item',
          backgroundColor: 'rgba(0,0,0,0.7)',
          borderColor: 'rgba(0,0,0,0.7)',
          textStyle: {
            color: '#fff'
          },
          formatter: function(params) {
            return `${params.name}<br/>
                    德育: ${params.value[0]}<br/>
                    智育: ${params.value[1]}<br/>
                    体育: ${params.value[2]}<br/>
                    美育: ${params.value[3]}<br/>
                    劳育: ${params.value[4]}`;
          }
        },
        legend: {
          data: gradeList,
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          top: 30,
          itemWidth: 12,
          itemHeight: 12,
          itemGap: 20
        },
        radar: {
          shape: 'polygon',
          center: ['50%', '60%'],
          radius: '60%',
          splitNumber: 4,
          nameGap: 15,
          scale: true,
          splitArea: {
            areaStyle: {
              color: ['rgba(0,0,0,0)', 'rgba(0,0,0,0.1)', 'rgba(0,0,0,0.2)', 'rgba(0,0,0,0.3)']
            }
          },
          axisLine: {
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.2)'
            }
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.2)'
            }
          },
          axisName: {
            color: '#fff',
            fontSize: 14,
            padding: [0, 15]
          },
          indicator: [
            { name: '德育', max: 100 },
            { name: '智育', max: 100 },
            { name: '体育', max: 100 },
            { name: '美育', max: 100 },
            { name: '劳育', max: 100 }
          ]
        },
        series: [{
          type: 'radar',
          symbolSize: 6,
          data: seriesData
        }]
      };

      // 设置自适应
      window.addEventListener('resize', () => {
        myChart.resize();
      });

      myChart.setOption(option);
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
    async fetchAllGradeWuyuScore() {
      // 清空数据
      this.wuyuSeries = [
        { name: '德育', data: [] },
        { name: '智育', data: [] },
        { name: '体育', data: [] },
        { name: '美育', data: [] },
        { name: '劳育', data: [] }
      ];
      for (let i = 0; i < this.grades.length; i++) {
        const grade = this.grades[i];
        try {
          const res = await getGradeScore(grade);
          // res 应为 [{name: '平均分', data: [德,智,体,美,劳], type: 'line'}]
          if (Array.isArray(res) && res.length > 0 && Array.isArray(res[0].data)) {
            for (let j = 0; j < 5; j++) {
              this.wuyuSeries[j].data.push(res[0].data[j]);
            }
          } else {
            // 若无数据，补空
            for (let j = 0; j < 5; j++) {
              this.wuyuSeries[j].data.push(null);
            }
          }
        } catch (e) {
          for (let j = 0; j < 5; j++) {
            this.wuyuSeries[j].data.push(null);
          }
        }
      }
      // 渲染五育图表
      for (let i = 0; i < 5; i++) {
        this.drawWuyuChart('id' + i, this.typeList[i], this.colorList[i], this.grades, this.wuyuSeries[i].data);
      }
    },
    drawWuyuChart(divId, name, color, xData, data) {
      const myChart = echarts.init(document.getElementById(divId));
      const option = {
        title: {  left: 'center', textStyle: { color: '#fff', fontSize: 16 } },
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: xData, axisLabel: { color: '#fff' } },
        yAxis: { type: 'value', min: 0, max: 100, axisLabel: { color: '#fff' } },
        series: [{
          name: name,
          data: data,
          type: 'line',
          itemStyle: { color: color },
          label: { show: true, position: 'top', color: '#fff' }
        }]
      };
      myChart.setOption(option);
      this.chartList.push(myChart);
    },
    async fetchGradeScoresByYear() {
      try {
        const promises = this.grades.map(grade => getGradeScore(grade));
        const results = await Promise.all(promises);

        // Process the results
        const gradeScores = {};
        results.forEach((result, index) => {
          gradeScores[this.grades[index]] = result;
        });

        this.gradeScores = gradeScores;

        // Initialize charts with the new data
        for (let i = 0; i < this.typeList.length; i++) {
          const seriesData = this.grades.map(grade => {
            return {
              name: grade,
              type: 'line',
              data: this.gradeScores[grade].map(yearData => yearData[this.typeEnList[i]])
            };
          });

          this.initChart('id' + i, this.typeList[i], this.colorList[i], this.grades, this.yearList, seriesData);
        }
      } catch (error) {
        console.error('Error fetching grade scores:', error);
      }
    },
    initChart(divId, index, myColor, legendData, yearList, series) {
      const myChart = echarts.init(document.getElementById(divId))
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          show: false,
        },
        grid: {
          left: '8%',
          right: '8%',
          bottom: '14%',
          top: 60,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: yearList,
          axisLabel: {
            show: true,
            color: '#333',
            fontSize: 13,
            fontWeight: 400,
            interval: 0,
            margin: 12
          },
          axisLine: {
            lineStyle: {
              color: '#d9d9d9',
              width: 1
            }
          },
          axisTick: { show: false },
          splitLine: { show: false }
        },
        yAxis: {
          type: 'value',
          name: '分数',
          nameTextStyle: {
            color: '#666',
            fontSize: 13,
            fontWeight: 400
          },
          axisLabel: {
            show: true,
            color: '#333',
            fontSize: 13,
            margin: 16
          },
          axisLine: {
            lineStyle: {
              color: '#d9d9d9',
              width: 1
            }
          },
          axisTick: { show: false },
          splitLine: {
            lineStyle: {
              color: '#ededed',
              type: 'dashed',
              width: 1
            }
          },
          min: 60,
          max: 100
        },
        series: series.map((item, idx) => ({
          ...item,
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 7,
          itemStyle: {
            color: myColor,
            borderColor: myColor,
            borderWidth: 0,
            shadowColor: 'transparent',
            shadowBlur: 0
          },
          emphasis: {
            focus: 'series',
            itemStyle: {
              borderColor: myColor,
              borderWidth: 0,
              shadowColor: 'transparent',
              shadowBlur: 0
            },
            lineStyle: {
              width: 3
            }
          },
          lineStyle: {
            width: 2,
            color: myColor,
            shadowColor: 'transparent',
            shadowBlur: 0
          },
          areaStyle: undefined,
          label: {
            show: true,
            position: 'top',
            color: '#333',
            fontWeight: 'normal',
            fontSize: 13,
            backgroundColor: 'transparent',
            borderRadius: 0,
            padding: 0,
            distance: 8,
            formatter: function(params) { return params.value; }
          }
        })),
        tooltip: {
          trigger: 'axis',
          backgroundColor: '#fff',
          borderColor: '#d9d9d9',
          borderWidth: 1,
          textStyle: {
            color: '#333',
            fontSize: 13
          },
          extraCssText: 'border-radius: 4px; box-shadow: 0 2px 8px #eee;'
        },
      };
      myChart.setOption(option)
      this.chartList.push(myChart)
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
    min-height: 100vh;
    position: relative;

    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.4);
      z-index: 1;
    }

    header {
      position: relative;
      height: 1rem;
      background: linear-gradient(to right, rgba(1, 134, 218, 0.3), rgba(1, 134, 218, 0.1));
      backdrop-filter: blur(10px);
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
      z-index: 2;

      h2 {
        color: #7ef0ff;
        font-size: 0.475rem;
        text-align: center;
        line-height: 0.75rem;
        letter-spacing: 1px;
        text-shadow: 0 0 10px rgba(126, 240, 255, 0.5);
      }

      .weather {
        position: absolute;
        left: 1.375rem;
        top: 0.35rem;
        font-size: 0.25rem;
        color: rgba(126, 240, 255, 0.7);
        display: flex;
        align-items: center;
        gap: 0.1rem;

        img {
          width: .45rem;
          filter: drop-shadow(0 0 5px rgba(126, 240, 255, 0.3));
        }

        button {
          margin-left: 0.2rem;
          padding: 0.1rem 0.2rem;
          background: rgba(126, 240, 255, 0.1);
          border: 1px solid rgba(126, 240, 255, 0.3);
          color: rgba(126, 240, 255, 0.7);
          border-radius: 4px;
          cursor: pointer;
          transition: all 0.3s;

          &:hover {
            background: rgba(126, 240, 255, 0.2);
            border-color: rgba(126, 240, 255, 0.5);
          }
        }
      }

      .showTime {
        position: absolute;
        right: 1.375rem;
        top: 0.5rem;
        color: rgba(126, 240, 255, 0.7);
        display: flex;
        align-items: center;

        .time {
          font-size: .28rem;
          margin-right: .18rem;
          font-family: 'DIGITALDREAMFAT', monospace;
        }

        .date {
          text-align: right;

          span {
            display: block;

            &:first-child {
              font-size: .14rem;
              margin-bottom: 0.05rem;
            }

            &:last-child {
              font-size: .16rem;
            }
          }
        }
      }
    }

    .mainbox {
      position: relative;
      z-index: 2;
      min-width: 1024px;
      max-width: 1920px;
      padding: 0.125rem 0.125rem 0;
      display: flex;
      gap: 0.125rem;

      .item {
        flex: 3;

        &.center {
          flex: 5;
        }

        .panel {
          position: relative;
          height: 3.875rem;
          background: linear-gradient(135deg, rgba(1,134,218,0.18) 0%, rgba(30,41,59,0.22) 100%);
          border: 1.5px solid rgba(126,240,255,0.18);
          border-radius: 16px;
          box-shadow: 0 6px 24px 0 rgba(1,134,218,0.10), 0 1.5px 6px 0 rgba(126,240,255,0.08);
          padding: 0.22rem 0.18rem;
          margin-bottom: 0.22rem;
          backdrop-filter: blur(8px);
          transition: all 0.3s cubic-bezier(.4,2,.6,1);

          &:hover {
            transform: translateY(-4px) scale(1.02);
            box-shadow: 0 12px 32px 0 rgba(1,134,218,0.18), 0 2px 8px 0 rgba(126,240,255,0.12);
            border-color: #7ef0ff;
          }

          .panel-header,
          h2 {
            color: #7ef0ff;
            font-size: 0.22rem;
            font-weight: 600;
            letter-spacing: 1.5px;
            text-shadow: 0 2px 8px rgba(126,240,255,0.18);
            margin-bottom: 0.12rem;
          }

          &.panel-main {
            height: auto;
            margin-bottom: 0.3rem;
          }

          &.panel-carousel {
            height: 100%;
            margin: 0;
          }
        }

        .resume {
          background: rgba(1, 134, 218, 0.1);
          border: 1px solid rgba(1, 134, 218, 0.3);
          border-radius: 4px;
          padding: 0.1875rem;
          margin-bottom: 0.3rem;
          backdrop-filter: blur(10px);

          .resume-hd {
            ul {
              display: flex;
              gap: 0.2rem;

              li {
                flex: 1;
                text-align: center;
                padding: 0.2rem;
                background: rgba(1, 134, 218, 0.1);
                border-radius: 4px;
                color: #ffeb7b;
                font-size: 0.4rem;
                font-family: 'DIGITALDREAMFAT', monospace;
                text-shadow: 0 0 10px rgba(255, 235, 123, 0.3);
              }
            }
          }

          .resume-bd {
            margin-top: 0.15rem;

            ul {
              display: flex;
              gap: 0.2rem;

              li {
                flex: 1;
                text-align: center;
                color: rgba(255, 255, 255, 0.7);
                font-size: 0.14rem;
              }
            }
          }
        }
      }
    }
  }
}

.carousel-container {
  :deep(.el-carousel__arrow) {
    background: rgba(1, 134, 218, 0.3);
    border: 1px solid rgba(1, 134, 218, 0.5);

    &:hover {
      background: rgba(1, 134, 218, 0.5);
    }
  }
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(1, 134, 218, 0.4);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(1, 134, 218, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(1, 134, 218, 0);
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
