<template>
  <div class="container">
    <el-button v-if="isPage34Show" @click="changePage(1)">< 上一页</el-button>
    <div class="student-info-page1">
      <el-descriptions style="width: 100%" border title="学生信息">
        <el-descriptions-item label="相片">
          <el-avatar
            size="large"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          />
        </el-descriptions-item>
        <el-descriptions-item label="姓名">{{ studentInfo.studentName }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ studentInfo.studentNum }}</el-descriptions-item>
        <el-descriptions-item label="班主任信息">
          <el-collapse>
            <el-collapse-item :title="monitorInfo.name">联系方式：{{monitorInfo.contactInfo}}</el-collapse-item>
          </el-collapse>
        </el-descriptions-item>
      </el-descriptions>
      <el-table style="margin-top: 30px" stripe :data="scoreList" >
        <el-table-column align="center"
                         label="时间"
                         prop="evaluateDate"/>
        <el-table-column align="center"
                         label="德育"
                         prop="moralityScore"/>
        <el-table-column align="center"
                         label="智育"
                         prop="intelligenceScore"/>
        <el-table-column align="center"
                         label="体育"
                         prop="physicalScore"/>
        <el-table-column align="center"
                         label="美育"
                         prop="aestheticScore"/>
        <el-table-column align="center"
                         label="劳育"
                         prop="labourScore"/>
      </el-table>
    </div>
    <!-- 总览柱状图 -->
    <div class="student-info-page2">
      <div id="radar-chart"></div>
      <div class="item-line-chart">
        <div class="button-row">
          <el-button class="item-button" @click="lineChartInit(0)">德育</el-button>
          <el-button class="item-button" @click="lineChartInit(1)">智育</el-button>
          <el-button class="item-button" @click="lineChartInit(2)">体育</el-button>
          <el-button class="item-button" @click="lineChartInit(3)">美育</el-button>
          <el-button class="item-button" @click="lineChartInit(4)">劳育</el-button>
        </div>
        <div id="line-chart"/>
      </div>
    </div>
    <!-- 对比信息预览区 -->
    <div class="student-info-page3">
      <div class="fu-item-block">
        <div id="morality-radar-chart"></div>
      </div>
    </div>
    <!-- 总览柱状图 -->
    <div class="student-info-page4">

    </div>
    <el-button v-if="isPage12Show" @click="changePage(0)">下一页 ></el-button>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {getDateList, getGradeScore, getStudentsFuScore} from '@/api/fuScore';
