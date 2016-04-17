package com.bootstrap.storage.di;

import android.content.Context;

import com.bootstrap.di.AppScope;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;

@Module
public final class StorageModule {
  @Provides @AppScope public Manager provideManager(final Context context) {
    try {
      return new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Provides @AppScope public Database provideDatabase(final Manager manager) {
    try {
      manager.setStorageType(Manager.SQLITE_STORAGE);
      return manager.getDatabase("app-db");
    } catch (CouchbaseLiteException e) {
      throw new RuntimeException(e);
    }
  }

  @Provides @AppScope public ObjectMapper provideObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
    objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
    return objectMapper;
  }
}
