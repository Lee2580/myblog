import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/css/index.css";
import "./assets/css/iconfont.css";
import axios from "axios";
import VueAxios from "vue-axios";
import ECharts from "vue-echarts";
import "echarts/lib/chart/line";
import "echarts/lib/chart/pie";
import "echarts/lib/chart/bar";
import "echarts/lib/chart/map";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import VueCalendarHeatmap from "vue-calendar-heatmap";
import tagCloud from "./components/tag-cloud";
import './util/dateTimeUtil'


//markdown编辑器
Vue.use(mavonEditor);
//3D云标签
Vue.use(tagCloud);
Vue.use(VueCalendarHeatmap);
//图表
Vue.component("v-chart", ECharts);
Vue.use(VueAxios, axios);
Vue.use(ElementUI);
Vue.config.productionTip = false;

// 页面加载进度条
NProgress.configure({
  easing: "ease", // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3 // 初始化时的最小百分比
});

// 路由守卫  路由开始跳转，开启进度条
router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.path == "/login") {
    next();
  } else if (!store.state.userId) {
    next({ path: "/login" });
  } else {
    next();
  }
});

// 路由跳转结束后，关闭进度条
router.afterEach(() => {
  NProgress.done();
});

// axios响应拦截器
axios.interceptors.response.use(
  function(response) {
    switch (response.data.code) {
      case 401:
        //code = 401的处理
        Vue.prototype.$message({
          type: "error",
          message: response.data.message
        });
        router.push({ path: "/login" });
        break;
      case 500:
        //系统异常处理
        Vue.prototype.$message({
          type: "error",
          message: response.data.message
        });
        break;
    }
    return response;
  },
  function(error) {
    return Promise.reject(error);
  }
);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
