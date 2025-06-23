/**
* create by zx on 2022/2/16 09:07
* 类注释：
* 备注：
*/
<template>
  <div class="zxTable">
    <zx-table
        :is-index="true"
        :show-page="false"
        :is-selection="true"
        :table-data="item.tableData"
        :table-cols="item.tableCols">
    </zx-table>
  </div>
</template>

<script type="text/ecmascript-6">
import zxTable from "../../table/zxTable";
import mixins from "../mixins";

export default {
  name: "table",
  mixins: [mixins],
  components: {
    zxTable
  },
  props: {},
  data() {
    return {}
  },
  computed: {},
  methods: {},
  activated() {
  },
  mounted() {
  },
  created() {
  }
}
</script>

<style scoped>

</style>