package com.shuanghu.vending.common.param;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

import com.shuanghu.vending.common.param.ParamCheckException.ErrorType;

public class StringParamCheckTest {
  private static final String FIELD_NAME = "field";

  @Test
  public void nullTest() throws NoSuchFieldException {
    {
      Object object = new Object(){
        // 无注解
        private String noAnnotation;
      };
      Field field = object.getClass().getDeclaredField("noAnnotation");
      DtoParamCheck.checkString(field, null, FIELD_NAME);
      DtoParamCheck.checkString(field, "1", FIELD_NAME);
    }

    {
      Object object = new Object(){
        @StringParam
        private String value;
      };

      Field field = object.getClass().getDeclaredField("value");
      DtoParamCheck.checkString(field, "Not empty", FIELD_NAME);
      try {
        DtoParamCheck.checkString(field, null, FIELD_NAME);
        fail();
      }catch (ParamCheckException e){
        assertEquals(e.name[0], FIELD_NAME+"."+"value");
        assertEquals(e.type, ErrorType.REQUIRED);
      }
    }

    {
      Object obj = new Object(){
        private String value = null;
      };
      Field field = obj.getClass().getDeclaredField("value");
      DtoParamCheck.checkString(field, DtoParamCheck.getFieldValue(obj, field), FIELD_NAME);
      DtoParamCheck.checkString(field, "1", FIELD_NAME);
    }

    {
      Object obj = new Object(){
        @StringParam(required = false)
        private String value = null;
      };
      Field field = obj.getClass().getDeclaredField("value");
      DtoParamCheck.checkString(field, DtoParamCheck.getFieldValue(obj, field), FIELD_NAME);
      // Have value
      DtoParamCheck.checkString(field, "1", FIELD_NAME);
    }
  }

  @Test
  public void testTooLong() throws NoSuchFieldException {
    {
      Object obj = new Object(){
        @StringParam(required = false, maxLen = 3)
        private String value = null;
      };

      Field field = obj.getClass().getDeclaredField("value");
      try {
        DtoParamCheck.checkString(field, "1234", FIELD_NAME);
        fail();
      }catch (ParamCheckException e){
        assertEquals(e.type, ErrorType.TOO_LONG);
        assertEquals(e.maxLen, 3);
        assertEquals(e.name[0], FIELD_NAME+".value");
      }
    }
  }

  @Test
  public void testDefault() throws NoSuchFieldException {
    {
      Object obj = new Object(){
        @StringParam
        private String value = null;
      };

      Field field = obj.getClass().getDeclaredField("value");
      try {
        // parent is null
        DtoParamCheck.checkString(field, null, null);
        fail();
      }catch (ParamCheckException e){
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], "value");
      }

      try {
        // null
        DtoParamCheck.checkString(field, null, FIELD_NAME);
        fail();
      }catch (ParamCheckException e){
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], FIELD_NAME+".value");
      }

      try {
        // empty
        DtoParamCheck.checkString(field, "", FIELD_NAME);
        fail();
      }catch (ParamCheckException e){
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], FIELD_NAME+".value");
      }

      try {
        // space
        DtoParamCheck.checkString(field, "  ", FIELD_NAME);
        fail();
      }catch (ParamCheckException e){
        assertEquals(e.type, ErrorType.ALL_SPACE);
        assertEquals(e.name[0], FIELD_NAME+".value");
      }

      DtoParamCheck.checkString(field, "1", FIELD_NAME);
    }
  }
}
