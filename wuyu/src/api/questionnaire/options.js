import request from '@/utils/request1'

// 查询选项管理列表
export function listOptions(query) {
  return request({
    url: '/questionnaire/options/list',
    method: 'get',
    params: query,


  })
}

// 查询选项管理详细
export function getOptions(optId) {
  return request({
    url: '/questionnaire/options/' + optId,
    method: 'get',


  })
}

// 新增选项管理
export function addOptions(data) {
  return request({
    url: '/questionnaire/options',
    method: 'post',
    data: data,




  })
}

// 修改选项管理
export function updateOptions(data) {
  return request({
    url: '/questionnaire/options',
    method: 'put',
    data: data,




  })
}

// 删除选项管理
export function delOptions(optId) {
  return request({
    url: '/questionnaire/options/' + optId,
    method: 'delete',




  })
}
