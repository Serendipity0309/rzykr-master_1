<template>
	<div>
		<!-- <p style="font-size: 12pt;font-family: 微软雅黑;line-height: 150%"><b>提示!x：</b>获取信息，请<span
				style="color:red">允许获取你的信息</span></p> -->
		<!-- <vue-simple-spinner></vue-simple-spinner> -->
		<!-- <el-button :plain="true" @click="open2">成功</el-button> -->
		<h1 color="red"> 模拟微信登陆</h1>
		<Spinner></Spinner>

	</div>
</template>

<script>
	export default {
		mounted() {
			// this.open2();
			this.handleLogin();

		},
		methods: {
			// open2() {
			// 	this.$message({
			// 		message: '正在获取用户信息,请稍后.......',
			// 		type: 'warning',
			// 		center: true
			// 	});
			// },
			handleLogin() {
				//将课程id存入store
				sessionStorage.setItem('id', this.$route.query.id);
				location.href = '/loginByCode?code=xizhuren';
				// 		'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe7da22fd77ddf525&agentid=1000028&redirect_uri=https://34228ag576.imdo.co/loginByCode&response_type=code&scope=snsapi_privateinfo&state=STATE#wechat_redirect';
			},
		}
	};
</script>

<style>
</style>
