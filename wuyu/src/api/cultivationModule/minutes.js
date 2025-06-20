import request from '@/utils/request'

export default {
  insert(obj) {
    return request({
      url: `/api/minutes/insert`,
      method: 'post',
      data: obj
    })
  },
  details(obj) {
    return request({
      url: `/api/minutes/details`,
      method: 'post',
      data: obj
    })
  },
  delete(obj) {
    return request({
      url: `/api/minutes/delete`,
      method: 'post',
      data: obj
    })
  },
  paging(obj) {
    return request({
      url: `/api/minutes/paging`,
      method: 'post',
      data: obj
    })
  },
}
