package com.codepath.apps.twitterapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by pallaud on 6/27/16.
 *
 * This class should encapsulate attributes of tweets. It parses and stores the JSON data.
 */
public class Tweet {

    //Attributes
    private String body;
    private long id; //unique tweet id
    private User user;
    private String createdAt;
    private int favoriteCount;
    private int retweetCount;
    private boolean favorited;
    private boolean retweeted;

    public boolean getFavorited() { return favorited; }

    public boolean getRetweeted() { return retweeted; }

    public String getFavoriteCount() {
        return Integer.toString(favoriteCount);
    }

    public String getRetweetCount() {
        return Integer.toString(retweetCount);
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    //Deserialize JSON & build tweet object
    //Extract values, store, return
    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.id = jsonObject.getLong("id");
            tweet.createdAt = getTimeDifference(jsonObject.getString("created_at"));
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.favorited = jsonObject.getBoolean("favorited");
            tweet.retweeted = jsonObject.getBoolean("retweeted");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    //convert from JSONArray to array of tweet objects
    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(tweetJson);
                if(tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    public static String getTimeDifference(String rawJsonDate) {
        String time = "";
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        format.setLenient(true);
        try {
            long diff = (System.currentTimeMillis() - format.parse(rawJsonDate).getTime()) / 1000;
            if (diff < 5)
                time = "Just now";
            else if (diff < 60)
                time = String.format(Locale.ENGLISH, "%ds",diff);
            else if (diff < 60 * 60)
                time = String.format(Locale.ENGLISH, "%dm", diff / 60);
            else if (diff < 60 * 60 * 24)
                time = String.format(Locale.ENGLISH, "%dh", diff / (60 * 60));
            else if (diff < 60 * 60 * 24 * 30)
                time = String.format(Locale.ENGLISH, "%dd", diff / (60 * 60 * 24));
            else {
                Calendar now = Calendar.getInstance();
                Calendar then = Calendar.getInstance();
                then.setTime(format.parse(rawJsonDate));
                if (now.get(Calendar.YEAR) == then.get(Calendar.YEAR)) {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
                } else {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                            + " " + String.valueOf(then.get(Calendar.YEAR) - 2000);
                }
            }
        }  catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

}
