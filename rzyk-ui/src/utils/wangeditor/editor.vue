<template>
    <div>
        <div ref="editor" style="text-align:left">
        </div>
    </div>
</template>

<script>
    import E from 'wangeditor' //引入编辑器

    /* import {
         uploadPicture
     } from "@/api/resume/briefIntroduction"; */ //图片上传方法
    import {
        getToken
    } from "@/utils/auth"; //获取用户验证token
    export default {
        name: "MEditor",
        props: {
            /*编辑器的内容*/
            value: {
                type: String
            },
        },
        data: function() {
            return {
                editor: null,
                Title: '',
                Content: this.value,
            }
        },
        mounted: function() {
            let That = this;
            this.editor = new E(this.$refs.editor);
            this.editor.config.placeholder = ''
            //图片上传数量限制
            this.editor.config.uploadImgMaxLength = 1;
            //自定义图片上传方法
            this.editor.config.customUploadImg = function(resultFiles, insertImgFn) {
                let data = new FormData;
                data.append('introductionPicture', resultFiles[0]);
                uploadPicture({
                    Authorization: "Bearer" + getToken(),
                }, data).then(res => {
                    insertImgFn(res.imgUrl)
                });
                // 上传图片，返回结果，将图片插入到编辑器中
            };
            this.editor.config.onchange = function(newHtml) {
                That.$emit('change', newHtml)
            }; //组件内容改变，将文本返回到父组件
            this.editor.create();
            this.editor.txt.html(That.Content); //初始化编辑器内文本内容
        },
        watch: {
            value: { //监听value的内容，如果发生改变将文本内容重新写入编辑器
                handler(val) {
                    if (val !== this.Content) {
                        this.Content = val === null ? "" : val;
                        if (this.editor) {
                            this.editor.txt.html(this.Content);
                        }
                    }
                },
                immediate: true,
            },
        },
        beforeDestroy() {
            // 销毁编辑器
            this.editor.destroy()
            this.editor = null
        },
    }
</script>
