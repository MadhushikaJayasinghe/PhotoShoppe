package com.example.madhushikabasnayake.photoshoppe_madhushika.BasicActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madhushikabasnayake.photoshoppe_madhushika.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_initial)
public class InitialActivity extends RoboActivity {

    @InjectView(R.id.initial_name)
    private EditText userNameTxt;
    @InjectView(R.id.initial_email)
    private EditText emailTxt;
    @InjectView(R.id.initial_password)
    private EditText passwordTxt;
    @InjectView(R.id.password_strong_text) private TextView passwordErrorTxt;

//    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onClick(View view) {
        SharedPreferences preferences = getSharedPreferences("com.irononetech.PhotoShoppe", 0);
        SharedPreferences.Editor editor = preferences.edit();


        if (userNameTxt.getText().toString().equals("") || emailTxt.getText().toString().equals("") || passwordTxt.getText().toString().equals("")) {
            //Toast.makeText(this,"You should fill all fields",Toast.LENGTH_SHORT);
            if (userNameTxt.getText().toString().equals("")) {
                userNameTxt.setHint(getResources().getString(R.string.sign_in_name_blank));
                userNameTxt.setHintTextColor(getResources().getColor(R.color.colorRed));
            }
            if (emailTxt.getText().toString().equals("")) {
                emailTxt.setHint(getResources().getString(R.string.sign_in_email_blank));
                emailTxt.setHintTextColor(getResources().getColor(R.color.colorRed));

            }
            if (passwordTxt.getText().toString().equals("")) {
                passwordTxt.setHint(getResources().getString(R.string.sign_in_password_blank));
                passwordTxt.setHintTextColor(getResources().getColor(R.color.colorRed));
            }


        }
        if(!isValidEmail(emailTxt.getText().toString())){
            //Toast.makeText(this,"invalid email",Toast.LENGTH_SHORT).show();
            emailTxt.setText("");
            emailTxt.setHint(getResources().getString(R.string.sign_in_email_invalid));
            emailTxt.setHintTextColor(getResources().getColor(R.color.colorRed));
        }
        if(!checkStrongPassword(passwordTxt.getText().toString())){
            passwordErrorTxt.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else {
            editor.putString("user_name", userNameTxt.getText().toString());
            editor.putString("email", emailTxt.getText().toString());
            editor.putString("password", passwordTxt.getText().toString());
            editor.putBoolean("log_in_status", true);
            Toast.makeText(this,"Your Registration was successful ",Toast.LENGTH_SHORT);

            editor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intent);
            this.finish();

        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        //return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean checkStrongPassword(String password){
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean isAtLeast8   = password.length() >= 8;//Checks for at least 8 characters
        boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*");
        boolean found=false;
        for(char c : password.toCharArray()){
            if(Character.isDigit(c)){
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return hasUppercase && hasLowercase && isAtLeast8 && hasSpecial && found;
    }
}
