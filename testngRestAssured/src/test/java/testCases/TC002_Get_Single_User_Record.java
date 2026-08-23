package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jsonplaceholderapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC002_Get_Single_User_Record extends TestBase {

    @BeforeClass
    public void getUserData() {

        logger.info("********* Started TC002_Get_Single_User_Record **********");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET, "/users/" + userId);
    }

    @Test
    public void checkResponseBody() {

        logger.info("Checking response body");

        int responseUserId = response.jsonPath().getInt("id");

        Assert.assertEquals(
                responseUserId,
                Integer.parseInt(userId),
                "Incorrect user record returned."
        );
    }

    @Test
    public void checkStatusCode() {

        logger.info("Checking status code");

        Assert.assertEquals(response.getStatusCode(), 200);
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
    public void checkContentType() {

        logger.info("Checking content type");

        String contentType = response.getHeader("Content-Type");

        Assert.assertTrue(
                contentType.contains("application/json"),
                "Response content type is not JSON."
        );
    }

    @Test
    public void checkUserName() {

        logger.info("Checking user name");

        String name = response.jsonPath().getString("name");

        Assert.assertNotNull(name);
        Assert.assertFalse(name.isBlank());
    }

    @AfterClass
    public void tearDown() {
        logger.info("********* Finished TC002_Get_Single_User_Record **********");
    }
}