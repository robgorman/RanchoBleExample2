package com.ranchosoftware.ranchobleexample2.application;

import android.app.Application;

import com.ranchosoftware.ranchobleexample2.DeviceScanData;

/**
 * Created by rob on 5/16/16.
 */
public class RanchoApplication  extends Application {
  private final static String TAG = RanchoApplication.class.getSimpleName();

  private DeviceScanData deviceOfInterest;

  public DeviceScanData getDeviceOfInterest() {
    return deviceOfInterest;
  }

  public void setDeviceOfInterest(DeviceScanData deviceOfInterest) {
    this.deviceOfInterest = deviceOfInterest;
  }
}
