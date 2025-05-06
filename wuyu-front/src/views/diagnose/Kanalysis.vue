<template xmlns:el-col="http://www.w3.org/1999/html">
  <div class="Kanalysis">

    <el-form style="margin-top: 20px">
      <el-row :gutter="10">
        <el-col :span="6">
          <div class="select-wrapper">
            <!-- 选择框-->
            <div class="select-type">
              <el-select v-model="type" clearable placeholder="类型">
                <el-option label="班级" value="1"></el-option>
                <el-option label="学生个人" value="2"></el-option>
              </el-select>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="select-subject">
            <el-select v-model="subject" clearable placeholder="学科">
              <el-option label="语文" value="chinese" ></el-option>
              <el-option label="数学" value="math"></el-option>
              <el-option label="英语" value="english"></el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :span="6">
          <div v-show="Sswitch">
            <div class="select-grade">
              <el-select v-model="grade" clearable placeholder="年级">
                <el-option value="" label="请选择年级"></el-option>
                <el-option value="1" label="一年级"></el-option>
                <el-option value="2" label="二年级"></el-option>
                <el-option value="3" label="三年级"></el-option>
                <el-option value="4" label="四年级"></el-option>
                <el-option value="5" label="五年级"></el-option>
                <el-option value="6" label="六年级"></el-option>
              </el-select>
            </div>
          </div>
          <div  v-show="!Sswitch">
            <!-- 搜索学生-->
            <el-input
              size="mini"
              placeholder="请输入完整的学号或姓名"
              v-model="Keywords"
              clearable>
            </el-input>
          </div>
        </el-col>
        <el-col :span="6">
          <div v-show="Sswitch">
            <div class="select-class">
              <!--              <span>班级:</span>-->
              <el-select v-model="Sclass">
                <el-option label="请选择班级" value=""></el-option>
                <el-option label="一班" value="1"></el-option>
                <el-option label="二班" value="2"></el-option>
                <el-option label="三班" value="2"></el-option>
              </el-select>
            </div>
          </div>
          <div  v-show="!Sswitch">
            <el-button type="primary" @click="searchStudent" icon="el-icon-search" size="small">Search</el-button>
            <el-button type="warning" id="ExcelBtn"  @click="getPdf('学生智育诊断')">智育诊断导出</el-button>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="10">
      </el-row>
    </el-form>

    <div class="Kanalysis-content"  v-show="!Sswitch">
      <el-button-group>
        <el-button type="primary" size="mini" @click="showKpoints=true">知识点分析</el-button>
        <el-button type="primary" size="mini" @click="showKpoints=false">题型分析</el-button>
      </el-button-group>
      <el-form style="margin-top: 20px" >
        <el-row :gutter="10">
          <el-col :span="12" v-show="showKpoints">
            <el-card style="min-width: 100%; height: 100%;">
              <div v-show="showKpoints">
                <div v-show="display" id="knowledgeShow">
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="24" v-show="!showKpoints">
            <el-card style="margin-top: 20px">
              <div class="questionType" v-show="!showKpoints">
                <div class="QTypeProcess">
                  <el-progress :width="200"  :show-text="false" :stroke-width="20"
                               type="circle" :percentage="loseEasyWeight" id="firstProcess">

                  </el-progress>
                  <div class="circleCenter1">
                    <p>简单题</p>
                    <p>共{{easyQ}}分</p>
                  </div>
                  <div class="wordScore1">

                  </div>
                  <p id="loseScore1">丢{{loseEasy}}分</p>
                  <el-progress :width="200" :show-text="false" :stroke-width="20"
                               type="circle" :percentage="loseMediumWeight" status="exception" id="seconedProcess">

                  </el-progress>
                  <div class="circleCenter2">
                    <p>中等题</p>
                    <p>共{{mediumQ}}分</p>
                  </div>
                  <div class="wordScore2">

                  </div>
                  <p id="loseScore2">丢{{loseMedium}}分</p>
                  <el-progress :width="200" :show-text="false" :stroke-width="20"
                               type="circle" :percentage="loseDifficuitWeight" status="success" id="thirdProcess">

                  </el-progress>
                  <div class="circleCenter3">
                    <p>复杂题</p>
                    <p>共{{difficuitQ}}分</p>
                  </div>
                  <div class="wordScore3">

                  </div>
                  <p id="loseScore3">丢{{loseDifficuit}}分</p>
                </div>
                <div class="QTypeAnalyse">
                  <p>{{QtypeAnalyse}}</p>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12" v-show="showKpoints">
            <el-card style="min-width: 100%; height: 100%; margin-right: 20px;">
              <div v-show="display" class="analyse" id="pdfDom">
                <p>{{analysis}}</p>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="class-content"  v-show="Sswitch">
      <el-table
        id="subjecttable"
        :data="listSub"
        border
        style="width: 100%">
        <el-table-column label="测试情况" align="center">
          <el-table-column
            prop="MaxScore"
            label="最高分"
            align="center">
          </el-table-column>
          <el-table-column
            prop="MinScore"
            label="最低分"
            align="center">
          </el-table-column>
          <el-table-column
            prop="AvgScore"
            label="平均分"
            align="center">
          </el-table-column>
        </el-table-column>
      </el-table>
      <el-divider></el-divider>
      <el-button-group>
        <el-button type="primary" size="mini" @click="showTable">表格</el-button>
        <el-button type="primary" size="mini" @click="showLine">折线图</el-button>
        <el-button type="primary" size="mini" @click="analyzeClassScores">班级成绩分析</el-button>
      </el-button-group>
      <el-table
        v-show="isShow"
        id="subjectsegtable"
        :data="listScore"
        border
        style="width: 100%">
        <el-table-column label="分数段统计" align="center">
          <el-table-column
            prop="btw90100"
            label="90-100"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="btw8090"
            label="80-90"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="btw7080"
            label="70-80"
            align="center">
          </el-table-column>
          <el-table-column
            prop="btw6070"
            label="60-70"
            align="center">
          </el-table-column>
          <el-table-column
            prop="btw060"
            label="0-60"
            align="center">
          </el-table-column>
        </el-table-column>
      </el-table>
      <div id="lineShow" v-show="!isShow">
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import * as echarts from 'echarts';
import { MessageBox } from 'element-ui';
export default{
  name:'K-analysis',
  data(){
    return{
      QtypeAnalyse:'',
      easyQ:'0',
      mediumQ:'0',
      difficuitQ:'0',
      easyScore:'0',
      mediumScore:'0',
      difficuitScore:'0',
      showKpoints:true,
      isShow:true,
      Sswitch:false,
      grade:'',
      Sclass:'',
      type:'2',
      subject:'chinese',
      Keywords:'',
      display:false,
      analysis:'示例',
      listSub:[{
        MaxScore:'0',
        MinScore:'0',
        AvgScore:'0'
      }],
      listScore:[{
        btw90100:'0',
        btw8090:'0',
        btw7080:'0',
        btw6070:'0',
        btw060:'0'
      }],
      /*        listKnowledge:[] */
    }
  },
  /* 计算属性，返回grade,Sclass*/
  computed:{
    classgradesubject() {
      return {
        subject:this.subject,
        grade:this.grade,
        Sclass:this.Sclass
      }
    },
    loseEasy(){
      return this.easyQ-this.easyScore;
    },
    loseMedium(){
      return this.mediumQ-this.mediumScore;
    },
    loseDifficuit(){
      return this.difficuitQ-this.difficuitScore;
    },
    /* 计算所占百分比*/
    loseEasyWeight(){
      if(this.easyQ==0){
        return 0;
      }
      else{

        return (this.loseEasy/this.easyQ)*100;
      }
    },
    loseMediumWeight(){
      if(this.mediumQ==0){
        return 0;
      }
      else{
        return (this.loseMedium/this.mediumQ)*100;
      }
    },
    loseDifficuitWeight(){
      if(this.difficuitQ==0){
        return 0;
      }
      else{
        return (this.loseDifficuit/this.difficuitQ)*100;
      }
    }
  },
  watch:{
    type(){
      if(this.type=="1"){
        this.easyQ=0;
        this.easyScore=0;
        this.mediumQ=0;
        this.mediumScore=0;
        this.loseDifficuitWeight=0;
        this.loseEasyWeight=0;
        this.loseMediumWeight=0;
        this.analysis="示例"
        this.QtypeAnalyse=""
        var chartDom = document.getElementById('knowledgeShow');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
          title: {
            text: '语文学科示例'
          },
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
          },
          series: [
            {
              name:'例图',
              type: 'pie',
              radius: [0, '35%'],
              label: {
                normal: {
                  position: 'inner'
                }
              },//扇形区域内显示文字
              data:[
                { value: 30, name: '拼音' },
                { value: 20, name: '词语' },
                { value: 10, name: '听写' },
                { value: 20, name: '作文' },
                { value: 20, name: '选词填空' }
              ],
              color:[]
            },
            {
              name:'例图',
              type: 'pie',
              radius: ['35%', '75%'],
              labelLine: {
                normal: {
                  show: true,
                }
              },
              data:[
                { value: 25, name:'拼音(得分)'},
                { value: 5, name:'拼音(失分)'},
                { value: 15, name:'词语(得分)'},
                { value: 5, name:'词语(失分)'},
                { value: 8, name:'听写(得分)'},
                { value: 2, name:'听写(失分)'},
                { value: 15, name:'作文(得分)'},
                { value: 5, name:'作文(失分)'},
                { value: 15, name:'选词填空(得分)'},
                { value: 5, name:'选词填空(失分)'}
              ],
              color:[]
            },
          ],
        };
        option && myChart.setOption(option);
        this.Sswitch=true;
      }
      else{

        /* 切换清空*/
        this.grade='';
        this.Sclass='';
        this.listSub=[{
          MaxScore:'0',
          MinScore:'0',
          AvgScore:'0'
        }];
        this.listScore=[{
          btw90100:'0',
          btw8090:'0',
          btw7080:'0',
          btw6070:'0',
          btw060:'0'
        }];
        var chartDom = document.getElementById('lineShow');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
          xAxis: {
            type: 'category',
            data: ['0-60', '60-70', '70-80', '80-90', '90-100']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: [0,0,0,0,0],
              type: 'line'
            }
          ]
        };

        option && myChart.setOption(option);
        this.Sswitch=false;
      }
    },
    classgradesubject:{
      handler: function(newval,oldval) {
        /* 为空不加载数据*/
        if(newval.subject==''||newval.grade==''||newval.Sclass==''){
          return;
        }
        /* 加载线形图数据*/
        var chartDom = document.getElementById('lineShow');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
          xAxis: {
            type: 'category',
            data: ['0-60', '60-70', '70-80', '80-90', '90-100']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: [],
              type: 'line'
            }
          ]
        };
        /* 获取最高分最低分*/
        // axios.get("http://49.51.69.99:9200/diagnose/classsubject",{
        axios.get("http://36.111.68.174:33380/diagnose/classsubject",{
          params:{
            subject:newval.subject,
            grade:newval.grade,
            sclass:newval.Sclass
          }
        }).then(
          response =>{
            this.listSub[0].MaxScore=response.data[0];
            this.listSub[0].MinScore=response.data[1];
            this.listSub[0].AvgScore=response.data[2];
          },
          error =>{
            const h = this.$createElement;
            this.$notify({
              title: '出错了',
              message: '显示失败!'
            });
          })
        /* 获取各分数段人数*/
        // axios.get("http://49.51.69.99:9200/diagnose/classsubjectseg",{
        axios.get("http://36.111.68.174:33380/diagnose/classsubjectseg",{
          params:{
            subject:newval.subject,
            grade:newval.grade,
            sclass:newval.Sclass
          }
        }).then(
          response =>{
            console.log(response.data)
            this.listScore[0].btw90100=response.data[0];
            this.listScore[0].btw8090=response.data[1];
            this.listScore[0].btw7080=response.data[2];
            this.listScore[0].btw6070=response.data[3];
            this.listScore[0].btw060=response.data[4];
            for(let i=0;i<response.data.length;i++){
              option.series[0].data[i]=response.data[i];
              option && myChart.setOption(option);
            }
          },
          error => {})
      },
      deep:true
    }
  },
  methods:{

    // 班级成绩分析方法...
    analyzeClassScores() {
      const { btw060, btw6070, btw7080, btw8090, btw90100 } = this.listScore[0];
      const countBelow60 = parseInt(btw060);
      const countBetween60And90 = parseInt(btw6070) + parseInt(btw7080) + parseInt(btw8090);
      const countAbove90 = parseInt(btw90100);

      let analysisResult = "";

      if (countBelow60 > countAbove90) {
        analysisResult = "该班基础薄弱，着重简单题";
      } else if (countBetween60And90 > countBelow60 && countBetween60And90 > countAbove90) {
        analysisResult = "该班应该抓稳基础，注重中难题型";
      } else if (countAbove90 > countBetween60And90) {
        analysisResult = "该班基础良好，着重复杂题";
      } else if (countBelow60 === 0 && countBetween60And90 === 0 && countAbove90 === 0) {
        analysisResult = "无数据";
      } else {
        analysisResult = "无法分析该班级成绩";
      }

      // 使用 MessageBox 显示结果
      MessageBox.alert(analysisResult, '班级成绩分析', {
        confirmButtonText: '确定',
        center: true
      });
    },
    showTable(){
      this.isShow=true;
    },
    showLine(){
      this.isShow=false;
    },
    searchStudent(){
      if(this.subject==''||this.Keywords==''){
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '选择学科或输入不能为空!',
        });
        return;
      }
      var Name='NULL'
      var Id=-1
      var reg = new RegExp("[0-9]")//
      if(reg.test(this.Keywords)){//包含数字,赋值给id
        Id=this.Keywords
      }else{
        Name=this.Keywords
      }
      var chartDom = document.getElementById('knowledgeShow');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: '学生知识点得分情况'
        },
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name:'知识点占比统计',
            type: 'pie',
            radius: [0, '35%'],
            label: {
              normal: {
                position: 'inner'
              }
            },//扇形区域内显示文字
            data:[
            ],
            color: []
          },
          {
            name:'知识点得分统计',
            type: 'pie',
            radius: ['35%', '75%'],
            labelLine: {
              normal: {
                show: true,
              }
            },
            data:[
            ],
            color:[]
          },
        ],
      };
      /* 获取知识点echarts图*/
      axios.get("http://36.111.68.174:33380/diagnose/SKPScore",{//直接请求9200
        // axios.get("http://49.51.69.99:9200/diagnose/SKPScore",{//直接请求9200
        params:{
          type:this.subject,
          name:Name,
          id:Id
        }
      }).then(//  /Learning重定向到8081
        response =>{
          /* 无数据返回时*/
          if(response.data.length==0){
            const h = this.$createElement;
            this.$notify({
              title: '出错了',
              message: '请输入正确且完整的姓名或学号!'
            });
            return;
          }
          console.log(response.data);
          /* 添加知识点占比数据*/
          for(var i=0;i<response.data.length;i++){
            option.series[0].data[i]={
              value:response.data[i].kProportion,
              name:response.data[i].kName
            };
          };

          /* 添加知识点得分情况*/

          for(var i=0;i<response.data.length;i++){
            option.series[1].data[2*i]={
              value:response.data[i].ksScore,
              name:response.data[i].kName+'(得分)'
            };
            option.series[1].data[2*i+1]={
              value:response.data[i].kProportion-response.data[i].ksScore,
              name:response.data[i].kName+'(失分)'
            };
          }
          option && myChart.setOption(option);
          this.display=true;
        },
        error =>{
          const h = this.$createElement;
          this.$notify({
            title: '出错了',
            message: '搜索失败!'
          });
        }
      )
      /* 获取知识点分析*/
      axios.get("http://36.111.68.174:33380/diagnose/SKPAnalysis",{
        // axios.get("http://49.51.69.99:9200/diagnose/SKPAnalysis",{
        params:{
          type:this.subject,
          name:Name,
          id:Id
        }
      }).then(//  /Learning重定向到8081
        response =>{
          /* 无数据返回时*/
          if(response.data=="其知识点掌握不错，请继续保持失分较多，仍有进步空间是知识点的薄弱部分，需要加强练习。"){
            return;
          }
          this.analysis=response.data;
        },
      )
      /* 获取题型分值*/
      // axios.get("http://49.51.69.99:9200/diagnose/qtypeproportion",{
      axios.get("http://36.111.68.174:33380/diagnose/qtypeproportion",{
        params:{
          subject:this.subject,
          name:Name,
          id:Id
        }
      }).then(//
        response =>{
          this.easyQ=response.data.qtEasy;
          this.mediumQ=response.data.qtMedium;
          this.difficuitQ=response.data.qtDifficuit;
        },
      )
      /* 获取题型得分*/
      // axios.get("http://49.51.69.99:9200/diagnose/qtypescore",{
      axios.get("http://36.111.68.174:33380/diagnose/qtypescore",{
        params:{
          subject:this.subject,
          name:Name,
          id:Id
        }
      }).then(//  /Learning重定向到8081
        response =>{
          this.easyScore=response.data.easyScore;
          this.mediumScore=response.data.mediumScore;
          this.difficuitScore=response.data.difficuitScore;
        },
      )
      /* 获取题型失分分析*/
      // axios.get("http://49.51.69.99:9200/diagnose/losescoreanalyse",{
      axios.get("http://36.111.68.174:33380/diagnose/losescoreanalyse",{
        params:{
          subject:this.subject,
          name:Name,
          id:Id
        }
      }).then(
        response =>{
          this.QtypeAnalyse=response.data;
        },
      )
    }
  },
  /* 加载初始示例图*/
  mounted(){
    this.display = true;
    var chartDom = document.getElementById('knowledgeShow');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
      title: {
        text: '语文学科示例'
      },
      tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
      },
      series: [
        {
          name:'例图',
          type: 'pie',
          radius: [0, '35%'],
          label: {
            normal: {
              position: 'inner'
            }
          },//扇形区域内显示文字
          data:[
            { value: 30, name: '拼音' },
            { value: 20, name: '词语' },
            { value: 10, name: '听写' },
            { value: 20, name: '作文' },
            { value: 20, name: '选词填空' }
          ],
          color:[]
        },
        {
          name:'例图',
          type: 'pie',
          radius: ['35%', '75%'],
          labelLine: {
            normal: {
              show: true,
            }
          },
          data:[
            { value: 25, name:'拼音(得分)'},
            { value: 5, name:'拼音(失分)'},
            { value: 15, name:'词语(得分)'},
            { value: 5, name:'词语(失分)'},
            { value: 8, name:'听写(得分)'},
            { value: 2, name:'听写(失分)'},
            { value: 15, name:'作文(得分)'},
            { value: 5, name:'作文(失分)'},
            { value: 15, name:'选词填空(得分)'},
            { value: 5, name:'选词填空(失分)'}
          ],
          color:[]
        },

      ],

    };
    option && myChart.setOption(option);

    var chartDom = document.getElementById('lineShow');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
      xAxis: {
        type: 'category',
        data: ['0-60', '60-70', '70-80', '80-90', '90-100']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [0,0,0,0,0],
          type: 'line'
        }
      ]
    };
    option && myChart.setOption(option);
  }
}
</script>
<style scoped>
span{
  font-weight:bold;
  vertical-align: center;
  margin-right:5px;
}

