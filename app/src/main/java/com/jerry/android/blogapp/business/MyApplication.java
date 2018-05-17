package com.jerry.android.blogapp.business;

import android.app.Application;

import com.jerry.android.blogapp.business.beans.User;

public class MyApplication extends Application
{
    private static MyApplication _instance = null;

    public static MyApplication getInstance()
    {
        return _instance;
    }

    private User _currentUser;


    @Override
    public void onCreate()
    {
        super.onCreate();

        _instance = this;
    }


    public void setCurrentUser( User user)
    {
        _currentUser = user;
    }

    public User getCurrentUser()
    {
        return _currentUser;
    }
}
