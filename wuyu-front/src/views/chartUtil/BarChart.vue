<template>
  <div
    :class="className"
    :autoresize="true"
    :style="{ height: height, width: width }"
  />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";

const animationDuration = 6000;

export default {
  mixins: [resize],
  props: {
    cdata: {
      type: Object,
      default: () => ({}),
    },
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "400px",
    },
  },
  data() {
    return {
      chart: null,
      options: {},
    };
  },
  // 监听cdata异步加载
  watch: {
    cdata: {
      handler(newData) {
        // 设置option
        this.setOpt(newData);
        // 重新渲染， true是清除旧数据
        this.initChart();
      },
      deep: true,
    },
  },
  //重新计算echarts的宽度
  calculateEcharts() {
    //获取这个组件的窗口宽度
    this.initWidth = document.getElementById("staticWidth").offsetWidth;
    console.log(this.initWidth);
  },

  mounted() {
    this.initChart();
  },

  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    // 初始化echart
    initChart() {
      let _this = this;
      _this.chart = echarts.init(_this.$el, "macarons");
      _this.chart.setOption(_this.options, true);
      _this.chart.resize();
    },
    // 设置option
    setOpt(newData) {
      this.options = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        grid: {
          top: 10,
          left: "2%",
          right: "2%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: newData.xData,
            axisTick: {
              alignWithLabel: true,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisTick: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: newData.seriesName,
            type: "bar",
            stack: "vistors",
            barWidth: "60%",
            data: newData.seriesData,
            animationDuration,
          },
        ],
      };
    },
  },
};
</script>
<style >
</style>
