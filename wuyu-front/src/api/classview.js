import axios from 'axios'
export function fetchAllViewList(params) {
  return axios({
    url: '/api/view/listViewAll',
    method: 'GET',
    params: params,
  })
}
export function deleteView(id) {
  return request({
    url:'/api/view/deleteview/'+id,
    method:'post',

  })
}
export function updateView(data) {
  console.log("====================================="+data.id)
  return axios({
    url:'/api/view/updateview',
    method:'post',
    params: data,

  })
}
export function getView(id) {
  return axios({
    url: '/api/view/getView/'+id,
    method: 'GET',

  })
}
export function fetchViewList(params) {
  return axios({
    url: '/api/view/listview',
    method: 'GET',
    params: params,

  })
}
