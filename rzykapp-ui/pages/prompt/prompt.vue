<template>
	<view>
		<!-- {{list}} -->
		<!-- <view>{{list.examdate}}</view>
		 -->
		<uni-row>
			<uni-col :span="1">
				<view class="grid-content bg-purple-dark "></view>
			</uni-col>
			<uni-col :span="22">
				<view v-html="item"> </view>
			</uni-col>
			<uni-col :span="1">
				<view grid-content bg-purple-dark></view>
			</uni-col>
		</uni-row>


	</view>
</template>

<script>
	import config from '@/config'
	const baseUrl = config.baseUrl
	const Authorization = `Bearer ${uni.getStorageSync("token")}`;
	export default {
		data() {
			return {
				item: '',
				list: {
					// examdate: ''
				}
			}
		},
		mounted() {
			this.getList()
			this.getDate()

		},
		methods: {
			async getDate() {
				const id = uni.getStorageSync("id");
				await uni.request({
					url: baseUrl + "/act/prompt/" + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.data
					}

				})
			},
			/**
			 * 获取数据
			 */
			async getList() {
				// alert("getList")
				const id = uni.getStorageSync("id");
				const Authorization = `Bearer ${uni.getStorageSync("token")}`;
				await uni.request({
					url: baseUrl + "/system/data/getData?id=" + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.list = res.data.data
					}
				})
			},
		}
	}
</script>

<style>
	.grid-content {
		border-radius: 4px;
		min-height: 36px;
		height: 100%;
	}

	.bg-purple-dark {
		background: #99a9bf;
	}
</style>