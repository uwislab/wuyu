/**
* create by zhangxiang on 2021-02-22 17:39
* 类注释：
* 备注：
*/
<template>
  <div class="zxTag">
    <el-tag
        v-for="tag in options"
        :key="tag.label"
        :type="item.type"
        :closable="item.closable"
        :disable-transitions="item.disableTransitions"
        :hit="item.hit"
        :color="item.color"
        :size="item.size"
        :effect="item.effect"
        @close="close(tag)"
        @click="click(tag)"
    >
      {{ tag.label }}
    </el-tag>
  </div>
</template>

<script type="text/ecmascript-6">
import mixins from '../mixins'

export default {
  mixins: [mixins],
  name: 'zxTag',
  computed: {
    options() {
      if (this.item.options instanceof Array) {
        return this.item.options
      } else {
        let list = this.item.options.split(',')
        return list.map(item => {
          return { value: item, label: item }
        })
      }
    }
  },
  methods: {
    close(item) {
      this.$emit('event', {
        type: 'close',
        prop: this.item.prop,
        value: item
      })
    },
    click(item) {
      this.$emit('event', {
        type: 'click',
        prop: this.item.prop,
        value: item
      })
    }
  }
}
</script>

<style scoped>
.el-tag {
  margin-right: 10px;
}
</style>
