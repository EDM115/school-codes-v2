import express, { Express } from "express";
import cors from "cors";
import prometheusClient from "prom-client";

import { EmployeeModule } from "./employee/employee.module";
import swagger from "./resources/swagger.json";

class Server {
	#app: Express;

	constructor() {
		this.#app = express();

		prometheusClient.collectDefaultMetrics({
			labels: { service: "rhapi" },
		});
	}

	bootstrap() {
		this.#app.use(cors());
		this.#app.use(express.json());
		this.#app.use(
			express.urlencoded({
				extended: false,
			}),
		);

		new EmployeeModule(this.#app);

		this.#app.use("/", express.static(`${__dirname}/public`));

		this.#app.get("/swagger.json", (req, res) => {
			res.json(swagger);
		});

		this.#app.get("/metrics", async (req, res) => {
			res.writeHead(200, {
				"Content-Type": prometheusClient.register.contentType,
			});
			return res.end(await prometheusClient.register.metrics());
		});

		return this.#app;
	}

	run() {
		this.#app.listen(8080, () => {
			console.log("🚀 server started and available on http://localhost:8080");
		});
	}
}

export { Server };
