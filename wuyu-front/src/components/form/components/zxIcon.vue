/**
* create by zhangxiang on 2021-02-03 10:37
* 类注释：
* 备注：
*/
<template>
  <i v-if="item.elementUi" :class="item.class"></i>
  <i v-else class="iconfont" :class="item.class"></i>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "zxIcon",
  mixins: [mixins],
  components: {},
  props: {},
  data() {
    return {}
  },
  computed: {},
  methods: {},
  activated() {
  },
  mounted() {
  },
  created() {
  }
}
</script>

<style scoped lang="less" rel="stylesheet/less">

</style>