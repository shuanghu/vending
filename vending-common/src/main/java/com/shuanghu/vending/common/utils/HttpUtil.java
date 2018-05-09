package com.shuanghu.vending.common.utils;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 */
public class HttpUtil {

  public static String getPathParam(HttpServletRequest request){
    StringBuilder builder = new StringBuilder();

    builder.append(request.getRequestURI()).append("?");
    Enumeration<String> parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()){
      String name = parameterNames.nextElement();
      builder.append(name).append("=").append(request.getParameter(name)).append("&");
    }
    return builder.toString();
  }

}
