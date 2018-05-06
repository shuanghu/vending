package com.shuanghu.vending.common.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function annotation used to check param
 * 用于校验参数的函数注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCheck {
  /**
   * 自定义函数执行后，是否继续属性校验，默认为false，不校验，提高效率
   *
   * Whether to continue property verification after the function is executed
   */
  boolean checkProperty() default false;
}
