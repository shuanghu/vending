package com.shuanghu.vending.api.service;

import com.shuanghu.vending.api.dto.output.OrderDetail;
import com.shuanghu.vending.api.dto.input.ProductParam;
import com.shuanghu.vending.api.service.utils.DaoUtils;
import com.shuanghu.vending.common.exception.NotFoundException;
import com.shuanghu.vending.common.utils.IdUtils;
import com.shuanghu.vending.dao.conf.BizConf;
import com.shuanghu.vending.dao.mapper.OrderInfoMapper;
import com.shuanghu.vending.dao.mapper.ProductMapper;
import com.shuanghu.vending.dao.model.OrderInfo;
import com.shuanghu.vending.dao.model.Product;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Autowired
  private OrderInfoMapper orderInfoMapper;
  @Autowired
  private ProductMapper productMapper;

  public OrderDetail createOrder(String deviceId, ProductParam param){
    Product product = DaoUtils.findById(param.getId(),productMapper::findById);

    OrderInfo orderInfo = new OrderInfo();
    orderInfo.setOrderNo(IdUtils.orderId());
    orderInfo.setCreateTime(new Date());
    orderInfo.setDeviceId(deviceId);
    orderInfo.setTotalAmount(product.getPrice());

    orderInfoMapper.insert(orderInfo);
    return new OrderDetail(orderInfo);
  }

  public OrderDetail getDetail(String deviceId){
    List<OrderInfo> orderInfoList = orderInfoMapper.findEffectiveByDevice(deviceId, BizConf.orderEffective());
    if (CollectionUtils.isEmpty(orderInfoList)){
      // 404
      throw new NotFoundException("设备无订单信息");
    }

    return new OrderDetail(orderInfoList.get(0));
  }

  @Scheduled(fixedRate = 1000*60*5)
  public void updateOrder(){
    // Updated every 5 minutes
    LOGGER.info("Begin update timeout order");
    orderInfoMapper.updateTimeout(BizConf.orderEffective());
    LOGGER.info("End update timeout order");
  }
}
