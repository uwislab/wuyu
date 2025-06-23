<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";

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
      this.chart = echarts.init(this.$el, "macarons");
      this.chart.setOption(this.options, true);
    },
    // 设置option
    setOpt(newData) {
      this.options = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          left: "center",
          bottom: "10",
          data: newData.xData,
        },
        series: [
          {
            name: newData.seriesName,
            type: "pie",
            roseType: "radius",
            radius: [15, 95],
            center: ["50%", "38%"],
            data: newData.seriesData,
            animationEasing: "cubicInOut",
            animationDuration: 2600,
          },
        ],
      };
    },
  },
};
</script>
