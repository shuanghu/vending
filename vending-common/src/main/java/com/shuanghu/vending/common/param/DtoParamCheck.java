package com.shuanghu.vending.common.param;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtoParamCheck {

  private static Logger logger = LoggerFactory.getLogger(DtoParamCheck.class);

  /**
   * 自定义校验函数
   */
  static boolean customCheck(Object param){
    if (param == null){
      return false;
    }
    Class classObj = param.getClass();
    Method[] methods = classObj.getDeclaredMethods();
    for (Method method :methods){
      CustomCheck checkMethod = method.getAnnotation(CustomCheck.class);
      if (checkMethod == null) {
        continue;
      }

      try {
        method.setAccessible(true);
        method.invoke(param);
      } catch (IllegalAccessException | InvocationTargetException e) {
        if (e.getCause() instanceof RuntimeException) {
          throw (RuntimeException) e.getCause();
        }
        logger.info("Param check error.", e);

        return false;
      }

      return !checkMethod.checkProperty();
    }
    return false;
  }

  private static String[] fieldName(String[] customName, String parent, String name){
    if (customName == null || customName.length == 0){
      if (StringUtils.isEmpty(parent)){
        return new String[]{name};
      }
      return new String[]{parent + "." + name};
    }

    return customName;
  }

  static void innerCheckEnum(Field field, Object value, String parent) {
    EnumParam enumCheck = field.getAnnotation(EnumParam.class);
    if (enumCheck == null) {
      // No check
      return;
    }

    if (value == null){
      if (enumCheck.required()){
        throw ParamCheckException.requiredException(fieldName(enumCheck.name(), parent, field.getName()));
      }
      return;
    }

    String[] values = enumCheck.value();
    if (values.length == 0) {
      return;
    }
    @SuppressWarnings("unchecked")
    Class<Enum> clz = (Class<Enum>) field.getType();
    if (Arrays.stream(values).noneMatch(v -> value.equals(Enum.valueOf(clz, v)))) {
      throw ParamCheckException.errorValue(fieldName(enumCheck.name(), parent, field.getName()),
          value.toString(), array2String(values));
    }
  }

  static void checkString(Field field, Object value, String parent){
    // check string
    StringParam param = field.getAnnotation(StringParam.class);
    if (param == null) {
      // No check
      return;
    }

    if (value == null) {
      if (param.required()) {
        // 必选参数，不能为null
        throw ParamCheckException.requiredException(fieldName(param.name(), parent, field.getName()));
      }
      return;
    }

    String strVal = (String) value;

    if (strVal.isEmpty()){
      if (param.required() && param.emptyAsNull()){
        throw ParamCheckException.requiredException(fieldName(param.name(), parent, field.getName()));
      }
      return;
    }

    // check length
    if (param.trim() && strVal.trim().isEmpty()) {
      throw ParamCheckException.spaceException(fieldName(param.name(), parent, field.getName()));
    }
    int maxLen = param.maxLen();
    if (strVal.length() > maxLen) {
      throw ParamCheckException.tooLong(fieldName(param.name(), parent, field.getName()), maxLen);
    }
  }

  static void checkCollection(Field field, Object value, String parent) {
    CollectionParam collectionCheck = field.getAnnotation(CollectionParam.class);
    if (collectionCheck == null) {
      return;
    }
    if (value == null) {
      if (collectionCheck.required()) {
        throw ParamCheckException.requiredException(fieldName(collectionCheck.name(), parent, field.getName()));
      }

      return;
    }

    // check collection
    Collection collection = (Collection) value;
    if (CollectionUtils.isEmpty(collection)) {
      if (collectionCheck.emptyAsNull() && collectionCheck.required()) {
        throw ParamCheckException
            .requiredException(fieldName(collectionCheck.name(), parent, field.getName()));
      }

      return;
    }

    for (Object obj : collection) {
      if (obj == null) {
        if (collectionCheck.notNull()) {
          throw ParamCheckException
              .requiredException(fieldName(collectionCheck.name(), parent, field.getName()));
        }
        continue;
      }
      if (collectionCheck.recursive()) {
        try {
          paramCheck(obj, parent + "." + field.getName());
        }catch (ParamCheckException e){
          if (collectionCheck.name().length > 0) {
            e.name = collectionCheck.name();
          }
          throw e;
        }
      }
    }
  }

  private static String nameJoin(String parent, Field field) {
    if (StringUtils.isEmpty(parent)) {
      return field.getName();
    }

    return parent + "." + field.getName();
  }

  /**
   * Check field.
   *
   * @param body body
   */
  static void checkField(Object body, String parent) {
    Class classObj = body.getClass();

    Field fields[] = classObj.getDeclaredFields();

    for (Field field : fields) {
      if (field.getType().isPrimitive()) {
        // Build-in type
        continue;
      } else if (field.getType().isEnum()) {
        // Enum
        innerCheckEnum(field, getFieldValue(body, field), parent);
      } else if (field.getType().isAssignableFrom(String.class)) {
        // String
        checkString(field, getFieldValue(body, field), parent);
      } else if (Collection.class.isAssignableFrom(field.getType())) {
        // Collection
        checkCollection(field, getFieldValue(body, field), parent);
      }

      RequiredParam param = field.getAnnotation(RequiredParam.class);
      if (param != null) {
        Object value = getFieldValue(body, field);
        if (value == null && param.required()) {
          throw ParamCheckException
              .requiredException(fieldName(null, parent, field.getName()));
        }

        if (value != null && param.recursive()) {
          paramCheck(value, nameJoin(parent, field));
        }
      }
    }
  }

  public static boolean check(Object param){
    CheckRequestParam checkRequestParam = param.getClass().getAnnotation(CheckRequestParam.class);
    if (checkRequestParam == null){
      return false;
    }

    paramCheck(param, null);

    return true;
  }

  /**
   * Check param.
   *
   * @param param param
   */
  private static void paramCheck(Object param, String parent) {
    if (!customCheck(param)) {
      // do not have custom function, check properties directly
      checkField(param, parent);
    }
  }

  /**
   * Check param.
   *
   * @param param param
   */
  public static void paramCheck(Object param) {
    paramCheck(param, null);
  }

  /**
   * Get field value
   */
  static Object getFieldValue(Object body, Field field) {
    try {
      field.setAccessible(true);

      return field.get(body);
    } catch (Exception e) {
      logger.info("Param check error.", e);
    }
    return null;
  }

//  private static DefaultMessageSourceResolvable createMsg(String fieldName,
//      StringParam stringCheck) {
//    String[] names = stringCheck.name();
//    DefaultMessageSourceResolvable msg = null;
//    for (String name : names) {
//      if (msg == null) {
//        msg = new DefaultMessageSourceResolvable(fieldName);
//      } else {
//        msg = new DefaultMessageSourceResolvable(new String[]{name}, new Object[]{msg});
//      }
//    }
//
//    return msg;
//  }

  private static String array2String(String[] arr) {
    return "["+Arrays.stream(arr).collect(Collectors.joining(", "))+"]";
  }
}
