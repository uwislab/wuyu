<template>
<div class="dashboard-editor-container">
  <el-table ref="studentTable"
            border
            :data="list"
            style="width: 100%"
            @selection-change="handleSelectionChange"
  >
    <el-table-column label="编号" width="100" align="center">
      <template slot-scope="scope"></template>
    </el-table-column>
    <el-table-column label="学生姓名" width="200" align="center">
      <template slot-scope="scope"></template>
    </el-table-column>
    <el-table-column label="性别" width="100" align="center">
      <template slot-scope="scope"></template>
    </el-table-column>
    <el-table-column label="学号"  align="center">
      <template slot-scope="scope"></template>
    </el-table-column>
    <el-table-column label="班级"  align="center">
      <template slot-scope="scope"></template>
    </el-table-column>
    <el-table-column label="操作" width="300" align="center">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleSelectScore(scope.$index, scope.row)">查看信息
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</div>
  <!--<div class="dashboard-editor-container">-->
    <!--<div class=" clearfix">-->
      <!--<pan-thumb :image="avatar" style="float: left">-->
        <!--Your roles:-->
        <!--<span v-for="item in roles" :key="item" class="pan-info-roles">{{ item }}</span>-->
      <!--</pan-thumb>-->
      <!--<github-corner style="position: absolute; top: 0px; border: 0; right: 0;" />-->
      <!--<div class="info-container">-->
        <!--<span class="display_name">{{ name }}</span>-->
        <!--<span style="font-size:20px;padding-top:20px;display:inline-block;">Editor's Dashboard</span>-->
      <!--</div>-->
    <!--</div>-->
    <!--<div>-->
      <!--<img :src="emptyGif" class="emptyGif">-->
    <!--</div>-->
  <!--</div>-->
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import GithubCorner from '@/components/GithubCorner'

export default {
  name: 'DashboardEditor',
  components: { PanThumb, GithubCorner },
  data() {
    return {
      emptyGif: 'https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3'
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ])
  }
}
</script>

<style lang="scss" scoped>
  .emptyGif {
    display: block;
    width: 45%;
    margin: 0 auto;
  }

  .dashboard-editor-container {
    background-color: #e3e3e3;
    min-height: 100vh;
    padding: 50px 60px 0px;
    .pan-info-roles {
      font-size: 12px;
      font-weight: 700;
      color: #333;
      display: block;
    }
    .info-container {
      position: relative;
      margin-left: 190px;
      height: 150px;
      line-height: 200px;
      .display_name {
        font-size: 48px;
        line-height: 48px;
        color: #212121;
        position: absolute;
        top: 25px;
      }
    }
  }
</style>
