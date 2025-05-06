import request from '@/utils/request'

export function themeList(query) {
  return request({
    url: '/theme/find',
    method: 'get',
    params: query,




  })
}

export function changeStatus(id, status) {
  return request({
    url: '/theme/updateStatus',
    method: 'post',
    params: { id, status },




  })
}


export function createArticle(data) {
  return request({
    url: '/theme/insert',
    method: 'post',




    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/theme/update',
    method: 'post',



    data
  })
}

export function findTheme() {
  return request({
    url: '/theme/find',
    method: 'get',




  })
}

export function deleteById(id) {
  return request({
    url: '/theme/delete',
    method: 'post',
    params: { id },




  })
}
