<template>
	<el-main class="bg table_wrap">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="课程名称">
						<el-input v-model="query.courseName"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="课程类型">
						<el-select id="courseType" v-model="query.courseType" placeholder="请选择课程类型">
							<el-option label="德育" value="0"></el-option>
							<el-option label="智育" value="1"></el-option>
							<el-option label="体育" value="2"></el-option>
							<el-option label="美育" value="3"></el-option>
							<el-option label="劳育" value="4"></el-option>
						</el-select>
						<!-- <el-input v-model="query.courseType"></el-input> -->
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="教师姓名">
						<el-input v-model="query.teacherName"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="学生学号">
						<el-input v-model="query.studentNum"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="学生姓名">
						<el-input v-model="query.studentName"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="10" :lg="8" class="search_btn_wrap_1">
					<el-form-item>
						<el-button type="primary" @click="getCourseScoreApi();" class="search_btn_find">查询</el-button>
						<el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
						<router-link class="el-button el-button--default el-button--primary search_btn_add"
							to="./view?">添加
						</router-link>
						<el-button v-if="isAcademicDirector" class="search_btn_del" type="danger" @click="delInfo()">撤回</el-button>
					</el-form-item>
				</el-col>

			</el-row>
		</el-form>
		<el-table :data="list" @selection-change="handleSelectionChange" style="width: 100%" id="dataTable">
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
			<el-table-column prop="courseName" label="课程名称" min-width="200">
			</el-table-column>
			<el-table-column prop="courseType" label="课程类型" min-width="200">
				<template slot-scope="scope">
					<!-- 使用映射表获取对应的标签 -->
					{{ courseTypeMap[scope.row.courseType] || '未知类型' }}
				</template>
			</el-table-column>
			<el-table-column prop="testNumber" label="考试序号" min-width="200">
			</el-table-column>
			<el-table-column prop="teacherName" label="课程教师" min-width="200">
			</el-table-column>
			<el-table-column prop="studentNum" label="学生学号" min-width="200">
			</el-table-column>
			<el-table-column prop="studentName" label="学生姓名" min-width="200">
			</el-table-column>
			<el-table-column prop="inputTime" label="录入日期" min-width="200">
				<template slot-scope="scope">
					{{ scope.row.inputTime }}
				</template>
			</el-table-column>
			<el-table-column prop="score" label="学生成绩" min-width="200">
			</el-table-column>
			<el-table-column prop="remark" label="教师评语" min-width="200">
			</el-table-column>

			<el-table-column fixed="right" label="操作" min-width="120">
				<template slot-scope="scope">
					<router-link class="el-button el-button--small is-plain el-button--success"
						style="margin: 5px !important;" :to="{
							path: './view',
							query: { rowData: JSON.stringify(scope.row) }
						}" size="small">
						<span>详情</span>
					</router-link>
				</template>
			</el-table-column>

		</el-table>

		<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination :current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.pageSize"
				:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"
				layout="total, sizes, prev, pager, next, jumper">
			</el-pagination>
		</div>
		<!-- /分页器 -->

		<!-- 成绩统计图表 -->
		<div class="statistics-container mt">
			<div class="statistics-header">
				<h3>课程成绩统计</h3>
				<el-select v-model="selectedCourse" placeholder="请选择课程" @change="handleCourseChange" style="width: 200px;">
					<el-option 
						v-for="item in courseOptions" 
						:key="item.value" 
						:label="item.label" 
						:value="item.value">
					</el-option>
				</el-select>
			</div>
			<div id="scoreChart" class="chart-container"></div>
		</div>
		<!-- /成绩统计图表 -->

		<div class="modal_wrap" v-if="showModal">
			<div class="modal_box">
				<!-- <div class="modal_box_close" @click="closeModal">X</div> -->
				<p class="modal_box_title">重要提醒</p>
				<p class="modal_box_text">当前有数据达到预警值！</p>
				<div class="btn_box">
					<span @click="closeModal">取消</span>
					<span @click="closeModal">确定</span>
				</div>
			</div>
		</div>


	</el-main>
