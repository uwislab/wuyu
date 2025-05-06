<template>
  <div class="app-container">
      <!-- 进度条 -->
      <div class="controlScoll">
        <el-steps :active="activityInfo.activityState + 1" finish-status="success" simple style="margin-top: 20px">
          <el-step title="待开始" ></el-step>
          <el-step title="待评分" ></el-step>
          <el-step title="已完成" ></el-step>
        </el-steps>
      </div>
      

      <!-- 基础信息 活动详情 -->
      <div class="infoDisplay" style="margin-top: 40px">
        <el-tabs type="border-card">
          <el-tab-pane label="基础信息">
            <div>
              <el-descriptions title="活动详情">
                      <el-descriptions-item label="活动名称">{{ activityInfo.activityName }}</el-descriptions-item>
                      <el-descriptions-item label="活动Id">{{ activityInfo.activityId }}</el-descriptions-item>
                      <el-descriptions-item label="活动类型">
                        <el-tag size="small">{{ activityInfo.activityType == 0 ? "班级活动" : "年级活动" }}</el-tag>
                      </el-descriptions-item>
                      <el-descriptions-item label="参与者">{{ activityInfo.classNameList ? activityInfo.classNameList.toString() : activityInfo.grade }}</el-descriptions-item>
                      <el-descriptions-item label="活动内容">{{ activityInfo.activityContent }}</el-descriptions-item>
                      <el-descriptions-item label="活动地点">{{ activityInfo.activityPlace }}</el-descriptions-item>
                      <el-descriptions-item label="带队老师">
                        <el-tag size="small" type="success">{{ activityInfo.teacherName }}</el-tag>
                      </el-descriptions-item>
                  </el-descriptions>
            </div>
          </el-tab-pane>

          <el-tab-pane label="活动照片">
            <div style="margin-left: 40px">
              <el-row>
                <el-col :span="4" v-for="item in activityInfo.activityPictureList" :key="item" style="margin-left:20px">
                  <el-card :body-style="{ padding: '0px' }">
                    <img :src="item" class="image">
                  </el-card>
                </el-col>
              </el-row>
            </div>
          </el-tab-pane>
          
          <el-tab-pane label="活动评价">
            <div>
              <el-descriptions title="教师点评">
                <el-descriptions-item label="活动分数">
                  <el-tag size="small" type="success">{{ activityInfo.activityScore }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="教师评语">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4}"
                    placeholder="请输入内容"
                    disabled
                    v-model="teacherComment">
                  </el-input>
                </el-descriptions-item>
              </el-descriptions>
                <el-table 
                
                          :data="studentScoreList"
                          border
                          fit
                          highlight-current-row 
                          style="width: 100%">
                  <el-table-column align="center" label="姓名" width="180px">
                    <template slot-scope="{row}">
                      <span>{{ row.studentName }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column width="180px" align="center" label="学号">
                    <template slot-scope="{row}">
                      <span>{{ row.studentNum }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column width="120px" align="center" label="班级">
                    <template slot-scope="{row}">
                      <span>{{ row.grade+row.className }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column width="80px" align="center" label="性别">
                    <template slot-scope="{row}">
                      <span>{{ row.gender === 1 ? "男" : "女" }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column class-name="status-col" align="center" label="家长电话" width="180px">
                    <template slot-scope="{row}">
                      <span>{{ row.parentPhoneNum }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column class-name="status-col" align="center" label="班主任" width="110">
                    <template slot-scope="{row}">
                      <span>{{ row.monitorName }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="活动分数" align="center"> 
                    <template slot-scope="{row}">
                      <template v-if="edit">
                        <el-input v-model="row.activityScore" class="edit-input" size="small" />
                        <el-button
                          class="cancel-btn"
                          size="small"
                          icon="el-icon-refresh"
                          type="warning"
                          @click="cancelEdit(row)"
                        >
                          取消
                        </el-button>
                      </template>
                      <span v-else>{{ row.activityScore }}</span>
                    </template>
                  </el-table-column>

                  <el-table-column align="center" label="编辑分数" width="180">
                    <template slot="header">
                      <el-input
                        v-model="search"
                        size="mini"
                        placeholder="输入学生姓名搜索"/>
                    </template>
                    <template slot-scope="{row}">
                      <el-button
                        v-if="edit"
                        type="success"
                        size="small"
                        icon="el-icon-circle-check-outline"
                        @click="confirmEdit(row)"
                      >
                        完成
                      </el-button>
                      <el-button
                        v-else
                        type="primary"
                        size="small"
                        icon="el-icon-edit"
                        @click="edit=!edit"
                      >
                        编辑
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
  </div>
</template>

<script>
import { getActivityDetail } from "@/api/activityModule/activity";
import { insertScoreSingleStudent } from "@/api/activityModule/phaseTwo";
export default {
  data(){
    return {
      activityInfo: {
        activityName: "",
        activityId: "",
        classNameList: [],
        activityContent: "",
        teacherId: "",
        teacherName: "",
        activityPlace: "",
        activityState: "",
        activityType: "",
        activityAspect: "",
        activityScore: "",
        activityPictureList: [],
        teacherComment: "",
        grade: "",
        studentScoreList: []  //.filter(data => !search || data.studentName.toLowerCase().includes(search.toLowerCase()))
      },
      teacherComment: '快去评论!!',
      studentScoreList: [],
      edit: false,
      search: '',
    }
  },
  created(){
    this.getList();
  },
  methods: {
    confirmEdit(row){

      const payload = {
        activityId: row.activityId,  //活动ID
        studentNum: row.studentNum,    //学生学号
        score: row.activityScore,    //点击修改拿取 活动分数
      }

      insertScoreSingleStudent(payload).then(res => {
        this.edit = false;
        this.$message({
          message: '修改成功',
          type: 'success'
        })
        this.getList();
      })
    },

    cancelEdit() {
      this.edit = false
      this.$message({
        message: '取消修改',
        type: 'warning'
      });
      this.getList();
    },

    getList(){
      const activityId = this.$route.query.activityId;
      getActivityDetail({activityId: activityId}).then(res =>{
        this.activityInfo = res.data;
        this.studentScoreList = res.data.studentScoreList;
         console.log(this.studentScoreList)
        if(res.data.teacherComment){
          this.teacherComment = res.data.teacherComment
        }
      })
    }

  }

}
</script>

<style lang="scss" scoped>
  .image {
    width: 100%;
    display: block;
  }
</style>