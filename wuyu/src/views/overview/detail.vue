<template>
  <div class="detail">
    <div class="detail-select">
      <el-select v-model="selectedGrade" placeholder="请选择">
        <el-option
          v-for="(item, index) in grades"
          :key="index"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-select v-model="selectedClass" placeholder="请选择">
        <el-option
          v-for="(item, index) in classes"
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

    <div class="detail-body">
      <div class="detail-body-left">
        <overview-stutable
          :selectedGrade="selectedGrade"
          :selectedClass="selectedClass"
          :selectedTerm="selectedTerm"
        ></overview-stutable>
      </div>
      <div class="detail-body-right">
        <overview-stuchart
          :selectedGrade="selectedGrade"
          :selectedClass="selectedClass"
          :selectedTerm="selectedTerm"
        ></overview-stuchart>
      </div>
    </div>
  </div>
</template>

<script>
import overviewStutable from "./overview-stutable.vue";
import overviewStuchart from "./overview-stuchart.vue";
export default {
  data() {
    return {
      selectedGrade: "",
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
      selectedClass: "",
      classes: [
        {
          value: "1",
          label: "一班",
        },
        {
          value: "2",
          label: "二班",
        },
        {
          value: "3",
          label: "三班",
        },
      ],
      selectedTerm: "",
      terms: [
        {
          value: "202012",
          label: "第一学期",
        },
        {
          value: "202107",
          label: "第二学期",
        },
      ],
    };
  },

  components: {
    overviewStutable,
    overviewStuchart,
  },

  created() {
    // 在组件创建时设置默认值
    this.selectedGrade = "1"; // 设置默认年级
    this.selectedClass = "1"; // 设置默认班级
    this.selectedTerm = "202012"; // 设置默认学期
  },

  mounted() {
    const selectedGrade = this.$route.params.selectedGrade;
    const selectedClass = this.$route.params.selectedClass;
    const selectedTerm = this.$route.params.selectedTerm;
    if (
      selectedGrade != undefined &&
      selectedClass != undefined &&
      selectedTerm != undefined
    ) {
      this.selectedGrade = selectedGrade;
      this.selectedClass = selectedClass;
      this.selectedTerm = selectedTerm;
    }
  },
};
</script>

<style scoped>
.detail {
  margin: 0 20px;
  position: relative;
}

.detail-select {
  position: absolute;
  display: flex;
  width: 440px;
  justify-content: space-between;
  top: 40px;
  left: 410px;
}
.el-select {
  width: 130px;
}

.detail-body {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
}

.detail-body-left {
  padding: 20px;
  padding-left: 50px;
}
.detail-body-right {
  padding-top: 20px;
  padding-right: 150px;
}
</style>
