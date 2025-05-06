import request from '@/utils/request'

export default {
  insert(obj) {
    return request({
      url: `/api/guidance/insert`,
      method: 'post',
      data: obj
    })
  },
  list(obj) {
    return request({
      url: `/api/guidance/list`,
      method: 'post',
      data: obj
    })
  },
  delete(obj) {
    return request({
      url: `/api/guidance/delete`,
      method: 'post',
      data: obj
    })
  },
  paging(obj) {
    return request({
      url: `/api/guidance/paging`,
      method: 'post',
      data: obj
    })
  },
}
