<template>
  <body>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="70%">
      <el-row :gutter="0">
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart3" style="width: 500px; height: 350px"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart2" style="width: 500px; height: 350px"></div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="0">
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart1" style="width: 500px; height: 350px"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
            <div id="chart4" style="width: 500px; height: 350px"></div>
          </el-card>
        </el-col>
      </el-row>
    </el-dialog>
    <div style="margin: 10px 0">
      <el-dropdown>
        <span
          class="el-dropdown-link"
          style="font-weight: bold; font-size: 25px"
        >
          全部<i class="el-icon-arrow-down el-icon--right"></i>
        </span>

        <el-select v-model="kvalue" placeholder="请选择" @input="searchInput">
          <el-option
            v-for="item in subjects"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-dropdown>
      <el-tag style="margin-left: 100px">评语</el-tag>
      <el-input
        v-model="search"
        placeholder="请输入关键词"
        style="width: 20%"
        clearable
        @input="searchInput"
      ></el-input>
      <el-button type="primary" plain icon="el-icon-search" @click="reset"
        >重置</el-button
      >
      <el-button
        type="primary"
        plain
        icon="el-icon-search"
        @click="searchs"
        :disabled="disableVar"
        >查询</el-button
      >
      <el-button
        type="primary"
        plain
        icon="el-icon-search"
        @click="add"
        style="margin-left: 250px"
        >添加</el-button
      >
      <el-button type="primary" plain icon="el-icon-search" @click="deleteAll"
        >批量删除</el-button
      >
    </div>
    <!--  反馈表格  -->
    <div>
      <el-table
        ref="multipleTable"
        :data="tableData"
        style="width: 10000px"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="280"> </el-table-column>
        <el-table-column prop="score" label="分数" width="380">
        </el-table-column>
        <el-table-column prop="comment" label="评语" width="380">
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button
              @click="handleEdit(scope.row)"
              type="text"
              icon="el-icon-edit"
              size="small"
              >修改</el-button
            >
            <el-button
              type="text"
              icon="el-icon-delete"
              size="small"
              @click="handleDelete(scope.row.id)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-dialog title="修改" :visible.sync="dialogVisible1" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="科目" prop="region">
            <el-select v-model="operationId" placeholder="请选择">
              <el-option label="德育" :value="1"></el-option>
              <el-option label="智育" :value="2"></el-option>
              <el-option label="美育" :value="3"></el-option>
              <el-option label="体育" :value="4"></el-option>
              <el-option label="劳育" :value="5"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="ID">
            <el-input v-model="form.id" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="分数">
            <el-input v-model="form.score"></el-input>
          </el-form-item>
          <el-form-item label="评语">
            <el-input v-model="form.comment"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="editInfo">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <div>
      <el-dialog title="添加" :visible.sync="dialogVisible2" width="30%">
        <el-form :model="form1" label-width="120px">
          <el-form-item label="科目" prop="region">
            <el-select v-model="operationId" placeholder="请选择">
              <el-option label="德育" :value="1"></el-option>
              <el-option label="智育" :value="2"></el-option>
              <el-option label="体育" :value="3"></el-option>
              <el-option label="美育" :value="4"></el-option>
              <el-option label="劳育" :value="5"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分数">
            <el-input v-model="form1.score" placeholder="请输入分数"></el-input>
          </el-form-item>
          <el-form-item label="评语">
            <el-input
              v-model="form1.comment"
              placeholder="请输入评语"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="createComment">确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      :page-sizes="[5, 10, 20]"
      :page-size="pageSize"
      layout="sizes, prev, pager, next"
      :total="total"
    >
    </el-pagination>
  </body>
</template>

<script>
import request from "@/utils/re_request";
import { Message } from "element-ui";

