<template>
    <div>
        <div class="search">
            <span style="margin-left: 30px;">学生ID：</span>
            <el-input v-model.number="sid" placeholder="请输入学生学号" style="width:20%" clearable @clear="handleClear"></el-input>
            <el-button class="searchbtn" type="primary" icon="el-icon-search" @click="getStudent">查询</el-button>
            <el-button class="exportbtn" type="success" icon="el-icon-download" @click="exportToPDF">导出为PDF</el-button>

        </div>
        <el-row >
            <el-table 
                :data="studensList"
                style="width: 100%">
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
                <el-table-column
                    prop="virtue"
                    label="道德">
                </el-table-column>
                <el-table-column
                    prop="intelligence"
                    label="智力">
                </el-table-column>
                <el-table-column
                    prop="sports"
                    label="体育">
                </el-table-column>
                <el-table-column
                    prop="art"
                    label="美">
                </el-table-column>
                <el-table-column
                    prop="labor"
                    label="劳">
                </el-table-column>
            </el-table>
            <el-pagination 
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="total, prev, pager, next"
                :total="total">
            </el-pagination>
        </el-row>
        <div id="pdfContent">
            <el-row>
                <el-col :span="15">
                    <div v-if="currentPage > 1 || !isTeacher" id="main" style="height:500px;"></div>
                </el-col>
            </el-row>
            <el-row>
                <el-card v-if="this.data.id || this.data.length" class="box-card">
                    <div slot="header" class="clearfix">
                        <span>评价</span>
                    </div>
                    <div class="text item">
                    {{ this.data.id !== undefined ? data.remark.length > 0 ? data.remark : '暂无评价' : data }}
                    </div>
                </el-card>
            </el-row>
        </div>
    </div>
</template>

<script>
import * as echarts from "echarts"
import { Message } from "element-ui";
import request from "@/utils/re_request"
import html2pdf from 'html2pdf.js';

