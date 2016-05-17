package com.ranchosoftware.ranchobleexample2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.primitives.Bytes;
import com.ranchosoftware.ranchobleexample2.application.RanchoApplication;
import com.ranchosoftware.ranchobleexample2.utility.Advertisement;
import com.ranchosoftware.ranchobleexample2.utility.Hex;
import com.ranchosoftware.ranchobleexample2.utility.IBeaconAdvertisement;
import com.ranchosoftware.ranchobleexample2.utility.UuidUtils;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

public class BleAdvertisementActivity extends BaseActivity {

  private DeviceScanData deviceData;
  private Advertisement ad;
  private LayoutInflater inflater;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ble_advertisement);
    RanchoApplication app = getApp();
    deviceData = app.getDeviceOfInterest();
    inflater = getLayoutInflater();

    ad = deviceData.getAdvertisement();
    setupFlags();
    setupName();
    setupCompanyName();

    setupCompleteList16BitServiceUuids();
    setupIncompleteList16BitServiceUuids();
    setupCompleteList32BitServiceUuids();
    setupIncompleteList32BitServiceUuids();
    setupCompleteList128BitServiceUuids();
    setupIncompleteList128BitServiceUuids();
    setup16BitServiceSolicitationUuids(); 
    setup32BitServiceSolicitationUuids(); 
    setup128BitServiceSolicitationUuids();
    
    setupBeacon();
    setupTxPowerLevel(); 
    setupDeviceClass();
    setupManufacturerSpecificData(); 
    setupLeRole();
    setupAppearance();
  }

  private void setupCompleteList16BitServiceUuids() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutCompleteList16BitServiceUuids);
    if (ad.getCompleteList16BitServiceUuid().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getCompleteList16BitServiceUuid()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as16BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());
        layout.addView(view);

      }
    }
  }
  private void setupIncompleteList16BitServiceUuids() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutIncompleteList16BitServiceUuids);
    if (ad.getIncompleteList16BitServiceUuid().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getIncompleteList16BitServiceUuid()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as16BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());
        layout.addView(view);
      }
    }
  }

  private void setupCompleteList32BitServiceUuids() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutCompleteList32BitServiceUuids);
    if (ad.getCompleteList32BitServiceUuid().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getCompleteList32BitServiceUuid()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as32BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());
        layout.addView(view);

      }
    }
  }

  private void setupIncompleteList32BitServiceUuids() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutIncompleteList32BitServiceUuids);
    if (ad.getIncompleteList32BitServiceUuid().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getIncompleteList32BitServiceUuid()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as32BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());
        layout.addView(view);

      }
    }
  }


  private  void setupCompleteList128BitServiceUuids() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutCompleteList128BitServiceUuid);
    if (ad.getCompleteList128BitServiceUuid().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getCompleteList128BitServiceUuid()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as128BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());

        layout.addView(view);
      }
    }

  }
  private  void setupIncompleteList128BitServiceUuids() {
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutIncompleteList128BitServiceUuids);
    if (ad.getIncompleteList128BitServiceUuid().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getIncompleteList128BitServiceUuid()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as128BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());

        layout.addView(view);
      }
    }

  }

  private void setup16BitServiceSolicitationUuids(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layout16BitServiceSolicitationUuids);
    if (ad.getListof16_bitServiceSolicitationUUIDs().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getListof16_bitServiceSolicitationUUIDs()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as16BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());

        layout.addView(view);
      }
    }
  }
  private void setup32BitServiceSolicitationUuids(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layout32BitServiceSolicitationUuids);
    if (ad.getListof32_bitServiceSolicitationUUIDs().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getListof32_bitServiceSolicitationUUIDs()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as32BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());

        layout.addView(view);
      }
    }
  }
  private void setup128BitServiceSolicitationUuids(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layout128BitServiceSolicitationUuids);
    if (ad.getListof128_bitServiceSolicitationUUIDs().size() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      for (List<Byte> byteList : ad.getListof128_bitServiceSolicitationUUIDs()){
        byte[] bytes = Bytes.toArray(byteList);
        UUID uuid = UuidUtils.as128BitUuid(bytes);

        View view = inflater.inflate(R.layout.uuid, null);
        TextView tv = (TextView) view.findViewById(R.id.tvUuid);
        tv.setText(uuid.toString());

        layout.addView(view);
      }
    }
  }
  private void setupCompanyName() {
    TextView companyName = (TextView) findViewById(R.id.tvCompany);
    if (!ad.getCompany().equals("")) {
      companyName.setText(ad.getCompany());
    } else {
      companyName.setText(getResources().getString(R.string.noname));
    }
  }

  private void setupName() {
    TextView name = (TextView) findViewById(R.id.tvName);
    if (!ad.getCompleteLocalName().equals("")){
      name.setText(ad.getCompleteLocalName());
    } else if (!ad.getShortenedLocalName().equals("")){
      name.setText(ad.getShortenedLocalName() + " shortened");
    } else {
      name.setText(getResources().getString(R.string.noname));
    }
  }

  private void setupFlags() {
    CheckBox limitedDiscoverable = (CheckBox) findViewById(R.id.cbLimitedDiscoverable);
    CheckBox generalDiscoverable = (CheckBox) findViewById(R.id.cbGeneralDiscoverable);
    CheckBox brEdrNotSupported = (CheckBox) findViewById(R.id.cbBrEdrNotSupported);
    CheckBox leAndBrEdrController = (CheckBox) findViewById(R.id.cbSimultaneusLeAdnBrEdrController);
    CheckBox leAndBrEdrHost = (CheckBox) findViewById(R.id.cbSimultaneousLeAndBrEdrHost);

    limitedDiscoverable.setChecked(ad.isLimitedDiscoverableMode());
    generalDiscoverable.setChecked(ad.isGeneralDiscoverableMode());
    brEdrNotSupported.setChecked(ad.isBrEdrNotSupported());
    leAndBrEdrController.setChecked(ad.isSimultaneousLeAndBrEdrController());
    leAndBrEdrHost.setChecked(ad.isSimultaneousLeAndBrEdrHost());
  }


  private void setupBeacon(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutBeacon);
    if (ad.getBeacon() == null){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      TextView viewMinor = (TextView) findViewById(R.id.tvBeaconMinor);
      TextView viewMajor = (TextView) findViewById(R.id.tvBeaconMajor);
      TextView viewProximityUuid = (TextView) findViewById(R.id.tvProximityUuid);
      TextView viewCompany = (TextView) findViewById(R.id.tvBeaconCompany);
      TextView viewCalibratedPower = (TextView) findViewById(R.id.tvCalibratedPower);
      IBeaconAdvertisement beacon = ad.getBeacon();
      viewMinor.setText(Integer.toString(beacon.getMinor()));
      viewMajor.setText(Integer.toString(beacon.getMajor()));
      viewProximityUuid.setText(beacon.getProximityUuid().toString());
      viewCompany.setText(beacon.getCompany());
      viewCalibratedPower.setText(Integer.toString(beacon.getCalibratedPower()));
    }
  }
  private void setupTxPowerLevel(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutTxPowerLevel);
    if (ad.getTxPowerLevel() == 0){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      TextView txPower = (TextView) findViewById(R.id.tvTxPower);
      txPower.setText(Integer.toString(ad.getTxPowerLevel()));
    }
  }
  private void setupDeviceClass(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutDeviceClass);
    if (ad.getClassOfDevice() == null){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      byte[] classOfDevice = ad.getClassOfDevice();
      // TODO interpret, but format as hex for now maybe instructions here http://www.question-defense.com/tools/class-of-device-bluetooth-cod-list-in-binary-and-hex
      String s = Hex.bytesToHex(classOfDevice);
      TextView viewDeviceClass = (TextView) findViewById(R.id.tvDeviceClass);
      viewDeviceClass.setText(s);
    }
  }
  private void setupManufacturerSpecificData(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutManufacturerSpecificData);
    if (ad.getManufacturerSpecificData() == null || ad.getBeacon() !=  null){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      TextView mfc = (TextView) findViewById(R.id.tvManufactureSpecific);
      // TODO interpret but format as hex for now.
      String s = Hex.bytesToHex(ad.getManufacturerSpecificData());
      mfc.setText("0x" + s);

    }
  }
  private void setupLeRole(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutLeRole);
    if (ad.getLeRole() == null){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      byte[] role = ad.getLeRole();
      // TODO hex for now
      String s = Hex.bytesToHex(role);
      TextView tvRole = (TextView) findViewById(R.id.tvLeRole);
      tvRole.setText("0x" + s);
    }
  }

  private void setupAppearance(){
    LinearLayout layout = (LinearLayout) findViewById(R.id.layoutAppearance);
    if (ad.getAppearance() == null){
      layout.setVisibility(View.GONE);
    } else {
      layout.setVisibility(View.VISIBLE);
      byte[] bytes = ad.getAppearance();
      String s = Hex.bytesToHex(bytes);
      TextView viewAppearance = (TextView)findViewById(R.id.tvAppearance);
      viewAppearance.setText("0x" + s);
    }
  }

}
