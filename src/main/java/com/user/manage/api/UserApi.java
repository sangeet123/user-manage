package com.user.manage.api;

import activemq.service.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.manage.model.request.UserRequest;
import com.user.manage.model.response.UserResponse;
import com.user.manage.model.transaction.Transaction;
import com.user.manage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by sangeet on 4/11/2017.
 */
@RestController() @RequestMapping("/user") public class UserApi {
  @Autowired() private UserService userService;
  @Autowired private Producer producer;
  private static ObjectMapper om = new ObjectMapper();
  private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);

  @Secured({
      "ROLE_APP_ADMIN" }) @RequestMapping(method = RequestMethod.GET) public @ResponseBody() List<UserResponse> get(
      final Pageable pageable, final Transaction transaction) {
    final List<UserResponse> userResponses = userService.getUsers(pageable);
    transaction.setReturnStatus(HttpServletResponse.SC_OK);
    return userResponses;
  }

  @Secured({
      "ROLE_APP_ADMIN" }) @RequestMapping(value = "/{userId}", method = RequestMethod.GET) public @ResponseBody() UserResponse get(
      @PathVariable Long userId, final Transaction transaction) {
    final UserResponse userResponse = userService.getUser(userId);
    transaction.setReturnStatus(HttpServletResponse.SC_OK);
    return userResponse;
  }

  @RequestMapping(method = RequestMethod.POST) public void create(
      @RequestBody() @Valid() final UserRequest userRequest, final Transaction transaction) {
    final UserResponse userResponse = userService.create(userRequest);
    transaction.setLocationHeader("user/" + userResponse.getId());
    transaction.setReturnStatus(HttpServletResponse.SC_CREATED);
    try {
      final String createdUser = om.writeValueAsString(userResponse);
      producer.send(createdUser);
    }catch(final JsonProcessingException ex){
      //This should never happen
      LOGGER.error("Failed to serilize created user ex{}",ex);
    }
  }

}
