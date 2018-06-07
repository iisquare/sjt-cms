<template>
  <section>
    <!--顶部工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-button type="primary" @click="searchVisible = true">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="add" v-permit="'manage:user:add'">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table :data="rows" highlight-current-row v-loading="loading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" sortable=""></el-table-column>
      <el-table-column prop="serial" label="账号" sortable></el-table-column>
      <el-table-column prop="name" label="名称" sortable></el-table-column>
      <el-table-column prop="sort" label="排序" sortable></el-table-column>
      <el-table-column prop="statusText" label="状态" sortable></el-table-column>
      <el-table-column prop="roles" label="角色" :formatter="roles" sortable></el-table-column>
      <el-table-column prop="loginedTime" label="登录时间" width="150" :formatter="date" sortable></el-table-column>
      <el-table-column prop="updatedUidName" label="操作者" sortable></el-table-column>
      <el-table-column prop="updatedTime" label="操作时间" width="150" :formatter="date" sortable></el-table-column>
      <el-table-column label="操作" width="130">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-permit="'manage:user:'" @click="show(scope.$id, scope.row)">查看</el-button>
          <el-button v-if="scope.row.status != -1" v-permit="'manage:user:modify'" type="text" size="small" @click="edit(scope.$id, scope.row)">编辑</el-button>
          <el-button v-if="scope.row.status != -1" v-permit="'manage:user:role'" type="text" size="small" @click="editTree('role', scope.row.id)">角色</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--底部工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" v-permit="'manage:user:delete'" :disabled="this.sels.length===0">删除所选</el-button>
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :current-page="filters.page" :page-size="filters.pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
    <!--角色分配-->
    <el-dialog :title="tree.title + '[' + tree.id + ']'" :visible.sync="tree.visible" :close-on-click-modal="false">
      <el-table :data="tree.data" ref="tree" highlight-current-row v-loading="loading" @selection-change="treeChange" style="width: 100%;">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" sortable=""></el-table-column>
        <el-table-column prop="name" label="名称" sortable></el-table-column>
        <el-table-column prop="statusText" label="状态" sortable></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="tree.visible = false">取消</el-button>
        <el-button type="primary" @click.native="saveTree" :loading="tree.loading">提交</el-button>
      </div>
    </el-dialog>
    <!--搜索界面-->
    <el-dialog title="查询参数" :visible.sync="searchVisible" :close-on-click-modal="false">
      <el-form :model="filters" label-width="80px" ref="filters">
        <el-form-item label="账号" prop="serial">
          <el-input v-model="filters.serial" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="filters.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="filters.status" clearable placeholder="请选择">
            <el-option v-for="(value, key) in config.status" :key="key" :label="value" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
          <el-select v-model="filters.roleIds" multiple clearable placeholder="请选择">
            <el-option v-for="item in config.roles" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="注册IP" prop="createdIp">
          <el-input v-model="filters.createdIp" auto-complete="on"></el-input>
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
        <el-form-item label="登录IP" prop="loginedIp">
          <el-input v-model="filters.loginedIp" auto-complete="on"></el-input>
        </el-form-item>
        <el-form-item label="登录时间" prop="loginedTime">
          <el-date-picker v-model="filters.loginedTime" type="datetimerange" @change="dateChange('loginedTime')"
            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="锁定时间" prop="lockedTime">
          <el-date-picker v-model="filters.lockedTime" type="datetimerange" @change="dateChange('lockedTime')"
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
        <el-form-item label="账号" prop="serial">
          <el-input v-model="form.serial" auto-complete="off" :disabled="form.id ? true : false"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" auto-complete="off" placeholder="留空时不对密码做任何处理"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" :max="200"></el-input-number>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option v-for="(value, key) in config.status" :key="key" :label="value" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="锁定">
          <el-date-picker v-model="form.lockedTime" type="datetime" placeholder="选择日期时间"></el-date-picker>
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
        <el-form-item label="账号">{{form.serial}}</el-form-item>
        <el-form-item label="名称">{{form.name}}</el-form-item>
        <el-form-item label="排序">{{form.sort}}</el-form-item>
        <el-form-item label="状态">{{form.statusText}}</el-form-item>
        <el-form-item label="描述">{{form.description}}</el-form-item>
        <el-form-item label="创建者">{{form.createdUidName}}</el-form-item>
        <el-form-item label="创建时间">{{form.createdTime|date}}</el-form-item>
        <el-form-item label="注册IP">{{form.createdIp}}</el-form-item>
        <el-form-item label="修改者">{{form.updatedUidName}}</el-form-item>
        <el-form-item label="修改时间">{{form.updatedTime|date}}</el-form-item>
        <el-form-item label="登录IP">{{form.loginedIp}}</el-form-item>
        <el-form-item label="登录时间">{{form.loginedTime|date}}</el-form-item>
        <el-form-item label="锁定时间">{{form.lockedTime|date}}</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="infoVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script>
import wrapper from '@/core/RequestWrapper'
import userService from '@/service/user'
import DateUtil from '@/utils/date'
export default {
  data () {
    return {
      filters: {
        page: 1,
        pageSize: 15
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
        status: [],
        roles: []
      },
      form: {},
      rules: {
        serial: [{required: true, message: '请输入账号', trigger: 'blur'}],
        name: [{required: true, message: '请输入名称', trigger: 'blur'}],
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
    treeChange: function (sels) {
      this.tree.checked = sels.map(item => item.id)
    },
    editTree (type, id) {
      Object.assign(this.tree, {
        id: id,
        type: type,
        title: {role: '角色'}[type],
        data: [],
        checked: [],
        visible: true,
        loading: true
      })
      wrapper.tips(userService.tree({id: id, type: type})).then((response) => {
        if (response.code === 0) {
          Object.assign(this.tree, {
            data: this.config.roles,
            checked: response.data.checked,
            loading: false
          })
          this.$nextTick(() => {
            let checked = this.tree.checked
            this.tree.data.forEach(item => {
              if (checked.indexOf(item.id) !== -1) {
                this.$refs.tree.toggleRowSelection(item, true)
              }
            })
          })
        }
      })
    },
    saveTree () {
      if (this.tree.loading) return false
      this.tree.loading = true
      wrapper.tips(userService.tree({
        id: this.tree.id, type: this.tree.type, bids: this.tree.checked
      })).then(response => {
        if (response.code === 0) {
          this.tree.visible = false
          this.search()
        }
        this.tree.loading = true
      })
    },
    roles (row, column, cellValue, index) {
      if (!cellValue) return ''
      return cellValue.map(item => item.name).join(',')
    },
    reset (form) {
      this.$refs[form].resetFields()
      switch (form) {
        case 'filters' :
          ['createdTime', 'updatedTime', 'loginedTime', 'lockedTime'].forEach((value) => {
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
      wrapper.tips(userService.list(this.filters)).then((response) => {
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
        wrapper.tips(userService.delete(ids), true).then((response) => {
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
        wrapper.tips(userService.save(param)).then(response => {
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
    wrapper.tips(userService.config()).then((response) => {
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
