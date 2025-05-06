 <template>
  <!-- 设置评价维度页面 -->
  <div class="container">
    <div class="step-line">
      <el-steps direction="vertical" :active="1">
        <el-step title="Step 1" description="选择一个量表中的项目，为其添加具体评价内容"/>
        <el-step title="Step 2" description="编辑当前评价规则内容"/>
      </el-steps>
    </div>
    <div class="edit-block">
      <el-form :model="ruleForm" ref="infoFormRef"
               v-if="isRuleInfoTableShow"
               label-position="top"
               :rules="infoFormRules">
        <el-form-item label="名称" prop="ruleName">
          <el-input v-model="ruleForm.ruleName" />
        </el-form-item>

        <el-form-item label="选择量表中的一个可评价项">
          <el-col :span="11">
            <el-form-item prop="scaleId">
              <el-select
                v-model="ruleForm.scaleId"
                placeholder="Select"
                style="width: 240px"
                @change="selectScale"
              >
                <el-option
                  v-for="(item, index) in scaleList"
                  :key="index"
                  :label="item.title"
                  :value="item.scaleId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" class="text-center">
            <span class="text-gray-500">-</span>
          </el-col>
          <el-col :span="11">
            <el-form-item prop="itemId">
              <el-select
                v-model="ruleForm.itemId"
                placeholder="Select"
                style="width: 240px"
              >
                <el-option
                  v-for="(item, index) in itemList"
                  :key="index"
                  :label="item.itemContent + item.evaluationMethod"
                  :value="index"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-form-item>

        <el-form-item label="当前规则适用年级" prop="grade">
          <el-select
            v-model="ruleForm.grade"
            multiple
            placeholder="Select"
            style="width: 240px"
          >
            <el-option
              v-for="(item, index) in gradeList"
              :key="index"
              :label="item"
              :value="index"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="当前规则输入方式" prop="ruleType">
          <el-radio-group v-model="ruleForm.ruleType" @change="selectType">
            <el-radio label="1" size="large">直接输入成绩</el-radio>
            <el-radio label="2" size="large">公式换算成绩</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="resetForm()">确认</el-button>
        </el-form-item>
      </el-form>
      <template class="content-editor" v-if="isRuleContentTableShow">
        <el-button type="info">← 返回</el-button>
        <el-table>
          <el-table-column label="说明"></el-table-column>
          <el-table-column label="参数名"></el-table-column>
          <el-table-column label="参数分值"></el-table-column>
          <el-table-column label="公式"></el-table-column>
        </el-table>
      </template>
    </div>
  </div>
</template>

<script>

import {getAllFuScale, getEditableContent} from "@/api/fuScale";
import {insertRuleInfo} from "@/api/fuRule";

export default {
  name: "createRules",
  data() {
    return {
      // 细则信息编辑表单是否显示
      isRuleInfoTableShow: true,
      // 细则内容编辑表单是否显示
      isRuleContentTableShow: false,
      // 细则信息编辑表单是否显示
      isSuccess: false,
      // 年级列表
      gradeList: ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级'],
      // 量表列表
      scaleList: [],
      // 量表内容
      itemList: [],
      // 细则信息表单
      ruleForm: {
        ruleName: null,
        scaleId: null,
        itemId: null,
        grade: [],
        ruleType: null
      },
      // 细则内容表单

      // 细则信息表单校验规则
      infoFormRules: {
        ruleName: [{required: true, message: '请输入规则名称', trigger: 'blur'}],
        scaleId: [{required: true, message: '请选择评价量表', trigger: 'blur'}],
        itemId: [{required: true, message: '请选择评价项目', trigger: 'blur'}],
        grade: [{required: true, message: '请选择年级范围', trigger: 'blur'}],
        ruleType: [{required: true, message: '请选择输入方式', trigger: 'blur'}],
      },
    };
  },

  created(){
    getAllFuScale().then(res => {
      this.scaleList = res.data;
    })
  },

  methods: {
    selectScale(value) {
      this.scaleList.scaleId = value;
      getEditableContent(value).then(res => {
        this.itemList = res.data;
      });
    },
    resetForm() {
      this.$refs.infoFormRef.validate((valid) => {
        if (valid) {
          this.ruleForm.grade = this.ruleForm.grade.toString();
          //调用接口保存
          insertRuleInfo(this.ruleForm).then(() => {
            this.$message.success('添加成功');
          });
          this.isRuleInfoTableShow = false;
          this.isRuleContentTableShow = true;
        } else {
          this.$message.error('请完善表单相关信息！');
          return false;
        }
      });
    }
  }
}

</script>

 <style scoped>
 .container {
   width: 100%;
   height: 850px;
   border: solid 1px black;
   margin-top: 20px;

   display: flex;
   flex-direction: row;
   align-items: center;
   justify-content: center;
 }
  .step-line {
    width: 20%;
    height: 100%;

    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;

    padding: 100px;
  }
  .edit-block {
    width: 65%;
    height: 100%;
    border: solid 1px black;
    margin-left: 20px;

    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
  }
    .content-editor {
      width: 100%;
      height: 100%;

      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
    }
 </style>
