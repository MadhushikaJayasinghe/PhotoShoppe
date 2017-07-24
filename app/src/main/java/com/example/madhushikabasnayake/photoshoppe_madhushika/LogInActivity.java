package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_log_in)
public class LogInActivity extends RoboActivity {

    @InjectView(R.id.login_name) private EditText loginNameTxt;
    @InjectView(R.id.login_password) private  EditText loginPasswordTxt;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onClick(View view){
        SharedPreferences preferences = getSharedPreferences("com.irononetech.persistencedemo", 0);
        String userNameStored = preferences.getString("user_name", "NONE");
        String passwordStored=preferences.getString("password","NONE");

        String userNameGiven=loginNameTxt.getText().toString();
        String passwordGiven=loginPasswordTxt.getText().toString();

        if(userNameGiven==userNameStored && passwordGiven==passwordStored){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"User name or password incorrect",Toast.LENGTH_SHORT);
        }
    }
}
