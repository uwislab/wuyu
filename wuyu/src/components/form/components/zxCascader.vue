/**
* create by zhangxiang on 2021-02-03 13:39
* 类注释：
* 备注：
*/
<template>
  <el-cascader
      style="width: 100%;"
      v-model="formData[item.prop]"
      :placeholder="item.placeholder"
      :style="item.style"
      :class="item.class"
      :options="item.options"
      :disabled="item.disabled"
      :show-all-levels="item.showAllLevels"
      :separator="item.separator"
      :collapse-tags="item.collapseTags"
      :props="item.props"
      :filterable="item.filterable"
      :debounce="item.debounce"
      :filter-method="item.filterMethod"
      :clearable="item.clearable"
      @change="change"
  >
  </el-cascader>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "zxCascader",
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

<style scoped lang="less" rel="stylesheet/less"></style>
