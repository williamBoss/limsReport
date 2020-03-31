<template>
  <div id="reportSearchContainer">
    <el-form>
      <el-row>
        <el-col :span="5">
          <el-form-item label="订单编号:" >
            <el-input
            placeholder="请输入订单编号"
            clearable
            size="small"
            v-model="searchQuery.orderCode"
            />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="检测项目:" >
            <el-input
            placeholder="请输入检测项目"
            clearable
            size="small"
            v-model="searchQuery.goodsName"
            />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="患者姓名:" >
            <el-input
            placeholder="请输入患者姓名"
            clearable
            size="small"
            v-model="searchQuery.patientName"
            />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="销售:">
            <el-input
            placeholder="请输入销售名称"
            clearable
            size="small"
            v-model="searchQuery.salerName"
            />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item class="search">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="5">
          <el-form-item label="医院:" class="hospital">
            <el-input
            placeholder="请输入医院名称"
            clearable
            size="small"
            v-model="searchQuery.hospitalName"
            />
          </el-form-item>
        </el-col>
        <el-col :span="10" class="time">
          <el-form-item label="报告完成时间:">
            <el-date-picker v-model="exCheckDate" type="daterange" :picker-options="pickerOptions" class="datePickers"
              value-format="yyyy-MM-dd" @change="changeDatePicker(exCheckDate)" @focus="changeDatefocus" :editable="false"
              size='small' :style="{width:width+60+'px'}" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row :gutter="10">
      <el-col :span="5">
        <el-button
          type="primary"
          size="small"
          @click="downLoadReport"
        >下载报告</el-button>
        <el-button
          type="primary"
          size="small"
          @click="exportReport"
        >导出数据</el-button>
      </el-col>
    </el-row>
    <el-table :data="reportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="orderCode" />
      <el-table-column label="报告完成时间" align="center" prop="updateTime" />
      <el-table-column label="应出报告时间" align="center" prop="" />
      <el-table-column label="检测项目" align="center" prop="goodsName" />
      <el-table-column label="检测报告" align="center" prop="uploadFilePOList" />
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="医院" align="center" prop="hospitalName" />
      <el-table-column label="销售" align="center" prop="salerName" />
      <el-table-column label="销售邮箱" align="center" prop="salerEmail" />
      <el-table-column label="营销经理邮箱" align="center" prop="managerEmail" />
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
export default {
  name: 'download',
  props: {
    nowNum:{
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      defaultstatu:"全部",
      statusOptions:[
        {id:'0',status:'全部'},
        {id:'1',status:'待分配'},
        {id:'2',status:'已分配'},
        {id:'3',status:'已上传'},
        {id:'4',status:'待审核'},
        {id:'5',status:'已发送'}
      ],
      defaultSortRule:"实验完成时间",
      sortRule:[
        {id:'1',rule:"实验完成时间"},
        {id:'2',rule:"应出报告时间"}
      ],
      // 总条数
      total: 0,
      ids:[],//选中的列表id
      // 报告数据
      reportList: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20
      },
      //查询参数
      searchQuery:{
        orderCode: undefined,
        patientName: undefined,
        goodsName: undefined,
        salerName:undefined,
        hospitalName:undefined,
        start:undefined,
        end:undefined,
        identification:"YFS",
        leaderStatus:4
      },
      //datePicker
      width: 180,
      exCheckDate: [],
      minDate: '',
      maxDate: '',
      pickerOptions: {
        onPick: ({
          maxDate,
          minDate
        }) => {
          this.maxDate = maxDate;
          this.searchQuery.end = maxDate;
          this.minDate = minDate;
          this.searchQuery.start = minDate;
        },
        disabledDate: (date) => {
          let maxDate = this.maxDate;
          let minDate = this.minDate;
          let endTime = '';
          if (minDate) {
            let val = Date.now() - (new Date(this.minDate).valueOf() + 30 * 24 * 60 * 60 * 1000);
            if (val < 0) {
              endTime = Date.now() - this.nowNum * 24 * 60 * 60 * 1000
              // this.nowNum == 1时不包含当前日期， this.nowNum == 0时包含当前日期
            } else {
              endTime = (new Date(this.minDate).valueOf() + 30 * 24 * 60 * 60 * 1000);
            }
            if (minDate && maxDate) {
              return date && (date.valueOf() > endTime)
            }
            return date && (date.valueOf() > endTime || date.valueOf() < minDate.valueOf() - 30 * 24 * 60 * 60 *
              1000)
          } else {
            return date && (date.valueOf() > Date.now() - this.nowNum * 24 * 60 * 60 * 1000)
          }
        }
      },
      
    };
  },
  components: {},
  created () {
    this.getList();
  },
  mounted () {},
  methods: {
    getList(){
      this.$axios.get('/api/experiment/order/list',
        { params: {'identification': 'YFS','leaderStatus':4}}
      )
        .then( res => {
          this.reportList = res.data.rows;
          console.log(this.reportList);
          this.total = this.reportList.length
          this.handelData(this.reportList);
        })
        .catch(e => {
          console.log(e);
        })
    },
    //处理数据
    handelData(obj){
      //处理状态显示
          var currentArr=[];
          for(var val of obj){
            for(var item of val.uploadFilePOList){
              currentArr.push( item.originFileName+"    " );
              val.uploadFilePOList = currentArr;
            }
            if(val.leaderStatus == 4){
              val.leaderStatus = "已发送";
            }else{
              val.leaderStatus = "";
            }
          }
          if(obj.length == 0){
            this.tableList = [];
          }else{
            this.tableList = obj.slice((this.queryParams.pageNum-1)*this.queryParams.pageSize,this.queryParams.pageNum*this.queryParams.pageSize)
          }
    },
    handleQuery(){
      console.log(this.searchQuery)
      this.$axios.get('/api/experiment/order/list',{params: this.searchQuery})
        .then(res => {
          this.reportList = res.data.rows;
          this.total = this.reportList.length;
          console.log(this.reportList )
          //处理状态显示
          this.handelData(this.reportList)
        })
        .catch(e => {

        })
    },
    //下载报告
    downLoadReport(){
      for( var val of this.ids){
        let data = new FormData();
        data.append("experimentOrderId", val);
        this.$axios
          .post("/api/downloadReport/downloadReportFiles", data, { responseType: "blob" })
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
      
    },
    //导出报告
    exportReport(){
      if(this.ids.length > 0){
        this.$axios.get('/api/experiment/order/export',
          { params: {'experimentOrderIds':this.ids.toString()}}
        ).then(res => {
          console.log(res)
        }).catch(e => {
          
        })
      }else{
        this.$message.error('请选择需要导出的对象');
      }
    },
    changeDatePicker() {
      if (!this.exCheckDate) {
        this.minDate = '';
        this.maxDate = '';
        return
      }
      this.$emit('input', this.exCheckDate);
      this.$emit('change', this.exCheckDate);
    },
    changeDatefocus() {
      this.exCheckDate = []
      this.minDate = '';
      this.maxDate = '';
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.experimentOrderId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
  }
}
</script>

<style lang='scss' scoped>
#reportSearchContainer{
  margin: 20px 20px 0;
  .el-form{
    .el-row{
      display: flex;
      flex-direction: row;
      .el-col{
        .hospital{
          .label{
            width: 4.5rem;
          }
        }
        .search{
          float: right;
        }
      }
    }
    .el-form-item{
      display: flex;
      white-space:nowrap;
      margin: 0 10px;
      
    }
    
  }
}
</style>