</template>
<script>

import axios from "axios";
import * as echarts from 'echarts';
import { getCourseScore, deleteCourseScore } from '@/api/courseScore/';

export default {
	data() {
		return {
			courseTypeMap: {
				0: '德育',
				1: '智育',
				2: '体育',
				3: '美育',
				4: '劳育'
			},
			// 弹框
			showModal: false,
			// 获取数据地址
			url_get_list: "~/api/course_score/get_list?like=0",
			url_del: "~/api/course_score/del?",

			// 字段ID
			field: "course_score_id",
			// 查询
			query: {
				"pageSize": 7,
				"page": 1,
				"courseName": "",
				"courseType": "",
				"teacherName": "",
				"studentNum": "",
				"studentName": "",
				"login_time": "",
				"create_time": "",
				"orderby": `create_time desc`
			},

			// 数据
			total: 0,//数据总数
			list: [],
			list_user_course_teacher: [],
			deleteIds: [],
			
			// 成绩统计相关数据
			selectedCourse: '',
			courseOptions: [],
			statisticsData: [],
			chart: null,
		}
	},
	computed: {
		// 判断当前用户是否为教务角色(identity为1)
		isAcademicDirector() {
			// 从store或localStorage获取当前用户信息
			const userInfo = this.$store.state.user.userInfo || JSON.parse(localStorage.getItem('userInfo') || '{}');
			return userInfo && userInfo.identity === 1;
		}
	},
	methods: {
		//api
		//分页条件查询
		getCourseScoreApi() {
			const formData = new FormData();
			formData.append('courseName', this.query.courseName);
			formData.append('courseType', this.query.courseType);
			formData.append('teacherName', this.query.teacherName);
			formData.append('studentNum', this.query.studentNum);
			formData.append('studentName', this.query.studentName);
			formData.append('page', this.query.page);
			formData.append('pageSize', this.query.pageSize);
			getCourseScore(formData).then(res => {
				console.log(res.data)
				this.total = Number(res.data.total);
				console.log("数据总数:" + this.total)
				this.list = res.data.data;
				
				// 查询条件变化后，重新计算统计数据
				this.calculateCourseScoreStatistics();
			})
		},
		//
		reset() {
			this.query.pageSize = 7
			this.query.page = 1
			this.query.courseName = ""
			this.query.courseType = ""
			this.query.teacherName = ""
			this.query.studentNum = ""
			this.query.studentName = ""
			this.query.login_time = ""
			this.query.create_time = ""
			this.query.orderby = `create_time desc`
			this.getCourseScoreApi()
		},
		handleSizeChange(pageSize) {
			this.query.pageSize = pageSize
			this.getCourseScoreApi()
		},
		handleCurrentChange(page) {
			this.query.page = page
			this.getCourseScoreApi()
		},
		handleSelectionChange(val) {
			console.log(val)
			// 提取所有对象的 id 值
			this.deleteIds = val.map(item => item.id);
			console.log(this.deleteIds); // 查看提取的 id 数组
		},
		delInfo() {
			console.log(this.deleteIds);
			//根据ids数组删除成绩
			deleteCourseScore(this.deleteIds).then(res => {
				console.log("删除返回数据:" + res.code)
				if (res.code == 200) {
					// 前端更新
					this.$message.success("撤回成功")
					//成功重新获取数据
					this.getCourseScoreApi()
					// 删除数据后重新计算统计数据
					this.calculateCourseScoreStatistics();
				} else {
					// 前端更新
					this.$message.error(res.msg)
				}

			})
		},
		// 关闭弹框
		closeModal() {
			this.showModal = false;
		},
		
		// 获取所有课程成绩数据（包括所有分页）
		async getAllCourseScoreData() {
			try {
				// 保存当前分页设置
				const currentPage = this.query.page;
				const currentPageSize = this.query.pageSize;
				
				// 计算总页数
				const totalPages = Math.ceil(this.total / currentPageSize);
				let allData = [];
				
				// 创建一个临时的查询对象，避免影响当前页面显示
				const tempQuery = { ...this.query };
				
				// 获取所有页的数据
				for (let page = 1; page <= totalPages; page++) {
					const formData = new FormData();
					formData.append('courseName', tempQuery.courseName);
					formData.append('courseType', tempQuery.courseType);
					formData.append('teacherName', tempQuery.teacherName);
					formData.append('studentNum', tempQuery.studentNum);
					formData.append('studentName', tempQuery.studentName);
					formData.append('page', page);
					formData.append('pageSize', currentPageSize);
					
					const res = await getCourseScore(formData);
					if (res.code === 200 && res.data.data) {
						allData = allData.concat(res.data.data);
					}
				}
				
				return allData;
			} catch (error) {
				console.error('获取所有课程成绩数据出错:', error);
				this.$message.error('获取所有课程成绩数据出错');
				return [];
			}
		},
		
		// 计算课程成绩统计数据
		async calculateCourseScoreStatistics() {
			try {
				// 获取所有课程成绩数据
				const allData = await this.getAllCourseScoreData();
				
				if (allData.length === 0) {
					this.$message.warning('没有可用的课程成绩数据');
					return;
				}
				
				// 按课程名称分组
				const courseGroups = {};
				allData.forEach(item => {
					if (!courseGroups[item.courseName]) {
						courseGroups[item.courseName] = [];
					}
					courseGroups[item.courseName].push(item);
				});
				
				// 计算每个课程的成绩分布
				const statisticsData = [];
				for (const courseName in courseGroups) {
					const scores = courseGroups[courseName].map(item => parseFloat(item.score));
					
					const bucket_0_59 = scores.filter(score => score >= 0 && score < 60).length;
					const bucket_60_69 = scores.filter(score => score >= 60 && score < 70).length;
					const bucket_70_79 = scores.filter(score => score >= 70 && score < 80).length;
					const bucket_80_89 = scores.filter(score => score >= 80 && score < 90).length;
					const bucket_90_100 = scores.filter(score => score >= 90 && score <= 100).length;
					
					statisticsData.push({
						courseName,
						bucket_0_59,
						bucket_60_69,
						bucket_70_79,
						bucket_80_89,
						bucket_90_100
					});
				}
				
				this.statisticsData = statisticsData;
				
				// 处理课程选项
				this.courseOptions = this.statisticsData.map(item => ({
					value: item.courseName,
					label: item.courseName
				}));
				
				// 默认选择第一个课程
				if (this.courseOptions.length > 0 && !this.selectedCourse) {
					this.selectedCourse = this.courseOptions[0].value;
					this.initChart();
				} else if (this.chart) {
					this.updateChart();
				}
			} catch (error) {
				console.error('计算课程成绩统计数据出错:', error);
				this.$message.error('计算课程成绩统计数据出错');
			}
		},
		
		// 处理课程选择变化
		handleCourseChange(value) {
			this.selectedCourse = value;
			this.updateChart();
		},
		
		// 初始化图表
		initChart() {
			// 确保DOM已经渲染
			this.$nextTick(() => {
				// 初始化ECharts实例
				const chartDom = document.getElementById('scoreChart');
				if (chartDom) {
					this.chart = echarts.init(chartDom);
					this.updateChart();
				}
			});
		},
		
		// 更新图表数据
		updateChart() {
			if (!this.chart || !this.selectedCourse) return;
			
			// 查找选中课程的数据
			const courseData = this.statisticsData.find(item => item.courseName === this.selectedCourse);
			if (!courseData) return;
			
			// 准备图表数据
			const option = {
				title: {
					text: `${this.selectedCourse} - 成绩分布统计`,
					left: 'center'
				},
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
				},
				xAxis: {
					type: 'category',
					data: ['0-59分', '60-69分', '70-79分', '80-89分', '90-100分'],
					axisLabel: {
						interval: 0
					}
				},
				yAxis: {
					type: 'value',
					name: '学生人数',
					nameLocation: 'end'
				},
				series: [
					{
						name: '学生人数',
						type: 'bar',
						data: [
							courseData.bucket_0_59,
							courseData.bucket_60_69,
							courseData.bucket_70_79,
							courseData.bucket_80_89,
							courseData.bucket_90_100
						],
						itemStyle: {
							color: function(params) {
								// 根据分数段设置不同颜色
								const colors = ['#FF4500', '#FF8C00', '#FFD700', '#90EE90', '#32CD32'];
								return colors[params.dataIndex];
							}
						},
						label: {
							show: true,
							position: 'top',
							formatter: '{c}人'
						}
					}
				]
			};
			
			// 设置图表选项并渲染
			this.chart.setOption(option);
		},

		get_list_before(param) {
			var user_group = this.user.user_group;
			if (user_group != "管理员") {
				let sqlwhere = "(";
				if (user_group == "教师用户") {
					sqlwhere += "`course_teacher` = " + this.user.user_id + " or ";
				}
				if (sqlwhere.length > 1) {
					sqlwhere = sqlwhere.substr(0, sqlwhere.length - 4);
					sqlwhere += ")";
					param["sqlwhere"] = sqlwhere;
				}
			}
			return param;
		},


		open_tip() {
			const h = this.$createElement;

			var message = "";
			var list = this.list;

			var ifs = [
			];
			for (var n = 0; n < ifs.length; n++) {
				var o = ifs[n];
				for (var i = 0; i < list.length; i++) {
					var lt = list[i];
					if (o.type == "数内") {
						if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
							if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.start || o.start === 0) {
							if (lt[o.factor] > o.start) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.end || o.end === 0) {
							if (lt[o.factor] < o.end) {
								o["idx"] = o["idx"] + 1;
							}
						}
					} else if (o.type == "数外") {
						if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
							if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.start || o.start === 0) {
							if (lt[o.factor] < o.start) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.end || o.end === 0) {
							if (lt[o.factor] > o.end) {
								o["idx"] = o["idx"] + 1;
							}
						}
					} else if (o.type == "日内") {
						if ((o.start) && (o.end)) {
							if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.start) {
							if (lt[o.factor] < o.start) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.end) {
							if (lt[o.factor] > o.end) {
								o["idx"] = o["idx"] + 1;
							}
						}
					} else if (o.type == "日外") {
						if (o.start && o.end) {
							if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.start) {
							if (lt[o.factor] < o.start) {
								o["idx"] = o["idx"] + 1;
							}
						} else if (o.end) {
							if (lt[o.factor] > o.end) {
								o["idx"] = o["idx"] + 1;
							}
						}
					}
				}

				if (o["idx"]) {
					message += o.title;
					if (o["type"] == "数内") {
						if (o.start || o.start === 0) {
							message += "大于" + o.start;
						}
						if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
							message += "并且";
						}
						if (o.end || o.end === 0) {
							message += "小于" + o.end;
						}
					} else if (o["type"] == "数外") {
						if (o.start || o.start === 0) {
							message += "小于" + o.start;
						}
						if ((o.start || o.start === 0) || (o.end || o.end === 0)) {
							message += "或者";
						}
						if (o.end || o.end === 0) {
							message += "大于" + o.end;
						}
					} else if (o["type"] == "日内") {
						if (o.start) {
							message += "在" + o.start + "之后";
						}
						if (o.start && o.end) {
							message += "并且";
						}
						if (o.end) {
							message += "在" + o.end + "之前";
						}
					} else if (o["type"] == "日外") {
						if (o.start) {
							message += "在" + o.start + "之前";
						}
						if (o.start || o.end) {
							message += "或者";
						}
						if (o.end) {
							message += "在" + o.end + "之后";
						}
					}
					message += "的有" + o["idx"] + "条";
				}
			}

			if (message) {
				message += "，需要处理、请尽快处理。";
				this.showModal = true;
				// this.$notify({
				// 	title: '提醒',
				// 	dangerouslyUseHTMLString: true,
				// 	message: h('i', {
				// 		style: 'color: teal'
				// 	}, message)
				// });
			}
		},




		/**
* 获取教师用户用户列表
*/
		async get_list_user_course_teacher() {
			var json = await axios.get("~/api/user/get_list?user_group=教师用户");
			if (json.result && json.result.list) {
				this.list_user_course_teacher = json.result.list;
			}
			else if (json.error) {
				console.error(json.error);
			}
		},

		get_user_course_teacher(id) {
			var obj = this.list_user_course_teacher.getObj({ "user_id": id });
			var ret = "";
			if (obj) {
				ret = obj.nickname + "-" + obj.username;
				// if(obj.nickname){
				// 	ret = obj.nickname;
				// }
				// else{
				// 	ret = obj.username;
				// }
			}
			return ret;
		},
		deleteRow(index, rows) {
			rows.splice(index, 1);
		}

	},
	created() {
		this.getCourseScoreApi();
		// this.get_list_user_course_teacher();
		setTimeout(() => {
			this.open_tip();
		}, 1000)
		
		// 计算课程成绩统计数据
		setTimeout(() => {
			this.calculateCourseScoreStatistics();
		}, 1500);
	},
	mounted() {
		// 初始化图表
		this.initChart();
		
		// 监听窗口大小变化，重新调整图表大小
		window.addEventListener('resize', () => {
			if (this.chart) {
				this.chart.resize();
			}
		});
	},
	beforeDestroy() {
		// 销毁图表实例，避免内存泄漏
		if (this.chart) {
			this.chart.dispose();
			this.chart = null;
		}
		
		// 移除窗口大小变化监听
		window.removeEventListener('resize', () => {
			if (this.chart) {
				this.chart.resize();
			}
		});
	}
}
</script>

