package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();
	}
	
	private void loadProfileInfo() {
		TwitterApp.getRestClient().GetAccountCredentials(
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject json) {
						User u = User.fromJSON(json);
						getActionBar().setTitle("@" + u.getScreenName());
					}
					
				});
	}
}
