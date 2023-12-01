package com.nobody.OrderSmoothAPI.validate;

import com.nobody.OrderSmoothAPI.validate.validator.DateTimeIntervalValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(
  {
    ElementType.TYPE,
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.PARAMETER,
    ElementType.ANNOTATION_TYPE,
    ElementType.TYPE_USE,
  }
)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeIntervalValidator.class)
@Documented
public @interface DateTimeInterval {
  String message() default "end time must be greater than start time";

  String[] startTime() default { "startTime" };

  String[] endTime() default { "endTime" };

  String format() default "yyyy-MM-dd HH:mm:ss";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
