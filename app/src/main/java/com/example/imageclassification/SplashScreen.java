package com.example.imageclassification;

import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashScreen extends ClassAndMethods
{
    SharedPreferences pref;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        //sharedPreferences
        pref=getSharedPreferences("Mypreference",MODE_PRIVATE);
        boolean h=pref.getBoolean("THEME",false);
        if (h)
        { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); }
        else
        { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); }
        //initializatiom
        progressBar = findViewById(R.id.progressbarpb2x);

        //splash screen
        new Handler().postDelayed(() ->
        {
            try
            {
                java.lang.Thread.sleep(500);
                progressBar.setProgress(60);
                java.lang.Thread.sleep(350);
                progressBar.setProgress(75);
                java.lang.Thread.sleep(250);
                progressBar.setProgress(98);
            }
            catch (InterruptedException e)
            { maketoast(e + ""); }
            startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        },100);
    }
}