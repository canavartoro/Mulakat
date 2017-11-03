package com.mulakat.huseyin.task;

        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.text.Html;
        import android.view.WindowManager;

        import com.mulakat.huseyin.data.ArchiveItemComponent;
        import com.mulakat.huseyin.interfaces.onPositiveListener;
        import com.mulakat.huseyin.interfaces.ontaskend;
        import com.mulakat.huseyin.mulakat.R;
        import com.mulakat.huseyin.util.screens;
        import com.mulakat.huseyin.util.statics;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.StringTokenizer;
        import java.util.Vector;
        import java.util.logging.Logger;

/**
 * Created by huseyin on 1.11.2017.
 */

public class wiredDetailTask extends AsyncTask<String, Void, String> {

    private static ProgressDialog dialog;
    private ontaskend listener;

    public wiredDetailTask(ontaskend l) {
        super();
        listener = l;
        dialog = new ProgressDialog(statics.getCurrentActivity());
        this.dialog.setIcon(R.drawable.ic_launcher);
        this.dialog.setTitle(R.string.wait_title);
        this.dialog.setMessage(statics.getMainActivity().getString(R.string.wait_message));
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(String result) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (listener != null) {
            listener.onEnd(0, "", result);
        }

        super.onPostExecute(result);
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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        String result = null;

        try{
            String data = streamToString(doRequest(params[0]));
            if (data != null) {
                data = data.substring(data.indexOf("<article"),data.indexOf("</article>"));
                result = Html.fromHtml(data).toString();
                return result;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream doRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            return new BufferedInputStream(urlConnection.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String streamToString(InputStream in) {
        if (in != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            Boolean append = false;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                System.out.println(sb.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
        return null;
    }
}
