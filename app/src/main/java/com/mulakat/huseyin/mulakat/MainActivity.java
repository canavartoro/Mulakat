package com.mulakat.huseyin.mulakat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mulakat.huseyin.data.ArchiveItemComponent;
import com.mulakat.huseyin.data.DBHelper;
import com.mulakat.huseyin.data.adapter_archiveItem;
import com.mulakat.huseyin.interfaces.onPositiveListener;
import com.mulakat.huseyin.interfaces.ontaskend;
import com.mulakat.huseyin.task.wiredTask;
import com.mulakat.huseyin.util.screens;
import com.mulakat.huseyin.util.statics;
import com.mulakat.huseyin.util.utility;

import java.util.Vector;

//http://javaconceptoftheday.com/find-most-repeated-word-in-text-file-in-java/
public class MainActivity extends AppCompatActivity implements ontaskend {

    private LinearLayout internet_Layout;
    private ListView list_wired;
    private ArchiveItemComponent selectedItem;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        internet_Layout = ((LinearLayout)findViewById(R.id.internet_LinearLayout));
        list_wired = ((ListView)findViewById(R.id.list_wired));
        adapter_archiveItem adapter = new adapter_archiveItem(this, null);
        list_wired.setAdapter(adapter);
        list_wired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = list_wired.getItemAtPosition(position);
                ((adapter_archiveItem) list_wired.getAdapter()).setSelectedPosition(position);
                selectedItem = (ArchiveItemComponent) o;
                if (selectedItem != null) {
                    statics.setCurrentComponent(selectedItem);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                    //screens.showText(selectedItem.getArchiveTitle());

                } else {
                        screens.showAlert("Seçilen medya cihazınız tarafından desteklenmiyor! \"" + selectedItem.getArchiveTitle(), "Dikkat");
                }
            }
        });
        list_wired.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = list_wired.getItemAtPosition(position);
                ((adapter_archiveItem) list_wired.getAdapter()).setSelectedPosition(position);
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabrefresh = (FloatingActionButton) findViewById(R.id.fabrefresh);
        fabrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getwiredData();
            }
        });

        FloatingActionButton fabclose = (FloatingActionButton) findViewById(R.id.fabclose);
        fabclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statics.setCurrentActivity(MainActivity.this);
                screens.showAlert(getString(R.string.exit_message), getString(R.string.error_title), new onPositiveListener() {
                    @Override
                    public void onPositiveButton(int index, String item) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        MainActivity.super.onDestroy();
                    }
                });
            }
        });

        statics.setMainActivity(this);
        statics.setCurrentActivity(this);
        getwiredData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            statics.setCurrentActivity(this);
            screens.showAlert(statics.getWeburl(), "Web Url");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void getwiredData() {

        if (utility.checkInternetConnection()) {
            internet_Layout.setVisibility(View.GONE);

            wiredTask task = new wiredTask(this);
            task.execute();
        }
        else {
            internet_Layout.setVisibility(View.VISIBLE);
            screens.showAlert(getString(R.string.isnternet_error), getString(R.string.error_title));
        }
    }

    @Override
    public void onEnd(int resp, String item, Object data) {
        if (resp == 0 && data != null) {
            list_wired.setAdapter(new adapter_archiveItem(this, (Vector<ArchiveItemComponent>)data));
        }
    }

    public DBHelper getDbHelper() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(this);
        }
        return dbHelper;
    }

    public void reloadList() {
        list_wired.invalidateViews();
    }
}
