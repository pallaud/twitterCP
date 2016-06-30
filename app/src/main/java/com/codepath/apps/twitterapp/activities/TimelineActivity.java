package com.codepath.apps.twitterapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.SmartFragmentStatePagerAdapter;
import com.codepath.apps.twitterapp.fragments.ComposeDialogFragment;
import com.codepath.apps.twitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterapp.fragments.MentionsTimelineFragment;
import com.codepath.apps.twitterapp.models.Tweet;

public class TimelineActivity extends AppCompatActivity
        implements ComposeDialogFragment.OnComposeListener {
    private TweetsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //Get viewpager, set viewpager adapter, find pager sliding tabs, attach pager tabs to viewpager
        //viewpager indicator displays which page you're on within viewpager (top bar)
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new TweetsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);

    }

    public void onProfileView(MenuItem item) {
        //Launch profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent i = new Intent(this, SearchActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void compose(View view) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment frag = new ComposeDialogFragment();
        frag.show(fm, "fragment_compose");
    }

    public void onUpdateTimeline(Tweet tweet) {
        HomeTimelineFragment fragmentHomeTweets =
                (HomeTimelineFragment) adapter.getRegisteredFragment(0);
        fragmentHomeTweets.appendTweet(tweet);
    }

    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {

        final int PAGE_COUNT = 2;
        private String[] tabTitles = { "Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //The order and creation of fragments within pager
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1){
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        //How many fragments there are
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }


}
