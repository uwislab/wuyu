<template>
  <div class="maindiv clearfix">
    <!--    信息框-->
    <div v-show="!showdetail" class="Sportscard clearfix">
      <div style="margin-top: 20px;margin-left: 20px">
        <el-input size="mini" placeholder="请输入学号或姓名" v-model="input" clearable>
        </el-input>
        <el-button id="ser" @click="searchStudent" type="primary" icon="el-icon-search" size="small">Search</el-button>
        <div>
          <el-button type="success" @click="ChangePage" id="detailbtn">小学体育评价细节</el-button>
        </div>
      </div>



      <el-form style="margin-top: 40px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-card style="width: 950px;margin-left: 10px">
              <div class="contentbox clearfix">
                <div id="Sportsdiagnosis" style="margin-top: 20px"></div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card style="margin-left: 320px;width: 950px;height: 500px">
              <div id="Sportsdiagnosistwo" style="margin-top: 100px"></div>
            </el-card>
          </el-col>
          <el-col>
            <el-card style="margin-left: 10px; width: 950px ">
              <div class="">
                <div id="SportsAnalyse">
                  <p>{{Analyse}}</p>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-form>
    </div>



    <div v-show="showdetail">
      <div width="100%" class="btnList">
        <el-button type="info" @click="ChangePage">返回</el-button>
        <el-button type="danger" id="ExcelBtn" @click="exportExcel">导出为Excel</el-button>
        <el-button type="warning" id="ExcelBtn" @click="getPdf('小学学生体育评价细节')">导出为PDF</el-button>
      </div>
      <!--      体育评价量表-->
      <div id="pdfDom">
        <el-tag type="success">小学学生体育评价量表</el-tag>
        <el-table class="SportsStandardsDiv" id="SportsStandardsTable" :data="SportsStandards" style="width: 100%">
          <el-table-column prop="content" label="评价内容" align="center" width="250">
          </el-table-column>
          <el-table-column prop="Standard" label="评价方式及评价标准" align="center" width="180">
          </el-table-column>
          <el-table-column prop="evaluator" label="评价者" align="center">
          </el-table-column>
          <el-table-column prop="proportion" label="评价维度及占比" align="center" width="250">
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import * as echarts from 'echarts';
import FileSaver from 'file-saver'
import * as XLSX from 'xlsx'
export default {
  name: 'SportsD',
  data() {
    return {
      Analyse: '',
      input: '',
      showdetail: false,
      imgList: [{
        id: '1',
        url: require('./photo/跑步.jpg')
      },
        {
          id: '2',
          url: require('./photo/拔河.jpg')
        },
        {
          id: '3',
          url: require('./photo/足球.jpg')
        },
      ],
      SportsStandards: [{
        content: '积极参与、团结协作、意志品质等',
        Standard: '专业量表测试',
        evaluator: '体育教师',
        proportion: '体育品德(5%)'
      },
        {
          content: '体育与健康常识、安全等',
          Standard: '学科测试：百分折算',
          evaluator: '体育教师',
          proportion: '体育与健康知识(5%)'
        },
        {
          content: '肥胖',
          Standard: '体检数据',
          evaluator: '体育教师',
          proportion: '体质与心理健康(5%)'
        },
        {
          content: '近视',
          Standard: '体检数据',
          evaluator: '体育教师',
          proportion: '体质与心理健康(5%)'
        },
        {
          content: '患病次数',
          Standard: '数据记录：学年实际请病假数',
          evaluator: '体育教师',
          proportion: '体质与心理健康(5%)'
        },
        {
          content: '体质健康',
          Standard: '体质测试数据：参照《国家体质健康标准》',
          evaluator: '体育教师',
          proportion: '体质与心理健康(20%)'
        },
        {
          content: '睡眠',
          Standard: '量表问卷',
          evaluator: '家长、体育教师',
          proportion: '体质与心理健康(5%)'
        },
        {
          content: '心理健康',
          Standard: '量表测试',
          evaluator: '校内心理健康师',
          proportion: '体质与心理健康(5%)'
        },
        {
          content: '运动量：体育课、大课间、校外',
          Standard: '数据记录',
          evaluator: '体育教师',
          proportion: '体锻活动(10%)'
        },
        {
          content: '学科融通',
          Standard: '活动评价',
          evaluator: '相关人士',
          proportion: '体锻活动(10%)'
        },
        {
          content: '体能',
          Standard: '测试数据',
          evaluator: '体育老师',
          proportion: '体育技能(5%)'
        },
        {
          content: '基本运动技能',
          Standard: '测试数据',
          evaluator: '体育老师',
          proportion: '体育技能(5%)'
        },
        {
          content: '专项运动技能（三大球+曲棍球）',
          Standard: '2项运动水平测试',
          evaluator: '专业人士',
          proportion: '体育技能(10%)'
        },
        {
          content: '专项运动员',
          Standard: '数据记录',
          evaluator: '体育老师',
          proportion: '体育技能(1%)'
        },
        {
          content: '体育特长',
          Standard: '等级证书',
          evaluator: '体育老师',
          proportion: '体育技能(1%)'
        },
        {
          content: '体育获奖',
          Standard: '数据记录',
          evaluator: '体育老师',
          proportion: '体育技能(3%)'
        },
      ]
    }
  },
  methods: {

    searchStudent() {
      if (this.input == '') {
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '输入不能为空!',
        });
        return;
      }
      var Name = 'NULL'
      var Id = 1
      var reg = new RegExp("[0-9]") //
      if (reg.test(this.input)) { //包含数字,赋值给id
        Id = this.input
      } else {
        Name = this.input
      }

      var chartDom = document.getElementById('Sportsdiagnosis');
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: '小学体育评价构成'
        },
        legend: {
          data: ['个人成绩', '达标成绩', '优秀成绩']
        },
        radar: {
          indicator:[
            {name:'体育品格',max:100},
            {name:'体育知识',max:100},
            {name:'体育技能',max:100},
            {name:'体锻时长',max:100},
            {name:'体质健康',max:100}
          ]
        },
        series: [{
          name: '成绩评价',
          type: 'radar',
          tooltip: {
            trigger: 'item'
          },
          data: [{
            value: [],
            name: '个人成绩'
          },
            {
              value: [],
              name: '达标成绩'
            },
            {
              value: [],
              name: '优秀成绩'
            }
          ]
        }]
      };
      option && myChart.setOption(option);

      var chartDom = document.getElementById('Sportsdiagnosistwo');
      var myChart1 = echarts.init(chartDom);
      var option1;
      option1 = {
        title: {
          text: '小学体育评价饼图',
          left: 'center'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [{
          name: '成绩评价',
          type: 'pie',
          radius: '50%',
          data: [{
            value: 100,
            name: '体育品格'
          },
            {
              value: 100,
              name: '体育知识'
            },
            {
              value: 100,
              name: '体育技能'
            },
            {
              value: 100,
              name: '体锻时长'
            },
            {
              value: 100,
              name: '体质健康'
            }
          ]
        }]
      };

      axios.get("http://36.111.68.174:33380/diagnose/tiyuscore", {
        params: {
          name: Name,
          id: Id
        }
      }).then(
        response => {
          console.log(response.data)
          this.Analyse = response.data;
          this.diagnose = response.data;
          console.log(diagnose);
        },
        error => {
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '出错了',
          });
        })


      axios.get("http://36.111.68.174:33380/diagnose/tiyuscore/json", {
        params: {
          name: Name,
          id: Id
        }
      }).then(
        response => {
          console.log(response.data)
          let tuscore;
          tuscore = [response.data.sportsMorality, response.data.healthKnowledge,
            response.data.sportsSkills, response.data.sportsActivity,
            response.data.mentalHealth
          ]
          console.log(tuscore);
          option.series[0].data[0].value = tuscore;
          option.series[0].data[1].value = [60, 60, 60, 60, 60];

          for (var i = 0; i < 5; i++) {
            option1.series[0].data[i].value = tuscore[i];
          }
          option && myChart.setOption(option);
          option1 && myChart1.setOption(option1);
        },
        error => {
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '出错了',
          });
        })
    },

    //导出(pdf)
    savePdf() {
      this.$PDFSave(this.$refs.exportPdf, "小学生学生体育评价量表");
    },
    ChangePage() {
      this.showdetail = !this.showdetail;
    },
    exportExcel() {
      /* generate workbook object from table */
      var wb = XLSX.utils.table_to_book(document.querySelector('#SportsStandardsTable'))
      /* get binary string as output */
      var wbout = XLSX.write(wb, {
        bookType: 'xlsx',
        bookSST: true,
        type: 'array'
      })
      try {
        FileSaver.saveAs(new Blob([wbout], {
          type: 'application/octet-stream'
        }), '小学生学生体育评价量表.xlsx')
      } catch (e) {
        if (typeof console !== 'undefined') console.log(e, wbout)
      }
      return wbout
    },
  },
  mounted() {
    var chartDom = document.getElementById('Sportsdiagnosis');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
      title: {
        text: '小学体育评价构成',
      },
      legend: {
        data: ['个人成绩', '达标成绩', '优秀成绩'],
      },
      tooltip: {
        trigger: 'axis'
      },
      radar: {
        indicator:[
          {name:'体育品格',max:100},
          {name:'体育知识',max:100},
          {name:'体育技能',max:100},
          {name:'体锻时长',max:100},
          {name:'体质健康',max:100}
        ],
        center: ['50%', '60%'],
        name: {
          textStyle: {
            padding: [-10, -12]
          }
        }
      },
      series: [{
        name: '成绩评价',
        type: 'radar',
        areaStyle: {},
        tooltip: {
          trigger: 'item'
        },
        data: [{
          value: [60, 60, 60, 60, 60],
          name: '个人成绩'
        },
          {
            value: [],
            name: '达标成绩'
          },
          {
            value: [],
            name: '优秀成绩'
          }
        ]
      }]
    };

    option && myChart.setOption(option);

    var chartDom = document.getElementById('Sportsdiagnosistwo');
    var myChart = echarts.init(chartDom);
    var option;
    option = {
      title: {
        text: '小学体育评价饼图',
        left: 'center'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      tooltip: {
        trigger: 'item'
      },
      series: [{
        name: '成绩评价',
        type: 'pie',
        radius: '50%',
        data: [{
          value: 100,
          name: '体育品格'
        },
          {
            value: 100,
            name: '体育知识'
          },
          {
            value: 100,
            name: '体育技能'
          },
          {
            value: 100,
            name: '体锻时长'
          },
          {
            value: 100,
            name: '体质健康'
          }
        ]
      }]
    };
    option && myChart.setOption(option);
  }
}
</script>
<style scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  clear: both;
  content: '';
}

.maindiv {
  height: 870px;
  border: 1px solid rgb(252, 251, 220);
  box-shadow: 2px 2px 5px;
}

#ser {
  float: left;
  margin-left: 20px;
}

.contentbox {
  margin-top: 50px;
  width: 100%;
  height: 410px;
  /*position:flex;*/
}

#Sportsdiagnosis {
  width: 700px;
  height: 300px;
  margin: 0 auto;
}

#Sportsdiagnosistwo {
  width: 700px;
  height: 300px;
  margin: 0 auto;
}

#SportsAnalyse {

  margin-left: 50px;
  width: 700px;
  height: 200px;
  margin: 0 auto;

}

#SportsAnalyse p {
  font-family: "微软雅黑", "Microsoft YaHei", "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", Arial, sans-serif;
  font-weight: bold;
  font-size: large;
  line-height: 1.5;
  margin-top: 2px;
  margin-left: 0px;
  text-indent: 40px;
}

#detailbtn {
  /*margin-top:140px;*/
  margin-left: 10px;
}

.btnList {
  display: flex;
}

.Sportscard {
  width: 70%;
  /*margin:0 auto;*/
  height: 200px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}
</style>
