<template>
  <div class="container">

    <!-- 活动照片轮播图 -->
    <el-carousel :interval="4000" type="card" height="250px">
        <el-carousel-item v-for="item in picList" :key="item">
            <img :src="item" width="100%" height="100%"/>
        </el-carousel-item>
    </el-carousel>

    <!-- 活动仪表盘 -->
    <el-card class="box-card">
        <div class="clock" style="margin-left:145px">
            <span class="clockText">活动情况：</span>
            <el-row type="flex" class="row-bg" justify="space-around">
                <el-col :span="6">
                    <div>
                        <el-progress type="circle"
                            :width="150"
                            :height="150"
                            :percentage="finishNum"
                            :format="getFinishNum"
                            color="#5cb87a"></el-progress>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div>
                        <el-progress type="circle"
                            :width="150"
                            :height="150"
                            :percentage="toBeginNum"
                            :format="getToBeginNum"
                            color="#e6a23c"></el-progress>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div>
                        <el-progress type="circle"
                            :width="150"
                            :height="150"
                            :percentage="activityScore"
                            color="#1989fa"
                            :format="getActivityScore"></el-progress>
                    </div>
                </el-col>
            </el-row>
        </div>
    </el-card>

    <!-- 活动展示卡片 -->
    <div class="displayCard" style="margin-left:-40px">
        <el-row type="flex" class="row-bg" style="flex-wrap:wrap;" justify="space-around">
            <el-col  :span="6" v-for="item in cardData" :offset="1" :key="item.activityId" shadow="hover" style="margin-top:20px">
                <el-card :body-style="{ padding: '0px' }">
                    <img :src="item.imgUrl"
                        class="image"
                        width="100%"
                        height="220px">
                    <div style="padding: 14px ">
                        <span>{{ item.activityContent }}</span>
                        <div class="bottom clearfix">
                        <time class="time">{{ item.startTime }}</time>
                        <!-- <el-button type="text" class="button" @click="handleToDetail(item.activityId)">查看详情</el-button> -->
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>


  </div>
</template>

<script>
import { getFirstScreenData, getBannerPic, getCardData } from '@/api/activityModule/firstScreen';
export default {
    data(){
        return {
            picList: ['https://img1.baidu.com/it/u=463439701,1254524263&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800','https://img1.baidu.com/it/u=146657627,1172731911&fm=253&fmt=auto&app=138&f=JPEG?w=890&h=500','https://img1.baidu.com/it/u=2605372625,2257936617&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=313'],
            finishNum : Math.floor( (13 / 19 * 100) ), // 完成的活动 / 总活动数 * 100
            toBeginNum: 100,
            activityScore: 68,
            cardData: [],
        }
    },
    created(){
        getFirstScreenData().then(res => {
            this.finishNum = res.data.finishedEventNum;
            this.toBeginNum = res.data.toBeginEventNum;
            this.activityScore = res.data.averageEventScore;
        });
        // getBannerPic().then(res => {
        //     this.picList = res.data.urlList;
        // });
        getCardData().then(res => {
            this.cardData = res.data.list;
            console.log(res.data.list)
        })
    },
    methods: {
        getFinishNum(){
            return '已完成：' + this.finishNum;
        },
        getToBeginNum(){
            return '待完成：' + this.toBeginNum;
        },
        getActivityScore(){
            return '活动平均分：' + this.activityScore;
        },
        handleToDetail(id){
            this.$router.push({name: 'activityDetail', params: { activityId: id }})
        }
    }
}
</script>

<style scoped>
    .clockText{
        font-size: 20px;
        display: inline-block;
        position: absolute;
        transform: translateX(-100px);
        /* font-weight: bold; */
    }


    .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }

  .clearfix:after {
      clear: both
  }
</style>
