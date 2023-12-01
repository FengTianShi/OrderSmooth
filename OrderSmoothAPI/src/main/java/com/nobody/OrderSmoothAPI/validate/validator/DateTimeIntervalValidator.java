package com.nobody.OrderSmoothAPI.validate.validator;

import com.nobody.OrderSmoothAPI.validate.DateTimeInterval;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class DateTimeIntervalValidator
  implements ConstraintValidator<DateTimeInterval, Object> {

  private SimpleDateFormat sdf;

  private String[] startTime;
  private String[] endTime;

  @Override
  public void initialize(DateTimeInterval constraintAnnotation) {
    this.sdf = new SimpleDateFormat(constraintAnnotation.format());
    this.sdf.setLenient(false);

    this.startTime = constraintAnnotation.startTime();
    this.endTime = constraintAnnotation.endTime();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(value);

    for (int i = 0; i < startTime.length; i++) {
      Object startTimePropVal = beanWrapper.getPropertyValue(startTime[i]);
      Object endTimePropVal = beanWrapper.getPropertyValue(endTime[i]);

      try {
        Date beginTimeVal = sdf.parse((String) startTimePropVal);
        Date endTimeVal = sdf.parse((String) endTimePropVal);

        if (
          beginTimeVal != null &&
          endTimeVal != null &&
          endTimeVal.compareTo(beginTimeVal) <= 0
        ) {
          return false;
        }
      } catch (Exception e) {
        continue;
      }
    }

    return true;
  }
}
