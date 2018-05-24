import ElementUI from 'element-ui'

export default {
  tips (request, showResponse = true, showError = true) {
    return new Promise((resolve, reject) => {
      request.then(response => {
        if (response && response.code === 0) {
          // Do nothing
        } else if (showResponse) {
          ElementUI.Notification.error({
            title: '接口异常[' + response.code + ']',
            message: response.message
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
