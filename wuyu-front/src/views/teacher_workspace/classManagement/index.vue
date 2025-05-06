<template>
  <el-container style="height: 100%; ">
<el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
      <el-container>
        <div>
    <!-- 搜索表单 -->
    <el-form :inline="true" class="demo-form-inline" style="margin-top: 10px">
      <el-form-item>
        <el-select v-model="selectedGrade" clearable placeholder="年级筛选">
          <el-option v-for="(value, index) in gradeList"
                     :key="index"
                     :label="value"
                     :value="index"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="selectedMonitor" clearable placeholder="班主任">
          <el-option v-for="(value, index) in monitorList"
                     :key="index"
                     :label="value"
                     :value="index"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList(1,selectedGrade,selectedMonitor)">查询</el-button>
      <el-button type="success" icon="el-icon-plus" @click="addClassInfo()">新增</el-button>
      <el-upload
        style="display: inline-block;margin-left: 10px"
        class="upload-class"
        action="http://49.51.69.99:9200/api/class/upload"
        accept=".xls,.xlsx"
        :on-success="uploadSuccess"
        :on-error="uploadError"
        :before-upload="beforeUpload"
        name="file"
        :on-change="fileChange"
        :show-file-list="false">
        <el-tooltip  class="item" effect="dark" content="导入文件为.xls/.xlsx文件，且依次包含年级、班级、班主任名称三列不得为空的数据" placement="bottom">
          <el-button  type="warning" icon="el-icon-upload" >导入</el-button>
        </el-tooltip>
      </el-upload>
    </el-form>
    <!-- 展示表格 -->
    <el-table :data="classList" stripestyle="width:100%" >
      <el-table-column type="index"
                       width="50"
                       label="序号"
                       align="center"/>
      <el-table-column label="年级"
                       align="center">
        <template slot-scope="scope">
          <span>{{gradeList[scope.row.gradeId-1]}}</span>
        </template>
      </el-table-column>
      <el-table-column label="班级"
                       align="center">
        <template slot-scope="scope">
          <span>{{scope.row.classType}}班</span>
        </template>
      </el-table-column>
      <el-table-column label="班主任信息" align="center">
        <el-table-column prop="realName"
                         label="姓名"
                         width="120"
                         align="center"/>
        <el-table-column label="性别"
                         width="120"
                         align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.gender==0">女</span>
            <span v-else>男</span>
          </template>
        </el-table-column>
        <el-table-column prop="contactInfo"
                         label="联系方式"
                         width="300"
                         align="center"/>
      </el-table-column>
      <el-table-column prop="studentNumber"
                       label="学生数量"
                       width="150"
                       align="center">
      </el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="primary" style="margin-left:5px" size="mini" @click="clickClass(gradeList[scope.row.grade-1],scope.row.className)">详情</el-button>
          <el-button type="danger" style="margin-left:5px" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination

      :current-page.sync="page"
      layout="prev, pager, next"
      style="padding: 30px 0; text-align: center;"
      :total="total"
      @current-change="getCurList(page)">
    </el-pagination>

    <!-- 详情 -->
    <el-drawer
      :title="classTitle"
      :visible.sync="drawer"
      size="35%"
    >
      <el-button type="info" @click="drawer = false">← 返回</el-button>
      <div class="drawer-table">
        <span>教师信息</span>
        <el-table :data="teacherList">
          <el-table-column prop="identityInfo"
                           label="担任职位"
                           width="200"/>
          <el-table-column prop="realName"
                           label="教师姓名"
                           width="200"/>
          <el-table-column prop="gender"
                           label="性别"
                           width="200">
            <template slot-scope="scope">
              <span v-if="scope.row.gender==0">女</span>
              <span v-else>男</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="drawer-table">
        <span>学生信息</span>
        <el-table :data="studentList">
          <el-table-column prop="studentId"
                           label="学号"
                           width="200"/>
          <el-table-column prop="studentName"
                           label="姓名"
                           width="200"/>
          <el-table-column prop="gender"
                           label="性别"
                           width="200">
            <template slot-scope="scope">
              <span v-if="scope.row.gender==0">女</span>
              <span v-else>男</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>
    <!--    添加班级页面-->
    <el-dialog
      title="请编辑班级信息"
      :visible.sync="addFormVisible">
      <el-form :model="classInfoForm"
               ref="classInfoForm"
               :rules="classFormRules">
        <el-form-item label="年级" prop="gradeId" label-width="120px">
          <el-select v-model="classInfoForm.gradeId"
                     placeholder="选择年级"
                     clearable>
            <template v-for="(item, index) in gradeList">
              <el-option :label="item" :value="index+1"></el-option>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="班级ID" prop="classId" label-width="120px">
          <el-input-number v-model="classInfoForm.classId"  :min="1"  label="班级ID，指明当前是第几班"></el-input-number>
        </el-form-item>
        <el-form-item label="班主任姓名" prop="teacherId" label-width="120px">
          <el-select v-model="classInfoForm.teacherId"
                     placeholder="选择班主任"
                     clearable>
            <template v-for="(item, index) in teacherList">
              <el-option :label="item" :value="index"></el-option>
            </template>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleClassSure('classInfoForm')">提 交</el-button>
      </div>
    </el-dialog>

    <!--   第二部分-->
    <el-dialog title="班级详细信息" :visible.sync="dialogFormVisible" center width="45%">
      <el-form
        :model="classForm"
        :rules="classAllFormRules"
        ref="classForm"
        label-width="80px">
        <el-form-item label="年级:" prop="grade" :label-width="formLabelWidth">
          <el-input readonly v-model="classForm.grade" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="班级:" prop="className" :label-width="formLabelWidth">
          <el-input readonly v-model="classForm.className" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="班主任:" prop="headTeacherName" :label-width="formLabelWidth">
          <el-input readonly v-model="classForm.headTeacherName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="班级简介:" prop="introductionToClass" :label-width="formLabelWidth">
          <el-input type="textarea" autosize v-model="classForm.introductionToClass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="查看全部学生:" :label-width="formLabelWidth">
          <el-button type="primary" @click="clickAllStudent()" >全部学生</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button  @click="dialogFormVisible=false">取消</el-button>
        <el-button type="warning" @click="sumbitClassInfo('classForm',classForm.classId,classForm.introductionToClass)">确认</el-button>
      </div>

    </el-dialog>

    <el-dialog
      title="班级所有学生"
      :visible.sync="showStudent"
      width="40%"
      border center>
      <el-table :data="student" height="400px">
        <el-table-column property="studentId" label="学号" width="140"></el-table-column>
        <el-table-column property="studentName" label="姓名" width="140"></el-table-column>
        <el-table-column  property="gender" label="性别" width="120">
          <template slot-scope="scope">{{ scope.row.gender === 0 ? '女' : '男' }}</template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="150">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="primary" size="small">查看</el-button>
            <el-button type="danger" size="small" @click="deletecurrStudent(scope.row.studentId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <span class="demonstration"></span>
        <el-pagination
          @current-change="handleCurrentChange(stucurrentPage)"
          @size-change="handleSizeChange"
          :current-page.sync="stucurrentPage"
          :page-sizes="[5,10,15]"
          :pager-count="5"
          :page-size="pageSize"
          :total="pageTotal"
          layout="total, sizes, prev, pager, next"
        >
        </el-pagination>
      </div>
    </el-dialog>
  </div>
      </el-container>
