<template>
  <div>
    <div style="padding: 10px 0">
      <!-- 搜索表单 -->
      <el-form :inline="true" class="demo-form-inline" :rules="searchRules" ref="searchForm">
        <el-form-item>
          <el-form-item prop="teacherName">
            <el-input v-model="searchObj.teacherName" clearable placeholder="教师姓名" @focus="clearTeacherName"/>
          </el-form-item>
          <el-form-item>
            <el-select v-model="searchObj.gender" clearable placeholder="性别">
              <el-option label="男" value="1">男</el-option>
              <el-option label="女" value="0">女</el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select  v-model="searchObj.position" clearable placeholder="职位">
              <el-option v-for="(item,index) in positionList" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-form-item>
        <el-button style="margin-left: 5px" type="primary" @click="searchTeacher">搜索</el-button>
        <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
        <el-button style="margin-left: 5px" type="success" @click="exportExcel">导出</el-button>
        <el-button style="margin-left: 5px" type="success" @click="downloadTemplate">下载模板</el-button>
        <el-button type="success" round @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      </el-form>
    </div>
    <!--          表格数据，要关联到数据库 -->
    <el-table :data="tableData" border stripe header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange" >
      <!--            在下面有tableData-->

      <el-table-column type="selection" align="center"   width="40"></el-table-column>
      <el-table-column prop="id"   align="center"   label="教师ID" width="110"></el-table-column>
      <el-table-column prop="teacherName"   align="center"   label="老师姓名" width="110"></el-table-column>
      <el-table-column prop="gender" align="center"   label="性别" width="50"></el-table-column>
      <el-table-column prop="phoneNum"   align="center"      label="电话号码"></el-table-column>
      <el-table-column prop="position"   align="center"  label="职位"></el-table-column>
      <el-table-column prop="title"  align="center"   label="职称"></el-table-column>
      <el-table-column prop="role"   align="center"  label="角色"></el-table-column>
<!--      <el-table-column prop="deleted"  align="center"   label="是否已删除"></el-table-column>-->
<!--      <el-table-column prop="schoolId"  align="center"   label="学校编号"></el-table-column>-->
      <el-table-column prop="username"  align="center"   label="账户"></el-table-column>
      <el-table-column prop="password"  align="center"   label="密码"></el-table-column>
      <el-table-column prop="politicalAppearance"   align="center"  label="政治面貌"></el-table-column>
      <el-table-column prop="birthPlace"  align="center"   label="籍贯"></el-table-column>
      <el-table-column prop="age"  align="center"   label="出生年份"></el-table-column>
      <el-table-column prop="info" align="center" show-overflow-tooltip  label="备注信息"></el-table-column>
      <el-table-column label="操作" align="center" width="190px">
        <template slot-scope="scope">
          <div style="display: flex; justify-content: flex-start; align-items: center;">
            <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
            <el-popconfirm
              style="margin-left: 5px"
              confirm-button-text='确定删除'
              cancel-button-text='我再思考一下'
              icon="el-icon-info"
              icon-tel="red"
              title="您确定删除此用户吗？"
              @confirm="del(scope.row.id)">
              <el-button type="danger" slot="reference" >删除<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="block">
      <el-pagination
        background
        :current-page.sync="currentPage"
        layout="sizes,prev, pager, next,total,jumper"
        style="padding: 30px 0; text-align: center;"
        :total="total"
        :page-count="pageCount"
        :page-size="limit"
        :page-sizes="[3,5,10]"
        @current-change="jump"
        @next-click="nextPage()"
        @prev-click="prePage()"
        @size-change="handleSizeChange"
      >
      </el-pagination>
    </div>



    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px" size="small">
        <el-form-item label="老师姓名" prop="teacherName">
          <el-input v-model="form.teacherName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码" prop="phoneNum">
          <el-input v-model="form.phoneNum" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-select  v-model="form.position" placeholder="请选择职位">
            <el-option v-for="(item,index) in positionList" :key="index" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-input v-model="form.role" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="是否已删除" prop="deleted">
          <el-select v-model="form.deleted" placeholder="请选择">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>-->
<!--        <el-form-item label="学校编号" prop="schoolId">
          <el-input v-model="form.schoolId" autocomplete="off"></el-input>
        </el-form-item>-->
        <el-form-item label="账户" prop="username">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="政治面貌" prop="politicalAppearance">
          <el-input v-model="form.politicalAppearance" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="籍贯" prop="birthPlace">
          <el-input v-model="form.birthPlace" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出生年份" prop="age">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注信息" prop="info">
          <el-input v-model="form.info" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <lottie :options="defaultOptions" style="width: 600px" @animCreated="handleAnimation" />
  </div>
</template>

