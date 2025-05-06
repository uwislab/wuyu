<template>
  <div class="app-container">
    <!-- 我草死你妈的，之前写代码的人在这里复制粘贴了六个年级，每个年级必须有两个班，多了少了都是错 -->
    <!-- 你敢相信，这个地方之前全是在复制粘贴，一次重构删除了171行代码，嘻嘻 -->
    <el-tabs type="border-card" tab-position="left" style="height: calc(100% - 50px); width: 100%;" value="1"
      @tab-click="tab => changeGrade(tab.name)">
      <el-tab-pane v-for="i of 6" :key="i" :name="i.toString()" :label="getGrade(i)" v-if="!loading">
        <div class="card" v-for="j in classList[i].length" :key="`${i}-${j}`">
          <div :id="`${i}-${j}`" style="width: 100%; height:700px;"></div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import api from '@/api/demonstrate/api'
import * as echarts from "echarts";
export default {
  name: "index",
  data() {
    return {
      classList: [],
      chartList: [],
      loading: true
    };
  },
  // 他妈的，之前写代码的人连async await都不会
  async mounted() {
    await this.getData()
    this.changeGrade(1)
  },
  methods: {
    getGrade(i) {
      let res = ''
      switch (i) {
        case 1:
          res = "一年级"
          break
        case 2:
          res = "二年级"
          break
        case 3:
          res = "三年级"
          break
        case 4:
          res = "四年级"
          break
        case 5:
          res = "五年级"
          break
        case 6:
          res = "六年级"
          break
        default:
          res = "Unknown"
      }
      return res
    },
    async getData() {
      this.loading = true
      try {
        const res = (await api.getNjqk()).class
        res.forEach(item => {
          if (!this.classList[item.grade]) this.classList[item.grade] = []
          this.classList[item.grade].push(item)
        })
      } catch (e) {
        console.log(e)
      }
      this.loading = false
    },
    changeGrade(i) {
      this.destroyCharts()
      this.initEcharts(i)
      // 神奇bug，应该是elui的问题
      setTimeout(() => this.chartList.forEach(chart => chart.resize()), 1)
    },
    initEcharts(grade) {
      // 多列柱状图
      for (let i = 0; i < this.classList[grade].length; i++) {
        const myChartData = {
          title: {
            text: `${this.getGrade(parseInt(this.classList[grade][i].grade))} ${this.classList[grade][i].class_name}  总分:${this.classList[grade][i].avg_score}`,
          },
          legend: {
            orient: 'vertical',
            right: '5%'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b} : {c} 分' // <br/>换行
            //a:（系列名称） b:(数据名称) c:(数值) d:(饼图百分比)
          },
          series: [
            {
              type: 'pie',
              radius: ["20%", "80%"],
              top: '50%',
              center: ['50%', '60%'],
              roseType: 'area',
              itemStyle: {
                borderRadius: 8
              },
              data: [
                {
                  value: this.classList[grade][i].deyu,
                  name: '德育',
                  className: this.classList[grade][i].class
                },
                {
                  value: this.classList[grade][i].zhiyu,
                  name: '智育',
                  className: this.classList[grade][i].class
                },
                {
                  value: this.classList[grade][i].tiyu,
                  name: '体育',
                  className: this.classList[grade][i].class
                },
                {
                  value: this.classList[grade][i].meiyu,
                  name: '美育',
                  className: this.classList[grade][i].class
                },
                {
                  value: this.classList[grade][i].laoyu,
                  name: '劳育',
                  className: this.classList[grade][i].class
                },
              ]
            }
          ],
        };
        const chart = echarts.init(document.getElementById(`${grade}-${i + 1}`));
        chart.setOption(myChartData);
        chart.on('click', function (params) {
          console.log(params.data.className);
          this.$router.push({
            path: 'xxzs',
            query: {
              param: params.data.className,
              range: 'class'
            }
          })
        })
        this.chartList.push(chart)
      }
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => this.chartList.forEach(chart => chart.resize()));
    },
    destroyCharts() {
      for (let chart of this.chartList)
        if (chart) chart.dispose()
      window.removeEventListener("resize", () => this.chartList.forEach(chart => chart.resize()));
      this.chartList = []
    }
  },
  // 在生命周期末销毁图表，防止内存泄漏
  beforeDestroy() {
    this.destroyCharts()
  },
}
</script>

<style scoped>
.card {
  width: 100%;
  box-shadow: 3px 4px 10px rgba(0, 0, 0, 0.3);
  margin: 10px 0;
}
</style>
