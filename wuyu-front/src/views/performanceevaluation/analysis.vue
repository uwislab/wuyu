<template>
  <div>
    <div class="search">
      <el-form :model="form" v-if="canSeeForm" label-width="80px" inline>
        <el-form-item label="年级">
          <el-select v-model="form.grade" placeholder="请选择年级" @change="getClassByGrade(form.grade)">
            <el-option
              v-for="grade in grades"
              :key="grade"
              :label="grade"
              :value="grade"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="form.class"  placeholder="请选择班级" @change="getClassScore">
            <el-option
              v-for="classNum in classNumbers"
              :key="classNum.classID"
              :label="classNum.className"
              :value="classNum.className"
            ></el-option>
          </el-select>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="resetForm">重置</el-button>
          <el-button type="primary" @click="findData">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="chart-container">
      <div class="chart" ref="pieChart"></div>
      <div class="chart" ref="lineChart"></div>
    </div>

    <div class="table-container">
      <el-table :data="pagedStudents" :row-key="row => row.id" border>

        <el-table-column type="index" label="id"></el-table-column>
        <el-table-column prop="studentName" label="姓名"></el-table-column>
        <el-table-column prop="gender" label="性别">
          <template slot-scope="scope">
            {{ genderLabel[scope.row.gender] }}
          </template>
        </el-table-column>
        <el-table-column  label="年级">{{form.grade}}</el-table-column>
        <el-table-column  label="班级">{{form.class}}</el-table-column>
        <el-table-column label="德">
          <template slot-scope="scope">
            <div class="score-cell">
              <div class="score">{{ scope.row.moralityScore }}</div>
              <div class="grade-distribution">{{ getGradeDistribution(scope.row.moralityScore) }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="智">
          <template slot-scope="scope">
            <div class="score-cell">
              <div class="score">{{ scope.row.intelligenceScore }}</div>
              <div class="grade-distribution">{{ getGradeDistribution(scope.row.intelligenceScore) }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="体">
          <template slot-scope="scope">
            <div class="score-cell">
              <div class="score">{{ scope.row.physicalScore }}</div>
              <div class="grade-distribution">{{ getGradeDistribution(scope.row.physicalScore) }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="美">
          <template slot-scope="scope">
            <div class="score-cell">
              <div class="score">{{ scope.row.aestheticScore }}</div>
              <div class="grade-distribution">{{ getGradeDistribution(scope.row.aestheticScore) }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="劳">
          <template slot-scope="scope">
            <div class="score-cell">
              <div class="score">{{ scope.row.labourScore }}</div>
              <div class="grade-distribution">{{ getGradeDistribution(scope.row.labourScore) }}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" @click="checkDetail(scope.row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        :total="students.length"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts"
import { Message } from "element-ui";
import {getClassByGrade, getScoreByClassId, getScoreByGradeAndClassAndItem} from "@/api/fuScore";
export default {
  name: 'analysis',
  data() {
    return {
      chartSeries: [], // 存储所有班级的折线数据
      gradeCounts: {
        优: 0,
        良: 0,
        中: 0,
        差: 0
      },
      tid: '',
      // tid对应的教师信息
      score: '',//标准分
      teacher: {},
      // 优势科目分析数据
      data: [],
      classScore: [],
      classId: 0,
      // 优势科目展示
      subjects: ['virtue', 'intelligence', 'sports', 'art', 'labor'],
      // 优势科目名称
      subjectsName: ['德育', '智育', '体育', '美育', '劳育'],
      form: {
        grade: '',
        class: "",
        standard: '',
      },
      grades: ['1年级', '2年级', '3年级', '4年级', '5年级', '6年级'],
      classNumbers: [

      ],
      standards: ['知识达标', '能力水平'],
      students: [],
      currentPage: 1,
      pageSize: 10,

    }
  },
  computed: {
    genderLabel() {
      return {
        0: '女',
        1: '男',
      };
    },
    pagedStudents() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.students.slice(startIndex, endIndex);
    },
  },
  watch: {
    data: {
      handler() {
      },
      deep: true,
    }
  },
  methods: {
    getClassByGrade(gradeId) {
      console.log(gradeId);

      getClassByGrade(gradeId.charAt(0))
        .then(response => {
          console.log(response.data);
          this.classNumbers = response.data;
        })
        .catch(error => {
          console.error(error);
          Message.error('Failed to get class names for the selected grade.');
        });
    },
    /*getClassScore() {
      getScoreByGradeAndClassAndItem(this.form.class, this.form.grade.charAt(0)).then(response => {
        console.log(response.data)
        const classS = response.data
        this.classId = classS[0].classID
        var avgScores = classS.map((item) => {
          const totalScore = item.moralityScore + item.intelligenceScore + item.physicalScore + item.aestheticScore + item.labourScore;
          const avgScore = totalScore / 5;

          return {
            avgScore,
            evaluateDate: item.evaluateDate
          };
        });
        avgScores.sort((a, b) => a.evaluateDate - b.evaluateDate);
        var i = 1
        avgScores.forEach((item) => {
          avgScores[i - 1].evaluateDate = "第" + i + "学期"
          i++
        })

        console.log(avgScores)
        this.renderLineChart(avgScores)
      })
    },*/
    getClassScore() {
      getScoreByGradeAndClassAndItem(this.form.class, this.form.grade.charAt(0)).then(response => {
        console.log(response.data)
        const classS = response.data;
        this.classId = classS[0].classID;
        const classid=classS[0].classID;
        var avgScores = classS.map((item) => {
          const totalScore = item.moralityScore + item.intelligenceScore + item.physicalScore + item.aestheticScore + item.labourScore;
          const avgScore = totalScore / 5;
          return {
            avgScore,
            evaluateDate: item.evaluateDate
          };
        });

        avgScores.sort((a, b) => a.evaluateDate - b.evaluateDate);


        var i = 1
        avgScores.forEach((item) => {
          avgScores[i - 1].evaluateDate = "第" + i + "学期"
          i++
        })
        console.log(avgScores)

        // 创建新的系列对象，并添加到chartSeries数组中
        const newSeries = {
          name: `班级${classid}`,
          type: 'line',
          data: avgScores.map(item => item.avgScore),
          lineStyle: {
            color: this.getClassColor(classid), // 使用getClassColor获取颜色
          },
        };

        this.chartSeries.push(newSeries);

        this.renderLineChart(avgScores);
      })
    },
    getClassColor(classId) {
      // 这里提供一个简单的颜色生成逻辑，您可以根据需要替换为更复杂的逻辑
      const colors = ['#ff0000', '#00ff00', '#0000ff', '#ffff00', '#ff00ff', '#00ffff'];
      const colorIndex = classId % colors.length;
      return colors[colorIndex];
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.currentPage = 1;
    },

    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },

    getGradeDistribution(grade) {
      if (grade >= 90) {
        return '优秀';
      } else if (grade >= 75) {
        return '良好';
      } else if (grade >= 60) {
        return '及格';
      } else {
        return '不及格';
      }
    },
    resetForm() {
      this.form.grade = '';
      this.form.class = '';
      this.form.standard = '';
      this.classId = 0;
      this.chartSeries=[];
      this.students=[];
      this.clearLineChart();
      this.clearPieChart();
    },
    getGradeCounts(totalScore) {
      if (totalScore >= 90) {
        this.gradeCounts.优++
      } else if (totalScore >= 80) {
        this.gradeCounts.良++
      } else if (totalScore >= 60) {
        this.gradeCounts.中++
      } else {
        this.gradeCounts.差++
      }
    }
    ,
    findData() {
      // 在这里处理表单提交逻辑
      console.log('年级:', this.form.grade);
      console.log('班级:', this.form.class);
      console.log('评价标准:', this.form.standard);

      const gradeNumber = this.extractGradeNumber();
      const classNumber = this.extractClassNumber();
      if (gradeNumber !== null && classNumber !== null) {
        this.classId = gradeNumber * 10 + classNumber;
      }
      //从年级页面跳转过来


      getScoreByClassId(this.classId).then(response => {
        console.log(response.data)
        if (response.data.length === 0){
          this.$message.error("此班级内不存在学生数据");
          return;
        }

        this.students = response.data
        this.gradeCounts = {
          优: 0,
          良: 0,
          中: 0,
          差: 0
        }

        // 统计学生的成绩 大于90 为优 大于80为良，大于60为中，其余为差
        for (let i = 0; i < this.students.length; i++) {
          const student = this.students[i];
          const moralityScore = student.moralityScore;
          const intelligenceScore = student.intelligenceScore;
          const physicalScore = student.physicalScore;
          const aestheticScore = student.aestheticScore;
          const labourScore = student.labourScore;
          this.getGradeCounts(moralityScore)
          this.getGradeCounts(intelligenceScore)
          this.getGradeCounts(physicalScore)
          this.getGradeCounts(aestheticScore)
          this.getGradeCounts(labourScore)
        }
        this.renderPieChart()

      })


    },
    handleClear() {
      this.data = {}
      this.teacher = {}
      this.showAverage()
    },
    renderPieChart() {
      const chartDom = this.$refs.pieChart;
      const myChart = echarts.init(chartDom);

      const option = {
        title: {
          text: '本学期成绩分布',
          x: 'center',
        },
        series: [
          {
            name: '成绩分布',
            type: 'pie',
            radius: '55%',
            data: [
              {value: this.gradeCounts.差, name: '差', itemStyle: {color: '#FF4D4F'}},
              {value: this.gradeCounts.中, name: '中', itemStyle: {color: '#FFC53D'}},
              {value: this.gradeCounts.良, name: '良', itemStyle: {color: '#52C41A'}},
              {value: this.gradeCounts.优, name: '优', itemStyle: {color: '#1890FF'}},
            ],
          },
        ],
      };

      myChart.setOption(option);
    },

    renderLineChart(avgScores) {
      const chartDom = this.$refs.lineChart;
      const myChart = echarts.init(chartDom);
      const option = {
        title: {
          text: '历史平均成绩',
          x: 'center',
        },
        xAxis: {
          type: 'category',
          //data: avgScores.map((item) => item.evaluateDate),
          //data: this.chartSeries.flatMap(series => series.data.map(item => item.evaluateDate)),
          data: ['第一学期', '第二学期', '第三学期', '第四学期', '第五学期', '第六学期'],
          axisLabel: {
            interval: 0, // 显示所有标签
          },
        },
        yAxis: {
          type: 'value',
        },

        series: this.chartSeries, // 使用chartSeries数组中的系列对象
      };

      myChart.setOption(option);
    },
    clearLineChart() {
      const chartDom = this.$refs.lineChart;
      const myChart = echarts.getInstanceByDom(chartDom); // 获取已初始化的图表实例
      myChart.clear();
      myChart.setOption({
        title: {
          text: '历史平均成绩',
          x: 'center',
        },
        xAxis: {
          type: 'category',
          //data: avgScores.map((item) => item.evaluateDate),
          //data: this.chartSeries.flatMap(series => series.data.map(item => item.evaluateDate)),
          data: ['第一学期', '第二学期', '第三学期', '第四学期', '第五学期', '第六学期'],
          axisLabel: {
            interval: 0, // 显示所有标签
          },
        },
        yAxis: {
          type: 'value',
        },
        series: []
      });
    },
    clearPieChart() {
      const chartDom = this.$refs.pieChart;
      const myChart = echarts.getInstanceByDom(chartDom); // 获取已初始化的图表实例
      myChart.clear();
      myChart.setOption({
        title: {
          text: '本学期成绩分布',
          x: 'center',
        },
        series: [
          {
            name: '成绩分布',
            type: 'pie',
            radius: '55%',
            data: [
              {value: 1, name: '差', itemStyle: {color: '#FF4D4F'}},
              {value: 1, name: '中', itemStyle: {color: '#FFC53D'}},
              {value: 1, name: '良', itemStyle: {color: '#52C41A'}},
              {value: 1, name: '优', itemStyle: {color: '#1890FF'}},
            ],
          },
        ],
      });
    },
    extractGradeNumber() {
      // 提取年级中的数字  例：1年级
      return parseInt(this.form.grade.charAt(0), 10);
    },
    extractClassNumber() {
      // 提取班级的第一个字符 例：一班
      const firstChar = this.form.class.charAt(0);
      // 汉字数字到阿拉伯数字的映射
      const numberMapping = {
        '一': 1,
        '二': 2,
        '三': 3,
        '四': 4,
        '五': 5,
        '六': 6,
        '七': 7,
        '八': 8,
        '九': 9,
        '十': 10
      };
      // 返回对应的数字
      return numberMapping[firstChar] || null;
    },
    checkDetail(row) {
      const studentName = row.studentName;
      window.localStorage.setItem(
        "studentName",
        studentName
      );
      // this.$message.error(window.localStorage.getItem("studentName"))
      this.$router.push({ path: '/performanceevaluation/personal' });
    },
    checkIdentity() {
      const userInFoString = window.localStorage.getItem("UserInfo");
      const jsonObject = JSON.parse(userInFoString);

      const  identity = jsonObject.identity;
      const  id = jsonObject.id;

      //校长和教导主任
      if(identity == 0 || identity == 1){
        this.canSeeForm = true;
      }
      //班主任和老师，无查询
      if(identity == 2 || identity == 3){
        this.canSeeForm = false;
      }
    },
  },

  created() {

    // 获取查询参数
    this.form.grade = this.$route.query.grade || '';
    this.form.class = this.$route.query.className || '';
    console.log(this.form.grade);
    console.log(this.form.class);
    if(this.form.grade && this.form.class){

      this.findData();
      this.getClassByGrade(this.form.grade);
      this.getClassScore();
    }

    this.checkIdentity();
  },
  mounted() {

    this.renderPieChart();
    this.renderLineChart([]);
  },
  destroyed() {
    const chartPieDom = this.$refs.pieChart;
    const myPieChart = echarts.getInstanceByDom(chartPieDom)
    if (this.myPieChart) {
      this.myPieChart.dispose(); // 销毁 ECharts 实例
      this.myPieChart = null; // 清理引用
    }

    const chartLineDom = this.$refs.lineChart;
    const myLineChart = echarts.getInstanceByDom(chartLineDom)
    if (this.myLineChart) {
      this.myLineChart.dispose(); // 销毁 ECharts 实例
      this.myLineChart = null; // 清理引用
    }
  }
}
</script>

<style>
.chart-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.chart {
  width: 70%;
  height: 400px;
}
.search{
  margin: 20px 50px 0 50px;
}

.searchbtn{
  margin-left: 10px;
}

.chart{
  margin: 20px 50px 0 50px;
}
.table-container {
  width: 100%;
  overflow-x: auto;
}

.score-cell {
  padding: 0;
  display: flex;
  align-items: center;
}

.score {
  width: 50%;

}

.grade-distribution {
  border-left: 1px solid #ebeef5;
  width: 50%;
  height: 100%;
  text-align: center;
}

.el-table th.gutter,
.el-table td.gutter {
  border-right: 1px solid #ebeef5;
}

</style>
