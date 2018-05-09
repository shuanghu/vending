package com.shuanghu.vending.api.dto.output;

import com.shuanghu.vending.dao.model.OrderInfo;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetail {
  private String deviceId;
  private String orderNo;
  private String body;
  private double totalAmount;
  private Date createTime;

  public OrderDetail(OrderInfo orderInfo){
    deviceId = orderInfo.getDeviceId();
    orderNo = orderInfo.getOrderNo();
    body = orderInfo.getBody();
    totalAmount = orderInfo.getTotalAmount();
    createTime = orderInfo.getCreateTime();
  }
}
