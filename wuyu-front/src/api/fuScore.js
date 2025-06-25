import request from "@/utils/request";
import {baseUrl} from "@/api/baseapi";

export default {
  //查询所有的班级
  getClass(current, limit, gradeNumber) {
    return request({
      url: `/api/class/findPage/${current}/${limit}/${gradeNumber}`,
      method: 'post',
    })
  }
}


export function getGradeFuScoreByDate(date) {
  return request({
    url:'/fuScore/getGradeFuScoreByDate',
    method:'get',
    params: {date},


  })
}
export function getClassByGrade(gradeId) {
  return request({
    url:'/fuScore/getClassByGrade',
    method:'get',
    params: {'gradeId': gradeId},


  })
}

export function getScoreByGradeAndClassAndItem(className,gradeId) {
  return request({
    url:'/fuScore/getScoreByGradeAndClassAndItem',
    method:'get',
    params: {'className': className, 'gradeId': gradeId,'itemId': 1},


  })
}

export function getScoreByClassId(classId) {
  return request({
    url:'/fuScore/getStudentScoreByClassID',
    method:'get',
    params: {classId},


  })
}


export function getGradeScore(gradeId) {
  return request({
    url:'/fuScore/getGradeScore',
    method:'get',
    params: {gradeId},


  })
}
export function getStudentInfo(studentNum) {
  return request({
    url:'/fuScore/getStudentInfo',
    method:'get',
    params: {studentNum},


  })
}
export function getStudentScore(studentName, studentId) {
  return request({
    url:'/fuScore/getStudentScore',
    method:'get',
    params: {studentName,studentId},


  })
}



export function getAllClassScore(gradeId) {
  return request({
    url:'/fuScore/getAllClassScore',
    method:'get',
    params: {gradeId},


  })
}

export function getGradeOneScoreByDate(itemId, date) {
  return request({
    url:'/fuScore/getGradeOneScoreByDate',
    method:'get',
    params: {itemId, date},


  })
}

export function getGradeFuScoreByItem(selectedItemID) {
  return request({
    url:'/fuScore/getGradeFuScoreByItem',
    method:'get',
    params: { selectedItemID },


  })
}

export function getGradeFuScoreByGradeID(gradeID, selectedItemID) {
  return request({
    url:'/fuScore/getGradeFuScoreByGradeID',
    method:'get',
    params: { gradeID, selectedItemID },


  })
}

export function getStudentsFuScoreList(gradeId, classId, date) {
  return request({
    url:'/fuScore/getStudentsFuScoreList',
    method:'get',
    params: { gradeId, classId, date },



  })
}

export function getStudentsFuScore(studentNum) {
  return request({
    url:'/fuScore/getStudentsFuScore',
    method:'get',
    params: { studentNum },



  })
}

export function getLastScore(studentNum, date) {
  return request({
    url: '/fuScore/getLastScore',
    method: 'get',
    params: {studentNum, date},



  })
}

// 获取学生个人五育成绩
export function getStudentFuScoreById(studentId) {
  return request({
    url: '/api/fuScore/getStudentFuScoreById',
    method: 'get',
    params: { studentId }
  })
}

// 获取班级五育平均成绩
export function getClassAverageScores(classId) {
  return request({
    url: '/api/fuScore/getClassAverageScores',
    method: 'get',
    params: { classId }
  })
}

export function getDateList() {
  return request({
    url:'/fuScore/getDateList',
    method:'get',



  })
}

//工程实践 古
export function getWorkList(teacher_id){
  return request({
    url:'/teacher/getWorkList',
    method:'get',
    params:{teacher_id},

  })
}

export function enterScore(data){
  return request({
    url:'/fuScore/enterScore',
    method:'post',
    data,
  })
}
