<template>
  <div class="container">
      <div  style="width: 50%;margin:40px auto">
        <el-form ref="form" :model="activityInfo" name="form" label-width="150px">

        <el-form-item label="活动名称:">
            <el-input v-model="activityInfo.activityName"></el-input>
        </el-form-item>

        <!-- 修改：遍历拿到的评价量化表数组 -->
        <el-form-item label="评价量表关联项目:">
          <el-select v-model="activityInfo.activityAspect" placeholder="请选择评价量表">
            <el-option v-for="scale in scaleList"
                       :label="scale.title"
                       :value="scale.scaleId"
                       :key="scale"/>
          </el-select>
        </el-form-item>

        <el-form-item label="活动年级:">
          <el-radio-group v-model="isGradeAll">
            <el-radio :label="true">全年级</el-radio>
            <el-radio :label="false">选定年级</el-radio>
          </el-radio-group>
          <!-- 选年级 -->
          <div>
            <el-select v-if="!isGradeAll" v-model="activityInfo.grade"  placeholder="活动年级" @change="handleGradeChange">
              <el-option v-for="item in gradeList"
                        :label="item.gradeName"
                        :value="item.id"
                        :key="item.id"/>
            </el-select>
          </div>
          <!-- 选班级 -->
          <div>
            <!-- 当选择了年级后，展示班级选择框 -->
            <el-select v-if="activityInfo.grade != null" v-model="activityInfo.classId" placeholder="请选择班级">
              <el-option v-for="item in classList" :key="item.classId" :label="item.className" :value="item.classId"/>
            </el-select>
          </div>
        </el-form-item>

        <el-form-item label="活动内容:">
          <el-input type="textarea"
                    :rows="10"
                    v-model="activityInfo.activityContent"/>
        </el-form-item>

          <!-- <el-form-item  label="活动评价方式:">
            <el-select v-model="activityInfo.evaluationMethod" placeholder="请选择">
              <el-option v-for="method in evaluationMethodList"
                         :label="method.content"
                         :value="method.content"/>
            </el-select>
          </el-form-item> -->

          <el-form-item label="活动开始时间:" >
            <el-date-picker
              type="date"
              v-model="activityInfo.startTimeDate"
              placeholder="请选择活动开始时间"/>
          </el-form-item>

          <el-form-item label="活动截至时间:" >
            <el-date-picker
              type="date"
              v-model="activityInfo.endTimeDate"
              placeholder="请选择活动截至时间"/>
          </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="onSubmit">创建活动</el-button>
        </el-form-item>

        </el-form>
      </div>
  </div>
</template>

<script>
import {getAllFuScale} from "@/api/fuScale";
import { getClassInfo } from '@/api/activityModule/common';
import { addAct } from "@/api/activityModule/activity";

export default {
    data(){
        return {
          // 年级列表
          gradeList: [
            {id: 1, gradeName:'一年级'},
            {id: 2, gradeName:'二年级'},
            {id: 3, gradeName:'三年级'},
            {id: 4, gradeName:'四年级'},
            {id: 5, gradeName:'五年级'},
            {id: 6, gradeName:'六年级'},
          ],
          // 判断是否为全年级
          isGradeAll: true,
          // 选择的年级
          classList: [],
          // 年级选择框是否显示
          // gradeSelectorShow: false,
          // 评价量表表头列表
          scaleList: [],
          // 评价量表维度列表
          dimensionList: [],
          // 评价量表内容列表
          contentList: [],
          // // 评价方式列表
          // evaluationMethodList: [],
          // 活动信息
          activityInfo: {
            activityNum: null,
            activityName: '',
            activityAspect: '',
            grade: null,
            classId: '',
            // dimensionCorrelated: '',
            // itemCorrelated: '',
            activityContent: '',
            evaluationMethod: '',
            startTimeDate: null,
            endTimeDate: null,
            classIdList: [],
            activityPlace: "学校",
            teacherId: "2018083085",
            withParent: "0",
            shouldCarryStuff: '1',
            gameContent: '',
            wearingLimit: '1'
          },
        }
    },
    created(){
      getAllFuScale().then(res => {
        this.scaleList = res.data;
        console.log(this.scaleList);
        
      })
    },
    methods: {
      allOrNot (data) {
        console.log(data);
      },

      onSubmit(){
        console.log("onSubmit");
        
        addAct(this.activityInfo);
      },

      // 处理年级变化，更新班级列表
      handleGradeChange() {
        console.log("处理年级变化，更新班级列表");
        
        // 选择年级时，清空班级选择
        this.classList = null; // 重置班级选择框
        // 这里你可以根据选择的年级从服务器或者本地数据更新班级列表
        if (this.activityInfo.grade) {
          // 假设通过选择的年级ID来过滤班级列表
          getClassInfo(this.activityInfo.grade).then(res => {
            this.classList = res.data.classList;//班级下拉列表
          })
        } else {
          // 如果没有选择年级，清空班级列表
          this.classList = [];
        }
      },
    }

}
</script>

<style lang="scss" scoped>
    ::v-deep .el-step__icon{
        width: 50px;
        height: 50px;
    }
    ::v-deep .el-step.is-horizontal .el-step__line{
        top: 25px;
    }
    .controller{
        margin-top: 40px;
    }
    .choosePeopleType{
        margin-top: 40px;
        span{
            font-size: 20px;
            margin-right: 20px;
        }
    }
    .buttons{
        position: fixed;  //将button按钮固定在页面底部，注意，：和；是英文的哦，一定不要写成中文哦。
        bottom: 50px;
        right: 50px;
    }


</style>
