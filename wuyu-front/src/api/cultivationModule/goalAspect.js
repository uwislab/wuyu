import request from '@/utils/request'

export default {
  info(obj) {
    return request({
      url: `/api/goalAspect/info`,
      method: 'post',
      data: obj
    })
  },
  //增加维度和内容，二级和三级标题请求方法
  insert(obj) {
    return request({
      url: `/api/goalAspect/insert`,
      method: 'post',
      data: obj
    })
  },

  delete(obj, child) {
    return request({
      url: `/api/goalAspect/delete/${child}`,
      method: "post",
      data: obj,
    });
  },


  paging(obj) {
    return request({
      url: `/api/goalAspect/paging`,
      method: 'post',
      data: obj
    })
  },
  //插入子节点（三级以后）
  insertchild(data) {
    return request({
      url: `/api/goalAspect/insertchild`,
      method: "post",
      data,
    });
  },
  //更新三级子节点以后
  updateChild(data) {
    return request({
      url: "/api/goalAspect/updatechild",
      method: "post",
      data,
    });
  },
  //更新二级节点
  update(data) {
    return request({
      url: "/api/goalAspect/update",
      method: "post",
      data,
    });
  },
};
