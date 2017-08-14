package com.example.madhushikabasnayake.photoshoppe_madhushika.BasicActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madhushikabasnayake.photoshoppe_madhushika.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_log_in)
public class LogInActivity extends RoboActivity {

    @InjectView(R.id.login_name)
    private EditText loginNameTxt;
    @InjectView(R.id.login_password)
    private EditText loginPasswordTxt;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onClick(View view) {
        SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
        //SharedPreferences.Editor=preferences.edit();
        String userNameStored = preferences.getString("user_name", "NONE");
        String passwordStored = preferences.getString("password", "NONE");
        SharedPreferences.Editor editor = preferences.edit();


        if (loginNameTxt.getText() != null && loginPasswordTxt.getText() != null) {

            String userNameGiven = loginNameTxt.getText().toString();
            String passwordGiven = loginPasswordTxt.getText().toString();
            if (userNameGiven.equals(userNameStored) && passwordGiven.equals(passwordStored)) {
                //Toast.makeText(this, "You are login now", Toast.LENGTH_SHORT);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
                editor.putBoolean("log_in_status", true);

            } else {
                Toast.makeText(this, getResources().getString(R.string.log_in_error), Toast.LENGTH_SHORT).show();

            }
        }
        if (loginNameTxt.getText().toString().equals("")) {

            loginNameTxt.setHint(getResources().getString(R.string.log_in_name_error));
            loginNameTxt.setHintTextColor(getResources().getColor(R.color.colorRed));
        }

        if (loginPasswordTxt.getText().toString().equals("")) {
            loginPasswordTxt.setHint(getResources().getString(R.string.log_in_password_error));
            loginPasswordTxt.setHintTextColor(getResources().getColor(R.color.colorRed));
        }
        editor.commit();

    }
}

