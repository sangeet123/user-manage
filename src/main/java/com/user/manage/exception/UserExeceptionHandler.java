package com.user.manage.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sangeet on 4/12/2017.
 */
@ControllerAdvice() @RestController() public class UserExeceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserExeceptionHandler.class);

  @ResponseStatus(HttpStatus.FORBIDDEN) @ExceptionHandler(value = AccessDeniedException.class) public void handleException(
      final Exception e) {
    LOGGER.error("{}", e);
  }
}
