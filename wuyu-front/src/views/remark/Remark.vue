<template>
  <body>
  <link  rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">
  <div>
<!--    <el-input v-model="studentNum" placeholder="请输入学生学号" style="width:20%" clearable></el-input>-->

    <el-input
      v-model="studentNum"
      placeholder="请输入学生学号(不超过10位的数字)"
      style="width:20%"
      clearable
      @input="limitInput"
    ></el-input>

    <el-select v-model="evaluateDate" placeholder="请选择学期" style="width:20%">
      <el-option label="第一学期" value=202107></el-option>
      <el-option label="第二学期" value=202112></el-option>
      <el-option label="第三学期" value=202207></el-option>
      <el-option label="第四学期" value=202212></el-option>
      <el-option label="第五学期" value=202307></el-option>
      <el-option label="第六学期" value=2024></el-option>
    </el-select>
<!--    <el-input v-model="evaluateDate" placeholder="请输入学期" style="width:20%" clearable></el-input>-->
    <el-button type="primary" icon="el-icon-search" @click="getdata">查询</el-button>
<!--    <el-button type="info" icon="el-icon-message" @click="showbox">提出反馈</el-button>-->
<!--    <el-button type="success" icon="el-icon-edit" @click="showbox1">修改反馈</el-button>-->
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
<!--    //散点图-->
    <el-col :span="12">
      <el-card class="box-card">
        <div v-if="showMainPlot" id="main" style="width: 800px;height:400px;float: left;"></div>
      </el-card>
    </el-col>
    <el-button @click="toggleMainPlot">显示/隐藏散点图</el-button>
<!--    //雷达图-->
    <el-col :span="12">
      <el-card class="box-card" style="display: block" id="sandiantu">
        <div id="scatter" style="width: 800px;height:400px;margin-left:1px;"></div>
      </el-card>
    </el-col>
  </el-row>


  <el-row :gutter="0" style="margin-bottom: 20px;">
    <el-col :span="12">
      <el-card class="box-card">
        <h3>教师评语</h3>
        <div style="white-space: pre-wrap;font-family: 微软雅黑;font-size: 20px;width: 780px;height: 500px;float: left ">{{this.remark}}</div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card class="box-card">
        <div class="block" style="width: 700px; text-align:center;line-height:545px;">
          <!--      <span class="demonstration">图片展示</span>-->
          <el-carousel height="545px">
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
      placeholder="请输入内容 "
      v-model="textarea"
      clearable>
    </el-input>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible1 = false">取 消</el-button>
      <el-button type="primary" @click="editback">确 定</el-button>
    </span>
  </el-dialog>
  </body>
</template>
<script>
import * as echarts from "echarts"
import request from "@/utils/re_request"


const objectOptions = ['德育', '智育', '体育', '美育','劳育'];
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name:'Remark',
  data(){
    return{
      showMainPlot: true,
      feedback_id:null,
      name:'',
      //id:2018083064
      evaluateDate:2024,
      studentNum:2018083068,
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
        id: 123456,
        name: "张三",
        virtue:100,
        intelligence:100,
        sports:100,
        art:100,
        labor:100,
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
      flag1:1
    }
  },

  async created(){
    await this.getaverage()
    // this.showtable()
  },
  methods:{
    getdata(){
      request.get("/student/boy",{
        params:{
          studentNum:this.studentNum,
          evaluateDate:this.evaluateDate
        }
      }).then(res =>{
        if(res.studentName==null){
          this.$message("未找到该学生")
          return;
        }
        console.log(this.remark)
        this.name = res.studentName
        this.virtue = res.moralityScore
        this.intelligence = res.intelligenceScore
        this.sports = res.physicalScore
        this.art = res.aestheticScore
        this.labor = res.labourScore
        // 如果评语为空 显示"老师暂未给出评语"
        this.remark = res.remark || "老师暂未给出评语"
        this.sum = this.virtue+this.intelligence+this.sports+this.art+this.labor
        this.tableData = [{
          sid: this.studentNum,
          name: this.name,
          virtue:this.virtue,
          intelligence:this.virtue,
          sports:this.sports,
          art:this.art,
          labor:this.labor,
        },]
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
    limitInput() {
      this.studentNum = this.studentNum.replace(/\D/g, '').slice(0, 10);
    },
    toggleMainPlot() {
      if(this.flag1===1){
        document.getElementById("sandiantu").style.display="none";
        this.flag1=0;
      }else {document.getElementById("sandiantu").style.display="block";this.flag1=1;}
      this.showScatterPlot = !this.showScatterPlot;
      // 如果散点图现在需要显示，重新初始化它
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
    // getremark(){
    //   request.get("/student/remark",{
    //     params:{
    //       id:this.id,
    //       tid:this.tid
    //     }
    //   }).then(res =>{
    //     // console.log(res)
    //     this.remark = res.remark
    //   })
    // },
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
      }).then(res =>{
        this.$message({
          message: '修改反馈成功',
          type: 'success'
        })
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
    }
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

/*.el-carousel__item:nth-child(2n) {*/
/*  background-color: #00ffff;*/
/*}*/

/*.el-carousel__item:nth-child(2n+1) {*/
/*  background-color: #00ffff;*/
/*}*/
</style>



