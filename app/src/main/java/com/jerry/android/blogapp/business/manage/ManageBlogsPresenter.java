package com.jerry.android.blogapp.business.manage;

import com.alibaba.fastjson.JSONObject;
import com.jerry.android.blogapp.business.beans.ApiError;
import com.jerry.android.blogapp.business.beans.Blog;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageBlogsPresenter implements IManageBlogsContract.IManageBlogsPresenter
{
    private IManageBlogsContract.IManageBlogsView _view;
    private Page _currentPage;
    private boolean _isWorking = false;

    public ManageBlogsPresenter(IManageBlogsContract.IManageBlogsView view)
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


    private HttpEngine.HttpCallback onDeleteBlogCallback = new HttpEngine.HttpCallback()
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
            String blogId = map.getString( "id" );

            if(_view == null)
                return;

            if(blogId != null){
                _view.onDeleteBlog( blogId );
            }
            else{
                onFailure( "Delete blog failed" );
            }
        }

        @Override
        public void onFailure( String reason )
        {
            if( _view == null)
                return;

            _view.showError(reason);
        }
    };

    @Override
    public void delete( String blogId )
    {
        if(blogId == null)
            return;

        String url = Url.Api_Blogs + "/" + blogId + "/delete";

        Map<String, String> params = new HashMap<>();

        HttpEngine.getInstance().Post( url, params, this.onDeleteBlogCallback );
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
