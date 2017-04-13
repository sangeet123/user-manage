package com.user.manage.service;

import com.user.manage.model.request.UserRequest;
import com.user.manage.model.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sangeet on 4/12/2017.
 */
public interface UserService {
  List<UserResponse> getUsers(final Pageable pageable);

  UserResponse getUser(final Long userId);

  UserResponse create(final UserRequest userRequest);
}
