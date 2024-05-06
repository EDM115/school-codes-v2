<template>
  <span>
    <h3 v-if="!creation">Mise à jour de l'employé {{ employee.id }}</h3>
    <form @submit.prevent="submit">
      <label>Matricule: <input v-model="employee.id" placeholder="ID" :disabled="!creation" :id="creation ? 'creerEmpId' : 'modifierEmpId'"></label>
      <div class="grid">
        <label>Nom: <input v-model="employee.name" placeholder="Nom" :id="creation ? 'creerEmpName' : 'modifierEmpName'"></label>
        <label>Prénom: <input v-model="employee.lastname" placeholder="Prénom" :id="creation ? 'creerEmpLastname' : 'modifierEmpLastname'"></label>
      </div>
      <div class="grid">
        <label>Niveau: <input v-model="employee.level" placeholder="Niveau" :id="creation ? 'creerEmpLevel' : 'modifierEmpLevel'"></label>
        <label>Salaire: <input v-model="employee.salary" placeholder="Salaire" :id="creation ? 'creerEmpSalary' : 'modifierEmpSalary'"></label>
      </div>
      <div class="submit">
        <button v-if="creation" id="creerEmp">Créer</button>
        <button v-if="!creation" id="modifierEmp">Modifier</button>
      </div>
    </form>
  </span>
</template>

<script setup>
import { defineEmits, reactive } from 'vue'
import { emptyEmployee } from '../services/employee.service'

const props = defineProps({
  id: String,
  name: String,
  lastname: String,
  level: String,
  salary: String,
})

const emit = defineEmits(['created', 'updated'])

const creation = !props.id?.length

// let employee = creation ? {
//   id: "",
//   name: "",
//   lastname: "",
//   level: "",
//   salary: ""
// } : {
//   id: props.id,
//   name: props.name,
//   lastname: props.lastname,
//   level: props.level,
//   salary: props.salary,
// }

const employee = reactive(creation ? {
  id: "",
  name: "",
  lastname: "",
  level: "",
  salary: ""
} : {
  id: props.id,
  name: props.name,
  lastname: props.lastname,
  level: props.level,
  salary: props.salary,
});

function resetForm() {
  Object.assign(employee, emptyEmployee);
}

const submit = () => {
  emit(creation ? "created" : "updated", employee);
  resetForm();
}

</script>

<style>
.submit {
    display: flex;
    justify-content: flex-end;
}
</style>