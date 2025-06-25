<template>
  <div class="app-container">
    <p style="color: rgb(104, 104, 104);font-size: 16px;margin-left: 10px;padding:10px 20px;">
      <!--      &nbsp;&nbsp;&nbsp;(此页面只展示量表状态为<span style="color: blue;">编辑中</span>和<span style="color: blue;">审核驳回</span>)-->
    </p>
    <!-- 评价量表内容表格   -->
    <div class="scale_content_bar">

      <el-table v-loading="loading" :cell-style="{border:'1px solid black'}" :header-cell-style="{border:'1px solid black',background: '#324157', color: '#ffffff'}"
                border :data="scaleList.slice((currentPage - 1) * pageSize, currentPage * pageSize)" class="scale_content">
        <el-table-column label="量表标题" prop="title" align="center"></el-table-column>
        <el-table-column label="更新批次" prop="title" align="center">
          <template slot-scope="scope">
            <span>第{{ scope.row.updateBatch }}次</span>
          </template>
        </el-table-column>
        <el-table-column label="量表id" prop="scaleId" align="center"></el-table-column>
        <!--        <el-table-column label="更新者" prop="updateName" align="center"></el-table-column>-->
        <el-table-column label="更新日期" prop="updateDate" align="center"></el-table-column>
        <el-table-column label="创建者" prop="creatorName" align="center"></el-table-column>
        <el-table-column label="创建日期" prop="createDate" align="center"></el-table-column>
        <el-table-column label="版本" prop="grade" align="center"></el-table-column>
        <!--        <el-table-column label="操作" align="center">-->
        <!--          <template slot-scope="{row, $index}">-->
        <!--            <el-button  style="margin-bottom: 4px;" type="primary" size="mini" @click="selectScale(row.scaleId,row.title)">内容更新历史</el-button>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
      </el-table>

      <!-- 分页 -->
      <el-pagination class="page" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="currentPage" :page-sizes="[1, 5, 10, 20]" :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="this.scaleList.length">
      </el-pagination>

      <el-dialog :visible.sync="editHisDialogVisible" title="评价内容历史" width="90%">
        <!-- 量表评价内容 -->
        <div>
          <label class="notice_02">当前选中量表:</label>
          <label style="font-size: 30px;">{{ selectedScaleName }}</label>
          <el-button class="insert_levle1" round @click="showItemDialog()">添加一级评价内容</el-button>
        </div>
        <!-- 选中量表后显示细则，最多添加四级 -->
        <el-table :cell-style="{border: '1px solid black'}" :header-cell-style="{border: '1px solid black', background: '#324157', color: '#ffffff'}"
                  :data="structuredData" row-key="itemId" lazy :load="load" :indent="30"
                  :tree-props="{ hasChildren: 'hasChildren' }" ref="myTable">

          <el-table-column label="评价内容ID" prop="itemId" align="center" />
          <el-table-column label="评价内容(标题)" prop="itemContent" align="center" />
          <el-table-column label="细则层级" prop="itemLevel" align="center" :formatter="formatItemLevel" />
          <el-table-column label="评价维度分数" prop="itemScore" align="center" />
          <el-table-column label="评价方式及评价手段" prop="evaluationMethod" align="center" />
          <el-table-column label="评价者" prop="evaluators" align="center" />

          <el-table-column label="是否更新" align="center" >
            <template slot-scope="scope">
              <span v-if="scope.row.isUpdate===1">已更新</span>
            </template>
          </el-table-column>
          <el-table-column label="是否删除" align="center" >
            <template slot-scope="scope">
              <span v-if="scope.row.isDelete===1">已删除</span>
            </template>
          </el-table-column>
          <el-table-column label="更新者" prop="updateName" align="center" />
          <el-table-column label="更新日期" prop="updateDate" align="center" />

          <el-table-column label="备注" prop="remark" align="center" />
        </el-table>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import {
  ChildSumScores,
  SumScores,
  deleteItemContent,
  insertFuScale,
  insertScaleContent,
  getContentByIdCopy,
  getFuScaleByState,
  getItemByPreCopy,
  deleteItem,
  editScale,
  getAllLevelScore,
  getFuScaleByStates, updateScaleContent, getFuScaleHisByStates
} from '@/api/fuScale'
import {
  insertDetailList,
} from '@/api/fuRule'
import { getIdentity } from '@/api/user'
import { reactive } from 'vue'
export default {
  name: "evaluateDimension",
  data() {
    return {
      loading: false,
      // 显示控制参数 -- 评价内容创建对话框
      itemDialogVisible: false,
      editItemDialogVisible: false,
      editItemBoolean: false,
      oldScore: 0,
      // 显示控制参数 -- 细则内容创建对话框
      detailDialogVisible: false,
      // 细则创建框 -- 是否为全年级
      isAllGrade: true,
      // 五育下拉框数据来源
      fuItemList: ['德育', '智育', '体育', '美育', '劳育'],
      // 年级数据
      gradeList: [],
      // 评价量表表头内容
      scaleInfoForm: {
        id: null,
        title: '',
        createDate: null,
        state: 0,
        creatorId: null,
        domain: null,
        grade: null
      },
      // 评价内容表单
      itemContentForm: {
        scaleId: null,
        itemId: '',
        itemContent: '',
        itemLevel: 1,
        preItem: '',
        itemScore: 0,
        evaluationMethod: '',//评价方式
        evaluators: '',
        remark: '',
      },
      // 编辑评价内容表单
      editItemContentForm: reactive({
        scaleId: null,
        itemContent: '',
        itemLevel: 1,
        preItem: '',
        itemScore: 0,
        evaluationMethod: '',
        evaluators: '',
        remark: '',
      }),
      // 正在添加细则的评价内容
      selectedItem: {
        id: null,
        name: '',
      },
      // 评价量表内容数列
      scaleContentList: [],
      /* 细则添加界面 */
      // 选择的年级
      selectedGrade: 1,
      // 细则命名
      detailName: '',
      // 左侧列表内容(六个括号对应六个年级)
      detailNameList: [[], [], [], [], [], []],
      // 选择的细则序号
      selectedDetailNum: null,
      // 细则得分层级数组
      showDetailContent: [
        { scaleId: null, itemId: null, name: null, level: '优秀', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
        { scaleId: null, itemId: null, name: null, level: '良好', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
        { scaleId: null, itemId: null, name: null, level: '及格', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
        { scaleId: null, itemId: null, name: null, level: '不及格', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
      ],
      // 判断当前细则是否为直接输入成绩（false）或数据换算成绩（true）
      isDataType: false,
      // 细则对象列表(六个括号对应六个年级)
      detailsList: [[], [], [], [], [], []],
      // 测试者数列
      identityList: [],
      // 提示框是否显示
      showTable: false,
      //能编辑的量表
      scaleList: [],
      //选中量表id
      selectScaleId: '',
      //选择量表名字用于显示
      selectedScaleName:'',
      //量表评价内容列表
      structuredData: [],
      currentPage: 1,
      pageSize: 5,
      rules_item: {
        itemContent: [{ required: true, message: "请输入评价内容(1~50字符)", pattern: /^[\s\S]{1,50}$/ }],
        itemScore: [{ required: true, message: "请输入分值(1~100整数)", pattern: /^(?:|[1-9][0-9]?|100)$/ }],
        evaluationMethod: [{ required: true, message: "请输入评价方式(1~50字符)", pattern: /^[\s\S]{1,50}$/ }],
        evaluators: [{ required: true, message: "请选择评价者" }],
        remark: [{ required: false, message: "请输入备注(最多20字符)", pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]{0,20}$/ }]
      },
      scaleDialogVisible: false,
      editHisDialogVisible: false,
      //这个是量表table的结构
      // scaleInfoForm: {
      //   id: null,
      //   title: '',
      //   createDate: null,
      //   state: 0,
      //   creatorId: null,
      //   domain: null,
      //   creatorName: null,
      //   grade: null
      // },
      rules: {
        title: [{ required: true, message: '请输入量表名称(2~20字符)', pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]{2,20}$/ }],
        grade: [{ required: true, message: '请选择绑定年级' }],
      },
      handleClose: false
    };
  },

  created() {
    this.scaleInfoForm.creatorId = JSON.parse(sessionStorage.getItem('userInfo')).userId
    // 获取测试人员列表
    getIdentity().then(res => {
      this.identityList = res.data
    })
  },
  mounted() {
    this.loading = true;
    let states = "0,1,2,3,4";
    // 读取编辑中和审核驳回
    getFuScaleHisByStates(states).then(res => {
      // console.log('res.data '+res.data);
      this.scaleList = res.data;
      this.loading = false;
    })

    //下拉列表循环添加2001-2023年份
    for (let i = 2023; i >= 2001; i--) {
      this.gradeList.push(i.toString());
    }

  },
  methods: {
    //树形加载数据
    load(tree, treeNode, resolve) {
      getItemByPreCopy(tree.itemId).then(res => {
        resolve(res.data)
      });
    },

    // 层级数据显示转换
    formatItemLevel(row) {
      return row.itemLevel + "级";
    },

    // 表格层级操作函数，可以直接获得表格行、列信息
    cell({row, column, rowIndex, columnIndex}) {

    },

    // 按钮点击事件 -- 评价内容添加对话框弹出
    showItemDialog(preItem, level, preItemId) {
      if (!this.selectScaleId) {
        alert("请选择量表后操作");
        return;
      }

      this.editItemBoolean = false;
      // 初始化评价内容对象
      this.itemContentForm = {
        scaleId: this.selectScaleId,
        itemContent: '',
        itemLevel: level || 1,
        preItem: preItem || '',
        itemScore: 0,
        evaluationMethod: '录入成绩',
        evaluators: '教师',
        remark: '',
        preItemId: preItemId
      };

      // 判断是否为一级项目
      if (!this.itemContentForm.preItem) {
        // 判断1级项目分数满100没
        getAllLevelScore(this.selectScaleId).then(res => {
          const levelSum = res.data[1] || 0;
          if (levelSum < 100) {
            getIdentity().then(res => {
              this.identityList = res.data;
              this.itemDialogVisible = true;
            });
          } else {
            this.$alert('一级指标分数总和已达到100,无法继续添加', '警告', {
              confirmButtonText: '确定',
              type: 'warning'
            });
          }
        });
      } else {
        getIdentity().then(res => {
          this.identityList = res.data;
          this.itemDialogVisible = true;
        });
      }
    },


    //每页条数改变时触发 选择一页显示多少行
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.currentPage = 1;
      this.pageSize = val;
    },

    //当前页改变时触发 跳转其他页
    handleCurrentChange(val) {
      this.currentPage = val;
    }
  }
}

</script>

<style scoped>
/* 评价量表内容创建区域设置 */
.scale_content_bar {
  border: 1px solid black;
  margin-top: 20px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.detail-block {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.block-left {
  width: 30%;
  height: 430px;
  margin: 10px;
  padding: 10px;
  background-color: #eaeaea;
  border-radius: 15px;
}

.create-detail {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.detail-name-block {
  width: 95%;
  height: 80%;
  display: flex;
  align-items: center;
  margin: 10px;
  padding: 10px;
  border-radius: 40px;
  background-color: #eaf3fe;
}

.block-right {
  width: 65%;
  height: 430px;
  margin: 10px;
  padding: 10px;
  background-color: #eaeaea;
  border-radius: 15px;
}

.notice_02{
  font-size: large;
}

.notice_01{
  margin: 64px auto 14px auto;
  font-weight:bold;
  position: relative;
}

.notice_01::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 1px;
  background-color: black;
}

.insert_levle1{
  background-color: rgb(242, 118, 118);
  font-size: 20px;
  width: 250px;
  margin-bottom: 5px;
  color: black;
}

.insert_levle1:hover{
  transform: scale(1.05); /* 鼠标悬停时增大按钮大小 */
  font-weight: bold;
}

.threebtn {
  display: flex;
  flex-direction: column;
  align-items: center;

}

.threebtn button {
  width: calc(100% - 70px);
  margin: 3px;
  text-align: center;
}
.scale_content{
  margin-bottom: 7px;
}
/* 分页栏 */
.page{
  align-self: center;
  margin: 3px 3px;
}
</style>
