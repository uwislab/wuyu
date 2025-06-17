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
          <el-select v-model="searchQuery.gender" placeholder="性别" size="medium">
            <el-option label="男" value="1" />
            <el-option label="女" value="0" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.className" placeholder="班级" size="medium">
            <el-option v-for="classItem in classInfo" :key="classItem" :label="classItem" :value="classItem" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.gradeId" placeholder="年级" size="medium">
            <el-option v-for="grade in gradeInfo" :key="grade" :label="grade" :value="grade" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.schoolId" placeholder="学校" size="medium">
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

      <!-- 用户列表 - 直接渲染原始数字数据 -->
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
        <template #header>
          <div class="card-header">学生总数</div>
        </template>
        <div class="card-content">
          <span class="count">{{ totalStudents }}</span>
        </div>
      </el-card>
      <el-card class="stat-card">
        <template #header>
          <div class="card-header">男生人数</div>
        </template>
        <div class="card-content">
          <span class="count">{{ statisticData.genderRatio?.maleCount || 0 }}</span>
        </div>
      </el-card>
      <el-card class="stat-card">
        <template #header>
          <div class="card-header">女生人数</div>
        </template>
        <div class="card-content">
          <span class="count">{{ statisticData.genderRatio?.femaleCount || 0 }}</span>
        </div>
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
            <el-option v-for="className in classInfo" :key="className" :label="cclassName" :value="className" />
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
import { baseUrl } from "@/api/baseapi"; // 引入 baseUrl
import XLSX from "xlsx";
import { saveAs } from "file-saver";
import * as echarts from "echarts";

