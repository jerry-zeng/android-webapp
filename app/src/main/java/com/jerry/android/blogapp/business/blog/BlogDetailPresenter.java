package com.jerry.android.blogapp.business.blog;

import com.alibaba.fastjson.JSONObject;
import com.jerry.android.blogapp.business.MyApplication;
import com.jerry.android.blogapp.business.beans.ApiError;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogDetailPresenter implements IBlogDetailContract.IBlogDetailPresenter
{
    private IBlogDetailContract.IBlogDetailView _view;
    private Blog _currentBlog;
    private boolean _isWorking = false;
    private Page _currentPage;

    public BlogDetailPresenter(IBlogDetailContract.IBlogDetailView detailView)
    {
        this._view = detailView;
    }


    private HttpEngine.HttpCallback onLoadBlogDetailCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            ApiError error = JsonUtil.deserialize( json, ApiError.class );
            if(error != null && error.getError() != null){
                onFailure(error.getError());
                return;
            }

            Blog blog = JsonUtil.deserialize( json, Blog.class );
            _currentBlog = blog;

            if( _view == null)
                return;

            if(blog != null){
                _view.hideProgress();
                _view.showBlogDetail( blog );
            }
            else{
                onFailure( "Request blog detail failed" );
            }

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

    private HttpEngine.HttpCallback onLoadCommentCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            ApiError error = JsonUtil.deserialize( json, ApiError.class );
            if(error != null && error.getError() != null){
                onFailure(error.getError());
                return;
            }

            JSONObject map = JSONObject.parseObject( json );

            Page page = JsonUtil.deserialize( map.getString( "page" ), Page.class );
            List<Comment> comments = JsonUtil.deserializeArray( map.getString( "comments" ), Comment.class );
            _currentPage = page;

            if( _view == null)
                return;

            if(comments != null){
                _view.hideProgress();
                _view.showComments( comments );
            }
            else{
                onFailure( "Request comment list failed" );
            }
        }

        @Override
        public void onFailure( String reason )
        {
            if( _view == null)
                return;

            _view.hideProgress();
            _view.showLoadFailMsg();
        }
    };

    @Override
    public void loadBlogComments()
    {
        String url = Url.Api_Blogs + "/" + _currentBlog.getId() + "/comments";
        HttpEngine.getInstance().Get( url, this.onLoadCommentCallback );

        //if( _view != null)
        //    _view.showProgress();
    }

    private HttpEngine.HttpCallback onSendCommentCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            ApiError error = JsonUtil.deserialize( json, ApiError.class );
            if(error != null && error.getError() != null){
                onFailure(error.getError());
                return;
            }

            Comment comment = JsonUtil.deserialize( json, Comment.class );

            if( _view == null)
                return;

            if(comment != null){
                _view.addComment( comment );
            }
            else{
                onFailure( "Sending comment failed" );
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
        // put user.
        User user = MyApplication.getInstance().getCurrentUser();
        params.put( "user", JsonUtil.serialize( user ) );

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
        _isWorking = false;
        _currentPage = null;
    }

    @Override
    public boolean isWorking()
    {
        return _isWorking;
    }

    @Override
    public void setCurrentBlog( Blog blog )
    {
        this._currentBlog = blog;
    }
}
