package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Tweet implements Parcelable {
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	
	public static Tweet fromJSON(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		// Extract values from the json to populate the member variables
		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJSON(tweetJson);
			if(tweet != null) {
				tweets.add(tweet);
			}
		}
		
		return tweets;
	}
	
	@Override
	public String toString() {
		return getBody() + "  - " + getUser().getScreenName();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(body);
		dest.writeLong(uid);
		dest.writeString(createdAt);
		dest.writeParcelable(user, 0);
	}
	
	public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
		@Override
		public Tweet createFromParcel(Parcel in) {
			return new Tweet(in);
		}
		
		@Override
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}
	};
	
	private Tweet(Parcel in) {
		body = in.readString();
		uid = in.readLong();
		createdAt = in.readString();
		user = in.readParcelable(User.class.getClassLoader());
	}
	
	public Tweet() {
	}

}
