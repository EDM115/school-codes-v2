import { createRouter, createWebHistory } from "vue-router"
import Home from "@/views/Home.vue"
import GameBoard from "@/components/GameBoard.vue"
import WaitingRoom from "@/views/WaitingRoom.vue"
import { usePlayerStore } from "@/stores/playerStore"

const routes = [
  { path: "/", component: Home, name: "Home" },
  { path: "/game/:gameId", component: GameBoard, name: "Game" },
  { path: "/waiting-room/:gameId/:playerName", component: WaitingRoom, name: "WaitingRoom" }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const playerStore = usePlayerStore()

  if (to.path === "/") {
    playerStore.resetPlayerInfo()
  }

  next()
})

export default router
