package com.example.imageclassification;

import static com.example.imageclassification.MainActivity.resultList;
import android.os.Bundle;
import android.util.Size;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class ScrollView extends ClassAndMethods
{
    TextView fullresultTV;
    List<Fruit> resultList1 = resultList;
    Toolbar toolbar2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        toolbar2 = findViewById(R.id.toolbartb2x);
        setSupportActionBar(toolbar2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview);
        fullresultTV = findViewById(R.id.fullresulttv2x);
        StringBuilder fullresult = new StringBuilder("\n");

        for (int i = 0; i < 1000; i++)
            fullresult.append(resultList1.get(i).name).append(" | ").append(resultList1.get(i).probability).append("\n\n");

        fullresultTV.setText(fullresult.toString());
        maketoast(fullresult.length() + "");
    }
}