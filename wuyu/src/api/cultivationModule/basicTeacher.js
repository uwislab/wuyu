import request from '@/utils/request'

export default {
  list(obj) {
    return request({
      url: `/api/basicTeacher/list`,
      method: 'post',
      data: obj
    })
  },
}