</el-container>

</template>
<script>
import Aside from "@/views/teacher_workspace/Aside"
import {
  deleteStudent,
  getClassPartInfoByClassAndGrade,
  getStudentByPage,
  updateClassByInformationId,   addClass,
  deleteClass1,
  getAllTeacherList,
  getGradeList,
  getMonitorList,
  getStudentsByClass
} from '@/api/classManage'
export default {
  data() {
    return {
      testparagrade:'三年级',
      testparaclass:'2班',
      stucurrentPage:1,
      pageSize:5,
      pageTotal:10,
      student: [],
      showStudent: false,
      dialogFormVisible: false,
      studentId:'',
      classForm: {
        grade:'',
        className:'',
        headTeacherName:'',
        introductionToClass:'',
        classId:'',
      },
      formLabelWidth: '120px',

      // 第一部分的数据
      // searchObj: {}, // 条件搜索对象
      currentPage: 1, //当前页
      page: 1, //分页组件页码初始化
      tableData:[],
      total: 20,
      // 年级选择框数据
      gradeList: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      // 教师身份数组
      identityList: ['班主任', '语文老师', '数学老师', '英语老师', '美术老师', '体育老师'],
      selectedGrade: null,
      // 班主任选择框数据
      monitorList: [],
      selectedMonitor: null,
      // 班级基本信息
      classList: [],
      // 所有教师
      allTeacherList: [[], [], [], [], [], []],
      // 班级教师信息
      teacherList: [],
      // 学生信息
      studentList: [],
      searchObj: {}, // 条件搜索对象
      // 右侧弹出框标题
      classTitle: '',
      // 控制参数 -- 班级添加对话框弹出
      addFormVisible: false,
      // 控制参数 -- 右侧弹出框弹出
      drawer: false,
      // 控制参数 -- 班级添加框中班级信息与教师添加界面变换
      isTeacherSelectorShow: false,
      // 班级信息表单
      classInfoForm: {},
      // 班级负责老师列表
      selectedTeacherList: [],


      // 班级信息表单校验规则
      classFormRules: {
        gradeId: [{required: true, message: '请选择年级', trigger: 'blur'}],
        classId: [{required: true, message: '请选择班级', trigger: 'blur'}],
        teacherId: [{required: true, message: '请输入班级名', trigger: 'blur'}],
      },
      classAllFormRules: {
        introductionToClass:[
          {required: true,message:'请填写班级简介',trigger: 'blur'},
          { min: 0, max: 200, message: '不超过200字', trigger: 'blur' }
        ]
      },
    };
  },
  components: {
      Aside
    },
  created(){
    this.getAllMonitorList();
    this.getList(this.page);
    this.getAllTeacher();
  },
  methods: {

    uploadSuccess(res) {
      if(res.code==200){
        this.page=1;
        this.getList(this.page);
        this.$message.success("导入成功");
      }else{
        this.$message.error(res.message);
      }

    },
    uploadError() {
      this.$message.error("导入失败，请检查文件是否合规");
    },
    //导入文件限制
    beforeUpload(file) {
      //文件类型
      const isType = file.type === "application/vnd.ms-excel";
      const isTypeComputer = file.type === "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      const fileType = isType || isTypeComputer;
      if (!fileType) {
        this.$message.error("上传文件只能是xls/xlsx格式！");
      }

      // 文件大小限制为10M
      const fileLimit = file.size / 1024 / 1024 < 10;
      if (!fileLimit) {
        this.$message.error("上传文件大小不超过10M！");
      }
      return fileType && fileLimit;
    },
    fileChange(file, fileList) {
      console.log(file);
    },
    //查看学生详细信息
    handleClick(row) {
      this.$router.push({path: "/management/student"});
    },
    //查看班级详细信息
    clickClass(grade,classname){
      this.dialogFormVisible=true;
      getClassPartInfoByClassAndGrade(grade,classname).then(res=>{
        this.classForm.grade=res.data.grade;
        this.classForm.className=res.data.className;
        this.classForm.headTeacherName=res.data.headTeacherNAme;
        this.classForm.introductionToClass=res.data.introductionToClass;
        this.classForm.classId=res.data.classId;
      })
    },
    //提交班级详细修改
    sumbitClassInfo(formName,classinfoid,introduct){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.dialogFormVisible=false;
          updateClassByInformationId(classinfoid,introduct).then(res=>{
            if(res.code==200){
              this.$message.success("编辑成功");
            }else{
              this.$message.error("编辑失败");
            }
          })
        } else {

          return false;
        }
      });

    },
    //学生总数分页
    handleCurrentChange() {
      getStudentByPage(this.classForm.classId,this.stucurrentPage,this.pageSize).then(res=>{
        this.student=res.data;
        this.pageTotal=res.data[0].totalNum;
      })
    },
    //查看班级所有学生
    clickAllStudent(){
      this.stucurrentPage=1;
      this.showStudent=true;
      this.handleCurrentChange();
    },
    //分页页面数量
    handleSizeChange(val){
      this.pageSize=val;
      this.handleCurrentChange();
    },
    //删除学生信息
    deletecurrStudent(studentId){
      console.log(studentId)
      deleteStudent(studentId).then(res=>{
        if(res.code==200){
          this.$message.success("删除成功");
          this.handleCurrentChange();
        }else{
          this.$message.error("删除失败");
        }
      })
    },


    // 第一部分
    getAllMonitorList(){
      getMonitorList().then(res => {
        this.monitorList = res.data;
      })
    },
    getAllTeacher(){
      getAllTeacherList().then(res => {
        this.teacherList = res.data;
      })
    },
    getList(page,grade = '',name = ''){
      this.currentPage = page;
      const payload = {
        pageSize: 10,
        pageNum: this.currentPage,
        grade: grade,
        realName: this.monitorList[name],
      }
      getGradeList(payload).then(res => {
        console.log(res)
        this.total = res.data.total;
        this.classList = res.data.list;
      })
    },
    getCurList(pageval){
      this.getList(pageval);
    },
    addClassInfo(){
      this.addFormVisible = true;
      this.getAllTeacher();
      this.classInfoForm={};
    },
    handleCancel(){
      this.dialogFormVisible = false;
      this.addFormVisible = false;
      this.isTeacherSelectorShow = false;
      this.classInfoForm = {};
    },
    handleClassSure(classInfoForm){
      this.$refs[classInfoForm].validate((valid) => {
        if (valid) {
          const classInfo = {
            classId: this.classInfoForm.classId,
            gradeId: this.classInfoForm.gradeId,
            teacherName: this.teacherList[this.classInfoForm.teacherId],
          }
          addClass(classInfo).then(res => {
            if (res.code==200){
              this.$message({
                message: '添加成功',
                type: 'success'
              });
              this.addFormVisible = false;
              this.getList(this.page)
            }
            if (res.code==-1){
              this.$message({
                message: '添加失败已存在指定班級',
                type: 'error'
              });
            }
          })
        } else {
          this.$message.error('请完善表单相关信息！');
          return false;
        }
      });
    },
    handleTeacherSure() {
      let selectedTeacherList = [];
      let classInfoForm = this.classInfoForm;
      this.selectedTeacherList.forEach(teacher => {
        selectedTeacherList.push({gradeId: this.classInfoForm.gradeId, classId: this.classInfoForm.classId, teacherId: teacher});
      })
      addClass(classInfoForm).then(res => {
        addTeacherToClass(selectedTeacherList).then(res => {

        })
      })
      console.log(this.classInfoForm);
      console.log(selectedTeacherList);
    },

    selectTeacher() {
      console.log();
    },
    //查询该老师班级里的学生
    handleDetail(index, row){
      this.drawer = true;
      this.classTitle = this.gradeList[row.gradeId] + row.classId +`班成员信息`;
      getTeacherByClass(row.gradeId, row.classId).then(res => {
        this.teacherList = res.data
      });
      getStudentsByClass(row.gradeId, row.classId).then(res => {
        this.studentList = res.data
      });
    },

    handleSure(){
      this.form.classId = this.form.id;
      editClass(this.form).then(res => {
        this.$message({
          message: '修改成功',
          type: 'success'
        });
      })
      this.dialogFormVisible = false;
    },
    handleDelete(index, row){
      console.log(row.id)
      deleteClass1(row.id).then(res => {
        this.$message({
          message: '删除成功',
          type: 'success'
        });
      })
    },
  },
};
</script>
<style scoped>
.drawer-table {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border-top: solid 1px black;
  font-size: 20px;
  font-weight: bold;
}
</style>
<!--<template>-->
<!--  <div class="app-container">-->

