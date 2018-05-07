package com.shuanghu.vending.api.controller;

import com.shuanghu.vending.api.dto.output.OrderDetail;
import com.shuanghu.vending.api.dto.input.OrderParam;
import com.shuanghu.vending.api.service.OrderService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/order/pre")
  public OrderDetail createOrder(@RequestBody OrderParam orderParam){
    return orderService.createOrder(orderParam.getDeviceId(), orderParam.getProduct());
  }

  @GetMapping("/order/pre")
  public OrderDetail findOrder(@RequestParam(name = "deviceId")String deviceId){
    return orderService.getDetail(deviceId);
  }
}
