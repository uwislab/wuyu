<template>
  <div>
    <div style="display: flex;flex-direction: row;padding:30px;justify-content: space-between;margin-top: 20px;margin-left: 80px;margin-right: 80px;
  box-shadow: -2px 2px 6px #e1e1e1,2px -2px 6px #e1e1e1;background: #fdf9f9">
      <!--    模糊查询前端-->
      <div style="display: flex;flex-direction: row;justify-content: space-between;align-items: flex-end">
        <p style="flex: 1;">学号:</p>
        <el-input style="flex: 3.5" v-model="selectList.student_num" placeholder="请输入学号"></el-input>
      </div>
      <div style="display: flex;flex-direction: row;justify-content: space-between;align-items: flex-end">
        <p style="flex: 1;">姓名:</p>
        <el-input style="flex: 3.5" v-model="selectList.student_name" placeholder="请输入姓名"></el-input>
      </div>
      <div style="display: flex;flex-direction: row;justify-content: space-between;align-items: flex-end">
        <p style="flex: 1;">班级:</p>
        <el-select style="flex: 3.5" v-model="selectList.class_name" placeholder="请选择班级">
          <el-option
            v-for="item in class_list"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </div>
      <div style="display: flex;flex-direction: row;justify-content: space-between;align-items: flex-end">
        <p style="flex: 1;">年级:</p>
        <el-select style="flex: 3.5" v-model="selectList.grade" placeholder="请选择年级">
          <el-option
            v-for="item in grade_list"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </div>
      <el-button @click="paramReset">重置</el-button>
      <el-button type="primary" @click="searchData()">查询</el-button>

      <!--    成绩列表前端-->
    </div>
    <div style="margin-left: 80px;font-size: 20px;margin-top: 30px"> 查询出的学生成绩列表如下</div>
    <div style="display: flex ; flex-direction: row;justify-content: space-between;margin: 0 30px 30px;">

      <div style="flex: 1.5;margin-left: 50px;margin-right: 50px;flex-direction: column">
        <el-table id="my-table"
                  :data="studentGradesList">
          <el-table-column
            prop="studentNum"
            label="学号"
            width="200">
          </el-table-column>
          <el-table-column
            prop="studentName"
            label="姓名"
            width="150">
          </el-table-column>
          <el-table-column
            prop="gender"
            label="性别"
            width="150">
          </el-table-column>
          <el-table-column
            prop="grade"
            label="年级"
            width="150">
          </el-table-column>
          <el-table-column
            prop="className"
            label="班级"
            width="200">
          </el-table-column>
          <el-table-column
            prop="score"
            label="总分"
            width="150">
          </el-table-column>
          <el-table-column>
            <template slot-scope="scope"></template>
          </el-table-column>
        </el-table>
        <div style="display: flex ;justify-content: center;margin-top: 10px">
          <!-- </el-select> -->
          <!-- <el-button class="myBut" @click="fristPage()">首页</el-button>
          <el-button class="myBut" @click="prePage()">上页</el-button>
          <div style="font-size: 20px; text-align: center;" class="myBut"> 第{{this.pageNum}}页</div>
          <div style="font-size: 20px; text-align: center;" class="myBut"> 共{{this.totalPage}}页</div>
          <el-button class="myBut" @click="nextPage()">下页</el-button>
          <el-button class="myBut" @click="tailPage()">尾页</el-button> -->

          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="pageNum"
            :page-size="100"
            layout="total, prev, pager, next"
            :total="this.total">
          </el-pagination>

        </div>
      </div>

      <!--    图表前端-->
      <div style="flex: 1;background: #fcfcfc;margin-right: 30px">
        <div ref="myCharts" style="width: 100%;height: 350px"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";



