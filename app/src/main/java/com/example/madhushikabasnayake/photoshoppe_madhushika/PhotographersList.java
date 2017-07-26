package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class PhotographersList extends RoboActivity {

   // DBHelper dbHelper=new DBHelper(this);

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {


//        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.detail_list_cell,dbHelper.getPhotographers());
//        ListView listView=(ListView)findViewById(R.id.photographer_detail_list);
//        listView.setAdapter(adapter);




    public class Placeholder{
        public TextView firstnameTV;
        public TextView lastnameTV;
        public TextView mobileTV;
        public TextView emailTV;
    }

    @InjectView(R.id.photographer_detail_list) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographers_list);

        DBHelper handler = new DBHelper(this);
        final Photographer[] usersList = handler.getPhotographers();

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return usersList.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup viewGroup) {

                Photographer photographer = usersList[i];
                View cellUser;

                if(convertView == null){
                    cellUser = LayoutInflater.from(PhotographersList.this).inflate(R.layout.detail_list_cell,null);
                }
                else{
                    cellUser = convertView;
                }

                Placeholder ph = (Placeholder) cellUser.getTag();
                final TextView firstnameTV;
                TextView lastnameTV;
                final TextView mobileTV;
                final TextView emailTV;

                if(ph == null){
                    firstnameTV = (TextView) cellUser.findViewById(R.id.list_first_name);
                    lastnameTV = (TextView) cellUser.findViewById(R.id.list_last_name);
                    mobileTV = (TextView) cellUser.findViewById(R.id.list_telephone_number);
                    emailTV = (TextView) cellUser.findViewById(R.id.list_email_address);

                    ph = new Placeholder();
                    ph.firstnameTV = firstnameTV;
                    ph.lastnameTV = lastnameTV;
                    ph.mobileTV = mobileTV;
                    ph.emailTV = emailTV;

                    cellUser.setTag(ph);
                }
                else {
                    firstnameTV = ph.firstnameTV;
                    lastnameTV = ph.lastnameTV;
                    mobileTV = ph.mobileTV;
                    emailTV=ph.emailTV;
                }

                firstnameTV.setText(photographer.firstName);
                lastnameTV.setText(photographer.lastName);
                mobileTV.setText(photographer.phone);
                mobileTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(PhotographersList.this,"make call",Toast.LENGTH_SHORT).show();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+mobileTV.getText().toString()));

                        if (ActivityCompat.checkSelfPermission(PhotographersList.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);

                    }
                });

                emailTV.setText(photographer.email);
                emailTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("Send email", "");
                        String[] TO = {emailTV.getText().toString()};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);

                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello"+firstnameTV.getText().toString()+"");

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();
                            Log.i("Finished sending email...", "");
                        } catch (android.content.ActivityNotFoundException ex) {

                        }
                    }
                });

                return cellUser;
            }
        });
    }
}
