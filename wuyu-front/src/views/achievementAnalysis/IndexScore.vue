<script>

import API from '@/api/sixGroup/SixGroupApi'


export default {
  data() {
    return {
      yearOps: [],
      clsOps: [],
      clsOps1: [],
      drawer: false,
      indexOptions: [],
      indexOptions1: [],
      students: [],
      indexVal: '',
      tableData: [],
      stuOps: [],
      tiOptions: [],
      years: [],
      pageInfo: {
        当前页: 1,
        每页数量: 15,
        total: 0,
        pages: 0
      },
      queryInfo: {
        年级: '',
        班级: '',
        icId: '',
        no: '',
        name: ''
      },
      addInfo: {
        年级: '',
        班级: '',
        icId: '',
        stuId: '',
      }
    }
  }, methods: {
    async 分页获取表格数据() {
      const ret = await API.获取指标成绩列表(
        {
          ...this.pageInfo,
          year: this.queryInfo.年级,
          cls: this.queryInfo.班级,
          icId: this.queryInfo.icId,
          studentNum: this.queryInfo.no,
          studentName: this.queryInfo.name
        }
      )
      console.log(ret, "ret,tag")
      this.tableData = ret.list;
      this.pageInfo.total = ret.total
      this.pageInfo.pages = ret.pages
      console.log(this.pageInfo, "tag")
    },
    changeYear() {
      this.获取班级列表()
      this.分页获取表格数据()
    },
    changeIcId() {
      this.分页获取表格数据()
    },
    changeCls() {
      this.分页获取表格数据()
    },
    addChangeYear() {
      this.获取班级列表1()
      this.学生获取();
    },
    addChangeIcId() {
    },
    addChangeCls() {
      this.学生获取();
    },
    学生获取() {
      let ret = []
      this.students.forEach(i => {
        console.log(i)
        if (i.gradeId == this.addInfo.年级 && i.classId == this.addInfo.班级) {
          ret.push({
            label: i.studentName,
            value: i.id
          })
        }
      })
      this.stuOps = ret
    },
    changeIndex() {
      this.queryInfo.一级指标 = this.indexVal
      this.计算生成图表数据()
    },
    获取所有学生() {
      API.所有学生().then(res => {
        this.students = res
      })
    },
    根据学号查询学生信息(学号) {
      let stu;
      this.students.forEach(i => {
        if (i.studentNum === 学号) {
          stu = i;
        }
      })
      return stu
    },
    getStuAttr(row, col, type) {
      let stu = this.根据学号查询学生信息(row.studentNum);
      return stu[type]
    },
    changePage(cur) {
      this.pageInfo.当前页 = cur
      this.分页获取表格数据()
    },
    async 获取年级班级() {
      const res = await API.获取年级班级()
      console.log('year', res)
      this.years = res;
      this.queryInfo.年级 = res[0].grade
      const j = []
      const j1 = []
      res.forEach(i => {
        if (!j1.includes(i.grade)) {
          j.push({
            label: i.grade + '年级',
            value: i.grade
          })
          j1.push(i.grade)
        }
      })
      this.yearOps = j
      this.获取班级列表()
    },
    search() {
      this.分页获取表格数据()
    },
    async 获取三级评价指标() {
      const ret = await API.获取三级评价指标()
      ret.forEach(i => {
        this.indexOptions.push({
          value: i.id,
          label: i.itemContent
        })
      })
      this.queryInfo.icId = ret[0].id
    },
    async 获取三级评价指标1() {
      const ret = await API.获取三级评价指标()
      ret.forEach(i => {
        this.indexOptions1.push({
          value: i.id,
          label: i.itemContent
        })
      })
      this.addInfo.icId = ret[0].id
    },
    获取班级列表() {
      const ret = []
      this.years.forEach(i => {
        if (i.grade === this.queryInfo.年级) {
          ret.push(i.cls)
        }
      })
      const j = []
      const j1 = []
      ret.forEach(i => {
        j.push({
          label: i + '班',
          value: i
        })
      })
      this.clsOps = j
      this.queryInfo.班级 = ret[0]

      return ret
    },
    获取班级列表1() {
      const ret = []
      this.years.forEach(i => {
        if (i.grade === this.addInfo.年级) {
          ret.push(i.cls)
        }
      })
      const j = []
      ret.forEach(i => {
        j.push({
          label: i + '班',
          value: i
        })
      })
      this.clsOps1 = j
      this.addInfo.班级 = ret[0]
      return ret
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageInfo.每页数量 = val;
      this.分页获取表格数据()
    },
    changeTi() {
      this.分页获取表格数据()
    },
    handleClose() {
      this.drawer = false
    },
    addChangeStu() {

    },
    addIndexScore() {

      // 检查分数是否在区间内
      const { 年级, 班级, icId, stuId, score } = this.addInfo;
      if (score === null || score < 0 || score > 100) {
        this.$message.error('分数必须在 0 到 100 的范围内');
        return;
      }

      // 检查 addInfo 中是否有空值
      if (!年级 || !班级 || !icId || !stuId) {
        this.$message.error('所有字段均为必填项，请检查输入');
        return;
      }


      API.添加指标成绩(this.addInfo)
      this.$msgbox.alert('成功')
    }
  },
  async created() {
    await this.获取三级评价指标();
    await this.获取三级评价指标1();
    this.获取所有学生();
    await this.获取年级班级();
    this.分页获取表格数据();
  },
  
}

