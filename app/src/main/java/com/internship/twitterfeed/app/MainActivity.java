package com.internship.twitterfeed.app;


import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MyTask().execute();

    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "", "Loading. Please wait...", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpGet get = new
                        HttpGet("http://search.twitter.com/search.json?q=android");

                HttpResponse rp = hc.execute(get);

                if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String result = EntityUtils.toString(rp.getEntity());
                    JSONObject root = new JSONObject(result);
                    JSONArray sessions = root.getJSONArray("results");
                    for (int i = 0; i < sessions.length(); i++) {
                        JSONObject session = sessions.getJSONObject(i);

                        Tweet tweet = new Tweet();
                        tweet.content = session.getString("text");
                        tweet.author = session.getString("from_user");
                        tweets.add(tweet);
                    }
                }
            } catch (Exception e) {
                Log.e("TwitterFeedActivity", "Error loading JSON", e);
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            setListAdapter(new TweetListAdaptor(
                    MainActivity.this, R.layout.list_item, tweets));
        }

    }

    private class TweetListAdaptor extends ArrayAdapter<Tweet> {

        private ArrayList<Tweet> tweets;

        public TweetListAdaptor(Context context,

                                int textViewResourceId,
                                ArrayList<Tweet> items) {
            super(context, textViewResourceId, items);
            this.tweets = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }
            Tweet o = tweets.get(position);

            TextView tt = (TextView) v.findViewById(R.id.toptext);
            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
            tt.setText(o.content);
            bt.setText(o.author);

            return v;
        }
    }
}