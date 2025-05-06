import request from "@/utils/request";
import student from "../student";

import { baseUrl } from "../baseapi";
// 导出班级学生数据
export function getStudentsInClass(params) {
  let xhr = new XMLHttpRequest();
  let fileName = `${params.className}.xlsx`; // 文件名称
  xhr.open(
    "GET",
    `${baseUrl}/api/class/downloadStudentInfo?classId=${params.classId}`,
    true
  );
  xhr.responseType = "arraybuffer";
  xhr.onload = function () {
    if (this.status === 200) {
      let type = xhr.getResponseHeader("Content-Type");
      let blob = new Blob([this.response], { type: type });
      if (typeof window.navigator.msSaveBlob !== "undefined") {
        window.navigator.msSaveBlob(blob, fileName);
      } else {
        let URL = window.URL || window.webkitURL;
        let objectUrl = URL.createObjectURL(blob);
        if (fileName) {
          var a = document.createElement("a");
          if (typeof a.download === "undefined") {
            window.location = objectUrl;
          } else {
            a.href = objectUrl;
            a.download = fileName;
            document.body.appendChild(a);
            a.click();
            a.remove();
          }
        } else {
          window.location = objectUrl;
        }
      }
    }
  };
  xhr.send();
}

// 获取学生分页
export function getStudentsByPage(params) {
  return request({
    url: "/api/student/getStudentList",
    method: "get",
    params,
    baseURL: `${baseUrl}`,
  });
}

// 添加学生
export function addStudent(data) {
  return request({
    url: "/api/student/addStudent",
    method: "post",
    data,
    baseURL: `${baseUrl}`,
  });
}

// 修改学生
export function alterStudent(data) {
  return request({
    url: "/api/student/alterStudent",
    method: "put",
    data,
    baseURL: `${baseUrl}`,
  });
}

// 删除学生
export function deleteStudent(params) {
  return request({
    url: "/api/student/deleteStudent",
    method: "delete",
    params,
    baseURL: `${baseUrl}`,
  });
}
//获取所有学生
export function getAllStudent(){
  return request({
    url:'/api/student/getAllStudent',
    method:'get',
    baseURL: `${baseUrl}`,
  })
}
//批量删除
export function deleteBatch(data){
  return request({
    url:'/api/student/deleteBatch',
    method:'delete',
    data

  })
}

// 模版下载
// export function getTemplate() {
//   let xhr = new XMLHttpRequest();
//   let fileName = `学生导入模版.xlsx`; // 文件名称
//   xhr.open("GET", `${baseUrl}/api/student/getUploadTemplate`, true);
//   xhr.responseType = "arraybuffer";
//   xhr.onload = function () {
//     if (this.status === 200) {
//       let type = xhr.getResponseHeader("Content-Type");
//       let blob = new Blob([this.response], { type: type });
//       if (typeof window.navigator.msSaveBlob !== "undefined") {
//         window.navigator.msSaveBlob(blob, fileName);
//       } else {
//         let URL = window.URL || window.webkitURL;
//         let objectUrl = URL.createObjectURL(blob);
//         if (fileName) {
//           var a = document.createElement("a");
//           // safari doesn't support this yet
//           if (typeof a.download === "undefined") {
//             window.location = objectUrl;
//           } else {
//             a.href = objectUrl;
//             a.download = fileName;
//             document.body.appendChild(a);
//             a.click();
//             a.remove();
//           }
//         } else {
//           window.location = objectUrl;
//         }
//       }
//     }
//   };
//   xhr.send();
// }
export function getTemplate() {
  // return request({
  //   url: "/api/student/getUploadTemplate",
  //   method: "get",
  //   baseURL: `${baseUrl}`,
  // });
  window.open(`${baseUrl}/api/student/getUploadTemplate`);
}

// 按条件导出学生
// export function getStudentExcel(params) {
//   let xhr = new XMLHttpRequest();
//   let fileName = "studentOutput.xlsx"; // 文件名称
//   xhr.open('GET', `${baseUrl}/api/student/download?keyword=${params.keyword ? params.keyword : ""}&gender=${params.gender ? params.gender : ''}&classId=${params.classId ? params.classId : ''}`, true)
//   xhr.responseType = "blob";
//   console.log("step1")
//   xhr.onload = function () {
//     if (xhr.status === 200) {
//       let blob = new Blob([xhr.response], { type: "application/vnd.ms-excel" });
//       console.log("step2")
//       if (typeof window.navigator.msSaveBlob !== "undefined") {
//         console.log("error")
//         window.navigator.msSaveBlob(blob, fileName);
//       } else {
//         let URL = window.URL || window.webkitURL;
//         let objectUrl = window.URL.createObjectURL(blob);
//         if (fileName) {
//           var a = document.createElement("a");
//           if (typeof a.download === "undefined") {
//             window.location = objectUrl;
//           } else {
//             a.href = objectUrl;
//             a.download = "1.xlsx";
//             // a.href = window.URL.createObjectURL(blob)

//             // document.body.appendChild(a)
//             a.click();
//             // a.remove()
//             // document.body.removeChild(a)
//             // return
//           }
//         } else {
//           window.location = objectUrl;
//         }
//       }
//     }
//   };
//   xhr.send();
// }

// 按条件导出学生
export function getStudentExcel(params) {
  params.classId = parseInt(params.className)
  //   window.open(`${baseUrl}/api/student/download`);
  window.open(
    `${baseUrl}/api/student/download?keyword=${
      params.keyword ? params.keyword : ""
    }&gender=${params.gender ? params.gender : ""}&classId=${
      params.classId ? params.classId : ""
    }&gradeId=${params.gradeId ? params.gradeId : ""}`
  );
  //   xhr.responseType = "blob";
  //   const xhr = new XMLHttpRequest();
  //   xhr.open("GET", "http://49.51.69.99:9200/api/student/download", true);
  //   xhr.responseType = "blob";

  //   xhr.onload = () => {
  //     if (xhr.status === 200) {
  //       const blob = new Blob([xhr.response], {
  //         type: "application/vnd.ms-excel",
  //       });
  //       const url = window.URL.createObjectURL(blob);
  //       const a = document.createElement("a");
  //       a.href = url;
  //       a.download = studenOutput.xlsx;
  //       a.click();
  //       window.URL.revokeObjectURL(url);

  //     }
  //   };
  //   xhr.send();
}
