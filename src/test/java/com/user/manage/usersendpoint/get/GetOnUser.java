package com.user.manage.usersendpoint.get;

import com.user.manage.IntegrationTestConfigurer;
import com.user.manage.IntegrationTestUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

/**
 * Created by sangeet on 4/12/2017.
 */
@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
    "classpath:sql/create-schema.sql", "classpath:sql/create-user.sql" }),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:sql/drop-schema.sql" }) }) public class GetOnUser
    extends IntegrationTestConfigurer {
  private final static String USER_ENTRY_ENDPOINT = "/user";

  @Test() public void test_get_all_users() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        when().
        get(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("username", containsInAnyOrder("contactlist", "user")).
        body("id", containsInAnyOrder(1, 2)).body("firstName", containsInAnyOrder("Sangeet", "Ram"))
        .body("lastName", containsInAnyOrder("Dahal", "Bahadur"))
        .body("email", containsInAnyOrder("contactlist@email.com", "user@email.com"));
  }

  @Test() public void test_get_all_users_on_page_0_with_size_1() throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessToken()).
        queryParam("page", 0).
        queryParam("size", 1).
        queryParam("sort", "username,asc").
        when().
        get(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.OK.value()).
        contentType(JSON).body("username", contains("contactlist")).
        body("id", contains(1));
  }

  @Test() public void test_get_all_users_for_normal_app_user_should_result_in_forbidden_access()
      throws Exception {
    given().
        header("Authorization", IntegrationTestUtils.getAccessTokenForNormalUser()).
        when().
        get(IntegrationTestUtils.getEndPoint(USER_ENTRY_ENDPOINT)).
        then().
        statusCode(HttpStatus.FORBIDDEN.value()).
        contentType(isEmptyOrNullString()).
        body(isEmptyOrNullString());
  }

}
