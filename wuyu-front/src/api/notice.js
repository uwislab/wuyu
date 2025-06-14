import request from '@/utils/request'
// 获取学生信息
export function getStudent(data) {
  return request.get('/noticeBooklet/getAllStudentAndClassAndGrade',data)
}
// 批量导出通知册
export function exportZip (data) {
  return request.get('/export/zip',data)
}
// 获取通知册内容
export function noticeBooklet (data) {
  return request.get('/noticeBooklet/get',data)
}
// 导出通知册
//  feature-06-lsy
// export function exportNoticeBooklet (data) {
//   return request.post('/noticeBooklet/word/generate', data)
// }
// export function exportNoticeBooklet(studentId) {
//   return request({
//     url: '/noticeBooklet/word/generate',
//     method: 'post',
//     responseType: 'blob', // 关键：声明响应类型为二进制流
//     params: { studentId } // 参数通过URL query传递
//   })
// }
export function exportNoticeBooklet (studentId) {
  return request.post(`/noticeBooklet/word/generate?studentId=${studentId}`, null, {
    responseType: 'blob'
  })
}

// 预览通知册
export function previewNoticeBooklet (data) {
  return request.get('/noticeBooklet/word/generateHTML', data)
}
=======
export function exportBooklet(data) {
  return request.post('/noticeBooklet/word/generate',data)
}
// 预览通过册
export function previewNoticeBooklet(data) {
  return request.get('/noticeBooklet/word/generateHTML',data)
}
