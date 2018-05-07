package com.shuanghu.vending.api.dto.input;

import com.shuanghu.vending.common.param.CheckRequestParam;
import com.shuanghu.vending.common.param.StringParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@CheckRequestParam
public class ProductParam {

  @StringParam
  private String id;
  private int num;
}
