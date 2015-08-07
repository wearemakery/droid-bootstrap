package com.bootstrap.storage;

import com.bootstrap.storage.util.JsonUtils;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.UnsavedRevision;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.tribe7.common.base.Joiner;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public abstract class BaseRepository<T> {
  protected final Database database;
  protected final ObjectMapper objectMapper;

  private final Class<T> clazz;

  public BaseRepository(final Database database, final ObjectMapper objectMapper, final Class<T> clazz) {
    this.database = database;
    this.objectMapper = objectMapper;
    this.clazz = clazz;
  }

  public Observable<T> loadByKey(final String key) {
    return Observable.create(new Observable.OnSubscribe<T>() {
      @Override public void call(final Subscriber<? super T> subscriber) {
        final Document document = database.getExistingDocument(createPrefixedKey(key));
        if (document != null) {
          final T item = objectMapper.convertValue(document.getProperties(), clazz);
          subscriber.onNext(item);
        }
        subscriber.onCompleted();
      }
    });
  }

  public Observable<T> save(final String key, final T item) {
    return Observable.create(new Observable.OnSubscribe<T>() {
      @Override public void call(final Subscriber<? super T> subscriber) {
        final Document document = database.getDocument(createPrefixedKey(key));
        try {
          final Map<String, Object> properties = objectMapper.convertValue(item, JsonUtils.MAP_TYPE_REFERENCE);
          document.update(new Document.DocumentUpdater() {
            @Override public boolean update(final UnsavedRevision newRevision) {
              newRevision.getProperties().putAll(properties);
              return true;
            }
          });
          subscriber.onNext(item);
          subscriber.onCompleted();
        } catch (CouchbaseLiteException e) {
          subscriber.onError(e);
        }
      }
    });
  }

  public Observable<Boolean> delete(final String key) {
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(final Subscriber<? super Boolean> subscriber) {
        try {
          final Document document = database.getDocument(createPrefixedKey(key));
          if (document.getCurrentRevision() != null) {
            subscriber.onNext(document.delete());
          } else {
            subscriber.onNext(false);
          }
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  protected String createPrefixedKey(final String key) {
    return Joiner.on(":").skipNulls().join(getPrefix(), key);
  }

  protected abstract String getPrefix();
}
