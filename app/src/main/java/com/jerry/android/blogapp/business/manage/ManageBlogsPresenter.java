package com.jerry.android.blogapp.business.manage;

import com.alibaba.fastjson.JSONObject;
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

    public ManageBlogsPresenter(IManageBlogsContract.IManageBlogsView view)
    {
        this._view = view;
    }


    private HttpEngine.HttpCallback onLoadDataCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            if( _view == null)
                return;

            JSONObject map = JSONObject.parseObject( json );

            Page page = JsonUtil.deserialize( map.getString( "page" ), Page.class );
            List<Blog> blogs = JsonUtil.deserializeArray( map.getString( "blogs" ), Blog.class );

            if(blogs != null){
                _view.hideProgress();
                _view.addDataList( blogs );
            }
            else{
                _view.hideProgress();
                _view.showError( "Request blog list failed" );
            }
            _currentPage = page;
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
            JSONObject map = JSONObject.parseObject( json );
            String blogId = map.getString( "id" );
            if(blogId != null){
                _view.onDeleteBlog( blogId );
            }
            else{
                _view.showError( "Delete blog failed" );
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
    }
}
