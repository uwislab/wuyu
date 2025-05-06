<template>
  <div class="edit">

      <div v-show="searchVisible">
          <span>教师ID：</span>
          <el-input v-model.number="tid" placeholder="请输入教师ID" style="width:20%" clearable @clear="handleClear"></el-input>
          <el-button class="searchbtn" type="primary" icon="el-icon-search" @click="getData">查询</el-button>
      </div>

      <el-dropdown>
      <span class="el-dropdown-link">
        {{this.currentItem}}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <!-- 动态展示学期 -->
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-for= "(item,index) in itemData"
        :key="index"
        :disabled="item.disabled"
        :divided="item.divided"
        split-button="true"
        >
          <el-button @click="handleItemClick(item)">{{ item }}</el-button>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

      <el-popconfirm
          title="确定生成评语吗？"
          @confirm="autoRemark()"
        >
          <el-button slot="reference">生成</el-button>
        </el-popconfirm>

      <el-dialog :visible.sync="dialogFormVisible" :before-close="handleClose">
      <el-form :model="form">
          <el-form-item label="评语" label-width="120" prop="remark">
            <el-input autosize type="textarea" v-model="form.remark" autocomplete="off"></el-input>
          </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancle">取 消</el-button>
        <el-button type="primary" @click="handleForm">确 定</el-button>
      </div>
  </el-dialog>
  <!-- boeder和stripe都是布尔值,原来用的"1",导致输入教师id时经常出现报错行为.已经解决 -->
      <el-table
  :data="list"
  style="width: 100%;margin-top: 10px;"
  height="620"
  stripe
  v-loading="loadingTable"
  >

  <el-table-column
    type = "expand">
    <template slot-scope="props">
      <el-form label-position="left" inline class="demo-table-expand">
        <el-form-item label="德育成绩">
          <span>{{ props.row.virtue }}</span>
        </el-form-item>
        <el-form-item label="智育成绩">
          <span>{{ props.row.intelligence }}</span>
        </el-form-item>
        <el-form-item label="体育成绩">
          <span>{{ props.row.sports }}</span>
        </el-form-item>
        <el-form-item label="美育成绩">
          <span>{{ props.row.art }}</span>
        </el-form-item>
        <el-form-item label="劳育成绩">
          <span>{{ props.row.labor }}</span>
        </el-form-item>
        <el-form-item label="教师姓名">
          <span>{{ props.row.teacher.username }}</span>
        </el-form-item>
      </el-form>
    </template>
  </el-table-column>

  <el-table-column
    prop="id"
    label="评语id"
    width="120">
  </el-table-column>
  <el-table-column
    prop="sid"
    label="学号"
    width="120">
  </el-table-column>
  <el-table-column
    prop="name"
    label="姓名"
    width="120">
  </el-table-column>


  <!-- <el-table-column
    prop="virtue"
    label="德育成绩"
    width="80">
  </el-table-column>
  <el-table-column
    prop="intelligence"
    label="智育成绩"
    width="80">
  </el-table-column>
  <el-table-column
    prop="sports"
    label="体育成绩"
    width="80">
  </el-table-column>
  <el-table-column
    prop="art"
    label="美育成绩"
    width="80">
  </el-table-column>
  <el-table-column
    prop="labor"
    label="劳育成绩"
    width="80">
  </el-table-column> -->
  <el-table-column
    prop="totalScore"
    label="总评成绩"
    width="120">
  </el-table-column>
  <el-table-column
    prop="remark"
    label="评语"
    width="480"
    show-overflow-tooltip>
  </el-table-column>
  <!-- <el-table-column
    prop="teacher.username"
    label="教师名称"
    width="120"
    >
  </el-table-column> -->

  <el-table-column
    label="操作"
    width="360"
    v-if="!searchVisible"
    >
    <template slot-scope="scope">
        <el-button
        type="primary"
          @click.native.prevent="handleUpdate(scope.row)"
          >
          编辑
        </el-button>
        <el-button
        type="danger"
          @click.native.prevent="$event => handleDelete(scope.row.id)"
          >
          删除
        </el-button>
    </template>
  </el-table-column>
</el-table>

  <el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="currentPage"
    :page-sizes="[10, 20, 30, 40, 50]"
    :page-size="pageSize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="total"
    background
    class="pagination"
    >
  </el-pagination>

  </div>
</template>

