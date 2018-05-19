package com.jerry.android.blogapp.business.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.android.blogapp.R;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ManageFragment extends BaseFragment
{
    public static final int MANAGE_TYPE_BLOG = 0;
    public static final int MANAGE_TYPE_COMMENT = 1;
    public static final int MANAGE_TYPE_USER = 2;

    private TabLayout mTablayout;
    private ViewPager mViewPager;


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_manage, null);

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //mViewPager.setOffscreenPageLimit(2);

        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题.
        MyPageAdapter mAdapter = new MyPageAdapter(getChildFragmentManager());
        mAdapter.addFragment( new ManageBlogsFragment(), "Blog" );
        mAdapter.addFragment( new ManageCommentsFragment(), "Comment" );
        mAdapter.addFragment( new ManageUsersFragment(), "User" );
        mViewPager.setAdapter( mAdapter );

        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mTablayout.addTab( mTablayout.newTab().setTag( "Blog" ) );
        mTablayout.addTab( mTablayout.newTab().setTag( "Comment" ) );
        mTablayout.addTab( mTablayout.newTab().setTag( "User" ) );
        mTablayout.setupWithViewPager( mViewPager );

        return view;
    }

    private static class MyPageAdapter extends FragmentPagerAdapter
    {
        private final List<String> titleList = new ArrayList<>();
        private final List<Fragment> fragmentList = new ArrayList<>();

        public MyPageAdapter( FragmentManager fm )
        {
            super( fm );
        }

        public void addFragment(Fragment fragment, String title)
        {
            titleList.add( title );
            fragmentList.add( fragment );
        }


        @Override
        public CharSequence getPageTitle( int position )
        {
            return titleList.get( position );
        }

        @Override
        public Fragment getItem( int position )
        {
            return fragmentList.get( position );
        }

        @Override
        public int getCount()
        {
            return fragmentList.size();
        }
    }
}
