<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程编号" prop="coursenumber">
        <el-input v-model="queryParams.coursenumber" placeholder="请输入课程编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="课程名称" prop="coursename">
        <el-input v-model="queryParams.coursename" placeholder="请输入课程名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="授课教师" prop="teacher">
        <el-input v-model="queryParams.teacher" placeholder="请输入授课教师" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="主监考" prop="chiefinv">
        <el-input v-model="queryParams.chiefinv" placeholder="请输入主监考" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="所属学期" prop="semester">
        <el-input v-model="queryParams.semester" placeholder="请输入所属学期" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="打印批次" prop="batch">
        <el-input v-model="queryParams.batch" placeholder="请输入打印批次" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:data:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleAllUpdate"
          v-hasPermi="['system:data:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-printer" size="mini" :disabled="multiple" @click="handlePrint"
          v-hasPermi="['system:data:edit']">打印</el-button>
        <!-- <printfdata v-bind:printfdata="printfdata" :disabled="multiple"></printfdata> -->
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:data:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload" size="mini" @click="getSemester()"
          v-hasPermi="['system:data:export']">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:data:export']">导出</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-sort" size="mini" :disabled="single" @click="handleInvUpdate"
          v-hasPermi="['system:data:edit']">监考老师替换</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-sort" size="mini" :disabled="single"
          @click="handleexaminationroomUpdate" v-hasPermi="['system:data:edit']">考场替换</el-button>
      </el-col>
    </el-row>
    <el-table border v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" fixed="left" />
      <el-table-column sortable label="课程编号" align="center" prop="coursenumber" width="180" />
      <el-table-column sortable label="课程名称" align="center" prop="coursename" width="180" />
      <el-table-column sortable label="授课教师" align="center" prop="teacher" width="180" />
      <el-table-column sortable label="教学班名称" align="center" prop="teachingclass" width="180" />
       <el-table-column label="学生班级" align="center" prop="studentclass" width="180" />
      <el-table-column sortable label="人数" align="center" prop="num" width="180" />
      <el-table-column sortable label="考试地点" align="center" prop="place" width="180" />
      <el-table-column sortable label="考场总人数" align="center" prop="totalnum" width="180" />
      <el-table-column sortable label="考试时间" align="center" prop="examtime" width="180" />
      <el-table-column sortable label="考试日期" align="center" prop="examdate" width="180" />
      <el-table-column sortable label="主考人员" align="center" prop="examiners" width="180" />
      <el-table-column sortable label="主监考" align="center" prop="chiefinv" width="180" />
       <el-table-column label="副监考" align="center" prop="assistantinv" width="180" />
      <!--      <el-table-column label="试卷编号" align="center" prop="papersnum" />
            <el-table-column label="开课校区" align="center" prop="classescampus" />
            <el-table-column label="考试所在校区" align="center" prop="examcampus" />
            <el-table-column label="开课院系" align="center" prop="classessdept" />
            <el-table-column label="上课院系" align="center" prop="toteachsdept" />
            <el-table-column label="所属校区" align="center" prop="campus" />
            <el-table-column label="考场容量" align="center" prop="capacity" />
            <el-table-column label="考试节次" align="center" prop="examsection" />
            <el-table-column label="所属学院" align="center" prop="college" />
            <el-table-column label="所属学期" align="center" prop="semester" />
            <el-table-column label="上传日期" align="center" prop="uploaddate" width="180"> -->
      <!-- <template slot-scope="scope">
                <span>{{ parseTime(scope.row.uploaddate, '{y}-{m}-{d}') }}</span>
            </template>
            </el-table-column>
            <el-table-column label="分组名" align="center" prop="groupname" />
            <el-table-column label="打印页面编号" align="center" prop="printnum" />
            <el-table-column label="打印批次" align="center" prop="batch" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <!-- <el-button type="info" size="mini" icon="el-icon-edit" @click="handleAllUpdate(scope.row)"
                        v-hasPermi="['system:data:edit']">修改</el-button> -->
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:data:remove']">删除</el-button>

        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button slot="reference" @click="getCode(scope.row.id)">生成二维码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />


    <!-- 显示二维码 -->
    <el-dialog title="提示" :visible.sync="qrCodedialogVisible" width="28%">
      <img style="width: 100%;height: 80%;" :src='imgUrl' />
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="qrCodedialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 添加或修改考试安排数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body> <el-form ref="form" :model="form"
        :rules="rules" label-width="80px">
        <el-form-item label="课程编号" prop="coursenumber">
          <el-input v-model="form.coursenumber" placeholder="请输入课程编号" />
        </el-form-item>
        <el-form-item label="课程名称" prop="coursename">
          <el-input v-model="form.coursename" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="授课教师" prop="teacher">
          <el-input v-model="form.teacher" placeholder="请输入授课教师" />
        </el-form-item>
        <!--  <el-form-item label="教学班名称" prop="teachingclass">
          <el-input v-model="form.teachingclass" placeholder="请输入教学班名称" />
        </el-form-item> -->
        <el-form-item label="学生班级" prop="studentclass">
          <el-input v-model="form.studentclass" placeholder="请输入学生班级" />
        </el-form-item>
        <el-form-item label="人数" prop="num">
          <el-input v-model="form.num" placeholder="请输入人数" />
        </el-form-item>
        <el-form-item label="考试地点" prop="place">
          <el-input v-model="form.place" placeholder="请输入考试地点" />
        </el-form-item>
        <el-form-item label="考场总人数" prop="totalnum">
          <el-input v-model="form.totalnum" placeholder="请输入考场总人数" />
        </el-form-item>
        <el-form-item label="考试时间" prop="examtime">
          <el-input v-model="form.examtime" placeholder="请输入考试时间" />
        </el-form-item>
        <el-form-item label="考试日期" prop="examdate">
          <el-input v-model="form.examdate" placeholder="请输入考试日期" />
        </el-form-item>
        <!--  <el-form-item label="主考人员" prop="examiners">
          <el-input v-model="form.examiners" placeholder="请输入主考人员" />
        </el-form-item> -->
        <el-form-item label="主监考" prop="chiefinv">
          <el-input v-model="form.chiefinv" placeholder="请输入主监考" />
        </el-form-item>
        <el-form-item label="副监考" prop="assistantinv">
          <el-input v-model="form.assistantinv" placeholder="请输入副监考" />
        </el-form-item>
        <!--  <el-form-item label="试卷编号" prop="papersnum">
          <el-input v-model="form.papersnum" placeholder="请输入试卷编号" />
        </el-form-item>
          <el-form-item label="开课校区" prop="classescampus">
          <el-input v-model="form.classescampus" placeholder="请输入开课校区" />
        </el-form-item>
        <el-form-item label="考试所在校区" prop="examcampus">
          <el-input v-model="form.examcampus" placeholder="请输入考试所在校区" />
        </el-form-item>
        <el-form-item label="开课院系" prop="classessdept">
          <el-input v-model="form.classessdept" placeholder="请输入开课院系" />
        </el-form-item>
        <el-form-item label="上课院系" prop="toteachsdept">
          <el-input v-model="form.toteachsdept" placeholder="请输入上课院系" />
        </el-form-item>
        <el-form-item label="所属校区" prop="campus">
          <el-input v-model="form.campus" placeholder="请输入所属校区" />
        </el-form-item>
        <el-form-item label="考场容量" prop="capacity">
          <el-input v-model="form.capacity" placeholder="请输入考场容量" />
        </el-form-item>
        <el-form-item label="考试节次" prop="examsection">
          <el-input v-model="form.examsection" placeholder="请输入考试节次" />
        </el-form-item>
        <el-form-item label="所属学院" prop="college">
          <el-input v-model="form.college" placeholder="请输入所属学院" />
        </el-form-item>
        <el-form-item label="所属学期" prop="semester">
          <el-input v-model="form.semester" placeholder="请输入所属学期" />
        </el-form-item>
        <el-form-item label="上传日期" prop="uploaddate">
          <el-date-picker clearable v-model="form.uploaddate" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择上传日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="分组名" prop="groupname">
          <el-input v-model="form.groupname" placeholder="请输入分组名" />
        </el-form-item>
        <el-form-item label="打印页面编号" prop="printnum">
          <el-input v-model="form.printnum" placeholder="请输入打印页面编号" />
        </el-form-item>
        <el-form-item label="打印批次" prop="batch">
          <el-input v-model="form.batch" placeholder="请输入打印批次" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 监考老师替换 -->
    <el-dialog :title="title" :visible.sync="invUpdateOpen" width="500px" append-to-body>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程编号" prop="coursenumber">
          <el-input disabled v-model="form.coursenumber" placeholder="请输入课程编号" />
        </el-form-item>
        <el-form-item label="课程名称" prop="coursename">
          <el-input disabled v-model="form.coursename" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="主监考" prop="chiefinv">
          <!-- <el-input v-model="form.chiefinv" placeholder="请输入主监考" /> -->

          <!-- <div class="sub-title">激活即列出输入建议</div> -->
          <el-autocomplete class="inline-input" v-model="form.chiefinv" :fetch-suggestions="querySearchAllteachers"
            placeholder="请输入内容" @select="handleSelect"></el-autocomplete>

        </el-form-item>




        <el-form-item label="副监考" prop="assistantinv">
          <!-- <el-input v-model="form.assistantinv" placeholder="请输入副监考" /> -->
          <el-autocomplete class="inline-input" v-model="form.assistantinv" :fetch-suggestions="querySearchAllteachers"
            placeholder="请输入内容" @select="handleSelect"></el-autocomplete>

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 考场替换 -->
    <el-dialog :title="title" :visible.sync="examinationroomUpdateOpen" width="500px" append-to-body>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程编号" prop="coursenumber">
          <el-input disabled v-model="form.coursenumber" placeholder="请输入课程编号" />
        </el-form-item>
        <el-form-item label="课程名称" prop="coursename">
          <el-input disabled v-model="form.coursename" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="考试地点" prop="place">
          <!-- <el-input v-model="form.place" placeholder="请输入考试地点" /> -->
          <el-autocomplete class="inline-input" v-model="form.place" :fetch-suggestions="querySearchSyncPlaces"
            placeholder="请输入内容" @select="handleSelectExchangePlace"></el-autocomplete>

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitExaminationroomForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导入数据 -->
    <el-dialog :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <template slot="title">
        <div style="font-size:18px;color:#aa0000;font-weight: bold;">！ 注:只能导入excel表</div>
      </template>
      <el-form>
        <el-form-item label="全部学期" prop="classescampus">
          <el-select v-model="semester_sel" placeholder="请选择">
            <el-option v-for="item in semesters" :key="item" :label="item.label" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考试类型" prop="classescampus">
          <span>{{batch}}</span>
          <el-select v-model="batch" placeholder="请选择打印批次">
            <el-option v-for="dict in dict.type.examtype" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
          </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <hr>

      <div>
        <!-- action=`${process.env.VUE_APP_BASE_API}/system/data/${brach}/importdate/${semester_sel}` -->
        <el-upload class="upload-demo" :headers="myHeaders" :action="uploadExc()" :on-preview=" handlePreview"
          :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed"
          :file-list="fileList">
          <el-button size="small" type="primary">导入</el-button>
          <div slot="tip" class="el-upload__tip">只能导入excel文件</div>
        </el-upload>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>

    </el-dialog>
  </div>
