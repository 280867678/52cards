package com.example.a25cards;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.pm.ActivityInfo;


import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class MenuActivity extends AppCompatActivity {

    private Button out;
    private Button classical;
    private Button competition;
    private Button rank;
    private Button ending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏、隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.menu);
        out = (Button)findViewById(R.id.out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                MenuActivity.this.finish();
            }
        });
        classical = (Button)findViewById(R.id.classical);
        competition = (Button)findViewById(R.id.competition);
        rank = (Button)findViewById(R.id.rank);
        ending = (Button)findViewById(R.id.ending);
        classical.setX(1100);
        classical.setY(190);
        competition.setX(1500);
        competition.setY(190);
        rank.setX(1100);
        rank.setY(500);
        ending.setX(1500);
        ending.setY(500);
        classical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}