import request from "@/utils/request";

export const getStudentScore=(id,name,semester)=>{
    return request.get('/fuScore/getStudentSemesterScores',{
        params:{
            studentId:id,
            studentName:name,
            semester:semester
        }
    })
}

export const getSemesterScore=(id,name)=>{
    return request.get('/fuScore/studentScoreSemester',{
        params:{
            studentId:id,
            studentName:name
        }
    })
}

export function getSearchStudent(keyword) {
  return request.get('/fuScore/student/search',
    {
      params: {
        keyword: keyword
      }
    }
  )
}

export function getStudentSemesters(id) {
  return request.get('/fuScore/student/semesters',{
    params:{
      studentId:id
    }
  })
}