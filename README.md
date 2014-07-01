# Week 4 Project:  Simple Twitter App with Fragments

Extend the simple twitter app.

Time spent:  xyz hours.

Completed user stories:

 * [x] Includes all required user stories from Week 3 Twitter Client
 * [x] User can switch between Timeline and Mention views using tabs.
 * [x] User can view their home timeline tweets.
 * [x] User can view the recent mentions of their username.
 * [x] User can scroll to bottom of either of these lists and new tweets will load ("infinite scroll")
 * [ ] Optional: Implement tabs in a gingerbread-compatible approach
 * [x] User can navigate to view their own profile
 * [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
 * [x] User can click on the profile image in any tweet to see another user's profile.
 * [x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [x] Profile view should include that user's timeline
 * [ ] Optional: User can view following / followers list through the profile
 * [ ] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
 * [ ] Advanced: When a network request is sent, user sees an indeterminate progress indicator
 * [ ] Advanced: User can "reply" to any tweet on their home timeline
 * [ ] The user that wrote the original tweet is automatically "@" replied in compose
 * [ ] Advanced: User can click on a tweet to be taken to a "detail view" of that tweet
 * [ ] Advanced: User can take favorite (and unfavorite) or reweet actions on a tweet
 * [ ] Advanced: Improve the user interface and theme the app to feel twitter branded
 * [ ] Advanced: User can search for tweets matching a particular query and see results
 * [ ] Bonus: User can view their direct messages (or send new ones)




# Week 3 Project:  Simple Twitter App

Create a simple twitter app.

Time spent:  15 hours total.

Completed user stories:

 * [x] User can sign in to Twitter using OAuth login
 * [x] User can view the tweets from their home timeline
 * [x] User should be able to see the username, name, body and timestamp for each tweet
 * [x] User should be displayed the relative timestamp for a tweet "8m", "7h"
 * [x] User can view more tweets as they scroll with infinite pagination
 * [x] Optional: Links in tweets are clickable and will launch the web browser (see autolink)
 * [x] User can compose a new tweet
 * [x] User can click a “Compose” icon in the Action Bar on the top right
 * [x] User can then enter a new tweet and post this to twitter
 * [x] User is taken back to home timeline with new tweet visible in timeline
 * [x] Optional: User can see a counter with total number of characters left for tweet
 * [x] Advanced: User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)
 * [ ] Advanced: User can open the twitter app offline and see last loaded tweets
 * [ ] Advanced: User can tap a tweet to display a "detailed" view of that tweet
 * [ ] Advanced: User can select "reply" from detail view to respond to a tweet
 * [ ] Advanced: Improve the user interface and theme the app to feel "twitter branded"
 * [ ] Bonus: User can see embedded image media within the tweet detail view
 * [ ] Bonus: Compose activity is replaced with a modal overlay

Release Notes:

Barely functional Twitter app.

Implementation Notes:

Spent 4 hours dealing with sending a serializable / parcelable from compose activity back to timeline activity.
Spent 3 hours trying to debug Pull-to-Refresh, when all I had to do was restart Eclipse!
Also got bitten when Pull-to-Refresh broke the endless scrolling.  Not sure my hack is the right way to fix this.

Conclusion:  Pull-to-Refresh is Lucifer's brainchild.  Nathan is mean for introducing this to us.  I should have left that thing alone.

Todo:

* I suspect there are some instances of repeated tweets, because I'm not sure if Pull-to-Refresh interacts with Endless Scroll 
properly to update the totalItemsCount.  I need to play with twitter some more to make sure this works properly.
* Didn't get to all the Advanced and Bonus materials.  I really want to do the offline SQL component and modal overlay.
* Didn't check for network connection.
* UI is awful.

Walkthrough of all user stories:

![Video Walkthrough](SimpleTwitterDemo.gif)