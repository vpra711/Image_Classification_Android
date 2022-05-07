package com.example.imageclassification;

import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Comparator;

public class ClassAndMethods extends AppCompatActivity
{
    SharedPreferences pref;
    public void maketoast(String msg)
    { Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); }

    public void themeMethod(ImageButton theme)
    {
        final boolean[] checked = new boolean[1];
        pref = getSharedPreferences("Mypreference",MODE_PRIVATE);
        boolean bool = pref.getBoolean("THEME",false);
        if (bool)
        {
            theme.setImageResource(R.drawable.ic_lighttheme);
            checked[0] = true;
        }
        else
        {
            checked[0] = false;
            theme.setImageResource(R.drawable.ic_nighttheme);
        }
        theme.setOnClickListener(v -> {
            if (!checked[0]) {
                theme.setImageResource(R.drawable.ic_lighttheme);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                checked[0] = true;
                pref.edit().putBoolean("THEME", true).apply();
            } else {
                theme.setImageResource(R.drawable.ic_nighttheme);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                checked[0] = false;
                pref.edit().putBoolean("THEME", false).apply();
            }
        });
    }

}

class Fruit
{
    String name;
    float probability;

    Fruit(String name,float probability)
    {
        this.name=name;
        this.probability=probability;
    }
}

class CompareObj implements Comparator<Fruit>
{
    @Override
    public int compare(Fruit f1,Fruit f2)
    {
        if(f1.probability > f2.probability)
            return -1;
        else if(f1.probability == f2.probability)
            return 0;
        return 1;
    }
}