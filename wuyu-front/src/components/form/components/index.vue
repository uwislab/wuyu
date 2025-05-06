/**
* create by zhangxiang on 2021-01-27 18:59
* 类注释：
* 备注：
*/
<template>
  <component
      :is="`zx${item.eType}`"
      :item="item"
      :form-data="formData"
      @event="event"
  ></component>
</template>

<script type="text/ecmascript-6">
import zxInput from "./zxInput";
import zxButton from "./zxButton";
import zxRadio from "./zxRadio";
import zxRadioButton from "./zxRadioButton";
import zxCheck from "./zxCheck";
import zxCheckButton from "./zxCheckButton";
import zxInputNumber from "./zxInputNumber";
import zxSelect from "./zxSelect";
import zxDatePicker from "./zxDatePicker";
import zxTimePicker from "./zxTimePicker";
import zxSwitch from "./zxSwitch";
import zxSlider from "./zxSlider";
import zxSpan from "./zxSpan";
import zxRate from "./zxRate";
import zxIcon from "./zxIcon";
import zxCascader from "./zxCascader";
import zxColorPicker from "./zxColorPicker";
import zxEditor from "./zxEditor";
import zxTag from "./zxTag";
import zxTable from './zxTable'

export default {
  name: "index",
  components: {
    zxInput,
    zxButton,
    zxRadio,
    zxRadioButton,
    zxCheck,
    zxCheckButton,
    zxInputNumber,
    zxSelect,
    zxDatePicker,
    zxTimePicker,
    zxSwitch,
    zxSlider,
    zxSpan,
    zxRate,
    zxIcon,
    zxCascader,
    zxColorPicker,
    zxEditor,
    zxTag,
    zxTable
  },
  props: {
    item: {},
    formData: {},
  },
  data() {
    return {}
  },
  computed: {},
  methods: {
    event(params) {
      this.$emit('event', {...params, data: this.formData})
    }
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
