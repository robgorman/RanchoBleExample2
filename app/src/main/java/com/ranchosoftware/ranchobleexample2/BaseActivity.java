package com.ranchosoftware.ranchobleexample2;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;

import com.ranchosoftware.ranchobleexample2.application.RanchoApplication;

/**
 * Created by rob on 5/16/16.
 */
public class BaseActivity extends AppCompatActivity {

  public RanchoApplication getApp(){
    return (RanchoApplication) getApplication();
  }
}
