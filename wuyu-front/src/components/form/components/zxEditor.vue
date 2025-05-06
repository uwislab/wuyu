/**
* create by zhangxiang on 2021-02-08 13:26
* 类注释：
* 备注：
*/
<template>
  <div class="zxEditor" :id="id">

  </div>
</template>

<script type="text/ecmascript-6">
import wangEditor from './wangEditor.min'
import mixins from '../mixins'

window.wangEditor = wangEditor

export default {
  name: "zxEditor",
  mixins: [mixins],
  components: {},
  props: {},
  data() {
    return {
      id: null,
      editor: null
    }
  },
  computed: {},
  watch: {},
  methods: {
    initEditor() {
      const E = window.wangEditor
      const editor = new E(`#${this.id}`)
      editor.config.height = this.item.height ? this.item.height : 300
      editor.config.zIndex = this.item.zIndex ? this.item.zIndex : 20
      editor.config.placeholder = this.item.placeholder ? this.item.placeholder : "请输入内容"
      // editor.config.menus=[];
      editor.config.showFullScreen = false
      editor.config.onchange = (newHtml) => {
        this.$set(this.formData, this.item.prop, newHtml)
        this.change()
      }
      editor.config.onchangeTimeout = 500 // 修改为 500ms
      editor.create()
      editor.txt.html(this.formData[this.item.prop]?this.formData[this.item.prop]:"")
      this.editor = editor
    },
    change() {
      this.mixinEvent({
        type: 'change',
        prop: this.item.prop,
        value: this.formData[this.item.prop]
      })
    },
  },
  mounted() {
    this.initEditor()
  },
  created() {
    this.id = "zxEditor_" + new Date().getTime()
  },
  beforeDestroy() {
    this.editor.destroy()
    this.editor = null
  },
}
</script>

<style scoped lang="less" rel="stylesheet/less">

</style>
