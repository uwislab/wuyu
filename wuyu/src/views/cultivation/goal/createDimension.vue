<template>
  <div class="app-container">
    <p style="color: rgb(104, 104, 104);font-size: 16px;margin-left: 10px;padding:10px 20px;">
      &nbsp;&nbsp;&nbsp;(此页面只展示量表状态为<span style="color: blue;">编辑中</span>和<span style="color: blue;">审核驳回</span>)
    </p>
    <!-- 评价量表内容表格   -->
    <div class="scale_content_bar">

      <el-table v-loading="loading" :cell-style="{border:'1px solid black'}" :header-cell-style="{border:'1px solid black',background: '#324157', color: '#ffffff'}"
       border :data="scaleList.slice((currentPage - 1) * pageSize, currentPage * pageSize)" class="scale_content">
        <el-table-column label="量表id" prop="scaleId" align="center"></el-table-column>
        <el-table-column label="量表标题" prop="title" align="center"></el-table-column>
        <el-table-column label="创建者" prop="creatorName" align="center"></el-table-column>
        <el-table-column label="创建日期" prop="createDate" align="center"></el-table-column>
        <el-table-column label="版本" prop="grade" align="center"></el-table-column>
        <el-table-column label="量表状态" prop="state" align="center">
          <template slot-scope="scope">
            <el-tag type="" size="medium" v-if="scope.row.state == 0">编辑中</el-tag>
            <el-tag type="" size="medium" v-if="scope.row.state == 1">待审核</el-tag>
            <el-tag type="success" size="medium" v-if="scope.row.state == 2">审核通过</el-tag>
            <el-tag type="danger" size="medium" v-if="scope.row.state == 3">审核驳回</el-tag>
            <el-tag type="warning" size="medium" v-if="scope.row.state == 4">审核逾期</el-tag>
            <el-tag type="info" size="medium" v-if="scope.row.state == 5">已弃用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="相关领域" prop="domain" align="center">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.domain == 0">泛型</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 1">德育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 2">智育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 3">体育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 4">美育</el-tag>
            <el-tag size="medium" v-if="scope.row.domain == 5">劳育</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="{row, $index}">
            <el-button style="margin-bottom: 4px;" type="primary" size="mini" @click="selectScale(row.scaleId,row.title)">编辑评价内容</el-button>
            <el-button type="warning" size="mini" @click="showUpdateScale(row)">修改量表</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 修改量表对话框 -->
      <el-dialog :visible.sync="scaleDialogVisible" :close-on-click-modal = "false" title="修改量表" width="30%">
        <el-form :model="scaleInfoForm" ref="scaleInfoForm" label-width="130px" :rules="rules">
          <el-form-item label="修改量表名称" prop="title">
            <el-input v-model="scaleInfoForm.title" />
          </el-form-item>
          <el-form-item label="修改版本" prop="grade">
            <el-select placeholder="绑定年级" v-model="scaleInfoForm.grade">
              <el-option v-for="(grade, index) in gradeList" :label="grade" :value="2023-index"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="scaleDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="updateScale()">
              确认
            </el-button>
          </span>
        </template>
      </el-dialog>
      <!-- 分页 -->
      <el-pagination class="page" @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="currentPage" :page-sizes="[1, 5, 10, 20]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="this.scaleList.length">
      </el-pagination>

      <el-dialog :visible.sync="editDialogVisible" title="编辑评价内容" width="90%">
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
          
          <el-table-column label="评价id" prop="itemId" align="center" />
          <el-table-column label="评价内容(标题)" prop="itemContent" align="center" />
          <el-table-column label="细则层级" prop="itemLevel" align="center" :formatter="formatItemLevel" />
          <el-table-column label="评价维度分数" prop="itemScore" align="center" />
          <el-table-column label="评价方式及评价手段" prop="evaluationMethod" align="center" />
          <el-table-column label="评价者" prop="evaluators" align="center" />
          <el-table-column label="备注" prop="remark" align="center" />
          <el-table-column label="操作" align="center" >
            <template slot-scope="{row, $index}">
              <div class="threebtn">
                <el-button  :type="row.itemLevel < 4 ? 'text' : ''" size="mini" :disabled="row.itemLevel >= 4"
                @click="showItemDialog(row.itemContent, row.itemLevel + 1, row.itemId)">{{ row.itemLevel >= 4 ?'最多四级':'新增下级'}}</el-button>
              <!-- <el-button type="warning">修改</el-button> -->
              <el-button type="text" size="mini" style="color: red;"
                @click="deleteScaleItem(row.itemId, row.itemLevel, row.preItemId)">删除</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

    </div>

    <!-- 创建评价内容对话框 -->
    <el-dialog :visible.sync="itemDialogVisible" title="创建评价内容" :close-on-click-modal = "false" width="30%">
      <br />
      <el-form :model="itemContentForm" ref="scaleInfoFormRef" label-width="100px" :rules="rules_item">
        <el-form-item label="评价内容" prop="itemContent">
          <el-input v-model="itemContentForm.itemContent" placeholder="请输入评价内容" />
        </el-form-item>
        <el-form-item label="分值" prop="itemScore">
          <el-input v-model="itemContentForm.itemScore" type="number" placeholder="请输入分值" />
        </el-form-item>
        <el-form-item label="评价方式" prop="evaluationMethod">
          <el-input v-model="itemContentForm.evaluationMethod" placeholder="请输入评价方式" />
        </el-form-item>
        <el-form-item label="评价者" prop="evaluators">
          <el-select v-model="itemContentForm.evaluators" placeholder="请选择评价者">
            <el-option v-for="item in identityList" :key="item.identityId" :label="item.identityInfo" :value="item.identityInfo" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="itemContentForm.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <!-- 对话框底部 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="itemDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="createItem">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑评价内容对话框 -->
    <el-dialog :visible.sync="editItemDialogVisible" title="编辑评价内容" width="30%">
      <br />
      <el-form :model="editItemContentForm" ref="editScaleInfoFormRef" label-width="100px" :rules="rules_item">
        <el-form-item label="评价内容" prop="itemContent">
          <el-input v-model="editItemContentForm.itemContent" placeholder="请输入评价内容" />
        </el-form-item>
        <el-form-item label="分值" prop="itemScore">
          <el-input v-model="editItemContentForm.itemScore" type="number" placeholder="请输入分值" />
        </el-form-item>
        <el-form-item label="评价方式" prop="evaluationMethod">
          <el-input v-model="editItemContentForm.evaluationMethod" placeholder="请输入评价方式" />
        </el-form-item>
        <el-form-item label="评价者" prop="evaluators">
          <el-select v-model="editItemContentForm.evaluators" placeholder="请选择评价者">
            <el-option v-for="item in identityList" :key="item.identityId" :label="item.identityInfo" :value="item.identityInfo" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="editItemContentForm.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <!-- 对话框底部 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editItemDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="editScaleContent(editItemContentForm)">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 创建细则对话框 -->
    <el-dialog :visible.sync="detailDialogVisible" :title="selectedItem.name" :before-close="handleClose" :close-on-click-modal = "false">
      <el-switch v-model="isAllGrade" size="large" active-text="全年级" inactive-text="选择年级" @change="changeGrade" />
      <br />
      <el-button-group v-if="!isAllGrade">
        <template v-for="(grade, index) in gradeList">
          <el-button @click="selectGrade(index)">{{ grade }}</el-button>
        </template>
        <span>当前选择的年纪是：{{ gradeList[selectedGrade - 1] }}</span>
      </el-button-group>
      <!-- 全年级细则管理框 -->
      <div class="detail-block">
        <div class="block-left">
          <!-- 全年级细则输入框 -->
          <div class="create-detail" style="margin-bottom: 5px">
            <el-input placeholder="添加一项细则" v-model="detailName"></el-input>
            <el-button type="success" @click="insertDetail">+ 添加</el-button>
          </div>
          <!-- 全年级细则名展示列表1 -->
          <div class="detail-list">
            <el-scrollbar max-height="300px" style="background-color: white">
              <template v-for="(item, index) in detailNameList[selectedGrade - 1]">
                <div class="detail-name-block">
                  <span style="width: 220px" size="large" @click="chooseDetail(item, index)">{{ index + 1 }} . {{ item
                  }}</span>
                  <el-button size="small" type="danger" plain round @click="deleteDetail(index)">删除</el-button>
                </div>
              </template>
            </el-scrollbar>
          </div>
        </div>
        <div class="block-right">
          <el-switch v-model="isDataType" size="large" active-text="数据换算成绩" inactive-text="直接输入成绩" />
          <p>当前管理细则为：“{{ showDetailContent[0].name }}”</p>
          <el-table :data="showDetailContent">
            <el-table-column label="等级" prop="level" width="80px" align="center" />
            <el-table-column label="分数范围" align="center">
              <el-table-column label="下限" prop="scoreMin" align="center">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.scoreMin" style="width: 50px" />
                </template>
              </el-table-column>
              <el-table-column label="上限" align="center">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.scoreMax" style="width: 50px" />
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column v-if="isDataType" label="参数范围" align="center">
              <el-table-column label="下限" prop="scoreMin" align="center">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.dataMin" style="width: 50px" />
                </template>
              </el-table-column>
              <el-table-column label="上限" align="center">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.dataMax" style="width: 50px" />
                </template>
              </el-table-column>
            </el-table-column>
          </el-table>
          <el-button type="primary" @click="saveDetailContent">保存</el-button>
        </div>
      </div>

      <!-- 对话框底部 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="detailsSubmit">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>
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
  getFuScaleByStates
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
      editDialogVisible: false,
      //这个是量表table的结构
      scaleInfoForm: {
        id: null,
        title: '',
        createDate: null,
        state: 0,
        creatorId: null,
        domain: null,
        creatorName: null,
        grade: null
      },
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
    let states = "0,3";
    // 读取编辑中和审核驳回
    getFuScaleByStates(states).then(res => {
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
    //选中量表，查询量表评价内容
    selectScale(id,title) {
      this.editDialogVisible = true;
      this.selectScaleId = id;
      this.selectedScaleName = title;
      getContentByIdCopy(id).then(res => {
        this.structuredData = res.data;//获取量表的一级项目信息
      })
    },

    //树形加载数据
    load(tree, treeNode, resolve) {
      getItemByPreCopy(tree.itemId).then(res => {
        resolve(res.data)
      });
    },

    // 层级数据显示转换
    formatItemLevel(row){
      return row.itemLevel + "级";
    },

    // 按钮点击事件 -- 量表基本信息提交
    createScale() {
      // 获取当前事件，并格式化为 ‘年-月-日’ 形式
      let nowDate = new Date();
      let date = {
        year: nowDate.getFullYear(),
        month: nowDate.getMonth() + 1,
        date: nowDate.getDate()
      }
      this.scaleInfoForm.createDate = date.year + '-' + date.month + '-' + date.date
      insertFuScale(this.scaleInfoForm).then(res => {
        if (res.code == 200) {
          this.scaleDialogVisible = false;
        } else {
          this.scaleInfoForm = {
            id: null,
            title: '',
            createDate: null,
            state: 0,
            creatorId: null,
            domain: null
          };
        }
      })
    },

    // 表格层级操作函数，可以直接获得表格行、列信息
    cell({ row, column, rowIndex, columnIndex }) {

    },

    // 按钮点击事件 -- 评价内容添加对话框弹出
    showItemDialog(preItem, level, preItemId) {
      if (!this.selectScaleId) {
        alert("请选择量表后操作");
        return;
      }

      // 初始化评价内容对象
      this.itemContentForm = {
        scaleId: this.selectScaleId,
        itemContent: '',
        itemLevel: level || 1,
        preItem: preItem || '',
        itemScore: 0,
        evaluationMethod: '',
        evaluators: '',
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

    // 按钮点击事件 -- 评价内容提交
    createItem() {
      this.$refs.scaleInfoFormRef.validate(valid => {
        if (!valid) return; // 输入不合法，直接返回

        const { scaleId, evaluators, itemScore, itemLevel, preItemId } = this.itemContentForm;
        this.itemContentForm.evaluators = evaluators.toString();

        // 检查1级项目
        if (itemLevel === 1) {
          this.checkLevelOneScale(scaleId, itemScore, preItemId);
        } else {
          this.checkChildScale(preItemId, itemScore, itemLevel);
        }
      });
    },

    checkLevelOneScale(scaleId, itemScore, preItemId) {
      getAllLevelScore(scaleId).then(res => {
        const leveloneSum = res.data[1] || 0;
        const sum = parseInt(itemScore) + leveloneSum;
        if (sum > 100) {
          this.$alert('1级层级评价项之不能大于100', '警告', {
            confirmButtonText: '确定',
            type: 'warning'
          });
          return;
        }
        this.insertScaleContent(scaleId, preItemId);
      });
    },

    checkChildScale(preItemId, itemScore, itemLevel) {
      ChildSumScores(preItemId).then(res => {
        if (res.data.ChildSum) { // 有子层级的情况
          const isEqual = (parseInt(res.data.ChildSum) + parseInt(itemScore)) <= res.data.CurSum;
          if (isEqual) {
            this.insertScaleContent(this.itemContentForm.scaleId, preItemId);
          } else {
            this.greaterError();
          }
        } else {
          SumScores(preItemId).then(res => {
            if (itemLevel !== 2 && res.data.CurSum !== res.data.PreSum) {
              this.$alert('本层级评价项分数之和未达到上层级,请继续增加同级', '警告', {
                confirmButtonText: '确定',
                type: 'warning'
              });
            } else {
              this.insertScaleContent(this.itemContentForm.scaleId, preItemId);
            }
          });
        }
      });
    },

    insertScaleContent(scaleId, preItemId) {
      insertScaleContent(this.itemContentForm).then(res => {
        if (res.code === 200) {
          this.selectScale(scaleId);
          getItemByPreCopy(preItemId).then(res => {
            this.$set(this.$refs.myTable.store.states.lazyTreeNodeMap, preItemId, res.data);
          });
        }
      });
    },

    greaterError() {
      this.$alert('本层级评价项之和应该不大于上层级评价项之和', '警告', {
        confirmButtonText: '确定',
        type: 'warning'
      });
    },

    // 按钮点击事件 -- 为评价内容插入评价细则（评价细则创建界面显示）
    editItem(row) {
      this.detailDialogVisible = true;
      this.selectedItem.id = row.itemId;
      this.selectedItem.name = row.content;
    },

    // 按钮点击事件 -- 删除评价内容并更新显示数组
    deleteItem(row, $index) {
      deleteItemContent(row.itemId).then(res => {
        // console.log(res.data);
        if (res.data == 1) {
          this.scaleContentList.splice($index, 1);
          // console.log("删除记录成功");
        }
      })
    },

    deleteScaleItem(itemId, itemLevel, preItemId) {
      this.$confirm('此操作将永久删除该评价内容, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteItem(itemId).then(res => {
          if (res.code == 200) {
            if (itemLevel != 1) {
              getItemByPreCopy(preItemId).then(res => {
                this.$set(this.$refs.myTable.store.states.lazyTreeNodeMap, preItemId, res.data);
                this.$message({
                  message: "删除成功！",
                  type: "success"
                });
              })
            } else {
              getContentByIdCopy(this.selectScaleId).then(res => {
                this.structuredData = res.data;
                this.$message({
                  message: "删除成功！",
                  type: "success"
                });
              })
            }
          }
        })
      }).catch(() => {
        //取消放空
      });
    },

    // 按钮点击事件 -- 为当前评价内容新增子条目
    addSubItem(row) {
      let preItem = row.itemContent;
      let level = row.itemLevel;
      this.showItemDialog(preItem, level);
    },

    // 按钮点击事件 -- 评价细则项添加
    insertDetail() {
      if (this.detailName.trim() != '') { // 如果细则输入框不为空，就向列表添加
        let scaleId = this.scaleInfoForm.id;
        let itemId = this.selectedItem.id;
        let name = this.detailName;
        let grade = this.selectedGrade;
        this.detailNameList[this.selectedGrade - 1].push(name);
        this.detailsList[this.selectedGrade - 1].push([
          { scaleId, itemId, name, level: '优秀', grade, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
          { scaleId, itemId, name, level: '良好', grade, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
          { scaleId, itemId, name, level: '及格', grade, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
          { scaleId, itemId, name, level: '不及格', grade, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
        ]);
        // 向列表添加后初始化细则名输入框
        this.detailName = '';
      }
    },

    // 开关切换事件 -- 切换全年级还是单独年级
    changeGrade() {
      let scaleId = this.scaleInfoForm.id;
      let itemId = this.selectedItem.id;
      // 切换后清空数据
      this.detailName = "";
      this.detailNameList = [[], [], [], [], [], []];
      this.detailsList = [[], [], [], [], [], []];
      this.showDetailContent = [
        { scaleId, itemId, name: null, level: '优秀', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
        { scaleId, itemId, name: null, level: '良好', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
        { scaleId, itemId, name: null, level: '及格', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
        { scaleId, itemId, name: null, level: '不及格', grade: null, scoreMin: null, scoreMax: null, dataMin: null, dataMax: null, isDataType: false },
      ];
    },

    // 按钮点击事件 -- 细则创建页面，选择年级查看对应的细则数组
    selectGrade(gradeNum) {
      this.selectedGrade = gradeNum + 1;
    },

    // div点击事件 -- 细则创建界面，点击一个细则名可以查看该细则内容
    chooseDetail(item, index) {
      this.selectedDetailNum = index;
      this.showDetailContent = this.detailsList[this.selectedGrade - 1][index];
      this.isDataType = this.detailsList[this.selectedGrade - 1][index][0].isDataType;
    },

    // 按钮点击事件-- 细则创建界面，删除一个已有的细则及其内容
    deleteDetail(index) {
      this.detailNameList[this.selectedGrade - 1].splice(index, 1);
      this.detailsList[this.selectedGrade - 1].splice(index, 1)
    },

    // 按钮点击事件 -- 细则创建界面，保存细则内容
    saveDetailContent() {
      // 获取关键数据
      let isDataType = this.isDataType;               // 当前细则数据输入形式
      let selectedGrade = this.selectedGrade;         // 当前选择的年级
      let selectedDetailNum = this.selectedDetailNum; // 当前正在修改的细则序号
      let showDetailContent = this.showDetailContent; // 当前正在修改的细则内容
      showDetailContent.forEach(detail => { // 将当前正在修改的细则内容中的数据输入格式修改
        detail.isDataType = isDataType;
      })
      this.detailsList[selectedGrade][selectedDetailNum] = showDetailContent; // 赋值到保存细则内容数组
    },

    detailsSubmit() {
      insertDetailList(this.isAllGrade, this.detailsList).then(res => {

      })
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
    },

    showUpdateScale(row) {
      this.scaleDialogVisible = true
      Object.assign(this.scaleInfoForm, row)//将 row 对象的属性复制到 this.scaleInfoForm 对象中
    },

    updateScale() {
      this.$refs.scaleInfoForm.validate((valid) => {
        if (valid) {
          editScale(this.scaleInfoForm).then(res => {
            if (res.code == 200) {
              this.$message({
                type: "success",
                message: "修改成功"
              })
              this.scaleDialogVisible = false;
              getFuScaleByState().then(res => {
                this.scaleList = res.data;
              })
            } else {
              this.$message({
                type: "danger",
                message: "修改失败"
              })
            }
          })
        }
      })
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
  font:bold;
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
  font: bold;
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
