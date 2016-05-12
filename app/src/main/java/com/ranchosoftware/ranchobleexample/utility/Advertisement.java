package com.ranchosoftware.ranchobleexample.utility;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Advertisement {


  enum AdvertisementFlag {
    LeLimitedDiscoverableMode((byte) 0x01),
    LeGeneralDiscoverableMode((byte) 0x02),
    BrEdrNotSupported((byte) 0x04),
    SimultaneousLeAndBrEdrController((byte) 0x08),
    SimultaneousLeAndBrEdrHost((byte)0x10);

    private byte value;
    AdvertisementFlag(byte value)
    {
      this.value = value;
    }

    public byte getValue(){return value;}
  }

  private List<List<Byte>> incompleteList16BitServiceUuid = new ArrayList<List<Byte>>();
  private List<List<Byte>> completeList16BitServiceUuid = new ArrayList<List<Byte>>();

  private List<List<Byte>> incompleteList32BitServiceUuid = new ArrayList<List<Byte>>();
  private List<List<Byte>> completeList32BitServiceUuid = new ArrayList<List<Byte>>();

  private List<List<Byte>> incompleteList128BitServiceUuid = new ArrayList<List<Byte>>();
  private List<List<Byte>> completeList128BitServiceUuid = new ArrayList<List<Byte>>();

  private List<List<Byte>> listof16_bitServiceSolicitationUUIDs = new ArrayList<List<Byte>>();
  private List<List<Byte>> listof32_bitServiceSolicitationUUIDs = new ArrayList<List<Byte>>();
  private List<List<Byte>> listof128_bitServiceSolicitationUUIDs = new ArrayList<List<Byte>>();



  private String shortenedLocalName = "";
  private String completeLocalName = "";

  // Flags
  private boolean isLimitedDiscoverableMode = false;
  private boolean isGeneralDiscoverableMode = false;
  private boolean BrEdrNotSupported = false;
  private boolean SimultaneousLeAndBrEdrController = false;
  private boolean SimultaneousLeAndBrEdrHost = false;

  // ManufacturerSpecificData
  private IBeaconAdvertisement ibeacon = null;
  private String company ="";
  private byte[] manufacturerSpecificData;

  // TX Power level (formula pathloos = TXPowerLeve = RSSI)
  // value from -127 to +127
  int txPowerLevel = 0;

  // class of device
  byte classOfDevice[]; // 3 bytes if present

  byte simplePairingHashC192[]; // 16 bytes
  byte simplePairingRandomizerR192[];
  byte simplePairingHashC256[];

  HashMap<GattAdType, Boolean> containedCodes = new HashMap<GattAdType, Boolean>();



  private byte[] simplePairingHashC;
  private byte[] simplePairingHashC_192;
  private byte[] simplePairingRandomizerR;
  private byte[] simplePairingRandomizerR_192;
  private byte[] deviceId;
  private byte[] SecurityManagerTKValue;
  private byte[] SecurityManagerOutofBandFlags;
  private byte[] SlaveConnectionIntervalRange;

  private byte[] ServiceData;
  private byte[] ServiceData_16_bitUUID;
  private byte[] ServiceData_32_bitUUID;
  private byte[] ServiceData_128_bitUUID;
  private byte[] PublicTargetAddress;
  private byte[] RandomTargetAddress;
  private byte[] Appearance;
  private byte[] AdvertisingInterval;
  private byte[] LEBluetoothDeviceAddress;
  private byte[] LERole;
  private byte[] SimplePairingHashC_256;
  private byte[] SimplePairingRandomizerR_256;
  private byte[] ThreeDInformationData;




  private byte rawAdvertisement[];
  private int position;

  public Advertisement(byte[] advertisement)
  {
    rawAdvertisement = advertisement;
    parse();
  }

  private void partitionUuids(List<List<Byte>> list, int byteSize, byte data[])
  {
    if (((data.length - 1) % byteSize) != 0)
    {
      //Log.d(TAG, "Error data size unexpected");
    } else {
      int position = 0;
      for (int i = 0; i < (data.length-1) / byteSize; i++)
      {
        ArrayList<Byte> nextList = new ArrayList<Byte>();
        for (int j = 0; j < byteSize; j++){
          nextList.add(new Byte(data[position + 1]));
          position++;
        }
        list.add(nextList);
      }
    }
  }

  private byte[] strip(byte next[], int expected)
  {
    if (next.length == expected + 1){
      byte result[] = new byte[expected];
      for (int i = 0; i < next.length -1; i++)
      {
        result[i] = next[i+1];
      }
      return result;
    }
    else
    {
      //Log.d(TAG, "Unexpected lengght");/
      byte result[] = new byte[1];
      result[0] = 0;
      return result;
    }
  }

  private void parseRecord(byte next[]){
    // the 0th byte is the code. the rest is data. Also we know the length
    // because next.length

    assert(next.length >= 1);

    int code =(int)  next[0];

    if (code == GattAdType.Flags.getCode()){
      byte value = next[1];

      if ((value & AdvertisementFlag.LeLimitedDiscoverableMode.getValue()) != 0){
        isLimitedDiscoverableMode = true;
      }
      if ((value & AdvertisementFlag.LeGeneralDiscoverableMode.getValue()) != 0){
        isGeneralDiscoverableMode = true;
      }
      if ((value & AdvertisementFlag.BrEdrNotSupported.getValue()) != 0){
        BrEdrNotSupported = true;
      }
      if ((value & AdvertisementFlag.SimultaneousLeAndBrEdrController.getValue()) != 0){
        SimultaneousLeAndBrEdrController = true;
      }
      if ((value & AdvertisementFlag.SimultaneousLeAndBrEdrHost.getValue()) != 0){
        SimultaneousLeAndBrEdrHost = true;
      }


    } else if (code == GattAdType.IncompleteListof16_bitServiceClassUUIDs.getCode()) {
      partitionUuids(incompleteList16BitServiceUuid, 2, next);
    } else if (code == GattAdType.CompleteListof16_bitServiceClassUUIDs.getCode()) {
      partitionUuids(completeList16BitServiceUuid, 2, next);
    } else if (code == GattAdType.IncompleteListof32_bitServiceClassUUIDs.getCode()) {
      partitionUuids(incompleteList32BitServiceUuid, 4, next);
    } else if (code == GattAdType.CompleteListof32_bitServiceClassUUIDs.getCode()) {
      partitionUuids(completeList32BitServiceUuid, 4, next);
    } else if (code == GattAdType.IncompleteListof128_bitServiceClassUUIDs .getCode()) {
      partitionUuids(incompleteList128BitServiceUuid, 16, next);
    } else if (code == GattAdType.CompleteListof128_bitServiceClassUUIDs.getCode()) {
      partitionUuids(completeList128BitServiceUuid, 16, next);
    } else if (code == GattAdType.ShortenedLocalName.getCode()) {
      byte ascii[] = new byte[next.length -1];
      for (int i = 0; i < next.length -1; i++)
      {
        ascii[i] = next[i+1];
      }
      shortenedLocalName = new String(ascii);
    } else if (code == GattAdType.CompleteLocalName.getCode()) {
      byte ascii[] = new byte[next.length -1];
      for (int i = 0; i < next.length -1; i++)
      {
        ascii[i] = next[i+1];
      }
      completeLocalName = new String(ascii);
    } else if (code == GattAdType.TxPowerLevel.getCode()) {
      txPowerLevel = next[1];
    } else if (code == GattAdType.ClassofDevice.getCode()) {
      classOfDevice = strip(next, 3);
    } else if (code == GattAdType.SimplePairingHashC.getCode()) {
      simplePairingHashC = strip(next, next.length-1);
    } else if (code == GattAdType.SimplePairingHashC_192.getCode()) {
      simplePairingHashC_192 = strip(next, next.length-1);
    } else if (code == GattAdType.SimplePairingRandomizerR.getCode()) {
      simplePairingRandomizerR = strip(next, next.length-1);
    } else if (code == GattAdType.SimplePairingRandomizerR_192.getCode()) {
      simplePairingRandomizerR_192 = strip(next, next.length-1);
    } else if (code == GattAdType.DeviceID.getCode()) {
      deviceId = strip(next, next.length-1);
    } else if (code == GattAdType.SecurityManagerTKValue.getCode()) {
      SecurityManagerTKValue = strip(next, next.length-1);
    } else if (code == GattAdType.SecurityManagerOutofBandFlags.getCode()) {
      SecurityManagerOutofBandFlags = strip(next, next.length-1);
    } else if (code == GattAdType.SlaveConnectionIntervalRange.getCode()) {
      SlaveConnectionIntervalRange = strip(next, next.length-1);
    } else if (code == GattAdType.Listof16_bitServiceSolicitationUUIDs.getCode()) {
      partitionUuids(listof16_bitServiceSolicitationUUIDs, 2, next);
    } else if (code == GattAdType.Listof32_bitServiceSolicitationUUIDs.getCode()) {
      partitionUuids(listof32_bitServiceSolicitationUUIDs, 4, next);
    } else if (code == GattAdType.Listof128_bitServiceSolicitationUUIDs.getCode()) {
      partitionUuids(listof128_bitServiceSolicitationUUIDs, 16, next);
    } else if (code == GattAdType.ServiceData.getCode()) {
      ServiceData = strip(next, next.length-1);
    } else if (code == GattAdType.ServiceData_16_bitUUID.getCode()) {
      ServiceData_16_bitUUID = strip(next, next.length-1);
    } else if (code == GattAdType.ServiceData_32_bitUUID.getCode()) {
      ServiceData_32_bitUUID = strip(next, next.length-1);
    } else if (code == GattAdType.ServiceData_128_bitUUID.getCode()) {
      ServiceData_128_bitUUID = strip(next, next.length-1);
    } else if (code == GattAdType.PublicTargetAddress.getCode()) {
      PublicTargetAddress = strip(next, next.length-1);
    } else if (code == GattAdType. RandomTargetAddress.getCode()) {
      RandomTargetAddress = strip(next, next.length-1);
    } else if (code == GattAdType. Appearance.getCode()) {
      Appearance = strip(next, next.length-1);
    } else if (code == GattAdType.AdvertisingInterval.getCode()) {
      AdvertisingInterval = strip(next, next.length-1);
    } else if (code == GattAdType. LEBluetoothDeviceAddress.getCode()) {
      LEBluetoothDeviceAddress = strip(next, next.length-1);
    } else if (code == GattAdType. LERole .getCode()) {
      LERole = strip(next, next.length-1);
    } else if (code == GattAdType. SimplePairingHashC_256 .getCode()) {
      SimplePairingHashC_256 = strip(next, next.length-1);
    } else if (code == GattAdType. SimplePairingRandomizerR_256 .getCode()) {
      SimplePairingRandomizerR_256 = strip(next, next.length-1);
    } else if (code == GattAdType.ThreeDInformationData.getCode()) {
      ThreeDInformationData = strip(next, next.length-1);
    } else if (code == GattAdType.ManufacturerSpecificData.getCode()) {

      int companyCode = next[2] << 8;
      companyCode = next[1];
      String name = CompanyIdentifiers.companyNameFromCode(companyCode);
      company = name;
      if (next.length > 5 && next[3] == 0x02 && next[4] == 0x15)
      {
        // we have an apple beacon
        byte  uuid[] = new byte[16];
        for (int i = 0; i < 16; i++)
        {
          uuid[i] = next[i+5];
        }
        int major = next[22] << 8;
        major += next[21];
        int minor = next[24] << 8;
        minor += next[23];
        ByteBuffer bb = ByteBuffer.wrap(uuid);
        long first = bb.getLong();
        long second = bb.getLong();
        UUID proximity = new UUID(first, second);
        int txPower = next[25];
        ibeacon = new IBeaconAdvertisement(name, proximity, major, minor, txPower);
      } else {
        manufacturerSpecificData = new byte[next.length - 3];
        for (int i = 0; i < next.length - 3; i++) {
          manufacturerSpecificData[i] = next[i + 3];
        }
      }


    }

    GattAdType type = GattAdType.forCode(code);
    if (type != null)
      containedCodes.put(type, true);
  }

  private void parse()
  {
    position = 0;
    while(position < rawAdvertisement.length){
      byte next[] = getNextRecord();
      if (next.length == 0)
        break;
      parseRecord(next);
    }

  }

  private byte[] getNextRecord(){
    int length = rawAdvertisement[position];
    byte next[] = new byte[length];
    for (int i = 0; i < length; i++)
    {
      // this shouldn't occur but verify there is enought data
      if (rawAdvertisement.length > position + 1 + i)
        next[i] = rawAdvertisement[position + 1 + i];
      else
      {
        //Log.d(TAG, "Error reading past data in ad; replaceing with 0");
        next[i] = 0;
      }
    }
    position = position + 1 + length;
    return next;
  }

  public List<List<Byte>> getIncompleteList16BitServiceUuid() {
    return incompleteList16BitServiceUuid;
  }

  public List<List<Byte>> getCompleteList16BitServiceUuid() {
    return completeList16BitServiceUuid;
  }

  public List<List<Byte>> getIncompleteList32BitServiceUuid() {
    return incompleteList32BitServiceUuid;
  }

  public List<List<Byte>> getCompleteList32BitServiceUuid() {
    return completeList32BitServiceUuid;
  }

  public List<List<Byte>> getIncompleteList128BitServiceUuid() {
    return incompleteList128BitServiceUuid;
  }

  public List<List<Byte>> getCompleteList128BitServiceUuid() {
    return completeList128BitServiceUuid;
  }

  public List<List<Byte>> getListof16_bitServiceSolicitationUUIDs() {
    return listof16_bitServiceSolicitationUUIDs;
  }

  public List<List<Byte>> getListof32_bitServiceSolicitationUUIDs() {
    return listof32_bitServiceSolicitationUUIDs;
  }

  public List<List<Byte>> getListof128_bitServiceSolicitationUUIDs() {
    return listof128_bitServiceSolicitationUUIDs;
  }

  public String getShortenedLocalName() {
    return shortenedLocalName;
  }

  public String getCompleteLocalName() {
    return completeLocalName;
  }

  public boolean isLimitedDiscoverableMode() {
    return isLimitedDiscoverableMode;
  }

  public boolean isGeneralDiscoverableMode() {
    return isGeneralDiscoverableMode;
  }

  public boolean isBrEdrNotSupported() {
    return BrEdrNotSupported;
  }

  public boolean isSimultaneousLeAndBrEdrController() {
    return SimultaneousLeAndBrEdrController;
  }

  public boolean isSimultaneousLeAndBrEdrHost() {
    return SimultaneousLeAndBrEdrHost;
  }

  public String getCompany() {
    return company;
  }

  public byte[] getManufacturerSpecificData() {
    return manufacturerSpecificData;
  }

  public int getTxPowerLevel() {
    return txPowerLevel;
  }

  public byte[] getClassOfDevice() {
    return classOfDevice;
  }

  public byte[] getSimplePairingHashC192() {
    return simplePairingHashC192;
  }

  public byte[] getSimplePairingRandomizerR192() {
    return simplePairingRandomizerR192;
  }

  public byte[] getSimplePairingHashC256() {
    return simplePairingHashC256;
  }

  public HashMap<GattAdType, Boolean> getContainedCodes() {
    return containedCodes;
  }

  public byte[] getSimplePairingHashC() {
    return simplePairingHashC;
  }

  public byte[] getSimplePairingHashC_192() {
    return simplePairingHashC_192;
  }

  public byte[] getSimplePairingRandomizerR() {
    return simplePairingRandomizerR;
  }

  public byte[] getSimplePairingRandomizerR_192() {
    return simplePairingRandomizerR_192;
  }

  public byte[] getDeviceId() {
    return deviceId;
  }

  public byte[] getSecurityManagerTKValue() {
    return SecurityManagerTKValue;
  }

  public byte[] getSecurityManagerOutofBandFlags() {
    return SecurityManagerOutofBandFlags;
  }

  public byte[] getSlaveConnectionIntervalRange() {
    return SlaveConnectionIntervalRange;
  }


  public byte[] getServiceData() {
    return ServiceData;
  }

  public byte[] getServiceData_16_bitUUID() {
    return ServiceData_16_bitUUID;
  }

  public byte[] getServiceData_32_bitUUID() {
    return ServiceData_32_bitUUID;
  }

  public byte[] getServiceData_128_bitUUID() {
    return ServiceData_128_bitUUID;
  }

  public byte[] getPublicTargetAddress() {
    return PublicTargetAddress;
  }

  public byte[] getRandomTargetAddress() {
    return RandomTargetAddress;
  }

  public byte[] getAppearance() {
    return Appearance;
  }

  public byte[] getAdvertisingInterval() {
    return AdvertisingInterval;
  }

  public byte[] getLEBluetoothDeviceAddress() {
    return LEBluetoothDeviceAddress;
  }

  public byte[] getLERole() {
    return LERole;
  }

  public byte[] getSimplePairingHashC_256() {
    return SimplePairingHashC_256;
  }

  public byte[] getSimplePairingRandomizerR_256() {
    return SimplePairingRandomizerR_256;
  }

  public byte[] getThreeDInformationData() {
    return ThreeDInformationData;
  }



  public byte[] getRawAdvertisement() {
    return rawAdvertisement;
  }


  public IBeaconAdvertisement getBeacon(){return ibeacon;}

}
