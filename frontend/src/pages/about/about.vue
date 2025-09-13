<script setup lang="ts">
import logo from "@/assets/Logo&Fonts.png";
import {onMounted, reactive, ref} from "vue";

import {useRoute, useRouter} from "vue-router";

import TextType from '@/components/TextType/TextType.vue';

const Router = useRouter();

onMounted(() => {
  document.title = "FluxSync，一款面向团队的实时协同笔记与云端文档管理工具";
});

const options = reactive([
  {id: 1, name: "首页", path: "/about", weight: "bold"},
  {id: 2, name: "帮助", path: "/help"}
]);

const texts = ref([
    'Flux Sync',
    '一款面向团队的实时协同笔记与云端文档管理工具',
    '多人实时协同编辑',
    '文档组织与项目管理',
    '细粒度权限控制',
    '版本快照与文档历史恢复'
]);

</script>

<template>

  <div>
    <!--   topbar   -->
    <div style="display: flex; height: 80px; margin: 0 300px;">

      <div style="display: flex; width: 130px; margin-right: 20%;">
        <el-image :src="logo" fit="contain"/>
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
        <el-button color="#626aef" @click="Router.push('/login')" plain>登录/注册</el-button>
      </div>

    </div>

    <div style="width: 100%; height: 100%;">
      <TextType
          class="texts"
          text-colors="rgb(0, 0, 0)"
          :text="texts"
          :typingSpeed="75"
          :pauseDuration="1500"
          :showCursor="true"
          cursorCharacter="_"
          :variable-speed="{min: 80, max: 150}"
      />
      <div style="display: flex; justify-content: center; margin-top: 80px;">
        <el-button type="primary" style="border-radius: 30px; width: 220px; height: 50px;" @click="Router.push('/login')" plain>
          免费使用
        </el-button>
      </div>
    </div>


  </div>
</template>

<style scoped>
.texts {
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
  font-size: 80px;
  margin-top: 160px;
}
</style>
