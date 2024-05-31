<template>
  <div>
    <!-- <MEditor :value="briefintroduction" @change="contentChange"></MEditor> -->
    <editor ref="quillEditor" id="textarea" v-model="briefintroduction" :min-height="300">
    </editor>
    <div style="border: 1px; margin: auto auto;height: 40px;width: 200px; ">
      <el-button type="success" plain icon="el-icon-edit" size="mini" @click="updateDataInfo">保存
      </el-button>
      <!-- <button class="tag-read" data-clipboard-text=":<span v-text='list.examdate'></span>"-->
    </div>
    <br />
    <!-- copyDate(e.COLUMN_NAME) -->
    <div style="padding-top: 5px;" v-for="item in tableField">
      <span v-for="e in item">
        &nbsp;
        <el-button class="tag-read" type="info" plain :data-clipboard-text="copyDate(e.COLUMN_NAME)"
          @click="copy(e.COLUMN_NAME)" v-text="e.COLUMN_COMMENT"></el-button>
      </span>

    </div>

    <!-- <button class="tag-read" :data-clipboard-text="copyDate(' 考试时间')" @click="copy('考试时间')">考试时间</button> -->
  </div>
</template>
<script>
  import MEditor from "@/utils/wangeditor/editor";
  import Clipboard from 'clipboard';
  import {
    getData,
    updateData,
    selectTableField
  } from "@/views/rzykapi/warmprompt/js/data.js";
  export default {
    components: {
      MEditor
    },
    data() {
      return {
        briefintroduction: '',
        tableField: []
      }
    },
    methods: {
      contentChange(htmlContent) {
        this.briefintroduction = htmlContent;
        console.log(htmlContent)
      },
      getDataInfo() {
        getData().then(res => {
          this.briefintroduction = res.rows[0].noticeContent;
        })
      },
      updateDataInfo() {
        console.log(this.briefintroduction)
        updateData(this.briefintroduction).then(res => {
          this.$message.success('保存成功')
        })
      },
      copy(str) {
        var clipboard = new Clipboard('.tag-read')
        clipboard.on('success', e => {
          console.log('复制成功')
          // 释放内存
          clipboard.destroy()
        })
        clipboard.on('error', e => {
          // 不支持复制
          console.log('该浏览器不支持自动复制')
          // 释放内存
          clipboard.destroy()
        })
        const in_data = this.briefintroduction.indexOf(str);
        this.briefintroduction = this.briefintroduction.replace(str, str)
      },
      copyDate(str) {
        return ":{{" + str + "}}"
      },
      initTableField() {
        selectTableField().then(res => {
          this.tableField = res.rows
          console.log(res)
        })
      },
    },
    mounted() {
      this.getDataInfo();
      this.initTableField();
    }
  }
</script>