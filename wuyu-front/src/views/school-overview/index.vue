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


    <!--    进行添加的方框-->
    <el-dialog title="收货地址" :visible.sync="dialogFormVisible" width="70%">
      <el-form label-width="120px" size="small">
        <el-form-item label="主题">
          <el-input v-model="form.theme" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主要内容">
          <el-input v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
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
import {getNoticeList} from "@/api/fuScale";
import {deleteById} from "@/api/fuScale";
import axios from 'axios';
import {addList} from "@/api/fuScale";
import request from "@/utils/request";

export default {
  data() {
    return {
      notifications: [],
      dialogVisible: false,
      dialogFormVisible: false,
      fileContent: "这是一段文字",
      form: {}
    };
  },

  mounted() {
    this.load()
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
      addList(this.form).then(res => {
        console.log(this.form)
        if (res) {
          this.$message.success("添加成功")
          this.dialogFormVisible = false;
          this.load()
        } else {
          this.$message.error("添加失败")
        }
      })

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
