package com.jerry.android.blogapp.business.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.edit.BlogEditActivity;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseFragment;

import java.util.List;

public class ManageBlogsFragment extends BaseFragment implements IManageBlogsContract.IManageBlogsView
{
    private static final String TAG = "ManageBlogsFragment";

    private IManageBlogsContract.IManageBlogsPresenter _presenter;


    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

    }

    @Override
    public void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }

    @Override
    public void addDataList( List<Blog> list )
    {

    }

    @Override
    public void onDeleteBlog( String blogId )
    {
        Debug.log( TAG, "deleted blog " + blogId );
    }

    @Override
    public void showLoadFailMsg()
    {
        Debug.log( TAG, "load data list failed" );
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


    private void OnClickItem()
    {


        Intent intent = new Intent( getContext(), BlogEditActivity.class );

        //String blogId = "001526130925642d9fc4d674154402484fa79870530cb46000";
        //intent.putExtra( "blogId", blogId );

        Blog blog = new Blog();
        intent.putExtra( "blog", blog );

        startActivity( intent );
    }
}
