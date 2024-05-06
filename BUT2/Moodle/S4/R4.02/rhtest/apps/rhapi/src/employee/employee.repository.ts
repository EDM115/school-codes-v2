import fs from "node:fs";
import salaries from "../resources/salarie.json";
import { Employee } from "./employee.model";

class EmployeeRepository {
	#employees: Employee[];

	constructor() {
		this.#employees = salaries.data;
	}

	list() {
		return this.#employees;
	}

	getByName(name: string) {
		return this.#employees.filter((salarie) => salarie.name === name);
	}

	add(employee: Employee) {
		this.#employees.push(employee);
	}

	update(update: Employee) {
		const found = this.#employees.find((employee) => employee.id === update.id);
		if (!found) {
			return;
		}
		found.name = update.name;
		found.lastname = update.lastname;
		found.salary = update.salary;
		found.level = update.level;
		found.time = update.time;
	}

	delete(index: number) {
		this.#employees.splice(index, 1);
	}

	deleteAll() {
		this.#employees = [];
	}

	reset() {
		const fileData = JSON.parse(
			fs.readFileSync(`${__dirname}/../resources/salarie.json`, "utf-8"),
		);
		this.#employees = fileData.data;
	}
}

export { EmployeeRepository };
