package com.example.a25cards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username, et_password,et_kickname;
    private String username, password, kickname;
    private Button rebt_register;
    private Intent it;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏、隐藏状态栏
        setContentView(R.layout.activity_register);
        et_username =(EditText) findViewById(R.id.reet_username);
        et_password =(EditText) findViewById(R.id.reet_password);
        et_kickname =(EditText) findViewById(R.id.reet_kickname);
        rebt_register =(Button) findViewById(R.id.rebt_register);

        rebt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(RegisterActivity.this,LoginActivity.class);
                bundle = new Bundle();
                bundle.putString("userName",et_username.getText().toString());
                it.putExtras(bundle);
                startActivity(it);
                RegisterActivity.this.finish();
            }
        });

    }
}
