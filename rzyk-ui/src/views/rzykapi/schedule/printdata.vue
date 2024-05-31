<template>
  <div class="printSample">
    <!--        <el-button @click="myPrintSetup2" type="success" plain icon="el-icon-printer" size="mini"
            v-hasPermi="['system:data:print']">打印维护</el-button> -->
    <el-row>
      <el-col :span="8">
        <el-card :body-style="{ padding: '0px' }">
          <img :src="src" class="image">
          <div style="padding: 14px;">
            <span>打印总数:</span>
            <br />
            <span>已打印:{{residue}}</span>
            <div class="bottom clearfix">
              <time f class="time">{{ currentDate }}</time><br />
              <el-button @click="myPreview(1)" type="success" plain icon="el-icon-printer" size="mini"
                v-hasPermi="['system:data:print']">开始打印</el-button>
              <el-button @click="myPreview(0)" type="success" plain icon="el-icon-printer" size="mini"
                v-hasPermi="['system:data:print']">预览</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-table :data="showPrintList" v-el-table-infinite-scroll="load" height="460px">
          <el-table-column label="课程编号" align="center" prop="coursenumber" width="180" />
          <el-table-column label="课程名称" align="center" prop="coursename" width="180" />
          <el-table-column label="授课教师" align="center" prop="teacher" width="180" />
          <el-table-column label="教学班名称" align="center" prop="teachingclass" width="180" />
          <el-table-column label="考试地点" align="center" prop="place" width="180" />
          <el-table-column label="主考人员" align="center" prop="examiners" width="180" />
          <el-table-column label="主监考" align="center" prop="chiefinv" width="180" />
        </el-table>
      </el-col>


    </el-row>
    <!-- 	<el-button @click="myPreview()" type="success" plain icon="el-icon-printer" size="mini"
				v-hasPermi="['system:data:print']">打印</el-button> -->

  </div>
