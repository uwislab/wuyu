/**
* create by zhangxiang on 2021-02-01 10:37
* 类注释：
* 备注：
*/
<template>
    <span :class="item.class" :style="item.style">
        {{ item.formatter && item.formatter(formData[item.prop]) || formData[item.prop] }}
    </span>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "zxSpan",
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