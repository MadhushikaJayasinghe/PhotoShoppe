package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_initial)
public class InitialActivity extends RoboActivity {

    @InjectView(R.id.initial_name) private EditText userNameTxt;
    @InjectView(R.id.initial_email) private EditText emailTxt;
    @InjectView(R.id.initial_password) private EditText passwordTxt;

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File database=getApplicationContext().getDatabasePath(dbHelper.DATABASE_NAME);
        if(false==database.exists()){
            dbHelper.getReadableDatabase();
            if(copyDatabase(this)){
                Toast.makeText(this,"copy database success",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"copy database error",Toast.LENGTH_SHORT).show();
                return;
            }
        }


        SharedPreferences pref = getSharedPreferences("com.irononetech.PhotoShoppe", 0);

        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);

            if(pref.getBoolean("log_in_status",true)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            }
            ed.commit();
        }
    }


    public void onClick(View view) {
        SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("log_in_status",true);
        editor.putString("user_name", userNameTxt.getText().toString());
        editor.putString("email", emailTxt.getText().toString());
        editor.putString("password", passwordTxt.getText().toString());
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