export default {
  data() {
    return {
      // 统计数据
      statisticData: {},
      // 图表实例
      genderChart: null,
      gradeChart: null,
      schoolChart: null,
      dialogVisible:false,
      uploadFile: null,
      // 学生信息
      newUser: {
        studentNum: "",
        studentName: "",
        className: "",
        gradeId:"",
        parentPhoneNum: "",
        gender: 1,
        schoolId: "",
        deleted: 0,
        isreview: 0,
        isenter: 0
      },
      file: {},
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
      editingUser: false, // 是否为编辑模式
      editingUserId: null, // 编辑的用户ID
      searchQuery: {
        studentNum: "",
        studentName: "",
        parentPhoneNum: "",
        gender: "",
        gradeId: "",
        classId: "",
        className: "",
        schoolId: "",
        sizeOfPage: 15,
        page: 1
      },
      users: {
        data: [],
        sizeOfPage: 10,
        page: 1,
        totalNum: 0,
        isLast: false
      },
      totalStudents: 0, // 学生总数
      showAddUserDialog: false, // 弹窗显示状态
      schools: [], // 存储学校列表
      grades: [], // 存储年级列表
      classes: [], // 存储班级列表
      // 性别选项
      genderOptions: [
        { label: "男", value: 1 },
        { label: "女", value: 0 }
      ],
      identityOptions: [
        { label: "学生", value: 1 },
        { label: "教师", value: 2 },
        { label: "管理员", value: 3 },
      ],
      schoolInfro: [],
      gradeInfo: [],
      classInfo: [],
      // Excel导入相关
      importDialogVisible: false,  // 导入进度弹窗
      importProgress: 0,           // 导入进度
      exportLoading: false,        // 导出加载状态
      // Excel导入模板列定义
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
    handleFileChange(file) {
      this.uploadFile = file.raw
    },
    // 导出excel
    exportExcel() {
      // 创建表单
      const form = document.createElement('form');
      form.action = `${baseUrl}/studentExcel/export`;
      form.method = 'GET';
      form.style.display = 'none';
      
      // 添加到文档并提交
      document.body.appendChild(form);
      form.submit();
      
      // 清理
      document.body.removeChild(form);
    },
    // 导入excel
    async handleUpload() {
      console.log('handleUpload');
      if (!this.uploadFile) {
        this.$message.warning('请先选择文件')
        return
      }
      const formData = new FormData()
      formData.append('file', this.uploadFile)
      try {
        const res = await axios.post(`${baseUrl}/studentExcel/import`, formData)
        console.log(res);
        if (res.data.code ===200) {
          this.$message.success('上传成功')
          this.dialogVisible = false
          this.uploadFile = null
          this.fetchStudentManager() // 刷新数据
        } else {
          this.$message.error('上传失败' + res.data.message)
        }
      } catch (error) {
        this.$message.error('上传失败：' + (error.message || '未知错误'))
      }
    },
    
    cancelExcel() {
      this.uploadFile = null
      this.dialogVisible = false
    },

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
              // 编辑用户逻辑
              this.updateUser();
            } else {
              // 添加用户逻辑
              this.addUser();
            }
          } else {
            console.log('表单验证失败');
            return false;
          }
        });
      },
      
      addUser() {
        console.log('请求地址:', baseUrl);
        axios.post(`${baseUrl}/StudentManager/addStudent`, this.newUser)
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

      updateUser() {
        axios.post(`${baseUrl}/StudentManager/updateStudent`, this.newUser)
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
    fetchSchools() {
      axios
        .get(`${baseUrl}/StudentManager/getSchool`)
        .then((response) => {
          this.schools = response.data;
        })
        .catch(error => {
          console.error("获取学校列表失败：", error);
        });
    },

    // 获取年级列表
    fetchGrades(schoolId) {
      axios
        .get(`${baseUrl}/webUser/grades?schoolId=${schoolId}`)
        .then(response => {
          this.grades = response.data;
          // 重置年级选择后，清空班级选择
          this.classes = [];
          this.newUser.classId = "";
        })
        .catch(error => {
          console.error("获取年级列表失败：", error);
        });
    },

    // 获取班级列表
    fetchClasses(gradeId) {
      axios
        .get(`${baseUrl}/studentManager/addStudent`)
        .then((response) => {
          this.allUsers = response.data; // 假设接口返回的数据为用户列表
        })
        .catch(error => {
          console.error("获取班级列表失败：", error);
        });
    },
    // exportExcel() {
    //   // 创建表单
    //   const form = document.createElement('form');
    //   form.action = `${baseUrl}/studentExcel/export`;
    //   form.method = 'GET';
    //   form.style.display = 'none';
      
    //   // 添加到文档并提交
    //   document.body.appendChild(form);
    //   form.submit();
      
    //   // 清理
    //   document.body.removeChild(form);
    // },

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
      axios
        .post(`${baseUrl}/StudentManager/getStudent`, {
          page: this.users.page,
            sizeOfPage: this.users.sizeOfPage,
            studentNum:this.searchQuery.studentNum,
            studentName:this.searchQuery.studentName,
            gender:this.searchQuery.gender,
            className:this.searchQuery.className,
            gradeId:this.searchQuery.gradeId,
            schoolId:this.searchQuery.schoolId,
        })
        .then(response => {
          this.users = response.data.data;
          
        })
        .catch(error => {
          console.error("获取用户列表出错：", error);
        });
    },

    // 获取统计数据
    fetchStatisticData() {
      axios
        .get(`${baseUrl}/api/statistics/student`)
        .then(res => {
          this.statisticData = res.data;
          this.totalStudents = 
            (this.statisticData.genderRatio?.maleCount || 0) + 
            (this.statisticData.genderRatio?.femaleCount || 0);
          this.renderCharts();
        })
        .catch(err => {
          console.error("获取统计数据失败:", err);
          this.$message.error("获取统计数据失败，请稍后重试");
        });
    },

    // 渲染所有图表
    renderCharts() {
      this.renderGenderChart();
      this.renderGradeChart();
      this.renderSchoolChart();
    },

    // 渲染性别图表
    renderGenderChart() {
      const chartDom = document.getElementById("genderPie");
      if (!chartDom) return;
      
      this.genderChart = echarts.init(chartDom);
      
      const { genderRatio } = this.statisticData;
      if (!genderRatio) return;
      
      const data = [
        { value: genderRatio.maleCount, name: "男" },
        { value: genderRatio.femaleCount, name: "女" }
      ];
      
      const option = {
        title: {
          text: "学生性别分布",
          left: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name: "性别",
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              formatter: "{b}: {d}%"
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "15",
                fontWeight: "bold"
              }
            },
            labelLine: {
              show: true
            },
            data: data
          }
        ]
      };
      
      this.genderChart.setOption(option);
      window.addEventListener("resize", () => {
        this.genderChart.resize();
      });
    },

    // 渲染年级图表
    renderGradeChart() {
      const chartDom = document.getElementById("gradePie");
      if (!chartDom) return;
      
      this.gradeChart = echarts.init(chartDom);
      
      const { gradeRatio } = this.statisticData;
      if (!gradeRatio) return;
      
      const data = Object.entries(gradeRatio).map(([gradeId, count]) => ({
        value: count,
        name: `年级${gradeId}`
      }));
      
      const option = {
        title: {
          text: "学生年级分布",
          left: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name: "年级",
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              formatter: "{b}: {d}%"
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "15",
                fontWeight: "bold"
              }
            },
            labelLine: {
              show: true
            },
            data: data
          }
        ]
      };
      
      this.gradeChart.setOption(option);
      window.addEventListener("resize", () => {
        this.gradeChart.resize();
      });
    },

    // 渲染学校图表
    renderSchoolChart() {
      const chartDom = document.getElementById("schoolPie");
      if (!chartDom) return;
      
      this.schoolChart = echarts.init(chartDom);
      
      const { schoolRatio } = this.statisticData;
      if (!schoolRatio) return;
      
      const data = Object.entries(schoolRatio).map(([schoolId, count]) => ({
        value: count,
        name: `学校${schoolId}`
      }));
      
      const option = {
        title: {
          text: "学生学校分布",
          left: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name: "学校",
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              formatter: "{b}: {d}%"
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "15",
                fontWeight: "bold"
              }
            },
            labelLine: {
              show: true
            },
            data: data
          }
        ]
      };
      
      this.schoolChart.setOption(option);
      window.addEventListener("resize", () => {
        this.schoolChart.resize();
      });
    },

    
    // 删除学生
    async confirmDeleteUser(id) {
    try {
      // 修改提示信息，与逻辑删除保持一致
      await this.$confirm(
        "此操作将删除该学生信息, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",

        }
      );
    
      // 修改API调用，与后端保持一致
      await this.removeStudent(id);
      
      // 删除成功，显示成功消息
      this.$message.success("学生删除成功");
      
      // 刷新学生列表
      this.fetchStudentManager();
    } catch (error) {
      // 用户取消删除或删除操作失败
      if (error !== "cancel") {
        // 非用户取消的情况，显示错误消息
        this.$message.error("删除学生失败，请稍后重试");
        console.error("删除学生失败:", error);
      }
    }
  },

  // 新增或修改API调用方法，确保与后端接口匹配
    removeStudent(studentId) {
      console.log(studentId)
    // 修正API路径，确保与后端接口匹配
      return axios.get(`${baseUrl}/StudentManager/removeStudent`, {
        params: {
          id: studentId // 确保参数名与后端@RequestParam("id")一致
        }
      });
    },

    formatGender(row, column) {
      return row.gender === 1 ? "男" : "女";
    },

    // 分页处理
    handleSizeChange(size) {
      this.users.sizeOfPage = size;
      this.users.page = 1; // 改变每页条数时重置到第一页
      this.fetchStudentManager();
    },

    handlePageChange(page) {
      this.users.page = page;
      this.fetchStudentManager();
    },

    // 编辑学生
    editUser(student) {
      this.editingUser = true;
      this.editingUserId = student.id; // 设置当前编辑学生的 ID
      this.newUser = { ...student }; // 将学生信息复制到 newUser 中
      
      // 加载关联数据
      if (this.newUser.schoolId) {
        this.fetchGrades(this.newUser.schoolId);
      }
      
      if (this.newUser.gradeId) {
        this.fetchClasses(this.newUser.gradeId);
      }
      
      this.showAddUserDialog = true;
    },

    // 搜索学生
    searchUsers() {
      this.users.page = 1;
      this.fetchStudentManager();
    },

    // 关闭弹窗
    closeAddUserDialog() {
      this.showAddUserDialog = false;
      this.editingUser = false;
      this.editingUserId = null; // 清空编辑用户 ID
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
      // 重置关联数据
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

        this.newUser.className = this.classInfo[0]; // 默认选择第一个班级
        this.newUser.gradeId = this.gradeInfo[0]; // 默认选择第一个年级
        this.newUser.schoolId = this.schoolInfo[0].id; // 默认选择第一个学校

        this.showAddUserDialog = true;
      },
      // closeAddUserDialog() {
      //   this.showAddUserDialog = false;
      // },
    genderFormatter(row) {
      return this.genderOptions.find(option => option.value === row.gender)?.label || "";
    },

    // 重置搜索条件
    resetSearch() {
      this.searchQuery = {
        studentNum: "",
        studentName: "",
        parentPhoneNum: "",
        gender: "",
        gradeId: "",
        classId: "",
        schoolId: "",
        sizeOfPage: 15,
        page: 1
      };
      this.fetchStudentManager();
    },

    //获取学校信息列表
  fetchSchoolInfo() {
      axios
        .get(`${baseUrl}/StudentManager/getSchool`)
        .then((response) => {
          this.schoolInfo = response.data.data;
        })
        .catch(error => {
          console.error("获取学校列表失败：", error);
        });
  },
  //获取班级列表
  fetchClassInfo() {
      axios
        .get(`${baseUrl}/StudentManager/getClassName`)
        .then((response) => {
          this.classInfo = response.data.data;
        })
        .catch(error => {
          console.error("获取班级列表失败：", error);
        });
  },
  //获取年级列表
  fetchGradeInfo() {
      axios
        .get(`${baseUrl}/StudentManager/getGrade`)
        .then((response) => {
          this.gradeInfo = response.data.data;
        })
        .catch(error => {
          console.error("获取年级列表失败：", error);
        });
  },

    // 窗口大小变化时重绘图表
    resizeCharts() {
      this.genderChart?.resize();
      this.gradeChart?.resize();
      this.schoolChart?.resize();
    }
  },
  mounted() {
    this.fetchStudentManager();
    this.fetchSchoolInfo();
    this.fetchClassInfo();
    this.fetchGradeInfo();
    this.fetchStatisticData();
    
    // 监听窗口大小变化
    window.addEventListener("resize", this.resizeCharts);

    this.totalStudents = this.users.totalNum; // 初始化总学生数
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
