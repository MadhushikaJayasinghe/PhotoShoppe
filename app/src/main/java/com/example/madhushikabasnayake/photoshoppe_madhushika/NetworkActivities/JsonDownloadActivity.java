package com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.madhushikabasnayake.photoshoppe_madhushika.BasicActivities.MainActivity;
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
    //public String[] idList;
    //public String[] urlList;
    public ArrayList<Image> imageDetailsArray;
    //ArrayList<HashMap<String, String>> contactList;
    // URL to get contacts JSON
    private static String url = "https://api.flickr.com/services/feeds/photos_public.gne?id=86071258@N00&format=json&nojsoncallback=1";
    //http://api.androidhive.info/contacts/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageDetailsArray = new ArrayList<>();
        //urlList=new String[10];
        ///contactList = new ArrayList<>();
        if (isNetworkAvailable()) {
            setContentView(R.layout.activity_json_download);
            listView = (ListView) findViewById(R.id.image_list);
            new GetJson().execute();
        } else {
            Toast.makeText(this, "Connect Internet first", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_view_image);
            ImageView imageView = (ImageView) findViewById(R.id.selected_image);
            imageView.setImageResource(R.drawable.oops);
            imageView.setBackgroundColor(getResources().getColor(R.color.colorRed));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(JsonDownloadActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class GetJson extends AsyncTask<Void, Void, ArrayList<Image>> {

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
        protected ArrayList<Image> doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            //return null;

            if (jsonStr != null) {
                Log.e(TAG, "inside if from url: ");

                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    //JSONObject jsonObject = jsonObj.getJSONObject("jsonFlickrFeed");
                    JSONArray photos = jsonObject.getJSONArray("items");

                    for (int i = 0; i < 10; i++) {
                        JSONObject c = photos.getJSONObject(i);

                        String title = c.getString("title");
                        JSONObject imgLinkjson = c.getJSONObject("media");
                        String imgLink = imgLinkjson.getString("m");
                        String webUrl = c.getString("link");
                        String date = c.getString("date_taken");
                        Log.d(TAG,imgLink);
                        if (!imgLink.equals(null)) {
                            Image image = new Image(title, imgLink, webUrl, date);
                            imageDetailsArray.add(image);
                        }
                        //urlList[i]=imgLink;
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

            return imageDetailsArray;
        }

        @Override
        protected void onPostExecute(ArrayList<Image> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            int length = result.size();
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (length < 1) {
                Toast.makeText(JsonDownloadActivity.this, "Internet connection error", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Image[] imageList=  result.toArray(new Image[result.size()]);

                ImageListAdapter imageListAdapter = new ImageListAdapter(getApplicationContext(), R.layout.activity_image_list_adapter, imageList);
                listView.setAdapter(imageListAdapter);
            }
        }
    }
}
