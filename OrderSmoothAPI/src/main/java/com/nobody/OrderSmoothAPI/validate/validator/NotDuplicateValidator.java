package com.nobody.OrderSmoothAPI.validate.validator;

import com.nobody.OrderSmoothAPI.validate.NotDuplicate;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotDuplicateValidator
  implements ConstraintValidator<NotDuplicate, List<Integer>> {

  @Override
  public boolean isValid(
    List<Integer> values,
    ConstraintValidatorContext context
  ) {
    if (values == null || values.size() == 0) {
      return true;
    }

    for (int i = 0; i < values.size(); i++) {
      for (int j = i + 1; j < values.size(); j++) {
        if (values.get(i).equals(values.get(j))) {
          return false;
        }
      }
    }

    return true;
  }
}
