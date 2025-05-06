<template>
  <el-main class="bg edit_wrap">
    <el-form ref="form" :model="form" status-icon label-width="120px" :rules="rules">
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="课程名称" prop="courseName">
          <el-input id="courseName" v-model="form['courseName']" placeholder="请输入课程名称"
            :disabled="disabledObj['course_name_isDisabled']"></el-input>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="课程类型" prop="courseType">
          <el-select id="courseType" v-model="form['courseType']" placeholder="请选择课程类型"
            :disabled="disabledObj['course_type_isDisabled']">
            <el-option label="德育" value="0"></el-option>
            <el-option label="智育" value="1"></el-option>
            <el-option label="体育" value="2"></el-option>
            <el-option label="美育" value="3"></el-option>
            <el-option label="劳育" value="4"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="考试序号" prop="testNumber">
          <el-input id="testNumber" v-model.number="form['testNumber']" placeholder="请输入考试序号"
            :disabled="disabledObj['test_num_isDisabled']"></el-input>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="课程教师" prop="teacherName">
          <el-select id="teacherName" v-model="form['teacherName']"
            :disabled="disabledObj['course_teacher_isDisabled']">
            <el-option v-for="o in list_user_course_teacher" :key="o['username']" :label="o['username']"
              :value="o['username']"></el-option>
          </el-select>
          <!--          <el-select :disabled="true">-->
          <!--            <el-option v-for="o in list_user_course_teacher" :key="o['username']" :label="o['teacher_name'] + '-' + o['username']" :value="o['teacherId']"></el-option>-->
          <!--          </el-select>-->
        </el-form-item>
      </el-col>
      <!--								<el-col v-if="user_group === '管理员' || $check_field('get','teacher_name') || $check_field('add','teacher_name') || $check_field('set','teacher_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">-->
      <!--				<el-form-item label="教师姓名" prop="teacher_name">-->
      <!--												<el-input id="teacher_name" v-model="form['teacher_name']" placeholder="请输入教师姓名"-->
      <!--							  v-if="user_group === '管理员' || (form['course_score_id'] && $check_field('set','teacher_name')) || (!form['course_score_id'] && $check_field('add','teacher_name'))" :disabled="disabledObj['teacher_name_isDisabled']"></el-input>-->
      <!--					<div v-else-if="$check_field('get','teacher_name')">{{form['teacher_name']}}</div>-->
      <!--											</el-form-item>-->
      <!--			</el-col>-->
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="学生学号" prop="studentNum">
          <el-input id="studentNum" v-model="form['studentNum']" placeholder="请输入学生学号"
            :disabled="disabledObj['student_id_isDisabled']"></el-input>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="学生姓名" prop="studentName">
          <el-input id="studentName" v-model="form['studentName']" placeholder="请输入学生姓名"
            :disabled="disabledObj['student_name_isDisabled']"></el-input>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="录入日期" prop="inputTime">
          <el-date-picker :disabled="disabledObj['entry_date_isDisabled']" id="inputTime" v-model="form['inputTime']"
            type="date" placeholder="选择日期" @change="formatDateTime">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="学生成绩" prop="score">
          <el-input id="score" v-model.number="form['score']"
            :disabled="disabledObj['student_achievements_isDisabled']"></el-input>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="教师评语" prop="remark">
          <el-input type="textarea" id="remark" v-model="form['remark']" placeholder="请输入教师评语"
            :disabled="disabledObj['teacher_comments_isDisabled']"></el-input>
        </el-form-item>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
        <el-form-item>
          <el-button @click="save()">保存</el-button>
          <el-button type="primary" @click="submit()">提交</el-button>
          <el-button @click="cancel()">取消</el-button>
        </el-form-item>
      </el-col>
    </el-form>
    <el-upload :action=uploadUrl :on-success="successUpload"
      :before-upload="beforeUpload">
      <el-button>导入数据</el-button>
    </el-upload>
    <el-button @click="confirmGradeUpdate()">一键修改</el-button>
  </el-main>
</template>

<script>

