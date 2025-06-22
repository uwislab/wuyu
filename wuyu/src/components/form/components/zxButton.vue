/**
* create by zhangxiang on 2021-01-27 17:31
* 类注释：
* 备注：
*/
<template>
  <el-button
      :type="item.type"
      @click="click"
      :icon="item.icon"
      :loading="item.loading"
      :size="item.size"
      :round="item.round"
      :disabled="item.disabled"
      :style="item.style"
      :class="item.class"
      :plain="item.plain"
      :circle="item.circle "
      :autofocus="item.autofocus"
      :native-type="item.nativeType">
    {{ item.value }}
  </el-button>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  components: {},
  props: {},
  mixins: [mixins],
  data() {
    return {}
  },
  computed: {},
  methods: {
    click() {
      this.mixinEvent({
        type: 'click',
        prop: this.item.prop,
        value: this.formData[this.item.prop]
      })
    },
  },
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