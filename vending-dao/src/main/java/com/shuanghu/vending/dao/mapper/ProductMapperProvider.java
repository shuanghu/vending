package com.shuanghu.vending.dao.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.ibatis.jdbc.SQL;

public class ProductMapperProvider {
  private final static String TABLE_NAME = "product";

  public String insert(){
    return new SQL() {
      {
        INSERT_INTO(TABLE_NAME);
        VALUES("id", "#{product.id}");
        VALUES("name", "#{product.name}");
        VALUES("subject", "#{product.subject}");
        VALUES("body", "#{product.body}");
        VALUES("price", "#{product.price}");
        VALUES("category", "#{product.category}");
        VALUES("create_time", "#{product.createTime}");
        VALUES("modify_time", "#{product.modifyTime}");
      }
    }.toString();
  }

  @SuppressWarnings("unchecked")
  public String findByIds(Map<String, Object> parameter){
    List<String> ids = (List<String>) parameter.get("ids");

    return new SQL() {
      {
        SELECT("*");
        FROM(TABLE_NAME);
        WHERE("id in '"+ids.stream().collect(Collectors.joining("', '"))+"'");
      }
    }.toString();
  }

  public String findById(){
    return new SQL() {
      {
        SELECT("*");
        FROM(TABLE_NAME);
        WHERE("id = #{id}");
      }
    }.toString();
  }
}
