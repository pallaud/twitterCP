package com.codepath.apps.twitterapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.twitterapp.TwitterApplication;
import com.codepath.apps.twitterapp.TwitterClient;
import com.codepath.apps.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pallaud on 6/29/16.
 */
public class TweetLookupFragment extends TweetsListFragment {
    private static TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static TweetLookupFragment newInstance(String query) {
        TweetLookupFragment lookupFragment = new TweetLookupFragment();
        client = TwitterApplication.getRestClient();
        Bundle args = new Bundle();
        args.putString("q", query);
        lookupFragment.setArguments(args);
        return lookupFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();
            }
        });
    }

    //Send API request to get the timeline JSON, fill listview by creating tweet objects from JSON
    public void populateTimeline() {
        client.getSearchTweets(getArguments().getString("q"), new JsonHttpResponseHandler() {
            //JSON array since root is JSON array
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //need to deserialize JSON, create models, load data into listview
                //dont need access to fragment anymore since this is fragment, call addAll on self
                clear();
                addAll(Tweet.fromJsonArray(response));
                Log.d("SEARCH",response.toString());
                Toast.makeText(getContext(), "made search", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("FAILURE",responseString);
            }

        });
    }

}
