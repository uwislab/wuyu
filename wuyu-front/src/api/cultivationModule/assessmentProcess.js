import request from '@/utils/request'

// 获取量表考核页数据

const api = {

  // 量表列表
  getSCaleList(params) {
    // return request.get('http://10.0.0.169:33380/Total/page', { params: params })
    return request.get('http://36.111.68.174:33380/Total/page', { params: params })
  },

  getExamineSCaleList(params) {
    return request.get('http://36.111.68.174:33380/Find/page', { params })
  },

  delListScale(id) {
    return request.post(`http://36.111.68.174:33380/Find/delete/?id=${id}`);
  },

  getScaleDetaile(params) {
    return request.get('http://36.111.68.174:33380/Find/look', { params: params })
  },

  commitList(params) {
    return request.post('http://36.111.68.174:33380/Find/add', params)
  },

  commitEdit(params) {
    return request.post('http://36.111.68.174:33380/Find/change', params)
  },
  // 商品搜索
  /*
  params:{
    search:{

    }
  }
  */
  login(params) {
    return request.post(base.login, params)
  },

}
//单导出
export const permission = (params) => {
  return request.get(base.permission, { params })
}



export default api;
