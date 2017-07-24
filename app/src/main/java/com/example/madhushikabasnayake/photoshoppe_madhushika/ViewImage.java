package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ViewImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
    }

    public void onClick(View view){
        Intent intent=new Intent(this,ViewImageWithButtons.class);
        startActivity(intent);
    }

}
