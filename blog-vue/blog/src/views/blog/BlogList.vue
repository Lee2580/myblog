<template>
  <div>
    <!-- 标签或分类名 -->
    <div class="banner" :style="cover">
      <h1 class="banner-title animated fadeInDown">{{ title }} - {{ name }}</h1>
    </div>
    <div class="blog-list-wrapper">
      <v-row>
        <v-col md="4" cols="12" v-for="item of blogList" :key="item.blogId">
          <!-- 文章 -->
          <v-card class="animated zoomIn blog-item-card">
            <div class="blog-item-cover">
              <router-link :to="'/blogs/' + item.blogId">
                <!-- 缩略图 -->
                <v-img
                  class="on-hover"
                  width="100%"
                  height="100%"
                  :src="item.firstPicture"
                />
              </router-link>
            </div>
            <div class="blog-item-info">
              <!-- 文章标题 -->
              <div>
                <router-link :to="'/blogs/' + item.blogId">
                  {{ item.title }}
                </router-link>
              </div>
              <div style="margin-top:0.375rem">
                <!-- 发表时间 -->
                <v-icon size="20">mdi-clock-outline</v-icon>
                {{ item.createTime | date }}
                <!-- 文章分类 -->
                <router-link
                  :to="'/categories/' + item.categoryId"
                  class="float-right"
                >
                  <v-icon>mdi-bookmark</v-icon>{{ item.categoryName }}
                </router-link>
              </div>
            </div>
            <!-- 分割线 -->
            <v-divider></v-divider>
            <!-- 文章标签 -->
            <div class="tag-wrapper">
              <router-link
                :to="'/tags/' + tag.tagId"
                class="tag-btn"
                v-for="tag of item.tagDTOList"
                :key="tag.tagId"
              >
                {{ tag.tagName }}
              </router-link>
            </div>
          </v-card>
        </v-col>
      </v-row>
      <!-- 无限加载 -->
      <infinite-loading @infinite="infiniteHandler">
        <div slot="no-results" />
        <div slot="no-more" />
      </infinite-loading>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    const path = this.$route.path;
    if (path.indexOf("/categories") != -1) {
      this.title = "分类";
    } else {
      this.title = "标签";
    }
  },
  data: function() {
    return {
      current: 1,
      size: 10,
      blogList: [],
      name: "",
      title: ""
    };
  },
  methods: {
    infiniteHandler($state) {
      this.axios
        .get("/api/blogs/condition", {
          params: {
            categoryId: this.$route.params.categoryId,
            tagId: this.$route.params.tagId,
            current: this.current
          }
        })
        .then(({ data }) => {
          if (data.data.blogPreviewDTOList.length) {
            this.current++;
            this.name = data.data.name;
            document.title = this.title + " - " + this.name;
            this.blogList.push(...data.data.blogPreviewDTOList);
            $state.loaded();
          } else {
            $state.complete();
          }
        });
    }
  },
  computed: {
    cover() {
      let cover = "";
      this.$store.state.blogInfo.pageList.forEach(item => {
        if (item.pageLabel == "blogList") {
          cover = item.pageCover;
        }
      });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
@media (min-width: 760px) {
  .blog-list-wrapper {
    max-width: 1106px;
    margin: 370px auto 1rem auto;
  }
  .blog-item-card:hover {
    transition: all 0.3s;
    box-shadow: 0 4px 12px 12px rgba(7, 17, 27, 0.15);
  }
  .blog-item-card:not(:hover) {
    transition: all 0.3s;
  }
  .blog-item-card:hover .on-hover {
    transition: all 0.6s;
    transform: scale(1.1);
  }
  .blog-item-card:not(:hover) .on-hover {
    transition: all 0.6s;
  }
  .blog-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
    font-size: 15px;
  }
}
@media (max-width: 759px) {
  .blog-list-wrapper {
    margin-top: 230px;
    padding: 0 12px;
  }
  .blog-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
  }
}
.blog-item-card {
  border-radius: 8px !important;
  box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 0.06);
}
.blog-item-card a {
  transition: all 0.3s;
}
.blog-item-cover {
  height: 220px;
  overflow: hidden;
}
.blog-item-card a:hover {
  color: #8e8cd8;
}
.tag-wrapper {
  padding: 10px 15px 10px 18px;
}
.tag-wrapper a {
  color: #fff !important;
}
.tag-btn {
  display: inline-block;
  font-size: 0.725rem;
  line-height: 22px;
  height: 22px;
  border-radius: 10px;
  padding: 0 12px !important;
  background: linear-gradient(to right, #bf4643 0%, #6c9d8f 100%);
  opacity: 0.6;
  margin-right: 0.5rem;
}
</style>
