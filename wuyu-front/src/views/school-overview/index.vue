<template>
  <div class="container">
    <div class="header">
      <span>通知 · 公告</span>
      <el-button type="primary" @click="handleAdd">添加</el-button>
    </div>

    <el-dialog
      title="公告详情"
      :visible.sync="dialogVisible"
      width="700px"
      :before-close="handleClose">
      <div class="detail-container">
        <div class="detail-header">
          <h2 class="detail-title">{{ currentAnnouncement.theme }}</h2>
          <div class="detail-meta">
            <span>发布时间: {{ formatFullDate(currentAnnouncement.releaseTime) }}</span>
            <span>发布人: {{ currentAnnouncement.createdByName || '管理员' }}</span>
            <el-tag v-if="currentAnnouncement.isImportant" type="danger" size="small">重要</el-tag>
          </div>
        </div>

        <div class="detail-content">
          <pre>{{ currentAnnouncement.content }}</pre>
        </div>

        <div class="detail-footer">
          <span>公告ID: {{ currentAnnouncement.id }}</span>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">关 闭</el-button>
      <el-button
        type="primary"
        @click="ok()">
        确定
      </el-button>
    </span>
    </el-dialog>
    <!-- 公告列表 -->
    <div class="announcement-list">
      <!-- 添加加载动画 -->
      <div v-if="loading" class="loading-overlay">
        <div class="spinner"></div>
        <div class="loading-text">加载中...</div>
      </div>
      <el-card
        v-for="(item, index) in notifications"
        :key="index"
        class="announcement-card"
        :class="{'is-read': item.isRead}"
        @click="showButton(item)"
        shadow="hover">
        <div class="card-content">
          <div class="date-section">
            <div class="date-circle">
              <span class="day">{{ formatDate(item.releaseTime).day }}</span>
              <span class="month">{{ formatDate(item.releaseTime).month }}</span>
            </div>
          </div>
          <div class="content-section">
            <div class="title-row">
              <h3 class="announcement-title">{{ item.theme }}</h3>
              <div class="tags">
                <el-tag
                  v-if="item.isImportant"
                  type="danger"
                  size="small"
                  effect="dark">
                  重要
                </el-tag>
              </div>
            </div>
            <p class="announcement-preview">{{ item.content }}</p>
          </div>
          <div class="action-section">
            <el-button
              type="text"
              size="small"
              @click.stop="showButton(item)"
              class="detail-btn">
              详情
            </el-button>
            <el-button
              type="text"
              class="delete-btn"
              @click.stop="deleteButton(item.id)">
              <i class="el-icon-delete"></i>
            </el-button>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 分页组件 -->
    <div class="pagination-container">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background>
      </el-pagination>
    </div>

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
      loading: false,
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
  computed: {
    readCount() {
      return this.notifications.filter(item => item.isRead).length;
    },
    unreadCount() {
      return this.notifications.filter(item => !item.isRead).length;
    },
    canPublishNotice() {
      try {
        const userInfoString = localStorage.getItem("UserInfo");
        if (userInfoString) {
          const userInfo = JSON.parse(userInfoString);
          const identity = userInfo.identity;
          return identity === 0 || identity === 4;
        }
      } catch (e) {
        console.error("从 localStorage 解析 UserInfo 失败或获取 identity 失败", e);
      }
      return false;
    }
  },

  watch: {
    searchQuery() {
      this.pageNum = 1; // 搜索时重置页码
      this.load();
    },
    pageNum() {
      this.load();
    },
    pageSize() {
      this.load();
    }
  },
  mounted() {
    this.load()
    this.getIdentityOptions()
  },

  methods: {
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
    load() {
      this.loading = true; // 开始加载时设为true
      const query = {
        userId: null,
        keyword: this.searchQuery,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        total: this.total
      };

      getNoticeList(query).then(res => {
        if (res.code === 200) {
          console.log('学校概览页面返回数据：', res.data);
          this.notifications = res.data.records;
          this.total = parseInt(res.data.total);
          console.log('设置的总条数：', this.total);
        } else {
          this.$message.error("获取公告列表失败");
        }
        this.loading = false; // 加载完成设为false
      }).catch(error => {
        console.error("获取公告列表请求失败:", error);
        this.$message.error("获取公告列表请求失败");
        this.loading = false; // 加载失败也要设为false
      });
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      return {
        day: date.getDate(),
        month: `${date.getMonth() + 1}月`
      };
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {}
    },
    formatFullDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
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
    ok() {
      this.dialogVisible = false;
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
      this.currentAnnouncement  = content;
    },

    deleteButton(id) {
      this.$confirm('确认删除该公告?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteNotice(id).then(res => {
          if (res.code === 200) {
            this.$message.success("删除成功");
            this.load();
          } else {
            this.$message.error("删除失败: " + res.message);
          }
        }).catch(error => {
          console.error("删除公告请求失败:", error);
          this.$message.error("删除公告请求失败");
        });
      }).catch(() => {});
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      // this.load() 会被 watch 触发，无需在此再次调用
    },

    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      // this.load() 会被 watch 触发，无需在此再次调用
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
.container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.statistics {
  display: flex;
  gap: 10px;
}

.search-box {
  width: 300px; /* 调整搜索框宽度 */
  margin-right: 20px; /* 添加右边距 */
}

.announcement-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  padding: 10px;
}

.announcement-card {
  cursor: pointer;
  transition: all 0.3s ease;
}

.announcement-card:hover {
  transform: translateY(-5px);
}

.card-content {
  display: flex;
  align-items: flex-start;
  gap: 15px;
}

.date-section {
  flex-shrink: 0;
}

.date-circle {
  width: 60px;
  height: 60px;
  background-color: #409EFF;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
}

.date-circle .day {
  font-size: 20px;
  font-weight: bold;
}

.date-circle .month {
  font-size: 14px;
}

.content-section {
  flex-grow: 1;
  min-width: 0;
}

.title-row {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 8px;
}

.announcement-title {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.announcement-preview {
  margin: 0;
  font-size: 14px;
  color: #606266;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.action-section {
  flex-shrink: 0;
}

.delete-btn {
  color: #909399;
  padding: 5px;
}

.delete-btn:hover {
  color: #F56C6C;
}

.announcement-detail {
  padding: 20px;
}

.detail-title {
  margin: 0 0 20px 0;
  font-size: 20px;
  color: #303133;
}

.detail-meta {
  margin-bottom: 20px;
  color: #909399;
  font-size: 14px;
}

.detail-content {
  line-height: 1.6;
  color: #606266;
  white-space: pre-wrap;
}

.is-read {
  opacity: 0.8;
}

.is-read .announcement-title {
  color: #909399;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 100;
  border-radius: 8px;
}
.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid #f3f3f3;
  border-top: 5px solid #409eff;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading-text {
  margin-top: 15px;
  font-size: 16px;
  color: #409eff;
  font-weight: bold;
}
</style>
