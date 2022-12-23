package com.example.mpproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
    FloatingActionButton floatingActionButton;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initTab();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabHost.setCurrentTabByTag("ARTIST");
                showToast("Click?");
            }
        });
    }

    void init() {
        floatingActionButton = findViewById(R.id.mainAppendMemo);


    }

    void initTab() {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpecMain = tabHost.newTabSpec("MAIN").setIndicator("메인페이지");
        TabHost.TabSpec tabSpecWrite = tabHost.newTabSpec("WRITE").setIndicator("글쓰기");
        TabHost.TabSpec tabSpecSetting = tabHost.newTabSpec("SETTING").setIndicator("세팅");
        tabSpecMain.setContent(R.id.tabSong);
        tabSpecWrite.setContent(R.id.tabArtist);
        tabSpecSetting.setContent(R.id.tabAlbum);
        tabHost.addTab(tabSpecMain);
        tabHost.addTab(tabSpecWrite);
        tabHost.addTab(tabSpecSetting);
    }

    void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}