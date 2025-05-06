/**
* create by zhangxiang on 2021-01-26 15:21
* 类注释：      form二次封装
* noShow      设置该条是否展示
* noFormItem  设置是否有label
* needToast   form校验是否需要Toast提示
* canDraggable 是否可拖拽排序
* 备注：
*/
<template>
  <el-form
      ref="elForm"
      :model="formData"
      :label-suffix="labelSuffix"
      :status-icon="statusIcon"
      :size="size"
      :disabled="disabled"
      :inline="isInline"
      :label-width="labelWidth"
      :label-position="labelPosition"
      :rules="rules">
    <el-row
        v-for="(items,index) in formCols"
        :key="index"
        :gutter="items&&items.gutter?items.gutter:0">
      <template v-if="canDraggable">
        <vuedraggable :scroll="true" chosenClass="chosen">
          <transition-group>
            <el-col
                v-for="item in items"
                v-if="!item.noShow"
                :span="item.span"
                :key="item.prop"
                :offset="item.offset"
                @click.native="formItemClick(item)"
                @dblclick.native="formItemDbClick(item)">
              <el-form-item
                  :label="item.noFormItem?'':item.label"
                  :prop="item.prop"
                  :label-width="item.noFormItem?'0':labelWidth">
                <slot v-if="item.eType==='slot'" :name="item.slotName" :data="{...item,...formData}"></slot>
                <m-element v-else :item="item" :form-data="formData" @event="event"></m-element>
              </el-form-item>
            </el-col>
          </transition-group>
        </vuedraggable>
      </template>
      <template v-else>
        <el-col
            v-for="item in items"
            :span="item.span"
            v-if="!item.noShow"
            :key="item.prop"
            :offset="item.offset"
            @click.native="formItemClick(item)"
            @dblclick.native="formItemDbClick(item)">
          <el-form-item
              :label="item.noFormItem?'':item.label"
              :prop="item.prop"
              :label-width="item.noFormItem?'0':labelWidth">
            <slot v-if="item.eType==='slot'" :name="item.slotName" :data="{...item,...formData}"></slot>
            <m-element v-else :item="item" :form-data="formData" @event="event"></m-element>
          </el-form-item>
        </el-col>
      </template>
    </el-row>
  </el-form>
</template>

<script type="text/ecmascript-6">
import mElement from './components'
import {Message} from 'element-ui'
import vuedraggable from 'vuedraggable'

export default {
  name: "zxForm",
  components: {
    mElement,
    vuedraggable
  },
  props: {
    //是否可拖拽排序
    canDraggable: {type: Boolean, default: false},
    disabled: {type: Boolean, default: false},
    needToast: {type: Boolean, default: false},
    labelPosition: {type: String, default: "right"},
    formData: {type: Object, default: null},
    rules: {type: Object, default: null},
    formCols: {type: Array, default: () => [[]]},
    isInline: {type: Boolean, default: false},
    statusIcon: {type: Boolean, default: false},
    size: {type: String, default: 'medium'},
    labelSuffix: {type: String, default: '：'},
    labelWidth: {type: String, default: '120px'},
  },
  watch: {
    formCols: {
      handler() {
        this.formCols.forEach(items => {
          items.forEach(item => {
            if (item.noShow)
              if (item.eType === 'Check' || item.eType === 'CheckButton') {
                this.formData[item.prop] = []
              } else {
                delete this.formData[item.prop]
              }
          })
        })
      },
      deep: true
    },
  },
  methods: {
    //提交校验
    submit() {
      return new Promise((resolve) => {
        this.$refs['elForm'].validate((valid, message) => {
          if (valid)
            resolve(valid)
          else if (this.needToast) {
            Message({
              type: "error",
              message: message[Object.keys(message)[0]][0].message ? message[Object.keys(message)[0]][0].message : "校验失败"
            })
          }
        });
      })
    },
    //重置
    reset() {
      this.$refs['elForm'].resetFields();
      this.formCols.forEach(items => {
        items.forEach(item => {
          if (item.eType === 'Check' || item.eType === 'CheckButton') {
            this.formData[item.prop].length = 0
          } else {
            delete this.formData[item.prop]
          }
        })
      })
    },
    formItemClick(item) {
      this.$emit('formItemClick', item)
    },
    formItemDbClick(item) {
      this.$emit('formItemDbClick', item)
    },
    //所有change以及click事件
    event(params) {
      this.$emit('event', params)
      console.log('event', JSON.stringify(params))
      if (params.prop === "submit") {
        this.submit().then(res => {
          this.$emit('submit')
        })
      } else if (params.prop === "reset") {
        this.reset()
        this.$emit('reset')
      }
    },
    //设置是否展示
    setNoShow(prop, value) {
      this.setNewValue(prop, "noShow", value)
    },
    //设置radio checkbox options值
    setOptions(prop, value) {
      this.setNewValue(prop, "options", value)
    },
    /**
     * 改变二维数组中某个值
     * @param prop        数组中唯一标识值
     * @param key         需要修改的字段
     * @param value        需要修改的值
     */
    setNewValue(prop, key, value) {
      let two = -1;
      let one = this.formCols.findIndex(item => {
        let iIndex = item.findIndex(iItem => {
          return iItem.prop === prop
        })
        if (iIndex !== -1) {
          two = iIndex
          return true
        }
      })
      if (one === -1 || two === -1) {
        console.log('找不到要设置的字段')
        return
      }
      this.$set(this.formCols[one][two], key, value)
    }
  },
}
</script>

<style>
.chosen {
  background-color: #eaffea !important;
}
</style>
