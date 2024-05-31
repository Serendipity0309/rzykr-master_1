<template>
  <div><!--
    <div>

      <el-image class="backgroundImage" :src="mybakimage">
        <div slot="placeholder" class="image-slot">
          加载中<span class="dot">...</span>
        </div>
      </el-image>
      <div>
        <el-row>
          <el-col :span="24">
            <div>贵州师范学院考务数据汇总</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="1">
            <div class="grid-content bg-purple-light"></div>
          </el-col>
          <el-col :span="10">
            <div> <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="date" label="排名" width="180">
                </el-table-column>
                <el-table-column prop="date" label="姓名" width="180">
                </el-table-column>
                <el-table-column prop="date" label="监考场次" width="180">
                </el-table-column>
                <el-table-column prop="name" label="批改场次" width="180">
                </el-table-column>
                <el-table-column prop="address" label="被驳回场次">
                </el-table-column>
              </el-table></div>
          </el-col>
          <el-col :span="1">
            <div class="grid-content bg-purple-light"></div>
          </el-col>
          <el-col :span="10">
            <h3 style="text-align:center">被驳回原因统计</h3>
            <div class="testbox">

              <countecharts></countecharts>

            </div>


          </el-col>
          <el-col :span="2">
            <div class="grid-content bg-purple-light"></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <div> <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="date" label="排名" width="180">
                </el-table-column>
                <el-table-column prop="date" label="姓名" width="180">
                </el-table-column>
                <el-table-column prop="date" label="监考场次" width="180">
                </el-table-column>
                <el-table-column prop="name" label="批改场次" width="180">
                </el-table-column>
                <el-table-column prop="address" label="被驳回场次">
                </el-table-column>
              </el-table></div>
          </el-col>
        </el-row>
      </div>
    </div> -->
    <Header></Header>
    <mainvue></mainvue>
  </div>


</template>

<script>
  import mybakimage_import from "../assets/images/login-background.jpg"
  import countecharts from "../views/rzykapi/echarts/echarts.vue"
  import mainvue from "../views/digitalLargeScreen/Mainbox.vue"
  import Header from "../views/digitalLargeScreen/Header.vue"
  export default {
    components: {
      countecharts,
      mainvue,
      Header
    },
    name: "Index",
    data() {
      return {
        mybakimage: mybakimage_import,
        // 版本号
        version: "3.8.4",
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
      };
    },
    methods: {
      goTarget(href) {
        window.open(href, "_blank");
      },
    },
  };
</script>

<style scoped lang="scss">
  .center {
    margin: 0 auto;
  }

  .backgroundImage {
    margin-top: 4%;
    margin-left: 1%;
    z-index: -1;
    width: 90%;
    height: 90%;
    position: fixed;
    top: 0;
    opacity: 0.5;
  }


  //ceshi
  .el-row {
    margin-bottom: 20px;

    &:last-child {
      margin-bottom: 0;
    }
  }

  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  /* .bg-purple-light {
    background: #e5e9f2;
  } */
  .bg-purple-light {
    background: #e5e9f2;
    opacity: 0;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .testbox {
    border: 2px solid transparent;
    padding: 15px;
    background-color: #f9fafc;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    // border-color: black;
    height: 500px;
    width: 500px;
    // background-color: aqua;
  }
</style>