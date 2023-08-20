# DailyReader
Daily Reader

# Introduction
This is an Android reader application that allow you to import books from your SD card and set daily reading goal for each book. 
You can check your reading time in charts as well.
Meanwhile, the application can show you the weather and the nearest library based on your current location. 
To insure the data privacy, the application also integrate authentication feature.

# The main tech that the application used are:
Navigation Drawer -- for nice UI and navigation
Room Database -- store data related to books and reading goals
Firebase Authentication -- for sign up and log in
Firebase Database -- store data in the Room Database
WorkManager -- upload data in the Room Database to the Firebase Database every day
GoogleMap API -- retrieve the nearest library
Retrofit -- retrieve weather information

# Some tips for future development
- See others UI designs as many as possible before design your own one so that you can come out with better idea
- Use Canvas to draw the text other than use TextView will be better for a reader application
- Learn how to do performance optimization
- Use a real phone to do testing
- NEVER, NEVER, NEVER git your API key to the repository!!!
