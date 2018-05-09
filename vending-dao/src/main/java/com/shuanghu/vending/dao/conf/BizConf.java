package com.shuanghu.vending.dao.conf;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:biz.properties"})
public class BizConf {

  //Default 90s.
  private static long effectiveValue = 90*1000;

  @Value("${order.effective}")
  public void setEffectiveValue(long second){
    effectiveValue = second * 1000;
  }
  /**
   * 订单有效的时间
   * @return
   */
  public static Date orderEffective(){
    return new Date(System.currentTimeMillis() - effectiveValue);
  }

  public static Date orderTimeout(){
    /**
     * 如果用户在选择商品后，90秒内没有付款，则认为该订单失效。
     */
    return new Date(System.currentTimeMillis() + effectiveValue);
  }
}
