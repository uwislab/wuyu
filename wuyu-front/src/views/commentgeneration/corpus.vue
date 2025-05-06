<template>
  <div class="corpus">
    <el-button type="primary" round @click="dialogFormVisible = true">增加语料</el-button>
    <el-dialog :title="isEdit ? '更新语料' : '增加语料'" :visible.sync="dialogFormVisible" :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="科目" label-width="50" required prop="subjectId">
          <el-select v-model="form.subjectId">
            <el-option v-for="item in subjects" :value="item.id" :label="item.name" :key="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="分数" label-width="50" required prop="score">
          <el-tooltip class="item" effect="dark" content="大于或等于当前分数即为该评价" placement="top">
            <el-input v-model.number="form.score" autocomplete="off"></el-input>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="语料" label-width="120" required prop="comment">
          <el-input type="textarea" v-model="form.comment" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancle">取 消</el-button>
        <el-button type="primary" @click="handleForm">确 定</el-button>
      </div>
    </el-dialog>
    <el-table :data="list" style="margin-top: 10px" height="600" border="1" stripe="1"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column 
        prop="subject.name" 
        label="科目名称">
      </el-table-column>
      <el-table-column prop="score" label="最低分数"></el-table-column>
      <el-table-column prop="comment" label="语料"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click.native.prevent="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="danger" @click.native.prevent="($event) => handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button class="delete" type="delete" @click="deleteAll">批量删除</el-button>

    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
      :page-sizes="[10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"
      background class="pagination">
    </el-pagination>
  </div>
</template>

<script>
// import * as echarts from "echarts"
import { Message } from "element-ui";
import request from "@/utils/re_request";
import {baseUrl} from "@/api/baseapi";

export default {
  name: "corpus",
  data() {
    return {
      pageSize: 10,
      currentPage: 1, //当前页
      total: 0, //总记录数
      // 原始数据
      data: [],
      // 显示数据
      list: [],
      // 对话框
      dialogFormVisible: false,
      // 增加语料
      dialogFormAdd: false,
      // 科目
      subjects: [],
      // 表单
      form: {
        subjectId: "",
        score: "",
        comment: "",
      },
      isEdit: false,
      // 操作的数据ID
      operationId: "",

      testArr: [], //合并行数存放的数组
      testPosition: 0, //合并行的位置

      multipleSelection: [], //批量删除
    };
  },
  methods: {
    getData() {
      request.get("/corpus/all").then((response) => {
        if (response.code === 600) {
          this.pageSize = 10;
          this.currentPage = 1;
          this.data = response.data;
          this.list = response.data.slice(0, this.pageSize);
          this.total = response.count;
          // this.toQuery()
        }
      });
    },
    // 科目
    getSubjects() {
      request.get("/subject/all").then((response) => {
        if (response.code === 600) {
          this.subjects = response.data;
        }
      });
    },
    // 当前页变化
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      this.list = this.data.slice(
        this.pageSize * (currentPage - 1),
        this.pageSize * currentPage
      );
    },
    // 页面大小更新
    handleSizeChange(pageSize) {
      this.currentPage = 1;
      this.pageSize = pageSize;
      this.list = this.data.slice(0, pageSize);
    },

    // filterName(value, row) {
    //   return row.subject.name === value;
    // },

    // 处理语料编辑操作
    handleUpdate(item) {
      // 将isEdit设置为true方便对话框确定时的逻辑处理
      this.isEdit = true;
      // 开启对话框
      this.dialogFormVisible = true;

      // 填充数据
      this.$nextTick(() => {
        //在这里进行对form表单的赋值
        for (let key in item) {
          if (this.form.hasOwnProperty(key)) {
            this.form[key] = item[key];
          }
        }

        // 科目ID
        this.form.subjectId = item.subject.id;
        this.operationId = item.id;
      });
    },
    // 实现增加或者编辑语料逻辑
    handleForm() {
      // 关闭对话框
      this.dialogFormVisible = false;
      // 根据isEdit处理逻辑
      // 后端处理
      if (this.isEdit) {
        request
          .put("/corpus/update", {
            id: this.operationId,
            ...this.form,
          })
          .then((response) => {
            if (response.code === 600) {
              // 前端更新
              this.getData();
              Message.success(response.msg);
            }
          });
      } else {
        request
          .post("/corpus/add", {
            ...this.form,
          })
          .then((response) => {
            if (response.code === 600) {
              // 前端更新
              this.getData();
              Message.success(response.msg);
            }
          });
      }
      // 重置form和isEdit
      // 清空表单中的值
      this.$refs["form"].resetFields();
      this.isEdit = false;
    },
    // 处理从下方的取消 确定关闭对话框
    handleCancle() {
      // 关闭对话框
      this.dialogFormVisible = false;

      // 清空表单中的值并重置isEdit和totalScore
      this.$refs["form"].resetFields();
      this.isEdit = false;
    },
    // 处理从右上角的的×关闭对话框
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          this.handleCancle();
          done();
        })
        .catch((_) => { });
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
                this.getData();
                Message.success(response.msg);
              }
            });
        })
        .catch((_) => { });
    },

    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    deleteAll() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请至少选择一条数据进行删除！");
        return;
      }
      this.$confirm(
        `此操作将永久删除【${this.multipleSelection.length}】条语料, 是否继续?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
          center: true,
        }
      )
        .then(() => {
          // 获取选中的ID列表
          const ids = this.multipleSelection.map((item) => item.id);
          // 发送批量删除请求
          request
            .post("/corpus/deleteAll", ids) // 使用 POST 请求传递数组
            .then((response) => {
              if (response.code === 600) {
                this.getData(); // 删除成功后刷新数据
                this.$message.success("批量删除成功！");
              } else {
                this.$message.error(response.msg || "批量删除失败！");
              }
            })
            .catch((error) => {
              console.error(error);
              this.$message.error("删除时出现错误，请稍后重试！");
            });
        })
        .catch(() => {
          this.$message.info("已取消删除操作");
        });
    },

    toQuery() {
      this.rowspan(this.testArr, this.testPosition, "subject.name");
    },
    // 合并行或者是列
    arraySpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        const _row = this.testArr[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col,
        };
      }
    },

    rowspan(spanArr, position, name) {
      this.list.forEach((item, index) => {
        if (index === 0) {
          spanArr.push(1);
          position = 0;
        } else {
          if (this.list[index][name] === this.list[index - 1][name]) {
            spanArr[position] += 1;
            spanArr.push(0);
          } else {
            spanArr.push(1);
            position = index;
          }
        }
      });
    },
  },
  mounted() {
    this.getData();
    this.getSubjects();
  },
};
</script>

<style>
.corpus {
  margin: 30px 50px 0 50px;
}

.pagination {
  margin-left: 30%;
  font-size: 14px;
}
</style>
