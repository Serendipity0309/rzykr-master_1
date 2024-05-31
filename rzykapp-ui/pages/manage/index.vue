<template>
	<view>
		<br>
		<uni-card :isShadow="true" title="试卷信息" mode="basic" thumbnail="/static/favicon.png" note="slot卡槽默认内容">
			<view v-if="viewMap.isInvigilate==1">
				<!-- <view> -->
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
			<view v-if="viewMap.isInvigilate==0">
				<h2 style="color:red" v-show="message('无法查看数据','error')">！你不是该场考试的监考老师</h2>
			</view>
			<view v-show="viewMap.isGET==1">
				<button type="info" @click="startProcess()" size="10">领卷</button>
			</view>
			<view v-show="viewMap.isSubmit==1">
				<button type="primary" @click="handExamination(item.id)" size="10">
					{{handTime}}交卷</button>
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
				},
				isShowData: 400,
				user: {},
				isDisabled: true,
				handTime: '',
				tempTime: 3600,
				butType: 'warning',
				examLog: {},
				viewMap: {},
				ReservedTime: 3600,
				longitude: '0.0', //经度
				latitude: '0.0' //纬度
			};
		},
		mounted() {

			this.getUserInfo()
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
			//获取url中的参数
			getParam(path, name) {

				var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");

				if (reg.test(path))

					return unescape(RegExp.$2.replace(/\+/g, " "));
			},
			/**
			 * 开启流程
			 */
			startProcess() {


				//获取id
				const id = uni.getStorageSync("id");

				this.isDisabled == true
				uni.request({
					url: baseUrl + "/act/bingingandstart/" + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.data
						this.getList();
						// this.handTimeForSch()
					}

				})

			},
			handTimeForSch() {
				if (this.tempTime < 1) {
					return
				}
				timer = setInterval(() => {

					--this.tempTime
					let S_Str = this.tempTime % 60
					let M_Str = Math.floor(this.tempTime / 60)
					this.handTime = "".concat(M_Str).concat(":").concat(S_Str)
					if (this.tempTime == 1) {
						this.butType = 'warning'
						this.isDisabled = true
						return
					}
				}, 1000)
			},
			/**
			 * 完成个人任务
			 */
			handExamination(id) {
				/* 	if (this.tempTime > 0) {
						this.message("未到交卷时间", "error")
						return
					} */
				uni.request({
					url: baseUrl + "/act/handExamination?id=" + id,
					method: 'GET',
					header: {
						Authorization
					},
					success: () => {
						this.getList()
					}
				})
			},
			/**
			 * 获取数据
			 */
			async getList() {
				const id = uni.getStorageSync("id");
				const Authorization = `Bearer ${uni.getStorageSync("token")}`;

				await uni.request({
					url: baseUrl + "/act/manageList/" + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.data.exam
						this.examLog = res.data.data.examLog
						this.viewMap = res.data.data.viewMap
						var date_get = new Date(this.examLog
								.chiefinvInvigilateFetchTime)
							.getTime()
						//将领卷时间+领卷时间定时得到可领卷时间，再与当前时间对比，看当前时间是否可以领卷
						// this.tempTime = (date_get + (this.ReservedTime * 1000)) - (new Date().getTime())

						this.tempTime = this.viewMap.timer
						this.handTimeForSch()
					}
				})

			},
			message(mes, type) {
				uni.showToast({
					title: mes,
					icon: type
				})
				return true
			},
			/**
			 * 获取数据
			 */
			async getUserInfo() {
				const Authorization = `Bearer ${uni.getStorageSync("token")}`;
				await uni.request({
					url: baseUrl + "/act/userinfo",
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.user = res.data.data
					}
				})
			},
			clickCard() {
				uni.showToast({
					title: '点击卡片',
					icon: 'none'
				});
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
</style>