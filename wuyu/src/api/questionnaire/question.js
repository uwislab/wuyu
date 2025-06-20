import request from '@/utils/request1'

// 预览题目管理列表
export function listQuestion(query) {
  return request({
    url: '/questionnaire/question/list',
    method: 'get',
    params: query,




  })
}


// 答题界面管理列表
export function listQuestion1(query) {
  return request({
    url: '/questionnaire/question/list2',
    method: 'get',
    params: query,




  })
}

// 统计
export function listCount(query) {
  return request({
    url: '/questionnaire/question/listCount',
    method: 'get',
    params: query,




  })
}

// 查询题目管理详细
export function getQuestion(questId) {
  return request({
    url: '/questionnaire/question/' + questId,
    method: 'get',



  })
}

// 新增题目管理
export function addQuestion(data) {
  return request({
    url: '/questionnaire/question',
    method: 'post',
    data: data,




  })
}

// 修改题目管理
export function updateQuestion(data) {
  return request({
    url: '/questionnaire/question',
    method: 'put',
    data: data,



  })
}

// 删除题目管理
export function delQuestion(questId) {
  return request({
    url: '/questionnaire/question/' + questId,
    method: 'delete',



  })
}
