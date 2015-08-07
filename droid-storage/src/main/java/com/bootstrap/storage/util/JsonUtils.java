package com.bootstrap.storage.util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public final class JsonUtils {
  public static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {
  };

  private JsonUtils() {
  }
}
