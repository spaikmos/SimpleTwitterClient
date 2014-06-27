package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if(savedInstanceState == null)
		{
			savedInstanceState = new Bundle();
		}
		savedInstanceState.putInt("timelineType", HOME_TIMELINE);
		super.onCreate(savedInstanceState);
	}
}
