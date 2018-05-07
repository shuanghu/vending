package com.shuanghu.vending.api.dto.output;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetail {
  private String deviceId;
  private String orderNo;
  private double totalAmount;
  private Date createTime;
}
