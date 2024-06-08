import { useDateFormat } from "@vueuse/core"

export const formatDate = (date) => {
  return useDateFormat(date, "dddd DD/MM/YYYY", { locales: "fr-FR" }).value
}

export function getStatusIcon(status) {
  switch (status) {
    case "À faire":
      return "mdi-checkbox-blank-circle-outline"
    case "En cours":
      return "mdi-progress-check"
    case "Terminée":
      return "mdi-checkbox-marked-circle-outline"
  }
}

export function getStatusColor(status) {
  switch (status) {
    case "À faire":
      return "indigo"
    case "En cours":
      return "yellow"
    case "Terminée":
      return "green"
  }
}

export function getPriorityIcon(priority) {
  switch (priority) {
    case "Haute":
      return "mdi-arrow-up-bold-circle-outline"
    case "Moyenne":
      return "mdi-arrow-up-drop-circle-outline"
    case "Basse":
      return "mdi-arrow-down-drop-circle-outline"
  }
}

export function getPriorityColor(priority) {
  switch (priority) {
    case "Haute":
      return "red"
    case "Moyenne":
      return "orange"
    case "Basse":
      return "cyan"
  }
}
