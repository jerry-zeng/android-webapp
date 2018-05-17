package com.jerry.android.blogapp.business.manage;

import com.alibaba.fastjson.JSONObject;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.Comment;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCommentsPresenter implements IManageCommentsContract.IManageCommentsPresenter
{
    private IManageCommentsContract.IManageCommentsView _view;
    private Page _currentPage;
    private boolean _isWorking = false;

    public ManageCommentsPresenter(IManageCommentsContract.IManageCommentsView view)
    {
        this._view = view;
    }


    private HttpEngine.HttpCallback onLoadDataCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            JSONObject map = JSONObject.parseObject( json );

            Page page = JsonUtil.deserialize( map.getString( "page" ), Page.class );
            List<Comment> comments = JsonUtil.deserializeArray( map.getString( "comments" ), Comment.class );
            _currentPage = page;

            if( _view == null)
                return;

            if(comments != null){
                _view.hideProgress();
                _view.addDataList( comments );
            }
            else{
                _view.hideProgress();
                _view.showError( "Request comment list failed" );
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
        String url = Url.Api_Comments + "?page=" + page + "&size=" + size;
        HttpEngine.getInstance().Get( url, this.onLoadDataCallback );

        if( _view != null)
            _view.showProgress();
    }

    private HttpEngine.HttpCallback onDeleteCommentCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            JSONObject map = JSONObject.parseObject( json );
            String commentId = map.getString( "id" );

            if(commentId != null){
                _view.onDeleteComment( commentId );
            }
            else{
                _view.showError( "Delete comment failed" );
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
    public void delete( String commentId )
    {
        if(commentId == null)
            return;

        String url = Url.Api_Comments + "/" + commentId + "/delete";

        Map<String, String> params = new HashMap<>();

        HttpEngine.getInstance().Post( url, params, this.onDeleteCommentCallback );
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
