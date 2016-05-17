package com.ranchosoftware.ranchobleexample2.utility;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by rob on 5/16/16.
 */
public class UuidUtils {
  public static UUID as128BitUuid(byte[] bytes) {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    long firstLong = bb.getLong();
    long secondLong = bb.getLong();
    return new UUID(firstLong, secondLong);
  }

  private static final String baseBluetoothUuidPostfix = "0000-1000-8000-00805F9B34FB";


  public static UUID as16BitUuid(byte [] bytes){
    assert(bytes.length == 2);
    String s = String.format("%x", bytes[0]) + String.format("%x", bytes[1]);
    return UUID.fromString("0000" + s + "-" + baseBluetoothUuidPostfix);
  }

  public static UUID as32BitUuid(byte [] bytes){
    assert(bytes.length == 4);
    String s = String.format("%x", bytes[0]) + String.format("%x", bytes[1] + String.format("%x", bytes[2])
            + String.format("%x", bytes[3]));
    return UUID.fromString( s + "-" + baseBluetoothUuidPostfix);
  }

  public static byte[] as128BitBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }
}

