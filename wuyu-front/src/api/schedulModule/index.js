import request from '@/utils/request'

export function getLessonPageAPI(params) {
  return request({
    url: '/lesson/page',
    method: 'get',
    params: params,
  })
}

export function addLessonAPI(lesson) {
  return request({
    url: '/lesson/add',
    method: 'post',
    data: lesson,
  })
}

export function deleteLessonAPI(ids) {
  return request({
    url: '/lesson',
    method: 'delete',
    data: ids,
  })
}

export function updateLessonAPI(lesson) {
  return request({
    url: `/lesson/${lesson.id}`,
    method: 'put',
    data: lesson
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

// 复制上学期排课
export function copyLastSemesterSchedule() {
  return request({
    url: '/lesson/copyLastSemester',
    method: 'post'
  })
}

