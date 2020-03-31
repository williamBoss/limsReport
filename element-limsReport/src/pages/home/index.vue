<template>
  <div id="index">
    <keep-alive>
      <top-menu :active.sync="active"></top-menu>
    </keep-alive>
    <div id="container">
      <router-view></router-view>
    </div>
  </div>
</template>


<script>
import topMenu from '@/topMenu';
import reportSearch from '@/components/Reportsearch';
import upload from '@/components/Upload';
import auditreport from '@/components/Auditreport';
import download from '@/components/Download';
export default {
  name: '',
  data () {
    return {
      active: '/home/reportsearch'
    };
  },
  components: {
    topMenu,
    reportSearch,
    upload,
    auditreport,
    download
  },
  watch:{
    active(newVal){
      console.log(newVal);
      this.$router.push({
        path:`${this.active}`
      }).catch(err => {err})
    }
  },
  created (){
    var key = sessionStorage.getItem('active');
    if(key == null){
      this.active = "/home/reportsearch"
    }else{
      this.active = key;
    }
  },
  mounted () {},
  methods: {
    
  }
}
</script>

<style lang='scss' scoped>
  
</style>