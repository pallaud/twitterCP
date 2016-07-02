package com.codepath.apps.twitterapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.TwitterApplication;
import com.codepath.apps.twitterapp.TwitterClient;
import com.codepath.apps.twitterapp.activities.ProfileActivity;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by pallaud on 7/1/16.
 */
public class SetUpTweet {
    Context context;
    Tweet tweet;
    User user;
    boolean detail;

    public SetUpTweet(Context context, Tweet tweet, boolean detail) {
        this.context = context;
        this.tweet = tweet;
        this.user = tweet.getUser();
        this.detail = detail;
    }

    public Context getContext() {
        return context;
    }

    //Sets up views for given convertView using tweet info
    public View setUp(View convertView) {
        // Fill in views
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        final TextView tvTagline = (TextView) convertView.findViewById(R.id.tvTagline);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        final TextView tvRetweetCount = (TextView) convertView.findViewById(R.id.tvRetweetCount);
        final TextView tvFavoriteCount = (TextView) convertView.findViewById(R.id.tvFavoriteCount);
        final ImageButton ibRetweet = (ImageButton) convertView.findViewById(R.id.ibRetweet);
        final ImageButton ibFavorite = (ImageButton) convertView.findViewById(R.id.ibFavorite);
        ImageView ivImg = (ImageView) convertView.findViewById(R.id.ivImg);

        // Populate data into views
        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvTagline.setText("@" + tweet.getUser().getScreenName());
        tvTimestamp.setText(tweet.getCreatedAt());
        if(detail) {
            tvRetweetCount.setText(tweet.getRetweetCount() + " RETWEETS");
            tvFavoriteCount.setText(tweet.getFavoriteCount() + " LIKES");
        } else {
            tvRetweetCount.setText(tweet.getRetweetCount());
            tvFavoriteCount.setText(tweet.getFavoriteCount());
        }

        ivImg.setImageResource(0);
        if(tweet.getImgUrl() != null) {
            Picasso.with(getContext()).load(tweet.getImgUrl()).
                    transform(new RoundedCornersTransformation(7,0)).into(ivImg);
        }

        ibFavorite.setImageResource(0);
        if(tweet.getFavorited()) { ibFavorite.setImageResource(R.drawable.ic_favorite_pressed); }
        else { ibFavorite.setImageResource(R.drawable.ic_favorite); }

        ibRetweet.setImageResource(0);
        if(tweet.getRetweeted()) { ibRetweet.setImageResource(R.drawable.ic_retweet_pressed); }
        else { ibRetweet.setImageResource(R.drawable.ic_retweet); }

        ivProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getUser()
                .getProfileImageUrl()).transform(new RoundedCornersTransformation(3,3)).into(ivProfileImage);

        //Onclick listeners for clickable items
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Take to tweeter's profile
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("user", user);
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Post request, modifies UI
                favorite(tweet);
                tvFavoriteCount.setText(tweet.getFavoriteCount());
                ibFavorite.setImageResource(R.drawable.ic_favorite_pressed);
            }
        });

        ibRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Post request, modifies UI
                retweet(tweet);
                tvFavoriteCount.setText(tweet.getRetweetCount());
                ibRetweet.setImageResource(R.drawable.ic_retweet_pressed);
            }
        });

        // Return view to be inserted into list
        return convertView;
    }

    public void favorite(Tweet tweet) {
        TwitterClient client = TwitterApplication.getRestClient();
        client.postFavorite(Long.toString(tweet.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG", responseString);
            }

        });
    }

    public void retweet(Tweet tweet) {
        TwitterClient client = TwitterApplication.getRestClient();
        client.postRetweet(Long.toString(tweet.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                Log.d("DEBUG", throwable.getMessage());
            }

        });
    }
}



