package com.shuanghu.vending.common.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionParam {
  /**
   * 字段是否必选
   *
   * Whether required field.
   */
  boolean required() default true;

  /**
   * 是否将空数组当成null
   *
   * Whether treat an empty array as null
   */
  boolean emptyAsNull() default true;

  // Whether to recursively deal with the elements in the collection
  boolean recursive() default true;

  // Whether the collection element is nullable
  boolean notNull() default true;

  /**
   * 字段默认名称
   */
  String[] name() default {};
}
