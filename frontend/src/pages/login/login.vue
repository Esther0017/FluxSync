<script setup lang="ts">
import logo from "@/assets/Logo&Fonts.png";
import bg1 from "@/assets/bg1.png";
import {ref} from "vue";

const enum State { login, register }
const status = ref(State.login);

</script>

<template>

  <div style="display: flex; height: 100%; width: 100%; flex-direction: column;">

    <el-image style="position: absolute; z-index: -1; width: 100vw; height: 100vh;"  :src="bg1" fit="fill" />

    <el-image style="width: 130px; height: 80px; padding-left: 25px;" :src="logo" fit="contain"/>

    <div style="display: flex; justify-content: center; width: 100%; ">

      <el-card class="box" style="margin-top: 100px;">
        <template #header>
          <div style="display: flex; flex-direction: column; gap: 15px;">
            <el-text type="info" style="font-size: 18px; color: black; font-weight: bold; align-self: normal;">欢迎使用
              FluxSync
            </el-text>
            <div style="display: flex">
              <el-button
                  :type="status === State.login ? 'primary' : 'default'"
                  @click="status = State.login"
                  :class="status === State.login ? 'bottomLine' : ''"
                  text
              >登录
              </el-button>
              <el-button
                  :type="status === State.register ? 'primary' : 'default'"
                  @click="status = State.register"
                  :class="status === State.register ? 'bottomLine' : ''"
                  text
              >注册
              </el-button>
            </div>
          </div>
        </template>

        <!-- 表单区域 -->
        <div>
          <transition name="fade" mode="out-in">
            <div v-if="status === State.login">
              <div class="form">
                <input id="username" type="text" class="inputs" placeholder=" " />
                <label for="username">用户名</label>
              </div>
              <div class="form" style="margin-top: 30px;">
                <input id="password" type="password" class="inputs" placeholder=" " />
                <label for="password">密码</label>
              </div>
              <div>
                <el-button
                    type="primary"
                    style="width: 100%; height: 40px; border-radius: 5px; margin-top: 30px;"
                >确认</el-button>
              </div>
            </div>
            <div v-else>
              <div class="form">
                <input id="username" type="text" class="inputs" placeholder=" " />
                <label for="username">用户名</label>
              </div>
              <div class="form" style="margin-top: 30px;">
                <input id="password" type="password" class="inputs" placeholder=" " />
                <label for="password">密码</label>
              </div>
              <div class="form" style="margin-top: 30px;">
                <input id="cpassword" type="password" class="inputs" placeholder=" " />
                <label for="cpassword">确认密码</label>
              </div>
              <div>
                <el-button
                    type="primary"
                    style="width: 100%; height: 40px; border-radius: 5px; margin-top: 30px;"
                >确认</el-button>
              </div>
            </div>
          </transition>
        </div>

        <template #footer>
          <el-divider>更多登录方式</el-divider>
          <el-button
              type="default"
              style="width: 100%; height: 40px; border-radius: 5px;"
              class="find"
          >找回密码</el-button>
        </template>
      </el-card>
    </div>

  </div>
</template>

<style scoped>
.inputs {
  all: unset;
  width: 100%;
  height: 30px;
  border-bottom: 1px solid black;
  font-size: 14px;
}
.form {
  position: relative;
}
.form > label {
  position: absolute;
  top: 7px;
  left: 7px;
  font-size: 14px;
  pointer-events: none;
  opacity: 0.6;
}
.inputs::placeholder {
  color: transparent;
}
.inputs:focus + label,
.inputs:not(:placeholder-shown) + label {
  font-size: 12px;
  top: -15px;
  left: 0;
  opacity: 1;
  color: #1867C0;
  transition: font-size 0.3s ease, top 0.3s ease, left 0.3s ease, opacity 0.3s ease;
}
.inputs:focus,
.inputs:not(:placeholder-shown) {
  border-bottom: 2px solid #1867C0;
}
.inputs:not(:focus) + label {
  color: black;
}
.inputs:not(:focus) {
  border-bottom: 1px solid black;
}

.box {
  border-radius: 8px;
  width: 440px;
}

:deep(.el-card__header) {
  border-bottom: none;
}

:deep(.el-card__footer) {
  border-top: none;
}

.bottomLine {
  border-bottom: 2px solid #409EFF;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.find:hover {
  background-color: #F2F3F5;
  border-color: var(--el-border-color);
  color: var(--el-text-color-regular);
}
</style>
