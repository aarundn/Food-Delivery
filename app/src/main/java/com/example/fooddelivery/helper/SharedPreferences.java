package com.example.fooddelivery.helper;

import android.content.Context;

public class SharedPreferences {
//        private static final String PREF_NAME = "MyAppPrefs";
//        private static final String KEY_IS_FIRST_LAUNCH = "isFirstLaunch";
//
//        private SharedPreferences sharedPreferences;
//
//        public SharedPreferences(Context context) {
//            sharedPreferences = (SharedPreferences) context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        }
//
//        public android.content.SharedPreferences.Editor saveData(String key, boolean value) {
//            android.content.SharedPreferences.Editor  editor = sharedPreferences.saveData(key, value);
//            editor.putBoolean(key, value);
//            editor.apply();
//            return editor;
//        }
//
//    public void putBoolean(String key, Boolean value){
//        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(key,value);
//        editor.apply();
//    }
//
//        public boolean loadData(String key, boolean defaultValue) {
//            return sharedPreferences.getBoolean(key, defaultValue);
//        }
     private final android.content.SharedPreferences sharedPreferences;
    public SharedPreferences(Context context){
        sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE);
    }


    public void putBoolean(String key, Boolean value){
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }
    public void putString(String key, String value){
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }
    public void clear(){
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    }
