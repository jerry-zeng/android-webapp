package com.jerry.android.blogapp.business.utils;

import android.util.Log;

public class Debug
{
    public static boolean debugMode = true;

    public static void log(String msg)
    {
        if(debugMode){
            Log.i( "DEBUG", msg );
        }
    }

    public static void log(String tag, String msg)
    {
        if(debugMode){
            Log.i( tag, msg );
        }
    }

    public static void print(String msg)
    {
        if(debugMode){
            System.out.println(msg);
        }
    }
}
