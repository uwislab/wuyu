import request from '@/utils/request';

// 分页条件查询学生成绩
export function getCourseScore(formData) {
  return request({
    url: '/coursescore/get',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
//成绩录入
export function addCourseScore(formData) {
  return request({
    url: '/coursescore/add',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
//根据ids数组删除成绩
export function deleteCourseScore(ids) {
  return request({
    url: '/coursescore/deleteByIds',
    method: 'post',
    data: ids,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
//编辑成绩
export function editCourseScore(formData) {
  return request({
    url: '/coursescore/edit',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
//获取课程成绩统计数据
export function getCourseScoreStatistics() {
  return request({
    url: '/coursescore/statistics',
    method: 'get'
  })
}
