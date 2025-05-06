import request from '@/utils/request1'

// 获取问卷信息列表
export function listPaper(query) {
  return request({
    url: '/questionnaire/paper/list',
    method: 'get',
    params: query,





  })
}

// 查询问卷详细信息
export function getPaper(paperId) {
  return request({
    url: '/questionnaire/paper/' + paperId,
    method: 'get',




  })
}

// 新增问卷信息
export function addPaper(data) {
  console.log(data)
  return request({
    url: '/questionnaire/paper/add',
    method: 'post',
    data: data,







  })
}

// 修改问卷信息
export function updatePaper(data) {
  console.log(data)
  return request({
    url: '/questionnaire/paper/edit',
    method: 'put',
    data: data,





  })
}

// 删除问卷信息
export function delPaper(paperId) {
  return request({
    url: '/questionnaire/paper/' + paperId,
    method: 'delete',





  })
}
