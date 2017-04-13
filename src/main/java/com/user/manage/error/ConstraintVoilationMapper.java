package com.user.manage.error;

import error.ValidationErrorInfo;
import exception.mapper.DataIntegrityViolationMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by sangeet on 4/6/2017.
 */
public class ConstraintVoilationMapper implements DataIntegrityViolationMapper {

  @Override public ValidationErrorInfo mapException(final DataIntegrityViolationException e) {
    if (e.getCause() instanceof ConstraintViolationException) {
      final ConstraintViolationException constraintViolationException = (ConstraintViolationException) e
          .getCause();
      return ErrorInfoForConstraints
          .getValidationErrorInfo(constraintViolationException.getConstraintName());
    }
    return null;
  }
}
