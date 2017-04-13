package com.user.manage.error;

import error.ValidationErrorInfo;

import java.util.HashMap;

/**
 * Created by sangeet on 4/6/2017.
 */
public class ErrorInfoForConstraints {
  final static String UNIQUE_KEY_CONSTRAINT_ON_USERNAME = "UK_username";
  final static String UNIQUE_KEY_CONSTRAINT_ON_EMAIL = "UK_email";
  final static HashMap<String, ValidationErrorInfo> constraintToValidationErrorMapper;

  static {
    constraintToValidationErrorMapper = new HashMap<>();

    //UNIQUE_KEY_CONSTRAINT_ON_CONTACTLIST_NAME
    final ValidationErrorInfo forUK_contactlist = new ValidationErrorInfo();
    forUK_contactlist.addFieldError("username", "username already in use.");
    constraintToValidationErrorMapper.put(UNIQUE_KEY_CONSTRAINT_ON_USERNAME, forUK_contactlist);

    //UNIQUE_KEY_CONSTRAINT_ON_CONTACT
    final ValidationErrorInfo forUK_emailInContactList = new ValidationErrorInfo();
    forUK_emailInContactList.addFieldError("email", "email address already in use.");
    constraintToValidationErrorMapper.put(UNIQUE_KEY_CONSTRAINT_ON_EMAIL, forUK_emailInContactList);

  }

  final static ValidationErrorInfo getValidationErrorInfo(final String constraintName) {
    return constraintToValidationErrorMapper.get(constraintName);
  }
}
