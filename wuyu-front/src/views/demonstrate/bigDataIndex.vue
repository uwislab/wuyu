<template>
  <div class="brand-container" ref="myContent">
    <div class="wrap">
      <header>
        <div class="weather">
          <img :src="imgSrc">
          <span class="tem">{{ weatcherData.tem }}°C</span>
          <span class="wea">{{ weatcherData.wea }}</span>
          <button @click="toggleFullscreen">全屏</button>
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
            <div id="id0" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>智育各年级变化</h2>
            <div id="id1" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>体育各年级变化</h2>
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
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xybjChart1" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xybjChart2" :style="myChartStyle"></div>
                </div>
              </el-carousel-item>
              <el-carousel-item>
                <div class="panel">
                  <div class="echart" id="xybjChart3" :style="myChartStyle"></div>
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
                  <div class="echart" id="xygrChart5" :style="myChartStyle"></div>、
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
            <h2>美育各年级变化</h2>
            <div id="id3" style="width: 350px;height: 200px"></div>
            <!--            <div class="panel-footer"></div>-->
          </div>
          <div class="panel">
            <h2>劳育各年级变化</h2>
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
export default {
  name: 'Brand',
  components: {
    countTo
  },
  data() {
    return {
      pictureList1: [
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp06%2F200QQU3202Y7-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656687720&t=7cae6f9a936d1ce9b590ee9f4f574fc4",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F0R620115Q8%2F200R6115Q8-6-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656687720&t=c87971b1dfb562bd6e4314165076c073",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F102920105033%2F201029105033-6-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656687720&t=93f86a9d21d937fdba1c824955f87a60"
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
      weatcherData: {},
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

  },
  created() {
    const that = this;
    api.getWynjbh().then(e => {
      setTimeout(function () {
        for (var i = 0; i < that.typeList.length; i++) {
          // console.log(e[that.typeEnList[i]])
          e[that.typeEnList[i]].forEach(eList => {
            if (eList.data.length < 3) {
              for (let i = 0; i <= 3 - eList.data.length; i++) {
                eList.data.unshift('')
              }
            }
          })
          // console.log(e[that.typeEnList[i]])
          that.initChart('id' + i, that.typeList[i], that.colorList[i]
            , e["gradeList"]
            , e[e["gradeList"][i] + 'riqi']
            , e[that.typeEnList[i]]);
        }
      }, 500);
    })
    api.getWanchengdu().then(e => {
      that.all = Number.parseInt(e.count);
      that.ava = Number.parseInt(e.ava);
      setTimeout(function () {
        that.initWancheng("id5", e.gradeList, e.successList, e.failList);
      }, 500);
    })
  },
  mounted() {
    this.init();
    if (screenfull.enabled) {
      screenfull.on('change', this.handleFullscreenChange);
    };
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
      setInterval(this.nowTimes, 1000);
      this.clear();
    },
    clear() {
      clearInterval(this.nowTimes)
      this.nowTimes = null;
    },
    getWeather() { // 第三方天气api接口
      this.imgSrc = require('../../assets/img/brand/qing.png');
    },

    initChart(divId, index, myColor, legendData, riqi, series) {
      const myChart = echarts.init(document.getElementById(divId))
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: legendData,
          textStyle: {
            color: '#fffff'//字体颜色
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['2020-2021学年', '2021-2022学年', '2022-2023学年'],
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            },
            interval: 0,//使x轴文字显示全
            formatter: function (params) {
              var newParamsName = "";
              var paramsNameNumber = params.length;
              var provideNumber = 9; //一行显示几个字
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
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            },
          },
          min: 60
        },
        series: series
      };
      myChart.setOption(option)
      this.chartList.push(myChart)
    },
    initXYBJEcharts() {
      api.getXYClass()
        .then(function (res) {
          const xyClassList = res.xyClass
          for (let i = 0; i < xyClassList.length; i++) {
            const option = {
              title: {
                text: xyClassList[i].class,
                textStyle: {
                  color: '#fff',
                  fontWeight: 500,
                },
                top: '5%'
              },
              // legend: {
              //     orient: 'vertical',
              //     right: '10%'
              // },
              tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} 分' // <br/>换行
                //a:（系列名称） b:(数据名称) c:(数值) d:(饼图百分比)
              },
              series: [
                {
                  type: 'pie',
                  radius: '50%',
                  center: ['55%', '50%'],
                  roseType: 'area',
                  itemStyle: {
                    borderRadius: 8
                  },
                  data: [
                    { value: xyClassList[i].deyu, name: '德育' },
                    { value: xyClassList[i].zhiyu, name: '智育' },
                    { value: xyClassList[i].tiyu, name: '体育' },
                    { value: xyClassList[i].meiyu, name: '美育' },
                    { value: xyClassList[i].laoyu, name: '劳育' },
                  ]
                }
              ]
            };
            const myChart = echarts.init(document.querySelector("#xybjChart" + (i + 1)));
            myChart.setOption(option);
            window.addEventListener("resize", function () {
              myChart.resize();
            });
            this.chartList.push(myChart)
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    initXYGREcharts() {
      const kemu = ['zong', 'deyu', 'zhiyu', 'tiyu', 'meiyu', 'laoyu']
      const kumuName = ['总分', '德育', '智育', '体育', '美育', '劳育']
      api.getXYStudent()
        .then(function (res) {
          kemu.forEach((element, index) => {
            const option = {
              title: {
                text: kumuName[index],
                textStyle: {
                  color: '#fff',
                  fontWeight: 500,
                },
                top: '5%'
              },
              xAxis: {
                data: res[element].name,
                axisLabel: {
                  show: true,
                  textStyle: {
                    color: '#fff'
                  },
                  interval: 0,//使x轴文字显示全
                  formatter: function (params) {
                    var newParamsName = "";
                    var paramsNameNumber = params.length;
                    var provideNumber = 2; //一行显示几个字
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
                }
              },
              // 图例
              // legend: {
              //   data: ["分数"],
              //   top: "0%",
              //   textStyle: {
              //     color: '#fffff'//字体颜色
              //   }
              // },
              yAxis: {
                axisLabel: {
                  show: true,
                  textStyle: {
                    color: '#fff'
                  }
                }
              },
              series: [
                {
                  type: "line",
                  data: res[element].score,
                  name: "分数", // legend属性
                  label: {
                    // 柱状图上方文本标签，默认展示数值信息
                    show: true,
                    position: "top"
                  }
                },
              ],
              grid: {
                left: '15%',
                right: '15%',
                bottom: '15%'
              },
            }
            const myChart = echarts.init(document.getElementById(`xygrChart${index + 1}`))
            myChart.setOption(option)
            window.addEventListener("resize", function () {
              myChart.resize();
            });
            this.chartList.push(myChart)
          })
        })
        .catch(function (error) {
          console.log(error);
        })
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
    click() {  // 修改部分：添加点击事件处理函数
      if (!screenfull.enabled) {
        this.$message({
          message: 'you browser can not work',
          type: 'warning'
        })
        return false
      }
      screenfull.toggle(this.$refs.myContent)
    },
    change() {  // 修改部分：添加全屏状态变化处理函数
      this.isFullscreen = screenfull.isFullscreen
    },
    init() {
      if (screenfull.enabled) {
        screenfull.on('change', this.change)
      }
    },
    destroy() {  // 修改部分：销毁全屏监听
      if (screenfull.enabled) {
        screenfull.off('change', this.change)
      }
    },
    destroyChart() {
      this.chartList.forEach(chart => {
        chart.dispose()
      })
      this.chartList = []
    }
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.destroyChart();
    this.destroy(); // 修改部分：销毁全屏监听
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
</style>
