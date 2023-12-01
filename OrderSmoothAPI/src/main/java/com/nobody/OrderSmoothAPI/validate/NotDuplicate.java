package com.nobody.OrderSmoothAPI.validate;

import com.nobody.OrderSmoothAPI.validate.validator.NotDuplicateValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotDuplicateValidator.class)
@Documented
public @interface NotDuplicate {
  String message() default "list element duplicate";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
