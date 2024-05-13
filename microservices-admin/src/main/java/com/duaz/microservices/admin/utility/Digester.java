package com.duaz.microservices.admin.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digester {
  private static final char[] digits = new char[] { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f' };
  
  private static final MessageDigest md5;
  
  private static final MessageDigest sha1;
  
  static {
    try {
      md5 = MessageDigest.getInstance("MD5");
      sha1 = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      throw new Error(e);
    } 
  }
  
  public static synchronized String md5(String string) throws Exception {
    return hex(md5.digest(string.getBytes("UTF-8")));
  }
  
  public static synchronized String sha1(String string) throws Exception {
    return hex(sha1.digest(string.getBytes("UTF-8")));
  }
  
  private static char[] hex(byte b) {
    char[] chars = new char[2];
    int d2 = b & 0xF;
    int d1 = (b & 0xF0) >> 4;
    chars[0] = digits[d1];
    chars[1] = digits[d2];
    return chars;
  }
  
  private static String hex(byte[] digest) {
    StringBuilder sb = new StringBuilder();
    for (byte b : digest)
      sb.append(hex(b)); 
    String digested = sb.toString();
    return digested;
  }
}
