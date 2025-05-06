<template>
	<div class="searchInfo">
		<!-- 建立搜索框 -->
		<div class="search">
			<i  @click="searchStudent"  class="iconfont icon-sousuo1" style="width:100px"></i>
			<div class="input-group">
				<input id="btn" type="text" class="form-control"  v-model="Keywords" placeholder="请输入学生姓名或学号">
			</div>
			<i class="el-icon-close" @click="clearvalue"></i>
		</div>
		<!-- 建立学生计划信息 -->
		<div class="classInfo">
			<table class="table table-bordered table-hover table-striped">
				<thead>
					<tr>
						<th>学号</th>
						<th>姓名</th>
						<th>班级</th>
						<th>年级</th>
						<th>目标德育成绩</th>
						<th>目标智育成绩</th>
						<th>目标体育成绩</th>
						<th>目标美育成绩</th>
						<th>目标劳育成绩</th>
						<th>学生规划</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="s in list" :key="s.sid">
						<td>{{s.studentId}}</td>
						<td>{{s.studentName}}</td>
						<td>{{s.studentClassNumber}}</td>
						<td>{{s.studentGrade}}</td>
						<td>{{s.pDeyu}}</td>
						<td>{{s.pZhiyu}}</td>
						<td>{{s.pTiyu}}</td>
						<td>{{s.pMeiyu}}</td>
						<td>{{s.pLaoyu}}</td>
						<td><a href="#" @click="getid(s.studentId)" style="margin-right: 10px;">详情</a>
							<a href="#" @click="getComment(s.studentId)">查评</a></td>
					</tr>
				</tbody>
			</table>
			<div style="display: flex; justify-content: center;">
				<el-pagination background layout="total, sizes, prev, pager, next, jumper":total="total":current-page="currentPage" :page-size="pageSize" @current-change="handlePageChange" @size-change="handleSizeChange" :page-sizes="[2,5,10]"></el-pagination>
			</div>
		</div>
		  <el-dialog id="S-plan" title="学生规划" :visible.sync="outerVisible" class="roll-dialog">
			<div class="studentplan">
				  <p>{{studentplan}}</p>
			</div>
			<el-dialog id="tcomment"
			  width="30%"
			  title="评价"
			  :visible.sync="innerVisible"
			  append-to-body>
			  <!-- 内层内容区-->
			  <textarea  class="form-control"  v-model="SPcomment" rows="16"></textarea>
			  <div slot="footer" class="dialog-footer">
			    <el-button type="primary" @click="postinfo">提 交</el-button>
			  </div>
			</el-dialog>
			<div slot="footer" class="dialog-footer">
				<el-button @click="outerVisible = false">取 消</el-button>
				<el-button type="primary" @click="docomment">评价</el-button>
			</div>
		</el-dialog>
		<el-dialog id="S-comment" title="教师评语" :visible.sync="outerVisibleComment" class="roll-dialog">
		  	<div class="studentplan">
				<p>{{Comment.comment}}</p>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click="outerVisibleComment = false">取 消</el-button>
			</div>
		</el-dialog>
	</div>
</template>
<script>
 import axios from 'axios'
