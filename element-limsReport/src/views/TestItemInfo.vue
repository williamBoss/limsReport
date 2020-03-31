<template>
  <div id="testItenInfoContainer">
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="检测项目" name="testItem" v-model="testInfo">
        <div class="line">
          <span>订单编号:</span>
          <span>{{testInfo.orderCode}}</span>
        </div>
        <div class="line">
          <span>产品名称:</span>
          <span>{{testInfo.goodsName}}</span>
        </div>
        <div class="line">
          <span>销售姓名:</span>
          <span>{{testInfo.salerName}}</span>
        </div>
        <div class="line">
          <span>销售邮箱:</span>
          <span>{{testInfo.salerEmail}}</span>
        </div>
        <div class="line">
          <span>销售电话:</span>
          <span>{{testInfo.salerPhone}}</span>
        </div>
        <div class="line">
          <span>销售经理邮箱:</span>
          <span>{{testInfo.managerEmail}}</span>
        </div>
        <div class="line">
          <span>报告接收人姓名:</span>
          <span>{{testInfo.reportreceptionName}}</span>
        </div>
        <div class="line">
          <span>报告接收人邮箱:</span>
          <span>{{testInfo.reportreceptionEmail}}</span>
        </div>
        <div class="line">
          <span>报告接收人电话:</span>
          <span>{{testInfo.reportreceptionPhone}}</span>
        </div>
        <div class="line">
          <span>报告接收地址:</span>
          <span>{{testInfo.reportreceptionAddress}}</span>
        </div>
        <div class="line">
          <span>医院名称:</span>
          <span>{{testInfo.hospitalName}}</span>
        </div>
        <div class="line">
          <span>主治医生:</span>
          <span>{{testInfo.doctorName}}</span>
        </div>
        <div class="line">
          <span>科室:</span>
          <span>{{testInfo.department}}</span>
        </div>
        <div class="line">
          <span>取样来源:</span>
          <span>{{testInfo.histologicOrigin}}</span>
        </div>
        <div class="line">
          <span>取样部位:</span>
          <span>{{testInfo.histologicPosition}}</span>
        </div>
        <div class="line">
          <span>患者姓名:</span>
          <span>{{testInfo.patientName}}</span>
        </div>
        <div class="line">
          <span>性别:</span>
          <span>{{testInfo.patientGender}}</span>
        </div>
        <div class="line">
          <span>年龄:</span>
          <span>{{testInfo.patientAge}}</span>
        </div>
        <div class="line">
          <span>病理信息:</span>
          <span>{{testInfo.clinicalInfo}}</span>
        </div>
      </el-tab-pane>
      <el-tab-pane label="实验结果" name="result">
        <div>
          
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: 'TestItemInfo',
  props:[
    'clickTestInfo'
  ],
  data () {
    return {
      activeName: 'testItem',
      // nowTestInfoId: this.clickTestInfo,
      testInfo:{}, //订单信息
      resultList:null,
    };
  },
  components: {},
  created () {
    this.getTestInfo();
    this.getResult();
  },
  mounted () {
  },
  watch:{
    clickTestInfo(newVal,oldVal){
      this.getTestInfo()
      this.getResult()
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    getTestInfo(){
      console.log("zi"+this.clickTestInfo);
      this.$axios.get('/api/experiment/order/getInfo',{params:{'experimentOrderId':this.clickTestInfo}})
        .then(res => {
          this.testInfo = res.data.data
        })
        .catch(e => {
          console.log(e);
        })
    },
    getResult(){
      this.$axios.get('/api/experiment/result/list',{params:{'experimentOrderId':this.clickTestInfo}})
        .then(res => {
          console.log(res.data)
        })
        .catch(e => {
          console.log(e);
        })
    }
  }
}
</script>

<style lang='scss' scoped>
  #testItenInfoContainer{
    .el-tabs{
      .line{
        width: 100%;
        display: flex;
        justify-content: space-between;
        width: 90%;
        height: 32px;
        padding-left: 30px;
      }
    }
  }
</style>