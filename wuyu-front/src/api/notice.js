/*
 * @Author: hezeliangfj
 * @Date: 2025-06-14 13:33:22
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-15 18:29:49
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\api\notice.js
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import request from '@/utils/request'
// 获取学生信息
export function getStudent(data) {
  return request.get('/noticeBooklet/getAllStudentAndClassAndGrade',data)
}
// 批量导出通知册
export function exportZip(params) {
  return request({
    url: '/export/zip',
    method: 'get',
    params,
    responseType: 'blob' // 仍需要获取blob确保文件有效性
  });
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
// 导出通知册
export function exportNoticeBooklet (data) {
  return request.post('/noticeBooklet/word/generate',data)
}

// 预览通知册
export function previewNoticeBooklet (data) {
  return request.get('/noticeBooklet/word/generateHTML', data)
}
export function exportBooklet(data) {
  return request({
    url: '/noticeBooklet/word/generate',
    method: 'get',
    responseType: 'arraybuffer', // 强制声明二进制类型
    params: data,
    headers: {
      // 明确申明接受Word格式（部分框架需要）
      'Accept': 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    },
    transformResponse: [] // 禁用所有转换逻辑
  });
}
