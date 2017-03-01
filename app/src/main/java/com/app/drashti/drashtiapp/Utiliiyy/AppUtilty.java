package com.app.drashti.drashtiapp.Utiliiyy;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.app.drashti.drashtiapp.Model.User;
import com.google.gson.Gson;


public class AppUtilty {

    Context mContext;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    Gson gson;


    public AppUtilty(Context mContext) {
        this.mContext = mContext;
        mPrefs = mContext.getSharedPreferences("AverTek", Context.MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        gson = new Gson();

    }

    public void setUserData(User user) {
        String json = gson.toJson(user);
        Log.e("User", json);
        prefsEditor.putString("user", json);
        prefsEditor.commit();
    }


    public User getUserData() {
        gson = new Gson();
        String json = mPrefs.getString("user", "");
        Log.e("JSON", json);
        User user = gson.fromJson(json, User.class);
        return user;
    }

}
