package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImage extends AppCompatActivity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Intent i=getIntent();
         url=i.getExtras().getString("url");
        ImageView imageView=(ImageView)findViewById(R.id.selected_image);
        Picasso.with(this)
                .load(url)
                .into(imageView);
    }

    public void onClick(View view){
        Intent intent=new Intent(this,ViewImageWithButtons.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url",url);
        startActivity(intent);
    }

}
