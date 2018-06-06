export default {
  hasPermit ($store, value) {
    if (!value) return false
    let permission = $store.state.user.data.resource
    if (!permission) return false
    let values = value.split(':')
    if (values.length !== 3) return false
    if (!permission[values[0]]) return false
    if (!permission[values[0]][values[1]]) return false
    return permission[values[0]][values[1]].indexOf(values[2]) !== -1
  }
}
