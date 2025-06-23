import request from "@/utils/request";
import { baseUrl } from "../baseapi";
//获取所有体育成绩
export function getAllSportScore(){
    return request({
      url:'/sportScore/getAllScores',
      method:'get',
      baseURL: `${baseUrl}`,
    })
}
// 查询体育成绩
export function getSportsForSearch(params){
  return request({
    url:'/sportScore/getSportsForSearch',
    method:'get',
    params,
    baseURL: `${baseUrl}`,
  })
}
//删除体育成绩
export function deleteSportScore(params){
  return request({
    url:'/sportScore/deleteSportScore',
    method:'delete',
    params,
    baseURL: `${baseUrl}`,
  })
}
//添加体育成绩
export function addSportScore(params){
  return request({
    url:'/sportScore/addSportScore',
    method:'post',
    data:{...params},
    baseURL: `${baseUrl}`,
  })
}
//修改体育成绩
export function editSportScore(params){
  return request({
    url:'/sportScore/editSportScore',
    method:'put',
    data:{...params},
    baseURL: `${baseUrl}`,
  })
}
//批量删除体育成绩
export function deleteBatch(ids){
  return request({
    url:'/sportScore/deleteBatchSportScore',
    method:'delete',
    data:ids,
    baseURL: `${baseUrl}`,
  })
}