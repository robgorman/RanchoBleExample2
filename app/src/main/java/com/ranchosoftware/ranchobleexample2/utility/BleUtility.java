package com.ranchosoftware.ranchobleexample2.utility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc

/**
 * The Class BleUtility.
 */
public class BleUtility {

  public static enum FormatType {
    rfu(0), //  Reserved for future use
    gatt_uuid(128), //   unsigned 16-bit integer or unsigned 128-bit integer
    btboolean(1), //   unsigned 1-bit; 0 = false, 1 = true
    ble2bit(2), //    2-bit value
    nibble(4), //   4-bit value
    ble8bit(8), //   8-bit value
    ble16bit(16), //  16-bit value
    ble24bit(24), //  24-bit value
    ble32bit(32), //  32-bit value
    uint8(8), //  unsigned 8-bit integer
    uint12(12), //   unsigned 12-bit integer
    uint16(16), //   unsigned 16-bit integer
    uint24(24), //   unsigned 24-bit integer
    uint32(32), //   unsigned 32-bit integer
    uint40(40), //   unsigned 40-bit integer
    uint48(48), //   unsigned 48-bit integer
    uint64(64), //   unsigned 64-bit integer
    uint128(28), //  unsigned 128-bit integer
    sint8(8), //  signed 8-bit integer
    sint12(12), //   signed 12-bit integer
    sint16(16), //   signed 16-bit integer
    sint24(24), //   signed 24-bit integer
    sint32(32), //   signed 32-bit integer
    sint48(48), //   signed 48-bit integer
    sint64(64), //   signed 64-bit integer
    sint128(128), //  signed 128-bit integer
    float32(32), //  IEEE-754 32-bit floating point
    float64(64), //  IEEE-754 64-bit floating point
    SFLOAT(16), //   IEEE-11073 16-bit SFLOAT
    FLOAT(32), //  IEEE-11073 32-bit FLOAT
    dunit16(16), //  double unsigned 16-bit integer
    utf8s(32), //  UTF-8 string
    utf16s(16), //   UTF-16 string
    reg_cert_data_list(-1), //   Regulatory Certification Data List - Refer to IEEE 11073-20601 Regulatory Certification Data List characteristic
    variable(-1), //   Defined by the Service Specification
    array(-1), // Array[] Example: uint8[]
    unknown(-1); // this is a custom placehold rob created just because rob got tired of looking them all up  
    
    private int bitSize; 
    private FormatType(int bitSize)
    {
      this.bitSize = bitSize;
    }
    public int getByteSize()
    {
      return bitSize / 8 + 1;
    }
    
    public int getBitSize()
    {
      return bitSize; 
    }
  }
  // Standard BLE services
  
  final static public UUID GENERIC_ACCESS =            UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
  final static public UUID GENERIC_ATTRIBUTE =         UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
  final static public UUID IMMEDIATE_ALERT =           UUID.fromString("00001802-0000-1000-8000-00805f9b34fb");
  final static public UUID LINK_LOSS =                 UUID.fromString("00001803-0000-1000-8000-00805f9b34fb");
  final static public UUID TX_POWER =                  UUID.fromString("00001804-0000-1000-8000-00805f9b34fb");
  final static public UUID CURRENT_TIME =              UUID.fromString("00001805-0000-1000-8000-00805f9b34fb");
  final static public UUID REF_TIME_UPDATE =           UUID.fromString("00001806-0000-1000-8000-00805f9b34fb");
  final static public UUID NEXT_DST_CHANGE =           UUID.fromString("00001807-0000-1000-8000-00805f9b34fb");
  final static public UUID GLUCOSE =                   UUID.fromString("00001808-0000-1000-8000-00805f9b34fb");
  final static public UUID HEALTH_THERMOMETER =        UUID.fromString("00001809-0000-1000-8000-00805f9b34fb");
  final static public UUID DEVICE_INFO =               UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
  final static public UUID HEART_RATE =                UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
  final static public UUID PHONE_ALERT_STATUS =        UUID.fromString("0000180e-0000-1000-8000-00805f9b34fb");
  final static public UUID BATTERY =                   UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
  final static public UUID BLOOD_PRESSURE =            UUID.fromString("00001810-0000-1000-8000-00805f9b34fb");
  final static public UUID ALERT_NOTIFICATION =         UUID.fromString("00001811-0000-1000-8000-00805f9b34fb");
  final static public UUID HUMAN_INTERFACE_DEVICE =     UUID.fromString("00001812-0000-1000-8000-00805f9b34fb");
  final static public UUID SCAN_PARAMETERS =            UUID.fromString("00001813-0000-1000-8000-00805f9b34fb");
  final static public UUID RUNNING_SPEED_AND_CADENCE =  UUID.fromString("00001814-0000-1000-8000-00805f9b34fb");
  final static public UUID CYCLING_SPEED_AND_CADENCE =  UUID.fromString("00001816-0000-1000-8000-00805f9b34fb");
  final static public UUID CYCLING_POWER =              UUID.fromString("00001818-0000-1000-8000-00805f9b34fb");
  final static public UUID LOCATION_AND_NAVIGATION =    UUID.fromString("00001819-0000-1000-8000-00805f9b34fb");
  