import axios from "axios";
import { addCourseScore,editCourseScore } from '@/api/courseScore/';
import { baseUrl } from "@/api/baseapi";
import request from "@/utils/re_request";
import { Message } from "element-ui";
export default {
  data() {
    return {
      field: "course_score_id",
      url_add: "~/api/course_score/add?",
      url_set: "~/api/course_score/set?",
      url_get_obj: "~/api/course_score/get_obj?",
      url_upload: "~/api/course_score/upload?",
      uploadUrl: `${baseUrl}/coursescore/upload`, // 拼接完整的上传地址

      rules: {
        // 考试序号规则
        testNumber: [
          { required: true, message: '请输入考试序号', trigger: 'blur' },
          { type: 'number', message: '考试序号必须为数字值' },
          { validator: this.validateTestNumber, trigger: 'blur' }
        ],
        // 学生学号规则
        studentNum: [
          { required: true, message: '请输入学生学号', trigger: 'blur' },
          { pattern: /^\d{10}$/, message: '学号必须为10位数字', trigger: 'blur' },
          { validator: this.validateStudentIdYear, trigger: 'blur' }
        ],
        // 学生姓名规则
        studentName: [
          { required: true, message: '请输入学生姓名', trigger: 'blur' },
          { pattern: /^[a-zA-Z\u4e00-\u9fa5]+$/, message: '学生姓名只能为汉字和字母', trigger: 'blur' }
        ],
        // 学生成绩规则
        score: [
          { required: true, message: '请输入学生成绩', trigger: 'blur' },
          { type: 'number', message: '学生成绩必须为数字值' },
          { validator: this.validateScore, trigger: 'blur' }
        ]
      },
      query: {
        "course_score_id": 0,
      },

      form: {
        "courseName": '', // 课程名称
        "courseType": '', // 课程类型
        "testNumber": '', // 考试序号
        "teacherName": '', // 课程教师
        "studentNum": '', // 学生学号
        "studentName": '', // 学生姓名
        "inputTime": '', // 录入日期
        "score": 0, // 学生成绩
        "remark": '', // 教师评语
        "id": 0, // ID
      },
      disabledObj: {
        "course_name_isDisabled": false,
        "course_type_isDisabled": false,
        "test_num_isDisabled": false,
        "course_teacher_isDisabled": false,
        "teacher_name_isDisabled": false,
        "student_id_isDisabled": false,
        "student_name_isDisabled": false,
        "entry_date_isDisabled": false,
        "student_achievements_isDisabled": false,
        "teacher_comments_isDisabled": false,
      },
      // 用户列表
      list_user_course_teacher: [],
    }
  },
  methods: {
    beforeUpload(file) {
      const extension = file.name.split('.').pop().toLowerCase();
      if (extension !== 'xls' && extension !== 'xlsx') {
        this.$message.error('只能上传 .xls 或 .xlsx 格式的文件');
        return false; // 阻止上传
      }
      return true; // 允许上传
    },
    // 考试序号自定义验证函数
    validateTestNumber(rule, value, callback) {
      const num = parseInt(value);
      if (isNaN(num) || num < 1 || num > 55) {
        callback(new Error('考试序号必须在1到55之间'));
      } else {
        callback();
      }
    },

    // 学生成绩自定义验证函数
    validateScore(rule, value, callback) {
      const score = parseInt(value);
      if (isNaN(score) || score < 0 || score > 100) {
        callback(new Error('学生成绩必须在0到100之间'));
      } else {
        callback();
      }
    },

    validateStudentIdYear(rule, value, callback) {
      const year = value.substring(0, 4);
      const currentYear = new Date().getFullYear();
      if (year < 1900 || year > currentYear) {
        callback(new Error('学号年份必须在1900年到当前年份之间'));
      } else {
        callback();
      }
    },
    confirmGradeUpdate() {
      this.$confirm('确定将所有成绩修改为85吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 用户点击确定按钮，向后端发送请求将所有成绩修改为85
        this.updateGrades();
      }).catch(() => {
        // 用户点击取消按钮，不做任何操作
      });
    },
    updateGrades() {
      // 在这里向后端发送请求，将所有成绩修改为85
      axios.put(baseUrl + '/coursescore/85')
        .then(response => {
          this.$message.success('成绩更新成功');
        })
        .catch(error => {
          this.$message.error('成绩更新失败');
        });

    },
    cancel() {
      // 清空表单中的所有值
      Object.keys(this.form).forEach(key => {
        this.form[key] = '';
      });
    },
    formatDateTime(value) {
      if (value) {
        const formattedDateTime = new Date(value).toISOString().slice(0, 19).replace('T', ' ');
        this.form.inputTime = formattedDateTime;
      }
    },
    save() {
      // 将表单数据转换为 JSON 字符串并保存到本地存储中
      localStorage.setItem('formData', JSON.stringify(this.form));
      this.$message.success('数据已保存');
    },
    loadData() {
      // 从本地存储中获取之前保存的表单数据
      const savedData = localStorage.getItem('formData');
      if (savedData) {
        // 如果存在保存的数据，则将其解析为对象并赋值给表单数据
        this.form = JSON.parse(savedData);
        this.$message.success('已加载上次保存的数据');
      } else {
        this.$message.info('没有保存的数据');
      }
    },
    successUpload(res) {
      if (res.code === 600) {
        this.$message.success("批量导入成功")
      }
      else {
        this.$message.error("批量导入失败:"+res.msg)
      }

    },
    /**
   * 获取教师用户用户列表
   */
    async get_list_user_course_teacher() {
      // if(this.user_group !== "管理员" && this.form["course_teacher"] === 0) {
      //     this.form["course_teacher"] = this.user.user_id;
      // }
      // var json = await axios.get(baseUrl+"/teacher/list");
      request.get(baseUrl + "/teacher/list").then((response) => {
        if (response.code === 600) {
          this.list_user_course_teacher = response.data;
          for (i in this.list_user_course_teacher) {
            console.log(i.username);
          }
        }
      });
      // if(json.result && json.result.list){
      //     this.list_user_course_teacher = json.result.list;
      //     for (i in this.list_user_course_teacher){
      //       console.log(i.username);
      //     }
      // }
      // else if(json.error){
      //     console.error(json.error);
      // }
    },
    get_user_course_teacher(id) {
      var obj = this.list_user_course_teacher.getObj({ "user_id": id });
      var ret = "";
      if (obj) {
        if (obj.nickname) {
          ret = obj.nickname;
        }
        else {
          ret = obj.username;
        }
      }
      return ret;
    },
    /**
     * 获取对象之前
     * @param {Object} param
     */
    get_obj_before(param) {
      var form = "";
      // 获取缓存数据附加
      form = $.db.get("form");
      $.push(this.form, form);

      if (this.form && form) {
        Object.keys(this.form).forEach(key => {
          Object.keys(form).forEach(dbKey => {
            // if(dbKey === "charging_standard"){
            // 	this.form['charging_rules'] = form[dbKey];
            // 	this.disabledObj['charging_rules_isDisabled'] = true;
            // };
            if (key === dbKey) {
              this.disabledObj[key + '_isDisabled'] = true;
            }
          })
        })
      }
      if (this.form["entry_date"].indexOf("-") === -1) {
        this.form["entry_date"] = this.$toTime(parseInt(this.form["entry_date"]), "yyyy-MM-dd")
      }
      $.db.del("form");
      return param;
    },

    /**
     * 获取对象之后
     * @param {Object} json
     * @param {Object} func
     */
    get_obj_after(json, func) {


      if (this.form["entry_date"] == "0000-00-00") {
        this.form["entry_date"] = null;
      }
      if (parseInt(this.form["entry_date"]) > 9999) {
        this.form["entry_date"] = this.$toTime(parseInt(this.form["entry_date"]), "yyyy-MM-dd")
      }


    },
    submit() {
      //如果有编辑传来的数据则是编辑成绩
      const rowData = this.$route.query.rowData;
      if (rowData) {
        console.log("开始发送编辑成绩请求")
        // console.log(this.form)
        editCourseScore(this.form).then(res => {
          console.log(res)
            // 前端更新
            this.$message.success('修改成功')
        })
      } else {
        //没有则是录入成绩
        console.log("开始发送录入成绩请求")
        addCourseScore(this.form).then(res => {
          console.log(res)
          if (res.code === 600) {
            // 前端更新
            this.$message.success('录入成功')
            this.cancel();
          }
        })
      }
    },

    /**
     * 提交前验证事件
     * @param {Object} 请求参数
     * @return {String} 验证成功返回null, 失败返回错误提示
     */
    submit_check(param) {
      let msg = null
      return msg;
    },

    is_view() {
      var bl = this.user_group == "管理员";

      return bl;
    },
    /**
     * 上传文件
     * @param {Object} param
     */
    uploadimg(param) {
      this.uploadFile(param.file, "avatar");
    },

  },
  created() {
    this.form['course_name'] = localStorage.getItem(form['course_name']);
  },
  mounted() {
    this.get_list_user_course_teacher();
    //如果有编辑传来的数据则获取编辑数据
    const rowData = this.$route.query.rowData;
    if (rowData) {
      console.log(rowData)
      //放入表单
      this.form = JSON.parse(rowData);
    } else {
      //无编辑数据则载入保存数据
      this.loadData();
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
