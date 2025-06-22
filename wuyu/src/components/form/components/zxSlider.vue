/**
* create by zhangxiang on 2021-01-28 10:40
* 类注释：
* 备注：
*/
<template>
  <el-slider
      v-model="formData[item.prop]"
      :min="item.min"
      :max="item.max"
      :step="item.step"
      :disabled="item.disabled"
      :show-input="item.showInput"
      :show-input-controls="item.showInputControls"
      :show-stops="item.showStops"
      :show-tooltip="item.showTooltip"
      :range="item.range"
      :height="item.height"
      :vertical="item.vertical"
      :marks="item.marks"
      @change="change">
  </el-slider>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "mElSlider",
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