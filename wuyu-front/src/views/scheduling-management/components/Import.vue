<template>
    <div class="layout">
      <el-dialog
      title="导入学生数据"
      :visible="props.importDialogVisible"
      :before-close="handleClose"
      width="70%"
      >
          <el-upload
          class="upload-demo"
          action="http://localhost:9082/lesson/excel/try-import" 
          :before-upload="beforeUpload"
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          multiple
          :limit="1"
          :on-exceed="handleExceed"
          :on-error="handleError"
          >
              <el-button size="small" type="primary">点击上传</el-button>
              
          </el-upload>
  
          <el-tabs v-model="activeName" @tab-click="handleClick" v-loading="loading">
              <el-tab-pane label="正确数据" name="first">
                  <el-table
                  :data="pagedSuccessData"
                  stripe
                  style="width: 100%">
                      <el-table-column type="index" label="索引" :index="(successCurrentPage - 1) * pageSize + 1" width="100px"></el-table-column>
                      <el-table-column prop="grade" label="年级" ></el-table-column>
                      <el-table-column prop="classNum" label="班级" ></el-table-column>
                      <el-table-column prop="className" label="班级名称"></el-table-column>
                      <el-table-column prop="course" label="课程名称"></el-table-column>
                      <el-table-column prop="teacherName" label="任课老师"></el-table-column>
                      <el-table-column prop="teacherId" label="老师主键"></el-table-column>
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
                  style="width: 100%">
                      <el-table-column type="index" label="索引" :index="(failCurrentPage - 1) * pageSize + 1" width="100px"></el-table-column>
                      <el-table-column prop="grade" label="年级"></el-table-column>
                      <el-table-column prop="classNum" label="班级"></el-table-column>
                      <el-table-column prop="className" label="班级名称"></el-table-column>
                      <el-table-column prop="course" label="课程名称"></el-table-column>
                      <el-table-column prop="teacherName" label="任课老师"></el-table-column>
                      <el-table-column prop="teacherId" label="老师主键"></el-table-column>
                      <el-table-column prop="failReason" label="失败原因"></el-table-column>
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
  import { importSuccess, exportFail } from '@/api/schedulModule/index'
  
  // props 声明
  const props = defineProps({
      // 匹配父组件的 dialog-visible
      importDialogVisible: { 
      type: Boolean,
      default: false
    }
  });
  
  const emit = defineEmits(['update:importDialogVisible']);
  
  // 取消关闭对话框并通知父组件
  const handleDialogClose = () => {
      emit('update:importDialogVisible', false);
      loading.value = false
  };
  
  // 右上角关闭对话框并通知父组件
  const handleClose = (done) => {
      emit('update:importDialogVisible', false);
      loading.value = false
  }
  
  // 数据未加载出来显示加载框
  const loading = ref(false)
  
  // tab标签
  const activeName = ref('first')
  const handleClick = (tab, event) => {
          console.log(tab, event);
        }
  
  // 上传文件处理
      //   移除上传文件
  const handleRemove = (file, fileList) => {
          console.log(file, fileList);
          successData.value = []
          failData.value = []
          successNumber.value = 0
          failNumber.value = 0
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
  
      //  文件上传之前的处理
  const beforeUpload = (file) => {
      loading.value = true
      console.log(loading.value)
  
  } 
  
  
      // 文件上传成功处理
  const successData = ref([])
  const failData = ref([])
  const successNumber = ref([])
  const failNumber = ref([])
  const id = ref() //缓存编号id
  
  const handleSuccess = async (response,file, fileList) => {
      successData.value = response.data.successList
      failData.value = response.data.failList
      successNumber.value = response.data.successNumber
      failNumber.value = response.data.failNumber
      loading.value = false
      id.value = response.data.id
      console.log(response.data)
  }
      // 文件上传失败处理
  const handleError = (err, file, fileList) => {
      Message.error(
      `${file.name}上传出错,${err.message}`
    )
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
      try{
          console.log(id.value)
          const res = await exportFail(id.value)
          console.log(res)
          const url = window.URL.createObjectURL(new Blob([res.data],{ type: "application/vnd.ms-excel" }))
          const link = document.createElement('a')
          document.body.appendChild(link);
          link.href = url
          link.setAttribute('download','校验失败数据.xls')
          link.click()
          // 清除
          document.body.removeChild(link);
          window.URL.revokeObjectURL(url);
  
      } catch(error){
          Message.warning(
              `数据导入失败，${error.message}`
          )
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
  
  