<template>
  <div>
    <el-row :gutter="1" class="search">
      <el-input v-model="studentName" placeholder="请输入学生姓名" style="width:20%"></el-input>
      <el-input v-model="studentNum" placeholder="请输入学生学号" style="width:20%"></el-input>
      <el-button class="searchbtn" type="primary" icon="el-icon-search" @click="reset()">重置</el-button>
      <el-button class="searchbtn" type="primary" icon="el-icon-search" @click="search()">查询</el-button>
    </el-row>

    <el-row :gutter="10" class="content">


      <el-col :xs="24" :sm="10">
        <el-card class="student-card">
          <el-row>
            <el-col :span="8">
              <div class="student-photo">
                <img src="https://pic2.zhimg.com/80/v2-76a083febca664209097391ed4750769_720w.webp" alt="学生照片">
              </div>
            </el-col>
            <el-col :span="16">
              <el-row>
                <el-col :span="24">
                  <el-descriptions title="学生信息" :bordered="true" :column="1">
                    <el-descriptions-item label="姓名">{{ list.studentName }}</el-descriptions-item>
                    <el-descriptions-item label="性别">{{ list.gender }}</el-descriptions-item>
                    <el-descriptions-item label="学号">{{ list.studentNum }}</el-descriptions-item>
                  </el-descriptions>
                </el-col>
              </el-row>

            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-descriptions title="学生成绩" :bordered="true" :column="1">
                <el-descriptions-item label="德">{{ list.moralityScore }}</el-descriptions-item>
                <el-descriptions-item label="智">{{ list.intelligenceScore }}</el-descriptions-item>
                <el-descriptions-item label="体">{{ list.physicalScore }}</el-descriptions-item>
                <el-descriptions-item label="美">{{ list.aestheticScore }}</el-descriptions-item>
                <el-descriptions-item label="劳">{{ list.labourScore }}</el-descriptions-item>
                <!-- <el-descriptions-item label="教师评语">{{ list.remark }}</el-descriptions-item> -->
              </el-descriptions>
              <el-collapse>
                <el-collapse-item title="教师评语">
                  <p>{{ list.remark }}</p>
                </el-collapse-item>
              </el-collapse>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-card class="box-card">
        <el-table :data="grades" border>
          <el-table-column prop="evaluateDate" label="学期"></el-table-column>
          <el-table-column prop="avgScore" label="成绩"></el-table-column>
        </el-table>
      </el-card>



      <el-card class="box-card">
        <div id="line-chart" style="width: 100%; height: 60vh;"></div>
      </el-card>

    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts"
import request from "@/utils/request"

export default {
  name:'personal',
  data(){
    return {
      studentName:'',
      studentNum:'',
      moralityScore: "",
      intelligenceScore: "",
      physicalScore: "",
      aestheticScore: "",
      labourScore: "",
      remark: "",
      list:"",
      avg1:"",
      avg2:"",
      avg3:"",
      grades:[
        {
          "avgScore": '',
          "evaluateDate": "第1学期"
        },
        {
          "avgScore": '',
          "evaluateDate": "第2学期"
        },

        {
          "avgScore": '',
          "evaluateDate": "第3学期"
        }
      ]
    }
  },
  methods:{
    getData() {
      request({
        url: '/personal/search',
        params:{
          studentNum:this.studentNum,
          studentName:this.studentName
        },
        method: 'get',
        // baseURL: 'http://localhost:33312'
        // baseURL: 'http://36.111.68.174:33312'
      })
        .then(response => {
          if(response.code == 200) {
            this.list = response.data[0];
            if(this.list.gender == 1) {
              this.list.gender = "男"
            }
            else {
              this.list.gender = "女";
            }
            this.avg1 = (this.list.moralityScore + this.list.intelligenceScore + this.list.physicalScore)/3;
            this.avg2 = (this.list.aestheticScore + this.list.intelligenceScore + this.list.physicalScore)/3;
            this.avg3 =  (this.list.aestheticScore + this.list.intelligenceScore + this.list.labourScore)/3;
            this.grades = [
              {
                "avgScore": this.avg1,
                "evaluateDate": "第1学期"
              },
              {
                "avgScore": this.avg2,
                "evaluateDate": "第2学期"
              },

              {
                "avgScore": this.avg3,
                "evaluateDate": "第3学期"
              }
            ]
            this.showLineChart();
            this.$message.success("查询成功");
          }
          if(response.code == 500) {
            this.$message.error("查询失败");
          }
        })
        .catch(error => {
          this.$message.error(error)
        })},
    search() {
      if (this.studentNum == '' && this.studentName == '') {
        this.$message.error("至少一个不为空");
        return;
      }
      if(this.studentNum != '') {
        // this.$message.error(typeof(this.studentNum));
        if (this.studentNum.length != 10 || !/^\d{10}$/.test(this.studentNum)) {
          this.$message.error("学号必须为10位数字");
          return;
        }
        this.getData();
      }
      else {
        this.getData();
      }
    },
    showLineChart() {
      var chartDom = document.getElementById("line-chart");
      var myChart = echarts.init(chartDom);
      var option = {
        xAxis: {
          type: "category",
          data: this.grades.map((grade) => grade.evaluateDate),
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: this.grades.map((grade) => grade.avgScore),
            type: "line",
          },
        ],
      };
      myChart.setOption(option);
    },
    reset() {
      this.studentName = '';
      this.studentNum = '';
    },
    showDetails(row) {
      // TODO: Implement the logic to show the details of a specific grade
    },
  },
  mounted(){
    this.studentName = window.localStorage.getItem("studentName");
    if(window.localStorage.getItem("studentName")) {
      this.getData();
    }
    window.localStorage.removeItem("studentName");
    this.showLineChart();
  }
}
</script>

<style>

.student-award{
  width: 500px;
  margin: 0 auto;

  align-items: center;
  justify-content: center;
}

.award-photo {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  text-align: center;
}

.award-photo img {
  max-width: 100%;
  max-height: 100%;
}

.student-card {
  width: 500px;
  height: 874px;

  margin: 0 auto;
}
.student-photo {
  text-align: center;
  margin-bottom: 20px;
}

.student-photo img {
  width: 75%;
  height: auto;
  object-fit: cover;
  border-radius: 50%;
}


.info-row {
  background-color: #f2f2f2;
  border: 1px solid #ccc;
  padding: 3px;
  margin-bottom: 3px;
}

.student-details {
  background-color: #f2f2f2;
  border: 1px solid #ccc;
  padding: 3px;
  margin-top: 3px;
}

.search {
  margin: 20px 50px 0 50px;
}

.searchbtn {
  margin-left: 10px;
}

.content {
  margin: 20px 50px 0 50px;
}





.student-details {
  margin-left: 20px;
}

.award-photo {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 90%;
  text-align: center;
  margin-bottom: 20px;
}

.el-table {
  width: 100%;
  height: 100%;
}

#line-chart {
  width: auto;
  height: auto;
}
</style>
