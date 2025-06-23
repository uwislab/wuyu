<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
     <el-form-item style="float: right;">
        <el-button type="primary"  @click="exportImage">导出图片</el-button>
        <el-button type="primary" @click="exportWord" :disabled="this.$route.params.preview">导出Word</el-button>
        <el-button type="info" @click="back">返回</el-button>
     </el-form-item>
     <el-form-item><div class="tree"></div></el-form-item>
    </el-form>
  </div>
</template>

<script>
import echarts from "echarts";
import goalApi from '@/api/cultivationModule/goal';
import aspectActivityApi from "@/api/cultivationModule/aspectActivity";

export default {
  name: "goalMindMap",
  data() {
    return {
      goal: {},
      ataMaps: [],
      URL_ACTION: process.env.VUE_APP_BASE_API,
      myChart: {},
      root: {},
      //表示查询通用量表
      DEFAULT_ID: false,
    }
  },

  methods: {
    initChart(root) {

      let option = {
        tooltip: {
          trigger: "item",
          triggerOn: "mousemove",
        },
        series: [
          {
            type: "tree",
            data: [root],
            top: "1%",
            bottom: "1%",
            symbolSize: 10,
            label: {
              position: "bottom",
              verticalAlign: "middle",
              align: "center",
              fontSize: 13,
              fontFamily: "PingFang SC",
            },
            leaves: {
              label: {
                position: "bottom",
                verticalAlign: "middle",
                align: "center",
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
      this.myChart.setOption(option);
    },
    /**
     * @description: 处理树形结构的节点数据
     * @param {*} id
     * @return {*}
     */
    async getGoal(id) {
      let res = await goalApi.details(id, this.DEFAULT_ID);
      if (res.code == 200) {
        this.goal = res.data.goal;
        //临时保存节点
        let rootw = res.data.root;
        let child = res.data.child;
        //总分
        let score = rootw.reduce((pre, cur) => {
          return (pre += parseInt(cur.summary));
        }, 0);
        child.forEach((item) => {
          //数据库设计是这样，不可删除此判断
          if (item.title == "二级评价指标") {
            //二级子节点的父id
            item.pid = item.gid;
            item.gid = null;
          }
        });
        let tree = this.getTreeList(child, null, []);
        // 根节点
        let root = {
          name: `${this.goal.title} (${score} 分)`,
          children: [],
        };
        rootw.forEach((item) => {
          item.children = [];
          item.name = `${item.type} (${item.summary}分)`;
          tree.forEach((i) => {
            if (i.pid == item.id) {
              item.children.push(i);
            }
          });
          root.children.push(item);
        });
        //临时存在的节点
        if (this.$route.params.preview) {
          this.$route.params.data.forEach(item=>{
            root.children.push(item)
          })
        }
        this.root = root;
        this.initChart(root);

      } else {
        this.$message.error(res.msg);
      }
    },
    /**
     * @description: 得到树形结构的数据
     * @param {*} rootlist
     * @param {*} id
     * @param {*} newArray
     * @return {*}
     */
    getTreeList(rootlist, id, newArray) {
      rootlist.forEach((item) => {
        if (item.gid == id) {
          newArray.push(item);
        }
      });
      newArray.forEach((item) => {
        item.children = [];
        let tempname = item.type || item.content;
        let score = item.summary || item.weight || 0;
        if (score == 0) {
          item.name = `${tempname} `;
        } else {
          item.name = `${tempname} (${score}分)`;
        }
        this.getTreeList(rootlist, item.id, item.children);
      });
      return newArray;
    },
    //导出评价量表脑图
    exportImage() {
      const url = this.myChart.getDataURL({
        type: "png",

        pixelRatio: 1,
        backgroundColor: "#fff",
        excludeComponents: "toolbox",
      });
      const link = document.createElement("a");
      link.download = this.goal.title;
      link.style.display = "none";
      link.href = url;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(href);
    },
    //返回功能
    back() {
      this.$router.go(-1); //返回上一步路由
      this.$store.dispatch("tagsView/delView", this.$route); //调用全局挂载的方法，关闭当前页
    },
    async exportWord() {
      const link = document.createElement("a");
      // let objectUrl = `http://49.51.69.99:9200/api/goalAspect/exportword/${this.$route.params.id}`;
      let objectUrl = `${this.URL_ACTION}/api/goalAspect/exportword/${this.$route.params.id}`;
      link.href = objectUrl;
      link.download = "下载文档";
      document.body.appendChild(link);
      link.click();
      URL.revokeObjectURL(objectUrl);
      document.body.removeChild(link);
    },
  },
  mounted() {
    this.myChart = echarts.init(document.getElementsByClassName("tree")[0]);
    const id = this.$route.params.id;
    if (id == null) {
      this.$message({
        type: "error",
        message: "数据获取异常，返回上一步",
      });
      this.back(); //返回上一步
    } else {
      this.getGoal(id);
    }
    window.addEventListener("resize", () => {
      this.myChart.resize();
    });
  },
};
</script>

<style scoped>
.el-header {
  background-color: #b3c0d1;
  line-height: 60px;
}
.el-main {
  background-color: #e9eef3;
  line-height: 160px;
}
.tree {
  height: 550px;
  width: 1230px;
  /* position: absolute;
  top: 70px; */
}
</style>
