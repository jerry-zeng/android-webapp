package com.jerry.android.blogapp.business.manage;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageBlogsFragment extends BaseFragment implements IManageBlogsContract.IManageBlogsView
{
    private IManageBlogsContract.IManageBlogsPresenter _presenter;


    @Override
    public void addDataList( List<Blog> list )
    {

    }

    @Override
    public void onDeleteBlog( String blogId )
    {

    }

    @Override
    public void showLoadFailMsg()
    {

    }

    @Override
    public void setPresenter( IManageBlogsContract.IManageBlogsPresenter presenter )
    {
        this._presenter = presenter;
    }

    @Override
    public void showError( String msg )
    {
        showToast( msg );
    }

    @Override
    public void showProgress()
    {

    }

    @Override
    public void hideProgress()
    {

    }
}
