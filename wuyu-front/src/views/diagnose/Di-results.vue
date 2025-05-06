<template>
  <div>
    <div class="Dresults" v-show="showresult">
      <!--信息选择框-->
      <div class="select-wrappers">
        <el-form :inline="true" class="demo-form-inline" style="margin-top: 20px">
          <el-form-item>
            <el-select v-model="type" clearable placeholder="类型">
              <el-option  label="班级" value="1" ></el-option>
              <el-option label="学生个人" value="2"></el-option>
            </el-select>
            <el-select v-model="grade" clearable placeholder="年级" v-show="showselect">
              <el-option v-for="item in grades" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
            <el-select v-model="Sclass" clearable placeholder="班级" v-show="showselect">
              <el-option v-for="item in classes" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <!-- 搜索学生-->
        <div v-show="Sswitch" style="margin-bottom: 20px">
          <el-input
            size="mini"
            placeholder="请输入学号或姓名"
            v-model="input"
            clearable>
          </el-input>
          <el-button id="ser" @click="searchStudent" type="primary"
                     icon="el-icon-search">搜索</el-button>
          <el-button type="primary" @click="showtargetC">点击查看计划完成情</el-button>
        </div>
      </div>
      <div class="result-show" >
        <el-form>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-card style="color: #67C23A; min-width: 100%; height: 100%; margin-right: 20px;">
                <div v-show="!showtarget" id="contrastResult">
                </div>
                <div v-show="showtarget" class="targetComplition">
                  <p>{{targetstudent}}</p>
                  <el-tag>德育成绩完成度</el-tag>
                  <el-progress :text-inside="true" :stroke-width="26" :percentage="targetComplitionlist[0]"></el-progress>
                  <el-tag type="success">智育育成绩完成度</el-tag>
                  <el-progress :text-inside="true" :stroke-width="24" :percentage="targetComplitionlist[1]" status="success"></el-progress>
                  <el-tag type="warning">体育成绩完成度</el-tag>
                  <el-progress :text-inside="true" :stroke-width="22" :percentage="targetComplitionlist[2]" status="warning"></el-progress>
                  <el-tag type="exception">美育成绩完成度</el-tag>
                  <el-progress :text-inside="true" :stroke-width="20" :percentage="targetComplitionlist[3]" status="exception"></el-progress>
                  <el-tag type="info">劳育成绩完成度</el-tag>
                  <el-progress :text-inside="true" :stroke-width="20" :percentage="targetComplitionlist[4]" status="exception"></el-progress>
                  <el-button  class="backR" type="success" @click="backR">返回</el-button>
                </div>
              </el-card>
            </el-col>
            <!-- 学风建设模块 -->
            <el-col :span="12">
              <el-card style="color: #67C23A; min-width: 100%; height: 100%; margin-right: 20px;">
                <div id="pdfDom" class="analyse" v-show="!showpdf" style="margin-left: 60px">
                </div>
                <div id="pdfDom1" class="analyse" v-show="showpdf">
                  <p>{{analyse}}</p>
                  <el-button v-show="!Sswitch" id="info" type="success" @click="tostudystyle">学风建设</el-button>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- 学风建设模块-->
    <div class="studystyleconstruct" v-show="!showresult">
      <div class="editClassactivity">
        <el-radio-group v-model="activityradio">
          <div class="list-group" id="listgroup">
            <button type="button" class="list-group-item list-group-item-info">
              <el-radio class="selectone" label="成绩分析班会" @click.native.prevent="radioChange('成绩分析班会')"></el-radio>
            </button>
            <button type="button" class="list-group-item ">
              <el-radio class="selectone" label="学习经验交流会" @click.native.prevent="radioChange('学习经验交流会')"></el-radio>
            </button>
            <button type="button" class="list-group-item list-group-item-info">
              <el-radio class="selectone" label="位置选择班会" @click.native.prevent="radioChange('位置选择班会')"></el-radio>

            </button>
            <button type="button" class="list-group-item ">
              <el-radio class="selectone" label="心理主题班会" @click.native.prevent="radioChange('心理主题班会')"></el-radio>

            </button>
            <button type="button" class="list-group-item list-group-item-info">
              <el-radio class="selectone" label="春游" @click.native.prevent="radioChange('春游')"></el-radio>

            </button>
            <button type="button" class="list-group-item" >
              <el-input  id="actel"  size="mini" placeholder="自定义班级活动" v-model="activity" clearable></el-input>
            </button>
            <button type="button" class="list-group-item list-group-item-info">
              <div class="block">
                <el-date-picker
                  size="mini"
                  v-model="time"
                  type="datetime"
                  placeholder="选择日期时间">
                </el-date-picker>
              </div>
            </button>
          </div>
        </el-radio-group>
        <el-button  class="sub" type="success" @click="postactivity">提交</el-button>
      </div>
      <el-button  class="back" type="success" @click="backto">返回</el-button>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';

