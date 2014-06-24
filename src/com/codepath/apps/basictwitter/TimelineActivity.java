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
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	static final int COMPOSE_REQUEST = 1234;
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApp.getRestClient();
		populateTimeline(0);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		// Attach the listener to the AdapterView onCreate
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				long maxId = tweets.get(totalItemsCount - 1).getUid();
				populateTimeline(maxId);
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
			//Tweet tweet = (Tweet) data.getParcelableExtra("tweet");
			Log.d("debug", "Inserting tweet into arraylist");
			//aTweets.insert(tweet, 0);
			//lvTweets.setSelection(0);
		}
	}

	public void composeMessage() {
		Intent i = new Intent(getApplicationContext(),
				ComposeTweetActivity.class);
		startActivityForResult(i, COMPOSE_REQUEST);
	}

	public void populateTimeline(long maxId) {
		client.GetHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, maxId);
	}
}
