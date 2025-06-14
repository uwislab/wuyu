import request from '@/utils/request'

//获取公告列表（支持分页和搜索）
// export function getNoticeList(query) {
//   return request({
//     url: '/api/notice/list',
//     method: 'get',
//     params: query
//   })
// }

// 添加公告
// export function addNotice(data) {
//   return request({
//     url: '/api/notice/add',
//     method: 'post',
//     data
//   })
// }

// 删除公告
export function deleteNotice(id) {
  return request({
    url: `/api/notice/${id}`,
    method: 'delete'
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

//获取身份列表
// export function getIdentityIds() {
//   return request({
//     url: '/api/notice/getIdentityIds',
//     method: 'get'
//   })
// }
