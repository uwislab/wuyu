<template>
    <div class="app-container">

      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="query.title" placeholder="目标标题"/>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getPage">查询</el-button>
      </el-form>

      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="title" label="标题" align="center" fixed="left" width="300"></el-table-column>

        <!-- <el-table-column label="附件下载" align="center" width="120">
          <template #default="scope">
            <a :href="URL_ACTION+scope.row.fileLocation" :download="scope.row.fileName">点击下载</a>
          </template>
        </el-table-column> -->

        <el-table-column label="状态" align="center" width="100">
          <template #default="scope">
             <el-tag v-if="scope.row.state===1" type="info">待发起</el-tag>
             <el-tag v-if="scope.row.state===2" type="warning">发起中</el-tag>
             <el-tag v-if="scope.row.state===3" type="info">待审核</el-tag>
             <el-tag v-if="scope.row.state===4" type="warning">审核中</el-tag>
             <el-tag v-if="scope.row.state===5" type="success">审核通过</el-tag>
             <p v-if="scope.row.state===6">
               <el-tag type="danger">审核驳回</el-tag>{{scope.row.remark}}
             </p>
             <el-tag v-if="scope.row.state===7" type="info">未发布</el-tag>
             <el-tag v-if="scope.row.state===8" type="success">发布中</el-tag>
            <el-tag v-if="score.row.state===9" type="info">逾期</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="teacherName" align="center" label="发起人"></el-table-column>
        <el-table-column prop="editorNames" align="center" label="编辑人" width="300"></el-table-column>
        <el-table-column prop="reviewerName" align="center" label="审核人"></el-table-column>
        <el-table-column prop="launchDate" align="center" label="实施日期"></el-table-column>
        <!-- <el-table-column prop="createDate" align="center" label="创建时间" width="180"></el-table-column>
        <el-table-column prop="modifyDate" align="center" label="更新时间" width="180"></el-table-column> -->
        <el-table-column label="操作" align="center" fixed="right" width="400">
          <template #default="scope">
            <el-button type="primary" plain  @click="handleDetail(scope.row.id, 'see')">查看详情</el-button>
            <el-button type="primary" plain  @click="handleView(scope.row.id, 'see')">生成脑图</el-button>
            <el-button type="primary" plain  @click="handleChangeState(scope.row.id, 5)">通过</el-button>
            <el-button type="primary" plain  @click="handleChangeState(scope.row.id, 6)">驳回</el-button>
            <!-- <el-button :loading="downloadLoading"
                       style="margin:0 0 20px 20px;"
                       type="primary"
                       icon="el-icon-document"
                       @click="exportExcel(scope.index, scope.row)">ss
              表格导出
            </el-button> -->
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination background
                       layout="total, prev, pager, next"
                       :current-page="query.pageNo"
                       :page-size="query.pageSize"
                       :total="pageTotal"
                       @current-change="handlePageChange"></el-pagination>
      </div>

    </div>
  </template>

  <script>
  import goalProto from '@/api/cultivationModule/goal';
  import {parseTime} from "@/utils";
  import goalApi from "@/api/cultivationModule/goal";
  export default {
    name: "tp",
    data() {
      return {
        downloadLoading: false,
        list: [],
        tp_type: '',
        URL_ACTION: '',
        tableData: [],
        guidances: [],
        teachers: [],
        editors: [],
        ataMaps: [],
        query: {
          pageNo: 1,
          pageSize: 10,
          title: '',
          editorTids: '',
          reviewerTid: '',
        },
        pageTotal: 0,
        editVisible: false,
        dataForm: {
          id: '',
          editorTids: '',
          reviewerTid: '',
          title: '',
          gid: '',
          forGrade: '',
          forTerm: '',
          launchDate: '',
        },
        query: {
        pageNo: 1,
        pageSize: 10,
        title: '',
        editorTids: '',
        reviewerTid: '',
        state:''
       }
      };
    },
    created() {
      var teacher=JSON.parse(sessionStorage.getItem('userInfo'));
      if(teacher == null){
         this.$message({
            type: 'error',
            message: '当前账户未登录!'
         });
         sessionStorage.clear;//清除sessionStorage
         this.$store.dispatch('user/resetToken') //非接口版本退出登录
         this.$router.push(`/login?redirect=${this.$route.fullPath}`) //跳转到登录页面
      }else{
        this.URL_ACTION = process.env.VUE_APP_BASE_API;
        this.getPage();
      }
    },

    methods: {
      //翻页
      handlePageChange(val) {
        this.query.pageNo = val;
        this.getPage();
      },
      //查看详情
      handleDetail(id, type_) {
        this.$router.push({name: 'goalDetails', params:{id:id,type_:type_}} ) // 只能用 name
      },
      //生成脑图
      handleView(id, type_) {
        this.$router.push({name: 'goalMindMap', params:{id:id,type_:type_}} )
      },
      //改变状态
      handleChangeState(id, state) {
        goalProto.changeState({id: id, state: state}).then((res) => {
          if (res.code === 200) {
            if (state === 1) {
              this.$message.info('请到创建培养中编辑');
            }
            this.getPage();
          } else {
            this.$message.error(res.msg);
          }
        })
      },

      getPage() {
        this.query.state = 3;//3状态 发起2状态提交到3状态
        this.query.reviewerTid = 100024;
        goalProto.paging(this.query).then((res) => {
          console.log(this.query)
          this.tableData = res.data.list
          this.pageTotal = res.data.total
        })
      },

      getGoal(row) {
        goalApi.details({id: row.id}).then((res) => {
          if (res.code === 200) {
            this.ataMaps = res.data.ataMaps
            let list = []
            this.ataMaps.forEach(function (ataMap) {
              ataMap.activities.forEach(function (activity) {
                list.push({
                  aspect: ataMap.aspect.type,
                  activity_title: activity.title,
                  activity_tags: activity.tags,
                  activity_content: activity.content,
                  activity_weight: activity.weight,
                })
              })
            })
            this.list = list
          } else {
            this.$message.error(res.msg)
          }
        })
      },

      exportExcel(row) {
        this.downloadLoading = true
        this.getGoal(row)
        const headers = [
          '方面',
          '活动名称',
          '活动标签',
          '活动内容',
          '活动权重',
        ]
        const filter = [
          'aspect',
          'activity_title',
          'activity_tags',
          'activity_content',
          'activity_weight',
        ]
        const merges = ["A2:A3", "A4:A7"];
        import('@/vendor/Export2Excel').then(excel => {
          // console.log(this.list)
          const data = this.formatJson(filter, this.list)
          excel.export_json_to_excel({
            header: headers,
            data,
            merges: merges,
            filename: row.title,
            autoWidth: true,
            bookType: 'xlsx',
          })
          this.downloadLoading = false
        })
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        }))
      }
    },
  }
  </script>

  <style scoped>
  </style>
