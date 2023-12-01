package com.nobody.OrderSmoothAPI.validate;

import com.nobody.OrderSmoothAPI.validate.validator.KeyNotDuplicateValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = KeyNotDuplicateValidator.class)
@Documented
public @interface KeyNotDuplicate {
  String message() default "list key duplicate";

  String key() default "key";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
