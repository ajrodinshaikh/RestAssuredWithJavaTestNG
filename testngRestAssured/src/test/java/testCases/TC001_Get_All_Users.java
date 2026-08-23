package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jsonplaceholderapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC001_Get_All_Users extends TestBase {

    @BeforeClass
    public void getAllUsers() {

        logger.info("********* Started TC001_Get_All_Users **********");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET, "/users");
    }

    @Test
    public void checkResponseBody() {

        logger.info("********* Checking Response Body **********");

        String responseBody = response.getBody().asString();
        logger.info("Response Body --> " + responseBody);

        Assert.assertNotNull(responseBody);
        Assert.assertTrue(responseBody.contains("Leanne Graham"),
                "Expected user data was not returned.");
    }

    @Test
    public void checkStatusCode() {

        logger.info("********* Checking Status Code **********");

        int statusCode = response.getStatusCode();
        logger.info("Status Code --> " + statusCode);

        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void checkResponseTime() {

        logger.info("********* Checking Response Time **********");

        long responseTime = response.getTime();
        logger.info("Response Time --> " + responseTime + " ms");

        Assert.assertTrue(responseTime < 20000,
                "Response time is greater than 20 seconds.");
    }

    @Test
    public void checkContentType() {

        logger.info("********* Checking Content Type **********");

        String contentType = response.getHeader("Content-Type");
        logger.info("Content Type --> " + contentType);

        Assert.assertTrue(contentType.contains("application/json"),
                "Response is not JSON.");
    }

    @Test
    public void checkUsersCount() {

        logger.info("********* Checking Users Count **********");

        int userCount = response.jsonPath().getList("$").size();
        logger.info("Users Count --> " + userCount);

        Assert.assertEquals(userCount, 10);
    }

    @AfterClass
    public void tearDown() {
        logger.info("********* Finished TC001_Get_All_Users **********");
    }
}