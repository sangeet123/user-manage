package com.user.manage.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sangeet on 4/11/2017.
 */
public class UserResponse {

  private Long id;

  private String username;

  private String firstName;

  private String lastName;

  private String email;

  private Boolean enabled;

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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(final Boolean enabled) {
    this.enabled = enabled;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    UserResponse that = (UserResponse) o;

    return new EqualsBuilder().append(getId(), that.getId())
        .append(getUsername(), that.getUsername()).append(getFirstName(), that.getFirstName())
        .append(getLastName(), that.getLastName()).append(getEmail(), that.getEmail())
        .append(getEnabled(), that.getEnabled()).isEquals();
  }

  @Override public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getId()).append(getUsername()).append(getFirstName())
        .append(getLastName()).append(getEmail()).append(getEnabled()).toHashCode();
  }
}
