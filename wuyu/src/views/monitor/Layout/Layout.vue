<template>
  <div>
    <Head/>
    <div style="display: flex">
      <Aside/>
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>
<script>
import Aside from "@/views/monitor/components/Aside";
import Head from "@/views/monitor/components/Head";
export default {
  name: "Layout",
  components:{
    Head,
    Aside
  }
}
</script>

<style scoped>

</style>
