package com.example.madhushikabasnayake.photoshoppe_madhushika.BasicActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.madhushikabasnayake.photoshoppe_madhushika.DBActivities.PhotographersList;
import com.example.madhushikabasnayake.photoshoppe_madhushika.DisplayDetails;
import com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities.JsonDownloadActivity;
import com.example.madhushikabasnayake.photoshoppe_madhushika.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.portfolio_button) {
            Intent intent = new Intent(this, JsonDownloadActivity.class);
            startActivity(intent);
        }
        if (id == R.id.directory_button) {
            Intent intent = new Intent(this, PhotographersList.class);
            startActivity(intent);
        }
        if (id == R.id.more_button) {
            Intent intent = new Intent(this, DisplayDetails.class);
            startActivity(intent);
        }
    }
}
