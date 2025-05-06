<template>
  <div class="app-container">

    <el-form :inline="true"class="demo-form-inline">
    <el-form-item>
      <el-input v-model="searchObj.title"placeholder="大纲题目" />
    </el-form-item>
    <el-form-item>
      <el-input v-model="searchObj.code"placeholder="大纲编号" />
    </el-form-item>
    <el-button type="primary"icon="el-icon-search"@click="getList()">查询</el-button>
    </el-form>

    <!--工具条-->
    <div>
      <el-button type="danger"size="mini"@click="removeRows()">批量删除</el-button>
    </div>

    <el-table :data="list"
              stripestyle="width:100%"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"width="55" />
      <el-table-column type="index"
                       width="50"
                       label="序号" />
      <el-table-column prop="title"
                       label="大纲标题" />
      <el-table-column prop="code"
                       label="大纲编号" />
      <el-table-column prop="username"
                       label="作者" />
      <el-table-column prop="createTime"
                       label="制定时间" />
      <el-table-column prop="outline"
                       label="概要"
                       width="200" />
      <el-table-column prop="importance"
                       label="重要等级" />
      <el-table-column label="操作" width="280" align="center">
      <template slot-scope="scope">

      <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.code)">删除</el-button>
<!--        <router-link :to="' /hospSet/edit/'+scope.row.id">-->
<!--      <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>-->
<!--      </router-link>‘-->
        <router-link :to="'/teaching_program/detail/'+scope.row.code">
          <el-button type="primary" size="mini" icon="el-icon-edit">查看详情</el-button>
        </router-link>
      </template>
    </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page"
                   :page-size="limit"
                   :total="total"
                   style="padding: 30px 0; text-align: center;"
                   layout="total,prev,pager,next,jumper"
                   @current-change="getList" />
  </div>
</template>

<script>
  import  teachingProgram  from '@/api/teaching_program'

  export default {
    name: "teachingProgramList",

    data() {
      return {
        current: 1,//当前页
        limit: 3,//每页显示记录数
        searchObj: {}, //条件封装对象
        list: [],//每页数据集合
        total: 0,//总记录数
        multipleSelection: [], // 批量选择中选择的记录列表
        page:1
      }
    },
    created() {
      this.getList();
    },

    methods: {
      getList (page = 1) {
        //添加当前页参数
        this.current = page;
        teachingProgram.getTeachingProgram(this.current, this.limit, this.searchObj).then(
          response => {
            // 返回集合赋值list
            this.list = response.data.records
            // 总记录数
            this.total = response.data.total
            console.log(response)
          }
        ).catch(
          error => {
            console.log(error)
          }
        )
      },
      // 删除
      removeDataById (id) {
        this.$confirm('此操作将永久删除教案信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //调用接口
          alert(id)
          teachingProgram.deleteTeachingProgram(id)
            .then(response => {
              //提示
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              //刷新页面
              this.getList(1)
            })
        })
      },
      // 当表格复选框选项发生变化的时候触发
      handleSelectionChange (selection) {
        this.multipleSelection = selection
      },
      //批量删除
      removeRows () {
        this.$confirm('此操作将永久删除教案信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { //确定执行then方法
          var idList = []
          //遍历数组得到每个id值，设置到idList里面
          for (var i = 0; i < this.multipleSelection.length; i++) {
            var obj = this.multipleSelection[i]
            var id = obj.id
            idList.push(id)
          }
          alert(this.multipleSelection.length)
          //调用接口

          console.log("idList"+idList)
          teachingProgram.batchRemoveTeachingProgram(idList)
            .then(response => {
              //提示
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              //刷新页面
              this.getList(1)
            })
        })
      },

      //修改
      update () {
        teachingProgram.updateTeachingProgram(this.TeachingProgram)
          .then(response => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/teaching_program/list' })
          })
      },
      saveOrUpdate () {
        //判断添加还是修改
        if (!this.TeachingProgram.id) { //没有id，做添加
          this.save();
        } else {//修改
          this.update()
        }
      }

    }
  }
</script>
