package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	static final String INVALID_SCREENNAME = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String screenname = getIntent().getStringExtra("screenname");
		setContentView(R.layout.activity_profile);
		loadProfileInfo(screenname);
		
		// Begin the transaction
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		UserTimelineFragment userFragment = UserTimelineFragment.newInstance(screenname);
		ft.add(R.id.flUserTimelineFragment, userFragment);
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
	}

	private void loadProfileInfo(String screenname) {
		JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJSON(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
		};

		if (screenname == INVALID_SCREENNAME) {
			TwitterApp.getRestClient().GetAccountCredentials(handler);
		} else {
			Log.d("debug", "ProfileActivity uid = " + screenname);

			TwitterApp.getRestClient().GetUsersShow(screenname, handler);
		}
	}

	protected void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText("Followers: "
				+ String.valueOf(user.getFollowersCount()));
		tvFollowing.setText("Following: "
				+ String.valueOf(user.getFollowingCount()));
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),
				ivProfileImage);
	}
}
