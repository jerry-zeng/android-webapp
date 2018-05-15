package com.jerry.android.blogapp.framework;

import android.widget.Toast;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BaseSwipeBackActivity extends SwipeBackActivity
{
    protected void showToast(String msg)
    {
        Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT ).show();
    }
}
