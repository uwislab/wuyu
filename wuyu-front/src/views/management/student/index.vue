<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :inline="true" class="demo-form-inline">
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
      <el-button  icon="el-icon-refresh-right" @click="reset()"
      >重置</el-button
      >
      <el-button type="primary" icon="el-icon-search" @click="getList()"
      >查询</el-button
      >

      <el-button
        :loading="downloadLoading"
        type="info"
        icon="el-icon-download"
        @click="handleModule"
      >模版下载</el-button
      >
      <el-button type="success" icon="el-icon-plus" @click="addNewStudent()"
      >新增</el-button
      >
      <el-button
        :loading="downloadLoading"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button
      >
      <el-button style="margin-left: 15px;" type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <!-- 导出按钮 -->
      <el-button
        type="primary"
        icon="el-icon-download"
        @click="exportExcel">
        导出选中数据
      </el-button>
      <!-- <el-upload
        class="filter-item"
        prop="uploadfile"
        ref="upload"
        accept=".xls,.xlsx"
        :action="baseUrl"
        :before-upload="beforeUpload"
        name="uploadFile"
        :on-change="fileChange"
        :auto-upload="false"
        :on-preview="handlePreview"
        :http-request="httpRequest"

      >
        <el-button icon="el-icon-upload" type="warning">导入</el-button>
       </el-upload> -->
      <!-- 导入按钮 -->
      <!-- <el-upload
        class="filter-item"
        prop="uploadfile"
        :action="baseUrl"
        ref="uploada"
        :multipe="false"
        :show-file-list="false"
        accept=".xls,.xlsx"
        :on-change="fileChange"
        :before-upload="beforeUpload"
        :on-success="handleSuccess"
        :on-preview="handlePreview"

      >
        <el-button
          size="middle"
          icon="el-icon-upload"
          type="warning"
          @click="exceltable"
          >导入</el-button
        >
      </el-upload> -->
      <!-- 按钮end -->

      <!-- <br /> -->
      <!-- 列表 -->
      <!-- <el-dialog :visible.sync="excelVisible" :close-on-click-modal="false">
        <el-table :data="exceldata" border style="width: 100%">
          <el-table-column prop="学生学号" label="学号" width="auto">
          </el-table-column>
          <el-table-column prop="学生姓名" label="姓名"> </el-table-column>
          <el-table-column prop="性别" label="性别"> </el-table-column>
          <el-table-column prop="年级" label="年级"> </el-table-column>
          <el-table-column prop="班级" label="班级"> </el-table-column>
          <el-table-column prop="联系方式" label="联系方式"> </el-table-column>
        </el-table>
        <el-button @click="exceltable()">返回</el-button>
        <el-button type="primary" @click="uploadConfirm">确 定</el-button>
      </el-dialog> -->

      <!-- 列表 end -->

      <!-- <el-button
        size="middle"
        class="filter-item"
        type="warning"
        icon="el-icon-upload"
        >导入</el-button
      > -->
      <el-button
        size="middle"
        type="warning"
        @click="uploadOpen"
        class="filter-item"
        icon="el-icon-upload"
      >
        导入
      </el-button>
      <!-- 弹框 -->
      <el-dialog
        title="导入学生信息"
        :visible.sync="uploadVisible"
        width="60%"
        :close-on-click-modal="false"
      >
        <el-table :data="exceldata" border style="width: 100%">
          <el-table-column prop="学生学号" label="学生学号" width="auto">
          </el-table-column>
          <el-table-column prop="学生姓名" label="学生姓名"> </el-table-column>
          <el-table-column prop="性别" label="性别"> </el-table-column>
          <el-table-column prop="年级" label="年级"> </el-table-column>
          <el-table-column prop="班级" label="班级"> </el-table-column>
          <el-table-column prop="联系方式" label="联系方式"> </el-table-column>
        </el-table>
        <el-form
          ref="uploadform"
          :model="uploadform"
          label-width="110px"
          :label-position="labelPosition"
          size="small"
        >
          <el-form-item label="上传学生表格" prop="docType.crFile" ref="crFile">
            <el-upload
              ref="uploadFile"
              prop="uploadFile"
              :file-list="uploadform.docType.crFile"
              :auto-upload="false"
              :action="baseUrl"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :on-change="handleFileChange"
              :limit="1"
              :on-exceed="handleExceed"
              :on-success="handleSuccess"
              :before-upload="beforeUpload"
            >
              <el-button size="mini" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="mini" type="primary" @click="uploadConfirm"
          >确 定</el-button
          >
          <el-button size="mini" @click="closeUpload">关 闭</el-button>
        </span>
      </el-dialog>
    </el-form>

    <!-- <el-button style="margin-left: 15px;" type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button> -->

    <!-- 展示表格 -->
    <el-table :data="tableData" border stripestyle="width:100%" @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="40">
      </el-table-column>
      <el-table-column prop="studentName" label="姓名" align="center" />
      <el-table-column prop="studentNum" label="学号" align="center" />
      <el-table-column prop="gender" label="性别" align="center" />
      <el-table-column prop="gradeName" label="年级" align="center" />
      <el-table-column prop="className" label="班级" align="center" />
      <!-- <el-table-column prop="schoolName" label="学校" align="center" /> -->
      <el-table-column prop="parentPhoneNum" label="家长联系" align="center" />
      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button
          >
          <el-button
            type="danger"
            style="margin-left: 5px"
            size="mini"
            @click="handleDelete(scope.row.studentId)"
          >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑表单 -->
    <el-dialog
      title="请编辑信息"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
    >
      <el-form :model="form">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form.studentName"></el-input>
        </el-form-item>
        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input v-model="form.studentNum"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select v-model="form.gender">
            <el-option label="男生" value="1"></el-option>
            <el-option label="女生" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" :label-width="formLabelWidth">
          <el-select v-model="form.gradeId" filterable clearable>
            <el-option
              v-for="item in gradeandclass"
              :key="item.id"
              :label="item.grade.gradeName"
              :value="item.grade.gradeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" :label-width="formLabelWidth">
          <el-select v-model="form.classId" filterable clearable>
            <el-option
              v-for="item in classlist"
              :key="item.classId"
              :label="item.className"
              :value="item.classId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="家长联系方式" :label-width="formLabelWidth">
          <el-input v-model="form.parentPhoneNum"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleSure">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 新增表单 -->
    <el-dialog
      title="请添加学生信息"
      :visible.sync="addFormVisible"
      :close-on-click-modal="false"
    >
      <el-form :model="addForm">
        <el-form-item label="学生姓名" :label-width="formLabelWidth">
          <el-input v-model="addForm.studentName" placeholder="名字"></el-input>
        </el-form-item>
        <el-form-item label="学生学号" :label-width="formLabelWidth">
          <el-input v-model="addForm.studentNum" placeholder="学号"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select v-model="addForm.gender">
            <el-option label="男生" value="1"></el-option>
            <el-option label="女生" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" :label-width="formLabelWidth">
          <el-select v-model="addForm.grade" filterable clearable>
            <el-option
              v-for="item in gradeandclass"
              :key="item.id"
              :label="item.grade.gradeName"
              :value="item.grade.gradeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" :label-width="formLabelWidth">
          <el-select v-model="addForm.classId" filterable clearable>
            <el-option
              v-for="item in classlist"
              :key="item.classId"
              :label="item.className"
              :value="item.classId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="家长联系" :label-width="formLabelWidth">
          <el-input
            v-model="addForm.parentPhoneNum"
            placeholder="手机号"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleAddSure">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 分页 -->
    <!--      layout="prev, pager, next"-->
