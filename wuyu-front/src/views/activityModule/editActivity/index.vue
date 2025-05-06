<template>
  <div>
    <div class="components-container board" style="margin-left: 90px">
      <DragTable :key="1"
                :list="list1"
                :group="group"
                @handleToStart="handleToStart"
                class="kanban todo"
                header-text="待开始"
                handlerName="handleToStart" />
      <DragTable :key="2"
                :list="list2"
                :group="group"
                class="kanban working"
                header-text="待评分"
                @handleToHaveScore="handleToHaveScore"
                handlerName="handleToHaveScore"/>
      <DragTable :key="3"
                :list="list3"
                :group="group"
                class="kanban done"
                header-text="已完成"
                @handleToFinish="handleToFinish"
                handlerName="handleToFinish" />
    </div>
    <!-- 编辑表单 -->
    <el-dialog title="请编辑信息" :visible.sync="dialogFormVisible">
        <el-form :model="form">
            <el-form-item label="活动名称"  :label-width="formLabelWidth">
                <el-input v-model="form.activityName" disabled></el-input>
            </el-form-item>
            <el-form-item label="活动类型" :label-width="formLabelWidth">
                <el-input v-model="form.activityType" disabled></el-input>
            </el-form-item>
            <template v-if="upPhoto">
            <el-form-item label="上传照片" :label-width="formLabelWidth">
              <el-upload
                action="http://36.111.68.174:33380/api/file/upload"
                list-type="picture-card"
                name="activityPicture"
                :data="currentActivityId"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove">
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
            </template>
            <template v-if="toScore">
              <el-form-item label="活动评语" :label-width="formLabelWidth">
                <el-input v-model="form.activityComment" type="textarea" :rows="2" ></el-input>
              </el-form-item>
              <el-form-item label="活动打分" :label-width="formLabelWidth">
                  <el-input v-model="form.activityScore" ></el-input>
              </el-form-item>
              <el-form-item label="评分模式" :label-width="formLabelWidth">
                  <el-radio-group v-model="scoreMode">
                    <el-radio :label="1">总体打分</el-radio>
                    <el-radio :label="2">模版打分</el-radio>
                  </el-radio-group>
              </el-form-item>
              <template v-if="scoreMode == 1">
                <el-form-item label="学生平均分" :label-width="formLabelWidth">
                  <el-rate
                    v-model="agerageScore"
                    show-text
                    :texts="['60-整体一般','70-活动达标','80-整体良好','90-活动圆满','100-整体优秀']">
                  </el-rate>
                </el-form-item>
              </template>
              <template v-if="scoreMode == 2">
                <el-form-item label="模版下载与上传" :label-width="formLabelWidth">
                  <el-button :loading="downloadLoading" type="info" icon="el-icon-download" @click="handleModule">
                    下载学生分数模版
                  </el-button>
                  <el-upload
                      class="filter-item"
                      ref="upload"
                      accept=".xls,.xlsx"
                      action="http://36.111.68.174:33380/api/uploadStudentScore"
                      :before-upload="beforeUpload"
                      name="file"
                      :on-change="fileChange">
                      <el-button icon="el-icon-upload" type="warning">导入</el-button>
                  </el-upload>
                </el-form-item>
              </template>


            </template>

        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleSure">确 定</el-button>
        </div>
    </el-dialog>
  </div>

