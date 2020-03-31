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
      <el-form-item label="状态:">
        <el-select placeholder="岗位状态" v-model="queryParams.ajaxLeaderStatus" size="small" >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.id"
            :value="dict.status"
          />
        </el-select>
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
    <el-row :gutter="10">
      <el-col :span="5">
        <el-button
          type="primary"
          size="small"
          @click="showDialog()"
        >分配</el-button>
      </el-col>
    </el-row>
    <el-table :data="tableList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="selectable" />
      <el-table-column label="订单编号" align="center" prop="orderCode" />
      <el-table-column label="检测项目" align="center" prop="goodsName" />
      <el-table-column label="分配时间" align="center" prop="" />
      <el-table-column label="应出报告时间" align="center" prop="" />
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="医院" align="center" prop="hospitalName" />
      <el-table-column label="检查报告" align="center" prop="uploadFilePOList" >
      </el-table-column>
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
                @click="showUploadDialog(scope.row)"
              >上传报告</el-button>
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

    <!-- 上传报告dialog -->
    <el-dialog 
      width="500px" 
      :modal="true" 
      :before-close="handleUploadDialogClose"
      custom-class="dialog-custom" 
      :visible.sync="dialogUploadVisible">
      <el-upload
        class="upload-demo"
        action="http://47.100.221.10:8089/upload/file/addUploadFile"
        :on-success="successCallback"
        :before-remove="beforeRemove"
        multiple
        :limit="5"
        :on-exceed="handleExceed"
        :file-list="fileList">
        <el-button size="small" type="primary" >点击上传</el-button>
        <div slot="tip" class="el-upload__tip">上传的文件大小不超过30M</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">确 定</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 分配对话框 -->
    <el-dialog 
      width="500px" 
      :modal="true" 
      custom-class="dialog-custom" 
      :visible.sync="dialogVisible">
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

  </div>
</template>

