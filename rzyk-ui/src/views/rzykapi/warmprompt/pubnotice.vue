<template>
    <div>
        <div style="height: auto; width: 900px; margin: auto auto;" v-html="briefintroduction"></div>
    </div>
</template>

<script>
    import {
        getData
    } from "@/views/rzykapi/warmprompt/js/data.js";

    import {
        getDataSch
    } from "@/views/rzykapi/schedule/js/data.js";

    export default {
        data() {
            return {
                briefintroduction: ''
            }
        },
        methods: {
            getDataInfo() {
                getData().then(res => {
                    this.briefintroduction = res.rows[0].noticeContent;
                })
            },
        },
        mounted() {
            this.getDataInfo();
        }
    }
</script>

<style>
</style>
