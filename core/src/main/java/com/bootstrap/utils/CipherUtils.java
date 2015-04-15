package com.bootstrap.utils;

import android.util.Base64;

public final class CipherUtils {
  public static String unlockMessage(final String msg, final String key) {
    return xor(new String(Base64.decode(msg, 0)), key);
  }

  private static String xor(final String msg, final String key) {
    final StringBuilder sb = new StringBuilder();
    final int len = msg.length();
    final int keyLen = key.toCharArray().length;
    for (int i = 0; i < len; i++) {
      sb.append((char) (msg.charAt(i) ^ key.toCharArray()[i % keyLen]));
    }
    return (sb.toString());
  }
}
