package com.codepath.apps.twitterapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.twitterapp.TwitterApplication;
import com.codepath.apps.twitterapp.TwitterClient;
import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pallaud on 6/27/16.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get singleton client which can be used for requests
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    //Send API request to get the timeline JSON, fill listview by creating tweet objects from JSON
    private void populateTimeline() {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {

            //JSON array since root is JSON array
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //need to deserialize JSON, create models, load data into listview
                //dont need access to fragment anymore since this is fragment, call addAll on self
                addAll(Tweet.fromJsonArray(response));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG", responseString);
            }

        });
    }
}
