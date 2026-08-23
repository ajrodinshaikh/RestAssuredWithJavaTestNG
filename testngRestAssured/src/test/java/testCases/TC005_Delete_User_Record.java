package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jsonplaceholderapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC005_Delete_User_Record extends TestBase {

    private final int postId = 1;

    @BeforeClass
    public void deletePost() {

        logger.info("********* Started TC005_Delete_User_Record **********");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        httpRequest = RestAssured.given();

        response = httpRequest.request(
                Method.DELETE,
                "/posts/" + postId
        );
    }

    @Test
    public void checkStatusCode() {

        logger.info("Checking status code");

        int statusCode = response.getStatusCode();
        logger.info("Status code --> " + statusCode);

        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void checkResponseTime() {

        logger.info("Checking response time");

        long responseTime = response.getTime();
        logger.info("Response time --> " + responseTime + " ms");

        Assert.assertTrue(responseTime < 20000,
                "Response time is greater than 20 seconds.");
    }

    @Test
    public void checkResponseBody() {

        logger.info("Checking delete response body");

        String responseBody = response.getBody().asString();
        logger.info("Response body --> " + responseBody);

        Assert.assertNotNull(responseBody);
    }

    @AfterClass
    public void tearDown() {
        logger.info("********* Finished TC005_Delete_User_Record **********");
    }
}