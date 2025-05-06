<template>
  <div class="app-container">
    <!--最上面一行问卷搜索框-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="88px">

      <el-form-item label="查询字段" prop="paper" class="def">
        <el-select v-model="selectedField" filterable placeholder="请选择查询字段...">
          <el-option v-for="item in paperInfo" :key="item.label" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="值" prop="paperTitle">
        <el-input v-model="queryParams[selectedField]" placeholder="请输入查询关键字..." clearable size="medium"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="问卷类型" prop="paperType" class="def">
        <el-select v-model="queryParams.paperType" filterable clearable placeholder="请选择问卷类型...">
          <el-option v-for="item in paperTypeOption" :key="item.name" :label="item.name" :value="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="问卷状态" prop="paperStatus" class="def">
        <el-select v-model="queryParams.paperStatus" filterable clearable placeholder="请选择问卷状态...">
          <el-option v-for="item in paperStatusOption" :key="item.label" :label="item.label"
            :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="gh">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索
        </el-button>
        <el-button type="success" icon="el-icon-refresh" size="mini" @click="resetQuery">重置
        </el-button>
      </el-form-item>
    </el-form>
    <!--第二行问卷创建修改批量删除-->
    <el-row :gutter="15" class="abc">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">创建
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">批量删除
        </el-button>
      </el-col>
    </el-row>
    <!--第三行问卷的基本信息-->
    <el-table :data="paperList" ref="multipleTable" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="问卷号" align="center" prop="paperId" />
      <el-table-column label="问卷标题" align="center" prop="paperTitle" />
      <el-table-column label="问卷描述" align="center" prop="description" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{
      parseTime(scope.row.startTime, "{y}-{m}-{d} {h}:{i}:{s}")
    }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{
        parseTime(scope.row.endTime, "{y}-{m}-{d} {h}:{i}:{s}")
      }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="问卷地址" align="center" prop="paperUrl" width="180">
        <template slot-scope="scope">
          <a :href="scope.row.paperUrl" target="_blank" style="color: #409EFF; text-decoration: none;">{{
      scope.row.paperUrl }}</a>
        </template>
      </el-table-column>
      <el-table-column label="问卷二维码" align="center" prop="codeUrl" width="180">
        <template slot-scope="scope">
          <img :src="scope.row.codeUrl">
        </template>
      </el-table-column>
      <el-table-column label="问卷状态" align="center" prop="paperStatus">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.paperStatus == 0">设计中
          </el-tag>
          <el-tag type="success" v-else-if="scope.row.paperStatus == 1">收集中
          </el-tag>
          <el-tag type="danger" v-else-if="scope.row.paperStatus == 2">已结束
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="问卷类型" align="center" prop="paperType" />
      <el-table-column label="问卷操作" align="center" class-name="small-padding" width="120">
        <template slot-scope="scope">
          <el-button v-if="scope.row.paperStatus == 0" size="mini" type="warning" plain class="icon" icon="el-icon-edit"
            @click="updateQuestion(scope.row)">修改
          </el-button>
          <el-button size="mini" type="danger" plain class="icon2" icon="el-icon-delete"
            @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="内容操作" align="center" width="120" fixed="right">
        <template slot-scope="scope">
          <el-button-group class="icon3">
            <el-button v-if="scope.row.paperStatus == 0" size="mini" type="primary" icon="el-icon-plus"
              @click="handleAddQuest(scope.row)">添加
            </el-button>
            <el-button size="mini" type="primary" icon="el-icon-zoom-in" @click="handlePreview(scope.row)">预览
            </el-button>
            <el-button v-if="scope.row.paperStatus == 0" size="mini" type="primary" icon="el-icon-top"
              @click="handlePublish(scope.row)">发布
            </el-button>
            <el-button v-if="scope.row.paperStatus == 1" size="mini" type="primary" icon="el-icon-edit-outline"
              @click="handleAnswer(scope.row)">答题
            </el-button>
            <!-- <el-button
              v-if="scope.row.paperStatus != 0"
              size="mini"
              type="primary"
              icon="el-icon-document-copy"
            >记录
            </el-button> -->
            <el-button v-if="scope.row.paperStatus != 0" size="mini" type="primary" icon="el-icon-s-data"
              @click="handleCount(scope.row)">统计
            </el-button>
            <el-button v-if="scope.row.paperStatus == 1" size="mini" type="primary" icon="el-icon-top"
              @click="handleStop(scope.row)">结束
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <!--底部分页操作-->
    <el-pagination background :page-sizes="[5, 10, 15, 20]" layout="total, sizes, prev, pager, next, jumper"
      :current-page="queryParams.pageNum" :page-size="queryParams.pageSize" :total="total"
      @size-change="handleSizeChange" @current-change="handleCurrentChange">
    </el-pagination>
    <!-------------------新增问卷部分------------------------>
    <!-- 添加或修改问卷基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rulesPaper" label-width="80px">
        <el-form-item label="问卷标题" prop="paperTitle">
          <el-input v-model="form.paperTitle" placeholder="请输入问卷标题..." />
        </el-form-item>
        <el-form-item label="问卷类型" prop="paperType">
          <el-select v-model="form.paperType" filterable clearable placeholder="请选择">
            <el-option v-for="item in paperTypeOption" :key="item.name" :label="item.name" :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问卷描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容..." />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable size="small" v-model="form.startTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable size="small" v-model="form.endTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="danger" @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-------------------新增题目部分------------------------>
    <!-- 添加或修改题目信息对话框 -->
    <el-dialog :title="title" :visible.sync="showQuest" width="700px" append-to-body>
      <el-form ref="formQuest" :model="formQuest" :rules="rulesQuest" label-width="80px">
        <el-form-item label="题目类型" prop="questType">
          <el-radio-group v-model="formQuest.questType">
            <el-radio :label="1">单选</el-radio>
            <el-radio :label="2">多选</el-radio>
            <el-radio :label="3">判断</el-radio>
            <el-radio :label="4" @change="input_click">填空</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="题目标题" prop="questTitle">
          <el-input type="textarea" v-model="formQuest.questTitle" placeholder="请输入题目标题..." />
        </el-form-item>
        <template>
          <el-form-item v-for="(item, index) in qOptionsList" :label="'选项' + (index + 1)" :key="index"
            :prop="'optContent' + index">
            <el-input v-model="item.optContent" placeholder="请输入选项内容..." style="width: 450px; margin-right: 5px">
            </el-input>
            <el-button type="primary" plain @click.prevent="removeDomain(item)">删除</el-button>
          </el-form-item>
          <el-form-item v-if="formQuest.questType != 4">
            <el-button type="primary" plain @click="addDomain">新增选项</el-button>
          </el-form-item>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="submitFormQuest">确 定</el-button>
        <el-button type="danger" plain @click="cancelQuest">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPaper,
  getPaper,
  delPaper,
  addPaper,
  updatePaper,
} from "@/api/questionnaire/paper";
import QRCode from "qrcode";
import {
  listQuestion,
  getQuestion,
  delQuestion,
  addQuestion,
  updateQuestion,
} from "@/api/questionnaire/question";
import { string } from "clipboard";

