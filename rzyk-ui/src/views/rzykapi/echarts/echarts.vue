<template>
  <div ref="echart" :style="{width: '100%', height: '100%'}"></div>
</template>
<script>
  export default {
    props: {
      title: String,
      echartsData: Object,
    },
    data() {
      return {};
    },
    // 页面初始化挂载dom
    mounted() {
      this.getLoadEcharts();
    },
    methods: {
      getLoadEcharts() {
        var myChart = this.$echarts.init(
          this.$refs.echart
          // document.getElementById("echart")
        );

        //名称
        let dataX = this.echartsData.xLabel
        //数据
        let dataY = this.echartsData.data
        let zoomShow = false;
        if (dataY.length > 14) {
          zoomShow = true;
        } else {
          zoomShow = false;
        }
        var option = {
          backgroundColor: "#0D2753",
          tooltip: {
            trigger: "axis",
            axisPointer: {
              type: "shadow",
            },
          },
          grid: {
            top: "10%",
            right: "5%",
            left: "15%",
            bottom: "30%",
          },
          xAxis: [{
            type: "category",
            data: dataX,
            axisLine: {
              lineStyle: {
                color: "rgba(66, 192, 255, .3)",
              },
            },
            axisLabel: {
              interval: 0,
              margin: 10,
              color: "#05D5FF",
              textStyle: {
                fontSize: 11,
              },
              rotate: "45",
            },
            axisTick: {
              //刻度
              show: false,
            },
          }, ],
          yAxis: [{
            axisLabel: {
              padding: [3, 0, 0, 0],
              formatter: "{value}",
              color: "rgba(95, 187, 235, 1)",
              textStyle: {
                fontSize: 11,
              },
            },
            axisTick: {
              show: true,
            },
            axisLine: {
              lineStyle: {
                color: "rgba(66, 192, 255, .3)",
              },
            },
            splitLine: {
              lineStyle: {
                color: "rgba(255,255,255,0)",
              },
            },
          }, ],
          dataZoom: [
            //滚动条
            {
              show: zoomShow,
              type: "slider",
              realtime: true,
              startValue: 0,
              endValue: 14,
              xAxisIndex: [0],
              bottom: "10",
              left: "30",
              height: 10,
              borderColor: "rgba(0,0,0,0)",
              textStyle: {
                color: "#05D5FF",
              },
            },
          ],
          series: [{
            type: "bar",
            data: dataY,
            barWidth: "10",
            itemStyle: {
              normal: {
                /*               color: new echarts.graphic.LinearGradient(
                                 0, 0, 0, 1,
                                 [{
                                     offset: 0,
                                     color: "rgba(5, 213, 255, 1)", // 0% 处的颜色
                                   },
                                   {
                                     offset: 0.98,
                                     color: "rgba(5, 213, 255, 0)", // 100% 处的颜色
                                   },
                                 ],
                                 false
                               ), */
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [{
                      offset: 0,
                      color: '#2378f7'
                    }, // offset：坐标为0处的颜色
                    {
                      offset: 0.7,
                      color: '#2378f7'
                    },
                    {
                      offset: 0.98,
                      color: '#83bff6'
                    } // offset：坐标为1处的颜色
                  ]
                },

                shadowColor: "rgba(5, 213, 255, 1)",
                shadowBlur: 4,
              },
            },
            label: {
              normal: {
                show: true,
                lineHeight: 10,
                formatter: "{c}",
                position: "top",
                textStyle: {
                  color: "#fff",
                  fontSize: 10,
                },
              },
            },
          }, ],
        };
        myChart.setOption(option);
      }
    }
  };
</script>
<style>
  .container {
    width: 100%;
    height: 100%;
    background-color: black;
  }


  .ty {
    background-color: blanchedalmond;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
  }

  #echart {
    width: 100%;
    height: 100%;
  }

  .testbox {
    border: 2px solid transparent;
    background-color: #f9fafc;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    border-color: black;
    height: 400px;
    width: 500px;
    /* background-color: aqua; */
  }
</style>