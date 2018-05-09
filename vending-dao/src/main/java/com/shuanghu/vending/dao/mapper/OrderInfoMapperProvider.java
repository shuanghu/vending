package com.shuanghu.vending.dao.mapper;

import com.shuanghu.vending.dao.enums.OrderStatus;
import org.apache.ibatis.jdbc.SQL;

public class OrderInfoMapperProvider {

  private final static String TABLE_NAME = "order_info";

  public String insert(){
    return new SQL() {
      {
        INSERT_INTO(TABLE_NAME);
        VALUES("order_no", "#{order.orderNo}");
        VALUES("total_amount", "#{order.totalAmount}");
        VALUES("subject", "#{order.subject}");
        VALUES("create_time", "#{order.createTime}");
        VALUES("body", "#{order.body}");
        VALUES("device_id", "#{order.deviceId}");
        VALUES("status", "#{order.status}");
      }
    }.toString();
  }

  public String findEffectiveByDevice(){
    return new SQL() {
      {
        SELECT("*");
        FROM(TABLE_NAME);
        WHERE("device_id = #{deviceId}");
        WHERE("create_time >= #{createTime}");
        // 只读取待付款的订单
        WHERE("status = "+ OrderStatus.NORMAL.value());
        // 如果有多个订单，则以最新的订单为准
        ORDER_BY("create_time desc");
      }
    }.toString();
  }

  public String updateTimeout(){
    return new SQL() {
      {
        UPDATE(TABLE_NAME);
        SET("status = "+OrderStatus.TIMEOUT.value());
        WHERE("create_time < #{time}");
      }
    }.toString();
  }
}
