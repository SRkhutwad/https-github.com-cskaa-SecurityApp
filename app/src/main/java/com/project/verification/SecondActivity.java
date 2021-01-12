package com.project.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.verification.securityOtp.SendOtp;

public class SecondActivity extends AppCompatActivity
{

    TextView client,security;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        client = findViewById(R.id.client);
//        security = findViewById(R.id.security);

    }

    public void security(View view)
    {
        Intent intent = new Intent(getApplicationContext(), SendOtp.class);
        startActivity(intent);
    }

    public void client(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SendOTPActivity.class);
        startActivity(intent);
    }
}