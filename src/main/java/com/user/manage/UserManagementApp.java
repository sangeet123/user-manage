package com.user.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration() @EnableAutoConfiguration(exclude = {
    SecurityAutoConfiguration.class }) @ComponentScan(basePackages = { "security*",
    "com.user.manage", "exception*", "error*","activemq.service*" }) public class UserManagementApp {

  public static void main(String[] args) {
    SpringApplication.run(UserManagementApp.class, args);
  }
}
