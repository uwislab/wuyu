<template>
   <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>
  <div class="main-div">
    <div class="show-div" v-show="!showExcel">
    <!-- 搜索下拉框 -->
    <el-form :inline="true" class="search-form">
      <el-form-item>
        <el-select v-model="selectedGrade" clearable placeholder="选择年级">
          <el-option
            v-for="item in gradeList"
            :key="item.index"
            :label="item == ''? '不选择' : item"
            :value="item == ''? null : item"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="selectedClass" clearable placeholder="选择班级">
          <el-option
            v-for="item in classList"
            :key="item.index"
            :label="item == ''? '不选择' : item"
            :value="item == ''? null : item"
          />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getSportScoreList()">查询</el-button>
      <el-button id="TempleBtn" @click="downloadTemplate()">下载模板</el-button>
      <el-button type="danger" id="ExcelBtn" @click="exportExcel()">导出为Excel</el-button>
      <el-upload class="upload-demo" ref="upload" action=""
                 :auto-upload="false" :file-list="fileList" :on-change="handleChange" multiple
                 :show-file-list="false"
      >
        <el-button type="text" @click="changePage()">点击上传成绩表</el-button>
      </el-upload>

    </el-form>
    <!--  体育成绩列表  -->
    <el-table :data="SportsScoreList" id="sport-score-list">
        <el-table-column type="index" width="50" label="序号" align="center"/>
        <el-table-column prop="studentName" label="学生姓名" width="150" align="center"/>
        <el-table-column prop="studentNum" label="学号" width="150" align="center"/>
          <el-table-column prop="gender" label="性别" width="50" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.gender === true">男</span>
              <span v-if="scope.row.gender === false">女</span>
            </template>
          </el-table-column>
        <el-table-column prop="gradeNum" label="年级" width="150" align="center" />
        <el-table-column prop="classNum" label="班级" width="150" align="center"/>
        <el-table-column label="体育项目" align="center">
          <el-table-column prop="fvcscore" label="肺活量" width="100" align="center"></el-table-column>
          <el-table-column prop="rsscore" label="跳绳" width="100" align="center"></el-table-column>
          <el-table-column prop="bmiscore" label="体重指数" width="100" align="center"></el-table-column>
          <el-table-column prop="sarscore" label="坐位体前屈" width="100" align="center"></el-table-column>
          <el-table-column prop="subscore" label="仰卧起坐" width="100" align="center"></el-table-column>
          <el-table-column prop="oabscore" label="往返跑" width="100" align="center"></el-table-column>
          <el-table-column prop="dashscore" label="短跑" width="100" align="center"></el-table-column>
        </el-table-column>
      </el-table>
    </div>
    <div class="excel-div" v-show="showExcel">
      <button type="text" @click="changePage()">返回</button>
      <el-table :data="excelScoreList" id="excel-list">
        <el-table-column type="index" width="50" label="序号" align="center"></el-table-column>
        <el-table-column prop="学生姓名" label="学生姓名" width="150" align="center"></el-table-column>
        <el-table-column prop="学号" label="学号" width="150" align="center"></el-table-column>
        <el-table-column prop="性别" label="性别" width="50" align="center"></el-table-column>
        <el-table-column prop="年级" label="年级" width="150" align="center"></el-table-column>
        <el-table-column prop="班级" label="班级" width="150" align="center"></el-table-column>
        <el-table-column prop="肺活量" label="肺活量" width="100" align="center"></el-table-column>
        <el-table-column prop="跳绳" label="跳绳" width="100" align="center"></el-table-column>
        <el-table-column prop="体重指数" label="身高" width="100" align="center"></el-table-column>
        <el-table-column prop="体重指数" label="体重" width="100" align="center"></el-table-column>
        <el-table-column prop="坐位体前屈" label="坐位体前屈" width="100" align="center"></el-table-column>
        <el-table-column prop="仰卧起坐" label="仰卧起坐" width="100" align="center"></el-table-column>
        <el-table-column prop="往返跑" label="往返跑/分" width="100" align="center"></el-table-column>
        <el-table-column prop="往返跑" label="往返跑/秒" width="100" align="center"></el-table-column>
        <el-table-column prop="短跑" label="短跑" width="100" align="center"></el-table-column>
      </el-table>
    </div>
  </div>
</el-container>
  </el-container>
</template>

<script>
import axios from "axios";
import * as XLSX from "xlsx";
import * as FileSaver from "file-saver";
import Aside from "@/views/teacher_workspace/Aside"
export default {
  name: "index",
  data() {
    return {
      showExcel: false,
      selectedGrade: null,
      selectedClass: null,
      gradeList: ['', ],
      classList: ['', ],
      SportsScoreList: [],
      excelScoreList: [],
    }
  },
  components: {
      Aside
    },
  methods: {
    // 获取年级列表
    getGradeList() {
      axios.get("http://36.111.68.174:33380/diagnose/getGradeList").then(res => {
        this.gradeList = [...this.gradeList, ...res.data.data]
      })
    },
    // 获取班级列表
    getClassList() {
      axios.get("http://36.111.68.174:33380/diagnose/getClassList").then(res => {
        this.classList = [...this.classList, ...res.data.data]
      })
    },
    // 获取学生体育成绩
    getSportScoreList() {
      axios.get("http://36.111.68.174:33380/diagnose/getSportScoreList", {
        params: {
          gradeName: this.selectedGrade,
          className: this.selectedClass
        }
      }).then(res => {
        this.SportsScoreList = res.data.data
      })
    },
    // 从服务器下载体育成绩模板
    downloadTemplate () {
      window.location.href = "http://49.51.69.99:9200/managementFile/sport_score_template.xlsx";
    },
    // 导出为Excel执行函数
    exportExcel () {
      /* generate workbook object from table */
      var xlsxParam = {raw: true}
      const wb = XLSX.utils.table_to_book(document.querySelector('#sport-score-list'), xlsxParam);
      /* get binary string as output */
      const wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'});
      try {
        FileSaver.saveAs(new Blob([wbout],
          { type: 'application/octet-stream' }), this.selectedGrade+this.selectedClass+'学生体育评价量表.xlsx')
      } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
      return wbout
    },
    // 导入体育成绩表
    handleChange (file,fileList) {
        let that = this
        fileList = [fileList[fileList.length - 1]]; // 只能上传一个Excel，重复上传会覆盖之前的
        file = file.raw;
        let reader = new FileReader()
        reader.readAsArrayBuffer(file)
        reader.onload = function () {
          let buffer = reader.result
          let bytes = new Uint8Array(buffer)
          let length = bytes.byteLength
          let binary = ''
          for (let i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i])
          }
          let XLSX = require('xlsx')
          let wb = XLSX.read(binary, {
            type: 'binary'
          })
          let outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])
          console.log(outdata)
          that.excelScoreList = outdata
        }
      },
    changePage () { this.showExcel = !this.showExcel
      console.log(this.excelScoreList)
    },
  },
  mounted() {
    // 获得年级班级列表
    this.getGradeList()
    this.getClassList()
    // 获得学生体育成绩列表
    this.getSportScoreList()
  }
}
</script>

<style scoped>
  .search-form {
    margin: 10px;
  }
</style>
