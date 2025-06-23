<template>
  <div class="main-div">
    <div class="show-div" v-show="!showExcel">
    <!-- 搜索下拉框 -->
    <el-form :inline="true" class="search-form">
      <el-form-item>
        <el-form-item>
          <el-input
            v-model="searchObj.keyword"
            clearable
            placeholder="关键字"
          />
        </el-form-item>
        <el-select v-model="searchObj.gender" clearable placeholder="性别">
          <el-option label="男生" value="1"></el-option>
          <el-option label="女生" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="searchObj.gradeId"
          filterable
          clearable
          placeholder="年级"
        >
          <el-option
            v-for="item in gradeandclass"
            :key="item.id"
            :label="item.grade.gradeName"
            :value="item.grade.gradeId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="searchObj.className"
          filterable
          clearable
          placeholder="班级"
        >
          <el-option
            v-for="item in className"
            :key="item"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item>
        <el-select v-model="selectedGrade" clearable placeholder="选择年级">
          <el-option
            v-for="item in gradeList"
            :key="item.index"
            :label="item == ''? '年级' : item"
            :value="item == ''? null : item"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="selectedClass" clearable placeholder="选择班级">
          <el-option
            v-for="item in classList"
            :key="item.index"
            :label="item == ''? '班级' : item"
            :value="item == ''? null : item"
          />
        </el-select>
      </el-form-item> -->
      <el-button  icon="el-icon-refresh-right" @click="reset()">重置</el-button>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button id="TempleBtn" @click="downloadTemplate()">下载模板</el-button>
      <el-button type="success" icon="el-icon-plus" @click="addNewScore()">新增</el-button>
      <el-button style="margin-left: 15px;" type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <el-button type="danger" id="ExcelBtn" @click="exportExcel()">导出为Excel</el-button>
      <el-upload class="upload-demo" ref="upload" action=""
                 :auto-upload="false" :file-list="fileList" :on-change="handleChange" multiple
                 :show-file-list="false"
      >

      <!-- <el-button type="text" @click="changePage()">点击上传成绩表</el-button> -->
      </el-upload>

    </el-form>

    <!--  体育成绩列表  -->
    <el-table :data="SportsScoreList" id="sport-score-list" @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="40">
        </el-table-column>
        <el-table-column type="index" width="50" label="序号" align="center"/>
        <el-table-column prop="studentName" label="学生姓名" width="150" align="center"/>
        <el-table-column prop="studentNum" label="学号" width="150" align="center"/>
        <el-table-column prop="gender" label="性别" width="50" align="center" />
        <el-table-column prop="gradeName" label="年级" width="150" align="center" />
        <el-table-column prop="className" label="班级" width="150" align="center"/>
        <el-table-column label="体育项目" align="center">
          <el-table-column prop="fvcscore" label="肺活量" width="100" align="center"></el-table-column>
          <el-table-column prop="rsscore" label="跳绳" width="100" align="center"></el-table-column>
          <el-table-column prop="bmiscore" label="体重指数" width="100" align="center"></el-table-column>
          <el-table-column prop="sarscore" label="坐位体前屈" width="100" align="center"></el-table-column>
          <el-table-column prop="subscore" label="仰卧起坐" width="100" align="center"></el-table-column>
          <el-table-column prop="oabscore" label="往返跑" width="100" align="center"></el-table-column>
          <el-table-column prop="dashscore" label="短跑" width="100" align="center"></el-table-column>
          <el-table-column label="操作" width="280" align="center">
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="mini"
                @click="handleEdit(scope.row)"
              >编辑</el-button>
              <el-button
                type="danger"
                style="margin-left: 5px"
                size="mini"
                @click="handleDelete(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table-column>
    </el-table>


    <!-- 新增表单 -->
    <el-dialog
      title="请添加体育信息"
      :visible.sync="addFormVisible"
      :close-on-click-modal="false"
    >
      <el-form :model="addForm">
        <el-form-item label="学生学号" :label-width="formLabelWidth">
          <el-input v-model="addForm.studentNum" placeholder="学号"></el-input>
        </el-form-item>
        <el-form-item label="肺活量" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.fvcscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="跳绳" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.rsscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="体重指数" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.bmiscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="坐位体前屈" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.sarscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="仰卧起坐" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.subscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="往返跑" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.oabscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="短跑" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.dashscore"
          ></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleAddSure">确 定</el-button>
      </div>
    </el-dialog>


    <!-- 编辑表单 -->
    <el-dialog
      title="请编辑信息"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
    >
      <el-form :model="form">
        <el-form-item label="肺活量" :label-width="formLabelWidth">
          <el-input
            v-model="form.fvcscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="跳绳" :label-width="formLabelWidth">
          <el-input
            v-model="form.rsscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="体重指数" :label-width="formLabelWidth">
          <el-input
            v-model="form.bmiscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="坐位体前屈" :label-width="formLabelWidth">
          <el-input
            v-model="form.sarscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="仰卧起坐" :label-width="formLabelWidth">
          <el-input
            v-model="form.subscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="往返跑" :label-width="formLabelWidth">
          <el-input
            v-model="form.oabscore"
          ></el-input>
        </el-form-item>
        <el-form-item label="短跑" :label-width="formLabelWidth">
          <el-input
            v-model="form.dashscore"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleEditSure">确 定</el-button>
      </div>
    </el-dialog>


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
    <el-pagination
      background
      :current-page="page"
      :page-size="limit"
      :page-sizes="[10, 20, 30, 40, 50]"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
      style="padding: 30px 0; text-align: center"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>

