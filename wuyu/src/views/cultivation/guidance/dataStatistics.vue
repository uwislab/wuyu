<template>
  <div id="tree" :style="{height: '1200px', width: '100%'}"></div>
</template>

<script>
import * as echarts from 'echarts';
import guidanceProto from '@/api/cultivationModule/guidance';
import goalApi from '@/api/cultivationModule/goal';

export default {
  name: "dataStatistics",
  mounted() {
    guidanceProto.list({}).then((res) => {

      let root = {
        name: '数据统计',
        children : []
      }

      res.forEach(item => {
        let node = {
          name: item.fileName,
          children: []
        }
        item.guidanceNames.split('/').forEach(name => {
          if (name !== '') {
            node.children.push({name: name})
          }

        })

        root.children.push(node)
      })
      console.log(root)

      var chartDom = document.getElementById('tree');
      var myChart = echarts.init(chartDom);
      var option = {
        tooltip: {
          trigger: 'item',
          triggerOn: 'mousemove'
        },
        series: [
          {
            type: 'tree',
            data: [root],
            symbolSize: 20,
            label: {
              position: 'top',
              verticalAlign: 'middle',
              align: 'right',
              fontSize: 15
            },
            leaves: {
              label: {
                position: 'top',
                verticalAlign: 'middle',
                align: 'left'
              }
            },
            emphasis: {
              focus: 'descendant'
            },
            expandAndCollapse: true,
            animationDuration: 550,
            animationDurationUpdate: 750
          }
        ]
      }

      myChart.setOption(option)
    })

  }
}
</script>

<style scoped>

</style>