</template>
<script>
import DragTable from '@/components/DragTable'
import { getActivityList, editActivity } from '@/api/activityModule/activity';
import { getUploadScoreTemplate, scoreAllStudent } from '@/api/activityModule/phaseTwo';
export default {
  name: 'DragKanbanDemo',
  components: {
    DragTable
  },
  data() {
    return {
      dialogFormVisible: false, // 编辑对话框是否显示
      dialogImageUrl: '',
      dialogVisible: false,
      downloadLoading: false,
      scoreMode: 0,
      agerageScore: 0,//学生平均分
      formLabelWidth: '120px',
      toScore: false,
      upPhoto: false,
      currentActivityId: {},
      form: {
          questionId: '',
          description: '',
          analysis: '',
          correctOption: '',
          choices: []
      }, // 取到移动的对象
      group: 'mission',
      currentActivityIndex: null,
      list1: [
        { name: '活动名称', id: 1 },
        { name: '活动名称', id: 2 },
        { name: '活动名称', id: 3 },
        { name: '活动名称', id: 4 }
      ],
      list2: [
        { name: '活动名称', id: 5 },
        { name: '活动名称', id: 6 },
        { name: '活动名称', id: 7 }
      ],
      list3: [
        { name: '活动名称', id: 8 },
        { name: '活动名称', id: 9 },
        { name: '活动名称', id: 10 }
      ]
    }
  },
  created(){
    this.getInitList();
  },

  methods: {
    getInitList(){
      getActivityList({activityState: 0, pageSize: 99999, pageNum: 1}).then(res => {
        this.list1 = res.data.list.map(item => {
          return {
            name: item.activityName,
            ...item
          }
        });
      });
      getActivityList({activityState: 1, pageSize: 99999, pageNum: 1}).then(res => {
        this.list2 = res.data.list.map(item => {
          return {
            name: item.activityName,
            ...item
          }
        });
      });
      getActivityList({activityState: 2, pageSize: 99999, pageNum: 1}).then(res => {
        this.list3 = res.data.list.map(item => {
          return {
            name: item.activityName,
            ...item
          }
        });
      })
    },
    handleModule(){
      getUploadScoreTemplate({activityId: this.form.activityId});
    },
    // 上传
    beforeUpload(file) {
        //文件类型
        const isType = file.type === 'application/vnd.ms-excel'
        const isTypeComputer = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        const fileType = isType || isTypeComputer
        if(!fileType) {
            this.$message.error('上传文件只能是xls/xlsx格式！')
        }

        // 文件大小限制为10M
        const fileLimit = file.size / 1024 / 1024 < 10;
        if(!fileLimit) {
            this.$message.error('上传文件大小不超过10M！')
        }
        return fileType && fileLimit
    },
    // 文件更改
    fileChange(file, fileList){
      console.log(file);
    },
    handleToStart(value){
      this.dialogFormVisible = true;
      this.form = this.list1[value];
      console.log(111, this.form);
    },
    handleToHaveScore(value){
      this.dialogFormVisible = true;
      this.toScore = false;
      this.currentActivityId.activityId = this.list2[value].activityId;
      this.form = this.list2[value];
      console.log(this.form);
      this.form.activityState = 1;
      this.upPhoto = true;
    },
    handleToFinish(value){
      this.dialogFormVisible = true;
      this.upPhoto = false;
      this.currentActivityIndex = value;
      this.toScore = true;
      this.form = this.list3[value];
      this.form.activityState = 2;
      console.log(333, this.list3[value]);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleCancel(){
      this.dialogFormVisible = false;
      // 重新请求 将所有数组重置
      this.getInitList();
    },
    handleSure(){
      editActivity(this.form).then(res => {
        if(this.form.activityState == 1){
          this.$message({
            message: '活动照片已上传，可以开始评价啦',
            type: 'success'
          });
        }else if (this.form.activityState == 2){
          this.$message({
            message: '活动完成！',
            type: 'success'
          });
        }
      })
      if(this.scoreMode == 1){
        scoreAllStudent({
          activityId: this.form.activityId,
          score: 50 + this.agerageScore * 10,
        }).then(res => {
          this.$message({
            message: '平均分已录入，个别学生分数修改请到详情页！',
            type: 'success'
          });
        })
      }
      this.dialogFormVisible = false;
    }
  }
}
</script>
<style lang="scss">
.filter-item{
  float: left;
  margin-right: 40px;
}
.board {
  width: 1000px;
  margin-left: 20px;
  display: flex;
  justify-content: space-around;
  flex-direction: row;
  align-items: flex-start;
}
.kanban {
  &.todo {
    .board-column-header {
      background: #4A9FF9;
    }
  }
  &.working {
    .board-column-header {
      background: #f9944a;
    }
  }
  &.done {
    .board-column-header {
      background: #2ac06d;
    }
  }
}
</style>

