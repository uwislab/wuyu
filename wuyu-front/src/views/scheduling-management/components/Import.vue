<template>
    <div class="layout">
      <el-dialog
      title="导入学生数据"
      :visible="props.importDialogVisible"
      :before-close="handleClose"
      width="70%"
      >
          <el-upload
          ref="uploadRef"
          class="upload-demo"
          action=""
          :http-request="customRequest"
          accept=".xlsx, .xls"
          :on-remove="handleRemove"
          :limit="1"
          :on-exceed="handleExceed"
          >
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="uploadTip">仅允许上传一份xls、xlsx格式的文件。</div>

          </el-upload>

          <el-tabs v-model="activeName" v-loading="loading">
              <el-tab-pane label="正确数据" name="first">
                  <el-table
                  :data="pagedSuccessData"
                  stripe
                  style="width: 100%;"
                  :header-cell-style="{textAlign: 'center'}">
                      <el-table-column type="index" label="索引" :index="(successCurrentPage - 1) * pageSize + 1" width="100px" align="center"></el-table-column>
                      <el-table-column prop="grade" label="年级" align="center"></el-table-column>
                      <el-table-column prop="classNum" label="班级" align="center"></el-table-column>
                      <el-table-column prop="className" label="班级名称" align="center"></el-table-column>
                      <el-table-column prop="academicYear" label="学年" align="center"></el-table-column>
                      <el-table-column prop="semester" label="学期" align="center"></el-table-column>
                      <el-table-column prop="course" label="课程名称" align="center"></el-table-column>
                      <el-table-column prop="teacherName" label="任课老师" align="center"></el-table-column>
                      <el-table-column prop="teacherId" label="老师编号" align="center"></el-table-column>
                  </el-table>
                  <el-pagination
                      background
                      class="page"
                      :page-size="pageSize"
                      :current-page="successCurrentPage"
                      :total="successNumber"
                      layout="total, prev, pager, next, jumper"
                      @current-change="handleSuccessCurrentChange">
                  </el-pagination>
              </el-tab-pane>
              <el-tab-pane label="失败数据" name="second">
                  <el-table
                  :data="pagedFailData"
                  stripe
                  style="width: 100%"
                  :header-cell-style="{textAlign: 'center'}">
                      <el-table-column type="index" label="索引" :index="(failCurrentPage - 1) * pageSize + 1" width="100px" align="center"></el-table-column>
                      <el-table-column prop="grade" label="年级" align="center"></el-table-column>
                      <el-table-column prop="classNum" label="班级" align="center"></el-table-column>
                      <el-table-column prop="className" label="班级名称" align="center"></el-table-column>
                      <el-table-column prop="academicYear" label="学年" align="center"></el-table-column>
                      <el-table-column prop="semester" label="学期" align="center"></el-table-column>
                      <el-table-column prop="course" label="课程名称" align="center"></el-table-column>
                      <el-table-column prop="teacherName" label="任课老师" align="center"></el-table-column>
                      <el-table-column prop="teacherId" label="老师编号" align="center" width="120px"></el-table-column>
                      <el-table-column prop="failReason" label="失败原因" align="center" width="300px"></el-table-column>
                  </el-table>
                  <el-pagination
                  class="page"
                  :page-size="pageSize"
                  :current-page="failCurrentPage"
                  :total="failNumber"
                  layout="total, prev, pager, next, jumper"
                  background
                  @current-change="handleFailCurrentChange">
                  </el-pagination>
              </el-tab-pane>
          </el-tabs>



          <span slot="footer" class="dialog-footer">
              <el-button @click="handleDialogClose">取 消</el-button>
              <el-button type="primary" @click="excelSuccess">确定导入正确数据</el-button>
              <el-button type="danger" @click="excelFail">导出失败数据</el-button>
          </span>
      </el-dialog>
    </div>
  </template>

<script setup>
  import { Message } from 'element-ui';
  import { ref, computed, defineProps, defineEmits } from 'vue';
  import { importSuccess, exportFail,importExcel } from '@/api/schedulModule/index'

  // props 声明
  const props = defineProps({
      // 匹配父组件的 dialog-visible
      importDialogVisible: {
      type: Boolean,
      default: false
    }
  });

  const emit = defineEmits(['update:importDialogVisible']);
    
  const activeName = ref('first') // tab标签
  const successData = ref([]) //成功数据
  const failData = ref([])  //失败数据
  const successNumber = ref(0) //成功数目
  const failNumber = ref(0)    //失败数目
  const id = ref() //缓存编号id

