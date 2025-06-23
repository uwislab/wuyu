<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>  
  <div class="app-container">
    <el-tabs type="border-card" tab-position="left" style="height: calc(100% - 50px);" value="0">
      <el-tab-pane label="一年级">
        <el-row :gutter="24" style="padding-bottom: 10px;">
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班德育情况</span>
              </div> -->
              <div class="echart" id="mychart1" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班智育情况</span>
              </div> -->
              <div class="echart" id="mychart2" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="二年级">
        <el-row :gutter="24" style="padding-bottom: 10px;">
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班德育情况</span>
              </div> -->
              <div class="echart" id="mychart3" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班智育情况</span>
              </div> -->
              <div class="echart" id="mychart4" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="三年级">
        <el-row :gutter="24" style="padding-bottom: 10px;">
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班德育情况</span>
              </div> -->
              <div class="echart" id="mychart5" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班智育情况</span>
              </div> -->
              <div class="echart" id="mychart6" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="四年级">
        <el-row :gutter="24" style="padding-bottom: 10px;">
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班德育情况</span>
              </div> -->
              <div class="echart" id="mychart7" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班智育情况</span>
              </div> -->
              <div class="echart" id="mychart8" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="五年级">
        <el-row :gutter="24" style="padding-bottom: 10px;">
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班德育情况</span>
              </div> -->
              <div class="echart" id="mychart9" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班智育情况</span>
              </div> -->
              <div class="echart" id="mychart10" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="六年级">
        <el-row :gutter="24" style="padding-bottom: 10px;">
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班德育情况</span>
              </div> -->
              <div class="echart" id="mychart11" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
          <el-col :span="12" :xs="11"
                  style="height: 360px;display: flex;justify-content: center;align-items: center;">
            <el-card class="box-card" style="height: 100%;width:100%">
              <!-- <div slot="header" class="clearfix">
                  <span>各年级各班智育情况</span>
              </div> -->
              <div class="echart" id="mychart12" style="float: left; width: 500px; height: 300px"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div></el-container>
  </el-container>

</template>
<script>
import Aside from "@/views/teacher_workspace/Aside"
import api from '@/api/demonstrate/api'
import * as echarts from "echarts";
export default {
  name: "index",
  data() {
    return {
      classList: [],
    };
  },
  mounted() {
    this.getData();
  },
  components: {
      Aside
    },
  methods: {
    getData() {
      let that = this
      api.getNjqk()
        .then(function (res) {
          // console.log(res.data.class);
          that.classList = res.class;
          that.initEcharts();
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    initEcharts() {
      let that = this;
      // 多列柱状图
      for (let i = 1; i <= this.classList.length; i++) {
        const myChartData = {
          title: {
            text: that.classList[i - 1].class + "  总分:" + that.classList[i - 1].avg_score,
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
                  value: that.classList[i - 1].deyu,
                  name: '德育',
                  className: that.classList[i - 1].class
                },
                {
                  value: that.classList[i - 1].zhiyu,
                  name: '智育',
                  className: that.classList[i - 1].class
                },
                {
                  value: that.classList[i - 1].tiyu,
                  name: '体育',
                  className: that.classList[i - 1].class
                },
                {
                  value: that.classList[i - 1].meiyu,
                  name: '美育',
                  className: that.classList[i - 1].class
                },
                {
                  value: that.classList[i - 1].laoyu,
                  name: '劳育',
                  className: that.classList[i - 1].class
                },
              ]
            }
          ],
        };
        const myChart = echarts.init(document.getElementById(`mychart${i}`));
        myChart.setOption(myChartData);
        myChart.on('click', function (params) {
          console.log(params.data.className);
          that.$router.push({
            path: 'xxzs',
            query: {
              param: params.data.className,
              range: 'class'
            }
          })
        })
        //随着屏幕大小调节图表
        window.addEventListener("resize", () => {
          myChart.resize();
        });
      }
    },
  }
}
</script>
<style scoped>
.app-container{
  padding: 10px;
}
</style>
