<template>
  <body>
    <div>
      <el-button type="success" plain icon="el-icon-check" @click="showcharts">查看统计图表</el-button>
    </div>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="70%">

<!--      &lt;!&ndash; 表一 &ndash;&gt;-->
<!--      <div id="chart1" style="width: 600px;height:600px;"></div>-->
<!--      &lt;!&ndash; 表二 &ndash;&gt;-->
<!--      <div id="chart2" style="width: 500px;height:350px;"></div>-->
      <el-row :gutter="0">
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart3" style="width: 500px;height:350px;"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart2" style="width: 500px;height:350px;"></div>
          </el-card>
        </el-col>
      </el-row>
<!--      &lt;!&ndash; 表三 &ndash;&gt;-->
<!--      <div id="chart3" style="width: 500px;height:350px;"></div>-->
<!--      &lt;!&ndash; 表四 &ndash;&gt;-->
<!--      <div id="chart4" style="width: 500px;height:350px;"></div>-->
      <el-row :gutter="0">
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart1" style="width: 500px;height:350px;"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart4" style="width: 500px;height:350px;"></div>
          </el-card>
        </el-col>
      </el-row>

    </el-dialog>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键词" style="width:20%" clearable></el-input>
      <el-button type="primary" plain icon="el-icon-search" @click="load">查询</el-button>
    </div>
    <!--  反馈表格  -->
    <div>
      <el-table
          :data="tableData"
          stripe
          style="width: 100%">
        <el-table-column
            prop="sid"
            label="学号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="rate"
            label="评分">
<!--            <el-rate-->
<!--                v-model="value2"-->
<!--                :colors="colors">-->
<!--            </el-rate>-->
        </el-table-column>
        <el-table-column
            prop="fobject"
            label="最佳课程">
        </el-table-column>
        <el-table-column
            prop="hobject"
            label="可提升课程">
        </el-table-column>
        <el-table-column
            prop="feedback"
            label="反馈">
        </el-table-column>
<!--        <el-table-column-->
<!--          fixed="right"-->
<!--          label="操作"-->
<!--          width="100%">-->
<!--          <template slot-scope="scope">-->
<!--            <el-button type="text" icon="el-icon-edit"  size="small" @click="handleEdit(scope.row.id)">更新评语</el-button>-->
<!--          </template>-->
<!--        </el-table-column>-->
      </el-table>
    </div>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[5,10,20]"
        :page-size="pageSize"
        layout="sizes, prev, pager, next"
        :total="total">
    </el-pagination>
  </body>
</template>