  // Standard BLE characteristic
  final static public UUID DeviceName =                             UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb");
  final static public UUID Appearance =                             UUID.fromString("00002a01-0000-1000-8000-00805f9b34fb");
  final static public UUID PeripheralPrivacyFlag =                  UUID.fromString("00002a02-0000-1000-8000-00805f9b34fb");
  final static public UUID ReconnectionAddress =                    UUID.fromString("00002a03-0000-1000-8000-00805f9b34fb");
  final static public UUID PeripheralPerferredConnectionParameter=  UUID.fromString("00002a04-0000-1000-8000-00805f9b34fb");
  final static public UUID ServiceChanged =                         UUID.fromString("00002a05-0000-1000-8000-00805f9b34fb");
  final static public UUID AlertLevel =                             UUID.fromString("00002a06-0000-1000-8000-00805f9b34fb");
  final static public UUID TxPowerLevel =                           UUID.fromString("00002a07-0000-1000-8000-00805f9b34fb");
  final static public UUID DateTime =                               UUID.fromString("00002a08-0000-1000-8000-00805f9b34fb");
  final static public UUID DayOfWeek =                              UUID.fromString("00002a09-0000-1000-8000-00805f9b34fb");
  final static public UUID DayDateTime =                            UUID.fromString("00002a0a-0000-1000-8000-00805f9b34fb");
  final static public UUID ExactTime256 =                           UUID.fromString("00002a0c-0000-1000-8000-00805f9b34fb");
  final static public UUID DstOffset =                              UUID.fromString("00002a0d-0000-1000-8000-00805f9b34fb");
  final static public UUID TimeZone =                               UUID.fromString("00002a0e-0000-1000-8000-00805f9b34fb");
  final static public UUID LocalTimeInformation =                   UUID.fromString("00002a0f-0000-1000-8000-00805f9b34fb");
  final static public UUID TimeWithDst =                            UUID.fromString("00002a11-0000-1000-8000-00805f9b34fb");
  final static public UUID TimeAccuracy =                           UUID.fromString("00002a12-0000-1000-8000-00805f9b34fb");
  final static public UUID TimeSource =                             UUID.fromString("00002a13-0000-1000-8000-00805f9b34fb");
  final static public UUID ReferenceTimeInformaion =                UUID.fromString("00002a14-0000-1000-8000-00805f9b34fb");
  final static public UUID TimeUpdateControlPoint =                 UUID.fromString("00002a16-0000-1000-8000-00805f9b34fb");
  final static public UUID TimeUpdateState =                        UUID.fromString("00002a17-0000-1000-8000-00805f9b34fb");
  final static public UUID GlucoseMeasurement =                     UUID.fromString("00002a18-0000-1000-8000-00805f9b34fb");
  final static public UUID BatteryLevel =                           UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
  final static public UUID TemperatureMeasurement =                 UUID.fromString("00002a1c-0000-1000-8000-00805f9b34fb");
  final static public UUID TemperatureType =                        UUID.fromString("00002a1d-0000-1000-8000-00805f9b34fb");
  final static public UUID IntermediateTemperature =                UUID.fromString("00002a1e-0000-1000-8000-00805f9b34fb");
  final static public UUID MeasurementInterval =                    UUID.fromString("00002a21-0000-1000-8000-00805f9b34fb");
  final static public UUID BootKeyboardInputReport =                UUID.fromString("00002a22-0000-1000-8000-00805f9b34fb");
  final static public UUID SystemId =                               UUID.fromString("00002a23-0000-1000-8000-00805f9b34fb");
  final static public UUID ModelNumberString =                      UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb");
  final static public UUID SerialNumberString =                     UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb");
  final static public UUID FirmwareRevisionString =                 UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
  final static public UUID HardwareRevisionString =                 UUID.fromString("00002a27-0000-1000-8000-00805f9b34fb");
  final static public UUID SoftwareRevisionString =                 UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb");
  final static public UUID ManufacturerNameString =                 UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");
  final static public UUID IEEE11073_20601RegulatoryCertificationDataList =  UUID.fromString("00002a2a-0000-1000-8000-00805f9b34fb");
  final static public UUID CurrentTime =                            UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb");
  final static public UUID ScanRefresh =                            UUID.fromString("00002a31-0000-1000-8000-00805f9b34fb");
  final static public UUID BootKeyboardOutputReport =               UUID.fromString("00002a32-0000-1000-8000-00805f9b34fb");
  final static public UUID BootMouseInputReport =                   UUID.fromString("00002a33-0000-1000-8000-00805f9b34fb");
  final static public UUID GlucoseMeasurementContext =              UUID.fromString("00002a34-0000-1000-8000-00805f9b34fb");
  final static public UUID BloodPressureMeasurement =               UUID.fromString("00002a35-0000-1000-8000-00805f9b34fb");
  final static public UUID IntermediateCuffPressure =               UUID.fromString("00002a36-0000-1000-8000-00805f9b34fb");
  final static public UUID HeartRateMeasurement =                   UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
  final static public UUID BodySensorLocation =                     UUID.fromString("00002a38-0000-1000-8000-00805f9b34fb");
  final static public UUID HeartRateControlPoint =                  UUID.fromString("00002a39-0000-1000-8000-00805f9b34fb");
  final static public UUID AlertStatus =                            UUID.fromString("00002a3f-0000-1000-8000-00805f9b34fb");
  final static public UUID RingerControlPoint =                     UUID.fromString("00002a40-0000-1000-8000-00805f9b34fb");
  final static public UUID RingerSetting =                          UUID.fromString("00002a41-0000-1000-8000-00805f9b34fb");
  final static public UUID AlertCategoryIDBitMask =                 UUID.fromString("00002a42-0000-1000-8000-00805f9b34fb");
  final static public UUID AlertCategoryID =                        UUID.fromString("00002a43-0000-1000-8000-00805f9b34fb");
  final static public UUID AlertNotificationControlPoint =          UUID.fromString("00002a44-0000-1000-8000-00805f9b34fb");
  final static public UUID UnreadAlertStatus =                      UUID.fromString("00002a45-0000-1000-8000-00805f9b34fb");
  final static public UUID NewAlert =                               UUID.fromString("00002a46-0000-1000-8000-00805f9b34fb");
  final static public UUID SupportedNewAlertCategory =              UUID.fromString("00002a47-0000-1000-8000-00805f9b34fb");
  final static public UUID SupportedUnreadAlertCategory =           UUID.fromString("00002a48-0000-1000-8000-00805f9b34fb");
  final static public UUID BloodPressureFeature =                   UUID.fromString("00002a49-0000-1000-8000-00805f9b34fb");
  final static public UUID HIDInformation =                         UUID.fromString("00002a4a-0000-1000-8000-00805f9b34fb");
  final static public UUID ReportMap =                              UUID.fromString("00002a4b-0000-1000-8000-00805f9b34fb");
  final static public UUID HIDControlPoint =                        UUID.fromString("00002a4c-0000-1000-8000-00805f9b34fb");
  final static public UUID Report =                                 UUID.fromString("00002a4d-0000-1000-8000-00805f9b34fb");
  final static public UUID ProtocolMode =                           UUID.fromString("00002a4e-0000-1000-8000-00805f9b34fb");
  final static public UUID ScanIntervalWindow =                     UUID.fromString("00002a4f-0000-1000-8000-00805f9b34fb");
  final static public UUID PnPID =                                  UUID.fromString("00002a50-0000-1000-8000-00805f9b34fb");
  final static public UUID GlucoseFeature =                         UUID.fromString("00002a51-0000-1000-8000-00805f9b34fb");
  final static public UUID RecordAccessControlPoint =               UUID.fromString("00002a52-0000-1000-8000-00805f9b34fb");
  final static public UUID RSCMeasurement =                         UUID.fromString("00002a53-0000-1000-8000-00805f9b34fb");
  final static public UUID RSCFeature =                             UUID.fromString("00002a54-0000-1000-8000-00805f9b34fb");
  final static public UUID SCControlPoint =                         UUID.fromString("00002a55-0000-1000-8000-00805f9b34fb");
  final static public UUID CSCMeasurement =                         UUID.fromString("00002a5b-0000-1000-8000-00805f9b34fb");
  final static public UUID CSCFeature =                             UUID.fromString("00002a5c-0000-1000-8000-00805f9b34fb");
  final static public UUID SensorLocation =                         UUID.fromString("00002a5d-0000-1000-8000-00805f9b34fb");
  final static public UUID CyclingPowerMeasurement =                UUID.fromString("00002a63-0000-1000-8000-00805f9b34fb");
  final static public UUID CyclingPowerVector =                     UUID.fromString("00002a64-0000-1000-8000-00805f9b34fb");
  final static public UUID CyclingPowerFeature =                    UUID.fromString("00002a65-0000-1000-8000-00805f9b34fb");
  final static public UUID CyclingPowerControlPoint =               UUID.fromString("00002a66-0000-1000-8000-00805f9b34fb");
  final static public UUID LocationAndSpeed =                       UUID.fromString("00002a67-0000-1000-8000-00805f9b34fb");
  final static public UUID Navigation =                             UUID.fromString("00002a68-0000-1000-8000-00805f9b34fb");
  final static public UUID PositionQuality =                        UUID.fromString("00002a69-0000-1000-8000-00805f9b34fb");
  final static public UUID LNFeature =                              UUID.fromString("00002a6a-0000-1000-8000-00805f9b34fb");
  final static public UUID LNControlPoint =                         UUID.fromString("00002a6b-0000-1000-8000-00805f9b34fb");
  
  
  // Standard BLE Descriptors
  final static public UUID CHAR_CLIENT_CONFIG =         UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

