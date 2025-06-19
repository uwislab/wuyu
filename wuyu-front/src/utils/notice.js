/*
 * @Author: hezeliangfj
 * @Date: 2025-06-15 20:39:40
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-19 19:13:30
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\utils\notice.js
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */

import { showLoading, closeLoading } from '@/utils/loading'
import service from '@/utils/request'

// 从环境变量获取基础URL
const baseUrl = process.env.VUE_APP_DEVELOP06_API

/**
 * 通用文件导出函数
 * @param {Object} options - 导出选项
 * @param {string} options.url - 导出接口URL（不需要包含基础URL）
 * @param {Object} options.params - 请求参数
 * @param {string} options.fileName - 导出文件名
 * @param {string} options.fileType - 文件类型 (例如: 'application/octet-stream', 'application/zip')
 * @param {string} options.loadingText - 加载提示文本
 * @returns {Promise<void>}
 */
export const exportFile = async (options) => {
  const {
    url,
    params,
    fileName,
    fileType = 'application/octet-stream',
    loadingText = '正在导出，请稍候...'
  } = options

  try {
    showLoading(loadingText)

    // 构建查询字符串
    const queryString = Object.keys(params)
      .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
      .join('&')

    // 构建完整URL
    const downloadUrl = `${baseUrl}/${url}?${queryString}`
    console.log('Download URL:', downloadUrl) // 添加日志以便调试

    // 发起下载请求
    const response = await fetch(downloadUrl, {
      method: 'GET',
      headers: {
        'Accept': fileType
      }
    })

    if (!response.ok) {
      throw new Error(`下载失败，服务器返回状态码: ${response.status}`)
    }

    const arrayBuffer = await response.arrayBuffer()
    if (!arrayBuffer || arrayBuffer.byteLength === 0) {
      throw new Error('下载的文件为空')
    }

    // 检查文件大小（对于ZIP文件）
    if (fileType === 'application/zip' && arrayBuffer.byteLength < 100) {
      throw new Error('下载的文件太小，可能已损坏')
    }

    // 创建下载链接
    const blob = new Blob([arrayBuffer], { type: fileType })
    const blobUrl = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.style.display = 'none'
    a.href = blobUrl
    a.download = fileName
    document.body.appendChild(a)
    a.click()

    // 清理资源
    setTimeout(() => {
      window.URL.revokeObjectURL(blobUrl)
      document.body.removeChild(a)
      closeLoading()
    }, 1000)

    return true
  } catch (error) {
    console.error('导出失败:', error)
    closeLoading()
    throw error
  }
}

// 成绩输入归一化，最大100，最小0，非数字返回空字符串
export function normalizeScoreInput(val) {
  let num = Number(val);
  if (isNaN(num)) return '';
  if (num > 100) return 100;
  if (num < 0) return 0;
  return num;
}
