<template>
  <div class="container">
    <!-- 公告列表显示 -->
    <div class="announcement-list">
      <el-card
        v-for="(item, index) in notifications"
        :key="index"
        class="announcement-card"
        shadow="hover">
        <div class="card-header">
          <span :class="{'is-important': item.isImportant}">公告标题：{{ item.theme }}</span>
          <div class="card-buttons">
            <el-tag v-if="item.isImportant" size="mini" type="danger" class="important-tag">重要</el-tag>
            <el-tag v-else size="mini" type="info" class="unread-tag">普通</el-tag>
          </div>
        </div>
        <div class="card-content">
          <p>
            公告内容：{{ getPreviewContent(item.content) }}
            <span v-if="shouldShowMoreLink(item.content)">
              ...
              <el-button type="text" size="small" @click.stop="showButton(item)" class="more-detail-btn">详情</el-button>
            </span>
          </p>
        </div>
        <div class="card-footer">
          <span class="release-time">发布时间：{{ item.releaseTime }}</span>
          <div class="action-buttons">
            <el-button v-if="!item.isRead" type="text" >确认收到</el-button>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 查看公告详情对话框 -->
    <el-dialog
      title="公告详情"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose">
      <div class="announcement-detail">
        <h2 class="detail-title">标题:{{ currentAnnouncement.theme }}</h2>
        <div class="detail-content">正文:{{ currentAnnouncement.content }}</div>
        <div class="detail-meta">
          <span class="publish-time">{{ currentAnnouncement.releaseTime }}</span>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getNoticeList } from "@/api/notice";

export default {
  data() {
    return {
      notifications: [],
      dialogVisible: false,
      currentAnnouncement: {},
      contentPreviewLimit: 20 // 公告内容预览字数限制
    };
  },

  mounted() {
    this.load();
  },

  methods: {
    load() {
      getNoticeList(query).then(res => { //页面加载时请求公告数据
        if (res.code === 200) {
          console.log('工作台页面返回数据：', res.data);
          this.notifications = res.data.records;
          this.total = parseInt(res.data.total);
          console.log('设置的总条数：', this.total);
        } else {
          this.$message.error("获取公告列表失败");
        }
      }).catch(error => {
        console.error("获取公告列表请求失败:", error);
        this.$message.error("获取公告列表请求失败");
      });
    },

    showButton(item) {
      this.currentAnnouncement = item;
      this.dialogVisible = true;
    },

    handleClose(done) {
      done();
    },

    getPreviewContent(content) {
      if (content.length > this.contentPreviewLimit) {
        return content.substring(0, this.contentPreviewLimit);
      }
      return content;
    },

    shouldShowMoreLink(content) {
      return content.length > this.contentPreviewLimit;
    }
  }
};
</script>

<style lang="scss" scoped>
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
  flex-grow: 1;
  max-width: 400px; /* 限制搜索框最大宽度 */
  margin-left: 20px; /* 与左侧内容保持距离 */
}

.announcement-list {
  display: block; /* 确保每张卡片独占一行 */
  margin-top: 20px;
}

.announcement-card {
  width: 60%; /* 缩小宽度 */
  margin: 0 auto 15px auto; /* 减小上下间距 */
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
    transform: translateY(-2px);
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 15px; /* 减小内边距 */
    border-bottom: 1px solid #ebeef5;
    background-color: #f8f8f8;

    span {
      font-size: 16px; /* 稍微减小标题字体 */
      font-weight: bold;
      color: #303133;
    }

    .is-important {
      color: #f56c6c; /* 重要公告标题颜色 */
    }

    .card-buttons {
      display: flex;
      gap: 8px; /* 标签之间间距 */

      .el-tag {
        height: 24px; /* 统一标签高度 */
        line-height: 22px; /* 调整行高 */
        padding: 0 10px; /* 调整内边距 */
        border-radius: 4px; /* 略微圆角 */
        font-size: 12px; /* 字体大小 */
        font-weight: bold;
        border: none; /* 移除边框 */
      }

      .el-tag--danger.important-tag {
        background-color: #f56c6c; /* 鲜艳红色 */
        color: #fff; /* 白色文字 */
        box-shadow: 0 2px 4px rgba(245, 108, 108, 0.3); /* 柔和阴影 */
      }

      .el-tag--warning {
        background-color: #e6a23c; /* 鲜艳黄色 */
        color: #fff; /* 白色文字 */
        box-shadow: 0 2px 4px rgba(230, 162, 60, 0.3); /* 柔和阴影 */
      }

      .el-tag--success {
        background-color: #67c23a; /* 鲜艳绿色 */
        color: #fff; /* 白色文字 */
        box-shadow: 0 2px 4px rgba(103, 194, 58, 0.3); /* 柔和阴影 */
      }
    }
  }

  .card-content {
    padding: 15px; /* 减小内边距 */
    line-height: 1.6; /* 稍微减小行高 */
    color: #606266;
    font-size: 14px;

    p {
      margin: 0;
      /* 限制内容显示行数 */
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 3; /* 显示3行 */
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .more-detail-btn {
      margin-left: 5px; /* 与省略号的间距 */
      vertical-align: baseline; /* 保持与文本基线对齐 */
    }
  }

  .card-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 15px; /* 减小内边距 */
    border-top: 1px solid #ebeef5;
    background-color: #f8f8f8;
    font-size: 13px;
    color: #909399;

    .action-buttons {
      display: flex;
      gap: 10px; /* 按钮之间间距 */

      .el-button {
        padding: 0;
      }
    }
  }

  &.is-read {
    opacity: 0.8; /* 已读公告稍微透明 */
  }
}

.pagination-container {
  padding: 20px 0;
  text-align: center;
}

/* 详情对话框样式 */
.announcement-detail {
  display: flex;
  flex-direction: column;
  min-height: 250px; /* 确保有足够高度，让发布时间可以推到底部 */
  padding: 20px 30px; /* 调整整体内边距 */

  .detail-title {
    font-size: 24px; /* 标题字体更大 */
    font-weight: bold; /* 加粗 */
    color: #303133; /* 标题颜色 */
    margin-bottom: 30px; /* 增大标题与内容间的间距 */
    text-align: left; /* 标题左对齐 */
  }

  .detail-content {
    font-size: 16px; /* 内容字体略大 */
    color: #404040; /* 正文颜色，与标题区分 */
    line-height: 1.8; /* 调整行高 */
    white-space: pre-wrap; /* 保留换行符 */
    text-align: left; /* 内容左对齐 */
    padding: 0 20px; /* 左右缩进 */
    flex-grow: 1; /* 让内容区尽可能占据剩余空间，将发布时间推到底部 */
    overflow-y: auto; /* 如果内容太多可以滚动 */
  }

  .detail-meta {
    font-size: 13px;
    color: #909399; /* 发布时间颜色 */
    text-align: right; /* 发布时间右对齐 */
    margin-top: auto; /* 将发布时间推到底部 */
    padding-top: 15px; /* 与内容间的间距 */
    padding-right: 20px; /* 与右侧的距离 */
  }
}
</style>
