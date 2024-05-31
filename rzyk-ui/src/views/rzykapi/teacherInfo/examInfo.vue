<template>

	<div>
		<div v-if="isOper===1">
			<el-row>
				<el-col :span="24">
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>今日待领卷</span>
						</div>
						<br />

						<el-button type="danger" round>
							<div @click="">去领卷</div>
						</el-button>

						<hr />
						<br />
					</el-card>
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>待交卷</span>
						</div>
						<br />

						<el-button type="danger" round>
							<div @click="">去交卷</div>
						</el-button>

						<hr />
						<br />
					</el-card>
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>督导待办</span>
						</div>
						<br />

						<el-button type="danger" round>
							<div @click="">去处理</div>
						</el-button>

						<hr />
						<br />
					</el-card>
				</el-col>
			</el-row>
		</div>
		<div v-if="isOper===2">
			<el-row>
				<el-col :span="24">
					<br />
					<br />
					<div class="center">
						<h1 v-if="examIsNull">暂无数据</h1>
						<el-card class="box-card" v-for=" (item,i) in dataList">
							<div slot="header" class="clearfix">
								<span>试卷信息</span>
							</div>
							<div>课程编号:<span v-text="item.coursenumber"></span></div>
							<div>课程名称:<span v-text="item.coursename"></span></div>
							<div>授课教师:<span v-text="item.teacher"></span></div>
							<div>教学班名称:<span v-text="item.teachingclass"></span></div>
							<div>考试地点:<span v-text="item.place"></span></div>
							<div>考场总人数:<span v-text="item.totalnum"></span></div>
							<div>考试时间:<span v-text="item.examtime"></span></div>
							<div>考试日期:<span v-text="item.examdate"></span></div>
							<div>主考人员:<span v-text="item.examiners"></span></div>
							<div>主监考:<span v-text="item.chiefinv"></span></div>
							<br />
							<el-button :type="buttonMsg" round>
								<div @click="takeExamination(item.id)">{{time}}{{submit}}</div>
							</el-button>
							<el-button type="danger" round v-if="actIndex===1">
								<div @click="">生成标语</div>
							</el-button>
							<el-button type="info" round>
								<div @click="">试卷详情</div>
							</el-button>
							<hr />
							<br />
						</el-card>

					</div>
				</el-col>
			</el-row>
		</div>

		<div v-if="isOper===3">
			<supervisor></supervisor>
		</div>
		<div class="footer">
			<!-- 
			
			
			<el-button v-model="isOper" @click="isOper=3">督导</el-button> -->
			<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
				background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
				<el-row>
					<el-col :span="5">
						<el-menu-item index="1">
							<div v-model="isOper" @click="isOper=1"><i class="el-icon-s-order"></i> 待办 </div>
						</el-menu-item>
					</el-col>
					<el-col :span="7">
						<el-submenu icon='el-icon-guide' index="2">
							<template slot="title">
								<i class="el-icon-finished"></i>试卷处理
							</template>
							<el-menu-item index="2-1">
								<div v-model="isOper" @click="getAndUpExam(1)">领卷记录</div>
							</el-menu-item>
							<el-menu-item index="2-2">
								<div @click="getAndUpExam(2)">交卷</div>
							</el-menu-item>
							<el-menu-item index="2-3">
								<div @click="getAndUpExam(3)">历史提交</div>
							</el-menu-item>
						</el-submenu>
					</el-col>
					<!-- <el-menu-item index="3">
					<div v-model="isOper" @click="toPubnotice()">标语</div>
				</el-menu-item> -->
					<el-col :span="5">
						<!-- v-hasPermi="['supervisor:supervisor:data']" -->
						<el-menu-item index="3" icon='el-icon-guide'>


							<div v-model="isOper" @click="isOper=3">
								<i class="el-icon-s-check"></i>督导
							</div>
						</el-menu-item>
					</el-col>
					<el-col :span="7">
						<el-menu-item index="4">
							<i class="el-icon-guide"></i>试卷流向
						</el-menu-item>
					</el-col>
					<!-- <el-col :span="1"></el-col> -->
				</el-row>
			</el-menu>
		</div>
	</div>
</template>

