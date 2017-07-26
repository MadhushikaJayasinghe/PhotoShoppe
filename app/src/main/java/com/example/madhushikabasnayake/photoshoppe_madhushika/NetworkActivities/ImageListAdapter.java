package com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.madhushikabasnayake.photoshoppe_madhushika.R;
import com.squareup.picasso.Picasso;

public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] imageUrls;

    public ImageListAdapter(Context context, String[] imageUrls) {
        super(context, R.layout.activity_image_list_adapter, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.activity_image_list_adapter, parent, false);
        }

        Picasso.with(context)
                .load(imageUrls[position])
                .placeholder(R.drawable.waiting_loading_image)
                .error(R.drawable.error_loading_image)
                .into((ImageView) convertView);


        return convertView;
    }
}