package com.jerry.android.blogapp.business.edit;

import android.os.Bundle;
import android.util.Log;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseSwipeBackActivity;

public class BlogEditActivity extends BaseSwipeBackActivity implements IBlogEditContract.IBlogEditView
{
    private static final String TAG = "BlogEditActivity";

    private IBlogEditContract.IBlogEditPresenter _presenter;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //setContentView(  );

        _presenter = new BlogEditPresenter( this );
        _presenter.start();
    }

    @Override
    protected void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }

    @Override
    public void showBlogDetail( Blog blog )
    {
        Debug.log( TAG, blog.toString() );
    }

    @Override
    public void onSavedBlog( Blog blog )
    {
        Debug.log( TAG, blog.toString() );
    }

    @Override
    public void setPresenter( IBlogEditContract.IBlogEditPresenter presenter )
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
