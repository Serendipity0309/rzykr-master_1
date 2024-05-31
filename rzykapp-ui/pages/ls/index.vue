<template>
	<view>

		<uni-card :isShadow="true" :title="item.codeitemdesc" mode="basic" thumbnail="/static/favicon.png"
			note="slot卡槽默认内容">
			<view>
				<view>姓名:<text v-text="item.name"></text></view>
				<view>角色:
					<text v-if="item.position==0">老师</text>
					<text v-if="item.position==1">系主任</text>
					<text v-if="item.position==2">督导</text>
				</view>
			</view>
		</uni-card>
		<uni-card class="my-uni-card">

			<view class="el-menu-item " @click="handleToSetting('invigilator_get_exam')">
				<view class="style-el list-cell-arrow" :index="invigilator_get_exam">
					<view>
						<image style="  height: 20px; width:20px;margin-bottom: -5px;padding-right: 20px;"
							src="../../static/manage-l-menu.jpg">
						</image>我的领卷记录
					</view>
				</view>
			</view>
			<hr />
			<view class="el-menu-item" @click="handleToSetting('invigilator_hand_exam')">
				<view class="style-el list-cell-arrow" :index="invigilator_hand_exam">
					<view>
						<image style="  height: 20px; width:20px;margin-bottom: -5px;padding-right: 20px;"
							src="../../static/manage-j-menu.jpg">
						</image> 我的交卷记录
					</view>
				</view>
			</view>
			<hr />
			<view class="el-menu-item" @click="handleToSetting('teacher_get_exam')">
				<view class="style-el list-cell-arrow" :index="teacher_get_exam">
					<view>
						<image style="  height: 25px; width:25px;margin-bottom: -5px;padding-right: 20px;"
							src="../../static/xzr-menu.jpg">
						</image>授课老师领卷记录
					</view>
				</view>
			</view>
			<hr />
			<view class="el-menu-item list-cell-arrow" @click="handleToSetting('teacher_hand_exam')">
				<view class="style-el list-cell-arrow" :index="teacher_hand_exam">
					<!-- <view class="iconfont icon-setting menu-icon"></view> -->
					<view>
						<image style="  height: 20px; width:20px;margin-bottom: -5px;padding-right: 20px;"
							src="../../static/dd-menu.jpg">
						</image> 授课老师交卷
					</view>
				</view>
			</view>
			<hr />
			<view class="el-menu-item list-cell-arrow" @click="handleToSetting('supervisory_approval')">
				<view class="style-el list-cell-arrow" :index="supervisory_approval">
					<!-- <view class="iconfont icon-setting menu-icon"></view> -->
					<view>
						<image style="  height: 20px; width:20px;margin-bottom: -5px;padding-right: 20px;"
							src="../../static/dd-menu.jpg">
						</image> 督导审批
					</view>
				</view>
			</view>
		</uni-card>
		<view>
			<!-- <comm></comm> -->
		</view>
	</view>
</template>

<script>
	import config from '@/config';
	const baseUrl = config.baseUrl;
	const Authorization = `Bearer ${uni.getStorageSync("token")}`;
	export default {
		data() {
			return {
				index: '',
				item: {},
				Authorization: Authorization
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
			 * 页面跳转
			 * @param {Object} e 选中项目索引
			 */
			handleToSetting(e) {

				// if (true) {
				// 	this.$modal.showToast('模块建设中~')
				// 	return
				// }
				let thisUrl = '/pages/menu-comm/comm?index=' + e;

				uni.navigateTo({
					url: thisUrl
				});
			},
			navigateBack() {
				uni.navigateTo({
					url: '/pages/dd/index'
				});
			},
			/**
			 * 获取数据
			 */
			getList() {
				const Authorization = `Bearer ${uni.getStorageSync("token")}`;
				uni.request({
					url: baseUrl + "/act/userinfo",
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.data
						console.log(this.item)
					}
				})
			},

		}
	};
</script>

<style>
	.style-el {
		padding-left: 20px;
		color: rgb(48, 65, 86);
		background-color: #ffffff;
	}

	.el-menu-item {
		height: 56px;
		line-height: 56px;
		font-size: 14px;
		color: #ffffff;
		list-style: none;
		cursor: pointer;
		position: relative;
		-webkit-transition: border-color 0.3s, background-color 0.3s, color 0.3s;
		transition: border-color 0.3s, background-color 0.3s, color 0.3s;
		-webkit-box-sizing: border-box;
		box-sizing: border-box;
		white-space: nowrap;
		border-radius: 8px;
	}

	.uni-card {
		margin: 15px 15px;
		padding: 20px 0px;
		border-radius: 8px;
		/* background-color: rgb(48, 65, 86); */
		color: #ffffff;
	}

	.list-cell-arrow::before {
		content: ' ';
		height: 10px;
		width: 10px;
		border-width: 2px 2px 0 0;
		border-color: #c0c0c0;
		border-style: solid;
		-webkit-transform: matrix(0.5, 0.5, -0.5, 0.5, 0, 0);
		transform: matrix(0.5, 0.5, -0.5, 0.5, 0, 0);
		position: absolute;
		top: 50%;
		margin-top: -6px;
		right: 15px;
	}

	.icofont {
		font-family: "iconfont" !important;
		font-size: 16px;
		display: inline-block;
		font-style: normal;
		-webkit-font-smoothing: antialiased;
	}

	.menu-icon {
		color: #007AFF;
		font-size: 16px;
		margin-right: 5px;
	}
</style>