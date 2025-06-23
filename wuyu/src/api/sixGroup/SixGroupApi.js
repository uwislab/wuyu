import request from '@/utils/request'

export default {
  获取一级评价指标 () {
    return request({
      url: `/six/getFirstIndex`,
      method: 'get'
    })
  },
  获取三级评价指标 () {
    return request({
      url: `/six/get3Index`,
      method: 'get'
    })
  },
  添加指标成绩 (data) {
    return request({
      url: `/six/addIndexScore`,
      method: 'post',
      data: data
    })
  },
  计算生成图表数据 (data) {
    return request({
      url: `/six/getChartData?一级指标=${data.一级指标}`,
      method: 'get',
    })
  },
  获取体育项目 () {
    return request({
      url: `/six/getSportsItem`,
      method: 'get',
    })
  },
  分页获取表格数据 (data) {
    return request({
      url: `/six/getTableData`,
      method: 'post',
      data: data
    })
  },
  获取指标成绩列表 (data) {
    return request({
      url: `/six/getIndexScoreList`,
      method: 'post',
      data: data
    })
  },
  所有学生 () {
    return request({
      url: `/six/getAllStudent`,
      method: 'get',
    })
  },
  获取所有体育成绩() {
    return request({
      url: `/six/getAllTiScore`,
      method: 'get',
    })
  },
  获取年级班级() {
    return request({
      url: `/six/getYears`,
      method: 'get',
    })
  },

}
