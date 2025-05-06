<template>
  <body>
    <div id="pdfDom">
      <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">
      <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
      <div>
    <!--    <el-input v-model="id" placeholder="请输入学生学号" style="width:20%" clearable></el-input>-->
        <el-button type="primary" icon="el-icon-search" @click="getdata">查看评语</el-button>
        <el-button type="info" icon="el-icon-message" @click="showbox">提出反馈</el-button>
        <el-button type="success" icon="el-icon-edit" @click="showbox1">修改反馈</el-button>
        <el-button type="success" icon="el-icon-download" @click="getPdf('教师评语')">下载网页</el-button>
      </div>
      <el-table
        :data="tableData"
        style="width: 100%">
        <!--增加sid    -->
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
          prop="virtue"
          label="德育成绩">
        </el-table-column>
        <el-table-column
          prop="intelligence"
          label="智育成绩">
        </el-table-column>
        <el-table-column
          prop="sports"
          label="体育成绩">
        </el-table-column>
        <el-table-column
          prop="art"
          label="美育成绩">
        </el-table-column>
        <el-table-column
          prop="labor"
          label="劳育成绩">
        </el-table-column>
      </el-table>
      <!--  <div>-->
      <!--      &lt;!&ndash; 表一 &ndash;&gt;-->
      <!--      <div id="main" style="width: 840px;height:400px;float: left;min-width: 840px"></div>-->
      <!--      &lt;!&ndash; 表二 &ndash;&gt;-->
      <!--      <div id="scatter" style="width: 840px;height:400px;float: right;min-width: 840px">-->
      <!--  </div>-->
      <el-row :gutter="0">
        <el-col :span="12">
          <el-card class="box-card">
            <div id="main" style="width: 800px;height:400px;float: left;"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
            <div id="scatter" style="width: 800px;height:400px;float: right;"></div>
          </el-card>
        </el-col>
      </el-row>
      <!--  <div>-->
      <!--&lt;!&ndash; 文本   &ndash;&gt;-->
      <!--    <div style="white-space: pre-wrap;font-family: 微软雅黑;font-size: 20px;width: 780px;float: left ">{{this.remark}}</div>-->
      <!--&lt;!&ndash; 图片   &ndash;&gt;-->
      <!--    <div class="block" style="width: 780px;float: right">-->
      <!--&lt;!&ndash;      <span class="demonstration">图片展示</span>&ndash;&gt;-->
      <!--      <el-carousel height="450px">-->
      <!--        <el-carousel-item v-for="item in imgList" :key="item.url">-->
      <!--&lt;!&ndash;          <h3 class="small">{{ item.idView }}</h3>&ndash;&gt;-->
      <!--          <img :src="item.url"/>-->
      <!--        </el-carousel-item>-->
      <!--      </el-carousel>-->
      <!--    </div>-->
      <!--  </div>-->

      <el-row :gutter="0" style="margin-bottom: 20px;">
        <el-col :span="12">
          <el-card class="box-card">
            <h3>教师评语</h3>
            <div style="white-space: pre-wrap;font-family: 微软雅黑;font-size: 20px;width: 780px;height: 500px;float: left ">{{this.remark}}</div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
            <div class="block" style="width: 700px;text-align:center;line-height:550px;">
              <!--      <span class="demonstration">图片展示</span>-->
              <el-carousel height="550px">
                <el-carousel-item v-for="item in imgList" :key="item.url">
                  <!--          <h3 class="small">{{ item.idView }}</h3>-->
                  <img :src="item.url"/>
                </el-carousel-item>
              </el-carousel>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-dialog
        title="请填写以下信息帮助老师更好的进行教学工作"
        :visible.sync="dialogVisible"
        width="30%">
        <!--        :before-close="handleClose">-->
        <span>请对教师评语进行打分</span>
        <el-rate
          v-model="value"
          show-text>
        </el-rate>
        <span>请选择这学期你最满意的课程</span>
        <div style="margin-top: 20px">
          <el-radio-group v-model="radio1" size="medium">
            <el-radio-button label="德育" ></el-radio-button>
            <el-radio-button label="智育"></el-radio-button>
            <el-radio-button label="体育"></el-radio-button>
            <el-radio-button label="美育"></el-radio-button>
            <el-radio-button label="劳育"></el-radio-button>
          </el-radio-group>
        </div>
        <span>请选择你希望老师加强哪项课程的教学</span>
        <div style="margin-top: 20px">
          <el-radio-group v-model="radio2" size="medium">
            <el-radio-button label="德育" ></el-radio-button>
            <el-radio-button label="智育"></el-radio-button>
            <el-radio-button label="体育"></el-radio-button>
            <el-radio-button label="美育"></el-radio-button>
            <el-radio-button label="劳育"></el-radio-button>
          </el-radio-group>
        </div>
        <span>请写出您对班主任评语的意见</span>
        <el-input
          type="textarea"
          autosize
          placeholder="请输入内容"
          v-model="textarea"
          clearable>
        </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="pushback">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="请填写以下信息帮助老师更好的进行教学工作"
        :visible.sync="dialogVisible1"
        width="30%">
        <!--        :before-close="handleClose">-->
        <span>请对教师评语进行打分</span>
        <el-rate
          v-model="value"
          show-text>
        </el-rate>
        <span>请选择这学期你最满意的课程</span>
        <div style="margin-top: 20px">
          <el-radio-group v-model="radio1" size="medium">
            <el-radio-button label="德育" ></el-radio-button>
            <el-radio-button label="智育"></el-radio-button>
            <el-radio-button label="体育"></el-radio-button>
            <el-radio-button label="美育"></el-radio-button>
            <el-radio-button label="劳育"></el-radio-button>
          </el-radio-group>
        </div>
        <span>请选择你希望老师加强哪项课程的教学</span>
        <div style="margin-top: 20px">
          <el-radio-group v-model="radio2" size="medium">
            <el-radio-button label="德育" ></el-radio-button>
            <el-radio-button label="智育"></el-radio-button>
            <el-radio-button label="体育"></el-radio-button>
            <el-radio-button label="美育"></el-radio-button>
            <el-radio-button label="劳育"></el-radio-button>
          </el-radio-group>
        </div>
        <span>请写出您对班主任评语的意见</span>
        <el-input
          type="textarea"
          autosize
          placeholder="请输入内容"
          v-model="textarea"
          clearable>
        </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="editback">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </body>
