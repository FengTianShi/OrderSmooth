package com.nobody.OrderSmoothAPI.validate.validator;

import com.nobody.OrderSmoothAPI.validate.KeyNotDuplicate;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class KeyNotDuplicateValidator
  implements ConstraintValidator<KeyNotDuplicate, List<?>> {

  private String key;

  @Override
  public void initialize(KeyNotDuplicate constraintAnnotation) {
    this.key = constraintAnnotation.key();
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
      Object key1PropVal = beanWrapper.getPropertyValue(key);

      for (int j = i + 1; j < n; j++) {
        beanWrapper = new BeanWrapperImpl(values.get(j));
        Object key2PropVal = beanWrapper.getPropertyValue(key);

        try {
          if (beanWrapper.getPropertyType(key) == String.class) {
            String key1 = (String) key1PropVal;
            String key2 = (String) key2PropVal;

            if (key1.equals(key2)) {
              return false;
            }
          } else if (beanWrapper.getPropertyType(key) == Integer.class) {
            Integer key1 = (Integer) key1PropVal;
            Integer key2 = (Integer) key2PropVal;

            if (key1 == key2) {
              return false;
            }
          } else if (beanWrapper.getPropertyType(key) == Long.class) {
            Long key1 = (Long) key1PropVal;
            Long key2 = (Long) key2PropVal;

            if (key1 == key2) {
              return false;
            }
          }
        } catch (Exception e) {
          continue;
        }
      }
    }

    return true;
  }
}
