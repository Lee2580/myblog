import Vue from 'vue'
import dayjs from "dayjs";

//prototype实例，可用设置作用域   dayjs时间处理工具
Vue.prototype.$moment = dayjs;

Vue.filter("date", function(value, formatStr = "YYYY-MM-DD") {
    return dayjs(value).format(formatStr);
});

Vue.filter("dateTime", function(value, formatStr = "YYYY-MM-DD HH:mm:ss") {
    return dayjs(value).format(formatStr);
});
