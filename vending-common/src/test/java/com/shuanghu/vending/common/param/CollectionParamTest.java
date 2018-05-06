package com.shuanghu.vending.common.param;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import com.shuanghu.vending.common.param.ParamCheckException.ErrorType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

public class CollectionParamTest {

  private static final String FIELD_NAME = "field";

  @Setter
  @Getter
  public static class ParamObject {

    @StringParam
    private String notEmpty;
  }

  @Test
  public void defaultTest() throws NoSuchFieldException {
    Object object = new Object() {
      @CollectionParam
      private List<ParamObject> value;
    };

    Field field = object.getClass().getDeclaredField("value");

    try {
      DtoParamCheck.checkCollection(field, null, FIELD_NAME);
      fail();
    } catch (ParamCheckException e) {
      assertEquals(e.type, ErrorType.REQUIRED);
      assertEquals(e.name[0], FIELD_NAME + "." + field.getName());
    }

    try {
      DtoParamCheck.checkCollection(field, new ArrayList<>(), FIELD_NAME);
      fail();
    } catch (ParamCheckException e) {
      assertEquals(e.type, ErrorType.REQUIRED);
      assertEquals(e.name[0], FIELD_NAME + "." + field.getName());
    }

    {
      List<ParamObject> list = new ArrayList<>();
      list.add(null);
      try {
        DtoParamCheck.checkCollection(field, list, FIELD_NAME);
        fail();
      } catch (ParamCheckException e) {
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], FIELD_NAME + "." + field.getName());
      }
    }

    {
      List<ParamObject> list = new ArrayList<>();
      list.add(new ParamObject());
      try {
        DtoParamCheck.checkCollection(field, list, FIELD_NAME);
        fail();
      } catch (ParamCheckException e) {
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], FIELD_NAME + "." + field.getName() + ".notEmpty");
      }
    }
  }

  @Test
  public void testName() throws NoSuchFieldException {
    Object obj = new Object() {
      @CollectionParam(name = {"name1", "name2"})
      private List<ParamObject> value;
    };

    Field field = obj.getClass().getDeclaredField("value");

    {
      List<ParamObject> list = new ArrayList<>();
      list.add(new ParamObject());
      try {
        DtoParamCheck.checkCollection(field, list, FIELD_NAME);
        fail();
      } catch (ParamCheckException e) {
        assertEquals(e.type, ErrorType.REQUIRED);
        assertEquals(e.name[0], "name1");
        assertEquals(e.name[1], "name2");
      }
    }
  }

  @Test
  public void testEmpty() throws NoSuchFieldException {
    Object obj = new Object() {
      @CollectionParam(emptyAsNull = false)
      private List<ParamObject> value;
    };
    Field field = obj.getClass().getDeclaredField("value");

    DtoParamCheck.checkCollection(field, new ArrayList<>(), FIELD_NAME);
  }

  @Test
  public void testNull() throws NoSuchFieldException {
    Object obj = new Object() {
      @CollectionParam(notNull = false)
      private List<ParamObject> value;
    };
    Field field = obj.getClass().getDeclaredField("value");

    List<ParamObject> list = new ArrayList<>();

    try {
      DtoParamCheck.checkCollection(field, list, FIELD_NAME);
      fail();
    } catch (ParamCheckException e) {
      assertEquals(e.name[0], FIELD_NAME + "." + field.getName());
      assertEquals(e.type, ErrorType.REQUIRED);
    }

    list.add(null);
    DtoParamCheck.checkCollection(field, list, FIELD_NAME);
  }

  @Test
  public void recursiveTest() throws NoSuchFieldException {
    Object obj = new Object() {
      @CollectionParam(recursive = false)
      private List<ParamObject> value;
    };

    Field field = obj.getClass().getDeclaredField("value");
    {
      List<ParamObject> list = new ArrayList<>();
      list.add(new ParamObject());
      DtoParamCheck.checkCollection(field, list, FIELD_NAME);
    }
  }

//  @Test
//  public void notNullValueTest() throws NoSuchFieldException {
//    CollectionParamData collectionParamData = new CollectionParamData();
//
//    {
//      // not null
//      try {
//        Field field = collectionParamData.getClass().getDeclaredField("params");
//        DtoParamCheck.checkCollection(field, collectionParamData.getParams(), "params");
//        assert false;
//      } catch (ParamException e) {
//        assertEquals(e.getMsgKey(),
//            "com.baifendian.bi.common.exception.ParameterException.empty");
//      }
//    }
//
//    {
//      List<ParamObject> paramObjectList = new ArrayList<>();
//      paramObjectList.add(null);
//      collectionParamData.setParams(paramObjectList);
//      try {
//        Field field = collectionParamData.getClass().getDeclaredField("params");
//        DtoParamCheck.checkCollection(field, collectionParamData.getParams(), "");
//        assert false;
//      } catch (ParamException e) {
//        assertEquals(e.getMsgKey(),
//            "com.baifendian.bi.common.exception.ParameterException.empty");
//      }
//    }
//
//    {
//      ParamObject paramObject = new ParamObject();
//      paramObject.setName("name");
//      paramObject.setNotEmpty(null);
//
//      List<ParamObject> paramObjectList = new ArrayList<>();
//      paramObjectList.add(paramObject);
//      collectionParamData.setParams(paramObjectList);
//
//      try {
//        Field field = collectionParamData.getClass().getDeclaredField("params");
//        DtoParamCheck.checkCollection(field, collectionParamData.getParams(), "param");
//        assert false;
//      } catch (ParamException e) {
//        Object[] args = e.getArgs();
//        assertEquals("param.notEmpty", args[0]);
//        assertEquals(e.getMsgKey(),
//            "com.baifendian.bi.common.exception.ParameterException.empty");
//      }
//
//      // Do not check recursive
//      collectionParamData.setNotRecursive(paramObjectList);
//      Field field = collectionParamData.getClass().getDeclaredField("notRecursive");
//      DtoParamCheck.checkCollection(field, collectionParamData.getParams(), "notRecursive");
//    }
//  }
//
//  @Test
//  public void nullValueTest() throws NoSuchFieldException {
//    List<ParamObject> paramObjectList = new ArrayList<>();
//    paramObjectList.add(null);
//
//    CollectionParamData collectionParamData = new CollectionParamData();
//    collectionParamData.setParams(paramObjectList);
//
//    Field field = collectionParamData.getClass().getDeclaredField("params");
//    DtoParamCheck.checkCollection(field, collectionParamData.getParams(), "");
//  }
}
