package com.example.a25cards.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a25cards.R;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username, et_password;
    private String username,password;
    private Button bt_login, bt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏、隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                if(username.equals("123")&&password.equals("123")){
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"账号或密码错误，请重新输入",Toast.LENGTH_LONG).show();
                }
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }
}
