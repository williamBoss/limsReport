'use strict'
const path = require('path')

const SpeedMeasurePlugin = require('speed-measure-webpack-plugin') // 打包时间查看插件
const smp = new SpeedMeasurePlugin()
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer') // 打包文件分析插件
  .BundleAnalyzerPlugin
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')

const webpack = require('webpack')
const AddAssetHtmlWebpackPlugin = require('add-asset-html-webpack-plugin')

let minimizer = []
let pluginsArr = [
  new webpack.DllReferencePlugin({
    context: process.cwd(),
    manifest: require('./public/vendor/vuebundle-manifest.json')
  }),
  new webpack.DllReferencePlugin({
    context: process.cwd(),
    manifest: require('./public/vendor/utilsbundle-manifest.json')
  }),
  new webpack.DllReferencePlugin({
    context: process.cwd(),
    manifest: require('./public/vendor/elementbundle-manifest.json')
  }),
  // 将 dll 注入到 生成的 html 模板中
  new AddAssetHtmlWebpackPlugin({
    // dll文件位置
    filepath: path.resolve(__dirname, './public/vendor/*.js'),
    // dll 引用路径
    publicPath: './vendor',
    // dll最终输出的目录
    outputPath: './vendor'
  })
]
if (process.env.NODE_ENV === 'production' && process.env.UPLOAD === 'false') {
  // 生产环境下
  // 插件添加 打包分析
  pluginsArr.push(new BundleAnalyzerPlugin())
  minimizer.push(
    new UglifyJsPlugin({
      uglifyOptions: {
        warnings: false,
        compress: {
          drop_console: true, //console
          drop_debugger: false,
          pure_funcs: ['console.log'] //移除console
        }
      }
    })
  )
} else {
  minimizer.push(
    new UglifyJsPlugin({
      uglifyOptions: {
        warnings: false,
        compress: {
          drop_console: true, //console
          drop_debugger: false,
          pure_funcs: ['console.log'] //移除console
        }
      }
    })
  )
}

function resolve(dir) {
  return path.join(__dirname, dir)
}

const name = 'simcere' // page title

// If your port is set to 80,
// use administrator privileges to execute the command line.
// For example, Mac: sudo npm run
// You can change the port by the following method:
// port = 9527 npm run dev OR npm run dev --port = 9527
const port = process.env.port || process.env.npm_config_port || 8888 // dev port

module.exports = {
  publicPath: './',
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: process.env.NODE_ENV === 'development',
  productionSourceMap: false,
  devServer: {
    port: port,
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      // change xxx-api/login => mock/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      // [process.env.VUE_APP_BASE_API]: {
      //   target: 'http://172.16.110.220:9001',
      //   changeOrigin: true,
      //   pathRewrite: {
      //     ['^' + process.env.VUE_APP_BASE_API]: ''
      //   }
      // }
      '/api': {
        target: process.env.APP_HOST,
        headers: {
          // 做请求转发时还可以自定义请求头。
          host: process.env.APP_HOST
        },
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },
  configureWebpack: smp.wrap({
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      modules: [path.resolve(__dirname, 'node_modules')],
      alias: {
        '@': resolve('src')
      }
    },
    module: {
      noParse: /^(vue|vue-router|vuex|vuex-router-sync|lodash|moment|axios)$/
    },
    plugins: pluginsArr,
    optimization: {
      minimizer
    }
  }),
  chainWebpack(config) {
    config
      // https://webpack.js.org/configuration/devtool/#development
      .when(process.env.NODE_ENV === 'development', config =>
        config.devtool('cheap-source-map')
      )
    config
      // https://webpack.js.org/configuration/devtool/#development
      .when(process.env.NODE_ENV === 'development', config =>
        config.devtool('cheap-source-map')
      )
    config.when(process.env.NODE_ENV !== 'development', config => {
      config
        .plugin('ScriptExtHtmlWebpackPlugin')
        .after('html')
        .use('script-ext-html-webpack-plugin', [
          {
            // `runtime` must same as runtimeChunk name. default is `runtime`
            inline: /runtime\..*\.js$/
          }
        ])
        .end()
      config.optimization.splitChunks({
        chunks: 'all',
        cacheGroups: {
          elementUI: {
            name: 'chunk-elementUI', // split elementUI into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
          },
          libs: {
            name: 'chunk-libs',
            test: /[\\/]node_modules[\\/]/,
            priority: 10,
            chunks: 'initial' // only package third parties that are initially dependent
          },
          commons: {
            name: 'chunk-commons',
            test: resolve('src/components'), // can customize your rules
            minChunks: 3, //  minimum common number
            priority: 5,
            reuseExistingChunk: true // 如果一个文件已经被打包过了，再打包时就忽略这个模块，直接使用之前已经打包的那个模块即可。(比如入口文件中引入了a,b模块，但是a模块中已经引入了b模块，所以打包a代码时不会在打包b了，因为b在入口中引入时已经打包过了，会直接使用)
          }
        }
      })
      config.optimization.runtimeChunk('single')
    })
  }
}
