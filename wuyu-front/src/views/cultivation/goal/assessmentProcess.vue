<template>
  <div id="boxholder">
    <div class="header">
      量表选择
    </div>
    <div class="scaleBox">
      <div style="min-height: 420px;">

        <p style="color: rgb(104, 104, 104);font-size: 16px;margin-left: 10px;padding:10px 20px;"> 共
          <span style="color: rgb(55, 158, 248);">{{ total }}</span>
          条记录&nbsp;&nbsp;&nbsp;(此页面只展示量表状态为<span style="color: blue;">编辑中</span>与<span style="color: blue;">审核驳回</span>)
        </p>

        <el-table v-loading="loading" :data="tableData.slice((page - 1) * pageSize, page * pageSize)" :header-cell-style="{ background: '#324157', color: '#ffffff' }" style="width: 100%"
          border>
          <el-table-column prop="scaleId" label="量表ID" width="80" align="center"> </el-table-column>
          <el-table-column prop="title" label="量表标题" width="240" align="center"> </el-table-column>
          <el-table-column prop="creatorName" label="创建人" min-width="80" show-overflow-tooltip align="center">
          </el-table-column>
          <el-table-column prop="createDate" label="创建时间" min-width="120" show-overflow-tooltip align="center">
          </el-table-column>
          <!-- <el-table-column prop="finishdate" label="结束时间" min-width="120" show-overflow-tooltip align="center">
          </el-table-column> -->
          <el-table-column prop="state" label="量表状态" min-width="80" show-overflow-tooltip align="center">
            <template slot-scope="scope">
              <el-tag type="" size="medium" v-if="scope.row.state == 0">编辑中</el-tag>
            <el-tag type="" size="medium" v-if="scope.row.state == 1">待审核</el-tag>
            <el-tag type="success" size="medium" v-if="scope.row.state == 2">审核通过</el-tag>
            <el-tag type="danger" size="medium" v-if="scope.row.state == 3">审核驳回</el-tag>
            <el-tag type="warning" size="medium" v-if="scope.row.state == 4">审核逾期</el-tag>
            <el-tag type="info" size="medium" v-if="scope.row.state == 5">已弃用</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="grade" label="版本" min-width="80" show-overflow-tooltip align="center">
          </el-table-column>
          <el-table-column label="操作" min-width="120" align="center">
            <template slot-scope="{row}">
              <el-button size="mini" type="primary" @click="goExamineDetails(row)">查看考核</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page"
        :page-sizes="[1, 5, 10, 15]" :page-size="pageSize" layout="sizes,prev, pager, next, jumper" background :total="this.total"
        style="text-align: center;" prev-click="handlePrrClick">
      </el-pagination>
    </div>
  </div>
</template>


<script>
import router from "@/router";
import { getFuScaleByStates } from "@/api/fuScale";
export default {
  name: 'assessmentProcess',
  data() {
    return {
      loading: false,
      tableData: [],
      page: 1,
      pageSize: 5,
      total: 0,// 条目总数
    }
  },
  methods: {
    //每页条数改变时触发 选择一页显示多少行
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.currentPage = 1;
      this.pageSize = val;
    },

    async getProjectList(page, pageSize) {
      let res = await this.$api.getScaleList({ page, pageSize })
      // console.log(res)
      this.tableData = res.data.records
      this.total = res.data.total
    },

    handleCurrentChange(val) {
      // this.getProjectList(val,this.pageSize)
      this.page = val;
      // console.log('change too!')
    },

    goExamineDetails(row) {
      // console.log('跳转传参', row)
      router.push({
        path: '/goal/examineDetails',
        query: { id: row.scaleId,
                creatorName: row.creatorName
                }
      }); //, query: { row }
    }
  },
  mounted() {
    let states = "0,3";
    this.loading = true;
    // 读取编辑中和审核驳回
    getFuScaleByStates(states).then(res => {
      // console.log('res.data '+res.data);
      this.tableData = res.data;
      this.total = res.data.length;
      this.loading = false;
    })
  }
}
</script>

<style>
#boxholder {
  background-color: #ebebeb;
  height: 1200px;
  padding: 10px 10px 0 10px;
}

.header {
  background-color: #ffffff;
  padding: 20px;
  font-size: 18px;
  font-weight: 700;
  /* box-shadow: rgba(145, 145, 145, 0.5); */
}

.scaleBox {
  margin-top: 15px;
  background-color: #fff;
  padding: 20px;
  color: rgb(103, 148, 232);
  font-size: 14px;

}
</style>