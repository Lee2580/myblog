<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 文章标题 -->
    <div class="blog-title-container">
      <el-input
        v-model="blog.title"
        size="medium"
        placeholder="输入文章标题"
      />
      <el-button
        type="danger"
        size="medium"
        class="save-btn"
        @click="saveBlogDraft"
        v-if="blog.blogId == null || blog.status == 3"
      >
        保存草稿
      </el-button>
      <el-button
        type="danger"
        size="medium"
        @click="openModel"
        style="margin-left:10px"
      >
        发布文章
      </el-button>
    </div>
    <!-- 文章内容 -->
    <mavon-editor
      ref="md"
      v-model="blog.content"
      @imgAdd="uploadImg"
      style="height:calc(100vh - 260px)"
    />
    <!-- 添加文章对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="40%" top="3vh">
      <div class="dialog-title-container" slot="title">
        发布文章
      </div>
      <!-- 文章数据 -->
      <el-form label-width="80px" size="medium" :model="blog">
        <!-- 文章分类 -->
        <el-form-item label="文章分类">
          <el-tag
            type="success"
            v-show="blog.categoryName"
            style="margin:0 1rem 0 0"
            :closable="true"
            @close="removeCategory"
          >
            {{ blog.categoryName }}
          </el-tag>
          <!-- 分类选项 -->
          <el-popover
            placement="bottom-start"
            width="460"
            trigger="click"
            v-if="!blog.categoryName"
          >
            <div class="popover-title">分类</div>
            <!-- 搜索框 -->
            <el-autocomplete
              style="width:100%"
              v-model="categoryName"
              :fetch-suggestions="searchCategories"
              placeholder="请输入分类名搜索，enter可添加自定义分类"
              :trigger-on-focus="false"
              @keyup.enter.native="saveCategory"
              @select="handleSelectCategories"
            >
              <template slot-scope="{ item }">
                <div>{{ item.categoryName }}</div>
              </template>
            </el-autocomplete>
            <!-- 分类 -->
            <div class="popover-container">
              <div
                v-for="item of categoryList"
                :key="item.categoryId"
                class="category-item"
                @click="addCategory(item)"
              >
                {{ item.categoryName }}
              </div>
            </div>
            <el-button type="success" plain slot="reference" size="small">
              添加分类
            </el-button>
          </el-popover>
        </el-form-item>
        <!-- 文章标签 -->
        <el-form-item label="文章标签">
          <el-tag
            v-for="(item, index) of blog.tagNameList"
            :key="index"
            style="margin:0 1rem 0 0"
            :closable="true"
            @close="removeTag(item)"
          >
            {{ item }}
          </el-tag>
          <!-- 标签选项 -->
          <el-popover
            placement="bottom-start"
            width="460"
            trigger="click"
            v-if="blog.tagNameList.length < 3"
          >
            <div class="popover-title">标签</div>
            <!-- 搜索框 -->
            <el-autocomplete
              style="width:100%"
              v-model="tagName"
              :fetch-suggestions="searchTags"
              placeholder="请输入标签名搜索，enter可添加自定义标签"
              :trigger-on-focus="false"
              @keyup.enter.native="saveTag"
              @select="handleSelectTag"
            >
              <template slot-scope="{ item }">
                <div>{{ item.tagName }}</div>
              </template>
            </el-autocomplete>
            <!-- 标签 -->
            <div class="popover-container">
              <div style="margin-bottom:1rem">添加标签</div>
              <el-tag
                v-for="(item, index) of tagList"
                :key="index"
                :class="tagClass(item)"
                @click="addTag(item)"
              >
                {{ item.tagName }}
              </el-tag>
            </div>
            <el-button type="primary" plain slot="reference" size="small">
              添加标签
            </el-button>
          </el-popover>
        </el-form-item>
        <el-form-item label="文章类型">
          <el-select v-model="blog.blogType" placeholder="请选择类型">
            <el-option
              v-for="item in typeList"
              :key="item.blogType"
              :label="item.desc"
              :value="item.blogType"
            />
          </el-select>
        </el-form-item>
        <!-- 文章类型 -->
        <el-form-item label="原文地址" v-if="blog.blogType != 1">
          <el-input
            v-model="blog.originalUrl"
            placeholder="请填写原文链接"
          />
        </el-form-item>
        <el-form-item label="上传封面">
          <el-upload
            class="upload-cover"
            drag
            :show-file-list="false"
            action="/api/admin/blogs/images"
            multiple
            :on-success="uploadCover"
          >
            <i class="el-icon-upload" v-if="blog.firstPicture == ''" />
            <div class="el-upload__text" v-if="blog.firstPicture == ''">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <img
              v-else
              :src="blog.firstPicture"
              width="360px"
              height="180px"
            />
          </el-upload>
        </el-form-item>
        <el-form-item label="置顶">
          <el-switch
            v-model="blog.recommend"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
        <el-form-item label="发布形式">
          <el-radio-group v-model="blog.status">
            <el-radio :label="1">公开</el-radio>
            <el-radio :label="2">私密</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="danger" @click="saveOrUpdateBlog">
          发 表
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  created() {
    const path = this.$route.path;
    const arr = path.split("/");
    const blogId = arr[2];
    if (blogId) {
      this.axios.get("/api/admin/blogs/" + blogId).then(({ data }) => {
        this.blog = data.data;
      });
    } else {
      const blog = sessionStorage.getItem("blog");
      if (blog) {
        this.blog = JSON.parse(blog);
      }
    }
  },
  destroyed() {
    //文章自动保存功能
    this.autoSaveBlog();
  },
  data: function() {
    return {
      addOrEdit: false,
      autoSave: true,
      categoryName: "",
      tagName: "",
      categoryList: [],
      tagList: [],
      typeList: [
        {
          blogType: 1,
          desc: "原创"
        },
        {
          blogType: 2,
          desc: "转载"
        },
        {
          blogType: 3,
          desc: "翻译"
        }
      ],
      blog: {
        blogId: null,
        title: this.$moment(new Date()).format("YYYY-MM-DD"),
        content: "",
        firstPicture: "",
        categoryName: null,
        tagNameList: [],
        originalUrl: "",
        recommend: 0,
        blogType: 1,
        status: 1
      }
    };
  },
  methods: {
    listCategories() {
      this.axios.get("/api/admin/categories/search").then(({ data }) => {
        this.categoryList = data.data;
      });
    },
    listTags() {
      this.axios.get("/api/admin/tags/search").then(({ data }) => {
        this.tagList = data.data;
      });
    },
    openModel() {
      if (this.blog.title.trim() == "") {
        this.$message.error("文章标题不能为空");
        return false;
      }
      if (this.blog.content.trim() == "") {
        this.$message.error("文章内容不能为空");
        return false;
      }
      this.listCategories();
      this.listTags();
      this.addOrEdit = true;
    },
    uploadCover(response) {
      this.blog.firstPicture = response.data;
    },
    uploadImg(pos, file) {
      let formData = new FormData();
      formData.append("file", file);
      this.axios
        .post("/api/admin/blogs/images", formData)
        .then(({ data }) => {
          this.$refs.md.$img2Url(pos, data.data);
        });
    },
    saveBlogDraft() {
      if (this.blog.title.trim() == "") {
        this.$message.error("文章标题不能为空");
        return false;
      }
      if (this.blog.content.trim() == "") {
        this.$message.error("文章内容不能为空");
        return false;
      }
      this.blog.status = 3;
      this.axios.post("/api/admin/saveOrUpdateBlog", this.blog).then(({ data }) => {
        if (data.status) {
          this.$notify.success({
            title: "成功",
            message: "保存草稿成功"
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: "保存草稿失败"
          });
        }
      });
      //关闭自动保存功能
      this.autoSave = false;
    },
    saveOrUpdateBlog() {
      if (this.blog.title.trim() == "") {
        this.$message.error("文章标题不能为空");
        return false;
      }
      if (this.blog.content.trim() == "") {
        this.$message.error("文章内容不能为空");
        return false;
      }
      if (this.blog.categoryName == null) {
        this.$message.error("文章分类不能为空");
        return false;
      }
      if (this.blog.tagNameList.length == 0) {
        this.$message.error("文章标签不能为空");
        return false;
      }
      if (this.blog.firstPicture.trim() == "") {
        this.$message.error("文章封面不能为空");
        return false;
      }
      this.axios.post("/api/admin/saveOrUpdateBlog", this.blog).then(({ data }) => {
        if (data.status) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.addOrEdit = false;
      });
      //关闭自动保存功能
      this.autoSave = false;
    },
    autoSaveBlog() {
      // 自动上传文章
      if (
        this.autoSave &&
        this.blog.title.trim() != "" &&
        this.blog.content.trim() != "" &&
        this.blog.blogId != null
      ) {
        this.axios
          .post("/api/admin/saveOrUpdateBlog", this.blog)
          .then(({ data }) => {
            if (data.status) {
              this.$notify.success({
                title: "成功",
                message: "自动保存成功"
              });
            } else {
              this.$notify.error({
                title: "失败",
                message: "自动保存失败"
              });
            }
          });
      }
      // 保存本地文章记录
      if (this.autoSave && this.blog.blogId == null) {
        sessionStorage.setItem("blog", JSON.stringify(this.blog));
      }
    },
    searchCategories(keywords, cb) {
      this.axios
        .get("/api/admin/categories/search", {
          params: {
            keywords: keywords
          }
        })
        .then(({ data }) => {
          cb(data.data);
        });
    },
    handleSelectCategories(item) {
      this.addCategory({
        categoryName: item.categoryName
      });
    },
    saveCategory() {
      if (this.categoryName.trim() != "") {
        this.addCategory({
          categoryName: this.categoryName
        });
        this.categoryName = "";
      }
    },
    addCategory(item) {
      this.blog.categoryName = item.categoryName;
    },
    removeCategory() {
      this.blog.categoryName = null;
    },
    searchTags(keywords, cb) {
      this.axios
        .get("/api/admin/tags/search", {
          params: {
            keywords: keywords
          }
        })
        .then(({ data }) => {
          cb(data.data);
        });
    },
    handleSelectTag(item) {
      this.addTag({
        tagName: item.tagName
      });
    },
    saveTag() {
      if (this.tagName.trim() != "") {
        this.addTag({
          tagName: this.tagName
        });
        this.tagName = "";
      }
    },
    addTag(item) {
      if (this.blog.tagNameList.indexOf(item.tagName) == -1) {
        this.blog.tagNameList.push(item.tagName);
      }
    },
    removeTag(item) {
      const index = this.blog.tagNameList.indexOf(item);
      this.blog.tagNameList.splice(index, 1);
    }
  },
  computed: {
    tagClass() {
      return function(item) {
        const index = this.blog.tagNameList.indexOf(item.tagName);
        return index != -1 ? "tag-item-select" : "tag-item";
      };
    }
  }
};
</script>

<style scoped>
.blog-title-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}
.save-btn {
  margin-left: 0.75rem;
  background: #fff;
  color: #f56c6c;
}
.tag-item {
  margin-right: 1rem;
  margin-bottom: 1rem;
  cursor: pointer;
}
.tag-item-select {
  margin-right: 1rem;
  margin-bottom: 1rem;
  cursor: not-allowed;
  color: #ccccd8 !important;
}
.category-item {
  cursor: pointer;
  padding: 0.6rem 0.5rem;
}
.category-item:hover {
  background-color: #f0f9eb;
  color: #67c23a;
}
.popover-title {
  margin-bottom: 1rem;
  text-align: center;
}
.popover-container {
  margin-top: 1rem;
  height: 260px;
  overflow-y: auto;
}
</style>
