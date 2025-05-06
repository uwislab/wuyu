import request from '@/utils/request'


export function getlist(){
   {
    return request({
      url: `/api/goal/list`,
      method: 'get',

    })
  }
}


export default {
  insert(obj) {
    return request({
      url: `/api/goal/insert`,
      method: 'post',
      data: obj
    })
  },
  info(obj) {
    return request({
      url: `/api/goal/info`,
      method: 'post',
      data: obj
    })
  },

  //获取详细的评价量表信息
  details(id,tags=false) {
    return request({
      url: `/api/goal/details/${id}/${tags}`,
      method: 'get',
    })
  },
  //改变评价量表的状态
  changeState(obj) {
    return request({
      url: `/api/goal/changeState`,
      method: 'post',
      data: obj
    })
  },
  delete(id) {
    return request({
      url: `/api/goal/delete/${id}`,
      method: 'delete',
    })
  },
  paging(obj) {
    return request({
      url: `/api/goal/paging`,
      method: 'post',
      data: obj
    })
  },
  pagingByTn(obj){
    return request({
      url: `/api/goal/getPageByTeacher`,
      method: 'post',
      data: obj,
    })
  },
  pagingByEditor(obj){
    return request({
      url:`/api/goal/getPageByEditor`,
      method:'post',
      data:obj,
    })
  }
}
