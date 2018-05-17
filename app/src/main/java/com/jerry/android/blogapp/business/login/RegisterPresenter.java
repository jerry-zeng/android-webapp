package com.jerry.android.blogapp.business.login;

import com.jerry.android.blogapp.business.Url;
import com.jerry.android.blogapp.business.beans.ApiError;
import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.core.HttpEngine;
import com.jerry.android.blogapp.framework.core.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter implements IRegisterContract.IRegisterPresenter
{
    private IRegisterContract.IRegisterView _view;
    private User _currentUser;
    private boolean _isWorking = false;

    public RegisterPresenter(IRegisterContract.IRegisterView view)
    {
        this._view = view;
    }

    private HttpEngine.HttpCallback onRegisterCallback = new HttpEngine.HttpCallback()
    {
        @Override
        public void onSuccess( String json )
        {
            Debug.print( "onRegisterCallback:\n" + json );

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
                _view.onRegister( user );
            }
            else{
                onFailure("Register failed");
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
    public void register( String name, String email, String password )
    {
        if(email == null || password == null || name == null)
            return;

        String url = Url.Api_Users;

        Map<String, String> params = new HashMap<>();
        params.put( "email", email );
        params.put( "password", password );
        params.put( "name", name );

        HttpEngine.getInstance().Post( url, params, this.onRegisterCallback );

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
