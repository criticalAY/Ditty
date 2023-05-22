# Ditty
Short video app 
# Assignment: Mobile Application Development for Android Internship

## Dependency 
Retrofit : I am using retrofit to fetch the API data then storing the fetched APIs JSON body to data class in kotlin (model class in Java)

# Recycler View
I have used recyler view to display the fetched data using a custom layout that has a thumbnail and is using the Android's `VideoView` compotent to display the
media file.

Clicking on the the thumbnail starts the video and the media files is stopped as soon as it is goes out of focus from the screen i.e is scrolled down or up
**We need to click twice initially because I have use the video player that is coming with the android studio not a custom player so we get in the screen and click
play **

There is a dummy layout at the bottom to replicate the toolbar at the bottom but when it comes to the real word application we will use the toolbar
depending on the use caseand the freedom or the features we want to access. 

The recycler view is paginated and the API is also using pagination to avoid data wastage initially only 5 items are loaded and later the next 5 and so on.

# Installation Guidelines 
1. Pre requisites
    AndroidStudio
    JDK
    Git(VCS)
2. Clone it and open the project/repo in AndroidStudio and let the required files be downloaded and set up 
3. Hit run on the AndroidStudio (if using Physcial Device then make sure USB debugging is enabled)

This is the debug version we can also build the release build but this is a assignment so we are just testing it.
