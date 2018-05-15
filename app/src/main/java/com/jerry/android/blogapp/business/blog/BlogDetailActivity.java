package com.jerry.android.blogapp.business.blog;

import android.os.Bundle;
import android.util.Log;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.framework.BaseSwipeBackActivity;

public class BlogDetailActivity extends BaseSwipeBackActivity implements IBlogDetailContract.IBlogDetailView
{

    private static final String TAG = "UserDetailActivity";

    private IBlogDetailContract.IBlogDetailPresenter _presenter;


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //setContentView(  );

        initUI();

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

    void initUI(){

    }


    @Override
    public void showBlogDetail( Blog blog )
    {
        Log.i( TAG, String.format("Blog: id=%s, author=%s, title=%s", blog.getId(), blog.getUser_name(), blog.getTitle()) );
    }

    @Override
    public void addComment( Comment comment )
    {
        Log.i( TAG, String.format("add Comment: id=%s, author=%s, blog_title=%s, content=%s", comment.getId(), comment.getUser_name(), comment.getBlog_title(), comment.getContent()) );
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

    }

    @Override
    public void showError( String msg )
    {
        showToast( msg );
    }
}
