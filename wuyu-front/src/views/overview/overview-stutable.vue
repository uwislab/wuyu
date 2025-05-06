<template>
  <div>
    <h1 class="table-title">学生成绩录入评阅情况</h1>
    <el-table
      :data="
        tableData.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      "
      border
      style="width: 100%; font-size: 22px"
      :row-style="{ height: '60px' }"
      class="table-body"
    >
      <el-table-column
        fixed
        prop="studentName"
        label="学生"
        width="150"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="moralityScore"
        label="德"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="intelligenceScore"
        label="智"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="physicalScore"
        label="体"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="aestheticScore"
        label="美"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="labourScore" label="劳" width="120" align="center">
      </el-table-column>
      <el-table-column
        prop="isEnter"
        label="是否录入"
        width="150"
        align="center"
      >
      <template v-slot="scope">
    <el-button v-if="scope.row.isEnter === 'true'" type="text" size="big" style="color: green;" 
    @click="navigateToPersonal(scope.row.studentName)" >
      已录入
    </el-button>
    <el-button @click="review(scope.row)" v-else type="text" size="big" style="color: red;">
      未录入
    </el-button>
  </template>
        
        </el-table-column
      >
      <el-table-column
        prop="isReview"
        label="是否评阅"
        width="196"
        align="center"
      >

      <template v-slot="scope">
    <el-button v-if="scope.row.isReview === 'true'" type="text" size="big" style="color: green;" 
    @click="review(scope.row)" >
      已评阅
    </el-button>
    <el-button v-else type="text" size="big" style="color: red;" @click="review(scope.row)">
      未评阅
    </el-button>

    <el-dialog
      title="学生评阅"
      :visible.sync="dialogVisible"
      width="40%"
      @close="">
      <el-form class="demo-form-inline">
      <el-form-item label="学生姓名">
        <span>{{ student.studentName }}</span>
      </el-form-item>
      <el-form-item label="品德分">
        <div class="student-form-item">
          <el-input style="width: 200px; margin-left: 150px;" v-model="student.moralityScore"></el-input></div>
        <!-- <span>{{ student.moralityScore }}</span> -->
      </el-form-item>
      <el-form-item label="智力分">
        <div class="student-form-item">
          <el-input style="width: 200px; margin-left: 150px;" v-model="student.intelligenceScore"></el-input></div>
      </el-form-item>
      <el-form-item label="体育分">
        <div class="student-form-item">
          <el-input style="width: 200px; margin-left: 150px;" v-model="student.physicalScore"></el-input></div>
      </el-form-item>
      <el-form-item label="审美分">
        <div class="student-form-item">
          <el-input style="width: 200px; margin-left: 150px;" v-model="student.aestheticScore"></el-input></div>
        <!-- <span>{{ student.aestheticScore }}</span> -->
      </el-form-item>
      <el-form-item label="劳动分">
        <div class="student-form-item">
          <el-input style="width: 200px; margin-left: 150px;" v-model="student.labourScore"></el-input></div>
        <!-- <span>{{ student.labourScore }}</span> -->
      </el-form-item>

      <!-- <el-form-item label="是否复审">
        <span>{{ student.isReview === 'true' ? '是' : '否' }}</span>
      </el-form-item>
      <el-form-item label="是否录入">
        <span>{{ student.isEnter === 'true' ? '是' : '否' }}</span>
      </el-form-item> -->
    </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateStu">确 定</el-button>
      </span>
    </el-dialog>

  </template>
        <!-- <template slot-scope="scope">
          {{ formatIsActive2(scope.row.isReview) }}
        </template> -->
      </el-table-column>
    </el-table>
    <div style="text-align: center">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pagesize"
        :current-page.sync="currentPage"
        @current-change="current_change"
        class="table-pagination"
      ></el-pagination>
    </div>
  </div>


</template>

<script>
import { getStudentSorce } from "@/api/overview";
import { updateStu } from "@/api/overview";

