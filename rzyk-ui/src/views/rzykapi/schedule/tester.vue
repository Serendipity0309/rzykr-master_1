<template>
    <div>
        <!-- 设置了两个块区域 当点击了生成海报时把canvas转换成base64的图片，然后把最开始当样式隐藏掉 把获取的的base64的地址赋值给src-->
        <img class="kahuf" v-if="!test" :src="imgSrc" alt />
        <div v-else>
            <div class="baijing">
                <img :src="logo" alt height="400px" width="400px" />
                <!-- . //图片这里自己找一个 -->
                <div id="qrcode"></div>
            </div>
            <button @click="btn" v-if="shuuju">点我生成</button>
        </div>
    </div>
</template>

<script>
    import {
        qrcanvas
    } from "qrcanvas";
    //将整个页面转换成canvas
    import html2canvas from "html2canvas";

    export default {
        data() {
            return {
                test: true,
                imgSrc: "",
                logo: require('@/assets/logo/logo.png'),
                shuuju: true

            };
        },
        methods: {
            btn() {
                this.shuuju = false
                //生成一个二维码 data是二维码跳转的地址（写自己需要跳转的地址即可）
                const canvas = qrcanvas({
                    data: "https://www.baidu.com/s?ie=UTF-8&wd=%E8%8A%B1%E7%93%A3&tn=78000241_30_hao_pg",
                    // console.log( data)
                });
                document.getElementById("qrcode").appendChild(canvas);
                //点击生成canvas转换成base64的图片
                this.$nextTick(() => {
                    const that = this;
                    html2canvas(document.body).then(function(canvas) {
                        // console.log(canvas.toDataURL());
                        that.imgSrc = canvas.toDataURL();
                    });
                    //隐藏元素
                    this.test = false;
                });
            },
        },
        mounted() {},
    };
</script>
