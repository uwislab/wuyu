/**
 * create by zhangxiang on 2021-01-27 17:12
 * 类注释：
 * 备注：
 */

export default {
    props: {
        item: {type: Object, default: {}},
        formData: {},
    },
    methods: {
        mixinEvent(params) {
            this.$emit('event', params)
        },
    }
}