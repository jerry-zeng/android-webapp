package com.jerry.android.blogapp.business.manage;

import com.alibaba.fastjson.JSONObject;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.ApiError;
import com.jerry.android.blogapp.business.beans.Page;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.List;

public class ManageUsersPresenter implements IMangeUsersContract.IManageUsersPresenter
{
    private IMangeUsersContract.IManageUsersView _view;
    private Page _currentPage;
    private boolean _isWorking = false;

    public ManageUsersPresenter(IMangeUsersContract.IManageUsersView view)
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

            Debug.print( "ManageUsersPresenter.onLoadDataCallback:\n" + json );

            JSONObject map = JSONObject.parseObject( json );

            Page page = JsonUtil.deserialize( map.getString( "page" ), Page.class );
            List<User> users = JsonUtil.deserializeArray( map.getString( "users" ), User.class );
            _currentPage = page;

            if( _view == null)
                return;

            if(users != null){
                _view.hideProgress();
                _view.addDataList( users );
            }
            else{
                onFailure( "Request user list failed" );
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
        String url = Url.Api_Users + "?page=" + page + "&size=" + size;
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

    @Override
    public Page getCurrentPage()
    {
        return _currentPage;
    }
}