export default {
  name: "grade_wuyu_info",
  data () {
    return {
      isPage12Show: true,
      isPage34Show: false,
      // 五育项目列表
      wuyuItem: [
        { name:'德育', min:60 },
        { name:'智育', min:60 },
        { name:'体育', min:60 },
        { name:'美育', min:60 },
        { name:'劳育', min:60 }
      ],
      // 选择的项目 0-5：代表‘德智体美劳’五育
      selectedItemID: 0,
      // 年级ID
      gradeId: null,
      // 年级数组
      gradeList: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      // 日期数组
      dateList: [],
      // 某一年级数组
      scoreList: [],
      // 五育各项成绩
      wuyuScoreList: [[], [], [], [], []],
      //学生信息
      studentInfo: { studentName: '', studentNum: '' },
      // 班主任信息
      monitorInfo: { name: '', contactInfo: '' },
    };
  },
  mounted() {
    this.studentInfo = this.$route.query.studentInfo;
    this.monitorInfo = this.$route.query.monitorInfo;
    getDateList().then(res => {
      res.data.forEach(item => {
        if (item%100 == 7) this.dateList.push({label: parseInt(item/100+'')-1+'学年下学期', value: item});
        else this.dateList.push({label: parseInt(item/100+'')+'学年上学期', value: item});
      });
      this.selectedDate = res.data[0];
      getStudentsFuScore(this.studentInfo.studentNum).then(res => {
        this.scoreList = res.data;
        let gradeScore = []
        let i = 0
        res.data.forEach(data => {
          // 每循环一次，就向年级成绩数组中压入一个不同时间的五育所有成绩
          gradeScore.push([]);
          gradeScore[i].push(data.moralityScore);
          gradeScore[i].push(data.intelligenceScore);
          gradeScore[i].push(data.physicalScore);
          gradeScore[i].push(data.aestheticScore);
          gradeScore[i].push(data.labourScore);
          // 每循环一次，就向年级成绩数组中压入一个不同时间的五育所有成绩
          this.wuyuScoreList[0].push(data.moralityScore);
          this.wuyuScoreList[1].push(data.intelligenceScore);
          this.wuyuScoreList[2].push(data.physicalScore);
          this.wuyuScoreList[3].push(data.aestheticScore);
          this.wuyuScoreList[4].push(data.labourScore);
          i++;
        })
        this.radarInit(gradeScore, this.dateList);
        this.lineChartInit(0)
      });
    });
  },
  methods: {
    // 雷达图初始化方法
    radarInit (scoreList, dateList) {
      let seriesData = [];
      let legendData = [];
      let chartDom = document.getElementById('radar-chart');
      let myChart = echarts.init(chartDom);
      let option;

      for (let i = 0; i < scoreList.length; i++) {
        seriesData.push({value: scoreList[i], name: dateList[i].label+'('+dateList[i].value+')'});
        legendData.push(dateList[i].label+'('+dateList[i].value+')');
      }

      option = {
        tooltip: {
          show: true,
          trigger: 'item',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        legend: {
          data: legendData,
          orient: 'vertical',
          x: 'right',
          y: 'top',
        },
        title: {
          text: '五育成绩'
        },
        radar: {
          // shape: 'circle',
          indicator: this.wuyuItem,
          triggerEvent: true
        },
        series: [
          {
            type: 'radar',
            data: seriesData,
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

      option && myChart.setOption(option);
    },

    // 鼠标点击事件 -- 选择五育中某项，并触发折线图渲染事件
    selectItem (itemId) {
      let data = this.wuyuScoreList[itemId];
      this.lineChartInit(data);
    },

    lineChartInit (itemId) {
      let that = this;
      let {gradeId, gradeList, wuyuItem, wuyuScoreList, dateList} = that;
      let date = [];
      let scoreList = wuyuScoreList[itemId];
      dateList.forEach(datetime => {
        date.push(datetime.value)
      });
      let chartDom = document.getElementById('line-chart');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: wuyuItem[itemId].name + '成绩',
          x: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: date
        },
        yAxis: {
          type: 'value',
          min: '60'
        },
        series: [
          {
            data: scoreList,
            type: 'line',
            smooth: true,
            label: {
              show: true,
              position: 'top'
            },
          }
        ]
      };

      option && myChart.setOption(option);
    },

    // 鼠标点击 -- 判断要显示哪些页
    changePage (node) {
      if (node == 0) {
        this.isPage12Show = false;
        this.isPage34Show = true;
      } else {
        this.isPage12Show = true;
        this.isPage34Show = false;
      }
    }

  }
}
</script>

<style scoped>
  /* 主容器设置 */
  .container {
    /* 自身大小设置及定位 */
    width: 1680px;
    height: 880px;
    margin: 10px;
    border: solid 1px black;
    /* 子组件定位设置 */
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /* 左侧总览柱状图设置 */
  #radar-chart {
    /* 自身大小设置及定位 */
    width: 100%;
    height: 90%;
    /* 样式设计 */
  }

  /* 右侧对比信息预览设置 */
  .student-info-page1, .student-info-page2 {
    /* 自身大小设置及定位 */
    width: 50%;
    height: 100%;
    margin-left: 20px;
    /* 子组件定位设置 */
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
    .item-line-chart {
      width: 100%;
      height: 60%;
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
    }
      .button-row {
        width: 15%;
        height: 100%;
      }
        .item-button {
          width: 90%;
          margin-top: 50px;
          font-size: 20px;
          font-weight: bold;
        }
      #line-chart {
        width: 85%;
        height: 100%;
      }
</style>