</template>

<script defer=true>
  import {
    getToken
  } from '@/utils/auth';
  import {
    getqrcode
  } from "@/views/rzykapi/schedule/js/data.js";
  /* 当前表数据操作*/
  import axios from 'axios'
  import printfdata from "@/views/rzykapi/schedule/printdata"
  import {
    listData,
    getData,
    delData,
    addData,
    updateData,
    importDate,
    getAllteacher,
    getSyncPlace,
    exchange
  } from "@/views/rzykapi/schedule/js/data.js";
  import {
    needCLodop,
    loadCLodop,
    getLodop
  } from "@/utils/lodop/LodopFuncs.js"
  export default {
    dicts: ['examtype'],
    name: "Data",
    components: {
      printfdata
    },
    data() {
      return {
        syncPlace: [],
        allteacher: [],
        imgUrl: '',
        printfdata: [],
        batch: '',
        myHeaders: {
          'Authorization': 'Bearer ' + getToken()
        },
        exchangePlaceId: '',
        fileList: [],
        semester_sel: '',
        semesters: [],
        qrCodedialogVisible: false,
        dialogVisible: false,
        maxHeight: '',
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 考试安排数据表格数据
        dataList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //是否显示监考老师修改
        invUpdateOpen: false,
        //是否显示考场修改
        examinationroomUpdateOpen: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
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
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}

      };
    },
    /*  mounted() {
          this.initNum();
      }, */
    created() {
      this.getList();
    },
    mounted() {
      this.getAllteachers();

    },
    methods: {

      uploadExc(event) {
        var path = process.env.VUE_APP_BASE_API + "/system/data/importdate/file?batch="+this.batch+"&semester_sel="+this.semester_sel
        return path // 返回false不会自动上传
      },
      async getCode(id) {
        this.qrCodedialogVisible = true
        const {
          msg: result
        } = await getqrcode(id);
        this.imgUrl = process.env.VUE_APP_BASE_API + '/' + result;
      },
      // rofile / image / 7664. png

      handlePrint() {
        this.$router.push({
          name: 'vueqr',
          params: {
            printfdata: this.printfdata
          }
        })

      },
      //预览
      handlePreview(file) {

      },
      handleRemove(file, fileList) {

      },
      //选择提示
      handleExceed(files, fileList) {
        this.$message.warning(
          `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      //移除提示
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      //导入弹框关闭
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      /* 自动生成近10年的学期数据，*/
      getSemester() {
        this.dialogVisible = true;
        var date = new Date();
        var year = date.getFullYear();
        for (var i = 1; i <= 10; i++) {
          this.semesters.push(((year + 1) - i) + "-上半");
          this.semesters.push(((year + 1) - i) + "-下半");
        }
      },
      /** 查询考试安排数据列表 */
      getList() {
        this.loading = true;
        listData(this.queryParams).then(response => {
          this.dataList = response.rows;
          // this.brach = response.rows[0].batch;
          // this.semester_sel = response.rows[0].semester;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.invUpdateOpen = false;
        this.examinationroomUpdateOpen = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
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
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.printfdata = selection
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加考试安排数据";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getData(id).then(response => {
          this.form = response.data;


        });
      },
      /* 全部修改*/
      handleAllUpdate(row) {
        this.open = true;
        this.handleUpdate(row)
        this.title = "修改考试安排数据";
      },
      /* 监考老师修改*/
      handleInvUpdate(row) {
        this.invUpdateOpen = true
        this.handleUpdate(row)
        this.title = "监考老师修改";
      },
      /* 考场修改*/
      handleexaminationroomUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        this.examinationroomUpdateOpen = true
        console.log("handleexaminationroomUpdate" + id)
        this.handleUpdate(row)
        this.getSyncPlaces(id)
        this.title = "考场修改";
        // this.handleUpdate(row)
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateData(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addData(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }

          }
        });
      },
      /** 提交按钮 */
      submitExaminationroomForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            exchange(this.form.id, this.exchangePlaceId).then(res => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            })

          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除考试安排数据编号为"' + ids + '"的数据项？').then(function() {
          return delData(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/data/export', {
          ...this.queryParams
        }, `data_${new Date().getTime()}.xlsx`)
      },
      querySearchAllteachers(queryString, cb) {
        var restaurants = this.allteacher;
        var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      querySearchSyncPlaces(queryString, cb) {
        var restaurants = this.syncPlace;
        var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      getAllteachers() {
        getAllteacher().then(res => {
          this.allteacher = res.rows
        })
      },
      getSyncPlaces(id) {
        console.log("getSyncPlaces===" + id)
        getSyncPlace(id).then(res => {
          this.syncPlace = res.rows
          console.log(this.syncPlaces)
        })
      },
      handleSelectExchangePlace(item) {
        this.exchangePlaceId = item.id || '';
        console.log("handleSelect" + item);
      },
      handleSelect(item) {
        console.log("handleSelect" + item);
      }
    },
  };
</script>
