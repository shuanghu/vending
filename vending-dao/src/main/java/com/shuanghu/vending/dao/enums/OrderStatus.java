package com.shuanghu.vending.dao.enums;

public enum OrderStatus {
  NORMAL, TIMEOUT, CANCEL;

  public int value(){
    return ordinal();
  }
}
