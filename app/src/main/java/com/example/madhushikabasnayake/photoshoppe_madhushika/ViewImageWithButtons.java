package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImageWithButtons extends AppCompatActivity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_with_buttons);
        Intent i=getIntent();
        url=i.getExtras().getString("url");
        ImageView imageView=(ImageView)findViewById(R.id.image_viewed);
        Picasso.with(this)
                .load(url)
                .into(imageView);
    }

    public void onClick(View view){
        int id=view.getId();
        if(id==R.id.attached_email){
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/image");
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }else if(id==R.id.go_to_website){

        }
    }
}
