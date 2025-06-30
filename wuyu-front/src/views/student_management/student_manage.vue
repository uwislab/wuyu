<template>
  <div class="web-user">
    <!-- 学生管理表格 -->
    <el-card class="box-card">
      <h2 class="page-title">学生管理</h2>

      <!-- 搜索框和按钮 -->
      <el-row class="search-box" gutter="20" type="flex" justify="start" align="middle">
        <el-col :span="4">
          <el-input v-model="searchQuery.studentNum" placeholder="学号" size="medium" />
        </el-col>
        <el-col :span="4">
          <el-input v-model="searchQuery.studentName" placeholder="学生姓名" size="medium" />
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.gender" placeholder="性别" size="medium" @change="searchUsers">
            <el-option label="男" value="1" />
            <el-option label="女" value="0" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.className" placeholder="班级" size="medium" @change="searchUsers">
            <el-option v-for="classItem in classInfo" :key="classItem" :label="classItem" :value="classItem" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.gradeId" placeholder="年级" size="medium" @change="searchUsers">
            <el-option v-for="grade in gradeInfo" :key="grade" :label="grade" :value="grade" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.schoolId" placeholder="学校" size="medium" @change="searchUsers">
            <el-option v-for="school in schoolInfo" :key="school.id" :label="school.schoolName" :value="school.id" />
          </el-select>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" @click="searchUsers">搜索</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button @click="resetSearch">重置</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" @click="openAddUserDialog">添加学生</el-button>
        </el-col>
      </el-row>
      
      <!-- 导入excel -->
      <el-dialog
        title="导入excel"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <span style="display: flex; align-items: center; flex-direction: column;">
         <el-upload
          class="upload-demo"
          drag action=""
          :auto-upload="false"
          :on-change="handleFileChange"
          limit="1"
          accept=".xlsx,.xls"
          >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传xlsx/xls文件</div>
        </el-upload>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelExcel">取 消</el-button>
          <el-button type="primary" @click="handleUpload">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 用户列表 -->
      <el-table :data="users.data" border stripe>
        <el-table-column label="学号" prop="studentNum" />
        <el-table-column label="学生姓名" prop="studentName" />
        <el-table-column label="性别" :formatter="formatGender" />
        <el-table-column label="班级" prop="className" />
        <el-table-column label="年级" prop="gradeId"/>
        <el-table-column label="学校" prop="schoolName" />
        <el-table-column label="家长电话" prop="parentPhoneNum"/>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="small" @click="editUser(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="confirmDeleteUser(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页 -->
    <div style="display: flex; justify-content: space-between; padding: 20px;">
      <el-pagination class="pagination" :current-page="users.page" :page-size="users.sizeOfPage" :total="users.totalNum"
      :page-sizes="[5, 10, 20, 50]" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange"
      @current-change="handlePageChange" />
      <div style="margin: 20px;">
        <el-button type="success" @click="exportExcel">导出Excel</el-button>
        <el-button type="primary" @click="dialogVisible = true">导入Excel</el-button>
      </div>
    </div>

    <!-- 统计卡片 -->
    <div class="statistic-cards">
      <el-card class="stat-card">
        <template #header><div class="card-header">学生总数</div></template>
        <div class="card-content"><span class="count">{{ totalStudents }}</span></div>
      </el-card>
      <el-card class="stat-card">
        <template #header><div class="card-header">男生人数</div></template>
        <div class="card-content"><span class="count">{{ statisticData.genderRatio?.maleCount || 0 }}</span></div>
      </el-card>
      <el-card class="stat-card">
        <template #header><div class="card-header">女生人数</div></template>
        <div class="card-content"><span class="count">{{ statisticData.genderRatio?.femaleCount || 0 }}</span></div>
      </el-card>
    </div>

    <!-- 统计图表区域 -->
    <div class="chart-container">
      <div class="chart-box">
        <h3 class="chart-title">性别分布</h3>
        <div id="genderPie" class="chart"></div>
      </div>
      <div class="chart-box">
        <h3 class="chart-title">年级分布</h3>
        <div id="gradePie" class="chart"></div>
      </div>
      <div class="chart-box">
        <h3 class="chart-title">学校分布</h3>
        <div id="schoolPie" class="chart"></div>
      </div>
    </div>

    <!-- 添加/编辑用户弹窗 -->
    <el-dialog :visible.sync="showAddUserDialog" width="50%" @close="closeAddUserDialog">
      <h3>{{ editingUser ? '编辑学生' : '添加学生' }}</h3>
      <el-form :model="newUser" ref="form" label-width="100px" :rules="rules">
        <el-form-item label="学号" prop="studentNum">
          <el-input v-model="newUser.studentNum" />
        </el-form-item>
        <el-form-item  label="姓名" prop="studentName">
          <el-input v-model="newUser.studentName" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="newUser.gender">
            <el-option v-for="option in genderOptions" :key="option.value" :label="option.label"
              :value="option.value" />
          </el-select>
        </el-form-item>
        <el-form-item  label="家长手机号" prop="parentPhoneNum">
          <el-input v-model="newUser.parentPhoneNum"/>
        </el-form-item>
        <el-form-item label="年级">
          <el-select v-model="newUser.gradeId" placeholder="选择年级" @change="onGradeChange">
            <el-option v-for="grade in gradeInfo" :key="grade" :label="grade" :value="grade" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="newUser.className" placeholder="选择班级">
            <el-option v-for="className in classInfo" :key="className" :label="className" :value="className" />
          </el-select>
        </el-form-item>
        <el-form-item label="学校">
          <el-select v-model="newUser.schoolId" placeholder="选择学校">
            <el-option v-for="schoolItem in schoolInfo" :key="schoolItem.schoolName" :label="schoolItem.schoolName" :value="schoolItem.id" />
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="closeAddUserDialog">取消</el-button>
        <el-button type="primary" @click="validateAndSaveUser">保存</el-button>
      </div>
    </el-dialog>

    <!-- 导入进度弹窗 -->
    <el-dialog :visible.sync="importDialogVisible" title="Excel导入" width="30%">
      <el-progress :percentage="importProgress" status="success"></el-progress>
      <div slot="footer" class="dialog-footer">
        <el-button @click="importDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import XLSX from "xlsx";
import { saveAs } from "file-saver";
import * as echarts from "echarts";
import { showLoading, closeLoading } from '@/utils/loading'

export default {
  data() {
    return {
      // 请求地址
      baseUrl: "http://localhost:9080",
      
      // 统计数据
      statisticData: {
        genderRatio: { maleCount: 0, femaleCount: 0 }, // 性别比例
        gradeRatio: {}, // 年级比例 {gradeId: count}
        schoolRatio: {} // 学校比例 {schoolId: count}
      },
      
      // 图表实例
      genderChart: null,
      gradeChart: null,
      schoolChart: null,
      
      // 弹窗相关
      dialogVisible: false,
      uploadFile: null,
      
      // 学生信息
      newUser: {
        id: "",
        studentNum: "",
        studentName: "",
        className: "",
        gradeId: "",
        parentPhoneNum: "",
        gender: 1,
        schoolId: "",
        deleted: 0,
        isreview: 0,
        isenter: 0
      },
      
      // 表单验证规则
      rules: {
        studentNum: [
          { required: true, message: "学号不能为空", trigger: "blur" },
          { pattern: /^\d{10}$/, message: "学号必须为10个字符", trigger: "blur" },
        ],
        studentName: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
          { pattern: /^.{1,10}$/, message: "姓名不能超过10个字符", trigger: "blur" },
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "blur" },
        ],
        classId: [
          { required: true, message: "班级不能为空", trigger: "blur" },
        ],
        gradeId: [
          { required: true, message: "年级不能为空", trigger: "blur" },
        ],
        parentPhoneNum: [
          { required: true, message: "手机号不能为空", trigger: "blur" },
          { pattern: /^\d{11}$/, message: "手机号必须是11位数字", trigger: "blur" },
        ],
      },
      
      // 编辑状态
      editingUser: false,
      editingUserId: null,
      
      // 搜索查询条件
      searchQuery: {
        studentNum: "",
        studentName: "",
        parentPhoneNum: "",
        gender: null,
        gradeId: null,
        classId: null,
        className: "",
        schoolId: null,
        sizeOfPage: 15,
        page: 1
      },
      
      // 学生列表数据
      users: {
        data: [],
        sizeOfPage: 10,
        page: 1,
        totalNum: 0,
        isLast: false
      },
      
      // 总学生数
      totalStudents: 0,
      
      // 下拉选项数据
      genderOptions: [
        { label: "男", value: 1 },
        { label: "女", value: 0 }
      ],
      identityOptions: [
        { label: "学生", value: 1 },
        { label: "教师", value: 2 },
        { label: "管理员", value: 3 },
      ],
      schoolInfo: [],
      gradeInfo: [],
      classInfo: [],
      
      // Excel导入相关
      importDialogVisible: false,
      importProgress: 0,
      exportLoading: false,
      excelColumns: [
        { field: "studentNum", title: "学号" },
        { field: "studentName", title: "姓名" },
        { field: "gender", title: "性别", dict: { 1: "男", 0: "女" } },
        { field: "schoolId", title: "学校ID" },
        { field: "gradeId", title: "年级ID" },
        { field: "classId", title: "班级ID" },
        { field: "parentPhoneNum", title: "家长电话" }
      ]
    };
  },
  
  methods: {
    // 处理文件上传
    handleFileChange(file) {
      this.uploadFile = file.raw;
    },
    
    // 导出Excel
    exportExcel() {
      const form = document.createElement('form');
      form.action = `${this.baseUrl}/studentExcel/export`;
      form.method = 'GET';
      form.style.display = 'none';
      
      document.body.appendChild(form);
      form.submit();
      document.body.removeChild(form);
    },
    
    // 导入Excel
    async handleUpload() {
      if (!this.uploadFile) {
        this.$message.warning('请先选择文件');
        return;
      }
      
      const formData = new FormData();
      formData.append('file', this.uploadFile);
      
      try {
        showLoading('正在上传，请稍候...');
        const res = await axios.post(`${this.baseUrl}/studentExcel/import`, formData);
        
        if (res.data.code === 200) {
          this.$message.success('上传成功');
          this.dialogVisible = false;
          this.uploadFile = null;
          this.fetchStudentManager(); // 刷新数据
        } else {
          this.$message.error('上传失败' + res.data.message);
        }
      } catch (error) {
        this.$message.error('上传失败：' + error.message);
      } finally {
        closeLoading();
      }
    },
    
    // 取消导入
    cancelExcel() {
      this.uploadFile = null;
      this.dialogVisible = false;
    },
    
    // 关闭弹窗确认
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    
    // 表单验证并保存
    validateAndSaveUser() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.editingUser) {
            this.updateUser();
          } else {
            this.addUser();
          }
        } else {
          console.log('表单验证失败');
          return false;
        }
      });
    },
    
    // 添加学生
    addUser() {
      axios.post(`${this.baseUrl}/StudentManager/addStudent`, this.newUser)
        .then(response => {
          if (response.data.code === 200) {
            this.$message.success('添加学生成功');
            this.showAddUserDialog = false;
            this.searchUsers();
          } else {
            this.$message.error('添加学生失败: ' + response.data.message);
          }
        })
        .catch(error => {
          console.error('添加学生时发生错误:', error);
          this.$message.error('添加学生时发生错误');
        });
    },
    
    // 更新学生
    updateUser() {
      axios.post(`${this.baseUrl}/StudentManager/updateStudent`, this.newUser)
        .then(response => {
          if (response.data.code === 200) {
            this.$message.success('编辑学生成功');
            this.showAddUserDialog = false;
            this.searchUsers();
          } else {
            this.$message.error('编辑学生失败: ' + response.data.message);
          }
        })
        .catch(error => {
          console.error('编辑学生时发生错误:', error);
          this.$message.error('编辑学生时发生错误');
        });
    },
    
    // 获取学校列表
    fetchSchoolInfo() {
      axios.get(`${this.baseUrl}/StudentManager/getSchool`)
        .then(response => {
          this.schoolInfo = response.data.data;
        })
        .catch(error => {
          console.error("获取学校列表失败：", error);
        });
    },
    
    // 获取班级列表
    fetchClassInfo() {
      axios.get(`${this.baseUrl}/StudentManager/getClassName`)
        .then(response => {
          this.classInfo = response.data.data;
        })
        .catch(error => {
          console.error("获取班级列表失败：", error);
        });
    },
    
    // 获取年级列表
    fetchGradeInfo() {
      axios.get(`${this.baseUrl}/StudentManager/getGrade`)
        .then(response => {
          this.gradeInfo = response.data.data;
        })
        .catch(error => {
          console.error("获取年级列表失败：", error);
        });
    },
    
    // 学校变更事件
    onSchoolChange(schoolId) {
      if (schoolId) {
        this.fetchGrades(schoolId);
      } else {
        this.grades = [];
        this.classes = [];
        this.newUser.gradeId = "";
        this.newUser.classId = "";
      }
    },
    
    // 年级变更事件
    onGradeChange(gradeId) {
      if (gradeId) {
        this.fetchClasses(gradeId);
      } else {
        this.classes = [];
        this.newUser.classId = "";
      }
    },
    
    // 获取学生列表
    fetchStudentManager() {
      axios.post(`${this.baseUrl}/StudentManager/getStudent`, {
        page: this.users.page,
        sizeOfPage: this.users.sizeOfPage,
        studentNum: this.searchQuery.studentNum,
        studentName: this.searchQuery.studentName,
        gender: this.searchQuery.gender,
        className: this.searchQuery.className,
        gradeId: this.searchQuery.gradeId,
        schoolId: this.searchQuery.schoolId,
      })
      .then(response => {
        this.users = response.data.data;
      })
      .catch(error => {
        console.error("获取用户列表出错：", error);
      });
    },
    
    // 1. 初始化加载全局统计数据
    async fetchGlobalStatistics() {
      try {
        showLoading('加载统计数据中...');
        const res = await axios.get(`${this.baseUrl}/api/statistics/student`);
        if (res.data) {
          this.statisticData = res.data;
          this.calcTotalStudents(); // 计算总人数
          this.renderCharts(); // 渲染图表
        }
      } catch (err) {
        console.error("全局统计数据加载失败:", err);
        this.$message.error("统计数据加载失败，请刷新重试");
      } finally {
        closeLoading();
      }
    },
    
    // 2. 条件筛选时加载统计数据
