/**
* create by zhangxiang on 2021-01-28 09:31
* 类注释：type====year/month/date/dates/ week/datetime/datetimerange/daterange/monthrange
* 备注：
*/
<template>
  <el-date-picker
      style="width: 100%"
      v-model="formData[item.prop]"
      :disabled="item.disabled"
      :type="item.type"
      :editable="item.editable"
      :clearable="item.clearable"
      :value-format="item.valueFormat"
      :format="item.format"
      :align="item.align"
      :style="item.style"
      :class="item.class"
      :placeholder="item.placeholder"
      :picker-options="item.pickerOptions"
      :default-value="item.defaultValue"
      :range-separator="item.rangeSeparator"
      :start-placeholder="item.startPlaceholder ? item.startPlaceholder : '开始日期'"
      :end-placeholder="item.endPlaceholder ? item.endPlaceholder : '结束日期'"
      @change="change"
  >
  </el-date-picker>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  name: "DatePicker",
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
