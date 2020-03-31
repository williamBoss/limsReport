<template>
  <div id="reportSearchContainer">
    <el-form>
      <el-form-item label="订单编号:" >
        <el-input
        placeholder="请输入订单编号"
        clearable
        size="small"
        v-model="searchQuery.orderCode"
        />
      </el-form-item>
      <el-form-item label="检测项目:" >
        <el-input
        placeholder="请输入检测项目"
        clearable
        size="small"
        v-model="searchQuery.goodsName"
        />
      </el-form-item>
      <el-form-item label="患者姓名:" >
        <el-input
        placeholder="请输入患者姓名"
        clearable
        size="small"
        v-model="searchQuery.patientName"
        />
      </el-form-item>
      <el-form-item label="排序规则:">
        <el-select placeholder="请选择排序规则" v-model="queryParams.ajaxOrderSort" size="small">
          <el-option
            v-for="item in sortRule"
            :key="item.id"
            :value="item.rule"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>
    
    <el-table :data="reportList" @selection-change="handleSelectionChange">
      <el-table-column label="订单编号" align="center" prop="orderCode" />
      <el-table-column label="检测项目" align="center" prop="goodsName" />
      <el-table-column label="分配时间" align="center" prop="leaderUpdateTime" />
      <el-table-column label="应出报告时间" align="center" prop="" />
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="医院" align="center" prop="hospitalName" />
      <el-table-column label="检查报告" align="center" prop="uploadFilePOList" />
      <el-table-column label="状态" align="center" prop="leaderStatus" />
      <el-table-column
            label="操作"
            align="center"
            width="180"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="auditCross(scope.row)"
              >审核通过</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="rebackReport(scope.row)"
              >退回</el-button>
            </template>
          </el-table-column>
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
  name: 'auditreport',
  props: {

  },
  data () {
    return {
      defaultstatu:"全部",
      statusOptions:[
        {id:'1',status:'全部'},
        {id:'2',status:'已分配'},
        {id:'3',status:'已上传'}
      ],
      defaultSortRule:"分配时间",
      sortRule:[
        {id:'1',rule:"分配时间"},
        {id:'2',rule:"应出报告时间"}
      ],
      // 总条数
      total: 0,
      // 报告数据
      reportList: null,
      tableList: null,
      dealingPeople:[],
      roleForm:{
        userId:'',
        userName:''
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        ajaxOrderSort: "分配时间"
      },
      //查询参数
      searchQuery:{
        orderCode: undefined,
        patientName: undefined,
        goodsName: undefined,
        orderSort:"0",
        identification:"DSH",
        leaderStatus:"3"
      },
    };
  },
  components: {},
  created () {
    this.getList();
  },
  mounted () {
    this.getDealingList()
  },
  methods: {
    getList(){
      this.$axios.get('/api/experiment/order/list',
        { params: {'identification': 'DSH',"leaderStatus":3}}
      )
        .then( res => {
          this.reportList = res.data.rows;
          this.total = this.reportList.length
          this.handelData(this.reportList);
        })
        .catch(e => {
          console.log(e);
        })
    },
    handelData(obj){
      //处理状态显示
      var currentArr=[];
      for(var val of obj){
        for(var item of val.uploadFilePOList){
          currentArr.push( item.originFileName+"    " );
          val.uploadFilePOList = currentArr;
        }
        if( val.leaderStatus == "3" ){
          val.leaderStatus = "待审核";
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
      if(this.queryParams.ajaxOrderSort == "分配时间"){
        this.searchQuery.orderSort = "1"
      }else{
        this.searchQuery.orderSort = "0"
      }
      
      this.$axios.get('/api/experiment/order/list',{params: this.searchQuery})
        .then(res => {
          this.reportList = res.data.rows;
          this.total = this.reportList.length;
          //处理状态显示
          this.handelData(this.reportList)
        })
        .catch(e => {

        })
    },
    // 获取处理人列表
    getDealingList(){
      this.$axios.get('/api/experiment/user/queryUserList')
        .then( res => {
          this.dealingPeople = res.data.data;
          let userName = localStorage.getItem('user')
          this.change(userName);
        })
        .catch(e => {
          console.log(e);
        })
    },
    change(val){
        this.roleForm = this.dealingPeople.find((item) => {
          return item.userName == val
        });
    },
    auditCross (row) {
      this.$confirm('您将通过该报告审核, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.get('/api/experiment/order/edit',
          { params:{
            "experimentOrderIds":row.experimentOrderId.toString(),
            "leaderStatus": 4,
            "userId":this.roleForm.userId,
            "userName":this.roleForm.userName,
            "operationType":0
          }}).then(res => {
            if(res.data.code == 200){
              this.getList();
            }
          }).catch(e => {
            console.log(e);
          })
          this.$message({
            type: 'success',
            message: '审核通过！'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '审核未通过！'
          });   
        })
    },
    rebackReport (row) {
      this.$confirm('您将退回该报告, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.get('/api/experiment/order/edit',
          { params:{
            "experimentOrderIds":row.experimentOrderId.toString(),
            "leaderStatus": 3,
            "userId":this.roleForm.userId,
            "userName":this.roleForm.userName,
            "operationType":1
          }}).then(res => {
            if(res.data.code == 200){
              this.getList();
              this.$message({
                type: 'success',
                message: '退回成功！'
              });
            }
          }).catch(e => {
            console.log(e);
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消退回！'
          });  
        })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
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
    display: flex;
    .el-form-item{
      display: flex;
      white-space:nowrap;
      margin: 0 10px;
      
    }
    
  }
}
</style>