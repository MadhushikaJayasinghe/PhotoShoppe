package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_initial)
public class InitialActivity extends RoboActivity {

    @InjectView(R.id.initial_name) private EditText userNameTxt;
    @InjectView(R.id.initial_email) private EditText emailTxt;
    @InjectView(R.id.initial_password) private EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onClick(View view){
        SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString( "user_name",userNameTxt.getText().toString());
        editor.putString( "email",emailTxt.getText().toString());
        editor.putString( "password",passwordTxt.getText().toString());
        editor.commit();

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
