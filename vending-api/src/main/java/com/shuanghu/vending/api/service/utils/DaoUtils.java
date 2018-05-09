package com.shuanghu.vending.api.service.utils;

import com.shuanghu.vending.common.exception.NotFoundException;
import java.util.function.Function;

public class DaoUtils {
  public static <T> T findById(String id, Function<String, T> function){
    T t = function.apply(id);
    if (t == null){
      throw new NotFoundException("");
    }

    return t;
  }
}
