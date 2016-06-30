package com.codepath.apps.twitterapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterapp.activities.ProfileActivity;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        }

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

        // Populate data into views
        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvTagline.setText("@" + tweet.getUser().getScreenName());
        tvTimestamp.setText(tweet.getCreatedAt());
        tvRetweetCount.setText(tweet.getRetweetCount());
        tvFavoriteCount.setText(tweet.getFavoriteCount());

        ibFavorite.setImageResource(0);
        if(tweet.getFavorited()) { ibFavorite.setImageResource(R.drawable.ic_favorite_pressed); }
        else { ibFavorite.setImageResource(R.drawable.ic_favorite); }

        ibRetweet.setImageResource(0);
        if(tweet.getRetweeted()) { ibRetweet.setImageResource(R.drawable.ic_retweet_pressed); }
        else { ibRetweet.setImageResource(R.drawable.ic_retweet); }

        ivProfileImage.setImageResource(0); //clear out old image for a recycled view
        Picasso.with(getContext()).load(tweet.getUser()
                .getProfileImageUrl()).transform(new RoundedCornersTransformation(3,3)).into(ivProfileImage);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("user", user);
                Log.d("NAME",user.getScreenName());
                context.startActivity(i);
            }
        });

        ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite(tweet);
                tvFavoriteCount.setText(tweet.getFavoriteCount());
                ibFavorite.setImageResource(R.drawable.ic_favorite_pressed);
            }
        });

        ibRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