</template>
<script languagen="script" defer=true>
  import getLodop from "@/utils/lodop/lodop";
  import myimg from "@/assets/images/login-background.jpg"
  import mylogo from "@/assets/logo/logo.png"
  import mylogo01 from "@/assets/logo/logo01.png"
  import printStatic from "@/assets/images/print-static.jpg"
  import printDo from "@/assets/images/print-do.gif"
  import {
    getSchoolDivisionLogo
  } from '@/views/rzykapi/schoolDivision/api'


  import vueqr from 'vue-qr'
  // import QRCode from 'qrcodejs2'
  import tester from "@/views/rzykapi/schedule/tester"
  import QRCode from 'qrcode'

  import {
    getqrcode
  } from "@/views/rzykapi/schedule/js/data.js";
  import {
    Alert
  } from "element-ui";
  export default {
    components: {
      vueqr,
      tester,
      ispreview: 0
    },
    watch: { // watch 监听 props 中的值
      printfdata(newVal, oldVal) { // newVal 为新值， oldVal 为旧值
        this.localLevel = newVal
      }
    },
    data() {
      return {
        Divisionlogo: '',
        residue: 0,
        count: 0,
        currentDate: new Date(),
        src: '',
        imgUrl: '',
        LODOP: null,
        myimg: '',
        mylogo01: '',
        //  text: 'http://192.168.1.3' + "/notice",
        idurl: 'before',
        logo: require('@/assets/logo/logo.png'),
        // 非多个禁用
        multiple: true,
        isShow: false,
        printfdata: [{}],
        showPrintList: [],
        form: {
          id: null,
          coursenumber: null,
          coursename: null,
          teacher: null,
          teachingclass: null,
          studentclass: null,
          num: null,
          place: null,
          totalnum: null,
          examtime: null,
          examdate: null,
          examiners: null,
          chiefinv: null,
          assistantinv: null,
          papersnum: null,
          classescampus: null,
          examcampus: null,
          classessdept: null,
          toteachsdept: null,
          campus: null,
          capacity: null,
          examsection: null,
          college: null,
          semester: null,
          uploaddate: null,
          groupname: null,
          printnum: null,
          batch: null
        }
      };
    },
    beforeMount() {
      this.printfdata = this.$route.params.printfdata

      // this.showPrintList = this.$route.params.printfdata
      this.src = printStatic
      // this.idurl = "localhost/" + this.printfdata[0].id
      // console.log("idurl" + this.idurl)
    },
    mounted() {
      // this.useqrcode(); //生成二维码
    },

    created() {

    },
    methods: {
      load() {
        this.count += 2
      },
      myPrintSetup2() {
        this.CreatePage();
        this.LODOP.PRINT_SETUP();
      },
      async myPreview(ispreview) {
        this.ispreview = ispreview
        this.src = printDo
        this.isRf = true
        this.isShow = true
        // console.log(this.printfdata)
        //如果printfdata位空 添加一个空对象 防止报错
        if (this.printfdata.length < 1) {
          this.printfdata.push(this.form)
          return
        }
        for (let i = 0; i < this.printfdata.length; i++) {
          // this.idurl = '//192.168.1.3' + "/notice/" + this.printfdata[i].id;
          /** 查询考试安排数据列表 */
          this.residue++
          this.showPrintList.unshift(this.printfdata[i])
          //获取二维码路径
          const {
            msg: imgUrl
          } = await getqrcode(this.printfdata[i].id);
          this.imgUrl = imgUrl;
          //获取分院logo
          const {
            msg: thislogo
          } = await getSchoolDivisionLogo(this.printfdata[i].id);
          this.Divisionlogo = thislogo;
          console.log(thislogo)
          this.myPreview1(this.printfdata[i]);

        }
        this.src = printStatic
      },
      myPreview1(row) {
        this.CreatePage(row);
        this.LODOP.SET_SHOW_MODE("BKIMG_PRINT", 1);
        this.LODOP.SET_PRINT_MODE("PRINT_START_PAGE", 1); //设置从第2页开始打印
        this.LODOP.SET_PRINT_MODE("PRINT_END_PAGE", 1); //设置从第3页结束打印
        if (this.ispreview === 0) {
          this.LODOP.PREVIEW();
        } else {
          LODOP.SET_LICENSES("", "EE0887D00FCC7D29375A695F728489A6", "C94CEE276DB2187AE6B65D56B3FC2848", "");
          // this.LODOP.SET_LICENSES("", "25F0A364015260BFDFF8A77225E683D4E24", "", "");
          this.LODOP.PRINT();
        }


      },
      // 多选框选中数据
      handleSelectionChange() {
        this.multiple = !this.printfdata.length
      },
      CreatePage(row) {
        console.log(row)
        //设计网址 http://demo.sinoverse.cn/lodop_print
        this.LODOP = getLodop();
        // this.LODOP.PRINT_INIT("");
        this.LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        //LODOP.NewPage();
        // LODOP.ADD_PRINT_TEXT(18, 160, 220, 40, 'ccj');
        // console.log(row)
        LODOP.ADD_PRINT_TEXT(18, 80, 220, 40, row.classessdept);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "微软雅黑");
        LODOP.ADD_PRINT_IMAGE(5, 5, 60, 60,
          " <img    src='" + process.env.VUE_APP_BASE_API + this
          .Divisionlogo +
          "' />");
        // " <img  src='http://localhost:8080/profile/upload/2023/03/15/背景_20230315204902A001.jpg' />");
        var py = 10;
        LODOP.SET_PRINT_STYLEA(0, "Stretch", 1); //(可变形)扩展缩放模式
        LODOP.SET_PRINT_STYLE("FontSize", 10);
        LODOP.ADD_PRINT_HTM(70, 0, 365, 2, "<hr>");
        LODOP.ADD_PRINT_TEXT(86, 20 - py, 180, 15, "课程编号：" + row.coursenumber);
        // LODOP.ADD_PRINT_TEXT(66, 180, 100, 15, row.printnum);
        LODOP.ADD_PRINT_TEXT(105, 20 - py, 280, 17, "课程名称：" + row.coursename);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        // LODOP.ADD_PRINT_TEXT(126, 20 - py, 280, 15, "主考人员：" + row.teacher);
        LODOP.ADD_PRINT_TEXT(126, 20 - py, 280, 15, "主考人员：" + row.examiners );

        LODOP.ADD_PRINT_TEXT(144, 20 - py, 280, 17, "考试总人数：" + row.totalnum);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.ADD_PRINT_TEXT(144, 135 - py, 280, 17, "人数：" + row.num);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        // LODOP.ADD_PRINT_TEXT(166, 20, 280, 30, "授课班名称：" + row.teachingclass);
        LODOP.ADD_PRINT_TEXT(200, 20 - py, 280, 15, "授课老师：" + row.teacher);
        LODOP.ADD_PRINT_TEXT(226, 20 - py, 280, 85, "学生班级：" + row.studentclass);
        // LODOP.ADD_PRINT_TEXT(218, 20 - py, 280, 85, "学生班级：" + row.studentclass);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        // LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.ADD_PRINT_TEXT(297, 20 - py, 280, 15, "考试日期：" + row.examdate);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.ADD_PRINT_TEXT(314, 20 - py, 280, 15, "考试时间：" + row.examtime);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.ADD_PRINT_TEXT(336, 20 - py, 280, 17, "考试地点：" + row.place);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.ADD_PRINT_TEXT(357, 20 - py, 280, 17, "主监考：" + row.chiefinv);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.ADD_PRINT_TEXT(380, 20 - py, 280, 17, "副监考：" + row.assistantinv);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.ADD_PRINT_HTM(400, 0, 365, 2, "<hr>");
        LODOP.ADD_PRINT_IMAGE(410, 20 - py, 280, 100,
          " <img src='" + process.env.VUE_APP_BASE_API + "/" + this.imgUrl + "' />");
        LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
        LODOP.SET_LICENSES("", "25F0A364015260BFDFF8A77225E683D4E24", "", "");
        LODOP.ADD_PRINT_TEXT(425, 121, 140, 50, "扫一扫，考务信息“码”上知道！");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        //LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_LICENSES("", "25F0A364015260BFDFF8A77225E683D4E24", "", "");
      },
    },
  };
</script>
<style>
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
</style>
