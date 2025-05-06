<template>
  <div className="Home3">
    <div style="margin-left: 200px">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-form ref="form" :model="form" label-width="60px">
            <el-form-item >
              输入查询ip：
              <el-input v-model="form.ip">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button  @click="myEcharts2">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div id="myChart"></div>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import request from "../utils/request";
export default {
  name: 'Home4',
  data () {
    return {
      form: {},
      // 实时数据数组
      date: [],
      _timeOut:false,
      yieldRate: [],
      // 折线图echarts初始化选项
      echartsOption: {

        legend: {
          data: ['百分比'],

        },

        xAxis: {

          name: '时间',
          nameTextStyle: {

            fontWeight: 600,
            fontSize: 18

          },

          type: 'category',
          boundaryGap: false,
          data: this.date,	// 绑定实时数据数组

        },

        yAxis: {

          name: '内存利用率',
          nameTextStyle: {

            fontWeight: 600,
            fontSize: 18

          },
          type: 'value',
          scale: true,
          boundaryGap: ['15%', '15%'],
          axisLabel: {

            interval: 'auto',
            formatter: '{value} %'

          }
        },
        tooltip: {

          trigger: 'axis',

        },
        series: [

          {
            name:'内存利用率',
            type:'line',
            smooth: true,
            data: this.yieldRate,	// 绑定实时数据数组
          }

        ]

      }
    }
  },
  methods: {

    myEcharts2(){

      this.myChart = this.$echarts.init(document.getElementById('myChart'), 'light');	// 初始化echarts, theme为light
      this.myChart.setOption(this.echartsOption);	// echarts设置初始化选项
      this._timeOut =setInterval(this.addData, 3000);	// 每三秒更新实时数据到折线图

    },
    // 添加实时数据
    addData : function() {
      // 从接口获取数据并添加到数组
      request.post("http://36.111.68.174:33380/user4/ip",this.form).then(res =>{
        if(res.data==null){

        }else {

          console.log(this.form)
          this.yieldRate=res.data.dat;
          this.date=res.data.bai;
          console.log(this.yieldRate)
          console.log(this.date)
          this.echartsOption.xAxis.data = this.date;
          this.echartsOption.series[0].data = this.yieldRate;
          this.myChart.setOption(this.echartsOption);

        }

      })

    }

  },

  beforeDestroy() {

    if (this._timeOut) {

      clearInterval(this._timeOut);

    }

  }

}
</script>

<style>

#myChart{
  width: 100%;
  height: 500px;
  margin: 0 auto;
}
</style>
