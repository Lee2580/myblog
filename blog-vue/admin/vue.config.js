//服务器会将任何未知请求 (没有匹配到静态文件的请求) 代理到http://localhost:8080上
module.exports = {
  productionSourceMap: false,
  devServer: {
    proxy: {
      "/api": {
        //代理地址，这里设置的地址会代替axios中设置的baseURL
        target: "http://localhost:9501",
        //是否跨域
        changeOrigin: true,
        //pathRewrite方法重写url
        pathRewrite: {
          "^/api": ""
        }
      }
    },
    disableHostCheck: true
  },

  //设置路径别名
  chainWebpack: config => {
    config.resolve.alias.set("@", resolve("src"));
  }
};

const path = require("path");
function resolve(dir) {
  return path.join(__dirname, dir);
}
