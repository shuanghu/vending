package com.shuanghu.vending.common.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 枚举值校验, 默认表示属性为必选
 *
 * Check value of enum.The default is required.
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumParam {

  /**
   * 自定义字段名称
   */
  String[] name() default {};
  /**
   * 枚举值范围, 默认为全部
   */
  String[] value() default {};

  /**
   * 字段是否必选
   */
  boolean required() default true;
}
