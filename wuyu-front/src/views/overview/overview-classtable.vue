<template>
  <div>
    <h1 class="table-title">班级成绩录入评阅情况</h1>
    <el-table
      :data="tableData"
      border
      style="width: 100%; font-size: 22px"
      :row-style="{ height: '80px' }"
      class="table-body"
    >
      <el-table-column
        fixed
        prop="className"
        label="班级"
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
        prop="gradeEntered"
        label="是否录入"
        width="120"
        align="center"
      >
        <template slot-scope="scope">
          <el-button v-if="scope.row.gradeEntered === true" type="text" size="big" style="color: green;" 
     >
      已录入
    </el-button>
    <el-button @click="navigateToCourseScore()" v-else type="text" size="big" style="color: red;">
      未录入
    </el-button>
          <!-- {{ formatIsActive1(scope.row.gradeEntered) }} -->
        </template></el-table-column
      >
      <el-table-column
        prop="reviewed"
        label="是否评阅"
        width="120"
        align="center"
      >
        <template slot-scope="scope">
          <el-button v-if="scope.row.reviewed === true" type="text" size="big" style="color: green;" 
    @click="review(scope.row)" >
      已评阅
    </el-button>
    <el-button v-else type="text" size="big" style="color: red;" @click="navigateToRemark()">
      未评阅
    </el-button>
          <!-- {{ formatIsActive2(scope.row.reviewed) }} -->
        </template>

      </el-table-column>

      <el-table-column fixed="right" label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small"
            >查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getClassScore } from "@/api/overview/index";
export default {
  props: {
    selectedGrade: {
      type: String,
      required: true,
    },

    selectedTerm: {
      type: String,
      required: true,
    },
  },
  methods: {
    navigateToRemark(){
      this.$router.push('/remark/Home');
    },
    navigateToCourseScore(){
      this.$router.push('/course_score/view');
    },
    //点击详情
    handleClick(row) {
      console.log(row);
      let selectedGrade = this.selectedGrade;
      let selectedTerm = this.selectedTerm == 0 ? "202012" : "202107";
      let className = row.className;

      let selectedClass;
      if (className == "一班") {
        selectedClass = "1";
      } else if (className == "二班") {
        selectedClass = "2";
      } else {
        selectedClass = "3";
      }

      this.$router.push({
        name: "detail",
        params: { selectedGrade, selectedClass, selectedTerm },
      });
    },

    //是否录入和评阅
    formatIsActive1(isReviewed) {
      return isReviewed ? "已录入" : "未录入";
    },

    formatIsActive2(isGradeEntered) {
      return isGradeEntered ? "已评阅" : "未评阅";
    },

    async fetchClassScoreData() {
      try {
        const response = await getClassScore({
          grade: this.selectedGrade,
          term: this.selectedTerm,
        }); // 调用 getClassScore 方法并等待结果
        console.log("拿到班级信息", response.data);
        this.tableData = response.data;
      } catch (error) {
        // 捕获请求失败的错误
        this.error = error;
        console.error("班级信息 Error fetching data:", error);
      }
    },
  },

  data() {
    return {
      tableData: [
        {
          className: "一班",
          moralityScore: 80,
          intelligenceScore: 79,
          physicalScore: 76,
          aestheticScore: 75,
          labourScore: 81,
          reviewed: true,
          gradeEntered: true,
        },
        {
          className: "二班",
          moralityScore: 81,
          intelligenceScore: 80,
          physicalScore: 83,
          aestheticScore: 81,
          labourScore: 79,
          reviewed: true,
          gradeEntered: true,
        },
        {
          className: "三班",
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

  mounted() {
    this.fetchClassScoreData();
  },

  watch: {
    selectedGrade() {
      this.fetchClassScoreData();
    },
    selectedTerm() {
      this.fetchClassScoreData();
    },
  },
};
</script>

<style scoped>
.table-title {
  margin-left: 5px;
  font-weight: 800;
  margin-bottom: 40px;
}
</style>
