<template>
  <div>
    <!-- 轮播图区域 -->
    <div class="carousel-section">
      <div class="carousel">
        <div
          class="carousel-item"
          v-for="(image, index) in images"
          :key="index"
          :class="{ active: index === currentIndex }"
        >
          <img :src="image" alt="Carousel image" />
        </div>

        <div class="indicator">
          <span
            class="dot"
            v-for="(image, index) in images"
            :key="index"
            :class="{ active: index === currentIndex }"
            @click="goToImage(index)"
          ></span>
        </div>
      </div>
    </div>

    <!-- 奖项查询与管理区域 -->
    <div class="award-section">
      <!-- 查询区域 -->
      <el-input placeholder="请输入关键字查询" v-model="queryKey" @input="queryAwards"></el-input>

      <!-- 表格区域 -->
      <el-table :data="filteredAwards" style="width: 100%">
        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column prop="date" label="日期"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="danger" @click="deleteAward(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="prev, pager, next"
        ></el-pagination>
      </div>

      <!-- 添加奖项区域 -->
      <el-dialog title="添加奖项" :visible.sync="addDialogVisible">
        <el-form :model="newAward">
          <!-- 先写日期 -->
          <el-form-item label="日期">
            <el-input
              v-model="newAward.date"
              placeholder="请输入日期（格式：YYYY-MM-DD）"
            ></el-input>
          </el-form-item>
          <!-- 再写名称 -->
          <el-form-item label="名称">
            <el-input v-model="newAward.name"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button @click="addDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addAward">确定添加</el-button>
        </div>
      </el-dialog>

      <el-button type="primary" @click="addDialogVisible = true">添加奖项</el-button>
    </div>
  </div>
</template>

<script>
import { getAward, addAward as addAwardApi, deleteAward as deleteAwardApi } from "@/api/fuScale";
import { MessageBox } from "element-ui";

export default {
  data() {
    return {
      // 轮播图相关数据
      images: [
        require('@/assets/award/1.jpg'),
        require('@/assets/award/2.jpg'),
        require('@/assets/award/3.jpg'),
      ],
      currentIndex: 0,
      interval: null,

      // 奖项相关数据
      awards: [],
      filteredAwards: [],
      queryKey: "",
      addDialogVisible: false,
      newAward: {
        date: "",
        name: ""
      },
      // 分页相关数据
      currentPage: 1,
      pageSize: 5,
      total: 0
    };
  },
  mounted() {
    // 启动轮播图自动播放
    this.startAutoPlay();
    // 获取奖项数据并计算总页数
    this.fetchAwards();
  },
  beforeDestroy() {
    clearInterval(this.interval); // 清除轮播图定时器以避免内存泄漏
  },
  methods: {
    // 轮播图相关方法
    startAutoPlay() {
      this.interval = setInterval(() => {
        this.currentIndex =
          (this.currentIndex + 1) % this.images.length;
      }, 3000); // 每3秒切换一次
    },
    goToImage(index) {
      this.currentIndex = index;
    },

    // 奖项相关方法
    // 获取所有奖项数据并计算总页数
    async fetchAwards() {
      try {
        const res = await getAward();
        this.awards = res;
        this.total = this.awards.length;
        this.filteredAwards = this.getFilteredAwards();
      } catch (error) {
        console.error("获取奖项数据失败:", error);
      }
    },
    // 根据当前页码和查询关键字筛选数据
    getFilteredAwards() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      let tempAwards = this.awards;
      if (this.queryKey) {
        tempAwards = tempAwards.filter(award =>
          award.name.includes(this.queryKey)
        );
      }
      return tempAwards.slice(startIndex, endIndex);
    },
    // 查询奖项数据（切换页码或关键字查询时调用）
    queryAwards() {
      this.filteredAwards = this.getFilteredAwards();
    },
    // 删除奖项数据
    async deleteAward(id) {
      try {
        await deleteAwardApi(id);
        this.fetchAwards();
      } catch (error) {
        console.error("删除奖项失败:", error);
      }
    },
    // 添加奖项数据
    async addAward() {
      // 验证日期格式是否正确
      const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
      if (!dateRegex.test(this.newAward.date)) {
        MessageBox.alert(
          "日期格式不正确，请输入正确的日期格式（YYYY-MM-DD）",
          "错误提示",
          {
            confirmButtonText: "确定"
          }
        );
        return;
      }

      try {
        // 调用API添加奖项
        await addAwardApi(this.newAward);

        // 刷新奖项列表和总页数
        this.fetchAwards();

        // 关闭对话框
        this.addDialogVisible = false;

        // 清空新奖项数据
        this.newAward = {
          date: "",
          name: ""
        };

        MessageBox.alert("奖项添加成功！", "成功提示", {
          confirmButtonText: "确定"
        });
      } catch (error) {
        console.error("添加奖项失败:", error);

        MessageBox.alert("添加奖项失败，请重试。", "错误提示", {
          confirmButtonText: "确定"
        });
      }
    },
    // 处理页码切换
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.queryAwards();
    }
  }
};
</script>

<style scoped>
/* 轮播图样式 */
.carousel-section {
  margin-bottom: 20px; /* 轮播图与奖项区域之间的间距 */
}
.carousel {
  position: relative;
  width: 100%;
  max-width: 900px; /* 将轮播图的最大宽度调整为900px，可根据需求进一步调整 */
  overflow: hidden;
}
.carousel-item {
  display: none;
  transition: opacity 0.5s ease; /* 添加淡入效果 */
}
.carousel-item.active {
  display: block;
}
.carousel img {
  width: 100%;
  height: 400px; /* 设置图片的固定高度，可根据需求调整 */
  object-fit: cover; /* 确保图片填充整个容器，可能会裁剪部分图片 */
}

/* 轮播图分页器样式 */
.indicator {
  text-align: center;
  margin-top: 10px; /* 分隔符和轮播图之间的间距 */
}
.dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  margin: 0 5px; /* 小圆圈之间的间距 */
  background-color: #bbb; /* 默认颜色 */
  border-radius: 50%; /* 圆形 */
  cursor: pointer;
  transition: background-color 0.3s; /* 悬停效果 */
}
.dot.active {
  background-color: #717171; /* 当前选中的颜色 */
}
.dot:hover {
  background-color: #bbb; /* 鼠标悬停时变化效果 */
}

/* 奖项区域样式 */
.award-section {
  background-color: #f9f9f9; /* 奖项区域背景色，可根据需求调整 */
  padding: 20px; /* 奖项区域内边距 */
}

/* 分页样式 */
.pagination {
  margin-top: 10px;
  text-align: center;
}
</style>
