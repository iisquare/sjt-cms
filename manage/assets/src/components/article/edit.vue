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
      <el-input type="text" v-model="form.thumbUrl" auto-complete="on" placeholder="输入或上传封面图片">
        <el-upload slot="append" :action="upload.action" with-credentials :show-file-list="false" :data="{type: 'image'}"
          :on-progress="uploadProgress" :on-success="uploadSuccess" :on-error="uploadError">
          <el-button type="success" :loading="upload.loading" icon="el-icon-upload"></el-button>
        </el-upload>
      </el-input>
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
      <el-input-number v-model="form.sort" :min="0" size="medium" aria-placeholder="默认为发布时间"></el-input-number>
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
      <ueditor :content="form.content" :config="config.ueditor" class="editor" ref="ue"></ueditor>
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
import ElementUI from 'element-ui'
export default {
  components: {
    ueditor: () => import('@/components/layout/ueditor.vue')
  },
  data () {
    return {
      upload: {
        action: process.env.apiURL + '/upload/save',
        loading: false
      },
      formLoading: false,
      config: {
        ueditor: {
          initialFrameWidth: 850,
          initialFrameHeight: 420
        },
        ready: false,
        status: [],
        categories: []
      },
      props: {
        value: 'id',
        label: 'name'
      },
      form: {
        commentEnable: false,
        content: ''
      },
      rules: {
        title: [{required: true, message: '请输入文章标题', trigger: 'blur'}],
        categoryId: [{required: true, message: '请选择栏目名称', trigger: 'blur'}],
        status: [{required: true, message: '请选择文章状态', trigger: 'blur'}]
      }
    }
  },
  methods: {
    uploadProgress (event, file, fileList) {
      this.upload.loading = true
    },
    uploadSuccess (response, file, fileList) {
      this.upload.loading = false
      if (response && response.code === 0) {
        this.form.thumbUrl = response.data.url
      } else {
        ElementUI.Notification.warning({
          title: '状态：' + response.code,
          message: '消息:' + response.message
        })
      }
    },
    uploadError (err, file, fileList) {
      this.upload.loading = false
      ElementUI.Notification.warning({
        title: '上传失败',
        message: err
      })
    },
    submit () {
      this.$refs.form.validate((valid) => {
        if (!valid || this.formLoading) return false
        this.formLoading = true
        let param = Object.assign({}, this.form)
        param.categoryId = param.categoryId[0]
        param.content = this.$refs.ue.getContent()
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
    let id = this.$router.currentRoute.query.id
    if (id) {
      wrapper.tips(articleService.info({id})).then((response) => {
        if (response.code === 0) {
          Object.assign(this.form, response.data, {
            categoryId: [response.data.categoryId],
            commentEnable: response.data.commentEnable === 1
          })
        }
      })
    }
    wrapper.tips(articleService.config()).then((response) => {
      this.config.ready = true
      if (response.code === 0) {
        Object.assign(this.config, response.data)
      }
    })
  }
}
</script>

<style scoped lang="scss">
.editor {
  line-height: normal;
}
</style>
