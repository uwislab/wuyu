<template>
  <div class="app-container">
    <el-button @click="back" type="info" style="float: right;background-color: white;color: black;">
      <i class="el-icon-back" ></i>返回
    </el-button>

    <el-descriptions title="评价量表详细内容" class="descriptions">
      <el-descriptions-item label="标题">{{ scaleInfo.title }}</el-descriptions-item>
      <el-descriptions-item  id="sc_id" label="id">{{ scaleInfo.scaleId }}</el-descriptions-item>
      <el-descriptions-item label="发起人">
        <template v-if="scaleInfo.creatorId === 1">
          <el-tag type="info">老师</el-tag>
        </template>
        <template v-else-if="scaleInfo.creatorId === 100000">
          <el-tag type="info">校长</el-tag>
        </template>
        <template v-else-if="scaleInfo.creatorId === 100002">
          <el-tag type="info">学生</el-tag>
        </template>
        <template v-else>
          <el-tag type="info">管理员</el-tag>
        </template>
      </el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ scaleInfo.createDate }}</el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag v-if="scaleInfo.state===1" type="info">待审核</el-tag>
        <el-tag v-if="scaleInfo.state===2" type="success">审核通过</el-tag>
        <el-tag v-if="scaleInfo.state===3" type="danger">审核驳回</el-tag>
        <el-tag v-if="scaleInfo.state===4" type="warning">审核逾期</el-tag>
        <el-tag v-if="scaleInfo.state===5" type="info">弃用</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="量表领域">
        <el-tag v-if="scaleInfo.domain===0">无特殊领域</el-tag>
        <el-tag v-if="scaleInfo.domain===1">德育</el-tag>
        <el-tag v-if="scaleInfo.domain===2">智育</el-tag>
        <el-tag v-if="scaleInfo.domain===3">体育</el-tag>
        <el-tag v-if="scaleInfo.domain===4">美育</el-tag>
        <el-tag v-if="scaleInfo.domain===5">劳育</el-tag>
      </el-descriptions-item>

      <el-descriptions-item label="绑定年级">
        <template v-if="this.grades">{{ this.grades }}</template>
        <template v-else>无</template> 
      </el-descriptions-item>

      <el-descriptions-item label="操作">
          <el-button type="success" @click="scaleDialogVisible=true;updateExecData(scaleInfo.scaleId)">修改绑定年级</el-button>
      </el-descriptions-item>
    </el-descriptions>


    <div style="text-align:center">

    </div>


    <el-dialog :visible.sync="scaleDialogVisible" title="绑定情况" width="40%">
      <el-table :data="ExecuteData" style="width: 100%" align="center">
        <el-table-column prop="execId" label="执行id" width="200" align="center"></el-table-column>
        <el-table-column prop="gradeId" label="绑定年级" width="180" align="center"></el-table-column>
        <el-table-column label="操作" width="150"  align="center">
          <template slot-scope="scope" >
            <el-button type="danger" @click="deleteExec(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <template>
      <el-dialog :visible.sync="mindMapVisible"
                 @open="open()"
                 width="65%"
                 >
        <div id="mind_map"></div>
      </el-dialog>
    </template>
  </div>
</template>

