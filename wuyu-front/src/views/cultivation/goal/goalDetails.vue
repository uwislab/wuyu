<template>
  <div class="app-container">
    <el-button @click="back" type="info" style="float: right;background-color: white;color: black">
      <i class="el-icon-back"></i>返回
    </el-button>

    <el-descriptions title="评价量表详细内容">
      <el-descriptions-item label="标题">{{ scaleInfo.title }}</el-descriptions-item>
      <el-descriptions-item label="发起人">{{ scaleInfo.creatorId }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ scaleInfo.createDate }}</el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag v-if="scaleInfo.state === 0" type="">编辑中</el-tag>
        <el-tag v-if="scaleInfo.state === 1" type="info">待审核</el-tag>
        <el-tag v-if="scaleInfo.state === 2" type="success">审核通过</el-tag>
        <el-tag v-if="scaleInfo.state === 3" type="danger">审核驳回</el-tag>
        <el-tag v-if="scaleInfo.state === 4" type="warning">审核逾期</el-tag>
        <el-tag v-if="scaleInfo.state === 5" type="info">弃用</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="量表领域">
        <el-tag v-if="scaleInfo.domain === 0">无特殊领域</el-tag>
        <el-tag v-if="scaleInfo.domain === 1">德育</el-tag>
        <el-tag v-if="scaleInfo.domain === 2">智育</el-tag>
        <el-tag v-if="scaleInfo.domain === 3">体育</el-tag>
        <el-tag v-if="scaleInfo.domain === 4">美育</el-tag>
        <el-tag v-if="scaleInfo.domain === 5">劳育</el-tag>
      </el-descriptions-item>
    </el-descriptions>

    <el-button-group style="margin-bottom: 10px" >
      <!-- <el-button @click="generateMindMap" round>查看脑图</el-button> -->
      <el-button type="primary" @click="exportWord" round>导出为Word</el-button>
    </el-button-group>
    <el-button-group style="margin-bottom: 10px" >
      <el-button type="primary" @click="exportExcel" round>导出为Excel</el-button>
    </el-button-group>


    <div id="print-table" class="table_block" :class="{ 'printToPdf': isPrintToPdfFlag }">
      <el-table id="data_table" border :data="structuredData" row-key="itemId" lazy :load="load"
        :tree-props="{ hasChildren: 'hasChildren' }">
        <el-table-column label="评价id" prop="itemId" align="center" />
        <el-table-column label="评价内容(标题)" prop="itemContent" align="center" />
        <el-table-column label="细则层级" prop="itemLevel" align="center" :formatter="formatItemLevel" />
        <el-table-column label="评价维度分数" prop="itemScore" align="center" />
        <el-table-column label="评价方式及评价手段" prop="evaluationMethod" align="center" />
        <el-table-column label="评价者" prop="evaluators" align="center" />
        <el-table-column label="备注" prop="remark" align="center" />
      </el-table>
    </div>

    <template>
      <el-dialog :visible.sync="mindMapVisible" @open="open()" width="75%">
        <div id="mind_map"></div>
      </el-dialog>
    </template>

    <!-- 改变状态 -->
    <div>
      <div style="margin-top: 20px; text-align: center">
        <el-button v-if="scaleInfo.state != 2 && scaleInfo.state != 0" icon="el-icon-check" @click="confirmAction(0)">开放编辑</el-button>
        <el-button v-if="scaleInfo.state != 3 && scaleInfo.state != 5" type="danger" icon="el-icon-close" @click="confirmAction(3)">驳回</el-button>
        <el-button v-if="scaleInfo.state != 2 && scaleInfo.state != 5" type="success" icon="el-icon-check" @click="confirmAction(2)">通过</el-button>
        <el-button v-if="scaleInfo.state != 5" type="info" icon="el-icon-close" @click="confirmAction(5)">弃用</el-button>
        <el-button class="tips" @click="tipsDialogVisible = true" circle icon="el-icon-more"></el-button>
      </div>

    <el-dialog
      title="改变状态"
      :visible.sync="changeStateDialogVisible"
      width="30%"
      center
    >
      <div style="text-align: center;font-size: large;">
        <span>确定要将量表状态改变为
          <strong v-if="actionType == 0" style="color: blue;">编辑中</strong>
          <strong v-if="actionType == 3" style="color: red;">驳回</strong>
          <strong v-if="actionType == 2" style="color: green;">通过</strong>
          <strong v-if="actionType == 5" style="color: gray;">弃用</strong>
        吗？</span>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeStateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="changeState">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="关于量表状态"
      :visible.sync="tipsDialogVisible"
      width="30%"
      center
    >
      <span style="font-size: large;">
        量表一旦<span style="color: green;">审核通过</span>,就无法<span style="color: blue;">开放编辑</span><br>
        量表<span style="color: gray;">弃用</span>之后，只能在<span style="color: blue;">开放编辑</span>后才能审核
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="tipsDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

    </div>
  </div>
</template>

