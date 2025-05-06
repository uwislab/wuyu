<template>
    <div class="user-permission">
      <h2>用户权限管理</h2>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.userName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="身份">
          <el-select v-model="searchForm.identity" placeholder="身份">
            <el-option label="校长" value="0"></el-option>
            <el-option label="教务" value="1"></el-option>
            <el-option label="班主任" value="2"></el-option>
            <el-option label="老师" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="searchForm.realName" placeholder="真实姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch">查询</el-button>
          <el-button type="warning" @click="handleReset">重置</el-button> <!-- 添加重置按钮 -->
        </el-form-item>
      </el-form>
      <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="180"></el-table-column>
        <el-table-column prop="username" label="用户名" width="180"></el-table-column>
        <el-table-column prop="identity" label="身份">
          <template slot-scope="scope">
             {{ getIdentityLabel(scope.row.identity) }}
          </template>
        </el-table-column>
        <el-table-column prop="real_name" label="真实姓名"></el-table-column>
        <el-table-column prop="phone_number" label="电话号码"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current_page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
      <el-dialog title="编辑用户" :visible.sync="dialogVisible" width="30%">
        <el-form :model="editForm" label-width="100px">
          <el-form-item label="用户名">
            <el-input v-model="editForm.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="身份">
            <el-select v-model="editForm.identity" placeholder="身份">
              <el-option label="校长" :value="0"></el-option>
              <el-option label="教务" :value="1"></el-option>
              <el-option label="班主任" :value="2"></el-option>
              <el-option label="老师" :value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="真实姓名">
            <el-input v-model="editForm.real_name"></el-input>
          </el-form-item>
          <el-form-item label="电话号码">
            <el-input v-model="editForm.phone_number"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveEdit">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { searchUserPermissions, deleteUserPermissions, updateUserPermission } from "@/api/user";
  
  export default {
    name: "UserPermission",
    data() {
      return {
        searchForm: {
          userName: '',
          identity: null,
          realName: '',
          page: 1,
          pageSize: 10,
        },
        tableData: [],
        multipleSelection: [],
        pageNum: 1,
        pageSize: 10,
        total: 0,
        dialogVisible: false,
        editForm: {
          id: null,
          username: '',
          identity: null,
          real_name: '',
          phone_number: '',
        },
      };
    },
    created() {
      this.onSearch();
    },
    methods: {
      onSearch() {
        searchUserPermissions(this.searchForm).then(response => {
          this.tableData = response.data.data
          this.total =Number( response.data.total);
        }).catch(error => {
          console.error("查询用户权限失败", error);
        });
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleSizeChange(newSize) {
        this.pageSize = newSize;
        this.searchForm.pageSize = newSize;
        this.onSearch();
      },
      handleCurrentChange(newPage) {
        this.pageNum = newPage;
        this.searchForm.page = newPage;
        this.onSearch();
      },
      handleEdit(index, row) {
        this.dialogVisible = true;
        this.editForm = { ...row };
      },
      handleDelete(index, row) {
        this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteUserPermissions([row.id]).then(response => {
            if (response.code === 200) {
              this.$message.success('删除成功!');
              this.onSearch();
            } else {
              this.$message.error('删除失败!');
            }
          }).catch(error => {
            console.error("删除用户失败", error);
            this.$message.error('删除失败!');
          });
        }).catch(() => {
          this.$message.info('已取消删除');
        });
      },
      saveEdit() {
        updateUserPermission(this.editForm).then(response => {
          if (response.code === 600) {
            this.$message.success('修改成功!');
            this.dialogVisible = false;
            this.onSearch();
          } else {
            this.$message.error('修改失败!');
          }
        }).catch(error => {
          console.error("修改用户失败", error);
          this.$message.error('修改失败!');
        });
      },
      getIdentityLabel(identity) {
        switch (identity) {
          case 0:
            return '校长';
          case 1:
            return '教务';
          case 2:
            return '班主任';
          case 3:
            return '老师';
          default:
            return '未知';
        }
      },
      handleReset() { // 添加重置方法
        this.searchForm = {
          userName: '',
          identity: null,
          realName: '',
          page: 1,
          pageSize: 10,
        };
        this.onSearch();
      },
    },
  };
  </script>
  
  <style scoped>
  .user-permission {
    width: 100%;
    padding: 20px;
    height: 100%;
    background-color: white;
  }
  </style>
