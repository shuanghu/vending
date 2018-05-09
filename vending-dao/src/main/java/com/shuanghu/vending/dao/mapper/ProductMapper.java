package com.shuanghu.vending.dao.mapper;

import com.shuanghu.vending.dao.model.Product;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface ProductMapper {
  /**
   * @see ProductMapperProvider#insert
   */
  @InsertProvider(type = ProductMapperProvider.class, method = "insert")
  int insert(@Param("product") Product product);

  /**
   * @see ProductMapperProvider#findByIds
   */
  @Results(value = {
      @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "price", column = "price", javaType = double.class, jdbcType = JdbcType.DOUBLE),
      @Result(property = "subject", column = "subject", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "body", column = "body", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "category", column = "category", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
      @Result(property = "modifyTime", column = "modify_time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
  })
  @SelectProvider(type = ProductMapperProvider.class, method = "findByIds")
  List<Product> findByIds(@Param("ids")List<String> ids);

  /**
   * @see ProductMapperProvider#findById
   */
  @Results(value = {
      @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "price", column = "price", javaType = double.class, jdbcType = JdbcType.DOUBLE),
      @Result(property = "subject", column = "subject", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "body", column = "body", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "category", column = "category", javaType = String.class, jdbcType = JdbcType.VARCHAR),
      @Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
      @Result(property = "modifyTime", column = "modify_time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
  })
  @SelectProvider(type = ProductMapperProvider.class, method = "findById")
  Product findById(@Param("id")String id);
}
