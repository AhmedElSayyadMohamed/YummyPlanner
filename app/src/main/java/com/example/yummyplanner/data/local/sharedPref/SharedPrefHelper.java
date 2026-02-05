package com.example.yummyplanner.data.local.sharedPref;


public interface SharedPrefHelper {

     void setOnboardingSeen(boolean isSeen);
     boolean hasSeenOnboarding();
     void setGeust(boolean isGeust);
     boolean isGeust();
     void clearSession();
}
