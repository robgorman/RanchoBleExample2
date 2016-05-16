package com.ranchosoftware.ranchobleexample2;

import android.bluetooth.BluetoothDevice;

import com.ranchosoftware.ranchobleexample2.utility.Advertisement;

/**
 * Created by rob on 5/12/16.
 */
public class DeviceScanData {
  private BluetoothDevice device;
  private Advertisement advertisement;
  private int rssi;

  public DeviceScanData(BluetoothDevice device, int rssi, Advertisement advertisement){
    this.device = device;
    this.advertisement = advertisement;
    this.rssi = rssi;
  }

  public BluetoothDevice getDevice() {
    return device;
  }

  public Advertisement getAdvertisement() {
    return advertisement;
  }

  public int getRssi() {
    return rssi;
  }
}
