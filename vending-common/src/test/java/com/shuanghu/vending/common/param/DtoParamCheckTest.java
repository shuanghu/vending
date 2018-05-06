package com.shuanghu.vending.common.param;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class DtoParamCheckTest {

  @Test
  public void customCheck() {
    assertFalse(DtoParamCheck.customCheck(null));

    // 没有自定义校验函数
    assertFalse(DtoParamCheck.customCheck(new Object() {
    }));

    // 有函数，但校验成功后，不校验属性
    assertTrue(DtoParamCheck.customCheck(new Object(){
      @CustomCheck
      void check(){
      }
    }));

    // private函数
    assertTrue(DtoParamCheck.customCheck(new Object(){
      @CustomCheck
      private void check(){
      }
    }));

    // 有函数，但校验成功后，校验属性
    assertFalse(DtoParamCheck.customCheck(new Object(){
      @CustomCheck(checkProperty = true)
      void check(){
      }
    }));

    // 有函数，抛RunTimeException
    try{
      DtoParamCheck.customCheck(new Object(){
        @CustomCheck
        void check(){
          throw new RuntimeException("");
        }
      });
      fail();
    }catch (RuntimeException e){
      // OK
    }
    try{
      DtoParamCheck.customCheck(new Object(){
        @CustomCheck(checkProperty = true)
        void check(){
          throw new RuntimeException("");
        }
      });
      fail();
    }catch (RuntimeException e){
      // OK
    }
  }
}