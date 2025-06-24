import request from '@/utils/request';

// 一次性对所有参加活动的学生打分
export function scoreAllStudent(data) {
    return request({
      url: '/api/manageAct/together',
      method: 'post',
      data
    })
}

// 插入学生得分
export function insertScoreSingleStudent(data) {
  return request({
    url: '/api/insertStudentActivityScore',
    method: 'put',
    data
  })
}

export function getStudentByName(data) {
    return request({
        url: '/api/manageAct/getStudentByName',
        method: 'get',
        data
    })
}

// 修改单个学生得分
export function alterScoreSingleStudent(data) {
    return request({
      url: '/api/manageAct/alterStudentActivityScore',
      method: 'put',
      data
    })
}

// 上传学生分数 使用el-upload组件

// 导出活动所有信息包含班级学生的分数
export function getClassActivityInfo(params) {
    let xhr = new XMLHttpRequest()
    let fileName = `${params.activityId}.xlsx` // 文件名称
    xhr.open('GET', `http://36.111.68.174:33380/api/downloadActivity?activityId=${params.activityId}`, true)

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
                    // safari doesn't support this yet
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

// 下载学生活动分数上传的模版
export function getUploadScoreTemplate(params) {
    let xhr = new XMLHttpRequest()
    let fileName = `${params.activityId}.xlsx` // 文件名称
    // xhr.open('GET', `http://10.0.0.169:33380/api/scoreTemplate?activityId=${params.activityId}`, true)
    xhr.open('GET', `http://36.111.68.174:33380/api/scoreTemplate?activityId=${params.activityId}`, true)

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
                    // safari doesn't support this yet
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

// 获取基础活动模版
export function getBaseTemplate() {
    return request({
      url: '/api/getActivityTemplate',
      method: 'get',
    })
}

// 获取基础活动模版详情
export function getTemplateDetail(params) {
    return request({
      url: '/api/getBaseActivityDetail',
      method: 'get',
      params
    })
}
