<template>

   <div style="padding:50px ">

    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输方案信息" style="width:20%" clearable></el-input>
      <el-button type="primary" plain icon="el-icon-search" @click="load">查询</el-button>
    </div>

    <el-table
      :data="tableData"
      style="width: 100% "
      :row-style="rowStyle">
      <el-table-column prop="date" label="类别"  ></el-table-column>
      <el-table-column prop="name" label="内容"  ></el-table-column>
      <el-table-column prop="name" label="操作"  >
      <el-button type="text" icon="el-icon-edit" size="small" @click="handleEdit(scope.$index, scope.row)" >编辑</el-button>
      </el-table-column>
	  </el-table>

   </div>


</template>
<script>
	export default {
		data() {
			return {
				dataForm: {
					a: 'xxx',
					b: 'xxx',
          c: 'xxx',
					d: 'xxx',
          e: 'xxx'
				}
			}
		},
		computed: {
			//因为数据用到了dataform中的数据，所以写在了computed中
			tableData() {
				return [{
						date: "德育",
						name: this.dataForm.a
					},
					{
						date: "智育",
						name: this.dataForm.b
					},
                    {
						date: "体育",
						name: this.dataForm.c
					},
					{
						date: "劳育",
						name: this.dataForm.d
					},
                    {
						date: "美育",
						name: this.dataForm.e
					}
				]
			},
		},
		methods: {
			// 自定义列背景色
			rowStyle({
				row,
				rowIndex,
			}) {
				if (rowIndex == 0) {
					return "background:#f3f6fc;";
				} else {
					return "background:#ffffff;";
				}
			}
		},
	}
</script>
