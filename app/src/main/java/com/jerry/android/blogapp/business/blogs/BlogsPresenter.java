package com.jerry.android.blogapp.business.blogs;

import com.alibaba.fastjson.JSONObject;
import com.jerry.android.blogapp.business.beans.ApiError;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.List;

public class BlogsPresenter implements IBlogsContract.IBlogsPresenter
{
    private IBlogsContract.IBlogsView _view;
    private Page _currentPage;
    private boolean _isWorking = false;

    public BlogsPresenter(IBlogsContract.IBlogsView view)
    {
        this._view = view;
    }

    private HttpEngine.HttpCallback onLoadDataCallback = new HttpEngine.HttpCallback()
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
            List<Blog> blogs = JsonUtil.deserializeArray( map.getString( "blogs" ), Blog.class );
            _currentPage = page;

            if( _view == null)
                return;

            if(blogs != null){
                _view.hideProgress();
                _view.addDataList( blogs );
            }
            else{
                onFailure( "Request blog list failed" );
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
    public void loadData( int page, int size )
    {
        String url = Url.Api_Blogs + "?page=" + page + "&size=" + size;
        HttpEngine.getInstance().Get( url, this.onLoadDataCallback );

        if( _view != null)
            _view.showProgress();
    }

    @Override
    public void start()
    {

    }

    @Override
    public void destroy()
    {
        _view = null;
        _currentPage = null;
        _isWorking = false;
    }

    @Override
    public boolean isWorking()
    {
        return _isWorking;
    }
}
