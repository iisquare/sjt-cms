<template>
  <section>
    <!--顶部工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-button type="primary" @click="searchVisible = true">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="add" v-permit="'manage:comment:add'">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table :data="rows" highlight-current-row v-loading="loading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" sortable=""></el-table-column>
      <el-table-column label="栏目" sortable>
        <template slot-scope="scope">[{{scope.row.categoryId}}]{{scope.row.categoryIdName}}</template>
      </el-table-column>
      <el-table-column label="文章" sortable>
        <template slot-scope="scope">[{{scope.row.articleId}}]{{scope.row.articleIdTitle}}</template>
      </el-table-column>
      <el-table-column prop="content" label="内容" sortable></el-table-column>
      <el-table-column prop="updatedUidName" label="操作者" sortable></el-table-column>
      <el-table-column prop="updatedTime" label="操作时间" width="150" :formatter="date" sortable></el-table-column>
      <el-table-column label="操作" width="130">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-permit="'manage:comment:'" @click="show(scope.$id, scope.row)">查看</el-button>
          <el-button v-if="scope.row.status != -1" v-permit="'manage:comment:modify'" type="text" size="small" @click="edit(scope.$id, scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--底部工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" v-permit="'manage:comment:delete'" :disabled="this.sels.length===0">删除所选</el-button>
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :current-page="filters.page" :page-size="filters.pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--搜索界面-->
    <el-dialog title="查询参数" :visible.sync="searchVisible" :close-on-click-modal="false">
      <el-form :model="filters" label-width="80px" ref="filters">
        <el-form-item label="栏目" prop="categoryId">
          <el-input v-model="filters.categoryId" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="文章" prop="articleId">
          <el-input v-model="filters.articleId" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="父级" prop="parentId">
          <el-input v-model="filters.parentId" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="回复" prop="parentUid">
          <el-input v-model="filters.parentUid" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="顶级" prop="topId">
          <el-input v-model="filters.topId" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="filters.content" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="filters.status" clearable placeholder="请选择">
            <el-option v-for="(value, key) in config.status" :key="key" :label="value" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建IP" prop="createdIp">
          <el-input v-model="filters.createdIp" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker v-model="filters.publishTime" type="datetimerange" @change="dateChange('publishTime')"
            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建时间" prop="createdTime">
          <el-date-picker v-model="filters.createdTime" type="datetimerange" @change="dateChange('createdTime')"
            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="修改时间" prop="updatedTime">
          <el-date-picker v-model="filters.updatedTime" type="datetimerange" @change="dateChange('updatedTime')"
            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="reset('filters')">重置</el-button>
        <el-button type="primary" @click.native="search">搜索</el-button>
      </div>
    </el-dialog>
    <!--编辑界面-->
    <el-dialog :title="form.id ? ('修改[' + form.id + ']') : '新增'" :visible.sync="formVisible" :close-on-click-modal="false">
      <el-form :model="form" label-width="80px" :rules="rules" ref="form">
        <el-form-item label="文章" prop="articleId">
          <el-input v-model="form.articleId" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="父级">
          <el-input v-model="form.parentId" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" size="medium"></el-input-number>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option v-for="(value, key) in config.status" :key="key" :label="value" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker v-model="form.publishTime" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="form.content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="formVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submit" :loading="formLoading">提交</el-button>
      </div>
    </el-dialog>
    <!--展示界面-->
    <el-dialog title="查看" :visible.sync="infoVisible" :close-on-click-modal="false">
      <el-form :model="form" label-width="80px" :loading="infoLoading">
        <el-form-item label="栏目">[{{form.categoryId}}]{{form.categoryIdName}}</el-form-item>
        <el-form-item label="文章">[{{form.articleId}}]{{form.articleIdTitle}}</el-form-item>
        <el-form-item label="父级">{{form.parentId}}</el-form-item>
        <el-form-item label="回复">[{{form.parentUid}}]{{form.parentUidName}}</el-form-item>
        <el-form-item label="顶级">{{form.topId}}</el-form-item>
        <el-form-item label="排序">{{form.sort}}</el-form-item>
        <el-form-item label="状态">{{form.statusText}}</el-form-item>
        <el-form-item label="内容">{{form.content}}</el-form-item>
        <el-form-item label="发布时间">{{form.publishTime|date}}</el-form-item>
        <el-form-item label="创建者">{{form.createdUidName}}</el-form-item>
        <el-form-item label="创建时间">{{form.createdTime|date}}</el-form-item>
        <el-form-item label="创建IP">{{form.createdIp}}</el-form-item>
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
import commentService from '@/service/comment'
import DateUtil from '@/utils/date'
export default {
  data () {
    return {
      filters: {
        page: 1,
        pageSize: 15,
        status: '',
        createdTime: [],
        updatedTime: [],
        publishTime: []
      },
      rows: [],
      total: 0,
      loading: false,
      sels: [], // 列表选中列
      searchVisible: false,
      infoVisible: false,
      infoLoading: false,
      formVisible: false,
      formLoading: false,
      config: {
        ready: false,
        defaultPassword: '',
        status: []
      },
      form: {},
      rules: {
        articleId: [{required: true, message: '请输入文章ID', trigger: 'blur'}],
        status: [{required: true, message: '请选择状态', trigger: 'change'}]
      },
      tree: {
        id: '',
        type: '',
        title: '',
        visible: false,
        loading: false,
        checked: [],
        data: []
      }
    }
  },
  methods: {
    reset (form) {
      this.$refs[form].resetFields()
      switch (form) {
        case 'filters' :
          ['createdTime', 'updatedTime', 'publishTime'].forEach((value) => {
            this.dateChange(value)
          })
          break
      }
    },
    dateChange (field) {
      if (this.filters[field]) {
        this.filters[field + 'Start'] = DateUtil.format(new Date(this.filters[field][0]).getTime())
        this.filters[field + 'End'] = DateUtil.format(new Date(this.filters[field][1]).getTime())
      } else {
        this.filters[field + 'Start'] = this.filters[field + 'End'] = ''
      }
    },
    date (row, column, cellValue, index) {
      return DateUtil.format(cellValue)
    },
    search () {
      this.searchVisible = false
      this.loading = true
      wrapper.tips(commentService.list(this.filters)).then((response) => {
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
        wrapper.tips(commentService.delete(ids), true).then((response) => {
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
        let param = Object.assign({}, this.form, {
          lockedTime: this.form.lockedTime ? DateUtil.format(this.form.lockedTime.getTime()) : ''
        })
        wrapper.tips(commentService.save(param)).then(response => {
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
        serial: '',
        name: '',
        password: this.config.defaultPassword,
        sort: '',
        status: '',
        lockedTime: '',
        description: ''
      })
    },
    edit (id, row) {
      this.form = Object.assign({}, row, {
        status: row.status + '',
        lockedTime: (row.lockedTime && row.lockedTime > 0) ? new Date(row.lockedTime) : ''
      })
      this.formVisible = true
    },
    show (id, row) {
      this.form = Object.assign({}, row, {
        description: row.description ? row.description : '暂无',
        lockedTime: (row.lockedTime && row.lockedTime > 0) ? new Date(row.lockedTime) : ''
      })
      this.infoVisible = true
    }
  },
  mounted () {
    this.search()
    wrapper.tips(commentService.config()).then((response) => {
      this.config.ready = true
      if (response.code === 0) {
        Object.assign(this.config, response.data)
      }
    })
  }
}
</script>
<style lang="sass" scoped>

</style>
