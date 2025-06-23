/*
 * @Author: hezeliangfj
 * @Date: 2025-06-23 16:12:01
 * @LastEditors: hezeliangfj
 * @LastEditTime: 2025-06-23 16:12:16
 * @version: 0.0.1
 * @FilePath: \wuyu-front\src\utils\throttle-debounce.js
 * @Descripttion: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
// 防抖 debounce
export function debounce(fn, delay = 300) {
  let timer = null;
  return function(...args) {
    const context = this;
    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      fn.apply(context, args);
    }, delay);
  };
}

// 节流 throttle
function throttle(fn, delay = 300) {
  let last = 0;
  return function (...args) {
    const now = Date.now();
    if (now - last > delay) {
      last = now;
      fn.apply(this, args);
    }
  };
}
