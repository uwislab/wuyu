import request from "@/utils/request";
import { baseUrl} from "./baseapi";

//工程实践 古
//修改所有的baseurl名称

// 教师分页
export function getTeacherListByPage1(params) {
  return request({
    url: '/teacher/getTeacherByPage',
    method: 'post',
    params
  })
}

export function createTeacher(data) {
    return request({
        url:'/teacher/create',
        method:'post',
        data:data,




    })
}

export function deleteTeacher(id) {
    return request({
        url:'/teacher/delete/'+id,
        method:'get',



    })
}

export function getAllTeacher() {
  return request({
    url:'/teacher/getAllTeacher',
    method:'get',





  })
}

export function getTeacherInfo(teacher_id) {
  return request({
    url:'/teacher/getTeacherInfo',
    method:'get',
    params: {teacher_id},

    baseURL: baseUrl



  })
}

export function getTeacherByIdentity(identity) {
  return request({
    url:'/teacher/getTeacherByIdentity',
    method:'get',
    params: identity,





  })
}

export function getTeacherByClass(gradeId, classId) {
  return request({
    url:'/teacher/getTeacherByClass',
    method:'get',
    params: {gradeId, classId},





  })
}

export function getMonitorByClass(gradeId, classId) {
  return request({
    url:'/teacher/getMonitorByClass',
    method:'get',
    params: {gradeId, classId},





  })
}

export function getClassBasicInfo(teacher_id) {
  return request({
    url:'/teacher/getClassBasicInfo',
    method:'get',
    params: {teacher_id},





  })
}

export function updateTeacher(id,data) {
    return request({
        url:'/teacher/updateTeacherInfo'+id,
        method:'post',
        data:data,




    })
}

export function getAllWorkNum(){
  return request({
    url:'/teacher/getAllWork',
    method:'get',

    baseURL:baseUrl
  })
}

export function getWorkByPage(pageNum,pageSize,id,title,type){
  return request({
    url:'/teacher/getWorkByPage',
    method:'get',
    params: {pageNum, pageSize,id,title,type},

    baseURL: baseUrl
  })
}

export function deleteWork(id){
  return request({
    url:'/teacher/deleteWork',
    method:'delete',
    params: {id},

    baseURL:baseUrl
  })
}

export function updateWork(data){
  return request({
    url:'/teacher/updateWork',
    method:'post',
    data,

    baseURL:baseUrl
  })
}

export function updateTeacherInfo(data){
  return request({
    url:'/teacher/updateTeacherInfo',
    method:'post',
    data,

    baseURL:baseUrl
  })
}

export function updatePassword(data){
  return request({
    url:'/teacher/updatePassword',
    method:'post',
    data,

    baseURL:baseUrl
  })
}

export function getLesson(teacherId){
  return request({
    url:'/teacher/getLesson',
    method:'get',
    params: {teacherId},

    baseURL: baseUrl
  })
}

export function deleteBatch(data){
  return request({
    url:'/teacher/deleteBatch',
    method:'delete',
    data,

    baseURL:baseUrl
  })
}


export function addTeacher(data){
  return request({
    url:'/teacher/save',
    method:'post',
    data,

    baseURL:baseUrl
  })
}

