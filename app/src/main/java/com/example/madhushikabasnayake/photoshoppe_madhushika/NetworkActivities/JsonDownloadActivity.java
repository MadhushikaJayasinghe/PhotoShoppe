package com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.madhushikabasnayake.photoshoppe_madhushika.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonDownloadActivity extends AppCompatActivity {
    private String TAG = JsonDownloadActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView listView;
    public String[] idList;
    public String[] urlList;

    // URL to get contacts JSON
    private static String url = "https://api.flickr.com/services/feeds/photos_public.gne?id=26156338@N07&format=json&nojsoncallback=1";
    //http://api.androidhive.info/contacts/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_download);
        listView=(ListView)findViewById(R.id.image_list);
        idList=new String[10];
        urlList=new String[10];
        new GetJson().execute();

    }

    private class GetJson extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(JsonDownloadActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String[] doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            //return null;

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    //JSONObject jsonObject = jsonObj.getJSONObject("jsonFlickrFeed");
                    JSONArray photos=jsonObject.getJSONArray("items");

                        for (int i = 0; i < 10; i++) {
                            JSONObject c = photos.getJSONObject(i);

                            JSONObject imgLinkjson = c.getJSONObject("media");
                            String imgLink= imgLinkjson.getString("m");

                            urlList[i]=imgLink;
                        }
                    }// looping through All Contacts
                catch (final JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return urlList;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();

            }
            ImageListAdapter imageListAdapter=new ImageListAdapter(getApplicationContext(),R.layout.activity_image_list_adapter,result);
            listView.setAdapter(imageListAdapter);
        }
    }
}