<script>
import router from "@/router";
import {getContentById, changeState,fetchGrades,delExecution} from '@/api/fuScale';
import * as echarts from "echarts";
import Vue from "vue";
export default {
  name: "goalDetails12",
  data() {
    return {
      //执行情况 需要绑定情况
      ExecuteData:[],
      // 评价量表信息
      scaleInfo: {},
      // 原始数据
      rawData: [],
      // 标题结构数组
      structuredData: [],
      // 标题层级数组
      itemLevel: [],
      // 最终显示数据
      tableData: [],
      // 脑图所用树状数组
      treeList: [],
      // 脑图是否显示
      mindMapVisible: false,
      tableUpdate: false,
      grades: [],
      scaleDialogVisible: false,
    };
  },
  mounted (){
    this.scaleInfo = this.$route.query.row;
    let scaleId = this.scaleInfo.scaleId


    getContentById(scaleId).then(res => {
      this.rawData = res.data;
      this.rawDataChange();
    })

    fetchGrades(scaleId)
      .then(res => {
        //获取json中的所有gradeId,然后对年纪进行排序，然后转化为字符串（去中括号）

        this.grades =  res.data.map(item => item.gradeId).sort((a, b) => a - b).join(',');
      })
      .catch(error => {
        console.error('获取年级时出错:', error);
      });
  },

  methods: {
    // 数组结构转换，使表格可以动态生成列
    rawDataChange () {
      let item_num = 0;
      let rawList = this.rawData;
      let structuredData = this.structuredData;
      // 数据最大层级
      let maxLevel = 0;
      rawList.forEach(item => {
        if (maxLevel < item.itemLevel) maxLevel = item.itemLevel;
      })
      // 根据表格指标层级，插入数列
      let obj = {};
      rawList.forEach(item1 => {
        if (item1.evaluators != null && item1.itemLevel != 1) {
          Vue.set(obj, 'level'+item1.itemLevel, item1.itemContent + '(' + item1.itemScore + '分）');
          Vue.set(obj, 'evaluationMethod', item1.evaluationMethod);
          Vue.set(obj, 'evaluators', item1.evaluators);
          Vue.set(obj, 'remark', item1.remark == null?'无':item1.remark);
          // 如果当前插入的对象不是最底层，则为其添加一个空的下层属性，用于保证数组规整
          if (item1.itemLevel < maxLevel) Vue.set(obj, 'level'+(item1.itemLevel+1), '');
          this.hasPreItem(rawList, item1, obj);
          structuredData[item_num++] = obj;
          obj = {};
        }
      })
      this.structuredData = structuredData;
      this.tableData = !this.tableUpdate;
    },
    // 判断是否含有前驱节点（递归）

    updateExecData(scaleId) {
      console.log('scaleId: '+scaleId)
      fetchGrades(scaleId).then(res => {
        this.ExecuteData =res.data;
      })

    },

    deleteExec(row){
      console.log('execId: '+row.execId)
      delExecution(row.execId).then(res=> {
        if (res.code ==200){
          this.$alert('删除成功', '', {
            confirmButtonText: '确定',
            type: 'message',
          })
          // console.log('删除成功scaleId: '+this.scaleInfo.scaleId)
          this.updateExecData(this.scaleInfo.scaleId)
        }
        else {
          this.$alert('删除失败', '', {
            confirmButtonText: '确定',
            type: 'warning',
          })
        }
      })
    },
    hasPreItem (rawList, item, obj) {
      Vue.set(obj, 'level'+item.itemLevel, item.itemContent + '(' + item.itemScore + '分）');
      if (item.preItem != null) {
        rawList.forEach(item1 => {
          if (item1.itemContent == item.preItem) return this.hasPreItem(rawList, item1, obj);
        })
      }
    },
    // 将评价量表原始数组转为跨行表格数组
    contentTransform () {
      let rawList = this.rawData;
      let newList = [];

      // 上层项目名称和分数，用于子项目信息循环插入列表后保存
      let dimension = rawList[1].itemContent;
      let dScore = rawList[1].itemScore;
      rawList.forEach(rawData => {
        // 如果原数组中当前项”评价方法“不为空，则说明是最后一层，并向newList插入该条数据
        if (rawData.evaluationMethod != undefined) {
          if (rawData.preItem != dimension) {
            // 找出当前子项目所指前驱的项目名称和分数
            rawList.find(item => {
              if (item.itemContent == rawData.preItem) {
                dimension = item.itemContent;
                dScore = item.itemScore;
              }
            })
          }
          newList.push({
            dimension: dimension + '(' + dScore + '分)',
            evaluationContent: rawData.itemContent + '(' + rawData.itemScore + '分)',
            evaluationMethod: rawData.evaluationMethod,
            evaluators: rawData.evaluators,
            remark: rawData.remark == undefined?"无":rawData.remark,
          })
        }
      })

      this.tableData = newList;
    },

    // 表格跨行显示函数
    objectSpanMethod ({row, column, rowIndex, columnIndex}) {
      let tableData = this.tableData;
      // 首列项目内容记录
      let dimensionList = [];
      // 首列项目出现次数记录，即其子项目数，用于计算跨行数
      let timesList = [];
      tableData.forEach(item => {
        if (!dimensionList.includes(item.dimension)) {
          // 如果内容记录中未包含当前数据，则在记录数组中插入，并设置子项目数为1
          dimensionList.push(item.dimension);
          timesList[dimensionList.length - 1] = 1;
        } else {
          // 如果当前记录中已出现该数据，则将子项目数+1
          let index = dimensionList.indexOf(item.dimension);
          timesList[index] += 1;
        }
      })

      // 层级数组，用于判断当前项目是否跨行或隐藏
      let levelList = [0]
      for (let i = 0; i<timesList.length; i++) {
        if (i == 0)
          levelList[i] = 0;
        else if (i == 1)
          levelList[i] = timesList[i-1];
        else
          levelList[i] = timesList[i-1] + levelList[i-1];
      }

      let index = dimensionList.indexOf(row.dimension);
      // 跨越行数
      let rowspan = timesList[index];

      if (columnIndex == 0) {
        if (levelList.indexOf(rowIndex) != -1) {
          // 如果当前项目所处第一列，且行数满足跨行层次，则设置跨行
          return {
            rowspan,
            colspan: 1,
          }
        } else {
          // 否则设置为隐藏
          return {
            rowspan: 0,
            colspan: 0,
          }
        }
      }
    },

    // 生成脑图
    generateMindMap () {
      let rawData = this.rawData;
      let treeList = [];
      rawData.forEach(item1 => {
        if (item1.itemLevel == 1) {
          treeList.push({
            name: item1.itemContent,
            score: item1.itemScore,
            children: []
          })
        } else {
          treeList.find(item2 => {
            if (item1.preItem == item2.name) {
              item2.children.push({
                name: item1.itemContent,
                score: item1.itemScore,
                evaluationMethod: item1.evaluationMethod,
                evaluators: item1.evaluators
              })
            }
          })
        }
      })
      this.treeList = treeList;
      this.mindMapVisible = true;
    },

    open () {
      this.$nextTick(() => {
        //  执行echarts方法
        this.mindMapInit()
      })
    },

    mindMapInit () {
      let chartDom = document.getElementById('mind_map');
      let myChart = echarts.init(chartDom);
      let option;

      const data = {
        name: this.scaleInfo.title,
        children: this.treeList,
      };
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
            top: '0%',
            left: '25%',
            bottom: '0%',
            right: '25%',
            symbolSize: 10,
            edgeShape: 'polyline',
            toolbox:{
              show:true,
              feature: {
                saveAsImage: {
                  show: true,
                  type:'png',
                  name: '下载',
                  title:'保存为图片',
                },
              },
            },
            edgeForkPosition: '63%',
            initialTreeDepth: 3,
            lineStyle: {
              width: 2
            },
            label: {
              backgroundColor: '#fff',
              position: 'left',
              verticalAlign: 'middle',
              align: 'right',
              fontSize: 15,
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

      option && myChart.setOption(option);
    },

    // 生成word前的样式设置
    setTableStyle() {
      let tableDomList = document
        .querySelector(".table_block")
        .getElementsByTagName("table");

      // 去除element的覆盖层
      this.isPrintToPdfFlag = true;
      let headRightTh = tableDomList[0]
        .getElementsByTagName("tr")[0]
        .getElementsByTagName("th");
      headRightTh[headRightTh.length - 2].style.borderRight =
        "1px solid #ABABAB";

      // 遍历两个table
      let tableLength = tableDomList.length;
      for (let i = 0; i < tableLength; i++) {
        tableDomList[i].setAttribute("border", "1");
        tableDomList[i].style.border = "#ffffff";
        let childTr = tableDomList[i].getElementsByTagName("tr");
        let childTrLength = childTr.length;
        // 第一个table是头部，没有td会报错
        if (i > 0) {
          for (let j = 0; j < childTrLength; j++) {
            let childTd = childTr[j].getElementsByTagName("td");
            let childTdLength = childTd.length;
            // 修改最后一个的边框值
            if (childTd[childTdLength - 1].style) {
              childTd[childTdLength - 1].style.borderRight =
                "1px solid #ABABAB";
            }

            for (let k = 0; k < childTdLength; k++) {
              childTd[k].style.padding = "8px 0"; //改变之后与element高度一致
              childTd[k].style.valign = "center";
              childTd[k].style.align = "center";
              childTd[k].style.textAlign = "center";

              if (j == childTrLength - 1) {
                childTd[k].style.borderBottom = "1px solid #ABABAB";
              }
            }
          }
        } else {
          // 去除第一个table去除最右侧的空白th
          let childTr = tableDomList[i].getElementsByTagName("tr");
          let childTrLength = childTr.length;
          for (let i = 0; i < childTrLength; i++) {
            let lastTh = childTr[i].querySelector(".gutter");
            if (lastTh) {
              lastTh.parentNode.removeChild(lastTh);
            }
          }
        }
      }

      // print-table是我自己的外层id，换成你自己的id
      return document.querySelector(".table_block").outerHTML;
    },

    // 页面内容转为word
    exportFileByInnerHTML(outerHTML, typeName) {
      const dateTime = new Date();
      const dateTimeStr =
        dateTime.getFullYear() +
        "-" +
        dateTime.getMonth() +
        "-" +
        dateTime.getDay();
      let blob;
      try {
        //word文档为msword,docx为vnd,pdf文档为pdf,msexcel 为excel
        blob = new Blob([outerHTML], {
          type: "application/file"
        });
      } catch (e) {
        if (typeof console !== "undefined") {
          this.$message.error("导出失败");
          console.log(e);
        }
      }
      let objectUrl = URL.createObjectURL(blob);
      let link = document.createElement("a");
      let fname = `表格_${dateTimeStr}.${typeName}`; //下载文件的名字
      link.href = objectUrl;
      link.setAttribute("download", fname);
      document.body.appendChild(link);
      link.click();
    },

    vue_changeState(state) {
      changeState(this.scaleInfo.scaleId,state).then(res => {
        this.scaleInfo.state = state
      })
    },

    handlePass () {

    },

    back() {
      router.go(-1) //返回上一步路由
      this.$store.dispatch("tagsView/delView", this.$route) //调用全局挂载的方法，关闭当前页
    },
  }
}
</script>

<style scoped>
  #mind_map {
    width: 90%;
    height: 600px;
  }

  .descriptions{
    border: 1px solid green;
    padding: 10px;
    margin: 4% auto;
    font-size: large;
    width: 60%;
  }
</style>
