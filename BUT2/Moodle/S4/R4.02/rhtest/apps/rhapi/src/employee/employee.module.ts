import { EmployeeController } from "./employee.controller";
import { Express } from "express";
import { EmployeeService } from "./employee.service";
import { EmployeeRepository } from "./employee.repository";

class EmployeeModule {
	constructor(expressApp: Express) {
		const repository = new EmployeeRepository();
		const service = new EmployeeService(repository);
		const controller = new EmployeeController(service);

		expressApp.use(controller.routes());
	}
}

export { EmployeeModule };