export default {
  name: "assess",

  created() {
    this.load();
  },
  data() {
    return {
      tid: 1,
      tableData: [],
      yilist: [],
      colors: ["#99A9BF", "#F7BA2A", "#FF9900"],
      currentPage: 1,
      pageSize: 10,
      total: 100,
      form: {
        id: "",
        score: "",
        comment: "",
        // edit表单
      },
      form1: {
        score: "",
        comment: "",
      }, //add表单
      subjects: {},
      kvalue: null,
      search: "",
      disableVar: true,
      dialogVisible: false,
      dialogVisible1: false,
      dialogVisible2: false,
      multipleSelectionDepartment: [],
      operationId: 1, //德育智育体育id，默认德育
    };
  },
  methods: {
    load() {
      request
        .get("/corpus/list", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          this.tableData = res;
          console.log(res);
        });

      request.get("/subject/all").then((res) => {
        this.subjects = res.data;
        console.log(res);
      });
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleEdit(row) {
      this.form = Object.assign({}, row);
      this.operationId = row.subjectId;
      this.dialogVisible1 = true;
    },
    clearEditData() {
      this.dialogVisible1 = false;
      this.dialogVisible2 = false;
      this.operationId = 1;
      this.form = {
        id: "",
        score: "",
        comment: "",
      };
      this.form1 = {
        score: "",
        comment: "",
      };
    },
    hookBeforeEdit(score) {
      console.log("aa", Number(score));
      const num = Number(score);
      let regex = /^(100|[1-9]\d?|0)$/;
      if (isNaN(num)) {
        return false;
      } else if (regex.test(num)) {
        return true;
      } else {
        return false;
      }
    },
    async editInfo() {
      if (
        this.operationId == "" ||
        this.form.id === "" ||
        this.form.score === "" ||
        this.form.comment === ""
      ) {
        Message.error("信息不能为空！");
        return;
      }
      if (!this.hookBeforeEdit(this.form.score)) {
        Message.error("数据格式错误，分数应为0-100内的整数！");
        return;
      }
      try {
        const res = await request.put("/corpus/update", {
          id: this.form.id,
          subjectId: parseInt(this.operationId),
          score: this.form.score,
          comment: this.form.comment,
        });
        if (res.code === 600) {
          this.load();
          Message.success(res.msg);
        }
      } catch (error) {
        Message.error(error);
      } finally {
        this.clearEditData();
      }
    },
    add() {
      this.dialogVisible2 = true;
      this.form1 = {
        score: "",
        comment: "",
      };
      this.operationId = 1;
    },
    async createComment() {
      if (
        this.operationId == "" ||
        this.form1.score === "" ||
        this.form1.comment === ""
      ) {
        Message.error("信息不能为空！");
        return;
      }
      if (!this.hookBeforeEdit(this.form1.score)) {
        Message.error("数据格式错误，分数应为0-100内的整数！");
        return;
      }
      try {
        const res = await request.post("/corpus/add", {
          subjectId: parseInt(this.operationId),
          score: this.form1.score,
          comment: this.form1.comment,
        });
        if (res.code === 600) {
          this.load();
          Message.success(res.msg);
        }
      } catch (error) {
        Message.error(error);
      } finally {
        this.clearEditData();
      }
    },
    deleteAll() {
      if (
        this.multipleSelection.length === 0 ||
        this.multipleSelection.length === 1
      ) {
        this.$message.warning("请至少选择2条数据进行删除！");
      } else {
        this.$confirm(
          "此操作将永久删除【" +
            this.multipleSelection.length +
            "】条语料, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
            center: true,
          }
        )
          .then(() => {
            let ids = this.multipleSelection.map((item) => item.id);
            request
              .get("/corpus/deleteAll", { params: { ids: ids.join(",") } }) // 将数组转为逗号分隔的字符串
              .then((response) => {
                if (response.code === 600) {
                  this.load();
                  this.$message.success("删除成功");
                }
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      }
    },
    //条件查询语料库
    searchs() {
      console.log(this.kvalue);
      console.log(this.search);
      if (
        (this.kvalue == null || this.kvalue == "") &&
        (this.search == null || this.search == "")
      ) {
        this.disableVar = true;
      } else {
        request
          .get("/corpus/search", {
            params: {
              subjectId: this.kvalue,
              comment: this.search,
            },
          })
          .then((res) => {
            this.tableData = res.data;
            console.log(res);
          });
      }
    },
    searchInput() {
      if (
        (this.kvalue == null || this.kvalue == "") &&
        (this.search == null || this.search == "")
      ) {
        console.log("true");
        this.disableVar = true;
      } else {
        console.log("false");
        this.disableVar = false;
      }
    },

    reset() {
      this.kvalue = null;
      this.search = null;
      this.load();
      this.disableVar = true;
    },
    // 处理删除数据的逻辑
    handleDelete(id) {
      this.$confirm("确认删除吗？")
        .then((_) => {
          request
            .delete("/corpus/delete", {
              data: {
                id: id,
              },
            })
            .then((response) => {
              if (response.code === 600) {
                this.load();
                this.$message.success("删除成功");
              }
            });
        })
        .catch((_) => {});
    },
  },
};
</script>

<style></style>