<script>
import * as echarts from "echarts"
import request from "@/utils/re_request";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "feedback",
  created() {
    this.load()
  },
  data(){
    return{
      tid:1,
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }],
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      currentPage:1,
      pageSize:10,
      total:100,
      search:"",
      dialogVisible:false,
    }
  },
  methods:{
    showcharts(){
      request.get("/feedback/findnumber",{
        params:{
          tid:this.tid
        }
      }).then(res =>{
        console.log("data of tableone")
        console.log(res)
        var myChart1 = echarts.init(document.getElementById('chart1'));
        var option1 = {
          title:{
            text:"班级同学最喜欢的课程对比"
          },
          legend: {
            top: 'bottom'
          },
          toolbox: {
            show: true,
            feature: {
              mark: { show: true },
              saveAsImage: { show: true }
            }
          },
          series: [
            {
              name: 'Nightingale Chart',
              type: 'pie',
              radius: [10, 70],
              center: ['50%', '50%'],
              roseType: 'area',
              itemStyle: {
                borderRadius: 8
              },
              data: [
                { value: res.virtue, name: '共有'+res.virtue.toString()+'人选择德育' },
                { value: res.intelligence, name: '共有'+res.intelligence.toString()+'人选择智育' },
                { value: res.sports, name: '共有'+res.sports.toString()+'人选择体育' },
                { value: res.art, name: '共有'+res.art.toString()+'人选择美育' },
                { value: res.labor, name: '共有'+res.labor.toString()+'人选择劳育' },
              ]
            }
          ]
        };
        myChart1.setOption(option1)
      })
      request.get("/feedback/findhobject",{
        params:{
          tid:this.tid
        }
      }).then(res =>{
        console.log("data of tabletwo")
        console.log(res)
        var myChart2 = echarts.init(document.getElementById('chart2'));
        var option2 = {
          title:{
            text:"班级同学最希望提升的课程对比"
          },
          xAxis: {
            type: 'category',
            data: ['德育', '智育', '体育', '美育', '劳育']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: [
                {
                  value: res.virtue,
                  itemStyle: {
                    color: '#00ccff'
                  }
                },
                {
                  value: res.intelligence,
                  itemStyle: {
                    color: '#a90000'
                  }
                },
                {
                  value: res.sports,
                  itemStyle: {
                    color: '#cccc00'
                  }
                },
                {
                  value: res.art,
                  itemStyle: {
                    color: '#ff6633'
                  }
                },
                {
                  value: res.labor,
                  itemStyle: {
                    color: '#003366'
                  }
                },
              ],
              type: 'bar'
            }
          ]
        };
        myChart2.setOption(option2)
      })

      request.get("/feedback/findaverage",{
        params:{
          tid:this.tid
        }
      }).then(res =>{
        console.log("data of tablethree")
        console.log(res)
        var myChart3 = echarts.init(document.getElementById('chart3'));
        var option3 = {
          title:{
            text:"教师评语得分一览,平均分为："+res.ave.toFixed(2)+"分"
          },
          xAxis: {
            type: 'category',
            data: ['5分', '4分', '3分', '2分', '1分']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: [
                {
                  value: res.five,
                  itemStyle: {
                    color: '#00ccff'
                  }
                },
                {
                  value: res.four,
                  itemStyle: {
                    color: '#a90000'
                  }
                },
                {
                  value: res.three,
                  itemStyle: {
                    color: '#cccc00'
                  }
                },
                {
                  value: res.two,
                  itemStyle: {
                    color: '#ff6633'
                  }
                },
                {
                  value: res.one,
                  itemStyle: {
                    color: '#003366'
                  }
                },
              ],
              type: 'bar'
            }
          ]
        };
        myChart3.setOption(option3)
      })
      request.get("/feedback/findrate",{
        params:{
          tid:this.tid
        }
      }).then(res =>{
        console.log("data of tablefour")
        console.log(res)
        var myChart4 = echarts.init(document.getElementById('chart4'));
        var option4 = {
          title: {
            text: '学生人数与提出反馈学生人数对比',
            // subtext: 'Fake Data',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: '50%',
              data: [
                { value: res.stu_num, name: '学生总人数' },
                { value: res.fdk_num, name: '提出反馈学生数' },
                // { value: 580, name: 'Email' },
                // { value: 484, name: 'Union Ads' },
                // { value: 300, name: 'Video Ads' }
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0)'
                }
              }
            }
          ]
        };
        myChart4.setOption(option4)
      })
      this.dialogVisible = true
    },
    pagesetting(){
      request.get("/feedback/findtabledata",{
        params:{
          tid:this.tid
        }
      }).then(res =>{
        console.log(res.total)
        this.tableData = res
      })
    },
    // showcharts(){
    //   this.dialogVisible = true
    // },
    load(){
      const feedBackQueryRequest = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        search: this.search
      };
      request.post("/feedback/pageset", feedBackQueryRequest).then(res => {
        // 处理响应
        this.tableData = res.data.records;
        console.log(this.tableData);
        this.total = res.data.total;
      }).catch(error => {
        // 错误处理
        console.error("There was an error fetching the feedback data:", error);

      });
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    },
    handleEdit(id){
      console.log(id)
    }
  }
}
</script>

<style>

</style>
