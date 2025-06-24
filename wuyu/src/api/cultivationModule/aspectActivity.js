import request from '@/utils/request'

export default {
  //插入评价内容
  insert(obj) {
    return request({
      url: `/api/aspectActivity/insert`,
      method: 'post',
      data: obj
    })
  },
  //删除评价内容
  delete(id) {
    return request({
      url: `/api/aspectActivity/delete/${id}`,
      method: 'delete',
    })
  },
  paging(obj) {
    return request({
      url: `/api/aspectActivity/paging`,
      method: 'post',
      data: obj
    })
  },
  //调出word
  exportword() {
    return request({
      url: `/api/goalAspect/exportword`,
      method: 'get',
    })
  }
}
