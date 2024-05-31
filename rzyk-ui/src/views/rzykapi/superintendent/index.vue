<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!--      <el-form-item label="企业微信用户id" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入企业微信用户id" clearable @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <!--      <el-form-item label="父id" prop="parentId">
        <el-input v-model="queryParams.parentId" placeholder="请输入父id" clearable @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <!--     <el-form-item label="角色" prop="position">
        <el-input v-model="queryParams.position" placeholder="请输入角色(0 老师 1系主任 2督导)" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="工号" prop="jobId">
        <el-input v-model="queryParams.jobId" placeholder="请输入工号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!--      <el-form-item label="人事部主键" prop="guikey">
        <el-input v-model="queryParams.guikey" placeholder="请输入人事部主键" clearable @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="部门" prop="codeitemdesc">
        <el-input v-model="queryParams.codeitemdesc" placeholder="请输入部门" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:superintendent:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:superintendent:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:superintendent:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:superintendent:export']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload" size="mini" @click="importData()"
          v-hasPermi="['system:data:export']">导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="superintendentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="企业微信用户id" align="center" prop="userId" />
      <el-table-column label="父id" align="center" prop="parentId" />
      <el-table-column label="角色" align="center" prop="position" />
      <el-table-column label="工号" align="center" prop="jobId" />
      <el-table-column label="人事部主键" align="center" prop="guikey" />
      <el-table-column label="部门" align="center" prop="codeitemdesc" />
      <el-table-column label="数据来源" align="center" prop="type" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:superintendent:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:superintendent:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改监考老师信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="企业微信用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入企业微信用户id" />
        </el-form-item>
        <el-form-item label="父id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父id" />
        </el-form-item>
        <el-form-item label="角色(0 老师 1系主任 2督导)" prop="position">
          <el-input v-model="form.position" placeholder="请输入角色(0 老师 1系主任 2督导)" />
        </el-form-item>
        <el-form-item label="工号" prop="jobId">
          <el-input v-model="form.jobId" placeholder="请输入工号" />
        </el-form-item>
        <el-form-item label="人事部主键" prop="guikey">
          <el-input v-model="form.guikey" placeholder="请输入人事部主键" />
        </el-form-item>
        <el-form-item label="部门" prop="codeitemdesc">
          <!-- <el-input v-model="form.codeitemdesc" placeholder="请输入部门" /> -->
          <el-select v-model="form.codeitemdesc">
            <el-option :label="item.branchName" :value="item.id" v-for="item in schoolDivisionList">
              {{item.branchName}}
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 导入数据 -->
    <el-dialog :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <template slot="title">
        <div style="font-size:18px;color:#aa0000;font-weight: bold;">！ 注:只能导入excel表</div>
      </template>
      <div>
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

<script>
  import {
    getToken
  } from '@/utils/auth';
  import {
    listSuperintendent,
    getSuperintendent,
    delSuperintendent,
    addSuperintendent,
    updateSuperintendent
  } from "@/views/rzykapi/superintendent/superintendent.js";
  import {
    listSchoolDivision,
    getSchoolDivision,
    delSchoolDivision,
    addSchoolDivision,
    updateSchoolDivision
  } from "@/views/rzykapi/schoolDivision/api";
  export default {
    name: "Superintendent",
    data() {
      return {
        //上传文件携带token
        myHeaders: {
          'Authorization': 'Bearer ' + getToken()
        },
        fileList: [],
        //导入数据弹框控制
        dialogVisible: false,
        schoolDivisionList: [],
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
        // 监考老师信息表格数据
        superintendentList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          userId: null,
          parentId: null,
          position: null,
          jobId: null,
          guikey: null,
          codeitemdesc: null,
          type: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.getScholDivision()
    },
    methods: {
      uploadExc(event) {
        var path = process.env.VUE_APP_BASE_API + "/system/info/importdata"
        return path // 返回false不会自动上传
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
      /**
       * 导入数据
       */
      /* 自动生成近10年的学期数据，*/
      importData() {
        this.dialogVisible = true;
      },
      /**
       * 获取学院列表
       */
      getScholDivision() {
        listSchoolDivision().then(res => {
          this.schoolDivisionList = res.rows
          console.log("getScholDivision-->" + this.schoolDivisionList)
        })
      },
      /** 查询监考老师信息列表 */
      getList() {
        this.loading = true;
        listSuperintendent(this.queryParams).then(response => {
          this.superintendentList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          name: null,
          userId: null,
          parentId: null,
          position: null,
          jobId: null,
          guikey: null,
          codeitemdesc: null,
          type: null,
          createBy: null,
          updateBy: null,
          createTime: null,
          updateTime: null
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加监考老师信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getSuperintendent(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改监考老师信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateSuperintendent(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addSuperintendent(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除监考老师信息编号为"' + ids + '"的数据项？').then(function() {
          return delSuperintendent(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/superintendent/export', {
          ...this.queryParams
        }, `superintendent_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>