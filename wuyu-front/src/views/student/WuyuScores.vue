<!-- src/views/student/WuyuScores.vue -->
<template>
  <div class="wuyu-scores-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>学生五育成绩分析</span>
          <el-select v-model="selectedClassId" placeholder="选择班级" style="width: 200px" @change="loadClassData">
            <el-option v-for="item in classList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
          <el-select v-model="selectedStudentId" placeholder="选择学生" style="width: 200px" @change="loadStudentData">
            <el-option v-for="item in studentList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </div>
      </template>

      <!-- 个人成绩卡片 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card title="个人五育成绩">
            <el-descriptions :column="1" bordered v-if="studentScore">
              <el-descriptions-item label="学生姓名">{{ studentScore.studentName }}</el-descriptions-item>
              <el-descriptions-item label="德育成绩">{{ studentScore.moralityScore || 0 }}</el-descriptions-item>
              <el-descriptions-item label="智育成绩">{{ studentScore.intelligenceScore || 0 }}</el-descriptions-item>
              <el-descriptions-item label="体育成绩">{{ studentScore.physicalScore || 0 }}</el-descriptions-item>
              <el-descriptions-item label="美育成绩">{{ studentScore.aestheticScore || 0 }}</el-descriptions-item>
              <el-descriptions-item label="劳育成绩">{{ studentScore.labourScore || 0 }}</el-descriptions-item>
              <el-descriptions-item label="总分">{{ studentScore.totalScore || 0 }}</el-descriptions-item>
            </el-descriptions>
            <div v-else class="empty-state">请选择学生查看成绩</div>
          </el-card>
        </el-col>

        <!-- 班级平均成绩卡片 -->
        <el-col :span="12">
          <el-card title="班级平均成绩">
            <el-descriptions :column="1" bordered v-if="classAverage">
              <el-descriptions-item label="班级名称">{{ classAverage.className || '未知班级' }}</el-descriptions-item>
              <el-descriptions-item label="德育平均分">{{ (classAverage.deyuAverage || 0).toFixed(2) }}</el-descriptions-item>
              <el-descriptions-item label="智育平均分">{{ (classAverage.zhiyuAverage || 0).toFixed(2) }}</el-descriptions-item>
              <el-descriptions-item label="体育平均分">{{ (classAverage.tiyuAverage || 0).toFixed(2) }}</el-descriptions-item>
              <el-descriptions-item label="美育平均分">{{ (classAverage.meiyuAverage || 0).toFixed(2) }}</el-descriptions-item>
              <el-descriptions-item label="劳育平均分">{{ (classAverage.laoyuAverage || 0).toFixed(2) }}</el-descriptions-item>
              <el-descriptions-item label="总分平均分">{{ (classAverage.totalAverage || 0).toFixed(2) }}</el-descriptions-item>
            </el-descriptions>
            <div v-else class="empty-state">请选择班级查看平均分</div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 成绩图表 -->
      <el-row :gutter="20" class="chart-row">
        <el-col :span="24">
          <el-card title="五育成绩对比">
            <div ref="personalChart" style="width: 100%; height: 300px"></div>
            <div ref="classChart" style="width: 100%; height: 300px; margin-top: 20px"></div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { getStudentFuScoreById, getClassAverageScores, getClassList, getStudentListByClass } from '@/api/fuScore'
import echarts from 'echarts'

export default {
  name: 'WuyuScores',
  data() {
    return {
      selectedClassId: null,
      selectedStudentId: null,
      classList: [],
      studentList: [],
      studentScore: null,
      classAverage: null,
      personalChart: null,
      classChart: null
    }
  },
  mounted() {
    this.loadClassData()
  },
  beforeDestroy() {
    if (this.personalChart) this.personalChart.dispose()
    if (this.classChart) this.classChart.dispose()
  },
  methods: {
    // 加载班级列表
    async loadClassData() {
      // 假设这里有获取班级列表的接口
      // 实际项目中需要根据你的数据结构调整
      const classResponse = await getClassList()
      this.classList = classResponse.data || []

      if (this.classList.length > 0) {
        this.selectedClassId = this.classList[0].id
        this.loadStudentData()
        this.loadClassAverageData()
      }
    },

    // 加载班级学生列表
    async loadStudentData() {
      if (!this.selectedClassId) return

      const studentResponse = await getStudentListByClass(this.selectedClassId)
      this.studentList = studentResponse.data || []

      if (this.studentList.length > 0) {
        this.selectedStudentId = this.studentList[0].id
        this.loadStudentScoreData()
      }
    },

    // 加载学生个人成绩
    async loadStudentScoreData() {
      if (!this.selectedStudentId) {
        this.studentScore = null
        return
      }

      const response = await getStudentFuScoreById(this.selectedStudentId)
      this.studentScore = response.data
      this.renderPersonalChart()
    },

    // 加载班级平均成绩
    async loadClassAverageData() {
      if (!this.selectedClassId) {
        this.classAverage = null
        return
      }

      const response = await getClassAverageScores(this.selectedClassId)
      this.classAverage = response.data
      this.renderClassChart()
    },

    // 渲染个人成绩图表
    renderPersonalChart() {
      if (!this.studentScore || !this.$refs.personalChart) return

      if (!this.personalChart) {
        this.personalChart = echarts.init(this.$refs.personalChart)
      }

      const option = {
        title: {
          text: this.studentScore.studentName + '五育成绩'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ['德育', '智育', '体育', '美育', '劳育']
        },
        series: [
          {
            name: '成绩',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
              { value: this.studentScore.moralityScore || 0, name: '德育' },
              { value: this.studentScore.intelligenceScore || 0, name: '智育' },
              { value: this.studentScore.physicalScore || 0, name: '体育' },
              { value: this.studentScore.aestheticScore || 0, name: '美育' },
              { value: this.studentScore.labourScore || 0, name: '劳育' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }

      this.personalChart.setOption(option)
    },

    // 渲染班级平均成绩图表
    renderClassChart() {
      if (!this.classAverage || !this.$refs.classChart) return

      if (!this.classChart) {
        this.classChart = echarts.init(this.$refs.classChart)
      }

      const option = {
        title: {
          text: this.classAverage.className + '班级五育平均成绩'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['平均分']
        },
        xAxis: {
          data: ['德育', '智育', '体育', '美育', '劳育']
        },
        yAxis: {},
        series: [
          {
            name: '平均分',
            type: 'bar',
            data: [
              this.classAverage.deyuAverage || 0,
              this.classAverage.zhiyuAverage || 0,
              this.classAverage.tiyuAverage || 0,
              this.classAverage.meiyuAverage || 0,
              this.classAverage.laoyuAverage || 0
            ]
          }
        ]
      }

      this.classChart.setOption(option)
    }
  }
}
</script>

<style scoped>
.wuyu-scores-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.empty-state {
  text-align: center;
  color: #909399;
  padding: 20px 0;
}

.chart-row {
  margin-top: 20px;
}
</style>
