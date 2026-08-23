package testCases;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jsonplaceholderapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

public class TC004_Put_User_Record extends TestBase {

    private final int postId = 1;
    private String updatedTitle;
    private final String updatedBody = "Updated through Rest Assured PUT request.";
    private final int updatedUserId = 2;

    @BeforeClass
    public void updatePost() {

        logger.info("********* Started TC004_Put_User_Record **********");

        updatedTitle = "Updated API Post " + UUID.randomUUID();

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("id", postId);
        requestParams.put("title", updatedTitle);
        requestParams.put("body", updatedBody);
        requestParams.put("userId", updatedUserId);

        httpRequest = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestParams);

        response = httpRequest.request(Method.PUT, "/posts/" + postId);
    }

    @Test
    public void checkResponseBody() {

        logger.info("Checking response body");

        Assert.assertEquals(response.jsonPath().getInt("id"), postId);
        Assert.assertEquals(response.jsonPath().getString("title"), updatedTitle);
        Assert.assertEquals(response.jsonPath().getString("body"), updatedBody);
        Assert.assertEquals(response.jsonPath().getInt("userId"), updatedUserId);
    }

    @Test
    public void checkStatusCode() {

        logger.info("Checking status code");

        Assert.assertEquals(response.getStatusCode(), 200);
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

    @AfterClass
    public void tearDown() {
        logger.info("********* Finished TC004_Put_User_Record **********");
    }
}