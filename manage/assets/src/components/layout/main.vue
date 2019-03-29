<template>
  <el-row class="container">
    <el-col :span="24" class="header">
      <el-col :span="10" class="logo">
        <router-link to="/">{{sysName}}</router-link>
      </el-col>
      <el-col :span="10">
        <div class="tools" @click.prevent="collapse">
          <i class="fa fa-align-justify"></i>
        </div>
      </el-col>
      <el-col :span="4" class="userinfo">
        <el-dropdown trigger="hover">
          <span class="el-dropdown-link userinfo-inner"><img :src="this.sysUserAvatar" /> {{sysUserName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="$router.push('/user/info')">个人信息</el-dropdown-item>
            <el-dropdown-item @click.native="$router.push('/user/password')">修改密码</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-col>
    <el-col :span="24" class="main">
      <aside :class="collapsed?'menu-collapsed':'menu-expanded'">
        <el-menu :default-active="$router.path" :collapse="collapsed" router style="overflow:auto">
          <template v-for="item in $store.state.user.data.menu">
            <el-submenu :key="item.id" :index="item.url ? item.url : ('#' + item.id)" v-if="item.children.length > 0">
              <template slot="title">
                <i :class="item.icon"></i><span slot="title">{{item.name}}</span>
              </template>
              <el-menu-item :key="children.id" :index="children.url" v-for="children in item.children">
                <i :class="children.icon"></i><span slot="title">{{children.name}}</span>
              </el-menu-item>
            </el-submenu>
            <el-menu-item :key="item.id" :index="item.url ? item.url : ('#' + item.id)" v-else>
              <i :class="item.icon"></i><span slot="title">{{item.name}}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </aside>
      <section class="content-container">
        <div class="grid-content bg-purple-light">
          <el-col :span="24" class="breadcrumb-container">
            <strong class="title">{{$route.name}}</strong>
          </el-col>
          <el-col :span="24" class="content-wrapper">
            <transition name="fade" mode="out-in">
              <router-view></router-view>
            </transition>
          </el-col>
        </div>
      </section>
    </el-col>
  </el-row>
</template>

<script>
import wrapper from '@/core/RequestWrapper'
import userService from '@/service/user'
import imgLogo from '@/assets/images/logo.png'
export default {
  data () {
    return {
      sysName: '后台管理系统',
      collapsed: false,
      sysUserName: '',
      sysUserAvatar: imgLogo
    }
  },
  methods: {
    logout () { // 退出登录
      var _this = this
      this.$confirm('确认退出吗?', '提示')
        .then(() => {
          wrapper.tips(userService.logout()).then(() => {
            _this.$router.go(0)
          })
        })
        .catch(() => {})
    },
    collapse () { // 折叠导航栏
      this.collapsed = !this.collapsed
    }
  },
  mounted () {
    let user = this.$store.state.user
    this.sysUserName = user.data.info.name || ''
  }
}
</script>

<style scoped lang="scss">
@import '@/styles/vars.scss';

.container {
  position: absolute;
  top: 0px;
  bottom: 0px;
  width: 100%;
  .header {
    height: 60px;
    line-height: 60px;
    background: $color-primary;
    color: #fff;
    .userinfo {
      text-align: right;
      padding-right: 35px;
      float: right;
      .userinfo-inner {
        cursor: pointer;
        color: #fff;
        img {
          width: 40px;
          height: 40px;
          border-radius: 20px;
          margin: 10px 0px 10px 10px;
          float: right;
        }
      }
    }
    .logo {
      width:230px;
      height: 60px;
      font-size: 22px;
      padding-left: 20px;
      padding-right: 20px;
      border-color: rgba(238, 241, 146, 0.3);
      border-right-width: 1px;
      border-right-style: solid;
      img {
        width: 40px;
        float: left;
        margin: 10px 10px 10px 18px;
      }
      a {
        color: #fff;
        text-decoration: none;
      }
    }
    .tools {
      padding: 0px 23px;
      width: 14px;
      height: 60px;
      line-height: 60px;
      cursor: pointer;
    }
  }
  .main {
    display: flex;
    // background: #324057;
    position: absolute;
    top: 60px;
    bottom: 0px;
    overflow: hidden;
    aside {
      flex: 0 0 230px;
      width: 230px;
      // position: absolute;
      // top: 0px;
      // bottom: 0px;
      .el-menu {
        height: 100%;
      }
      .collapsed {
        width: 60px;
        .item {
          position: relative;
        }
        .submenu {
          position: absolute;
          top: 0px;
          left: 60px;
          z-index: 99999;
          height: auto;
          display: none;
        }
      }
    }
    .menu-collapsed {
      flex: 0 0 60px;
      width: 60px;
    }
    .menu-expanded {
      flex: 0 0 230px;
      width: 230px;
    }
    .content-container {
      // background: #f1f2f7;
      flex: 1;
      // position: absolute;
      // right: 0px;
      // top: 0px;
      // bottom: 0px;
      // left: 230px;
      overflow-y: scroll;
      padding: 20px;
      .breadcrumb-container {
        //margin-bottom: 15px;
        .title {
          width: 200px;
          float: left;
          color: #475669;
        }
        .breadcrumb-inner {
          float: right;
        }
      }
      .content-wrapper {
        background-color: #fff;
        box-sizing: border-box;
      }
    }
  }
}
</style>
