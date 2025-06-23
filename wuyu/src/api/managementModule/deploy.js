import request from '@/utils/request';

// 开始部署
export function startDeploy(data) {
    return request({
      url: '/api/deploy/start',
      method: 'post',
      data,
    })
}

// 监控异步任务进度接口
export function getTaskStatus(params) {
    return request({
      url: '/api/getTaskStatus',
      method: 'get',
      params
    })
}

// 一键备份数据库
export function startBackup(data) {
  return request({
    url: '/api/startBackup',
    method: 'post',
    data,
  })
}

// 一键还原本地备份数据库文件
export function recoverDataBase() {
  return request({
    url: '/api/recoverDataBase',
    method: 'get',
  })
}