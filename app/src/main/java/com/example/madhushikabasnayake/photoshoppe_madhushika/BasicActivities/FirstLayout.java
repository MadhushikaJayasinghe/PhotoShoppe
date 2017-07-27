package com.example.madhushikabasnayake.photoshoppe_madhushika.BasicActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.madhushikabasnayake.photoshoppe_madhushika.DBActivities.DBHelper;
import com.example.madhushikabasnayake.photoshoppe_madhushika.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FirstLayout extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
        if(!preferences.getBoolean("log_in_status",false)){
            setContentView(R.layout.activity_first_layout);
        }
        else {
            setContentView(R.layout.activity_main);
        }

        File database=getApplicationContext().getDatabasePath(dbHelper.DATABASE_NAME);
        if(database.exists()==false){
            dbHelper.getReadableDatabase();
            if(copyDatabase(this)){
                Toast.makeText(this,"copy database success",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"copy database error",Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            Toast.makeText(this,"copy database",Toast.LENGTH_SHORT).show();
        }
    }


    public void onClick(View view){
        int id=view.getId();
        SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
        //SharedPreferences.Editor editor = preferences.edit();

        if(!preferences.getBoolean("log_in_status",false)) {

            if (id == R.id.first_sign_in_button) {
//                Button button=(Button)findViewById(R.id.first_sign_in_button);
//                button.setVisibility(button.GONE);
                Intent intent = new Intent(this, InitialActivity.class);
                //intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (id == R.id.first_log_in_button) {
                //Button button=(Button)findViewById(R.id.first_log_in_button);
                Intent intent = new Intent(this, LogInActivity.class);
                //intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(dbHelper.DATABASE_NAME);
            String outFileName = dbHelper.DB_LOCATION + dbHelper.DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("MainActivity", "DBCopied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
