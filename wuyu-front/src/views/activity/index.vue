<template>
  <div>
    <div id="container" :style="{ height: '1500px', width: '100%' }" />
<!--    <div id="container" :style="{ height: '900px', width: '100%' }" />-->
  </div>
</template>


<script>
  import axios from 'axios';
  import echarts from 'echarts';



  export default {
    data() {
      return {

      }
    },
    mounted() {
      var dom = document.getElementById("container");
      var myChart = echarts.init(dom);
      var app = {};

      var option;

      axios.get('http://36.111.68.174:33380/api/teacher_activity/t2').
      then(response => {
        const data = response.data;
        option = {
          tooltip: {
            trigger: 'item',
            triggerOn: 'mousemove'
          },
          series: [
            {
              type: 'tree',
              id: 0,
              name: 'tree1',
              data: [data],
              top: '10%',
              left: '8%',
              bottom: '22%',
              right: '20%',
              symbolSize: 7,
              edgeShape: 'polyline',
              edgeForkPosition: '63%',
              initialTreeDepth: 3,
              lineStyle: {
                width: 2
              },
              label: {
                backgroundColor: '#fff',
                position: 'left',
                verticalAlign: 'middle',
                align: 'right'
              },
              leaves: {
                label: {
                  position: 'right',
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
        };

        if (option && typeof option === 'object') {
          myChart.setOption(option);
        }
      });
    },

    methods: {
    }
  }

</script>

