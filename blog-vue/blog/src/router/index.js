import Vue from "vue";
import VueRouter from "vue-router";

const Music = () => import('../views/music/Music.vue')
const Playlists = () => import( '../views/music/Playlists.vue')
const Mvs = () => import('../views/music/Mvs.vue')
const Songs = () => import('../views/music/Songs.vue')
const Discovery = () => import('../views/music/Discovery.vue')
const Result = () => import('../views/music/Result.vue')
const Mv = () => import('../views/music/Mv.vue')
const Playlist = () => import('../views/music/Playlist.vue')

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        component: resolve => require(["../views/home/Home.vue"], resolve)
    },
    {
        path: "/blogs/:blogId",
        component: resolve => require(["../views/blog/Blog.vue"], resolve)
    },
    {
        path: "/archives",
        component: resolve => require(["../views/archive/Archive.vue"], resolve),
        meta: {
            title: "归档"
        }
    },
    {
        path: "/albums",
        component: resolve => require(["../views/album/Album.vue"], resolve),
        meta: {
            title: "相册"
        }
    },
    {
        path: "/albums/:albumId",
        component: resolve => require(["../views/album/Photo.vue"], resolve)
    },
    {
        path: "/tags",
        component: resolve => require(["../views/tag/Tag.vue"], resolve),
        meta: {
            title: "标签"
        }
    },
    {
        path: "/categories",
        component: resolve => require(["../views/category/Category.vue"], resolve),
        meta: {
            title: "分类"
        }
    },
    {
        path: "/categories/:categoryId",
        component: resolve => require(["../views/blog/BlogList.vue"], resolve)
    },
    {
        path: "/tags/:tagId",
        component: resolve => require(["../views/blog/BlogList.vue"], resolve)
    },
    {
        path: "/links",
        component: resolve => require(["../views/link/Link.vue"], resolve),
        meta: {
            title: "友链列表"
        }
    },
    {
        path: "/about",
        component: resolve => require(["../views/about/About.vue"], resolve),
        meta: {
            title: "关于我"
        }
    },
    {
        path: "/message",
        component: resolve => require(["../views/message/Message.vue"], resolve),
        meta: {
            title: "留言板"
        }
    },
    {
        path: "/user",
        component: resolve => require(["../views/user/User.vue"], resolve),
        meta: {
            title: "个人中心"
        }
    },
    {
        path: "/oauth/login/qq",
        component: resolve => require(["../components/OauthLogin.vue"], resolve)
    },
    {
        path: "/oauth/login/weibo",
        component: resolve => require(["../components/OauthLogin.vue"], resolve)
    },
    {
        path: '/Music',
        name: 'Music',
        component: Music,
        children: [
            {
                // 发现音乐
                path: '/Discovery',
                component: Discovery
            },
            {
                // 推荐歌单
                path: '/Playlists',
                component: Playlists
            },
            {
                // 推荐歌单
                path: '/Playlist',
                component: Playlist
            },
            {
                // 最新音乐
                path: '/Songs',
                component: Songs
            },
            {
                // 最新音乐
                path: '/Mvs',
                component: Mvs
            },
            // mv详情
            {
                path: '/Mv',
                component: Mv
            },
            // 搜索结果页
            {
                path: '/Result',
                component: Result
            }
        ]
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;