</template>

<script>
import * as echarts from "echarts"
import request from "@/utils/re_request"
import re_htmlToPdf from '@/utils/re_htmlToPdf'

const objectOptions = ['德育', '智育', '体育', '美育','劳育'];
// export default {
//   name: "Sturemark"
// }
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name:'Sturemark',
  data(){
    return{
      feedback_id:null,
      name:'',
      //id:2018083064
      // id:2018083083,
      id:localStorage.teacherId,
      virtue:0,
      intelligence:0,
      sports:0,
      art:0,
      labor:0,
      remark:"",
      tid:1,
      sum:0,
      v_sum:0,
      i_sum:0,
      s_sum:0,
      a_sum:0,
      l_sum:0,
      data1:[],
      tableData: [{
        id_1: 123456,
        name_1: "张三",
        virtue_1:100,
        intelligence_1:100,
        sports_1:100,
        art_1:100,
        labor_1:100,
      },],
      dialogVisible:false,
      dialogVisible1:false,
      value:null,
      radio1:null,
      radio2:null,
      objects: objectOptions,
      textarea:'',
      msg:'',
      imgList:[
        {url:require("./remark_images/01.jpg")},
        {url:require("./remark_images/02.jpg")},
        {url:require("./remark_images/03.jpg")},
        {url:require("./remark_images/04.jpg")},
        {url:require("./remark_images/05.jpg")},
        {url:require("./remark_images/06.jpg")},
        {url:require("./remark_images/07.jpg")},
        {url:require("./remark_images/08.jpg")},
        {url:require("./remark_images/09.jpg")},
        {url:require("./remark_images/10.jpg")},
      ],
      htmlTitle: "PDF名称",
      nowTime: "",
    }
  },

  async created(){
    await this.getaverage()
    // this.showtable()
    this.showuser()
  },
  methods:{
    getdata(){
      request.get("/student/boy",{
        params:{
          id:this.id
        }
      }).then(res =>{
        // console.log(res)
        // var list = []
        // list.push(res.id,res.name,res.virtue,res.intelligence,res.sports,res.art,res.labor)
        // console.log(list)
        console.log(this.remark)
        this.name = res.name
        this.virtue = res.virtue
        this.intelligence = res.intelligence
        this.sports = res.sports
        this.art = res.art
        this.labor = res.labor
        this.sum = this.virtue+this.intelligence+this.sports+this.art+this.labor
        this.tableData = [res]
        var myChart = echarts.init(document.getElementById('main'));
        var myChart1 = echarts.init(document.getElementById('scatter'))
        var option = {
          title: {
            text: this.name+'同学分数与平均分对比'
          },
          legend: {
            data: ['五育分数', '五育平均分']
          },
          radar: {
            // shape: 'circle',
            indicator: [
              { name: '品德', max: 100 },
              { name: '智力', max: 100 },
              { name: '体育', max: 100 },
              { name: '美术', max: 100 },
              { name: '劳动', max: 100 },
            ]
          },
          series: [
            {
              name: 'Budget vs spending',
              type: 'radar',
              data: [
                {
                  value: [this.virtue,this.intelligence,this.sports,this.art,this.labor],
                  name: '五育分数'
                },
                {
                  value: [this.v_sum,this.i_sum,this.s_sum,this.a_sum,this.l_sum],
                  name: '五育平均分'
                }
              ]
            }
          ]
        };
        var option1= {
          title:{
            text:this.name+"同学及全班总分散点图"
          },
          xAxis: {
            scale: true
          },
          yAxis: {
            scale: true
          },
          series: [
            {
              type: 'effectScatter',
              symbolSize: 20,
              data: [
                [50, this.sum],
              ]
            },
            {
              type: 'scatter',
              // prettier-ignore
              // data: [[161.2, 52.6], [167.5, 59.0], [159.5, 49.2], [157.0, 63.0], [155.8, 53.6],
              //
              // ]
              data: this.data1
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option)
        myChart1.setOption(option1)
        this.getremark()
      })
    },
    getaverage(){
      request.get("/student/average",{
        params:{
          tid:this.tid
        }
      }).then(res =>{
        // console.log(res)
        this.v_sum = res.v_sum
        this.i_sum = res.i_sum
        this.s_sum = res.s_sum
        this.a_sum = res.a_sum
        this.l_sum = res.l_sum
      })
      this.getsum()
    },
    getsum(){
      request.get("/student/sum",{
        params:{
          tid:this.tid
        }
      }).then(res =>{
        // console.log("success")
        // console.log(res)
        for(let key in res){
          let data2=[key,res[key]];
          this.data1.push(data2)
        }
        // console.log(this.data1)
        // console.log(typeof this.data1)
      })
      this.getdata()
    },
    getremark(){
      request.get("/student/remark",{
        params:{
          id:this.id,
          tid:this.tid
        }
      }).then(res =>{
        // console.log(res)
        this.remark = res.remark
      })
    },
    showbox(){
      request.get("/feedback/iffeedback",{
        params:{
          sid:this.id
        }
      }).then(res =>{
        console.log(res.flag)
        if(res.flag === 0){
          this.$message({
            message:"你已经提出反馈了，点击修改按钮即可修改反馈",
            type:"warning"
          })
        }
        else {
          this.value = null
          this.radio1 = null
          this.radio2 = null
          this.textarea = ''
          this.dialogVisible = true
        }
      })

    },
    showbox1(){
      request.get("/feedback/iffeedback",{
        params:{
          sid:this.id
        }
      }).then(res =>{
        console.log(res.flag)
        if(res.flag === 1){
          this.$message({
            message:"请先点击提出按钮编写反馈哦",
            type:"warning"
          })
        }
        else{
          request.get("/feedback/getfeedback",{
            params:{
              sid:this.id
            }
          }).then(res =>{
            console.log("开始显示找回的数据")
            console.log(res)
            this.value = res.rate
            this.radio1 = res.fobject
            this.radio2 = res.hobject
            this.textarea = res.feedback
            this.feedback_id = res.id
          })
          this.dialogVisible1 = true
        }
      })
      // request.get("/feedback/getfeedback",{
      //   params:{
      //     sid:this.id
      //   }
      // }).then(res =>{
      //   console.log("开始显示找回的数据")
      //   console.log(res)
      //   this.value = res.rate
      //   this.radio1 = res.fobject
      //   this.radio2 = res.hobject
      //   this.textarea = res.feedback
      //   this.feedback_id = res.id
      // })
      // this.dialogVisible1 = true
    },
    pushback(){
      console.log("push back run!")
      request.get("/feedback/save",{
        params:{
          id:this.id,
          rate:this.value,
          fobject:this.radio1,
          hobject:this.radio2,
          feedback:this.textarea,
          sid:this.id,
          tid:this.tid,
          name:this.name
        }
      }).then(res =>{
        this.$message({
          message: '提出反馈成功',
          type: 'success'
        })
      })
      this.dialogVisible = false
    },
    editback(){
      // request.put("/feedback/edit",{
      //   id:this.id,
      //   rate:this.value,
      //   fobject:this.radio1,
      //   hobject:this.radio2,
      //   feedback:this.textarea,
      //   sid:this.id,
      //   tid:this.tid
      // })
      request.get("/feedback/edit",{
        params:{
          sid:this.id,
          tid:this.tid,
          feedback_id:this.feedback_id,
          name:this.name,
          rate:this.value,
          fobject:this.radio1,
          hobject:this.radio2,
          feedback:this.textarea
        }
      })
      this.dialogVisible1 = false
    },
    showtable(){
      request.get("/student/boy",{
        params:{
          id:this.id
        }
      }).then(res =>{
        console.log(res)
        this.tableData = [res]
      })
    },
    showuser(){
      console.log("当前登录用户的id是：")
      console.log(localStorage.teacherId)
    },
  },

}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #00ffff;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #00ffff;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #00ffff;
}
</style>
