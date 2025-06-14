<template>
  <div style="padding:20px;">
    <!-- 年级五育雷达图 -->
    <el-row :gutter="20">
      <el-col style="margin-bottom: 20px;" :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>年级五育雷达图</span>
            <el-select v-model="selectedGrade" placeholder="请选择年级" style="float: right; width: 150px;" @change="handleGradeChange">
              <el-option v-for="item in gradeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </div>
          <div id="gradeChart" style="width: 100%;height:400px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 班级五育雷达图 -->
    <el-row :gutter="20">
      <el-col style="margin-bottom: 20px;" :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>班级五育雷达图</span>
            <div style="float: right;">
              <el-select v-model="selectedClassGrade" placeholder="请选择年级" style="width: 120px; margin-right: 10px;" @change="handleClassGradeChange">
                <el-option v-for="item in gradeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
              <el-select v-model="selectedClass" placeholder="请选择班级" style="width: 120px;" @change="handleClassChange">
                <el-option v-for="item in classOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </div>
          </div>
          <div id="classChart" style="width: 100%;height:400px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 个人五育雷达图 -->
    <el-row :gutter="20">
      <el-col style="margin-bottom: 20px;" :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人五育雷达图</span>
            <div style="float: right;">
              <el-input v-model="studentName" placeholder="请输入姓名" style="width: 120px; margin-right: 10px;"></el-input>
              <el-input v-model="studentId" placeholder="请输入学号" style="width: 150px; margin-right: 10px;"></el-input>
              <el-button type="primary" size="small" @click="getStudentData">查询</el-button>
            </div>
          </div>
          <div id="studentChart" style="width: 100%;height:400px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import api from '@/api/demonstrate/api'
import echarts from 'echarts'

