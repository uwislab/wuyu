import request from '@/utils/request'
import axios from 'axios'


export function fetchList(query) {
  return axios({
    url: '/api/teaching_program/list',
    method: 'get',
    params: query,


  })
}


export function changeStatus(id, status) {
  return request({
    url: '/education/updateStatus',
    method: 'post',
    params: { id, status },


  })
}

export function fetchArticle(id) {
  return request({
    url: '/vue-element-admin/article/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/vue-element-admin/article/pv',
    method: 'get',
    params: { pv }
  })
}

export function createArticle(data) {
  return request({
    url: '/education/insert',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/education/update',
    method: 'post',
    data
  })
}

export function deleteById(id) {
  return request({
    url: '/education/delete',
    method: 'post',
    params: { id },
  })
}

export function getImgList(fk_id) {
  return request({
    url: '/img/findImg',
    params: { fk_id },
    method: 'get',




  })
}

export function delImg(id) {
  return request({
    url: '/img/delImg',
    params: { id },
    method: 'post',




  })
}

export function findTheme() {
  return request({
    url: '/theme/findActive',
    method: 'get',




  })
}
