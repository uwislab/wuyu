<template>
  <div class="app-container">
    <div class="aspectmanager_button">
      <div>
        <el-button @click="back" type="info"
        ><i class="el-icon-back"></i>返回</el-button
        >
        <el-button type="success" icon="el-icon-plus" @click="addAspect"
        >添加评价维度</el-button
        >
        <el-button type="primary" @click="goMindMap">预览脑图</el-button>
        <el-button
          :disabled="!this.previewData.length > 0"
          type="primary"
          @click="saveExcel"
        >保存数据</el-button
        >
        <el-button type="success" @click="getCopyGoal">复制量表</el-button>
      </div>
      <div class="uploadcontainer">
        <el-upload
          :action="uploadActionUrl"
          :on-success="uploadSuccess"
          :on-error="uploadError"
          :before-remove="beforeRemove"
          :on-remove="handleRemove"
          :accept="'.xlsx,.xls'"
          :multiple="false"
          :before-upload="beforeUpload"
          :limit="1"
        >
          <el-button type="success">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">请上传Excel格式文件</div>
        </el-upload>
      </div>
    </div>

    <el-descriptions title="评价量表内容">
      <el-descriptions-item label="标题">{{ goal.title }}</el-descriptions-item>
      <el-descriptions-item label="年级/学期"
      >{{ goal.forGrade }} {{ goal.forTerm }}</el-descriptions-item
      >
      <el-descriptions-item label="年级/学期">{{
          goal.launchDate
        }}</el-descriptions-item>
      <el-descriptions-item label="发起人">{{
          goal.teacherName
        }}</el-descriptions-item>
      <el-descriptions-item label="编辑人">{{
          goal.editorNames
        }}</el-descriptions-item>
      <el-descriptions-item label="审核人">{{
          goal.reviewerName
        }}</el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag v-if="goal.state === 1" type="info">待发起</el-tag>
        <el-tag v-if="goal.state === 2" type="warning">发起中</el-tag>
        <el-tag v-if="goal.state === 3" type="info">待审核</el-tag>
        <span v-if="goal.state === 4"
        ><el-tag type="danger">审核驳回</el-tag>{{ scope.row.remark }}</span
        >
        <el-tag v-if="goal.state === 5" type="success">发布中</el-tag>
        <!-- <el-tag v-if="goal.state ===9" type="info">逾期</el-tag> -->
      </el-descriptions-item>
      <!-- <el-descriptions-item label="附件"><el-tag size="small"><a :href="URL_ACTION + goal.fileLocation" :download="goal.fileName">点击下载</a></el-tag></el-descriptions-item> -->
    </el-descriptions>

    <el-table
      :data="dataTable"
      border
      class="table"
      ref="multipleTable"
      header-cell-class-name="table-header"
    >
      <el-table-column
        prop="type"
        align="center"
        label="评价维度"
      ></el-table-column>
      <el-table-column
        prop="summary"
        align="center"
        label="分数"
      ></el-table-column>
      <el-table-column
        prop="createDate"
        align="center"
        label="创建时间"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="modifyDate"
        align="center"
        label="更新时间"
        width="180"
      ></el-table-column>

      <el-table-column label="操作" align="center" fixed="right" width="400">
        <template #default="scope">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            @click="manageActivity(scope.row.id)"
            v-if="goal.state === 2"
          >添加评价内容</el-button
          >
          <el-button
            type="primary"
            plain
            icon="el-icon-edit"
            @click="editAspect(scope.row)"
          >编辑</el-button
          >
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            class="red"
            @click="deleteAspect(scope.$index, scope.row)"
          >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        background
        layout="total, prev, pager, next"
        :current-page="queryForm.pageNo"
        :page-size="queryForm.pageSize"
        :total="dataTable.length"
        @current-change="handlePaging"
      >
      </el-pagination>
    </div>

    <!--  评价维度添加框  -->
    <el-dialog title="维度和内容" :visible.sync="editVisible">
      <el-form
        label-width="120px"
        :model="dataForm"
        :rules="FORM_RULES"
        ref="dataForm"
      >
        <!-- 父节点内容 -->
        <template v-if="dataTable.length > 0">
          <el-form-item label="上级内容">
            <el-select
              v-model="value"
              placeholder="请选择"
              clearable
              :disabled="editStatu"
            >
              <el-option
                v-for="(item, index) in dataTable.slice(
                  0,
                  this.dataTableLength
                )"
                :key="index"
                :lable="item.type"
                :value="item.type"
              ></el-option>
            </el-select>
          </el-form-item>
        </template>

        <el-form-item label="评价维度" prop="type">
          <!-- <el-select v-model="dataForm.type"><el-option v-for="opt in ASPECT_OPTS" :label="opt" :value="opt"></el-option></el-select> -->
          <el-input v-model="dataForm.type" type="textarea" />
        </el-form-item>
        <!-- 分数校验 -->
        <el-form-item label="分数" prop="summary">
          <el-input v-model.number="dataForm.summary" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button type="primary" @click="commit">确定</el-button>
        </span>
      </template>

    </el-dialog>
    <!-- 复制 -->
    <el-dialog title="复制量表" :visible.sync="copyStatu" width="800">
      <el-table :data="goalTable" height="180" :visable="copyStatu">
        <el-table-column prop="createDate" label="日期"></el-table-column>
        <el-table-column prop="title" label="名称"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" plain @click="copyGoal(scope.row)"
            >复制</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import goalApi from "@/api/cultivationModule/goal";
