import request from '@/utils/request1'

// 查询答题管理列表
export function listAnswer(query) {
  return request({
    url: '/questionnaire/answer/list',
    method: 'get',
    params: query,


  })
}

// 查询答题管理详细
export function getAnswer(answerId) {
  return request({
    url: '/questionnaire/answer/' + answerId,
    method: 'get',


  })
}

// 新增答题管理
export function addAnswer(data) {
  return request({
    url: '/questionnaire/answer',
    method: 'post',
    data: data,


  })
}

// 修改答题管理
export function updateAnswer(data) {
  return request({
    url: '/questionnaire/answer',
    method: 'put',
    data: data,


  })
}

// 删除答题管理
export function delAnswer(answerId) {
  return request({
    url: '/questionnaire/answer/' + answerId,
    method: 'delete',


  })
}

// 修改题目管理
export function updQuest(data) {
  return request({
    url: '/questionnaire/question/updQuestion',
    method: 'post',
    data: data,


  })
}

//删除题目管理
export function delQuest(data) {
  return request({
    url: '/questionnaire/question/delQuest',
    method: 'delete',
    data: data,


  })
}

