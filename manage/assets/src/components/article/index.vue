<template>
  <section>
    <!--顶部工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input type="number" v-model="filters.categoryId" placeholder="栏目ID"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.fromName" placeholder="来源"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.author" placeholder="作者"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" v-permit="'manage:article:add'" @click.native="$router.push('/article/edit')">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table :data="rows" highlight-current-row v-loading="loading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" sortable=""></el-table-column>
      <el-table-column prop="title" label="标题" sortable></el-table-column>
      <el-table-column label="栏目" sortable>
        <template slot-scope="scope">[{{scope.row.categoryId}}]{{scope.row.categoryIdName}}</template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" sortable></el-table-column>
      <el-table-column prop="statusText" label="状态" sortable></el-table-column>
      <el-table-column prop="updatedUidName" label="操作者" sortable></el-table-column>
      <el-table-column prop="publishTime" label="发布时间" width="150" :formatter="date" sortable></el-table-column>
      <el-table-column label="操作" width="126">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-permit="'manage:article:'" @click="window.open(scope.row.url)">查看</el-button>
          <el-button type="text" size="small" v-permit="'manage:article:modify'" @click.native="$router.push('/article/edit?id=' + scope.row.id)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--底部工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" v-permit="'manage:article:delete'" @click="batchRemove" :disabled="this.sels.length===0">删除所选</el-button>
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :current-page="filters.page" :page-size="filters.pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>
  </section>
</template>
<script>
import wrapper from '@/core/RequestWrapper'
import articleService from '@/service/article'
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
      sels: [] // 列表选中列
    }
  },
  methods: {
    date (row, column, cellValue, index) {
      return DateUtil.format(cellValue)
    },
    search () {
      this.loading = true
      wrapper.tips(articleService.list(this.filters)).then((response) => {
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
        wrapper.tips(articleService.delete(ids), true).then((response) => {
          if (response.code === 0) {
            this.search()
          } else {
            this.loading = false
          }
        })
      }).catch(() => {})
    }
  },
  mounted () {
    this.search()
  }
}
</script>
<style lang="scss" scoped>
</style>