<!--    @current-change="getList"-->
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
import {
  getStudentsByPage,
  deleteStudent,
  addStudent,
  alterStudent,
  getStudentExcel,
  getTemplate,
  deleteBatch, getAllStudent
} from "@/api/managementModule/student";
import { getAllClassInfo } from "@/api/managementModule/classManagement";
import { isExternal } from "@/utils/validate";
import { baseUrl } from "../../../api/baseapi";
import item from "@/mixins/item";
import * as XLSX from "xlsx";
import JOSN from "qs";
export default {
  data() {
    return {
      uploadVisible: false,
      labelPosition: "right",
      uploadform: {
        docType: {
          crFile: [],
        },
      },
      excelVisible: false,
      exceldata: [],
      baseUrl: baseUrl + "/api/student/uploadStudents",
      limit: 10,
      page: 1, //分页组件页码初始化
      searchObj: {}, // 条件搜索对象
      currentPage: 1, //当前页
      tableData: [],
      list:[],
      total: 20,
      classMapping: {},
      formLabelWidth: "120px",
      addFormVisible: false,
      addForm: {},
      downloadLoading: false, // 导出按钮
      dialogFormVisible: false, //编辑表单
      form: {}, //表单信息
      teacherID: "",
      username: "",
      grade: [],
      className: [],
      gradeName: [],
      gradeMapping: {},
      gradeandclass: [],
      classlist: [],
      multipleSelection:[],
    };
  },
  created() {
    this.getClassID();
  },
  mounted() {
    this.getAll();
  },
  watch: {
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
  methods: {
    // 导出选中的数据为Excel
    exportExcel() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning('请先选择数据');
        return;
      }

      // 准备导出数据
      const exportData = this.multipleSelection.map(row => ({
        学生Id: row.studentId,
        学号: row.studentNum,
        学生姓名: row.studentName,
        性别标识: row.gender === '男' ? 1 : 0,
        学生性别: row.gender,
        学生年级Id: row.gradeId,
        学生班级Id: row.classId,
        学生年级: row.gradeName,
        学生班级: row.className,
        家长电话: row.parentPhoneNum,
      }));

      // 创建Excel工作表
      const ws = XLSX.utils.json_to_sheet(exportData, {
        header: ['学生Id', '学号', '学生姓名', '性别标识', '学生性别', '学生年级Id', '学生班级Id', '学生年级', '学生班级', '家长电话'],
      });

      // 设置列宽，确保列内容能显示完全
      ws['!cols'] = [
        { wch: 10 }, // 学生Id
        { wch: 12 }, // 学号
        { wch: 15 }, // 学生姓名
        { wch: 15 }, // 性别标识
        { wch: 10 }, // 学生性别
        { wch: 15 }, // 学生年级Id
        { wch: 15 }, // 学生班级Id
        { wch: 20 }, // 学生年级
        { wch: 20 }, // 学生班级
        { wch: 18 }, // 家长电话
      ];

      // 设置header样式
      const headerStyle = {
        font: { name: '宋体', sz: 14, bold: true }, // 字体为宋体，14号，粗体
        alignment: { horizontal: 'center', vertical: 'center', wrapText: true }, // 居中对齐，文字换行
        fill: { fgColor: { rgb: 'C0C0C0' } }, // 背景填充颜色
      };

      // 设置数据行样式
      const rowStyle = {
        font: { name: '宋体', sz: 11 }, // 字体为宋体，11号
        alignment: { horizontal: 'center', vertical: 'center' }, // 居中对齐
      };

      // 为header添加样式
      const range = XLSX.utils.decode_range(ws['!ref']);
      for (let col = range.s.c; col <= range.e.c; col++) {
        const cell = ws[XLSX.utils.encode_cell({ r: range.s.r, c: col })];
        if (cell) {
          cell.s = headerStyle;
        }
      }

      // 为数据行添加样式
      for (let row = range.s.r + 1; row <= range.e.r; row++) {
        for (let col = range.s.c; col <= range.e.c; col++) {
          const cell = ws[XLSX.utils.encode_cell({ r: row, c: col })];
          if (cell) {
            cell.s = rowStyle;
          }
        }
      }

      // 创建工作簿
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, '选中数据');

      // 导出文件
      XLSX.writeFile(wb, '选中学生数据.xlsx');
    },

    // exportExcel() {
    //   if (this.multipleSelection.length === 0) {
    //     this.$message.warning('请先选择数据');
    //     return;
    //   }
    //
    //   // 准备导出数据
    //   const exportData = this.multipleSelection.map(row => ({
    //     学生Id: row.studentId,
    //     学号: row.studentNum,
    //     学生姓名: row.studentName,
    //     性别标识: row.gender,
    //     学生性别: row.gender,
    //     学生年级Id: row.gradeId,
    //     学生班级Id: row.classId,
    //     学生年级: row.gradeName,
    //     学生班级: row.className,
    //     家长电话: row.parentPhoneNum,
    //   }));
    //
    //   // 创建Excel工作簿
    //   const ws = XLSX.utils.json_to_sheet(exportData, {
    //     header: ['学生Id', '学号', '学生姓名', '性别标识', '学生性别', '学生年级Id', '学生班级Id', '学生年级', '学生班级', '家长电话'],
    //   });
    //
    //   // 设置header样式
    //   const headerStyle = {
    //     font: { name: '宋体', sz: 14, bold: true }, // 字体为宋体，14号，粗体
    //     alignment: { horizontal: 'center', vertical: 'center', wrapText: true }, // 居中对齐，文字换行
    //     fill: { fgColor: { rgb: 'C0C0C0' } }, // 背景填充颜色
    //   };
    //
    //   // 设置数据行样式
    //   const rowStyle = {
    //     font: { name: '宋体', sz: 11 }, // 字体为宋体，11号
    //     alignment: { horizontal: 'center', vertical: 'center' }, // 居中对齐
    //   };
    //
    //   // 为header添加样式
    //   const range = XLSX.utils.decode_range(ws['!ref']);
    //   for (let col = range.s.c; col <= range.e.c; col++) {
    //     const cell = ws[XLSX.utils.encode_cell({ r: range.s.r, c: col })];
    //     if (cell) {
    //       cell.s = headerStyle;
    //     }
    //   }
    //
    //   // 为数据行添加样式
    //   for (let row = range.s.r + 1; row <= range.e.r; row++) {
    //     for (let col = range.s.c; col <= range.e.c; col++) {
    //       const cell = ws[XLSX.utils.encode_cell({ r: row, c: col })];
    //       if (cell) {
    //         cell.s = rowStyle;
    //       }
    //     }
    //   }
    //
    //   // 创建工作簿
    //   const wb = XLSX.utils.book_new();
    //   XLSX.utils.book_append_sheet(wb, ws, '选中数据');
    //
    //   // 导出文件
    //   XLSX.writeFile(wb, '选中学生数据.xlsx');
    // },
    // 当前页变化
    handleCurrentChange(currentPage) {
      this.page = currentPage
      this.tableData = this.list.slice(this.limit * (currentPage - 1), this.limit * currentPage)
    },
    // 页面大小更新
    handleSizeChange(pageSize) {
      this.page = 1
      this.limit = pageSize
      this.tableData = this.list.slice(0, pageSize)
    },

    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.studentId)     //  [{},{},{}] => [1,2,3]
      console.log(ids);
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() =>{
        deleteBatch(ids).then(res => {
          if(res){
            this.$message.success("批量删除成功")
            this.getList();
          }else{
            this.$message.error("批量删除失败")

          }
        })
      }).catch(() => {
        this.$message.error("已取消批量删除")
      });
    },

    reset(){
      this.searchObj = {}
    },
    uploadOpen() {
      console.log("windowopenstemp1");
      var vm = this;
      vm.uploadform.docType.crFile = [];
      console.log(vm);
      vm.uploadVisible = true;
    },
    handlePreview(file) {
      console.log(111111111111111111111111);
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
    handleExceed(files, fileList) {
      this.$message.warning(`最多上传1个文件`);
    },
    beforeRemove(file, fileList) {
      if (this.Qualified == "") {
        return this.$confirm(`确定移除 ${file.name}？`);
      } else if (this.Qualified == "1") {
        return true;
      }
    },
    // 上传测试文件
    uploadConfirm() {
      var vm = this;
      // vm.$refs.doctypeCrfile.submit();
      vm.$refs.uploadFile.submit();
      this.exceldata = [];
    },
    closeUpload() {
      this.uploadVisible = false;
      this.exceldata = [];
    },
    handleSuccess(response) {
      console.log(response);
      if (response.code == 200) {
        this.$message.success(response.message);
        this.uploadVisible = false;
      } else {
        this.$message.error(response.message);
      }
      this.exceldata = [];
      this.getList();
    },
    beforeUpload(file) {
      console.log(file);
      //文件类型
      const isType = file.type === "application/vnd.ms-excel";
      const isTypeComputer =
        file.type ===
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      const fileType = isType || isTypeComputer;
      if (!fileType) {
        this.$message.error("上传文件只能是xls/xlsx格式！");
      }

      // 文件大小限制为10M
      const fileLimit = file.size / 1024 / 1024 < 10;
      if (!fileLimit) {
        this.$message.error("上传文件大小不超过10M！");
      }
      console.log("brforeuploadfile");
      return fileType && fileLimit;
      // return false
    },
    submitexcel() {
      // beforeUpload(file)
    },
    exceltable() {
      this.excelVisible = !this.excelVisible;
    },
    isNum(str) {
      return /^[0-9]{10}$/.test(str);
    },
    ischina(str) {
      return /^[\u4E00-\u9FA5]{1,20}$/.test(str);
    },
    istel(str) {
      return /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/.test(str);
    },
    // handleSuccess(response) {
    //   console.log(response, "response");
    // },
    // 上传文件
    httpRequest(e) {
      // const file = e.file; //文件信息
      const file = e.raw; //文件信息
      if (!file) {
        // 没有文件
        return false;
      } else if (!/\.(xls|xlsx)$/.test(file.name.toLowerCase())) {
        // 非xlsx或者xls
        this.$message.error("上传格式有误，请上传xlsx或者xls格式");
        return false;
      }
      // 读取文件
      const fileReader = new FileReader();
      fileReader.onload = (ev) => {
        try {
          const data = ev.target.result;
          const workbook = XLSX.read(data, {
            type: "binary", //以字符编码方式解析
          });
          const exlname = workbook.SheetNames[0]; //取第一张表
          const exl = XLSX.utils.sheet_to_json(workbook.Sheets[exlname]); //生成表格内容
          console.log(exl);
          // 将JSON数据挂到data里
          const header = ['学生学号','学生姓名','性别','年级','班级','联系方式']
          const filteredData = exl.filter(
            row => row[header[0]] !== undefined&&
                   row[header[1]] !== undefined&&
                   row[header[2]] !== undefined&&
                   row[header[3]] !== undefined&&
                   row[header[4]] !== undefined&&
                   row[header[2]] !== undefined);
          this.exceldata = filteredData;
          console.log(this.exceldata);
        } catch (e) {
          console.log("出错了");
          return false;
        }
      };
      fileReader.readAsBinaryString(file);
    },
    handleFileChange(file, fileList) {
      console.log(file, "filechange");
      this.httpRequest(file);
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
    async getList(page = 1) {
      this.currentPage = page;
      // console.log(this.currentPage);
      // console.log(this.searchObj.gradeId);

      var classId = "";
      if (
        this.searchObj.className != "" &&
        this.searchObj.className != undefined
      ) {
        classId = this.searchObj.className.toString().slice(0, -1);
      }
      // var classId = JSON.parse(sessionStorage.getItem("classId"));
      const payload = {
        ...this.searchObj,
        pageSize: this.limit,
        pageNum: this.page,
        classId: classId, //传入班级ID
      };
      console.log(payload, "payload");

      const res = await getStudentsByPage(payload);
      this.total = res.data.total;
      this.list = res.data.list.map((item) => {
        return {
          ...item,

          gender: item.gender === 0 ? "女" : "男",
          className:
          // item.classId.toString()[0] +
          // "年级" +
            item.classId.toString() + "班",

          gradeName: item.gradeId.toString() + "年级",
        };
      });
      this.tableData = this.list.slice(0, this.limit);
    },
    async getAll(){
      this.page = 1;
      const res = await getAllStudent();
      this.total = res.data.length;
      this.list = res.data.map((item) => {
        return {
          ...item,
          gender: item.gender === 0 ? "女" : "男",
          className: item.classId.toString() + "班",
          gradeName: item.gradeId.toString() + "年级",
        };
      });
      this.tableData = this.list.slice(0, this.limit)
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
        console.dir("??///"+gac.id)
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
    unique(arr) {
      return Array.from(new Set(arr));
    },
    async handleEdit(index, row) {
      console.log(row);
      this.form = row;
      this.form.gradeId = this.form.gradeId + "年级";
      // console.log(this.form, "编辑前的东西");
      console.log("this.form.gradeId = " + this.form.gradeId);
      this.getclasslist(this.form.gradeId);
      console.log(this.form.classId, "classId");
      var temp = row.classId;
      // this.form.classId = parseInt(this.form.className);
      this.form.classId = temp;
      //   parseInt(this.form.classId) - parseInt(this.form.grade) * 10;
      this.dialogFormVisible = true;
    },
    // handlePreview(file) {
    //   console.log(file, 11111111111);
    //   this.excelVisible = !this.excelVisible;
    // },
    // uploadConfirm() {
    //   var vm = this;
    //   console.log("uploadconfirm");
    //   vm.$refs.uploada.submit();
    // },

    fileChange(file, fileList) {
      console.log(file, "filechange");
      console.log(fileList);
    },
    addNewStudent() {
      this.addFormVisible = true;
      this.addForm = {
        grade: "",
        classId: "",
      };
    },
    async handleCancel() {
      this.dialogFormVisible = false;
      this.addFormVisible = false;
      // this.form = {
      //   gradeId:''
      // }
      // this.getList();
      this.getAll();
    },
    handleModule() {
      this.downloadLoading = true;  // 开始加载

      // 模板文件的路径（相对于 public 文件夹）
      const fileUrl = '/excel/student.xlsx';

      // 创建一个链接来触发下载
      const link = document.createElement('a');
      link.href = fileUrl;
      link.download = 'studentInput.xlsx';  // 设置下载的文件名
      link.click();  // 触发下载

      // 重置加载状态
      this.downloadLoading = false;
      // getTemplate();
    },
    handleAddSure() {
      if (
        this.addForm.studentName === "" ||
        this.addForm.studentNum === "" ||
        this.addForm.gender === "" ||
        this.addForm.classId === "" ||
        this.addForm.grade === ""
      ) {
        console.log(this.addForm, "表单数据");
        this.$message({
          message: "添加失败，部分数据为空",
          type: "error",
        });
        return;
      }
      // 学号--学号只能是8为数字
      if (!this.isNum(this.addForm.studentNum)) {
        this.$message({
          message: "添加失败,学号只能是10位数字",
          type: "error",
        });
        return;
      }
      //姓名，只能是中文，且长度不能超过20
      if (!this.ischina(this.addForm.studentName)) {
        this.$message({
          message: "添加失败，姓名只能是中文，且长度不能超过二十",
          type: "error",
        });
        return;
      }
      //电话
      if (!this.istel(this.addForm.parentPhoneNum)) {
        this.$message({
          message: "添加失败，请输入正确的电话号码",
          type: "error",
        });
        return;
      }
      let temp = this.addForm.classId;

      // this.addForm.classId = this.addForm.grade + temp;
      this.addForm.gradeId = this.addForm.grade;
      console.log("this.addFormO(843) = " + this.addForm);
      addStudent(this.addForm).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "添加成功",
            type: "success",
          });
          this.getList();
          // this.getAll();
        } else {
          this.classId = temp;
          this.$message({
            message: "添加失败",
            type: "error",
          });
        }
        return;
      });
      this.addFormVisible = false;
    },
    async fixsex(classId) {
      if (this.form.gender == 1) {
        this.form.gender = "男";
      }
      if (this.form.gender == 0) {
        this.form.gender = "女";
      }
      this.form.classId = classId;
    },
    //确认修改
    async handleSure() {
      var temp = this.form.classId;
      console.log(temp);

      if (this.form.gender == "男") {
        this.form.gender = 1;
      }
      if (this.form.gender == "女") {
        this.form.gender = 0;
      }
      // this.form.classId = this.form.grade + this.form.classId;
      this.form.gradeId = parseInt(this.form.gradeId);
      // this.form.classId = parseInt(this.form.classId)
      console.log(this.form, "即将修改的表单长这样");
      if (
        this.form.studentName === "" ||
        this.form.studentNum === "" ||
        this.form.gender === "" ||
        this.form.classId === "" ||
        this.form.grade === ""
      ) {
        console.log(this.form, "修改数据的表单");
        this.$message({
          message: "修改失败，部分数据为空",
          type: "error",
        });
        this.fixsex(temp);
        // this.fixsex();

        return;
      }

      // 学号
      if (!this.isNum(this.form.studentNum)) {
        this.$message({
          message: "修改失败，学号有误",
          type: "error",
        });
        this.fixsex(temp);
        return;
      }
      //姓名
      if (!this.ischina(this.form.studentName)) {
        this.$message({
          message: "修改失败，姓名有误",
          type: "error",
        });
        this.fixsex(temp);
        return;
      }
      //电话
      if (!this.istel(this.form.parentPhoneNum)) {
        this.$message({
          message: "修改失败，电话号码有误",
          type: "error",
        });
        this.fixsex(temp);
        return;
      }
      alterStudent(this.form).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "修改成功",
            type: "success",
          });
        } else {
          this.$message({
            message: "修改失败，网络错误",
            type: "error",
          });
        }
        this.getList();
        // this.getAll();
      });
      this.dialogFormVisible = false;
    },
    //导出学生数据
    handleDownload() {
      this.downloadLoading = true;
      getStudentExcel(this.searchObj);
      this.downloadLoading = false;
    },
    handleDelete(studentId) {
      deleteStudent({ studentId }).then((res) => {
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.getList();
        // this.getAll();
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.filter-item {
  float: right;
  margin-right: 40px;
}
</style>
