/**
* create by zhangxiang on 2021-02-01 11:22
* 类注释：
* 备注：
*/
<template>
  <el-rate
      v-model="formData[item.prop]"
      :colors="item.colors"
      :void-color="item.voidColor"
      :disabled-void-color="item.disabledVoidColor"
      :disabled-void-icon-class="item.disabledVoidIconClass"
      :max="item.max"
      :disabled="item.disabled"
      :class="item.class"
      :icon-classes="item.iconClasses"
      :void-icon-class="item.voidIconClass"
      :allow-half="item.allowHalf"
      :style="item.style"
      :texts="item.texts"
      :show-text="item.showText"
      :low-threshold="item.lowThreshold"
      @change="change">
  </el-rate>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "zxRate",
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