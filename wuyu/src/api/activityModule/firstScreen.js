import request from '@/utils/request';

// 获取首屏展示数据
export function getFirstScreenData() {
    return request({
      url: '/api/firstScreen/getFirstScreenData',
      method: 'get',
    })
}

// 获取滚动轮播图
export function getBannerPic(){
    return request({
        url: '/api/firstScreen/getAllActPic',
        method: 'get'
    })
}

// 获取卡片数据
export function getCardData(){
    return request({
        url: '/api/firstScreen/getDisplayData',
        method: 'get'
    })
}