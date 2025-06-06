import request from '@/utils/request'
import axios from 'axios'

export function getLessonPageAPI(params) {
  return request({
    url: '/lesson/page',
    method: 'get',
    params: params,
  })
}

//导入课程信息
export function importExcel(params) {
  return request({
    url: '/lesson/excel/try-import',
    method: 'post',
    params: params,
    // responseType: 'blob',
  })
}

// 导入校验成功的数据
export function importSuccess(id) {
  return request({
    url: `/lesson/excel/import/${id}`,
    method: 'post',
  })
}

// 导出校验失败的数据
export function exportFail(id) {
  let xhr = new XMLHttpRequest()
  let fileName = `校验失败数据.xls` // 文件名称
  const baseUrl = 'http://localhost:9082'
  xhr.open('GET', `${baseUrl}/lesson/excel/export/fail/${id}`, true)
  xhr.responseType = 'blob'
  xhr.onload = function () {
    if (this.status === 200) {
      let type = xhr.getResponseHeader('Content-Type')
      let blob = new Blob([this.response], { type: type })
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
};


// 下载排课模板
export function downloadModel() {
  let xhr = new XMLHttpRequest()
  let fileName = `排课模板.xls` // 文件名称
  const baseUrl = 'http://localhost:9082'
  xhr.open('GET', `${baseUrl}/lesson/excel/export-template`, true)
  xhr.responseType = 'blob'
  xhr.onload = function () {
    if (this.status === 200) {
      let type = xhr.getResponseHeader('Content-Type')
      let blob = new Blob([this.response], { type: type })
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

// 导出排课数据
export function exportExcel(params) {
  let xhr = new XMLHttpRequest()
  let fileName = `排课信息.xls` // 文件名称
  const baseUrl = 'http://localhost:9082'
  xhr.open('GET', `${baseUrl}/lesson/excel/export?page=${params.page}&size=${params.size}`, true)
  xhr.responseType = 'blob'
  xhr.onload = function () {
    if (this.status === 200) {
      let type = xhr.getResponseHeader('Content-Type')
      let blob = new Blob([this.response], { type: type })
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

export function addLessonAPI(lesson) {
  return request({
    url: '/lesson/add',
    method: 'post',
    data: lesson,
  })
}

export function deleteLessonAPI(ids) {
  return request({
    url: '/lesson',
    method: 'delete',
    data: ids,
  })
}

export function updateLessonAPI(lesson) {
  return request({
    url: `/lesson/${lesson.id}`,
    method: 'put',
    data: lesson
  })
}
// 获取教师列表
export function getTeacherListAPI() {
  return request({
    url: '/teacher/getTeacherInfo',
    method: 'get'
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

// 复制上学期排课
export function copyLastSemesterSchedule() {
  return request({
    url: '/lesson/copyLastSemester',
    method: 'post'
  })
}

