package com.example.imageclassification;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

public class HelpFragment extends ClassAndMethods
{
    ImageButton likeIB, dislikeIB;
    Toolbar toolbar3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        toolbar3 = findViewById(R.id.toolbartb3x);
        setSupportActionBar(toolbar3);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renaming);
        likeIB = findViewById(R.id.likeib1x);
        dislikeIB = findViewById(R.id.dislikeib1x);
        likeIB.setOnClickListener(v -> maketoast("This is like"));
        dislikeIB.setOnClickListener(v -> maketoast("This is dislike"));
    }
}