<script>
export default {
  name: 'upload',
  data () {
    return {
      dealingPeople:this.$store.getters.user,
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
      // 选中的行
      ids:[],
      // 报告数据
      reportList: [],
      tableList:[],
      // 分页参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ajaxLeaderStatus: "全部",
        ajaxOrderSort: "分配时间"
      },
      //查询参数
      searchQuery:{
        orderCode: undefined,
        patientName: undefined,
        goodsName: undefined,
        leaderStatus:"0",
        orderSort:"0",
        identification:"YSC",
      },
      // upload-dialog开关
      dialogUploadVisible : false,
      fileList: [],//上传文件数组
      nowUploadId:null,//当前选中的订单编号
      nowRowState:null,//当前选中行的leaderState
      fileIdArr: [],//上传成功后的文件ID

      //分配审核人
      dialogVisible: false,
      defaultDeal:"",
      dealingPeople:[],
      roleForm:{
        userId:'',
        userName:''
      },//所选分配人物信息
      rebackRole:{
        userId:'',
        userName:''
      },//退回报告时的信息
      submitIds:[],//分配的ids
    };
  },
  components: {},
  created (){
    this.getList();
  },
  mounted () {
    this.getDealingList();
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize = val;
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    getList(){
      this.$axios.get('/api/experiment/order/list',{params:{identification:"YSC"}})
        .then( res => {
          this.reportList = res.data.rows;
          this.total = res.data.total;
          console.log(this.reportList)
          this.handelData(this.reportList);
        })
        .catch(e => {
          console.log(e)
        })
    },
    //处理数据
    handelData(obj){
      var currentArr=[];
      for(var val of obj){
        for(var item of val.uploadFilePOList){
          currentArr.push( item.originFileName );
        }
        val.uploadFilePOList = currentArr;
        currentArr = [];
        switch (val.leaderStatus) {
            case "1":
              val.leaderStatus = "已分配";
              break;
            case "2":
              val.leaderStatus = "已上传";
              break;
        }
      }
      if(obj.length == 0){
        this.tableList = [];
      }else{
        this.tableList = obj.slice((this.queryParams.pageNum-1)*this.queryParams.pageSize,this.queryParams.pageNum*this.queryParams.pageSize)
      }
    },
    //查询
    handleQuery(){
      if(this.queryParams.ajaxLeaderStatus == "已分配"){
        this.searchQuery.leaderStatus = "1";
      }else if(this.queryParams.ajaxLeaderStatus == "已上传"){
        this.searchQuery.leaderStatus = "2";
      }else{
        this.searchQuery.leaderStatus = "";
      }
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
          this.rebackRole = this.dealingPeople.find((item) => {
            return item.userId == userName
          });
        })
        .catch(e => {
          console.log(e);
        })
    },
    //处理状态不为已分配的数据
    selectable(row,index){
      if(row.leaderStatus==="已上传"){
           return true
       }else {
           return false
       }
    },
    //选择处理人
    change(val){
        this.roleForm = this.dealingPeople.find((item) => {
          return item.userId == val
        });
    },
    // 拉起分配dialog方法
    showDialog(){
      this.submitIds = this.ids;
      console.log(this.submitIds);
      if(this.ids == ""){
        this.chooseWarning = true;
        setTimeout(function(){
          this.chooseWarning = false
        },3000)
      }else{
        this.dialogVisible = true;
      }
    },
    submitForm(){
      console.log(this.submitIds);
      this.$axios.get('/api/experiment/order/edit',
      { params:{
        experimentOrderIds:this.submitIds.toString(),
        leaderStatus: 3,
        userId: this.roleForm.userId,
        userName: this.roleForm.userName
      }}).then(res => {
        if(res.data.code == 200){
          this.submitIds = [];
          this.dialogVisible = false;
          this.getList();
        }
      }).catch(e => {
        console.log(e);
      })
    },
    cancel(){
      this.dialogVisible = false;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.experimentOrderId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //上传dialog
    showUploadDialog(row){
      this.nowUploadId = row.experimentOrderId;
      console.log(this.nowUploadId);
      this.nowRowState = row.leaderStatus;
      this.dialogUploadVisible = true;
    },
    handleUploadDialogClose(){
      this.dialogUploadVisible = false;
      this.fileList = [];
    },
    // 上传文件方法
    successCallback(response,file,fileList){
      this.fileIdArr.push(response.data.fileId);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 5 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    submitUpload(){
      let data = new FormData();
      console.log(this.nowUploadId)
      data.append('experimentOrderId',this.nowUploadId);
      data.append('fileId',this.fileIdArr);
      if( this.fileIdArr.length > 0 ){
        this.$axios.post('/api/upload/file/save',data
        ).then(res => {
          this.handleUploadDialogClose();
          this.fileIdArr = [];
          if(this.nowRowState == "已分配"){
            this.updateState(this.nowUploadId,2);
          }else{
            this.getList();
          }
        }).catch(e => {
          console.log(e)
        })
      }else{
        this.handleUploadDialogClose();
      }
    },
    //退回报告
    rebackReport(row){
      this.nowUploadId = row.experimentOrderId;
      if(row.leaderStatus == "已上传"){
        this.nowRowState = 2;
      }else{
        this.nowRowState = 1;
      }
      this.$confirm('您将退回报告至待分配状态, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.get('/api/experiment/order/edit',
          { params:{
            experimentOrderIds:this.nowUploadId.toString(),
            leaderStatus: this.nowRowState,
            userName: "11",//不为空就行
            userId: 11,//不为空就行
            operationType: 1
          }}).then(res => {
            if(res.data.code == 200){
              this.getList();
            }
            this.$message({
              type: 'success',
              message: '退回成功！'
            });
          }).catch(e => {
            console.log(e);
          })
          
        }).catch(() => {
            this.$message({
            type: 'info',
            message: '退回失败！'
          }); 
        })
      
    },
    //取消上传
    cancelUpload(){
      this.handleUploadDialogClose();
    },
    //上传文件后修改订单状态
    updateState(id,state){
        this.$axios.get('/api/experiment/order/edit',
          { params: {
            experimentOrderIds:id.toString(),
            leaderStatus: state
            }
          }
        ).then(res => {
          this.getList();
        }).catch(e => {

        })
    }
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
  .el-dialog{
    .dialog-footer{
      display: flex;
      justify-content: space-evenly;
    }
  }
}
</style>