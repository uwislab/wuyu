<template>
  <div class="container">
    <!-- 左侧表格区域 -->
    <div id="table_block">
      <!-- 表格顶端搜索区域 -->
      <div id="search_bar">
        <!-- 下拉选择框 -->
        <div id="select_bar">
          <!-- 年级选择框 -->
          <el-select class="select_block" v-model="selectedGradeId" placeholder="请选择年级" @change="selectGrade">
            <el-option
              v-for="(item, index) in gradeList"
              :key="item"
              :label="gradeList[index]"
              :value="index+1"/>
          </el-select >
          <!-- 班级选择框 -->
          <el-select class="select_block" v-model="selectedClassId" placeholder="请选择班级" @change="selectClass">
            <el-option
              v-for="item in classList"
              :key="item"
              :label="item+'班'"
              :value="item"/>
          </el-select>
          <!-- 日期选择框 -->
          <el-select class="select_block" v-model="selectedDate" placeholder="请选择学年">
            <el-option
              v-for="item in dateList"
              :key="item"
              :label="item"
              :value="item"/>
          </el-select>
          <!-- 下拉选择条件搜索按钮 -->
          <el-button class="search_button" type="success" @click="getScoreByClass">
            <img src="../../assets/img/search_button.png" class="button_img"/>
          </el-button>
        </div>
        <!-- 搜索框 -->
        <div id="input_bar">
          <el-input id="search_input" placeholder="输入学号或姓名搜索"></el-input>
          <el-button class="search_button" type="success" @click="getScoreByInfo">
            <img src="../../assets/img/search_button.png" class="button_img"/>
          </el-button>
        </div>
      </div>

      <!-- 成绩表区域 -->
      <el-table :data="classFuScoreList"
                border stripe
                id="fu_score_table"
                @row-click="checkStudentInfo">
        <el-table-column label="序号" type="index" width="50" align="center"/>
        <el-table-column prop="studentName" label="学生姓名" align="center"/>
        <el-table-column prop="studentNum" label="学号" align="center"/>
        <el-table-column prop="gradeId" label="年级" align="center"/>
        <el-table-column prop="classId" label="班级" align="center"/>
        <el-table-column prop="moralityScore" label="德育成绩" align="center"/>
        <el-table-column prop="intelligenceScore" label="智育成绩" align="center"/>
        <el-table-column prop="physicalScore" label="体育成绩" align="center"/>
        <el-table-column prop="aestheticScore" label="美育成绩" align="center"/>
        <el-table-column prop="labourScore" label="劳育成绩" align="center"/>
        <el-table-column prop="evaluateDate" label="日期" align="center"/>
      </el-table>
    </div>

    <!-- 右侧详情区域 -->
    <div id="student_block">
      <div class="basic_info">
        <!-- 学生基本信息展示栏 -->
        <el-descriptions border title="学生信息">
          <el-descriptions-item label="相片">
            <el-avatar
              size="large"
              src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
          </el-descriptions-item>
          <el-descriptions-item label="姓名">{{ studentInfo.studentName }}</el-descriptions-item>
          <el-descriptions-item label="学号">{{ studentInfo.studentNum }}</el-descriptions-item>
          <el-descriptions-item label="班主任信息">
            <el-collapse>
              <el-collapse-item :title="monitorInfo.name">联系方式：{{monitorInfo.contactInfo}}</el-collapse-item>
            </el-collapse>
          </el-descriptions-item>
        </el-descriptions>
        <!-- 学生成绩对比展示栏 -->
        <el-descriptions column="1" title="成绩">
          <template v-for="(info, index) in scoreCompareInfo">
            <el-descriptions-item class="score_item" :label="wuyuItem[index]">
              <el-collapse style="width: 100%">
                <el-collapse-item :title="info.score">相比去年：{{info.info}}</el-collapse-item>
              </el-collapse>
            </el-descriptions-item>
          </template>
        </el-descriptions>
        <el-button @click="checkStudentAll">查看学生个人情况 ></el-button>
      </div>
    </div>
  </div>
</template>

<script>

import {getDateList, getLastScore, getStudentsFuScoreList} from "@/api/fuScore.js"
import {getClassList} from "@/api/classManage.js"
import {getMonitorByClass} from "@/api/teacher";

