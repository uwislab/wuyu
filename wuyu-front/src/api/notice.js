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