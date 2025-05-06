<template>

  <div style="padding:10px ">
    <!--    <div style="margin: 10px 0">-->
    <!--      <el-button type="success" plain icon="el-icon-check" @click="add">添加学生信息</el-button>-->
    <!--    </div>-->
    <div style="margin: 10px 0">
      年级
      <el-select v-model="value" clearable placeholder="学生年级" class="ml-5">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      班级
      <el-select v-model="valueClass" clearable placeholder="学生班级" class="ml-5">
        <el-option
          v-for="item in optionsClass"
          :key="item.valueClass"
          :label="item.label"
          :value="item.valueClass">
        </el-option>
      </el-select>
      状态
      <el-select v-model="valueZT" clearable placeholder="状态" class="ml-5">
        <el-option
          v-for="item in optionsZT"
          :key="item.valueZT"
          :label="item.label"
          :value="item.valueZT">
        </el-option>
      </el-select>
      <el-input v-model="search" maxlength="10" placeholder="请输入十位以内的学生姓名" style="width:20%" clearable></el-input>
      <el-button type="warning" @click="reset">重置</el-button>
      <el-button type="primary" plain icon="el-icon-search" @click="load">查询</el-button>
      <el-button type="primary" plain icon="el-icon-star-off" @click="delBatch">一键评阅</el-button>
    </div>
    <el-table
      v-loading="tableLoading"
      :data="tableData"
      :row-style="{ height:'100px'}"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <!--  增加sid    -->
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column
        prop="studentNum"
        label="学号"
      >
        <!--  增加sid      -->
      </el-table-column>
      <el-table-column
        prop="studentName"
        label="学生姓名"
      >
      </el-table-column>
      <el-table-column
        prop="gradeId"
        label="年级"
      >
      <template v-slot="{ row }">
          <span v-show="row.gradeId === 1">一年级</span>
          <span v-show="row.gradeId === 2">二年级</span>
          <span v-show="row.gradeId === 3">三年级</span>
          <span v-show="row.gradeId === 4">四年级</span>
          <span v-show="row.gradeId === 5">五年级</span>
          <span v-show="row.gradeId === 6">六年级</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="classId"
        label="班级"
      >
      <template v-slot="{ row }">
          <span v-show="row.classId === 1">一班</span>
          <span v-show="row.classId === 2">二班</span>
          <span v-show="row.classId === 3">三班</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="moralityScore"
        label="德育"
      >
      </el-table-column>
      <el-table-column
        prop="intelligenceScore"
        label="智育"
      >
      </el-table-column>
      <el-table-column
        prop="physicalScore"
        label="体育"
      >
      </el-table-column>
      <el-table-column
        prop="aestheticScore"
        label="美育"
      >
      </el-table-column>
      <el-table-column
        prop="labourScore"
        label="劳育"
      >
      </el-table-column>
      <el-table-column prop="isenter" label="成绩录入状态" >
        <template v-slot="{ row }">
          <span v-show="row.isenter === 0">未录入</span>
          <span v-show="row.isenter === 1">已录入</span>
        </template>
      </el-table-column>
      <el-table-column prop="isreview" label="评阅状态" >
        <template v-slot="{ row }">
          <span v-show="row.isreview === 0">未评阅</span>
          <span v-show="row.isreview === 1">已评阅</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="remark"
        label="评语"
      >
        <template slot-scope="{ row }">
          <el-tooltip class="item" effect="dark" :content="row.remark" :popper-class="'custom-tooltip'">
            <span>{{ truncateString(row.remark) }}</span>
           </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100%">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="text" icon="el-icon-edit"  size="small">编辑评语</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin:10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>

      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px" :rules="rules">
          <el-form-item label="学号">
            <!--  增加sid      -->
            <el-input v-model="form.sid" prop="id" placeholder="请输入学生学号"></el-input>
            <!--            <el-input v-model="form.id"></el-input>-->
          </el-form-item>
          <el-form-item label="学生名称">
            <el-input v-model="form.name" prop="name" placeholder="请输入学生姓名"></el-input>
          </el-form-item>
          <el-form-item label="德育分数">
            <el-input v-model="form.virtue" prop="virtue" placeholder="请输入学生德育分数"></el-input>
          </el-form-item>
          <el-form-item label="智育分数">
            <el-input v-model="form.intelligence" prop="intelligence" placeholder="请输入学生智育分数"></el-input>
          </el-form-item>
          <el-form-item label="体育分数">
            <el-input v-model="form.sports" prop="sports" placeholder="请输入学生体育分数"></el-input>
          </el-form-item>
          <el-form-item label="美育分数">
            <el-input v-model="form.art" prop="art" placeholder="请输入学生美育分数"></el-input>
          </el-form-item>
          <el-form-item label="劳育分数">
            <el-input v-model="form.labor" prop="labor" placeholder="请输入学生劳育分数"></el-input>
          </el-form-item>
          <el-form-item label="评价">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save_get">确 定</el-button>
  </span>
      </el-dialog>
      <el-dialog title="提示" :visible.sync="dialogVisible1" width="30%">
        <el-form :model="form" label-width="120px">
          <!--          <el-form-item label="学号">
                      <el-input v-model="form.studentNum"></el-input>

                    </el-form-item>
                    <el-form-item label="学生名称">
                      <el-input v-model="form.studentName"></el-input>
                    </el-form-item>
                    <el-form-item label="评阅状态" prop="isreview">
                      &lt;!&ndash;                <el-radio-group v-model="form.equUse">
                                <el-radio label="使用中"></el-radio>
                                <el-radio label="未使用"></el-radio>
                              </el-radio-group>&ndash;&gt;
                      <template>
                        <el-radio v-model="form.isreview" :label="0">未审阅</el-radio>
                        <el-radio v-model="form.isreview" :label="1">已审阅</el-radio>
                      </template>
                    </el-form-item>-->
          <el-form-item label="评语">
            <el-input type="textarea" v-model="form.remark" :rows="40"></el-input>

          </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="edit_get">确 定</el-button>
  </span>
      </el-dialog>

    </div>
  </div>
