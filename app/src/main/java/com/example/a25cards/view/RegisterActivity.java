package com.example.a25cards.view;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.a25cards.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username, et_password,et_kickname;
    private String username, password, kickname;
    private Button rebt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏、隐藏状态栏
        setContentView(R.layout.activity_register);
        et_username = findViewById(R.id.reet_username);
        et_password = findViewById(R.id.reet_password);
        et_kickname = findViewById(R.id.reet_kickname);
        rebt_register = findViewById(R.id.rebt_register);

    }
}
