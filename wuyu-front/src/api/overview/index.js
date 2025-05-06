import request from "@/utils/request";

export function getClassScore(data) {
  return request({
    url: "/overallOperation/classInfo",
    method: "get",
    params: data,
  });
}

export function getClassRate(data) {
  return request({
    url: "/overallOperation/studentRatio",
    method: "get",
    params: data,
  });
}

export function getStudentSorce(data) {
  return request({
    url: "/overallOperation/studentScore",
    method: "get",
    params: data,
  });
}

export function getStudentRate(data) {
  return request({
    url: "/overallOperation/studentEnter",
    method: "get",
    params: data,
  });
}

export function updateStu(data) {
  return request({
    url: "/overallOperation/studentUpdate",
    method: "post",
    data: data,
  });
}