<template>
  <el-dialog
    title="教师检索"
    :visible.sync="dialogVisible"
    width="70%"
    :before-close="handleClose"
  >
    <!-- 教师搜索模块 -->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>教师搜索</span>
        <el-button 
          style="float: right; padding: 3px 0" 
          type="text"
          @click="resetSearch"
        >
          <i class="el-icon-refresh"></i> 重置列表
        </el-button>
      </div>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-autocomplete
            v-model="searchObj.teacherName"
            :fetch-suggestions="querySearchAsync"
            placeholder="输入教师姓名"
            @select="handleSelect"
            clearable
            class="teacher-search"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 教师列表 -->
    <el-card class="box-card" style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>教师列表</span>
      </div>
      <el-table :data="teacherList" border style="width: 100%">
        <el-table-column prop="teacherName" label="教师姓名" align="center" />
        <el-table-column prop="gender" label="性别" align="center" />
        <el-table-column prop="position" label="职位" align="center" />
        <el-table-column prop="title" label="职称" align="center" />
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleSetCourse(scope.row)">设置课程</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 排课设置对话框 -->
    <el-dialog
      title="排课设置"
      :visible.sync="courseDialogVisible"
      width="50%"
      append-to-body
    >
      <el-form :model="courseForm" :rules="courseRules" ref="courseForm" label-width="100px">
        <el-form-item label="教师姓名" prop="teacherName">
          <span>{{ selectedTeacher.teacherName }}</span>
        </el-form-item>
        <el-form-item label="关联课程" prop="courseId">
          <el-select v-model="courseForm.courseId" placeholder="请选择课程" @change="handleCourseChange">
            <el-option
              v-for="item in courseList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-select v-model="courseForm.courseType" placeholder="请选择课程类型" disabled>
            <el-option
              v-for="item in courseTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="上课时间" required>
          <el-col :span="11">
            <el-form-item prop="schedule.weekday">
              <el-select v-model="courseForm.schedule.weekday" placeholder="请选择周几">
                <el-option
                  v-for="item in weekdays"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="text-center">
            <span class="text-muted">-</span>
          </el-col>
          <el-col :span="11">
            <el-form-item prop="schedule.time">
              <el-select v-model="courseForm.schedule.time" placeholder="请选择节次">
                <el-option
                  v-for="item in timeSlots"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="上课班级" prop="classInfo">
          <el-select v-model="courseForm.classInfo" placeholder="请选择班级">
            <el-option
              v-for="item in classList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="courseDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitCourseSetting">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 复制上学期排课按钮 -->
    <el-card class="box-card" style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>排课复制</span>
      </div>
      <el-button type="warning" @click="handleCopyLastSemester">复制上学期排课</el-button>
    </el-card>
  </el-dialog>
</template>

<script>
import pinyin from 'pinyin';
import { getTeacherListByPage } from '@/api/managementModule/teacher';
import { getCourseList, setTeacherCourse, copyLastSemesterSchedule } from '@/api/managementModule/courseSchedule';