// 条件筛选时加载统计数据（修正版）
async fetchFilteredStatistics() {
  try {
    // 1. 构造查询参数（与全局搜索条件一致）
    const query = { ...this.searchQuery };
    delete query.sizeOfPage; // 移除分页参数
    delete query.page;

    // 2. 修正接口路径（确保与后端一致）
    // 注意：如果后端条件统计接口也用GET，这里改为get并通过params传参
    const res = await axios.post(
      `${this.baseUrl}/api/statistics/student/filtered`, // 与全局接口路径保持层级一致
      query // POST请求参数放在请求体
    );

    // 3. 处理响应数据
    if (res.data) {
      this.statisticData = res.data;
      this.calcTotalStudents(); // 重新计算总人数
      this.renderCharts(); // 刷新图表
    }
  } catch (err) {
    console.error("条件统计数据加载失败:", err);
    this.$message.error("筛选统计数据加载失败，请检查接口是否存在");
  } finally {
    closeLoading();
  }
},
    
    // 计算总学生数（男+女）
    calcTotalStudents() {
      const { maleCount = 0, femaleCount = 0 } = this.statisticData.genderRatio || {};
      this.totalStudents = maleCount + femaleCount;
    },
    
    // 3. 搜索方法改造：同时加载列表和筛选统计
    async searchUsers() {
      this.users.page = 1;
      // 先加载学生列表（原有逻辑）
      await this.fetchStudentManager();
      // 再加载筛选后的统计数据
      await this.fetchFilteredStatistics();
    },
    
    // 4. 重置搜索：恢复全局统计
    resetSearch() {
      this.searchQuery = {
        studentNum: "",
        studentName: "",
        gender: null,
        className: "",
        gradeId: null,
        schoolId: null,
        sizeOfPage: 15,
        page: 1
      };
      // 重置后加载列表和全局统计
      this.fetchStudentManager();
      this.fetchGlobalStatistics();
    },
    
    // 5. 图表渲染方法改造（适配新数据结构）
    renderCharts() {
      this.renderGenderChart();
      this.renderGradeChart();
      this.renderSchoolChart();
    },
    
    // 渲染性别分布图表
    renderGenderChart() {
      const chartDom = document.getElementById("genderPie");
      if (!chartDom) return;
      this.genderChart = echarts.init(chartDom);
      
      const { maleCount = 0, femaleCount = 0 } = this.statisticData.genderRatio || {};
      const option = {
        title: { text: "性别分布", left: "center" },
        tooltip: { trigger: "item" },
        series: [{
          name: "性别",
          type: "pie",
          radius: ["40%", "70%"],
          data: [
            { value: maleCount, name: "男" },
            { value: femaleCount, name: "女" }
          ]
        }]
      };
      this.genderChart.setOption(option);
      window.addEventListener("resize", () => this.genderChart.resize());
    },
    
  // 渲染年级分布图表
