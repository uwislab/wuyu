import request from "@/utils/request";

// 获取拓扑图
export function getTopology(params) {
  return request({
    url: `/api/getTopology/${params.schoolId}`,
    method: "get",
  });
}

// 获取基础信息
export function getPanelData(params) {
  return request({
    url: `/api/management/getPanelData/${params.schoolId}`,
    method: "get",
  });
}