  private static HashMap<UUID, String> serviceUuidToNameMap;
  static {
    serviceUuidToNameMap = new HashMap<UUID, String>();
    serviceUuidToNameMap.put(GENERIC_ACCESS, "GENERIC_ACCESS");
    serviceUuidToNameMap.put(GENERIC_ATTRIBUTE, "GENERIC_ATTRIBUTE");
    serviceUuidToNameMap.put(IMMEDIATE_ALERT, "IMMEDIATE_ALERT");
    serviceUuidToNameMap.put(LINK_LOSS, "LINK_LOSS");
    serviceUuidToNameMap.put(TX_POWER, "TX_POWER");
    serviceUuidToNameMap.put(CURRENT_TIME, "CURRENT_TIME");
    serviceUuidToNameMap.put(REF_TIME_UPDATE, "REF_TIME_UPDATE");
    serviceUuidToNameMap.put(NEXT_DST_CHANGE, "NEXT_DST_CHANGE");
    serviceUuidToNameMap.put(GLUCOSE, "GLUCOSE");
    serviceUuidToNameMap.put(HEALTH_THERMOMETER, "HEALTH_THERMOMETER");
    serviceUuidToNameMap.put(DEVICE_INFO, "DEVICE_INFO");
    serviceUuidToNameMap.put(HEART_RATE, "HEART_RATE");
    serviceUuidToNameMap.put(PHONE_ALERT_STATUS, "PHONE_ALERT_STATUS");
    serviceUuidToNameMap.put(BATTERY, "BATTERY");
    serviceUuidToNameMap.put(BLOOD_PRESSURE, "BLOOD_PRESSURE");
    serviceUuidToNameMap.put(ALERT_NOTIFICATION, "ALERT_NOTIFICATION");
    serviceUuidToNameMap.put(HUMAN_INTERFACE_DEVICE, "HUMAN_INTERFACE_DEVICE");
    serviceUuidToNameMap.put(SCAN_PARAMETERS, "SCAN_PARAMETERS");
    serviceUuidToNameMap.put(RUNNING_SPEED_AND_CADENCE, "RUNNING_SPEED_AND_CADENCE");
    serviceUuidToNameMap.put(CYCLING_SPEED_AND_CADENCE, "CYCLING_SPEED_AND_CADENCE");
    serviceUuidToNameMap.put(CYCLING_POWER, "CYCLING_POWER");
    serviceUuidToNameMap.put(LOCATION_AND_NAVIGATION, "LOCATION_AND_NAVIGATION");
  }
    
  

