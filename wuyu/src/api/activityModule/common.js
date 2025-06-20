import request from '@/utils/request';

// 班级信息获取
export function getClassInfo(grade) {
    return request({
      url: '/api/manageAct/getClassInfo?grade=' + grade,
      method: 'get',
    })
}

// 老师信息获取
export function getTeacherInfo() {
    return request({
      url: '/api/manageAct/getTeacherInfo',
      method: 'get',
    })
}