/**
* create by zhangxiang on 2021-01-28 10:07
* 类注释：
* 备注：
*/
<template>
  <el-time-picker
      style="width:100%"
      v-model="formData[item.prop]"
      :is-range="item.isRange"
      :clearable="item.clearable"
      :placeholder="item.placeholder"
      :picker-options="item.pickerOptions"
      :value-format="item.valueFormat"
      :align="item.align"
      :editable="item.editable"
      :arrow-control="item.arrowControl"
      :disabled="item.disabled"
      :range-separator="item.rangeSeparator"
      :start-placeholder="item.startPlaceholder?item.startPlaceholder:'开始日期'"
      :end-placeholder="item.endPlaceholder?item.endPlaceholder:'结束日期'"
      @change="change">
  </el-time-picker>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "mElTimePicker",
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