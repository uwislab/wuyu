<template>
  <div class="container">
    <div class="login-wrapper">
      <div class="header">五育后台管理系统</div>
      <div class="form-wrapper">
        <div class="login-type-switch">
          <span
            :class="{ active: loginType === 'password' }"
            @click="switchLoginType('password')"
          >
            密码登录
          </span>
          <span
            :class="{ active: loginType === 'sms' }"
            @click="switchLoginType('sms')"
          >
            验证码登录
          </span>
        </div>

        <!-- 密码登录表单 -->
        <div v-if="loginType === 'password'">
          <input
            v-model="loginForm.username"
            type="text"
            name="username"
            placeholder="请输入用户名"
            class="input-item"
          />
          <input
            v-model="loginForm.password"
            type="password"
            name="password"
            placeholder="请输入密码"
            class="input-item"
            @keyup.enter="handleLogin"
          />
          <div class="btn" @click="handleLogin">登录</div>
        </div>

        <!-- 验证码登录表单 -->
        <div v-else>
          <input
            v-model="smsForm.phoneNumber"
            type="text"
            name="phoneNumber"
            placeholder="请输入手机号"
            class="input-item"
          />
          <div class="sms-code-container">
            <input
              v-model="smsForm.code"
              type="text"
              name="code"
              placeholder="请输入验证码"
              class="input-item sms-input"
              @keyup.enter="handleSmsLogin"
            />
            <button
              class="send-code-btn"
              :disabled="cooldown > 0"
              @click="sendCode"
            >
              {{ cooldown > 0 ? `${cooldown}秒后重试` : '发送验证码' }}
            </button>
          </div>
          <div class="btn" @click="handleSmsLogin">登录</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login, sendSmsCode, loginBySms } from "@/api/user";
import { setToken } from "@/utils/auth";
import router from "@/router";

export default {
  name: "Login",
  data() {
    return {
      loginType: "password", // 默认使用密码登录
      loginForm: {
        username: "",
        password: "",
        identityId: "", // 默认为空,需要用户选择
      },
      smsForm: {
        phoneNumber: "",
        code: "",
      },
      cooldown: 0, // 验证码冷却时间
      timer: null, // 计时器

      loading: false,
      redirect: undefined,
      otherQuery: {},
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query;
        if (query) {
          this.redirect = query.redirect;
          this.otherQuery = this.getOtherQuery(query);
        }
      },
      immediate: true,
    },
  },
  beforeDestroy() {
    // 清除计时器
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
  methods: {
    // 切换登录方式
    switchLoginType(type) {
      this.loginType = type;
    },

    // 发送验证码
    sendCode() {
      // 验证手机号
      if (!this.smsForm.phoneNumber) {
        this.$message.error("请输入手机号");
        return;
      }

      const phoneRegex = /^1[3-9]\d{9}$/;
      if (!phoneRegex.test(this.smsForm.phoneNumber)) {
        this.$message.error("请输入正确的手机号格式");
        return;
      }

      // 发送验证码请求
      sendSmsCode(this.smsForm.phoneNumber)
        .then(response => {
          if (response.code === 200) {
            console.log(response.data.code)
            this.$message.success("验证码已发送，请注意查收");
            // 设置冷却时间
            this.cooldown = 60;
            this.timer = setInterval(() => {
              this.cooldown--;
              if (this.cooldown <= 0) {
                clearInterval(this.timer);
              }
            }, 1000);
          } else {
            this.$message.error(response.message || "验证码发送失败");
          }
        })
        .catch(error => {
          console.error("发送验证码失败:", error);
          this.$message.error("验证码发送失败，请稍后重试");
        });
    },

    // 验证码登录
    handleSmsLogin() {
      // 表单验证
      if (!this.smsForm.phoneNumber) {
        this.$message.error("请输入手机号");
        return;
      }
      if (!this.smsForm.code) {
        this.$message.error("请输入验证码");
        return;
      }

      this.loading = true;

      // 调用验证码登录接口
      loginBySms(this.smsForm)
        .then(response => {
          if (response.code === 200) {
            let user = response.data;
            this.handleLoginSuccess(user);
          } else {
            this.$message.error(response.message || "登录失败");
            this.loading = false;
          }
        })
        .catch(error => {
          console.error("登录失败:", error);
          this.$message.error("登录失败，请稍后重试");
          this.loading = false;
        });
    },

    handleLogin() {
      // 表单验证
      if (!this.loginForm.username) {
        this.$message.error("请输入用户名");
        return;
      }
      if (!this.loginForm.password) {
        this.$message.error("请输入密码");
        return;
      }

      this.loading = true;

      // 调用登录接口
      login(this.loginForm)
        .then((response) => {
          if (response.code === 200) {
            let user = response.data;
            this.handleLoginSuccess(user);
          } else {
            this.$message.error(response.message || "登录失败");
            this.loading = false;
          }
        })
        .catch((error) => {
          console.error("登录失败:", error);
          this.$message.error("登录失败,请稍后重试");
          this.loading = false;
        });
    },

    // 处理登录成功
    handleLoginSuccess(user) {
      // 这是包含 identity 的 WebUser 对象
      console.log(user.identity);
      if(user.identity == 0) {
        this.$message.success("登录成功，校长");
      } else if(user.identity == 1) {
        this.$message.success("登录成功，教务主任");
      } else if(user.identity == 2) {
        this.$message.success("登录成功，班主任");
      } else if(user.identity == 3) {
        this.$message.success("登录成功，教师");
      } else if(user.identity == 4) {
        this.$message.success("登录成功，管理员");
      }

      console.log(user);
      // 存储用户信息到 store
      this.$store.commit("user/SET_USER_INFO", user);
      // 确保同时设置loginUser
      this.$store.commit("user/SET_LOGIN_USER", user);

      // 保存到localStorage，使用统一的键名
      window.localStorage.setItem(
        "userInfo",
        JSON.stringify(user)
      );
      // Set the permission group of this login in user.
      window.localStorage.setItem(
        "PermissionType",
        this.loginForm.identityId
      );
      // 设置 token
      setToken(user.token);

      // 根据角色跳转
      this.loading = false;
      router.push({ path: this.redirect || "/", query: this.otherQuery });
    },

    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== "redirect") {
          acc[cur] = query[cur];
        }
        return acc;
      }, {});
    },
  },
};
</script>

