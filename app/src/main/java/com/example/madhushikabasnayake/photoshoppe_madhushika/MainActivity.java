package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onClick(View view){
        int id=view.getId();

        if(id == R.id.portfolio_button){
            Intent intent =new Intent(this,ViewImageList.class);
            startActivity(intent);
        }
        if(id==R.id.directory_button){
            Intent intent=new Intent(this,PhotographersList.class);
            startActivity(intent);
        }
        if(id==R.id.more_button){
            Intent intent=new Intent(this,DisplayDetails.class);
            startActivity(intent);
        }
    }
}
