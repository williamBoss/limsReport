export default {
  checkToken: {
    url: 'check/token',
    method: 'get'
  },
  login: {
    url: 'login',
    method: 'post'
  },
  setSwitchNode: {
    url: 'v1/user/role/{id}',
    method: 'get'
  },
  getSwitchNodeList: {
    url: 'v1/role/local/list',
    method: 'get'
  }
}
