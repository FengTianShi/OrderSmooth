package com.nobody.OrderSmoothAPI.validate;

import com.nobody.OrderSmoothAPI.validate.validator.NotOverlapValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotOverlapValidator.class)
@Documented
public @interface NotOverlap {
  String message() default "time interval overlap";

  String startTime() default "startTime";

  String endTime() default "endTime";

  String format() default "yyyy-MM-dd HH:mm:ss";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
