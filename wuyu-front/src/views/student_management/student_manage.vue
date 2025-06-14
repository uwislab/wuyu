<template>
  <div class="web-user">
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
          <el-input v-model="searchQuery.classId" placeholder="班级" size="medium" />
        </el-col>
        <el-col :span="4">
          <el-input v-model="searchQuery.gradeId" placeholder="年级" size="medium" />
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

      <!-- 用户列表 -->
      <el-table :data="users.data" border stripe>
        <el-table-column label="学号" prop="studentNum" />
        <el-table-column label="学生姓名" prop="studentName" />
        <el-table-column label="性别" :formatter="formatGender" />
        <el-table-column label="班级" prop="classId" />
        <el-table-column label="年级" prop="gradeId"/>
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
            <el-option v-for="option in genderOptions" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-input v-model="newUser.classId" />
        </el-form-item>
        <el-form-item label="年级" prop="gradeId">
          <el-input v-model="newUser.gradeId" />
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
import {StudentManagerUrl} from "@/api/baseapi"; // 引入 StudentManagerUrl

export default {
  data() {
    return {
      // 用户信息
      newUser: {
        studentNum: "",
        studentName: "",
        gender: "",
        classId: "",
        gradeId:"",
        parentPhoneNum: "",
        gender: 1,
        identity: 3,
        schoolId: "",
      },
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
        studentNum:"",
        studentName:"",
        parentPhoneNum:"",
        gender:"",
        gradeId:"",
        classId:"",
        schoolId:"",
        sizeOfPage: 15,
        page: 1,
      },
      allUsers: [],
      users: {
        data: [],
        sizeOfPage: 10,
        pages: 1,
        total: 0,
        isLast: false,
      },
      showAddUserDialog: false, // 弹窗显示状态
      schools: [], // 存储学校列表
      // 性别和角色选项
      genderOptions: [
        { label: "男", value: 1 },
        { label: "女", value: 0 },
      ],
      school: [],
      grade:[],
      class:[],
    };
  },
  methods: {
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
        console.log('请求地址:', StudentManagerUrl);
        axios.post(`${StudentManagerUrl}StudentManager/addStudent`, this.newUser)
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
        axios.post(`${StudentManagerUrl}StudentManager/updateStudent`, this.newUser)
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
        .get(`${StudentManagerUrl}/StudentManager/getStudent`)
        .then((response) => {
          this.schools = response.data;
        })
        .catch((error) => {
          console.error("获取学校列表失败：", error);
        });
    },
    fetchAllUsers() {
      axios
        .get(`${StudentManagerUrl}/studentManager/addStudent`)
        .then((response) => {
          this.allUsers = response.data; // 假设接口返回的数据为用户列表
        })
        .catch((error) => {
          console.error("获取所有用户失败：", error);
        });
    },
    fetchStudentManager() {
      axios
        .post(`${StudentManagerUrl}StudentManager/getStudent`, {
            page: this.users.page,
            sizeOfPage: this.users.sizeOfPage,
            studentNum:this.searchQuery.studentNum,
            studentName:this.searchQuery.studentName,
            gender:this.searchQuery.gender,
            classId:this.searchQuery.classId,
            gradeId:this.searchQuery.gradeId,
            schoolId:this.searchQuery.schoolId,
        })
        .then((response) => {
          console.log(response.data.data);
          this.users = response.data.data;
        })
        .catch((error) => {
          console.error("获取用户列表出错：", error);
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
    // 修正API路径，确保与后端接口匹配
      return axios.get(`${StudentManagerUrl}StudentManager/removeStudent`, {
        params: {
          id: studentId // 确保参数名与后端@RequestParam("id")一致
        }
      });
    },

    formatGender(row, column) {
      return row.gender === 1 ? "男" : "女";
    },
    // 分页处理
   handleSizeChange (size){
     this.users.sizeOfPage = size;
     this.users.page = 1; // 改变每页条数时重置到第一页
     this.fetchStudentManager();
    },

  handlePageChange (page){
      console.log(page)
    this.users.page = page;
    this.fetchStudentManager();
    },
    editUser(user) {
      this.editingUser = true;
      this.editingUserId = user.id; // 设置当前编辑用户的 ID
      this.newUser = { ...user }; // 将用户信息复制到 newUser 中
      this.showAddUserDialog = true;
    },
    
    searchUsers() {
    /*  this.users.page = 1;*/
      this.fetchStudentManager();
    },
    closeAddUserDialog() {
      this.showAddUserDialog = false;
      this.editingUser = false;
      this.editingUserId = null; // 清空编辑用户 ID
      this.newUser = {
        username: "",
        password: "",
        realName: "",
        phoneNumber: "",
        gender: 1,
        identity: 3,
        schoolId: "",
      };
    },
    openAddUserDialog() {
        this.editingUser = false;
        this.newUser = {
          studentNum: "",
          studentName: "",
          gender: 1,
          classId: "",
          gradeId: "",
          parentPhoneNum: "",
          identity: 3,
          schoolId: "",
        };
        this.showAddUserDialog = true;
      },
      closeAddUserDialog() {
        this.showAddUserDialog = false;
      },
    genderFormatter(row) {
      return this.genderOptions.find((option) => option.value === row.gender)?.label || "未知";
    },
    identityFormatter(row) {
      return this.identityOptions.find((option) => option.value === row.identity)?.label || "未知";
    },
    schoolNameFormatter(row) {
      const school = this.schools.find((school) => school.id === row.schoolId);
      return school ? school.schoolName : "未知学校";
    },
    resetSearch() {
      this.searchQuery = {
        username: "",
        realName: "",
        identity: "",
        gender: "",
      };
      this.fetchStudentManager();
    },
  },
  mounted() {
    this.fetchSchools();
    this.fetchAllUsers();
    this.fetchStudentManager();
  },
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
</style>
