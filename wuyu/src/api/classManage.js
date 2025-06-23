import request from '@/utils/request'
import { baseUrl } from "@/api/baseapi";
export function getAllClassInfo() {
  return request({
    url: '/class/getAllClassInfo',
    method: 'get',

  })
}

/**
 *
 * @param classInfo
 * @returns {*}
 * zjl
 */
export function addClass(classInfo){
  return request({
    url: '/api/class/addClass',
    method: 'post',
    data: classInfo,


  })
}

// 获取班级所有学生信息
export function getStudentsByClass(gradeId, classId) {
  return request({
    url: '/api/class/getStudentsByClass',
    method: 'get',
    params: {gradeId, classId},


  })
}

export function getClassList(gradeId) {
  return request({
    url:'/api/class/getClassByGradeId',
    method:'get',
    params: {gradeId},


  })
}

//获取班级详细信息
export function getClassPartInfoByClassAndGrade(grade,className){
  return request({
    url:'/api/class/getClassPartInfoByClassNameAndGrade',
    method:'get',
    params:{grade,className},


  })
}
//更新班级详细信息
export function updateClassByInformationId(classInformationId,classIntroduction){
  return request({
    url:'/api/class/updateClassByClassInformationId',
    method:'put',
    params:{classInformationId,classIntroduction},

  })
}
//获取分页显示的学生信息
export function getStudentByPage(classId, page, pageSize){
  return request({
    url:'/api/class/getStudentByPage',
    method:'get',
    params:{classId, page, pageSize},


  })
}
//删除学生
//李江平 TODO
export function deleteStudent(studentId){
  return request({
    url:'/api/class/deleteStudent',
    method:'get',
    params: {studentId},


  })
}
// 分页获取班级信息
export function getGradeList(data) {
  return request({
    url: '/api/class/getClassListByPage',
    method: 'post',
    data,
  })
}
// 获取教师姓名列表
export function getMonitorList() {
  return request({
    url: '/api/class/getMonitorList',
    method: 'get',

  })
}

// 获取所有教师姓名列表
export function getAllTeacherList() {
  return request({
    url: '/api/class/getAllTeacherList',
    method: 'get',


  })
}
//删除班级
//李江平 TODO
export function deleteClass1(id){
  return request({
    url: `/api/class/deleteClass/${id}`,
    method: 'delete',


  })
}

//批量删除
export function deleteBatch(data){
  return request({
    url:'/api/class/deleteBatch',
    method:'delete',
    data,

  })
}