var optionk;
optionk = {
  title: {
    text: '成绩进步曲线图示例'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    selected: {
      '德育':false
    }
  },
  toolbox: {
    show: true,
    orient:"vertical",
    feature: {
      dataZoom: {
        yAxisIndex: 'none'
      },
      dataView: { readOnly: false },
      magicType: { type: ['line', 'bar'] },
      restore: {},
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['2020', '2021', '2022']
  },
  yAxis: {
    type: 'value',
    axisLabel: {
      formatter: '{value} 分'
    }
  },
  series: [
    {
      name: '智育',
      type: 'line',
      data: [60,70,80],
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    },
    {
      name: '德育',
      type: 'line',
      data: [60,60,60],
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    }
  ]
};

import re_htmlToPdf from '@/utils/re_htmlToPdf'
export default {
  name:"Dresult",
  data(){
    return{
      formLabelWidth: '120px',
      showresult:true,
      showpdf:false,
      type:'1',
      Sswitch:false,
      showselect:true,
      input:'',
      activityradio:'',
      activity:'',
      time:'',
      grade:'',
      Sclass:'',
      analyse:'示例',
      showtarget:false,
      targetstudent:'示例',
      targetComplitionlist:[],
      grades:[{ label: '全部年级', value: '' }],
      classes:[{ label: '全部班级', value: '' }]
    }
  },
  methods:{

      // 转换数据格式的方法
  transformData1(data) {
    const transformedData = data.map(item => ({
      label: `${item}年级`, // 添加前缀和后缀
      value: item
    }));
    // 插入“全部年级”选项
    transformedData.unshift({ label: '全部年级', value: ''});
    return transformedData;
  },

  transformData2(data) {
    const transformedData = data.map(item => ({
      label: `${item}班`, // 添加前缀和后缀
      value: item
    }));
    // 插入“全部班级”选项
    transformedData.unshift({ label: '全部班级', value: ''});
    return transformedData;
  },

    showtargetC(){
      this.showtarget=true;
    },

    /* 单选框取消选中*/
    radioChange(txt){
      this. activityradio?this.activityradio=false:this.activityradio=txt;
    },

    tostudystyle(){
      this.showresult=false;
    },

    backto(){
      this.showresult=true;
    },
    backR(){
      this.showtarget=false;
    },
    //获取所有年级
    fetchGrades() {

      axios.get('http://36.111.68.174:33380/diagnose/GetGrades')
        .then(response => {
          this.grades = this.transformData1(response.data);
        })
        .catch(error => {
          console.error('Error fetching grades:', error);
        });
    },

    //获取班级
    fetchClasses() {
      axios.get('http://36.111.68.174:33380/diagnose/GetClasses',{
        params: {
          grade: this.grade
        }
      })
      .then(response => {
          this.classes = this.transformData2(response.data);
        })
        .catch(error => {
          console.error('Error fetching classes:', error);
        });
    },

    /*显示学生进步情况*/
    studentgrad(){
    },
    /* 得到学生成绩*/
    searchStudent(){
      if(this.input==''){
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '输入不能为空!',
        });
        return;
      }
      var Name='NULL'
      var Id=-1
      var reg = new RegExp("[0-9]")//
      if(reg.test(this.input)){//包含数字,赋值给id
        Id=this.input
      }else{
        Name=this.input
      }
// 图形化显示
      var chartDom = document.getElementById('contrastResult');
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: '成绩对比'
        },
        legend: {
          data: ['上次成绩', '本次班级平均成绩','本次成绩']
        },
        radar: {
          // shape: 'circle',
          indicator: [
            { name: '德育', max: 100 },
            { name: '智育', max: 100 },
            { name: '体育', max: 100 },
            { name: '美育', max: 100 },
            { name: '劳育', max: 100 }
          ]
        },
        series: [
          {
            name: '成绩对比',
            type: 'radar',
            tooltip:{
              trigger:'item'
            },
            data: [
              {
                value:[],
                name: '上次成绩'
              },
              {
                value:[],
                name: '本次班级平均成绩'
              },
              {
                value: [],
                name: '本次成绩'
              }
            ]
          }
        ]
      };

      var chartDom = document.getElementById('pdfDom');
      var myChart1 = echarts.init(chartDom);
      var option1;

      option1 = {
        title: {
          text: '学生成绩进步性',
          top: '5%', // 调整标题位置
          left: 'center'
        },
        // tooltip: {
        //   trigger: 'axis'
        // },
        legend: {
          top: '15%', // 调整图例位置
          left: 'center',
          selected:{
            '德育':false,
            '美育':false,
            '体育':false,
            '劳育':false
          }
        },
        grid: {
          top: '30%', // 调整图表内容距离顶部的距离
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        toolbox: {
          show: true,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            dataView: { readOnly: false },
            magicType: { type: ['line', 'bar'] },
            restore: {},
            saveAsImage: {}
          },
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['2020', '2021', '2022']
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} 分'
          }
        },
        series: [
          {
            name: '智育',
            type: 'line',

            data: [],
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: []
            }
          },
          {
            name: '德育',
            type: 'line',
            data: [],
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: []
            }
          },
          {
            name: '美育',
            type: 'line',
            data: [],
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: []
            }
          },
          {
            name: '体育',
            type: 'line',
            data: [],
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: []
            }
          },
          {
            name: '劳育',
            type: 'line',
            data: [],
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: []
            }
          }
        ]
      };


      /* 获取学生上次成绩与本次成绩*/
      axios.get("http://36.111.68.174:33380/diagnose/studentscore",{
        params:{
          id:Id,
          name:Name
        }
      }).then(
        response =>{
          if(response.data.length==0){
            const h = this.$createElement;
            this.$notify({
              title: '提示信息',
              message: '请输入正确且完整的姓名或学号!',
            });
          }
          /* 上次成绩*/
          option.series[0].data[0].value[0]=response.data[1].sDeyu;
          option.series[0].data[0].value[1]=response.data[1].sZhiyu
          option.series[0].data[0].value[2]=response.data[1].sTiyu
          option.series[0].data[0].value[3]=response.data[1].sMeiyu
          option.series[0].data[0].value[4]=response.data[1].sLaoyu
          /* 本次成绩*/
          option.series[0].data[2].value[0]=response.data[0].sDeyu;
          option.series[0].data[2].value[1]=response.data[0].sZhiyu
          option.series[0].data[2].value[2]=response.data[0].sTiyu
          option.series[0].data[2].value[3]=response.data[0].sMeiyu
          option.series[0].data[2].value[4]=response.data[0].sLaoyu

          option1.series[0].data = [response.data[1].sZhiyu,response.data[0].sZhiyu];
          option1.series[1].data = [response.data[1].sDeyu,response.data[0].sDeyu];
          option1.series[2].data = [response.data[1].sMeiyu,response.data[0].sMeiyu];
          option1.series[3].data = [response.data[1].sTiyu,response.data[0].sTiyu];
          option1.series[4].data = [response.data[1].sLaoyu,response.data[0].sLaoyu];
          console.log(response.data);
          option && myChart.setOption(option);
          option1 && myChart1.setOption(option1);
        },
        error =>{
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '出错了!',
          });
        })
      /* 获取学生所在班级平均成绩*/
      axios.get("http://36.111.68.174:33380/diagnose/avgscore",{
        params:{
          id:Id,
          name:Name
        }
      }).then(
        response =>{
          for(var i=0;i<response.data.length;i++){
            option.series[0].data[1].value[i]=response.data[i]
          }
          option && myChart.setOption(option);
        },
        error =>{
        })
      /* 获取学生计划完成度*/
      axios.get("http://36.111.68.174:33380/diagnose/studentplancomplete",{
        params:{
          id:Id,
          name:Name
        }
      }).then(
        response =>{
          /* 解决精度*/
          for(let i=0;i<response.data.length;i++){
            response.data[i]=Math.round(response.data[i]);
          }
          this.targetComplitionlist=response.data;
          //console.log(response.data)
          this.targetstudent='学生计划完成度';
        },
        error =>{
        })
      /* 获取学生成绩分析*/
      axios.get("http://36.111.68.174:33380/studentscoreanalyse",{
        params:{
          id:Id,
          name:Name
        }
      }).then(
        response =>{
          this.analyse=response.data;
        },
        error =>{
        })
    },
    postactivity(){
      if(this.activityradio==''&&this.activity==''){
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '班级活动主题不能为空!',
        });
        return;
      }
      if(this.time==''){
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '时间不能为空!',
        });
        return;
      }
      if(this.activityradio!=''&&this.activity!=''){
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '每次只能提交一种班级活动',
        });
        return;
      }
      axios.get("http://36.111.68.174:33380/diagnose/teachactivity",{
        params:{
          activity:this.activityradio||this.activity,
          sclass:this.Sclass,
          grades:this.grade,
          date:this.time
        }
      }).then(
        response =>{
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '提交成功',
          });
        },
        error =>{
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '提交失败',
          });
        }
      )

    }
  },
  /* 加载初始示例图*/
  mounted() {

    this.fetchGrades();

    var chartDom = document.getElementById('contrastResult');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
      title: {
        text: '成绩对比图示例',
        top: '5%',
        left: 'center'
      },
      legend: {
        data: [ '本次班级平均成绩','本次年级平均成绩']
      },
      radar: {
        //shape: 'circle',
        top: '15%',
        indicator: [
          { name: '德育', max: 100 },
          { name: '智育', max: 100 },
          { name: '体育', max: 100 },
          { name: '美育', max: 100 },
          { name: '劳育', max: 100 }
        ],
        center: ['50%', '60%'], // 调整雷达图中心位置
        radius: '60%' // 调整雷达图半径
      },
      series: [
        {
          name: '成绩对比',
          type: 'radar',
          data: [
            {
              value:[90,90,90,90,90],
              name: '本次班级平均成绩'
            },
            {
              value: [85,85,85,85,85],
              name: '本次年级平均成绩'
            }
          ]
        }
      ]
    };
    option && myChart.setOption(option);

    var chartDom = document.getElementById('pdfDom');
    var myChart1 = echarts.init(chartDom);

     optionk && myChart1.setOption(optionk);
   },
  /* 计算属性，返回grade,Sclass*/
   computed:{
     classgrade() {
       return {
         grade:this.grade,
         Sclass:this.Sclass
      }
    }
  },
  watch:{

    grade(newVal, oldVal) {
      console.log('年级发生变化:', newVal, '旧值:', oldVal);
      this.fetchClasses();
    },

    /* 改变student与class显示*/
    type(){
      if(this.type=="1" || this.type==""){
        this.showselect = true,
        this.analyse='示例';
        this.Sswitch=false;
        this.input='';
        this.targetComplitionlist=[];
        this.targetstudent='示例';
        var chartDom = document.getElementById('contrastResult');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
          title: {
            text: '班级成绩对比图示例',
            top: '5%',
            left: 'center'

          },
          legend: {
            top: '15%',
            data: [ '本次班级平均成绩','本次年级平均成绩']
          },
          radar: {
            // shape: 'circle',
            indicator: [
              { name: '德育', max: 100 },
              { name: '智育', max: 100 },
              { name: '体育', max: 100 },
              { name: '美育', max: 100 },
              { name: '劳育', max: 100 }
            ]
          },
          series: [
            {
              name: '成绩对比',
              type: 'radar',
              data: [
                {
                  value:[90,90,90,90,90],
                  name: '本次班级平均成绩'
                },
                {
                  value: [85,85,85,85,85],
                  name: '本次年级平均成绩'
                }
              ]
            }
          ]
        };
        option && myChart.setOption(option);

        var chartDom = document.getElementById('pdfDom');
        var myChart1 = echarts.init(chartDom);

        optionk && myChart1.setOption(optionk);

        this.showpdf = false;
      }
      else{
        this.analyse='示例';
        this.showselect = false,
        this.Sswitch=true;
        this.grade='';
        this.Sclass='';
        var chartDom = document.getElementById('contrastResult');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
          title: {
            text: '个人成绩对比图示例',
            top: '5%', // 调整标题位置
            left: 'center'
          },
          legend: {
            top: '15%',
            data: [ '上次成绩','本次班级平均成绩','本次成绩']
          },
          radar: {
            // shape: 'circle',
            indicator: [
              { name: '德育', max: 100 },
              { name: '智育', max: 100 },
              { name: '体育', max: 100 },
              { name: '美育', max: 100 },
              { name: '劳育', max: 100 }
            ],
            center: ['50%', '60%'], // 调整雷达图中心位置
            radius: '60%' // 调整雷达图半径
          },
          series: [
            {
              name: '成绩对比',
              type: 'radar',
              data: [
                {
                  value:[90,90,90,90,90],
                  name: '本次成绩'
                },
                {
                  value: [85,85,85,85,85],
                  name: '本次班级平均成绩'
                },
                {
                  value: [80,80,80,80,80],
                  name: '上次成绩'
                }
              ]
            }
          ]
        };
        option && myChart.setOption(option);

        var chartDom = document.getElementById('pdfDom');
        var myChart1 = echarts.init(chartDom);

        optionk && myChart1.setOption(optionk);

        this.showpdf = false;
      }
    },
    /* 监视选项*/
    classgrade:{
      handler: function(newval,oldval) {
        /* 为空不加载数据*/
        if(newval.grade==''||newval.Sclass==''){
          return;
        }

        var chartDom = document.getElementById('contrastResult');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
          title: {
            text: '成绩对比',
            top: '5%',
            left: 'center'
          },
          legend: {
            top: '15%',
            data: ['本次班级平均成绩','本次年级平均成绩']
          },
          radar: {
            // shape: 'circle',
            indicator: [
              { name: '德育', max: 100 },
              { name: '智育', max: 100 },
              { name: '体育', max: 100 },
              { name: '美育', max: 100 },
              { name: '劳育', max: 100 }
            ]
          },
          series: [
            {
              name: '成绩对比',
              type: 'radar',
              data: [
                {
                  value:[],
                  name: '本次班级平均成绩'
                },
                {
                  value:[],
                  name: '本次年级平均成绩'
                }
              ]
            }
          ]
        };
        /* 获取班级平均成绩与年级平均成绩*/
        axios.get("http://36.111.68.174:33380/diagnose/classscore",{
          params:{
            grade:newval.grade,
            sclass:newval.Sclass
          }
        }).then(
          response =>{
            //console.log(response.data)
            for(var i=0;i<5;i++){

              option.series[0].data[0].value[i]=response.data[i]
            }
            //console.log(option.series[0].data[0].value)
            for(var i=0;i<5;i++){
              option.series[0].data[1].value[i]=response.data[i+5]
            }
            /* option 在axios内设置*/
            option && myChart.setOption(option);
          },
          error =>{
            const h = this.$createElement;
            this.$notify({
              title: '提示信息',
              message: '加载失败',
            });
          }
        )
        /* 获取班级分析*/
        axios.get("http://36.111.68.174:33380/diagnose/classanalyse",{
          params:{
            grade:newval.grade,
            sclass:newval.Sclass
          }
        }).then(
          response =>{
            this.analyse=response.data;
            this.showpdf = true;
          },
          error =>{
            const h = this.$createElement;
            this.$notify({
              title: '提示信息',
              message: '加载失败',
            });
          }
        )
      },
      deep: true
    },
  }

}
</script>

