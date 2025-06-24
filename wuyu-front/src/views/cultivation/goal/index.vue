<template>
  <div class="app-container">
    <!-- 头部搜索板块 -->
    <div class="header">
      <div class="search_block">
        <el-input
          class="search_input01"
          v-model="queryForm.title"
          placeholder="根据量表名称查询"
        ></el-input>
        <el-input
          class="search_input02"
          v-model="queryForm.creatorName"
          placeholder="根据编辑人查询"
        ></el-input>
        <el-date-picker
          class="search_input03"
          value-format="yyyy-MM-dd"
          v-model="queryForm.createDate"
          placeholder="根据创建日期查询"
        ></el-date-picker>
        <el-button
          class="search_btn"
          type="primary"
          round
          @click="queryByButton()"
        >
          搜索
        </el-button>
      </div>
      <div class="condition_button_group">
        <el-button-group class="button-group">
          <el-button type="primary" @click="getAll">全部</el-button>
          <el-button type="" @click="queryState(0)">编辑中</el-button>
          <el-button type="danger" @click="queryState(3)">已驳回</el-button>
          <el-button type="success" @click="queryState(2)">已通过</el-button>
          <el-button type="info" @click="queryState(5)">已弃用</el-button>
        </el-button-group>
        <el-button class="resetBtn" @click="getAll">重置</el-button>
      </div>
    </div>

    <div style="width: 100%; border: solid 1px gray; margin: 15px" />

    <!-- 量表表格区域 -->
    <div class="scale_table">
      <div
        style="
          display: flex;
          align-items: center;
          flex-direction: row;
          justify-content: space-between;
          flex-wrap: wrap;
        "
      >
        <el-button
          type="warning"
          @click="scaleDialogVisible = true"
          class="insertScale"
          >+新增量表</el-button
        >
        <h1>所有量表</h1>
      </div>

      <el-table :cell-style="{border:'1px solid black'}" v-loading="loading" :header-cell-style="{border:'1px solid black',background: '#324157', color: '#ffffff'}"
       border :data="scaleList.slice((currentPage - 1) * pageSize, currentPage * pageSize)">
        <el-table-column label="量表id" prop="scaleId" align="center"></el-table-column>
        <el-table-column label="量表标题" prop="title" align="center"></el-table-column>
        <el-table-column label="创建者" prop="creatorName" align="center"></el-table-column>
        <el-table-column label="创建日期" prop="createDate" align="center"></el-table-column>
        <el-table-column label="年级" prop="grade" align="center"></el-table-column>
        <el-table-column label="量表状态" prop="state" align="center">
          <template slot-scope="scope">
            <el-tag type="" size="medium" v-if="scope.row.state == 0"
              >编辑中</el-tag
            >
            <el-tag type="success" size="medium" v-if="scope.row.state == 2"
              >审核通过</el-tag
            >
            <el-tag type="danger" size="medium" v-if="scope.row.state == 3"
              >驳回</el-tag
            >
            <el-tag type="info" size="medium" v-if="scope.row.state == 5"
              >弃用</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="相关领域" prop="domain" align="center">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.domain == 0">泛型</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 1">德育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 2">智育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 3">体育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 4">美育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 5">劳育</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{ row }">
            <div>
              <el-button
                type="primary"
                class="copy_btn"
                size="mini"
                @click="scaleCopy(row.scaleId)"
                >复制</el-button
              >
            </div>
            <div>
              <el-button
                type="danger"
                class="del_btn"
                size="mini"
                @click="deleteScale(row.scaleId)"
              >
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="查看详情" align="center">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" @click="checkScale(row)"
              >查看详情</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[1, 5, 10, 20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="this.scaleList.length"
    >
    </el-pagination>

    <!-- 创建量表对话框 -->
    <el-dialog
      :visible.sync="scaleDialogVisible"
      title="创建一个量表"
      width="30%"
    >
      <el-form
        :model="scaleInfoForm"
        ref="scaleInfoFormRef"
        label-width="130px"
        :rules="rules"
      >
        <el-form-item label="评价量表名称" prop="title">
          <el-input v-model="scaleInfoForm.title" />
        </el-form-item>
        <el-form-item label="绑定年级" prop="grade">
          <el-select placeholder="请绑定一个年级" v-model="scaleInfoForm.grade">
            <el-option v-for="(grade) in gradeList" :label="grade" :value="grade"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评价量表相关领域" prop="domain">
          <el-select
            placeholder="请选择当前量表关联领域"
            v-model="scaleInfoForm.domain"
          >
            <el-option
              v-for="(item, index) in fuItemList"
              :key="index"
              :label="item"
              :value="index + 1"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="scaleDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="createScale()"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
//引入fuScale.js
import {
  insertFuScale,
  getAllFuScale,
  getBy,
  deleteScale,
  scaleCopy,
} from "@/api/fuScale";
import router from "@/router";


export default {
  name: "tp",
  data() {
    return {
      loading: false,
      scaleList: [],
      records: [],
      total: 0,
      fuItemList: ['德育', '智育', '体育', '美育', '劳育'],
      scaleDialogVisible: false,
      scaleInfoForm: {
        id: null,
        title: "",
        createDate: null,
        state: 0,
        creatorId: null,
        domain: null,
        creatorName: null,
        grade: null,
      },
      queryForm: {
        title: '',
        createDate: '',
        state: null,
        creatorName: null,
      },
      currentPage: 1,
      pageSize: 5,
      rules: {
        title: [
          {
            required: true,
            message: "请输入量表名称(2~20字符)",
            pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]{2,20}$/,
          },
        ],
        grade: [{ required: true, message: "请选择绑定年级" }],
        domain: [{ required: true, message: "请选择量表领域" }],
      },
      gradeList: Array.from({ length: 23 }, (_, i) => (2023 - i).toString()) // 生成2001到2023的年级列表
    };
  },
  mounted() {
    this.loadAllFuScale();
  },

  methods: {
    // 加载所有量表
    loadAllFuScale() {
      this.currentPage = 1;
      this.loading = true;
      getAllFuScale()
        .then(res => {
          this.scaleList = res.data || [];
          this.loading = false;
        })
        .catch(error => {
          this.$message.error('加载量表失败: ' + error.message);
          this.loading = false;
        });
    },

    // 创建量表
    createScale() {
      this.$refs.scaleInfoFormRef.validate((valid) => {
        if (valid) {
          this.scaleInfoForm.createDate = this.formatDate(new Date());
          this.scaleInfoForm.creatorId  = JSON.parse(window.localStorage.getItem("UserInfo")).id
          insertFuScale(this.scaleInfoForm)
            .then(res => {
              if (res.code === 200) {
                this.loadAllFuScale();
                this.scaleDialogVisible = false;
                this.$refs.scaleInfoFormRef.resetFields();
                 this.$message.success('量表创建成功');
              } else {
                this.$message.error('创建量表失败');
                this.$refs.scaleInfoFormRef.resetFields();
              }
            })
            .catch(error => {
              this.$message.error('创建量表过程中出现错误');
            });
        }
      });
    },

    formatDate(date) {
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
    },

    checkScale(row) {
      router.push({ path: "/goal/goalDetails", query: { row } });
    },

    getAll() {
      this.queryForm = { title: '', createDate: '', state: null, creatorName: null};
      this.loadAllFuScale();
    },

    queryState(stateId) {
      this.queryForm.state = stateId
      this.queryByButton()
    },

    //搜索框按钮触发函数
    queryByButton() {
      getBy(this.queryForm).then(res => {
        this.scaleList = res.data || [];
        this.queryForm.state = null
      })
    },

    deleteScale(scaleId) {
      this.$confirm('此操作将永久删除该量表, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteScale(scaleId)
          .then(res => {
            if (res.code === 200) {
              this.$message.success("删除成功");
              this.loadAllFuScale();
            } else {
              this.$message.error('删除失败');
            }
          })
          .catch(error => {
            this.$message.error('删除过程中出现错误: ' + error.message);
          });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },

    handleSizeChange(val) {
      this.currentPage = 1;
      this.pageSize = val;
    },

    handleCurrentChange(val) {
      this.currentPage = val;
    },

    scaleCopy(scaleId) {
      this.$confirm('此操作将复制该量表, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        scaleCopy(scaleId)
          .then(res => {
            if (res.code === 200) {
              this.$message.success("复制成功");
              this.loadAllFuScale();
              this.currentPage = 1;
              this.pageSize = 5;
            } else {
              this.$message.error('复制失败');
            }
          })
          .catch(error => {
            this.$message.error('复制过程中出现错误: ' + error.message);
          });
      }).catch(() => {
        this.$message.info('已取消复制');
      });
    }
  },
};
</script>