<!--    &lt;!&ndash; 搜索表单 &ndash;&gt;-->
<!--    <el-form :inline="true" class="demo-form-inline">-->
<!--      <el-form-item>-->
<!--        <el-select v-model="selectedGrade" clearable placeholder="年级筛选">-->
<!--          <el-option v-for="(value, index) in gradeList"-->
<!--                     :key="index"-->
<!--                     :label="value"-->
<!--                     :value="index"/>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-select v-model="selectedMonitor" clearable placeholder="班主任">-->
<!--          <el-option v-for="(value, index) in monitorList"-->
<!--                     :key="index"-->
<!--                     :label="value"-->
<!--                     :value="index"/>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-button type="primary" icon="el-icon-search" >查询</el-button>-->
<!--      <el-button type="success" icon="el-icon-plus" @click="addClassInfo()">新增</el-button>-->
<!--    </el-form>-->

<!--    &lt;!&ndash; 展示表格 &ndash;&gt;-->
<!--    <el-table :data="classList" stripestyle="width:100%" >-->
<!--      <el-table-column type="index"-->
<!--                       width="50"-->
<!--                       label="序号"-->
<!--                       align="center"/>-->
<!--      <el-table-column label="年级"-->
<!--                       align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{gradeList[scope.row.gradeId-1]}}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="班级"-->
<!--                       align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{scope.row.classId}}班</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="班主任信息" align="center">-->
<!--        <el-table-column prop="realName"-->
<!--                         label="姓名"-->
<!--                         width="120"-->
<!--                         align="center"/>-->
<!--        <el-table-column label="性别"-->
<!--                         width="120"-->
<!--                         align="center">-->
<!--          <template slot-scope="scope">-->
<!--            <span v-if="scope.row.gender==0">女</span>-->
<!--            <span v-else>男</span>-->
<!--          </template>-->
<!--        </el-table-column>-->
<!--        <el-table-column prop="contactInfo"-->
<!--                         label="联系方式"-->
<!--                         width="300"-->
<!--                         align="center"/>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="操作" width="280" align="center">-->
<!--       <template slot-scope="scope">-->
<!--          <el-button type="primary" style="margin-left:5px" size="mini" @click="handleDetail(scope.$index, scope.row)">详情</el-button>-->
<!--          <el-button type="danger" style="margin-left:5px" size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>-->
<!--       </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->


