package com.nobody.OrderSmoothAPI.validate.validator;

import com.nobody.OrderSmoothAPI.validate.NotOverlap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class NotOverlapValidator
  implements ConstraintValidator<NotOverlap, List<?>> {

  private SimpleDateFormat sdf;

  private String startTime;
  private String endTime;

  @Override
  public void initialize(NotOverlap constraintAnnotation) {
    this.sdf = new SimpleDateFormat(constraintAnnotation.format());
    this.sdf.setLenient(false);

    this.startTime = constraintAnnotation.startTime();
    this.endTime = constraintAnnotation.endTime();
  }

  @Override
  public boolean isValid(List<?> values, ConstraintValidatorContext context) {
    if (values == null || values.size() == 0) {
      return true;
    }

    int n = values.size();
    BeanWrapper beanWrapper;
    for (int i = 0; i < n; i++) {
      beanWrapper = new BeanWrapperImpl(values.get(i));

      Object range1StartTimePropVal = beanWrapper.getPropertyValue(startTime);
      Object range1EndTimePropVal = beanWrapper.getPropertyValue(endTime);

      Date range1StartTime;
      Date range1EndTime;
      try {
        range1StartTime = sdf.parse((String) range1StartTimePropVal);
        range1EndTime = sdf.parse((String) range1EndTimePropVal);
      } catch (Exception e) {
        continue;
      }

      for (int j = i + 1; j < n; j++) {
        beanWrapper = new BeanWrapperImpl(values.get(j));

        Object range2StartTimePropVal = beanWrapper.getPropertyValue(startTime);
        Object range2EndTimePropVal = beanWrapper.getPropertyValue(endTime);

        Date range2StartTime;
        Date range2EndTime;
        try {
          range2StartTime = sdf.parse((String) range2StartTimePropVal);
          range2EndTime = sdf.parse((String) range2EndTimePropVal);
        } catch (Exception e) {
          continue;
        }

        if (
          isOverlap(
            range1StartTime,
            range1EndTime,
            range2StartTime,
            range2EndTime
          )
        ) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean isOverlap(
    Date range1StartTime,
    Date range1EndTime,
    Date range2StartTime,
    Date range2EndTime
  ) {
    if (
      range1EndTime.before(range2StartTime) ||
      range2EndTime.before(range1StartTime)
    ) {
      return false;
    }

    return true;
  }
}
