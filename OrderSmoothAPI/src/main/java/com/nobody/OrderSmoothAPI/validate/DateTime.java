package com.nobody.OrderSmoothAPI.validate;

import com.nobody.OrderSmoothAPI.validate.validator.DateTimeValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeValidator.class)
@Documented
public @interface DateTime {
  String message() default "invalid date time";

  String format() default "yyyy-MM-dd HH:mm:ss";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
