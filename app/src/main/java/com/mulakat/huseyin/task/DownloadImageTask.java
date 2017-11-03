package com.mulakat.huseyin.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mulakat.huseyin.interfaces.ontaskend;
import com.mulakat.huseyin.util.statics;

import java.io.InputStream;

/**
 * Created by huseyin on 2.11.2017.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ontaskend listener;
    private final String mUrl;

    public DownloadImageTask(ontaskend l, String url) {
        listener = l;
        mUrl = url;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = mUrl;
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null && listener != null) {
            listener.onEnd(0, "", result);
        }
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = statics.getCurrentActivity().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
