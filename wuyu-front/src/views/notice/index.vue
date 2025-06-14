<!--
 * @Author: hezeliangfj
 * @Date: 2025-06-14 12:54:59
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-14 15:12:54
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\views\notice\index.vue
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div>
    <div class="filter-container">
      <div class="left-filters">
        <el-select v-model="gradeId" placeholder="请选择年级">
          <el-option
            v-for="item in grades"
            :key="item"
            :label="`${item}年级`"
            :value="item"
          >
          </el-option>
        </el-select>
        <el-select
          v-model="classId"
          placeholder="请选择班级"
          style="margin-left: 20px"
        >
          <el-option
            v-for="item in classes"
            :key="item"
            :label="`${item}班`"
            :value="item"
          >
          </el-option>
        </el-select>
      </div>
      <div class="right-actions">
        <el-button type="primary" @click="dialogVisible = true"
          >批量导出<i class="el-icon-tickets"></i
        ></el-button>
      </div>
    </div>
    <el-dialog
      :close-on-click-modal="false"
      title="批量导出"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <span
        >是否导出{{ gradeId ? gradeId + "年级" : ""
        }}{{ classId ? classId + "班" : "" }}的所有学生的通知册信息</span
      >
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleExportBatch">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="预览通知册"
      :visible.sync="dialogVisiblepreview"
      width="60%"
      :before-close="handleClose"
    >
      <span v-html="content"></span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cleanpreview">取 消</el-button>
        <el-button type="primary" @click="cleanpreview">确 定</el-button>
      </span>
    </el-dialog>
    <el-table :data="paginatedList" style="width: 100%" id="dataTable">
      <el-table-column fixed type="selection" tooltip-effect="dark">
      </el-table-column>
      <el-table-column prop="studentGrade" label="年级"> </el-table-column>
      <el-table-column prop="studentClassNumber" label="班级">
      </el-table-column>

      <el-table-column prop="studentId" label="学生学号"> </el-table-column>
      <el-table-column prop="studentName" label="学生姓名"> </el-table-column>

      <el-table-column label="操作" align="center" width="200px">
        <template slot-scope="scope">
          <el-button type="success" @click="preview(scope.row)"
            >预览<i class="el-icon-tickets"></i
          ></el-button>
          <el-button type="primary" @click="handleExport(scope.row)"
            >导出<i class="el-icon-tickets"></i
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页器 -->
    <div class="mt text_center">
      <el-pagination
        :current-page="query.page"
        :page-sizes="[10, 30, 100]"
        :page-size="query.pageSize"
        :total="totalCount"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
      >
      </el-pagination>
    </div>
    <!-- /分页器 -->
  </div>
</template>

