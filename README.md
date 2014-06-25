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