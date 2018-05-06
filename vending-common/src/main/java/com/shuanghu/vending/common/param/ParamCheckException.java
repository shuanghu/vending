package com.shuanghu.vending.common.param;

public class ParamCheckException extends RuntimeException{
  enum ErrorType{
    REQUIRED, ERROR_VALUE, TOO_LONG, ALL_SPACE
  }

  String[] name;
  ErrorType type;
  String realValue;
  String expectedValue;
  int maxLen;

  static ParamCheckException requiredException(String[] names){
    ParamCheckException paramCheckException = new ParamCheckException();
    paramCheckException.name = names;
    paramCheckException.type = ErrorType.REQUIRED;
    return paramCheckException;
  }

  static ParamCheckException spaceException(String[] names){
    ParamCheckException paramCheckException = new ParamCheckException();
    paramCheckException.name = names;
    paramCheckException.type = ErrorType.ALL_SPACE;
    return paramCheckException;
  }

  static ParamCheckException tooLong(String[] names, int maxLen){
    ParamCheckException paramCheckException = new ParamCheckException();
    paramCheckException.name = names;
    paramCheckException.type = ErrorType.TOO_LONG;
    paramCheckException.maxLen = maxLen;
    return paramCheckException;
  }

  static ParamCheckException errorValue(String[] name, String real, String expected){
    ParamCheckException paramCheckException = new ParamCheckException();
    paramCheckException.name = name;
    paramCheckException.type = ErrorType.ERROR_VALUE;
    paramCheckException.expectedValue = expected;
    paramCheckException.realValue = real;
    return paramCheckException;
  }
}
