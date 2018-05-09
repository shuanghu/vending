package com.shuanghu.vending.api.service;

import com.shuanghu.vending.api.dto.output.OrderDetail;
import com.shuanghu.vending.api.dto.input.ProductParam;
import com.shuanghu.vending.dao.conf.BizConf;
import com.shuanghu.vending.dao.mapper.OrderInfoMapper;
import com.shuanghu.vending.dao.model.OrderInfo;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  private OrderInfoMapper orderInfoMapper;

  public OrderDetail createOrder(String deviceId, ProductParam param){
    OrderInfo orderInfo = param.toOrderInfo(deviceId);
    orderInfoMapper.insert(orderInfo);
    return new OrderDetail(orderInfo);
  }

  public OrderDetail getDetail(String deviceId){
    List<OrderInfo> orderInfoList = orderInfoMapper.findEffectiveByDevice(deviceId, BizConf.orderTimeout());
    if (CollectionUtils.isEmpty(orderInfoList)){
      // 404
      return null;
    }

    return new OrderDetail(orderInfoList.get(0));
  }
}
