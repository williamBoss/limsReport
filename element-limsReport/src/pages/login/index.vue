<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">报告发送系统</h3>
      <el-form-item>
        工号：
        <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号">
          <!-- <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" /> -->
        </el-input>
      </el-form-item>
      <el-form-item>
        密码：
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          
        >
          <!-- <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" /> -->
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="loginHandle"
        >
          <span v-if="!loading">登 录</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2020 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
      },
      loading: false,
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
      },
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    console.log('this is login page')
  },
  methods: {
    loginHandle() {
      
      this.$refs['loginForm'].validate(valid => {
        if(valid){
          let data = new FormData();
          data.append('userName',this.loginForm.username);
          data.append('password',this.loginForm.password);
          this.$axios.post('/api/experiment/user/login', data
          ).then(res => {
            // console.log(res.data)
            if(res == undefined){
              this.$message.error('输入的账号密码有误，请重新输入');
            }else if(res.data.code == 200){
              //localStorage.setItem('user',this.loginForm.username);
              this.$store.commit('$_setStorage', this.loginForm.username)
              let redirect = decodeURIComponent(this.$route.query.redirect || '/');
              this.$router.push({ path: redirect });
            }else if(res.data.code == 500){
              this.$message.error('输入的账号密码有误，请重新输入');
            }
          })
        }
      })
    }
  }
};
</script>

<style lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../../assets/image/login-background.jpg");
  background-size: cover;
  position: page;
    top:0px;
    left:0px;
    right:0px;
    bottom:0px;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  letter-spacing: 3px;
  font-size: 20px;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    width: 88%;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
