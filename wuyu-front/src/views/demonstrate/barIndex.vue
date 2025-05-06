<template>
  <div style="padding:20px;">
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>德育按照年级展现</span>
          </div>
          <div id="id0" style="width: 100%;height:300px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>智育按照年级展现</span>
          </div>
          <div id="id1" style="width: 100%;height:300px;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20"  style="margin-bottom: 20px;">
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>体育按照年级展现</span>
          </div>
          <div id="id2" style="width: 100%;height:300px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>美育按照年级展现</span>
          </div>
          <div id="id3" style="width: 100%;height:300px;"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20"  style="margin-bottom: 20px;">
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>劳育按照年级展现</span>
          </div>
          <div id="id4" style="width: 100%;height:300px;"></div>
        </el-card>
      </el-col>
    </el-row>
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
        idList: ['id0','id1','id2','id3','id4'],
        colorList: ['#1b6cd2','#B5C334','#FCCE10','#E87C25','#27727B'],
        dataList:[[],[],[],[],[]]

      }
    },
    mounted() {
      api.getXxzl().then(e=>{
        if(e.xAxisData){
          var xAxisData=e.xAxisData;
          this.dataList[0]=e.deyuData;
          this.dataList[1]=e.zhiyuData;
          this.dataList[2]=e.tiyuData;
          this.dataList[3]=e.meiyuData;
          this.dataList[4]=e.laoyuData;
          for(var i=0;i<this.typeList.length;i++){
            this.initChart(this.idList[i],this.typeList[i],this.colorList[i],xAxisData,this.dataList[i]);
          }
        }
      })

    },
    methods: {
      initChart(divId,index,myColor,xData,data){
        const that =this;
        const myChart = echarts.init(document.getElementById(divId))
        const option = {
          xAxis: {
            type: 'category',
            data: xData
          },
          yAxis: {
            type: 'value',
            name: '分数',
            min:0,
            max:100
          },
          series: [
            {
              data: data,
              type: 'bar',
              itemStyle: {
                color: myColor
              },
              label: {
                show: true,
                position: 'outside'
              }
            }
          ]
        };
        myChart.setOption(option)
      }

    }
  }
</script>
