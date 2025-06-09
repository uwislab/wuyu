import request from '@/utils/request'

export default {
  //学校总览
  getXxzl(params) {
    return request({
      url: `/api/demonstrate/getQxwycj`,
      method: 'get',
      params: params
    })
  },
  // 五育达成
  getWydc(params) {
    return request({
      url: `/api/demonstrate/getWydc`,
      method: 'get',
      params: params
    })
  },
  getGrade2(params) {
    return request({
      url: `/api/demonstrate/getGrade2`,
      method: 'get',
      params: params
    })
  },

  getRadarTj() {
    return request({
      url: `/api/demonstrate/getRadarTj`,
      method: 'get'
    })
  },
  getWynjbh() {
    return request({
      url: `/api/demonstrate/getLineTj`,
      method: 'get'
    })
  },
  getLineTj2() {
    return request({
      url: `/api/demonstrate/getLineTj2`,
      method: 'get'
    })
  },
  getWanchengdu() {
    return request({
      url: `/api/demonstrate/getWanchengdu`,
      method: 'get'
    })
  },

  //年级成长
  getNjcz(param) {
    return request({
      url: `/api/demonstrate/getNjcz`,
      method: 'get',
      params: param
    })
  },
  getNjqk(){
    return request({
      url: `/api/demonstrate/getNjqk`,
      method: 'post',
    })
  },
  getXYStudent(){
    return request({
      url: `/api/demonstrate/getXYStudent`,
      method: 'post',
    })
  },
  getXYClass(){
    return request({
      url: `/api/demonstrate/getXYClass`,
      method: 'post',
    })
  },
  getTable(data){
    return request({
      url: `/api/demonstrate/getTable`,
      method: 'post',
      contentType: "application/json",
      data: data
    })
  },
  // 获取学生个人五育成绩
  getStudentScore(params) {
    return request({
      url: `/diagnose/studentscore`,
      method: 'get',
      params: params
    })
  },
  // 获取班级平均五育成绩
  getClassAverageScores(params) {
    return request({
      url: `/diagnose/class/average/scores`,
      method: 'get',
      params: params
    })
  }
}
