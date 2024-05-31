<template>
	<div>
		{{paperId}}
		<el-card class="box-card" v-for=" (item,i) in dataList">
			<div slot="header" class="clearfix">
				<span>试卷信息</span>
			</div>
			<div>上级提交人:<span v-text="item.currentSubmitterName"></span></div>
			<div>创建时间:<span v-text="item.createdTime"></span></div>
			<div>课程编号:<span v-text="item.paperNumber"></span></div>
			<div>状态:<span v-text="item.status"></span></div>
			<div>角色:<span v-text="item.role"></span></div>
			<div>修改人:<span v-text="item.updatedBy"></span></div>
			<div>修改时间:<span v-text="item.updatedTime"></span></div>
			<br />

			<el-button type="danger" size="mini" icon="el-icon-back" @click="cl_handAllow(item,0)" round>驳回</el-button>
			<el-button type="success" size="mini" icon="el-icon-right" @click="cl_handAllow(item,1)" round>通过
			</el-button>
			<el-button type="info" round>试卷详情</el-button>
			<hr />
			<br />
		</el-card>


	</div>
</template>

<script>
	import {
		selectSupervisorByUser,
		handAllow
	} from '@/views/rzykapi/teacherInfo/info/js/supervisor.js'
	export default {
		//获取父组件传过来的id
		props: ['paperId'],
		data() {
			return {
				dataList: [],
				query: {}
			}
		},
		methods: {
			async getSupervisor() {

				// this.query.id = paperId
				this.query.id = sessionStorage.getItem('id')
				const {
					rows: list
				} = await selectSupervisorByUser(this.query);
				this.dataList = list
			},
			async cl_handAllow(data, isAllow) {
				const {
					msg: message
				} = await handAllow(data, isAllow);
				this.$message(message)
			}
		},
		mounted() {
			this.getSupervisor();
		}
	}
</script>

<style>
</style>