export default {
    name:'class',
    data(){
        return {
            tid:'',
            sid:'',
            pageSize: 10,
            currentPage: 1,//当前页
            total: 0,//总记录数
            // tid对应的教师信息
            teacher:{},
            // sid对应的学生信息
            student:{},
            // 5个成绩平均分对象
            avg:{},
            // 对应数据
            data:[],
            // 优势科目展示
            subjects:['virtue', 'intelligence', 'sports', 'art', 'labor'],
            // 优势科目名称
            subjectsName:['德育', '智育', '体育', '美育', '劳育'],
            // 是根据学生查询还是根据老师查询
            isTeacher:'',
            studensList:[]  //教师所教学生列表
        }
    },
    watch:{
        data:{
            handler() {
                if(this.data.id) {
                    this.showAverage()
                }
            },
            deep:true,
        }
    },
    methods:{
        exportToPDF() {
        const element = document.getElementById('pdfContent'); // 假设要导出的内容包含在id为pdfContent的元素内
        const opt = {
            margin: 1,
            filename: this.data.name + '.pdf',
            image: { type: 'jpeg', quality: 0.98 },
            html2canvas: { scale: 2 },
            jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
        };
        html2pdf().from(element).set(opt).save();
        },

        getTeacher() {
            if(!Number.isInteger(this.tid)) {
                Message.error('请输入数字值！')
                return
            }else {
                this.isTeacher = true;
                this.currentPage = 1
                request.get(`/teacher/get?teacherID=${this.tid}`).then(
                    response => {
                        if(response.code === 600) {
                            this.teacher = response.data
                            // 查询对应教师的平均分对象
                            this.getAverage(this.tid)
                            // 查询评语册
                            this.getEvaluationBook()

                            Message.success(response.msg)
                        } else {
                            Message.error(response.msg)
                        }
                    }
                ).catch (
                    test=>{
                        Message.error('查询失败')
                    }
                )
            }
        },
        getStudent() {
            if(!Number.isInteger(this.sid)) {
                Message.error('请输入数字值！')
                return
            }else if(this.sid.toString().length !== 10){
                Message.error('当前学号长度不对，应为10位数字！')
                return
            } else {
                this.isTeacher = false;
                request.get(`/performance/get?sid=${this.sid}`).then(
                    response => {
                        if(response.code === 600) {
                            this.data = response.data
                            console.log("平均分",this.data)
                            // 查询对应教师的平均分对象
                            this.getAverage(response.data.teacher.teacherID)
                            // 查询个人相关评价数据
                            this.getPersonalDetail()
                            // 重置currentPage
                            this.currentPage = 0
                            Message.success(response.msg)
                        } else {
                            Message.error(response.msg)
                        }
                    }
                )
            }
        },
        handleClear() {
            if(this.sid === '' && this.tid === '') {
                this.avg = {}
            }

            if(this.sid == '') {
                this.studnet = {}
            }

            if(this.tid == '') {
                this.teacher = {}
                this.currentPage = 1
            }
            this.isTeacher = ''
            this.data = {}
        },
        // 获取班级平均成绩
        getAverage(tid) {
            request.get(`/performance/avg?tid=${tid}`).then(
                response => {
                    if(response.code === 600) {
                        this.avg = response.data
                    }
                }
            )
        },
        // 获取班级的评语册
        getEvaluationBook() {
            request.get(`/remark/class?tid=${this.teacher.teacherID}&page=${this.currentPage}`).then(
                response => {
                    if(response.code === 600) {
                        this.data = response.data
                        this.total = response.count + 1
                    } else {
                        Message.error(response.msg)
                    }
                }
            )
        },
        // 获取个人的相关信息
        getPersonalDetail() {
            request.get(`/remark/get?sid=${this.sid}`).then(
                response => {
                    if(response.code === 600) {
                        this.data = response.data
                    } else {
                        Message.error(response.msg)
                    }
                }
            )
        },
        // 当前页变化
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
            request({
                url:`/performance/list/teacher/${this.teacher.id}`,
                method:'post',
                data:{pageSize:this.pageSize,currentPage:this.currentPage}
            }).then(
                response => {
                    if(response.code === 600) {
                        console.log("学生",response.data)
                        this.studensList = [...response.data]
                        this.total = response.count
                    } else {
                        Message.error(response.msg)
                    }
                }
            ).catch (
                test=>{
                    Message.error('查询失败')
                }
            )
            if(currentPage !== 1) {

                this.$nextTick(() => {
                    this.showAverage()
                });
            }
        },
        // 展示个人成绩与班级平均成绩
        showAverage() {
            console.log("hhhh");
            console.log(this.data)
            var myChart = echarts.init(document.getElementById('main'));
            if(myChart !== undefined) {
                var option = {
            title: {
                text: this.data.name !== undefined ? this.data.name +'同学分数与平均分对比' : '',
                left:'center'
            },
            legend: {
                orient: 'vertical',
                data: ['五育分数', '五育平均分'],
                left:'75%',
            },
            radar: {
                // shape: 'circle',
                indicator: [
                { name: '德育', max: 100 },
                { name: '智育', max: 100 },
                { name: '体育', max: 100 },
                { name: '美育', max: 100 },
                { name: '劳育', max: 100 },
                ],
                center: ["50%", "56%"],
            },
            series: [
                {
                name: 'Budget vs spending',
                type: 'radar',
                data: [
                    {
                        value: [this.data.virtue,this.data.intelligence,this.data.sports,this.data.art,this.data.labor],
                        name: '五育分数',
                        itemStyle: {
                            normal: {
                                color: 'rgba(0,243,51,.8)',
                                lineStyle: {
                                    color: 'rgba(0,243,51,.8)',
                                }
                            },
                        },
                        label: {
                            normal: {
                                show: true,
                                formatter:function(params) {
                                    return params.value;
                                }
                            }
                        }
                    },
                    {
                        value: [this.avg.virtue/10,this.avg.intelligence/10,this.avg.sports/10,this.avg.art/10,this.avg.labor/10],
                        name: '五育平均分',
                        itemStyle: {
                            normal: {
                                color: 'rgba(220,20,60,.8)',
                                lineStyle: {
                                    color: 'rgba(220,20,60,.8)',
                                }
                            },
                        },
                        label: {
                            normal: {
                                show: true,
                                formatter:function(params) {
                                    return params.value;
                                }
                            }
                        }
                    }
                ]
                }
            ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option)
            }
        },
        exportToExcel(){
            if(this.tid === '') {
                // 没有指定导出的是哪一个班级
                Message.error("请先查询要导出班级的教师ID")
                return
            } else {
                // 获取初始数据
                request.get(`/remark/class?tid=${this.teacher.teacherID}&export=1`).then(
                    response => {
                        if(response.code === 600) {

                            const th = ['ID','学号', '姓名', '德育', '智育', '体育', '美育', '劳育', '评语', '总评成绩']
                            const filterVal = ['id', 'sid', 'name', 'virtue', 'intelligence', 'sports', 'art', 'labor', 'remark', 'totalScore']
                            var count = 0;
                            const data = response.data.map(v => {
                                    return filterVal.map(
                                        k => {
                                            if(k === 'tid') {
                                                return v.teacher.teacherID
                                            }

                                            if(k === 'id') {
                                                count = count + 1
                                                return count
                                            }
                                            return v[k]
                                        }
                                    )
                                }
                            )
                            if(response.data.length > 0) {
                                const teacherName = response.data[0].teacher.username
                                const [fileName, fileType, sheetName] = ['教师' + teacherName + '班级评语册', 'xlsx', 'sheet1']
                                this.$toExcel({th, data, fileName, fileType, sheetName})
                            } else {
                                Message.error("当前教师ID对应的班级无任何学生！")
                            }

                        } else {
                            Message.error(response.msg)
                        }
                    }
                )
            }
        },

    },
    mounted(){
        this.teacher = JSON.parse(localStorage.getItem("UserInfo"))
        request.get(`/performance/averageGrade/${this.teacher.id}`).then(
            response => {
                if(response.code === 600) {
                    console.log("平均成绩",response.data)
                    //'virtue', 'intelligence', 'sports', 'art', 'labor'
                    this.avg['virtue'] = response.data[0]
                    this.avg['intelligence'] = response.data[1]
                    this.avg['sports'] = response.data[2]
                    this.avg['art'] = response.data[3]
                    this.avg['labor'] = response.data[4]
                } else {
                    Message.error(response.msg)
                }
            }
        ).catch (
            test=>{
                Message.error('查询失败')
            }
        )

        request({
            url:`/performance/list/teacher/${this.teacher.id}`,
            method:'post',
            data:{pageSize:this.pageSize,currentPage:this.currentPage}
        }).then(
            response => {
                if(response.code === 600) {
                    console.log("学生",response.data)
                    this.studensList = [...response.data]
                    this.total = response.count
                } else {
                    Message.error(response.msg)
                }
            }
        ).catch (
            test=>{
                Message.error('查询失败')
            }
        )
    }
}
</script>

<style>

    .search{
        margin: 20px 50px 0 50px;
    }

    .searchbtn{
        margin-left: 10px;
    }

    .avg{
        margin: 20px 50px 0 50px;
        text-align: center;
        font-size: 20px;
    }

    .pagination {
        margin-left: 30%;
        font-size: 14px;
    }

    .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    margin: 20px 50px 20px 50px;
  }

</style>
