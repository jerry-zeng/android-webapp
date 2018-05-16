package com.jerry.android.blogapp.business.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jerry.android.blogapp.business.beans.User;
import com.jerry.android.blogapp.business.utils.Debug;
import com.jerry.android.blogapp.framework.BaseAppCompatActivity;

public class RegisterActivity extends BaseAppCompatActivity implements IRegisterContract.IRegisterView
{
    private static final String TAG = "RegisterActivity";

    private IRegisterContract.IRegisterPresenter _presenter;


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //setContentView(  );

        _presenter = new RegisterPresenter( this );
        _presenter.start();
    }

    @Override
    protected void onDestroy()
    {
        _presenter.destroy();
        _presenter = null;

        super.onDestroy();
    }


    @Override
    public void onRegister( User user )
    {
        Debug.log( TAG, user.toString() );
    }

    @Override
    public void setPresenter( IRegisterContract.IRegisterPresenter presenter )
    {
        this._presenter = presenter;
    }

    @Override
    public void showError( String msg )
    {
        showToast( msg );
    }

    @Override
    public void showProgress()
    {

    }

    @Override
    public void hideProgress()
    {

    }
}
