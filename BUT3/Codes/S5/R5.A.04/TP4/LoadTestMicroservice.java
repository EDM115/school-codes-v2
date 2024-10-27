import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.core.body.*;
import io.gatling.javaapi.jdbc.*;

import io.gatling.http.response.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

import io.gatling.core.Predef.*;
import io.gatling.http.Predef.*;

import java.nio.file.Paths;

public class LoadTestMicroservice extends Simulation {

  {
    String uri = "http://localhost:8080";

    HttpProtocolBuilder httpProtocol = http
      .baseUrl(uri)
      .inferHtmlResources()
      .acceptHeader("*/*")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
	.userAgentHeader("Mozilla/5.0 (Ubuntu) Gecko/20100101 Firefox/111.0");
    
    ScenarioBuilder scn = scenario("Load Test User Microservice")
	.exec(http("Get All").get(uri+"/api/users"))
	.exec(http("Get One User").get(uri+"/api/users/3"));
    
    int max_vus = 10;
    int phase_duration = 10;
    setUp(
	  scn.injectOpen(
			 rampUsersPerSec(1).to(max_vus).during(phase_duration).randomized(),
			 constantUsersPerSec(max_vus).during(phase_duration).randomized(),
			 nothingFor(10),
			 rampUsersPerSec(1).to(max_vus).during(phase_duration).randomized(),
			 constantUsersPerSec(max_vus).during(phase_duration).randomized(),
			 nothingFor(10),
			 rampUsersPerSec(1).to(max_vus).during(phase_duration).randomized(),
			 constantUsersPerSec(max_vus).during(phase_duration).randomized(),
			 nothingFor(10),
			 rampUsersPerSec(1).to(max_vus).during(phase_duration).randomized(),
			 constantUsersPerSec(max_vus).during(phase_duration).randomized(),						    			 
			 nothingFor(10),
			 rampUsersPerSec(1).to(max_vus).during(phase_duration).randomized(),
			 constantUsersPerSec(max_vus).during(phase_duration).randomized())
	  .constantPauses())
	.protocols(httpProtocol);    
  }
}
