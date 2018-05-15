package com.jerry.android.blogapp.framework;

import android.support.v4.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment
{
    protected void showToast(String msg)
    {
        Toast.makeText( getContext(), msg, Toast.LENGTH_SHORT ).show();
    }
}
