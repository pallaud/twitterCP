package com.codepath.apps.twitterapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.models.Tweet;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Tweet tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        populateViews();
    }

    public void populateViews() {
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) findViewById(R.id.tvBody);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);
        TextView tvRetweetCount = (TextView) findViewById(R.id.tvRetweetCount);
        TextView tvFavoriteCount = (TextView) findViewById(R.id.tvFavoriteCount);
        ImageButton ibRetweet = (ImageButton) findViewById(R.id.ibRetweet);
        ImageButton ibFavorite = (ImageButton) findViewById(R.id.ibFavorite);
        ImageView ivImg = (ImageView) findViewById(R.id.ivImg);

    }
}