<script>
import router from "@/router";
import { getContentByIdCopy, getTreeItemList,getItemByPreCopy,changeState } from '@/api/fuScale';
import * as echarts from "echarts";
import request from '@/utils/request'
import Vue from "vue";
import $ from "@/utils/jq"; // 文件引入路径一定要正确，这是第二步创建的js文件（jq.js）
import saveAs from "file-saver/dist/FileSaver";
import "@/utils/jquery.wordexport"; // 文件引入路径一定要正确，这是第二步创建的js文件(jquery.wordexport.js)
import * as XLSX from "xlsx";
import htmlDocx from 'html-docx-js/dist/html-docx';
import FileSaver from 'file-saver';

import { changeStatus } from "@/api/article";
export default {
  name: "goalDetails",
  data() {
    return {
      // 评价量表信息
      scaleInfo: { scaleId: -1 },
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
      //确认改变状态是否显示
      changeStateDialogVisible: false,
      //提示窗口是否显示
      tipsDialogVisible: false,
      //状态类型
      actionType: null,
      // 脑图是否显示
      mindMapVisible: false,
      tableUpdate: false,
    };
  },
  mounted() {
    this.test()
    this.scaleInfo = this.$route.query.row;
    let scaleId = this.scaleInfo.scaleId;
    getContentByIdCopy(scaleId).then(res => {
      this.structuredData = res.data;
      // this.rawDataChange();
      // this.contentTransform();
    })
    //脑图数据请求
    getTreeItemList(scaleId).then(res => {
      this.rawData = res.data;
    })
  },

  methods: {
    //树形加载数据
    load(tree, treeNode, resolve) {
      getItemByPreCopy(tree.itemId).then(res => {
        resolve(res.data)
      });
    },

    confirmAction(type) {
      this.changeStateDialogVisible = true;
      this.actionType = type;
    },

    changeState(state){
      state = this.actionType;
      changeState(this.scaleInfo.scaleId,state).then(res => {
        if(res.code == 200){
          this.scaleInfo.state = state
          this.changeStateDialogVisible = false
          this.$message({
          type: 'success',
          message: '状态更改成功'
        });
        }
      })
    },

    // 数组结构转换，使表格可以动态生成列
    rawDataChange() {
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
        if (item1.evaluators != null) {
          Vue.set(obj, 'level' + item1.itemLevel, item1.itemContent + '(' + item1.itemScore + '分）');
          Vue.set(obj, 'evaluationMethod', item1.evaluationMethod);
          Vue.set(obj, 'evaluators', item1.evaluators);
          Vue.set(obj, 'remark', item1.remark == null ? '无' : item1.remark);
          // 如果当前插入的对象不是最底层，则为其添加一个空的下层属性，用于保证数组规整
          if (item1.itemLevel < maxLevel) Vue.set(obj, 'level' + (item1.itemLevel + 1), '');
          this.hasPreItem(rawList, item1, obj);
          if (item1.itemNum != 0) structuredData[item1.itemNum - 1] = obj;
          obj = {};
        }
      })
      this.structuredData = structuredData;
      this.tableData = !this.tableUpdate;
    },
    // 判断是否含有前驱节点（递归）
    hasPreItem(rawList, item, obj) {
      Vue.set(obj, 'level' + item.itemLevel, item.itemContent + '(' + item.itemScore + '分）');
      if (item.preItem != null) {
        rawList.forEach(item1 => {
          if (item1.itemContent == item.preItem) return this.hasPreItem(rawList, item1, obj);
        })
      }
    },
    // 将评价量表原始数组转为跨行表格数组
    contentTransform() {
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
            remark: rawData.remark == undefined ? "无" : rawData.remark,
          })
        }
      })

      this.tableData = newList;
    },

    // 表格跨行显示函数
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
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
      for (let i = 0; i < timesList.length; i++) {
        if (i == 0)
          levelList[i] = 0;
        else if (i == 1)
          levelList[i] = timesList[i - 1];
        else
          levelList[i] = timesList[i - 1] + levelList[i - 1];
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

    // 层级数据显示转换
    formatItemLevel(row){
      return row.itemLevel === 1 ? "一级" : row.itemLevel === 2 ? "二级" : row.itemLevel === 3 ? "三级" : "其它";
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

    // 按钮点击事件 -- 导出Word表

  //   exportWord() {
  //     this.setTableStyle();
  //     let table = document.querySelector('.table_block').outerHTML;
  //     this.exportFileByInnerHTML(table, 'doc')
  //
  //     // this.$nextTick(()=>{
  //     //   $("#data_table").wordExport("导出的内容")
  //     // })
  //     const el = document.getElementById('data_table').innerHTML;
  //     function removeLastCol(html) {
  //       const modifiedHtml = html.replace(/<col [^>]*>(?![\s\S]*<col [^>]*>[\s\S]*<\/colgroup>)/, '');
  //       return modifiedHtml;
  //     }
  //
  //     console.log(el);
  //
  //     let cssHTML = `
  //       .el-table__inner-wrapper{
  //       width: 100%;
  //       border:1px solid #ddd;
  //       }
	// 	    table {
	// 	    width: 100% !important;
	// 	    table-layout: fixed;
	// 	    margin-top:10px;
	// 	    border: 1px solid #ddd;
	// 	    border-collapse: collapse;
	// 	    }
  //       col {
  //         width: auto !important;
  //       }
	// 	    .export-tb .thead td {
	// 	    font-weight: bold;
	// 	    }
	// 	    td {
	// 	    border: 1px solid #ddd;
	// 	    color: #333;
	// 	    text-align: left;
	// 	    padding: 6px 10px;
	// 	    }
	// 	    th {
	// 	    border: 1px solid #ddd;
	// 	    color: #333;
	// 	    text-align: left;
	// 	    padding: 6px 10px;
	// 	    }`;
  //     const content = `
  //   <!DOCTYPE html>
  //   <html>
  //     <head>
  //       <meta charset="UTF-8">
  //       <style>
  //         ${cssHTML}
  //       </style>
  //     </head>
  //     <body>
  //       <table class="export-table">
  //         ${el}
  //       </table>
  //     </body>
  //   </html>
  // `;
  //
  //     let converted = htmlDocx.asBlob(content);
  //     FileSaver.saveAs(converted, '编辑量表');
  //
  //
  //   },


    exportExcel() {
      // 获取表格元素
      const tableElement = document.getElementById('data_table');

      // 使用XLSX.utils.table_to_book方法将HTML表格转换为工作簿对象
      const workbook = XLSX.utils.table_to_book(tableElement, {sheet: "Sheet1"});

      // 生成Excel文件名
      const filename = `评价量表详情.xlsx`;

      // 导出Excel文件
      XLSX.writeFile(workbook, filename);
    },
    exportWord() {
      this.setTableStyle();
      const tableElement = document.getElementById('data_table');
      const tableHtml = tableElement.outerHTML;
      const name = document.getElementsByClassName('el-descriptions-item__container').innerText;
      console.log(name);
      const docTitle = '评价量表详细内容';
      const docContent = `
    <!DOCTYPE html>
    <html>
      <head>
        <meta charset="UTF-8">
        <title>${docTitle}</title>
        <style>
          table {
            width: 100%;
            border-collapse: collapse;
          }
          th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
          }
          th {
            background-color: #f2f2f2;
          }
        </style>
      </head>
      <body>
        <h1>${docTitle}</h1>
        ${tableHtml}
      </body>
    </html>
  `;

      const blob = new Blob([docContent], { type: 'application/vnd.ms-word' });
      const url = URL.createObjectURL(blob);

      const link = document.createElement('a');
      link.href = url;
      link.download = `${docTitle}.doc`;
      link.click();

      URL.revokeObjectURL(url);
    }

    ,



    // todo设置表格样式
    setTableStyle() {
      //table-designer-container是我自己的外层class，换成你自己的！！！！！！！！！！！！！！！
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
              childTd[k].style.padding = "10px 20px"; // 添加左右内边距
              childTd[k].style.verticalAlign = "middle"; // 垂直居中对齐更加精确
              childTd[k].style.textAlign = "center"; // 水平居中对齐
              childTd[k].style.verticalAlign="center";
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
      return document.querySelector("#print-table .el-table").innerHTML;
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

    // todo清除表格样式
    clearTableStyle() {
      //table-designer-container是我自己的外层class，换成你自己的！！！！！！！！！！！！！！！
      let tableDomList = document
        .querySelector(".table_block")
        .getElementsByTagName("table");

      // 去除第一个table表头最右侧的边框
      let headRightTh = tableDomList[0]
        .getElementsByTagName("tr")[0]
        .getElementsByTagName("th");
      headRightTh[headRightTh.length - 2].style.borderRight = "1px solid #EBEEF5";

      //去除右侧和最底部的样式
      for (let i = 0; i < tableDomList.length; i++) {
        tableDomList[i].setAttribute("border", "0");
        tableDomList[i].style.border = "none";
        let childTr = tableDomList[i].getElementsByTagName("tr");
        let childTrLength = childTr.length;
        // 第一个table是头部，没有td会报错
        if (i > 0) {
          for (let j = 0; j < childTrLength; j++) {
            let childTd = childTr[j].getElementsByTagName("td");
            let childTdLength = childTd.length;
            // 修改最后一个的边框值
            if (childTd[childTdLength - 1].style) {
              childTd[childTdLength - 1].style.borderRight = "none";
            }
            for (let k = 0; k < childTdLength; k++) {
              if (j == childTrLength - 1) {
                childTd[k].style.borderBottom = "1px solid #EBEEF5";
              }
            }
          }
        }
      }
    },


    handleReject() {

    },

    handlePass() {

    },

    back() {
      router.go(-1) //返回上一步路由
      this.$store.dispatch("tagsView/delView", this.$route) //调用全局挂载的方法，关闭当前页
    },

    test() {
      console.log(document.querySelector(".table_block").getElementsByTagName("table"))
    }
  }
}
</script>

<style scoped>
#mind_map {
  width: 90%;
  height: 600px;
}

.tips{
  background-color: #3498db;
  color: white;
  float: right;
  font-size: 15px;
}
</style>
