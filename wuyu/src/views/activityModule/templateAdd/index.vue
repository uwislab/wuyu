<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span style="font-size:30px">模版选择</span>
            </div>
            <el-card v-for="item in templateList" :key="item.templateId" class="text item" shadow="hover" @click.native="handleClick(item.templateId)">
                {{ item.activityName }}
                <el-tag
                    type="success"
                    effect="dark"
                    style="float: right;
                            display: inline-block;
                            width: 60px;
                            height: 30px;
                            text-align: center;
                            margin-top: -3px;
                            font-size: 16px;"
                    disable-transitions>{{ item.activityType }}</el-tag>
            </el-card>
        </el-card>
        <el-drawer
            title="活动信息"
            size="40%"
            :before-close="handleClose"
            :visible.sync="createVisible"
            direction="rtl"
            custom-class="demo-drawer"
            ref="drawer"
            >
            <div class="demo-drawer__content" style="padding:5px">
                <el-form :model="form">
                    <el-form-item v-for="(value,key,index) in commonObj" :key="index" :label="value.label" :label-width="formLabelWidth">
                        <el-input :value="value.content" disabled width="200px"></el-input>
                    </el-form-item>
                    <el-form-item label="带队老师" :label-width="formLabelWidth">
                        <el-select v-model="form.teacherId" filterable placeholder="请选择">
                            <el-option
                                v-for="item in teacherMapping"
                                :key="item.teacherId"
                                :label="item.teacherName"
                                :value="item.teacherId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="活动时间" :label-width="formLabelWidth">
                        <el-col :span="11">
                            <el-date-picker
                                v-model="activityTime"
                                type="datetime"
                                placeholder="选择日期时间">
                            </el-date-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="活动年级" :label-width="formLabelWidth">
                        <el-select v-model="form.grade" placeholder="请选择活动年纪">
                            <el-option label="一年级" value="一年级"></el-option>
                            <el-option label="二年级" value="二年级"></el-option>
                            <el-option label="三年级" value="三年级"></el-option>
                            <el-option label="四年级" value="四年级"></el-option>
                            <el-option label="五年级" value="五年级"></el-option>
                            <el-option label="六年级" value="六年级"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div class="demo-drawer__footer" style=" margin-left: 40%">
                    <el-button @click="cancelForm">取 消</el-button>
                    <el-button type="primary" @click="handleSubmit" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
                </div>
            </div>
        </el-drawer>
    </div>
</template>

<script>
import { getBaseTemplate, getTemplateDetail } from '@/api/activityModule/phaseTwo';
import { getTeacherInfo } from '@/api/activityModule/common';
import { addAct } from '@/api/activityModule/activity';
  export default {
    data() {
      return {
          templateList: [],
          formLabelWidth: '120px',
          createVisible: false,
          form: {},
          loading: false,
          commonObj: {},
          teacherMapping: [],
          activityTime: '',
      }
    },
    created(){
        getBaseTemplate().then(res => {
            this.templateList = res.data.list;
        });
        getTeacherInfo().then(res => {
            this.teacherMapping = res.data.teacherList;
        })
    },
    methods: {
        handleClick(templateId){
            getTemplateDetail({templateId: templateId}).then(res => {
                this.commonObj = res.data;
                delete this.commonObj.grade;
                delete this.commonObj.teacherId;
                delete this.commonObj.startTime;
                console.log(this.commonObj)
            })
            this.createVisible = true;
        },
        cancelForm(){
            this.createVisible = false;
            this.$message({
                message: '取消创建活动',
                type: 'warning'
            });
        },
        handleSubmit(){
            const commonData = {}
            for(let item in this.commonObj){
                commonData[item] = this.commonObj[item].content;
            }
            const payload = {
                ...commonData,
                ...this.form,
                startTime: this.activityTime?.getTime(),
            }
            addAct(payload).then(res => {
                this.$message({
                    message: '新增活动成功！',
                    type: 'success'
                });
                this.createVisible = false;
            })
        },
        handleClose(){
            this.createVisible = false;
        }
    }
  };
</script>

<style>
    .text {
        font-size: 20px;
    }

    .item {
        margin-bottom: 20px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    /* .box-card {
        width: 480px;
    } */
</style>