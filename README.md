# myblog
## 简介

* 本项目是基于 SpringBoot + Vue 的前后端分离博客系统
* 本人能力有限，前端主要是基于模板和其他大佬博客样式修改，后端基本是个人完成

### 项目预览

* 博客地址：[叶沐秋の个人博客 (lovesky.top)](https://www.lovesky.top/)
* 后台地址：[后台管理系统 (lovesky.top)](https://admin.lovesky.top/login)
  * 预览账号：test，密码：123456

## 主要模块功能

* 博文模块
  * 采用`Markdown`编辑器
  * 发布、保存草稿、逻辑删除、分类、标签等功能
* 评论模块
  * 登录后即可发表评论
  * 评论回复支持向评论者发送邮件提醒
* 访客统计
  * 访客进行请求就会使用Redis进行统计，定时任务每天2点存入数据库
* 图片上传
  * 用于上传博文图片、封面首图等
  * 目前实现两种方案：阿里云OSS和本地
  * 考虑到更换服务器数据迁移方便，暂时使用的OSS
* 登录模块
  * 目前仅支持邮箱注册登录
  * 微博登录已测试暂未上线，后续会考虑增加QQ、GitHub等三方登录功能
* 搜索模块
  * 搜索结果支持高亮显示
  * 服务器内存限制暂时使用Mysql模糊匹配，后续更换服务器会升级ElasticSearch作为搜索引擎
* 音乐盒模块
  * 接口调用：[Binaryify/NeteaseCloudMusicApi: 网易云音乐 Node.js API service (github.com)](https://github.com/Binaryify/NeteaseCloudMusicApi)
  * 音乐播放器：[vue-aplayer](https://aplayer.netlify.app/)
* 权限管理
  * 支持动态权限修改
  * 根据用户的角色权限动态获取后台菜单
* 系统管理
  * 支持后台修改前台页面背景图片、网站配置信息
* 日志管理
  * 通过AOP注解实现日志管理



## 技术栈

### 前端

| 名称       | 技术点                                                       |
| ---------- | ------------------------------------------------------------ |
| 基础       | Vue、Vue Router、Vuex                                        |
| 异步请求   | Axios                                                        |
| UI框架     | Vuetify、Element、Echarts                                    |
| 文本编辑器 | [mavonEditor](https://github.com/hinesboy/mavonEditor)       |
| 前台模板   | [hexo-theme-butterfly](https://github.com/jerryc127/hexo-theme-butterfly) |
| 后台模板   | [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin) |

### 后端

| 名称     | 技术点                                              |
| -------- | --------------------------------------------------- |
| 开发     | SpringBoot、MyBatisPlus                             |
| 安全框架 | SpringSecurity                                      |
| 数据库   | MySQL                                               |
| 缓存     | Redis                                               |
| 消息队列 | RabbitMq                                            |
| 其他     | 邮件任务、定时任务、阿里云OSS、第三方登录（待完善） |

### 部署

| 名称       | 技术点 |
| ---------- | ------ |
| 容器引擎   | Docker |
| 代理服务器 | Nginx  |

### 开发环境

| 开发环境   | 版本   |
| ---------- | ------ |
| JDK        | 11     |
| SpringBoot | 2.5.4  |
| Vue        | 2.6.11 |
| MySQL      | 5.7    |
| Redis      | 6.2.5  |
| RabbitMQ   | 3.9.7  |
| Nginx      | 1.21.1 |

## 注意事项

* 此项目用的Mysql 5.7版，不支持emoji表情存储，博文保存时会报错，如有需要可以将Mysql升级为8.0，Mysql 8.0 的字符集是 `utf8mb4` ，支持emoji表情，而且8.0也支持窗口函数
* 最好不要使用8080端口，容易被恶意侦测

## 项目部署

* 项目部署详情：[博客网站部署 (lovesky.top)](https://www.lovesky.top/blogs/4)
* 服务器最低要求：1核1G
  * 使用国内服务器直接上2核4G就行，可以扩充功能，不贵，就是域名需要备案
  * 国外服务器一般比较贵，无需备案，推荐香港的，延迟低
* 证书申请阿里云的一年SSL免费证书即可

## 结语

* 目前部署在海外VPS上，内存有限，内容还比较单薄，到期后会换一个服务器
* 本项目长期维护
  * 为了巩固SpringCloud的学习，后续会将项目改为分布式微服务架构
  * 优化认证机制
  * 还会更新优化一些功能，比如：引入ElasticSearch搜索引擎，优化日志分类、定时任务，优化Sql语句、增加其他页面放一些个人兴趣的小功能等
