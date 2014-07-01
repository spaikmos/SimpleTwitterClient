package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	
	private long followersCnt;
	private long followingCnt;
	private String tagLine;
	

	public static User fromJSON(JSONObject jsonObject) {
		User u = new User();
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageUrl = jsonObject.getString("profile_image_url");
			
			u.followersCnt = jsonObject.getLong("followers_count");
			u.followingCnt = jsonObject.getLong("friends_count");
			
			JSONObject status = jsonObject.getJSONObject("status");
			u.tagLine = status.getString("text");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}


	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public long getFollowersCount() {
		return followersCnt;
	}
	
	public long getFollowingCount() {
		return followingCnt;
	}
	
	public String getTagline() {
		return tagLine;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeLong(uid);
		dest.writeString(screenName);
		dest.writeString(profileImageUrl);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}
		
		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
	
	private User(Parcel in) {
		name = in.readString();
		uid = in.readLong();
		screenName = in.readString();
		profileImageUrl = in.readString();
	}
	
	public User() {
	}
}
