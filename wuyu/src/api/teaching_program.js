import request from '@/utils/request'

export default {

  //查询所有的教案
  getTeachingProgram (current, limit, searchObj) {
    return request({
      url: `/api/teaching_program/findPage/${current}/${limit}`,
      method: 'post',
      data: searchObj //使用json
    })
  },

  //删除指定id的教案
  deleteTeachingProgram (id) {
    return request({
      url: `/api/teaching_program/${id}`,
      method: 'delete'
    })
  },

  // 批量删除
  batchRemoveTeachingProgram (idList) {
    return request({
      url: `/api/teaching_program/batchRemove`,
      method: 'delete',
      data: idList
    })
  },
  //添加教案
  saveTeachingProgram (teaching_program) {
    return request({
      url: `/api/teaching_program/save`,
      method: 'post',
      data: teaching_program
    })
  },
  //id查询教案
  getTeachingProgramById (id) {
    return request({
      url: `api/teaching_program/getById/${id}`,
      method: 'get'
    })
  },
  //修改医院设置
  updateTeachingProgram (hospitalSet) {
    return request({
      url: `api/teaching_program/updateHospitalSet`,
      method: 'post',
      data: hospitalSet
    })
  }

}
