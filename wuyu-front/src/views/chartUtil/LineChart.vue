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
    autoResize: {
      type: Boolean,
      default: true,
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
    this.$nextTick(() => {
      this.initChart();
    });
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
      this.chart.resize();
    },
    // 设置option
    setOpt(newData) {
      this.options = {
        xAxis: {
          data: newData.xData,
          boundaryGap: false,
          axisTick: {
            show: false,
          },
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
          },
          padding: [5, 10],
        },
        yAxis: {
          axisTick: {
            show: false,
          },
        },
        legend: {
          data: newData.xData,
        },
        series: [
          {
            name: newData.seriesName,
            itemStyle: {
              normal: {
                color: "#FF005A",
                lineStyle: {
                  color: "#FF005A",
                  width: 2,
                },
              },
            },
            smooth: true,
            type: "line",
            data: newData.seriesData,
            animationDuration: 2800,
            animationEasing: "cubicInOut",
          },
        ],
      };
    },
  },
};
</script>
<style >
</style>
