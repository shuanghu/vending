package com.shuanghu.vending.api.controller;

import com.shuanghu.vending.common.exception.ExceptionResponse;
import com.shuanghu.vending.common.exception.NotFoundException;
import com.shuanghu.vending.common.exception.ServerException;
import com.shuanghu.vending.common.exception.VendingException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理类
 *
 */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  /**
   * 默认异常处理
   * @param req
   * @param e
   * @return
   */
  @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  ResponseEntity handleAllException(HttpServletRequest req, Exception e) {
    LOGGER.error("INTERNAL_SERVER_ERROR", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionResponse.build(ServerException.serverException(e)));
  }

  /**
   * 404类型异常
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  ResponseEntity handleNotFoundException(HttpServletRequest req, NotFoundException e) {
    LOGGER.error("NOT_FOUND", e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ExceptionResponse.build(e));
  }

  /**
   * 404类型异常
   */
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(VendingException.class)
  ResponseEntity handleVendingException(HttpServletRequest req, VendingException e) {
    LOGGER.error("Vending exception", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionResponse.build(e));
  }
}
