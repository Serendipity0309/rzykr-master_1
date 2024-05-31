<template>
	<view>


		<uni-card :isShadow="true" title="授课老师提交材料" mode="basic" thumbnail="/static/favicon.png" note="true">
			<view v-if="isBtnShow!=1">
				<text>老师未提交材料</text>
			</view>
			<view v-else>
				<view>
					<checkbox-group>
						<label v-for="e in sunmitDocumnets"
							style="padding-top:5% ; display: flex;flex-direction: row;font-size: 28upx;">
							<view>
								<checkbox checked="checked" disabled :value="e.id">{{e.detail}}</checkbox>
							</view>
						</label>
					</checkbox-group>
				</view>
				<view>
					<hr>
					<view class="footer-box">
						<!-- <view @click="finishIndividualTask(elm.examData.id,0)" v-if="permission==2"> -->
						<view @click="showDrawer('showLeft')"
							class="icon-container footer-butter danger-is-plain is-round text">
							<uni-icons class="icon" type="undo" size="20"></uni-icons>
							<text>驳回</text>
						</view>

						<view @click="finishIndividualTask(1)"
							class="icon-container footer-butter success-is-plain is-round text">
							<uni-icons class="icon" type="redo" size="20"></uni-icons>
							<text>通过</text>
						</view>
						<view @click="showDrawer('showRight')"
							class="icon-container footer-butter primary-is-plain is-round text">
							<uni-icons class="icon" type="eye-filled" size="20"></uni-icons>
							<text>试卷详情</text>
						</view>
					</view>
				</view>
			</view>
		</uni-card>
		<view>
			<!-- <uni-section title="左侧滑出" type="line"> -->
			<view class="example-body">
				<uni-drawer ref="showRight" mode="right" :width="320" @change="change($event,'showRight')">
					<uni-card :isShadow="true" title="试卷信息" mode="basic" thumbnail="/static/favicon.png" note="true">
						<view>
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
					</uni-card>
				</uni-drawer>
			</view>
			<!-- </uni-section> -->
			<view class="example-body">
				<uni-drawer ref="showLeft" mode="left" :width="350" @change="change($event,'showLeft')">
					<uni-card :isShadow="true" title="试卷信息" mode="basic" thumbnail="/static/favicon.png" note="true">
						<form>
							<h3>A、考查课材料</h3>
							<view>
								<checkbox-group @change="handleChange">
									<label v-for="e in documents"
										style="padding-top:5% ; display: flex;flex-direction: row;font-size: 28upx;">
										<view>
											<checkbox :value="e.id">{{e.detail}}</checkbox>
										</view>
									</label>

								</checkbox-group>
							</view>
							<view class="close">
								<button @click="closeDrawer('showLeft')"><text class="word-btn-white">驳回</text></button>
							</view>
						</form>
					</uni-card>
				</uni-drawer>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	/* 	import {
			permission
		} from "@/permission.js" */
	const baseUrl = config.baseUrl
	const Authorization = `Bearer ${uni.getStorageSync("token")}`;
	export default {
		data() {
			return {
				allpicks: false,
				values: '',
				item: {},
				documents: [],
				sunmitDocumnets: [],
				invigilationIsSubmit: -1,
				isBtnShow: 0,
				showRight: false,
				showLeft: false
			}
		},
		mounted() {
			this.initData()
		},
		methods: {
			handleChange(e) {
				this.values = e.detail.value.join(';');

			},
			initData() {
				const id = uni.getStorageSync("id");
				uni.request({
					url: baseUrl + '/act/getDocumentList?id=' + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.data.examData
						this.documents = res.data.data.documents
						this.invigilationIsSubmit = res.data.data.invigilationIsSubmit
						this.sunmitDocumnets = res.data.data.dataList
						this.isBtnShow = res.data.data.isBtn
					}
				})
			},
			/**
			 * 授课老师领卷
			 */
			teacherLeadsThePaper() {
				const id = uni.getStorageSync("id");
				uni.request({
					url: baseUrl + `/act/teacherLeadsThePaper?id= ${id}`,
					method: 'GET',
					header: {
						Authorization
					},
					success: (res) => {
						this.initData()
					}
				})
			},
			/**
			 * 授课老师领卷
			 */
			teacherHandThePaper() {
				alert(this.values)
				const id = uni.getStorageSync("id");
				uni.request({
					url: baseUrl + `/act/teacherHandThePaper`,
					method: 'GET',
					data: {
						'id': id,
						'selectList': this.values
					},
					header: {
						Authorization
					},
					success: (res) => {
						this.initData()
					}
				})
			},
			//驳回
			supervisorReject() {
				const id = uni.getStorageSync("id");
				uni.request({
					url: baseUrl + "/act/supervisorReject",
					method: 'GET',
					data: {
						reason: this.values,
						id: id
					},
					header: {
						Authorization
					},
					success: (res) => {
						this.initData()
						uni.showToast({
							title: '提交成功', //标题 必填
							icon: 'none', //图标
							// image: '', //自定义图标的本地路径（app端暂不支持gif）
							mask: true, //是否显示透明蒙层，防止触摸穿透，默认：false
							duration: '', //提示的延迟时间，单位毫秒，默认：1500
							position: '', //纯文本轻提示显示位置，填写有效值后只有 title 属性生效，且不支持通过         uni.hideToast 隐藏。有效值详见下方说明。	App
							success: '', //接口调用成功的回调函数	
							fail: '', //接口调用失败的回调函数	
							complete: '', //接口调用结束的回调函数（调用成功、失败都会执行）
						});
					}
				})
			},
			/**
			 * 完成个人任务
			 */
			finishIndividualTask(isPass) {
				const id = uni.getStorageSync("id");
				uni.request({
					url: baseUrl + `/act/finishIndividualTask?isPass=${isPass}&id= ${id}`,
					method: 'GET',
					header: {
						Authorization
					},
					success: (res) => {
						this.getList()
						uni.showToast({
							title: '提交成功', //标题 必填
							icon: 'none', //图标
							// image: '', //自定义图标的本地路径（app端暂不支持gif）
							mask: true, //是否显示透明蒙层，防止触摸穿透，默认：false
							duration: '', //提示的延迟时间，单位毫秒，默认：1500
							position: '', //纯文本轻提示显示位置，填写有效值后只有 title 属性生效，且不支持通过         uni.hideToast 隐藏。有效值详见下方说明。	App
							success: '', //接口调用成功的回调函数	
							fail: '', //接口调用失败的回调函数	
							complete: '', //接口调用结束的回调函数（调用成功、失败都会执行）
						});
					}
				})
			},
			confirm() {},
			// 打开窗口
			showDrawer(e) {
				this.$refs[e].open()
			},
			// 关闭窗口
			closeDrawer(e) {
				this.supervisorReject()
				this.$refs[e].close()
			},
			// 抽屉状态发生变化触发
			change(e, type) {
				console.log((type === 'showLeft' ? '左窗口' : '右窗口') + (e ? '打开' : '关闭'));
				this[type] = e
			}
		},
		onNavigationBarButtonTap(e) {
			if (this.showLeft) {
				this.$refs.showLeft.close()
			} else {
				this.$refs.showLeft.open()
			}
		},
		// app端拦截返回事件 ，仅app端生效
		onBackPress() {
			if (this.showRight || this.showLeft) {
				this.$refs.showLeft.close()
				this.$refs.showRight.close()
				return true
			}
		}

	}
