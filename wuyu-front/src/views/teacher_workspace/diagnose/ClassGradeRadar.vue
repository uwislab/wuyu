<template>
  <div style="padding:20px;">
    <!-- 班级与年级五育柱状图 -->
    <el-row :gutter="20">
      <el-col style="margin-bottom: 20px;" :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>班级与年级五育柱状图</span>
            <div style="float: right;">
              <el-select v-model="selectedGrade" placeholder="请选择年级" style="width: 120px; margin-right: 10px;" @change="handleGradeChange">
                <el-option v-for="item in gradeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
              <el-select v-model="selectedClass" placeholder="请选择班级" style="width: 120px;" @change="handleClassChange">
                <el-option v-for="item in classOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </div>
          </div>
          <div id="classGradeChart" style="width: 100%;height:400px;"></div>
          <!-- 新增：分析结果展示区域 -->
          <el-card id="analysisResultCard" style="margin-top: 20px;">
            <template #header>
              <span>成绩比较分析</span>
            </template>
            <div id="analysisResult"></div>
          </el-card>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import echarts from 'echarts';
import { getClassAndGradeAvgScore } from '@/api/classGradeScore';

export default {
  data() {
    return {
      selectedGrade: null,
      selectedClass: null,
      gradeOptions: [
        { value: 1, label: '一年级' },
        { value: 2, label: '二年级' },
        { value: 3, label: '三年级' },
        { value: 4, label: '四年级' },
        { value: 5, label: '五年级' },
        { value: 6, label: '六年级' }
      ],
      classOptions: []
    };
  },
  methods: {
    handleGradeChange() {
      // 这里可以根据年级获取班级列表，暂不实现
      this.classOptions = [
        { value: 1, label: '一班' },
        { value: 2, label: '二班' },
        { value: 3, label: '三班' }
      ];
    },
    handleClassChange() {
      if (this.selectedGrade && this.selectedClass) {
        getClassAndGradeAvgScore(this.selectedGrade, this.selectedClass).then(response => {
          const classAvg = response.data.classAvg;
          const gradeAvg = response.data.gradeAvg;

          const option = {
            title: {
              text: '班级与年级五育成绩对比',
              top: '5%',
              left: 'center'
            },
            legend: {
              top: '15%',
              data: ['班级平均成绩', '年级平均成绩']
            },
            xAxis: {
              type: 'category',
              data: ['德育', '智育', '体育', '美育', '劳育']
            },
            yAxis: {
              type: 'value',
              min: 0,
              max: 100,
              axisLabel: {
                formatter: '{value} 分'
              }
            },
            series: [
              {
                name: '班级平均成绩',
                type: 'bar',
                data: classAvg,
                label: {
                  show: true,
                  position: 'top'
                },
                itemStyle: {
                  color: 'rgba(30, 144, 255, 0.8)' // 蓝色
                }
              },
              {
                name: '年级平均成绩',
                type: 'bar',
                data: gradeAvg,
                label: {
                  show: true,
                  position: 'top'
                },
                itemStyle: {
                  color: 'rgba(50, 205, 50, 0.8)' // 绿色
                }
              }
            ]
          };

          const myChart = echarts.init(document.getElementById('classGradeChart'));
          myChart.setOption(option);

          // 新增：成绩比较分析
          const analysis = this.analyzeScores(classAvg, gradeAvg);
          const analysisResultDiv = document.getElementById('analysisResult');
          analysisResultDiv.innerHTML = analysis;
        }).catch(error => {
          console.error('获取数据失败', error);
        });
      }
    },
    analyzeScores(classAvg, gradeAvg) {
      const categories = ['德育', '智育', '体育', '美育', '劳育'];
      let analysis = '';
      for (let i = 0; i < classAvg.length; i++) {
        const diff = classAvg[i] - gradeAvg[i];
        let icon = '';
        if (diff > 0) {
          icon = '<i class="el-icon-arrow-up" style="color: green;"></i>';
          analysis += `<p>${icon} ${categories[i]}方面，班级平均成绩高于年级平均成绩 <span style="color: green;">${diff.toFixed(2)}</span> 分。</p>`;
        } else if (diff < 0) {
          icon = '<i class="el-icon-arrow-down" style="color: red;"></i>';
          analysis += `<p>${icon} ${categories[i]}方面，班级平均成绩低于年级平均成绩 <span style="color: red;">${Math.abs(diff).toFixed(2)}</span> 分。</p>`;
        } else {
          icon = '<i class="el-icon-minus" style="color: gray;"></i>';
          analysis += `<p>${icon} ${categories[i]}方面，班级平均成绩与年级平均成绩持平。</p>`;
        }
      }
      return analysis;
    }
  }
};
</script>

<style scoped>
#analysisResultCard {
  border: 1px solid #e6e6e6;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

#analysisResult p {
  font-size: 16px;
  line-height: 1.8;
  margin-bottom: 5px;
}
</style>
