/**
* create by zhangxiang on 2021-02-07 16:58
* 类注释：
* 备注：
*/
<template>
  <el-color-picker
      v-model="formData[item.prop]"
      :disabled="item.disabled"
      :size="item.size"
      :color-format="item.colorFormat"
      :predefine="item.predefine"
      :show-alpha="item.showAlpha"
      @change="change">
  </el-color-picker>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "zxColorPicker",
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