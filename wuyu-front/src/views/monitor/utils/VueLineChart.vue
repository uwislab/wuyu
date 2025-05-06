<template>

</template>





<script>
// CommitChart.js
import { Bar } from 'vue-chartjs'

export default {
  extends: Bar,
  props:['data'],
  mounted () {
    // Overwriting base render method with actual data.
    this.renderChart(this.data )
  }
}
</script>

<style scoped>

</style>