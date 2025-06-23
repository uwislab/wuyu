/**
* create by zhangxiang on 2021-01-28 10:31
* 类注释：
* 备注：
*/
<template>
  <el-switch
      v-model="formData[item.prop]"
      :width="item.width"
      :active-color="item.activeColor"
      :inactive-color="item.inactiveColor"
      :active-value=" item.activeValue"
      :inactive-value="item.inactiveValue"
      :active-text="item.activeText"
      :inactive-text="item.inactiveText"
      :disabled="item.disabled"
      :style="item.style"
      @change="change">
  </el-switch>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "mElSwitch",
  mixins: [mixins],
  components: {},
  props: {},
  data() {
    return {}
  },
  computed: {},
  methods: {
    change() {
      this.mixinEvent({
        type: 'change',
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