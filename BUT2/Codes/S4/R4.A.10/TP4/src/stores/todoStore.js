import { defineStore } from "pinia"
import { v4 as uuidv4 } from "uuid"

export const useTodoStore = defineStore("todo", {
  state: () => ({
    tasks: JSON.parse(localStorage.getItem("tasks")) || [],
    filteredTasks: JSON.parse(localStorage.getItem("tasks")) || [],
  }),
  getters: {
    getTasks: (state) => state.filteredTasks,
    getTask: (state) => (id) => state.tasks.find(task => task.id === id),
    taskCount: (state) => state.filteredTasks.length,
  },
  actions: {
    addTask(task) {
      task.id = uuidv4()
      task.creationDate = new Date().toISOString()
      this.tasks.unshift(task)
      this.filteredTasks = [...this.tasks]
      this.saveTasks()
    },
    deleteTasks() {
      this.tasks = []
      this.filteredTasks = []
      this.saveTasks()
    },
    removeTask(id) {
      this.tasks = this.tasks.filter(task => task.id !== id)
      this.filteredTasks = this.filteredTasks.filter(task => task.id !== id)
      this.saveTasks()
    },
    updateTask(id, updatedTask) {
      const index = this.tasks.findIndex(task => task.id === id)
      if (index !== -1) {
        this.tasks[index] = { ...this.tasks[index], ...updatedTask }
        this.filteredTasks = [...this.tasks]
        this.saveTasks()
      }
    },
    saveTasks() {
      localStorage.setItem("tasks", JSON.stringify(this.tasks))
    },
    filterTasksByDate(startDate, endDate) {
      console.log("store", startDate, endDate)
      this.filteredTasks = this.tasks.filter(task => {
        const taskStartDate = new Date(task.startDate)
        const taskEndDate = new Date(task.endDate)
        if (startDate && endDate) {
          return taskStartDate >= new Date(startDate) && taskEndDate <= new Date(endDate)
        } else if (startDate) {
          return taskStartDate >= new Date(startDate)
        } else if (endDate) {
          return taskEndDate <= new Date(endDate)
        } else {
          return true
        }
      })
    },
    filterTasksByPriorities(priorities) {
      this.filteredTasks = this.tasks.filter(task => priorities.includes(task.priority))
    },
    filterTasksByStatuses(statuses) {
      this.filteredTasks = this.tasks.filter(task => statuses.includes(task.status))
    },
    resetFilters() {
      this.filteredTasks = [...this.tasks]
    }
  },
})

export default useTodoStore