<!--    &lt;!&ndash; 详情 &ndash;&gt;-->
<!--    <el-drawer-->
<!--      :title="classTitle"-->
<!--      :visible.sync="drawer"-->
<!--      size="35%"-->
<!--      >-->
<!--      <el-button type="info" @click="drawer = false">← 返回</el-button>-->
<!--      <div class="drawer-table">-->
<!--        <span>教师信息</span>-->
<!--        <el-table :data="teacherList">-->
<!--          <el-table-column prop="identityInfo"-->
<!--                           label="担任职位"-->
<!--                           width="200"/>-->
<!--          <el-table-column prop="realName"-->
<!--                           label="教师姓名"-->
<!--                           width="200"/>-->
<!--          <el-table-column prop="gender"-->
<!--                           label="性别"-->
<!--                           width="200">-->
<!--            <template slot-scope="scope">-->
<!--              <span v-if="scope.row.gender==0">女</span>-->
<!--              <span v-else>男</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--        </el-table>-->
<!--      </div>-->
<!--      <div class="drawer-table">-->
<!--        <span>学生信息</span>-->
<!--        <el-table :data="studentList">-->
<!--          <el-table-column prop="studentId"-->
<!--                           label="学号"-->
<!--                           width="200"/>-->
<!--          <el-table-column prop="studentName"-->
<!--                           label="姓名"-->
<!--                           width="200"/>-->
<!--          <el-table-column prop="gender"-->
<!--                           label="性别"-->
<!--                           width="200">-->
<!--            <template slot-scope="scope">-->
<!--              <span v-if="scope.row.gender==0">女</span>-->
<!--              <span v-else>男</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--        </el-table>-->
<!--      </div>-->
<!--    </el-drawer>-->

