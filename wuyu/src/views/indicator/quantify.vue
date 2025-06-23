<template>
  <div>
    <el-table :data="tableData" border style="width: 100%" stripe>
      <el-table-column prop="id" label="量表id" width="180"></el-table-column>
      <el-table-column
        prop="title"
        label="量表标题"
        min-width="200"
      ></el-table-column>
      <el-table-column prop="editorNames" label="创建人">
        <template slot-scope="scope">
          <el-tooltip
            class="item"
            effect="dark"
            content="scope.row.editorNames"
            placement="top"
          >
            <span>{{ scope.row.editorNames }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="createDate" label="创建时间" :sortable="true">
        <template slot-scope="scope">
          {{ formatDate(scope.row.createDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="modifyDate" label="结束时间" :sortable="true">
        <template slot-scope="scope">
          {{ formatDate(scope.row.modifyDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="state" label="量表状态"></el-table-column>
      <el-table-column prop="forGrade" label="绑定年级"></el-table-column>
      <el-table-column prop="indicator" label="查看指标">
        <template slot-scope="scope">
          <a
            :href="'#/remark/Indicator?id=' + scope.row.id"
            class="indicator-link"
          >
            <el-icon><CheckCircle /></el-icon> 查看指标
          </a>
        </template>
      </el-table-column>
    </el-table>

    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage1"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        class="custom-pagination"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getlist } from "@/api/cultivationModule/goal";

var i = 10;
var l = [];
export default {
  data() {
    return {
      tableData: [],
      currentPage1: 1,
      total: "",
    };
  },
  created() {
    getlist().then((res) => {
      l = res.data.map((item) => {
        return {
          ...item,
          editorNames: item.editorNames.split(",")[0],
          // 确保日期字段是标准化的
          createDate: new Date(item.createDate), // 转换为 Date 对象
          modifyDate: new Date(item.modifyDate), // 转换为 Date 对象
        };
      });
      this.total = l.length;
      this.updateTableData();
    });
  },
  methods: {
    handleSizeChange(val) {
      i = Number(val);
      this.updateTableData();
    },
    handleCurrentChange(val) {
      this.currentPage1 = val;
      this.updateTableData();
    },
    updateTableData() {
      const start = (this.currentPage1 - 1) * i;
      const end = this.currentPage1 * i;
      this.tableData = l.slice(start, end);
    },
    formatDate(date) {
      const options = { year: "numeric", month: "2-digit", day: "2-digit" };
      return date.toLocaleDateString("zh-CN", options); // 格式化日期为中文格式
    },
    sortByDate(field) {
      l.sort((a, b) => {
        return a[field] - b[field]; // 数字排序
      });
      this.updateTableData();
    },
  },
};
</script>

<style scoped>
.custom-pagination .el-pager li {
  font-weight: bold;
}

.custom-pagination .el-select {
  width: 100px;
}

.custom-pagination .el-pagination__sizes {
  margin-right: 20px;
}

.indicator-link {
  text-decoration: none;
  color: #409eff;
  display: inline-flex;
  align-items: center;
}

.indicator-link:hover {
  color: #66b1ff;
}
</style>
