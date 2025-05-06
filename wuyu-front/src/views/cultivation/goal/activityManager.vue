<template>
  <div class="app-container">
    <el-button @click="back" type="info" style="float: right"><i class="el-icon-back"></i>返回</el-button>
    <el-descriptions title="评价内容">
      <el-descriptions-item label="标题">{{ goal.title }}</el-descriptions-item>
      <el-descriptions-item label="年级/学期">{{ goal.forGrade }} {{ goal.forTerm }}</el-descriptions-item>
      <el-descriptions-item label="实施日期">{{ goal.launchDate }}</el-descriptions-item>
      <el-descriptions-item label="发起人">{{ goal.teacherName }}</el-descriptions-item>
      <el-descriptions-item label="编辑人">{{ goal.editorNames }}</el-descriptions-item>
      <el-descriptions-item label="审核人">{{ goal.reviewerName }}</el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag v-if="goal.state===1" type="info">待发起</el-tag>
        <el-tag v-if="goal.state===2" type="warning">发起中</el-tag>
        <el-tag v-if="goal.state===3" type="info">待审核</el-tag>
        <span v-if="goal.state===4">
          <el-tag type="danger">审核驳回</el-tag>{{ scope.row.remark }}
        </span>
        <el-tag v-if="goal.state===5" type="success">发布中</el-tag>
      </el-descriptions-item>
      <!-- <el-descriptions-item label="附件"><el-tag size="small"><a :href="URL_ACTION + goal.fileLocation" :download="goal.fileName">点击下载</a></el-tag></el-descriptions-item> -->
      <el-descriptions-item label="评价维度">{{ aspect.type }}</el-descriptions-item>
      <el-descriptions-item label="类型内容">{{ aspect.summary }}</el-descriptions-item>
    </el-descriptions>

    <el-button type="success" icon="el-icon-plus" @click="addActivity">添加评价内容</el-button>

    <el-table :data="dataTable" border class="table" ref="multipleTable" header-cell-class-name="table-header">
      <el-table-column prop="title" align="center" label="评价内容"></el-table-column>
      <el-table-column prop="tags" align="center" label="评价者"></el-table-column>
      <el-table-column prop="content" align="center" label="评价方式&评价标准"></el-table-column>
      <el-table-column prop="weight" align="center" label="分数"></el-table-column>
      <el-table-column prop="createDate" label="创建时间" align="center" width="180"></el-table-column>
      <el-table-column prop="modifyDate" label="更新时间" align="center" width="180"></el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="300">
        <template #default="scope">
          <el-button type="primary" plain icon="el-icon-edit" @click="editActivity(scope.$index, scope.row)">编辑</el-button>
          <el-button  type="danger"  plain icon="el-icon-delete" class="red" @click="deleteActivity(scope.$index,scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination background layout="total, prev, pager, next"
                                           :current-page="queryForm.pageNo"
                                           :page-size="queryForm.pageSize"
                                           :total="pageTotal"
                                           @current-change="handlePaging">
      </el-pagination>
    </div>

    <el-dialog title="添加评价内容" :visible.sync="editVisible">
      <el-form label-width="120px" :model="dataForm" :rules="FORM_RULES" ref="dataForm">
        <el-form-item label="评价内容" prop="title"><el-input v-model="dataForm.title"></el-input></el-form-item>
        <el-form-item label="评价者" prop="tags"><el-input v-model="dataForm.tags"></el-input><p>请用逗号分隔</p></el-form-item>
        <el-form-item label="评价方式&评价标准" prop="content"><el-input v-model="dataForm.content" type="textarea"></el-input></el-form-item>
        <el-form-item label="分数" prop="weight"><el-input v-model="dataForm.weight"></el-input></el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible=false">取消</el-button>
          <el-button type="primary" @click="insertActivity">确定</el-button>
        </span>
      </template>

    </el-dialog>
    </div>
</template>

<script>
import goalApi from '@/api/cultivationModule/goal'
import aspectApi from '@/api/cultivationModule/goalAspect'
import activityApi from '@/api/cultivationModule/aspectActivity'

export default {
  name: "activityManager",
  data() {
    return {
      aid: '',
      aspect: {},
      goal: {},
      dataTable: [],
      dataForm: {
        id: '',
        gid: '',
        aid: '',
        title: '',
        tags: '',
        content: '',
        weight: '',
      },
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        aid: '',
      },
      pageTotal: 0,
      editVisible: false,
      editStatu: false,
      URL_ACTION: process.env.VUE_APP_BASE_API,
      FORM_RULES: {
        title: [{required: true, message: '不能为空', trigger: 'blur'}],
        weight: [{required: true, message: '不能为空', trigger: 'blur'}],
      },
    }
  },
  methods: {
    getGoal() {
      goalApi.info({id: this.aspect.gid}).then((res) => {
        if (res.code === 200) {
          this.goal = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getAspect() {
      aspectApi.info({id: this.aid}).then((res) => {
        if (res.code === 200) {
          this.aspect = res.data
          this.getGoal()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    addActivity() {
      this.dataForm = {}
      this.editStatu = false
      this.editVisible = true
    },
    editActivity(row) {
      this.editStatu = true
      this.editVisible = true
      this.dataForm.id = row.id
      this.dataForm.title = row.title
      this.dataForm.tags = row.tags
      this.dataForm.content = row.content
      this.dataForm.weight = row.weight
    },

    //插入评价内容
    insertActivity() {
      this.dataForm.gid = this.goal.id
      this.dataForm.aid = this.aid
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          this.$message.error("请按提示填写表单")
          this.editVisible = true
          return false
        } else if(this.dataForm.weight*1 < 0 || this.dataForm.weight * 1 > 100) {
          this.$message.error("评价标准分数必须在0-100内")
          this.editVisible = true
          return false
        } else {
          //根据editStatu的值判断当前的数据操作：false表示新增，true代表修改
          if(!this.editStatu) {
            activityApi.insert(this.dataForm).then((res) => {
              if (res.code === 200) {
                this.editVisible = false
                if (this.dataForm.id === undefined || this.dataForm.id === null) {
                  this.queryForm.pageNo = 1
                }
                this.paging()
              } else {
                this.$message.error(res.message)
              }
            })
          } else {

          }
        }
      })
    },

    //删除评价内容
    deleteActivity(index,id) {
      this.$confirm("确定要删除吗 ？", "提示", {type: "warning"}).then(() => {
        activityApi.delete(id).then((res) => {
          if (res.code === 200) {
            this.$message({type: 'success', message: '已删除'})
            this.dataTable.splice(index, 1)
          } else {
            this.$notify({title: '提示', message: res.message, duration: 0})
          }
        })
      }).catch(() => {this.$message({type: 'info', message: '已取消'})})
    },

    handlePaging(val) {
      this.queryForm.pageNo = val
      this.paging()
    },
    paging() {
      activityApi.paging(this.queryForm).then((res) => {
        this.dataTable = res.list
        this.pageTotal = res.total
      })
    },
    back() {
      this.$router.go(-1) //返回上一步路由
      this.$store.dispatch("tagsView/delView", this.$route) //调用全局挂载的方法，关闭当前页
    },
  },
  mounted() {
    this.aid = this.$route.query.aid
    this.getAspect()
    this.queryForm.aid = this.aid
    this.paging()
  }
}
</script>

<style scoped>
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
</style>
