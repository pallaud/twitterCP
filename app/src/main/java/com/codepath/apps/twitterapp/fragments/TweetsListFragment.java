package com.codepath.apps.twitterapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.TweetsArrayAdapter;
import com.codepath.apps.twitterapp.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pallaud on 6/27/16.
 */
public class TweetsListFragment extends Fragment {
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter adapter;
    private ListView lvTweets;

    //creation life cycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        //adapter takes context (activity) , data source
        adapter = new TweetsArrayAdapter(getActivity(),tweets);
    }

    //inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);

        return view;
    }

    public void addAll(List<Tweet> tweets) {
        adapter.addAll(tweets);
    }


}