<style type="text/css">
.bg {
	background: white;
}

.form.p_4 {
	padding: 1rem;
}

.form .el-input {
	width: initial;
}

.mt {
	margin-top: 1rem;
}

.text_center {
	text-align: center;
}

.float-right {
	float: right;
}

/* 成绩统计图表样式 */
.statistics-container {
	background-color: white;
	padding: 20px;
	border-radius: 4px;
	box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.statistics-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.statistics-header h3 {
	margin: 0;
	font-size: 18px;
	color: #303133;
}

.chart-container {
	height: 400px;
	width: 100%;
}

.modal_wrap {
	width: 100vw;
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	background: rgba(0, 0, 0, 0.5);
	z-index: 9999999999;
}

.modal_wrap .modal_box {
	width: 400px;
	height: 200px;
	background: url("../../assets/modal_bg.jpg") no-repeat center;
	background-size: cover;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-left: -200px;
	margin-top: -100px;
	border-radius: 10px;
}

.modal_wrap .modal_box .modal_box_close {
	font-size: 20px;
	position: absolute;
	top: 10px;
	right: 10px;
	cursor: pointer;
}

.modal_wrap .modal_box .modal_box_title {
	text-align: center;
	font-size: 18px;
	margin: 16px auto;
	color: #fff;
	border-bottom: 1px solid rgba(117, 116, 116, 0.5);
	padding-bottom: 16px;
	width: 356px;
}

.modal_wrap .modal_box .modal_box_text {
	text-align: center;
	font-size: 15px;
	color: #fff;
	margin-top: 25px;
}

.modal_wrap .modal_box .btn_box {
	display: flex;
	flex-direction: row;
	justify-content: center;
	margin-top: 42px;
}

.modal_wrap .modal_box .btn_box span {
	display: inline-block;
	width: 80px;
	height: 30px;
	line-height: 30px;
	text-align: center;
	border: 1px solid #ccc;
	font-size: 14px;
	cursor: pointer;
	color: #fff;
}

.modal_wrap .modal_box .btn_box span:nth-child(2) {
	background: #409EFF;
	color: #fff;
	border-color: #409EFF;
	margin-left: 15px;
}
</style>
