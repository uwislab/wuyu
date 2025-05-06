<template>
  <div class="app-container">
    <div>
      <el-button class="mg-l20" type="primary" plain size="mini" icon="el-icon-message" @click="getPdf(paper.paperTitle)">导出pdf</el-button>
      <el-button class="mg-l20" type="primary" plain size="mini" icon="el-icon-message" @click="btnWord(paper.paperTitle)">导出word</el-button>
    </div>
    <div class="row" id="pdfDom" > 
      <div class="title">{{ paper.paperTitle }}【{{paper.paperType}}】</div>
      <div class="mg-b30">{{ paper.description }}</div>
      <Divider class="divider_cp">__________________________________________________________________________________________________________________________________________________________</Divider>
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
                @change="radio_click(count, item.questId, item.questType, opt.optId)"
                >{{ opt.optContent }}
              </el-radio>
            </div>
          </div>
        </span>
        <!--多选-->
        <span v-if="item.questType == 2">
          <el-checkbox-group v-model="checkList">
            <div v-for="(opt, index) in item.qOptionsList" class="mg-l10">
              <el-checkbox
                :label="opt.optId"
                @change="check_click(count, item.questId, item.questType, opt.optId)"
                >{{ opt.optContent }}
              </el-checkbox>
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
              placeholder="请输入内容..."
              style="width: 500px"
            >
            </el-input>
          </div>
        </span>
      </div>
    </div>
    <el-form>
      <el-form-item>
        <el-button type="danger" class="mg-b10" plain icon="el-icon-top-left" size="mini" @click="close">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import $ from 'jquery'
// import '../../utils/export'
import {
  listQuestion,
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

    };
  },

  created() {
    this.paper = this.$route.query;
    console.log(this.paper);
    // 获取题目信息
    listQuestion({ paperId: this.paper.paperId }).then((res) => {
      this.questList = res.data;
      console.log(this.questList)
    });
  },
  methods: {
     btnWord(title){
       this.$modal
        .confirm('是否导出名称为"' + title + '"的问卷？').then(() => {
            $("#pdfDom").wordExport(title);//参数是下载的word文件名
          this.$modal.msgSuccess("导出问卷成功！");
        })
        .catch(() => {});
       
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
      console.log(answerAry);
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
      console.log(answerAry);
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
      console.log(answerAry);
    },
  },
};
</script>

<style>

.title {
  font-family: "微软雅黑";
  font-size: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  color: rgb(12, 118, 241);
}
.mg-l10 {
  margin-left: 10px;
  margin-bottom: 2px;
}
.mg-l20 {
  margin-left: 1150px;
}
.mg-b10 {
  margin-bottom: 15px;  
  margin-left: 50px;
}
.mg-b30 {
  margin-top: 39px;
  margin-left: 50px;
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
  margin-left: 40px;
}




  
</style>