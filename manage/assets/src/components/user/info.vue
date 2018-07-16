<template>
  <el-form ref="form" :model="form" label-width="80px" style="margin:20px;width:60%;min-width:600px;">
    <el-form-item label="账号">{{form.serial}}</el-form-item>
    <el-form-item label="名称">{{form.name}}</el-form-item>
    <el-form-item label="描述">{{form.description ? form.description : "暂无"}}</el-form-item>
    <el-form-item label="注册IP">{{form.createdIp}}</el-form-item>
    <el-form-item label="注册时间">{{form.createdTime|date}}</el-form-item>
    <el-form-item label="登录IP">{{form.loginedIp}}</el-form-item>
    <el-form-item label="登录时间">{{form.loginedTime|date}}</el-form-item>
    <el-form-item>
      <el-button @click.native="$router.go(-1)">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data () {
    return {}
  },
  computed: {
    form () {
      return this.$store.state.user.data.info
    }
  }
}
</script>
