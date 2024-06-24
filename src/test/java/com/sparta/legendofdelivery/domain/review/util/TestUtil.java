package com.sparta.legendofdelivery.domain.review.util;

import java.lang.reflect.Field;

public class TestUtil {

  public static void setField(Object obj, String fieldName, Object value)
      throws Exception {
    Field field = obj.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(obj, value);
  }
}
