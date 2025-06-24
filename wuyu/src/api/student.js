import request from "@/utils/request";
import { baseUrl} from "./baseapi";

export default {
  //查询所有的学生
  getStudent(current, limit, searchObj) {
    return request({
      url: `/api/student/findPage/${current}/${limit}`,
      method: "post",
      data: searchObj,
    });
  },
};
export function fetchStudentList(params) {
  return request({
    url: "api/studentinfo/getstudent",
    method: "get",
    params: params,
    // baseURL: 'http://82.157.231.88:9200'

  });
}

export function fetchAllStudentList(params) {
  return request({
    url: "api/studentinfo/listAll",
    method: "get",
    params: params,
    // baseURL: 'http://82.157.231.88:9200'

  });
}

export function createStudent(data) {
  return request({
    url: "api/studentinfo/add",
    method: "post",
    data: data,
    // baseURL: "http://82.157.231.88:9200",

  });
}

export function deleteStudent(id) {
  return request({
    url: "api/studentinfo/delete/" + id,
    method: "post",
    // baseURL: "http://82.157.231.88:9200",

  });
}

export function getStudent(id) {
  return request({
    url: "api/student/" + id,
    method: "get",
    // baseURL: "http://82.157.231.88:9200",

  });
}

export function updateStudent(id, data) {
  return request({
    url: "api/student/update/" + id,
    method: "post",
    data: data,
    // baseURL: "http://82.157.231.88:9200",

  });
}