<!--    &lt;!&ndash; 新增表单 &ndash;&gt;-->
<!--    <el-dialog-->
<!--               :title="isTeacherSelectorShow?'请为该班级指定老师':'请编辑班级信息'"-->
<!--               :visible.sync="addFormVisible">-->
<!--            <el-form :model="classInfoForm"-->
<!--                     ref="classInfoRef"-->
<!--                     :rules="classFormRules"-->
<!--                     v-if="!isTeacherSelectorShow">-->
<!--                <el-form-item label="年级" prop="gradeId" label-width="120px">-->
<!--                    <el-select v-model="classInfoForm.gradeId"-->
<!--                              placeholder="选择年级"-->
<!--                              clearable>-->
<!--                      <template v-for="(item, index) in gradeList">-->
<!--                        <el-option :label="item" :value="index+1"></el-option>-->
<!--                      </template>-->
<!--                    </el-select>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="班级ID" prop="classId" label-width="120px">-->
<!--                  <el-input v-model="classInfoForm.classId"-->
<!--                            placeholder="班级ID，指明当前是第几班"-->
<!--                            type="number"/>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="班级名称" prop="className" label-width="120px">-->
<!--                    <el-input v-model="classInfoForm.className"-->
<!--                              placeholder=""/>-->
<!--                </el-form-item>-->
<!--            </el-form>-->
<!--            <div class="teacher-selector" v-if="isTeacherSelectorShow">-->
<!--              <el-button type="info"-->
<!--                         style="margin-bottom: 20px"-->
<!--                         @click="isTeacherSelectorShow = false">← 返回</el-button>-->
<!--              <el-form>-->
<!--                <template v-for="(identity, index) in identityList">-->
<!--                  <el-form-item :label="identity"  label-width="120px">-->
<!--                    <el-select v-model="selectedTeacherList[index]" placeholder="Select" size="large">-->
<!--                      <el-option-->
<!--                        v-for="item in allTeacherList[index]"-->
<!--                        :key="item.userId"-->
<!--                        :label="item.realName"-->
<!--                        :value="item.userId"-->
<!--                      />-->
<!--                    </el-select>-->
<!--                  </el-form-item>-->
<!--                </template>-->
<!--              </el-form>-->
<!--            </div>-->
<!--            <div slot="footer" class="dialog-footer">-->
<!--              <el-button @click="handleCancel">取 消</el-button>-->
<!--              <el-button v-if="!isTeacherSelectorShow" type="primary" @click="handleClassSure">确 定</el-button>-->
<!--              <el-button v-if="isTeacherSelectorShow" type="primary" @click="handleTeacherSure">提 交</el-button>-->
<!--            </div>-->
<!--        </el-dialog>-->

