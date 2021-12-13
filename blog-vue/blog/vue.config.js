module.exports = {
  transpileDependencies: ["vuetify"],
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:9501",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      },
      '/music': {
        target: 'https://music.lovesky.top',
        changeOrigin: true,
        // secure: false,
        pathRewrite: {
          '^/music': ''
        }
      }
    },
    disableHostCheck: true
  },
  productionSourceMap: false,
  css: {
    extract: true,
    sourceMap: false
  }
};
