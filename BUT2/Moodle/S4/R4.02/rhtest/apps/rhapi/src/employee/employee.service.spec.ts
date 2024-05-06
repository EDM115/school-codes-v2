import { beforeAll, describe, expect, it } from "vitest";
import { EmployeeService } from "./employee.service";
import { EmployeeRepository } from "./employee.repository";
describe("Employee unit tests", () => {
	let employeeService: EmployeeService;

	beforeAll(() => {
		employeeService = new EmployeeService(new EmployeeRepository());
	});

	it("should list employees", async () => {
		const employees = await employeeService.list();
		expect(employees).toHaveLength(2);
	});

	it("should get employee by name", async () => {
		const employee = await employeeService.getByName("DUPOND");
		expect(employee).toHaveLength(1);
	});

	it("should have an error while adding employee with negative salary", async () => {
		expect(() =>
			employeeService.add("test", "doe", "john", "-10", "1"),
		).rejects.toThrow("Le salaire doit être un nombre positif");
	});

	it("should have an error while adding employee with level > 10", async () => {
		expect(() =>
			employeeService.add("test", "doe", "john", "10", "11"),
		).rejects.toThrow("Le niveau doit être > -10 et < 10");
	});

	it("should have an error while adding employee without id", async () => {
		expect(() =>
			employeeService.add("", "doe", "john", "10", "4"),
		).rejects.toThrow("Le matricule est obligatoire");
	});

	it("should have an error while adding employee if employee already exists", async () => {
		expect(() =>
			employeeService.add("SAL1", "doe", "john", "10", "4"),
		).rejects.toThrow("Le matricule existe déjà");
	});

	it("should create employee", async () => {
		await employeeService.add("DOEJ", "doe", "john", "10", "4");
		const employee = await employeeService.getByName("doe");
		expect(employee).toEqual([
			expect.objectContaining({
				id: "DOEJ",
				name: "doe",
				lastname: "john",
				salary: "10",
				level: "4",
			}),
		]);
	});

	it("should have an error while updating employee if doesn't exists", async () => {
		expect(() =>
			employeeService.update("notexists", "doe", "john", "10", "4"),
		).rejects.toThrow("Le matricule n'a pas été trouvé");
	});

	it("should update employee", async () => {
		await employeeService.update("SAL1", "DURAND", "Pierre", "333", "2");
		const employee = await employeeService.getByName("DURAND");
		expect(employee).toEqual([
			expect.objectContaining({
				id: "SAL1",
				name: "DURAND",
				lastname: "Pierre",
				salary: "333",
				level: "2",
			}),
		]);
	});
});
