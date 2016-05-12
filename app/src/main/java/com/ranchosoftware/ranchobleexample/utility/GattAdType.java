package com.ranchosoftware.ranchobleexample.utility;

/**
 * Created by rob on 12/23/14.
 */
public enum GattAdType {


  Flags((byte) 0x01),
  IncompleteListof16_bitServiceClassUUIDs((byte) 0x02),
  CompleteListof16_bitServiceClassUUIDs((byte) 0x03),
  IncompleteListof32_bitServiceClassUUIDs((byte) 0x04),
  CompleteListof32_bitServiceClassUUIDs((byte) 0x05),
  IncompleteListof128_bitServiceClassUUIDs ((byte) 0x06),
  CompleteListof128_bitServiceClassUUIDs ((byte) 0x07),
  ShortenedLocalName ((byte) 0x08),
  CompleteLocalName((byte) 0x09),
  TxPowerLevel ((byte) 0x0A),
  ClassofDevice((byte) 0x0D),
  SimplePairingHashC ((byte) 0x0E),
  SimplePairingHashC_192 ((byte) 0x0E),
  SimplePairingRandomizerR ((byte) 0x0F),
  SimplePairingRandomizerR_192 ((byte) 0x0F),
  DeviceID ((byte) 0x10),
  SecurityManagerTKValue ((byte) 0x10),
  SecurityManagerOutofBandFlags((byte) 0x11),
  SlaveConnectionIntervalRange ((byte) 0x12),
  Listof16_bitServiceSolicitationUUIDs ((byte) 0x14),
  Listof32_bitServiceSolicitationUUIDs ((byte) 0x1F),
  Listof128_bitServiceSolicitationUUIDs((byte) 0x15),
  ServiceData((byte) 0x16),
  ServiceData_16_bitUUID ((byte) 0x16),
  ServiceData_32_bitUUID ((byte) 0x20),
  ServiceData_128_bitUUID((byte) 0x21),
  PublicTargetAddress((byte) 0x17),
  RandomTargetAddress((byte) 0x18),
  Appearance ((byte) 0x19),
  AdvertisingInterval((byte) 0x1A),
  LEBluetoothDeviceAddress ((byte) 0x1B),
  LERole ((byte) 0x1C),
  SimplePairingHashC_256 ((byte) 0x1D),
  SimplePairingRandomizerR_256 ((byte) 0x1E),
  ThreeDInformationData((byte) 0x3D),
  ManufacturerSpecificData ((byte) 0xFF);


  private final byte code ;
  GattAdType(byte code){
    this.code = code;
  }



  public int getCode(){
    return code;
  }

  public static GattAdType forCode(int code)
  {
    for (GattAdType type : GattAdType.values())
    {
      if (type.getCode() == code)
        return type;
    }

    return null;
  }

}