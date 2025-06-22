<template>
  <div class="app-container">
    <!-- 头部搜索板块 -->
    <!-- <div class="header">
      <div>
        <div class="search_block">
          量表ID:<el-input style="width:200px;margin: 0 30px 0 2px;" v-model="id" placeholder="请输入内容"></el-input>
          标题:<el-input style="width: 230px;margin: 0 30px 0 2px;" v-model="title" placeholder="请输入内容"></el-input>
          考核类型:<el-select v-model="selectedValue" placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </div>
        <div style="float: right;">
          <el-button style="width: 90px; margin: 0 10px 0 0px;" @click="resetForm">重置</el-button>
          <el-button style="width: 90px; margin: 0 10px 0 2px;" type="primary" @click="handleSearch">🔍查询</el-button>
        </div>
      </div>
    </div> -->

    <!-- 量表表格区域 -->
    <div class="scale_table">
      <p style="float: right;">
        <el-button style="width: 140px; margin: 25px 50px 10px 80px;" @click="handleAdd" type="primary">+
          新增老师打分</el-button>
        <!-- <el-button style="width: 120px; margin: 25px 50px 0 10px;" type="primary" @click="handlePushAdd">↪
          导入问卷</el-button> -->
      </p>
      <div class="scaleBox" style="min-height: 450px;">
        <el-table border :data="tableData" stripe class="header-row-style"
          :header-cell-style="{ background: '#324157', color: '#ffffff' }" 
          @select="handleSelect" @select-all="handleSelect" empty-text="暂无数据">
          <!-- <el-table-column type="selection" width="35">
          </el-table-column> -->
          <el-table-column label="id" prop="id" align="center" width="60"></el-table-column>
          <el-table-column label="标题" prop="itemContent" align="center" width="150"></el-table-column>
          <el-table-column label="考核方式" prop="evaluationMethod" align="center" width="300"></el-table-column>
          <!-- <el-table-column label="开始时间" prop="startTime" align="center" width="168"></el-table-column> -->
          <el-table-column label="分值" prop="itemScore" align="center" width="100"></el-table-column>
          <el-table-column label="考核人员" prop="evaluators" align="center" width="100"></el-table-column>
          <el-table-column label="备注" prop="beizhu" align="center" ></el-table-column>
          <el-table-column label="操作" align="center" >
          <template slot-scope="{row, $index}">
            <el-button type="danger" size="mini"
              @click="deleteItemContent(row.id)">删除</el-button>
          </template>
        </el-table-column>
        </el-table>
      </div>

      <!-- 编辑查看弹窗 -->
      <!-- <el-dialog title="" :visible.sync="dialogVisible" width="40%" :before-close="handleClose"> -->
        <!-- 弹窗表单内容 -->
        <!-- <el-form :model="form" label-width="80px" label-position="top">
          <el-form-item label="量表ID">
            <el-input v-model="form.id" style="width: 140px; height: 20px;" :placeholder="form.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="问卷标题">
            <el-input v-model="form.title" style="width: 140px; height: 20px;" :placeholder="form.title"></el-input>
          </el-form-item>
          <el-form-item label="问卷类型">
            <el-select v-model="form.type" :placeholder="form.type" style="width: 140px;">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <div style="height: 10px;"></div>
          <el-form-item label="开始时间">
            <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-date-picker v-model="form.finishTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <div style="height: 26px;"></div>

          <el-form-item label="一级指标">
            <el-select v-model="form.zhibiao" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
          <el-form-item label="二级指标">
            <el-select v-model="form.zhibiao2" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
          <el-form-item label="三级指标">
            <el-select v-model="form.zhibiao3" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
          <div style="height: 10px;"></div>
          <el-form-item label="得分">
            <el-input v-model="form.score" style="width: 140px; height: 20px;"></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer" style="margin-right: 30%;">
          <el-button @click="dialogVisible = false" style="margin-right: 80px;">取 消</el-button>
          <el-button type="primary" @click="handleCommitEdit">提交</el-button>
        </span>
      </el-dialog> -->

      <!-- 添加状态弹窗 -->
      <el-dialog title="新增老师打分" :visible.sync="addDialogVisible" width="40%" :close-on-click-modal = "false">
        <!-- 弹窗表单内容 -->
        <el-form :model="newForm" ref="scoreInfoFormRef" label-width="80px" label-position="top" :rules="rules_score">

          <el-form-item label="问卷标题" prop="title">
            <el-input v-model="newForm.title" style="width: 140px; height: 20px;"></el-input>
          </el-form-item>

          <el-form-item label="问卷类型" prop="type">
            <el-select v-model="newForm.type" style="width: 140px;" placeholder="问卷">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="开始时间" prop="startTime" style="margin-top: 17px;">
            <el-date-picker v-model="newForm.startTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>

          <el-form-item label="结束时间" prop="finishTime" style="margin-top: 17px;">
            <el-date-picker v-model="newForm.finishTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          
          <el-form-item label="一级指标" prop="zhibiao" style="margin-top: 17px;">
            <el-select v-model="newForm.zhibiao" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption_1" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>

          <el-form-item label="二级指标" prop="zhibiao2">
            <el-select v-model="newForm.zhibiao2" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption_2" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>

          <el-form-item label="三级指标" style="margin-top: 17px;" prop="zhibiao3">
            <el-select v-model="newForm.zhibiao3" clearable filterable placeholder="请选择" style="width: 170px;">
              <el-option-group v-for="group in slectOption_3" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>

          <el-form-item label="得分" prop="score" style="margin-top: 17px;">
            <el-input v-model="newForm.score" style="width: 140px; height: 20px;"></el-input>
          </el-form-item>
          
          <el-form-item label="备注" prop="beizhu" style="margin-top: 17px;">
            <el-input v-model="newForm.beizhu" style="width: 300px; height: 60px;"></el-input>
          </el-form-item>
          
        </el-form>
        <span slot="footer" class="dialog-footer" style="margin-right: 30%;">
          <el-button @click="addDialogVisible = false" style="margin-right: 80px;">取 消</el-button>
          <el-button type="primary" @click="handleCommit()">提交</el-button>
        </span>
      </el-dialog>

      <!-- <el-dialog title="" :visible.sync="pushDialogVisible" width="40%" :before-close="handleClose"> -->
        <!-- 弹窗表单内容 -->
        <!-- <el-form :model="newForm" label-width="80px" label-position="top">
          <el-form-item label="量表ID">
            <el-input v-model="newForm.id" style="width: 140px; height: 20px;" disabled></el-input>
          </el-form-item>
          <el-form-item label="问卷标题">
            <el-input v-model="newForm.title" style="width: 140px; height: 20px;"></el-input>
          </el-form-item>
          <el-form-item label="问卷类型">
            <el-select v-model="newForm.type" style="width: 140px;" placeholder="问卷">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <div style="height: 10px;"></div>
          <el-form-item label="开始时间">
            <el-date-picker v-model="newForm.startTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-date-picker v-model="newForm.finishTime" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>


          <div style="height: 26px;"></div>

          <el-form-item label="一级指标">
            <el-select v-model="newForm.zhibiao" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
          <el-form-item label="二级指标">
            <el-select v-model="newForm.zhibiao2" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
          <el-form-item label="三级指标">
            <el-select v-model="newForm.zhibiao3" clearable filterable placeholder="请选择" style="width: 140px;">
              <el-option-group v-for="group in slectOption" :key="group.label" :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value"
                  style="width: 140px;">
                </el-option>
              </el-option-group>
            </el-select>
          </el-form-item>
          <div style="height: 10px;"></div> -->
          <!-- 导入问卷 -->
          <!-- <el-upload class="upload-excel" action="http://49.51.69.99:9200/question/import" :show-file-list="true"
            :on-success="handleUploadSuccess" :before-upload="beforeUpload">
            <el-button type="primary" style="margin-top: 20px;margin-left: 20px;">导入问卷</el-button>
          </el-upload>

        </el-form>

        <span slot="footer" class="dialog-footer" style="margin-right: 30%;">
          <el-button @click="pushDialogVisible = false" style="margin-right: 80px;">取 消</el-button>
          <el-button type="primary" @click="handleCommit">提交</el-button>
        </span>
      </el-dialog> -->

      <!-- <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page"
        :page-sizes="[5, 10, 15, 20]" :page-size="pageSize" layout="prev, pager, next" background :total="total"
        style="text-align: center;" prev-click="handlePrrClick">
      </el-pagination> -->

    </div>
  </div>
