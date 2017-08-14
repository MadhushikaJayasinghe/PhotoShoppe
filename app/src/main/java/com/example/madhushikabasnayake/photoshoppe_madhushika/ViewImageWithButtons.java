package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities.Image;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.Telephony.Mms.Part.FILENAME;
import static android.support.v4.content.FileProvider.getUriForFile;
import static java.security.AccessController.getContext;

public class ViewImageWithButtons extends AppCompatActivity {
    String url;
    String webUrl;
    Image[] array;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_with_buttons);
        Intent i = getIntent();
        url = i.getExtras().getString("url");
        webUrl = i.getExtras().getString("weburl2");
        array = (Image[]) i.getExtras().getSerializable("imageDetailsArray2");
        position = i.getExtras().getInt("position2");

        final ImageView imageView = (ImageView) findViewById(R.id.image_viewed);
        //final ImageView imageViewTesting = (ImageView) findViewById(R.id.testing_image);
        Picasso.with(this)
                .load(url)
                .into(imageView);

        Button webButton = (Button) findViewById(R.id.go_to_website);
        Button email = (Button) findViewById(R.id.attached_email);

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(webUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Picasso.with(ViewImageWithButtons.this).load(url).into(target);

                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + array[position].getTitle() + ".png");


                Uri uri = Uri.fromFile(file);
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                //emailIntent.setType("application/image");
                emailIntent.setType("image/png");
                //emailIntent.setData(Uri.parse(url));
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                //startActivityForResult(emailIntent,0);


            }
        });


    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + array[position].getTitle() + ".png");
            try {
                while (true) {
                    if (isStoragePermissionGranted()) {
                        file.createNewFile();
                        FileOutputStream outputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");

                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

                int permsRequestCode = 200;

                requestPermissions(perms, permsRequestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        //Log.d(TAG,"permission granted : "+storageAccepted);
                    }

                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
