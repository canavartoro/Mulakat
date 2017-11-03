package com.mulakat.huseyin.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.mulakat.huseyin.interfaces.onPositiveListener;
import com.mulakat.huseyin.mulakat.R;

/**
 * Created by huseyin on 1.11.2017.
 */

public class screens {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int SELECT_FILE = 2;

    public static void showText(String text) {

        if (text == null)
            return;

        Log.i("showText", text);

        try {
            if (statics.getCurrentActivity() == null)
                return;

            Toast pieceToast = Toast.makeText(statics.getCurrentActivity(), text, Toast.LENGTH_LONG);
            pieceToast.show();
        }
        catch (Exception e) {
            Log.e("showText", e.getMessage());
        }
    }

    public static void showAlert(String text, String title, final onPositiveListener positiveListener) {

        if (text == null)
            return;

        System.err.println("showAlert-" + text);

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(statics.getCurrentActivity());
            dialog.setTitle(title);
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.setCancelable(false);
            dialog.setMessage(text);
            dialog.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            dialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (positiveListener != null)
                        positiveListener.onPositiveButton(0, "");
                    dialog.dismiss();
                }
            }).create().show();
        } catch (Exception e) {
            Log.e("showAlert", e.getMessage());
        }
    }

    public static void showAlert(String text, String title) {

        if (text == null)
            return;

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(statics.getCurrentActivity());
            dialog.setTitle(title);
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.setCancelable(false);
            dialog.setMessage(text);
            dialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            }).create().show();
        } catch (Exception e) {
            Log.e("showAlert", e.getMessage());
        }
    }
}
