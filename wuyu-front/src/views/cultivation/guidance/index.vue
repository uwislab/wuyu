<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="query.fileName" placeholder="文件名称"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model="query.teacherName" placeholder="上传者姓名"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="loadData">查询</el-button>
      <el-button type="primary" icon="el-icon-search" @click="dataStatistics">数据统计</el-button>
    </el-form>

    <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
      <el-table-column prop="fileName" label="文件名称"></el-table-column>
      <el-table-column prop="teacherName" label="上传者"></el-table-column>
      <el-table-column prop="createDate" label="上传时间" width="180"></el-table-column>
      <el-table-column label="下载"  align="center">
        <template #default="scope">
          <a :href="URL_ACTION + scope.row.fileLocation"  :download="scope.row.fileName">点击下载</a>
        </template>
      </el-table-column>
      <el-table-column label="引用" align="center">
        <template #default="scope">
          <p v-if="scope.row.gid !== ''">
            <el-tag type="info">是</el-tag>
          </p>
          <p v-if="scope.row.gid === ''">
            <el-tag type="info">否</el-tag>
          </p>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNo"
                     :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
    </div>
    </div>
</template>

<script>
import guidanceProto from '@/api/cultivationModule/guidance';
export default {
  name: "tp",
  data() {
    return {
      URL_ACTION:'',
      tableData: [],
      query :{
        pageNo: 1,
        pageSize: 10,
        fileName:'',
        teacherName:'',
      },
      pageTotal: 0,
    };
  },
  methods:{
    dataStatistics() {
      this.$router.push("dataStatistics");
    },
    handlePageChange (val) {
      this.query.pageNo = val;
      this.loadData();
    },
    loadData(){
      guidanceProto.paging(this.query).then((res) => {
        this.tableData = res.list
        this.pageTotal = res.total
      })
    },
  },
  mounted() {
    this.URL_ACTION= process.env.VUE_APP_BASE_API;
    this.loadData();
  }
}
</script>

<style scoped>
  .table {
    width: 100%;
    font-size: 14px;
  }
</style>
