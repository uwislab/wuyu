/**
* create by zhangxiang on 2021-01-27 18:41
* 类注释：
* 备注：
*/
<template>
  <el-select
      style="width: 100%"
      v-model="formData[item.prop]"
      :placeholder="item.placeholder"
      :class="item.class"
      :style="item.value"
      :allow-create="item.allowCreate"
      :filterable="item.filterable"
      :clearable="item.clearable"
      :disabled="item.disabled"
      :multiple="item.multiple"
      :multiple-limit="item.multipleLimit"
      @change="change">
    <el-option
        v-for="option in options"
        :key="option.value?option.value:option"
        :label="option.label?option.label:option"
        :value="option.value?option.value:option"
        :disabled="option.disabled">
    </el-option>
  </el-select>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "mElSelect",
  mixins: [mixins],
  components: {},
  props: {},
  data() {
    return {}
  },
  computed: {
    options() {
      if (this.item.options instanceof Array) {
        return this.item.options
      } else {
        let list = this.item.options.split(',')
        return list.map(item => {
          return {value: item, label: item}
        })
      }
    },
  },
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