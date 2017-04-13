package com.user.manage.usersendpoint.post;

import com.user.manage.IntegrationTestConfigurer;
import com.user.manage.IntegrationTestUtils;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

/**
 * Created by sangeet on 4/13/2017.
 */

@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class PostOnUser
    extends IntegrationTestConfigurer {
  private final static String USER_ENTRY_ENDPOINT = "/user";
  private final static String USER_SPECIFIC_ENTRY_ENDPOINT = "/user/{id}";

  @Test() public void create_a_valid_user() {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstName");
    map.put("lastName", "lastName");
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    final Response response = given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT));

    response.
        then().
        statusCode(HttpStatus.CREATED.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());

    final String location = response.getHeader("location");
    final String id = location.substring(location.lastIndexOf("/") + 1);

    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("id", id).
        when().
        get(IntegrationTestUtils.getEndPoint(USER_SPECIFIC_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).
        body("firstName", equalTo("firstName")).
        body("lastName", equalTo("lastName")).
        body("username", equalTo("username")).
        body("email", equalTo("first_last@email.com"));
  }

  @Test() public void test_post_with_empty_user_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstName");
    map.put("lastName", "lastName");
    map.put("username", "");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("username")).
        body("fieldErrors.message", contains("Value is required."));
  }

  @Test() public void test_post_with_null_user_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstName");
    map.put("lastName", "lastName");
    map.put("username", "");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("username")).
        body("fieldErrors.message", contains("Value is required."));
  }

  @Test() public void test_post_with_user_name_size_larger_than_30() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstName");
    map.put("lastName", "lastName");
    map.put("username",
        "This is a very large last name and it should not be allowed to be entered into db.");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("username")).
        body("fieldErrors.message", contains("String length must be within boundry."));
  }

  @Test() public void test_post_with_empty_first_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "");
    map.put("lastName", "lastName");
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("firstName")).
        body("fieldErrors.message", contains("Value is required."));
  }

  @Test() public void test_post_with_null_first_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", null);
    map.put("lastName", "lastName");
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("firstName")).
        body("fieldErrors.message", contains("Value is required."));
  }

  @Test() public void test_post_with_first_name_size_larger_than_30() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName",
        "This is a very large last name and it should not be allowed to be entered into db.");
    map.put("lastName", "lastName");
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("firstName")).
        body("fieldErrors.message", contains("String length must be within boundry."));
  }

  @Test() public void test_post_with_empty_last_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstname");
    map.put("lastName", "");
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("lastName")).
        body("fieldErrors.message", contains("Value is required."));
  }

  @Test() public void test_post_with_null_last_name() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstname");
    map.put("lastName", null);
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("lastName")).
        body("fieldErrors.message", contains("Value is required."));
  }

  @Test() public void test_post_with_last_name_size_larger_than_30() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("lastName",
        "This is a very large last name and it should not be allowed to be entered into db.");
    map.put("firstName", "firstName");
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "Thisisverystrongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("lastName")).
        body("fieldErrors.message", contains("String length must be within boundry."));
  }

  @Test() public void test_post_with_empty_password() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstname");
    map.put("lastName", "lastName");
    map.put("username", "username");
    map.put("email", "first_last@email.com");
    map.put("password", "");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.BAD_REQUEST.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("password")).
        body("fieldErrors.message", contains("String length must be within boundry."));
  }

  @Test() public void test_post_with_duplicate_username_that_exist() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstname");
    map.put("lastName", "lastName");
    map.put("username", "contactlist");
    map.put("email", "my_contactlist@email.com");
    map.put("password", "strongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.CONFLICT.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("username")).
        body("fieldErrors.message", contains("username already in use."));
  }

  @Test() public void test_post_with_duplicate_email_that_exist() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", "firstname");
    map.put("lastName", "lastName");
    map.put("username", "contactlist1234");
    map.put("email", "contactlist@email.com");
    map.put("password", "strongpassword");

    given().
        contentType("application/json").
        body(map).
        when().
        post(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        contentType(JSON).
        statusCode(HttpStatus.CONFLICT.value()).
        body("context", equalTo("user")).
        body("fieldErrors.field", contains("email")).
        body("fieldErrors.message", contains("email address already in use."));
  }

}
