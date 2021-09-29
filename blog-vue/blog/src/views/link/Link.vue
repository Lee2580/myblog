<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">友情链接</h1>
    </div>
    <!-- 链接列表 -->
    <v-card class="website-container">
      <div class="link-title mb-1">
        <v-icon color="blue">mdi-link-variant</v-icon> 大佬链接
      </div>
      <v-row class="link-container">
        <v-col
          class="link-wrapper"
          md="4"
          cols="12"
          v-for="item of friendLinkList"
          :key="item.id"
        >
          <a :href="item.linkAddress" target="_blank">
            <v-avatar size="65" class="link-avatar">
              <img :src="item.linkAvatar" />
            </v-avatar>
            <div style="width:100%;z-index:10;">
              <div class="link-name">{{ item.linkName }}</div>
              <div class="link-intro">{{ item.linkIntro }}</div>
            </div>
          </a>
        </v-col>
      </v-row>
      <!-- 说明 -->
      <div class="link-title mt-4 mb-4">
        <v-icon color="blue">mdi-dots-horizontal-circle</v-icon> 添加友链
      </div>
      <blockquote>
        <div>名称：{{ blogInfo.websiteConfig.websiteName }}</div>
        <div>简介：{{ blogInfo.websiteConfig.websiteIntro }}</div>
        <div>头像：{{ blogInfo.websiteConfig.websiteAvatar }}</div>
      </blockquote>
      <div class="mt-5 mb-5">
        需要交换友链的可在下方留言💖
      </div>
      <div class="link-title mt-4 mb-4">
        需要交换友链的可在此处填写表格💖
        <v-btn color="primary" @click="openAddLink">
          添加友链
        </v-btn>
      </div>
      <blockquote class="mb-10">
        友链信息展示需要，你的信息格式要包含：名称、介绍、链接、头像
      </blockquote>
      <!-- 评论 -->
      <Comment
        :commentList="commentList"
        :count="count"
        @reloadComment="listComments"
      />
    </v-card>
    <!-- 添加表单 -->
    <v-dialog title="添加友链" v-model="addLinkFlag" max-width="460">
      <v-card class="login-container" style="border-radius:4px">
        <v-icon class="float-right" @click="addLinkFlag = false">
          mdi-close
        </v-icon>
        <div class="login-wrapper">
          <v-text-field
              v-model="linkForm.linkName"
              label="友链名称"
              placeholder="请输入您的链接名称"
              clearable
              @keyup.enter="updateLink"
          />
          <v-text-field
              v-model="linkForm.linkAvatar"
              label="头像地址"
              placeholder="请输入您的头像地址"
              clearable
              @keyup.enter="updateLink"
          />
          <v-text-field
            v-model="linkForm.linkAddress"
            label="链接地址"
            placeholder="请输入您的链接地址"
            clearable
            @keyup.enter="updateLink"
        />
          <v-text-field
            v-model="linkForm.linkIntro"
            label="链接介绍"
            placeholder="请输入您的链接介绍"
            clearable
            @keyup.enter="updateLink"
        />
          <!-- 按钮 -->
          <v-row
              align="center"
              justify="space-around"
          >
            <v-btn @click="addLinkFlag = false">取消</v-btn>
            <v-btn
                color="primary"
                @click="updateLink"
            >
              添加
            </v-btn>
          </v-row>
        </div>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import Comment from "../../components/Comment";
export default {
  components: {
    Comment
  },
  created() {
    this.listFriendLink();
    this.listComments();
  },
  //
  data: function() {
    return {
      friendLinkList: [],
      commentList: [],
      count: 0,
      linkForm: {
        id: null,
        linkName: "",
        linkAvatar: "",
        linkIntro: "",
        linkAddress: "",
        linkStatus: 0
      }, // 表单数据
      addLinkFlag: false // 增加表单是否可见
    };
  },
  methods: {
    openAddLink() {
      //判断登录
      if (!this.$store.state.userId) {
        this.$store.state.loginFlag = true;
        return false;
      }
      console.log("进入openAddLink")
      this.linkForm.id = null;
      this.linkForm.linkName = "";
      this.linkForm.linkAvatar = "";
      this.linkForm.linkIntro = "";
      this.linkForm.linkAddress = "";
      this.addLinkFlag = true;
    },
    updateLink() {
      console.log("进入updateLink")
      console.log(this.linkForm)
      if (this.linkForm.linkName.trim() == "") {
        this.$toast({ type: "error", message: "友链名不能为空" });
        return false;
      }
      if (this.linkForm.linkAvatar.trim() == "") {
        this.$toast({ type: "error", message: "头像地址不能为空" });
        return false;
      }
      if (this.linkForm.linkAddress.trim() == "") {
        this.$toast({ type: "error", message: "链接地址不能为空" });
        return false;
      }
      if (this.linkForm.linkIntro.trim() == "") {
        this.$toast({ type: "error", message: "链接介绍不能为空" });
        return false;
      }
      this.axios.post("/api/admin/saveOrUpdateFriendLink", this.linkForm).then(({ data }) => {
        if (data.status) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listLinks();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.addLinkFlag = false;
      });
    },
    listFriendLink() {
      this.axios.get("/api/links").then(({ data }) => {
        this.friendLinkList = data.data;
      });
    },
    listComments() {
      this.axios
        .get("/api/comments", {
          params: { current: 1 }
        })
        .then(({ data }) => {
          this.commentList = data.data.recordList;
          this.count = data.data.count;
        });
    }
  },
  computed: {
    blogInfo() {
      return this.$store.state.blogInfo;
    },
    cover() {
      let cover = "";
      this.$store.state.blogInfo.pageList.forEach(item => {
        if (item.pageLabel == "link") {
          cover = item.pageCover;
        }
      });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
blockquote {
  line-height: 2;
  margin: 0;
  font-size: 15px;
  border-left: 0.2rem solid #49b1f5;
  padding: 10px 1rem !important;
  background-color: #ecf7fe;
  border-radius: 4px;
}
.link-title {
  color: #344c67;
  font-size: 21px;
  font-weight: bold;
  line-height: 2;
}
.link-container {
  margin: 10px 10px 0;
}
.link-wrapper {
  position: relative;
  transition: all 0.3s;
  border-radius: 8px;
}
.link-avatar {
  margin-top: 5px;
  margin-left: 10px;
  transition: all 0.5s;
}
@media (max-width: 759px) {
  .link-avatar {
    margin-left: 30px;
  }
}
.link-name {
  text-align: center;
  font-size: 1.25rem;
  font-weight: bold;
  z-index: 1000;
}
.link-intro {
  text-align: center;
  padding: 16px 10px;
  height: 50px;
  font-size: 13px;
  color: #1f2d3d;
  width: 100%;
}
.link-wrapper:hover a {
  color: #fff;
}
.link-wrapper:hover .link-intro {
  color: #fff;
}
.link-wrapper:hover .link-avatar {
  transform: rotate(360deg);
}
.link-wrapper a {
  color: #333;
  text-decoration: none;
  display: flex;
  height: 100%;
  width: 100%;
}
.link-wrapper:hover {
  box-shadow: 0 2px 20px #49b1f5;
}
.link-wrapper:hover:before {
  transform: scale(1);
}
.link-wrapper:before {
  position: absolute;
  border-radius: 8px;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background: #49b1f5 !important;
  content: "";
  transition-timing-function: ease-out;
  transition-duration: 0.3s;
  transition-property: transform;
  transform: scale(0);
}
</style>
