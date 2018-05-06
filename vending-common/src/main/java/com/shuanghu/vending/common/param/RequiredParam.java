package com.shuanghu.vending.common.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredParam {
  /**
   * 字段是否必选
   */
  boolean required() default true;

  /**
   * 是否递归
   */
  boolean recursive() default true;
}
