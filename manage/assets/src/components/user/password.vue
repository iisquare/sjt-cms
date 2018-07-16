<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px" style="margin:20px;width:60%;min-width:600px;">
    <el-form-item label="原密码" prop="passwordOld">
      <el-input type="password" v-model="form.passwordOld" auto-complete="off" placeholder="当前登录密码"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="password">
      <el-input type="password" v-model="form.password" auto-complete="off" placeholder="输入新的登录密码"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="passwordNew">
      <el-input type="password" v-model="form.passwordNew" auto-complete="off" placeholder="重复输入新的登录密码"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click.native="submit" :loading="formLoading">提交</el-button>
      <el-button @click.native="$router.go(-1)">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import wrapper from '@/core/RequestWrapper'
import userService from '@/service/user'
export default {
  data () {
    return {
      formLoading: false,
      rules: {
        passwordOld: [{required: true, message: '请输入当前登录密码', trigger: 'blur'}],
        password: [{required: true, message: '请输入新的登录密码', trigger: 'blur'}],
        passwordNew: [{required: true, message: '请确认新的登录密码', trigger: 'blur'}]
      }
    }
  },
  computed: {
    form () {
      return this.$store.state.user.data.info
    }
  },
  methods: {
    submit () {
      this.$refs.form.validate((valid) => {
        if (!valid || this.formLoading) return false
        this.formLoading = true
        wrapper.tips(userService.password(this.form)).then(response => {
          if (response.code === 0) {
            this.$router.go(0)
          }
          this.formLoading = false
        })
      })
    }
  }
}
</script>
