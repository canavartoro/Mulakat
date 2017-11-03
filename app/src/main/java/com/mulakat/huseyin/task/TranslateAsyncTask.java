package com.mulakat.huseyin.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;

import com.mulakat.huseyin.data.ArchiveItemWord;
import com.mulakat.huseyin.interfaces.ontaskend;
import com.mulakat.huseyin.mulakat.R;
import com.mulakat.huseyin.util.statics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by huseyin on 2.11.2017.
 */

public class TranslateAsyncTask extends AsyncTask<ArrayList<ArchiveItemWord>, Void, Void> {

    private ontaskend listener;
    private static ProgressDialog dialog;

    public TranslateAsyncTask(ontaskend l) {
        listener = l;
        dialog = new ProgressDialog(statics.getCurrentActivity());
        this.dialog.setIcon(R.drawable.ic_launcher);
        this.dialog.setTitle(R.string.wait_title);
        this.dialog.setMessage(statics.getMainActivity().getString(R.string.wait_message));
    }

    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (listener != null) {
            listener.onEnd(2, "", null);
        }
    }

    @Override
    protected void onPreExecute() {
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException be) {
            be.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(ArrayList<ArchiveItemWord>... params) {
        try {
            if (params != null && params.length > 0) {
                ArrayList<ArchiveItemWord> words = params[0];
                for (int i = 0;i < words.size();i++) {
                    words.get(i).setWordTr(mTranslateData(words.get(i).getWord()));
                }
            }
        } catch (IOException e) {
            Log.e("Hata", e.getLocalizedMessage());
        }
        return null;
    }

    public String mTranslateData(String text) throws IOException {

        URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20171103T055503Z.4ac45ca7c3ca12d9.5f3a8a88e503a7929ddc14d21bfd18eb2c589fb2&text="+ URLEncoder.encode(text, "UTF-8") + "&lang=tr");
        URLConnection urlConnection = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream()));
        String result = br.readLine();
        br.close();

        try {
            JSONObject obj = new JSONObject(result);
            if (obj != null) {
                JSONArray textArray = obj.getJSONArray("text");
                if (textArray != null) {
                    return textArray.get(0).toString();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
