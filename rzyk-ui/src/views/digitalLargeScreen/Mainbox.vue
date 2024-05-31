<template>
  <div class="mainbox">
    <!-- 左边的图形展示 -->
    <div class="column">
      <Panel v-if="isshow" :test="'监考场次排名-正序'" :echdata="InvigilationRankingASC"></Panel>
      <Panel v-if="isshow" :test="'试卷提交驳回排名-正序'" :echdata="RejecCountDESC"></Panel>
      <!-- <Panel :test="'提交被驳回排名-正序'" :echdata="childrenData[0]"></Panel> -->
    </div>
    <!-- 中间的图形展示 -->
    <div class="column">
      <!-- 首页数字展示 -->
      <div class="mainboxtop">
        <div class="tophd">
          <ul>
            <li>总共考试</li>
            <li>参与老师</li>
          </ul>
        </div>
        <div class="topbd">
          <ul>
            <li>{{countExamlog}}</li>
            <li>{{countTeacher}}</li>
          </ul>
        </div>
      </div>
      <!-- 首页地图展示 -->
      <div class="mainboxbody">
        <div ref="chinamap" class="chinamap"></div>
        <!-- 背景地球 -->
        <!-- <div class="map1"></div> -->
        <!-- 正旋转 -->
        <!-- <div class="map2"></div> -->
        <!-- 逆旋转 -->
        <div class="map3"></div>

      </div>
      <div class="centerData">
        <countecharts v-if="isshow" :title="驳回原因统计" :echartsData="examLogListDocument"></countecharts>
      </div>

    </div>
    <!-- 右边的图形展示 -->
    <div class="column">
      <Panel v-if="isshow" :test="'监考场次排名-倒序'" :echdata="InvigilationRankingDESC"></Panel>
      <Panel v-if="isshow" :test="'试卷提交驳回排名-倒序'" :echdata="RejecCountASC"></Panel>
      <!-- <Panel :test="'提交被驳回排名-倒序'" :echdata="childrenData[0]"></Panel> -->
    </div>
  </div>
</template>
<script>
  import countecharts from "../rzykapi/echarts/echarts.vue"
  import {
    getcountdata
  } from "../digitalLargeScreen/js/data.js"
  // import MyEcharts from "./MyEcharts.vue";
  // import "../../node_modules/echarts/dist/china.js";
  // import "../../node_modules/echarts/map/js/china.js";
  // import "../config/chinamap.js";
  // import "../config/china.js";
  import * as echarts from "echarts";
  import Panel from "../digitalLargeScreen/Panel.vue"
  var yearData = [];
  var geoCoordMap = {};
  var XAData = [];
  var XNData = [];
  var YCData = [];
  export default {
    components: {
      countecharts,
      Panel
    },
    name: "MainBox",

    data() {
      return {
        //这里存放图表
        chartsList: [],
        mycharts: null,
        chartFun: null,
        InvigilationRankingASC: {},
        InvigilationRankingDESC: {},
        RejecCountDESC: {},
        RejecCountASC: {},
        examLogListDocument: {},
        countExamlog: '',
        countTeacher: '',
        isshow: false,

      };
    },
    beforeDestroy() {
      // 移除窗口改变监听
      window.removeEventListener("resize", this.chartFun);
    },
    created() {

    },
    beforeMount() {
      this.initList()
    },
    mounted() {

    },
    methods: {
      initList() {
        getcountdata().then(res => {
          console.log(res)
          this.InvigilationRankingASC = res.data.InvigilationRankingASC;

          this.InvigilationRankingDESC = res.data.InvigilationRankingDESC;

          this.RejecCountDESC = res.data.RejecCountDESC;

          this.RejecCountASC = res.data.RejecCountASC;

          this.examLogListDocument = res.data.examLogListDocument;

          this.countExamlog = res.data.countExamlog

          this.countTeacher = res.data.countTeacher

          this.isshow = true

        })
      }
    },
  }
</script>
<style scoped>
  .mainbox {
    display: flex;
    /* background-color: pink; */
    padding: 0.125rem 0.125rem 0;
    background-image: url(../../assets/images/csBack.png);
  }

  .column {
    /* height: 10rem; */
    /* background-color: red; */
    flex: 3;
  }

  .mainbox .column:nth-child(2) {
    /* background-color: blue; */
    padding: 0 0.125rem 0.1875rem;
    /* background-color: blue; */
    flex: 5;
  }

  /* 以下是存放中国地图的容器样式 */
  .mainboxtop {
    background-color: rgba(101, 132, 226, 0.1);
    padding: 0.1875rem;
  }

  .tophd {
    position: relative;
    border: 1px solid rgba(25, 186, 139, 0.17);
  }

  @font-face {
    font-family: electronicFont;
    src: url(../../assets/images/csBack.png);
  }

  .tophd>ul,
  .topbd>ul {
    display: flex;
  }

  .tophd>ul>li {
    flex: 1;
    display: inline-block;
    height: 1rem;
    color: #ffeb7b;
    font-size: 0.875rem;
    font-family: electronicFont;
  }

  .tophd>ul::after {
    position: absolute;
    right: 50%;
    width: 0.0125rem;
    height: 50%;
    top: 25%;
    background-color: rgba(255, 255, 255, 0.2);
    content: "";
  }

  .tophd::before,
  .tophd::after {
    position: absolute;
    content: "";
    width: 0.375rem;
    height: 0.125rem;
  }

  .tophd::before {
    top: 0;
    left: 0;
    border-top: 2px solid #02a6b5;
    border-left: 2px solid #02a6b5;
  }

  .tophd::after {
    bottom: 0;
    right: 0;
    border-bottom: 2px solid #02a6b5;
    border-right: 2px solid #02a6b5;
  }

  .topbd>ul>li {
    flex: 1;
    height: 0.5rem;
    line-height: 0.5rem;
    color: rgba(255, 255, 255, 0.7);
    font-size: 0.225rem;
    padding-top: 0.125rem;
  }

  .mainboxbody {
    position: relative;
    width: 100%;
    height: 10.125rem;
    /* background-color: pink; */
  }

  .chinamap {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 5;
    height: 10.125rem;
    width: 100%;
  }

  .map1,
  .map2,
  .map3 {
    position: absolute;
    top: 50%;
    left: 50%;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    transform: translate(-50%, -50%);
    width: 6.475rem;
    height: 6.475rem;
    opacity: 0.3;
  }

  .map1 {
    background-image: url(../../assets/images/csBack.png);
    animation: rotate 15s linear infinite;
  }

  .map2 {
    width: 8.0375rem;
    height: 8.0375rem;
    background-image: url(../../assets/images/csBack.png);
    opacity: 0.8;
    animation: rotate 5s linear infinite;
    z-index: 2;
  }

  .map3 {
    width: 7.075rem;
    height: 7.075rem;
    background-image: url(../../assets/logo/logo.png);
    animation: rotate1 10s linear infinite;
  }

  @keyframes rotate {
    from {
      transform: translate(-50%, -50%) rotate(0deg);
    }

    to {
      transform: translate(-50%, -50%) rotate(360deg);
    }
  }

  @keyframes rotate1 {
    from {
      transform: translate(-50%, -50%) rotate(0deg);
    }

    to {
      transform: translate(-50%, -50%) rotate(-360deg);
    }
  }

  .centerData {
    width: 35rem;
    height: 35rem;
  }
</style>