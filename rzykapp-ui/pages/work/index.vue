<template>
	<view class="container">
		<view class="work-container">
			<!-- 宫格组件 -->
			<uni-section title="试卷审批管理工作台" type="line"></uni-section>
			<view class="content">

				<image class="logo" src="/static/favicon.png"></image>
				<!--  -->
				<view class="text-area">
					<text class="title">{{title}}</text>
				</view>

			</view>
			<!-- style="background-color: aliceblue;" -->
			<view class="grid-body">
				<uni-grid :column="4" :showBorder="false" @change="navigateTo">
					<uni-grid-item :index="0">
						<view class="grid-item-box">
							<uni-icons type="map-filled" color="#1d3b82" size="30"></uni-icons>
							<text class="text">试卷管理</text>
						</view>
					</uni-grid-item>
					<uni-grid-item :index="1">
						<view class="grid-item-box">
							<uni-icons type="staff" color="#1d3b82" size="30"></uni-icons>
							<text class="text">授课老师阅卷</text>
						</view>
					</uni-grid-item>
					<uni-grid-item v-if="this.$permission('supervisor:supervisor:supervisor')" :index="2">
						<view class="grid-item-box">
							<uni-icons type="contact" color="#1d3b82" size="30">
							</uni-icons>
							<text class="text">督导审批</text>
						</view>
					</uni-grid-item>
					<uni-grid-item :index="3">
						<view class="grid-item-box">
							<uni-icons type="settings-filled" color="#1d3b82" size="30"></uni-icons>
							<text class="text">试卷流向</text>
						</view>
					</uni-grid-item>
					<uni-grid-item :index="4">
						<view class="grid-item-box">
							<uni-icons type="list" color="#1d3b82" size="30"></uni-icons>
							<text class="text">考试标语</text>
						</view>
					</uni-grid-item>
					<uni-grid-item :index="5">
						<view class="grid-item-box">
							<uni-icons type="search" color="#1d3b82" size="30"></uni-icons>
							<text class="text">查询考试</text>
						</view>
					</uni-grid-item>
					<uni-grid-item :index="7">
						<view class="grid-item-box">
							<uni-icons type="search" color="#1d3b82" size="30"></uni-icons>
							<text class="text">地图</text>
						</view>
					</uni-grid-item>
					<!-- 					<uni-grid-item :index="6">
						<view class="grid-item-box">
							<uni-icons type="search" color="#1d3b82" size="30"></uni-icons>
							<text class="text">材料</text>
						</view>
					</uni-grid-item> -->
					<!--   <uni-grid-item :index="6">
                        <view class="grid-item-box">
                            <uni-icons type="person-filled" color="#1d3b82" size="30"></uni-icons>
                            <text class="text">系主任审批记录</text>
                        </view>
                    </uni-grid-item>
                    <uni-grid-item :index="7"> 
                    <view class="grid-item-box">
                        <uni-icons type="person-filled" color="#1d3b82" size="30"></uni-icons>
                        <text class="text">督导审批记录</text>
                    </view>
                    </uni-grid-item>-->
				</uni-grid>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config';
	const baseUrl = config.baseUrl
	const Authorization = `Bearer ${uni.getStorageSync("token")}`;
	export default {
		data() {
			return {
				title: 'GZEU',
				current: 0,
				swiperDotIndex: 0,
				data: [{
						image: '/static/images/banner/banner01.jpg'
					},
					{
						image: '/static/images/banner/banner02.jpg'
					},
					{
						image: '/static/images/banner/banner03.jpg'
					}
				],
			}
		},
		mounted() {
			this.getPermission()
		},
		onLoad() {

		},
		methods: {
			getPermission() {
				/**
				 * 获取数据
				 */

				uni.request({
					url: baseUrl + "/act/permission",
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						let permission = res.data.data
						uni.setStorageSync("permission", permission)
					}
				})
			},
			clickBannerItem(item) {
				console.info(item)
			},
			changeSwiper(e) {
				this.current = e.detail.current
			},
			changeGrid(e) {
				// this.$modal.showToast('模块建设中~')
			},
			/**
			 * 工作台跳转
			 * @param {Object} e 选中项目索引
			 */
			navigateTo(e) {
				// this.$modal.showToast('模块建设中~')
				const {
					index
				} = e.detail;
				if (index > 7) {
					uni.showToast({
						title: '模块开发中',
						//将值设置为 success 或者直接不用写icon这个参数
						icon: 'error',
						//显示持续时间为 2秒
						duration: 2000
					})
				}
				let thisUrl = '/';
				switch (index) {
					case 0:
						thisUrl = '/pages/manage/index';
						break;
					case 1:
						thisUrl = '/pages/xzr/index';
						break;
					case 2:
						thisUrl = '/pages/dd/index';
						break;
					case 3:
						thisUrl = '/pages/transferRecord/index';
						break;
					case 4:
						thisUrl = '/pages/prompt/prompt';
						break;
					case 5:
						thisUrl = '/pages/findExams/findExams';
						break;
					case 6:
						thisUrl = '/pages/Document/Document';
						break;
					case 7:
						thisUrl = '/pages/AppMap/AppMap';
						break;
				}

				uni.navigateTo({
					url: thisUrl
				});
			},
			navigateBack() {
				uni.navigateTo({
					url: '/pages/dd/index'
				});
			},
		}
	}
</script>

<style lang="scss">
	/* #ifndef APP-NVUE */
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #fff;
		min-height: 100%;
		height: auto;
	}

	view {
		font-size: 14px;
		line-height: inherit;
	}

	/* #endif */

	.text {
		text-align: center;
		font-size: 26rpx;
		margin-top: 10rpx;
	}

	.grid-item-box {
		flex: 1;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 15px 0;
	}

	.uni-margin-wrap {
		width: 690rpx;
		width: 100%;
		;
	}

	.swiper {
		height: 300rpx;
	}

	.swiper-box {
		height: 150px;
	}

	.swiper-item {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		justify-content: center;
		align-items: center;
		color: #fff;
		height: 300rpx;
		line-height: 300rpx;
	}

	@media screen and (min-width: 500px) {
		.uni-swiper-dot-box {
			width: 400px;
			/* #ifndef APP-NVUE */
			margin: 0 auto;
			/* #endif */
			margin-top: 8px;
		}

		.image {
			width: 100%;
		}
	}

	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>