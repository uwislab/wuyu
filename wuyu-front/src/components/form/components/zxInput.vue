/**
* create by zhangxiang on 2021-01-27 16:57
* 类注释：
* 备注：
*/
<template>
  <el-input
      v-model="formData[item.prop]"
      :style="item.style?item.style:{width:'100%'}"
      :type="item.type"
      :placeholder="item.placeholder"
      :suffixIcon="item.suffixIcon"
      :prefixIcon="item.prefixIcon"
      :show-password="item.showPassword"
      :maxlength="item.maxlength"
      :rows="item.rows"
      :autosize="item.autosize"
      :minlength="item.minlength"
      :show-word-limit="item.showWordLimit"
      :disabled="item.disabled"
      :clearable="item.clearable"
      @change="change">
  </el-input>
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