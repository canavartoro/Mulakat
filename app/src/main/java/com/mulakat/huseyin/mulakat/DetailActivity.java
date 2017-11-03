package com.mulakat.huseyin.mulakat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mulakat.huseyin.data.ArchiveItemComponent;
import com.mulakat.huseyin.interfaces.ontaskend;
import com.mulakat.huseyin.task.wiredDetailTask;
import com.mulakat.huseyin.util.statics;

public class DetailActivity extends AppCompatActivity implements ontaskend {

    TextView textInfo, textTitle, textDesc, textWords;
    ImageView imImageView;
    wiredDetailTask wiredDetailTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imImageView = (ImageView)findViewById(R.id.imageView2);
        textInfo = (TextView)findViewById(R.id.textInfo);
        textTitle = (TextView)findViewById(R.id.textTitle);
        textDesc = (TextView)findViewById(R.id.textDesc);
        textWords = (TextView)findViewById(R.id.textWord);
        textDesc.setText("");
        textWords.setText("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        statics.setCurrentActivity(this);

        if (statics.getCurrentComponent() != null) {
            textTitle.setText(statics.getCurrentComponent().getArchiveTitle());
            textInfo.setText(statics.getCurrentComponent().getArchiveTime() + " | " + statics.getCurrentComponent().getArchiveAuthor());
            if (statics.getCurrentComponent().getImage() != null) {
                imImageView.setImageBitmap(statics.getCurrentComponent().getImage());
            }
            wiredDetailTask = new wiredDetailTask(this);
            statics.getCurrentComponent().setWordListener(this);
            wiredDetailTask.execute(statics.getCurrentComponent().getArchiveLink());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onEnd(int resp, String item, Object data) {
        if (resp == 0 && data != null) {
            textDesc.setText(data.toString());
            statics.getCurrentComponent().setArchiveText(data.toString());
        }
        else {
            ArchiveItemComponent component = (ArchiveItemComponent)data;
            textWords.setText(component.getWords());
        }
    }
}
