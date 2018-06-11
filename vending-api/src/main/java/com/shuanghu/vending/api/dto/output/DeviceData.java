package com.shuanghu.vending.api.dto.output;

import com.shuanghu.vending.dao.enums.DeviceProduct;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceData {

  private String id;
  private String name;
  private String position;
  private String desc;
  private DeviceProduct productStatus;
}
