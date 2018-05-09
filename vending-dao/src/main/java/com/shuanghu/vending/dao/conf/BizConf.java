package com.shuanghu.vending.dao.conf;

import java.util.Date;

public class BizConf {
  public static Date orderTimeout(){
    /**
     * 如果用户在选择商品后，90秒内没有付款，则认为该订单失效。
     */
    return new Date(System.currentTimeMillis() + 1000*90);
  }
}
