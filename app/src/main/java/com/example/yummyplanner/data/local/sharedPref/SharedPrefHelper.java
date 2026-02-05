package com.example.yummyplanner.data.local.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private final SharedPreferences prefs;

    public SharedPrefHelper (Context context){
        prefs = context.getSharedPreferences(PrefKeys.PREF_NAME,Context.MODE_PRIVATE);
    }

    public void setOnboardingSeen(boolean isSeen){
        prefs.edit().putBoolean(PrefKeys.KEY_ONBOARDING_SEEN,isSeen).apply();
    }

    public boolean isOnboardingSeen(){
        return prefs.getBoolean(PrefKeys.KEY_ONBOARDING_SEEN,false);
    }

    public void setGeust(boolean isGeust){
        prefs.edit().putBoolean(PrefKeys.KEY_IS_GEUST,isGeust).apply();
    }

    public boolean isGeust(){
        return prefs.getBoolean(PrefKeys.KEY_IS_GEUST,false);
    }

    public void clearSession(){
        prefs.edit().remove(PrefKeys.PREF_NAME).apply();
    }
}
