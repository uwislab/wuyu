<template>
    <div class="app-container" style="dispaly: flex;" >
        <el-container>
            <el-container>
                <el-aside width="200px">
                    <div style="height: 500px; margin-left: 40px;" class="control">
                        <el-steps direction="vertical" :active="currentStep">
                            <el-step title="请填入目标服务器"></el-step>
                            <el-step title="开始部署"></el-step>
                        </el-steps>
                    </div>
                </el-aside>
                <el-main>
                    <div class="display">
                        <el-card class="box-card" v-show="currentStep == 1">
                            <div slot="header" class="clearfix">
                                <span style="cursor: pointer;" @click="handleClick(1)">远端一键部署项目</span>
                                <span style="margin-left: 20px; cursor: pointer;" @click="handleClick(2)">后端本地备份数据库</span>
                                <span style="margin-left: 20px; cursor: pointer;" @click="handleClick(3)">远端备份数据库</span>
                                <span style="margin-left: 20px; cursor: pointer;" @click="handleClick(4)">一键还原本地数据库备份</span>
                                <el-button v-show="panel == 1" style="float: right; padding: 3px 0" type="text" @click="handleDeploy">开始部署</el-button>
                                <el-button v-show="panel == 2 || panel == 3" style="float: right; padding: 3px 0" type="text" @click="handleBackup">开始备份</el-button>
                                <el-button v-show="panel == 4" style="float: right; padding: 3px 0" type="text" @click="handleReduction">开始还原</el-button>
                            </div>
                            <div v-show="panel == 1">
                                <el-form label-position="left" label-width="80px" :model="form">
                                    <el-form-item label="服务器IP地址" :label-width="formLabelWidth">
                                        <el-input v-model="form.remoteIP" ></el-input>
                                    </el-form-item>
                                    <el-form-item label="服务器用户名" :label-width="formLabelWidth">
                                        <el-input v-model="form.username"></el-input>
                                    </el-form-item>
                                    <el-form-item label="服务器密码" :label-width="formLabelWidth">
                                        <el-input v-model="form.password"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <div v-show="panel == 3">
                                <el-form label-position="left" label-width="80px" :model="form">
                                    <el-form-item label="另一台服务器IP" :label-width="formLabelWidth">
                                        <el-input v-model="form.remoteIP" ></el-input>
                                    </el-form-item>
                                    <el-form-item label="服务器用户名" :label-width="formLabelWidth">
                                        <el-input v-model="form.username"></el-input>
                                    </el-form-item>
                                    <el-form-item label="服务器密码" :label-width="formLabelWidth">
                                        <el-input v-model="form.password"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-card>
                        <el-card class="box-card" v-show="currentStep == 2">
                            <div slot="header" class="clearfix">
                                <span>部署进度</span>
                            </div>
                            <el-progress :text-inside="true" :stroke-width="24" :percentage="percent" status="success"></el-progress>
                        </el-card>
                    </div>
                </el-main>
            </el-container>
        </el-container>
    </div>
    
</template>

<script>
import { startDeploy, getTaskStatus, startBackup, recoverDataBase } from '@/api/managementModule/deploy';
export default {
    data(){
        return {
            form: {},
            formLabelWidth: '120px',
            currentStep: 1,
            percent: 0,
            isStart: false,
            taskId: "",
            panel: 1,
        }
    },
    watch: {
        isStart(){
        if(this.isStart){
            let timer = setInterval(()=>{
                getTaskStatus({taskId: this.taskId}).then(res => {
                    if(res.data.status == 'STARTED' || res.data.status == 'RUNNING'){
                        if(this.percent < 90){
                            this.percent += 5;
                        }
                    }
                    if(res.data.status == 'SUCCESS'){
                        this.percent = 100;
                        clearInterval(timer); // 清除定时器
                        this.$message({
                            message: '部署成功',
                            type: 'success'
                        });
                        this.currentStep = 1;
                        this.isStart = false;
                        this.percent = 0;
                    }
                    if(res.data.status == 'FAILED'){
                        clearInterval(timer); // 清除定时器
                        this.$message({
                            message: '部署失败，请重新部署!',
                            type: 'error'
                        });
                        this.currentStep = 1;
                        this.isStart = false;
                        this.percent = 0;
                    }
                })
            },1000)
        }
    }
    },
    methods: {
        handleDeploy(){
            startDeploy(this.form).then(res => {
                if(res.data.taskInfo?.taskId){
                    this.taskId = res.data.taskInfo.taskId;
                    this.$message({
                        message: '开始部署',
                        type: 'success'
                    });
                    this.currentStep = 2;
                    this.isStart = true;
                }else{
                    this.$notify({
                        title: '提示',
                        message: '请检查IP，用户名和密码是否正确',
                        duration: 0
                    });
                }
            })
        },
        handleClick(option){
            this.panel = option;
        },
        handleBackup(){
            if(this.panel == 2){
                startBackup({localBackup: 1}).then(res => {
                    if(res.data.taskInfo?.taskId){
                        this.taskId = res.data.taskInfo.taskId;
                        this.$message({
                            message: '开始部署',
                            type: 'success'
                        });
                        this.currentStep = 2;
                        this.isStart = true;
                        this.downloadFile(res.data.backupFile);
                    }
                });
            }
            if(this.panel == 3){
                const payload = {
                    ...this.form,
                    localBackup: 0
                }
                startBackup(payload).then(res => {
                    if(res.data.taskInfo?.taskId){
                        this.taskId = res.data.taskInfo.taskId;
                        this.$message({
                            message: '开始部署',
                            type: 'success'
                        });
                        this.currentStep = 2;
                        this.isStart = true;
                    }
                    this.form = {};
                });
            }
        },
        downloadFile(fileData) {
        // 创建一个新的 Blob 对象，用于表示二进制数据
        const blob = new Blob([fileData]);

        // 创建一个 <a> 标签
        const link = document.createElement('a');

        // 将 <a> 标签的下载属性设置为要下载的文件名
        link.download = 'backup.zip';

        // 将 <a> 标签的 href 属性设置为 Blob 对象的 URL
        link.href = window.URL.createObjectURL(blob);

        // 将 <a> 标签添加到文档中
        document.body.appendChild(link);

        // 模拟点击 <a> 标签以下载文件
        link.click();

        // 移除 <a> 标签
        document.body.removeChild(link);
    },
        handleReduction(){
            recoverDataBase().then(res => {
                if(res.data.taskInfo?.taskId){
                    this.taskId = res.data.taskInfo.taskId;
                    this.$message({
                        message: '开始还原',
                        type: 'success'
                    });
                    this.currentStep = 2;
                    this.isStart = true;
                }
            });
        }
    }
}
</script>

<style scoped>
    .el-aside {
        background-color: transparent;
        color: #333;
        text-align: center;
    }
    ::v-deep .el-step.is-vertical .el-step__line {
        width: 2px;
        top: 16px;
        bottom: -7px;
        left: 11px;
    }
</style>