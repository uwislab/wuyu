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
            <el-option label="学校1" value="1" />
            <el-option label="学校2" value="2" />
            <el-option label="学校3" value="3" />
            <el-option label="学校4" value="4" />
            <el-option label="学校5" value="5" />
            <el-option label="学校6" value="6" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.gradeId" placeholder="年级" size="medium">
            <el-option label="一年级" value="1" />
            <el-option label="二年级" value="2" />
            <el-option label="三年级" value="3" />
            <el-option label="四年级" value="4" />
            <el-option label="五年级" value="5" />
            <el-option label="六年级" value="6" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchQuery.classId" placeholder="班级" size="medium">
            <el-option label="一班" value="1" />
            <el-option label="二班" value="2" />
            <el-option label="三班" value="3" />
            <el-option label="四班" value="4" />
            <el-option label="五班" value="5" />
            <el-option label="六班" value="6" />
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
        <!-- Excel操作按钮 -->
        <!-- <el-col :span="1.5">
          <el-button type="primary" @click="dialogVisible = true">导入Excel</el-button>
        </el-col> -->
        
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
        <el-table-column label="学生" prop="studentName" />
        <el-table-column label="电话" prop="parentPhoneNum" />
        <el-table-column label="性别" prop="gender" :formatter="genderFormatter" />
        <el-table-column label="学校" prop="schoolId" :formatter="schoolNameFormatter" />
        <el-table-column label="年级" prop="gradeId" />
        <el-table-column label="班级" prop="classId" />
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
    <div style="display: flex; justify-content: space-between; padding: 20px;">
      <el-pagination class="pagination" :current-page="users.page" :page-size="users.sizeOfPage" :total="users.totalNum"
      :page-sizes="[5, 10, 20, 50]" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange"
      @current-change="handlePageChange" />
      <div style="margin: 20px;">
        <el-button type="success" @click="exportExcel">导出Excel</el-button>
        <el-button type="primary" @click="dialogVisible = true">导入Excel</el-button>
      </div>
    </div>

    <!-- 添加/编辑用户弹窗 -->
    <el-dialog :visible.sync="showAddUserDialog" width="50%" @close="closeAddUserDialog">
      <h3>{{ editingUser ? '编辑用户' : '添加用户' }}</h3>
      <el-form :model="newUser" ref="form" label-width="100px" :rules="rules">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="newUser.username" />
        </el-form-item>
        <el-form-item v-if="!editingUser" label="密码" prop="password">
          <el-input v-model="newUser.password" type="password" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="newUser.realName" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="newUser.phoneNumber" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="newUser.gender">
            <el-option v-for="option in genderOptions" :key="option.value" :label="option.label"
              :value="option.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="newUser.identity">
            <el-option v-for="option in identityOptions" :key="option.value" :label="option.label"
              :value="option.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="学校">
          <el-select v-model="newUser.schoolId" placeholder="选择学校">
            <el-option v-for="school in schools" :key="school.id" :label="school.schoolName" :value="school.id" />
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
import { StudentManagerUrl } from "@/api/baseapi"; // 引入 StudentManagerUrl
import XLSX from "xlsx";
import { saveAs } from "file-saver";