export default {
  name: "class_fu_score",
  data () {
    return {
      // 年级下拉框数据来源
      gradeList: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      // 班级下拉框数据来源
      classList: [],
      dateList: [],
      // 下拉框选择年级ID
      selectedGradeId: null,
      // 下拉框选择班级ID
      selectedClassId: null,
      // 下拉框选择日期
      selectedDate: null,
      // 当前班级数据
      classFuScoreList: [],
      // 五育项目列表
      wuyuItem: ['德育', '智育', '体育', '美育', '劳育'],

      //学生信息
      studentInfo: { studentName: '', studentNum: '' },
      // 班主任信息
      monitorInfo: { name: '', contactInfo: '' },
      scoreCompareInfo: [],
    };
  },
  mounted() {
    getDateList().then(res => {
      this.dateList = res.data;
    })
  },
  methods: {
    // 年级选择框获取方法
    selectGrade (item) {
      this.selectedGradeId = item;
      getClassList(item).then(res => {
        this.classList = res.data
      })
    },
    // 班级选择框获取方法
    selectClass (item) {
      this.selectedClassId = item;
    },
    // 根据班级、五育项目查询成绩（页面初始化加载）
    getScoreByClass () {
      console.log(this);
      let {
        selectedGradeId,
        selectedClassId,
        selectedDate
      } = this;
      console.log(selectedGradeId + '|' + selectedClassId + '|' + selectedDate);
      if (selectedGradeId == null || selectedClassId == null || selectedDate == null) {
        console.log('请选择年级、班级、日期');
        return;
      }
      getStudentsFuScoreList(selectedGradeId, selectedClassId, selectedDate).then( res => {
        this.classFuScoreList = res.data;
      })
    },
    // 根据学生信息查询成绩
    getScoreByInfo () {

    },
    // 学生成绩比较
    getScoreCompareInfo (row) {
      this.scoreCompareInfo = [];
      getLastScore(row.studentNum, row.evaluateDate-100).then(res => {
        if (res.data == null) {
          this.scoreCompareInfo.push({score: row.moralityScore, info: '无上一年同期成绩'});
          this.scoreCompareInfo.push({score: row.intelligenceScore, info: '无上一年同期成绩'});
          this.scoreCompareInfo.push({score: row.physicalScore, info: '无上一年同期成绩'});
          this.scoreCompareInfo.push({score: row.aestheticScore, info: '无上一年同期成绩'});
          this.scoreCompareInfo.push({score: row.labourScore, info: '无上一年同期成绩'});
        } else {
          console.log('有去年数据');
          this.scoreCompareInfo.push({score: row.moralityScore, info: row.moralityScore-res.data.moralityScore>0?'↑':'↓'});
          this.scoreCompareInfo.push({score: row.intelligenceScore, info: row.intelligenceScore-res.data.intelligenceScore>0?'↑':'↓'});
          this.scoreCompareInfo.push({score: row.physicalScore, info: row.physicalScore-res.data.physicalScore>0?'↑':'↓'});
          this.scoreCompareInfo.push({score: row.aestheticScore, info: row.aestheticScore-res.data.aestheticScore>0?'↑':'↓'});
          this.scoreCompareInfo.push({score: row.labourScore, info: row.labourScore-res.data.labourScore>0?'↑':'↓'});
        }
      })
    },
    // 点击表格行，查看学生信息
    checkStudentInfo (row) {
      this.studentInfo.studentName = row.studentName;
      this.studentInfo.studentNum = row.studentNum;
      getMonitorByClass(row.gradeId, row.classId).then(res => {
        this.monitorInfo.name = res.data.realName;
        this.monitorInfo.contactInfo = res.data.contactInfo;
      });
      this.getScoreCompareInfo(row);
    },
    checkStudentAll() {
      this.$router.push( {
        path: 'student_wuyu_info',
        query: {
          studentInfo: this.studentInfo,
          monitorInfo: this.monitorInfo,
        }
      });
    }
  },
}
</script>

<style scoped>
  /* 主容器设置 */
  .container {
    /* 自身大小设置及定位 */
    width: 1680px;
    height: 850px;
    margin: 10px;
    /* 样式设计 */
    /* 子组件定位设置 */
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

    /* 左侧表格区域设置 */
    #table_block {
      /* 自身大小设置及定位 */
      width: 70%;
      height: 100%;
      margin: 5px;
      /* 样式设计 */
      border: solid 2px black;
      /* 子组件定位设置 */
      padding: 10px;
      /* 子组件定位设置 */
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    }
    /* 表格顶端搜索区域设置 */
    #search_bar {
      /* 自身大小设置及定位 */
      width: 100%;
      height: 10%;
      margin: 10px;
      /* 样式设计 */
      border: solid 2px black;
      /* 子组件定位设置 */
      display: flex;
      align-items: center;
      justify-content: center;
    }
      /* 多选框区域设置 */
      #select_bar {
        /* 自身大小设置及定位 */
        width: 70%;
        height: 100%;
        margin: 5px;
        /* 样式设计 */
        border-right: solid 1px black;
        /* 子组件定位设置 */
        display: flex;
        align-items: center;
        justify-content: center;
      }
      /* 搜索框区域设置 */
      #input_bar {
        /* 自身大小设置及定位 */
        width: 30%;
        height: 100%;
        margin: 5px;
        /* 样式设计 */
        /* 子组件定位设置 */
        display: flex;
        align-items: center;
        justify-content: center;
      }
        /* 搜索框设置 */
        #search_input {
          /* 自身大小设置及定位 */
          width: auto;
          height: 100%;
          margin: 5px;
        }
        /* 搜索确认按钮设置 */
        .search_button {
          /* 自身大小设置及定位 */
          width: 25px;
          height: 40px;
          margin: 5px;
          /* 样式设计 */
          border-radius: 50%;
          /* 子组件定位设置 */
          display: flex;
          align-items: center;
          justify-content: center;
        }
          /* 搜索按钮图片设置 */
          .button_img {
            /* 自身大小设置 */
            width: 30px;
            height: 30px;
          }
    /* 成绩表区域设置 */
    #fu_score_table {
      /* 自身大小设置及定位 */
      width: 100%;
      height: auto;
      margin: 10px;
      /* 样式设计 */
      overflow: auto;
    }

    /* 右侧详情区域 */
    #student_block {
      /* 自身大小设置及定位 */
      width: 45%;
      height: 100%;
      margin: 5px;
      /* 样式设计 */
      border: solid 2px black;
      padding: 5px;
    }
    .score_item {
      width: 100%;
      font-size: 30px;
      font-weight: bold;
    }
</style>
