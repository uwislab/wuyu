<template>
  <div>
    <el-select v-model="selectedGrade" placeholder="请选择年级" @change="handleGradeChange">
      <el-option v-for="grade in grades" :key="grade.value" :value="grade.value" :label="grade.label"></el-option>
    </el-select>
    <el-select v-model="selectedClass" placeholder="请选择班级" @change="handleClassChange" v-if="showClassSelector">
      <el-option v-for="classNumber in classNumbers" :key="classNumber" :value="classNumber" :label="`${classNumber}班`"></el-option>
      <el-option key="all" value="all" label="全年级"></el-option>
    </el-select>
    <el-button @click="fetchData" type="primary">查询</el-button>
    <el-row :gutter="10">
      <el-col :span="18">
        <el-card>
          <div style="width: 100%; height: 400px" id="line"></div>
        </el-card>
      </el-col>
      <el-col :span="6" v-if="errorMessage">
        <el-alert
          title="数据加载失败"
          type="error"
          :description="errorMessage"
          show-icon
        ></el-alert>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { gradeclassScore, gradeScore } from "@/api/fuScale"; // 引入年级平均分接口

export default {
  name: "grade",
  data() {
    return {
      selectedGrade: 1,
      selectedClass: null, // 默认不选择班级
      grades: [
        { value: 1, label: '一年级' },
        { value: 2, label: '二年级' },
        { value: 3, label: '三年级' },
        { value: 4, label: '四年级' },
        { value: 5, label: '五年级' },
        { value: 6, label: '六年级' }
      ],
      classNumbers: [1, 2, 3], // 每个年级3个班
      showClassSelector: false, // 控制班级选择器是否显示
      scoreData: [],
      chart: null,
      errorMessage: ''
    }
  },
  mounted() {
    // 初始不自动加载数据，等待用户选择
  },
  methods: {
    fetchData() {
      this.errorMessage = '';
      this.destroyChart();
      
      // 参数校验
      if (!this.selectedGrade) {
        this.errorMessage = '请选择年级';
        return;
      }
      
      // 转换为字符串类型
      const grade = this.selectedGrade.toString();
      
      // 判断是查询班级还是全年级
      if (this.selectedClass === 'all') {
        // 查询全年级平均分
        console.log('请求全年级数据:', { grade });
        
        gradeScore(grade)
          .then(res => {
            console.log('全年级平均分接口返回数据:', res);
            this.processData(res, true); // 第二个参数表示是否为全年级数据
            this.drawLine();
          })
          .catch(error => {
            console.error('全年级平均分接口调用失败:', error);
            this.errorMessage = `获取全年级数据失败: ${error.message}`;
          });
      } else if (this.selectedClass !== null) {
        // 查询单个班级平均分
        const sclass = this.selectedClass.toString();
        console.log('请求班级数据:', { grade, sclass });
        
        gradeclassScore(grade, sclass)
          .then(res => {
            console.log('班级平均分接口返回数据:', res);
            this.processData(res, false); // 第二个参数表示是否为全年级数据
            this.drawLine();
          })
          .catch(error => {
            console.error('班级平均分接口调用失败:', error);
            this.errorMessage = `获取班级数据失败: ${error.message}`;
          });
      } else {
        this.errorMessage = '请选择班级或全年级';
      }
    },
    
    processData(data, isWholeGrade = false) {
      if (!data || typeof data !== 'object') {
        this.errorMessage = '数据格式不正确';
        return;
      }
      
      // 定义成绩字段映射
      const scoreFields = {
        '德': '德育',
        '智': '智育',
        '体': '体育',
        '美': '美育',
        '劳': '劳育'
      };
      
      // 处理每个维度的成绩，将无效值转换为null
      const processedData = Object.keys(scoreFields).map(dimension => {
        const field = scoreFields[dimension];
        const value = data[field];
        
        // 检查值是否为有效数字
        if (typeof value === 'number' && !isNaN(value)) {
          return value;
        } else {
          console.warn(`无效的${dimension}育成绩:`, value);
          return null; // 返回null表示缺失值，图表会显示为空白
        }
      });
      
      let newData = {
        name: isWholeGrade 
          ? `${this.getGradeLabel(this.selectedGrade)}全年级平均分` 
          : `${this.getGradeLabel(this.selectedGrade)}${this.selectedClass}班`,
        type: 'line',
        data: processedData
      };
      
      this.scoreData = [newData];
    },
    
    getGradeLabel(value) {
      const grade = this.grades.find(g => g.value === value);
      return grade ? grade.label : `未知年级(${value})`;
    },
    
    drawLine() {
      if (this.errorMessage) return;
      
      let lineDom = document.getElementById('line');
      let lineChart = echarts.init(lineDom);
      const option = {
        title: {
          text: this.selectedClass === 'all' 
            ? `${this.getGradeLabel(this.selectedGrade)}全年级平均分` 
            : `${this.getGradeLabel(this.selectedGrade)}${this.selectedClass}班平均分`
        },
        tooltip: {
          trigger: 'axis',
          formatter: (params) => {
            const param = params[0];
            return param.name + ': ' + (param.value !== null ? param.value + '分' : '成绩缺失');
          }
        },
        xAxis: {
          type: 'category',
          data: ['德', '智', '体', '美', '劳']
        },
        yAxis: {
          type: 'value',
          min: 80,
          max: 95,
          axisLabel: {
            formatter: '{value}分'
          }
        },
        series: [
          {
            ...this.scoreData[0],
            symbol: 'circle', // 显示数据点
            symbolSize: 10,
            lineStyle: {
              width: 3
            },
            itemStyle: {
              color: '#1890ff' // 数据点颜色
            },
            // 配置缺失值的显示方式
            connectNulls: false, // 不连接缺失值
            markPoint: {
              data: this.scoreData[0].data.map((value, index) => {
                if (value === null) {
                  return {
                    name: '缺失值',
                    xAxis: index,
                    yAxis: 80, // 显示在底部
                    itemStyle: {
                      color: 'red'
                    },
                    label: {
                      show: true,
                      position: 'bottom',
                      formatter: '缺失'
                    }
                  };
                }
                return null;
              }).filter(Boolean) // 过滤掉null
            }
          }
        ]
      };
      
      lineChart.setOption(option);
      this.chart = lineChart;
    },
    
    handleGradeChange() {
      // 年级变更时重置班级选择
      this.selectedClass = null;
      this.showClassSelector = true; // 显示班级选择器
    },
    
    handleClassChange() {
      // 班级变更时不自动查询，等待用户点击查询按钮
    },
    
    destroyChart() {
      if (this.chart) {
        this.chart.dispose();
      }
    }
  },
  beforeDestroy() {
    this.destroyChart();
  }
}
</script>

<style scoped></style>    