</template>

<script>
import dayjs from 'dayjs';
import { getExam, insertFuScore,deleteItemContent } from '@/api/fuScale';
export default {
  name: "examineDetails",
  data() {
    return {
      //1-3级指标参考
      slectOption_1:[{
        label: '参考一级指标',
        options: [{
          value: '艺术',
          label: '艺术'
        },  {
          value: '学习能力',
          label: '学习能力'
        },
        {
          value: '运动',
          label: '运动'
        },
        {
          value: '团队合作',
          label: '团队合作'
        },
        {
          value: '科研创作',
          label: '科研创作'
        }
      ]
      }],

      slectOption_2:[
        {
          label: '参考二级指标',
        options: [{
          value: '科学研究',
          label: '科学研究'
        }, {
          value: '创新技术',
          label: '创新技术'
        }, {
          value: '学术探究',
          label: '学术探究'
        }, {
          value: '实验设计',
          label: '实验设计'
        },
        {
          value: '解决问题能力',
          label: '解决问题能力'
        },
        {
          value: '绘画艺术',
          label: '绘画艺术'
        },
        {
          value: '音乐艺术',
          label: '音乐艺术'
        },
        {
          value: '舞蹈艺术',
          label: '舞蹈艺术'
        },
        {
          value: '戏剧艺术',
          label: '戏剧艺术'
        },
        {
          value: '文学艺术',
          label: '文学艺术'
        },
        {
          value: '影视艺术',
          label: '影视艺术'
        },
        {
          value: '球类运动',
          label: '球类运动'
        },
        {
          value: '田径运动',
          label: '田径运动'
        },
        {
          value: '水上运动',
          label: '水上运动'
        },
        {
          value: '格斗运动',
          label: '格斗运动'
        },
        {
          value: '健身运动',
          label: '健身运动'
        },
        {
          value: '目标设定与分工',
          label: '目标设定与分工'
        }
      ]
        }
      ],

      slectOption_3:[{
        label: '参考三级指标',
        options: [{
          value: '实验数据收集与分析',
          label: '实验数据收集与分析'
        }, {
          value: '研究方法与技巧',
          label: '研究方法与技巧'
        }, {
          value: '学术会议报告',
          label: '学术会议报告'
        },
        {
          value: '技术创新与应用',
          label: '技术创新与应用'
        },
        {
          value: '学习方法与策略',
          label: '学习方法与策略'
        }, {
          value: '创意思维与创新能力',
          label: '创意思维与创新能力'
        },
        {
          value: '口头和书面表达能力',
          label: '口头和书面表达能力'
        },
        {
          value: '协调能力',
          label: '协调能力'
        },
        {
          value: '领导与管理技巧',
          label: '领导与管理技巧'
        },
        {
          value: '油画',
          label: '油画'
        },
        {
          value: '水彩画',
          label: '水彩画'
        },
        {
          value: '素描',
          label: '素描'
        },
        {
          value: '音乐演奏',
          label: '音乐演奏'
        },
        {
          value: '音乐创作',
          label: '音乐创作'
        },
        {
          value: '舞蹈编排',
          label: '舞蹈编排'
        },
        {
          value: '戏剧表演',
          label: '戏剧表演'
        },
        {
          value: '剧本创作',
          label: '剧本创作'
        },
        {
          value: '诗歌创作',
          label: '诗歌创作'
        },
        {
          value: '小说创作',
          label: '小说创作'
        },
        {
          value: '影视拍摄',
          label: '影视拍摄'
        },
        {
          value: '影视剪辑',
          label: '影视剪辑'
        },
        {
          value: '足球',
          label: '足球'
        },
        {
          value: '篮球',
          label: '篮球'
        },
        {
          value: '网球',
          label: '网球'
        },
        {
          value: '排球',
          label: '排球'
        },
        {
          value: '田径赛跑',
          label: '田径赛跑'
        },
        {
          value: '田径跳远',
          label: '田径跳远'
        },
        {
          value: '游泳',
          label: '游泳'
        },
        {
          value: '冲浪',
          label: '冲浪'
        },
        {
          value: '拳击',
          label: '拳击'
        },
        {
          value: '跆拳道',
          label: '跆拳道'
        },
        {
          value: '跳舞',
          label: '跳舞'
        },
        {
          value: '瑜伽',
          label: '瑜伽'
        },
        {
          value: '目标明确和任务分配',
          label: '目标明确和任务分配'
        },
        {
          value: '有效沟通和协调',
          label: '有效沟通和协调'
        },
        {
          value: '团队建设和促进凝聚力',
          label: '团队建设和促进凝聚力'
        }]
      }],

      rules_score:{
        title: [{ required: true, message: "请输入标题(1~50字符)", pattern: /^[\s\S]{1,50}$/ }],
        score: [{ required: true, message: "请输入分值(1~100整数)", pattern: /^(?:|[1-9][0-9]?|100)$/ }],
        beizhu: [{ required: false,message: "请输入备注(最多20字符)", pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]{0,20}$/ }]
      },

      page: 1,
      total: 1,
      pageSize: 5,
      // title: '',
      id: '',
      type: '',
      fileName: '参考文档说明.docx',
      formName: '',
      tableData: [],
      selectedValue: '问卷',
      //类型下拉框
      options: [{
        value: '问卷',
        label: '问卷'
      },
      {
        value: '其他',
        label: '其他'
      }],
      //这个是打分数据表的结构
      newForm: {
        upid: this.$route.query.id,//跳转传参
        title: '',
        type: '',
        startTime: '',
        finishTime: '',
        zhibiao: '',
        zhibiao2: '',
        zhibiao3: '',
        score: '',
        beizhu: '',
        status:0,
        teacher_name: this.$route.query.creatorName//跳转传参
      },
      ids: [],
      scaleList: [],
      dialogVisible: false,
      addDialogVisible: false,
      pushDialogVisible: false,
      form: {
        id: '123'
      },
      staticFormData: []
    };
  },

  mounted() {
    // console.log('老师名字： '+this.$route.query.creatorName);
    getExam(this.$route.query.id).then(res => {
      console.log(res.data);
      this.tableData = res.data;
    })
    
  },

  methods: {
    beforeUpload(file) {
      // 检查文件类型是否为 Excel 文件
      const isExcel = (file.type === 'application/vnd.ms-excel' || file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
      if (!isExcel) {
        this.$message.error('只能上传 Excel 文件');
      }
      return isExcel;
    },
    handleUploadSuccess(response) {
      // 处理上传成功后的响应
      if (response === "导入成功！") {
        this.$message.success('导入成功！');
      }
      console.log(response);
    },
    handleChange(value) {
      // console.log(value);
    },
    
    async getExamineScaleList(page, title, id, type, upid) {
      let res = await this.$api.getExamineScaleList({
        page: page,
        title: title,
        id: id,
        type: type,
        upid: upid
      })
      if (res.code === 1) {
        this.$message({
          type: 'success',
          message: '查询成功'
        });
        this.tableData = res.data.records
        this.total = res.data.total
      }
    },

    resetForm() {
      getExam(this.$route.query.id).then(res => {
      console.log(res.data);
      this.tableData = res.data;
    })
      this.title = '',
        this.id = '',
        this.selectedValue = '问卷'
    },
    handleSizeChange() { //这块数据不够，就假装可以翻页查询(●'◡'●)<---这是2023年学长写的不是我写的(2024.5.3)
      console.log('change!')
    },
    handleCurrentChange(val) {
      this.getExamineScaleList(val, this.title, 0, this.selectedValue, this.$route.query.id)
      console.log('change too!')
    },
    handleSelect(selection) {
      this.ids = [] //数组内置空
      selection.forEach(item => {
        this.ids.push(item.id)
      })
    },
    handleSearch() {
      // 先处理获取到的表单数据
      if (this.id === '') {
        this.id = 0
        this.getExamineScaleList(this.page, this.title, this.id, this.selectedValue, this.$route.query.id)
        this.id = ''
      } else {
        this.getExamineScaleList(this.page, this.title, this.id, this.selectedValue, this.$route.query.id)
      }

    },
    async handleAdd() {
      this.addDialogVisible = true
    },
    async handlePushAdd() {
      this.pushDialogVisible = true
    },

    deleteItemContent(id) {
      this.$confirm('此操作将永久删除该考核内容, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteItemContent(id).then(res => {
          if (res.code == 200) {
            getExam(this.$route.query.id).then(res => {
              if (res.code == 200) {
                this.$message({
                  message: "删除成功",
                  type: 'success'
                });
                this.tableData = res.data;
              }
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    // 单一删除
    async handleDelete(index, row) {
      this.$confirm('此操作将永久删除该问卷, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        console.log('flag001', typeof (row.id))
        let res = await this.$api.delListScale(row.id)
        console.log(res)
        if (res.code === 1) {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          this.getExamineScaleList(this.page, this.title, 0, this.selectedValue, this.$route.query.id)
        }
      })
    },
    // 批量删除
    async batchDelete() {
      if (!this.ids.length) {
        this.$message({
          showClose: true,
          message: '请您选择量表',
          type: 'error'
        });
        return false
      }
      this.$confirm('此操作将永久删除该考核量表, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let idStr = this.ids.join(',')
        let res = await this.$api.batchDelete({ ids: idStr })
        console.log(res)
        if (res.status === 200) {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          // -----------------批量删除-----------------
          // 先获取数据可以放置多少页  16/8=2   17/8=2.....1(3) 可以用总条数除以每页的容纳条数， 如果不能整除则向上取整
          const maxPage = Math.ceil(this.total / this.pageSize)
          // 再处理数据列表最后一页的条数  3 % 8 ----3  | 16%8 ----0  |  17%8 ----1 
          const itemNum = this.total % this.pageSize > 0 ? this.total % this.pageSize : this.pageSize
          console.log('最后一页条数------', itemNum)
          const len = this.ids.length
          if (this.currentPage === maxPage) {
            if (len === itemNum) {
              this.currentPage = this.currentPage - 1 > 0 ? this.currentPage - 1 : 1
            }
          }
          this.ids = []
          this.getProjectList(this.currentPage)
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    //编辑查看 打开弹窗--收集表单数据--确认键提交--提示确认操作--修改成功
    async handleEdit(index, row) {
      this.dialogVisible = true;
      console.log('Editing!', row);
      let res = await this.$api.getScaleDetaile({ id: row.id, upid: row.upid });
      this.form = res.data
      this.staticFormData = this.form
      console.log(res);
    },

    handleClose(done) {
      this.$confirm('确认关闭？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      })
        .then(_ => {
          done();
        })
        .catch(_ => { });
    },
    
  //提交打分表
  handleCommit() {
    this.$refs.scoreInfoFormRef.validate((valid) => {
      if (valid) {
        //处理日期格式判断开始时间小于结束时间
        const startTime = dayjs(this.newForm.startTime);
        const finishTime = dayjs(this.newForm.finishTime);

        if (finishTime.isAfter(startTime)) {
          this.newForm.startTime = startTime.format('YYYY-MM-DD');
          this.newForm.finishTime = finishTime.format('YYYY-MM-DD');

          // console.log('newForm.title: '+this.newForm.title);
          // 将this.newForm转换为JSON字符串并输出到控制台
        console.log(JSON.stringify(this.newForm));
          //插入数据到FuScore
          insertFuScore(this.newForm).then(res => {
            if (res.code == 200) {
              console.log('插入成功！');
              this.addDialogVisible = false;
              this.$refs.scoreInfoFormRef.resetFields();
            } else {
              console.log('插入失败！');
              this.$refs.scoreInfoFormRef.resetFields();
            }
          });
        } else {
          // 结束日期小于等于开始日期，显示错误提示
          this.$message.error('结束日期必须大于开始日期');
        }
      }
    });
},

    handleConfirm() {
      this.$confirm('请确认操作！', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        this.dialogVisible = false
        this.addDialogVisible = true
        if (this.staticFormData !== this.form) {

        }
        done();
      })
        .catch(_ => { });

    }
  }
}
</script>

<style scoped>
.app-container {
  background-color: #f2f2f2;
  width: 100%;
  padding: 10px 10px 0 10px;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.header {
  width: 100%;
  background-color: #ffffff;
  padding: 20px;
  display: inline;
  font-weight: normal;
  margin-bottom: 15px;
}

.search_block {
  float: left;
  margin-left: 20px;
}

.condition_button_group {
  margin-top: 13px;
  float: right;
}

::v-deep .el-form-item--medium .el-form-item__label {
  display: inline-block;
  line-height: 15px;
  font-size: 16px;
  color: #828282;
  font-weight: normal;
}

::v-deep .el-form-item--medium {
  display: inline-block;
  margin: 0 20px;
}


.scale_table {
  width: 100%;
  margin: 8px;
  /* padding: 20px; */
  background-color: #ffffff;
}

.header-row-style {
  background-color: #f2f2f2;
  font-weight: bold;
  border-bottom: 1px solid #ccc;
  background-color: rgb(0, 0, 0);
}

.custom-cascader .el-cascader-node {
  padding-left: 20px;
  /* 调整每一项的左侧内边距 */
}

.custom-cascader .el-cascader-node .el-cascader-node__prefix {
  left: 10px;
  /* 调整小圆圈的左侧位置 */
}

::v-deep .el-cascader-node>.el-radio {
  margin: 2px 0 0 0 !important;
}</style>