</template>

<script>
import axios from "axios";
import * as XLSX from "xlsx";
import * as FileSaver from "file-saver";
import { baseUrl } from "@/api/baseapi";
import { getAllClassInfo } from "@/api/managementModule/classManagement";
import {
  getAllSportScore,
  getSportsForSearch,
  deleteSportScore,
  editSportScore,
  addSportScore,
  deleteBatch
} from "@/api/managementModule/sportScore";
export default {
  name: "index",
  data() {
    return {
      addFormVisible: false,
      showExcel: false,
      selectedGrade: null,
      selectedClass: null,
      addForm: {},
      form: {}, //表单信息
      searchObj: {}, // 条件搜索对象
      dialogFormVisible: false, //编辑表单
      gradeList: ['1年级', '2年级','3年级','4年级','5年级','6年级'],
      gradeandclass: [],
      classList: [''],
      SportsScoreList: [],
      className: [],
      gradeName: [],
      gradeMapping: {},
      classMapping: {},
      grade: [],
      classlist: [],
      list:[],
      limit: 10,
      page: 1, //分页组件页码初始化
      total: 20,
      excelScoreList: [],
      multipleSelection:[],
    }
  },
  methods: {
    handleSelectionChange(val){
      this.multipleSelection = val
    },
    async getList() {
      this.page = 1;
      var classId = "";
      if (this.searchObj.className != "" && this.searchObj.className != undefined) {
        classId = this.searchObj.className.toString().slice(0, -1);
      }
      const payload = {
        ...this.searchObj,
        classId: classId, //传入班级ID
      };
      console.log(payload, "payload");
      const res = await getSportsForSearch(payload);
      this.total = res.data.length;
      this.page = 1;
      this.list = res.data.map((item) => {
        return {
          ...item,
          className: item.classNum.toString() + "班",
          gradeName: item.gradeNum.toString() + "年级",
          gender: item.gender === 0 ? "女" : "男",
        };
      });
      this.SportsScoreList = this.list.slice(0, this.limit);
    },
    unique(arr) {
      return Array.from(new Set(arr));
    },
    //获取体育成绩
    async getSportScoreList() {
      const res = await getAllSportScore();
      this.searchObj = {};
      this.form = {};
      this.addForm = {};
      this.list = res.data.map((item) => {
        return {
          ...item,
          className: item.classNum.toString() + "班",
          gradeName: item.gradeNum.toString() + "年级",
          gender: item.gender === 0 ? "女" : "男",
        };
      });
      this.SportsScoreList = this.list.slice(0, this.limit)
      this.total = this.list.length
      this.page = 1
    },
    handleCurrentChange(currentPage) {
      this.page = currentPage
      this.SportsScoreList = this.list.slice(this.limit * (currentPage - 1), this.limit * currentPage)
    },
    // 页面大小更新
    handleSizeChange(pageSize) {
      this.page = 1
      this.limit = pageSize
      this.SportsScoreList = this.list.slice(0, pageSize)
    },
    // 获取年级列表
    getGradeList() {
      axios.get(baseUrl+"/diagnose/getGradeList").then(res => {
        this.gradeList = [...this.gradeList, ...res.data.data]
      })
    },
    // 获取班级列表
    // getClassList() {
    //   axios.get(baseUrl+"/diagnose/getClassList").then(res => {
    //     this.classList = [...this.classList, ...res.data.data]
    //   })
    // },
    // 从服务器下载体育成绩模板
    downloadTemplate () {
      // window.location.href = baseUrl+"/managementFile/sport_score_template.xlsx";
      this.downloadLoading = true;  // 开始加载

      // 模板文件的路径（相对于 public 文件夹）
      const fileUrl = '/excel/sportScoreInput.xlsx';

      // 创建一个链接来触发下载
      const link = document.createElement('a');
      link.href = fileUrl;
      link.download = '体育成绩模板下载.xlsx';  // 设置下载的文件名
      link.click();  // 触发下载

      // 重置加载状态
      this.downloadLoading = false;
    },
    // 导出为Excel执行函数


    exportExcel () {
      // 创建工作簿
      const workbook = XLSX.utils.book_new();

      // 将数据转换为工作表
      const worksheetData = this.list.map(item => ({
        学生姓名: item.studentName,
        学号: item.studentNum,
        性别: item.gender,
        年级: item.gradeName,
        年级Id: item.gradeNum,
        班级: item.className,
        班级Id: item.classNum,
        肺活量: item.fvcscore,
        跳绳: item.rsscore,
        体重指数: item.bmiscore,
        坐位体前屈: item.sarscore,
        仰卧起坐: item.subscore,
        往返跑: item.oabscore,
        短跑: item.dashscore
      }));
      const worksheet = XLSX.utils.json_to_sheet(worksheetData);

      // 将工作表添加到工作簿
      XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');

      // 导出Excel文件
      XLSX.writeFile(workbook, '体育成绩表.xlsx');
      // /* generate workbook object from table */
      // var xlsxParam = {raw: true}
      // const wb = XLSX.utils.table_to_book(document.querySelector('#sport-score-list'), xlsxParam);
      // /* get binary string as output */
      // const wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'});
      // try {
      //   FileSaver.saveAs(new Blob([wbout],
      //     { type: 'application/octet-stream' }), this.selectedGrade+this.selectedClass+'学生体育评价量表.xlsx')
      // } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
      // return wbout
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
    delBatch(){
      let ids = this.multipleSelection.map(v => v.studentNum)
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() =>{
        deleteBatch(ids).then(res => {
          if(res.code==200){
            this.$message.success("批量删除成功")
            this.getSportScoreList();
          }else{
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message.error("已取消批量删除")
      });
    },

    reset(){
      this.searchObj = {};
    },
    uploadOpen() {
      console.log("windowopenstemp1");
      var vm = this;
      vm.uploadform.docType.crFile = [];
      console.log(vm);
      vm.uploadVisible = true;
    },

    handlePreview(file) {
      console.log("openwindow", file);
      // var fileUrl = library.isEmpty(file.url) ? file.response.url : file.url;
      // fileUrl =
      //   fileUrl.indexOf(apis.api_file_urlNew) >= 0
      //     ? fileUrl
      //     : apis.api_file_urlNew + fileUrl;
      window.open(file);
      // this.httpRequest(file)
    },
    handleRemove(file, fileList) {
      console.log("remove");
      console.log(file, fileList);
      for (var i = 0; i < fileList.length; i++) {
        if (this.fileList[i].uid == file.uid) {
          this.fileList.splice(i, 1);
          break;
        }
      }
    },
    // 更新classlist
    async getclasslist(index) {
      index = parseInt(index);
      console.log("index = " + index);
      let temp = this.gradeandclass[index - 1];
      console.log("temp.grade.class: ", temp.grade.class);
      if (temp != null && index >= 0) {
        this.classlist = temp.grade.class;
      }
    },
    async handleEdit(row) {
      this.form = {
        ...row
      };
      this.dialogFormVisible = true;
    },

    addNewScore() {
      this.addFormVisible = true;
    },
    async handleCancel() {
      this.dialogFormVisible = false;
      this.addFormVisible = false;
      this.form = {}
      this.addForm = {}
    },
    handleDelete(score) {
      if(score.gender=='男'){
        score.gender = 1
      }else if(score.gender=='女'){
        score.gender = 0
      }
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteSportScore(score).then((res) => {
          if(res.code==200){
            this.$message.success("删除成功")
            this.getSportScoreList();
          }else{
            this.$message.error(res.message)
          }
        })
      });
    },
    //确认修改
    async handleEditSure() {
      if(this.form.gender=='男'){
        this.form.gender = 1
      }else if(this.form.gender=='女'){
        this.form.gender = 0
      }
      // 肺活量成绩判断
      if(this.form.fvcscore!=null&&this.form.fvcscore!=''){
          if (!this.isNum(this.form.fvcscore)) {
          this.$message({
            message: "肺活量成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.form.fvcscore) < 0 || parseInt(this.form.fvcscore) > 100) {
          this.$message({
            message: "肺活量成绩不在0-100之间",
            type: "error",
          });
          return;
        }
      }
      // 跳绳成绩判断
      if(this.form.rsscore!=null&&this.form.rsscore!=''){
        if (!this.isNum(this.form.rsscore)) {
          this.$message({
            message: "跳绳成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.form.rsscore)< 0 || parseInt(this.form.rsscore) > 100) {
          this.$message({
            message: "跳绳成绩不在0-100之间",
           type: "error",
          });
          return;
        }
      }
      //体重指数成绩判断
      if(this.form.bmiscore!=null&&this.form.bmiscore!=''){
        if (!this.isNum(this.form.bmiscore)) {
          this.$message({
            message: "体重指数成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.form.bmiscore)< 0 || parseInt(this.form.bmiscore)>100) {
          this.$message({
            message: "体重指数成绩不在0-100之间",
           type: "error",
          });
          return;
        }
      }
      //坐位前屈成绩判断
      if(this.form.sarscore!=null&&this.form.sarscore!=''){
        if (!this.isNum(this.form.sarscore)) {
          this.$message({
            message: "坐位前屈成绩非数字",
            type: "error",
          });
          return;
        } else if (this.form.sarscore < 0 || this.form.sarscore > 100) {
          this.$message({
            message: "坐位前屈成绩不在0-100之间",
          });
          return;
        }
      }
      //仰卧起坐成绩判断
      if(this.form.subscore!=null&&this.form.subscore!=''){
        if (!this.isNum(this.form.subscore)) {
          this.$message({
            message: "仰卧起坐成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.form.subscore)< 0 || parseInt(this.form.subscore)>100) {
          this.$message({
            message: "仰卧起坐成绩不在0-100之间",
          });
          return;
        }
      }
      // 50米往返跑成绩判断
      if(this.form.dashscore!=null&&this.form.dashscore!=''){
        if (!this.isNum(this.form.dashscore)) {
          this.$message({
            message: "50米往返跑成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.form.dashscore)< 0 || parseInt(this.form.dashscore)>100) {
          this.$message({
           message: "50米往返跑成绩不在0-100之间",
          });
          return;
        }
      }
      // 50米跑成绩判断
      if(this.form.oabscore!=null&&this.form.oabscore!=''){
        if (!this.isNum(this.form.oabscore)) {
          this.$message({
            message: "50米跑成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.form.oabscore < 0) || parseInt(this.form.oabscore) > 100) {
          this.$message({
            message: "50米跑成绩不在0-100之间",
          });
          return;
        }
      }
      editSportScore(this.form).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "修改成功",
            type: "success",
          });
          this.dialogFormVisible = false;
          this.getSportScoreList();
        } else {
          this.$message({
            message: res.message,
            type: "error",
          });
          return;
        }
      });
    },
    isNum(str) {
      return /^[0-9]*$/.test(str);
    },
    handleAddSure() {
      if (this.addForm.studentNum === "") {
        this.$message({
          message: "添加失败，部分数据为空",
          type: "error",
        });
        return;
      }
      // 学号--学号只能是10位数字
      if  (!/^[0-9]{10}$/.test(this.addForm.studentNum)) {
        this.$message({
          message: "添加失败,学号只能是10位数字",
          type: "error",
        });
        return;
      }
      // 肺活量成绩判断
      if(this.addForm.fvcscore!=null&&this.addForm.fvcscore!=''){
          if (!this.isNum(this.addForm.fvcscore)) {
          this.$message({
            message: "肺活量成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.addForm.fvcscore) < 0 || parseInt(this.addForm.fvcscore) > 100) {
          this.$message({
            message: "肺活量成绩不在0-100之间",
            type: "error",
          });
          return;
        }
      }
      // 跳绳成绩判断
      if(this.addForm.rsscore!=null&&this.addForm.rsscore!=''){
        if (!this.isNum(this.addForm.rsscore)) {
          this.$message({
            message: "跳绳成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.addForm.rsscore)< 0 || parseInt(this.addForm.rsscore) > 100) {
          this.$message({
            message: "跳绳成绩不在0-100之间",
           type: "error",
          });
          return;
        }
      }
      //体重指数成绩判断
      if(this.addForm.bmiscore!=null&&this.addForm.bmiscore!=''){
        if (!this.isNum(this.addForm.bmiscore)) {
          this.$message({
            message: "体重指数成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.addForm.bmiscore)< 0 || parseInt(this.addForm.bmiscore)>100) {
          this.$message({
            message: "体重指数成绩不在0-100之间",
           type: "error",
          });
          return;
        }
      }
      //坐位前屈成绩判断
      if(this.addForm.sarscore!=null&&this.addForm.sarscore!=''){
        if (!this.isNum(this.addForm.sarscore)) {
          this.$message({
            message: "坐位前屈成绩非数字",
            type: "error",
          });
          return;
        } else if (this.addForm.sarscore < 0 || this.addForm.sarscore > 100) {
          this.$message({
            message: "坐位前屈成绩不在0-100之间",
          });
          return;
        }
      }
      //仰卧起坐成绩判断
      if(this.addForm.subscore!=null&&this.addForm.subscore!=''){
        if (!this.isNum(this.addForm.subscore)) {
          this.$message({
            message: "仰卧起坐成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.addForm.subscore)< 0 || parseInt(this.addForm.subscore)>100) {
          this.$message({
            message: "仰卧起坐成绩不在0-100之间",
          });
          return;
        }
      }
      // 50米往返跑成绩判断
      if(this.addForm.dashscore!=null&&this.addForm.dashscore!=''){
        if (!this.isNum(this.addForm.dashscore)) {
          this.$message({
            message: "50米往返跑成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.addForm.dashscore)< 0 || parseInt(this.addForm.dashscore)>100) {
          this.$message({
           message: "50米往返跑成绩不在0-100之间",
          });
          return;
        }
      }
      // 50米跑成绩判断
      if(this.addForm.oabscore!=null&&this.addForm.oabscore!=''){
        if (!this.isNum(this.addForm.oabscore)) {
          this.$message({
            message: "50米跑成绩非数字",
            type: "error",
          });
          return;
        } else if (parseInt(this.addForm.oabscore < 0) || parseInt(this.addForm.oabscore) > 100) {
          this.$message({
            message: "50米跑成绩不在0-100之间",
          });
          return;
        }
      }
      addSportScore(this.addForm).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "添加成功",
            type: "success",
          });
          this.addFormVisible = false;
          this.getSportScoreList();
        } else {
          this.$message({
            message: res.message,
            type: "error",
          });
        }
        return;
      });
    },
    async getClassID() {
      const res = await getAllClassInfo();

      // console.log(res);
      //获取年级和班级信息
      var origindata = res.data;
      console.log("origindata:");
      console.log(origindata);
      let _this = this;
      var temp = {
        grade: {
          gradeId: "",
          gradeName: "",
          class: [],
        },
        id: "",
      };

      origindata.forEach((item) =>
          // for (item in origindata)
        {
          temp.id = item.id;
          if (temp.grade.gradeId != item.grade) {
            _this.gradeandclass.push(temp);
            temp = {
              grade: {
                gradeId: "",
                gradeName: "",
                class: [],
              },
              id: "",
            };
            temp.grade.gradeId = item.grade;
            temp.grade.gradeName = item.grade + "年级";
            var classtemp = {
              classId: "",
              className: "",
            };
            classtemp.classId = parseInt(item.className);
            classtemp.className = item.className;
            temp.grade.class.push(classtemp);
          } else {
            var classtemp = {
              classId: "",
              className: "",
            };
            classtemp.classId = parseInt(item.className);
            classtemp.className = item.className;
            temp.grade.class.push(classtemp);
          }
        }
      );
      let removedElement = this.gradeandclass.shift();
      // console.log("removedElement: ");
      // console.dir(removedElement);
      this.gradeandclass.push(temp);
      // console.log("temp(722) = " + JOSN.stringify(temp));

      this.gradeandclass.forEach((gac) => {
        console.dir(gac)
      });

      // console.log("this.gradeandclass = " + JOSN.stringify(this.gradeandclass));

      // 获取班级信息
      this.classMapping = res.data.map((item) => {
        return {
          classId: item.id,
          grade: item.grade,
          className: `${item.className}`,
        };
      });
      // 获取年级信息
      this.gradeMapping = res.data.map((item) => {
        return {
          gradeId: item.id,
          gradeName: `${item.grade}`,
        };
      });
      for (const i of this.classMapping) {
        this.grade.push(i.grade);
      }
      for (const i of this.classMapping) {
        this.className.push(i.className);
      }

      for (const i of this.gradeMapping) {
        this.gradeName.push(i.grade);
      }
      this.grade = this.unique(this.grade);
      this.className = this.unique(this.className);
      this.gradeName = this.unique(this.grade);
    },
  },
  watch:{
    "form.gradeId": {
      handler(newval, oldval) {
        if (
          newval != "" &&
          newval != null &&
          newval != undefined &&
          newval != oldval &&
          oldval != undefined
        ) {
          this.getclasslist(parseInt(newval));
          // this.form.classId = "";
          console.log(1);
        } else if (newval === "") {
          this.classlist = [];
          this.form.classId = "";
        }
      },
      immediate: true,
      deep: true,
    },
    "addForm.grade": {
      handler(newval, oldval) {
        if (
          newval != "" &&
          newval != null &&
          newval != undefined &&
          newval != oldval &&
          oldval != undefined
        ) {
          this.getclasslist(parseInt(newval));
          this.addForm.classId = "";
        } else {
          this.classlist = [];
          this.addForm.classId = "";
        }
      },
      // immediate: true,
      // deep: true,
    },
  },
  mounted() {
    // 获得年级班级列表
    // this.getGradeList()
    // this.getClassList()
    // 获得学生体育成绩列表
    this.getSportScoreList()
  },
  created() {
    this.getClassID();
  }
}
</script>

<style scoped>
  .search-form {
    margin: 10px;
  }
</style>
