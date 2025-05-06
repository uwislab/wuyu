<template>
  <div>
    <table>
      <tr>
        <td>
          <input
            id="i2"
            type="text"
            v-model="tableData.min1"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><=</td>
        <td :style="{ color: getColor('优') }">优</td>
        <td><=</td>
        <td>
          <input
            id="i1"
            type="text"
            v-model="tableData.max1"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><a href="#/remark/assess" class="link">查看语料库</a></td>
      </tr>
      <tr>
        <td>
          <input
            id="i4"
            type="text"
            v-model="tableData.min2"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><=</td>
        <td :style="{ color: getColor('良') }">良</td>
        <td><=</td>
        <td>
          <input
            id="i3"
            type="text"
            v-model="tableData.max2"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><a href="#/remark/assess" class="link">查看语料库</a></td>
      </tr>
      <tr>
        <td>
          <input
            id="i6"
            type="text"
            v-model="tableData.min3"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><=</td>
        <td :style="{ color: getColor('中') }">中</td>
        <td><=</td>
        <td>
          <input
            id="i5"
            type="text"
            v-model="tableData.max3"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><a href="#/remark/assess" class="link">查看语料库</a></td>
      </tr>
      <tr>
        <td>
          <input
            id="i8"
            type="text"
            v-model="tableData.min4"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><=</td>
        <td :style="{ color: getColor('差') }">差</td>
        <td><=</td>
        <td>
          <input
            id="i7"
            type="text"
            v-model="tableData.max4"
            :disabled="disableVar"
            class="input-field"
          />
        </td>
        <td><a href="#/remark/assess" class="link">查看语料库</a></td>
      </tr>
    </table>
    <div class="bottom-btn">
      <button v-show="!disableVar" @click="save">完成</button>
      <button v-show="disableVar" @click="updateFn">编辑</button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      tableData: {},
      disableVar: true,
    };
  },
  methods: {
    updateFn() {
      this.disableVar = false;
    },
    getIndicator() {
      request({
        url: "api/goal/getIndicater",
        method: "get",
        params: {
          id: this.$route.query.id,
        },
      }).then((res) => {
        this.tableData = res.data;
      });
    },
    save() {
      for (let i = 1; i <= 8; i++) {
        let input = document.getElementById("i" + i);
        let value = input.value;
        let regex = /^(100|[1-9]\d?|0)$/;

        if (!regex.test(value)) {
          alert("请正确输入0~100的数: " + value);
          return;
        }

        if (i > 1) {
          let prevValue = parseFloat(
            document.getElementById("i" + (i - 1)).value
          );
          if (value >= prevValue) {
            alert("输入不合理");
            return;
          }
        }
      }
      this.disableVar = true;
      this.updateIndicator();
      this.getIndicator();
    },
    updateIndicator() {
      request({
        url: "api/goal/updateIndicater",
        method: "post",
        data: this.tableData,
      });
    },
    // 根据等级返回对应的颜色
    getColor(level) {
      switch (level) {
        case "优":
          return "blue"; // 优：蓝色
        case "良":
          return "green"; // 良：绿色
        case "中":
          return "orange"; // 中：黄色
        case "差":
          return "red"; // 差：红色
        default:
          return "black"; // 默认黑色
      }
    },
  },
  mounted() {
    this.getIndicator();
  },
};
</script>

<style lang="scss" scoped>
/* 表格样式 */
table {
  width: 100%;
  margin-bottom: 20px;
  border-collapse: collapse; /* 合并边框 */
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); /* 为表格添加阴影 */
}

td {
  width: 220px;
  height: 60px;
  text-align: center;
  vertical-align: middle;
  border: 1px solid #ddd;
  font-size: 16px;
  color: #555;
}

/* 输入框样式 */
.input-field {
  width: 100px;
  height: 35px;
  padding: 0 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
  outline: none;
  transition: all 0.3s ease;
}

.input-field:focus {
  border-color: #409eff; /* 聚焦时的边框颜色 */
  box-shadow: 0 0 5px rgba(64, 158, 255, 0.5); /* 聚焦时的阴影效果 */
}

/* 链接样式 */
.link {
  color: #409eff;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s;
}

.link:hover {
  color: #66b1ff; /* 悬停时的颜色 */
}

/* 按钮样式 */
.bottom-btn {
  text-align: center;
  margin-top: 20px;
}

button {
  width: 160px;
  height: 40px;
  background-color: #409eff;
  color: #fff;
  border: none;
  border-radius: 20px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

button:hover {
  background-color: #66b1ff;
}

button:focus {
  outline: none;
}
</style>