//   上传组件
  const uploadRef = ref(null);

// 清除上传文件的方法
  const clearUploadFiles = () => {
      if (uploadRef.value) {
          uploadRef.value.clearFiles(); // 清除已上传的文件
      }
      // 重置数据
      successData.value = [];
      failData.value = [];
      successNumber.value = 0;
      failNumber.value = 0;
      id.value = null;
  };

  // 取消关闭对话框并通知父组件
  const handleDialogClose = () => {
      emit('update:importDialogVisible', false);
      clearUploadFiles()
      loading.value = false
  };

  // 右上角关闭对话框并通知父组件
  const handleClose = (done) => {
      emit('update:importDialogVisible', false);
      clearUploadFiles()
      loading.value = false
  }

  // 数据未加载出来显示加载框
  const loading = ref(false)

      //   移除上传文件
  const handleRemove = (file, fileList) => {
         clearUploadFiles()
          loading.value = false
        }
      //   限制上传文件数量
  const handleExceed = (files, uploadFiles) => {
    Message.warning(
      `每次只能上传一个文件, 你已经上传了 ${files.length}个文件, 总共 ${
        files.length + uploadFiles.length
      } 个`
    )
  }

     
  // 文件上传处理
  const customRequest = async ({ file, onSuccess, onError }) => {
    loading.value = true
    try {
      // 创建FormData并添加文件
      const formData = new FormData()
      formData.append('file', file)
      
      const response = await importExcel(formData)
      
      if(response.code === 200) {
        successData.value = response.data.successList
        failData.value = response.data.failList
        successNumber.value = response.data.successNumber
        failNumber.value = response.data.failNumber
        id.value = response.data.id
        
        // 显示成功消息
        Message({
          message: '上传成功',
          type: 'success',
          center: true,
          duration: 2000,
          showClose: false
        })
      }
      
      // 通知Upload组件上传成功
      onSuccess(response.data)
      
    } catch (error) {
      // 处理错误
      Message.error(`${file.name}上传出错，${error.message}`)
      onError(error)
    } finally {
      // 关闭加载状态
      loading.value = false
      console.log(loading.value)
    }
}

  // 导入校验成功的数据
  const excelSuccess = async ()=> {
      try{
          loading.value = true
          const res = await importSuccess(id.value)
          console.log(res)
          if(res.code === 200){
              Message.success(
              `数据导入成功！`
          )
          }else{
              Message.error(
              res.message
          )
          }
      } catch(error){
          Message.error(
              `数据导入失败，${error.message}`
          )
      } finally {
          loading.value = false
      }
  }

  // 导出校验失败的数据
const excelFail = async ()=> {
  console.log(pagedFailData.value.length)
    if (pagedFailData.value.length ===0 ) {
      Message.error(
            `数据导出失败，无导出数据`
        )
    }
    else{
      try{
        console.log(id.value)
        const res = await exportFail(id.value)
        console.log(res)
        const url = window.URL.createObjectURL(new Blob([res.data],{ type:"application/vnd.ms-excel;charset=utf-8" }))
        const link = document.createElement('a')
        document.body.appendChild(link);
        link.href = url
        link.setAttribute('download','校验失败数据.xls')
        link.click()
        // 清除
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);

      }catch(error){
          Message.warning(
              `数据导出失败，${error.message}`
          )
      }
    }
}


  // 分页相关
  const pageSize = ref(5) // 每页条数
  const successCurrentPage = ref(1) // 正确数据当前页
  const failCurrentPage = ref(1) // 失败数据当前页

  // 计算分页后的数据
  const pagedSuccessData = computed(() => {
    const start = (successCurrentPage.value - 1) * pageSize.value
    const end = start + pageSize.value
    return successData.value.slice(start, end)
  })
  const pagedFailData = computed(() => {
    const start = (failCurrentPage.value - 1) * pageSize.value
    const end = start + pageSize.value
    return failData.value.slice(start, end)
  })

  const handleSuccessCurrentChange = (newPage) => {
    successCurrentPage.value = newPage
  }

  const handleFailCurrentChange = (newPage) => {
    failCurrentPage.value = newPage
  }



</script>

<style scoped>
  .page {
      margin-top: 20px;
  }
</style>

