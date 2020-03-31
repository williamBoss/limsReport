<template>
  <div id="reportSearchContainer">
    <el-form ref="form">
      <el-form-item label="订单编号:">
        <el-input
          placeholder="请输入订单编号"
          clearable
          size="small"
          v-model="searchQuery.orderCode"
        />
      </el-form-item>
      <el-form-item label="检测项目:">
        <el-input
          placeholder="请输入检测项目"
          clearable
          size="small"
          v-model="searchQuery.goodsName"
        />
      </el-form-item>
      <el-form-item label="患者姓名:">
        <el-input
          placeholder="请输入患者姓名"
          clearable
          size="small"
          v-model="searchQuery.patientName"
        />
      </el-form-item>
      <el-form-item label="状态:">
        <el-select
          placeholder="岗位状态"
          v-model="queryParams.ajaxLeaderStatus"
          size="small"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.id"
            :value="dict.status"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序规则:">
        <el-select
          placeholder="请选择排序规则"
          v-model="queryParams.ajaxOrderSort"
          size="small"
        >
          <el-option
            v-for="item in sortRule"
            :key="item.id"
            :value="item.rule"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >查询</el-button
        >
      </el-form-item>
    </el-form>
    <el-row :gutter="10">
      <el-col :span="5">
        <el-button type="primary" size="small" @click="showDialog()"
          >分配</el-button
        >
      </el-col>
    </el-row>
    <el-table :data="tableList" @selection-change="handleSelectionChange">
      <!--  -->
      <el-table-column
        type="selection"
        width="55"
        align="center"
        prop="leaderStatus"
        :selectable="selectable"
      >
      </el-table-column>
      <el-table-column label="订单编号" align="center" prop="orderCode" />
      <el-table-column label="检测项目" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" placement="top-start">
            <div slot="content">{{ scope.row.goodsName }}</div>
            <el-button type="text" size="small" @click="showTestInfo()">{{
              scope.row.goodsName
            }}</el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="实验完成时间" align="center" prop="updateTime" />
      <el-table-column label="应出报告时间" align="center" prop="" />
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="医院" align="center" prop="hospitalName" />
      <el-table-column label="病理评估报告" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            @click="downloadPathologyReport(scope.row)"
            >{{ scope.row.uploadFilePOList }}</el-button
          >
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="userName" />
      <el-table-column label="状态" align="center" prop="leaderStatus" />
    </el-table>
    <pagination
      :v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 分配对话框 -->
    <el-dialog
      width="500px"
      :modal="true"
      custom-class="dialog-custom"
      :visible.sync="dialogVisible"
    >
      <el-form label-width="80px" ref="form" :model="roleForm">
        <el-form-item label="角色名称">
          <el-select size="small" v-model="defaultDeal" @change="change">
            <el-option
              v-for="item in dealingPeople"
              :key="item.userId"
              :value="item.userId"
              :label="item.userName"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 检测项目详情 -->
    <el-dialog
      class="testInfo"
      width="600px"
      :modal="true"
      custom-class="dialog-custom"
      :visible.sync="tsetInfoVisible"
    >
      <div>
        <test-item-info :clickTestInfo="clickTestInfo" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import TestItemInfo from "../views/TestItemInfo";
