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
          <el-button type="success" v-permit="'manage:role:add'" @click="add">新增</el-button>
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
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-permit="'manage:role:'" @click="show(scope.$id, scope.row)">查看</el-button>
          <el-button type="text" size="small" v-permit="'manage:role:modify'" @click="edit(scope.$id, scope.row)">编辑</el-button>
          <el-button type="text" size="small" v-permit="'manage:role:resource'" @click="editTree('resource', scope.row.id)">资源</el-button>
          <el-button type="text" size="small" v-permit="'manage:role:menu'" @click="editTree('menu', scope.row.id)">菜单</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--底部工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" v-permit="'manage:role:delete'" @click="batchRemove" :disabled="this.sels.length===0">删除所选</el-button>
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :current-page="filters.page" :page-size="filters.pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--权限菜单-->
    <el-dialog :title="tree.title + '[' + tree.id + ']'" :visible.sync="tree.visible" :close-on-click-modal="false">
      <el-input placeholder="输入关键字进行过滤" v-model="treeKeyword"></el-input>
      <el-tree class="filter-tree" :data="tree.data"
        default-expand-all show-checkbox check-strictly ref="tree"
        :props="tree.props" :filter-node-method="filterTree"
        node-key="id" :default-checked-keys="tree.checked">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="tree.visible = false">取消</el-button>
        <el-button type="primary" @click.native="saveTree" :loading="tree.loading">提交</el-button>
      </div>
    </el-dialog>
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
    <!--展示界面-->
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
      },
      treeKeyword: '',
      tree: {
        id: '',
        type: '',
        title: '',
        visible: false,
        loading: false,
        checked: [],
        props: {
          children: 'children',
          label: 'name'
        },
        data: []
      }
    }
  },
  watch: {
    treeKeyword (val) {
      this.$refs.tree.filter(val)
    }
  },
  methods: {
    filterTree (value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    editTree (type, id) {
      Object.assign(this.tree, {
        id: id,
        type: type,
        title: {menu: '菜单', resource: '资源'}[type],
        data: [],
        checked: [],
        visible: true,
        loading: true
      })
      wrapper.tips(roleService.tree({id: id, type: type})).then((response) => {
        if (response.code === 0) {
          Object.assign(this.tree, {
            data: response.data.tree,
            checked: response.data.checked,
            loading: false
          })
        }
      })
    },
    saveTree () {
      if (this.tree.loading) return false
      this.tree.loading = true
      wrapper.tips(roleService.tree({
        id: this.tree.id, type: this.tree.type, bids: this.$refs.tree.getCheckedKeys()
      })).then(response => {
        if (response.code === 0) {
          this.tree.visible = false
        }
        this.tree.loading = true
      })
    },
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
