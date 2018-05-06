package com.shuanghu.vending.common.exception;

public class ExceptionResponse {
  private String msg;

  private String detailInfo;

  public ExceptionResponse(String msg, String detail){
    this.msg = msg;
    this.detailInfo = detail;
  }
  /**
   * 构造一个异常返回
   */
  public static ExceptionResponse build(VendingException e) {
    // ExceptionResponse exceptionResponse = new ExceptionResponse();
    return new ExceptionResponse(e.getMsg(), e.getMessage());
  }
}