  public static class FormatNameTypePair
  {
    public String name;
    public FormatType type;
    public FormatNameTypePair(String name, FormatType type)
    {
      this.name = name; 
      this.type = type; 
    }
  }
    //////////
  public static class CharacteristicInfo
  {
    public String name;
    public FormatNameTypePair pairs[]; 
    public CharacteristicInfo(String name, FormatNameTypePair pairs[])
    {
      this.name = name; 
      this.pairs = pairs; 
    }
  }
  private static HashMap<UUID, CharacteristicInfo> characteristicUuidMap;
  static {
    characteristicUuidMap = new HashMap<UUID,CharacteristicInfo>();
    characteristicUuidMap.put(DeviceName,               new CharacteristicInfo("Device Name", 
        new FormatNameTypePair[]{new FormatNameTypePair("Name", FormatType.utf8s)}));
    
    characteristicUuidMap.put(Appearance,               new CharacteristicInfo("Appearance",
        new FormatNameTypePair[]{new FormatNameTypePair("Category", FormatType.ble16bit)}));
    
    characteristicUuidMap.put(PeripheralPrivacyFlag,    new CharacteristicInfo("Peripheral Privacy Flag",
       new FormatNameTypePair[]{new FormatNameTypePair("Flag", FormatType.btboolean)}));
    characteristicUuidMap.put(ReconnectionAddress,      new CharacteristicInfo("Reconnection Address",
       new FormatNameTypePair[]{new FormatNameTypePair("Address", FormatType.uint48)}));
    
    characteristicUuidMap.put(PeripheralPerferredConnectionParameter, new CharacteristicInfo("Peripheral Preferred Connection Parameters",
       new FormatNameTypePair[]{new FormatNameTypePair("Minimum Connection Interval", FormatType.uint16),
                                new FormatNameTypePair("Maximum Connection Interval", FormatType.uint16),
                                new FormatNameTypePair("Slave Latency", FormatType.uint16),
                                new FormatNameTypePair("Connection Supervisor Timeout Multiplier", FormatType.uint16),
       }));
    
    characteristicUuidMap.put(ServiceChanged,           new CharacteristicInfo("Service Changed",
       new FormatNameTypePair[]{new FormatNameTypePair("RangeStart", FormatType.uint16),
                                new FormatNameTypePair("RangeEnd", FormatType.uint16)}));
    
    characteristicUuidMap.put(AlertLevel,               new CharacteristicInfo("Alert Level",
        new FormatNameTypePair[]{new FormatNameTypePair("Alert Level", FormatType.uint8)}));
    
    characteristicUuidMap.put(TxPowerLevel,             new CharacteristicInfo("Tx Power Level",
       new FormatNameTypePair[]{new FormatNameTypePair("TxPower", FormatType.sint8)}));
    
    characteristicUuidMap.put(DateTime,                 new CharacteristicInfo("Date Time",
       new FormatNameTypePair[]{new FormatNameTypePair("Year", FormatType.uint8),
                                new FormatNameTypePair("Month", FormatType.uint8),
                                new FormatNameTypePair("Day", FormatType.uint8),
                                new FormatNameTypePair("Hours", FormatType.uint8),
                                new FormatNameTypePair("Minutes", FormatType.uint8),
                                new FormatNameTypePair("Seconds", FormatType.uint8),  }));
    
    characteristicUuidMap.put(DayOfWeek,                new CharacteristicInfo("Day of Week",
       new FormatNameTypePair[]{new FormatNameTypePair("DayOfWeek", FormatType.uint8)}));
    
    characteristicUuidMap.put(DayDateTime,              new CharacteristicInfo("Day Date Time",
       new FormatNameTypePair[]{new FormatNameTypePair("Year", FormatType.uint8),
                                new FormatNameTypePair("Month", FormatType.uint8),
                                new FormatNameTypePair("Day", FormatType.uint8),
                                new FormatNameTypePair("Hours", FormatType.uint8),
                                new FormatNameTypePair("Minutes", FormatType.uint8),
                                new FormatNameTypePair("Seconds", FormatType.uint8),
                                new FormatNameTypePair("DayOfWeek", FormatType.uint8)}));
    
    characteristicUuidMap.put(ExactTime256,             new CharacteristicInfo("Exact Time 256",
        new FormatNameTypePair[]{new FormatNameTypePair("Year", FormatType.uint8),
                                 new FormatNameTypePair("Month", FormatType.uint8),
                                 new FormatNameTypePair("Day", FormatType.uint8),
                                 new FormatNameTypePair("Hours", FormatType.uint8),
                                 new FormatNameTypePair("Minutes", FormatType.uint8),
                                 new FormatNameTypePair("Seconds", FormatType.uint8),
                                 new FormatNameTypePair("DayOfWeek", FormatType.uint8),
                                 new FormatNameTypePair("Fractions256", FormatType.uint8),}));
    
    characteristicUuidMap.put(DstOffset,                new CharacteristicInfo("DST Offset",
       new FormatNameTypePair[]{new FormatNameTypePair("DstOffset", FormatType.uint8)}));
    
    characteristicUuidMap.put(TimeZone,                 new CharacteristicInfo("Time Zone",
       new FormatNameTypePair[]{new FormatNameTypePair("TimeZone", FormatType.sint8)}));
    
    characteristicUuidMap.put(LocalTimeInformation,     new CharacteristicInfo("Local Time Information",
       new FormatNameTypePair[]{new FormatNameTypePair("TimeZone", FormatType.sint8),
                               new FormatNameTypePair("DstOffset", FormatType.uint8)}));
    
    characteristicUuidMap.put(TimeWithDst,              new CharacteristicInfo("Time with DST",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(TimeAccuracy,             new CharacteristicInfo("Time Accuracy",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(TimeSource,               new CharacteristicInfo("Time Source",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(ReferenceTimeInformaion,  new CharacteristicInfo("Reference Time Information",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(TimeUpdateControlPoint,   new CharacteristicInfo("Time Update Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(TimeUpdateState,          new CharacteristicInfo("Time Update State",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(GlucoseMeasurement,       new CharacteristicInfo("Glucose Measurement",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    
    characteristicUuidMap.put(BatteryLevel,             new CharacteristicInfo("Battery Level",
       new FormatNameTypePair[]{new FormatNameTypePair("percentage", FormatType.uint8)}));
    
    characteristicUuidMap.put(TemperatureMeasurement,   new CharacteristicInfo("Temperature Measurement",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(TemperatureType,          new CharacteristicInfo("Temperature Type",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(IntermediateTemperature,  new CharacteristicInfo("Intermediate Temperature",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(MeasurementInterval,      new CharacteristicInfo("Measurement Interval",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(BootKeyboardInputReport,  new CharacteristicInfo("Boot Keyboard Input Report",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(SystemId,                 new CharacteristicInfo("System ID",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(ModelNumberString,        new CharacteristicInfo("Model Number String",
        new FormatNameTypePair[]{new FormatNameTypePair("Model Number", FormatType.utf8s)}));
    characteristicUuidMap.put(SerialNumberString,       new CharacteristicInfo("Serial Number String",
        new FormatNameTypePair[]{new FormatNameTypePair("Serial Number", FormatType.utf8s)}));
    characteristicUuidMap.put(FirmwareRevisionString,   new CharacteristicInfo("Firmware Revision String",
        new FormatNameTypePair[]{new FormatNameTypePair("Firmware Revision", FormatType.utf8s)}));
    characteristicUuidMap.put(HardwareRevisionString,   new CharacteristicInfo("Hardware Revision String",
        new FormatNameTypePair[]{new FormatNameTypePair("Hardware Revision", FormatType.utf8s)}));
    characteristicUuidMap.put(SoftwareRevisionString,   new CharacteristicInfo("Software Revision String",
        new FormatNameTypePair[]{new FormatNameTypePair("Software Revision", FormatType.utf8s)}));
    characteristicUuidMap.put(ManufacturerNameString,   new CharacteristicInfo("Manufacturer Name String",
        new FormatNameTypePair[]{new FormatNameTypePair("Manufacturer", FormatType.utf8s)}));
    characteristicUuidMap.put(IEEE11073_20601RegulatoryCertificationDataList, new CharacteristicInfo("IEEE 11073-20601 Regulatory Certification Data List",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(CurrentTime,              new CharacteristicInfo("Current Time",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(ScanRefresh,              new CharacteristicInfo("Scan Refresh",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(BootKeyboardOutputReport, new CharacteristicInfo("Boot Keyboard Output Report",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(BootMouseInputReport,     new CharacteristicInfo("Boot Mouse Input Report",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(GlucoseMeasurementContext, new CharacteristicInfo("Glucose Measurement Context",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    
    characteristicUuidMap.put(BloodPressureMeasurement, new CharacteristicInfo("Blood Pressure Measurement",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    
    characteristicUuidMap.put(IntermediateCuffPressure, new CharacteristicInfo("Intermediate Cuff Pressure",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(HeartRateMeasurement,     new CharacteristicInfo("Heart Rate Measurement",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    
    characteristicUuidMap.put(BodySensorLocation,       new CharacteristicInfo("Body Sensor Location",
       new FormatNameTypePair[]{new FormatNameTypePair("BSLocation", FormatType.ble8bit)}));
    
    characteristicUuidMap.put(HeartRateControlPoint,    new CharacteristicInfo("Heart Rate Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    
    characteristicUuidMap.put(AlertStatus,              new CharacteristicInfo("Alert Status",
       new FormatNameTypePair[]{new FormatNameTypePair("AlertStatus", FormatType.uint8)}));
    
    characteristicUuidMap.put(RingerControlPoint,       new CharacteristicInfo("Ringer Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(RingerSetting,            new CharacteristicInfo("Ringer Setting",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    
    characteristicUuidMap.put(AlertCategoryIDBitMask,   new CharacteristicInfo("Alert Category ID Bit Mask",
        new FormatNameTypePair[]{new FormatNameTypePair("Mask0", FormatType.uint8), 
                                 new FormatNameTypePair("Mask1(opt)", FormatType.uint8)}));
    
    characteristicUuidMap.put(AlertCategoryID,          new CharacteristicInfo("Alert Category ID",
        new FormatNameTypePair[]{new FormatNameTypePair("categoryId", FormatType.uint8)}));
    
    characteristicUuidMap.put(AlertNotificationControlPoint, new CharacteristicInfo("Alert Notification Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("commandId", FormatType.uint8), 
                                new FormatNameTypePair("categoryId", FormatType.uint8), }));
    
    characteristicUuidMap.put(UnreadAlertStatus,        new CharacteristicInfo("Unread Alert Status",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(NewAlert,                 new CharacteristicInfo("New Alert",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(SupportedNewAlertCategory, new CharacteristicInfo("Supported New Alert Category",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(SupportedUnreadAlertCategory, new CharacteristicInfo("Supported Unread Alert Category",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    
    characteristicUuidMap.put(BloodPressureFeature,     new CharacteristicInfo("Blood Pressure Feature",
       new FormatNameTypePair[]{new FormatNameTypePair("BPFeature", FormatType.ble16bit)}));
    
    characteristicUuidMap.put(HIDInformation,           new CharacteristicInfo("HID Information",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(ReportMap,                new CharacteristicInfo("Report Map",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(HIDControlPoint,          new CharacteristicInfo("HID Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(Report,                   new CharacteristicInfo("Report",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(ProtocolMode,             new CharacteristicInfo("Protocol Mode",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(ScanIntervalWindow,       new CharacteristicInfo("Scan Interval Window",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(PnPID,                    new CharacteristicInfo("PnP ID",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(GlucoseFeature,           new CharacteristicInfo("Glucose Feature",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(RecordAccessControlPoint, new CharacteristicInfo("Record Access Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(RSCMeasurement,           new CharacteristicInfo("RSC Measurement",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(RSCFeature,               new CharacteristicInfo("RSC Feature",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(SCControlPoint,           new CharacteristicInfo("SC Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(CSCMeasurement,           new CharacteristicInfo("CSC Measurement",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(CSCFeature,               new CharacteristicInfo("CSC Feature",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(SensorLocation,           new CharacteristicInfo("Sensor Location",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(CyclingPowerMeasurement,  new CharacteristicInfo("Cycling Power Measurement",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(CyclingPowerVector,       new CharacteristicInfo("Cycling Power Vector",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(CyclingPowerFeature,      new CharacteristicInfo("Cycling Power Feature",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(CyclingPowerControlPoint, new CharacteristicInfo("Cycling Power Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(LocationAndSpeed,         new CharacteristicInfo("Location and Speed",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(Navigation,               new CharacteristicInfo("Navigation",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(PositionQuality,          new CharacteristicInfo("Position Quality",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(LNFeature,                new CharacteristicInfo("LN Feature",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
    characteristicUuidMap.put(LNControlPoint,           new CharacteristicInfo("LN Control Point",
       new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)}));
  }
    
  private static HashMap<UUID, String> descriptorUuidToNameMap;
  static {
    descriptorUuidToNameMap = new HashMap<UUID,String>();
    descriptorUuidToNameMap.put(CHAR_CLIENT_CONFIG, "CHAR_CLIENT_CONFIG");
  

  }

  public static String serviceUuidToName(UUID uuid) {

    if (serviceUuidToNameMap.containsKey(uuid))
      return serviceUuidToNameMap.get(uuid); 
    
    return "unknown";
  }
  public static String characteristicUuidToName(UUID uuid) {

    if (characteristicUuidMap.containsKey(uuid))
      return characteristicUuidMap.get(uuid).name; 
    
    return "unknown";
  }
  public static CharacteristicInfo characteristicUuidToCharacteristicInfo(UUID uuid)
  {
    if (characteristicUuidMap.containsKey(uuid))
      return characteristicUuidMap.get(uuid); 
    
    return new CharacteristicInfo("unknown", 
        new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)});
  }
  public static FormatNameTypePair[] characteristicUuidToType(UUID uuid) {

    if (characteristicUuidMap.containsKey(uuid))
      return characteristicUuidMap.get(uuid).pairs; 
    
    return new FormatNameTypePair[]{new FormatNameTypePair("unknown", FormatType.unknown)};
  }

  public static String descriptorUuidToName(UUID uuid) {

    if (descriptorUuidToNameMap.containsKey(uuid))
      return descriptorUuidToNameMap.get(uuid); 
    
    return "<unknown>"; 
  }


  // don't allow construction; this is utility class
  /**
   * Instantiates a new ble utility.
   */
  private BleUtility() {
  }

  /**
   * Device supports bluetooth.
   * 
   * @param context
   *          the context
   * @param adapter
   *          the adapter
   * @return true, if successful
   */
  public static boolean deviceSupportsBluetooth(Context context, BluetoothAdapter adapter) {
    if (adapter == null)
      return false;
    return true;
  }

  /**
   * Device supports bluetooth low energy.
   * 
   * @param context
   *          the context
   * @param adapter
   *          the adapter
   * @return true, if successful
   */
  public static boolean deviceSupportsBluetoothLowEnergy(Context context, BluetoothAdapter adapter) {
    if (!deviceSupportsBluetooth(context, adapter))
      return false;

    if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
      return false;

    return true;

  }

  /**
   * Checks if is bluetooth enabled.
   * 
   * @param context
   *          the context
   * @param adapter
   *          the adapter
   * @return true, if is bluetooth enabled
   */
  public static boolean isBluetoothEnabled(Context context, BluetoothAdapter adapter) {
    if (deviceSupportsBluetooth(context, adapter)) {
      return adapter.isEnabled();
    }
    return false;
  }

  /**
   * Gets the bluetooth adapter.
   * 
   * @return the bluetooth adapter. Note this may return null
   */
  public static BluetoothAdapter getBluetoothAdapter(Context context) {
    BluetoothAdapter adapter = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      // this is for JELLY_BEAN_MR2 and greater
      BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
      adapter = manager.getAdapter();
    } else {
      adapter = BluetoothAdapter.getDefaultAdapter();
    }
    return adapter;
  }


  public static String formatNewState(int newState) {
    switch (newState) {
    case BluetoothGatt.STATE_CONNECTED:
      return "STATE_CONNECTED";
    case BluetoothGatt.STATE_DISCONNECTED:
      return "STATE_DISCONNECTED";
    case BluetoothGatt.STATE_CONNECTING:
      return "STATE_CONNECTING"; 
    case BluetoothGatt.STATE_DISCONNECTING:
      return "STATE_DISCONNECTING";
    default:
      return "UnknownState: " + newState;
    }
  }

  public static String formatStatus(int status) {
    String log = "status=";
    switch (status) {
    case BluetoothGatt.GATT_FAILURE:
      log += "GATT_FAILURE";
      break;
    case BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION:
      log += "GATT_INSUFFICIENT_AUTHENTICATION";
      break;
    case BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION:
      log += "GATT_INSUFFICIENT_ENCRYPTION";
      break;
    case BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH:
      log += "GATT_INVALID_ATTRIBUTE_LENGTH";
      break;
    case BluetoothGatt.GATT_INVALID_OFFSET:
      log += "GATT_INVALID_OFFSET";
      break;
    case BluetoothGatt.GATT_READ_NOT_PERMITTED:
      log += "GATT_READ_NOT_PERMITTED";
      break;
    case BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED:
      log += "GATT_REQUEST_NOT_SUPPORTED";
      break;
    case BluetoothGatt.GATT_SUCCESS:
      log += "GATT_SUCCESS";
      break;
    case BluetoothGatt.GATT_WRITE_NOT_PERMITTED:
      log += "GformatGattLongOT_PERMITTED";
      break;
    default:
      log += "Unknown Gatt status:" + status;
      break;
    }
    return log;
  }

  public static String formatGattLong(BluetoothGatt gatt) {
    BluetoothDevice device = gatt.getDevice();
    String log = "Name= ";
    if (device != null)
      log += device.getName();
    else
      log += "unknown";

    List<BluetoothGattService> services = gatt.getServices();
    if (services != null) {
      log += "; Service UUIDs are:";
      for (BluetoothGattService service : services) {
        log += ";" + service.getUuid().toString();
      }
    }
    return log;
  }

  public static String formatGattShort(BluetoothGatt gatt) {
    BluetoothDevice device = gatt.getDevice();
    String log = "";
    if (device != null)
      log += device.getName();
    else
      log += "unknown";

    return log;
  }



  public static String formatWriteType(BluetoothGattCharacteristic characteristic) {
    int type = characteristic.getWriteType(); 
    switch (type)
    {
      case BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT:
        return "Default"; 
      case BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE:
        return "No Response"; 
      case BluetoothGattCharacteristic.WRITE_TYPE_SIGNED:
        return "Authentication Signature"; 
      default: 
        return "<Unknown>"; 
    }
    
  }
  
  public static String formatFormatType(int formatType) {
   
    switch (formatType)
    {
      case BluetoothGattCharacteristic.FORMAT_FLOAT:
        return "FORMAT_FLOAT"; 
      case BluetoothGattCharacteristic.FORMAT_SFLOAT:
        return "FORMAT_SFLOAT"; 
      case BluetoothGattCharacteristic.FORMAT_SINT16:
        return "FORMAT_SINT16"; 
      case BluetoothGattCharacteristic.FORMAT_SINT32:
        return "FORMAT_SINT32"; 
      case BluetoothGattCharacteristic.FORMAT_SINT8:
        return "FORMAT_SINT8"; 
      case BluetoothGattCharacteristic.FORMAT_UINT16:
        return "FORMAT_UINT16"; 
      case BluetoothGattCharacteristic.FORMAT_UINT32:
        return "FORMAT_UINT32"; 
      case BluetoothGattCharacteristic.FORMAT_UINT8:
        return "FORMAT_UINT8"; 
      default: 
        return "<Unknown>"; 
    }
    
  }

  public static String formatPermissions(BluetoothGattCharacteristic characteristic) {
    String result = "";
    int permissions = characteristic.getPermissions(); 
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_READ) != 0){
      result += "Read,";
    }
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED) != 0){
      result += "ReadEncrypted,";
    }
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM) != 0){
      result += "ReadEncryptedMitm,";
    }
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_WRITE) != 0){
      result += "Write,";
    }
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED) != 0){
      result += "WriteEncrypted,";
    }
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM) != 0){
      result += "WriteEncryptedMitm,";
    }
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED) != 0){
      result += "WriteSigned,";
    }
    if ((permissions & BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED_MITM) != 0){
      result += "WriteSignedMitm,";
    }
    
    if (result.length() == 0)
      result = "<none>";
    return result;
  }

  public static String formatValue(byte[] value) {
    String val = "";
    if (value != null) {
      for (int i = 0; i < value.length; i++) {
        if (val.length() == 0)
          val += value[i];
        else
          val += ":" + value[i];
      }
    } else {
      val += "<null>";
    }
    return val;
  }

  public static String formatGattDescriptor(BluetoothGattDescriptor descriptor) {
    String log = "";
    if (descriptor == null) {
      log += "<null>";
    } else {
      log += "descriptorUUID=" + descriptor.getUuid();
      log += "descriptorValue=" + descriptor.getValue();
    }
    return log;
  }
  /*
  public static String formatKnownCharacteristicValue(BluetoothGattCharacteristic characteristic)
  {
     UUID uuid = characteristic.getUuid();
     if (uuid.equals(DeviceName)){
       
     
     } else if (uuid.equals(Appearance)){
     } else if (uuid.equals(PeripheralPrivacyFlag)){
     } else if (uuid.equals(ReconnectionAddress)){
     } else if (uuid.equals(PeripheralPerferredConnectionParameter)){
     } else if (uuid.equals(ServiceChanged)){
     } else if (uuid.equals(AlertLevel)){
     } else if (uuid.equals(TxPowerLevel)){
     } else if (uuid.equals(DateTime)){
     } else if (uuid.equals(DayOfWeek)){
     } else if (uuid.equals(DayDateTime)){
     } else if (uuid.equals(ExactTime256)){
     } else if (uuid.equals(DstOffset)){
     } else if (uuid.equals(TimeZone)){
     } else if (uuid.equals(LocalTimeInformation)){
     } else if (uuid.equals(TimeWithDst)){
     } else if (uuid.equals(TimeAccuracy)){
     } else if (uuid.equals(TimeSource)){
     } else if (uuid.equals(ReferenceTimeInformaion)){
     } else if (uuid.equals(TimeUpdateControlPoint)){
     } else if (uuid.equals(TimeUpdateState)){
     } else if (uuid.equals(GlucoseMeasurement)){
     } else if (uuid.equals(BatteryLevel)){
     } else if (uuid.equals(TemperatureMeasurement)){
     } else if (uuid.equals(TemperatureType)){
     } else if (uuid.equals(IntermediateTemperature)){
     } else if (uuid.equals(MeasurementInterval)){
     } else if (uuid.equals(BootKeyboardInputReport)){
     } else if (uuid.equals(SystemId)){
     } else if (uuid.equals(ModelNumberString)){
     } else if (uuid.equals(SerialNumberString)){
     } else if (uuid.equals(FirmwareRevisionString)){
     } else if (uuid.equals(HardwareRevisionString)){
     } else if (uuid.equals(SoftwareRevisionString)){
     } else if (uuid.equals(ManufacturerNameString)){
     } else if (uuid.equals(IEEE11073_20601RegulatoryCertificationDataList)){
     } else if (uuid.equals(CurrentTime)){
     } else if (uuid.equals(ScanRefresh)){
     } else if (uuid.equals(BootKeyboardOutputReport)){
     } else if (uuid.equals(BootMouseInputReport)){
     } else if (uuid.equals(GlucoseMeasurementContext)){
     } else if (uuid.equals(BloodPressureMeasurement)){
     } else if (uuid.equals(IntermediateCuffPressure)){
     } else if (uuid.equals(HeartRateMeasurement)){
     } else if (uuid.equals(BodySensorLocation)){
     } else if (uuid.equals(HeartRateControlPoint)){
     } else if (uuid.equals(AlertStatus)){
     } else if (uuid.equals(RingerControlPoint)){
     } else if (uuid.equals(RingerSetting)){
     } else if (uuid.equals(AlertCategoryIDBitMask)){
     } else if (uuid.equals(AlertCategoryID)){
     } else if (uuid.equals(AlertNotificationControlPoint)){
     } else if (uuid.equals(UnreadAlertStatus)){
     } else if (uuid.equals(NewAlert)){
     } else if (uuid.equals(SupportedNewAlertCategory)){
     } else if (uuid.equals(SupportedUnreadAlertCategory)){
     } else if (uuid.equals(BloodPressureFeature)){
     } else if (uuid.equals(HIDInformation)){
     } else if (uuid.equals(ReportMap)){
     } else if (uuid.equals(HIDControlPoint)){
     } else if (uuid.equals(Report)){
     } else if (uuid.equals(ProtocolMode)){
     } else if (uuid.equals(ScanIntervalWindow)){
     } else if (uuid.equals(PnPID)){
     } else if (uuid.equals(GlucoseFeature)){
     } else if (uuid.equals(RecordAccessControlPoint)){
     } else if (uuid.equals(RSCMeasurement)){
     } else if (uuid.equals(RSCFeature)){
     } else if (uuid.equals(SCControlPoint)){
     } else if (uuid.equals(CSCMeasurement)){
     } else if (uuid.equals(CSCFeature)){
     } else if (uuid.equals(SensorLocation)){
     } else if (uuid.equals(CyclingPowerMeasurement)){
     } else if (uuid.equals(CyclingPowerVector)){
     } else if (uuid.equals(CyclingPowerFeature)){
     } else if (uuid.equals(CyclingPowerControlPoint)){
     } else if (uuid.equals(LocationAndSpeed)){
     } else if (uuid.equals(Navigation)){
     } else if (uuid.equals(PositionQuality)){
     } else if (uuid.equals(LNFeature)){
     } else if (uuid.equals(LNControlPoint)){
     }
     
     
  }
*/
  /*
  public static String formatCharacteristicValue(CharacteristicWrapper characteristic) {
    
    UUID uuid = characteristic.getUuid();
    // if this is a custom characteristic, it will not be in the map
    CharacteristicInfo info = characteristicUuidMap.get(uuid);
    String formattedData = "";
    if (info != null)
      formattedData = formatAsHex(characteristic.getValue());
    else
      formattedData = formatAsHex(characteristic.getValue());
    

    if (info != null) {
      FormatNameTypePair[] pairs =  characteristicUuidMap.get(characteristic.getUuid()).pairs;
      for (int i = 0; i < pairs.length; i++)
      {
        if (i > 0)
          formattedData += "; "; 
        
        formattedData = pairs[i].name + ":";
        switch (pairs[i].type) {
          case utf8s: {
            formattedData = formatAsString(characteristic.getValue());
            break; 
          }
          case btboolean: {
            formattedData = formatAsBoolean(characteristic.getValue());
            break; 
          }
          default: {
            formattedData = formatAsHex(characteristic.getValue());
           break; 
        }
      }
        // lots of constants were left out
      }
    }

    return formattedData; 
  }

*/

 

  public static String formatDescriptors(List<BluetoothGattDescriptor> descriptors) {
    String result = "\n";
    if (descriptors == null || descriptors.size() == 0)
      result = "<empty>"; 
    else
    {
      for (BluetoothGattDescriptor desc : descriptors)
      {
        result += "              uuid=" + desc.getUuid().toString() + "\n"; 
        result += "              name=" + BleUtility.descriptorUuidToName(desc.getUuid()) + "\n";
        result += "              value=" + bytesToString(desc.getValue());
      }
    }
    return result;
  }

  final protected static char[] hexArray = "0123456789abcdef".toCharArray();
  
  public static String bytesToHexString(byte[] bytes) {
    if (bytes == null)
      return "<empty>";

    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }

  public static String bytesToString(byte[] value) {
    String result = "";
    if (value == null)
      return "<empty>";
    
    for (byte b : value)
    {
      if (result.length() == 0)
        result += Integer.toString(b);
      else
        result += ":" + Integer.toString(b);
    }
    return result;
  }

  public static String formatBondState(int bondState)
  {
    switch (bondState)
    {
      case BluetoothDevice.BOND_NONE:
        return "Not Bonded"; 
      case BluetoothDevice.BOND_BONDING:
        return "Bonding";
      case BluetoothDevice.BOND_BONDED:
        return "Bonded"; 
      default: 
        return "Unknown";
    }
    
  }
  
  public static String formatDeviceType(int type)
  {
    switch (type)
    {
      case BluetoothDevice.DEVICE_TYPE_CLASSIC:
        return "Bluetooth Classic";
      case BluetoothDevice.DEVICE_TYPE_LE:
        return "Low Energy"; 
      case BluetoothDevice.DEVICE_TYPE_DUAL:
        return "Dual"; 
      case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
      default:
        return "Unknown"; 
    }
  }
  
 

  public static String formatMajorDeviceClass(int major) {
    switch (major) {
      case BluetoothClass.Device.Major.AUDIO_VIDEO:
        return "Audio/Video";
      case BluetoothClass.Device.Major.COMPUTER:
        return "Computer";
      case BluetoothClass.Device.Major.HEALTH:
        return "Health";
      case BluetoothClass.Device.Major.IMAGING:
        return "Imaging";
      case BluetoothClass.Device.Major.MISC:
        return "Miscellaneous";
      case BluetoothClass.Device.Major.NETWORKING:
        return "Networking";
      case BluetoothClass.Device.Major.PERIPHERAL:
        return "Peripheral";
      case BluetoothClass.Device.Major.PHONE:
        return "Phone";
      case BluetoothClass.Device.Major.TOY:
        return "Toy";
      case BluetoothClass.Device.Major.UNCATEGORIZED:
        return "Uncategorized";
      case BluetoothClass.Device.Major.WEARABLE:
        return "Wearable";
      default:
        return "Unknown";

    }

  }
  
  public static String formatDeviceClass(int deviceClass)
  {
    switch (deviceClass)
    {
      case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER: return "AUDIO_VIDEO_CAMCORDER";
      case BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO: return "AUDIO_VIDEO_CAR_AUDIO";
      case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE: return "AUDIO_VIDEO_HANDSFREE";
      case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES: return "AUDIO_VIDEO_HEADPHONES";
      case BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO: return "AUDIO_VIDEO_HIFI_AUDIO";
      case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER: return "AUDIO_VIDEO_LOUDSPEAKER";
      case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE: return "AUDIO_VIDEO_MICROPHONE";
      case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO: return "AUDIO_VIDEO_PORTABLE_AUDIO";
      case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX: return "AUDIO_VIDEO_SET_TOP_BOX";
      case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED: return "AUDIO_VIDEO_UNCATEGORIZED";
      case BluetoothClass.Device.AUDIO_VIDEO_VCR: return "AUDIO_VIDEO_VCR";
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA: return "AUDIO_VIDEO_VIDEO_CAMERA";
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING: return "AUDIO_VIDEO_VIDEO_CONFERENCING";
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER: return "AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER";
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY: return "AUDIO_VIDEO_VIDEO_GAMING_TOY";
      case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR: return "AUDIO_VIDEO_VIDEO_MONITOR";
      case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET: return "AUDIO_VIDEO_WEARABLE_HEADSET";
      case BluetoothClass.Device.COMPUTER_DESKTOP: return "COMPUTER_DESKTOP";
      case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA: return "COMPUTER_HANDHELD_PC_PDA";
      case BluetoothClass.Device.COMPUTER_LAPTOP: return "COMPUTER_LAPTOP";
      case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA: return "COMPUTER_PALM_SIZE_PC_PDA";
      case BluetoothClass.Device.COMPUTER_SERVER: return "COMPUTER_SERVER";
      case BluetoothClass.Device.COMPUTER_UNCATEGORIZED: return "COMPUTER_UNCATEGORIZED";
      case BluetoothClass.Device.COMPUTER_WEARABLE: return "COMPUTER_WEARABLE";
      case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE: return "HEALTH_BLOOD_PRESSURE";
      case BluetoothClass.Device.HEALTH_DATA_DISPLAY: return "HEALTH_DATA_DISPLAY";
      case BluetoothClass.Device.HEALTH_GLUCOSE: return "HEALTH_GLUCOSE";
      case BluetoothClass.Device.HEALTH_PULSE_OXIMETER: return "HEALTH_PULSE_OXIMETER";
      case BluetoothClass.Device.HEALTH_PULSE_RATE: return "HEALTH_PULSE_RATE";
      case BluetoothClass.Device.HEALTH_THERMOMETER: return "HEALTH_THERMOMETER";
      case BluetoothClass.Device.HEALTH_UNCATEGORIZED: return "HEALTH_UNCATEGORIZED";
      case BluetoothClass.Device.HEALTH_WEIGHING: return "HEALTH_WEIGHING";
      case BluetoothClass.Device.PHONE_CELLULAR: return "PHONE_CELLULAR";
      case BluetoothClass.Device.PHONE_CORDLESS: return "PHONE_CORDLESS";
      case BluetoothClass.Device.PHONE_ISDN: return "PHONE_ISDN";
      case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY: return "PHONE_MODEM_OR_GATEWAY";
      case BluetoothClass.Device.PHONE_SMART: return "PHONE_SMART";
      case BluetoothClass.Device.PHONE_UNCATEGORIZED: return "PHONE_UNCATEGORIZED";
      case BluetoothClass.Device.TOY_CONTROLLER: return "TOY_CONTROLLER";
      case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE: return "TOY_DOLL_ACTION_FIGURE";
      case BluetoothClass.Device.TOY_GAME: return "TOY_GAME";
      case BluetoothClass.Device.TOY_ROBOT: return "TOY_ROBOT";
      case BluetoothClass.Device.TOY_UNCATEGORIZED: return "TOY_UNCATEGORIZED";
      case BluetoothClass.Device.TOY_VEHICLE: return "TOY_VEHICLE";
      case BluetoothClass.Device.WEARABLE_GLASSES: return "WEARABLE_GLASSES";
      case BluetoothClass.Device.WEARABLE_HELMET: return "WEARABLE_HELMET";
      case BluetoothClass.Device.WEARABLE_JACKET: return "WEARABLE_JACKET";
      case BluetoothClass.Device.WEARABLE_PAGER: return "WEARABLE_PAGER";
      case BluetoothClass.Device.WEARABLE_UNCATEGORIZED: return "WEARABLE_UNCATEGORIZED";
      case BluetoothClass.Device.WEARABLE_WRIST_WATCH: return "WEARABLE_WRIST_WATCH";
      default: return "<Unknown>";
    }
  }
  


 
}
