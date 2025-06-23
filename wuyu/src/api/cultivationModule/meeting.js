import request from '@/utils/request'

export default {
  insert(obj) {
    return request({
      url: `/api/meeting/insert`,
      method: 'post',
      data: obj
    })
  },
  list(obj) {
    return request({
      url: `/api/meeting/list`,
      method: 'post',
      data: obj
    })
  },
  details(obj) {
    return request({
      url: `/api/meeting/details`,
      method: 'post',
      data: obj
    })
  },
  delete(obj) {
    return request({
      url: `/api/meeting/delete`,
      method: 'post',
      data: obj
    })
  },
  paging(obj) {
    return request({
      url: `/api/meeting/paging`,
      method: 'post',
      data: obj
    })
  },
}
