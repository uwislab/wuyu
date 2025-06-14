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
