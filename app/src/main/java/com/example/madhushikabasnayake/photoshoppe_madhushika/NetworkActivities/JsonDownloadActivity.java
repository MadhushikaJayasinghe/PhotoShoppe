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
    private static String url = "https://api.flickr.com/services/rest/?method=flickr.people.getPhotos&api_key=e092219e549a3ef169b692521e692cb9&user_id=116475418%40N03&format=json&nojsoncallback=1&api_sig=e6c121e82acfd5e9c3439731348ea495";
    //http://api.androidhive.info/contacts/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_download);
        idList=new String[10];
        urlList=new String[10];
        new GetJson().execute();

    }

    private class GetJson extends AsyncTask<Void, Void, Void> {

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
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            //return null;

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("photos");

                    // looping through All Contacts
                    for (int i = 0; i < 10; i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        idList[i]=id;
                        String imageUrl="https://www.flickr.com/photos/iwonapodlasinska/"+id;
                        urlList[i]=imageUrl;

                    }
                } catch (final JSONException e) {
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

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();

            }
            ListView listView=(ListView)findViewById(R.id.image_list);
            listView.setAdapter(new ImageListAdapter(JsonDownloadActivity.this, urlList));
        }
    }
}
