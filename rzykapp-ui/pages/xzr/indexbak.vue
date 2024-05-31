<template>
	<!-- 系主任审批 -->
	<view>
		<!-- <view v-if="item.length<1"> -->
		<image class="bg-img" src="../../static/nodata.jpg"></image>
		<!-- </view> -->
		<!-- <view v-for="elm in item"> -->
		<uni-card :isShadow="true" title="收材料要求" mode="basic" thumbnail="/static/favicon.png" note="true">
			<!-- <view>
					<view>提交人电话:<text v-text="elm.tel_up"></text></view>
					<view>提交人姓名:<text v-text="elm.teacherInfo.name"></text></view>
					<view>课程名称:<text v-text="elm.examData.coursename"></text></view>
					<view>课程编号:<text v-text="elm.examData.coursenumber"></text></view>
					<view>考试班级:<text v-text="elm.examData.studentclass"></text></view>
					<view>考试位置:<text v-text="elm.examData.place"></text></view>
					<view>考试日期:<text v-text="elm.examData.examdate"></text></view>
					<view>考试时间:<text v-text="elm.examData.examtime"></text></view>
				</view> -->
			<h3>A、考查课材料</h3>
			<view>
				<checkbox-group @change="selectCk">
					<label style="display: flex;flex-direction: row;font-size: 28upx;">
						<checkbox value="123" checked="allpicks">1、教学手册</checkbox>
					</label>
				</checkbox-group>
			</view>
			<button type="default" class="login" hover-class="hover" formType="submit">提交</button>

			<view v-show="(viewMap.isSubmit+viewMap.isGET)==0">
				<button type="primary" @click="finishIndividualTask(elm.examData.id,0)" size="10">
					{{handTime}}授课老师领卷</button>
			</view>
			<!-- 		<view>
					<view class="footer-box">
						<view @click="finishIndividualTask(elm.examData.id,0)" v-if="permission==1">
							<text class="footer-butter danger-is-plain is-round">驳回</text>
						</view>
						<view v-if="permission==1" @click="finishIndividualTask(elm.examData.id,1)">
							<text class="footer-butter success-is-plain is-round">通过</text>
						</view>
						<view @click="examDetail()">
							<text class="footer-butter primary-is-plain is-round">试卷详情</text>
						</view>
					</view>
				</view> -->
		</uni-card>

		<!-- </view> -->

	</view>

</template>

<script>
	// import request from "@/utils/request/index.js"
	import config from '@/config'
	const baseUrl = config.baseUrl
	const Authorization = `Bearer ${uni.getStorageSync("token")}`;
	export default {
		components: {},
		data() {
			return {
				item: [{}],
				permission: '3'
			};
		},

		mounted() {
			this.getPermissionLev()
			this.getList()
		},
		methods: {
			/**
			 * 试卷详情
			 */
			examDetail() {
				uni.navigateTo({
					url: "/pages/examDetail/examDetail"
				});
			},
			/**
			 * 获取老师当前登录人权限 等级
			 */
			getPermissionLev() {
				uni.request({
					url: baseUrl + "/act/getPermissionLev",
					method: 'GET',
					header: {
						Authorization
					},
					success: (res) => {
						this.permission = res.data.data
					}
				})
			},

			/**
			 * 下拉刷新
			 */
			asyonPullDownRefresh() {
				// this.getList()
				// uin.stopPullDownRefresh()
				uni.onPullDownRefresh()
			},
			//
			clickCard() {

			},
			/**
			 * @param {Object} types
			 */
			approved(id) {
				uni.request({
					url: 'baseUrl' + ' /act/IndividualTask ? processId = ' + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {

					}

				})
			},
			/**
			 * 完成个人任务
			 */
			finishIndividualTask(id, isPass) {
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
			/**
			 * 获取流程数据
			 */
			getList() {
				const token = '';
				const id = uni.getStorageSync("id");
				// const Authorization = token ? `Bearer ${uni.getStorageSync("token")}` : "";
				const Authorization = `Bearer ${uni.getStorageSync("token")}`;
				uni.request({
					url: baseUrl + "/act/QueryProcess?query=" + 1 + "&id=" + id,
					header: {
						Authorization
					},
					method: 'GET',
					success: (res) => {
						this.item = res.data.rows
						console.log("/act/QueryProcess" + this.item)
					}
				})
			},
		}
	};
</script>

<style>
	.footer-box {
		display: flex;
		justify-content: space-between;
		flex-direction: row;
	}


	.footer-butter {
		display: inline-block;
		line-height: 1;
		white-space: nowrap;
		cursor: pointer;
		background: #fff;
		border: 1px solid #dcdfe6;
		color: #606266;
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
		padding: 12px 20px;
		font-size: 14px;
		border-radius: 4px;
	}

	.success-is-plain:hover {
		color: #fff;
		background-color: #67c23a;
		border-color: #67c23a;
	}

	.success-is-plain {
		color: #67c23a;
		background: #f0f9eb;
		border-color: #c2e7b0;
	}

	.primary-is-plain:hover {
		color: #fff;
		background-color: #409eff;
		border-color: #409eff;
	}

	.primary-is-plain {
		color: #409eff;
		background: #ecf5ff;
		border-color: #b3d8ff;
	}

	.danger-is-plain:hover {
		color: #fff;
		background-color: #f56c6c;
		border-color: #f56c6c;
	}

	.danger-is-plain {
		color: #f56c6c;
		background: #fef0f0;
		border-color: #fbc4c4;
	}

	.is-round {
		border-radius: 20px;
		padding: 12px 23px;
	}

	.bg-img {
		position: fixed;
		width: 100%;
		height: 85%;
		top: 0;
		left: 0;
		/* z-index: -1; */
	}
</style>