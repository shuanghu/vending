package com.shuanghu.vending.common.param;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.shuanghu.vending.common.param.ParamCheckException.ErrorType;
import java.lang.reflect.Field;
import org.junit.Test;

public class EnumParamCheckTest {

  public enum EnumTestValue {
    V1, V2
  }

  @Test
  public void errorValueTest() throws NoSuchFieldException {
    Object object = new Object() {
      @EnumParam({"V1"})
      private EnumTestValue value = EnumTestValue.V2;
    };

    Field field = object.getClass().getDeclaredField("value");

    try {
      DtoParamCheck.innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), FIELD_NAME);
      fail();
    } catch (ParamCheckException e) {
      assertEquals(e.name[0], FIELD_NAME+".value");
      assertEquals(e.type, ErrorType.ERROR_VALUE);
      assertEquals(e.realValue, "V2");
      assertEquals(e.expectedValue, "[V1]");
    }
  }

  @Test
  public void rightValue() throws NoSuchFieldException {
    Object object = new Object() {
      @EnumParam({"V1", "V2"})
      private EnumTestValue value = EnumTestValue.V1;
    };

    Field field = object.getClass().getDeclaredField("value");
    DtoParamCheck
        .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), field.getName());
  }

  @Test
  public void noCheck() throws NoSuchFieldException {
    {
      Object object = new Object() {
        private EnumTestValue value = null;
      };
      Field field = object.getClass().getDeclaredField("value");
      DtoParamCheck
          .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), field.getName());
    }

    {
      Object object = new Object() {
        private EnumTestValue value = EnumTestValue.V1;
      };
      Field field = object.getClass().getDeclaredField("value");
      DtoParamCheck
          .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), field.getName());
    }
  }

  @Test
  public void testNull() throws NoSuchFieldException {
    {
      Object object = new Object() {
        @EnumParam(required = false)
        private EnumTestValue value = null;
      };
      Field field = object.getClass().getDeclaredField("value");
      DtoParamCheck
          .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), field.getName());
    }

    {
      // 只做必选校验，不关注值
      Object object = new Object() {
        @EnumParam(required = false)
        private EnumTestValue value = EnumTestValue.V1;
      };
      Field field = object.getClass().getDeclaredField("value");
      DtoParamCheck
          .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), field.getName());
    }
    {
      // 只做必选校验，不关注值
      Object object = new Object() {
        @EnumParam(required = false)
        private EnumTestValue value = EnumTestValue.V2;
      };
      Field field = object.getClass().getDeclaredField("value");
      DtoParamCheck
          .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), field.getName());
    }
  }

  @Test
  public void testNotNull() throws NoSuchFieldException {
    {
      Object object = new Object() {
        @EnumParam
        private EnumTestValue value = null;
      };
      Field field = object.getClass().getDeclaredField("value");
      try {
        DtoParamCheck
            .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), FIELD_NAME);
        fail();
      } catch (ParamCheckException e) {
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], FIELD_NAME+".value");
      }
    }
    {
      Object object = new Object() {
        @EnumParam
        private EnumTestValue value = EnumTestValue.V1;
      };
      Field field = object.getClass().getDeclaredField("value");
      DtoParamCheck
          .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), FIELD_NAME);
    }
  }

  @Test
  public void testName() throws NoSuchFieldException {
    {
      Object object = new Object() {
        @EnumParam(name = {"name1", "name2"})
        private EnumTestValue value = null;
      };
      Field field = object.getClass().getDeclaredField("value");
      try {
        DtoParamCheck
            .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), FIELD_NAME);
        fail();
      } catch (ParamCheckException e) {
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], "name1");
        assertEquals(e.name[1], "name2");
      }
    }

    {
      Object object = new Object() {
        @EnumParam(name = {"name1", "name2"}, value = "V1")
        private EnumTestValue value = EnumTestValue.V2;
      };
      Field field = object.getClass().getDeclaredField("value");
      try {
        DtoParamCheck
            .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), FIELD_NAME);
        fail();
      } catch (ParamCheckException e) {
        assertEquals(e.type, ErrorType.ERROR_VALUE);
        assertEquals(e.name[0], "name1");
        assertEquals(e.name[1], "name2");
      }
    }

    {
      Object object = new Object() {
        @EnumParam(value = "V1")
        private EnumTestValue value = EnumTestValue.V2;
      };
      Field field = object.getClass().getDeclaredField("value");
      try {
        DtoParamCheck
            .innerCheckEnum(field, DtoParamCheck.getFieldValue(object, field), null);
        fail();
      } catch (ParamCheckException e) {
        assertEquals(e.type, ErrorType.ERROR_VALUE);
        assertEquals(e.name[0], "value");
      }
    }
  }

  private static final String FIELD_NAME = "field";
}