import axios from 'axios';
import { options } from "dropzone";
import { nextTick } from "vue";
export default {
  name: "Paper",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 问卷信息表格数据
      paperList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 问卷基本参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        paperTitle: null,
        description: null,
        startTime: null,
        endTime: null,
        paperStatus: null,
        paperType: null,
      },
      // 存储当前选中的字段
      selectedField: 'paperTitle',
      //问卷信息
      paperInfo: [
        {
          label: "问卷标题",
          value: "paperTitle",
        },
        {
          label: "问卷描述",
          value: "description",
        },
      ],
      // 问卷类型
      paperTypeOption: [
        {
          name: "德育",
        },
        {
          name: "智育",
        },
        {
          name: "体育",
        },
        {
          name: "美育",
        },
        {
          name: "劳育",
        },
      ],
      // 问卷状态
      paperStatusOption: [
        {
          label: "设计中",
          value: "0",
        },
        {
          label: "收集中",
          value: "1",
        },
        {
          label: "已结束",
          value: "2",
        },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rulesPaper: {
        paperTitle: [
          { required: true, message: "问卷标题不能为空", trigger: "blur" },
        ],
        description: [
          { required: true, message: "问卷描述不能为空", trigger: "blur" },
        ],
        paperType: [
          { required: true, message: "请选择问卷类型", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "请选择开始时间", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "请选择结束时间", trigger: "blur" }
        ]
      },
      rulesQuest: {
        questType: [
          { required: true, message: "题目类型不能为空", trigger: "change" },
        ],
        questTitle: [
          { required: true, message: "题目标题不能为空", trigger: "blur" },
        ],
        optContent: [
          { required: true, message: '选项不能为空', trigger: 'blur' },
        ],
      },
      /********************新增题目部分***************** */
      // 是否显示
      showQuest: false,
      // 题目表单参数
      formQuest: { questType: null, qOptionsList: [] },
      // 选项表单参数
      qOptionsList: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 选中填空类型
    input_click() {
      this.qOptionsList = [];
    },

    /** 获取问卷基本信息列表 */


    async getList() {
      console.log(this.queryParams)
      try {
        const res = await axios.post('http://36.111.68.174:33380/questionnaire/paper/list', this.queryParams);
        res.data.data.forEach((item, index) => {
          res.data.data[index].paperUrl = "http://36.111.68.174:33380/#/questionnaire?paperId=" + res.data.data[index].paperId;
          QRCode.toDataURL("http://36.111.68.174:33380/#/questionnaire?paperId=" + res.data.data[index].paperId, {
            type: 'image/jpeg',
            width: 100,
            height: 100,
          }).then((url) => {
            res.data.data[index].codeUrl = url
          })
        })
        this.paperList = res.data.data
        this.total = Number(res.data.count)
      } catch (error) {
        console.error('数据加载失败:', error);
      }
    },

    // 取消创建问卷按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    //设置每页大小
    handleSizeChange: function (size) {
      console.log("设置每页大小")
      this.queryParams.pageSize = size;
      this.getList();
    },
    //页面跳转
    handleCurrentChange: function (page) {
      console.log("页面跳转")
      this.queryParams.pageNum = page;
      console.log(this.queryParams.pageNum)
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        paperId: null,
        createBy: null,
        paperTitle: null,
        description: null,
        startTime: null,
        endTime: null,
        paperStatus: "0",
        paperType: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log(this.queryParams)
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.selectedField = 'paperTitle'
      this.queryParams.description = null
      this.queryParams.paperTitle = null
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框全选
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.paperId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 创建问卷按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "创建问卷基本信息";
    },
    /** 修改问卷基本信息按钮操作 */
    handleUpdate(row) {
      this.reset();
      // debugger;
      this.form = this.$refs.multipleTable.selection[0];
      if (this.form.paperStatus != 0) {
        this.$modal.msgWarning("只允许修改设计中的问卷！");
        return;
      }
      this.open = true;
      this.title = "修改问卷基本信息";
    },
    /** 校验时间，返回 true 表示通过，false 表示不通过 */
    validateTime(startTime, endTime) {
      const start = new Date(startTime);
      const end = new Date(endTime);
      if (start > end) {
        this.$modal.msgError("开始时间不能大于结束时间！");
        return false;  // 校验失败
      }
      return true;  // 校验通过
    },
    /** 创建问卷提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          // 调用 validateTime 方法进行时间校验
          if (!this.validateTime(this.form.startTime, this.form.endTime)) {
            return;  // 如果校验失败，直接返回，不进行提交
          }

          if (this.form.paperId != null) {
            updatePaper(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功！");
              this.open = false;
              this.getList();
            });
          } else {
            addPaper(this.form).then((response) => {
              this.$modal.msgSuccess("创建成功！");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除问卷按钮操作 */
    handleDelete(row) {
      const paperIds = row.paperId || this.ids;
      this.$modal
        .confirm('是否确认删除编号为"' + paperIds + '"的问卷？')
        .then(function () {
          return delPaper(paperIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功！");
        })
        .catch(() => { });
    },

    /*************************新增题目部分*********************** */
    // 添加题目按钮
    handleAddQuest(row) {
      this.formQuest.paperId = row.paperId;
      this.showQuest = true;
    },
    // 内容预览按钮
    handlePreview(row) {
      sessionStorage.setItem("paperId", JSON.stringify(row.paperId))
      sessionStorage.setItem("paperTitle", JSON.stringify(row.paperTitle))
      sessionStorage.setItem("paperType", JSON.stringify(row.paperType))
      sessionStorage.setItem("description", JSON.stringify(row.description))
      this.$router.push({ name: "预览" });
    },
    // 答题按钮
    handleAnswer(row) {
      // this.$router.push({ path: "/questionnaire/answer", query: row });
      this.$router.push({ name: "答题", query: row });
    },
    // 发布问卷按钮
    handlePublish(row) {
      this.$modal
        .confirm('是否确认发布编号为"' + row.paperId + '"的问卷？')
        .then(() => {
          row.paperStatus = "1"
          return updatePaper(row);
        })
        .then((res) => {
          this.getList(); // 刷新页面
          console.log(res.data)
          if(res.data == -1){
            this.$modal.msgWarning("问卷没有题目,发布失败！");
          }
          else{
            this.$modal.msgSuccess("发布问卷成功！");
          }
        })
        .catch(() => { });
    },
    //修改问卷题目按钮
    updateQuestion(row) {
      if (row && typeof row === "object") {
        this.$router.push({
          name: "修改题型",  // 确保与路由配置中的 `name` 一致
          query: { ...row } // 将 `row` 的内容作为 query 参数传递
        });
      } else {
        console.error("Invalid row data: Expected an object.");
      }
    },
    // 问卷统计按钮
    handleCount(row) {
      this.$router.push({ name: "统计", query: row });
    },
    // 结束发布问卷按钮
    handleStop(row) {
      this.$modal
        .confirm('是否确认结束编号为"' + row.paperId + '"的问卷？')
        .then(() => {
          row.paperStatus = "2"
          return updatePaper(row);
        })
        .then((res) => {
          this.getList(); // 刷新页面
          this.$modal.msgSuccess("结束问卷成功！");
        })
        .catch(() => { });
    },
    // 取消添加题目按钮
    cancelQuest() {
      this.showQuest = false;
      // 重置表单
      (this.formQuest = { questType: 1 }), (this.qOptionsList = []);
    },
    // 提交题目按钮
    submitFormQuest() {
      this.$refs["formQuest"].validate((valid) => {
        if (valid) {
          if (this.formQuest.questType !== 4) {
            if (this.qOptionsList.length >= 2) {
              this.formQuest.qOptionsList = this.qOptionsList;
              addQuestion(this.formQuest).then((response) => {
                this.$modal.msgSuccess("添加题目成功");
                this.showQuest = false;
                this.getList(); // 刷新页面
                (this.formQuest = { questType: 1 }), (this.qOptionsList = []); // 重置表单
              });
            }
            else {
              this.$modal.msgWarning("选项个数至少两个！");
            }
          }
          else {
            this.formQuest.qOptionsList = this.qOptionsList;
            addQuestion(this.formQuest).then((response) => {
              this.$modal.msgSuccess("添加题目成功");
              this.showQuest = false;
              this.getList(); // 刷新页面
              (this.formQuest = { questType: 1 }), (this.qOptionsList = []); // 重置表单
            });
          }
        }
      });
    },
    // 删除选项按钮
    removeDomain(item) {
      var index = this.qOptionsList.indexOf(item);
      if (index !== -1) {
        this.qOptionsList.splice(index, 1);
      }
    },
    // 新增选项按钮
    addDomain() {
      this.qOptionsList.push({
        optSeque: this.qOptionsList.length + 1,
        key: Date.now(),
      });
    },
  }
};
</script>
<style>
.abc {
  padding: 0px 20px;
}

.def {
  margin-left: 30px;
}

.gh {
  margin-left: 80px;
}

.icon {
  margin-left: 10px;
  margin-bottom: 5px;
}

.icon2 {
  margin-left: 10px;
}

.icon3 {
  margin-left: 15px;
}
</style>
