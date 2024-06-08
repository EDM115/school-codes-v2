import Activities from "@/pages/Activities.vue"
import Home from "@/pages/Home.vue"
import Login from "@/pages/Login.vue"
import Register from "@/pages/Register.vue"
import Users from "@/pages/Users.vue"
import userStore from "@/stores/user"
import { createRouter, createWebHistory } from "vue-router"

const routes = [
  { path: '/', component: Home },
  { path: "/activities", component: Activities }, 
  { path: "/users", component: Users },
  { path: "/register", component: Register },
  { path: "/login", component: Login }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const store = userStore()
  if (!routes.find(route => route.path === to.path)) {
    next('/')
  }
  if (to.path === "/login" || to.path === "/register") {
    if (store.isLogged) {
      next('/')
    }
    next()
  } else if (store.isLogged) {
    next()
  } else {
    if (to.path === '/') {
      next()
    } else {
      next("/login")
    }
  }
})  

export default router
