package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities.Image;
import com.squareup.picasso.Picasso;

import org.apache.velocity.context.Context;

import java.io.Serializable;

public class ViewImage extends AppCompatActivity implements Serializable {
    String url;
    ImageView imageView;
    int position;
    Image[] array;
    String weburl;
    private android.content.Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        final Intent i = getIntent();
        url = i.getExtras().getString("imgurl");
        weburl = i.getExtras().getString("weburl");
        position = i.getExtras().getInt("position");
        array = (Image[]) i.getExtras().getSerializable("imageDetailsArray");
        imageView = (ImageView) findViewById(R.id.selected_image);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewImage.this, ViewImageWithButtons.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("url", url);
//                intent.putExtra("weburl2", weburl);
//                //intent.putExtra("weburl2",array[position].getWebUrl());
//                startActivity(intent);
//            }
//        });
        Picasso.with(this)
                .load(url)
                .into(imageView);


        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onDownEvent(){
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(ViewImage.this, ViewImageWithButtons.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", url);
                    intent.putExtra("weburl2", weburl);
                    intent.putExtra("position2",position);
                    intent.putExtra("imageDetailsArray2",array);
                    //intent.putExtra("weburl2",array[position].getWebUrl());
                    startActivity(intent);
//                    return true;
//                }
//                return false;
            }
            public void onSwipeRight() {
               // Toast.makeText(ViewImage.this, "right", Toast.LENGTH_SHORT).show();
                if(position>0) {
                    position = position - 1;
                    url = array[position].getPngUrl();
                    Picasso.with(context)
                            .load(url)
                            .into(imageView);
                }

            }
            public void onSwipeLeft() {
                //Toast.makeText(ViewImage.this, "left", Toast.LENGTH_SHORT).show();
                if(position<9) {
                    position = position + 1;
                    url = array[position].getPngUrl();
                    Picasso.with(context)
                            .load(url)
                            .into(imageView);
                }
            }


        });
 }




}
