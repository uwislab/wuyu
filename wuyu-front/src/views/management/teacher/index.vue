<template>
  <!-- 教师信息页面 -->
  <div class="app-container">

    <!-- 搜索表单 -->
    <el-form :inline="true" class="demo-form-inline" :rules="searchRules" ref="searchForm">
      <el-form-item>
        <el-form-item prop="teacherName">
          <el-autocomplete
            v-model="searchObj.teacherName"
            :fetch-suggestions="querySearchAsync"
            placeholder="输入教师姓名拼音"
            @select="handleSelect"
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchObj.gender" clearable placeholder="性别">
            <el-option label="男" value="1">男</el-option>
            <el-option label="女" value="0">女</el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchObj.position" clearable placeholder="职位">
            <el-option v-for="(item, index) in positionList" :key="index" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchObj.grade" clearable placeholder="任课年级">
            <el-option v-for="(item, index) in gradeList" :key="index" :value="item" :label="item"></el-option>
          </el-select>
        </el-form-item>
      </el-form-item>
      <el-button icon="el-icon-refresh-right" @click="resetQuery">重置</el-button>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="success" icon="el-icon-plus" @click="dialogVisible = true">信息导入</el-button>
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </el-form>

    <!-- 展示表格 -->
    <el-table :data="tableData" border stripestyle="width:100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40">
      </el-table-column>
      <el-table-column type="index" width="50" label="序号" align="center" />
      <el-table-column prop="teacherName" label="教师姓名" align="center" />
      <el-table-column prop="gender" label="教师性别" align="center" />
      <el-table-column prop="phoneNum" label="联系方式" align="center" />
      <el-table-column prop="position" label="职位" align="center" />
      <el-table-column prop="title" label="职称" align="center" />
      <el-table-column prop="className" label="任职班级" align="center" width="210px" />
      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row.id)">详情</el-button>
          <el-button type="danger" style="margin-left:5px" size="mini"
            @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- 详情和编辑表单 -->
    <el-dialog id="editForm" title="教师详情 | 信息编辑" :visible.sync="dialogFormVisible">
      <el-form :model="teacher" label-width="80px" :rules="rules" ref="teacher">
        <el-form-item label="教师名称" prop="teacherName">
          <el-input v-model="teacher.teacherName"></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-radio-group v-model="teacher.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="电话号码" prop="phoneNum">
          <el-input v-model="teacher.phoneNum"></el-input>
        </el-form-item>

        <!--        可以在班级信息中修改班主任，反过来显得没有意义，故不能修改任课班级，只能读取-->
        <el-form-item label="任课班级">
          <el-select v-model="classIds" multiple placeholder="请选择任课班级" style="width: 600px">
            <el-option v-for="item in classOptions" :key="item.classId" :label="item.className" :value="item.classId"
              :disabled="true">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="职位:" prop="position">
          <el-select placeholder="请选择教师职位" v-model="teacher.position">
            <el-option label="校长" value="校长"></el-option>
            <el-option label="语文老师" value="语文老师"></el-option>
            <el-option label="数学老师" value="数学老师"></el-option>
            <el-option label="英语老师" value="英语老师"></el-option>
            <el-option label="历史老师" value="历史老师"></el-option>
            <el-option label="美术老师" value="美术老师"></el-option>
            <el-option label="音乐老师" value="音乐老师"></el-option>
            <el-option label="体育老师" value="体育老师"></el-option>
            <el-option label="信息老师" value="信息老师"></el-option>
            <el-option label="劳技老师" value="劳技老师"></el-option>
            <el-option label="德育老师" value="德育老师"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="职称:" prop="title">
          <el-select placeholder="请选择教师职称" v-model="teacher.title">
            <!--            <el-option label="究极教师" value="究极教师"></el-option>-->
            <el-option label="特级教师" value="特级教师"></el-option>
            <el-option label="高级教师" value="高级教师"></el-option>
            <el-option label="中级教师" value="中级教师"></el-option>
            <el-option label="初级教师" value="初级教师"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="角色:" prop="role">
          <el-select placeholder="请选择教师角色" v-model="teacher.role">
            <el-option label="管理员" value="管理员"></el-option>
            <el-option label="班主任" value="班主任"></el-option>
            <el-option label="任课老师" value="任课老师"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('teacher')">确认修改</el-button>
          <el-button @click="dialogFormVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 信息导入 -->
    <el-dialog title="信息导入" :visible.sync="dialogVisible" :before-close="handleClose">
      <!-- 上传excel文件 -->
      <el-upload class="uploadExcel" action="" accept=".xlsx, .xls" :limit="1" :http-request="httpRequest" ref="load"
        :show-file-list="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <div slot="tip" class="uploadTip">仅允许上传一份xls、xlsx格式的文件。</div>
      </el-upload>

      <el-row class="previewTip" style="padding-top: 10px">
        <h4 style="display: inline">预览</h4> &nbsp; <span style="color: red">(*请注意：excel表格中需要包含以下列名)</span>
      </el-row>
      <!-- 信息预览表格 -->
      <el-table class="previewTable" :data="previewData" height="250" style="width: 100%">
        <el-table-column type="index" :index="indexMethod">
        </el-table-column>
        <el-table-column prop="teacherName" label="姓名">
        </el-table-column>
        <el-table-column prop="gender" label="性别">
        </el-table-column>
        <el-table-column prop="phoneNum" label="联系方式">
        </el-table-column>
        <el-table-column prop="position" label="职位">
        </el-table-column>
        <el-table-column prop="title" label="职称">
        </el-table-column>
        <el-table-column prop="role" label="角色">
        </el-table-column>
      </el-table>
      <span slot="footer">
        <el-button size="small" type="danger" @click="cleanTable()">清空</el-button>
        <el-button style="margin-left: 10px;" size="small" type="primary" @click="handleAddSure()">上传</el-button>
      </span>

    </el-dialog>

    <!-- 分页 -->
    <div class="block">
      <el-pagination background :current-page.sync="currentPage" layout="sizes,prev, pager, next,total,jumper"
        style="padding: 30px 0; text-align: center;" :total="total" :page-count="pageCount" :page-size="limit"
        :page-sizes="[3, 5, 10]" @current-change="jump" @next-click="nextPage()" @prev-click="prePage()"
        @size-change="handleSizeChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import * as xlsx from 'xlsx/xlsx.mjs';
