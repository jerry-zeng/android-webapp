package com.jerry.android.blogapp.business.user;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

public class UserDetailPresenter implements IUserDetailContract.IUserPresenter
{
    private IUserDetailContract.IUserView _view;
    private User _currentUser;

    public UserDetailPresenter(IUserDetailContract.IUserView userView)
    {
        this._view = userView;
    }

    private HttpEngine.HttpCallback onLoadUserDetailCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            if( _view == null)
                return;

            User user = JsonUtil.deserialize( json, User.class );
            if(user != null){
                _view.hideProgress();
                _view.showUserDetail( user );
            }
            else{
                _view.hideProgress();
                _view.showError( "Request user info failed" );
            }
            _currentUser = user;
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
    public void loadUserDetail( String userId )
    {
        if(userId == null)
            return;

        String url = Url.Api_Users + "/" + userId;
        HttpEngine.getInstance().Get( url, this.onLoadUserDetailCallback );

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
        _currentUser = null;
    }
}
