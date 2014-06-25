package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	static final int COMPOSE_REQUEST = 1234;
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private PullToRefreshListView lvTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApp.getRestClient();
		populateTimeline(0, 1);
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		// Attach the listener to the AdapterView onCreate
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// WTF?!?!  After adding PullToRefresh, the total items count seems to be
				//	1 greater than it used to.  Something is messed up here.
				if(totalItemsCount > 1) {
					long maxId = tweets.get(totalItemsCount - 2).getUid();
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
		
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_compose:
			composeMessage();
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("debug", "onActivityResult");
		if (resultCode == RESULT_OK && requestCode == COMPOSE_REQUEST) {
			Tweet tweet = (Tweet) data.getParcelableExtra("tweet");
			Log.d("debug", "Inserting tweet into arraylist" + tweet.getBody());
			aTweets.insert(tweet, 0);
			lvTweets.setSelection(0);
		}
	}

	public void composeMessage() {
		Intent i = new Intent(getApplicationContext(),
				ComposeTweetActivity.class);
		startActivityForResult(i, COMPOSE_REQUEST);
	}

	public void populateTimeline(long maxId, long sinceId) {
		client.GetHomeTimeline(maxId, sinceId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void fetchTimelineAsync() {
		long sinceId = tweets.get(0).getUid();
		
		client.GetHomeTimeline(0, sinceId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				ArrayList<Tweet> newTweets = Tweet.fromJSONArray(json);
				
				// Insert new tweets at the beginning of the list
				// Is there a better way to do this?  
				for (int i=0; i<newTweets.size(); i++) {
					aTweets.insert(newTweets.get(i),  i);
				}
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
}
