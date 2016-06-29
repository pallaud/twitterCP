package com.codepath.apps.twitterapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterapp.activities.ProfileActivity;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

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

        // Populate data into views
        tvUsername.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvTagline.setText("@" + tweet.getUser().getScreenName());
        tvTimestamp.setText(tweet.getCreatedAt());
        ivProfileImage.setImageResource(0); //clear out old image for a recycled view
        Picasso.with(getContext()).load(tweet.getUser()
                .getProfileImageUrl()).transform(new RoundedCornersTransformation(10, 10)).into(ivProfileImage);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("user", user);
                Log.d("NAME",user.getScreenName());
                context.startActivity(i);
            }
        });

        // Return view to be inserted into list
        return convertView;
    }
}