export default {
  props: {
    selectedGrade: {
      type: String,
      required: true,
    },
    selectedClass: {
      type: String,
      required: true,
    },
    selectedTerm: {
      type: String,
      required: true,
    },
  },
  watch: {
    selectedGrade() {
      this.fetchStudentScoreData();
    },
    selectedClass() {
      this.fetchStudentScoreData();
    },
    selectedTerm() {
      this.fetchStudentScoreData();
    },
  },

  mounted() {
    this.fetchStudentScoreData();
  },

  methods: {
    navigateToPersonal(studentName) {
      this.$router.push('/performanceevaluation/personal?studentName='+studentName);
    },
    review(row){
      this.student=row
      this.dialogVisible=true
    },
    updateStu(){
      console.log("stu:",this.student)
      console.log("stu:",this.selectedGrade)
      let data={
        grade:this.selectedGrade,
        term:this.selectedTerm,
        studentScore:this.student
      };
      updateStu(data)
      this.dialogVisible=false;
      // this.$router.go(0);
    },

    //是否录入和评阅
    // formatIsActive1(isReviewed) {
    //   return isReviewed ? "已录入" : "未录入";
    // },

    // formatIsActive2(isGradeEntered) {
    //   return isGradeEntered == "true" ? "已评阅" : "未评阅";
    // },

    //分页切换
    current_change(currentPage) {
      this.currentPage = currentPage;
    },

    async fetchStudentScoreData() {
      try {
        const response = await getStudentSorce({
          stuGrade: this.selectedGrade,
          stuClass: this.selectedClass,
          stuTerm: this.selectedTerm,
        });
        console.log("拿到学生信息", response.data);
        this.tableData = response.data;
        this.total = response.data.length;
        this.currentPage = 1;
        console.log(this.currentPage);
      } catch (error) {
        // 捕获请求失败的错误
        this.error = error;
        console.error("学生信息 Error fetching data:", error);
      }
    },
  },

  data() {
    return {
      total: 18, //总条数
      pagesize: 5, //指定展示多少条
      currentPage: 1, //当前页码下·
      dialogVisible: false ,
      student: {},
      tableData: [
        {
          studentName: "张三",
          moralityScore: 80,
          intelligenceScore: 79,
          physicalScore: 76,
          aestheticScore: 75,
          labourScore: 81,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 81,
          intelligenceScore: 80,
          physicalScore: 83,
          aestheticScore: 81,
          labourScore: 79,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 79,
          intelligenceScore: 77,
          physicalScore: 81,
          aestheticScore: 80,
          labourScore: 80,
          reviewed: false,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 80,
          intelligenceScore: 79,
          physicalScore: 76,
          aestheticScore: 75,
          labourScore: 81,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 81,
          intelligenceScore: 80,
          physicalScore: 83,
          aestheticScore: 81,
          labourScore: 79,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 79,
          intelligenceScore: 77,
          physicalScore: 81,
          aestheticScore: 80,
          labourScore: 80,
          reviewed: false,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 80,
          intelligenceScore: 79,
          physicalScore: 76,
          aestheticScore: 75,
          labourScore: 81,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 81,
          intelligenceScore: 80,
          physicalScore: 83,
          aestheticScore: 81,
          labourScore: 79,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 79,
          intelligenceScore: 77,
          physicalScore: 81,
          aestheticScore: 80,
          labourScore: 80,
          reviewed: false,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 80,
          intelligenceScore: 79,
          physicalScore: 76,
          aestheticScore: 75,
          labourScore: 81,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 81,
          intelligenceScore: 80,
          physicalScore: 83,
          aestheticScore: 81,
          labourScore: 79,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 79,
          intelligenceScore: 77,
          physicalScore: 81,
          aestheticScore: 80,
          labourScore: 80,
          reviewed: false,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 80,
          intelligenceScore: 79,
          physicalScore: 76,
          aestheticScore: 75,
          labourScore: 81,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 81,
          intelligenceScore: 80,
          physicalScore: 83,
          aestheticScore: 81,
          labourScore: 79,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 79,
          intelligenceScore: 77,
          physicalScore: 81,
          aestheticScore: 80,
          labourScore: 80,
          reviewed: false,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 80,
          intelligenceScore: 79,
          physicalScore: 76,
          aestheticScore: 75,
          labourScore: 81,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 81,
          intelligenceScore: 80,
          physicalScore: 83,
          aestheticScore: 81,
          labourScore: 79,
          reviewed: true,
          gradeEntered: true,
        },
        {
          studentName: "张三",
          moralityScore: 79,
          intelligenceScore: 77,
          physicalScore: 81,
          aestheticScore: 80,
          labourScore: 80,
          reviewed: false,
          gradeEntered: true,
        },
      ],
    };
  },
};
</script>

<style scoped>
.table-title {
  margin-left: 5px;
  font-weight: 800;
  height: 30px;
  font-size: 26px;
  margin-bottom: 30px;
}

.table-pagination {
  margin-top: 20px;
}

.student-form-item {
  display: flex;
  align-items: center; /* 垂直居中 */
}
</style>
