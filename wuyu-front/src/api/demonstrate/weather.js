// src/utils/weather.js
const API_KEY = 'd1f1e40002524874b27188d57349f3b0'; // 和风天气API key

// 根据经纬度获取位置ID
export async function getLocationId(latitude, longitude) {
  try {
    const response = await fetch(`https://geoapi.qweather.com/v2/city/lookup?location=${longitude},${latitude}&key=${API_KEY}`);
    const data = await response.json();

    if (data.code === '200' && data.location && data.location.length > 0) {
      return data.location[0].id;
    } else {
      throw new Error(`获取位置ID失败: ${data.code} - ${data.message || '未知错误'}`);
    }
  } catch (error) {
    console.error('获取位置ID失败:', error);
    throw error;
  }
}

// 根据位置ID获取天气信息
export async function getWeatherNow(locationId) {
  try {
    const response = await fetch(`https://devapi.qweather.com/v7/weather/now?location=${locationId}&key=${API_KEY}`);
    const data = await response.json();

    if (data.code === '200') {
      return data.now;
    } else {
      throw new Error(`获取天气失败: ${data.code} - ${data.message || '未知错误'}`);
    }
  } catch (error) {
    console.error('获取天气信息失败:', error);
    throw error;
  }
}

// 获取用户地理位置
export function getUserLocation() {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => resolve(position.coords),
        error => reject(error)
      );
    } else {
      reject(new Error('浏览器不支持地理位置获取'));
    }
  });
}

// 默认城市ID（北京）
export const DEFAULT_CITY_ID = '101010100';

// 天气图标映射表
export const WEATHER_ICON_MAP = {
  '晴': 'el-icon-sunny',
  '多云': 'el-icon-cloudy',
  '阴': 'el-icon-cloudy',
  '小雨': 'el-icon-light-rain',
  '中雨': 'el-icon-light-rain',
  '大雨': 'el-icon-heavy-rain',
  '暴雨': 'el-icon-heavy-rain',
  '雷阵雨': 'el-icon-heavy-rain',
  '小雪': 'el-icon-snow',
  '中雪': 'el-icon-snow',
  '大雪': 'el-icon-snow',
  '暴雪': 'el-icon-snow',
  '雾': 'el-icon-cloudy',
  '沙尘暴': 'el-icon-warning',
  '浮尘': 'el-icon-warning',
  '扬沙': 'el-icon-warning',
  '强沙尘暴': 'el-icon-warning',
  '阵雪': 'el-icon-snow',
  '毛毛雨': 'el-icon-light-rain',
  '雨': 'el-icon-light-rain',
  '雪': 'el-icon-snow'
};
