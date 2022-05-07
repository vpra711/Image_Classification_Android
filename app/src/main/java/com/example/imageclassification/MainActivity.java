package com.example.imageclassification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.imageclassification.ml.EfficientnetLite3Fp322;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ClassAndMethods
{
    ImageButton helpIB, themeIB;
    ImageView imgIV;
    TextView resultTV;
    Button analyzeB, uploadB;
    Bitmap bmimg = null;
    boolean analyze = false;
    public static List<Fruit> resultList = new ArrayList<>();
    ActivityResultLauncher<Intent> arl;
    int UPLOAD_MODE = 0;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbartb1x);
        imgIV = findViewById(R.id.imgiv1x);
        resultTV = findViewById(R.id.resulttv1x);
        analyzeB = findViewById(R.id.analyzeb1x);
        uploadB = findViewById(R.id.uploadb1x);
        helpIB = findViewById(R.id.helpib1x);
        themeIB = findViewById(R.id.themeib1x);
        setSupportActionBar(toolbar);
        themeMethod(themeIB);

        helpIB.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), HelpFragment.class)));

        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result ->
        {
            if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null)
            {
                if(UPLOAD_MODE == 0)
                {
                    Bundle bundle=result.getData().getExtras();
                    bmimg = Bitmap.createBitmap((Bitmap) bundle.get("data")).copy(Bitmap.Config.ARGB_8888, true);
                    imgIV.setImageBitmap(bmimg);
                    UPLOAD_MODE = -1;
                }
                if (UPLOAD_MODE == 1)
                {
                    Uri uri=result.getData().getData();
                    try
                    { bmimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri); }
                    catch (IOException e)
                    { e.printStackTrace(); }
                    imgIV.setImageURI(uri);
                    UPLOAD_MODE = -1;
                }
            }
        });

        String[] modes = {"camera", "gallery"};

        uploadB.setOnClickListener((View v) ->
        {
            helpIB.setVisibility(View.INVISIBLE);
            resultTV.setText("");
            analyze = false;
            AlertDialog.Builder bl = new AlertDialog.Builder(MainActivity.this);
            bl.setTitle("Choose Mode");
            bl.setItems(modes, (dialog, which) ->
            {
                if (modes[which].equals("camera"))
                    camera();
                if (modes[which].equals("gallery"))
                    gallery();
            });
            bl.show();
        });

        analyzeB.setOnClickListener((View v) ->
        {
            if(bmimg == null)
                Toast.makeText(getApplicationContext(), "Upload a image", Toast.LENGTH_SHORT).show();
            else
            {
                analyze = true;
                getResult(bmimg);
                helpIB.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.viewallresultmi1x)
        {
            if(bmimg == null)
                maketoast("Upload a image");
            else if(!analyze)
                maketoast("Kindly analyze the image");
            else
                startActivity(new Intent(getApplicationContext(), ScrollView.class));
        }
        else if(id == R.id.aboutmi1x)
            startActivity(new Intent(getApplicationContext(), FeedBack.class));
        return super.onOptionsItemSelected(item);
    }

    public void camera()
    {
        UPLOAD_MODE = 0;
        arl.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    public void gallery()
    {
        UPLOAD_MODE = 1;
        arl.launch(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"));
    }

    public void getResult(Bitmap bm)
    {
        bm = Bitmap.createScaledBitmap(bm, 280, 280, false);
        List<Category> probability = null;
        resultTV.setText(R.string.process);
        try
        {
            EfficientnetLite3Fp322 model = EfficientnetLite3Fp322.newInstance(getApplicationContext());
            TensorImage image = TensorImage.fromBitmap(bm);
            EfficientnetLite3Fp322.Outputs outputs = model.process(image);
            probability = outputs.getProbabilityAsCategoryList();
            model.close();
        }
        catch (IOException e) { e.printStackTrace(); }

        StringBuilder finalResult = new StringBuilder("\n");
        resultList.clear();

        if(probability != null)
        {
            for (Category ci : probability)
                resultList.add(new Fruit(ci.getLabel(), ci.getScore()));

            resultList.sort(new CompareObj());

            int i;
            for(i = 0; i < 3; i++)
                finalResult.append("   ").append(resultList.get(i).name).append("\n\n");

            resultTV.setText(finalResult.toString());
        }
        else
            maketoast("probability is null");
    }
}