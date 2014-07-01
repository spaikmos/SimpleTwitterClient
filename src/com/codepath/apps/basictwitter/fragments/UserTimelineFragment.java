package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;
import android.util.Log;

public class UserTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			savedInstanceState = new Bundle();
		}
		
		String screenname = getArguments().getString("screenname", "");	
		savedInstanceState.putInt("timelineType", USER_TIMELINE);
		savedInstanceState.putString("screenname", screenname);
		super.onCreate(savedInstanceState);
	}

	// Dynamically allocate fragment so we can pass in a screenname for the user timeline
	public static UserTimelineFragment newInstance(String screenname) {
		UserTimelineFragment userFragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("screenname",  screenname);
		userFragment.setArguments(args);
		return userFragment;
	}

}
