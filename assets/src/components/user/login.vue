<template>
  <el-form :model="form" :rules="rules" ref="form" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="serial">
      <el-input type="text" v-model="form.serial" auto-complete="on" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="form.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox v-model="form.remember" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="submit" :loading="loading">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import wrapper from '@/core/RequestWrapper'
import DataUtil from '@/utils/data'
import userService from '@/service/user'
export default {
  data () {
    return {
      loading: false,
      form: {
        serial: '',
        password: '',
        remember: true
      },
      rules: {
        serial: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submit (ev) {
      this.$refs.form.validate((valid) => {
        if (!valid || this.logining) return false
        this.loading = true
        wrapper.tips(userService.login(this.form)).then(response => {
          if (response.code === 0) {
            this.$store.commit('user/data', response.data)
            let url = this.$router.currentRoute.query.redirect
            if (DataUtil.empty(url)) url = '/'
            this.$router.push(url)
          }
          this.loading = false
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>
