<template>
  <section>
    <!--顶部工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="add">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table :data="rows" highlight-current-row v-loading="loading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" sortable=""></el-table-column>
      <el-table-column prop="name" label="名称" sortable></el-table-column>
      <el-table-column prop="sort" label="排序" sortable></el-table-column>
      <el-table-column prop="statusText" label="状态" sortable></el-table-column>
      <el-table-column prop="updatedUidName" label="操作者" sortable></el-table-column>
      <el-table-column prop="updatedTime" label="操作时间" width="150" :formatter="date" sortable></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="show(scope.$id, scope.row)">查看</el-button>
          <el-button type="text" size="small" @click="edit(scope.$id, scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--底部工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">删除所选</el-button>
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :current-page="filters.page" :page-size="filters.pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--编辑界面-->
    <el-dialog :title="form.id ? ('修改[' + form.id + ']') : '新增'" :visible.sync="formVisible" :close-on-click-modal="false">
      <el-form :model="form" label-width="80px" :rules="rules" ref="form">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" :max="200"></el-input-number>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option v-for="(value, key) in config.status" :key="key" :label="value" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="formVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submit" :loading="formLoading">提交</el-button>
      </div>
    </el-dialog>
    <!--编辑界面-->
    <el-dialog title="查看" :visible.sync="infoVisible" :close-on-click-modal="false">
      <el-form :model="form" label-width="80px" :loading="infoLoading">
        <el-form-item label="名称">{{form.name}}</el-form-item>
        <el-form-item label="排序">{{form.sort}}</el-form-item>
        <el-form-item label="状态">{{form.statusText}}</el-form-item>
        <el-form-item label="描述">{{form.description}}</el-form-item>
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
import roleService from '@/service/role'
import DateUtil from '@/utils/date'
export default {
  data () {
    return {
      filters: {
        page: 1,
        pageSize: 15,
        name: ''
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
        status: []
      },
      form: {},
      rules: {
        name: [{required: true, message: '请输入名称', trigger: 'blur'}],
        status: [{required: true, message: '请选择状态', trigger: 'change'}]
      }
    }
  },
  methods: {
    date (row, column, cellValue, index) {
      return DateUtil.format(cellValue)
    },
    search () {
      this.loading = true
      wrapper.tips(roleService.list(this.filters)).then((response) => {
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
        wrapper.tips(roleService.delete(ids), true).then((response) => {
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
        wrapper.tips(roleService.save(this.form)).then(response => {
          if (response.code === 0) {
            this.formVisible = false
            this.search()
          }
          this.formLoading = false
        })
      })
    },
    add () {
      this.edit(0, {
        name: '',
        sort: '',
        status: '',
        description: ''
      })
    },
    edit (id, row) {
      this.form = Object.assign({}, row, {status: row.status + ''})
      this.formVisible = true
      if (!this.config.ready) {
        this.config.ready = true
        wrapper.tips(roleService.config()).then((response) => {
          if (response.code === 0) {
            Object.assign(this.config, response.data)
          }
        })
      }
    },
    show (id, row) {
      this.form = Object.assign({}, row, {description: row.description ? row.description : '暂无'})
      this.infoVisible = true
    }
  },
  mounted () {
    this.search()
  }
}
</script>
<style lang="sass" scoped>

</style>
