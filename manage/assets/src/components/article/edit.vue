<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px" style="margin:20px;width:60%;min-width:600px;">
    <el-form-item label="标题" prop="title">
      <el-input type="text" v-model="form.title" auto-complete="on" placeholder="文章标题"></el-input>
    </el-form-item>
    <el-form-item label="栏目" prop="categoryId">
      <el-cascader v-model="form.categoryId" :options="config.categories"
        expand-trigger="hover" :props="props" :change-on-select="true"></el-cascader>
    </el-form-item>
    <el-form-item label="来源名称" prop="fromName">
      <el-input type="text" v-model="form.fromName" auto-complete="on" placeholder="文章来源名称"></el-input>
    </el-form-item>
    <el-form-item label="来源地址" prop="fromUrl">
      <el-input type="text" v-model="form.fromUrl" auto-complete="on" placeholder="文章来源的链接地址"></el-input>
    </el-form-item>
    <el-form-item label="作者" prop="author">
      <el-input type="text" v-model="form.author" auto-complete="on" placeholder="文章作者"></el-input>
    </el-form-item>
    <el-form-item label="封面图" prop="thumbUrl">
      <el-input type="text" v-model="form.thumbUrl" auto-complete="on" placeholder="输入或上传封面图片"></el-input>
    </el-form-item>
    <el-form-item label="链接" prop="url">
      <el-input v-model="form.url" auto-complete="on" placeholder="站内新闻落地页不需要输入内容"></el-input>
    </el-form-item>
    <el-form-item label="打开方式" prop="target">
      <el-input v-model="form.target" auto-complete="on" placeholder="链接打开方式"></el-input>
    </el-form-item>
    <el-form-item label="开启评论" prop="commentEnable">
      <el-checkbox v-model="form.commentEnable">启用评论</el-checkbox>
    </el-form-item>
    <el-form-item label="排序">
      <el-input-number v-model="form.sort" :min="0" aria-placeholder="默认为发布时间"></el-input-number>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="form.status" placeholder="请选择">
        <el-option v-for="(value, key) in config.status" :key="key" :label="value" :value="key"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="发布时间" prop="publishTime">
      <el-date-picker v-model="form.publishTime" type="datetime" placeholder="默认为记录创建时间">
    </el-date-picker>
    </el-form-item>
    <el-form-item label="描述" prop="description">
      <el-input type="textarea" v-model="form.description"></el-input>
    </el-form-item>
    <el-form-item label="正文" prop="content">
      <el-input type="textarea" v-model="form.content"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click.native="submit" :loading="formLoading">提交</el-button>
      <el-button @click.native="$router.go(-1)">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import wrapper from '@/core/RequestWrapper'
import articleService from '@/service/article'
export default {
  data () {
    return {
      formLoading: false,
      config: {
        ready: false,
        status: [],
        categories: []
      },
      props: {
        value: 'id',
        label: 'name'
      },
      form: {},
      rules: {
        title: [{required: true, message: '请输入文章标题', trigger: 'blur'}],
        categoryId: [{required: true, message: '请选择栏目名称', trigger: 'blur'}],
        status: [{required: true, message: '请选择文章状态', trigger: 'blur'}]
      }
    }
  },
  methods: {
    submit () {
      this.$refs.form.validate((valid) => {
        if (!valid || this.formLoading) return false
        this.formLoading = true
        let param = this.form
        param.categoryId = param.categoryId[0]
        wrapper.tips(articleService.save(param)).then(response => {
          if (response.code === 0) {
            this.$router.push('/article/index')
          }
          this.formLoading = false
        })
      })
    }
  },
  mounted () {
    wrapper.tips(articleService.config()).then((response) => {
      this.config.ready = true
      if (response.code === 0) {
        Object.assign(this.config, response.data)
      }
    })
  }
}
</script>
