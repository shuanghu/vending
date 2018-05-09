package com.shuanghu.vending.dao.model;


import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderInfo {

  private long id;
  private String orderNo;
  private double totalAmount;
  private String subject;
  private Date createTime;
  private String body;
  private String deviceId;
  private int status;
}
