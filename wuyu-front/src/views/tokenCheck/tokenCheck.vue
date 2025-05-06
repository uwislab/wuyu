<template>
  <div class="container">
    <div class="login-wrapper">
      <div class="header">五育后台管理系统</div>
      <div style="font-size: 20px; margin-bottom: 10px">
        您的身份:
        <el-select
          class="identity_selector"
          placeholder="请选择系统登录角色"
          :value="selectedIdentity.identityInfo"
          @change="selectIdentity"
        >
          <el-option
            v-for="item in identityList"
            :label="item.identityInfo"
            :value="item"
          ></el-option>
        </el-select>
      </div>
      <div class="form-wrapper">
        <input
          v-model="loginForm.username"
          type="text"
          name="username"
          placeholder="username"
          class="input-item"
        />
        <input
          v-model="loginForm.password"
          type="password"
          name="password"
          placeholder="password"
          class="input-item"
        />
        <div class="btn" @click="handleLogin">登录</div>
      </div>
    </div>
  </div>
</template>

<script>
import SocialSign from "@/views/login/components/SocialSignin";
import {getIdentity, login} from "@/api/user";
import {setToken} from "@/utils/auth";
import router from "@/router";

export default {
  name: "tokenCheck",
  components: { SocialSign },
  data() {
    return {
      loginForm: {
        username: "admin1",
        password: "123456",
        authority: 1,
      },
      identityList: [],
      selectedIdentity: {
        identityId: 1,
        identityInfo: "校长",
      },
      passwordType: "password",
      capsTooltip: false,
      loading: false,
      showDialog: false,
      redirect: undefined,
      otherQuery: {},
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query;
        console.log(route);
        if (query) {
          this.redirect = query.redirect;
          this.otherQuery = this.getOtherQuery(query);
        }
      },
      immediate: true,
    },
  },
  mounted() {
    // 获取权限列表
    getIdentity().then((res) => {
      this.identityList = res.data;
    });
    if (this.loginForm.username === "") {
      this.$refs.username.focus();
    } else if (this.loginForm.password === "") {
      this.$refs.password.focus();
    }
  },
  methods: {
    checkCapslock(e) {
      const { key } = e;
      this.capsTooltip = key && key.length === 1 && key >= "A" && key <= "Z";
    },
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin() {
      //设置token 才能跳转
      setToken("admin");
      //跳转路径
      this.loading = false;
      router.push({ path: this.redirect || '/', query: this.otherQuery})
      login(this.loginForm).then(response => {
        console.log(this.loginForm);
        console.log(response)
        if (response.code == 200) {
          if (response.data.identity == 1 || response.data.identity == 2) { // 管理员用户
            console.log("welcome manager")
            sessionStorage.setItem("userInfo",JSON.stringify(response.data))//存
            //设置token 才能跳转
            setToken("admin");
            //跳转路径
            this.loading = false;
            router.push({ path: this.redirect || '/', query: this.otherQuery })
          } else { // 教师用户
            console.log("welcome teacher")
            //设置token 才能跳转
            setToken("admin");
            //跳转路径
            this.loading = false;
            let userInfo = response.data;
            console.log(userInfo)
            router.push({ path: '/teacher_workspace/teacherWork', query: userInfo })
          }
        }
      }).catch(() => {this.loading = false})
    },

    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== "redirect") {
          acc[cur] = query[cur];
        }
        return acc;
      }, {});
    },

    selectIdentity(data) {
      // 为下拉选择框显示内容赋值
      this.selectedIdentity.identityId = data.identityId;
      this.selectedIdentity.identityInfo = data.identityInfo;
      // 为登录信息中的权限参数赋值
      this.loginForm.authority = data.identityId;
    },
  },
}
// token验证，生成时和请求验证时的都可以调用！返回token状态，false为已过期
function tokenVerify(token) {
  let status;
  jwt.verify(token, 'userLogin', (err, data) => {
    if (err) {// 过期token
      status = false
    } else {// 有效token
      status = true
    }
  })
  return status
}

// token生成，只用于登录时调用！通过调用token验证函数，判断token是否过期，若未过期，则返回true，前端token值不变；若过期，则重新生成，前端重新保存token
function tokenGenerate(token, account, password, time) {
  let status = tokenVerify(token);
  if (status) {// 若token未过期
    token = true;
  } else {// 若token已过期
    token = jwt.sign({ account: account, password: password }, 'userLogin', { expiresIn: time });
  }
  return token
}




</script>

<style scoped>
/* 主容器 */
.container {
  width: 100%;
  height: 100%;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
}

/* 登录板块 */
.login-wrapper {
  background-color: #fff;
  width: 520px;
  height: 600px;
  border-radius: 15px;
  padding: 0 50px;
  position: relative;
  left: 50%;
  top: 45%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
/* 标题 */
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
/* 输入框 */
.input-item {
  display: block;
  width: 100%;
  margin-bottom: 20px;
  border: 0;
  padding: 10px;
  border-bottom: 1px solid black;
  font-size: 20px;
  outline: none;
}

.input-item::placeholder {
  text-transform: uppercase;
}
/* 确认按钮 */
.btn {
  text-align: center;
  padding: 10px;
  width: 100%;
  margin-top: 40px;
  background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
  color: #fff;
  font-size: large;
}
</style>
