<template>
    <div>
        <vue-qr :text="text" :size="90" :logoSrc="logo" :logoScale="0.2">
        </vue-qr>
    </div>
</template>
<script>
    import VueQr from 'vue-qr'
    export default {
        name: '',
        components: {
            VueQr,
        },
        props: {
            printfdata: { //这个就是父组件中子标签自定义名字
                type: Array,
                required: true
            }
        },
        data() {
            return {
                text: 'http:192.168.10.1',
                logo: require('@/assets/logo/logo.png'),
            }
        },
        methods: {},
    }
</script>
