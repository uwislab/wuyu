<template>
  <div style="padding:20px;">
    <el-tabs type="border-card" tab-position="left" style="height: calc(100% - 50px);" value="1" @tab-click="handleClick">
      <el-tab-pane label="各班级平均分变化趋势" name="1">
        <el-row :gutter="20">
          <el-col :span="12" style="margin-bottom: 20px;" v-for="(type,index) in typeList1" :key="index">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>{{type}}各班平均分变化趋势</span>
              </div>
              <div :id="'id1_'+index" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="各年级五育分变化趋势" name="2">
        <el-row :gutter="20" style="margin-bottom: 20px;">
          <el-col :span="12">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>德育各年级变化趋势</span>
              </div>
              <div id="id0" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>智育各年级变化趋势</span>
              </div>
              <div id="id1" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="20"  style="margin-bottom: 20px;">
          <el-col :span="12">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>体育各年级变化趋势</span>
              </div>
              <div id="id2" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>美育各年级变化趋势</span>
              </div>
              <div id="id3" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="20"  style="margin-bottom: 20px;">
          <el-col :span="12">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>劳育各年级变化趋势</span>
              </div>
              <div id="id4" style="width: 100%;height:300px;"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import api from '@/api/demonstrate/api'
import echarts from 'echarts'
export default {
  data() {
    return {
      type:"智体美劳",
      typeList: ['德','智','体','美','劳'],
      typeEnList: ['deyu','zhiyu','tiyu','meiyu','laoyu'],
      idList: ['id0','id1','id2','id3','id4'],
      colorList: ['#1b6cd2','#B5C334','#FCCE10','#E87C25','#27727B'],

      typeList1: ['一年级','二年级','三年级','四年级','五年级','六年级'],
      idList1: ['id0','id1','id2','id3','id4','id5'],
      colorList1: ['#1b6cd2','#B5C334','#FCCE10','rgba(144,26,194,0.74)','#27727B','#901AC2BC']
    }
  },
  mounted() {
    this.handleClick({name:1});
  },
  methods: {
    handleClick(data) {
      const that = this;
      if (data.name == 1) {
        api.getLineTj().then(e=>{
          this.typeList1=e.gradeList;
          setTimeout(function() {
            for(var i=0;i<that.typeList1.length;i++){
              that.initChart1('id1_'+i,that.typeList1[i],that.colorList1[i]
                ,e[that.typeList1[i]]
                ,e[that.typeList1[i]+'_riqi']
                ,e[that.typeList1[i]+'__fenshu']);
            }
          }, 0);
        })
      }
      if (data.name == 2) {
        const that = this;
        api.getLineTj2().then(e=>{
          setTimeout(function() {
            for(var i=0;i<that.typeList.length;i++){
              that.initChart('id'+i,that.typeList[i],that.colorList[i]
                ,e["gradeList"]
                ,e[e["gradeList"][i]+'riqi']
                ,e[that.typeEnList[i]]);
            }
          }, 500);
        })
      }
    },
    initChart(divId,index,myColor,legendData,riqi,series){
      for(var i=0;i<series.length;i++){
        series[i]['itemStyle']={
          normal:{
            label : {
              show: true // 在折线拐点上显示数据
            }
          }
        };
      }
      const myChart = echarts.init(document.getElementById(divId))
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: legendData
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: riqi
        },
        yAxis: {
          type: 'value',
          min:60
        },
        series: series
      };
      myChart.setOption(option);
      window.onresize = function() {
        myChart.resize();
      };
    },
    initChart1(divId,index,myColor,legendData,riqiData,series){
      for(var i=0;i<series.length;i++){
        series[i]['itemStyle']={
          normal:{
            label : {
              show: true // 在折线拐点上显示数据
            }
          }
        };
      }
      const that =this;
      const myChart = echarts.init(document.getElementById(divId))
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: legendData
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: riqiData
        },
        yAxis: {
          type: 'value',
          name: '分数',
          min:60
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
.el-tabs__content{
  height: calc(100vh - 110px);
  overflow-y: auto;
}
</style>
