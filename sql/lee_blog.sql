/*
 Navicat MySQL Data Transfer

 Source Server         : Linux Mysql
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.79.10:3306
 Source Schema         : lee_blog

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 27/09/2021 14:12:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `blog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `user_id` bigint(20) NOT NULL COMMENT '作者',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '首图',
  `blog_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '博客类型 1原创 2转载 3翻译',
  `original_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原文链接',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  `thumbs` int(10) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `recommend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '推荐状态 0否 1推荐',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '发布状态 1公开 2私密 3草稿',
  `views` int(10) NULL DEFAULT 0 COMMENT '浏览次数',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, '1111', 2, '1111111', '2021-09-22 10:55:13', '2021-09-23 13:45:50', 'https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/blogs/223e92ee2b97ecc66cbe6d0d743a4d59.jpeg', 1, '', 0, 0, 0, 1, 0);
INSERT INTO `blog` VALUES (2, '测试111111', 2, '22222222222![timg.jpg](https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/blogs/6ed3e16a19fdbf815cc49169a5ae813f.jpg)', '2021-09-22 21:42:23', '2021-09-24 22:05:15', 'https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/blogs/0196aafee8c975fdf6ed534ea561169a.jpg', 1, '', 0, 0, 0, 1, 0);

-- ----------------------------
-- Table structure for blog_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `blog_id` bigint(20) NOT NULL COMMENT '文章id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_category
-- ----------------------------
INSERT INTO `blog_category` VALUES (1, 2, 5);
INSERT INTO `blog_category` VALUES (2, 2, 5);
INSERT INTO `blog_category` VALUES (3, 1, 5);

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `blog_id` bigint(20) NOT NULL COMMENT '文章id',
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES (2, 2, 6);
INSERT INTO `blog_tag` VALUES (3, 1, 6);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '项目记录', '2021-09-23 10:11:30', '2021-09-23 11:53:22');
INSERT INTO `category` VALUES (2, '学习日志', '2021-09-23 10:12:38', NULL);
INSERT INTO `category` VALUES (4, '生活随笔', '2021-09-23 10:16:51', NULL);
INSERT INTO `category` VALUES (5, '测试分类', '2021-09-23 12:50:14', NULL);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '评论用户Id',
  `blog_id` bigint(20) NULL DEFAULT NULL COMMENT '评论博客id',
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `reply_user_id` bigint(20) NULL DEFAULT NULL COMMENT '回复用户id',
  `parent_comment_id` bigint(20) NULL DEFAULT NULL COMMENT '父评论id',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_review` tinyint(1) NOT NULL DEFAULT 1 COMMENT '审核状态 0未审核 1已审核',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 9, 2, '测试11111<img src= \'https://static.lovesky.top/emoji/haqian.jpg\' width=\'22\'height=\'20\' style=\'padding: 0 1px\'/>', NULL, NULL, '2021-09-25 16:32:01', NULL, 1, 0);
INSERT INTO `comment` VALUES (2, 9, 2, '测试111', NULL, NULL, '2021-09-25 16:41:02', NULL, 1, 0);
INSERT INTO `comment` VALUES (3, 9, 2, 'qqqqqqq', NULL, NULL, '2021-09-25 16:50:41', NULL, 1, 0);
INSERT INTO `comment` VALUES (4, 9, 2, '回复测试', 9, 3, '2021-09-25 18:07:12', NULL, 1, 0);
INSERT INTO `comment` VALUES (5, 9, 2, 'huifuceshi', 9, 3, '2021-09-25 18:48:30', NULL, 1, 0);
INSERT INTO `comment` VALUES (6, 9, 2, '测试楼中楼<img src= \'https://static.lovesky.top/emoji/linghunchuqiao.jpg\' width=\'22\'height=\'20\' style=\'padding: 0 1px\'/>', 9, NULL, '2021-09-25 19:10:37', NULL, 1, 0);
INSERT INTO `comment` VALUES (7, 9, 2, '测试楼中楼1111', 9, NULL, '2021-09-25 19:17:32', NULL, 1, 0);
INSERT INTO `comment` VALUES (8, 9, 2, '测试楼中楼2222', 9, NULL, '2021-09-25 19:34:05', NULL, 1, 0);
INSERT INTO `comment` VALUES (9, 9, 2, '测试楼中楼33333', 9, 3, '2021-09-25 19:36:23', NULL, 1, 0);
INSERT INTO `comment` VALUES (10, 9, 2, 'cccccccc', 9, 3, '2021-09-25 20:20:08', NULL, 1, 0);
INSERT INTO `comment` VALUES (11, 9, 2, '33333', 9, 3, '2021-09-25 20:20:42', NULL, 1, 0);
INSERT INTO `comment` VALUES (12, 9, 2, '@叶沐秋', 9, 3, '2021-09-25 20:21:38', NULL, 1, 0);
INSERT INTO `comment` VALUES (13, 9, NULL, '测试友链评论', NULL, NULL, '2021-09-25 20:28:18', NULL, 1, 0);

-- ----------------------------
-- Table structure for friend_link
-- ----------------------------
DROP TABLE IF EXISTS `friend_link`;
CREATE TABLE `friend_link`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `link_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接名',
  `link_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接头像',
  `link_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接地址',
  `link_intro` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链接介绍',
  `link_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '友链展示状态 0否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend_link
-- ----------------------------
INSERT INTO `friend_link` VALUES (2, '测试', '1111', '1111111', '11111111', 1, '2021-09-23 21:45:48', NULL);
INSERT INTO `friend_link` VALUES (3, '测试222', '222222', '2222222', '2222222', 1, '2021-09-24 10:42:46', '2021-09-24 22:04:44');
INSERT INTO `friend_link` VALUES (4, '用户测试', '11111', '1111111', '33333333', 1, '2021-09-24 14:37:07', '2021-09-24 16:55:27');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名',
  `path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单路径',
  `component` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单图标',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `order_num` int(4) NOT NULL COMMENT '排序',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父id',
  `status` tinyint(1) NOT NULL COMMENT '状态',
  `type` int(4) NOT NULL COMMENT '类型 0：目录 1：菜单 2：按钮',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '首页', '/', '/Home.vue', 'el-icon-myshouye', '2021-09-21 14:30:33', '2021-09-21 14:30:36', 1, NULL, 0, 0);
INSERT INTO `menu` VALUES (2, '博客管理', '/blog-submenu', 'Layout', 'el-icon-myblog-copy', '2021-09-21 14:32:05', '2021-09-21 14:32:07', 2, NULL, 0, 0);
INSERT INTO `menu` VALUES (3, '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', '2021-09-21 14:32:50', '2021-09-21 14:32:47', 3, NULL, 0, 0);
INSERT INTO `menu` VALUES (4, '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', '2021-09-21 14:34:20', '2021-09-21 14:34:23', 4, NULL, 0, 0);
INSERT INTO `menu` VALUES (5, '权限管理', '/permission-submenu', 'Layout', 'el-icon-mydaohanglantubiao_quanxianguanli', '2021-09-21 14:35:57', '2021-09-21 14:35:59', 5, NULL, 0, 0);
INSERT INTO `menu` VALUES (6, '系统管理', '/system-submenu', 'Layout', 'el-icon-myshezhi', '2021-09-21 14:37:24', '2021-09-21 14:37:26', 6, NULL, 0, 0);
INSERT INTO `menu` VALUES (7, '日志管理', '/log-submenu', 'Layout', 'el-icon-myguanyuwo', '2021-09-21 14:38:37', '2021-09-21 14:38:39', 7, NULL, 0, 0);
INSERT INTO `menu` VALUES (8, '个人中心', '/setting', '/setting/Setting.vue', 'el-icon-myuser', '2021-09-21 14:39:50', '2021-09-21 14:39:52', 8, NULL, 0, 0);
INSERT INTO `menu` VALUES (9, '发布博客', '/blogs', '/blog/Blog.vue', 'el-icon-myfabiaoblog', '2021-09-21 14:43:12', '2021-09-21 14:43:14', 1, 2, 0, 1);
INSERT INTO `menu` VALUES (10, '修改博客', '/blogs/*', '/blog/Blog.vue', 'el-icon-myfabiaoblog', '2021-09-21 14:45:24', '2021-09-24 22:06:05', 2, 2, 1, 1);
INSERT INTO `menu` VALUES (11, '博客列表', '/blog-list', '/blog/BlogList.vue', 'el-icon-myblogliebiao', '2021-09-21 14:48:32', '2021-09-21 14:48:34', 3, 2, 0, 1);
INSERT INTO `menu` VALUES (12, '分类管理', '/categories', '/blog/Category.vue', 'el-icon-myfenlei', '2021-09-21 14:49:18', '2021-09-21 14:49:20', 4, 2, 0, 1);
INSERT INTO `menu` VALUES (13, '标签管理', '/tags', '/blog/Tag.vue', 'el-icon-myicontag', '2021-09-21 14:50:53', '2021-09-21 14:50:55', 5, 2, 0, 1);
INSERT INTO `menu` VALUES (14, '评论管理', '/comments', '/message/Comment.vue', 'el-icon-mypinglunzu', '2021-09-21 14:53:08', '2021-09-21 14:53:10', 1, 3, 0, 1);
INSERT INTO `menu` VALUES (15, '留言管理', '/messages', '/message/Message.vue', 'el-icon-myliuyan', '2021-09-21 14:54:40', '2021-09-21 14:54:42', 2, 3, 0, 1);
INSERT INTO `menu` VALUES (16, '用户列表', '/users', '/user/User.vue', 'el-icon-myyonghuliebiao', '2021-09-21 14:55:50', '2021-09-21 14:55:51', 1, 4, 0, 1);
INSERT INTO `menu` VALUES (17, '在线用户', '/online/users', '/user/Online.vue', 'el-icon-myyonghuliebiao', '2021-09-21 14:57:18', '2021-09-21 14:57:20', 2, 4, 0, 1);
INSERT INTO `menu` VALUES (18, '角色管理', '/roles', '/rbac/Role.vue', 'el-icon-myjiaoseliebiao', '2021-09-21 14:58:55', '2021-09-21 14:58:56', 1, 5, 0, 1);
INSERT INTO `menu` VALUES (19, '菜单管理', '/menus', '/rbac/Menu.vue', 'el-icon-mycaidan', '2021-09-21 15:00:36', '2021-09-21 15:00:37', 2, 5, 0, 1);
INSERT INTO `menu` VALUES (20, '模块管理', '/resources', '/rbac/Resource.vue', 'el-icon-myjiekouguanli', '2021-09-21 15:02:39', '2021-09-21 15:02:40', 3, 5, 0, 1);
INSERT INTO `menu` VALUES (21, '网站管理', '/website', '/system/Website.vue', 'el-icon-myxitong', '2021-09-21 15:04:08', '2021-09-21 15:04:10', 1, 6, 0, 1);
INSERT INTO `menu` VALUES (22, '页面管理', '/pages', '/system/Page.vue', 'el-icon-myyemianpeizhi', '2021-09-21 15:06:11', '2021-09-21 15:06:13', 2, 6, 0, 1);
INSERT INTO `menu` VALUES (23, '友链管理', '/links', '/system/FriendLink.vue', 'el-icon-mydashujukeshihuaico-', '2021-09-21 15:08:02', '2021-09-21 15:08:04', 3, 6, 0, 1);
INSERT INTO `menu` VALUES (24, '关于我', '/about', '/system/About.vue', 'el-icon-myguanyuwo', '2021-09-21 15:08:42', '2021-09-21 15:08:41', 4, 6, 0, 1);
INSERT INTO `menu` VALUES (25, '操作日志', '/operation/log', '/log/Operation.vue', 'el-icon-myguanyuwo', '2021-09-21 15:10:23', '2021-09-21 15:10:25', 1, 7, 0, 1);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `mid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `message_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  `time` tinyint(1) NOT NULL COMMENT '弹幕速度',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ip',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户地址',
  `is_review` tinyint(1) NOT NULL DEFAULT 1 COMMENT '审核状态 0未审核 1已审核',
  PRIMARY KEY (`mid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, '叶沐秋', 'https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/avatar/93753c3469564c361b1c612f7c29d402.jpg', '测试11111111111111111', 1, '2021-09-24 14:43:55', '127.0.0.1', '', 1);

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `opt_module` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作模块',
  `opt_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
  `opt_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作url',
  `opt_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作方法',
  `opt_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作描述',
  `request_param` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方式',
  `response_data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '返回数据',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作ip',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operation_log
-- ----------------------------
INSERT INTO `operation_log` VALUES (1, '标签模块', '新增或修改', '/admin/saveOrUpdateTag', 'com.lee.blog.controller.TagController.saveOrUpdateTag', '添加或修改标签', '[{\"tagName\":\"Docker\"}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 11:54:19', NULL);
INSERT INTO `operation_log` VALUES (2, '分类模块', '新增或修改', '/admin/saveOrUpdateCategory', 'com.lee.blog.controller.CategoryController.saveOrUpdateCategory', '保存或更新分类', '[{\"categoryName\":\"测试分类\"}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 12:50:14', NULL);
INSERT INTO `operation_log` VALUES (3, '标签模块', '新增或修改', '/admin/saveOrUpdateTag', 'com.lee.blog.controller.TagController.saveOrUpdateTag', '添加或修改标签', '[{\"tagName\":\"测试标签\"}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 12:50:23', NULL);
INSERT INTO `operation_log` VALUES (4, '后台博客模块管理', '新增或修改', '/admin/saveOrUpdateBlog', 'com.lee.blog.controller.BlogController.saveOrUpdateBlog', '保存或修改博客', '[{\"blogId\":2,\"blogType\":1,\"categoryName\":\"测试分类\",\"content\":\"22222222222![timg.jpg](null)\",\"originalUrl\":\"\",\"recommend\":0,\"status\":1,\"tagNameList\":[],\"title\":\"2021-09-22\"}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 13:40:51', NULL);
INSERT INTO `operation_log` VALUES (5, '后台博客模块管理', '新增或修改', '/admin/saveOrUpdateBlog', 'com.lee.blog.controller.BlogController.saveOrUpdateBlog', '保存或修改博客', '[{\"blogId\":2,\"blogType\":1,\"categoryName\":\"测试分类\",\"content\":\"22222222222![timg.jpg](https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/blogs/6ed3e16a19fdbf815cc49169a5ae813f.jpg)\",\"firstPicture\":\"https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/blogs/0196aafee8c975fdf6ed534ea561169a.jpg\",\"originalUrl\":\"\",\"recommend\":0,\"status\":1,\"tagNameList\":[],\"title\":\"测试111111\"}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 13:44:40', NULL);
INSERT INTO `operation_log` VALUES (6, '后台博客模块管理', '修改', '/admin/blog/recommend', 'com.lee.blog.controller.BlogController.updateBlogRecommend', '修改文章推荐置顶状态', '[{\"blogId\":2,\"recommend\":1}]', 'PUT', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 13:44:58', NULL);
INSERT INTO `operation_log` VALUES (7, '后台博客模块管理', '修改', '/admin/blog/recommend', 'com.lee.blog.controller.BlogController.updateBlogRecommend', '修改文章推荐置顶状态', '[{\"blogId\":2,\"recommend\":0}]', 'PUT', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 13:44:59', NULL);
INSERT INTO `operation_log` VALUES (8, '后台博客模块管理', '新增或修改', '/admin/saveOrUpdateBlog', 'com.lee.blog.controller.BlogController.saveOrUpdateBlog', '保存或修改博客', '[{\"blogId\":1,\"blogType\":1,\"content\":\"1111111\",\"firstPicture\":\"\",\"originalUrl\":\"\",\"recommend\":0,\"status\":3,\"tagNameList\":[],\"title\":\"1111\"}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 13:45:24', NULL);
INSERT INTO `operation_log` VALUES (9, '后台博客模块管理', '新增或修改', '/admin/saveOrUpdateBlog', 'com.lee.blog.controller.BlogController.saveOrUpdateBlog', '保存或修改博客', '[{\"blogId\":1,\"blogType\":1,\"categoryName\":\"测试分类\",\"content\":\"1111111\",\"firstPicture\":\"https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/blogs/223e92ee2b97ecc66cbe6d0d743a4d59.jpeg\",\"originalUrl\":\"\",\"recommend\":0,\"status\":1,\"tagNameList\":[],\"title\":\"1111\"}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-23 13:45:50', NULL);
INSERT INTO `operation_log` VALUES (10, '友链控制模块', '新增或修改', '/admin/saveOrUpdateFriendLink', 'com.lee.blog.controller.FriendLinkController.saveOrUpdateFriendLink', '保存或修改友链', '[{\"linkAddress\":\"1111111\",\"linkAvatar\":\"11111\",\"linkIntro\":\"33333333\",\"linkName\":\"用户测试\",\"linkStatus\":0}]', 'POST', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 9, '用户1441259384637317121', '127.0.0.1', '', '2021-09-24 14:37:07', NULL);
INSERT INTO `operation_log` VALUES (11, '用户操作模块', '修改', '/admin/users/status', 'com.lee.blog.controller.UserController.updateUserStatus', '修改用户状态', '[{\"status\":0,\"userId\":1}]', 'PUT', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 17:42:01', NULL);
INSERT INTO `operation_log` VALUES (12, '用户操作模块', '修改', '/admin/users/status', 'com.lee.blog.controller.UserController.updateUserStatus', '修改用户状态', '[{\"status\":1,\"userId\":1}]', 'PUT', '{\"code\":200,\"msg\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 17:45:54', NULL);
INSERT INTO `operation_log` VALUES (13, '用户操作模块', '修改', '/admin/users/status', 'com.lee.blog.controller.UserController.updateUserStatus', '修改用户状态', '[{\"id\":1,\"status\":1}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 21:05:20', NULL);
INSERT INTO `operation_log` VALUES (14, '角色管理', '修改', '/admin/role/status', 'com.lee.blog.controller.RoleController.updateUserStatus', '修改角色状态', '[{\"id\":3,\"status\":1}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 21:05:33', NULL);
INSERT INTO `operation_log` VALUES (15, '角色管理', '修改', '/admin/role/status', 'com.lee.blog.controller.RoleController.updateUserStatus', '修改角色状态', '[{\"id\":3,\"status\":0}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 21:05:59', NULL);
INSERT INTO `operation_log` VALUES (16, '菜单管理', '修改', '/admin/menus/status', 'com.lee.blog.controller.MenuController.updateMenuStatus', '修改菜单状态', '[{\"id\":10,\"status\":0}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 21:59:24', NULL);
INSERT INTO `operation_log` VALUES (17, '菜单管理', '修改', '/admin/menus/status', 'com.lee.blog.controller.MenuController.updateMenuStatus', '修改菜单状态', '[{\"id\":10,\"status\":1}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 22:00:04', NULL);
INSERT INTO `operation_log` VALUES (18, '博客管理模块', '修改', '/admin/blog/recommend', 'com.lee.blog.controller.BlogController.updateBlogRecommend', '修改文章推荐置顶状态', '[{\"blogId\":2,\"recommend\":1}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 22:05:06', NULL);
INSERT INTO `operation_log` VALUES (19, '博客管理模块', '修改', '/admin/blog/recommend', 'com.lee.blog.controller.BlogController.updateBlogRecommend', '修改文章推荐置顶状态', '[{\"blogId\":2,\"recommend\":0}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 22:05:15', NULL);
INSERT INTO `operation_log` VALUES (20, '用户操作模块', '修改', '/admin/users/status', 'com.lee.blog.controller.UserController.updateUserStatus', '修改用户状态', '[{\"id\":1,\"status\":0}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 22:05:36', NULL);
INSERT INTO `operation_log` VALUES (21, '用户操作模块', '修改', '/admin/users/status', 'com.lee.blog.controller.UserController.updateUserStatus', '修改用户状态', '[{\"id\":1,\"status\":1}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 22:05:42', NULL);
INSERT INTO `operation_log` VALUES (22, '菜单管理', '修改', '/admin/menus/status', 'com.lee.blog.controller.MenuController.updateMenuStatus', '修改菜单状态', '[{\"id\":10,\"status\":0}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 22:05:56', NULL);
INSERT INTO `operation_log` VALUES (23, '菜单管理', '修改', '/admin/menus/status', 'com.lee.blog.controller.MenuController.updateMenuStatus', '修改菜单状态', '[{\"id\":10,\"status\":1}]', 'PUT', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-24 22:06:05', NULL);
INSERT INTO `operation_log` VALUES (24, '角色管理', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"user\",\"menuIdList\":[8],\"name\":\"用户\",\"roleId\":2}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-25 00:17:38', NULL);
INSERT INTO `operation_log` VALUES (25, '角色管理', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"user\",\"name\":\"用户\",\"resourceIdList\":[1,4],\"roleId\":2}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-25 00:17:53', NULL);
INSERT INTO `operation_log` VALUES (26, '角色管理', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"user\",\"name\":\"用户\",\"resourceIdList\":[],\"roleId\":2}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-25 00:18:07', NULL);
INSERT INTO `operation_log` VALUES (27, '角色管理', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"test\",\"menuIdList\":[8],\"name\":\"测试\",\"roleId\":3}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-25 00:22:44', NULL);
INSERT INTO `operation_log` VALUES (28, '角色管理', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"user\",\"menuIdList\":[1,8],\"name\":\"用户\",\"roleId\":2}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 19:11:26', NULL);
INSERT INTO `operation_log` VALUES (29, '角色管理', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"test\",\"menuIdList\":[1,2,9,10,11,12,13,3,14,15,4,16,17,5,18,19,20,6,21,22,23,24,7,25,8],\"name\":\"测试\",\"roleId\":3}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 19:12:18', NULL);
INSERT INTO `operation_log` VALUES (30, '角色管理模块', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"admin\",\"name\":\"管理员\",\"resourceIdList\":[1,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,27,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102],\"roleId\":1}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 20:58:00', NULL);
INSERT INTO `operation_log` VALUES (31, '角色管理模块', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"admin\",\"name\":\"管理员\",\"resourceIdList\":[1,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,27,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102],\"roleId\":1}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 20:58:01', NULL);
INSERT INTO `operation_log` VALUES (32, '角色管理模块', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"test\",\"name\":\"测试\",\"resourceIdList\":[4,11,12,13,14,18,22,23,27,30,37,40,45,49,50,57,61,64,68,72,73,74,77,80,87,88,99],\"roleId\":3}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 21:12:32', NULL);
INSERT INTO `operation_log` VALUES (33, '角色管理模块', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"user\",\"name\":\"用户\",\"resourceIdList\":[18,91,92,95],\"roleId\":2}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 21:14:45', NULL);
INSERT INTO `operation_log` VALUES (34, '角色管理模块', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"user\",\"menuIdList\":[1,8],\"name\":\"用户\",\"roleId\":2}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 21:15:32', NULL);
INSERT INTO `operation_log` VALUES (35, '角色管理模块', '新增或修改', '/admin/saveOrUpdateRole', 'com.lee.blog.controller.RoleController.saveOrUpdateRole', '保存或更新角色信息', '[{\"label\":\"test\",\"menuIdList\":[1,2,9,10,11,12,13,3,14,15,4,16,17,6,21,22,23,24,7,25,8],\"name\":\"测试\",\"roleId\":3}]', 'POST', '{\"code\":200,\"message\":\"操作成功\",\"status\":true}', 2, '管理员', '127.0.0.1', '', '2021-09-26 21:16:21', NULL);

-- ----------------------------
-- Table structure for page
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '页面id',
  `page_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面名',
  `page_label` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面标签',
  `page_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面封面',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of page
-- ----------------------------
INSERT INTO `page` VALUES (1, '首页', 'home', 'https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/config/223e92ee2b97ecc66cbe6d0d743a4d59.jpeg', '2021-09-23 10:58:17', NULL);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `resource_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源权限名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限路径',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父权限id',
  `anonymous` tinyint(1) NOT NULL DEFAULT 0 COMMENT '匿名访问 0否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, '博客模块', NULL, NULL, NULL, 0, '2021-09-23 10:19:37', NULL);
INSERT INTO `resource` VALUES (4, '后台查看博客', '/admin/blogs', 'GET', 1, 0, '2021-09-23 10:42:45', '2021-09-24 22:04:14');
INSERT INTO `resource` VALUES (6, '添加或更新博客', '/admin/saveOrUpdateBlog', 'POST', 1, 0, '2021-09-26 19:15:07', NULL);
INSERT INTO `resource` VALUES (7, '逻辑删除博客', '/admin/restoreOrDeleteBlog', 'PUT', 1, 0, '2021-09-26 19:15:45', NULL);
INSERT INTO `resource` VALUES (8, '物理删除博客', '/admin/deleteBlogs', 'DELETE', 1, 0, '2021-09-26 19:17:17', NULL);
INSERT INTO `resource` VALUES (9, '上传博客图片', '/admin/blogs/images', 'POST', 1, 0, '2021-09-26 19:18:02', NULL);
INSERT INTO `resource` VALUES (10, '修改博客推荐置顶状态', '/admin/blog/recommend', 'PUT', 1, 0, '2021-09-26 19:18:39', NULL);
INSERT INTO `resource` VALUES (11, 'admin根据id查询博客', '/admin/blogs/*', 'GET', 1, 0, '2021-09-26 19:19:24', NULL);
INSERT INTO `resource` VALUES (12, 'admin查询博客列表', '/admin/listBlogs', 'GET', 1, 0, '2021-09-26 19:20:15', NULL);
INSERT INTO `resource` VALUES (13, '获取博客分类列表', '/admin/categories/search', 'GET', 1, 0, '2021-09-26 19:20:56', NULL);
INSERT INTO `resource` VALUES (14, '获取博客标签列表', '/admin/tags/search', 'GET', 1, 0, '2021-09-26 19:21:10', NULL);
INSERT INTO `resource` VALUES (15, '根据id查询博客信息', '/blogs/*', 'GET', 1, 1, '2021-09-26 19:21:54', '2021-09-26 20:59:56');
INSERT INTO `resource` VALUES (16, '搜索博客', '/blogs/search', 'GET', 1, 1, '2021-09-26 19:22:17', '2021-09-26 20:59:49');
INSERT INTO `resource` VALUES (17, '查询首页博客', '/blogs', 'GET', 1, 1, '2021-09-26 19:22:34', '2021-09-26 20:59:31');
INSERT INTO `resource` VALUES (18, '点赞博客', '/blogs/*/like', 'POST', 1, 0, '2021-09-26 19:22:59', NULL);
INSERT INTO `resource` VALUES (19, '查看博客归档', '/blogs/archives', 'GET', 1, 1, '2021-09-26 19:23:25', '2021-09-26 20:59:38');
INSERT INTO `resource` VALUES (20, '根据条件查询博客', '/blogs/condition', 'GET', 1, 1, '2021-09-26 19:23:41', '2021-09-26 20:59:42');
INSERT INTO `resource` VALUES (21, '博客前后台信息管理模块', NULL, NULL, NULL, 0, '2021-09-26 19:24:44', NULL);
INSERT INTO `resource` VALUES (22, '查询后台信息', '/admin', 'GET', 21, 0, '2021-09-26 19:25:05', NULL);
INSERT INTO `resource` VALUES (23, '获取用户区域分布', '/admin/user/area', 'GET', 21, 0, '2021-09-26 19:25:35', NULL);
INSERT INTO `resource` VALUES (24, '上传博客配置图片', '/admin/config/images', 'POST', 21, 0, '2021-09-26 19:25:59', NULL);
INSERT INTO `resource` VALUES (25, '上传访客信息', '/report', 'POST', 21, 1, '2021-09-26 19:26:15', NULL);
INSERT INTO `resource` VALUES (26, '查询博客首页信息', '/', 'GET', 21, 1, '2021-09-26 19:26:33', NULL);
INSERT INTO `resource` VALUES (27, '验证码模块', NULL, NULL, NULL, 0, '2021-09-26 19:27:17', NULL);
INSERT INTO `resource` VALUES (28, '获取验证码图片', '/captcha', 'GET', 27, 1, '2021-09-26 19:27:38', NULL);
INSERT INTO `resource` VALUES (29, '分类模块', NULL, NULL, NULL, 0, '2021-09-26 19:28:11', NULL);
INSERT INTO `resource` VALUES (30, 'admin查询分类列表', '/admin/categories', 'GET', 29, 0, '2021-09-26 19:28:29', NULL);
INSERT INTO `resource` VALUES (31, '根据id删除分类', '/admin/deleteCategories', 'DELETE', 29, 0, '2021-09-26 19:28:51', NULL);
INSERT INTO `resource` VALUES (32, '保存或更新分类', '/admin/saveOrUpdateCategory', 'POST', 29, 0, '2021-09-26 19:29:18', NULL);
INSERT INTO `resource` VALUES (33, '前台查询分类列表', '/categories', 'GET', 29, 1, '2021-09-26 19:29:46', '2021-09-26 21:00:25');
INSERT INTO `resource` VALUES (34, '评论模块', NULL, NULL, NULL, 0, '2021-09-26 19:30:04', NULL);
INSERT INTO `resource` VALUES (35, '审核评论', '/admin/comments/review', 'PUT', 34, 0, '2021-09-26 19:30:21', NULL);
INSERT INTO `resource` VALUES (36, '删除评论', '/admin/deleteComments', 'DELETE', 34, 0, '2021-09-26 19:30:41', NULL);
INSERT INTO `resource` VALUES (37, 'admin查询评论列表', '/admin/listComments', 'GET', 34, 0, '2021-09-26 19:31:05', NULL);
INSERT INTO `resource` VALUES (38, '添加评论', '/saveComment', 'POST', 34, 0, '2021-09-26 19:31:20', NULL);
INSERT INTO `resource` VALUES (39, '查询评论下的回复', '/comments/*/replies', 'GET', 34, 1, '2021-09-26 19:31:44', '2021-09-26 21:04:40');
INSERT INTO `resource` VALUES (40, '点赞评论', '/comments/*/like', 'POST', 34, 0, '2021-09-26 19:32:15', NULL);
INSERT INTO `resource` VALUES (41, '前台查询评论列表', '/comments', 'GET', 34, 1, '2021-09-26 19:32:45', '2021-09-26 21:04:37');
INSERT INTO `resource` VALUES (42, '友链模块', NULL, NULL, NULL, 0, '2021-09-26 19:33:11', NULL);
INSERT INTO `resource` VALUES (43, '根据id删除友链', '/admin/deleteFriendLink', 'DELETE', 42, 0, '2021-09-26 19:33:34', NULL);
INSERT INTO `resource` VALUES (44, '保存或更新友链', '/admin/saveOrUpdateFriendLink', 'POST', 42, 0, '2021-09-26 19:34:11', NULL);
INSERT INTO `resource` VALUES (45, 'admin查询友链列表', '/admin/links', 'GET', 42, 0, '2021-09-26 19:34:35', NULL);
INSERT INTO `resource` VALUES (46, '修改友链展示状态', '/admin/changeLinkStatus', 'PUT', 42, 0, '2021-09-26 19:34:49', NULL);
INSERT INTO `resource` VALUES (47, '前台查询友链列表', '/links', 'GET', 42, 1, '2021-09-26 19:35:44', '2021-09-26 21:01:16');
INSERT INTO `resource` VALUES (48, '菜单管理模块', NULL, NULL, NULL, 0, '2021-09-26 19:36:03', NULL);
INSERT INTO `resource` VALUES (49, '查询当前用户菜单', '/admin/user/menus', 'GET', 48, 0, '2021-09-26 19:36:20', NULL);
INSERT INTO `resource` VALUES (50, '根据条件查询菜单列表', '/admin/menus', 'GET', 48, 0, '2021-09-26 19:36:49', NULL);
INSERT INTO `resource` VALUES (51, '新增或修改菜单', '/admin/saveOrUpdateMenu', 'POST', 48, 0, '2021-09-26 19:37:09', NULL);
INSERT INTO `resource` VALUES (52, '删除菜单', '/admin/menus/*', 'DELETE', 48, 0, '2021-09-26 19:37:59', NULL);
INSERT INTO `resource` VALUES (53, '修改菜单状态', '/admin/menus/status', 'PUT', 48, 0, '2021-09-26 19:39:15', NULL);
INSERT INTO `resource` VALUES (54, '留言模块', NULL, NULL, NULL, 0, '2021-09-26 19:39:40', NULL);
INSERT INTO `resource` VALUES (55, '删除留言', '/admin/deleteMessages', 'DELETE', 54, 0, '2021-09-26 19:39:59', NULL);
INSERT INTO `resource` VALUES (56, '审核留言', '/admin/messages/review', 'PUT', 54, 0, '2021-09-26 19:40:13', NULL);
INSERT INTO `resource` VALUES (57, '查询留言列表', '/admin/listMessages', 'GET', 54, 0, '2021-09-26 19:40:27', NULL);
INSERT INTO `resource` VALUES (58, '添加保存留言', '/saveMessage', 'POST', 54, 1, '2021-09-26 19:41:01', '2021-09-26 21:04:13');
INSERT INTO `resource` VALUES (59, '查看留言列表', '/listMessages', 'GET', 54, 1, '2021-09-26 19:41:59', '2021-09-26 21:04:16');
INSERT INTO `resource` VALUES (60, '日志模块', NULL, NULL, NULL, 0, '2021-09-26 19:42:11', NULL);
INSERT INTO `resource` VALUES (61, '查看操作日志', '/admin/operation/logs', 'GET', 60, 0, '2021-09-26 19:42:27', NULL);
INSERT INTO `resource` VALUES (62, '删除操作日志', '/admin/deleteOperation/logs', 'DELETE', 60, 0, '2021-09-26 19:42:40', NULL);
INSERT INTO `resource` VALUES (63, '页面管理模块', NULL, NULL, NULL, 0, '2021-09-26 19:42:53', '2021-09-26 19:43:14');
INSERT INTO `resource` VALUES (64, '获取页面列表', '/admin/listPages', 'GET', 63, 0, '2021-09-26 19:43:28', NULL);
INSERT INTO `resource` VALUES (65, '保存或更新页面', '/admin/saveOrUpdatePage', 'POST', 63, 0, '2021-09-26 19:43:42', NULL);
INSERT INTO `resource` VALUES (66, '删除页面', '/admin/deletePage/*', 'DELETE', 63, 0, '2021-09-26 19:44:06', NULL);
INSERT INTO `resource` VALUES (67, '资源管理模块', NULL, NULL, NULL, 0, '2021-09-26 19:44:29', NULL);
INSERT INTO `resource` VALUES (68, '查询资源模块列表', '/admin/resources', 'GET', 67, 0, '2021-09-26 19:44:46', NULL);
INSERT INTO `resource` VALUES (69, '新增或修改资源模块', '/admin/saveOrUpdateResource', 'POST', 67, 0, '2021-09-26 19:51:29', NULL);
INSERT INTO `resource` VALUES (70, '删除资源模块', '/admin/resources/*', 'DELETE', 67, 0, '2021-09-26 19:51:40', NULL);
INSERT INTO `resource` VALUES (71, '角色管理模块', NULL, NULL, NULL, 0, '2021-09-26 19:53:45', NULL);
INSERT INTO `resource` VALUES (72, '查询角色列表', '/admin/roles', 'GET', 71, 0, '2021-09-26 19:53:55', NULL);
INSERT INTO `resource` VALUES (73, '查询角色资源权限列表', '/admin/role/resources', 'GET', 71, 0, '2021-09-26 19:55:07', NULL);
INSERT INTO `resource` VALUES (74, '查询角色菜单权限列表', '/admin/role/menus', 'GET', 71, 0, '2021-09-26 19:55:26', NULL);
INSERT INTO `resource` VALUES (75, '删除角色', '/admin/deleteRoles', 'DELETE', 71, 0, '2021-09-26 19:56:25', NULL);
INSERT INTO `resource` VALUES (76, '保存或更新角色信息', '/admin/saveOrUpdateRole', 'POST', 71, 0, '2021-09-26 19:56:39', NULL);
INSERT INTO `resource` VALUES (77, '查询用户角色选项', '/admin/users/role', 'GET', 71, 0, '2021-09-26 19:57:34', NULL);
INSERT INTO `resource` VALUES (78, '修改角色状态', '/admin/roles/status', 'PUT', 71, 0, '2021-09-26 19:57:38', NULL);
INSERT INTO `resource` VALUES (79, '标签模块', NULL, NULL, NULL, 0, '2021-09-26 19:58:42', NULL);
INSERT INTO `resource` VALUES (80, 'admin查询标签列表', '/admin/tags', 'GET', 79, 0, '2021-09-26 19:59:28', NULL);
INSERT INTO `resource` VALUES (81, '删除标签', '/admin/deleteTag', 'DELETE', 79, 0, '2021-09-26 20:00:00', NULL);
INSERT INTO `resource` VALUES (82, '添加或修改标签', '/admin/saveOrUpdateTag', 'POST', 79, 0, '2021-09-26 20:00:52', NULL);
INSERT INTO `resource` VALUES (83, '查询标签列表', '/tags', 'GET', 79, 1, '2021-09-26 20:01:15', '2021-09-26 21:01:36');
INSERT INTO `resource` VALUES (84, '用户管理模块', NULL, NULL, NULL, 0, '2021-09-26 20:02:03', NULL);
INSERT INTO `resource` VALUES (85, '修改用户角色', '/admin/users/role', 'PUT', 84, 0, '2021-09-26 20:03:02', NULL);
INSERT INTO `resource` VALUES (86, '修改用户状态', '/admin/users/status', 'PUT', 84, 0, '2021-09-26 20:03:34', NULL);
INSERT INTO `resource` VALUES (87, '查询后台用户列表', '/admin/listUsers', 'GET', 84, 0, '2021-09-26 20:04:09', NULL);
INSERT INTO `resource` VALUES (88, '查看在线用户', '/admin/users/online', 'GET', 84, 0, '2021-09-26 20:04:38', NULL);
INSERT INTO `resource` VALUES (89, '下线用户', '/admin/users/*/online', 'DELETE', 84, 0, '2021-09-26 20:05:14', NULL);
INSERT INTO `resource` VALUES (90, 'admin修改密码', '/admin/users/password', 'PUT', 84, 0, '2021-09-26 20:05:41', NULL);
INSERT INTO `resource` VALUES (91, '更新用户信息', '/users/info', 'PUT', 84, 0, '2021-09-26 20:06:03', NULL);
INSERT INTO `resource` VALUES (92, '更新用户头像', '/users/avatar', 'POST', 84, 0, '2021-09-26 20:06:27', NULL);
INSERT INTO `resource` VALUES (93, '微博登录', '/users/oauth/weibo', 'POST', 84, 1, '2021-09-26 20:06:54', '2021-09-26 21:02:21');
INSERT INTO `resource` VALUES (94, '发送邮箱验证码', '/users/code', 'GET', 84, 1, '2021-09-26 20:07:20', '2021-09-26 21:02:28');
INSERT INTO `resource` VALUES (95, '绑定用户邮箱', '/users/email', 'POST', 84, 0, '2021-09-26 20:07:48', '2021-09-26 21:03:27');
INSERT INTO `resource` VALUES (96, '修改密码', '/users/password', 'PUT', 84, 1, '2021-09-26 20:08:11', '2021-09-26 21:02:15');
INSERT INTO `resource` VALUES (97, '用户注册', '/register', 'POST', 84, 1, '2021-09-26 20:08:40', '2021-09-26 21:02:17');
INSERT INTO `resource` VALUES (98, '网站配置模块', NULL, NULL, NULL, 0, '2021-09-26 20:09:26', NULL);
INSERT INTO `resource` VALUES (99, '获取网站配置', '/admin/website/getConfig', 'GET', 98, 0, '2021-09-26 20:10:24', NULL);
INSERT INTO `resource` VALUES (100, '更新网站配置', '/admin/website/updateConfig', 'PUT', 98, 0, '2021-09-26 20:10:54', NULL);
INSERT INTO `resource` VALUES (101, '修改关于我的信息', '/admin/about', 'PUT', 98, 0, '2021-09-26 20:11:39', NULL);
INSERT INTO `resource` VALUES (102, '查看关于我信息', '/about', 'GET', 98, 1, '2021-09-26 20:12:01', '2021-09-26 21:05:11');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `label` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态，0禁用、1可用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 'admin', 0, '2021-09-21 15:14:07', '2021-09-26 20:58:00');
INSERT INTO `role` VALUES (2, '用户', 'user', 0, '2021-09-21 15:14:31', '2021-09-26 21:15:32');
INSERT INTO `role` VALUES (3, '测试', 'test', 0, '2021-09-21 15:14:52', '2021-09-26 21:16:20');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1);
INSERT INTO `role_menu` VALUES (2, 1, 2);
INSERT INTO `role_menu` VALUES (3, 1, 3);
INSERT INTO `role_menu` VALUES (4, 1, 4);
INSERT INTO `role_menu` VALUES (5, 1, 5);
INSERT INTO `role_menu` VALUES (6, 1, 6);
INSERT INTO `role_menu` VALUES (7, 1, 7);
INSERT INTO `role_menu` VALUES (8, 1, 8);
INSERT INTO `role_menu` VALUES (9, 1, 9);
INSERT INTO `role_menu` VALUES (10, 1, 10);
INSERT INTO `role_menu` VALUES (11, 1, 11);
INSERT INTO `role_menu` VALUES (12, 1, 12);
INSERT INTO `role_menu` VALUES (13, 1, 13);
INSERT INTO `role_menu` VALUES (14, 1, 14);
INSERT INTO `role_menu` VALUES (15, 1, 15);
INSERT INTO `role_menu` VALUES (16, 1, 16);
INSERT INTO `role_menu` VALUES (17, 1, 17);
INSERT INTO `role_menu` VALUES (18, 1, 18);
INSERT INTO `role_menu` VALUES (19, 1, 19);
INSERT INTO `role_menu` VALUES (20, 1, 20);
INSERT INTO `role_menu` VALUES (21, 1, 21);
INSERT INTO `role_menu` VALUES (22, 1, 22);
INSERT INTO `role_menu` VALUES (23, 1, 23);
INSERT INTO `role_menu` VALUES (24, 1, 24);
INSERT INTO `role_menu` VALUES (25, 1, 25);
INSERT INTO `role_menu` VALUES (55, 2, 1);
INSERT INTO `role_menu` VALUES (56, 2, 8);
INSERT INTO `role_menu` VALUES (57, 3, 1);
INSERT INTO `role_menu` VALUES (58, 3, 2);
INSERT INTO `role_menu` VALUES (59, 3, 9);
INSERT INTO `role_menu` VALUES (60, 3, 10);
INSERT INTO `role_menu` VALUES (61, 3, 11);
INSERT INTO `role_menu` VALUES (62, 3, 12);
INSERT INTO `role_menu` VALUES (63, 3, 13);
INSERT INTO `role_menu` VALUES (64, 3, 3);
INSERT INTO `role_menu` VALUES (65, 3, 14);
INSERT INTO `role_menu` VALUES (66, 3, 15);
INSERT INTO `role_menu` VALUES (67, 3, 4);
INSERT INTO `role_menu` VALUES (68, 3, 16);
INSERT INTO `role_menu` VALUES (69, 3, 17);
INSERT INTO `role_menu` VALUES (70, 3, 6);
INSERT INTO `role_menu` VALUES (71, 3, 21);
INSERT INTO `role_menu` VALUES (72, 3, 22);
INSERT INTO `role_menu` VALUES (73, 3, 23);
INSERT INTO `role_menu` VALUES (74, 3, 24);
INSERT INTO `role_menu` VALUES (75, 3, 7);
INSERT INTO `role_menu` VALUES (76, 3, 25);
INSERT INTO `role_menu` VALUES (77, 3, 8);

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` bigint(20) NULL DEFAULT NULL COMMENT '资源权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 323 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES (196, 1, 1);
INSERT INTO `role_resource` VALUES (197, 1, 4);
INSERT INTO `role_resource` VALUES (198, 1, 6);
INSERT INTO `role_resource` VALUES (199, 1, 7);
INSERT INTO `role_resource` VALUES (200, 1, 8);
INSERT INTO `role_resource` VALUES (201, 1, 9);
INSERT INTO `role_resource` VALUES (202, 1, 10);
INSERT INTO `role_resource` VALUES (203, 1, 11);
INSERT INTO `role_resource` VALUES (204, 1, 12);
INSERT INTO `role_resource` VALUES (205, 1, 13);
INSERT INTO `role_resource` VALUES (206, 1, 14);
INSERT INTO `role_resource` VALUES (207, 1, 15);
INSERT INTO `role_resource` VALUES (208, 1, 16);
INSERT INTO `role_resource` VALUES (209, 1, 17);
INSERT INTO `role_resource` VALUES (210, 1, 18);
INSERT INTO `role_resource` VALUES (211, 1, 19);
INSERT INTO `role_resource` VALUES (212, 1, 20);
INSERT INTO `role_resource` VALUES (213, 1, 21);
INSERT INTO `role_resource` VALUES (214, 1, 22);
INSERT INTO `role_resource` VALUES (215, 1, 23);
INSERT INTO `role_resource` VALUES (216, 1, 24);
INSERT INTO `role_resource` VALUES (217, 1, 27);
INSERT INTO `role_resource` VALUES (218, 1, 29);
INSERT INTO `role_resource` VALUES (219, 1, 30);
INSERT INTO `role_resource` VALUES (220, 1, 31);
INSERT INTO `role_resource` VALUES (221, 1, 32);
INSERT INTO `role_resource` VALUES (222, 1, 33);
INSERT INTO `role_resource` VALUES (223, 1, 34);
INSERT INTO `role_resource` VALUES (224, 1, 35);
INSERT INTO `role_resource` VALUES (225, 1, 36);
INSERT INTO `role_resource` VALUES (226, 1, 37);
INSERT INTO `role_resource` VALUES (227, 1, 38);
INSERT INTO `role_resource` VALUES (228, 1, 39);
INSERT INTO `role_resource` VALUES (229, 1, 40);
INSERT INTO `role_resource` VALUES (230, 1, 41);
INSERT INTO `role_resource` VALUES (231, 1, 42);
INSERT INTO `role_resource` VALUES (232, 1, 43);
INSERT INTO `role_resource` VALUES (233, 1, 44);
INSERT INTO `role_resource` VALUES (234, 1, 45);
INSERT INTO `role_resource` VALUES (235, 1, 46);
INSERT INTO `role_resource` VALUES (236, 1, 47);
INSERT INTO `role_resource` VALUES (237, 1, 48);
INSERT INTO `role_resource` VALUES (238, 1, 49);
INSERT INTO `role_resource` VALUES (239, 1, 50);
INSERT INTO `role_resource` VALUES (240, 1, 51);
INSERT INTO `role_resource` VALUES (241, 1, 52);
INSERT INTO `role_resource` VALUES (242, 1, 53);
INSERT INTO `role_resource` VALUES (243, 1, 54);
INSERT INTO `role_resource` VALUES (244, 1, 55);
INSERT INTO `role_resource` VALUES (245, 1, 56);
INSERT INTO `role_resource` VALUES (246, 1, 57);
INSERT INTO `role_resource` VALUES (247, 1, 58);
INSERT INTO `role_resource` VALUES (248, 1, 59);
INSERT INTO `role_resource` VALUES (249, 1, 60);
INSERT INTO `role_resource` VALUES (250, 1, 61);
INSERT INTO `role_resource` VALUES (251, 1, 62);
INSERT INTO `role_resource` VALUES (252, 1, 63);
INSERT INTO `role_resource` VALUES (253, 1, 64);
INSERT INTO `role_resource` VALUES (254, 1, 65);
INSERT INTO `role_resource` VALUES (255, 1, 66);
INSERT INTO `role_resource` VALUES (256, 1, 67);
INSERT INTO `role_resource` VALUES (257, 1, 68);
INSERT INTO `role_resource` VALUES (258, 1, 69);
INSERT INTO `role_resource` VALUES (259, 1, 70);
INSERT INTO `role_resource` VALUES (260, 1, 71);
INSERT INTO `role_resource` VALUES (261, 1, 72);
INSERT INTO `role_resource` VALUES (262, 1, 73);
INSERT INTO `role_resource` VALUES (263, 1, 74);
INSERT INTO `role_resource` VALUES (264, 1, 75);
INSERT INTO `role_resource` VALUES (265, 1, 76);
INSERT INTO `role_resource` VALUES (266, 1, 77);
INSERT INTO `role_resource` VALUES (267, 1, 78);
INSERT INTO `role_resource` VALUES (268, 1, 79);
INSERT INTO `role_resource` VALUES (269, 1, 80);
INSERT INTO `role_resource` VALUES (270, 1, 81);
INSERT INTO `role_resource` VALUES (271, 1, 82);
INSERT INTO `role_resource` VALUES (272, 1, 83);
INSERT INTO `role_resource` VALUES (273, 1, 84);
INSERT INTO `role_resource` VALUES (274, 1, 85);
INSERT INTO `role_resource` VALUES (275, 1, 86);
INSERT INTO `role_resource` VALUES (276, 1, 87);
INSERT INTO `role_resource` VALUES (277, 1, 88);
INSERT INTO `role_resource` VALUES (278, 1, 89);
INSERT INTO `role_resource` VALUES (279, 1, 90);
INSERT INTO `role_resource` VALUES (280, 1, 91);
INSERT INTO `role_resource` VALUES (281, 1, 92);
INSERT INTO `role_resource` VALUES (282, 1, 93);
INSERT INTO `role_resource` VALUES (283, 1, 94);
INSERT INTO `role_resource` VALUES (284, 1, 95);
INSERT INTO `role_resource` VALUES (285, 1, 96);
INSERT INTO `role_resource` VALUES (286, 1, 97);
INSERT INTO `role_resource` VALUES (287, 1, 98);
INSERT INTO `role_resource` VALUES (288, 1, 99);
INSERT INTO `role_resource` VALUES (289, 1, 100);
INSERT INTO `role_resource` VALUES (290, 1, 101);
INSERT INTO `role_resource` VALUES (291, 1, 102);
INSERT INTO `role_resource` VALUES (292, 3, 4);
INSERT INTO `role_resource` VALUES (293, 3, 11);
INSERT INTO `role_resource` VALUES (294, 3, 12);
INSERT INTO `role_resource` VALUES (295, 3, 13);
INSERT INTO `role_resource` VALUES (296, 3, 14);
INSERT INTO `role_resource` VALUES (297, 3, 18);
INSERT INTO `role_resource` VALUES (298, 3, 22);
INSERT INTO `role_resource` VALUES (299, 3, 23);
INSERT INTO `role_resource` VALUES (300, 3, 27);
INSERT INTO `role_resource` VALUES (301, 3, 30);
INSERT INTO `role_resource` VALUES (302, 3, 37);
INSERT INTO `role_resource` VALUES (303, 3, 40);
INSERT INTO `role_resource` VALUES (304, 3, 45);
INSERT INTO `role_resource` VALUES (305, 3, 49);
INSERT INTO `role_resource` VALUES (306, 3, 50);
INSERT INTO `role_resource` VALUES (307, 3, 57);
INSERT INTO `role_resource` VALUES (308, 3, 61);
INSERT INTO `role_resource` VALUES (309, 3, 64);
INSERT INTO `role_resource` VALUES (310, 3, 68);
INSERT INTO `role_resource` VALUES (311, 3, 72);
INSERT INTO `role_resource` VALUES (312, 3, 73);
INSERT INTO `role_resource` VALUES (313, 3, 74);
INSERT INTO `role_resource` VALUES (314, 3, 77);
INSERT INTO `role_resource` VALUES (315, 3, 80);
INSERT INTO `role_resource` VALUES (316, 3, 87);
INSERT INTO `role_resource` VALUES (317, 3, 88);
INSERT INTO `role_resource` VALUES (318, 3, 99);
INSERT INTO `role_resource` VALUES (319, 2, 18);
INSERT INTO `role_resource` VALUES (320, 2, 91);
INSERT INTO `role_resource` VALUES (321, 2, 92);
INSERT INTO `role_resource` VALUES (322, 2, 95);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'Linux', '2021-09-23 10:00:24', NULL);
INSERT INTO `tag` VALUES (2, 'Java', '2021-09-23 10:00:40', NULL);
INSERT INTO `tag` VALUES (3, 'MySql', '2021-09-23 10:00:49', NULL);
INSERT INTO `tag` VALUES (5, 'Docker', '2021-09-23 11:54:19', NULL);
INSERT INTO `tag` VALUES (6, '测试标签', '2021-09-23 12:50:22', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户简介',
  `web_site` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人网站',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态，0禁用、1可用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `last_login` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `ip_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户登录ip',
  `ip_source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `login_type` tinyint(1) NOT NULL COMMENT '登录类型 1邮箱 2QQ 3微博',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', '$2a$10$vlZt45lgxctiDMRSM5GWnOoBmqtGuBA0fLBgvEodQIUvhcE4OpFMK', 'test@qq.com', '测试', '111', NULL, NULL, 1, '2021-09-12 14:40:50', '2021-09-26 20:53:39', '2021-09-26 20:53:39', '127.0.0.1', '', 1);
INSERT INTO `user` VALUES (2, 'admin', '$2a$10$vlZt45lgxctiDMRSM5GWnOoBmqtGuBA0fLBgvEodQIUvhcE4OpFMK', NULL, '管理员', 'https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/avatar/2db7731ebe431a8526c232a29139e6aa.jpg', NULL, 'http://www.lovesky.top', 1, '2021-09-21 15:23:35', '2021-09-26 20:57:08', '2021-09-26 20:57:08', '127.0.0.1', '', 1);
INSERT INTO `user` VALUES (9, '1114862851@qq.com', '$2a$10$vlZt45lgxctiDMRSM5GWnOoBmqtGuBA0fLBgvEodQIUvhcE4OpFMK', '1114862851@qq.com', '叶沐秋', 'https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/avatar/93753c3469564c361b1c612f7c29d402.jpg', NULL, 'http://www.lovesky.top', 1, '2021-09-24 12:33:09', '2021-09-26 12:55:23', '2021-09-26 12:55:23', '127.0.0.1', '', 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 3);
INSERT INTO `user_role` VALUES (2, 2, 1);
INSERT INTO `user_role` VALUES (3, 3, 2);
INSERT INTO `user_role` VALUES (4, 5, 2);
INSERT INTO `user_role` VALUES (5, 7, 2);
INSERT INTO `user_role` VALUES (6, 9, 2);

-- ----------------------------
-- Table structure for views
-- ----------------------------
DROP TABLE IF EXISTS `views`;
CREATE TABLE `views`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `views_count` int(11) NOT NULL COMMENT '当日访问量',
  `total_visits` int(11) NULL DEFAULT NULL COMMENT '总访问量',
  `create_time` datetime NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of views
-- ----------------------------
INSERT INTO `views` VALUES (1, 0, 0, '2021-09-26 19:00:44');

-- ----------------------------
-- Table structure for website_config
-- ----------------------------
DROP TABLE IF EXISTS `website_config`;
CREATE TABLE `website_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of website_config
-- ----------------------------
INSERT INTO `website_config` VALUES (1, '{\"alipayQRCode\":\"https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/config/5a81982b14994aa1c4572932992f0be8.jpg\",\"gitee\":\"\",\"github\":\"https://github.com/Lee2580/myblog\",\"isCommentReview\":0,\"isEmailNotice\":1,\"isMessageReview\":0,\"isMusicPlayer\":1,\"isReward\":1,\"qq\":\"1114862851\",\"socialLoginList\":[\"weibo\"],\"socialUrlList\":[\"github\",\"gitee\",\"qq\"],\"touristAvatar\":\"https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/config/ccd6d80e378ed6c92009a3013e8d20e5.jpeg\",\"websiteAuthor\":\"叶沐秋\",\"websiteAvatar\":\"https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/config/2db7731ebe431a8526c232a29139e6aa.jpg\",\"websiteCreateTime\":\"2021-09-22\",\"websiteIntro\":\"网站简介\",\"websiteName\":\"叶沐秋の个人博客\",\"websiteNotice\":\"\",\"websiteRecordNo\":\"备案号\",\"websocketUrl\":\"ws://127.0.0.1:8080/websocket\",\"weiXinQRCode\":\"https://lovesky-blog.oss-cn-hongkong.aliyuncs.com/config/a7e81d9aa8177a6536f143e0f1ee4825.png\"}', '2021-09-22 17:33:21', '2021-09-24 23:46:25');

SET FOREIGN_KEY_CHECKS = 1;