<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import {-->
<!--  getAllClassInfo,-->
<!--  editClass,-->
<!--  deleteClass, addTeacherToClass-->
<!--} from '@/api/managementModule/classManagement';-->
<!--import { addClass, getStudentsByClass } from '@/api/classManage';-->
<!--import { getTeacherByIdentity, getTeacherByClass, getAllTeacher} from '@/api/teacher';-->
<!--import {insertRuleInfo} from "@/api/fuRule";-->
<!--export default {-->
<!--  data(){-->
<!--    return {-->
<!--      // 年级选择框数据-->
<!--      gradeList: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],-->
<!--      // 教师身份数组-->
<!--      identityList: ['班主任', '语文老师', '数学老师', '英语老师', '美术老师', '体育老师'],-->
<!--      selectedGrade: null,-->
<!--      // 班主任选择框数据-->
<!--      monitorList: [],-->
<!--      selectedMonitor: null,-->
<!--      // 班级基本信息-->
<!--      classList: [],-->
<!--      // 所有教师-->
<!--      allTeacherList: [[], [], [], [], [], []],-->
<!--      // 班级教师信息-->
<!--      teacherList: [],-->
<!--      // 学生信息-->
<!--      studentList: [],-->
<!--      searchObj: {}, // 条件搜索对象-->
<!--      // 右侧弹出框标题-->
<!--      classTitle: '',-->
<!--      // 控制参数 &#45;&#45; 班级添加对话框弹出-->
<!--      addFormVisible: false,-->
<!--      // 控制参数 &#45;&#45; 右侧弹出框弹出-->
<!--      drawer: false,-->
<!--      // 控制参数 &#45;&#45; 班级添加框中班级信息与教师添加界面变换-->
<!--      isTeacherSelectorShow: false,-->
<!--      // 班级信息表单-->
<!--      classInfoForm: {},-->
<!--      // 班级负责老师列表-->
<!--      selectedTeacherList: [],-->

<!--      // 班级信息表单校验规则-->
<!--      classFormRules: {-->
<!--        gradeId: [{required: true, message: '请选择年级', trigger: 'blur'}],-->
<!--        classId: [{required: true, message: '请选择班级', trigger: 'blur'}],-->
<!--        className: [{required: true, message: '请输入班级名', trigger: 'blur'}],-->
<!--      },-->
<!--    }-->
<!--  },-->
<!--  mounted(){-->
<!--    getAllClassInfo().then(res => {-->
<!--      this.classList = res.data;-->
<!--    });-->
<!--    getAllTeacher().then(res => {-->
<!--      res.data.forEach(teacher => {-->
<!--        this.allTeacherList[teacher.identity-3].push(teacher);-->
<!--      });-->
<!--    });-->
<!--  },-->
<!--  methods: {-->
<!--    addClassInfo(){-->
<!--        this.addFormVisible = true;-->
<!--    },-->
<!--    handleCancel(){-->
<!--      this.dialogFormVisible = false;-->
<!--      this.addFormVisible = false;-->
<!--      this.isTeacherSelectorShow = false;-->
<!--      this.classInfoForm = {};-->
<!--    },-->
<!--    handleClassSure(){-->
<!--      this.$refs.classInfoRef.validate((valid) => {-->
<!--        if (valid) {-->
<!--          this.classInfoForm.id = parseInt(this.classInfoForm.gradeId + '' + this.classInfoForm.classId);-->
<!--          this.classInfoForm.classId = parseInt(this.classInfoForm.classId);-->
<!--          this.isTeacherSelectorShow = true;-->
<!--        } else {-->
<!--          this.$message.error('请完善表单相关信息！');-->
<!--          return false;-->
<!--        }-->
<!--      });-->

<!--      // addClass(this.addForm).then(res => {-->
<!--      //   this.$message({-->
<!--      //         message: '添加成功',-->
<!--      //         type: 'success'-->
<!--      //       });-->
<!--      //   })-->
<!--      // this.addFormVisible = false;-->
<!--    },-->
<!--    handleTeacherSure() {-->
<!--      let selectedTeacherList = [];-->
<!--      let classInfoForm = this.classInfoForm;-->
<!--      this.selectedTeacherList.forEach(teacher => {-->
<!--        selectedTeacherList.push({gradeId: this.classInfoForm.gradeId, classId: this.classInfoForm.classId, teacherId: teacher});-->
<!--      })-->
<!--      addClass(classInfoForm).then(res => {-->
<!--        addTeacherToClass(selectedTeacherList).then(res => {-->

<!--        })-->
<!--      })-->
<!--      console.log(this.classInfoForm);-->
<!--      console.log(selectedTeacherList);-->
<!--    },-->
<!--    selectTeacher() {-->
<!--      console.log();-->
<!--    },-->
<!--    //查询该老师班级里的学生-->
<!--    handleDetail(index, row){-->
<!--      this.drawer = true;-->
<!--      this.classTitle = this.gradeList[row.gradeId] + row.classId +`班成员信息`;-->
<!--      getTeacherByClass(row.gradeId, row.classId).then(res => {-->
<!--        this.teacherList = res.data-->
<!--      });-->
<!--      getStudentsByClass(row.gradeId, row.classId).then(res => {-->
<!--        this.studentList = res.data-->
<!--      });-->
<!--    },-->

<!--    handleSure(){-->
<!--      this.form.classId = this.form.id;-->
<!--      editClass(this.form).then(res => {-->
<!--        this.$message({-->
<!--            message: '修改成功',-->
<!--            type: 'success'-->
<!--          });-->
<!--      })-->
<!--      this.dialogFormVisible = false;-->
<!--    },-->
<!--    handleDelete(index, row){-->
<!--      deleteClass({classId: row.id}).then(res => {-->
<!--        this.$message({-->
<!--            message: '删除成功',-->
<!--            type: 'success'-->
<!--          });-->
<!--      })-->
<!--    },-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.drawer-table {-->
<!--  margin-top: 20px;-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  align-items: center;-->
<!--  justify-content: center;-->
<!--  padding: 10px;-->
<!--  border-top: solid 1px black;-->
<!--  font-size: 20px;-->
<!--  font-weight: bold;-->
<!--}-->
<!--</style>-->
