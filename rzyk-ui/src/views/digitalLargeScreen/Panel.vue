<template>
  <div v-if="isshow">
    <div class="box">
      <h2 style="text-align: center;" v-text="test"></h2>
      <div ref="Echart" :style="{width: '100%', height: '80%'}"> </div>
    </div>

  </div>

  </div>
</template>

<script>
  export default {
    name: "Panel",
    inject: ['reload'],
    props: {
      //接受父组件传递来的数据
      test: String,
      echdata: Object,
    },
    watch: {
      echdata: function(newValue, oldValue) {
        this.getEcharts()
        /*        this.isshow = false
                this.isshow = true */
        console.log('原值:' + oldValue.xLabel + '新值:' + newValue.xLabel)
        // this.reload()

      }
    },

    data() {
      return {
        isshow: true
      };
    },
    mounted() {
      this.init()
      this.getEcharts()
    },
    methods: {
      init() {
        const self = this; //因为箭头函数会改变this指向，指向windows。所以先把this保存
        setTimeout(() => {
          window.addEventListener("resize", function() {
            self.chart = self.$echarts.init(self.$refs.Echart);
            self.chart.resize();
          });
        }, 10);
      },
      async getEcharts() {
        var myChart = this.$echarts.init(
          // document.getElementById(id)
          this.$refs.Echart
        );
        /* var myChart = this.$echarts.init(
          this.$refs.echartref
        ); */
        /*        let xLabel = [
                  "市区",
                  "市南",
                  "市北",
                  "万州",
                  "城口",
                  "秀山",
                  "川东",
                  "綦南",
                  "武隆",
                ]; */
        let xLabel = this.echdata.xLabel
        console.log("xLabel" + this.echdata.xLabel)
        console.log("data" + xLabel)
        var option = {
          tooltip: {},
          backgroundColor: "#0f375f",
          animation: false,
          grid: {
            top: "15%",
            bottom: "10%", //也可设置left和right设置距离来控制图表的大小
            left: "15%",
            // right: "10%"
          },
          xAxis: {
            data: xLabel,
            axisLine: {
              show: true, //隐藏X轴轴线
              lineStyle: {
                color: "#11417a",
              },
            },
            axisTick: {
              show: false, //隐藏X轴刻度
            },
            axisLabel: {
              show: true,
              margin: 14,
              fontSize: 14,
              textStyle: {
                color: "#A3C0DF", //X轴文字颜色
              },
            },
          },
          yAxis: [{
            type: "value",
            gridIndex: 0,
            min: 0,
            max: 100,
            interval: 25,
            // splitNumber: 4,
            splitLine: {
              show: true,
              lineStyle: {
                color: "#113763",
                width: 1,
              },
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: "#11417a",
              },
            },
            axisLabel: {
              show: true,
              margin: 14,
              fontSize: 14,
              textStyle: {
                color: "#A3C0DF", //X轴文字颜色
              },
            },
          }, ],
          series: [{
              // 值
              name: this.test,
              type: "bar",
              barWidth: 30,
              itemStyle: {
                normal: {
                  /*  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: "rgba(249,255,59,1)",
                      },
                      {
                        offset: 1,
                        color: "rgba(15,38,35,0.2)",
                      },
                    ]), */
                  color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: "rgba(249,255,59,1)",
                      }, // offset：坐标为0处的颜色
                      {
                        offset: 1,
                        color: "rgba(15,38,35,0.2)",
                      },
                    ]
                  },

                  shadowColor: "rgba(5, 213, 255, 1)",
                  shadowBlur: 4,
                },
              },
              // data: [88.6, 88.9, 89, 88.8, 88.8, 20, 20, 20, 20],
              data: this.echdata.data,
              z: 10,
              zlevel: 2,
              label: {
                show: true,
                position: "top",
                distance: 10,
                fontSize: 16,
                fontFamily: "DIN",
                color: "#ffffff",
                zlevel: 4,
                z: 10,
                formatter: "{c}%",
              },
            },
            {
              // 值分隔
              type: "pictorialBar",
              itemStyle: {
                normal: {
                  color: "#0F375F",
                },
              },
              symbolRepeat: "fixed",
              symbolMargin: 3,
              symbol: "rect",
              symbolClip: true,
              symbolSize: [30, 3],
              symbolPosition: "start",
              symbolOffset: [0, -1],
              // symbolBoundingData: this.total,
              // data: [88.6, 88.9, 89, 88.8, 88.8, 20, 20, 20, 20],
              data: this.echdata.data,
              width: 25,
              z: 0,
              zlevel: 3,
            },
            {
              //辅助背景图形
              name: "RZYK",
              type: "bar", //pictorialBar
              barWidth: "30",
              barGap: "-100%",
              itemStyle: {
                normal: {
                  borderWidth: 0,
                  color: "rgba(146,158,48,0.2)",
                },
                barBorderRadius: 10,
              },
              data: [100, 100, 100, 100, 100, 100, 100, 100, 100],
              z: 0,
              zlevel: 0,
            },
            {
              // 背景分隔
              type: "pictorialBar",
              itemStyle: {
                normal: {
                  color: "#0F375F",
                },
              },
              symbolRepeat: "fixed",
              symbolMargin: 3,
              symbol: "rect",
              symbolClip: true,
              symbolSize: [30, 3],
              symbolPosition: "start",
              symbolOffset: [0, -1],
              // symbolBoundingData: this.total,
              data: [100, 100, 100, 100, 100, 100, 100, 100, 100],
              width: 25,
              z: 0,
              zlevel: 1,
            },
          ],
        };
        await myChart.setOption(option);
      }
    }
  }
</script>

<style>
  .box {
    height: 25rem;
    width: 20rem;
    /* background-color: aquamarine; */
    margin: 15px;
    padding: 6px;
    background: linear-gradient(to left, #0DD7B5, #0DD7B5) left top no-repeat,
      linear-gradient(to bottom, #0DD7B5, #0DD7B5) left top no-repeat,
      linear-gradient(to left, #0DD7B5, #0DD7B5) right top no-repeat,
      linear-gradient(to bottom, #0DD7B5, #0DD7B5) right top no-repeat,
      linear-gradient(to left, #0DD7B5, #0DD7B5) left bottom no-repeat,
      linear-gradient(to bottom, #0DD7B5, #0DD7B5) left bottom no-repeat,
      linear-gradient(to left, #0DD7B5, #0DD7B5) right bottom no-repeat,
      linear-gradient(to left, #0DD7B5, #0DD7B5) right bottom no-repeat;
    background-size: 2px 8px, 8px 2px, 2px 8px, 8px 2px;
    color: aliceblue;
  }
</style>