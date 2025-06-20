import { Loading } from 'element-ui'

let loadingInstance = null

export function showLoading(text = '加载中...') {
  loadingInstance = Loading.service({
    lock: true,
    text: text,
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.7)'
  })
  return loadingInstance
}

export function closeLoading() {
  if (loadingInstance) {
    loadingInstance.close()
    loadingInstance = null
  }
}
