<template>
	<view>
		<uni-card :isShadow="true" title="收材料要求" mode="basic" thumbnail="/static/favicon.png" note="true">
			<view v-if="invigilationIsSubmit==1">
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
			<view v-if="invigilationIsSubmit==2">
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
				</form>
			</view>
			<view v-show="invigilationIsSubmit==1">
				<button type="info" @click="teacherLeadsThePaper()" size="10">授课老师领卷</button>
			</view>
			<view v-show="invigilationIsSubmit==2">
				<button type="primary" @click="teacherHandThePaper()" size="10">
					授课老师交卷</button>
			</view>
			<view v-show="invigilationIsSubmit==3">
				<checkbox-group @change="handleChange">
					<label v-for="e in sunmitDocumnets"
						style="padding-top:5% ; display: flex;flex-direction: row;font-size: 28upx;">
						<view>
							<checkbox checked="checked" disabled :value="e.id">{{e.detail}}</checkbox>
						</view>
					</label>
				</checkbox-group>
			</view>
		</uni-card>
	</view>
</template>

<script>
	import config from '@/config'
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
				invigilationIsSubmit: -1
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
			 * 授课老师交卷
			 */
			teacherHandThePaper() {
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

		}
	}
</script>

<style>

</style>