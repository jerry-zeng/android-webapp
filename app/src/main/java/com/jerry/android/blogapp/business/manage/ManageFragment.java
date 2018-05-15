package com.jerry.android.blogapp.business.manage;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jerry.android.blogapp.framework.BaseFragment;

public class ManageFragment extends BaseFragment
{
    public static final int MANAGE_TYPE_BLOG = 0;
    public static final int MANAGE_TYPE_COMMENT = 1;
    public static final int MANAGE_TYPE_USER = 2;

    private TabLayout mTablayout;
    private ViewPager mViewPager;


}
