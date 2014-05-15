package com.internship.twitterfeed.app;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tweet tweet = new Tweet();
        tweet.author = "ringo";
        tweet.content = "test1 as long as possible cuz f you thats why";
        ArrayList<Tweet> items = new ArrayList<Tweet>();
        items.add(tweet);

        TweetListAdaptor adaptor = new TweetListAdaptor(this,R.layout.list_item, items);
        setListAdapter(adaptor);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class TweetListAdaptor extends ArrayAdapter<Tweet> {

        private ArrayList<Tweet> tweets;

        public TweetListAdaptor(Context context, int textViewResourceId,  ArrayList<Tweet> items) {
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
            tt.setText(o.author);
            bt.setText(o.content);

            return v;
        }
    }
}