import {
  getTeacherListByPage,
  addTeacher,
  editTeacher,
  deleteTeacher,
  getTeacherExcel,
  deleteBatch
} from '@/api/managementModule/teacher';
import axios from 'axios'
import { baseUrl } from "@/api/baseapi";
import JOSN from "qs";
// 导入拼音库
import pinyin from 'pinyin';

export default {
  data() {
    return {
      limit: 10,
      searchObj: {
        teacherName: "",
        gender: "",
        position: "",
        grade: ""
      }, // 条件搜索对象
      gradeList: [],
      positionList: [],
      multipleSelection: [],
      pageCount: 1,
      currentPage: 1, //当前页
      page: 1, //分页组件页码初始化
      addFormVisible: false,
      addForm: {},
      tableData: [],
      total: 20,
      isLast: true,
      maxPage: 1,
      formLabelWidth: '120px',
      classMapping: {},
      activityStateMapping: {
        0: '待开始',
        1: '待评分',
        2: '已完成'
      },
      downloadLoading: false, // 导出按钮
      /*以下是编辑页面需要的数据*/
      dialogFormVisible: false, //编辑表单
      form: {}, //表单信息
      teacher: {}, //封装教师信息
      classOptions: [],  // 可选择的任课班级的信息
      classIds: [],  //当前已选中任课班级的值组成的数组
      rules: {
        teacherName: [
          { required: true, message: '请输入教师名称', trigger: 'blur' },
          { pattern: '^[ a-zA-Z\u4e00-\u9fa5]+$', message: "请输入合法姓名", trigger: 'blur' }
        ],
        phoneNum: [
          { required: true, message: '请输入电话号码（11位数字）', trigger: 'blur' },
          { pattern: '^1[34578]\\d{9}$', message: '目前只支持中国大陆的手机号码', trigger: 'blur' }
        ],
        position: [
          { required: true, message: '请选择职位', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请选择职称', trigger: 'change' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'change' }
        ]
      },
      previewData: [], //预览表格展示数据
      jsonData: [], //传回后端的新增教师数据
      dialogVisible: false, //信息导入面板显示状态
      inputStatus: true,
      searchRules: {
        teacherName: [
          { validator: this.validateTeacherName, trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    this.getList();
    this.getGradeAndPosition()
    // 初始化时获得所有可选择的班级数据
    this.getClassOptions()
  },
  methods: {
    querySearchAsync(queryString, cb) {
      // 使用 pinyin 库将教师姓名转换为拼音
      const results = queryString
        ? this.tableData.filter(teacher => {
            const teacherPinyin = pinyin(teacher.teacherName, { style: pinyin.STYLE_NORMAL }).join('').toLowerCase();
            return teacherPinyin.includes(queryString.toLowerCase());
          })
        : this.tableData;

      // 将匹配的结果传递给回调函数
      cb(results);
    },
    handleSelect(item) {
      this.searchObj.teacherName = item.teacherName;
      this.getList();
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },

    delBatch() {
      let ids = this.multipleSelection.map(v => v.id);
      if (ids.length === 0) {
        this.$message.warning("请选择需要删除的项目");
        return;
      }

      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBatch(ids).then(res => {
          if (res) {
            this.$message.success("批量删除成功");
            // 刷新数据
            this.getList();
            this.getGradeAndPosition();
            this.getClassOptions();
          } else {
            this.$message.error("批量删除失败");
            console.error("删除失败，返回结果：", res);
          }
        }).catch(err => {
          this.$message.error("删除请求出错");
          console.error("删除请求出错：", err);
        });
      }).catch(() => {
        this.$message.error("已取消批量删除");
      });
    }
    ,

    getGradeAndPosition() {
      axios.get(baseUrl + '/api/teacherQuery/getFormObject').then(res => {
        if (res.data.code === 200) {
          this.gradeList = res.data.data.gradeList;
          this.positionList = res.data.data.positionList;
        }
      })
    },
    validateTeacherName(rule, value, callback) {
      // Use a regular expression to check if the input contains non-Chinese characters
      const chineseRegExp = /^[a-zA-Z\u4e00-\u9fa5\ ]+$/;
      if (this.searchObj.teacherName !== "" && !chineseRegExp.test(this.searchObj.teacherName)) {
        this.inputStatus = false
        callback(new Error('请输入正确格式的姓名'));
      } else {
        this.inputStatus = true
        callback();
      }
    },
    clearTeacherName() {
      if (this.inputStatus === false) {
        this.$refs.searchForm.clearValidate('teacherName');
        this.searchObj.teacherName = '';
      }
      this.inputStatus = true
    },
    /*以下是信息导入页面用到的方法*/
    // 预览表格序号规则
    indexMethod(index) {
      return index + 1;
    },
    // 信息导入面板dialog关闭回调方法
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.cleanTable();
          done();
        })
        .catch(_ => {
        });
    },
    // 清除导入数据
    cleanTable() {
      this.previewData = [];
      this.jsonData = [];
    },
    // 数据格式错误提示
    warnTip(flag, position) {
      if (flag == 0) {
        this.$message({
          showClose: true,
          message: 'excel缺少必须元素',
          type: 'error'
        });
      } else if (flag == 1) {
        this.$message({
          showClose: true,
          message: '第' + position + '行的姓名应为正确格式（中文或英文）',
          type: 'error'
        });
      } else if (flag == 2) {
        this.$message({
          showClose: true,
          message: '第' + position + '行的性别应为正确格式（男、女）',
          type: 'error'
        });
      } else if (flag == 3) {
        this.$message({
          showClose: true,
          message: '第' + position + '行的联系方式应为正确格式（1开头的11位号码）',
          type: 'error'
        });
      } else if (flag == 4) {
        this.$message({
          showClose: true,
          message: '第' + position + '行的职位应为正确格式',
          type: 'error'
        });
      } else if (flag == 5) {
        this.$message({
          showClose: true,
          message: '第' + position + '行的职称应为正确格式',
          type: 'error'
        });
      } else if (flag == 6) {
        this.$message({
          showClose: true,
          message: '第' + position + '行的角色应为正确格式',
          type: 'error'
        });
      }
    },
    // 读取excel表数据，转换为json数据
    httpRequest(e) {
      let file = e.file;
      // 检查文件格式
      if (!file) {
        return false
      } else if (!/\.(xls|xlsx)$/.test(file.name.toLowerCase())) {
        this.$message.error('上传格式不正确，请上传xls或者xlsx格式')
        return false
      }
      const fileReader = new FileReader() // 读取文件
      fileReader.onload = (ev) => {
        try {
          const data = ev.target.result // 获取结果
          // 获取表信息
          const workbook = xlsx.read(data, {
            type: 'binary', // 以字符编码的方式解析
          })
          // 获取表的表名
          const exlname = workbook.SheetNames[0]
          // 转换成json数据
          const exl = xlsx.utils.sheet_to_json(workbook.Sheets[exlname]) // 生成json表格内容

          // 数据处理
          this.submit_from(exl);
          this.$refs['load'].clearFiles();

        } catch (e) {
          console.log('error', e);
          return false
        }
      }
      fileReader.readAsBinaryString(file);
    },
    // 数据处理成需要的格式
    submit_from(data) {
      console.log("data", data);
      // 检查数据中是否含有必须键
      if ("姓名" in data[0] == false || "性别" in data[0] == false ||
        "联系方式" in data[0] == false || "职位" in data[0] == false ||
        "职称" in data[0] == false || "角色" in data[0] == false) {
        this.warnTip(0, 0);
        return;
      }
      // 姓名、电话、汉字的正则表达式
      var regName = /(^([\u4e00-\u9fa5]{2,4}$)|(^[A-Za-z]*(\s[A-Za-z]*)*)$)/;
      var regPhone = /^1[3456789]\d{9}$/;
      var regChinese = /[\u4E00-\u9FA5\uF900-\uFA2D]{1,}/;

      // 循环遍历数据，进行格式检验，并给previewData和jsonData赋值
      for (var i in data) {
        let obj = {}
        var p = parseInt(i) + 1;

        obj.teacherName = data[i].姓名;
        obj.gender = data[i].性别;
        obj.phoneNum = data[i].联系方式;
        obj.position = data[i].职位;
        obj.title = data[i].职称;
        obj.role = data[i].角色;

        if (regName.test(obj.teacherName) == false) {
          this.warnTip(1, p);
          this.cleanTable();
          return;
        } else if (obj.gender != "男" && obj.gender != "女") {
          this.warnTip(2, p);
          this.cleanTable();
          return;
        } else if (regPhone.test(obj.phoneNum) == false) {
          this.warnTip(3, p);
          this.cleanTable();
          return;
        } else if (regChinese.test(obj.position) == false || ("校长" != obj.position && obj.position.includes(
          "老师") == false)) {
          this.warnTip(4, p);
          this.cleanTable();
          return;
        } else if (regChinese.test(obj.title) == false || obj.title.includes("级教师") == false) {
          this.warnTip(5, p);
          this.cleanTable();
          return;
        } else if (regChinese.test(obj.role) == false || ("管理员" != obj.role &&
          "班主任" != obj.role && "任课老师" != obj.role)) {
          this.warnTip(6, p);
          this.cleanTable();
          return;
        }

        this.jsonData.push(obj);
        this.previewData.push(obj);
      }

      console.log("1", this.jsonData);

    },
    // 转换性别数据为01
    relpaceGender(data) {
      for (var i in data) {
        if (data[i].gender == "男") {
          data[i].gender = 1;
        } else {
          data[i].gender = 0;
        }
      }

      return data
    },
    // 调用后端接口添加数据并返回添加状态
    handleAddSure() {
      console.log("adada");
      var data = this.relpaceGender(this.jsonData);
      console.log("json", data);
      addTeacher(data).then(res => {
        this.$message({
          message: '添加成功',
          type: 'success'
        });
        this.getList();
      })
      this.dialogVisible = false;
      this.cleanTable();
    },

    jump(page) {//跳转指定页面
      this.page = page;
      console.log("page:" + page)
      this.getList();
    },
    nextPage() {//下一页
      if (this.page < this.maxPage) {
        this.page = this.page + 1
        this.getList()
      }

    },
    prePage() {//上一页
      if (this.page > 1) {
        this.page = this.page - 1
        this.getList()
      }
    },
    handleSizeChange(val) {
      this.limit = val;
      this.getList()
    },
    getList() {//获取当前页面的教师信息
      this.currentPage = this.page;
      // this.limit = 2;
      const payload = {
        pageSize: this.limit,
        pageNum: this.currentPage,
        ...this.searchObj,
      }
      if (this.inputStatus === true) {
        getTeacherListByPage(payload).then(res => {
          this.maxPage = res.data.pages;
          // if(this.total<res.data.total){
          //    this.total = res.data.total;
          // }
          this.total = res.data.total;
          this.currentPage = res.data.curPage;//更换当前页
          this.isLast = res.data.isLast;

          // this.initElPageForbiddenBtn();
          this.tableData = res.data.list.map(item => {
            return {
              ...item,
              gender: item.gender == 0 ? '女' : '男',
            }
          })
        })
      } else {
        this.clearTeacherName()
        this.$message({
          message: '请输入正确格式的姓名',
          type: 'error'
        });
        this.getList()
      }

    },
    //重置查询数据
    resetQuery() {
      this.searchObj.teacherName = ""
      this.searchObj.gender = ""
      this.searchObj.grade = ""
      this.searchObj.position = ""
      this.page = 1
      this.getList()
    },
    handleCancel() {
      this.dialogFormVisible = false;
      this.addFormVisible = false;
      this.getList();
    },
    handleSure() {
      if (this.form.gender == '男') {
        this.form.gender = 1;
      }
      if (this.form.gender == '女') {
        this.form.gender = 0;
      }
      editTeacher(this.form).then(res => {
        this.$message({
          message: '修改成功',
          type: 'success'
        });
        this.getList();
      })
      this.dialogFormVisible = false;
    },
    handleDownload() {
      this.downloadLoading = true;
      console.log(this.searchObj.title);
      getTeacherExcel(this.searchObj);
      this.downloadLoading = false;
    },
    /*以下是编辑页面用到的方法*/
    handleEdit(index, teacherId) {
      console.log("teacherId(617) = " + teacherId);
      this.getInfo(teacherId);
      this.dialogFormVisible = true;
    },
    // 获取所有可以选择的班级
    getClassOptions() {
      axios.get(baseUrl + "/api/teacherQuery/getClassInfo")
        .then(data => {
          this.classOptions = data.data.data
        })
    },
    // 点击确认编辑按钮的回调
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        console.log("valid(632) = " + valid);
        if (valid) {
          console.log("this.teacher = " + this.teacher);
          console.log("this.classIds = " + this.classIds);
          editTeacher(this.teacher, this.classIds).then(res => {
            console.log("res(635) = " + JOSN.stringify(res));
            if (res.code === 50001) {
              this.$message({
                message: res.message,
                type: 'error'
              });
              this.dialogFormVisible = false;
            } else if (res.code === 500) {
              this.$message({
                message: res.message,
                type: 'error'
              });
            } else {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.dialogFormVisible = false;
              this.getList();
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 根据id获取需要编辑的教师信息并展示到页面
    getInfo(teacherId) {
      this.classIds = [];
      axios.get(baseUrl + "/api/teacherQuery/getTeacherInfo?teacherId=" + teacherId).then(data => {
        if (data.data.code === 200) {
          this.teacher = data.data.data;
          let list = data.data.data.classList;
          for (let i in list) {
            this.classIds.push(list[i].classId)
          }
        }
      })
    },
    handleDelete(teacherId) {
      this.$confirm('是否删除该条教师信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() =>
        deleteTeacher(teacherId).then(res => {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getList();
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        }))
    }
  }
}
</script>

<style lang="scss" scoped></style>
