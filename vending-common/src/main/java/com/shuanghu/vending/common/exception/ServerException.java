package com.shuanghu.vending.common.exception;

public class ServerException extends VendingException{
  public ServerException(Throwable cause) {
    super(cause);
  }

  public static ServerException serverException(Throwable cause) {
    return new ServerException(cause);
  }
}