<script>
import request from "@/utils/request";
import * as animationData from "@/assets/athletes.json";
import axios from "axios";
import {baseUrl} from "@/api/baseapi";
// import {getTeacherListByPage} from "@/api/managementModule/teacher";
import {getTeacherListByPage1} from "@/api/teacher";
import {resetForm} from "@/utils/ruoyi";

export default {
  name: "User",
  data(){
    return{
      searchObj: {
        teacherName: "",
        gender: "",
        position: "",
        grade: ""
      }, // 条件搜索对象
      form: {
        teacherName: '',
        gender: '',
        phoneNum: '',
        position: '',
        title: '',
        role: '',
        deleted: 0,
        schoolId: 1,
        username: '',
        password: '',
        politicalAppearance: '',
        birthPlace: '',
        age: '',
        info: '',
      },
      rules: {
        teacherName: [{ required: true, message: '老师姓名不能为空', trigger: 'blur' }],
        gender: [{ required: true, message: '性别不能为空', trigger: 'blur' }],
        phoneNum: [
          { required: true, message: '电话号码不能为空', trigger: 'blur' },
          { pattern: /^[0-9]{11}$/, message: '电话号码格式不正确', trigger: 'blur' }
        ],
        position: [{ required: true, message: '职位不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '职称不能为空', trigger: 'blur' }],
        role: [{ required: true, message: '角色不能为空', trigger: 'blur' }],
        //deleted: [{ required: true, message: '是否已删除不能为空', trigger: 'blur' }],
        //schoolId: [{ required: true, message: '学校编号不能为空', trigger: 'blur' }],
        username: [{ min: 1, max: 20, message: '账户长度需在1到20个字符之间', trigger: 'blur' }],
        password: [
          { min: 6, max: 20, message: '密码长度需在6到20个字符之间', trigger: 'blur' }
        ],
        // politicalAppearance: [{ required: true, message: '政治面貌不能为空', trigger: 'blur' }],
        // birthPlace: [{ required: true, message: '籍贯不能为空', trigger: 'blur' }],
        // age: [
        //   // { required: true, message: '出生日期不能为空', trigger: 'blur' },
        //   {
        //     pattern: /^\d{4}$/,
        //     message: '出生年份格式应为YYYY',
        //     trigger: 'blur'
        //   }
        // ],
        // info: [{ required: true, message: '备注信息不能为空', trigger: 'blur' }]
      },

      positionList: [],
      tableData: [],
      currentPage: 1, //当前页
      pageCount:1,
      limit: 10,
      page: 1, //分页组件页码初始化
      teacherValue: '',
      teacherContent: '',
      teacherSearch: [{
        value: 'id',
        label: '教师ID'
      }, {
        value: 'teacherName',
        label: '老师姓名'
      }, {
        value: 'gender',
        label: '性别'
      }, {
        value: 'phoneNum',
        label: '电话号码'
      }, {
        value: 'position',
        label: '职位'
      }, {
        value: 'title',
        label: '职称'
      }, {
        value: 'role',
        label: '角色'
      }, {
        value: 'politicalAppearance',
        label: '政治面貌'
      }, {
        value: 'birthPlace',
        label: '籍贯'
      }],
      total:0,//total的绑定在133行:total="400"
      pageNum:1,
      pageSize:10,
      id:"",
      gender:"",
      phone_num:"",
      // form: {},
      dialogFormVisible:false,
      multipleSelection:[],
      headerBg:'headerBg',
      defaultOptions: {
        animationData: animationData
      },
      searchRules: {
        teacherName: [
          {validator: this.validateTeacherName, trigger: 'blur'},
        ],
      },
    }
  },
  created() {
    this.searchTeacher()
    this.getGradeAndPosition()

  },
  methods: {
    clearTeacherName() {
      if (this.inputStatus === false) {
        this.$refs.searchForm.clearValidate('teacherName');
        this.searchObj.teacherName = '';
      }
      this.inputStatus = true
    },
    getGradeAndPosition() {
      axios.get(baseUrl + '/api/teacherQuery/getFormObject').then(res => {
        if (res.data.code === 200) {
          // this.gradeList = res.data.data.gradeList;
          this.positionList = res.data.data.positionList;
        }
      })
    },
    jump(page) {//跳转指定页面
      this.page = page;
      console.log("page:" + page)
      this.searchTeacher();
    },
    nextPage() {//下一页
      if (this.page < this.maxPage) {
        this.page = this.page + 1
        this.searchTeacher()
      }

    },
    prePage() {//上一页
      if (this.page > 1) {
        this.page = this.page - 1
        this.searchTeacher()
      }
    },
    searchTeacher() {
      this.currentPage = this.page;
      // this.limit = 2;
      const payload = {
        pageSize: this.limit,
        pageNum: this.currentPage,
        ...this.searchObj,
      }
      getTeacherListByPage1(payload).then(res => {
        console.log("----------++++++++++++");
        console.log(res);
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
            //deleted: item.deleted == 0 ? '否' : '是',
            username:(item.username==''|| item.username == null)?'(暂无)':item.username,
            politicalAppearance: (item.politicalAppearance == '' || item.politicalAppearance == null) ? '(暂无)': item.politicalAppearance,
            birthPlace:  (item.birthPlace == '' || item.birthPlace == null) ? '(暂无)': item.birthPlace,
            age:  (item.age == '' || item.age == null) ? '(暂无)': item.age,
            info:  (item.info == '' || item.info == null) ? '(暂无)': item.info,
          }
        })
      })
    },
    // load(){
    //   request.get("/teacher/page",{
    //     params: {
    //       pageNum: this.pageNum,
    //       pageSize: this.pageSize,
    //       teacher_name: this.teacher_name,
    //       gender: this.gender,
    //       phone_num:this.phone_num
    //     }
    //   }).then(res =>{
    //     this.tableData=res.records
    //     this.total=res.total
    //   })
    //   //请求分页查询混数据
    //   //res之后需要对res进行json的处理
    //   //使数据变成活的，当改变pageNum时候，他也会跟着改变
    // },
    resetForm() {
      this.$refs.formRef.resetFields();
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          // alert('提交成功');
          if (this.form.gender === '男') this.form.gender = '1';
          if (this.form.gender === '女') this.form.gender = '0';
/*          if (this.form.deleted === '是') this.form.deleted = '1';
          if (this.form.deleted === '否') this.form.deleted = '0';*/
          if (this.form.username === '(暂无)') this.form.username = null;
          if (this.form.politicalAppearance === '(暂无)') this.form.politicalAppearance = null;
          if (this.form.birthPlace === '(暂无)') this.form.birthPlace = null;
          if (this.form.age === '(暂无)') this.form.age = null;
          if (this.form.info === '(暂无)') this.form.info = null;
          this.form.schoolId = 1;
          this.form.deleted = 0;
          request.post("/teacher", this.form).then(res => {
            if (res) {
              this.$message.success("保存成功")
              //保存成功后弹窗关闭
              this.dialogFormVisible = false
              this.searchTeacher()
            } else {
              this.$message.success("保存失败")
            }
          })
        } else {
          console.log('表单验证失败');
          return false;
        }
      });
    },
    handleAdd() {
      this.form = {}
      this.dialogFormVisible = true
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    del(id) {
      request.delete("/teacher/" + id).then(res => {
        if (res) {
          this.$message.success("删除成功")
          //保存成功后弹窗关闭
          this.searchTeacher()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    // delBatch(){
    //   let ids=this.multipleSelection.map(v=>v.id) //[]=>[1.2.3]
    //     request.post("/teacher/del/batch",ids).then(res =>{
    //         if(res){
    //             this.$message.success("批量删除成功")
    //             //保存成功后弹窗关闭
    //             this.load()
    //         }else{
    //             this.$message.error("批量删除失败")
    //         }
    //     })
    // },
    handleAnimation: function (anim) {
      this.anim = anim;
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val

    },
    reset() {
      this.searchObj.teacherName = ""
      this.searchObj.gender = ""
      this.searchObj.grade = ""
      this.searchObj.position = ""
      this.page = 1
      this.searchTeacher()
    },
    handleSizeChange(val) {
      this.limit = val;
      this.searchTeacher()
    },
    // handleCurrentChange(pageNum){
    //   console.log(`当前页 ${pageNum} `)
    //   this.pageNum=pageNum
    //   this.load()
    // },
    // exportExcel(){
    //     window.open("http://localhost:9090/teacher/exportExcel")
    // },
    // importExcel(){
    //     this.$message.success("导入成功");
    //     this.load();
    // }
    downloadTemplate() {
      try {
        // 使用正确的端口号
        const downloadUrl = 'http://localhost:9085/teacher/downloadTemplate';

        // 创建一个临时的 a 标签用于下载
        const link = document.createElement('a');
        // 设置下载链接
        link.href = downloadUrl;
        // 设置下载文件名
        link.setAttribute('download', '教师信息导入模板.xlsx');
        // 设置样式为隐藏
        link.style.display = 'none';

        // 添加到文档中
        document.body.appendChild(link);

        // 触发点击事件开始下载
        link.click();

        // 下载开始后移除该元素
        document.body.removeChild(link);

        // 显示下载开始提示
        this.$message({
          message: '模板下载已开始，请稍候...',
          type: 'success',
          duration: 2000
        });
      } catch (error) {
        console.error('下载模板失败:', error);
        if (error.response) {
          console.error('错误状态:', error.response.status);
          console.error('错误信息:', error.response.data);
        }
        this.$message.error('下载模板失败，请确保后端服务正常运行');
      }
    },
  }

}
</script>
<style>
headerBg {
  background: #eee !important;
}
</style>