<script>
	var timer = '';

	import {
		listData,
		getData,
		getInfo
	} from "@/views/rzykapi/schedule/js/data.js";
	import {
		listSupervisor,
		getSupervisor,
		addSupervisor,
		updateSupervisor,
		getCurrDayData,
		getCurrExam,
		handShowDataById

	} from '@/views/rzykapi/teacherInfo/info/js/supervisor.js'
	import {
		Message
	} from "element-ui";
	import supervisor from "@/views/rzykapi/teacherInfo/supervisor"
	export default {
		components: {
			supervisor
		},
		data() {
			return {
				examIsNull: false,
				paperId: '',
				actIndex: 1,
				isOper: 1,
				// 遮罩层
				loading: true,
				time: '',
				dataCurrDay: {},
				dataList: [],
				buttonMsg: "info",
				submit: '',
				activeIndex: '1',
				value: '未领卷',
				queryParams: {
					pageNum: 1,
					pageSize: 10,
					coursenumber: null,
					coursename: null,
					teacher: null,
					teachingclass: null,
					studentclass: null,
					num: null,
					place: null,
					totalnum: null,
					examtime: null,
					examdate: null,
					examiners: null,
					chiefinv: null,
					assistantinv: null,
					papersnum: null,
					classescampus: null,
					examcampus: null,
					classessdept: null,
					toteachsdept: null,
					campus: null,
					capacity: null,
					examsection: null,
					college: null,
					semester: null,
					uploaddate: null,
					groupname: null,
					printnum: null,
					batch: null
				},
			}

		},
		created() {
			// todo this.$route.params
		},
		beforeMount() {
			this.getDataById();
		},
		mounted() {

			// this.upTime();
			// this.getCurrDayInfo();
		},
		methods: {
			/** 查询考试安排数据列表 */
			toPubnotice() {
				this.$router.push('/rzykapi/pubnotice')
			},
			handleSelect(key, keyPath) {

			},
			/**
			 * @param {Object} index 
			 */
			async getAndUpExam(index) {
				this.actIndex = index;
				const {
					rows: result
				} = await getCurrDayData(index);
				this.dataList = result;
				this.loading = false;
				this.buttonMsg = "primary";
				if (index === 1) {
					this.submit = "领取";
				} else if (index === 2) {
					this.submit = "交卷";
				} else if (index === 3) {
					this.submit = "待审批";
				}
				this.isOper = 2;
			},

			takeExamination(id) {
				addSupervisor({
					'paperNumber': id,
					"activeIndex": this.actIndex
				}).then(res => {
					this.$message(res.msg);
					this.getAndUpExam(this.actIndex);
				});
			},
			async getDataById() {
				let id = sessionStorage.getItem('id');
				this.paperId = id;
				const {
					data: res
				} = await handShowDataById(id);

				this.dataList = res.list
				this.isOper = res.isOper
				let index = res.actIndex
				this.actIndex = index
				this.loading = false;
				this.buttonMsg = "primary";
				if (index === 1) {
					this.submit = "领取";
					this.activeIndex = '2-1';
				} else if (index === 2) {
					this.submit = "交卷";
					this.activeIndex = '2-2';
				} else if (index === 3) {
					this.submit = "待审批";
					this.activeIndex = '2-3';
				}
				if (res.length <= 0) {
					this.examIsNull = true;
					return;
				}
			},
			async getCurrDayInfo() {
				const {
					rows: result
				} = await getCurrDayData();
				this.dataList = result;
				this.loading = false;
				const {
					data: dataRes
				} = await getCurrExam();
				this.dataCurrDay = dataRes.data;
				if (dataRes.isGet) {
					this.buttonMsg = "primary";
					this.submit = "领取";
				}
			},
			async upTime() {
				//年
				let year = new Date().getFullYear();
				//月份是从0月开始获取的，所以要+1;
				let month = new Date().getMonth() + 1;
				//日
				let day = new Date().getDate();
				//时
				let hour = new Date().getHours();
				//分
				let minute = new Date().getMinutes();
				//秒
				let second = new Date().getSeconds();
				let time = year + '-' + month + '-' + day
				var timeStart = '';
				var timeEnd = '';
				//计算一个小时毫秒值
				let oneHour = 60 * 60 * 1000;
				//获取当前时间毫秒值
				let currTime = new Date().getTime();

				this.queryParams.teacher = '仲崇轶';
				this.loading = true;

				//使用同步,避开回调地狱和异步数据未获取到问题
				const {
					rows: res
				} = await listSupervisor({
					"currentSubmitterName": this.$store.state.user.name,
					"status": "0"
				});
				// 
				var flag = false;
				// console.log("res.row.length > 0--->" + res.row.length > 0)
				if (res != undefined && res != null && res.length > 0) {
					//查询数据

					const {
						data: fin_res
					} = await getData(res[0].paperNumber);
					this.dataCurrDay = fin_res;
					this.submit = "交卷";
					let currDate = fin_res.examdate.replace("-", "/");
					let timeStr = fin_res.examtime.split('~');
					timeEnd = timeStr[1];
					let timeEndTime = new Date(currDate + " " + timeEnd + ":00")

					this.time = timeEndTime - currTime;


					console.log(this.time)
					let djs = parseInt(Math.floor(this.time / 1000) - (30 * 60));
					if (timer != '') {
						//清除多次执行定时器
						clearInterval(timer)
					}
					timer = setInterval(() => {
						djs--;
						if (djs <= 0) {
							this.submit = '交卷';
							clearInterval(timer)
							this.time = ''
						} else {
							this.time = Math.floor(djs / 60) + ":" + Math.floor(djs % 60)
						}

					}, 1000)
					return;
				} else {
					console.log("else--->")
					this.submit = "领卷";
					flag = true;
				}
				//查询数据
				const {
					rows: result
				} = await listData(this.queryParams);
				console.log(result)
				this.dataList = result;
				console.log(this.datalist)
				this.loading = false;
				var CurrDate = new Date();
				this.time = '';
				//判断是否有当天的

				for (let datas of result) {
					//获取本条时间毫秒值
					let currDate = datas.examdate.replace("-", "/");
					var timeStr = datas.examtime.split('~');
					timeStart = timeStr[0];
					timeEnd = timeStr[1];
					let examStartTime = new Date(currDate + " " + timeStart + ":00")
					let timeEndTime = new Date(currDate + " " + timeEnd + ":00")
					if ((examStartTime - currTime) <= oneHour && (examStartTime - currTime) > 0) {
						this.dataCurrDay = datas;
						this.time = examStartTime - currTime;
						// todo 考试结束时间
					} else if ((currTime - timeEndTime) <= (oneHour / 2) && (currTime - examStartTime) > 0) {
						this.dataCurrDay = datas;
						this.time = timeEndTime - currTime;
					}

				}
				if (this.time == '') {
					this.submit = '未临近考试';
					return;
				} else {
					this.submit = '领卷';
				}
				let djs = parseInt(Math.floor(this.time / 1000) - (30 * 60));
				if (timer != '') {
					//清除多次执行定时器
					clearInterval(timer)
				}
				timer = setInterval(() => {
					djs--;
					if (djs <= 0) {
						this.submit = '领卷';
						clearInterval(timer)
						this.time = ''
					} else {
						this.time = Math.floor(djs / 60) + ":" + Math.floor(djs % 60)
					}
				}, 1000)
				clearInterval(timer)
				this.time = '';
			},
			/**
			 * @param {Object} startTime
			 * @param {Object} endTime
			 * 定时器工具
			 */
			timingUtils(startTime, endTime) {
				//获取开始时间毫秒值
				let startTimeMS = new Date(startTime).getTime();
				//获取结束时间毫秒值
				let endTimeMS = new Date(endTime).getTime();
				//获取时间差
				let difference = endTimeMS - startTimeMS

				timer = setInterval(() => {
					difference--;
					this.time = Math.floor(djs / (60 * 60)) +
						":" + Math.floor(djs / 60) +
						":" + Math.floor(djs % 60)
				}, 1000)
			},
			handExamination() {

			},

		},
	}
</script>

<style>
	.el-icon-search {
		font-size: 10px;
		color: black;
	}

	.center {
		height: 800px;
		width: 800px;
		border: #fff 0.1px;
		margin: auto auto;
	}

	.footer {
		position: fixed;
		bottom: 0;
		width: 100%;
		line-height: var(--footer-height);
		/* background: #42b983; */
		color: #fff;
	}

	.text {
		font-size: 14px;
	}

	.item {
		margin-bottom: 18px;
	}

	.clearfix:before,
	.clearfix:after {
		display: table;
		content: "";
	}

	.clearfix:after {
		clear: both
	}

	.box-card {
		width: 480px;
	}
</style>
