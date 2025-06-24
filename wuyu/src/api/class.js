
import axios from 'axios'
import request from "@/utils/request";

export default {
  //查询所有的班级
  getClass(current, limit, gradeNumber) {
    return request({
      url: `/api/class/findPage/${current}/${limit}/${gradeNumber}`,
      method: 'post',
    })
  }
}

export function fetchClassList(params) {
    return request({
        url:'/class/list',
        method:'get',
        params:params,

    })
}

export function fetchAllClassList(params) {
    return axios({
        url:'/api/getClassInfo',
        method:'GET',

    })
}

export function createClass(data) {
    return request({
        url:'/class/create',
        method:'post',
        data:data,

    })
}

export function deleteClass(id) {
    return request({
        url:'/class/delete/'+id,
        method:'get',

    })
}

export function getClass(id) {
    return request({
        url:'/class/'+id,
        method:'get',

    })
}

export function updateClass(id,data) {
    return request({
        url:'/class/update/'+id,
        method:'post',
        data:data,

    })
}

