package com.example.imageclassification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FeedBack extends ClassAndMethods
{
    ImageButton github,mail;
    FloatingActionButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        github=findViewById(R.id.github);
        mail=findViewById(R.id.mail);
        back=findViewById(R.id.back);

        github.setOnClickListener(v->
        {
            //please add code for intent to open github repo
        });

        mail.setOnClickListener(v->
        {
            //please add code for intent to open mail
        });

        back.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),MainActivity.class)));
    }
}