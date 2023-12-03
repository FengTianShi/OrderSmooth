package com.nobody.OrderSmoothAPI.validate.validator;

import com.nobody.OrderSmoothAPI.validate.DateTime;
import java.text.SimpleDateFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateTimeValidator
  implements ConstraintValidator<DateTime, String> {

  private SimpleDateFormat sdf;

  @Override
  public void initialize(DateTime constraintAnnotation) {
    this.sdf = new SimpleDateFormat(constraintAnnotation.format());
    this.sdf.setLenient(false);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    if (value.length() != sdf.toPattern().length()) {
      return false;
    }

    try {
      sdf.parse(value);
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
