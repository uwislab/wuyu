import request from '@/utils/request';

// 获取班主任对应关系
export function getTeacherInfo() {
    return request({
      url: '/api/teacher/getTeacherInfo',
      method: 'get',
    })
}

// 教师分页
export function getTeacherListByPage(params) {
  return request({
    url: '/api/teacherQuery/getTeacherByPage',
    method: 'post',
    params
  })
}

// 添加教师
export function addTeacher(data){
  return request({
      url: '/teacherEdit/add',
      method: 'post',
      data
  })
}

// 修改教师
export function editTeacher(data,classIds){
  return request({
    url: '/teacherEdit/edit?classId='+classIds,
    method: 'post',
    data
  })
}

// 删除教师
export function deleteTeacher(params){
  return request({
      url: '/teacherEdit/delete?teacherId='+params,
      method: 'delete'
  })
}

// 导出教师
export function getTeacherExcel(params) {
  let xhr = new XMLHttpRequest()
  let fileName = `教师名单.xlsx` // 文件名称
  xhr.open('GET', `http://36.111.68.174:33380/api/teacher/downloadTeacherInfo?teacherName=${params.teacherName ? params.teacherName : ""}&title=${params.title ? params.title : ""}&position=${params.position ? params.position : ''}`, true)
  xhr.responseType = 'blob'
  xhr.onload = function() {
      if (this.status === 200) {
          let type = xhr.getResponseHeader('Content-Type')
          let blob = new Blob([this.response], {type: type})
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
              window.navigator.msSaveBlob(blob, fileName)
          } else {
              let URL = window.URL || window.webkitURL
              let objectUrl = URL.createObjectURL(blob)
              if (fileName) {
                  var a = document.createElement('a')
                  if (typeof a.download === 'undefined') {
                  window.location = objectUrl
                  } else {
                  a.href = objectUrl
                  a.download = fileName
                  document.body.appendChild(a)
                  a.click()
                  a.remove()
                  }
              } else {
                  window.location = objectUrl
              }
          }
      }
  }
  xhr.send()
}

//批量删除
export function deleteBatch(data){
  return request({
    url:'/api/teacher/deleteBatch',
    method:'delete',
    data

  })
}
