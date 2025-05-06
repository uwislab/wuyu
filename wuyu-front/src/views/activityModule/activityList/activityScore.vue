 <template>
    <div class="app-container">
              <el-descriptions title="教师点评">
                <el-descriptions-item label="活动分数">
                  <el-tag size="small" type="success">{{ activityScore }}</el-tag>
                </el-descriptions-item>
                 <el-descriptions-item label="教师评语">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4}"
                    disabled
                    v-model="teacherComment">
                  </el-input>
                </el-descriptions-item>
              </el-descriptions>

                <el-form :inline="true" class="demo-form-inline">
                  <el-form-item>
                   <el-input clearable v-model="search" placeholder="姓名" />
                   </el-form-item>

                   <el-form-item>
                   <el-button type="primary" icon="el-icon-search" v-model="studentName" @click="findStudent">查询</el-button>
                   </el-form-item>

                   <el-form-item>
                   <el-input clearable v-model="score" placeholder="分数" />
                   </el-form-item>

                    <el-form-item>
                    <el-button
                    type="primary"
                    icon="el-icon-edit"
                    style="float:right;margin-right: 40px;"
                    @click="confirmAllStuednet()">一键导入全体分数</el-button>
                   </el-form-item>

                </el-form>

                <el-table :data="studentScoreList.filter(data => !search || data.studentName.toLowerCase().includes(search.toLowerCase()))"
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
                      <span>{{ row.classId }}班</span>
                    </template>
                  </el-table-column>
                  <el-table-column width="80px" align="center" label="性别">
                    <template slot-scope="{row}">
                      <span>{{ row.gender === 1 ? "男" : "女" }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column class-name="status-col" align="center" label="家长电话" width="180px">
                    <template slot-scope="{row}">
                      <span>{{ row.parentPhone }}</span>
                    </template>
                  </el-table-column>
                  <!-- <el-table-column class-name="status-col" align="center" label="班主任" width="110">
                    <template slot-scope="{row}">
                      <span>{{ row.monitorName }}</span>
                    </template>
                  </el-table-column> -->

                  <el-table-column label="家长分数" align="center">
                    <template slot-scope="{row}">
                      <span>{{ row.parentScore }}</span>
                    </template>
                  </el-table-column>
                  
                  <el-table-column label="教师评分" align="center">
                    <template slot-scope="{row}">
                      <template v-if="!row.isEdit">{{ row.teacherScore }}</template>
                      <template v-else><el-input v-model="row.teacherScore"/></template>
                    </template>
                  </el-table-column>

                  <el-table-column label="总分" align="center">
                    <template slot-scope="{row}">
                      <span>{{ row.totalScore }}</span>
                    </template>
                  </el-table-column>

                  <el-table-column  label="操作" align="center" width="180">
                      <template slot-scope="{row}">
                        <!-- <el-button type="primary" plain  @click="handleScore(row) ">评分</el-button> -->
                        <el-button type="primary" plain  @click="editScore(row) ">{{ row.isEdit ? '完成' : '修改' }}</el-button>
                      </template>
                  </el-table-column>
                </el-table>

      <!-- 评分框 -->
      <el-dialog title="活动评分" :visible.sync="editVisible">
        <el-table :data="dataForm"
                  border
                  fit
                  highlight-current-row
                  style="width: 100%">
          <el-table-column  label="量表标题" align="center"  width="200">
            <template slot-scope="{row}">
              <span>{{ row.aspect.type }}({{ row.aspect.summary }})</span>
            </template>
          </el-table-column>
          <el-table-column  label="量表内容" align="center"  width="300">
            <template slot-scope="{row}">
              <span v-for="(item) in row.activities" :key="item.id">{{ item.title }}({{ item.weight }})<br/></span>
            </template>
          </el-table-column>
          <el-table-column  label="得分" align="center"  width="180">
            <template slot-scope="scope">
              <input  @change="changeScore($event, scope)" type="textarea"/>
            </template>
          </el-table-column>
        </el-table>
        <h3>总分：{{ aspectSummary }}</h3>
        <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="editVisible=false">取消</el-button>
                    <el-button type="primary" @click="saveScore">确定</el-button>
                  </span>
        </template>
      </el-dialog>

    </div>
</template>

<script>
import { getActivityDetail } from "@/api/activityModule/activity";
import { insertScoreSingleStudent, scoreAllStudent, alterScoreSingleStudent} from "@/api/activityModule/phaseTwo";
import goalProto from "@/api/cultivationModule/goal";
export default {
  data(){
    return {
      activityInfo: {},
      teacherComment: '',
      studentScoreList: [],
      dataForm: [],
      scoreList: [],
      search: '',
      score:'',
      aspectSummary: 0,
      activityId:'',
      activityScore: '',
      activityAspect:'',
      editVisible: false,
      isEdit:false,
      studentName: '',
      SingleData: {
        activityId: '',
        studentNum: '',
        stuActScore: 0,
      },

      query: {
        pageNo: 1,
        pageSize: 10,
        title: '',
        editorTids: '',
        reviewerTid: '',
        state: 5,
      },
    }
  },
  created(){
    this.activityAspect = JSON.parse(sessionStorage.getItem('activityAspect'))//传入评价维度表ID
    this.activityId = JSON.parse(sessionStorage.getItem('activityId'))//传入活动ID
    this.activityScore = JSON.parse(sessionStorage.getItem('activityScore'))//传入活动ID
    this.teacherComment = JSON.parse(sessionStorage.getItem('teacherComment'));//评语
    console.log(this.activityScore) 
    console.log(this.teacherComment);
    if (this.teacherComment == null) {
      this.teacherComment = "无评语"
    }
    
    this.getList();

    // goalProto.details(this.activityAspect).then((res) => {
    //   this.dataForm = res.data.ataMaps;
    // })

  },
  methods: {
    handleScore(row) {
      // 动态设置数据并通过这个数据判断显示方式
      this.editVisible = true
      //由于评分框中无法传入row值，因此在此处先行获取部分必要数据
      this.studentScore = row.activityScore
      this.SingleData.activityId = row.activityId
      this.SingleData.studentNum = row.studentNum
      },

    //给整个班级学生全体打分
    confirmAllStuednet(){
         const allData ={
            activityId: this.activityId,
            score: this.score,
         }
         if( parseInt(allData.score) > 100 || parseInt(allData.score) < 0 || allData.activityId == null) {
              this.$message({
                message: '输入的分数超出合理范围,请重新输入！',
                type: 'warning'
              })
         }else{
              scoreAllStudent(allData).then(res => {
                    this.$message({
                        message: '全体学生分数生成成功！',
                        type: 'success'
                        })
                    this.activityScore = res.data;
                    console.log("res.data" + res.data);
                    this.getList();
              })
         }

    },

    //校验并保存对应的分数
    changeScore(event, scope) {
      //当前被编辑输入框中的值
      let score = event.target.value
      //当前被编辑输入框的序号，用于修改对应的数组值
      let index = scope.$index
      //当前编辑输入框对应的评价维度设定的分数，用于判断输入数据是否在分值范围中
      let summary = this.dataForm[index].aspect.summary

      //评分必须大于0，且不能超过当前评价维度设定的分数
      if( score*1 < 0 || score*1 > summary*1 ) {
        //若不在范围，则将输入框、对应序号的数组值设为0
        this.$message.error("评分必须在零到当前维度分值范围内")
        event.target.value = 0
        this.scoreList[index] = 0
        this.editVisible = true
      } else {
        //若满足范围限定，则修改对应序号的数组值
        this.scoreList[index] = score * 1
      }
      //每次更改输入框后都重新计算一变总分，用于显示和传值
      this.aspectSummary = 0
      for(let i=0; i < this.scoreList.length; i++)
        this.aspectSummary += this.scoreList[i]
    },

    //检验发送数据内容后发出插入请求,个人评分
    saveScore() {
      this.SingleData.stuActScore = this.aspectSummary + ''
      if( parseInt(this.SingleData.stuActScore) > 100 || 
          parseInt(this.SingleData.stuActScore) < 0 || 
          this.SingleData.activityId == null){
        this.$message({
          message: '输入的分数超出合理范围',
          type: 'warning'
        })
        this.getList();
      } else {
        insertScoreSingleStudent(this.SingleData).then(res => {
          console.log(res)
          if(res.code == 200){
            this.$message({
              message: '个人评分修改成功',
              type: 'success'
            })
          }else{
            this.$message({
              message: res.message,
              type: 'error'
            })
          }
          this.getList();
        })
      }
      this.editVisible = false
    },

    // 编辑分数
    editScore(row) {// 动态设置数据并通过这个数据判断显示方式
        if (row.isEdit) {
          this.$delete(row, 'isEdit')
          this.alterScore(row)
        } else {
          this.$set(row, 'isEdit', true);
        }
    },

    //修改个人分数
    alterScore(row) {
      this.isEdit = true;

      this.SingleData.stuActScore = row.teacherScore
      this.SingleData.activityId = row.activityId
      this.SingleData.studentNum = row.studentNum

      if( parseInt(this.SingleData.stuActScore) > 100 || 
          parseInt(this.SingleData.stuActScore) < 0 || 
          this.SingleData.activityId == null){
        this.$message({
          message: '输入的分数超出合理范围',
          type: 'warning'
        })
        this.getList();
      } else {
        alterScoreSingleStudent(this.SingleData).then(res => {
          if(res.code == 200){
            this.$message({
              message: '个人评分成功',
              type: 'success'
            })
            this.activityScore = res.data;
            console.log("res.data" + res.data);
          }else{
            this.$message({
              message: res.message,
              type: 'error'
            })
          }
          this.getList();
        })
      }
      this.editVisible = false
    },

    //获取班级学生的活动信息
    getList(){
      if(this.activityId==null){
          this.$message({
          message: '请返回上一步操作，刷新页面，再进入！',
          type: 'warning'
        })
      }else{
        const data = {
          activityId: this.activityId,
          activityAspect: this.activityAspect,
        }
        getActivityDetail(data).then(res =>{
          // this.activityInfo = res.data;
          this.studentScoreList = res.data;
          // if(res.data.teacherComment){
          //   this.teacherComment = res.data.teacherComment
          // }
        })
      }
    },

    // 查找学生
    findStudent(){
      if(this.activityId==null){
          this.$message({
          message: '请返回上一步操作，刷新页面，再进入！',
          type: 'warning'
        })
      }else{
        const data = {
          activityId: this.activityId,
          studentName: this.studentName,
        }
        getActivityDetail(data).then(res =>{
          // this.activityInfo = res.data;
          this.studentScoreList = res.data;
          // if(res.data.teacherComment){
          //   this.teacherComment = res.data.teacherComment
          // }
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
