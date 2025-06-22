const webpack = require("webpack");
("use strict");
const path = require("path");
const defaultSettings = require("./src/settings.js");

function resolve(dir) {
  return path.join(__dirname, dir);
}

const name = defaultSettings.title || "五育后台管理系统"; // page title

//如果端口设置为80，
//使用管理员权限执行命令行。
//例如，Mac:sudo npm run
//您可以通过以下方法更改端口：
//端口=9527 npm run dev或npm run dev--端口=9522
const port = process.env.port || process.env.npm_config_port || 9180; // dev port
//所有配置项说明可在中找到https://cli.vuejs.org/config/
module.exports = {
  publicPath: "/",
  outputDir: "dist",
  assetsDir: "info/static",
  lintOnSave: process.env.NODE_ENV === "development",
  productionSourceMap: false,

  /**
   *  为什么添加配置devServer.proxy进行代理转发?
   *  因为前后端分离，就算是本地开发调试，也会因为端口不同而满足跨域请求条件，而浏览器禁止跨域请求。
   *  配置代理之后，在项目启动时本地会启动一个node服务，浏览器先将请求发送到本地node服务，
   *  然后经由node服务将请求代理转发到后台，这样就可以避开浏览器跨域限制的问题。
   *  --min
   * */
  devServer: {
    proxy: {
      "/api": {
        // target: "http://58.87.88.61:9200", // 后台端口号
        // target: "http://localhost:9081",
        //target: "http://36.111.68.174:33380",
        //target: "http://36.111.68.174:33380",
        target: "http://us.uwis.cn:9080",
        // target: "http://localhost:33381",
        changeOrigin: true,
        //ws: true,
        pathRewrite: { "^/api": "" },
      },
    },
    port: port,
    host: '0.0.0.0',
    open: true,
    overlay: {
      warnings: false,
      errors: true,
    },
    // before: require('./mock/mock-server.js')  //移除mock
  },

  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        "@": resolve("src"),
      },
    },
    plugins: [
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        jquery: "jquery",
        "window.jQuery": "jquery",
      }),
    ],
  },
  chainWebpack(config) {
    // it can improve the speed of the first screen, it is recommended to turn on preload
    // it can improve the speed of the first screen, it is recommended to turn on preload
    config.plugin("preload").tap(() => [
      {
        rel: "preload",
        // to ignore runtime.js
        // https://github.com/vuejs/vue-cli/blob/dev/packages/@vue/cli-service/lib/config/app.js#L171
        fileBlacklist: [/\.map$/, /hot-update\.js$/, /runtime\..*\.js$/],
        include: "initial",
      },
    ]);

    // when there are many pages, it will cause too many meaningless requests
    config.plugins.delete("prefetch");

    // set svg-sprite-loader
    config.module.rule("svg").exclude.add(resolve("src/icons")).end();
    config.module
      .rule("icons")
      .test(/\.svg$/)
      .include.add(resolve("src/icons"))
      .end()
      .use("svg-sprite-loader")
      .loader("svg-sprite-loader")
      .options({
        symbolId: "icon-[name]",
      })
      .end();

    config.when(process.env.NODE_ENV !== "development", (config) => {
      config
        .plugin("ScriptExtHtmlWebpackPlugin")
        .after("html")
        .use("script-ext-html-webpack-plugin", [
          {
            // `runtime` must same as runtimeChunk name. default is `runtime`
            inline: /runtime\..*\.js$/,
          },
        ])
        .end();
      config.optimization.splitChunks({
        chunks: "all",
        cacheGroups: {
          libs: {
            name: "chunk-libs",
            test: /[\\/]node_modules[\\/]/,
            priority: 10,
            chunks: "initial", // only package third parties that are initially dependent
          },
          elementUI: {
            name: "chunk-elementUI", // split elementUI into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?element-ui(.*)/, // in order to adapt to cnpm
          },
          commons: {
            name: "chunk-commons",
            test: resolve("src/components"), // can customize your rules
            minChunks: 3, //  minimum common number
            priority: 5,
            reuseExistingChunk: true,
          },
        },
      });
      // https:// webpack.js.org/configuration/optimization/#optimizationruntimechunk
      config.optimization.runtimeChunk("single");
    });
  },
};
