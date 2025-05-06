<template>
  <div class="app-container">
    <div class="title">{{ paper.paperTitle }}【{{paper.paperType}}】</div>
    <div class="mg-b30">{{ paper.description }}</div>
    <Divider class="divider_cp">________________________________________________________________________________________________________________________________________________________</Divider>
    <div>
      <!--题目-->
      <div v-for="(item, qs_index) in questList" class="mg-b10">
        {{ qs_index + 1 }}.{{ item.questTitle }}
        <span v-if="item.questType == 1">【单选】</span>
        <span v-if="item.questType == 2">【多选】</span>
        <span v-if="item.questType == 3">【判断】</span>
        <span v-if="item.questType == 4">【填空】</span>
        <el-button
          size="mini"
          @click="openQuest(item, 'q')"
          type="text"
          icon="el-icon-edit"
          >修改
        </el-button>
        <el-button
          size="mini"
          @click="delquest(item.questId, 'q')"
          type="text"
          icon="el-icon-delete"
          >删除
        </el-button>
        <!--单选和判断-->
        <span v-if="item.questType == 1 || item.questType == 3">
          <div v-for="(opt, index) in item.qOptionsList" class="mg-l10">
            <div v-if="opt.optContent">
              <el-radio v-model="radioList[qs_index]" :label="opt.optId">
                {{ opt.optContent }}
                <el-button
                  size="mini"
                  type="text"
                  @click="openQuest(opt, 'o')"
                  icon="el-icon-edit"
                  >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="delquest(opt.optId, 'o')"
                  icon="el-icon-delete"
                  >删除
                </el-button>
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
                @change="
                  check_click(count, item.questId, item.questType, opt.optId)
                "
                >{{ opt.optContent }}
                <el-button
                  size="mini"
                  @click="openQuest(opt, 'o')"
                  type="text"
                  icon="el-icon-edit"
                  >修改
                </el-button>
                <el-button
                  size="mini"
                  @click="delquest(opt.optId, 'o')"
                  type="text"
                  icon="el-icon-delete"
                  >删除
                </el-button>
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
        <el-button type="danger" class="mg-b10" plain size="mini" @click="close">返回</el-button>
      </el-form-item>
    </el-form>
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="quest" :rules="rulesPaper" label-width="80px">
        <el-form-item label="题目名称" prop="paperTitle">
          <el-input v-model="quest.name" placeholder="请输入修改内容..." />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="updquest(quest)">确 定</el-button>
        <el-button type="danger" plain @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>  
</template>

<script>
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
  updQuest,
  delQuest,
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
      quest: {
        questId: 0,
        name: "",
        type: "",
      },
      // 答题数组计算
      count: 0,
      open: false,
      // 填空model
      inputList: [],
    };
  },
  created() {
    this.paper = this.$route.query;
    // console.log(this.paper.paperId);
    // console.log(this.paperId);
    // 获取题目信息
    listQuestion({ paperId: this.paper.paperId }).then((res) => {
      this.questList = res.data;
    });
  },
  methods: {
    // 关闭
    close() {
      this.$tab.closePage();
    },
    openQuest(row, type) {
      if (type == "o") {
        this.quest.questId = row.optId;
        this.quest.name = row.optContent;
        this.quest.type = type;
      } else {
        this.quest.questId = row.questId;
        this.quest.name = row.questTitle;
        this.quest.type = type;
      }
      this.open = true;
    },
    updquest(row) {
      updQuest(row).then((res) => {
        // 获取题目信息
        listQuestion({ paperId: this.paper.paperId }).then((res) => {
          this.questList = res.data;
        });
        this.open = false;
        this.$modal.msgSuccess("修改成功!");
      });
    },
    //删除题目和选项
    delquest(id, type) {
      this.$modal.confirm('是否删除编号为"' + id + '"的内容？').then(() => {
        delQuest({ questId: id, type: type }).then((res) => {
          // 获取题目信息
            listQuestion({ paperId: this.paper.paperId }).then((res) => {
              this.questList = res.data;
            })
        })
      })
      .then(()=>{
        this.$modal.msgSuccess("删除成功！");
      })
        .catch(() => {});
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
  margin-bottom: 25px;
}
.mg-l10 {
  margin-left: 10px;
  margin-bottom: 0px;
}
.mg-b10 {
  margin-bottom: 15px;
  margin-left: 50px;
}
.mg-b30 {
  margin-bottom: -40px;
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
