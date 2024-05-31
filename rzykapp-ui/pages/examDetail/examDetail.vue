<template>
	<view>
		<br>
		<uni-card :isShadow="true" title="试卷信息" mode="basic" thumbnail="/static/favicon.png" note="slot卡槽默认内容">
			<view>
				<!-- <view>考试编号<text v-text="item.coursenumber"></text></view> -->
				<view>考试编号<text v-text="item.id"></text></view>
				<view>课程编号:<text v-text="item.coursenumber"></text></view>
				<view>课程名称:<text v-text="item.coursename"></text></view>
				<view>授课教师:<text v-text="item.teacher"></text></view>
				<view>教学班名称:<text v-text="item.teachingclass"></text></view>
				<view>考试地点:<text v-text="item.place"></text></view>
				<view>考场总人数:<text v-text="item.totalnum"></text></view>
				<view>考试时间:<text v-text="item.examtime"></text></view>
				<view>考试日期:<text v-text="item.examdate"></text></view>
				<view>主考人员:<text v-text="item.examiners"></text></view>
				<view>主监考:<text v-text="item.chiefinv"></text></view>
			</view>
		</uni-card>
	</view>
</template>

<script>
	import config from '@/config'
	const baseUrl = config.baseUrl
	const Authorization = `Bearer ${uni.getStorageSync("token")}`;
	export default {
		components: {},
		data() {
			return {
				item: {
					id: '',
					coursenumber: ''
				}
			};
		},
		mounted() {
			this.getList()
		},
		methods: {
			/**
			 * 下拉刷新
			 */
			onPullDownRefresh() {
				this.getList()
				uin.stopPullDownRefresh()
			},
			/**
			 * 获取数据
			 */
			getList() {
				const id = uni.getStorageSync("id");
				const Authorization = `Bearer ${uni.getStorageSync("token")}`;
				uni.request({
					url: baseUrl + "/system/data/getData?id=" + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.data
					}
				})
			},

		}
	};
</script>

<style>
	.footer-box {
		display: flex;
		justify-content: space-between;
		flex-direction: row;
	}

	.footer-box__item {
		font-size: 12px;
		color: #666;
	}
</style>