import cookie from "cookiejs"
import { defineStore } from "pinia"

export const userStore = defineStore("users", {
  state: () => ({
    isLoggedIn: false,
    user: null
  }),
  getters: {
    isLogged() {
      let loggedInCookie = cookie.get("isLoggedIn")

      if (loggedInCookie) {
        this.isLoggedIn = true
      }

      return this.isLoggedIn
    },
    getUser() {
      let userCookie = cookie.get("user")

      if (userCookie) {
        this.user = JSON.parse(decodeURI(userCookie))
      }

      return this.user
    }
  },
  actions: {
    createCookie(name, value, days) {
      cookie(name, value, days)

      return value
    },
    login(user) {
      this.isLoggedIn = this.createCookie("isLoggedIn", true, 1)
      this.user = this.createCookie("user", JSON.stringify(user), 1)
    },
    logout() {
      this.isLoggedIn = false
      this.user = null
      cookie.remove("isLoggedIn", "user")
    }
  }
})

export default userStore
