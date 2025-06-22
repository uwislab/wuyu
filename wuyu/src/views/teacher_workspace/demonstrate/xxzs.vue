<template>
    <el-container style="height: 100%; ">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="table">
          <el-table :data="tableData" border style="width: 100%" :max-height="650"
                    @filter-change="handleFilterChange">
            <el-table-column prop="xuenian" label="学年" :filters="xuenianList" :filter-method="filterXuenian"
                             column-key="xuenian">
            </el-table-column>
            <el-table-column prop="className" label="班级">
            </el-table-column>
            <el-table-column prop="name" label="姓名">
            </el-table-column>
            <el-table-column prop="avgScore" label="总成绩" sortable>
            </el-table-column>
            <el-table-column prop="deyu" label="德育" sortable>
            </el-table-column>
            <el-table-column prop="zhiyu" label="智育" sortable>
            </el-table-column>
            <el-table-column prop="tiyu" label="体育" sortable>
            </el-table-column>
            <el-table-column prop="meiyu" label="美育" sortable>
            </el-table-column>
            <el-table-column prop="laoyu" label="劳育" sortable>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="8" class="xxRight">
        <div class="miaoshu" v-for="miaoshu in miaoshuList">
          {{ param }}在{{ miaoshu.xuenian }}中总成绩：{{ miaoshu.avgList[0] }}分，德育成绩：{{ miaoshu.avgList[1] }}分，
          智育成绩：{{ miaoshu.avgList[2] }}分，体育成绩：{{ miaoshu.avgList[3] }}分，美育成绩：{{ miaoshu.avgList[4] }}分，
          劳育成绩：{{ miaoshu.avgList[5] }}分。<br /><br />
          按照85分以上是优秀，75分以上是良好，60分以上合格，60分以下不合格的评分标准。<br />
          {{ param }}总人数{{ miaoshu.renshu }}人。<br />
          <el-table :data="miaoshu.markData">
            <el-table-column prop="subject" label="科目">
            </el-table-column>
            <el-table-column label="达标人数">
              <el-table-column prop="youxiu" label="优秀">
              </el-table-column>
              <el-table-column prop="lianghao" label="良好">
              </el-table-column>
              <el-table-column prop="hege" label="合格">
              </el-table-column>
              <el-table-column prop="buhege" label="不合格">
              </el-table-column>
            </el-table-column>
          </el-table>
          <br/>
        </div>
      </el-col>
    </el-row>
  </div></el-container>
    </el-container>

</template>
<script>
import api from '@/api/demonstrate/api'
import Aside from "@/views/teacher_workspace/Aside"
export default {
  name: "index",
  data() {
    return {
      param: "",
      tableData: [],
      xuenianList: [],
      subList: ["avgScore", "deyu", "zhiyu", "tiyu", "meiyu", "laoyu"],
      subNameList: ["总分", "德育", "智育", "体育", "美育", "劳育"],
      xuenianList: [],//表格中可供筛选的数组
      xnList:[],//对应描述列表的学年数组
      miaoshuList: [],
    };
  },
  components: {
      Aside
    },
  mounted() {
    this.getTableData()
  },
  methods: {
    getTableData() {
      let that = this
      let queryParam = this.$route.query.param
      let range = this.$route.query.range
      that.param = queryParam
      api.getTable(
        {
          queryParam: queryParam,
          range: range
        }
      ).then(function (res) {
        console.log(res);
        that.tableData = res.table
        res.xuenianList.forEach(element => {
          that.xuenianList.push({
            text: element,
            value: element
          })
          that.xnList.push(element)
        });

        that.handleMS()
      })
        .catch(function (error) {
          console.log(error);
        })
    },
    filterXuenian(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    handleFilterChange(val) {//参数val是你选择的筛选条件
      let that = this
      this.xnList = val.xuenian
      if(val.xuenian.length===0){
        that.xuenianList.forEach(xuenian=>{
          that.xnList.push(xuenian.value)
        })
      }
      this.handleMS()
    },
    handleMS() {
      let that = this
      this.miaoshuList = []
      this.xnList.forEach(xuenian => {
        let avgList = []
        let markData = []
        let recentData = that.tableData.filter(item => {
          return item.xuenian === xuenian
        })
        this.subList.forEach((subject,index) => {
          avgList.push(
            (recentData.reduce((c, shuju) => c + parseInt(shuju[subject]), 0) / recentData.length).toFixed(2))
          let youxiu = 0
          let lianghao = 0
          let hege = 0
          let buhege = 0
          recentData.forEach(data => {
            if (data[subject] >= 85) {
              youxiu++
            } else if (data[subject] >= 75) {
              lianghao++
            } else if (data[subject] >= 60) {
              hege++
            } else {
              buhege++
            }
          })
          markData.push(
            {
              "subject": that.subNameList[index],
              "youxiu": youxiu,
              "lianghao": lianghao,
              "hege": hege,
              "buhege": buhege
            }
          )
        })
        this.miaoshuList.push({
          "renshu":recentData.length,
          "xuenian":xuenian,
          "avgList":avgList,
          "markData":markData,
        })
      })
    },
  }
}
</script>
<style scoped>
.xxRight{
  padding-right: 20px;
  max-height: 650px;
  overflow: scroll;
}
.app-container{
  padding: 10px;
}
</style>
