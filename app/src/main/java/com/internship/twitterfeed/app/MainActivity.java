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

        Tweet tweet = new Tweet();
        tweet.author = "test1";
        tweet.content = "asdfo134905805sfasdlfaksldfjaklsdfj304958";
        ArrayList<Tweet> items = new ArrayList<Tweet>();
        items.add(tweet);

        TweetListAdaptor adaptor = new TweetListAdaptor(this,R.layout.list_item, items);
        setListAdapter(adaptor);

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
            bt.setText(o.content);
            tt.setText(o.author);

            return v;
        }
    }
}