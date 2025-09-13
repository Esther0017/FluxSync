<script setup lang="ts">
import logo from "@/assets/Logo&Fonts.png";
import {onMounted, reactive, ref} from "vue";

import { useRoute, useRouter } from "vue-router";

const Router = useRouter();

onMounted(() => {
  document.title = "FluxSync，一款面向团队的实时协同笔记与云端文档管理工具";
});

const options = reactive([
  {id: 1, name: "首页", path: "/about", weight: "bold"},
  {id: 2, name: "帮助", path: "/help"}
]);

</script>

<template>

  <div style="display: flex;">
    <!--   topbar   -->
    <div style="display: flex; width: 100%; height: 80px; margin: 0 300px;">

      <div style="display: flex; width: 130px; margin-right: 20%;">
        <el-image :src="logo" fit="contain" />
      </div>

      <div style="
              display: flex;
              gap: 50px;
              user-select: none;
              color: #444444;
              width: 700px;
      ">
        <el-text v-for="item in options" :key="item.id" :style="{ fontWeight: item.weight }" style="font-size: 16px;">
          <a :href="item.path" style="color: inherit; text-decoration: none;">
            {{ item.name }}
          </a>
        </el-text>
      </div>

      <div style="display: flex; align-items: center;">
        <el-button color="#626aef" @click="Router.push('/login?')" plain>登录/注册</el-button>
      </div>

    </div>


  </div>
</template>

<style scoped>

</style>
