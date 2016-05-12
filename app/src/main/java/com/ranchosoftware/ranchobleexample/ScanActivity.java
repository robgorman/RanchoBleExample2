package com.ranchosoftware.ranchobleexample;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ranchosoftware.ranchobleexample.utility.Advertisement;
import com.ranchosoftware.ranchobleexample.utility.AlertUtility;
import com.ranchosoftware.ranchobleexample.utility.IBeaconAdvertisement;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

  private Button scanButton;
  private ProgressBar progressBarScanning;
  private BluetoothAdapter bluetoothAdapter;
  private Handler scanStopHandler = new Handler();
  private ListView devicesList;
  private DevicesAdapter devicesAdapter;
  private List<DeviceScanData> deviceScanList = new ArrayList<>();

  private boolean scanning = false;

  private final static int REQUEST_ENABLE_BLUETOOTH = 1;
  private final static int REQUEST_ACCESS_COARSE_LOCATION = 2;


  private boolean allNecessaryPermissionsGranted(){
    // on Android 6 (Marshmallow) sdk 23 and above we need Coarse location permission to receive the result of BLE Scans
    int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
    return locationPermission == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scan);
    setupClickableLogo();
    progressBarScanning = (ProgressBar) findViewById(R.id.progressBarScanning);
    scanButton = (Button) findViewById(R.id.scanButton);
    devicesList = (ListView) findViewById(R.id.listView);
    devicesAdapter = new DevicesAdapter(this, deviceScanList);
    devicesList.setAdapter(devicesAdapter);

    // We code to the permissions model of Marshmellos (api >= 23)
    if (allNecessaryPermissionsGranted()) {
      startTheActivity();
    } else {
      requestPermissions();
    }

  }

  private void startTheActivity(){

    scanButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startScan();
      }
    });

  // we have to make sure ble is supported
  if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
    AlertUtility.showMessageWithOk(this ,
            getString(R.string.ble_error),
            getString(R.string.no_device_ble_support)
            , null, null);

    disableScan();
  } else {
    final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
    bluetoothAdapter = bluetoothManager.getAdapter();

    if (!bluetoothAdapter.isEnabled()){
      Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);
    } else {
      // all is well
      startScan();
    }
  }
  updateViewFromState();

  }

  private void setupClickableLogo(){
    View logo = findViewById(R.id.ivLogo);
    logo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ranchosoftware.com"));
        startActivity(intent);
      }
    });
  }

  private void updateViewFromState(){

      if (scanning) {
        progressBarScanning.setVisibility(View.VISIBLE);
        scanButton.setText("Scanning...");
      } else {
        progressBarScanning.setVisibility(View.GONE);
        scanButton.setText("Scan");
      }

  }


  private void disableScan(){
    scanButton.setEnabled(false);
  }

  private void startScan(){
    scanning = true;
    deviceScanList.clear();
    devicesAdapter.notifyDataSetChanged();
    updateViewFromState();

    // stop scanning after 7 seconds
    scanStopHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        bluetoothAdapter.stopLeScan(leScanCallback);
        scanning = false;
        updateViewFromState();
      }
    }, 10000);

    bluetoothAdapter.startLeScan(leScanCallback);
  }

  private boolean contains(List<DeviceScanData> list, BluetoothDevice device){
    for (DeviceScanData next : list){
      if (next.getDevice().equals(device)){
        return true;
      }
    }
    return false;
  }

  BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback(){
    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {


      if (!contains(deviceScanList, device)){
        DeviceScanData discovered = new DeviceScanData(device, rssi, new Advertisement(scanRecord));
        deviceScanList.add(discovered);
        // update the ui but make sure it happens on the UI thread
        Handler mainThreadHandler = new Handler(ScanActivity.this.getMainLooper());
        mainThreadHandler.post(new Runnable() {
          @Override
          public void run() {
           devicesAdapter.notifyDataSetChanged();
          }
        });
      }
    }
  };

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Check which request we're responding to
    if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
      // Make sure the request was successful
      if (resultCode == RESULT_OK) {
        // the user enabled bluetooth
        startTheActivity();
      } else {
        disableScan();
      }
    }
  }


  private void requestPermissions() {

    // This code follows the Android patter for requesting permissions
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
      AlertUtility.showMessageWithOk(this, "Location Permission Required",
              "Bluetooth Low Energy Scanning requires Location permission", null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                      doTheRequest();
                    }
                  });
                }
              });
    } else {
      doTheRequest();
    }
  }

  private void doTheRequest(){
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
            REQUEST_ACCESS_COARSE_LOCATION);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case REQUEST_ACCESS_COARSE_LOCATION: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
           startTheActivity();
        } else {
          // permission denied, boo! Disable the
          // functionality that depends on this permission.
          updateViewFromState();
          AlertUtility.showMessageWithOk(this, "Coarse Location Required", "BLE scans require Coarse Location permission" , null, null);
        }
        return;
      }

      // other 'case' lines to check for other
      // permissions this app might request
    }
  }


  static private class DevicesAdapter extends ArrayAdapter<DeviceScanData> {
    private LayoutInflater inflater;

    public DevicesAdapter(Context context, List<DeviceScanData> deviceScans){
      super(context, R.layout.device_item, deviceScans);
      inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
      int count = super.getCount();
      return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null){
        convertView = inflater.inflate(R.layout.device_item, null);
      }
      DeviceScanData data = this.getItem(position);
      BluetoothDevice device = data.getDevice();
      TextView nameTextView = (TextView) convertView.findViewById(R.id.tvName);
      // Check all the posibilties for a name
      if (device.getName() != null){
        nameTextView.setText(device.getName());
      } else {
        if (!data.getAdvertisement().getCompleteLocalName().equals("")){
          nameTextView.setText(data.getAdvertisement().getCompleteLocalName());
        } else if (!data.getAdvertisement().getShortenedLocalName().equals("")){
          nameTextView.setText(data.getAdvertisement().getShortenedLocalName());
        } else {
          nameTextView.setText("  - ");
        }
      }

      TextView companyTextView = (TextView) convertView.findViewById(R.id.tvCompanyName);
      TextView beaconTextView = (TextView) convertView.findViewById(R.id.tvBeacon);

      // do we recognize as beacon
      IBeaconAdvertisement beacon = data.getAdvertisement().getBeacon();
      if (beacon != null){
        beaconTextView.setVisibility(View.VISIBLE);
      } else {
        beaconTextView.setVisibility(View.GONE);
      }

      companyTextView.setText(data.getAdvertisement().getCompany());

      return convertView;

    }
  }

}
