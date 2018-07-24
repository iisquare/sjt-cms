<template>
  <section>
    <!--顶部工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-select v-model="filters.type" placeholder="全部类型">
            <el-option v-for="(value, key) in config.types" :key="key" :label="key" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="search">查询</el-button>
        </el-form-item>
        <el-form-item v-permit="'manage:upload:add'">
          <el-upload :action="upload.action" with-credentials :show-file-list="false" :data="filters"
            :on-progress="uploadProgress" :on-success="uploadSuccess" :on-error="uploadError">
            <el-button type="success" :loading="upload.loading">上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table :data="rows" highlight-current-row v-loading="loading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" sortable=""></el-table-column>
      <el-table-column prop="name" label="名称" sortable></el-table-column>
      <el-table-column prop="type" label="类型" sortable></el-table-column>
      <el-table-column prop="contentType" label="文件类型" sortable></el-table-column>
      <el-table-column label="路径" sortable>
        <template slot-scope="scope">
          <a target="_blank" :title="scope.row.path" :href="scope.row.url">{{scope.row.path}}</a>
        </template>
      </el-table-column>
      <el-table-column prop="updatedUidName" label="操作者" sortable></el-table-column>
      <el-table-column prop="updatedTime" label="操作时间" width="150" :formatter="date" sortable></el-table-column>
      <el-table-column label="操作" width="126">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-permit="'manage:upload:'" @click="show(scope.$id, scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--底部工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" v-permit="'manage:upload:delete'" @click="batchRemove" :disabled="this.sels.length===0">删除所选</el-button>
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :current-page="filters.page" :page-size="filters.pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--展示界面-->
    <el-dialog title="查看" :visible.sync="infoVisible" :close-on-click-modal="false">
      <el-form :model="form" label-width="80px" :loading="infoLoading">
        <el-form-item label="名称">{{form.name}}</el-form-item>
        <el-form-item label="类型">{{form.type}}</el-form-item>
        <el-form-item label="文件类型">{{form.contentType}}</el-form-item>
        <el-form-item label="路径">{{form.path}}</el-form-item>
        <el-form-item label="预览">{{form.url}}</el-form-item>
        <el-form-item label="创建者">{{form.createdUidName}}</el-form-item>
        <el-form-item label="创建时间">{{form.createdTime|date}}</el-form-item>
        <el-form-item label="修改者">{{form.updatedUidName}}</el-form-item>
        <el-form-item label="修改时间">{{form.updatedTime|date}}</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="infoVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script>
import wrapper from '@/core/RequestWrapper'
import uploadService from '@/service/upload'
import DateUtil from '@/utils/date'
import ElementUI from 'element-ui'
export default {
  data () {
    return {
      upload: {
        action: process.env.apiURL + '/upload/save',
        loading: false
      },
      filters: {
        page: 1,
        pageSize: 15,
        name: '',
        type: ''
      },
      rows: [],
      total: 0,
      loading: false,
      sels: [], // 列表选中列
      infoVisible: false,
      infoLoading: false,
      formVisible: false,
      formLoading: false,
      config: {
        ready: false,
        types: []
      },
      form: {}
    }
  },
  computed: {
    icons () {
      return this.$store.state.icon.data
    }
  },
  methods: {
    uploadProgress (event, file, fileList) {
      this.upload.loading = true
    },
    uploadSuccess (response, file, fileList) {
      this.upload.loading = false
      if (response && response.code === 0) {
        ElementUI.Notification.success({
          title: '状态：' + response.code,
          message: '消息:' + response.message
        })
        this.search()
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
    date (row, column, cellValue, index) {
      return DateUtil.format(cellValue)
    },
    search () {
      this.loading = true
      wrapper.tips(uploadService.list(this.filters)).then((response) => {
        this.total = response.data.total
        this.rows = response.data.rows
        this.loading = false
      })
    },
    pageChange (page) {
      this.filters.page = page
      this.search()
    },
    selsChange: function (sels) {
      this.sels = sels
    },
    batchRemove: function () {
      let ids = this.sels.map(item => item.id)
      this.$confirm('确认删除选中记录吗？', '提示', {type: 'warning'}).then(() => {
        this.loading = true
        wrapper.tips(uploadService.delete(ids), true).then((response) => {
          if (response.code === 0) {
            this.search()
          } else {
            this.loading = false
          }
        })
      }).catch(() => {})
    },
    submit () {
      this.$refs.form.validate((valid) => {
        if (!valid || this.formLoading) return false
        this.formLoading = true
        wrapper.tips(uploadService.save(this.form)).then(response => {
          if (response.code === 0) {
            this.formVisible = false
            this.search()
          }
          this.formLoading = false
        })
      })
    },
    add () {
    },
    edit (id, row) {
      this.form = Object.assign({}, row, {status: row.status + ''})
      this.formVisible = true
    },
    show (id, row) {
      this.form = Object.assign({}, row, {description: row.description ? row.description : '暂无'})
      this.infoVisible = true
    }
  },
  mounted () {
    this.search()
    if (!this.config.ready) {
      this.config.ready = true
      wrapper.tips(uploadService.config()).then((response) => {
        if (response.code === 0) {
          Object.assign(this.config, response.data)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
