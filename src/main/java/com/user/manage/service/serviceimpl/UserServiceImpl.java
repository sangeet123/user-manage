package com.user.manage.service.serviceimpl;

import com.user.manage.model.request.UserRequest;
import com.user.manage.model.response.UserResponse;
import com.user.manage.service.UserService;
import exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.entity.User;
import security.entity.UserRole;
import security.repository.UserRepository;
import security.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangeet on 4/12/2017.
 */
@Transactional("userTransactionManager") @Service() public class UserServiceImpl
    implements UserService {
  private static final String ROLE_APP_USER = "ROLE_APP_USER";
  @Autowired() @Qualifier("userRepository") private UserRepository userRepository;
  @Autowired() @Qualifier("userRoleRepository") private UserRoleRepository userRoleRepository;

  private static final String getHashedPassword(final String password) {
    String salt = BCrypt.gensalt(12);
    String hashed_password = BCrypt.hashpw(password, salt);
    return hashed_password;
  }

  private static UserResponse mapToUserResponse(final User user) {
    final UserResponse userResponse = new UserResponse();
    userResponse.setUsername(user.getUsername());
    userResponse.setFirstName(user.getFirstName());
    userResponse.setLastName(user.getLastName());
    userResponse.setEmail(user.getEmail());
    userResponse.setEnabled(user.getEnabled());
    userResponse.setId(user.getId());
    return userResponse;
  }

  @Override public List<UserResponse> getUsers(final Pageable pageable) {
    final Iterable<User> contactLists = userRepository.findAll(pageable);
    final List<UserResponse> users = new ArrayList<>();
    if (users == null) {
      return users;
    }
    contactLists.iterator().forEachRemaining(user -> {
      users.add(mapToUserResponse(user));
    });
    return users;
  }

  @Override public UserResponse getUser(final Long userId) {
    final User user = userRepository.findOne(userId);
    if (user == null) {
      throw new NotFoundException("user with id " + userId + " not found");
    }
    return mapToUserResponse(user);
  }

  @Override public UserResponse create(UserRequest userRequest) {
    final User user = new User();
    user.setEnabled(Boolean.TRUE);
    user.setEmail(userRequest.getEmail());
    user.setUsername(userRequest.getUsername());
    user.setFirstName(userRequest.getFirstName());
    user.setLastName(userRequest.getLastName());
    user.setPassword(getHashedPassword(userRequest.getPassword()));
    userRepository.save(user);
    final UserRole userRole = new UserRole();
    userRole.setRole(ROLE_APP_USER);
    userRole.setUser(user);
    userRoleRepository.save(userRole);
    return mapToUserResponse(user);
  }
}
