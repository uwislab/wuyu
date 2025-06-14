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
          <el-input v-model="searchQuery.studentName" placeholder="学生" size="medium" />
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.gender" placeholder="性别" size="medium">
            <el-option label="男" value="1" />
            <el-option label="女" value="0" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.schoolId" placeholder="学校" size="medium">
            <el-option v-for="school in schools" :key="school.id" :label="school.schoolName" :value="school.id" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.gradeId" placeholder="年级" size="medium">
            <el-option v-for="grade in grades" :key="grade.id" :label="grade.gradeName" :value="grade.id" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.classId" placeholder="班级" size="medium">
            <el-option v-for="classItem in classes" :key="classItem.id" :label="classItem.className" :value="classItem.id" />
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

      <!-- 用户列表 - 直接渲染原始数字数据 -->
      <el-table :data="users.data" border stripe>
        <el-table-column label="学号" prop="studentNum" />
        <el-table-column label="学生" prop="studentName" />
        <el-table-column label="电话" prop="parentPhoneNum" />
        <el-table-column label="性别" prop="gender" :formatter="genderFormatter" />
        <el-table-column label="学校" prop="schoolId" /> <!-- 直接渲染原始数字 -->
        <el-table-column label="年级" prop="gradeId" /> <!-- 直接渲染原始数字 -->
        <el-table-column label="班级" prop="classId" /> <!-- 直接渲染原始数字 -->
        <el-table-column label="批阅" prop="isreview" />
        <el-table-column label="是否录入" prop="isenter" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button size="small" @click="editUser(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="confirmDeleteUser(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页 -->
    <el-pagination
      class="pagination"
      :current-page="users.page"
      :page-size="users.sizeOfPage"
      :total="users.totalNum"
      :page-sizes="[5, 10, 20, 50]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handlePageChange"
    />

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
      <h3>{{ editingUser ? '编辑用户' : '添加用户' }}</h3>
      <el-form :model="newUser" ref="form" label-width="100px" :rules="rules">
        <el-form-item label="学号" prop="studentNum">
          <el-input v-model="newUser.studentNum" />
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="newUser.studentName" />
        </el-form-item>
        <el-form-item label="家长电话" prop="parentPhoneNum">
          <el-input v-model="newUser.parentPhoneNum" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="newUser.gender">
            <el-option v-for="option in genderOptions" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="学校">
          <el-select v-model="newUser.schoolId" placeholder="选择学校" @change="onSchoolChange">
            <el-option v-for="school in schools" :key="school.id" :label="school.schoolName" :value="school.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="年级">
          <el-select v-model="newUser.gradeId" placeholder="选择年级" @change="onGradeChange">
            <el-option v-for="grade in grades" :key="grade.id" :label="grade.gradeName" :value="grade.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="newUser.classId" placeholder="选择班级">
            <el-option v-for="classItem in classes" :key="classItem.id" :label="classItem.className" :value="classItem.id" />
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="closeAddUserDialog">取消</el-button>
        <el-button type="primary" @click="validateAndSaveUser">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import * as echarts from "echarts";
import { StudentManagerUrl } from "@/api/baseapi";

export default {
  data() {
    return {
      // 学生信息
      newUser: {
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
      },
      rules: {
        studentNum: [
          { required: true, message: "学号不能为空", trigger: "blur" },
          { pattern: /^.{1,20}$/, message: "学号不能超过20个字符", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
          { pattern: /^.{1,15}$/, message: "姓名不能超过15个字符", trigger: "blur" }
        ],
        parentPhoneNum: [
          { required: true, message: "家长电话不能为空", trigger: "blur" },
          { pattern: /^\d{11}$/, message: "手机号必须是11位数字", trigger: "blur" }
        ]
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
        schoolId: "",
        sizeOfPage: 15,
        page: 1
      },
      users: {
        data: [],
        sizeOfPage: 10,
        pages: 1,
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
      // 统计数据
      statisticData: {},
      // 图表实例
      genderChart: null,
      gradeChart: null,
      schoolChart: null
    };
  },
  methods: {
    // 表单验证并保存
    validateAndSaveUser() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.saveUser();
        } else {
          this.$message.error("请检查表单输入是否正确");
        }
      });
    },

    // 获取学校列表
    fetchSchools() {
      axios
        .get(`${StudentManagerUrl}/webUser/schools`)
        .then(response => {
          this.schools = response.data;
        })
        .catch(error => {
          console.error("获取学校列表失败：", error);
        });
    },

    // 获取年级列表
    fetchGrades(schoolId) {
      axios
        .get(`${StudentManagerUrl}/webUser/grades?schoolId=${schoolId}`)
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
        .get(`${StudentManagerUrl}/webUser/classes?gradeId=${gradeId}`)
        .then(response => {
          this.classes = response.data;
          this.newUser.classId = "";
        })
        .catch(error => {
          console.error("获取班级列表失败：", error);
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
      axios
        .post(`${StudentManagerUrl}/StudentManager/getStudent`, {
          page: this.users.page,
          sizeOfPage: this.users.sizeOfPage,
          studentNum: this.searchQuery.studentNum,
          studentName: this.searchQuery.studentName,
          gender: this.searchQuery.gender ? parseInt(this.searchQuery.gender) : null,
          classId: this.searchQuery.classId ? parseInt(this.searchQuery.classId) : null,
          gradeId: this.searchQuery.gradeId ? parseInt(this.searchQuery.gradeId) : null,
          schoolId: this.searchQuery.schoolId ? parseInt(this.searchQuery.schoolId) : null
        })
        .then(response => {
          this.users = response.data.data;
          this.totalStudents = this.users.totalNum;
        })
        .catch(error => {
          console.error("获取用户列表出错：", error);
        });
    },

    // 获取统计数据
    fetchStatisticData() {
      axios
        .get(`${StudentManagerUrl}/api/statistics/student`)
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

    // 保存学生信息
    saveUser() {
      // 如果是编辑模式，调用更新方法
      if (this.editingUser) {
        this.updateStudent();
      } else {
        // 如果是新增模式，调用新增方法
        this.addStudent();
      }
    },

    // 添加学生
    addStudent() {
      axios
        .post(`${StudentManagerUrl}/StudentManager/add`, this.newUser)
        .then(() => {
          this.$message.success("学生添加成功！");
          this.fetchStudentManager();
          this.fetchStatisticData(); // 更新统计数据
          this.closeAddUserDialog();
        })
        .catch(error => {
          console.error("添加学生出错：", error);
          this.$message.error("添加学生失败，请重试！");
        });
    },

    // 确认删除学生
    confirmDeleteUser(id) {
      this.$confirm("此操作将永久删除该学生, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          // 确认删除
          this.deleteStudent(id);
        })
        .catch(() => {
          // 用户取消删除
          this.$message.info("已取消删除");
        });
    },

    // 删除学生
    deleteStudent(id) {
      axios
        .delete(`${StudentManagerUrl}/StudentManager/delete/${id}`)
        .then(() => {
          this.$message.success("学生删除成功！");
          this.fetchStudentManager();
          this.fetchStatisticData(); // 更新统计数据
        })
        .catch(error => {
          console.error("删除学生出错：", error);
          this.$message.error("删除学生失败，请重试！");
        });
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

    // 更新学生信息
    updateStudent() {
      axios
        .put(`${StudentManagerUrl}/StudentManager/update`, this.newUser)
        .then(() => {
          this.$message.success("学生信息更新成功！");
          this.fetchStudentManager();
          this.fetchStatisticData(); // 更新统计数据
          this.closeAddUserDialog();
        })
        .catch(error => {
          console.error("更新学生失败：", error);
          this.$message.error("更新学生失败，请重试！");
        });
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
      this.showAddUserDialog = true;
    },

    // 格式化性别显示（保留性别格式化，其他列直接显示数字）
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

    // 窗口大小变化时重绘图表
    resizeCharts() {
      this.genderChart?.resize();
      this.gradeChart?.resize();
      this.schoolChart?.resize();
    }
  },
  mounted() {
    this.fetchSchools();
    this.fetchStudentManager();
    this.fetchStatisticData();
    
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