export default {
  name: 'TeacherSel',
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dialogVisible: false,
      searchObj: {
        teacherName: '',
      },
      allTeachers: [],  // 保存完整的教师列表
      teacherList: [],  // 显示用的教师列表
      courseDialogVisible: false,
      selectedTeacher: {},
      courseForm: {
        courseId: '',
        courseType: '', // 课程类型
        schedule: {
          weekday: '', // 周几
          time: '', // 上课时间
        },
        classInfo: '', // 上课班级
      },
      // 测试数据
      testCourseList: [
        { id: 1, name: '语文', type: 'intellectual' },
        { id: 2, name: '数学', type: 'intellectual' },
        { id: 3, name: '英语', type: 'intellectual' },
        { id: 4, name: '音乐', type: 'aesthetic' },
        { id: 5, name: '体育', type: 'physical' },
        { id: 6, name: '美术', type: 'aesthetic' },
        { id: 7, name: '物理', type: 'intellectual' },
        { id: 8, name: '化学', type: 'intellectual' },
        { id: 9, name: '生物', type: 'intellectual' },
        { id: 10, name: '历史', type: 'intellectual' },
        { id: 11, name: '地理', type: 'intellectual' },
        { id: 12, name: '政治', type: 'moral' }
      ],
      courseList: [], // 实际使用的课程列表
      courseTypes: [
        { value: 'moral', label: '德育' },
        { value: 'intellectual', label: '智育' },
        { value: 'physical', label: '体育' },
        { value: 'aesthetic', label: '美育' },
        { value: 'labor', label: '劳育' }
      ],
      weekdays: [
        { value: '1', label: '周一' },
        { value: '2', label: '周二' },
        { value: '3', label: '周三' },
        { value: '4', label: '周四' },
        { value: '5', label: '周五' }
      ],
      timeSlots: [
        { value: '1', label: '第一节 (8:00-8:45)' },
        { value: '2', label: '第二节 (8:55-9:40)' },
        { value: '3', label: '第三节 (10:00-10:45)' },
        { value: '4', label: '第四节 (10:55-11:40)' },
        { value: '5', label: '第五节 (14:00-14:45)' },
        { value: '6', label: '第六节 (14:55-15:40)' },
        { value: '7', label: '第七节 (16:00-16:45)' },
        { value: '8', label: '第八节 (16:55-17:40)' }
      ],
      classList: [
        { value: '1-1', label: '一年级1班' },
        { value: '1-2', label: '一年级2班' },
        { value: '2-1', label: '二年级1班' },
        { value: '2-2', label: '二年级2班' },
        { value: '3-1', label: '三年级1班' },
        { value: '3-2', label: '三年级2班' }
      ],
      courseRules: {
        courseId: [
          { required: true, message: '请选择课程', trigger: 'change' }
        ],
        courseType: [
          { required: true, message: '请选择课程类型', trigger: 'change' }
        ],
        'schedule.weekday': [
          { required: true, message: '请选择上课日期', trigger: 'change' }
        ],
        'schedule.time': [
          { required: true, message: '请选择上课时间', trigger: 'change' }
        ],
        classInfo: [
          { required: true, message: '请选择上课班级', trigger: 'change' }
        ]
      }
    };
  },
  watch: {
    visible(val) {
      this.dialogVisible = val;
      if (val) {
        this.getTeacherList();
        this.getAllTeachers(); // 获取完整教师列表
        this.getCourseList();
      } else {
        // 弹窗关闭时重置
        this.resetSearch();
      }
    },
    dialogVisible(val) {
      if (!val) {
        this.$emit('update:visible', false);
      }
    }
  },
  methods: {
    // 教师搜索相关方法
    querySearchAsync(queryString, cb) {
      // 1. 过滤出姓名拼音包含查询字符串的教师
      const matchedTeachers = this.allTeachers.filter(teacher => {
        const teacherPinyin = pinyin(teacher.teacherName, { style: pinyin.STYLE_NORMAL }).join('').toLowerCase();
        return teacherPinyin.includes(queryString.toLowerCase());
      });
      
      // 2. 打印匹配的教师姓名
      if (matchedTeachers.length > 0) {
        console.log(`拼音包含 "${queryString}" 的教师：`);
        matchedTeachers.forEach(teacher => {
          console.log(teacher.teacherName); // 如 "李四"、"林立" 等
        });
      } else {
        console.log(`没有找到拼音包含 "${queryString}" 的教师。`);
      }
      console.log("matchedTeachers：",matchedTeachers);
      
      // 3. 通过回调函数 cb 返回结果（格式必须为 [{ value: '显示文本' }, ...]）
      const results = matchedTeachers.map(teacher => ({
        value: teacher.teacherName, // 显示在下拉列表中的文本
        teacher: teacher
      }));
      cb(results);
    },
    handleSelect(item) {
      // this.searchObj.teacherName = item.teacherName;
      console.log("选中教师的完整数据：", item.teacher);
    },
    handleSearch() {
      this.getTeacherList();
    },
    resetSearch() {
      this.searchObj.teacherName = '';
      this.getTeacherList();
    },

    // 获取教师列表
    getTeacherList() {
      const payload = {
        pageSize: 10,
        pageNum: 1,
        ...this.searchObj,
      };
      getTeacherListByPage(payload).then(res => {
        this.teacherList = res.data.list.map(item => ({
          ...item,
          gender: item.gender === 0 ? '女' : '男',
        }));
      });
    },

    // 获取完整教师列表
    getAllTeachers() {
      const payload = {
        pageSize: 9999, // 获取所有教师
        pageNum: 1,
      };
      getTeacherListByPage(payload).then(res => {
        this.allTeachers = res.data.list.map(item => ({
          ...item,
          gender: item.gender === 0 ? '女' : '男',
        }));
      });
    },

    // 获取课程列表
    getCourseList() {
      getCourseList().then(res => {
        if (res.code === 200 && res.data && res.data.length > 0) {
          // 为每个课程添加类型
          this.courseList = res.data.map(course => {
            // 根据课程名称判断类型
            let type = 'intellectual'; // 默认为智育
            if (['音乐', '美术'].includes(course.name)) {
              type = 'aesthetic';
            } else if (course.name === '体育') {
              type = 'physical';
            } else if (course.name === '政治') {
              type = 'moral';
            }
            return { ...course, type };
          });
        } else {
          // 使用测试数据
          console.log('使用测试课程数据');
          this.courseList = this.testCourseList;
        }
      }).catch(error => {
        // 如果接口调用失败，使用测试数据
        console.log('接口调用失败，使用测试课程数据');
        this.courseList = this.testCourseList;
      });
    },

    // 设置课程
    handleSetCourse(teacher) {
      this.selectedTeacher = teacher;
      this.courseForm = {
        courseId: '',
        courseType: '',
        schedule: {
          weekday: '',
          time: '',
        },
        classInfo: '',
      };
      this.courseDialogVisible = true;
    },

    // 提交课程设置
    submitCourseSetting() {
      this.$refs.courseForm.validate(valid => {
        if (valid) {
          const payload = {
            teacherId: this.selectedTeacher.id,
            courseId: this.courseForm.courseId,
            courseType: this.courseForm.courseType,
            schedule: this.courseForm.schedule,
            classInfo: this.courseForm.classInfo,
          };
          setTeacherCourse(payload).then(res => {
            if (res.code === 200) {
              this.$message.success('设置成功');
              this.courseDialogVisible = false;
              this.getTeacherList();
            } else {
              this.$message.error(res.message || '设置失败');
            }
          });
        }
      });
    },

    // 复制上学期排课
    handleCopyLastSemester() {
      this.$confirm('此操作将覆盖所有课程教师设置，是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        copyLastSemesterSchedule().then(res => {
          if (res.code === 200) {
            this.$message.success('复制成功');
            this.getTeacherList();
          } else {
            this.$message.error(res.message || '复制失败');
          }
        });
      }).catch(() => {
        this.$message.info('已取消复制');
      });
    },

    handleClose() {
      this.$emit('update:visible', false);
    },

    // 处理课程选择变化
    handleCourseChange(courseId) {
      const selectedCourse = this.courseList.find(course => course.id === courseId);
      if (selectedCourse) {
        this.courseForm.courseType = selectedCourse.type;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.box-card {
  margin-bottom: 20px;
}
.teacher-search {
  width: 300px;
}
</style> 