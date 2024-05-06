import assert from "node:assert";
import { EmployeeRepository } from "./employee.repository";

class EmployeeService {
	#employeeRepository: EmployeeRepository;

	constructor(employeeRepository: EmployeeRepository) {
		this.#employeeRepository = employeeRepository;
	}

	async list() {
		const employees = await this.#employeeRepository.list();
		return employees.sort(function (b, a) {
			return a.time - b.time;
		});
	}

	async getByName(name: string) {
		const found = await this.#employeeRepository.getByName(name);
		return found.sort(function (b, a) {
			return a.time - b.time;
		});
	}

	async #validateEmployeePayload(
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

		const employees = await this.list();

		if (kind === "create") {
			const found = employees.findIndex((salarie) => salarie.id === id);
			assert(found === -1, "Le matricule existe déjà");
		}

		if (kind === "update") {
			const found = employees.findIndex((salarie) => salarie.id === id);
			assert(found !== -1, "Le matricule n'a pas été trouvé");
		}
	}

	async add(
		id: string,
		name: string,
		lastname: string,
		salary: string,
		level: string,
	) {
		await this.#validateEmployeePayload(
			id,
			name,
			lastname,
			salary,
			level,
			"create",
		);

		this.#employeeRepository.add({
			id,
			name,
			lastname,
			salary,
			level,
			time: new Date().getTime(),
		});
	}

	async update(
		id: string,
		name: string,
		lastname: string,
		salary: string,
		level: string,
	) {
		await this.#validateEmployeePayload(
			id,
			name,
			lastname,
			salary,
			level,
			"update",
		);

		this.#employeeRepository.update({
			id,
			name,
			lastname,
			salary,
			level,
			time: new Date().getTime(),
		});
	}

	async delete(id: string) {
		assert(id, "Le salarié n'a pas été trouvé");
		const employees = await this.list();
		const found = employees.findIndex((salarie) => salarie.id === id);
		assert(found !== -1, "Le salarié n'a pas été trouvé");
		this.#employeeRepository.delete(found);
	}

	async deleteAll() {
		this.#employeeRepository.deleteAll();
	}

	async reset() {
		this.#employeeRepository.reset();
	}
}

export { EmployeeService };
