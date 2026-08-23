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

public class TC003_Post_User_Record extends TestBase {

    private String postTitle;
    private final String postBody = "Created from Rest Assured automation.";
    private final int postUserId = 2;

    @BeforeClass
    public void createPost() {

        logger.info("********* Started TC003_Post_User_Record **********");

        postTitle = "Automation Post " + UUID.randomUUID();

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("title", postTitle);
        requestParams.put("body", postBody);
        requestParams.put("userId", postUserId);

        httpRequest = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestParams);

        response = httpRequest.request(Method.POST, "/posts");
    }

    @Test
    public void checkResponseBody() {

        logger.info("Checking response body");

        Assert.assertEquals(response.jsonPath().getString("title"), postTitle);
        Assert.assertEquals(response.jsonPath().getString("body"), postBody);
        Assert.assertEquals(response.jsonPath().getInt("userId"), postUserId);
        Assert.assertNotNull(response.jsonPath().get("id"));
    }

    @Test
    public void checkStatusCode() {

        logger.info("Checking status code");

        Assert.assertEquals(response.getStatusCode(), 201);
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
        logger.info("********* Finished TC003_Post_User_Record **********");
    }
}