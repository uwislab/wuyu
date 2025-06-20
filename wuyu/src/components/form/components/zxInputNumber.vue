/**
* create by zhangxiang on 2021-01-27 18:32
* 类注释：
* 备注：
*/
<template>
  <el-input-number
      v-model="formData[item.prop]"
      :disabled="item.disabled"
      :controls-position="item.controlsPosition"
      style="width: 100%"
      :style="item.style"
      :class="item.class"
      :min="item.min"
      :max="item.max"
      :step="item.step"
      :step-strictly="item.stepStrictly"
      :precision="item.precision"
      :controls="item.controls"
      :placeholder="item.placeholder"
      @change="change"
      @blur="blur"
      @focus="focus">
  </el-input-number>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "mElInputNumber",
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
    blur() {
      this.mixinEvent({
        type: 'blur',
        prop: this.item.prop,
        value: this.formData[this.item.prop]
      })
    },
    focus() {
      this.mixinEvent({
        type: 'focus',
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
    if (!this.formData[this.item.prop]) {
      this.$set(this.formData, this.item.prop, 1)
    }
  }
}
</script>

<style scoped lang="less" rel="stylesheet/less">

</style>