<style scoped>
.Dresults{
  width:1300px;
  height:100%;
  position:absolute;
  top:0px;
  left:0px;
}

.Dresults span{
  font-weight:bold;
  vertical-align: center;
  margin-right:5px;
}

.select-wrapper{
  width:1300px;
  height:34px;
  position:absolute;
  z-index:999;/* 设立优先级防止遮蔽 */
  background-color:#caf7c6;
  border-bottom:1px solid #79ff9d;
}
.select-class,.select-grade,.select-type{
  width:193px;
  height:100px;
  float:left;
  margin-left:20px;
  margin-top:5px;
}

#info{
  /*margin-left: 20px;*/
  position: absolute;
  right: 20px;
  bottom:20px;
}
.el-input{
  width:300px;
  height:20px;
}
#ser{
  margin-left: 20px;
}
.advice-info{
  height:40px;
  width:100%;
  /*margin-top:35px;*/
  /*border:1px solid #79ff9d;*/
  border-top:none;
  color:red;
  font-size:14px;
}
.advice-info p{
  line-height: 40px;
  margin-left:50px;
}

.advice-info p a{
  color:#ff5500;
  margin-left:40px;
}
.advice-info p a:hover{
  color:orange;
}
.result-show{
  width:100%;
  height:100%;
  /*border:1px solid #67C23A;*/
  /*border: none;*/
}
.result-show p{
  margin-left:100px;
  margin-top:50px;
}

