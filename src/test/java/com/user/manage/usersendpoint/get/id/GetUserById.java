package com.user.manage.usersendpoint.get.id;

import com.user.manage.IntegrationTestConfigurer;
import com.user.manage.IntegrationTestUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;

/**
 * Created by sangeet on 4/12/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class GetUserById
    extends IntegrationTestConfigurer {
  private final static String USER_ENTRY_SPECIFIC_ENDPOINT = "/user/{id}";

  @Test() public void test_get_user_with_id_2_that_exist() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("id", 2).
        when().
        get(IntegrationTestUtils.getEndPoint(USER_ENTRY_SPECIFIC_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("username", equalTo("user")).
        body("id", equalTo(2)).body("firstName", equalTo("Ram"))
        .body("lastName", equalTo("Bahadur")).body("email", equalTo("user@email.com"));
  }

  @Test() public void test_get_user_with_id_5_that_does_not_exit() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        pathParam("id", 21).
        when().
        get(IntegrationTestUtils.getEndPoint(USER_ENTRY_SPECIFIC_ENDPOINT)).
        then().
        statusCode(HttpStatus.NOT_FOUND.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }

  @Test() public void test_get_all_users_for_normal_app_user_should_result_in_forbidden_access()
      throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessTokenForNormalUser()).
        pathParam("id", 2).
        when().
        get(IntegrationTestUtils.getEndPoint(USER_ENTRY_SPECIFIC_ENDPOINT)).
        then().
        statusCode(HttpStatus.FORBIDDEN.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }

}
