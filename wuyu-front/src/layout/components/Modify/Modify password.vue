<template>
  <div class="password-modify-container">
    <div class="form-header">
      <h2>修改密码</h2>
      <p>请谨慎修改您的登录密码</p>
    </div>

    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="password-form"
    >
      <el-form-item label="原密码" prop="oldpass">
        <el-input
          type="password"
          v-model="ruleForm.oldpass"
          placeholder="请输入原密码"
          prefix-icon="el-icon-lock"
          autocomplete="off"
        ></el-input>
      </el-form-item>

      <el-form-item label="新密码" prop="newpass">
        <el-input
          type="password"
          v-model="ruleForm.newpass"
          placeholder="请输入新密码"
          prefix-icon="el-icon-key"
          autocomplete="off"
        ></el-input>
      </el-form-item>

      <el-form-item label="确认密码" prop="checkPass">
        <el-input
          type="password"
          v-model="ruleForm.checkPass"
          placeholder="请再次输入新密码"
          prefix-icon="el-icon-check"
          autocomplete="off"
        ></el-input>
      </el-form-item>

      <el-form-item class="form-buttons">
        <el-button
          type="primary"
          @click="submitForm('ruleForm')"
          icon="el-icon-check"
        >
          确认修改
        </el-button>
        <el-button @click="resetForm('ruleForm')" icon="el-icon-refresh">
          重置输入
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { updatePassword, verifyOldPassword } from "@/api/user";

export default {
  computed: {
    userId() {
      return JSON.parse(window.localStorage.getItem("UserInfo")).id;
    },
  },
  data() {
    var validateOldPass = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入原密码"));
      }
      if (value === this.ruleForm.newpass) {
        return callback(new Error("原密码不能与新密码相同"));
      }
      verifyOldPassword(this.userId, value)
        .then((response) => {
          if (response.data) {
            callback();
          } else {
            callback(new Error("原密码错误"));
          }
        })
        .catch((error) => {
          console.error("验证原密码失败:", error);
          callback(new Error("验证原密码失败，请重试"));
        });
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入新密码"));
      } else if (value === this.ruleForm.oldpass) {
        callback(new Error("新密码不能与原密码相同"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }

        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入确认密码"));
      } else if (value !== this.ruleForm.newpass) {
        callback(new Error("两次输入的新密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        oldpass: "",
        newpass: "",
        checkPass: "",
      },
      rules: {
        oldpass: [{ validator: validateOldPass, trigger: "blur" }],
        newpass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updatePassword(this.userId, {
            id: this.userId,
            password: this.ruleForm.newpass,
          })
            .then((response) => {
              if (response.data) {
                this.$message.success("修改成功");
                this.$refs[formName].resetFields();
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
.password-modify-container {
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

.password-form {
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

.el-button--default {
  background: #f5f7fa;
  border-color: #dcdfe6;
  color: #606266;
}

.el-button--default:hover {
  background: #f0f2f5;
  transform: translateY(-1px);
  border-color: #c0c4cc;
}

.el-form-item__label {
  font-size: 14px;
  color: #606266;
  font-weight: 500;
}

/* 错误提示样式优化 */
.el-form-item__error {
  font-size: 12px;
  padding-top: 4px;
  color: #f56c6c;
}

/* 图标样式 */
.el-input__prefix {
  color: #909399;
}

.el-input:hover .el-input__prefix {
  color: #59808e;
}

@media (max-width: 768px) {
  .password-modify-container {
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

  .el-form-item__label {
    float: none;
    display: block;
    text-align: left;
    padding: 0 0 10px;
  }
}
</style>
