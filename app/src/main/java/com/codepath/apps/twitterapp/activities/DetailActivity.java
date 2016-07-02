package com.codepath.apps.twitterapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.utils.SetUpTweet;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Tweet tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        SetUpTweet sutweet = new SetUpTweet(getApplicationContext(), tweet, true);
        sutweet.setUp(this.findViewById(android.R.id.content));
    }


}
