<template>
  <div class="container">
    <div class="left">
      <el-form>
        <el-select
          v-model="selectedDate"
          placeholder="请选择日期"
        >
          <el-option v-for="data in dateList" :label="data" :value="data"></el-option>
        </el-select>
        <el-button @click="searchGradeFuScore">搜索</el-button>
      </el-form>
      <div id="grade-wuyu-score"></div>
    </div>
    <div class="right">
      <!-- 右侧顶部按钮栏，五个按钮对应五育项目 -->
      <div id="radar-group-1">
        <div id="radar-grade-1"/>
        <div id="radar-grade-2"/>
        <div id="radar-grade-3"/>
      </div>
      <div id="radar-group-2">
        <div id="radar-grade-4"/>
        <div id="radar-grade-5"/>
        <div id="radar-grade-6"/>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import {getGradeFuScoreByDate, getDateList} from "@/api/fuScore";

export default {
  name: "index",
  data() {
    return {
      // 五育项目列表
      wuyuItem: [
        { name:'德育', min:60 },
        { name:'智育', min:60 },
        { name:'体育', min:60 },
        { name:'美育', min:60 },
        { name:'劳育', min:60 }
      ],
      // 年级列表
      gradeList: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      // 选中的五育项目
      selectedItemID: 1,
      // 五项成绩列表
      moScoreList: [],
      inScoreList: [],
      phScoreList: [],
      aeScoreList: [],
      laScoreList: [],
      dateList: [],
      selectedDate: null,
    }
  },
  mounted() {
    // 页面初始化获取数据
    let moScoreList = [];
    let inScoreList = [];
    let phScoreList = [];
    let aeScoreList = [];
    let laScoreList = [];
    let gradeScore = [[], [], [], [], [], []];
    getDateList().then(res => {
      this.dateList = res.data;
      this.selectedDate = res.data[0];
        getGradeFuScoreByDate(this.selectedDate).then(res => {
          let gradeFuScore = res.data;
          for (let i = 0; i < gradeFuScore.length; i++) {
            moScoreList[i] = gradeFuScore[i].moralityScore;
            gradeScore[i].push(moScoreList[i]);
            inScoreList[i] = gradeFuScore[i].intelligenceScore;
            gradeScore[i].push(inScoreList[i]);
            phScoreList[i] = gradeFuScore[i].physicalScore;
            gradeScore[i].push(phScoreList[i]);
            aeScoreList[i] = gradeFuScore[i].aestheticScore;
            gradeScore[i].push(aeScoreList[i]);
            laScoreList[i] = gradeFuScore[i].labourScore;
            gradeScore[i].push(laScoreList[i]);
          }
          this.histogramInit(moScoreList, inScoreList, phScoreList, aeScoreList, laScoreList);
          for(let i = 1; i <= 6; i++) {
            this.radarInit(i, gradeScore[i-1]);
          }
      });
    });
  },
  methods: {
    // 柱状图初始化方法
    histogramInit (moScoreList, inScoreList, phScoreList, aeScoreList, laScoreList) {
      let year = parseInt(this.selectedDate/100 + '');
      let month = this.selectedDate%100;

      let chartDom = document.getElementById('grade-wuyu-score');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // Use axis to trigger tooltip
            type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        title: {
          show: true,
          text: '各年级'+year+'年'+month+'月五育成绩柱状图',
          x: 'center',
          y: 'top',
          top: '50px'
        },
        legend: {
          top: '80px'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '5%',
          containLabel: true,
          height: '80%'
        },
        xAxis: {
          type: 'value',
          max: 500
        },
        yAxis: {
          type: 'category',
          data: this.gradeList
        },
        series: [
          {
            name: '德育',
            type: 'bar',
            stack: 'total',
            barWidth: '60%',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: moScoreList
          },
          {
            name: '智育',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: inScoreList
          },
          {
            name: '体育',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: phScoreList
          },
          {
            name: '美育',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: aeScoreList
          },
          {
            name: '劳育',
            type: 'bar',
            stack: 'total',
            label: {
              show: true
            },
            emphasis: {
              focus: 'series'
            },
            data: laScoreList
          }
        ]
      };

      option && myChart.setOption(option);
    },
    // 雷达图初始化方法
    radarInit (gradeId, scoreList) {
      let that = this;
      let chartDom = document.getElementById('radar-grade-' + gradeId);
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        tooltip: {
          trigger: 'item',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        title: {
          text: this.gradeList[gradeId-1]
        },
        radar: {
          // shape: 'circle',
          indicator: this.wuyuItem,
          triggerEvent: true
        },
        series: [
          {
            type: 'radar',
            data: [
              {
                value: scoreList,
                name: this.gradeList[gradeId-1] + '五育成绩'
              }
            ],
            itemStyle: {
              normal: {
                label: {
                  show: true // 在折线拐点上显示数据
                }
              }
            }
          }
        ]
      };

      myChart.on('click', function () {
        that.checkInfo(gradeId);
      })
      option && myChart.setOption(option);
    },
    // 按钮点击事件 -- 选择成绩日期
    searchGradeFuScore () {
      let moScoreList = [];
      let inScoreList = [];
      let phScoreList = [];
      let aeScoreList = [];
      let laScoreList = [];
      let gradeScore = [[], [], [], [], [], []];
      getGradeFuScoreByDate(this.selectedDate).then(res => {
        let gradeFuScore = res.data;
        for (let i = 0; i < gradeFuScore.length; i++) {
          moScoreList[i] = gradeFuScore[i].moralityScore;
          gradeScore[i].push(moScoreList[i]);
          inScoreList[i] = gradeFuScore[i].intelligenceScore;
          gradeScore[i].push(inScoreList[i]);
          phScoreList[i] = gradeFuScore[i].physicalScore;
          gradeScore[i].push(phScoreList[i]);
          aeScoreList[i] = gradeFuScore[i].aestheticScore;
          gradeScore[i].push(aeScoreList[i]);
          laScoreList[i] = gradeFuScore[i].labourScore;
          gradeScore[i].push(laScoreList[i]);
        }
        this.histogramInit(moScoreList, inScoreList, phScoreList, aeScoreList, laScoreList);
        for(let i = 1; i <= 6; i++) {
          this.radarInit(i, gradeScore[i-1]);
        }
      });
    },
    // 按钮点击事件 -- 跳转页面
    checkInfo (gradeId) {
      this.$router.push( {path: 'wuyu_item_info', query: {gradeId}} );
    },
  }
}
</script>

<style scoped>

/* 主容器设置 */
.container {
  /* 自身大小设置及定位 */
  width: 1680px;
  height: 850px;
  margin: 10px;
  /* 子组件定位设置 */
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
  /* 左侧框设置 */
 .left {
   width: 55%;
   height: 100%;
   margin-right: 1%;
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
 }
    /* 左侧总览柱状图 */
    #grade-wuyu-score {
      /* 自身大小设置及定位 */
      width: 100%;
      height: 100%;
      /* 样式设计 */
    }

 /* 右侧框设置 */
 .right {
   width: 50%;
   height: 100%;
   display: flex;
   flex-direction: row;
   align-items: center;
   justify-content: center;
   border: solid 1px black;
   padding: 10px;
 }
    #radar-group-1, #radar-group-2 {
      width: 50%;
      height: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    }
      #radar-grade-1, #radar-grade-2, #radar-grade-3, #radar-grade-4, #radar-grade-5, #radar-grade-6 {
        width: 100%;
        height: 35%;
      }
</style>
