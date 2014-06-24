package com.codepath.apps.basictwitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity {
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		client = TwitterApp.getRestClient();
		populateUserInfo();
	}
	
	public void onTweetClick(View view) {
		EditText etTweet = (EditText) findViewById(R.id.etTweet);
		String tweetText = etTweet.getText().toString();
		
		client.PostUpdate(tweetText, new JsonHttpResponseHandler() {			
			@Override
			public void onSuccess(JSONObject json) {
				Tweet tweet = Tweet.fromJSON(json);
				Intent i = new Intent();
				i.putExtra("tweet", tweet);
				setResult(RESULT_OK, i);
				finish();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void populateUserInfo() {
		client.GetAccountCredentials(new JsonHttpResponseHandler() {			
			@Override
			public void onSuccess(JSONObject json) {
				TextView tvScreenname = (TextView)findViewById(R.id.tvComposeScreenname);
				String screenname = "screenname";
				// Parse the JSON to get screenname
				try {
					screenname = json.getString("screen_name");
					String name = json.getString("name");
					String imageUrl = json.getString("profile_image_url");
					TextView tvName = (TextView) findViewById(R.id.tvComposeName);
					tvName.setText(name);
					ImageView ivProfileImage = (ImageView) findViewById(R.id.ivComposeProfilePic);
					ImageLoader imageLoader = ImageLoader.getInstance();
					imageLoader.displayImage(imageUrl, ivProfileImage);					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				tvScreenname.setText("@" + screenname);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
}