<script>
import request from "@/utils/re_request"
import { Message } from "element-ui"
export default {
  name:'class',
  data(){
      return {
          //当前的教师(校长)id,由登陆界面传输数据确定
          id:'',
          //用来查询的教师id
          tid:'',
          //前端传来的信息
          userInfo:{},
          pageSize:10,
          currentPage: 1,//当前页
          total: 0,//总记录数
          // 原数据
          data:[],
          // 展示数据
          list:[],
          //学期数组
          itemData:[],
          // 是否显示对话框
          dialogFormVisible:false,
          //是否显示搜索框
          searchVisible:false,
          //当前学期
          currentItem:"全学期",
          // 表单
          form:{
              id:'',
              remark:'',
          },
          // 操作得学生ID
          operationId:'',
          //加载表标识
          loadingTable:false,
      }
  },
  methods:{
      // 查询教师ID对应得学生数据
      getData() {
        this.loadingTable = true;
        if(this.currentItem == '全学期'){
          request.get(`/performance/all?tid=${this.tid}`).then(
              response => {
                  if(response.code === 600) {
                      this.data = response.data
                      this.list = response.data.slice(0, this.pageSize)
                      this.total = response.count
                  }
              }
          )
        }
        else{
        request.get('/performance/allByItem?tid='+this.tid+'&item='+this.currentItem)
            .then(res => {
              if (res.data) {
                console.log("vue-235:"+this.currentItem); // 打印 item，查看它的结构
                this.data = res.data
                this.list = res.data.slice(0, this.pageSize)
                this.total = res.count
              } else {
              }
            })
            .catch(err => {
              console.error(err);
              this.$message.error("请求出错");
            });
        }
          //获取学期
          request.get(`/performance/getItem`).then(
              response => {
                  if(response.code === 600) {
                      this.itemData = response.data
                      this.total = response.count
                      console.log("item="+this.itemData)
                  }
              }
          )
          this.loadingTable = false;
      },
      // 清除
      handleClear() {
          this.data = []
      },
      // 当前页变化
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage
        this.list = this.data.slice(this.pageSize * (currentPage - 1), this.pageSize * currentPage)
      },
      // 页面大小更新
      handleSizeChange(pageSize) {
        this.currentPage = 1
        this.pageSize = pageSize
        this.list = this.data.slice(0, pageSize)
      },
      // 处理学生表现数据更新
      handleUpdate(item) {
        this.operationId = item.id
        // 开启对话框
        this.dialogFormVisible = true

        // 填充数据
        this.$nextTick(()=>{
          this.form.remark = item.remark
      })

      },
      // 处理从下方的取消 确定关闭对话框
      handleCancle() {
        // 关闭对话框
        this.dialogFormVisible = false

        // 清空表单中的值并重置isEdit和totalScore
        this.$refs["form"].resetFields()
        this.isEdit = false
        this.totalScore = ''
      },
      // 处理从右上角的的×关闭对话框
      handleClose(done){
        this.$confirm('确认关闭？')
        .then(_ => {
          this.handleCancle()
          done();
        })
        .catch(_ => {});
      },
      handleForm() {
        // 关闭对话框
        this.dialogFormVisible = false
        // 根据isEdit处理逻辑
        // 后端处理
          request.put('/remark/update', {
            id: this.operationId,
            remark: this.form.remark,
          }).then(
            response => {
              if(response.code === 600) {
                // 前端更新
                this.getData()
                Message.success(response.msg)
              }
            }
          )
        // 重置form和isEdit
        // 清空表单中的值
        this.$refs["form"].resetFields()
      },
      // 删除评价
      handleDelete(id) {
        this.loadingTable = true;
          this.$confirm('确认删除该学生的评价吗？')
        .then(_ => {
          request.delete('/performance/delete?id='+id).then(
            response => {
              if(response.code === 600) {
                Message.success(response.msg)
              }
              this.getData()
              this.loadingTable = false;
            }
          )
        })
        .catch(_ => {
          this.loadingTable = false;  // Ensure loading is set to false when the user clicks "Cancel"
        });
      },
      // 生成单条评语，已弃用
      handleGenerateRemark(item) {
        if(item.remark === undefined){
          item.remark = "";
        }
        if(item.remark.trim().length > 0) {
          Message.warning("评语已存在，不能生成！")
        }
        else
        {
          let a = [item.id]; // 数组数据
          console.log(a);
          // 发送数组而不是对象
          request.post('/studentInfo/batch', a)
            .then(res => {
              if (res.data) {
                this.getData();
                this.$message.success("生成评语成功");
              } else {
              }
            })
            .catch(err => {
              console.error(err);
              this.$message.error("请求出错");
            });

        }
      },
      //查询某个学期的当前老师的学生信息
      handleItemClick(item){
        this.loadingTable = true;
        //暂时写死老师id为1
        request.get('/performance/allByItem?tid='+this.tid+'&item='+item)
            .then(res => {
              if (res.data) {
                console.log("demo:"+item); // 打印 item，查看它的结构
                Message.success("跳转成功了,当前学期:"+item);
                this.currentItem = item;
                this.data = res.data
                this.list = res.data.slice(0, this.pageSize)
                this.total = res.count
              } else {
              }
            })
            .catch(err => {
              console.error(err);
              this.$message.error("请求出错");
            });
        this.loadingTable = false;
        return
      },
      //批量生成
      autoRemark(){
        console.log(this.data)
        if(this.id==-1){
          Message.error("尊敬的校长，如果需要修改评语，请先与班主任沟通后让班主任进行修改")
          return
        }
        if(this.currentItem == "全学期"){
          Message.error("请先选择一个学期在进行评语生成")
          return
        }
        else if(this.data==""){
          Message.error("当前学期没有学生信息，请切换至有学生的学期")
          return
        }
        else{
            this.loadingTable = true;
            console.log(this.data)
            console.log(this.tid)
            request.get('/performance/autoRemark?tid='+this.tid+'&item='+this.currentItem)
            .then(res => {
              if (res.data) {
               Message.success(res.msg)
              } else {
                Message.error("生成失败")
              }
              this.getData();
              this.loadingTable = false;
            })
            .catch(err => {
              console.error(err);
              this.$message.error("请求出错");
            });
        }
      }
  },
mounted() {
  this.getData();
},
created(){
  this.userInfo = JSON.parse(window.localStorage.getItem("UserInfo"));
  console.log("下面时userInfo的信息")
  // console.log(window.localStorage.getItem("UserInfo"))
  console.log(this.userInfo.id)
  console.log(this.userInfo.identity)
  if(this.userInfo.identity!=0){
    this.id = this.userInfo.id
    this.tid = this.userInfo.id
  }else{
    this.searchVisible = true
    this.id = -1
  }

}
}
</script>

<style>
.edit{
  margin: 30px 50px 0 50px;
}

.pagination {
  margin-left: 30%;
  font-size: 14px;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #000000;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 30%;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