export default {
  data() {
    return {
      dialogVisible:false,
      uploadFile: null,
      // 用户信息
      newUser: {
        username: "",
        password: "",
        realName: "",
        phoneNumber: "",
        gender: 1,
        identity: 3,
        schoolId: "",
      },
      file: {},
      rules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
          { pattern: /^.{1,10}$/, message: "用户名不能超过10个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { pattern: /^.{1,10}$/, message: "密码不能超过10个字符", trigger: "blur" },
        ],
        realName: [
          { required: true, message: "真实姓名不能为空", trigger: "blur" },
          { pattern: /^.{1,15}$/, message: "真实姓名不能超过15个字符", trigger: "blur" },
        ],
        phoneNumber: [
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
        schoolId: "",
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
      identityOptions: [
        { label: "学生", value: 1 },
        { label: "教师", value: 2 },
        { label: "管理员", value: 3 },
      ],
      school: [],
      grade: [],
      class: [],
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
      form.action = `${StudentManagerUrl}/studentExcel/export`;
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
      if (!this.uploadFile) {
        this.$message.warning('请先选择文件')
        return
      }
      const formData = new FormData()
      formData.append('file', this.uploadFile)
      try {
        const res = await axios.post('http://localhost:9080/studentExcel/import', formData)
        console.log('2222',res.data.code);
        if (res.data.code ===200) {
          this.$message.success('上传成功')
          this.dialogVisible = false
          this.uploadFile = null
          this.fetchStudentManager() // 刷新数据
        } else {
          this.$message.error('上传失败')
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
    validateAndSaveUser() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.saveUser();
        } else {
          this.$message.error("请检查表单输入是否正确");
        }
      });
    },
    fetchSchools() {
      axios
        .get(`${StudentManagerUrl}/webUser/schools`)
        .then((response) => {
          this.schools = response.data;
        })
        .catch((error) => {
          console.error("获取学校列表失败：", error);
        });
    },
    fetchAllUsers() {
      axios
        .get(`${StudentManagerUrl}/webUser/all`)
        .then((response) => {
          this.allUsers = response.data; // 假设接口返回的数据为用户列表
        })
        .catch((error) => {
          console.error("获取所有用户失败：", error);
        });
    },
    fetchStudentManager() {
      axios
        .post(`${StudentManagerUrl}/StudentManager/getStudent`, {
          page: this.users.page,
          sizeOfPage: this.users.sizeOfPage,
          studentNum: this.searchQuery.studentNum,
          studentName: this.searchQuery.studentName,
          gender: this.searchQuery.gender,
          classId: this.searchQuery.classId,
          gradeId: this.searchQuery.gradeId,
          schoolId: this.searchQuery.schoolId,
        })
        .then((response) => {
          console.log(response.data.data);
          this.users = response.data.data;
        })
        .catch((error) => {
          console.error("获取用户列表出错：", error);
        });
    },
    exportExcel() {
      // 创建表单
      const form = document.createElement('form');
      form.action = `${StudentManagerUrl}/studentExcel/export`;
      form.method = 'GET';
      form.style.display = 'none';
      
      // 添加到文档并提交
      document.body.appendChild(form);
      form.submit();
      
      // 清理
      document.body.removeChild(form);
    },
    saveUser() {
      // 验证用户名和手机号的唯一性
      const isDuplicateUsername = this.allUsers.some(
        (user) =>
          user.username === this.newUser.username &&
          (!this.editingUser || user.id !== this.editingUserId) // 排除当前编辑的用户
      );

      const isDuplicatePhoneNumber = this.allUsers.some(
        (user) =>
          user.phoneNumber === this.newUser.phoneNumber &&
          (!this.editingUser || user.id !== this.editingUserId) // 排除当前编辑的用户
      );

      if (isDuplicateUsername) {
        this.$message.error("用户名已存在，请重新输入！");
        return;
      }
      if (isDuplicatePhoneNumber) {
        this.$message.error("手机号已存在，请重新输入！");
        return;
      }

      // 如果是编辑模式，调用更新方法
      if (this.editingUser) {
        this.updateWebUser();
      } else {
        // 如果是新增模式，调用新增方法
        this.addWebUser();
      }
    },

    addWebUser() {
      axios
        .post(`${StudentManagerUrl}/webUser/add`, this.newUser)
        .then(() => {
          this.$message.success("用户添加成功！");
          this.fetchStudentManager();
          this.closeAddUserDialog();
        })
        .catch((error) => {
          console.error("添加用户出错：", error);
          this.$message.error("添加用户失败，请重试！");
        });
    },
    confirmDeleteUser(id) {
      this.$confirm(
        "此操作将永久删除该用户, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          // 确认删除
          this.deleteUser(id);
        })
        .catch(() => {
          // 用户取消删除
          this.$message.info("已取消删除");
        });
    },
    deleteUser(id) {
      axios
        .delete(`${StudentManagerUrl}/webUser/delete/${id}`)
        .then(() => {
          this.fetchStudentManager();
        })
        .catch((error) => {
          console.error("删除用户出错：", error);
        });
    },
    // 分页处理
    handleSizeChange(size) {
      this.users.sizeOfPage = size;
      this.users.page = 1; // 改变每页条数时重置到第一页
      this.fetchStudentManager();
    },

    handlePageChange(page) {
      console.log(page);
      this.users.page = page;
      this.fetchStudentManager();
    },
    editUser(user) {
      this.editingUser = true;
      this.editingUserId = user.id; // 设置当前编辑用户的 ID
      this.newUser = { ...user }; // 将用户信息复制到 newUser 中
      this.showAddUserDialog = true;
    },
    updateWebUser() {
      axios
        .put(`${StudentManagerUrl}/webUser/update`, this.newUser)
        .then(() => {
          this.$message.success("用户更新成功！");
          this.fetchStudentManager();
          this.closeAddUserDialog();
        })
        .catch((error) => {
          console.error("更新用户失败：", error);
          this.$message.error("更新用户失败，请重试！");
        });
    },
    searchUsers() {
      this.users.page = 1;
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
      this.showAddUserDialog = true;
    },
    genderFormatter(row) {
      return this.genderOptions.find((option) => option.value === row.gender)?.label || "未知";
    },
    identityFormatter(row) {
      return this.identityOptions.find((option) => option.value === row.identity)?.label || "未知";
    },
    schoolNameFormatter(row) {
      const school = this.schools.find(school => school.id === row.schoolId);
      return school ? school.schoolName : "未知学校";
    },
    resetSearch() {
      this.searchQuery = {
        studentNum: "",
        studentName: "",
        parentPhoneNum: "",
        gender: "",
        gradeId: "",
        classId: "",
        schoolId: "",
      };
      this.fetchStudentManager();
    },


    mounted() {
      this.fetchSchools();
      this.fetchAllUsers();
      this.fetchStudentManager();
    }
  }
}
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