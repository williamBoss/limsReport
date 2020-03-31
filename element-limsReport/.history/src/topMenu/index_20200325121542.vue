<template>
  <div id="topMenuContainer">
    <el-row>
      <el-col :span="4">
        <div id="logo">SimLIMS</div>
      </el-col>
      <el-col :span="17">
        <el-menu
          :default-active="active"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          active-text-color="#3baf61">
          <el-menu-item index="/home/reportsearch">报告任务查询</el-menu-item>
          <el-menu-item index="/home/upload">报告上传</el-menu-item>
          <el-menu-item index="/home/auditreport">报告审核</el-menu-item>
          <el-menu-item index="/home/download">报告下载</el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="3">
        <div class="user">
          <i class="el-icon-message-solid"></i>
          <el-dropdown @command="handelCommand">
            <!-- <el-button> -->
              <i class="el-icon-user" ></i>
            <!-- </el-button> -->
            <el-dropdown-menu slot="dropdown" class="user-dropdown">
              <el-dropdown-item @click="loginOut()">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
export default {
  name: 'topMenu',
  props:{
    active:{
      type: String,
      required: true
    }
  },
  data () {
    return {
      
    };
  },
  components: {},
  created () {
    console.log(this.$store.getters.user)
  },
  mounted () {},
  methods: {
    handleSelect(key, keyPath) {
      console.log("key="+key);
      sessionStorage.removeItem('active');
      sessionStorage.setItem('active',key);
      // if(key == "/home/reportsearch"){
      //   this.$router.go(0);
      // }
      this.$emit('update:active', key);
    },
    handelCommand(){
      this.loginOut();
    },
    loginOut(){
      this.$axios.post('/api/experiment/user/logout')
        .then(res  => {
          if(res.data.code == 200){
            localStorage.removeItem('user');
            sessionStorage.removeItem('active');
            this.$router.push( {path: "/login"} )
          }
        }).catch(e => {
          console.log(e);
        })
    }
  }
}
</script>

<style lang='scss' scoped>
  #topMenuContainer{
    #logo{
      color: #3baf61;
      text-align: center;
      font-size: 24px;
      width: 100%;
      height: 60px;
      line-height: 60px;
      border-bottom: solid 1px #e6e6e6;
    }
    .el-menu{
      .el-menu-item{
        padding: 0 60px;
      }
      .logo{
        color:"3baf61";
        font-size: 20px;
      }
    }
    .user{
      height: 58px;
      line-height: 58px;
      font-size: 26px;
      .el-dropdown{
        font-size: 26px;
        .i{
          height: 58px;
          line-height: 58px;
        }
      }
    }
  }
</style>