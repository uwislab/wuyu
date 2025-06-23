<template>
  <div class="container">
    <!-- 总览柱状图 -->
    <div class="left">
      <el-form>
        <el-select
          v-model="selectedDate"
          placeholder="请选择日期"
          @change="selectDate"
        >
          <el-option v-for="data in dateList" :label="data" :value="data"/>
        </el-select>
      </el-form>
      <div id="bar-chart"></div>
    </div>
    <!-- 对比信息预览区 -->
    <div id="contrast-info">
      <el-table stripe :data="allClassScore">
        <el-table-column align="center"
                         label="班级"
                         prop="classID"/>
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
        <el-table-column align="center"
                         label="时间"
                         prop="evaluateDate"/>
      </el-table>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {getAllClassScore, getDateList} from '@/api/fuScore';
export default {
  name: "grade_class_info",
  data () {
    return {
      // 五育项目列表
      wuyuItem: ['劳育', '美育', '体育', '智育', '德育'],
      classList: [],
      allClassScore: [],
      dateList: [],
      selectedDate: null,
    };
  },
  mounted() {
    let gradeId = this.$route.query.gradeId;
    getDateList().then(res => {
      this.dateList =res.data;
      this.selectedDate = res.data[0];
      this.selectDate(this.selectedDate);
    })
    getAllClassScore(gradeId).then(res => {
      res.data.forEach(item => {
        let i = 0;
        this.classList.push(i+1+'班');
        this.allClassScore = this.allClassScore.concat(item);
        i++;
      })
    })
  },
  methods: {
    selectDate(value) {
      let {allClassScore} = this;
      let classScoreByDate = [];
      let i = 0;
      allClassScore.forEach(classScore => {
        if (classScore.evaluateDate == value) {
          classScoreByDate.push([]);
          classScoreByDate[i].push(classScore.moralityScore);
          classScoreByDate[i].push(classScore.intelligenceScore);
          classScoreByDate[i].push(classScore.physicalScore);
          classScoreByDate[i].push(classScore.aestheticScore);
          classScoreByDate[i].push(classScore.labourScore);
          i++
        }
      });
      this.barChartInit(classScoreByDate);
    },
    barChartInit(scoreList) {
      let {wuyuItem} = this;
      let series = [];
      let i = 0;
      let chartDom = document.getElementById('bar-chart');
      let myChart = echarts.init(chartDom);
      let option;

      scoreList.forEach(score => {
        series.push({name: i+1+'班', type: 'bar', data: score});
        i++;
      });

      option = {
        title: {
          text: 'World Population'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: wuyuItem
        },
        series: series,
      };

      option && myChart.setOption(option);
    }
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
  width: 50%;
  height: 100%;
  margin-right: 1%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-right: solid 1px black;
}
/* 左侧总览柱状图设置 */
#bar-chart {
  /* 自身大小设置及定位 */
  width: 100%;
  height: 90%;
  /* 样式设计 */
}

/* 右侧对比信息预览设置 */
#contrast-info {
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
