package com.user.manage.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by sangeet on 4/11/2017.
 */
public class UserRequest {
  private static final int NAME_MAX_SIZE = 30;
  private static final int MIN_PASSWORD_SIZE = 8;
  private static final int MAX_PASSWORD_SIZE = 256;
  @JsonProperty(value = "username") @NotEmpty() @Size(max = NAME_MAX_SIZE) private String username;

  @JsonProperty(value = "firstName") @NotEmpty() @Size(max = NAME_MAX_SIZE) private String firstName;

  @JsonProperty(value = "lastName") @NotEmpty() @Size(max = NAME_MAX_SIZE) private String lastName;

  @JsonProperty(value = "email") @NotEmpty() @Email() private String email;

  @JsonProperty(value = "password") @Size(min = MIN_PASSWORD_SIZE, max = MAX_PASSWORD_SIZE) private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
