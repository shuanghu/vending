package com.shuanghu.vending.dao.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {

  private String id;
  private String name;
  private String body;
  private double price;
  private String category;
  private Date createTime;
  private Date modifyTime;
}