</script>

<style>
	.footer-box {
		margin-bottom: 1%;
		display: flex;
		justify-content: space-between;
		flex-direction: row;
		/* background-color: #a2a2a2; */
		border-radius: 14px;
		margin-top: 4%;

	}


	.footer-butter {
		display: inline-block;
		line-height: 1;
		white-space: nowrap;
		cursor: pointer;
		/* background: #fff; */
		/* background-color: #a2a2a2; */
		border: 1px none #dcdfe6;
		/* color: #606266; */
		-webkit-appearance: none;
		text-align: center;
		box-sizing: border-box;
		outline: none;
		margin: 0;
		transition: .1s;
		font-weight: 500;
		-moz-user-select: none;
		-webkit-user-select: none;
		-ms-user-select: none;
		font-size: 120%;
		/* border-radius: 4px; */
	}

	.success-is-plain:hover {
		color: #fff;
		background-color: #67c23a;
		border-color: #67c23a;
		/* background-color: #1a49a2;
		border-color: #1a49a2; */
	}

	.success-is-plain {
		/* color: #67c23a; */
		/* background: #f0f9eb; */
		/* border-color: #c2e7b0; */
	}

	.primary-is-plain:hover {
		color: #fff;
		background-color: #409eff;
		border-color: #409eff;
		/* background-color: #1a49a2;
		border-color: #1a49a2; */
	}

	.primary-is-plain {
		/* color: #409eff; */
		/* background: #ecf5ff; */
		/* border-color: #b3d8ff; */
	}

	.danger-is-plain:hover {
		color: #fff;
		background-color: #f56c6c;
		border-color: #f56c6c;
		/* background-color: #1a49a2;
		border-color: #1a49a2; */
	}

	.danger-is-plain {
		/* color: #f56c6c; */
		/* background: #fef0f0; */
		/* border-color: #fbc4c4; */

	}

	.is-round {
		border-radius: 10px;
		padding: 12px 23px;
	}

	.icon-container {
		white-space: nowrap;
		/* 防止换行 */
	}

	.icon,
	.text {
		display: inline-block;
		vertical-align: middle;
	}

	.text {
		margin-left: 0px;
		/* 可根据需要调整图标和文字之间的间距 */
	}
</style>