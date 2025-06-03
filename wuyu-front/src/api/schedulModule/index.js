import request from '@/utils/request'

export function getLessonPageAPI(params) {
  return request({
    url: '/lesson/page',
    method: 'get',
    params: params,
  })
}

// 获取教师列表
export function getTeacherListAPI() {
  return request({
    url: '/teacher/getTeacherInfo',
    method: 'get'
  })
}

// 教师分页
export function getTeacherListByPage(params) {
  return request({
    url: '/api/teacherQuery/getTeacherByPage',
    method: 'post',
    params
  })
}

