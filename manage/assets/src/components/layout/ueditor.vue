<template>
  <div>
    <script id="editor" type="text/plain"></script>
  </div>
</template>

<script>
import '../../../../src/main/resources/public/ueditor/ueditor.config.js'
import '../../../../src/main/resources/public/ueditor/ueditor.all.min.js'
import '../../../../src/main/resources/public/ueditor/lang/zh-cn/zh-cn.js'

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
    this.editor = window.UE.getEditor('editor', Object.assign({ // 初始化UE
      UEDITOR_HOME_URL: process.env.apiURL + '/static/ueditor/',
      serverUrl: process.env.apiURL + '/ueditor',
      toolbars: [[
        'fullscreen', 'source', '|', 'undo', 'redo', '|',
        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
        'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
        'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
        'directionalityltr', 'directionalityrtl', 'indent', '|',
        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
        'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
        'simpleupload', 'insertcode', 'pagebreak', '|',
        'horizontal', '|',
        'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
        'print', 'preview', 'searchreplace', 'drafts'
      ]],
      scaleEnabled: true
    }, this.config))
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
