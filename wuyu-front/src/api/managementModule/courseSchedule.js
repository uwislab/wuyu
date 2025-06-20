import request from '@/utils/request'

// 获取课程列表
export function getCourseList() {
  return request({
    url: '/api/courseSchedule/getCourseList',
    method: 'get'
  })
}

// 设置教师课程
export function setTeacherCourse(data) {
  return request({
    url: '/api/courseSchedule/setTeacherCourse',
    method: 'post',
    data
  })
}

// 复制上学期排课
export function copyLastSemesterSchedule() {
  return request({
    url: '/api/courseSchedule/copyLastSemester',
    method: 'post'
  })
}

// 获取教师课程设置
export function getTeacherCourse(teacherId) {
  return request({
    url: '/api/courseSchedule/getTeacherCourse',
    method: 'get',
    params: { teacherId }
  })
}

// 检查课程是否已有教师
export function checkCourseTeacher(courseId) {
  return request({
    url: '/api/courseSchedule/checkCourseTeacher',
    method: 'get',
    params: { courseId }
  })
} 