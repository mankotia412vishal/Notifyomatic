package com.example.todo;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Categories extends AppCompatActivity {
    //ImageView backbtn;
//    LottieAnimationView;
//    ImageView photo;

    RelativeLayout support, abc,Geof,imageViewtodo,photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getSupportActionBar().hide();
        abc=findViewById(R.id.mapv);
        Geof=findViewById(R.id.Geof);
        support=findViewById(R.id.support);
        imageViewtodo=findViewById(R.id.imageViewtodo);

        photo=findViewById(R.id.userph);
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Categories.this,DashBoard.class);
                Categories.super.onBackPressed();
                startActivity(intent);
                finish();
            }
        });

        Geof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Categories.this,MapsActivity.class);

                startActivity(intent);
                finish();
            }
        });

        imageViewtodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Categories.this,MainActivity.class);

                startActivity(intent);
                finish();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Categories.this,UserProfile.class);

                startActivity(intent);
                finish();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Categories.this,Support.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        IsFinish("Want to close app?");
    }

    public void IsFinish(String alertmessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        System.exit(0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        // This above line close correctly
                        //finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Categories.this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

}