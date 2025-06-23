<template>
  <div class="overview">
    <div class="overview-select">
      <el-select v-model="selectedGrade" placeholder="请选择">
        <el-option
          v-for="(item, index) in grades"
          :key="index"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>

      <el-select v-model="selectedTerm" placeholder="请选择">
        <el-option
          v-for="(item, index) in terms"
          :key="index"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </div>

    <div class="overview-body">
      <div class="overview-body-left">
        <overview-classtable
          :selectedGrade="selectedGrade"
          :selectedTerm="selectedTerm"
        ></overview-classtable>
      </div>
      <div class="overview-body-right">
        <overview-classchart
          :selectedGrade="selectedGrade"
          :selectedTerm="selectedTerm"
        ></overview-classchart>
      </div>
    </div>
  </div>
</template>

<script>
import overviewClasstable from "./overview-classtable.vue";
import overviewClasschart from "./overview-classchart.vue";
export default {
  data() {
    return {
      selectedGrade: "",
      selectedTerm: " ",
      grades: [
        {
          value: "1",
          label: "一年级",
        },
        {
          value: "2",
          label: "二年级",
        },
        {
          value: "3",
          label: "三年级",
        },
        {
          value: "4",
          label: "四年级",
        },
        {
          value: "5",
          label: "五年级",
        },
        {
          value: "6",
          label: "六年级",
        },
      ],
      terms: [
        {
          value: "0",
          label: "第一学期",
        },
        {
          value: "1",
          label: "第二学期",
        },
      ],
    };
  },

  components: {
    overviewClasstable,
    overviewClasschart,
  },

  created() {
    // 在组件创建时设置默认值
    this.selectedGrade = "1"; // 设置默认年级
    this.selectedTerm = "0"; // 设置默认学期
  },
};
</script>

<style scoped>
.overview {
  margin: 0 20px;
  position: relative;
}

.overview-select {
  position: absolute;
  display: flex;
  justify-content: space-between;
  top: 50px;
  left: 410px;
  width: 320px;
}

.el-select {
  width: 150px;
}

.overview-body {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
}

.overview-body-left {
  padding: 30px;
}
.overview-body-right {
  padding: 40px;
  padding-right: 50px;
}
</style>
