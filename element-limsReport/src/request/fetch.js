import axios from './config' // config文件还是跟上面的一样，这里不再说明

// 根据 ./apiModules文件夹中 生成fetchCfg
// fetchCfg = {
//     pharmacy
// };
const fetchCfg = {}
const requireContext = require.context('./apiModules', false, /\.js$/)
requireContext.keys().forEach(path => {
  let module = path.replace('.js', '').replace('./', '')
  fetchCfg[module] = requireContext(path).default
})

/**
 * 解析参数
 * 这个函数主要负责解析传入fetch的 module 和 apiName
 * @param {String} param
 */
const fetchParam = param => {
  var valid = /[a-z]+(\.[a-z])+/.test(param)
  if (!valid) {
    throw new Error('[Error in fetch]: fetch 参数格式为 moduleName.apiName')
  } else {
    return {
      moduleName: param.split('.')[0],
      apiName: param.split('.')[1]
    }
  }
}

/**
 * 请求函数
 * 这个函数主要负责解析传入fetch的 module 和 apiName
 * @param {String} moduleInfo
 * @param {any} payload
 */
export function fetch(moduleInfo, payload) {
  let prefix = ''
  let moduleName = fetchParam(moduleInfo)['moduleName']
  let apiName = fetchParam(moduleInfo)['apiName']
  // 判断没有找到传入模块
  if (!fetchCfg.hasOwnProperty(moduleName)) {
    throw new Error(
      `[Error in fetch]: 在api配置文件中未找到模块 -> ${moduleName}`
    )
  }
  // 判断没有找到对应接口
  if (!fetchCfg[moduleName].hasOwnProperty(apiName)) {
    throw new Error(
      `[Error in fetch]: 在模块${moduleName}中未找到接口 -> ${apiName}`
    )
  }
  let fetchInfo = fetchCfg[moduleName][apiName]
  let method = fetchInfo['method']
  let url = `${prefix}/${fetchInfo['url']}`
  let mode = fetchInfo['mode'] // 此处在headers中添加一个needCancel属性。
  mode
    ? (axios.defaults.headers['needCancel'] = true)
    : (axios.defaults.headers['needCancel'] = false)
  if (method === 'get') {
    if (url.indexOf('{') !== -1) {
      let temp = url.match(/\{[^\}]+\}/)[0]
      let param = temp.substring(1, temp.length - 1)
      let newUrl = url.replace(/\{[^\)]*\}/g, payload[param])
      return axios[method](newUrl)
    }
    return axios[method](url, {
      params: payload
    })
  } else {
    return axios[method](url, payload)
  }
}
