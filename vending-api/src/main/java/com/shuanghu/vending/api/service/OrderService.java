package com.shuanghu.vending.api.service;

import com.shuanghu.vending.api.dto.output.OrderDetail;
import com.shuanghu.vending.api.dto.input.ProductParam;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  public OrderDetail createOrder(String deviceId, ProductParam param){
    return null;
  }

  public OrderDetail getDetail(String deviceId){
    return new OrderDetail();
  }
}
