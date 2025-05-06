<template>
  <div class="personal-info-container">
    <div class="form-header">
      <h2>个人信息修改</h2>
      <p>请填写您的个人信息</p>
    </div>

    <el-form
      :model="userInfo"
      :rules="rules"
      ref="userInfoForm"
      label-width="80px"
      class="info-form"
    >
      <el-form-item label="昵称" prop="realName">
        <el-input
          v-model="userInfo.realName"
          placeholder="请输入昵称"
          prefix-icon="el-icon-user"
        ></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="userInfo.phone"
          placeholder="请输入手机号"
          prefix-icon="el-icon-mobile-phone"
        ></el-input>
      </el-form-item>

      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="userInfo.gender" class="gender-group">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="所属学校">
        <el-input
          v-model="userInfo.school"
          disabled
          placeholder="请输入昵称"
        ></el-input>
      </el-form-item>
      <el-form-item label="身份">
        <el-input
          v-model="userInfo.identity"
          disabled
          placeholder="请输入昵称"
        ></el-input>
      </el-form-item>

      <el-form-item label="籍贯" prop="birthPlace">
        <el-input
          v-model="userInfo.birthPlace"
          placeholder="请输入籍贯"
          prefix-icon="el-icon-location"
        ></el-input>
      </el-form-item>

      <el-form-item label="政治面貌" prop="politicalAppearance">
        <el-select 
          v-model="userInfo.politicalAppearance" 
          placeholder="请选择政治面貌"
          style="width: 100%"
        >
          <el-option label="群众" value="群众"></el-option>
          <el-option label="团员" value="团员"></el-option>
          <el-option label="党员" value="党员"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="职位" prop="position">
        <el-input
          v-model="userInfo.position"
          placeholder="请输入职位"
          prefix-icon="el-icon-user"
        ></el-input>
      </el-form-item>

      <el-form-item label="职称" prop="title">
        <el-input
          v-model="userInfo.title"
          placeholder="请输入职称"
          prefix-icon="el-icon-medal"
        ></el-input>
      </el-form-item>

      <el-form-item class="form-buttons">
        <el-button
          type="primary"
          @click="submitForm('userInfoForm')"
          icon="el-icon-check"
        >
          保存修改
        </el-button>
        <el-button @click="resetForm('userInfoForm')" icon="el-icon-refresh">
          重置信息
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getUserInfo, updateUserInfo } from "@/api/user";

export default {
  computed: {
    userId() {
      return JSON.parse(window.localStorage.getItem("UserInfo")).id;
    },
  },
  data() {
    const validaterealName = (rule, value, callback) => {
      if (!value) {
        callback(new Error("昵称不能为空"));
      } else {
        callback();
      }
    };

    const validatePhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error("手机号不能为空"));
      } else if (!/^1[3-9]\d{9}$/.test(value)) {
        callback(new Error("手机号格式不正确"));
      } else {
        callback();
      }
    };
    const validateGender = (rule, value, callback) => {
      if (value !== 0 && value !== 1) {
        callback(new Error("性别不正确"));
      } else {
        callback();
      }
    };

    return {
      userInfo: {
        phone: "",
        realName: "",
        gender: "",
        school: "",
        identity: " ",
        birthPlace: "",
        politicalAppearance: "",
        position: "",
        title: ""
      },
      rules: {
        phone: [{ validator: validatePhone, trigger: "blur" }],
        realName: [{ validator: validaterealName, trigger: "blur" }],
        gender: [{ validator: validateGender, trigger: "change" }],
        birthPlace: [{ required: true, message: "请输入籍贯", trigger: "blur" }],
        politicalAppearance: [{ required: true, message: "请选择政治面貌", trigger: "change" }],
        position: [{ required: true, message: "请输入职位", trigger: "blur" }],
        title: [{ required: true, message: "请输入职称", trigger: "blur" }]
      },
    };
  },

  created() {
    this.fetchUserInfo();
  },

  methods: {
    fetchUserInfo() {
      getUserInfo(this.userId)
        .then((response) => {
          if (response.data) {
            this.userInfo = {
              ...this.userInfo,
              realName: response.data.realName,
              phone: response.data.phone,
              gender: response.data.gender,
              school: response.data.school,
              identity: response.data.identity,
              birthPlace: response.data.birthPlace,
              politicalAppearance: response.data.politicalAppearance,
              position: response.data.position,
              title: response.data.title
            };
          } else {
            this.$message.error("获取用户信息失败，请重试！");
          }
        })
        .catch((error) => {
          console.error("获取用户信息失败:", error);
          this.$message.error("获取用户信息失败，请重试！");
        });
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateUserInfo(this.userId, {
            id: this.userId,
            realName: this.userInfo.realName,
            gender: this.userInfo.gender,
            phone: this.userInfo.phone,
            birthPlace: this.userInfo.birthPlace,
            politicalAppearance: this.userInfo.politicalAppearance,
            position: this.userInfo.position,
            title: this.userInfo.title
          })
            .then((response) => {
              if (response.data) {
                this.$message.success("修改成功");
              } else {
                this.$message.error("修改失败，请重试！");
              }
            })
            .catch((error) => {
              console.error("修改失败:", error);
              this.$message.error("修改失败，请重试！");
            });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scoped>
.personal-info-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 30px;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.form-header h2 {
  color: #303133;
  font-size: 24px;
  margin-bottom: 8px;
}

.form-header p {
  color: #909399;
  font-size: 14px;
}

.info-form {
  margin-top: 20px;
}

.el-form-item {
  margin-bottom: 25px;
}

.el-input {
  transition: all 0.3s ease;
}

.el-input:hover .el-input__inner {
  border-color: #59808e;
}

.el-input__inner {
  height: 45px;
  line-height: 45px;
  border-radius: 8px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s ease;
}

.el-input__inner:focus {
  border-color: #59808e;
  box-shadow: 0 0 0 2px rgba(89, 128, 142, 0.1);
}

.form-buttons {
  margin-top: 40px;
  display: flex;
  justify-content: center;
  gap: 15px;
}

.el-button {
  padding: 12px 25px;
  border-radius: 8px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.el-button--primary {
  background: #59808e;
  border-color: #59808e;
}

.el-button--primary:hover {
  background: #4a6b77;
  border-color: #4a6b77;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(89, 128, 142, 0.2);
}

.el-button--default:hover {
  background: #f5f7fa;
  transform: translateY(-1px);
}

@media (max-width: 768px) {
  .personal-info-container {
    margin: 10px;
    padding: 20px;
  }

  .form-buttons {
    flex-direction: column;
  }

  .el-button {
    width: 100%;
    margin: 5px 0;
  }
}
</style>
