<template>
  <!-- 量表表格区域 -->
  <div class="scale_table" >
    <p style="color: rgb(104, 104, 104);font-size: 16px;margin-left: 10px;padding:10px 20px;"> 
      &nbsp;&nbsp;&nbsp;(此页面只展示量表状态为<span style="color: blue;">审核通过</span>和<span style="color: blue;">弃用</span>)
    </p>

    <el-table border v-loading="loading" :data="scaleList" :header-cell-style="{border:'1px solid black',background: '#324157', color: '#ffffff'}">
      <el-table-column label="量表id" prop="scaleId" align="center"></el-table-column>
      <el-table-column label="版本" prop="grade" align="center"></el-table-column>
      <el-table-column label="量表标题" prop="title" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="{row, $index}">
          <el-button class="check_btn" size="mini" @click="checkScale(row)">查看执行</el-button>
          <el-button type="primary" size="mini" @click="setScaleDialogVisible(row)">执行</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="scaleDialogVisible" title="绑定年级" width="30%">
      <el-form :model="excuteForm" ref="excuteForm" label-width="130px" :rules="rules">
        <el-form-item label="绑定年级" prop="gradeId">
          <el-select placeholder="请绑定一个年级" v-model="excuteForm.gradeId">
            <el-option v-for="(gradeId, index) in gradeList" :key="index" :label="gradeId" :value="gradeId"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="scaleDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>


  </template>



<script>
import {addExecution, getGrade,getFuScaleByStates} from "@/api/fuScale";
import router from "@/router";

export default {
  loading: false,
  name: "tp",
  data() {
    return {
      scaleList: [
        { scaleId:'',
        grade: "", 
        title: "" 
        },
      ],
      scaleDialogVisible: false,
      chosenScaleId: '',
      gradeList: [], // 年级列表
      excuteForm: {
        scaleId: '',//选择的量表id
        gradeId: '' // 选择的年级
      },

      rules: {
        gradeId: [{ required: true, message: '请选择绑定年级', trigger: 'change' }]
      },
    };
  },
  mounted() {
    this.loading = true;
    let states = "2,5";
    // 读取通过和弃用
    getFuScaleByStates(states).then(res => {
      // console.log('res.data '+res.data);
      this.scaleList = res.data;
      this.loading = false;
    })

    getGrade().then(res => {
      this.gradeList = res.sort((a, b) => a - b);
    })
  },

  methods: {
    // 查看评价量表详情
    checkScale (row) {
      router.push({path:'/goal/goalDetails12', query: {row}});
    },
    //执行绑定年级
    setScaleDialogVisible(row){
      this.scaleDialogVisible = true;
      this.chosenScaleId = row.scaleId;
    },

    submitForm() {
      this.excuteForm.scaleId = this.chosenScaleId;
      // console.log('this.excuteForm: '+ JSON.stringify(this.excuteForm))

      addExecution(this.excuteForm).then(response => {
        // console.log('response'+response.data)
        if(response.code == 1){
          this.$alert('执行失败,重复绑定', '', {
            confirmButtonText: '确定',
            type: 'warning',
          })
        }else{
          if(response.code == 200){
          this.$alert('执行成功', '', {
            confirmButtonText: '确定',
            type: 'message',
          })
        }else {
          this.$alert('执行失败', '警告', {
            confirmButtonText: '确定',
            type: 'warning'
          })
        }
        }

        this.scaleDialogVisible = false;
      })
        .catch(error => {
        console.error('执行出错:', error);
      })

    }
  }
  }
;
</script>

<style scoped>
.app-container {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
  .header {
    width: 100%;
    height: 80px;
    margin: 10px;
    border-radius: 15px;
    background-color: #66afe9;
    padding: 20px;
    display: inline;
  }
    .search_block {
      float: left;
    }
    .condition_button_group {
      float: right;
    }

  .scale_table {
    width: 100%;
    margin: 10px;
    padding: 20px;
    border-radius: 15px;
    background-color: #e5f5e5;
  }

.el-table {
  border: 2px solid black;
}

.check_btn{
  background-color: #5cb85c;
  color: white;
}
</style>

