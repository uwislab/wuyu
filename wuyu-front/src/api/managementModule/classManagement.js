import request from '@/utils/request';
import { baseUrl } from '../baseapi';

// 班级对应Id接口 获取全部的班级信息
export function getAllClassInfo() {
    return request({
      url: '/api/class/getClassInfo',
      //没有后端接口/api/class/getClassInfo
      method: 'get',



    })
}

// 全部班级分页
export function getClassesByPage(params) {
    return request({
      url: '/api/class/getClassesByPage',
      method: 'get',
      params
    })
}

// 添加班级信息
export function addClass(classInfo){
  return request({
    url: '/api/class/addClass',
    method: 'post',
    data: classInfo,



  })
}

// 添加班级负责老师信息
export function addTeacherToClass(correlationList){
  return request({
    url: '/api/class/addTeacherToClass',
    method: 'post',
    data: {correlationList},



  })
}

// 编辑班级信息
export function editClass(data){
    return request({
        url: '/api/class/editClass',
        method: 'put',
        data
    })
}

// 删除班级
export function deleteClass(params){
    return request({
        url: '/api/class/deleteClass',
        method: 'delete',
        params
    })
}
