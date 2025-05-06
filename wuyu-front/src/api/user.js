import request from "@/utils/request";

export function login(data) {
  return request({
    url: "/api/mainLogin/login",
    method: "post",
    data,
  });
}

export function logout() {
  return request({
    url: "/vue-element-admin/user/logout",
    method: "post",
  });
}

export function getIdentity() {
  return request({
    url: "/api/user/getIdentity",
    method: "post",
  });
}

export function getUserInfo(userId) {
  return request({
    url: `/mainPage/${userId}`,
    method: "get",
  });
}

export function updateUserInfo(userId, data) {
  return request({
    url: `/mainPage/${userId}`,
    method: "put",
    data,
  });
}

export function updatePassword(userId, data) {
  return request({
    url: `/mainPage/${userId}`,
    method: "put",
    data,
  });
}

export function verifyOldPassword(userId, password) {
  return request({
    url: `/mainPage/${userId}`,
    method: "post",
    data: { password },
  });
}


export function searchUserPermissions(params) {
  return request({
    url: "/UserPermission/get",
    method: "post",
    params,
  });
}

export function getAllUserPermissions() {
  return request({
    url: "/UserPermission/GetAll",
    method: "post",
  });
}

export function deleteUserPermissions(ids) {
  return request({
    url: "/UserPermission/deleteByIds",
    method: "post",
    data: ids,
  });
}

export function updateUserPermission(data) {
  return request({
    url: "/UserPermission/edit",
    method: "post",
    data,
  });
}
