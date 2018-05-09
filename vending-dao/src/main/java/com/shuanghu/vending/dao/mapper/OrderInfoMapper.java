package com.shuanghu.vending.dao.mapper;

import com.shuanghu.vending.dao.model.OrderInfo;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface OrderInfoMapper {

  /**
   * @see OrderInfoMapperProvider#insert
   */
  @InsertProvider(type = OrderInfoMapperProvider.class, method = "insert")
  int insert(@Param("order") OrderInfo order);

  /**
   * @see OrderInfoMapperProvider#findEffectiveByDevice
   */
  @Results(value = {
      @Result(property = "id", column = "id", id = true, javaType = Long.class, jdbcType = JdbcType.BIGINT),
      @Result(property = "orderNo", column = "order_no", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "totalAmount", column = "total_amount", javaType = double.class, jdbcType = JdbcType.DOUBLE),
      @Result(property = "subject", column = "subject", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "body", column = "body", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "deviceId", column = "device_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "status", column = "status", javaType = int.class, jdbcType = JdbcType.INTEGER),
      @Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
  })
  @SelectProvider(type = OrderInfoMapperProvider.class, method = "findEffectiveByDevice")
  List<OrderInfo> findEffectiveByDevice(@Param("deviceId") String deviceId, @Param("createTime")Date createTime);

  /**
   * @see OrderInfoMapperProvider#updateTimeout
   * @param time
   * @return
   */
  @UpdateProvider(type = OrderInfoMapperProvider.class, method = "updateTimeout")
  int updateTimeout(@Param("time")Date time);
}