</script>

<template>
  <div>
    <section class="s2">
      <div class="ti-box">
        <el-button type="primary" style="margin-right: 10px;" @click="drawer = true">成绩录入</el-button>
        <el-select v-model="queryInfo.年级" placeholder="请选择年级" @change="changeYear">
          <el-option v-for="item in yearOps" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="queryInfo.班级" placeholder="请选择班级" @change="changeCls">
          <el-option v-for="item in clsOps" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="queryInfo.icId" placeholder="请选择指标" @change="changeIcId">
          <el-option v-for="item in indexOptions" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-input placeholder="根据学号查询" v-model="queryInfo.no" clearable>
        </el-input>
        <el-input placeholder="根据姓名查询" v-model="queryInfo.name" clearable>
        </el-input>
        <el-button type="primary" @click="search">查询</el-button>
      </div>
      <el-table :data="tableData">
        <el-table-column prop="studentNum" label="学号" sortable>
        </el-table-column>
        <el-table-column prop="studentName" label="姓名">
        </el-table-column>
        <el-table-column prop="gradeId" label="年级">
        </el-table-column>
        <el-table-column prop="classId" label="班级">
        </el-table-column>
        <el-table-column prop="itemContent" sortable label="指标">
        </el-table-column>
        <el-table-column prop="score" sortable label="得分">
        </el-table-column>
        <el-table-column sortable :formatter="(row, column) => { return '2023-12-14'; }" label="日期">
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background @current-change="changePage" :page-size="pageInfo.每页数量" :current-page="pageInfo.当前页"
          :page-count="pageInfo.pages" :total="pageInfo.total" @size-change="handleSizeChange"
          :page-sizes="[15, 25, 35, 45]" layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </div>
    </section>
    <el-drawer title="成绩录入" :visible.sync="drawer" direction="rtl" :before-close="handleClose">
      <div style="display: flex;flex-direction: column;padding:10px;">
        <el-select v-model="addInfo.年级" placeholder="请选择年级" style="margin-bottom: 10px;" @change="addChangeYear">
          <el-option v-for="item in yearOps" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="addInfo.班级" placeholder="请选择班级" style="margin-bottom: 10px;" @change="addChangeCls">
          <el-option v-for="item in clsOps1" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select style="margin-bottom: 10px;" v-model="addInfo.stuId" placeholder="请选择学生" @change="addChangeStu">
          <el-option v-for="item in stuOps" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select style="margin-bottom: 10px;" v-model="addInfo.icId" placeholder="请选择指标" @change="addChangeIcId">
          <el-option v-for="item in indexOptions1" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-input :min="0" :max="100" style="margin-bottom: 10px;" v-model="addInfo.score" type="number"
          placeholder="请输入分数" />
        <el-button type="primary" @click="addIndexScore">提交</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<style scoped>
.s2 {
  padding: 20px 40px;
}

.ti-box {
  margin: 10px 0;
  display: flex;
  align-items: center;
}

.el-select {
  margin-right: 10px;
  width: 200px;
}

.el-input {
  width: 200px;
  margin-right: 10px;
}


.pagination {
  width: 100%;
  text-align: center;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
}

.ml-10 {
  margin-left: 10px;
}
</style>
