<template>
  <header class="container">
    <h1><img id="logo" src="/public/logo.png" alt="application logo"> RhTest</h1>
  </header>
  <main class="container">
    <div class="error" id="errorMessage" v-if="errorMessage">
        Une erreur est survenue : {{ errorMessage }}
    </div>

    <div class="success" id="successMessage" v-if="successMessage">
      {{ successMessage }}
    </div>

    <section id="create-employee">
      <h2>Création d'un salarié :</h2>
      <Employee @created="createdEvent" />
    </section>

    <section id="list-employees">
      <h2>Liste des salariés ({{ employees.length || 0  }}) :</h2>
      <form>
        <div class="grid">
          <input v-model="searchTerm" type="search" id="search" name="search" placeholder="Rechercher"
            @keyup="search(searchTerm)">
        </div>
      </form>
      <table>
        <thead>
          <tr>
            <th>Matricule</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Salaire</th>
            <th>Niveau</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="employee, index in employees" :key="employee.id">
            <td :id="'empId_' + index">{{ employee.id }}</td>
            <td :id="'empName_' + index">{{ employee.name }}</td>
            <td :id="'empLasttname_' + index">{{ employee.lastname }}</td>
            <td :id="'empSalary_' + index">{{ employee.salary }}</td>
            <td :id="'empLevel_' + index">{{ employee.level }}</td>
            <td>
              <button class="outline small-btn" @click="toggleUpdate(employee)" :id="'empUpdate_' + index">📝</button>
              <button class="outline small-btn" @click="deleteEmployee(employee)" :id="'empDelete_' + index">🗑️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>

    <section id="update-employee">
      <Employee v-if="updateMode" :id="employee.id" :name="employee.name" :lastname="employee.lastname"
          :salary="employee.salary" :level="employee.level" @updated="updateEmployee" />
    </section>

    <section id="admin">
      <h2>Administration</h2>
      <div class="admin">
        <button class="small-btn" @click="deleteAll()" id="deleteAll">🗑️ Supprimer les données</button>
        <button class="small-btn" @click="resetData()" id="resetData">↩ Restaurer les données de test</button>
      </div>
    </section>        
  </main>
</template>

<script>
import Employee from './components/Employee.vue';
import { fetch, search, create, update, deleteOne, deleteAll, resetData, emptyEmployee } from './services/employee.service';

export default {
  data() {
    return {
      employee: emptyEmployee,
      employees: [],
      searchTerm: "",
      updateMode: false,
      error: null
    };
  },
  components: { Employee },
  mounted() {
    this.fetchEmployees();
  },
  methods: {
    async fetchEmployees() {
      this.employees = await fetch();
    },
    async search(name) {
      if (!name.length) {
        return this.fetchEmployees();
      }
      this.employees = await search(name);
    },
    async createdEvent(employee) {
      try {
        this.successMessage = await create(employee);
      } catch (error) {
        this.errorMessage = error.response.data;
      } finally {
        this.employee = emptyEmployee;
        return this.fetchEmployees();
      }
    },
    toggleUpdate(employee) {
      this.updateMode = !this.updateMode;
      if (this.updateMode) {
        this.employee = employee;
      } else {
        this.employee = emptyEmployee;
      }
    },
    async updateEmployee(employee) {
      try {
        this.successMessage = await update(employee);
      } catch (error) {
        this.errorMessage = error.response.data;
      } finally {
        this.employee = emptyEmployee;
        this.updateMode = false;
        return this.fetchEmployees();
      }
    },
    async deleteEmployee(employee) {
      try {
        this.successMessage = await deleteOne(employee);
      } catch (error) {
        this.errorMessage = error.response.data;
      } finally {
        this.employee = emptyEmployee;
        return this.fetchEmployees();
      }
    },
    async deleteAll() {
      await deleteAll()
      return this.fetchEmployees();
    },
    async resetData() {
      this.successMessage = await resetData();
      return this.fetchEmployees();
    },
    closeError() {
      this.errorMessage = null;
      this.successMessage = null;
    }
  },
}
</script>

<style>
.small-btn {
  width: auto;
  display: inline;
  padding: 0.25rem 0.5rem;
  margin-bottom: 0;
  font-size: 0.75em;
}

.admin>button {
  margin: 0.25rem;
}

.error {
  color: #D8000C;
  background-color: #FFBABA;
  border-radius: 10px;
  height: 4rem;
  line-height: 2rem;
  padding: 1rem; 
}

.success {
  color: #4F8A10;
  background-color: #DFF2BF;
  border-radius: 10px;
  height: 4rem;
  line-height: 2rem;
  padding: 1rem; 
}

#logo{
  width: 100px;
}

</style>