.Kanalysis{
  /*width:1160px;*/
  width:100%;
  height:100%;
  position:absolute;
}
.select-wrapper{
  width:1050px;
  height:34px;
  margin-left:60px;
}
.select-wrapper a{
  color:#ff5500;
  margin-left:40px;
}
.select-wrapper a:hover{
  color:orange;
}
.el-input{
  width:300px;
  height:20px;
}
.el-button{
  margin-left: 20px;
}

.select-class,.select-grade,.select-type,.select-subject{
  width:193px;
  height:100px;
  float:left;
  margin-left:20px;
  margin-top:5px;
}
.questionType{
  position:relative;
  width:100%;
  height:470px;

}
.QTypeProcess{
  width:100%;
  height:300px;
  margin-top:25px;
}
.QTypeAnalyse{
  width:800px;
  height:150px;
  margin:-50px auto;
  background-color:#f0f0f0;
}
#firstProcess,#seconedProcess,#thirdProcess{
  margin-left:100px;
}

.circleCenter1,.circleCenter2,.circleCenter3{
  float:left;
  position:absolute;
  font-weight:bold;
  font-size:20px;
}
.wordScore1,.wordScore2,.wordScore3{
  width:15px;
  height:15px;
  float:left;
  position:absolute;
  top:224px;
}
.wordScore1{
  left:170px;
  background-color:rgb(32,160,255);
}
.wordScore2{
  left:470px;
  background-color:#FF4949;
}
.wordScore3{
  left:770px;
  background-color:#13cE66;
}
#loseScore1,#loseScore2,#loseScore3{
  float:left;
  position:absolute;
  font-weight:bold;
  font-size:15px;
}
#loseScore1{
  left:200px;
  top:220px;
}
#loseScore2{
  left:500px;
  top:220px;
}
#loseScore3{
  left:800px;
  top:220px;
}
.circleCenter1{
  top:60px;
  left:170px;
}

.circleCenter2{
  top:60px;
  left:470px;
}

.circleCenter3{
  top:60px;
  left:770px;
}
.QTypeAnalyse p{
  text-indent: 40px;
  font-family: "微软雅黑","Microsoft YaHei","Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB",Arial,sans-serif;
  font-weight:bold;
  font-size: large;
  line-height: 1.5;
}
.class-content{
  margin-top: 20px;
  width:1050px;
  height:100%;
  position:absolute;
  top:60px;
  left:60px;
  border:1px solid rgb(252,251,220);
  box-shadow:2px 2px 5px;
}
#lineShow{
  width:800px;
  height:250px;
}

#subjectsegtable{
  margin-top:25px;
}
.el-divider{
  margin-top:75px;
}
.el-button-group{
  margin-left:450px;
}

#knowledgeShow{
  width:500px;
  height:350px;
  margin-left: 80px;
}

.analyse{
  width:400px;
  height:350px;
  margin-top:20px;
  float:left;
  position:relative;
}

.analyse p{
  position:absolute;
  left:50px;
  top:50px;
  font-family: "微软雅黑","Microsoft YaHei","Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB",Arial,sans-serif;
  font-weight:bold;
  font-size: large;
  line-height: 1.5;
}
</style>

