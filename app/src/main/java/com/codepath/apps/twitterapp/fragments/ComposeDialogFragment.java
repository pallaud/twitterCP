package com.codepath.apps.twitterapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterapp.R;
import com.codepath.apps.twitterapp.TwitterApplication;
import com.codepath.apps.twitterapp.TwitterClient;
import com.codepath.apps.twitterapp.models.Tweet;
import com.codepath.apps.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by pallaud on 6/28/16.
 */
public class ComposeDialogFragment extends DialogFragment implements View.OnClickListener {
    private EditText etStatus;
    private TextView tvCharCount;
    private TextWatcher etStatusWatcher;
    private Button btnTweet;
    private ImageButton ibCancel;
    private ImageView ivProfileImage;
    private TextView tvUsername;
    private Tweet tweet;
    private User user;

    public ComposeDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TwitterClient client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJson(response);
                tvUsername.setText("@" + user.getScreenName());
                ivProfileImage.setImageResource(0);
                Picasso.with(getContext()).load(user.getProfileImageUrl())
                        .transform(new RoundedCornersTransformation(3, 0)).into(ivProfileImage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

        });

        //find views
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        etStatus = (EditText) view.findViewById(R.id.etStatus);
        ibCancel = (ImageButton) view.findViewById(R.id.ibCancel);
        tvCharCount = (TextView) view.findViewById(R.id.tvCharCount);
        btnTweet = (Button) view.findViewById(R.id.btnTweet);

        //Set up character count
        etStatus.requestFocus();
        etStatusWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvCharCount.setText("140");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                tvCharCount.setText(String.valueOf(140 - (s.length())));
            }

            public void afterTextChanged(Editable s) {
            }
        };
        etStatus.addTextChangedListener(etStatusWatcher);
        btnTweet.setOnClickListener(this);
        ibCancel.setOnClickListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    //Make request and update timeline
    public void postTweet() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.postStatus(etStatus.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode,headers,response);
                tweet = Tweet.fromJson(response);
                OnComposeListener listener = (OnComposeListener) getActivity();
                listener.onUpdateTimeline(tweet);
                dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public interface OnComposeListener {
        void onUpdateTimeline(Tweet tweet);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibCancel:
                dismiss();
                break;
            case R.id.btnTweet:
                postTweet();
        }
    }

}
