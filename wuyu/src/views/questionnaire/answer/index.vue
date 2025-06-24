<template>
  <div>
    <div class="app-container" v-if="questList.length > 0">
        <div class="mg-l30">
          <img src="https://cdn.showmebug.com/packs/media/images/teams/result/written-pad-success-e4f7d92e0dae193442ef0fc6337a6b51.svg"/>
        </div>
      <div class="title">{{ paper.title }}</div>
      <div class="mg-b30">{{ paper.description }}</div>
       <Divider class="divider_cp">_________________________________________________________________________________________________________________________________________________________</Divider>
      <div>
        <div v-for="(item, qs_index) in questList" class="mg-b10">
          {{ qs_index + 1 }}.{{ item.questTitle }}
          <span v-if="item.questType == 1">【单选】</span>
          <span v-if="item.questType == 2">【多选】</span>
          <span v-if="item.questType == 3">【判断】</span>
          <span v-if="item.questType == 4">【填空】</span>
          <!--单选和判断-->
          <span v-if="item.questType == 1 || item.questType == 3">
            <div v-for="(opt, index) in item.qOptionsList" class="mg-l10">
              <div v-if="opt.optContent">
                <el-radio
                  v-model="radioList[qs_index]"
                  :label="opt.optId"
                  @change="
                    radio_click(count, item.questId, item.questType, opt.optId)
                  "
                  >{{ opt.optContent }}</el-radio
                >
              </div>
            </div>
          </span>
          <!--多选-->
          <span v-if="item.questType == 2">
            <el-checkbox-group v-model="checkList">
              <div v-for="(opt, index) in item.qOptionsList" class="mg-l10">
                <el-checkbox
                  :label="opt.optId"
                  @change="
                    check_click(count, item.questId, item.questType, opt.optId)
                  "
                  >{{ opt.optContent }}</el-checkbox
                >
              </div>
            </el-checkbox-group>
          </span>
          <!--填空-->
          <span v-if="item.questType == 4">
            <div>
              <el-input
                type="textarea"
                v-model="inputList[qs_index]"
                @blur="input_blur(count, item.questId, item.questType, $event)"
                placeholder="请输入内容......"
                style="width: 500px"
              ></el-input>
            </div>
          </span>
        </div>
      </div>
      <el-form>
        <el-form-item>
          <el-button type="primary" class="mg-b10" plain size="mini" icon="el-icon-top" @click="submit">提交</el-button>
          <!-- <el-button type="danger" size="mini" @click="close">返回</el-button> -->
        </el-form-item>
      </el-form>
    </div>
    <div id="mycontiner" v-if="questList.length <= 0">
      <div class="app-container">
        <div class="show-failure-frame">
          <div class="result-wrap">
            <div class="result-icon-wrap">
              <img
                src="https://cdn.showmebug.com/packs/media/images/teams/result/written-pad-success-e4f7d92e0dae193442ef0fc6337a6b51.svg"
              />
            </div>
            <div class="result-content-wrap">
              <div class="result-desc-title">不好意思，问卷已经截止或未发布。</div>
              <!-- <a class="smb-btn smb-btn-blue result-op" @click="$router.go(-2)"
                >返回主页</a
              > -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  listQuestion,
  listQuestion1,
  getQuestion,
  delQuestion,
  addQuestion,
  updateQuestion,
} from "@/api/questionnaire/question";
import {
  listAnswer,
  getAnswer,
  delAnswer,
  addAnswer,
  updateAnswer,
} from "@/api/questionnaire/answer";
var answerAry = new Array(); // 答题结果数组
export default {
  data() {
    return {
      // 题目
      questList: [],
      // 单选model
      radioList: [],
      // 多选model
      checkList: [],
      // 路由接收参数
      paper: {},
      // 答题数组计算
      count: 0,
      // 填空model
      inputList: [],
      // PaperType:null
    };
  },
  created() {
    this.paper = this.$route.query;
    // this.PaperType = this.paper.paperType//获取问卷类型}
    console.log(this.paper.paperType)
    // 获取题目信息
    listQuestion1({ paperId: this.paper.paperId }).then((res) => {
      if (res.code == 200) {
        this.questList = res.data.qQuestions;
        this.paper = res.data.paperVO;
        console.log(this.paper)
      } else {
      }
    });
  },
  methods: {
    // 提交
    submit() {
      if (answerAry.length == 0) {
        this.$modal.msgError("不允许提交空白卷！");

      }
      else if( answerAry.length<this.questList.length){
          this.$modal.msgWarning("请作答完毕！");
      }
        else {
        addAnswer(answerAry).then((res) => {
          this.$modal.msgSuccess("提交成功！");
          this.$router.push("/answersucess");
        });
      }
    },
    // 关闭
    close() {
      this.$tab.closePage();
    },
    /*
     * 点击多选操作
     * 1, 判断选项ID是否存在于数组
     * 2，如果存在则是反选，将选项内容置null，后端判断到选项值为null则去除
     * 3，如果不存在则是勾选，新增到数组，同时计算器加一。
     */
    check_click: function (index, questId, questionType, optId) {
      let j = 0;
      for (let i in answerAry) {
        // 取消
        if (answerAry[i].answerContent == optId) {
          answerAry[i].answerContent = null;
          j = 1;
        }
      }
      // 选中
      if (j == 0) {
        answerAry[index] = {
          questId: questId,
          questionType: questionType,
          answerContent: optId,
        };
        this.count = this.count + 1; // 计算器加1
      }
      // console.log(answerAry);
    },
    /*
     * 点击单选操作
     * 1, 判断题目ID是否存在于数组
     * 2，如果存在则是旧选项，修改数组中的值
     * 3，如果不存在则是新选项，新增到数组，同时计算器加一。
     */
    radio_click: function (index, questId, questionType, optId) {
      let j = 0;
      for (let i in answerAry) {
        // 修改
        if (answerAry[i].questId == questId) {
          answerAry[i].answerContent = optId;
          j = 1;
        }
      }
      // 新增
      if (j == 0) {
        answerAry[index] = {
          questId: questId,
          questionType: questionType,
          answerContent: optId,
        };
        this.count = this.count + 1; // 计算器加1
      }
      // console.log(answerAry);
    },
    /*
     * 填空操作
     * 1, 判断题目ID是否存在于数组
     * 2，如果存在则是旧选项，修改数组中的值
     * 3，如果不存在则是新选项，新增到数组，同时计算器加一。
     */
    input_blur: function (index, questId, questionType, event) {
      var myAnswer = event.target.value;
      let j = 0;
      for (let i in answerAry) {
        // 修改
        if (answerAry[i].questId == questId) {
          answerAry[i].answerContent = myAnswer;
          j = 1;
        }
      }
      // 新增
      if (j == 0) {
        answerAry[index] = {
          questId: questId,
          questionType: questionType,
          answerContent: myAnswer,
        };
        this.count = this.count + 1; // 计算器加1
      }
      // console.log(answerAry);
    },
  },
};
</script>

