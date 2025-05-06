<template>
  <div class="app-container">
    <div class="title">{{ paper.paperTitle }}【{{paper.paperType}}】</div>
    <div style="margin-top: -55px;">
      <el-badge :value="questList[0].total" class="item">
        <el-button size="small">本次问卷填写人数</el-button>
      </el-badge>
      <el-button type="primary" plain size="small" class="mg-l10" @click="close"
      >返回
      </el-button>  
    </div>
    <!-- <div class="mg-b10">{{ paper.description }}</div> -->
    <div>
      <div v-for="(item, qs_index) in questList" class="mg-b10">
        第{{ qs_index + 1 }}题：{{ item.questTitle }}
        <span v-if="questList2[qs_index].questType == 1">【单选】</span>
        <span v-if="questList2[qs_index].questType == 2">【多选】</span>
        <span v-if="questList2[qs_index].questType == 3">【判断】</span>
        <span v-if="questList2[qs_index].questType == 4">【填空】</span>
        <!--单选、判断、多选-->
        <div v-if="questList2[qs_index].questType != 4">
          <el-table :data="item.qOptionsList" style="width: 100%">
            <el-table-column label="选项">
              <template slot-scope="scope" >
                <i class="el-icon-s-opportunity"></i>选项
                <span style="margin-left: 20px">{{ scope.row.optContent }}</span>
              </template>
            </el-table-column>
            <el-table-column label="小计">
              <template slot-scope="scope">
                <span>{{ scope.row.answerCount }}次</span>
              </template>
            </el-table-column>
            <el-table-column label="比例">
              <template slot-scope="scope">
                <el-progress
                  :text-inside="true"
                  :stroke-width="26"
                  :percentage="(scope.row.answerCount / item.total) * 100"
                ></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--填空-->
        <div v-if="questList2[qs_index].questType == 4">
          <el-table :data="item.qOptionsList" style="width: 100%">
            <el-table-column label="填写内容">
              <template slot-scope="scope">
                <i class="el-icon-chat-dot-square"></i>
                <span style="margin-left: 20px">{{ scope.row.optContent }}</span>
              </template>
            </el-table-column>
            <el-table-column label="小计">
              <template slot-scope="scope">
                <span>{{ scope.row.answerCount }}次</span>
              </template>
            </el-table-column>
            <el-table-column label="比例">
              <template slot-scope="scope">
                <el-progress
                  :text-inside="true"
                  :stroke-width="26"
                  :percentage="(scope.row.answerCount / item.total) * 100"
                ></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--**********饼状图、柱状图**************-->
        <el-tabs
          type="border-card"
          v-model="item.tabname"
          @tab-click="handleClick"
        >
          <el-tab-pane :name="item.questId + '饼状图'" :stretch="true">
            <div
              slot="label"
              style="display: block"
              @click="change(item, '饼状图')"
            >
              <i class="el-icon-pie-chart"></i> 饼状图
            </div>
            <div class="chart-wrapper" style="width: 100%">
              <pie-chart
                style="width: 1400%"
                autoresize
                :ref="item.questId + '饼状图'"
                v-show="item.questId + '饼状图' == item.tabname"
                :cdata="getChartData(item.qOptionsList)"
              />
            </div>
          </el-tab-pane>
          <el-tab-pane
            label="柱状图"
            :name="item.questId + '柱状图'"
            :stretch="true"
            style="width: 85%"
          >
            <div
              slot="label"
              style="display: block"
              @click="change(item, '柱状图')"
            >
              <i class="el-icon-s-data"></i> 柱状图
            </div>
            <div class="chart-wrapper">
              <bar-chart
                autoresize
                :ref="item.questId + '柱状图'"
                v-show="item.questId + '柱状图' == item.tabname"
                :cdata="getChartData(item.qOptionsList)"
              /></div
          ></el-tab-pane>
          <el-tab-pane
            label="折线图"
            :name="item.questId + '折线图'"
            :stretch="true"
            style="width: 100%"
          >
            <div
              slot="label"
              style="display: block"
              @click="change(item, '折线图')"
            >
              <i class="el-icon-data-line"></i> 折线图
            </div>
            <div class="chart-wrapper">
              <line-chart
                autoresize
                :ref="item.questId + '折线图'"
                v-show="item.questId + '折线图' == item.tabname"
                :cdata="getChartData(item.qOptionsList)"
              /></div
          ></el-tab-pane>
          <!-- <el-tab-pane
            label="雷达图"
            :name="item.questId + '雷达图'"
            :stretch="true"
            style="width: 100%"
          >
            <div
              slot="label"
              style="display: block"
              @click="change(item, '雷达图')"
            >
              <i class="el-icon-date"></i> 雷达图
            </div>
            <div class="chart-wrapper">
              <raddar-chart
                autoresize
                :ref="item.questId + '雷达图'"
                v-show="item.questId + '雷达图' == item.tabname"
                :cdata="getChartData(item.qOptionsList)"
              /></div
          ></el-tab-pane> -->
        </el-tabs>
      </div>
    </div>
  </div>
</template>
<script>
import PanelGroup from "@/views/chartUtil/PanelGroup";
import LineChart from "@/views/chartUtil/LineChart";
import RaddarChart from "@/views/chartUtil/RaddarChart";
import PieChart from "@/views/chartUtil/PieChart";
import BarChart from "@/views/chartUtil/BarChart";
import { listCount,listQuestion } from "@/api/questionnaire/question";
export default {
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart,
  },
  data() {
    return {
      // 题目
      questList: [],
      questList2: [],
      // 路由接收参数
      paper: {},
      tabitem: {},
      pdata: [],
      pdata1: [],
      i: 0,
    };
  },
  created() {
    this.paper = this.$route.query;
    // console.log(this.paper);
    //获取题目类型
    listQuestion({ paperId: this.paper.paperId }).then((res) => {
      this.questList2 = res.data;
      // console.log('222222222',this.questList2);
    });
    // 获取题目信息
    listCount({ paperId: this.paper.paperId }).then((res) => {
      for (let i = 0; i < res.data.length; i++) { 
        res.data[i].total = 0;
        res.data[i].tabname = res.data[i].questId + "条形图";
        for (let j = 0; j < res.data[i].qOptionsList.length; j++) {
          res.data[i].total =
            res.data[i].total + res.data[i].qOptionsList[j].answerCount;
        }
      }
      this.questList = res.data;
      console.log('33333',this.questList);
    });
    
  },
  watch: {
    activeName: function () {
      this.chartLine.resize();
    },
  },
  mounted() {},
  handleClick(tab) {
  },
  methods: {
    handleClick(el) {
    },
    change(item, name) {
      // console.log(item.tabname)
      this.$set(item, "tabname", item.questId + name);
      // this.$refs[item.questId + name][0].chart.resize();
    },
    // 关闭
    close() {
      this.$tab.closePage();
    },
    // 预处理图表数据
    getChartData(array) {
      var chartData = {
        xData: [],
        seriesData: [],
        seriesName: "答题次数",
      };
      for (let i in array) {
        chartData.seriesData.push({
          value: array[i].answerCount,
          name: array[i].optContent,
        });
        chartData.xData.push(array[i].optContent);
      }
      return chartData;
    },
  },
};
</script>

<style scoped>
.title {
  font-family: "微软雅黑";
  font-size: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
}
.item {
  /* margin-bottom: 20px; */
  margin-left: 1050px;
}
.mg-l10 {
  margin-left: 10px;
}
.mg-b10 {
  margin-bottom: 10px;
}
.chart-wrapper {
  background: #fff;
  padding: 16px 16px 0;
  margin-bottom: 32px;
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>