import Vue from 'vue'
import dayjs from "dayjs";


Vue.filter("date", function(value) {
    return dayjs(value).format("YYYY-MM-DD");
});

Vue.filter("year", function(value) {
    return dayjs(value).format("YYYY");
});

Vue.filter("hour", function(value) {
    return dayjs(value).format("HH:mm:ss");
});

Vue.filter("num", function(value) {
    if (value >= 1000) {
        return (value / 1000).toFixed(1) + "k";
    }
    return value;
});
