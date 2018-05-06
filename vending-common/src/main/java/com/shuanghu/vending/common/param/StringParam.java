package com.shuanghu.vending.common.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringParam {
  /**
   * 字段是否必选
   *
   * Whether require.
   */
  boolean required() default true;
  int maxLen() default 65525;

  /**
   * 字段默认名称
   */
  String[] name() default {};
  /**
   * 是否将空字符串看成null，true，表示字段必选时，空字符串会报错，false，则认为合法
   *
   * 默认都为true, 空字符串看成未传值
   */
  boolean emptyAsNull() default true;

  /**
   * 字段不能只包含空格
   */
  boolean trim() default true;
}
