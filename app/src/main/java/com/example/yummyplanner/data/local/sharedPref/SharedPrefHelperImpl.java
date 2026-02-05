package com.example.yummyplanner.data.local.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelperImpl implements SharedPrefHelper{

    private final SharedPreferences prefs;

    public SharedPrefHelperImpl (Context context){
        prefs = context.getSharedPreferences(PrefKeys.PREF_NAME,Context.MODE_PRIVATE);
    }

    @Override
    public void setOnboardingSeen(boolean isSeen){
        prefs.edit().putBoolean(PrefKeys.KEY_ONBOARDING_SEEN,isSeen).apply();
    }
    @Override
    public boolean hasSeenOnboarding(){
        return prefs.getBoolean(PrefKeys.KEY_ONBOARDING_SEEN,false);
    }

    @Override
    public void setGeust(boolean isGeust){
        prefs.edit().putBoolean(PrefKeys.KEY_IS_GEUST,isGeust).apply();
    }
    @Override
    public boolean isGeust(){
        return prefs.getBoolean(PrefKeys.KEY_IS_GEUST,false);
    }

    public void clearSession(){
        prefs.edit().remove(PrefKeys.PREF_NAME).apply();
    }
}
