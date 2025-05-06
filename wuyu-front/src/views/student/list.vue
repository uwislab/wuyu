<template>
  <!--学生信息列表页面-->
  <div>
    <div class="app-container">
      <div class="handle-box">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="searchObj.studentId" placeholder="学生学号" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchObj.studentName" placeholder="学生姓名" />
          </el-form-item>
          <el-form-item>
            <el-select v-model="searchObj.studentGrade" placeholder="学生年级">
              <el-option label="一年级" value="1"></el-option>
              <el-option label="二年级" value="2"></el-option>
              <el-option label="三年级" value="3"></el-option>
              <el-option label="四年级" value="4"></el-option>
              <el-option label="五年级" value="5"></el-option>
              <el-option label="六年级" value="6"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchObj.studentClassNumber" placeholder="学生班级(输入数字即可)" />
          </el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
          <el-button type="danger" icon="" @click="clear()">清空条件</el-button>
        </el-form>

      </div>

      <el-table ref="studentTable"
                border
                :data="list"
                style="width: 100%"
      >
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column type="index"
                         :index='(current-1)*limit+1'
                         width="50"
                         label="序号"
        />
        <el-table-column label="学生学号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.studentId}}</template>
        </el-table-column>
        <el-table-column label="学生姓名" width="200" align="center">
          <template slot-scope="scope">{{scope.row.studentName}}</template>
        </el-table-column>
        <el-table-column label="学生性别" width="100" align="center">
          <template slot-scope="scope">{{getSex(scope.row.studentGender)}}</template>
        </el-table-column>
        <el-table-column label="学生班级"  align="center">
          <template slot-scope="scope">{{scope.row.studentClassName}}</template>
        </el-table-column>
        <el-table-column label="学生年级"  align="center">
          <template slot-scope="scope">{{getGrade(scope.row.studentGrade)}}</template>
        </el-table-column>

        <el-table-column label="操作" width="300" align="center">
          <template >
<!--            <router-link :to="'/student/score/'+scope.row.studentId">-->
            <el-button size="medium" type="primary" :plain="true" @click="getAlert" >五育评分</el-button>
<!--            </router-link>-->
          </template>
        </el-table-column>

      </el-table>
      <!-- 分页 -->
      <el-pagination :current-page="page"
                     :page-sizes="[10, 20, 30, 40]"
                     :page-size="limit"
                     :total="total"
                     style="padding: 30px 0; text-align: center;"
                     layout="total,prev,pager,next,jumper"
                     @current-change="getList" />
    </div>
  </div>
</template>

<script>
  import student from '@/api/student'
  export default {
    name: "studentList",
    data() {
      return {
        current: 1,//当前页
        limit: 10,//每页显示记录数
        searchObj: {}, //条件封装对象
        list: [],//每页数据集合
        total: 0,//总记录数
        multipleSelection: [], // 批量选择中选择的记录列表
        page: 1
      }
    },
    created() {
      this.getList();


    },
    methods: {
      getAlert(){
        this.$message({
          message: '功能模块正在开发中，敬请期待~',
          type: 'warning'
        });
      },
      getList(page = 1) {
        //添加当前页参数
        this.current = page;
        student.getStudent(this.current, this.limit, this.searchObj).then(
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

      getSex(val) {
        if (val === 0) return "女"
        return "男"
      },

      getGrade(val){
        if (val === 1) return "一年级"
        if (val === 2) return "二年级"
        if (val === 3) return "三年级"
        if (val === 4) return "四年级"
        if (val === 5) return "五年级"
        if (val === 6) return "六年级"
       },

       clear(){
         this.searchObj.studentId=""
         this.searchObj.studentName=""
         this.searchObj.studentGrade=""
         this.searchObj.studentClassName=""
       }
    }
  }
</script>

