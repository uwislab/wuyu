import request from "@/utils/request";

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

//获取公告列表（支持分页和搜索）
export function getNoticeList(query) {
  return request({
    url: '/api/notice/list',
    method: 'get',
    params: query
  })
}
// 获取公告已读未读统计
export function getNoticeStatistics(userId,identityId) {
  return request({
    url: '/api/notice/statistics',
    method: 'get',
    params: { userId,identityId }
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