<style scoped>
/* 主容器 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  background: linear-gradient(to right, #fbc2eb, #a6c1ee); /* 渐变背景 */
  position: relative;
  overflow: hidden;
}

/* 登录板块 */
.login-wrapper {
  width: 100%;
  max-width: 480px; /* 增加最大宽度限制 */
  min-width: 320px;
  height: auto;
  border-radius: 20px;
  padding: 3rem;
  background-color: #fff;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: all 0.3s ease;
}

.header {
  font-size: 38px;
  font-weight: bold;
  text-align: center;
  line-height: 100px;
}

/* 输入区域 */
.form-wrapper {
  width: 100%;
}

/* 登录方式切换 */
.login-type-switch {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.login-type-switch span {
  padding: 8px 15px;
  margin: 0 10px;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  transition: all 0.3s;
}

.login-type-switch span.active {
  color: #409EFF;
  border-bottom: 2px solid #409EFF;
  font-weight: bold;
}

/* 验证码输入框容器 */
.sms-code-container {
  display: flex;
  margin-bottom: 1.5rem;
}

.sms-input {
  flex: 1;
  margin-bottom: 0;
}

.send-code-btn {
  width: 120px;
  margin-left: 10px;
  background: #a6c1ee;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.send-code-btn:hover:not(:disabled) {
  background: #8baae8;
}

.send-code-btn:disabled {
  background: #cccccc;
  cursor: not-allowed;
}

/* 输入框 */
.input-item {
  width: 100%;
  padding: 1rem;
  margin-bottom: 1.5rem;
  font-size: 1.6rem;
  border: 2px solid #ddd;
  border-radius: 10px;
  outline: none;
  transition: all 0.3s ease;
  box-sizing: border-box;
}

.input-item::placeholder {
  font-size: 1.6rem;
  color: #bbb;
  letter-spacing: 0.5px;
}

.input-item:focus {
  border-color: #a6c1ee;
  box-shadow: 0 0 5px rgba(166, 193, 238, 0.6);
}

/* 确认按钮 */
.btn {
  width: 100%;
  padding: 1.2rem;
  background: linear-gradient(to right, #a6c1ee, #fbc2eb);
  color: white;
  font-size: 1.8rem;
  border-radius: 30px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
  text-transform: uppercase;
  font-weight: bold;
  letter-spacing: 0.5px;
}

.btn:hover {
  background: linear-gradient(to right, #fbc2eb, #a6c1ee);
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.btn:active {
  transform: translateY(1px); /* 点击时按钮有微小下移 */
}

/* 错误提示 */
.error-message {
  font-size: 1.4rem;
  color: red;
  margin-top: 10px;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .login-wrapper {
    padding: 2rem;
  }

  .header {
    font-size: 2rem;
  }

  .input-item {
    font-size: 1.4rem;
    padding: 1rem;
  }

  .btn {
    font-size: 1.6rem;
    padding: 1rem;
  }
}

@media (max-width: 480px) {
  .login-wrapper {
    padding: 1.5rem;
  }

  .header {
    font-size: 1.8rem;
  }

  .input-item {
    font-size: 1.2rem;
    padding: 0.8rem;
  }

  .btn {
    font-size: 1.4rem;
    padding: 0.8rem;
  }
}

/* 背景动效 */
@keyframes gradientMove {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

.container {
  animation: gradientMove 5s ease infinite;
}
</style>
