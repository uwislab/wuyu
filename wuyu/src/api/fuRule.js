import request from '@/utils/request'

export function insertRuleInfo(ruleForm) {
  return request({
    url: '/fuRule/insertRuleInfo',
    method: 'post',
    data: ruleForm,


  })
}

export function insertDetailList(isAllGrade, detailList) {
  return request({
    url: '/fuRule/insertDetailList',
    method: 'post',
    data: {isAllGrade: isAllGrade, detailList: detailList},


  })
}
