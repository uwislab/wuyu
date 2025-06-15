//获取公告列表（支持分页和搜索）
import request from "@/utils/request";

export function getNoticeList(query) {
  return request({
    url: '/api/notice/list',
    method: 'get',
    params: query
  })
}
