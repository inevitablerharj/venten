package com.venten.venten.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferenceHelper {

    private static final String PREF_TIME = "Pref Time";
    private static SharedPreferenceHelper instance;
    private SharedPreferences sharedPreferences;


    private SharedPreferenceHelper(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferenceHelper getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferenceHelper(context);
        }
        return instance;
    }

    public void saveUpdateTime(long time){
        sharedPreferences.edit().putLong(PREF_TIME,time).apply();
    }

    public Long getUpdateTime(){
        return sharedPreferences.getLong(PREF_TIME,0);
    }

    public String getCachedDuration(){
        return sharedPreferences.getString("pref_cache_duration","");
    }
}
