package com.shuanghu.vending.dao.mapper;

import com.shuanghu.vending.dao.model.OrderInfo;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface OrderInfoMapper {

  /**
   * @see OrderInfoMapperProvider#insert
   */
  @InsertProvider(type = OrderInfoMapperProvider.class, method = "insert")
  int insert(@Param("order") OrderInfo order);
}
