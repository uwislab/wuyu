import axios from 'axios'

export function fetchAllStudentList(params) {
  return axios({
    url: '/api/studentinfo/listAll',
    method: 'GET',
    params: params,




  })
}
export function fetchStudentList(params) {
  return axios({
    url: '/api/studentinfo/list',
    method: 'GET',
    params: params,



  })
}
export function fetchAllClassList(params) {
  return axios({
    url:'/api/classinfo/getclasslist',
    method:'GET',
    params:params,



  })
}
export function deleteStudent(id) {
  return axios({
    url:'/api/studentinfo/delete/'+id,
    method:'get',



  })
}
export function createStudent(data) {
  return axios({
    url:'/api/studentinfo/add',
    method:'post',
    data:data,



  })
}
export function getStudent(id) {
  return axios({
    url:'/api/studentinfo/getstudent/'+id,
    method:'get',



  })
}
export function updateStudent(data) {
  console.log("=="+data.id)
  return axios({
    url:'/api/studentinfo/update',
    method:'post',
    params: data,




  })
}
export function updateScore(data) {
  console.log("#######################"+data.id)
  return axios({
    url:'/api/studentscore/updatescore',
    method:'post',
    params: data,




  })
}
export function addScore(params) {
  console.log("=="+params)
  return axios({
    url:'/api/studentscore/addscore',
    method:'post',
    params: params,



  })
}
  export function getScore(id) {
    console.log("=="+id)
    return axios({
      url:'/api/studentscore/getscore/'+id,
      method:'get',





    })
}
