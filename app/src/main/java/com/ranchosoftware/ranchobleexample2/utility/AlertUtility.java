package com.ranchosoftware.ranchobleexample2.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by rob on 5/11/16.
 */
public class AlertUtility
{

  public static void showMessage(Context context, String title, String message)
  {
    showMessage(context, title, message, null);
  }


  public static void showMessage(Context context, String title, String message, AlertDialog.OnDismissListener dismissListener)
  {

    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setTitle(title);
    builder.setMessage(message);
    builder.setOnDismissListener(dismissListener);

    builder.show();
  }

  public static void showMessageWithOk(Context context, String title, String message, AlertDialog.OnDismissListener dismissListener,
                                       AlertDialog.OnClickListener okListener)
  {

    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setTitle(title);
    builder.setMessage(message);
    builder.setOnDismissListener(dismissListener);
    builder.setPositiveButton("OK", okListener);
    builder.show();
  }


  public static void showMessageWithOkCancel(Context context, String title, String message,
                                             DialogInterface.OnClickListener okListener,
                                             DialogInterface.OnClickListener cancelListener)
  {

    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton("OK", okListener);
    builder.setNegativeButton("Cancel", cancelListener);

    builder.show();
  }

}