#contrastResult{
  width:590px;
  height:400px;
  margin-top:20px;
  margin-left:10px;
  float:left;
}
.targetComplition{
  width:590px;
  height:400px;
  margin-top:20px;
  margin-left:10px;
  float:left;
}
.targetComplition p{
  font-weight:bold;
  font-size: large;
  line-height: 1.5;
  margin-top:-10px;
  margin-left:50px;
}
.analyse{
  width:500px;
  height:400px;
  margin-top:20px;
  float:left;
  margin-left:0px;
  /*background-color:#f0f0f0;*/
}

.analyse p{
  font-family: "微软雅黑","Microsoft YaHei","Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB",Arial,sans-serif;
  font-weight:bold;
  font-size: large;
  line-height: 1.5;
  margin-top:2px;
  margin-left:0px;
  text-indent: 40px;
}
.studystyleconstruct{
  position:relative;
}
.editClassactivity{
  width:1050px;
  height:418px;
  position:absolute;
  top:60px;
  left:60px;
  border:1px solid rgb(252,251,220);
  box-shadow:2px 2px 5px;
}
#listgroup{
  position:absolute;
  top:30px;
  left:150px;
  width:800px;
}
.el-date-picker{
  width:100px;
}
.sub{
  position:absolute;
  top:380px;
  left:500px;
}
.back{
  position:absolute;
  top:500px;
  left:1000px;
}
.el-tag{
  margin-top:10px;
}
.backR{
  position:absolute;
  top:483px;
  left:200px;
}
</style>

