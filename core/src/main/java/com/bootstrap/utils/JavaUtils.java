package com.bootstrap.utils;

import java.io.Closeable;
import java.io.InputStream;

import okio.Buffer;

public final class JavaUtils {
  private JavaUtils() {
  }

  public static void safeCloseCloseable(final Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        // ignore
      }
    }
  }

  public static String readStreamToString(final InputStream in) {
    try {
      return new Buffer().readFrom(in).readUtf8();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      safeCloseCloseable(in);
    }
  }
}
