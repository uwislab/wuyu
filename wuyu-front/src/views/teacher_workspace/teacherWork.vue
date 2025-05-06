<template>
  <el-container style="height: 100%; ">

    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
      <Aside/>
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; ">
        <!-- <div style="flex: 1;">
          <span style="font-size: 25px;"><b>>工作事项</b></span>
        </div>

        <span style="font-size: large;">欢迎：{{teacherName}}（{{title}}）</span>
        <el-button class="logout_button" @click="logout" style="margin-left: 5px; ">退出</el-button> -->
        <Header></Header>
      </el-header>

      <el-main>

        <div style="padding: 10px 0">
          <el-input style="width: 200px;margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入ID"
                    v-model="id"></el-input>
          <el-input style="width: 200px;margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入标题"
                    v-model="title"></el-input>
          <el-input style="width: 200px;margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入类型"
                    v-model="type"></el-input>
          <el-button style="margin-left: 5px" type="primary" @click="loadWork">搜索</el-button>
          <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
          <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
          <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="35"></el-table-column>
          <el-table-column prop="id" label="ID" width="50"></el-table-column>
          <el-table-column prop="upid" label="upid" width="55"></el-table-column>
          <el-table-column prop="title" label="标题"></el-table-column>
          <el-table-column prop="type" label="类型"></el-table-column>
          <el-table-column prop="status" label="状态" width="50"></el-table-column>
          <el-table-column prop="zhibiao" label="一级指标"></el-table-column>
          <el-table-column prop="score" label="分值" width="55"></el-table-column>
          <el-table-column prop="beizhu" label="备注"></el-table-column>
          <el-table-column prop="starttime" label="开始时间"></el-table-column>
          <el-table-column prop="finishtime" label="结束时间"></el-table-column>
          <el-table-column label="操作" width="210">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
              <el-button type="danger" @click="delConfirm(scope.row.id)">删除<i class="el-icon-remove-outline"
                                                                              style="margin-left: 2px"></i></el-button>
            </template>
          </el-table-column>
        </el-table>

        <div style="padding: 10px 0;">
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>

        <el-dialog title="详细信息" :visible.sync="dialogFormVisible" width="30%">
          <el-form label-width="80px" size="small">
            <el-form-item label="upid">
              <el-input v-model="form.upid" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="标题">
              <el-input v-model="form.title" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="类型">
              <el-input v-model="form.type" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="状态">
              <el-input v-model="form.status" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="一级指标">
              <el-input v-model="form.zhibiao" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="分值">
              <el-input v-model="form.score" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="form.beizhu" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-input v-model="form.starttime" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-input v-model="form.finishtime" autocomplete="off"></el-input>
            </el-form-item>

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </div>
        </el-dialog>

      </el-main>

    </el-container>
  </el-container>
</template>


<script>
import {deleteBatch, deleteWork, getWorkByPage, updateWork} from "@/api/teacher";
import Aside from "@/views/teacher_workspace/Aside"
import Header from "@/views/teacher_workspace/Header"

export default {
  data() {
    return {
      tableData: [],
      total: 0,
      pageSize: 5,
      pageNum: 1,
      id: "",
      title: "",
      type: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      teacherId: -1
    }
  },

  //工程实践 古
  created() {
    if (this.$route.query.id){
      console.log(this.$route.query);
      this.teacherId = this.$route.query.id;
      localStorage.setItem("teacherId", this.teacherId);
    }
    else{
      this.teacherId = localStorage.getItem("teacherId");
    }

    this.loadWork()
    // this.showTeacher()
    // this.refreshPage();
  },

  components: {
    Aside,
    Header
  },


  methods: {
    // 按钮点击事件 -- 退出登录
    async logout() {
      await this.$store.dispatch('user/resetToken');
      this.$router.push(`/login?redirect=${this.$route.fullPath}`);
    },




    loadWork() {
      //请求分页查询数据
      getWorkByPage(this.pageNum, this.pageSize, this.id, this.title, this.type).then(res => {
        console.log(res.data);
        this.tableData = res.data.data;
        this.total = res.data.total;
      })
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.loadWork()
    },

    handleCurrentChange(pageNum) {
      // console.log(pageNum);
      this.pageNum = pageNum
      this.loadWork()
    },

    delConfirm(id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteWork(id).then(res => {
          // console.log(res.data);
          if (res.data) {
            this.$message.success("删除成功")
            this.loadWork()
          } else {
            this.$message.error("删除失败")
            this.loadWork()
          }
        })
      }).catch(() => {
        this.$message.error("已取消删除")
      });
    },

    reset() {
      this.title = ""
      this.id = ""
      this.type = ""
      this.loadWork()
    },

    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },

    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },

    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },

    save() {
      // console.log(this.form);
      // console.log(typeof(this.form));
      updateWork(this.form).then(res => {
        // console.log(res);
        if (res.data) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.loadWork()
        } else {
          this.$message.error("保存失败")
          this.dialogFormVisible = false
          this.loadWork()
        }
      })
    },

    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)     //  [{},{},{}] => [1,2,3]
      console.log(ids);
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBatch(ids).then(res => {
          if (res) {
            this.$message.success("批量删除成功")
            this.loadWork()
          } else {
            this.$message.error("批量删除失败")
            this.loadWork()
          }
        })
      }).catch(() => {
        this.$message.error("已取消批量删除")
      });
    },

    // showTeacher(){
    //   getTeacherInfo(this.teacherId).then(res => {
    //     this.teacherName = res.data.teacherName,
    //     this.title = res.data.title
    //   })
    // }

  },
};
</script>


<style>


</style>