<style scoped>
#mycontiner {
  height: 100vh;
  background: #f5f5f5;
  padding-left: 0px !important;
  padding-right: 0px !important;
  padding: 0px !important;
}
.title {
  font-family: "微软雅黑";
  font-size: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 25px;
  color: rgb(12, 118, 241);
}
.mg-l10 {
  margin-left: 10px;

}
.mg-l30 {
  margin-bottom: -30px;
  margin-left: 100px;

}
.mg-b10 {
  margin-bottom: 15px;
  margin-left: 100px;
}
.mg-b30 {
  margin-bottom: -39px;
  margin-left: 100px;
}
.divider_cp {
    /* margin: 4px auto 15px; */
  width: 324px;
  background: #ffffff;
  border-radius: 20px;
  border-color: #e6e6e6;
  padding: 0 16px;
  font-size: 20px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #8c8ea5;
  line-height: 5em;
  margin-left: 90px;
}
#mycontiner {
  height: 100vh;
  background: #f5f5f5;
}
#mycontiner .result-wrap {
  height: 310px;
  background: #fff;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  padding: 40px 80px;
  width: 1260px;
  margin: auto;
}
.result-wrap .result-icon-wrap,
.result-wrap.with-extra-info .result-base-info .result-icon-wrap {
  width: 80px;
  height: 80px;
}
.result-wrap .result-content-wrap,
.result-wrap.with-extra-info .result-base-info .result-content-wrap {
  flex: auto;
  display: flex;
  flex-direction: column;
  padding-left: 20px;
  padding-top: 12px;
}
img {
  border-style: none;
}
img,
svg {
  vertical-align: middle;
}
.result-wrap .result-content-wrap .result-desc-title,
.result-wrap.with-extra-info
  .result-base-info
  .result-content-wrap
  .result-desc-title {
  font-weight: 600;
  font-size: 16px;
  line-height: 22px;
  color: #333;
}
.result-wrap .result-content-wrap .result-desc-content,
.result-wrap.with-extra-info
  .result-base-info
  .result-content-wrap
  .result-desc-content {
  margin-top: 12px;
  font-family: PingFang SC;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  color: #666;
}
.result-wrap .result-content-wrap .result-op,
.result-wrap.with-extra-info .result-base-info .result-content-wrap .result-op {
  margin-top: 33px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 16px;
  width: -webkit-fit-content;
  width: -moz-fit-content;
  width: fit-content;
  height: 36px;
  background: #408cff;
  border-radius: 4px;
  border: 1px solid #408cff;
  color: #fff;
  font-weight: 400;
  font-size: 14px;
  line-height: 20px;
  box-sizing: border-box;
}
</style>
