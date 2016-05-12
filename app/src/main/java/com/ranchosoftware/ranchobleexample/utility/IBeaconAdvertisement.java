package com.ranchosoftware.ranchobleexample.utility;

import java.util.UUID;

/**
 * Created by rob on 12/24/14.
 */
public class IBeaconAdvertisement {
  private String company;
  private UUID proximityUuid;
  private int major;
  private int minor;
  private int calibratedPower;

  IBeaconAdvertisement(String company, UUID proximityUuid, int major, int minor, int calibratedPower){
    this.company = company;
    this.proximityUuid = proximityUuid;
    this.major = major;
    this.minor = minor;
    this.calibratedPower = calibratedPower;
  }

  public String getCompany() {
    return company;
  }

  public UUID getProximityUuid() {
    return proximityUuid;
  }

  public int getMajor() {
    return major;
  }

  public int getMinor() {
    return minor;
  }

  public int getCalibratedPower() {
    return calibratedPower;
  }
}
