import ElementUI from 'element-ui'

export default {
  tips (request, showSuccess = false, showWarning = true, showError = true) {
    return new Promise((resolve, reject) => {
      request.then(response => {
        if (response && response.code === 0) {
          showSuccess && ElementUI.Notification.success({
            title: '状态：' + response.code,
            message: '消息:' + response.message
          })
        } else if (showWarning) {
          ElementUI.Notification.warning({
            title: '状态：' + response.code,
            message: '消息:' + response.message
          })
        }
        resolve(response)
      }).catch(error => {
        if (showError) {
          ElementUI.Notification.error({
            title: '请求异常',
            message: error.message
          })
        }
        resolve({code: 500, message: error.message, data: error})
      })
    })
  }
}
