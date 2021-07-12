package com.dogfeeder;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {
    protected Button login;
    protected Button create_new;
    protected TextView password;
    protected TextView username;
    protected EditText account_name;
    protected EditText account_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        create_new = findViewById(R.id.create_new);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        account_name = findViewById(R.id.account_name);
        account_password = findViewById(R.id.account_password);
        String s = String.valueOf(account_name.getText());
        String s1 = String.valueOf(account_password.getText());
        Context context = loginActivity.this;
        create_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSetUpActivity();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogRegHandler LRH = new LogRegHandler(s,s1);
                LRH.loginUser(context);
                gotoMainActivity();
            }
        });
    }

    private void gotoSetUpActivity() {
        Intent intent =new Intent(this,setupActivity.class);
        startActivity(intent);

    }
    private void gotoMainActivity() {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}