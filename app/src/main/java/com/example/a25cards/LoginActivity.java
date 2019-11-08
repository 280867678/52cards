package com.example.a25cards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username, et_password;
    private String username,password;
    private Button bt_login, bt_register;
    private CheckBox cb_rem;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏、隐藏状态栏
        setContentView(R.layout.activity_login);
        //获取控件id
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        cb_rem = findViewById(R.id.cb_rem);
        //记住账号密码
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        //SharedPreferences SharedPreferences = getSharedPreferences("configuration",0);
        editor = sharedPreferences.edit();
        username = sharedPreferences.getString("username","");
        password = sharedPreferences.getString("username","");
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        if(username==null && password==null && bundle==null){
            et_username.setText("");
            et_password.setText("");
        }else if(bundle!=null){
            String userName = bundle.getString("userName");
            et_username.setText(userName);
            et_password.setText("");
        }
        else if(username!=null && password!=null && bundle==null){
            et_username.setText(username);
            et_password.setText(password);
            cb_rem.setChecked(true);
        }

        //login function
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                if(username.equals("123")&&password.equals("123")){
                    //记住账号密码
                    if (cb_rem.isChecked()){
                        editor.putString("username",username);
                        editor.putString("password",password);
                        editor.commit();
                    }
                    Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"账号或密码错误，请重新输入",Toast.LENGTH_LONG).show();
                }
            }
        });
        //register function
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
