<template>
  <div class="container">
    <div class="chart-header">
      <div class="title">班级学生录入和评阅占比情况</div>
      <el-select v-model="currentClass" placeholder="请选择" class="selecter">
        <el-option
          v-for="item in selectList"
          :key="item.index"
          :label="item.className"
          :value="item.index"
        >
        </el-option>
      </el-select>
    </div>
    <div class="chart-body">
      <div ref="base_info_left" style="width: 550px; height: 400px"></div>
      <div ref="base_info_right" style="width: 550px; height: 400px"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getClassRate } from "@/api/overview";
export default {
  name: "overview-chart",
  props: ["selectedGrade", "selectedTerm"],
  data() {
    return {
      classList: [
        { isEntered: 28, isReview: 28, totalNum: 28 },
        { isEntered: 25, isReview: 28, totalNum: 30 },
        { isEntered: 35, isReview: 29, totalNum: 38 },
      ],
      selectList: [
        {
          className: "一班",
          index: 0,
        },
        { className: "二班", index: 1 },
        { className: "三班", index: 2 },
      ],
      currentClass: 0,
      base_info_left_option: {},
      base_info_right_option: {},
    };
  },
  methods: {
    initChart() {
      // 初始化饼图
      let base_info_left = echarts.init(this.$refs.base_info_left);
      let base_info_right = echarts.init(this.$refs.base_info_right);

      console.log("渲染图表", this.classList, this.currentClass);
      const colors = ['#FF6347', '#4682B4']; // 定义颜色数组

      this.base_info_left_option = {
        tooltip: {
          trigger: "item",
        },
        series: [
          {
            name: "成绩录入情况",
            type: "pie",
            radius: "50%",
            data: [
              {
                value:
                  this.classList[this.currentClass].totalNum -
                  this.classList[this.currentClass].isEntered,
                name: "未录入成绩",
                itemStyle: { color: colors[0] } // 应用颜色
              },
              {
                value: this.classList[this.currentClass].isEntered,
                name: "已录入成绩",
                itemStyle: { color: colors[1] } // 应用颜色
              },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      this.base_info_right_option = {
        tooltip: {
          trigger: "item",
        },
        series: [
          {
            name: "学生评阅情况",
            type: "pie",
            radius: "50%",
            data: [
              {
                value:
                  this.classList[this.currentClass].totalNum -
                  this.classList[this.currentClass].isReview,
                name: "未评阅",
                itemStyle: { color: colors[0] } // 应用颜色
              },
              {
                value: this.classList[this.currentClass].isReview,
                name: "已评阅",
                itemStyle: { color: colors[1] } // 应用颜色
              },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      base_info_left.setOption(this.base_info_left_option);
      base_info_right.setOption(this.base_info_right_option);
    },
    async fetchClassRateData() {
      try {
        const response = await getClassRate({
          grade: this.selectedGrade,
          term: this.selectedTerm,
        }); // 调用 getClassScore 方法并等待结果
        console.log("拿到班级比例", response.data);
        this.classList = response.data;

        this.currentClass = 0;
        this.initChart();
      } catch (error) {
        // 捕获请求失败的错误
        this.error = error;
        console.error("班级比例 Error fetching data:", error);
      }
    },
  },
  mounted() {
    this.fetchClassRateData();
    this.initChart();
  },
  watch: {
    selectedGrade() {
      this.fetchClassRateData();
    },
    selectedTerm() {
      this.fetchClassRateData();
    },
    currentClass() {
      this.initChart();
    },
  },
};
</script>

<style scoped>
.container {
  width: 800px;
  height: 350px;
  padding: 0;
  margin: 0;
}
.chart-header {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid rgb(7, 6, 6);
  align-items: center;
  padding: 0 20px;
}
.chart-header .title {
  font-size: 20px;
  font-weight: 600;
}
.chart-body {
  padding: 0 !important;
  display: flex;
  height: 500px;
  padding-top: 30px;
}
</style>
