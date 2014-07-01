package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "oTWoGSJMhxQFuF72hNupyXPdp";       // Change this
    public static final String REST_CONSUMER_SECRET = "tLEavPABMItOyzwd6UiKYbkKwqUj1EGeXxyPHZt548XmWdARul"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void GetHomeTimeline(long maxId, long sinceId, JsonHttpResponseHandler handler) {
    	GetGenericTimeline("home", maxId, sinceId, null, handler);
    }
    
    public void GetAccountCredentials(JsonHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("account/verify_credentials.json");
		Log.d("debug", "url - " + apiUrl.toString());
    	client.get(apiUrl, null, handler);
    }
    
    public void PostUpdate(String tweetText, JsonHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", tweetText);
    	client.post(apiUrl, params, handler);
    }

	public void GetMentionsTimeline(long maxId, long sinceId, JsonHttpResponseHandler handler) {
		GetGenericTimeline("mentions", maxId, sinceId, null, handler);
	}
    
	public void GetUsersShow(String screenname, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenname);
		client.get(apiUrl, params, handler);
	}
	
	public void GetUserTimeline(long maxId, long sinceId, String screenname, JsonHttpResponseHandler handler) {
		GetGenericTimeline("user", maxId, sinceId, screenname, handler);
	}

	private void GetGenericTimeline(String timelineName, long maxId, long sinceId, String screenname, JsonHttpResponseHandler handler) {
    	String apiUrl = getApiUrl("statuses/" + timelineName + "_timeline.json");
    	RequestParams params = new RequestParams();
    	if(sinceId != 0) {
        	params.put("since_id",  String.valueOf(sinceId));
    	} 
    	
    	if(maxId != 0) {
    		params.put("max_id", String.valueOf(maxId));
    	}
    	
    	if(screenname != null) {
    		params.put("screen_name", screenname);
    	}
    	client.get(apiUrl, params, handler);
	}

    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}