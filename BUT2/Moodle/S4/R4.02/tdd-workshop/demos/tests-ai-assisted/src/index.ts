import assert from 'node:assert';

type Employee = {
	id: string;
	name: string;
	lastname: string;
	salary: string;
	level: string;
};

const employees: Employee[] = [];
async function list() {
  return employees;
}

async function validateEmployeePayload(
  id: string,
  name: string,
  lastname: string,
  salary: string,
  level: string,
  kind: "create" | "update",
) {
  const numSalary = parseFloat(salary);
  assert(
    salary && numSalary > 0 && !isNaN(numSalary),
    "Le salaire doit être un nombre positif",
  );

  const numLevel = Math.abs(parseInt(level));
  assert(
    numLevel && numLevel < 10 && !isNaN(numLevel),
    "Le niveau doit être > -10 et < 10",
  );

  assert(id.length > 0, "Le matricule est obligatoire");

  const employees = await list();

  if (kind === "create") {
    const found = employees.findIndex((salarie) => salarie.id === id);
    assert(found === -1, "Le matricule existe déjà");
  }

  if (kind === "update") {
    const found = employees.findIndex((salarie) => salarie.id === id);
    assert(found !== -1, "Le matricule n'a pas été trouvé");
  }
}

async function addEmployee(
  id: string,
  name: string,
  lastname: string,
  salary: string,
  level: string,
) {
  await validateEmployeePayload(
    id,
    name,
    lastname,
    salary,
    level,
    "create",
  );

  employees.push({
    id,
    name,
    lastname,
    salary,
    level,
  })
}

export {addEmployee, list, employees}