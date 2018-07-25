<template>
  <div>
    <script id="editor" type="text/plain"></script>
  </div>
</template>

<script>
import '../../../static/ueditor/ueditor.config.js'
import '../../../static/ueditor/ueditor.all.min.js'
import '../../../static/ueditor/lang/zh-cn/zh-cn.js'
window.UEDITOR_HOME_URL = process.env.apiURL + '/static/ueditor/'
export default {
  name: 'ueditor',
  data () {
    return {
      editor: null
    }
  },
  props: {
    content: {
      type: String
    },
    config: {
      type: Object
    }
  },
  mounted () {
    const _this = this
    this.editor = window.UE.getEditor('editor', Object.assign({}, this.config, { // 初始化UE
      serverUrl: process.env.apiURL + '/ueditor'
    }))
    this.editor.addListener('ready', function () {
      _this.editor.setContent(_this.content) // 确保UE加载完成后，放入内容。
    })
  },
  methods: {
    setContent (content) {
      this.editor.setContent(content)
    },
    getContent () { // 获取内容方法
      return this.editor.getContent()
    }
  },
  destroyed () {
    this.editor.destroy()
  }
}
</script>