import { comment } from 'html-docx-js/dist/html-docx';
import { error } from 'jquery';
	export default {
	  name: 'SearchInfo',
	  data(){
		  return{
			  innerVisible:false,
			  outerVisible: false,
			  outerVisibleComment: false,
			  Keywords:'',
			  list:'',
			  SPcomment:'',
			  id:-1,
			  studentplan:'',
			  total:0,
			  currentPage:1,
			  pageSize:5,
			  Id:-1,
			  Name:'',
			  Comment:{comment: ''}
			}
	  },
	  methods:{
		  clearvalue(){
			  this.Keywords = ''
			  this.Id = -1
			  this.Name = ''
			  this.currentPage = 1
			  this.paginationQuery()
		  },
		  searchStudent(){
			  var reg = new RegExp("[0-9]")//
			  if(reg.test(this.Keywords)){//包含数字,赋值给id
					this.Id=this.Keywords
			  }else{
				  this.Name=this.Keywords
			  }
			  this.paginationQuery()

		  },
		  getid(id){
			  this.id=id
        //console.log(id)
			  this.outerVisible=true;
			  for(var i=0;i<this.list.length;i++){
				  if(this.list[i].studentId==id){
					  console.log(this.list[i].pPlan)
					  this.studentplan=this.list[i].pPlan
				  }
			  }

		  },
		  getComment(id){
			  this.outerVisibleComment=true;
			  this.Comment={comment:''}
			  axios.get("http://36.111.68.174:33380/diagnose/getcomment",{
				  params:{
					  id:id
				  }
			  }).then(
			  response =>{
				if(response.data.data==null){
					this.Comment.comment='暂无评论'
				}else{
					this.Comment=response.data.data
				}
			  },
			  error =>{
			  	const h = this.$createElement;
			  	this.$notify({
			  	    title: '提示信息',
			  	    message: '查询失败!',
			  	});
			  }
			  )
		  },
		  docomment(){
			  this.innerVisible=true

		  },
		  postinfo(){
			  if(this.SPcomment==''){
				  const h = this.$createElement;
				  this.$notify({
				      title: '提示信息',
				      message: '评价不能为空!',
				  });
				  return;
			  }
			  this.innerVisible=false
			  axios.get("http://36.111.68.174:33380/diagnose/SPcomment",{
				  params:{
					  id:this.id,
					  comment:this.SPcomment
				  }
			  }).then(//  /Learning重定向到8081
			  response =>{
			  	const h = this.$createElement;
			  	this.$notify({
			  	    title: '提示信息',
			  	    message: '提交成功!',
			  	});
			  },
			  error =>{
			  	const h = this.$createElement;
			  	this.$notify({
			  	    title: '提示信息',
			  	    message: '提交失败!',
			  	});
			  }
			  )
			  this.SPcomment = ''/* 清空输入框 */
		  },
		  paginationQuery(){
			axios.get("http://36.111.68.174:33380/diagnose/getallplan",{
				  params:{
					  page:this.currentPage,
					  pageSize:this.pageSize,
					  id:this.Id,
					  name:this.Name

				  }
			  }).then(
			  response =>{
			  	this.list=response.data.data.rows;
			  	this.total=response.data.data.total;
			  },
			  error =>{
			  	const h = this.$createElement;
			  	this.$notify({
			  	    title: '提示信息',
			  	    message: '查询失败!',
			  	});
			  }
			  )
		  },
		  handlePageChange(val){
			  this.currentPage=val
			  this.paginationQuery()
		  },
		  handleSizeChange(val){
			  this.currentPage = 1
			  this.pageSize=val
			  this.paginationQuery()
		  }
	  },
	  created(){
		  /*axios.get("http://36.111.68.174:33380/diagnose/getallplan").then(
		  response =>{
		  	this.list=response.data;
        console.log(response.data)
		  },
		  )*/
		  this.paginationQuery()
	  }
	}
</script>

<style scoped>
	.searchInfo {
		float: left;
		width: 500px;
		height: 100%;
    margin-left:250px;
	}
  .classInfo{
  	width:1000px;
    height:100%;
  	margin-left:-180px;
  	margin-top:200px;
    overflow-y: auto;
  }
  .classInfo a:hover{
    color:orange;
  }
	.searchInfo .input-group {
		position: absolute;
		width: 500px;
		top: 100px;
	}
	.el-icon-close{
		margin-left:-20px;
		margin-top:3px;
		display:none;

	}
	.search:hover .el-icon-close{
		display:block;
	}
	.searchInfo i {
		position: absolute;
		top: 104px;
		left: 730px;
		font-size: 18px;
		color: blue;
		z-index: 999;
	}
	.searchInfo i:hover{
		color:orange;
	}
	.searchInfo #btn {
		border-radius: 10px;
	}
	.studentplan{
		width:620px;
		height:220px;
		overflow-y: auto;
	}
</style>
