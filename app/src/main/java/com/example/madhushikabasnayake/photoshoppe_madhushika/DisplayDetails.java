package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DisplayDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);
    }

    public void onClick(View view){
        int id=view.getId();
        if(id==R.id.my_telephone){
            Toast.makeText(this,"make call",Toast.LENGTH_SHORT).show();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:0765849256"));

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        }



        else if(id==R.id.my_email){
            Toast.makeText(this,"make email",Toast.LENGTH_SHORT).show();


            Log.i("Send email", "");
            String[] TO = {"ruchirar@irononetech.com"};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry for the Photo Schoppe");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                Log.i("Finished sending email...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }


        }


        else if(id==R.id.log_out){
            SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
            boolean loginStatus= preferences.getBoolean("log_in_status", true);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("log_in_status",false);
            Intent intent=new Intent(this,FirstLayout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            editor.commit();
        }

        else if(id==R.id.view_location){
            Intent intent=new Intent(this,viewMapLocationActivity.class);
            startActivity(intent);
        }

    }
}
