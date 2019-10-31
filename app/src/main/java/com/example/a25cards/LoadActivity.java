package com.example.a25cards;

import android.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LoadActivity extends Activity {

    //time for picture display
    private static final int LOAD_DISPLAY_TIME = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.load);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //Go to main activity, and finish load activity
                Intent mainIntent = new Intent(LoadActivity.this, MainActivity.class);
                LoadActivity.this.startActivity(mainIntent);
                LoadActivity.this.finish();
            }
        }, LOAD_DISPLAY_TIME);
    }
}