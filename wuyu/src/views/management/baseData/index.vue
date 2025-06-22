<template>
  <div class="app-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <hands theme="outline" size="40" fill="#29eb42" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">学生数</div>
            <count-to
              :start-val="0"
              :end-val="panel.studentNum"
              :duration="2600"
              class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <peoples-two theme="outline" size="40" fill="#2971eb" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">教师数</div>
            <count-to
              :start-val="0"
              :end-val="panel.teacherNum"
              :duration="3000"
              class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <preschool theme="outline" size="40" fill="#eb8e29" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">班级数</div>
            <count-to
              :start-val="0"
              :end-val="panel.classNum"
              :duration="3200"
              class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <branch theme="outline" size="40" fill="#19ca99" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">年级数</div>
            <count-to
              :start-val="0"
              :end-val="panel.gradeNum"
              :duration="3600"
              class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
    </el-row>
    <div id="tree" :style="{ height: '1200px', width: '100%' }" />
  </div>
</template>

<script>
import echarts from "echarts";
import { getTopology, getPanelData } from "@/api/managementModule/dataBase";
import { Preschool, Hands, PeoplesTwo, Branch } from "@icon-park/vue";
import CountTo from "vue-count-to";

export default {
  data() {
    return {
      panel: {
        studentNum: 1220,
        teacherNum: 100,
        classNum: 20,
        gradeNum: 6,
      },
    };
  },
  components: {
    Preschool,
    Hands,
    PeoplesTwo,
    Branch,
    CountTo,
  },
  computed: {
    schoolId() {
      return JSON.parse(window.localStorage.getItem("UserInfo")).schoolId;
    },
  },
  mounted() {
    const dom = document.getElementById("tree");
    const myChart = echarts.init(dom);
    const app = {};
    let option;
    let data = {};

    console.log(this.schoolId);

    getPanelData({ schoolId: this.schoolId }).then((res) => {
      console.log(res.data);
      this.panel = res.data.panel;
    });

    getTopology({ schoolId: this.schoolId }).then((res) => {
      data = res.data;
      option = {
        tooltip: {
          trigger: "item",
          triggerOn: "mousemove",
        },
        backgroundColor: "rgb(16, 12, 42)",
        legend: {
          textStyle: {
            fontSize: 28,
            color: "#fff",
          },
        },
        series: [
          {
            type: "tree",
            id: 0,
            backgroundColor: "#1b1b1b",
            color: {
              type: "linear",
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                {
                  offset: 0,
                  color: "red",
                },
                {
                  offset: 1,
                  color: "blue",
                },
              ],
              global: false,
            },
            name: "基础信息总览",
            data: [data],
            top: "1%",
            left: "7%",
            bottom: "1%",
            right: "20%",
            symbolSize: 7,
            label: {
              position: "left",
              verticalAlign: "middle",
              backgroundColor: "transparent",
              fontSize: 20,
              color: "#fff",
              align: "right",
            },
            leaves: {
              label: {
                position: "right",
                verticalAlign: "middle",
                align: "left",
              },
            },
            emphasis: {
              focus: "descendant",
            },
            expandAndCollapse: true,
            animationDuration: 550,
            animationDurationUpdate: 750,
          },
        ],
      };
      myChart.setOption(option);
    });

    window.addEventListener("resize", () => {
      myChart.resize();
    });
  },
  beforeDestroy() {
    window.removeEventListener("resize", () => {
      myChart.resize();
    });
  },
};
</script>

<style lang="scss">
.block {
  padding: 8px 7px;
  overflow: hidden;
  border-bottom: 1px solid #eff2f6;
}
.demonstration {
  font-size: 24px;
  color: #8492a6;
  line-height: 33px;
}
.el-slider__runway.disabled .el-slider__bar {
  background-color: #1890ff;
}
.slider {
  float: right;
  width: 90%;
  margin-right: 20px;
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3;
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
