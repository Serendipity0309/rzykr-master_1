<template>
	<view>

		<br>
		<view v-if="item.length<1">
			<image class="bg-img" src="../../static/nodata.jpg"></image>
		</view>
		<view v-for="e in item">
			<uni-card :isShadow="true" :title="e.historicTaskInstance.name" mode="basic" thumbnail="/static/favicon.png"
				note="slot卡槽默认内容">
				<view>
					<!-- <view>考试编号<text v-text="item.coursenumber"></text></view> -->
					<!-- <view>考试编号<text v-text="item.id"></text></view>
				<view>课程编号:<text v-text="item.coursenumber"></text></view>
				<view>课程名称:<text v-text="item.coursename"></text></view> -->
					<view>时间:
						<uni-dateformat :date="e.historicTaskInstance.startTime" format="yyyy年MM月dd日hh:mm">
						</uni-dateformat>
					</view>
					<view>负责人:<text v-text="e.user.name"></text></view>
					<view>状态:<text v-if="e.isFinished== ''">审批中</text>
						<text v-if="e.isFinished== '1'">审批完成</text>
					</view>
				</view>

			</uni-card>
		</view>
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
				isopen: true,
				item: [{}]
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
					url: baseUrl + "/act/personalHistory?id=" + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.rows
						if (this.item.length < 0) {
							this.isopen = true
						}
						console.log(this.item)
					}
				})
			},

			footerClick(types) {
				uni.showToast({
					title: types,
					icon: 'none'
				});
			}
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

	.bg-img {
		position: fixed;
		width: 100%;
		height: 85%;
		top: 0;
		left: 0;
		/* z-index: -1; */
	}
</style>