<style scoped>
.app-container {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.header {
  display: flex;
  flex-flow: wrap;
  place-content: center space-around;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
  align-content: center;
  flex-direction: row;
  width: 100%;
  height: fit-content;
  margin: 10px;
  border-radius: 15px;
  padding: 1rem;
  border: 1px solid black;
}
/*查询区域 */
.search_block {
  display: flex;
  flex-wrap: nowrap;
  justify-content: center;
  align-content: center;
  align-items: center;
  flex-direction: row;
  padding: 1rem;
}

.del_btn {
  text-align: center;
  margin-top: 3px;
  height: 28px;
  width: 50px;
}

.copy_btn {
  height: 28px;
  width: 50px;
}

.search_input01 {
  width: 20%;
  margin-right: 5px;
}

.search_input02 {
  width: 20%;
  margin-right: 5px;
}

.search_input03 {
  width: 30%;
  margin-right: 5px;
}

.search_btn {
  margin-left: 8px;
  color: black;
  background-color: white;
  height: 36px;
}

.status {
  display: inline;
  font-size: 20px;
  font-weight: bold;
  vertical-align: middle;
  margin-right: 3px;
}

.resetBtn {
  margin-left: 25px;
  vertical-align: middle;
}

.condition_button_group {
  padding: 1rem;
}

.scale_table {
  width: 100%;
  margin: 10px;
  padding: 20px;
  border: 1px solid black;
  border-radius: 15px;
}

.insertScale {
  margin-bottom: 9px;
  margin-right: 550px;
  background-color: green;
  display: inline-flex;
}

.insertScale:hover {
  transform: scale(1.1);
}
</style>
