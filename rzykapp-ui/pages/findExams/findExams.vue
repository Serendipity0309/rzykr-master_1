<template>
	<view>
		<template>
			<view class="warp">
				<uni-card is-full :is-shadow="false">
					<text class="uni-h6">宫格组件主要使用场景如：商品推荐列表、热门内容等</text>
				</uni-card>
				<!-- 	<uni-section title="自定义列数" type="line" padding>
					<uni-grid :column="7" :highlight="true" @change="change">
						<uni-grid-item v-for="(value, key) in item" :index="index" :key="index">
							<view class="grid-item-box" style="background-color: #fff;">
								<view class="text">
									{{key}}
								</view>
							</view>
							<view v-for="e in value">
								<view class="text">
									{{e.place}}
								</view>
							</view>
						</uni-grid-item>
					</uni-grid>
				</uni-section> -->
				<!-- <uni-section title="滑动视图" type="line" padding>
					<swiper :indicator-dots="true">
						<swiper-item v-for="swi in item">
							<uni-grid :column="5" :showBorder="false" :highlight="false" @change="change">
								<uni-grid-item v-for="(value, key) in swi" :index="index" :key="index">
									<view class="grid-item-box" style="background-color: #fff;">
										<view>
											{{key}}
										</view>
									</view>
									<view v-for="e in value">
										<view>
											{{e.place}}
										</view>
									</view>
								</uni-grid-item>
							</uni-grid>
						</swiper-item>

						<uni-grid :column="5" :showBorder="false" :highlight="false" @change="change">
							<uni-grid-item v-for="(value, key) in swi" :index="index" :key="index">
								<view class="grid-item-box" style="background-color: #fff;">
									<view>
										{{key}}
									</view>
								</view>
								<view v-for="e in value">
									<view>
										{{e.place}}
									</view>
								</view>
							</uni-grid-item>
						</uni-grid>
					</swiper>
				</uni-section> -->
				<uni-row class="demo-uni-row" v-for="(value, key) in item" :width="nvueWidth">
					<!-- <uni-col :span="2">
						<view>.</view>
					</uni-col> -->
					<uni-col :span="6" v-for="(value, key) in value">
						<view style="font-size: 100%; height: 200%; width: 95%;" class="demo-uni-col dark">{{key}}
						</view>

						<view v-for="(e, key) in value">

							<view style="font-size: 100%; height: 200%;margin-left: 2%; width: 95%; margin-top: 2%;"
								class="demo-uni-col bak_col">
								<view>{{e.place}}</view>
								<view>{{ e.examtime}}</view>
								<!-- <view>{{ e.teacher}}</view> -->
								<view>{{ e.coursename}}</view>
							</view>
						</view>
					</uni-col>
					<!-- 	<uni-col :span="2">
						<view>.</view>
					</uni-col> -->
					<!-- <hr style="width: 100%; margin-top: 5%;"> -->
				</uni-row>

			</view>
		</template>

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
				item: [],
				dynamicList: [],
				nvueWidth: 720
			}
		},
		mounted() {
			this.getList()
		},
		methods: {
			change(e) {
				let {
					index
				} = e.detail
				this.list[index].badge && this.list[index].badge++

				uni.showToast({
					title: `点击第${index+1}个宫格`,
					icon: 'none'
				})
			},
			/**
			 * 获取数据
			 */
			getList() {
				uni.request({
					url: baseUrl + "/act/getExamDataGroupByDate",
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
	}
</script>

<style lang="scss">
	.image {
		width: 25px;
		height: 25px;
	}

	.text {
		font-size: 14px;
		margin-top: 5px;
	}

	.example-body {
		/* #ifndef APP-NVUE */
		// display: block;
		/* #endif */
	}

	.grid-dynamic-box {
		margin-bottom: 15px;
	}

	.grid-item-box {
		flex: 1;
		// position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 15px 0;
	}

	.grid-item-box-row {
		flex: 1;
		// position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		padding: 15px 0;
	}

	.dark {
		background-color: #d3dce6;
	}

	.bak_col {
		background-color: #e2d6a9;
	}

	.demo-uni-col {
		height: 36px;
		border-radius: 5px;
	}

	.demo-uni-row {
		margin-bottom: 10%;
		margin-top: 10%;

		// 组件在小程序端display为inline
		// QQ、抖音小程序文档写有 :host，但实测不生效
		// 百度小程序没有 :host
		/* #ifdef MP-TOUTIAO || MP-QQ || MP-BAIDU */
		display: block;
		/* #endif */
	}

	.swiper {
		height: 420px;
	}

	/* #ifdef H5 */
	@media screen and (min-width: 768px) and (max-width: 1425px) {
		.swiper {
			height: 630px;
		}
	}

	@media screen and (min-width: 1425px) {
		.swiper {
			height: 830px;
		}
	}

	/* #endif */
</style>