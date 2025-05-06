import request from '@/utils/request'

export default {

  //查询所有老师创建的活动
  getActivity(current, limit, searchObj) {
    return request({
      url: `/api/teacher_activity/findPage/${current}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },

  //教师添加活动
  saveActivity (activity) {
    return request({
      url: `/api/teacher_activity/save`,
      method: 'post',
      data: activity
    })
  },

}
