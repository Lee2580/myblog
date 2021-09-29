<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-title">管理员登录</div>
      <!-- 登录表单 -->
      <el-form
          status-icon
          :model="loginForm"
          :rules="rules"
          ref="ruleForm"
          class="login-form"
      >
        <!-- 用户名输入框 -->
        <el-form-item prop="username">
          <el-input
              v-model="loginForm.username"
              prefix-icon="el-icon-user-solid"
              placeholder="用户名"
              @keyup.enter.native="login"
          />
        </el-form-item>
        <!-- 密码输入框 -->
        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              prefix-icon="iconfont el-icon-mymima"
              show-password
              placeholder="密码"
              @keyup.enter.native="login"
          />
        </el-form-item>
        <el-form-item prop="code">
          <el-input
              style="width: 220px; float: left"
              maxlength="5"
              v-model="loginForm.code"
              prefix-icon="el-icon-edit-outline"
              placeholder="验证码"
              @keyup.enter.native="login"
          />
          <el-image
              style="border-radius: 5px; float: right"
              class="captchaImg"
              :src="captchaImg"
              @click="getCaptcha"></el-image>
        </el-form-item>
      </el-form>
      <!-- 登录按钮 -->
      <el-button type="primary" @click="login">登录</el-button>
    </div>
  </div>
</template>

<script>
import {generaMenu} from "../assets/js/menu";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
        code:"",
      },
      rules: {
        username: [
          {required: true, message: "用户名不能为空", trigger: "blur"}
        ],
        password: [
            {required: true, message: "密码不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {min: 5, max: 5, message: '验证码为5个字符', trigger: 'blur'}
        ],
      },
      captchaImg: '/api/captcha?time='+new Date()
    };
  },
  methods: {
    getCaptcha() {
      this.captchaImg = '/api/captcha?time='+new Date();
    },
    login() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          //发送登录请求
          let param = new URLSearchParams();
          param.append("username", this.loginForm.username);
          param.append("password", this.loginForm.password);
          param.append("code",this.loginForm.code);
          this.axios.post("/api/login", param).then(({data}) => {
            if (data.status) {
              // 登录后保存用户信息
              this.$store.commit("login", data.data);
              // 加载用户菜单
              generaMenu();
              this.$message.success("登录成功");
              this.$router.push({path: "/"});
            } else {
              this.$message.error(data.message);
            }
          });
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.login-container {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background: url(https://static.lovesky.top/zhizi.jpg) center center /
    cover no-repeat;
}

.login-card {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #fff;
  padding: 170px 60px 180px;
  width: 350px;
}

.login-title {
  color: #303133;
  font-weight: bold;
  font-size: 1rem;
}

.login-form {
  margin-top: 1.2rem;
}

.login-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>