export default {
  name:'show',
  data() {
    // 验证ID是否已经存在
    return {
      list: [],//显示数据
      // class_list:['1班','2班','3班'],
      // grade_map:[{grade:'一年级',val:1},{grade:'二年级',val:2},{grade:'三年级',val:3}],
      // grade_list:['一年级','二年级','三年级'],
      currentPage: 1,//当前页
      // pageSize: 5,//每页显示记录数
      allPage:0,
      allInfo:0,
      // total: 0,//总记录数
      // selectList: {page_num:1,page_size:5,student_num:'',student_name:'',class_name:'',grade:''},

      total: 0,//总记录数
      pageNum:1,
      pageSize:12,
      firstPage:1,
      lastPage:false,
      totalPage:0,
      studentGradesList:[],
      grade_list:[],
      class_list:[],
      selectList: {page_num:1,page_size:5,student_num:'',student_name:'',class_name:'',grade:''},

      myOption:{
        title: {
          text: '学生成绩分布',
          subtext: '所有数据',
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
            name: '学生成绩分布',
            type: 'pie',
            radius: '50%',
            data:[{ value: 1048, name: '优' }, { value: 735,name: '良' }, { value: 580, name: '中' }, { value: 484, name: '及格' }, { value: 300, name: '不及格' }],
            // data:[],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
    }
  },
  methods: {
    handleCurrentChange() {
      this.getStudentGrades();
    },
    jumpToDeclare(row){
      router.push({ name: 'personal', query:row.id })
    },

    nextPage() {
      if(this.pageNum == this.totalPage) {
        this.$message.error("最后一页了");
      }
      else {
        this.pageNum = this.pageNum + 1;
        this.getStudentGrades()
      }
    },
    prePage() {
      if (this.pageNum === 1) {
        this.$message.error("已经是第一页了");
      }
      else {
        this.pageNum = this.pageNum - 1;
        this.getStudentGrades()
      }
    },
    fristPage() {
      this.pageNum = 1;
      this.getStudentGrades()
    },
    tailPage() {
      this.pageNum = this.totalPage;
      this.getStudentGrades()
    },
    getAllGrades() {
      request({
        url: '/studentGrades/grades',
        method: 'get',

      })
        .then(response => {
          if(response.code == 200) {
            this.grade_list = response.data
            this.$message.success("获取所有年级成功");
          }
          if(response.conde == 500) {
            this.$message.error("获取所有年级出错");
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    getAllClass() {
      request({
        url: '/studentGrades/class',
        method: 'get',

      })
        .then(response => {
          if(response.code == 200) {
            this.class_list = response.data
            this.$message.success("获取所有班级成功");
          }
          if(response.conde == 500) {
            this.$message.error("获取所有班级出错");
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    getStudentGrades() {
      request({
        url: '/studentGrades/search',
        params:{pageNum:this.pageNum,
          pageSize:this.pageSize,
          studentNum:this.selectList.student_num,
          studentName:this.selectList.student_name,
          className:this.selectList.class_name,
          grade:this.selectList.grade,
        },
        method: 'get',

      })
        .then(response => {
          if(response.code == 200) {
            this.total = response.data.total;
            this.studentGradesList = response.data.list;
            for (let i = 0;i < this.studentGradesList.length;i++) {
              if (this.studentGradesList[i].gender == 1) {
                this.studentGradesList[i].gender = '女'
              }
              if (this.studentGradesList[i].gender == 0) {
                this.studentGradesList[i].gender = '男'
              }
            }
            this.totalPage = response.data.pages;
            if (response.data.isLastPage === true) {
              this.lastPage = true;
            }
            this.$message.success("获取信息成功");
          }
          if(response.code == 500) {
            this.$message.error("没查到数据");
            this.studentGradesList = null;
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    searchData(){
      this.pageNum = 1;
      this.getStudentGrades();
      this.getChartData();
    },
    getChartData() {
      request({
        url: '/studentGrades/chart',
        params:{
          studentNum:this.selectList.student_num,
          studentName:this.selectList.student_name,
          className:this.selectList.class_name,
          grade:this.selectList.grade,
        },
        method: 'get',
      })
        .then(response => {
          if(response.code == 200) {
            const data = response.data
            var chartData = Object.keys(data).map(function(key) {
              return {
                name : key,
                value : data[key]
              }
            })
            this.myOption.series[0].data = chartData
            let myEcharts=echarts.init(this.$refs.myCharts)
            myEcharts.setOption(this.myOption)
            this.$message.success("获取图标数据成功");
          }
          if(response.code == 500) {
            this.$message.error("获取图标数据失败");
          }
        })
        .catch(error => {
          this.$message.error(error)
        })
    },

    paramReset(){
      this.selectList={page_num:0,page_size:5,student_num:'',student_name:'',class_name:'',grade:''}
      this.currentPage=1
      this.getTableData()
    },

  },
  mounted(){



    this.getStudentGrades()
    this.getAllClass()
    this.getAllGrades()
    this.getChartData()
  },
  created() {
  }
}
</script>

<style>
.myBut{
  flex: 1;
  margin-left: 10px;
  margin-right: 10px;
}
.el-table .singular-row{
  background: #ffffff;
}
.el-table .even-row{
  background: #d7d7d7;
}
#my-table thead th{
  background-color: #324257;
  color: #ffffff;
}
</style>
