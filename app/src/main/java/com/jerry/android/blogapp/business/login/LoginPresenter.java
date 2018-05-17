package com.jerry.android.blogapp.business.login;

import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.ApiError;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter implements ILoginContract.ILoginPresenter
{
    private ILoginContract.ILoginView _view;
    private User _currentUser;
    private boolean _isWorking = false;


    public LoginPresenter(ILoginContract.ILoginView view)
    {
        this._view = view;
    }


    private HttpEngine.HttpCallback onLoginCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            Debug.print( "onLoginCallback:\n" + json );

            ApiError error = JsonUtil.deserialize( json, ApiError.class );
            if(error != null && error.getError() != null){
                onFailure(error.getError());
                return;
            }

            User user = JsonUtil.deserialize( json, User.class );
            _currentUser = user;

            if(_view == null)
                return;

            if(user != null){
                _view.hideProgress();
                _view.onLogin( user );
            }
            else{
                onFailure("Login failed");
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
    public void login( String email, String password )
    {
        if(email == null || password == null)
            return;

        String url = Url.Api_Login;

        Map<String, String> params = new HashMap<>();
        params.put( "email", email );
        params.put( "password", password );
        params.put( "remember", "true" );

        HttpEngine.getInstance().Post( url, params, this.onLoginCallback );

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
        _isWorking = false;
    }

    @Override
    public boolean isWorking()
    {
        return this._isWorking;
    }
}