renderGradeChart() {
  const chartDom = document.getElementById("gradePie");
  if (!chartDom) return;
  this.gradeChart = echarts.init(chartDom);
  
  const gradeRatio = this.statisticData.gradeRatio || [];
  
  // 将数组转换为ECharts需要的格式
  const gradeData = gradeRatio.map(item => ({
    name: `年级${item.gradeId}`, // 直接使用年级ID
    value: Number(item.count) || 0 // 将字符串转换为数字
  }));
  
  const option = {
    title: { text: "年级分布", left: "center" },
    tooltip: { 
      trigger: "item",
      formatter: "{b}: {c}人 ({d}%)" // 自定义提示格式
    },
    series: [{
      name: "年级",
      type: "pie",
      radius: ["40%", "70%"],
      data: gradeData.length > 0 ? gradeData : [
        { value: 1, name: "暂无数据", itemStyle: { color: '#eee' } }
      ],
      label: {
        formatter: "{b}: {d}%" // 显示百分比
      }
    }]
  };
  
  this.gradeChart.setOption(option);
  window.addEventListener("resize", () => this.gradeChart.resize());
},

// 渲染学校分布图表
renderSchoolChart() {
  const chartDom = document.getElementById("schoolPie");
  if (!chartDom) return;
  this.schoolChart = echarts.init(chartDom);
  
  const schoolRatio = this.statisticData.schoolRatio || [];
  
  // 将schoolId映射为学校名称
  const schoolMap = {};
  this.schoolInfo.forEach(school => {
    schoolMap[school.id] = school.schoolName;
  });
  
  // 将数组转换为ECharts需要的格式
  const schoolData = schoolRatio.map(item => ({
    name: schoolMap[item.schoolId] || `学校${item.schoolId}`, // 优先使用学校名称
    value: Number(item.count) || 0 // 将字符串转换为数字
  }));
  
  const option = {
    title: { text: "学校分布", left: "center" },
    tooltip: { 
      trigger: "item",
      formatter: "{b}: {c}人 ({d}%)" 
    },
    series: [{
      name: "学校",
      type: "pie",
      radius: ["40%", "70%"],
      data: schoolData.length > 0 ? schoolData : [
        { value: 1, name: "暂无数据", itemStyle: { color: '#eee' } }
      ],
      label: {
        formatter: "{b}: {d}%"
      }
    }]
  };
  
  this.schoolChart.setOption(option);
  window.addEventListener("resize", () => this.schoolChart.resize());
},
    
    // 删除学生
    async confirmDeleteUser(id) {
      try {
        await this.$confirm(
          "此操作将删除该学生信息, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        );
        
        await this.removeStudent(id);
        this.$message.success("学生删除成功");
        this.fetchStudentManager();
      } catch (error) {
        if (error !== "cancel") {
          this.$message.error("删除学生失败，请稍后重试");
          console.error("删除学生失败:", error);
        }
      }
    },
    
    // 删除学生API调用
    removeStudent(studentId) {
      return axios.get(`${this.baseUrl}/StudentManager/removeStudent`, {
        params: {
          id: studentId
        }
      });
    },
    
    // 格式化性别显示
    formatGender(row, column) {
      return row.gender === 1 ? "男" : "女";
    },
    
    // 分页处理
    handleSizeChange(size) {
      this.users.sizeOfPage = size;
      this.users.page = 1;
      this.fetchStudentManager();
    },
    
    handlePageChange(page) {
      this.users.page = page;
      this.fetchStudentManager();
    },
    
    // 编辑学生
    editUser(student) {
      this.editingUser = true;
      this.editingUserId = student.id;
      
      this.newUser = { ...student };
      
      if (this.newUser.schoolId) {
        this.fetchGrades(this.newUser.schoolId);
      }
      
      if (this.newUser.gradeId) {
        this.fetchClasses(this.newUser.gradeId);
      }
      
      this.showAddUserDialog = true;
    },
    
    // 关闭弹窗
    closeAddUserDialog() {
      this.showAddUserDialog = false;
      this.editingUser = false;
      this.editingUserId = null;
      this.newUser = {
        id: null,
        studentNum: "",
        studentName: "",
        parentPhoneNum: "",
        gender: 1,
        schoolId: "",
        gradeId: "",
        classId: "",
        isreview: 0,
        isenter: 0
      };
      this.grades = [];
      this.classes = [];
    },
    
    // 打开添加学生弹窗
    openAddUserDialog() {
      this.editingUser = false;
      this.newUser = {
        studentNum: "",
        studentName: "",
        gender: 1,
        className: "",
        gradeId: "",
        parentPhoneNum: "",
        schoolId: "",
      };
      
      if (this.classInfo.length) {
        this.newUser.className = this.classInfo[0];
      }
      
      if (this.gradeInfo.length) {
        this.newUser.gradeId = this.gradeInfo[0];
      }
      
      if (this.schoolInfo.length) {
        this.newUser.schoolId = this.schoolInfo[0].id;
      }
      
      this.showAddUserDialog = true;
    },
    
    // 窗口大小变化时重绘图表
    resizeCharts() {
      this.genderChart?.resize();
      this.gradeChart?.resize();
      this.schoolChart?.resize();
    }
  },
  
  mounted() {
    // 页面初始化：加载学生列表 + 全局统计数据
    this.fetchStudentManager();
    this.fetchGlobalStatistics();
    // 加载学校/年级/班级列表
    this.fetchSchoolInfo();
    this.fetchClassInfo();
    this.fetchGradeInfo();
    
    // 监听窗口大小变化
    window.addEventListener("resize", this.resizeCharts);
  },
  
  beforeDestroy() {
    // 移除事件监听
    window.removeEventListener("resize", this.resizeCharts);
    
    // 销毁图表实例
    this.genderChart?.dispose();
    this.gradeChart?.dispose();
    this.schoolChart?.dispose();
  }
};
</script>

<style scoped>
.page-title {
  font-size: 18px;
  margin-bottom: 20px;
}

.search-box {
  margin-bottom: 20px;
}

.statistic-cards {
  display: flex;
  gap: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.stat-card {
  flex: 1;
  text-align: center;
  padding: 20px;
}

.card-header {
  font-size: 16px;
  color: #666;
}

.card-content {
  margin-top: 10px;
}

.count {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.chart-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-bottom: 20px;
}

.chart-box {
  flex: 1;
  min-width: 300px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.chart-title {
  font-size: 18px;
  margin-bottom: 15px;
  text-align: center;
}

.chart {
  width: 100%;
  height: 300px;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}
</style>
