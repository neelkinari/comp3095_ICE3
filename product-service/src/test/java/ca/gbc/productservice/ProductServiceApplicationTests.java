package ca.gbc.productservice;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
    @Test
    void contextLoads() {
    }
	//this annonation is used in combination with testcontainers to automatically configure the test container
	@ServiceConnection
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:latest");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
//		RestAssured.defaultParser = Parser.JSON;
	}
	static {
		mongoDBContainer.start();
	}
//
	@Test
	void createProductTest() {
		String requestBody = """
			{
			"name": "Samsung TV",
			"description": "Samsung TV-model 2024",
			"price": 2000
			}
			""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.log().all()
				.statusCode(201)
	//			.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Samsung TV"))
				.body("description", Matchers.equalTo("Samsung TV-model 2024"))
				.body("price", Matchers.equalTo(2000));
	}

	@Test
	void  getAllProductsTest() {
		String requestBody = """
						{
						"name":"Samsung TV",
						"description":"Samsung TV-model 2024",
						"price":"2000"
						}
				""";
		//BDD -O behavioural Driven Development(given ,When, Then)
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Samsung Tv"))
				.body("description", Matchers.equalTo("Samsung TV-model 2024"))
				.body("price", Matchers.equalTo(2000));

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/product")
				.then()
				.log().all()
				.statusCode(200)
				.body("size()", Matchers.greaterThan(0))
				.body("[0].name", Matchers.equalTo("Samsung TV"))
				.body("[0].description", Matchers.equalTo("Samsung TV-model 2024"))
				.body("[0].price", Matchers.equalTo(2000));

	}
}
