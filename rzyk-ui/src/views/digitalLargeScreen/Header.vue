<template>
  <div class="header">
    <div class="centerBox">
      <p></p>
      <h1>数据概览</h1>
      <div class="showtime">时间</div>
    </div>
  </div>
</template>
<script>
  export default {
    name: "Header",
    mounted() {
      var t = null;
      t = setTimeout(time, 1000); //開始运行
      function time() {
        clearTimeout(t); //清除定时器
        let dt = new Date();
        var y = dt.getFullYear();
        var mt = dt.getMonth() + 1;
        var day = dt.getDate();
        var h = dt.getHours(); //获取时
        var m = dt.getMinutes(); //获取分
        var s = dt.getSeconds(); //获取秒
        document.querySelector(".showtime").innerHTML =
          y + "/" + mt + "/" + day + " -" + h + ":" + m + ":" + s;
        t = setTimeout(time, 1000); //设定定时器，循环运行
      }
    },
  };
</script>
<style scoped>
  .header {
    position: relative;
    width: 100%;
    height: 3.25rem;
    /* background-color: pink; */
    background: url(../../assets/images/csBack.png) no-repeat;
    background-position: top center;
    background-size: cover;
  }

  h1 {
    margin-top: 1.5rem;
    color: #fff;
    text-align: center;
    line-height: 4.5rem;
    font-size: 1.475rem;
  }

  .showtime {
    /* overflow: hidden; */
    width: 50.5625rem;
    height: 1rem;
    position: absolute;
    top: 0;
    right: 0.375rem;
    text-align: center;
    line-height: 0.9375rem;
    font-size: 1.25rem;
    color: rgba(255, 255, 255, 0.7);
    line-height: 2rem;
  }

  .centerBox {
    /* margin-top: 10rem; */
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
</style>