export default {
  data() {
    return {
      // 颜色列表
      colorList: ['#1b6cd2', '#B5C334', '#FCCE10', 'rgba(144,26,194,0.74)', '#27727B', '#901AC2BC'],

      // 年级图表相关数据
      gradeChart: null,
      gradeData: {},
      selectedGrade: '1',
      gradeOptions: [
        { value: '1', label: '一年级' },
        { value: '2', label: '二年级' },
        { value: '3', label: '三年级' },
        { value: '4', label: '四年级' },
        { value: '5', label: '五年级' },
        { value: '6', label: '六年级' }
      ],

      // 班级图表相关数据
      classChart: null,
      classData: {},
      selectedClassGrade: '1',
      selectedClass: '1',
      classOptions: [
        { value: '1', label: '1班' },
        { value: '2', label: '2班' },
        { value: '3', label: '3班' },
        { value: '4', label: '4班' },
        { value: '5', label: '5班' }
      ],

      // 学生图表相关数据
      studentChart: null,
      studentData: [],
      studentName: '',
      studentId: ''
    }
  },
  mounted() {
    // 初始化三个图表
    this.initGradeChart();
    this.initClassChart();
    this.initStudentChart();

    // 获取初始数据
    this.getGradeData();
    this.getClassData();

    // 监听窗口大小变化，重绘图表
    window.addEventListener('resize', this.handleResize);
  },
  methods: {
    // 窗口大小变化处理
    handleResize() {
      if (this.gradeChart) this.gradeChart.resize();
      if (this.classChart) this.classChart.resize();
      if (this.studentChart) this.studentChart.resize();
    },

    // 年级名称转换
    getGradeName(grade) {
      const gradeMap = {
        '1': '一年级',
        '2': '二年级',
        '3': '三年级',
        '4': '四年级',
        '5': '五年级',
        '6': '六年级'
      };
      return gradeMap[grade] || 'Unknown';
    },

    // 初始化年级图表
    initGradeChart() {
      this.gradeChart = echarts.init(document.getElementById('gradeChart'));
    },

    // 初始化班级图表
    initClassChart() {
      this.classChart = echarts.init(document.getElementById('classChart'));
    },

    // 初始化学生图表
    initStudentChart() {
      this.studentChart = echarts.init(document.getElementById('studentChart'));
    },

    // 获取年级数据
    getGradeData() {
      api.getWydc({ grade: this.selectedGrade }).then(res => {
        this.gradeData = res;
        this.updateGradeChart();
      }).catch(err => {
        console.error('获取年级数据失败:', err);
        this.$message.error('获取年级数据失败');
      });
    },

    // 获取班级数据
    getClassData() {
      api.getClassAverageScores({
        grade: this.selectedClassGrade,
        sclass: this.selectedClass
      }).then(res => {
        this.classData = res;
        this.updateClassChart();
      }).catch(err => {
        console.error('获取班级数据失败:', err);
        this.$message.error('获取班级数据失败');
      });
    },

    // 获取学生数据
    getStudentData() {
      if (!this.studentName && !this.studentId) {
        this.$message.warning('请输入学生姓名或学号');
        return;
      }

      api.getStudentScore({
        name: this.studentName,
        id: this.studentId
      }).then(res => {
        this.studentData = res;
        this.updateStudentChart();
      }).catch(err => {
        console.error('获取学生数据失败:', err);
        this.$message.error('获取学生数据失败');
      });
    },

    // 更新年级图表
    updateGradeChart() {
      if (!this.gradeChart || !this.gradeData) return;

      let list = [];
      // 只添加当前选择的年级数据
      let data = {
        value: this.gradeData[this.selectedGrade],
        name: this.getGradeName(this.selectedGrade)
      };
      list.push(data);

      const option = {
        title: {
          text: `${this.getGradeName(this.selectedGrade)}五育成绩`,
          left: 'center'
        },
        legend: {
          data: this.gradeData.gradeList || [],
          bottom: 0
        },
        tooltip: {
          show: true,
        },
        radar: {
          indicator: [
            { name: '德', max: 100 },
            { name: '智', max: 100 },
            { name: '体', max: 100 },
            { name: '美', max: 100 },
            { name: '劳', max: 100 }
          ]
        },
        series: [
          {
            type: 'radar',
            emphasis: {
              label: {
                show: true,
                position: 'top'
              },
              lineStyle: {
                width: 4
              },
              areaStyle: {}
            },
            data: list,
            itemStyle: {
              normal: {
                label: {
                  show: true
                }
              }
            }
          }
        ]
      };

      this.gradeChart.setOption(option);
    },

    // 更新班级图表
    updateClassChart() {
      if (!this.classChart || !this.classData) return;

      const option = {
        title: {
          text: `${this.getGradeName(this.selectedClassGrade)}${this.selectedClass}班五育成绩`,
          left: 'center'
        },
        tooltip: {
          show: true,
        },
        radar: {
          indicator: [
            { name: '德', max: 100 },
            { name: '智', max: 100 },
            { name: '体', max: 100 },
            { name: '美', max: 100 },
            { name: '劳', max: 100 }
          ]
        },
        series: [
          {
            type: 'radar',
            emphasis: {
              label: {
                show: true,
                position: 'top'
              },
              lineStyle: {
                width: 4
              },
              areaStyle: {}
            },
            data: [
              {
                value: [
                  this.classData['德育'] || 0,
                  this.classData['智育'] || 0,
                  this.classData['体育'] || 0,
                  this.classData['美育'] || 0,
                  this.classData['劳育'] || 0
                ],
                name: `${this.getGradeName(this.selectedClassGrade)}${this.selectedClass}班`,
                itemStyle: {
                  color: this.colorList[1]
                }
              }
            ],
            itemStyle: {
              normal: {
                label: {
                  show: true
                }
              }
            }
          }
        ]
      };

      this.classChart.setOption(option);
    },

    // 更新学生图表
    updateStudentChart() {
      if (!this.studentChart || !this.studentData || this.studentData.length === 0) return;

      const studentInfo = this.studentData[0];
      const option = {
        title: {
          text: `${this.studentName || this.studentId}五育成绩`,
          left: 'center'
        },
        tooltip: {
          show: true,
        },
        radar: {
          indicator: [
            { name: '德', max: 100 },
            { name: '智', max: 100 },
            { name: '体', max: 100 },
            { name: '美', max: 100 },
            { name: '劳', max: 100 }
          ]
        },
        series: [
          {
            type: 'radar',
            emphasis: {
              label: {
                show: true,
                position: 'top'
              },
              lineStyle: {
                width: 4
              },
              areaStyle: {}
            },
            data: [
              {
                value: [
                  studentInfo.sDeyu || 0,
                  studentInfo.sZhiyu || 0,
                  studentInfo.sTiyu || 0,
                  studentInfo.sMeiyu || 0,
                  studentInfo.sLaoyu || 0
                ],
                name: this.studentName || this.studentId,
                itemStyle: {
                  color: this.colorList[2]
                }
              }
            ],
            itemStyle: {
              normal: {
                label: {
                  show: true
                }
              }
            }
          }
        ]
      };

      this.studentChart.setOption(option);
    },

    // 年级选择变化处理
    handleGradeChange(value) {
      this.selectedGrade = value;
      this.getGradeData();
    },

    // 班级年级选择变化处理
    handleClassGradeChange(value) {
      this.selectedClassGrade = value;
      this.getClassData();
    },

    // 班级选择变化处理
    handleClassChange(value) {
      this.selectedClass = value;
      this.getClassData();
    }
  },
  // 在生命周期末销毁图表，防止内存泄漏
  beforeDestroy() {
    // 移除窗口大小变化监听
    window.removeEventListener('resize', this.handleResize);

    // 销毁图表实例
    if (this.gradeChart) {
      this.gradeChart.dispose();
      this.gradeChart = null;
    }
    if (this.classChart) {
      this.classChart.dispose();
      this.classChart = null;
    }
    if (this.studentChart) {
      this.studentChart.dispose();
      this.studentChart = null;
    }
  }
}
</script>

<style scoped>
.box-card {
  margin-bottom: 20px;
}
</style>
