import request from '@/utils/request';

export function getClassAndGradeAvgScore(grade, sclass) {
  return request({
    url: '/diagnose/classAndGradeAvgScore',
    method: 'get',
    params: { grade, sclass }
  });
}
