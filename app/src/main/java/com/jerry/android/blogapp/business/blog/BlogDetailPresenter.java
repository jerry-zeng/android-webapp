package com.jerry.android.blogapp.business.blog;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class BlogDetailPresenter implements IBlogDetailContract.IBlogDetailPresenter
{
    private IBlogDetailContract.IBlogDetailView _view;
    private Blog _currentBlog;

    public BlogDetailPresenter(IBlogDetailContract.IBlogDetailView detailView)
    {
        this._view = detailView;
    }


    private HttpEngine.HttpCallback onLoadBlogDetailCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            if( _view == null)
                return;

            Blog blog = JsonUtil.deserialize( json, Blog.class );
            if(blog != null){
                _view.hideProgress();
                _view.showBlogDetail( blog );
            }
            else{
                _view.hideProgress();
                _view.showError( "Request blog detail failed" );
            }
            _currentBlog = blog;
        }

        @Override
        public void onFailure( String reason )
        {
            if( _view == null)
                return;

            _view.hideProgress();
            _view.showError( reason );
        }
    };

    @Override
    public void loadBlogDetail( String blogId )
    {
        if(blogId == null)
            return;

        String url = Url.Api_Blogs + "/" + blogId;
        HttpEngine.getInstance().Get( url, this.onLoadBlogDetailCallback );

        if( _view != null)
            _view.showProgress();
    }


    private HttpEngine.HttpCallback onSendCommentCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            if( _view == null)
                return;

            Comment comment = JsonUtil.deserialize( json, Comment.class );
            if(comment != null){
                _view.addComment( comment );
            }
            else{
                _view.showError( "Sending comment failed" );
            }
        }

        @Override
        public void onFailure( String reason )
        {
            if( _view == null)
                return;
            _view.showError( reason );
        }
    };

    @Override
    public void sendComment( String content )
    {
        String blogId = _currentBlog.getId();

        String url = Url.Api_Blogs + "/" + blogId + "/comments";

        Map<String, String> params = new HashMap<>();
        params.put( "content", content );
        //todo: put user.

        HttpEngine.getInstance().Post( url, params, this.onSendCommentCallback);
    }

    @Override
    public void start()
    {

    }

    @Override
    public void destroy()
    {
        _view = null;
        _currentBlog = null;
    }
}
