<template>
  <div class="container">
    <div class="chart-header">
      <div class="title">各个分数区间人数分布情况</div>
      <div class="detail-rate">
        已录入<span class="is-entered"> {{ isEntered }} </span>/ 全部<span
          class="total"
        >
          {{ totalNum }}
        </span>
      </div>
      <el-select v-model="selectedIndex" placeholder="请选择" class="selector">
        <el-option
          v-for="item in selectList"
          :key="item.index"
          :label="item.title"
          :value="item.index"
        >
        </el-option>
      </el-select>
    </div>
    <div class="chart-body" style="display: flex;justify-content: space-between;height: 400px;padding-left: 400px;">
        <div ref="grade_histogram" style="width: 500px; height: 400px"></div>
        <div ref="grade_pie_chart" style="width: 500px; height: 400px"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getStudentSorce, getStudentRate } from "@/api/overview";
export default {
  name: "overview-stuchart",
  props: ["selectedGrade", "selectedClass", "selectedTerm"],
  data() {
    return {
      histogram_option: {},
      showList: [
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
      ],
      selectList: [
        { title: "德育", index: 0 },
        { title: "智育", index: 1 },
        { title: "体育", index: 2 },
        { title: "美育", index: 3 },
        { title: "劳育", index: 4 },
      ],
      selectedIndex: 0,

      tableData: [],
      isEntered: 0,
      totalNum: 0,
    };
  },
  methods: {
    initChart() {
      let gradeHistogram = echarts.init(this.$refs.grade_histogram);
      this.histogram_option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        xAxis: {
          type: "category",
          name: "分数",
          data: ["0-20", "20-40", "40-60", "60-80", "80-100"],
          axisLine: {
            show: true,
            symbol: ["none", "arrow"],
          },
          axisTick: {
            show: false,
          },
        },
        yAxis: {
          type: "value",
          name: "人数",
          axisLine: {
            show: true,
            symbol: ["none", "arrow"],
          },
          splitLine: {
            show: false,
          },
        },

        series: [
          {
            name: "人数",
            type: "bar",
            barWidth: "45%",
            data: [
              this.showList[this.selectedIndex]["0-20"],
              this.showList[this.selectedIndex]["20-40"],
              this.showList[this.selectedIndex]["40-60"],
              this.showList[this.selectedIndex]["60-80"],
              this.showList[this.selectedIndex]["80-100"],
            ],
            itemStyle: {
              // color: '#4CAF50',
              // 完善颜色
              // 完善提交
              normal: {
                color: function(params) {
                  var colorList = ['#0000FF', '#FFA500', '#FFFF00', '#00FF00', '#FF0000'];
                  return colorList[params.dataIndex];
                }
              }
            }
          },
        ],
      };

      gradeHistogram.setOption(this.histogram_option);

      let gradePieChart = echarts.init(this.$refs.grade_pie_chart); // 假设你有一个新的引用用于饼图
      this.pieChartOption = {
      title: {
      text: '分数段人数分布',
      left: 'center'
      },
      tooltip: {
      trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: ["0-20", "20-40", "40-60", "60-80", "80-100"]
      },
      series: [
        {
          name: '人数',
          type: 'pie',
          radius: '50%', // 可以调整为'55%'创建环形图效果
          data: [
            { value: this.showList[this.selectedIndex]["0-20"], name: "0-20" },
            { value: this.showList[this.selectedIndex]["20-40"], name: "20-40" },
            { value: this.showList[this.selectedIndex]["40-60"], name: "40-60" },
            { value: this.showList[this.selectedIndex]["60-80"], name: "60-80" },
            { value: this.showList[this.selectedIndex]["80-100"], name: "80-100" }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          labelLine: {
            show: true
          },
          label: {
            show: true,
            formatter: '{b}: {c} ({d}%)' // 显示标签，包括分类名、数值和百分比
          },
          itemStyle: {
            normal: {
              color: function(params) {
                var colorList = ['#0000FF', '#FFA500', '#FFFF00', '#00FF00', '#FF0000'];
                return colorList[params.dataIndex];
              }
            }
          }
        }
      ]
    };
    gradePieChart.setOption(this.pieChartOption);
    },

    //请求方法
    async fetchStudentScoreData() {
      try {
        const response = await getStudentSorce({
          stuGrade: this.selectedGrade,
          stuClass: this.selectedClass,
          stuTerm: this.selectedTerm,
        });
        console.log("拿到学生信息", response.data);
        return response.data;
      } catch (error) {
        // 捕获请求失败的错误
        this.error = error;
        console.error("学生信息 Error fetching data:", error);
      }
    },

    async fetchStudentRateData() {
      try {
        const response = await getStudentRate({
          stuGrade: this.selectedGrade,
          stuClass: this.selectedClass,
          stuTerm: this.selectedTerm,
        });
        console.log("拿到学生比例", response.data);
        return response.data;
      } catch (error) {
        // 捕获请求失败的错误
        this.error = error;
        console.error("学生比例 Error fetching data:", error);
      }
    },

    async getData() {
      //获取数据
      this.tableData = await this.fetchStudentScoreData();
      const response = await this.fetchStudentRateData();
      this.isEntered = response.isEntered;
      this.totalNum = response.totalNum;
      console.log(
        "响应式数据更新",
        this.tableData,
        this.isEntered,
        this.totalNum
      );
      this.showList = [
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
        { "0-20": 0, "20-40": 0, "40-60": 0, "60-80": 0, "80-100": 0 },
      ];
      this.tableData.forEach((student) => {
        this.scoreCounter("moralityScore", student.moralityScore);
        this.scoreCounter("intelligenceScore", student.intelligenceScore);
        this.scoreCounter("physicalScore", student.physicalScore);
        this.scoreCounter("aestheticScore", student.aestheticScore);
        this.scoreCounter("labourScore", student.labourScore);
      });
      this.initChart();
    },
    scoreCounter(type, score) {
      let index = [
        "moralityScore",
        "intelligenceScore",
        "physicalScore",
        "aestheticScore",
        "labourScore",
      ].indexOf(type);
      if (score > 80 && score <= 100) this.showList[index]["80-100"]++;
      else if (score > 60 && score <= 80) this.showList[index]["60-80"]++;
      else if (score > 40 && score <= 60) this.showList[index]["40-60"]++;
      else if (score > 20 && score <= 40) this.showList[index]["20-40"]++;
      else this.showList[index]["0-20"]++;
    },
  },

  mounted() {
    this.getData();
  },
  watch: {
    // 更改年级和班级时重新获取数据
    selectedGrade() {
      this.getData();
      this.selectedIndex = 0;
    },
    selectedClass() {
      this.getData();
      this.selectedIndex = 0;
    },
    selectedTerm() {
      this.getData();
      this.selectedIndex = 0;
    },

    selectedIndex() {
      this.initChart();
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 550px;
  height: 500px;
  padding: 0;
  margin: 0;
}
.chart-header {
  width: 100%;
  margin: 20px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4px 10px;
}
.title {
  font-weight: 600;
  font-size: 20px;
}
.is-entered {
  font-weight: 600;
  font-size: 16px;
  color: rgb(152, 195, 121);
}
.total {
  font-weight: 600;
  font-size: 16px;
  color: rgb(0, 135, 246);
}
.selector {
  width: 80px;
}
</style>
