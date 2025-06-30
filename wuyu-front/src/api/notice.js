/*
 * @Author: hezeliangfj 
 * @Date: 2025-06-14 13:33:22
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-18 21:58:05
 * @version: 0.0.1
 * @FilePath: \medical-demo\src\view\schedule\doctor\notice.js
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import request from '@/utils/request'

// ===== 公告管理相关接口 (来自 develop-01) =====
// 添加公告
export function addNotice(data) {
  return request({
    url: '/api/notice/add',
    method: 'post',
    data
  })
}

// 获取身份列表
export function getIdentityIds() {
  return request({
    url: '/api/notice/getIdentityIds',
    method: 'get'
  })
}

// 获取公告列表（支持分页和搜索）
export function getNoticeList(query) {
  return request({
    url: '/api/notice/list',
    method: 'get',
    params: query
  })
}

// 获取公告已读未读统计
export function getNoticeStatistics(userId, identityId) {
  return request({
    url: '/api/notice/statistics',
    method: 'get',
    params: { userId, identityId }
  })
}

// 标记公告为已读
export function markNoticeAsRead(id, userId) {
  return request({
    url: `/api/notice/read/${id}`,
    method: 'put',
    params: { userId }
  })
}

// ===== 通知册相关接口 (来自 main) =====
// 获取学生信息
export function getStudent(data) {
  return request.get('/noticeBooklet/getAllStudentAndClassAndGrade', data)
}

// 获取通知册内容
export function noticeBooklet(data) {
  return request.get('/noticeBooklet/get', data)
}

// 获取学生年级班级
export function noticeBookletStudent(data) {
  return request.get('/noticeBooklet/getAllStudentAndClassAndGrade', data)
}

// 修改学生信息
export function noticeBookletModify(data) {
  return request.put('/noticeBooklet/modify', data)
}

// 导出通知册
export function exportBooklet(data) {
  return request({
    url: '/noticeBooklet/word/generate',
    method: 'get',
    responseType: 'arraybuffer', // 强制声明二进制类型
    params: data,
    headers: {
      'Accept': 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    },
    transformResponse: [] // 禁用所有转换逻辑
  });
}

// 预览通知册
export function previewNoticeBooklet(data) {
  return request.get('/noticeBooklet/word/generateHTML', data)
}

// 批量导出通知册
export function exportZip(params) {
  return request({
    url: '/export/zip',
    method: 'get',
    params,
    responseType: 'blob'
  });
}