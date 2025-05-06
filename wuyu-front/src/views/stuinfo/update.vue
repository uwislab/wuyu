<template>
  <student-detail :is-edit="true"></student-detail>
</template>

<script>
  import StudentDetail from "@/views/stuinfo/components/StudentDetail";
  export default {
    name: "updateStudent",
    components: {StudentDetail}
  }
</script>

<style scoped>

</style>
