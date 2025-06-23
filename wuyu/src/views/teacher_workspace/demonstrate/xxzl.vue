<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>
  <div style="padding:20px;">
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>学校总览</span>
          </div>
          <div>
            <div>
              <el-select v-model="select" multiple placeholder="请选择" style="width: 100%" @change="myChange">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </div>
            <div id="id0" style="width: 100%;height:300px;margin-top: 20px;"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</el-container>
  </el-container>

</template>
<script>
import Aside from "@/views/teacher_workspace/Aside"
import api from '@/api/demonstrate/api'
import echarts from 'echarts'
export default {
  data() {
    return {
      options: [{
        value: '一年级',
        label: '一年级'
      }, {
        value: '二年级',
        label: '二年级'
      }, {
        value: '三年级',
        label: '三年级'
      }, {
        value: '四年级',
        label: '四年级'
      }, {
        value: '五年级',
        label: '五年级'
      }, {
        value: '六年级',
        label: '六年级'
      }],
      select: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      type: "德智体美劳",
      typeList: ['德', '智', '体', '美', '劳'],
      idList: ['id0', 'id1', 'id2', 'id3', 'id4'],
      colorList: ['#1b6cd2', '#B5C334', '#FCCE10', '#E87C25', '#27727B'],
      dataList: [[], [], [], [], []]
    }
  },
  components: {
      Aside
    },
  mounted() {
    api.getXxzl().then(e => {
      if (e.xAxisData) {
        var xAxisData = e.xAxisData;
        this.dataList[0] = {
          name: '德育',
          data: e.deyuData,
          type: 'bar',
          label: {
            show: true,
            position: 'outside'
          }
        }
        this.dataList[1] = {
          name: '智育',
          data: e.zhiyuData,
          type: 'bar',
          label: {
            show: true,
            position: 'outside'
          }
        }
        this.dataList[2] = {
          name: '体育',
          data: e.tiyuData,
          type: 'bar',
          label: {
            show: true,
            position: 'outside'
          }
        }
        this.dataList[3] = {
          name: '美育',
          data: e.meiyuData,
          type: 'bar',
          label: {
            show: true,
            position: 'outside'
          }
        }
        this.dataList[4] = {
          name: '劳育',
          data: e.laoyuData,
          type: 'bar',
          label: {
            show: true,
            position: 'outside'
          }
        }
        this.initChart("id0", "", this.colorList[0], xAxisData, this.dataList);
      }
    })
  },
  methods: {
    myChange(value) {
      api.getXxzl({ grade: value.join(",") }).then(e => {
        if (e.xAxisData) {
          var xAxisData = e.xAxisData;
          this.dataList[0] = {
            name: '德育',
            data: e.deyuData,
            type: 'bar',
            label: {
              show: true,
              position: 'outside'
            },
          }
          this.dataList[1] = {
            name: '智育',
            data: e.zhiyuData,
            type: 'bar',
            label: {
              show: true,
              position: 'outside'
            }
          }
          this.dataList[2] = {
            name: '体育',
            data: e.tiyuData,
            type: 'bar',
            label: {
              show: true,
              position: 'outside'
            }
          }
          this.dataList[3] = {
            name: '美育',
            data: e.meiyuData,
            type: 'bar',
            label: {
              show: true,
              position: 'outside'
            }
          }
          this.dataList[4] = {
            name: '劳育',
            data: e.laoyuData,
            type: 'bar',
            label: {
              show: true,
              position: 'outside'
            }
          }
          this.initChart("id0", "", this.colorList[0], xAxisData, this.dataList);
        }
      })
    },
    initChart(divId, index, myColor, xData, data) {
      const that = this;
      const myChart = echarts.init(document.getElementById(divId))
      const option = {
        legend: {
          data: ['德育', '智育', '体育', '美育', '劳育']
        },
        xAxis: {
          type: 'category',
          data: xData
        },
        yAxis: {
          type: 'value',
          name: '分数',
          min: 0,
          max: 100
        },
        tooltip: {
          trigger: 'axis', //坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用
          axisPointer: {// 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        series: data
      };
      myChart.setOption(option)
      myChart.on('click', function (params) {
        console.log(params);
        that.$router.push({
          path: 'xxzs',
          query: {
            param: params.name,
            range: 'grade'
          }
        })
      })
      this.$nextTick(() => {
        myChart.resize();
      });
    }
  }
}
</script>