export default {
  name: "ReportSearch",
  props: {},
  data() {
    return {
      defaultstatu: "全部",
      statusOptions: [
        { status: "全部" },
        { id: "0", status: "待分配" },
        { id: "1", status: "已分配" },
        { id: "2", status: "已上传" },
        { id: "3", status: "待审核" },
        { id: "4", status: "已发送" }
      ],
      defaultSortRule: "实验完成时间",
      sortRule: [
        { id: "1", rule: "实验完成时间" },
        { id: "2", rule: "应出报告时间" }
      ],
      // 总条数
      total: 0,
      // 报告数据
      reportList: [],
      tableList: [], //reportList.slice((queryParams.pageNum-1)*queryParams.pageSize,queryParams.pageNum*queryParams.pageSize),
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        ajaxLeaderStatus: "全部",
        ajaxOrderSort: "实验完成时间"
      },
      //查询参数
      searchQuery: {
        orderCode: undefined,
        patientName: undefined,
        goodsName: undefined,
        leaderStatus: "0",
        orderSort: "0",
        identification: "DFP"
      },
      // 选中的条数
      ids: [],
      // dialog部分
      chooseWarning: false,
      dialogVisible: false,
      defaultDeal: "",
      dealingPeople: [],

      roleForm: {
        userId: "",
        userName: ""
      },
      submitIds: [],
      // 检测项目
      tsetInfoVisible: false,
      //传入子组件的选中的订单编号
      clickTestInfo: ""
    };
  },
  components: {
    TestItemInfo
  },
  created() {
    this.getList();
  },
  mounted() {
    //获取处理人列表
    this.getDealingList();
  },
  methods: {
    // 获取报告列表
    getList() {
      this.$axios
        .get("/api/experiment/order/list", {
          params: { identification: "DFP" }
        })
        .then(res => {
          this.reportList = res.data.rows;
          this.total = this.reportList.length;
          //处理状态显示
          this.handelData(this.reportList);
        })
        .catch(e => {
          console.log(e);
        });
    },
    //处理数据
    handelData(obj) {
      for (var val of obj) {
        if (val.nodeKey == "BL") {
          val.uploadFilePOList = "下载";
        } else {
          val.uploadFilePOList = "";
        }
        switch (val.leaderStatus) {
          case "0":
            val.leaderStatus = "待分配";
            break;
          case "1":
            val.leaderStatus = "已分配";
            break;
          case "2":
            val.leaderStatus = "已上传";
            break;
          case "3":
            val.leaderStatus = "待审核";
            break;
          case "4":
            val.leaderStatus = "已发送";
            break;
        }
      }
      if (obj.length == 0) {
        this.tableList = [];
      } else {
        this.tableList = obj.slice(
          (this.queryParams.pageNum - 1) * this.queryParams.pageSize,
          this.queryParams.pageNum * this.queryParams.pageSize
        );
      }
    },
    //查询
    handleQuery() {
      if (this.queryParams.ajaxLeaderStatus == "待分配") {
        this.searchQuery.leaderStatus = "0";
      } else if (this.queryParams.ajaxLeaderStatus == "已分配") {
        this.searchQuery.leaderStatus = "1";
      } else if (this.queryParams.ajaxLeaderStatus == "已上传") {
        this.searchQuery.leaderStatus = "2";
      } else if (this.queryParams.ajaxLeaderStatus == "待审核") {
        this.searchQuery.leaderStatus = "3";
      } else if (this.queryParams.ajaxLeaderStatus == "已发送") {
        this.searchQuery.leaderStatus = "4";
      } else {
        this.searchQuery.leaderStatus = "";
      }
      if (this.queryParams.ajaxOrderSort == "应出报告时间") {
        this.searchQuery.orderSort = "1";
      } else {
        this.searchQuery.orderSort = "0";
      }

      this.$axios
        .get("/api/experiment/order/list", { params: this.searchQuery })
        .then(res => {
          this.reportList = res.data.rows;
          this.total = this.reportList.length;
          //处理状态显示
          this.handelData(this.reportList);
        })
        .catch(e => {});
    },
    //处理状态不为待分配的数据
    selectable(row, index) {
      if (row.leaderStatus === "待分配") {
        return true;
      } else {
        return false;
      }
    },
    // 获取处理人列表
    getDealingList() {
      this.$axios
        .get("/api/experiment/user/queryUserList")
        .then(res => {
          this.dealingPeople = res.data.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.experimentOrderId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // dialog方法
    showDialog() {
      if (this.ids.length > 0) {
        this.submitIds = this.ids;
        this.dialogVisible = true;
      } else {
        this.$message.error("请选择订单");
      }
    },
    // // 表单重置
    // resetForm(refName) {
    //   if (this.$refs[refName]) {
    //     this.$refs[refName].resetFields();
    //   }
    // },
    // // 表单重置
    // reset() {
    //   this.resetForm(form);
    // },
    //选择处理人
    change(val) {
      this.roleForm = this.dealingPeople.find(item => {
        return item.userId == val;
      });
    },
    // 确认按钮
    submitForm() {
      this.$axios
        .get("/api/experiment/order/edit", {
          params: {
            experimentOrderIds: this.submitIds.toString(),
            leaderStatus: 1,
            userId: this.roleForm.userId,
            userName: this.roleForm.userName
          }
        })
        .then(res => {
          if (res.data.code == 200) {
            this.dialogVisible = false;
            this.getList();
          }
        })
        .catch(e => {
          console.log(e);
        });
    },
    // 取消按钮
    cancel() {
      this.dialogVisible = false;
      this.reset();
    },

    // 展示检测项目信息
    showTestInfo(row) {
      if (column.id == "el-table_1_column_3") {
        this.clickTestInfo = row.experimentOrderId;
        this.tsetInfoVisible = true;
      }
    },
    // 下载病理报告
    downloadPathologyReport(row) {
      let data = new FormData();
      data.append("experimentOrderId", row.experimentOrderId);
      this.$axios
        .post("/api/reportPDF/downloadReport", data, { responseType: "blob" })
        .then(response => {
          let data = response.data;
          if (!data) {
            return;
          }
          let contentDisposition = response.headers["content-disposition"];
          let fileName = decodeURI(
            contentDisposition.substring(contentDisposition.indexOf("=") + 1)
          );
          let url = window.URL.createObjectURL(new Blob([data]));
          let a = document.createElement("a");
          a.style.display = "none";
          a.href = url;
          a.setAttribute("download", fileName);
          document.body.appendChild(a);
          //点击下载
          a.click();
          // 下载完成移除元素
          document.body.removeChild(a);
          // 释放掉blob对象
          window.URL.revokeObjectURL(url);
          console.log("下载完成");
        })
        .catch(function(error) {
          console.error(error);
        });
    }
  }
};
</script>

<style lang="scss" scoped>
#reportSearchContainer {
  margin: 20px 20px 0;
  .el-form {
    display: flex;
    .el-form-item {
      display: flex;
      white-space: nowrap;
      margin: 0 10px;
    }
  }
  .el-dialog {
    .dialog-footer {
      display: flex;
      justify-content: space-evenly;
    }
  }
  .testInfo {
    .el-dialog__header {
      padding: 0;
    }
  }
}
</style>
