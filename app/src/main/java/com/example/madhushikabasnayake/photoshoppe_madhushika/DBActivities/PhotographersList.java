package com.example.madhushikabasnayake.photoshoppe_madhushika.DBActivities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madhushikabasnayake.photoshoppe_madhushika.DisplayDetails;
import com.example.madhushikabasnayake.photoshoppe_madhushika.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import static android.app.PendingIntent.getActivity;

//@ContentView(R.layout.activity_photographers_list)
public class PhotographersList extends AppCompatActivity {

    //@InjectView(R.id.photographer_detail_list)
    ListView listView;

    private Context context;


    public class Placeholder {
        public TextView firstnameTV;
        public TextView lastnameTV;
        public TextView mobileTV;
        public TextView emailTV;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographers_list);
        listView = (ListView) findViewById(R.id.photographer_detail_list);
        DBHelper handler = new DBHelper(this);


        final Photographer[] usersList = handler.getPhotographers();

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return usersList.length;
            }

            @Override
            public Object getItem(int i) {
                return usersList[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup viewGroup) {

                Photographer photographer = usersList[i];
                View cellUser = null;

                if (convertView == null) {
                    cellUser = LayoutInflater.from(PhotographersList.this).inflate(R.layout.detail_list_cell, viewGroup, false);
                    //cellUser = LayoutInflater.from(PhotographersList.this).inflate(getActivity(), R.layout.detail_list_cell, null);
                    //cellUser = convertView.inflate(context, R.layout.detail_list_cell, null);
                    //View.inflate(context, R.layout.dialog_edit, null);
                    //convertView = infalInflater.inflate(R.layout.list_item, parent, false);
                    //View.inflate(getActivity(), R.layout.dialog, null);
                } else {
                    cellUser = convertView;
                }

                Placeholder ph = (Placeholder) cellUser.getTag();
                final TextView firstnameTV;
                TextView lastnameTV;
                final TextView mobileTV;
                final TextView emailTV;

                if (ph == null) {
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
                } else {
                    firstnameTV = ph.firstnameTV;
                    lastnameTV = ph.lastnameTV;
                    mobileTV = ph.mobileTV;
                    emailTV = ph.emailTV;
                }

                firstnameTV.setText(photographer.firstName);
                lastnameTV.setText(photographer.lastName);
                mobileTV.setText(photographer.phone);
                mobileTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(PhotographersList.this);
                        builder.setTitle("Make a call?");
                        builder.setMessage("Do you want to call " + mobileTV.getText().toString() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + mobileTV.getText().toString()));

                                if (ActivityCompat.checkSelfPermission(PhotographersList.this,
                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                startActivity(callIntent);
                                // User clicked OK button
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                // User cancelled the dialog
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();


                    }
                });

                emailTV.setText(photographer.email);
                emailTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Log.i("Send email", "");
                        String[] TO = {emailTV.getText().toString()};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);

                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello " + firstnameTV.getText().toString() + "");

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            //Log.i("Finished sending email...", "");
                        } catch (android.content.ActivityNotFoundException ex) {

                        }
                    }
                });

                return cellUser;
            }
        });
    }
}
