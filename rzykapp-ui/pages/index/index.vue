<template>
	<!-- <view class="container"> -->
	<view>
		<image class="backgroundImage" src="/static/DAMEN带logo.jpg"></image>

		<view class="content">
			<!-- <image src="/static/logo带字.png"></image> -->
			<view class="text-area">
				<text class="title">{{title}}</text>
			</view>

		</view>
		<!-- <testEch></testEch> -->
	</view>
</template>

<script>
	import request from "@/utils/request.js"
	import testEch from "@/pages/index/testEcharts.vue"
	export default {
		components: {
			testEch
		},
		data() {
			return {
				title: 'GZEU'
			}
		},
		mounted() {
			// this.login()
			this.getCode()
			this.getDataID()
			// this.getUserInfo()

		},
		onLoad() {

		},
		methods: {
			getCode() {
				let local = location.href;
				let code = this.getParam(local, "code");
				if (typeof(code) == "undefined") {
					// http://kaowu.gznc.edu.cn
					location.href =
						'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe7da22fd77ddf525&agentid=1000024&redirect_uri=http://kaowu.gznc.edu.cn/app&response_type=code&scope=snsapi_privateinfo&state=STATE#wechat_redirect';
				} else {
					this.login(code)
					// alert("code"+code)
				}
			},
			login(code) {
				request({
					url: "/login",
					method: "POST",
					data: {
						password: code
					}
				})
			},
			getDataID() {
				let local = location.href;
				// uni.removeStorageSync('id');w
				//获取url参数

				let id = this.getParam(local, "id");
				// alert("id"+id)
				try {
					if (typeof(id) != "undefined") {
						uni.setStorageSync('id', id);
					}

				} catch (e) {
					// error
				}
			},
			//获取url中的参数
			getParam(path, name) {

				var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");

				if (reg.test(path))

					return unescape(RegExp.$2.replace(/\+/g, " "));
			},

		}
	}
</script>

<style>
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

	.backgroundImage {

		z-index: -1;
		width: 100%;
		height: 100%;
		position: fixed;
		top: 0;
		/* opacity: 0.5; */
	}
</style>