<script>
import {
  getStudent,
  noticeBooklet,
  exportNoticeBooklet,
  previewNoticeBooklet,
} from "@/api/notice.js";
// import { create } from 'sortablejs';
import { Loading } from "element-ui";
export default {
  // name: 'NoticeIndex',
  // computed: {
  //   ...mapGetters([
  //     'permission_routes'
  //   ]),
  //   menuRoutes() {
  //     return this.permission_routes
  //   }
  // },
  data() {
    return {
      dialogVisible: false,
      list: [],
      grades: [],
      classNames: [],
      classes: [],
      gradeId: null,
      classId: null,
      dialogVisiblepreview: false,
      content: "", // 预览内容
      // 查询
      query: {
        pageSize: 10,
        page: 1,
        courseName: "",
        courseType: "",
        teacherName: "",
        studentNum: "",
        studentName: "",
        login_time: "",
        create_time: "",
        orderby: `create_time desc`,
      },
      // 数据
      total: 0, //数据总数
      list: [],
      list_user_course_teacher: [],
      deleteIds: [],
    };
  },
  computed: {
    filteredClasses() {
      if (!this.gradeId) return [];
      const uniqueClasses = new Set();
      this.classNames.forEach((item) => {
        if (item[0] === this.gradeId) {
          uniqueClasses.add(item[1]);
        }
      });
      return Array.from(uniqueClasses).sort((a, b) => a - b);
    },
    // 过滤后的数据
    filteredList() {
      let result = [...this.list];

      // 根据年级和班级筛选
      if (this.gradeId) {
        result = result.filter((item) => item.studentGrade === this.gradeId);
      }
      if (this.classId) {
        result = result.filter(
          (item) => item.studentClassNumber === this.classId
        );
      }

      return result;
    },
    // 分页后的数据
    paginatedList() {
      const start = (this.query.page - 1) * this.query.pageSize;
      const end = start + this.query.pageSize;
      return this.filteredList.slice(start, end);
    },
    // 总数据量
    totalCount() {
      return this.filteredList.length;
    },
  },
  watch: {
    gradeId: {
      handler(newVal) {
        this.classId = null;
        this.classes = this.filteredClasses;
        this.query.page = 1; // 重置页码
      },
      immediate: true,
    },
    classId: {
      handler() {
        this.query.page = 1; // 重置页码
      },
    },
  },
  methods: {
    async fetchCourse() {
      const params = {};
      const res = await getStudent(params);
      this.grades = res.data.grades;
      this.classNames = res.data.classNames;
    },
    async fetchNoticeBooklet() {
      const params = {
        isRemark: false,
      };
      const res = await noticeBooklet({ params });
      this.list = res.data;
    },
    handleSizeChange(pageSize) {
      this.query.pageSize = pageSize;
      this.query.page = 1; // 切换每页条数时重置为第一页
    },
    handleCurrentChange(page) {
      this.query.page = page;
    },
    // 通知册预览清除
    cleanpreview() {
      this.dialogVisiblepreview = false;
      this.content = ""; // 清除预览内容
    },
    // 导出通知册
    // async handleExport(row) {
    //   console.log("222", row);
    //   // handleExport
    //   try {
    //     // const params = { studentId: row.studentId };
    //     // console.log(params);
    //     const studentId = row.studentId;
    //     const res = await exportNoticeBooklet(studentId);
    //   } catch (error) {
    //     console.error("导出失败:", error);
    //   }
    // },
    async handleExport(row) {
      // 显示Loading
      const loadingInstance = this.$loading({
        lock: true,
        text: "文件生成中，请稍候...",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });

      try {
        const studentId = row.studentId;
        // 使用较长的超时时间（例如 60秒）
        const res = await exportNoticeBooklet(studentId, { timeout: 60000 });

        // 创建Blob并触发下载（代码同上一步）
        const blob = new Blob([res.data], { type: "application/octet-stream" });
        const link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = "通知册.docx";
        link.style.display = "none";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);

        this.$message.success("下载成功！");
      } catch (error) {
        if (error.code === "ECONNABORTED") {
          this.$message.error("请求超时，请稍后再试");
        } else {
          this.$message.error("下载失败：" + error.message);
        }
      } finally {
        // 关闭Loading
        loadingInstance.close();
      }
    },
    //预览
    async preview(row) {
      // console.log("预览", row);
      const loadingInstance = this.$loading({
        lock: true,
        text: "预览生成中，请稍候...",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      try {
        const params = {
          studentId: row.studentId,
        };
        const res = await previewNoticeBooklet({ params });
        // 处理预览逻辑，例如打开一个新窗口显示预览内容
        // console.log("预览数据:", res.data);
        // this.content = res.data; // 假设预览数据是字符串或HTML内容
        if (res.code === 200) {
          this.dialogVisiblepreview = true; // 打开预览对话框
          this.content = res.data; // 假设预览数据是字符串或HTML内容
          loadingInstance.close();
        } else {
          this.$message.error("预览数据获取失败");
        }
      } catch (error) {
        console.error("预览失败:", error);
      } finally {
        if (loadingInstance) {
          loadingInstance.close();
        }
      }
    },
    handleExportBatch() {
      // Implementation of handleExportBatch method
    },
    handleClose() {
      // Implementation of handleClose method
    },
  },
  created() {
    this.fetchCourse();
    this.fetchNoticeBooklet();
  },
};
</script>

<style lang="scss" scoped>
.filter-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  // margin-bottom: 20px;

  .left-filters {
    margin-top: 20px;
    display: flex;
    // gap: 10px;
    margin-left: 20px;
  }

  .right-actions {
    margin-right: 20px;
  }
}
.higher-z-index-loading {
  z-index: 3000 !important;
}
</style>