import aspectApi from "@/api/cultivationModule/goalAspect";
import { Row } from "element-ui";
export default {
  name: "aspectManager",
  data() {
    return {
      gid: "",
      goal: {},
      //维度的数据
      dataTable: [],
      //量表数据
      goalTable: [],
      //选择的数据
      dataForm: {
        id: "",
        //文本内容
        type: "",
        //分数
        summary: 0,
      },
      mindForm: {},
      queryForm: {
        pageNo: 1,
        pageSize: 20,
        gid: "",
      },
      pageTotal: 0,
      //状态，控制显示
      editVisible: false,
      editStatu: false,
      uploadStatu: false,
      copyStatu: false,
      URL_ACTION: process.env.VUE_APP_BASE_API,
      //表单校验规则
      FORM_RULES: {
        summary: [
          { required: true, message: "分数不能为空", trigger: "change" },
          { type: "integer", message: "请输入数字", trigger: "change" },
          {
            validator: (rule, value, callback) => {
              if (value <= 0) {
                callback(new Error("请输入正整数"));
              } else {
                callback();
              }
            },
            trigger: "change",
          },
          {
            validator: (rule, value, callback) => {
              if (value > 100) {
                callback(new Error("请输入不大于100的数"));
              } else {
                callback();
              }
            },
            trigger: "change",
          },
        ],
        type: [{ required: true, message: "内容不能为空", trigger: "change" }],
      },
      //节点下拉选中值
      value: "",
      //默认量表
      DEFAULT_ID: 1,
      secondChild: false,
      //上传地址
      // uploadActionUrl: "http://49.51.69.99:9200/api/goal/uploadExcel",
      //服务器地址
      uploadActionUrl: `${process.env.VUE_APP_BASE_API}/api/goal/uploadExcel`,
      //预览的数据，暂存
      dataTableLength: 0,
      previewData: [],
    };
  },

  methods: {

    getGoal() {
      goalApi.info({ id: this.gid }).then((res) => {
        if (res.code === 200) {
          this.goal = res.data;
        } else {
          this.$message.error(res.msg);
        }
      });
    },

    //跳转到评价内容页面
    manageActivity(id) {
      this.$router.push("activityManager?aid=" + id);
    },

    addAspect() {
      this.dataForm = {
        type: "",
        summary: "",
      };
      this.mindForm = {};
      this.editStatu = false;
      this.editVisible = true;
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate();
      });
    },

    editAspect(row) {
      //节点id
      this.dataForm.id = row.id;
      //暂存更新前的值
      this.dataForm.updateValue = row.summary;
      //如果是三级节点以后
      if (row.aid != undefined) {
        this.dataForm.aid = row.aid;
        this.dataForm.gid = row.gid;
        this.dataForm.type = row.type;
        this.dataForm.summary = row.summary;
        this.secondChild = false;
      } else {
        this.secondChild = true;
        this.dataForm.type = row.type;
        this.dataForm.summary = row.summary;
        this.dataForm.gid = row.gid;
      }
      this.editStatu = true;
      this.editVisible = true;
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate();
      });
    },
    cancelForm() {
      this.editVisible = false;
      this.$refs.dataForm.resetFields();
      //在下次渲染前清除上次校验的信息
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate();
      });
    },
    commit() {
      this.$refs.dataForm.validate((valid) => {
        if (!valid) {
          this.$message.error("请按提示填写表单");
          this.editVisible = true;
          return false;
        } else {
          //根据editStatu的值判断当前的数据操作：false表示新增，true代表修改
          if (!this.editStatu) {
            //插入到对应表中,此处gid是表id
            this.dataForm.gid = this.gid;
            //遍历是否选择了父节点
            let obj = this.dataTable.filter((item) => {
              return item.type == this.value;
            });
            //如果有就选中父节点的ID，没有赋空
            this.mindForm.aspectdata = this.dataForm;
            if (obj.length > 0) {
              if (!this.checkSummary()) {
                return false;
              }
              //object.assgin
              this.mindForm.aid = this.dataForm.gid;
              //此处gid是父节点
              this.mindForm.gid = obj[0].id;
              //数据库设计是这样，不可删除此判断
              if (obj[0].hasOwnProperty("aid")) {
                this.mindForm.title = "子评价指标";
              } else {
                this.mindForm.title = "二级评价指标";
              }
              this.mindForm.tags = this.dataForm.type;
              this.mindForm.content = this.dataForm.type;
              this.mindForm.weight = this.dataForm.summary;
              this.insertchild(this.mindForm);
            } else {
              if (!this.checkSummary()) {
                return false;
              }
              aspectApi.insert(this.dataForm).then((res) => {
                if (res.code === 200) {
                  this.successpromt("新增维度");
                  this.dataForm = {
                    type: "",
                    summary: "",
                  };
                  this.value = "";
                  this.editVisible = false;
                  if (
                    this.dataForm.id === undefined ||
                    this.dataForm.id === null
                  ) {
                    this.queryForm.pageNo = 1;
                  }
                  this.paging();
                } else {
                  this.$message.error(res.message);
                }
              });
            }
            //更新操作？
          } else {
            this.update();
          }
        }
      });
    },
    /**
     * @description: 删除维度
     * @param {*} index
     * @param {*} row
     * @param {*} e
     * @return {*}
     */
    deleteAspect(index, row) {
      this.dataForm.id = row.id;
      //判断节点的类型，有aid的是二级节点及以后
      if (row.hasOwnProperty("aid")) {
        this.secondChild = false;
      } else {
        this.secondChild = true;
      }
      this.$confirm("确定要删除吗 ？", "提示", { type: "warning" })
        .then(() => {
          aspectApi.delete(this.dataForm, !this.secondChild).then((res) => {
            if (res.code === 200) {
              this.$message({ type: "success", message: "已删除" });
              this.dataTable.splice(index, 1);
            } else {
              this.$notify({ title: "提示", message: res.msg, duration: 0 });
            }
          });
        })
        .catch(() => {
          this.$message({ type: "info", message: "已取消" });
        });
    },
    handlePaging(val) {
      this.queryForm.pageNo = val;
      this.paging();
    },
    //查询根节点之外的所有节点
    paging() {
      // this.queryForm.gid = this.DEFAULT_ID;
      aspectApi.paging(this.queryForm).then((res) => {
        res.list[1].forEach((item) => {
          item.type = item.content;
          item.summary = item.weight;
        });
        this.dataTable = [...res.list[0], ...res.list[1]];
        this.dataTableLength = this.dataTable.length;
        this.pageTotal = this.dataTableLength;
      });
    },
    back() {
      this.$router.go(-1); //返回上一步路由
      this.$store.dispatch("tagsView/delView", this.$route); //调用全局挂载的方法，关闭当前页
    },
    /**
     * @description: 插入三阶节点及以后
     * @param {dataForm} data
     * @return {*}
     */
    async insertchild(data) {
      let res = await aspectApi.insertchild(data);
      if (res.code == 200) {
        this.successpromt("新增维度");
        this.editVisible = false;
        this.dataForm = {
          type: "",
          summary: "",
        };
        this.mindForm = {};
        this.value = "";
        this.paging();
      } else {
        this.$message.error(res.msg);
      }
    },
    async updatechild(data) {
      let res = await aspectApi.updateChild(data);
      if (res.code == 200) {
        this.successpromt("更新");
        this.editVisible = false;
        this.dataForm = {
          type: "",
          summary: "",
        };
        this.value = "";
        this.paging();
      } else {
        this.$message.error(res.msg);
      }
    },
    //校验分数
    checkSummary() {
      //计算目前的总分
      let t_sum = this.dataTable.reduce((pre, cur) => {
        if (
          typeof cur.summary === "undefined" ||
          !cur.hasOwnProperty("summary")
        ) {
          return (pre += parseInt(cur.weight));
        } else {
          return (pre += parseInt(cur.summary));
        }
      }, 0);
      let rest = 100 - t_sum;
      //更新
      if (this.editStatu) {
        rest += parseInt(this.dataForm.updateValue);
        if (this.dataForm.summary > rest) {
          this.$message.error(`超过最大分数，剩余可更新分数${rest}`);
          this.dataForm.summary = "";
          return false;
        }
      } //新增
      else {
        if (this.dataForm.summary > rest) {
          this.$message.error(`超过最大分数,剩余可分配分数${rest}`);
          this.dataForm.summary = "";
          return false;
        }
      }
      return true;
    },

    update() {
      if (!this.checkSummary()) {
        return false;
      }
      //是否是三级节点
      if (!this.secondChild) {
        this.dataForm.weight = this.dataForm.summary;
        this.dataForm.content = this.dataForm.type;
        this.updatechild(this.dataForm);
      } else {
        aspectApi.update(this.dataForm).then((res) => {
          if (res.code === 200) {
            this.successpromt("更新");
            this.dataForm = {
              type: "",
              summary: "",
            };
            this.value = "";
            this.editVisible = false;
            if (this.dataForm.id === undefined || this.dataForm.id === null) {
              this.queryForm.pageNo = 1;
            }
            this.paging();
          } else {
            this.$message.error(res.message);
          }
        });
      }
    },
    /**
     * @description: 通用成功描述
     * @param {String} info 成功内容
     * @return {*}
     */
    successpromt(info) {
      this.$message({
        type: "success",
        message: `${info}成功`,
      });
    },
    open() {
      this.$msgbox({
        title: "消息",
        message: "请按照模板文件输入",
        showCancelButton: true,
        confirmButtonText: "下载",
        cancelButtonText: "取消",
        beforeClose: (action, instance, done) => {
          if (action === "confirm") {
            instance.confirmButtonLoading = true;
            instance.confirmButtonText = "请等待下载...";
            this.downloadTemplate();
          } else {
            done();
          }
        },
      }).then((action) => {
        this.$message({
          type: "info",
          message: "action: " + action,
        });
      });
    },
    //上传成功回调
    uploadSuccess(res) {
      let now = new Date();
      let year = now.getFullYear();
      let month = now.getMonth() + 1;
      let date = now.getDate();
      let hour = now.getHours();
      let minute = now.getMinutes();
      let second = now.getSeconds();
      let time =
        year +
        "-" +
        month +
        "-" +
        date +
        " " +
        hour +
        ":" +
        minute +
        ":" +
        second;
      //时间戳生成id
      let timestap = Date.now().toString();
      if (res.data.length == 0) {
        this.open();
      } else {
        this.successpromt("上传");
        res.data.forEach((item, index) => {
          item.type = item.cname;
          item.summary = item.score || 0;
          item.createDate = time;
          item.modifyDate = time;
          item.name = `${item.cname}(${item.score}分)`;
          item.title = "excel导入指标";
          //生成唯一的id，位数太长存不进去
          item.id = `${timestap.charAt(2)}${timestap.charAt(
            4
          )}${timestap.charAt(6)}${index}`;
        });
        this.dataTable.push(...res.data);
        this.previewData = [];
        this.previewData = this.getTreeList(res.data, "空", []);
        //将二级节点和三级节点标识出来
        this.previewData.forEach((item) => {
          item.isSecondRoot = true;
          item.children.forEach((i) => {
            i.title = "二级评价指标";
          });
        });
      }
    },
    uploadError() {
      this.$message({
        text: "上传失败，请按照模板上传",
        type: "error",
      });
    },
    /**
     * @description:得到树形结构的数据
     * @param {Array} rootlist
     * @param {String} comparename
     * @param {Array} newArray
     * @param {Int} id
     * @return {Array}
     */
    getTreeList(rootlist, comparename, newArray, id = 0) {
      rootlist.forEach((item) => {
        if (item.pname == comparename) {
          item.gid = id;
          newArray.push(item);
        }
      });
      newArray.forEach((item) => {
        item.children = [];
        this.getTreeList(rootlist, item.cname, item.children, item.id);
      });
      return newArray;
    },
    //上传Excel处理方法
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    handleRemove(file, fileList) {
      this.previewData = [];
      this.dataTable.splice(this.dataTableLength, this.dataTable.length);
    },
    beforeUpload(file) {
      const isExcel =
        file.type === "application/vnd.ms-excel" ||
        file.type ===
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      if (!isExcel) {
        this.$message.error("只能上传Excel文件！");
        return false;
      }
      return true;
    },
    //预览
    goMindMap() {
      this.$router.push({
        name: "goalMindMap",
        params: {
          id: this.gid,
          preview: this.previewData.length > 0 ? true : false,
          data: this.previewData,
        },
      });
    },
    // 保存数据
    saveExcel() {
      //扁平化数组
      const savedata = this.previewData.flat();
      const para = this.flattenArray(savedata);
      let mapdata = new Map([]);
      //去重
      para.forEach((i) => {
        mapdata.set(i.cname, i);
      });
      for (const [key, value] of mapdata) {
        //判断是否是二级节点
        if (value.isSecondRoot == true) {
          value.gid = this.gid;
          value.summary = value.score;
          delete value.children;
          let res = aspectApi.insert(value);
          if (res.code == 200) {
            this.successpromt("新增维度");
          }
        } else {
          delete value.children;
          value.aid = this.gid;
          value.content = value.cname;
          value.tags = value.cname;
          value.weight = value.score;
          this.insertchild(value);
        }
      }
      this.previewData = [];
      this.paging();
    },
    //扁平化数组
    flattenArray(savedata) {
      return savedata.reduce((pre, cur) => {
        pre.push(cur);
        if (Array.isArray(cur.children) && cur.children.length > 0) {
          pre = pre.concat(this.flattenArray(cur.children));
        }
        return pre;
      }, []);
    },
    async getCopyGoal() {
      this.copyStatu = true;
      let query = {
        pageNo: 1,
        pageSize: 10,
        title: "",
        editorTids: "",
        reviewerTid: "",
        editorNames: "",
      };
      query.state = 5;
      let res = await goalApi.paging(query);
      if (res.code == 200) {
        this.goalTable = res.data.list;
      } else {
        this.$message.error(res.message);
      }
    },
    /**
     * @description: 复制量表的维度
     * @param {*} row
     * @return {*}
     */
    async copyGoal(row) {
      this.queryForm.gid = row.id;
      let res = await aspectApi.paging(this.queryForm);
      let timestap = Date.now().toString();
      if (res.size > 0) {
        const promises = res.list[0].map((item, index) => {
          item.gid = this.gid;
          //生成唯一的id，位数太长存不进去
          item.id = `${timestap.charAt(2)}${timestap.charAt(
            4
          )}${timestap.charAt(6)}${index}`;
          return aspectApi.insert(item);
        });
        try {
          await Promise.all(promises);
          this.successpromt("插入");
        } catch (err) {
          this.$message.error({
            text: "插入失败",
          });
        }
        res.list[1].forEach((item, index) => {
          item.type = item.content;
          item.aid = this.gid;
          item.summary = item.weight;
          //生成唯一的id，位数太长存不进去
          item.id = `${timestap.charAt(2)}${timestap.charAt(
            4
          )}${timestap.charAt(6)}${index}`;
          this.insertchild(item);
        });
        this.queryForm.gid = this.gid;
        this.paging();
        this.copyStatu = false;
      } else {
        this.$message.error("此量表没有维度");
      }
    },
    downloadTemplate() {
      const link = document.createElement("a");
      let objectUrl = `http://36.111.68.174:33380/api/goal/downlaodTemplateWord`;
      // let objectUrl = `${this.URL_ACTION}/api/goal/downlaodTemplateWord`;
      link.href = objectUrl;
      link.download = "下载模板文档";
      document.body.appendChild(link);
      link.click();
      URL.revokeObjectURL(objectUrl);
      document.body.removeChild(link);
    },
  },
  mounted() {
    this.gid = this.$route.query.gid;
    this.getGoal();
    this.queryForm.gid = this.gid;
    this.paging();
  },
};
</script>

<style scoped>
.table {
  width: 100%;
  font-size: 14px;
}
.red {
  color: #ff0000;
}
.aspectmanager_button {
  display: flex;
  justify-content: flex-end;
}
.uploadcontainer {
  margin-left: 10px;
}
</style>

