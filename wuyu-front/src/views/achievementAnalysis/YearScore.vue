<template>
  <div class="grades-container">
    <div class="welcome-message">
      <!-- 根据 identity 渲染不同的欢迎信息 -->
      <h2>{{ welcomeMessage }}</h2>
    </div>

    <!-- 如果 identity 为 2 或 3，显示班级列表 -->
    <div v-if="identity === 2 || identity === 3">
      <div v-for="(grade, index) in grades" :key="index" class="grade-card">
        <div class="grade-info">
          <span class="grade-name">{{ grade.grade }} - {{ grade.className }}</span>
        </div>
        <div class="grade-action">
          <button @click="navigateToClass(grade.grade, grade.className)" class="navigate-button">查看班级</button>
        </div>
      </div>
    </div>
    <div v-if="identity === 0 || identity === 1">
      <div v-for="(grade, index) in grades1" :key="index" class="grade-card">
        <div class="grade-info">
          <span class="grade-name">{{ grade.grade }} - {{ grade.className }}</span>
        </div>
        <div class="grade-action">
          <button @click="navigateToClass(grade.grade, grade.className)" class="navigate-button">查看班级</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      grades: [
        { grade: '1年级', className: '一班', path: 'analysis' },
        { grade: '2年级', className: '一班', path: 'analysis' },
        { grade: '3年级', className: '一班', path: 'analysis' },
      ],
      grades1: [
        { grade: '1年级', className: '一班', path: 'analysis' },
        { grade: '1年级', className: '二班', path: 'analysis' },
        { grade: '1年级', className: '三班', path: 'analysis' },
        { grade: '1年级', className: '四班', path: 'analysis' },
        { grade: '1年级', className: '五班', path: 'analysis' },
        { grade: '1年级', className: '六班', path: 'analysis' },
        { grade: '2年级', className: '一班', path: 'analysis' },
        { grade: '2年级', className: '二班', path: 'analysis' },
        { grade: '2年级', className: '三班', path: 'analysis' },
        { grade: '2年级', className: '四班', path: 'analysis' },
        { grade: '2年级', className: '五班', path: 'analysis' },
        { grade: '2年级', className: '六班', path: 'analysis' },

        { grade: '3年级', className: '一班', path: 'analysis' },
        { grade: '3年级', className: '二班', path: 'analysis' },
        { grade: '3年级', className: '三班', path: 'analysis' },
        { grade: '3年级', className: '四班', path: 'analysis' },
        { grade: '3年级', className: '五班', path: 'analysis' },
        { grade: '3年级', className: '六班', path: 'analysis' },

        { grade: '4年级', className: '一班', path: 'analysis' },
        { grade: '4年级', className: '二班', path: 'analysis' },
        { grade: '4年级', className: '三班', path: 'analysis' },
        { grade: '4年级', className: '四班', path: 'analysis' },
        { grade: '4年级', className: '五班', path: 'analysis' },
        { grade: '4年级', className: '六班', path: 'analysis' },

        { grade: '5年级', className: '一班', path: 'analysis' },
        { grade: '5年级', className: '二班', path: 'analysis' },
        { grade: '5年级', className: '三班', path: 'analysis' },
        { grade: '5年级', className: '四班', path: 'analysis' },
        { grade: '5年级', className: '五班', path: 'analysis' },
        { grade: '5年级', className: '六班', path: 'analysis' },

        { grade: '6年级', className: '一班', path: 'analysis' },
        { grade: '6年级', className: '二班', path: 'analysis' },
        { grade: '6年级', className: '三班', path: 'analysis' },
        { grade: '6年级', className: '四班', path: 'analysis' },
        { grade: '6年级', className: '五班', path: 'analysis' },
        { grade: '6年级', className: '六班', path: 'analysis' },
      ],
      identity: '',
      welcomeMessage: '',
    };
  },
  methods: {
    navigateToClass(grade, className) {
      // 使用 Vue Router 跳转到指定路径，并通过查询参数传递 grade 和 className
      this.$router.push({
        path: 'analysis',
        query: {
          grade: grade,
          className: className
        }
      });
    }
  },
  created() {
    const userInfoString = window.localStorage.getItem("UserInfo");

    // 如果没有获取到 UserInfo，进行调试
    if (!userInfoString) {
      console.error("没有获取到 UserInfo！");
      return;
    }

    try {
      // 使用 JSON.parse() 将 JSON 字符串解析成 JavaScript 对象
      const jsonObject = JSON.parse(userInfoString);

      // 获取 identity 和 id 的值
      this.identity = jsonObject.identity;
      this.id = jsonObject.id;
      console.log("identity:", this.identity);  // 调试输出 identity

      // 根据 identity 设置 welcomeMessage
      if (this.identity === 0) {
        this.welcomeMessage = '校长您好,所有的班级如下';
      } else if (this.identity === 1) {
        this.welcomeMessage = '教务处您好,所有的班级如下';
      } else if (this.identity === 2) {
        this.welcomeMessage = '班主任您好,您教的班级如下';
      } else if (this.identity === 3) {
        this.welcomeMessage = '老师您好,您教的班级如下';
      } else {
        this.welcomeMessage = '欢迎您';
      }

      console.log("welcomeMessage:", this.welcomeMessage);  // 调试输出 welcomeMessage
    } catch (error) {
      console.error("解析 UserInfo 失败：", error);
    }
  }
}
</script>

<style scoped>
/* 整体容器布局 */
.grades-container {
  padding: 20px;
  font-family: 'Arial', sans-serif;
}

/* 欢迎信息样式 */
.welcome-message {
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

/* 班级卡片样式 */
.grade-card {
  background-color: #f9f9f9;
  border-radius: 8px;
  margin: 10px 0;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 班级信息文本 */
.grade-info {
  display: flex;
  flex: 1;  /* 使班级信息部分占据剩余空间 */
}

.grade-name {
  font-size: 18px;
  color: #333;
  font-weight: 500;
}

/* 操作按钮部分 */
.grade-action {
  display: flex;
  align-items: center;
}

/* 按钮样式 */
.navigate-button {
  padding: 8px 16px;
  background-color: #409EFF;
  color: #fff;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}

.navigate-button:hover {
  background-color: #66b1ff;
}
</style>
