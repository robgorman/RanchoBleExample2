package com.ranchosoftware.ranchobleexample2.utility;

import com.google.common.io.BaseEncoding;

/**
 * Created by rob on 5/17/16.
 */
public class Hex {

  public static String bytesToHex(byte[] bytes){
    // user guava
    String hex = BaseEncoding.base16().lowerCase().encode(bytes);
    return hex;
  }
}
