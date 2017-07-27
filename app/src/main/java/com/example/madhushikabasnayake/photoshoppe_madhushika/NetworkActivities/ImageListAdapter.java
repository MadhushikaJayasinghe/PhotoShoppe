package com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.madhushikabasnayake.photoshoppe_madhushika.R;
import com.example.madhushikabasnayake.photoshoppe_madhushika.ViewImage;
import com.squareup.picasso.Picasso;

public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] imageUrls;

    public ImageListAdapter(Context context, int activity_image_list_adapter, String[] imageUrls) {
        super(context, R.layout.activity_image_list_adapter, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.activity_image_list_adapter, parent, false);
            convertView = inflater.inflate(R.layout.activity_image_list_adapter, parent, false);
        }

        Picasso.with(context)
                .load(imageUrls[position])
                .placeholder(R.drawable.waiting_loading_image)
                        .error(R.drawable.error_loading_image)
                .into((ImageView) convertView.findViewById(R.id.image_view_cell));
        convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context, ViewImage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url",imageUrls[position]);
            //intent.putExtra("position",position);
            context.startActivity(intent);
    }
});

        return convertView;
    }
}