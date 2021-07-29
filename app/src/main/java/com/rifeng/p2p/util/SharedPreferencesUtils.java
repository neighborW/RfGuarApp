package com.rifeng.p2p.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import com.google.gson.Gson;

public class SharedPreferencesUtils {

    private Application application;


    private static final String FILE_NAME = "share_date";

    private static final SharedPreferencesUtils sharedInstance = new SharedPreferencesUtils();

    private SharedPreferencesUtils(){

    }

    public static SharedPreferencesUtils getInstance(Application appInstance){
        sharedInstance.application = appInstance;
        return  sharedInstance;
    }

    public void saveObject(String key, Object object) {
        SharedPreferences sp = application.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(key, json);
        editor.commit();
    }


    public void removeParams(String key) {
        SharedPreferences sp = application.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public <T> T getObject(String key, Class<T> tClass) {
        SharedPreferences sp = application.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, tClass);
        }
    }

    public void setParam(String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = application.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

    public <T> T getParam(String key, T defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = application.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return (T) sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return (T) (Integer) (sp.getInt(key, (Integer) defaultObject));
        } else if ("Boolean".equals(type)) {
            return (T) (Boolean) sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return (T) (Float) sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return (T) (Long) sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }
}
