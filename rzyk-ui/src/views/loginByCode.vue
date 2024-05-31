<template>
	<div class="login">
		<div>
			<!-- <p style="font-size: 12pt;font-family: 微软雅黑;line-height: 150%"><b>提示!：</b>认证中，请<span
					style="color:red">允许获取你的信息Bycode</span></p> -->
			<!-- <vue-simple-spinner></vue-simple-spinner> -->
			<!-- <el-button plain @click="open4">
				左上角
			</el-button> -->
			<!-- <el-button :plain="true" @click="open2">成功</el-button> -->
			<Spinner></Spinner>


		</div>

	</div>
</template>
<script>
	import Cookies from "js-cookie";

	import {
		encrypt,
		decrypt
	} from '@/utils/jsencrypt'
	export default {
		name: "Login",
		data() {
			return {
				loading: false,
				loginForm: {
					username: "",
					password: "",
					rememberMe: false,
					code: "",
					uuid: ""
				},
			};
		},
		created() {
			this.getCookie();
			const {
				params,
				query
			} = this.$route
			this.loginForm.password = query.code;
			console.log(query)
		},
		mounted() {
			this.open2();
			this.handleLogin();

		},
		methods: {
			// handleLogin() {
			// 	location.href =
			// 		'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe7da22fd77ddf525&agentid=1000028&redirect_uri=http://ecard.gznc.edu.cn/getinfo&response_type=code&scope=snsapi_privateinfo&state=STATE#wechat_redirect';
			// },
			open2() {
				this.$message({
					message: '正在获取用户信息,请稍后.......',
					type: 'warning',
					center: true
				});
			},
			handleLogin() {
				this.loading = true;
				if (this.loginForm.rememberMe) {
					Cookies.set("username", this.loginForm.username, {
						expires: 30
					});
					Cookies.set("password", encrypt(this.loginForm.password), {
						expires: 30
					});
				} else {
					Cookies.remove("username");
					Cookies.remove("password");
				}
				let id = this.$route.query.id;
				this.$store.dispatch("Login", this.loginForm).then(() => {
					this.$router.push({
						path: this.redirect || "/teacherInfo/examInfo"
					}).catch(() => {});
				}).catch(() => {
					this.loading = false;
					if (this.captchaEnabled) {
						this.getCode();
					}
				});
			},
			getCookie() {
				const username = Cookies.get("username");
				const password = Cookies.get("password");
				this.loginForm = {
					username: username === undefined ? this.loginForm.username : username,
					password: password === undefined ? this.loginForm.password : decrypt(password),
				};
			}
		}
	};
</script>

<style rel="stylesheet/scss" lang="scss">


</style>
