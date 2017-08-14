package com.example.madhushikabasnayake.photoshoppe_madhushika.BasicActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        setContentView(R.layout.activity_first_layout);
        SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
        Button log_in_Btn=(Button)findViewById(R.id.first_log_in_button);
        Button sign_in_Btn=(Button)findViewById(R.id.first_sign_in_button);

        log_in_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstLayout.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        sign_in_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstLayout.this, InitialActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        if (preferences.getBoolean("log_in_status", false)) {
            Intent intent =new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );

            startActivity(intent);
            finish();

        }

        File database = getApplicationContext().getDatabasePath(dbHelper.DATABASE_NAME);
        if (database.exists() == false) {
            dbHelper.getReadableDatabase();
            if (copyDatabase(this)) {
//                Toast.makeText(this, "copy database success", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "copy database error", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            //Toast.makeText(this,"copy database",Toast.LENGTH_SHORT).show();
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
