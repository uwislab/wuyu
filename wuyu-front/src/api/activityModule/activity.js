import request from '@/utils/request';

// 获取活动列表数据
export function getActivityList(data) {
  return request({
    url: '/api/manageAct/getActivityListByPage',
    method: 'post',
    data: JSON.stringify(data),
    headers: { 'Content-Type': 'application/json' },
  })
}

// 获取活动详情
export function getActivityDetail(params) {
  return request({
    url: '/api/manageAct/getActivityClassStudent',
    method: 'get',
    params
  })
}

// 活动照片上传 使用el-upload action为上传后端接口

// 增加活动
export function addAct(data){
    return request({
      url: '/api/manageAct/addAct',
      method: 'post',
      data
    })
}


// 活动信息编辑
export function editActivity(data){
  return request({
    url: '/api/manageAct/editActivityInfo',
    method: 'put',
    data
  })
}

// 删除活动
export function deleteActivity(params) {
    return request({
      url: '/api/manageAct/deleteActivity',
      method: 'delete',
      params
    })
  }