package com.shuanghu.vending.common.exception;

public class VendingException extends RuntimeException{
  private String msg;

  public VendingException(String msg){
    this.msg = msg;
  }

  public VendingException(Throwable cause){
    super(cause);
  }

  public String getMsg(){
    return msg;
  }
}
