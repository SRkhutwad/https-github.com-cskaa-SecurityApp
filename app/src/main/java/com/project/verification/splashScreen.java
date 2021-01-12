package com.project.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class splashScreen extends AppCompatActivity implements Runnable
{

    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        t=new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        Intent i = new Intent(this,SecondActivity.class);
        startActivity(i);
        finish();
    }
}