</template>

<script>

import request from "@/utils/re_request"
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name:"Home",
  data(){
    var validateCode = (rule, value, callback) => {
      const reg = /^.{0,10}$/ // 6-11位数字正则表达式
      setTimeout(() => {
        if (!reg.test(value)) {
          callback(new Error('字符必须小于10位'))
        } else {
          callback()
        }
      }, 500);
    };
    return{
      form:{},
      dialogVisible:false,
      dialogVisible1:false,
      search:'',
      classId:'',
      currentPage:1,
      total:0,
      multipleSelection: [],
      pageSize:10,
      tableLoading: false,
      tableData:[
        {
          studentNum: '2018083056',
          studentName: '001',
          moralityScore:100,
          intelligenceScore:100,
          physicalScore:100,
          aestheticScore:100,
          labourScore:100,
          classId:0,
          gradeId:0,
          isreview:0,
          isenter:0,
          remark:'',
        },

      ],
      options: [
        {
          value: '',
          label: '全部'
        },{
          value: '1',
          label: '一年级'
        }, {
          value: '2',
          label: '二年级'
        }, {
          value: '3',
          label: '三年级'
        }, {
          value: '4',
          label: '四年级'
        }, {
          value: '5',
          label: '五年级'
        }, {
          value: '6',
          label: '六年级'
        }], value: '',
      optionsClass: [
        {
          valueClass: '',
          label: '全部'
        },{
          valueClass: '1',
          label: '一班'
        }, {
          valueClass: '2',
          label: '二班'
        }, {
          valueClass: '3',
          label: '三班'
        }], valueClass: '',
      optionsZT: [{
        valueZT: '',
        label: '全部'
      }, {
        valueZT: '0',
        label: '未评阅'
      }, {
        valueZT: '1',
        label: '已评阅'
      }], valueZT: '',
      tid:1,
      rules:{
        search:[
          { min:0, max:10, message: '长度为10位字符以内', trigger: 'blur' },
          {required: true, validator: validateCode, trigger: 'blur'}
        ],
      },
    }
  },
  created(){
    this.load()
  },
  methods: {
    load(){
      this.tableLoading = true
      request.get("/studentInfo/findAll",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search,
          value:this.value,
          valueClass:this.valueClass,
          valueZT:this.valueZT,
        }
      }).then(res =>{
        console.log(res)
        this.tableData = res.data.list
        this.total = res.data.count
        this.tableLoading = false
      }).catch(()=> {
        this.tableLoading = false
      })
    },

    reset() {
      this.search = ""
      this.value = ""
      this.valueClass = ""
      this.valueZT = ""
      this.load()
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    //批量审阅
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)   //扁平化处理 将object对象转换为[1,2,3]，即[{},{},{}] => [1,2,3]
      request.post("/studentInfo/batch", ids).then(res => {
        if(res.data) {
          this.$message.success("一键审阅成功")
          this.createremark()
          this.load()
        } else {
          this.$message.error("一键审阅失败, 请选择评阅学生")
        }

      })
    },
    save(){
      request.post("/student",this.form).then(res => {
        console.log(res)
        this.$message({
          type:"success",
          message:"学生新增成功！"
        })
        setTimeout(this.load,2000)
        this.dialogVisible=false
      })
    },
    save_get(){
      request.get("/student/save_get",{
        params:{
          id:this.form.id,
          sid:this.form.studentNum,
          name:this.form.studentName,
          virtue:this.form.moralityScore,
          intelligence:this.form.intelligenceScore,
          sports:this.form.physicalScore,
          art:this.form.aestheticScore,
          labor:this.form.labourScore,
          isReview:this.form.isreview,
        }
      }).then(res =>{
        console.log(123)
        console.log(res)
        console.log(456)
        this.$message({
          type:"success",
          message:"学生信息增加成功！"
        })
      })
      this.dialogVisible = false
      setTimeout(this.load,2000)
    },
    edit(){
      request.put("/student",this.form).then(res => {
        console.log(res)
        this.$message({
          type:"success",
          message:"Success!"
        })
      })
      this.dialogVisible1=false
      this.load()
    },
    edit_get(){
      console.log("edit_get start")
      console.log(this.form)
      request.get("/studentInfo/edit_get",{
        params:{
          id:this.form.id,
          remark:this.form.remark,
        }
      }).then(res =>{
        console.log(res)
        this.$message({
          type:"success",
          message:"学生信息修改成功！"
        })
        this.load();
      })
      this.dialogVisible1 = false
    },
    add(){
      this.dialogVisible=true
      this.form = {}
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible1 = true
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    },
    handleDelete(id){
      console.log(id+1471)
      request.delete("/student/"+(id+1471)).then(res =>{
        if(res.code === 0){
          this.$message({
            type:"success",
            message:"学生信息删除成功！"
          })
        }
        this.load()
      })
      /*      request.get("/feedback/deletefeedback",{
              params:{
                id:id
              }
            })
            console.log("发出get请求")*/
    },
    createremark(){
      this.$message({
        type:"success",
        message:"生成评语成功，请刷新页面"
      })
      request.get("/studentInfo/allremark",{
        params:{
          tid:1
        }
      })
      this.load()
    },
    truncateString(str) {
      // 检查字符串长度是否超过10
      if (str?.length > 10) {
          // 截取前10个字符并加上省略号
          return str.slice(0, 10) + '...';
      } else {
          // 如果长度不超过10，直接返回原字符串
          return str;
      }
    }
  },
}
</script>

<style>
.custom-tooltip {
  max-width: 50vw; /* 设置最大宽度 */
}

.ellipsis {
    white-space: nowrap;        /* 不换行 */
    overflow: hidden;           /* 超出部分隐藏 */
    text-overflow: ellipsis;   /* 文字溢出时显示省略号 */
}
</style>
