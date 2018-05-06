package com.shuanghu.vending.api.aop;

import com.shuanghu.vending.common.param.DtoParamCheck;
import com.shuanghu.vending.common.util.HttpUtil;
import com.shuanghu.vending.common.util.JsonUtil;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ResponseMonitor {
  private static Logger logger = LoggerFactory.getLogger(ResponseMonitor.class);

  /**
   * 性能监控
   *
   * Performance monitoring
   */
  @Around(value = "within(com.shuanghu.vending.api.controller..*) && !within(com.shuanghu.vending.api.controller.ExceptionHandlerController)")
  public Object responseTranslation(ProceedingJoinPoint point) throws Throwable {
    long startTime = System.currentTimeMillis();
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();

    HttpServletRequest request = attributes.getRequest();

    String method = request.getMethod();

    String bodyStr = null;
    String url;
    if (!"GET".equals(method)) {
      // Get method
      url = request.getRequestURI();

      Object[] params = point.getArgs();
      for (Object obj : params) {
        if (obj == null) {
          continue;
        }

        if (DtoParamCheck.check(obj)){
          bodyStr = JsonUtil.toJsonString(obj);
          break;
        }
      }
    } else {
      url = HttpUtil.getPathParam(request);
    }

    logger.info("Start time:{}, aop time:{}, Request method:{}, url:{}, body:{}",
        startTime, System.currentTimeMillis() - startTime, method, url, bodyStr);

    Object response = point.proceed();

    // Cost time of return.
    logger.info("Performance monitoring, start time:{}, status code:{}, cost time:{} ms ", startTime,
        attributes.getResponse().getStatus(),
        System.currentTimeMillis() - startTime);

    return response;
  }
}
