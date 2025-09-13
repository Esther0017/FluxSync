import { createRouter, createWebHistory } from "vue-router";

const routes = [
    {
        // 首页
        path: "/",
        name: "about",
        component: () => import("@/pages/about/about.vue"),
        alias: '/about'
    },
    {
        // 登录页
        path: "/login",
        name: "login",
        component: () => import()
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
