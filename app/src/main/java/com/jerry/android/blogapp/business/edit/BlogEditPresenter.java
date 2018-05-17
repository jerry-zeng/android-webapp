package com.jerry.android.blogapp.business.edit;

import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class BlogEditPresenter implements IBlogEditContract.IBlogEditPresenter
{
    private IBlogEditContract.IBlogEditView _view;
    private Blog _currentBlog;
    private boolean _isWorking = false;

    public BlogEditPresenter(IBlogEditContract.IBlogEditView editView)
    {
        this._view = editView;
    }

    private HttpEngine.HttpCallback onLoadBlogDetailCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            Blog blog = JsonUtil.deserialize( json, Blog.class );
            _currentBlog = blog;

            if( _view == null)
                return;

            if(blog != null){
                _view.hideProgress();
                _view.showBlogDetail( blog );
            }
            else{
                _view.hideProgress();
                _view.showError( "Request blog detail failed" );
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

    private HttpEngine.HttpCallback onSaveBlogCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            Blog blog = JsonUtil.deserialize( json, Blog.class );

            if( _view == null)
                return;

            if(blog != null){
                _view.onSavedBlog( blog );
            }
            else{
                _view.showError( "Saving blog failed" );
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
    public void saveBlog( String title, String summary, String content )
    {
        String blogId = _currentBlog.getId();

        String url = Url.Api_Blogs + "/" + blogId + "/comments";

        Map<String, String> params = new HashMap<>();
        params.put( "title", title );
        params.put( "summary", summary );
        params.put( "content", content );
        //todo: put user.

        HttpEngine.getInstance().Post( url, params, this.onSaveBlogCallback);
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
    }

    @Override
    public boolean isWorking()
    {
        return _isWorking;
    }
}
