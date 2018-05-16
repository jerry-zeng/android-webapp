package com.jerry.android.blogapp.business.blog;

import android.os.Bundle;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseSwipeBackActivity;

public class BlogDetailActivity extends BaseSwipeBackActivity implements IBlogDetailContract.IBlogDetailView
{
    private static final String TAG = "BlogDetailActivity";

    private IBlogDetailContract.IBlogDetailPresenter _presenter;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //setContentView(  );

        _presenter = new BlogDetailPresenter( this );
        _presenter.start();

        //TODO: get blog id from previous Activity.
        String blogId = "001526132013994d97bd470d52f42a3b75c6b3d51a90dfb000";
        _presenter.loadBlogDetail( blogId );
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
    public void addComment( Comment comment )
    {
        Debug.log( TAG, comment.toString() );
    }

    @Override
    public void showProgress()
    {

    }

    @Override
    public void hideProgress()
    {

    }

    @Override
    public void setPresenter( IBlogDetailContract.IBlogDetailPresenter presenter )
    {
        this._presenter = presenter;
    }

    @Override
    public void showError( String msg )
    {
        showToast( msg );
    }
}
