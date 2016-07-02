package com.codepath.apps.twitterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.codepath.apps.twitterapp.activities.DetailActivity;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.codepath.apps.twitterapp.utils.SetUpTweet;

import java.util.List;

/**
 * Created by pallaud on 6/27/16.
 *
 * This will take tweet objects and turns them into views that are displayed in list view.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    private Context context;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
        this.context = context;
    }

    //Override and setup custom templates

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get tweet
        final Tweet tweet = getItem(position);
        final User user = tweet.getUser();

        // Find/inflate template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        //On click listener for items in list
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("tweet", (Tweet) tweet);
                getContext().startActivity(i);
                ((Activity)getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //Sets up views
        SetUpTweet sutweet = new SetUpTweet(context, tweet, false);
        return sutweet.setUp(convertView);
    }
}
