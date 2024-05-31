<template>
	<view>
		<!-- <uni-load-more show-loading="load"> -->
		<view v-if="isOpen">
			<image height="80%" width="80%" class="bg-img" src="../../static/nodata.jpg"></image>
		</view>
		<view v-else>
			<view v-for="elm in item">
				<uni-card :isShadow="true" :title="elm.schoolDivision.branchName" mode="basic"
					thumbnail="/static/favicon.png" note="slot卡槽默认内容">

					<view>
						<view>时间:
							<uni-dateformat :date="elm.hisActIns.startTime" format="yyyy年MM月dd日hh:mm"></uni-dateformat>
						</view>
						<view>试卷编号:<text v-text="elm.examData.coursenumber"></text></view>
						<view>主监考:<text v-text="elm.examData.chiefinv"></text></view>
					</view>
				</uni-card>
			</view>
		</view>
		<!-- </uni-load-more> -->
	</view>
</template>

<script>
	import config from '@/config'
	const baseUrl = config.baseUrl
	const Authorization = `Bearer ${uni.getStorageSync("token")}`;
	export default {
		data() {
			return {
				item: [],
				isOpen: true,
			}
		},
		created() {},
		mounted() {
			this.getList()

		},
		methods: {

			/**
			 * 获取索引
			 */
			getIndex() {

			},
			//获取url中的参数
			getParam(path, name) {

				var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");

				if (reg.test(path))

					return unescape(RegExp.$2.replace(/\+/g, " "));
			},
			/**
			 * 获取数据
			 */
			getList() {
				let local = location.href;
				//获取url参数
				let index = this.getParam(local, "index");

				const Authorization = `Bearer ${uni.getStorageSync("token")}`;
				uni.request({
					url: baseUrl + "/act/getinfobymenu?index=" + index,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						if (res.data.code === '204') {
							this.isOpen = true
						} else {
							this.item = res.data.data
							this.isOpen = false
							console.log(this.item)
						}

					}
				})
			},
		}
	}
</script>

<style>
	.bg-img {
		position: fixed;
		width: 70%;
		height: 70%;
		top: 5%;
		left: 10%;
		/* z-index: -1; */
	}
</style>