package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	private TwitterClient client;
	private ProgressBar pb;
	private int timelineType;
	private String screenname;

	// Different timeline types supported by this fragment
	// I wanted to use an enum but that didn't work
	protected static final int HOME_TIMELINE = 1;
	protected static final int MENTIONS_TIMELINE = 2;
	protected static final int USER_TIMELINE = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Non-view initialization
		timelineType = savedInstanceState.getInt("timelineType");
		screenname = savedInstanceState.getString("screenname", null);
		client = TwitterApp.getRestClient();
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);
		// Assign our view references
		pb = (ProgressBar) v.findViewById(R.id.timelineProgressBar);
		pb.setVisibility(View.VISIBLE);
		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		// Attach the listener to the AdapterView onCreate
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// WTF?!?! After adding PullToRefresh, the total items count
				// seems to be
				// 1 greater than it used to. Something is messed up here.
				if (totalItemsCount > 1) {
					long maxId = tweets.get(totalItemsCount - 2).getUid() - 1;
					populateTimeline(maxId, 0);
				}
			}
		});

		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				fetchTimelineAsync();
				lvTweets.onRefreshComplete();
			}
		});
		lvTweets.setAdapter(aTweets);

		populateTimeline(0, 1);

		// Return the layout view
		return v;
	}

	public void fetchTimelineAsync() {
		long sinceId = tweets.get(0).getUid();
		JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				ArrayList<Tweet> newTweets = Tweet.fromJSONArray(json);

				// Insert new tweets at the beginning of the list
				// Is there a better way to do this?
				for (int i = 0; i < newTweets.size(); i++) {
					aTweets.insert(newTweets.get(i), i);
				}
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		};

		switch (timelineType) {
		case HOME_TIMELINE:
			client.GetHomeTimeline(0, sinceId, handler);
			break;

		case MENTIONS_TIMELINE:
			client.GetMentionsTimeline(0, sinceId, handler);
			break;

		case USER_TIMELINE:
			client.GetUserTimeline(0, sinceId, screenname, handler);
			break;

		default:
			// throw an exception?
			break;
		}
	}

	public void populateTimeline(long maxId, long sinceId) {
		JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		};

		switch (timelineType) {
		case HOME_TIMELINE:
			client.GetHomeTimeline(maxId, sinceId, handler);
			break;

		case MENTIONS_TIMELINE:
			client.GetMentionsTimeline(maxId, sinceId, handler);
			break;

		case USER_TIMELINE:
			client.GetUserTimeline(maxId, sinceId, screenname, handler);
			break;

		default:
			// throw an exception?
			break;
		}
	}
}
