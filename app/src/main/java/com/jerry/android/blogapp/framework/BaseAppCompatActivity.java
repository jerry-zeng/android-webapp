package com.jerry.android.blogapp.framework;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseAppCompatActivity extends AppCompatActivity
{
    protected void showToast(String msg)
    {
        Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT ).show();
    }

    protected void initData()
    {

    }

    protected void initUI()
    {

    }
}
