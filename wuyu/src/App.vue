<template>
  <div id="app">
    <router-view />
    <el-backtop target="#app" :visibility-height="200"></el-backtop>
  </div>
</template>

<script>
!(function (t) {
  function e() {
    var e = this || self;
    (e.globalThis = e), delete t.prototype._T_;
  }
  "object" != typeof globalThis &&
  (this
    ? e()
    : (t.defineProperty(t.prototype, "_T_", {
      configurable: !0,
      get: e,
    }),
      _T_));
})(Object);
import { getToken, setToken, removeToken } from '@/utils/auth'
import 'globalthis/auto'
export default {
  name: 'App',

  destroyed() {
    sessionStorage.clear()
    removeToken()
  }


}

</script>
<style>
#app {
 /* 这里给app一个滚动效果 */
 overflow-y: scroll;
}
</style>
