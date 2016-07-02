# Project 3 - Twitter (Prachi version)

Twitter (Prachi version) is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: 20 hours spent in total

## User Stories

The following **required** functionality is completed:

* [ x] User can **sign in to Twitter** using OAuth login process
* [x ] User can **switch between Timeline and Mention views using tabs**
  * [ x] User is displayed the username, name, and body for each tweet
  * [ x] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
* [ x] User can **compose and post a new tweet**
  * [ x] User can click a "Compose" icon in the Action Bar on the top right
  * [ x] User can then enter a new tweet from a fragment and then post this to twitter
* [ x] User can navigate to **view their own profile**
  * [ x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [ x] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [ x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [ x] Profile view includes that user's timeline of recent tweets

The following **optional** features are implemented:

* [ x] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* [ x] User can **pull down to refresh tweets** in either timeline.
* [ x] User can **search for tweets matching a particular query** and see results.
* [ x] Improve the user interface and theme the app to feel twitter branded with colors and styles
* [ x] User can click on a tweet to be **taken to a "detail view"** of that tweet
 * [ x] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ x] User can see embedded image media within the tweet item in list or detail view.
* [ x] Compose activity is replaced with a modal compose overlay.
* [ x] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.

The following **additional** features are implemented:
In addition to being able to favorite/retweet tweets in detail view, users can do so directly from their home timeline, and icons will change to reflect that (as well as being colored when a user encounters a tweet they have already retweeted/favorited). Animations have been modified to sliding left/right animations to mimic the actual Twitter app. The compose button is a floating action button.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/dMA9PHJ.gif' title='Video Walkthrough 1' width='' alt='Video Walkthrough' />
<img src='http://i.imgur.com/Y8dM4RZ.gif' title='Video Walkthrough 2' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

The most challenging parts of this app were dealing with the HTTP client, fragment/code organization, and UI. While the UI involved was more time consuming, how to properly debug the client and how to plan fragment-activity interaction while keeping code clean and organized were the bigger conceptual challenges.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
