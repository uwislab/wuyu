<template>
  <div class="container">
    <div class="header">
      <span>通知 · 公告</span>
      <el-button type="primary" @click="handleAdd">添加</el-button>
    </div>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="70%"
      :before-close="handleClose">
      <span>{{fileContent}}</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>


    <!-- 添加公告对话框 -->
    <el-dialog
      title="发布公告"
      :visible.sync="dialogFormVisible"
      width="60%">
      <el-form
        :model="form"
        :rules="rules"
        ref="form"
        label-width="80px">
        <el-form-item label="主题" prop="theme">
          <el-input v-model="form.theme" placeholder="请输入公告主题"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input
            type="textarea"
            v-model="form.content"
            :rows="6"
            placeholder="请输入公告内容">
          </el-input>
        </el-form-item>
        <el-form-item label="重要性">
          <el-switch
            v-model="form.isImportant"
            active-text="重要公告">
          </el-switch>
        </el-form-item>
        <el-form-item label="发放对象" prop="identityId">
          <el-select v-model="form.identityId" placeholder="请选择发放对象">
            <el-option
              v-for="option in identityOptions"
              :key="option.identityId"
              :label="option.identityInfo"
              :value="option.identityId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">发 布</el-button>
      </div>
    </el-dialog>

    <div class="content">
      <div class="notification" v-for="(item, index) in notifications" :key="index">
        <el-button class="button-type" type="text" @click="showButton(item.content)">
          <div class="date-circle">
            <span>{{ item.releaseTime }}</span>
          </div>
          <div class="text-content">
            <h3>{{ item.theme }}</h3>
          </div>
        </el-button>

        <el-button class="delete-class" @click="deleteButton(item.id)">
          删除
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {deleteById} from "@/api/fuScale";
import axios from 'axios';
import {addNotice,getIdentityIds,getNoticeList} from "@/api/notice";
import request from "@/utils/request";

export default {
  data() {
    return {
      notifications: [],
      searchQuery: '', // 新增搜索关键词
      dialogVisible: false,
      dialogFormVisible: false,
      currentAnnouncement: {},
      form: {
        theme: '',
        content: '',
        isImportant: false,
        createdBy: null,
        identityId: null // 新增字段：发放对象身份ID
      },
      rules: {
        theme: [
          { required: true, message: '请输入公告主题', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入公告内容', trigger: 'blur' },
          { min: 10, max: 1000, message: '长度在 10 到 1000 个字符', trigger: 'blur' }
        ],
        identityId: [
          { required: true, message: '请选择发放对象', trigger: 'change' }
        ]
      },
      pageNum: 1, // 当前页码
      pageSize: 10, // 每页大小
      total: 0, // 总记录数
      identityOptions: [] // 新增数据属性：身份列表
    };
  },

  mounted() {
    this.load()
    this.getIdentityOptions()
  },

  methods: {

    load() {
      getNoticeList().then(res => {
        this.notifications = res;
        console.log(this.notifications)
      })
    },

    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {}
    },

    save() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          try {
            const userId = this.getCurrentUserId();
            if (!userId) {
              this.$message.error("无法获取当前用户ID，请重新登录。");
              return;
            }
            this.form.createdBy = userId;

            const res = await addNotice(this.form); // 传递整个表单对象，其中已包含 identityId
            if (res.code === 200) {
              this.$message.success("发布成功");
              this.dialogFormVisible = false;
              this.load(); // 重新加载公告列表
            } else {
              this.$message.error(res.message || "发布失败");
            }
          } catch (error) {
            console.error("发布公告请求失败:", error);
            this.$message.error("发布公告失败，请稍后再试");
          }
        } else {
          return false;
        }
      });
    },
    async getIdentityOptions() {//需要注释的地方
      try {
        const res = await getIdentityIds();
        if (res.data.length>0) {
          this.identityOptions = res.data;
          // 如果表单的 identityId 为空，则默认选中第一个选项
          if (this.identityOptions.length > 0 && this.form.identityId === null) {
            this.form.identityId = this.identityOptions[0].identityId;
          }
        } else {
          this.$message.error("获取身份列表失败");
        }
      } catch (error) {
        console.error("获取身份列表请求失败:", error);
        this.$message.error("获取身份列表失败，请稍后再试");
      }
    },
    getCurrentUserId() {
      try {
        const userInfoString = localStorage.getItem("UserInfo");
        if (userInfoString) {
          const userInfo = JSON.parse(userInfoString);
          return userInfo.id;
        }
      } catch (e) {
        console.error("从 localStorage 解析 UserInfo 失败", e);
      }
      return null;
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },

    showButton(content) {
      this.dialogVisible = true;
      this.fileContent = content;
    },

    deleteButton(id) {
      deleteById(id).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    }


  }
};
</script>

<style scoped>
/* 整体页面字体设置 */
body {
  font-family: Arial, sans-serif;
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

/* 容器样式 */
.container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f4f4f4;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* 头部样式 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header span {
  font-size: 20px;
  font-weight: bold;
}

/* 按钮通用样式及交互效果 */
.el-button {
  border-radius: 4px;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.el-button:hover {
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
}

.el-button.primary {
  background-color: #007BFF;
  border-color: #007BFF;
  color: white;
}

.el-button.primary:hover {
  background-color: #0056b3;
}

/* 对话框样式 */
.el-dialog {
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.el-dialog.title {
  font-size: 18px;
  font-weight: bold;
}

/* 对话框页脚按钮样式 */
.dialog-footer.el-button {
  margin: 0 5px;
}

/* 内容区域样式 */
.content {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
}

/* 通知列表项样式 */
.notification {
  background-color: white;
  width: calc(50% - 10px);
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* 日期圈样式 */
.date-circle {
  background-color: #007BFF;
  color: white;
  border-radius: 50%;
  width: 80px;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px;
  font-size: 14px;
}

/* 文本内容样式 */
.text-content {
  color: black;
  padding: 0 20px;
  flex: 1;
}

.text-content h3 {
  margin: 10px 0;
  font-size: 18px;
  font-weight: normal;
}

/* 删除按钮样式 */
.delete-class {
  align-self: flex-end;
  margin: 10px;
  color: #888;
  border-color: #ccc;
  background-color: transparent;
&:hover {
   color: red;
   border-color: red;
 }
}

/* 点击查看内容按钮样式 */
.button-type {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding: 10px;
  text-decoration: none;
  color: inherit;
  border: none;
  background-color: transparent;
&:hover {
   background-color: #f0f0f0;
 }
}

</style>
