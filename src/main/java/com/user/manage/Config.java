package com.user.manage;

import com.user.manage.error.ConstraintVoilationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sangeet on 4/12/2017.
 */
@Configuration("user-config") public class Config {
  @Bean() public ConstraintVoilationMapper getMapper() {
    return new ConstraintVoilationMapper();
  }
}
