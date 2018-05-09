package com.shuanghu.vending.api.dto.input;

import com.shuanghu.vending.common.param.CheckRequestParam;
import com.shuanghu.vending.common.param.StringParam;
import com.shuanghu.vending.common.utils.IdUtils;
import com.shuanghu.vending.dao.model.OrderInfo;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品信息
 */
@Setter
@Getter
@CheckRequestParam
public class ProductParam {

  @StringParam
  private String id;
  /**
   * 商品数量
   */
  private int num;

  public OrderInfo toOrderInfo(String deviceId){
    OrderInfo orderInfo = new OrderInfo();
    orderInfo.setOrderNo(IdUtils.orderId());
    orderInfo.setCreateTime(new Date());
    orderInfo.setDeviceId(deviceId);

    return orderInfo;
  }
}
