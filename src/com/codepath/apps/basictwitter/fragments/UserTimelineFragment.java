package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

public class UserTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if(savedInstanceState == null)
		{
			savedInstanceState = new Bundle();
		}
		savedInstanceState.putInt("timelineType", USER_TIMELINE);
		super.onCreate(savedInstanceState);
	}

}
