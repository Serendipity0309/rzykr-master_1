<template>
	<view class="content">
		<view id="container"></view>
		<view>{{latitude}}</view>
		<view>{{longitude}}</view>
		<view>{{province}}</view>
		<view>{{city}}</view>
		<view>{{district}}</view>
		<view>{{detail}}</view>
	</view>
</template>

<script>
	const img = '/static/logo.png';
	import AMapLoader from '@amap/amap-jsapi-loader';
	export default {
		data() {
			return {
				latitude: '',
				longitude: '',
				province: '',
				city: '',
				district: '',
				detail: ''
			}
		},
		created() {},
		mounted() {
			this.initMap()
		},
		methods: {

			initMap() {

				AMapLoader.load({
					key: "c0ccad2a0c07ac86361b8f7116f5d352", // 申请好的Web端开发者Key，首次调用 load 时必填
					version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
					plugins: ['AMap.Geocoder', 'AMap.Geolocation'], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
				}).then((AMap) => {
					var map = new AMap.Map('container', {
						zoom: 15,
						resizeEnable: true
					});
					map.plugin('AMap.Geolocation', () => {
						var geolocation = new AMap.Geolocation({
							enableHighAccuracy: true, //是否使用高精度定位，默认:true
							timeout: 100, //超过10秒后停止定位，默认：无穷大
							maximumAge: 0, //定位结果缓存0毫秒，默认：0
							convert: true, //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
							showButton: true, //显示定位按钮，默认：true
							buttonPosition: 'RB', //定位按钮停靠位置，默认：'LB'，左下角
							buttonOffset: new AMap.Pixel(10, 20),
							showMarker: true, //定位成功后在定位到的位置显示点标记，默认：true
							showCircle: true, //定位成功后用圆圈表示定位精度范围，默认：true
							panToLocation: true, //定位成功后将定位到的位置作为地图中心点，默认：true
							zoomToAccuracy: true, //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
						});
						map.addControl(geolocation);

						//获取定位信息
						geolocation.getCurrentPosition((status, result) => {
							if (status == 'complete') {
								//成功执行的回调函数
								this.latitude = result.position.lat
								this.longitude = result.position.lng
								this.onComplete(result)
							} else {
								//失败执行的回调函数
								this.onError(result)
							}
						});
					});
				}).catch(e => {
					console.log(e);
				})
			},
			onComplete(data) {


				console.log('定位成功')
				//发送请求
				uni.request({
					url: 'https://restapi.amap.com/v3/geocode/regeo', //逆地理编码接口地址。
					data: {
						key: 'c0ccad2a0c07ac86361b8f7116f5d352',
						//location：经纬度  lng ：经度  lat：纬度  
						location: data.position.lng + ',' + data.position.lat,
						radius: 1000,
						extensions: 'all',
						batch: false,
						roadlevel: 0
					},
					header: {
						//自定义请求头信息
					},
					success: (res) => {
						// this.detail = res
						//详细地址信息
						// console.log("res===" + res.data.regeocode);
					}
				});
			},
			//解析定位错误信息
			onError(data) {
				console.log('定位失败')
				console.log('失败原因排查信息:' + data.message)
				console.log('浏览器返回信息:' + data.originMessage)
			},

		}
	}
</script>

<style>
	.content {
		flex: 1;
	}

	.map {
		flex: 1;
	}

	#container {
		padding: 0px;
		margin: 10px;
		width: 100%;
		height: 280px;
	}
</style>
<view id="container"></view>