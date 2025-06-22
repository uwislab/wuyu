<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>  <div style="padding:20px;">
    <el-tabs type="border-card" tab-position="left" style="height: calc(100% - 50px);" value="0" @tab-click="handleClick">
      <el-tab-pane label="德育" name="0">
        <el-row :gutter="20">
          <el-col :span="12" style="margin-bottom: 20px;" v-for="(type, index) in typeList" :key="index">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>{{ type }}德育各班分变化趋势</span>
                <el-button style="float: right;padding: 2px;" size="mini" type="success" icon="el-icon-download" circle
                           @click="down(index)"></el-button>
              </div>
              <div :id="'id0_' + index" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="智育" name="1">
        <el-row :gutter="20">
          <el-col :span="12" style="margin-bottom: 20px;" v-for="(type, index) in typeList" :key="index">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>{{ type }}智育各班分变化趋势</span>
                <el-button style="float: right;padding: 2px;" size="mini" type="success" icon="el-icon-download" circle
                           @click="down(index)"></el-button>
              </div>
              <div :id="'id1_' + index" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="体育" name="2">
        <el-row :gutter="20">
          <el-col :span="12" style="margin-bottom: 20px;" v-for="(type, index) in typeList" :key="index">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>{{ type }}体育各班分变化趋势</span>
                <el-button style="float: right;padding: 2px;" size="mini" type="success" icon="el-icon-download" circle
                           @click="down(index)"></el-button>
              </div>
              <div :id="'id2_' + index" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="美育" name="3">
        <el-row :gutter="20">
          <el-col :span="12" style="margin-bottom: 20px;" v-for="(type, index) in typeList" :key="index">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>{{ type }}美育各班分变化趋势</span>
                <el-button style="float: right;padding: 2px;" size="mini" type="success" icon="el-icon-download" circle
                           @click="down(index)"></el-button>
              </div>
              <div :id="'id3_' + index" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="劳育" name="4">
        <el-row :gutter="20">
          <el-col :span="12" style="margin-bottom: 20px;" v-for="(type, index) in typeList" :key="index">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>{{ type }}劳育各班分变化趋势</span>
                <el-button style="float: right;padding: 2px;" size="mini" type="success" icon="el-icon-download" circle
                           @click="down(index)"></el-button>
              </div>
              <div :id="'id4_' + index" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div></el-container>
  </el-container>

</template>
<script>
import api from '@/api/demonstrate/api'
import echarts from 'echarts'
import FileSaver from 'file-saver'
import htmlDocx from "html-docx-js/dist/html-docx"
import Aside from "@/views/teacher_workspace/Aside"
export default {
  data() {
    return {
      typeList: ['二年级', '三年级', '四年级', '五年级', '六年级'],
      idList: ['id0', 'id1', 'id2', 'id3', 'id4', 'id5'],
      colorList: ['#1b6cd2', '#B5C334', '#FCCE10', 'rgba(144,26,194,0.74)', '#27727B', '#901AC2BC'],
      chartList: []
    }
  },
  components: {
      Aside
    },
  mounted() {
    this.handleClick({ name: 0 });
  },
  methods: {
    down(type) {
      this.$message('努力的下载中。。。。');
      var charts = this.chartList[type];
      let baseUrl = charts.getDataURL({
        pixelRatio: 2
      });
      let content = `<!DOCTYPE html><html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            </head>
            <body>
                <img src="${baseUrl}" width="623" height="300" />
            </body>
            </html>`;
      let converted = htmlDocx.asBlob(content);
      FileSaver.saveAs(converted, '输出报告' + new Date().getTime() + '.docx');
    },
    handleClick(data) {
      this.chartList = [];
      const that = this;
      api.getNjcz({ type: data.name }).then(e => {
        this.typeList = e.gradeList;
        setTimeout(function () {
          for (var i = 0; i < that.typeList.length; i++) {
            let xuenianList = []
            e[that.typeList[i] + '_riqi'].forEach(xuenian => {
              xuenianList.push(xuenian - 1 + "-" + xuenian + "学年")
            });
            that.initChart('id' + data.name + '_' + i, that.typeList[i], that.colorList[i]
              , e[that.typeList[i]]
              , xuenianList
              , e[that.typeList[i] + '__fenshu']);
          }
        }, 0);
      })
    },
    initChart(divId, index, myColor, legendData, riqiData, series) {
      for (var i = 0; i < series.length; i++) {
        series[i]['itemStyle'] = {
          normal: {
            label: {
              show: true // 在折线拐点上显示数据
            }
          }
        };
      }
      const that = this;
      const myChart = echarts.init(document.getElementById(divId))
      this.chartList.push(myChart);
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: legendData
        },
        xAxis: {
          type: 'category',
          // boundaryGap: false,
          data: riqiData,
        },
        yAxis: {
          type: 'value',
          name: '分数',
          min: 50
        },
        series: series
      };
      myChart.setOption(option)
      this.$nextTick(() => {
        myChart.resize();
      });
    }
  },
}
</script>
<style>
.el-tabs__content {
  height: calc(100vh - 110px);
  overflow-y: auto;
}
</style>
